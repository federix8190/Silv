package py.com.ceamso.administracion.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import py.com.ceamso.administracion.dao.CptDAO;
import py.com.ceamso.administracion.dao.CptLegajosDAO;
import py.com.ceamso.administracion.dao.CtsDAO;
import py.com.ceamso.administracion.model.Ceo;
import py.com.ceamso.administracion.model.Cpt;
import py.com.ceamso.administracion.model.CptLegajoPK;
import py.com.ceamso.administracion.model.Cts;
import py.com.ceamso.administracion.view.CptLegajosView;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;

/**
 *
 * @author mbaez
 */
@Stateless
public class CptService extends WritableServiceImpl<Cpt, CptDAO> {

    @Inject
    private CptDAO dao;
    
    @Inject
    private CtsDAO ctsDao;
    
    @Inject
    private CptLegajosDAO legajoDAO;

    /**
     *
     * @{@inheritDoc}
     */
    @Override
    public CptDAO getDao() {
        return dao;
    }

    public CptService() {
    }
    
    @Override
    public Cpt insertar(Cpt entity, HttpServletRequest httpRequest) throws AppException {
        try {
            if (dao.existeCpt(entity.getNivel(), entity.getSubNivel(), entity.getNumeroGasto())) {
                throw new AppException(500, "El código de CPT ya existe");
            }
            Usuario user = getCurrentUser();
            entity.setFechaCreacion(new Date());
            entity.setUsuarioCreacion(user.getId());
            entity.setIpCreacion(httpRequest.getRemoteAddr());
            entity.setActivo(true);
            validate(entity);
            getDao().insert(entity);
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(500, e.getMessage());
        }
    }
    
    @Override
    public void modificar(Long id, Cpt entity, HttpServletRequest httpRequest) throws AppException {
        try {
        	if (dao.existeCpt(entity.getNivel(), entity.getSubNivel(), entity.getNumeroGasto(), id)) {
                throw new AppException(500, "El código de CPT ya existe");
            }
            Usuario user = getCurrentUser();
            entity.setFechaModificacion(new Date());
            entity.setUsuarioModificacion(user.getId());
            entity.setIpModificacion(httpRequest.getRemoteAddr());
            validate(entity);
            getDao().modify(id, entity);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public List<String> getNiveles() {
        List<String> res = new ArrayList<>();
        List<Long> niveles = getDao().getNiveles();
        for (Long nivel : niveles) {
            res.add(nivel.toString());
        }
        res.add("TODOS");
        return res;
    }

    @Override
    public ListaResponse<Cpt> listar(Integer inicio, Integer cantidad, String orderBy, 
            String orderDir, HashMap<String, Object> filtros) {
    	
        if(orderBy.compareTo("id") == 0){
            orderBy="nivel,sub_nivel,nro_g";
           // orderDir="ASC";
        }
    
        ListaResponse<Cpt> res = getDao().listar(inicio, cantidad, orderBy, orderDir, filtros);
        return res;
    }
    
    public ListaResponse<Cts> getTramosCpt(Long nivelCpt, int inicio, int cantidad, 
            String orderDir, long anho, long mes) {
        
        List<Long> tramosAsignados = getDao().getTramosAsignados(nivelCpt, anho, mes);
        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("anho", anho);
        filtros.put("mes", mes);
        ListaResponse<Cts> res = ctsDao.listar(inicio, cantidad, "numeroTramo", orderDir, filtros);
        if (res.getRows().isEmpty()) {
            res = ctsDao.listarTramos(inicio, cantidad, orderDir);
        }
        List<Cts> lista = new ArrayList<>();
        for (Cts cts : res.getRows()) {
            if (tramosAsignados.contains(cts.getNumeroTramo())) {
                cts.setAsignado(true);
            } else {
                cts.setAsignado(false);
            }
            lista.add(cts);
        }
        res.setRows(lista);
        return res;
                
    }
    
    public Response asignarTramosCpt(Long nivelCpt, Cts cts) throws AppException {
        
        try {
            getDao().asignarTramos(nivelCpt, cts);
            return Response.ok().build();
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    /*public ListaResponse<Legajo> getLegajosRecomendados(Long nivelCpt, int inicio, int cantidad) 
            throws AppException {
        List<Integer> tramos = getDao().getTramos(nivelCpt);
        //List<Long> listaCpt = getDao().getCptByNivel(nivelCpt);
        return getDao().getLegajosRecomendados(nivelCpt, tramos, inicio, cantidad);
    }*/
    
    /**
     * {@inheritDoc}
     */
    public void eliminar(Long id, HttpServletRequest httpRequest) throws AppException {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT c " +
                    "FROM Cpt c " +
                    "WHERE c.id = :id " +
                    "AND (EXISTS (SELECT ce FROM CptE ce WHERE ce.cpt = c.id) " +
                    "OR EXISTS (SELECT cf FROM CptF cf WHERE cf.cpt = c.id))");
            Cpt cptFk = getDao().verificarConstraint(id, query);
            if(cptFk != null){
                //cptFk.setDisponible("N");
                //modificar(id, cptFk, httpRequest);
            	throw new AppException(500, Constantes.MENSAJE_ELIMINAR_REGISTRO);
            }
            else{
                eliminar(id);
            }
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public HashMap<Long, List<Long>> getConfiguracionTramos(int anho, int mes) {
        return getDao().getConfiguracionTramos(anho, mes);
    }
    
    public Response asignarLegajos(Long id, Legajo legajo, HttpServletRequest httpRequest) throws AppException {
        
        try {
            getDao().asignarLegajos(id, legajo, httpRequest);
            return Response.ok().build();
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public ListaResponse<CptLegajosView> getLegajos(Long idCpt, Integer inicio, Integer cantidad, 
            String orderBy, String orderDir, HashMap<String, Object> filtros) {
        
        if (filtros != null && filtros.containsKey("vinculacionFuncionario")) {
            if (filtros.get("vinculacionFuncionario").equals("TODOS")) {
                filtros.remove("vinculacionFuncionario");
            }
        }
        /*if (orderBy.equals("id")) {
            orderBy = "orden";
        }*/
        //return getDao().getLegajos(inicio, cantidad, orderBy, orderDir, filtros, id);
        
        int anho = (Integer) filtros.get("anho");
        int mes = (Integer) filtros.get("mes");
        List<Long> asignados = getDao().getLegajosAsignados(idCpt, anho, mes);
        ListaResponse<CptLegajosView> res = legajoDAO.listar(inicio, cantidad, orderBy, orderDir, filtros);        
        List<CptLegajosView> lista = res.getRows();
        
        List<CptLegajosView> nuevaLista = new ArrayList<>();        
        for (CptLegajosView item : lista) {
            if (asignados.contains(item.getId().getCedulaIdentidad())) {
                Long cedula = item.getId().getCedulaIdentidad();
                CptLegajoPK legajoAsignado = getDao().getCptLegajoAsignado(idCpt, cedula, anho, mes);
                if (legajoAsignado != null) {
                    item.setAsignado(true);
                } else {
                    item.setAsignado(false);
                }
            } else {
                item.setAsignado(false);
            }
            
            nuevaLista.add(item);
        }
        res.setRows(nuevaLista);
        return res;
                
    }
}

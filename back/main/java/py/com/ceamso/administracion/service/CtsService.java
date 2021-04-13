package py.com.ceamso.administracion.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import py.com.ceamso.administracion.dao.ConfiguracionCtsDAO;
import py.com.ceamso.administracion.dao.CtsDAO;
import py.com.ceamso.administracion.model.ConfiguracionCts;
import py.com.ceamso.administracion.model.Cts;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableServiceImpl;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author mbaez
 */
@Stateless
public class CtsService extends WritableServiceImpl<Cts, CtsDAO> {

    @Inject
    private CtsDAO dao;
    
    @Inject
    private ConfiguracionCtsDAO configuracionCtsDao;

    @Override
    public CtsDAO getDao() {
        return dao;
    }

    public CtsService() {
    }

    @Override
    public ListaResponse<Cts> listar(Integer inicio, Integer cantidad, String orderBy, 
            String orderDir, HashMap<String, Object> filtros) {
        if(orderBy == null || orderBy.equals("id")){
            orderBy= "id";
        }
        return super.listar(inicio, cantidad, orderBy, orderDir, filtros);
    }
    
    @Override
    public Cts obtener(Long id) throws AppException {
        try {
            System.err.println("get CTS !!!");
            return getDao().get(id);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    public List<String> getTramos() throws AppException {
        try {
            List<String> res = new ArrayList<String>();
            List<Long> tramos = getDao().getTramos();
            for (Long tramo : tramos) {
                res.add(tramo.toString());
            }
            res.add("TODOS");
            return res;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public ConfiguracionCts test() throws AppException {
        return configuracionCtsDao.get(1L);
    }
    
    public void insert(Cts dto, HttpServletRequest httpRequest) throws AppException {
        Long rango = dto.getMaximo() - dto.getMinimo();
        Long maximo = dto.getMinimo();
        
        for (int i = 0; i <= 30; i++) {
            Cts ctsCalculado = new Cts();
            ctsCalculado.setMes(dto.getMes());
            ctsCalculado.setAnho(dto.getAnho());
            ctsCalculado.setNumeroTramo(new Long(i));
            ctsCalculado.setAsignado(true);
            
            if (i != 0) {
                ctsCalculado.setMinimo(maximo);
                Double peso = configuracionCtsDao.get(new Long(i)).getPeso();
                Double tmp = new Double(maximo) + (new Double(rango) * peso / 100.0);
                maximo = tmp.longValue();
                ctsCalculado.setMaximo(maximo);
            } else {
                ctsCalculado.setMinimo(new Long(0));
                ctsCalculado.setMaximo(dto.getMinimo());
            }
            insertarOActualizar(ctsCalculado, httpRequest);
            getDao().actualizarTramos(ctsCalculado.getAnho(), ctsCalculado.getMes());
        }
    }
    
    public List<Cts> listarCts() {
        return getDao().listar();
    }
    
    public Long getRango() {
        return getDao().getRango();
    }
    
    public Cts insertarOActualizar(Cts entity, HttpServletRequest httpRequest) throws AppException {
        try {
            Cts cts = getDao().getCtsByTramo(entity.getNumeroTramo(), entity.getAnho(), entity.getMes());
            System.err.println(cts);
            Usuario user = getCurrentUser();
            if (cts == null) {
                entity.setFechaCreacion(new Date());
                entity.setUsuarioCreacion(user.getId());
                entity.setIpCreacion(httpRequest.getRemoteAddr());
                entity.setActivo(true);
                validate(entity);
                getDao().insert(entity);
            } else {
                entity.setId(cts.getId());
                entity.setFechaCreacion(cts.getFechaCreacion());
                entity.setUsuarioCreacion(cts.getUsuarioCreacion());
                entity.setIpCreacion(cts.getIpCreacion());
                entity.setFechaModificacion(new Date());
                entity.setUsuarioModificacion(user.getId());
                entity.setIpModificacion(httpRequest.getRemoteAddr());
                entity.setActivo(true);
                //validate(entity);
                getDao().modify(entity);
            }
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(500, e.getMessage());
        }
    }
}

package py.com.ceamso.administracion.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import py.com.ceamso.administracion.dao.CptEDAO;
import py.com.ceamso.administracion.model.CptE;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import py.com.ceamso.administracion.view.CptEELegajosView;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.reportes.dao.LegajoDAO;
import py.com.ceamso.reportes.model.Legajo;

/**
 *
 * @author mbaez
 */
@Stateless
public class CptEService extends WritableServiceImpl<CptE, CptEDAO> {

    @Inject
    private CptEDAO dao;
    
    @Inject
    private LegajoDAO legajoDAO;

    /**
     *
     * @{@inheritDoc}
     */
    @Override
    public CptEDAO getDao() {
        return dao;
    }
    /*
    public ListaResponse<Legajo> getLegajos(Long id, Integer inicio, Integer cantidad, 
            String orderBy, String orderDir, HashMap<String, Object> filtros) {
        
        if (filtros != null && filtros.containsKey("vinculacionFuncionario")) {
            if (filtros.get("vinculacionFuncionario").equals("TODOS")) {
                filtros.remove("vinculacionFuncionario");
            }
        }
        if (orderBy.equals("id")) {
            orderBy = "apellido, c.nombre";
        }
        return getDao().getLegajos(inicio, cantidad, orderBy, orderDir, filtros, id);
                
    }
    
    public ListaResponse<Legajo> getLegajosAsignados(Long id, Integer inicio, Integer cantidad, 
            String orderBy, String orderDir, HashMap<String, Object> filtros) {
        
        if (filtros != null && filtros.containsKey("vinculacionFuncionario")) {
            if (filtros.get("vinculacionFuncionario").equals("TODOS")) {
                filtros.remove("vinculacionFuncionario");
            }
        }
        if (orderBy.equals("id")) {
            orderBy = "apellido, c.nombre";
        }
        return getDao().getLegajosAsignados(inicio, cantidad, orderBy, orderDir, filtros, id);
                
    }
    
    public Response asignarLegajos(Long id, Legajo legajo) throws AppException {
        
        try {
            getDao().asignarLegajos(id, legajo);
            return Response.ok().build();
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    */
    
    public Response asignarLegajos(Long id, Legajo legajo, HttpServletRequest httpRequest) throws AppException {
        
        try {
            getDao().asignarLegajos(id, legajo, httpRequest);
            return Response.ok().build();
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public ListaResponse<CptEELegajosView> getLegajos(Long id, Integer inicio, Integer cantidad, 
            String orderBy, String orderDir, HashMap<String, Object> filtros) {
        
        if (filtros != null && filtros.containsKey("vinculacionFuncionario")) {
            if (filtros.get("vinculacionFuncionario").equals("TODOS")) {
                filtros.remove("vinculacionFuncionario");
            }
        }
        if (orderBy.equals("id")) {
            orderBy = "orden";
        }
        return getDao().getLegajos(inicio, cantidad, orderBy, orderDir, filtros, id);
                
    }
    
    /**
     * {@inheritDoc}
     */
    public void eliminar(Long id, HttpServletRequest httpRequest) throws AppException {
        
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT c " +
                    "FROM CptE c " +
                    "WHERE c.id = :id " +
                    "AND EXISTS (SELECT le FROM Legajo le WHERE le.idCptEe = c.id)");
            CptE cpteFk = getDao().verificarConstraint(id, query);
            if(cpteFk != null){
                //cpteFk.setActivo(false);
                //modificar(id, cpteFk, httpRequest);
            	throw new AppException(500, Constantes.MENSAJE_ELIMINAR_REGISTRO);
            }
            else{
                eliminar(id);
            }
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
}

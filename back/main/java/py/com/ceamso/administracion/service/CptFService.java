package py.com.ceamso.administracion.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import py.com.ceamso.administracion.dao.CptFDAO;
import py.com.ceamso.administracion.model.CptF;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import py.com.ceamso.administracion.view.CeoLegajosView;
import py.com.ceamso.administracion.view.CptEFLegajosView;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.reportes.dao.LegajoDAO;
import py.com.ceamso.reportes.model.Legajo;

/**
 *
 * @author mbaez
 */
@Stateless
public class CptFService extends WritableServiceImpl<CptF, CptFDAO> {

    @Inject
    private CptFDAO dao;
    
    @Inject
    private LegajoDAO legajoDAO;

    /**
     *
     * @{@inheritDoc}
     */
    @Override
    public CptFDAO getDao() {
        return dao;
    }

    /**
     * {@inheritDoc}
     */
    public void eliminar(Long id, HttpServletRequest httpRequest) throws AppException {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT c " +
                    "FROM CptF c " +
                    "WHERE c.id = :id " +
                    "AND EXISTS (SELECT le FROM Legajo le WHERE le.idCptEe = c.id)");
            CptF cptFFk = getDao().verificarConstraint(id, query);
            if(cptFFk != null){
                //cptFFk.setActivo(false);
                //modificar(id, cptFFk, httpRequest);
            	throw new AppException(500, Constantes.MENSAJE_ELIMINAR_REGISTRO);
            }
            else{
                eliminar(id);
            }
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public Response asignarLegajos(Long id, Legajo legajo, HttpServletRequest httpRequest) throws AppException {
        
        try {
            getDao().asignarLegajos(id, legajo, httpRequest);
            return Response.ok().build();
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public ListaResponse<CptEFLegajosView> getLegajos(Long id, Integer inicio, Integer cantidad, 
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
}

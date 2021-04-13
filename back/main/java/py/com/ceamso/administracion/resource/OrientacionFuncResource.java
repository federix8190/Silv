/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.resource;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import py.com.ceamso.administracion.model.OrientacionFunc;
import py.com.ceamso.administracion.service.OrientacionFuncService;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableResource;

/**
 *
 * @author konecta
 */
@Path("/administracion/orientacionfunc")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class OrientacionFuncResource extends ReadableResource<OrientacionFunc, OrientacionFuncService> {

    @EJB
    private OrientacionFuncService service;

    @Override
    public OrientacionFuncService getService() {
        return service;
    }

    public Class<OrientacionFunc> getDtoClass() {
        return OrientacionFunc.class;
    }

    @Override
    public ListaResponse<OrientacionFunc> listar(Integer pagina, Integer cantidad, String orderBy, String orderDir, String json) {
        return super.listar(pagina, cantidad, orderBy, "ASC", json); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.resource;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.com.ceamso.administracion.model.Ambito;
import py.com.ceamso.administracion.model.Departamento;
import py.com.ceamso.administracion.service.AmbitoService;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableResource;

/**
 *
 * @author konecta
 */
@Path("/administracion/ambito")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AmbitoResource extends ReadableResource<Ambito, AmbitoService> {

    @EJB
    private AmbitoService service;

    @Override
    public AmbitoService getService() {
        return service;
    }

    public Class<Ambito> getDtoClass() {
        return Ambito.class;
    }

    @Override
    public ListaResponse<Ambito> listar(Integer pagina, Integer cantidad, String orderBy, String orderDir, String json) {
        
    	return super.listar(pagina, cantidad, orderBy, "ASC", json); //To change body of generated methods, choose Tools | Templates.
    }
    
    @GET
    @Path("/ambitos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ambito> getAmbitos() {
        try {
            return getService().getAmbitos();
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
}

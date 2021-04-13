/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import py.com.ceamso.administracion.model.Clasificacion;
import py.com.ceamso.administracion.service.ClasificacionService;
import py.com.ceamso.base.WritableEntity;
import py.com.ceamso.base.WritableResource;
import py.com.ceamso.base.WritableService;

/**
 *
 * @author pablo
 */
@Path("/administracion/clasificacion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClasificacionResource extends WritableResource<Clasificacion, ClasificacionService> {

    @Inject
    private ClasificacionService service;

    @Override
    public ClasificacionService getService() {
        return service;
    }
    
}

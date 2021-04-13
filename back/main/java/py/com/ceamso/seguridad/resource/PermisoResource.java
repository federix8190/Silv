package py.com.ceamso.seguridad.resource;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import py.com.ceamso.seguridad.model.Permiso;
import py.com.ceamso.seguridad.service.PermisoService;

@Path("/seguridad/permisos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PermisoResource {
    
    @Inject
    private PermisoService service;
    
    @GET    
    @Produces(MediaType.APPLICATION_JSON)
    public List<Permiso> getAll() {
        
        try {
            return service.getAll();
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        }
        
    }
    
}

package py.com.ceamso.seguridad.resource;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import py.com.ceamso.seguridad.model.Rol;
import py.com.ceamso.seguridad.service.RolPermisoService;
import py.com.ceamso.base.WritableResource;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.seguridad.dto.RolDto;
import py.com.ceamso.seguridad.model.Permiso;

@Path("/seguridad/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RolesResource extends WritableResource<Rol, RolPermisoService> {

    @Inject
    private RolPermisoService service;

    @Override
    public RolPermisoService getService() {
        return service;
    }    
    
    @GET
    @Path("/todos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rol> getAll() {
        return getService().getAll();
    }
            
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Rol insertar(RolDto dto) {
        try {
            return getService().insertar(dto, httpRequest);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        }
    }
    
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RolDto modificar(@PathParam("id") Long id, RolDto dto) {
        try {
            getService().modificar(id, dto, httpRequest);
            return dto;
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{id}/permisos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Permiso> getPermisosRol(@PathParam("id") Long idRol) {
        
        try {
            return service.getPermisosRol(idRol);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        }
        
    }
    
    @Override
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)    
    public void eliminar(@PathParam("id") Long id) {
        try {
            getService().eliminar(id, httpRequest);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        }
    }
    
}

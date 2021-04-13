package py.com.ceamso.config;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import py.com.ceamso.base.WritableResource;
import py.com.ceamso.config.model.Configuration;
import py.com.ceamso.config.service.ConfiguracionService;

@Path("/config/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigResource extends WritableResource<Configuration, ConfiguracionService> {

    @Inject
    private ConfiguracionService service;

    @Override
    public ConfiguracionService getService() {
        return service;
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Configuration obtener(@PathParam("id") String id) {
        Configuration dto = null;
        try {
            dto = getService().obtener(id);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
        if (dto == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return dto;
    }
    
}

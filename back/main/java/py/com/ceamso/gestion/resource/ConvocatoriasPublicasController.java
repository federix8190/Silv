package py.com.ceamso.gestion.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import py.com.ceamso.base.WritableResource;
import py.com.ceamso.gestion.model.Convocatoria;
import py.com.ceamso.gestion.service.ConvocatoriaPublicaService;
import py.com.ceamso.gestion.service.ConvocatoriaService;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.utils.Utils;

/**
 *
 * @author mbaez
 */
@Path("/convocatorias-publica")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConvocatoriasPublicasController extends WritableResource<Convocatoria, ConvocatoriaPublicaService> {

    @Inject
    private ConvocatoriaPublicaService service;

    @Override
    public ConvocatoriaPublicaService getService() {
        return service;
    }
    
    @POST
    @Path("/me-interesa")
    @Produces(MediaType.APPLICATION_JSON)
    public Response meInteresa(Convocatoria dto) {
        try {
            Usuario usuario = Utils.obtenerUsuarioAutenticado();  
            boolean res = service.meInteresa(usuario, dto);
            return Response.ok().entity(res).build();
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

}

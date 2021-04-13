package py.com.ceamso.reportes.resource;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import py.com.ceamso.reportes.model.Anexos;
import py.com.ceamso.reportes.service.AnexoService;
import py.com.ceamso.reportes.service.ProgramasService;

/**
 *
 * @author konecta
 */
@Path("/programas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProgramasResource {
    
    @EJB
    private ProgramasService service;  
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getProgramas() {
        return service.getProgramas();
    }
    
    @GET
    @Path("/subprogramas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getSubProgramas() {
        return service.getSubProgramas();
    }
    
}

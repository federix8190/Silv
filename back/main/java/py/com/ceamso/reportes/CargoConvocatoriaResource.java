package py.com.ceamso.reportes;

import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableResource;
import py.com.ceamso.reportes.model.CargoConvocatoria;
import py.com.ceamso.reportes.service.CargoConvocatoriaService;

@Path("/cargos-convocatorias/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CargoConvocatoriaResource extends ReadableResource<CargoConvocatoria, CargoConvocatoriaService> {

    @EJB
    private CargoConvocatoriaService service;

    @Override
    public CargoConvocatoriaService getService() {
        return service;
    }

    public Class<CargoConvocatoria> getDtoClass() {
        return CargoConvocatoria.class;
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<CargoConvocatoria> listar(
            @QueryParam("anho") Integer anho,
            @QueryParam("mes") Integer mes,
            @QueryParam("sortBy") @DefaultValue("id") String orderBy,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir) {
    	
    	HashMap<String, Object> filtros = new HashMap<String, Object>(); 
    	filtros.put("anho", anho);
    	filtros.put("mes", mes);
    	return getService().listar(0, -1, orderBy, orderDir, filtros);
    }
    
    
}

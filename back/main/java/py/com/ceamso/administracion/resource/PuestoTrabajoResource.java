package py.com.ceamso.administracion.resource;

import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import py.com.ceamso.administracion.model.Cts;
import py.com.ceamso.administracion.service.PuestoTrabajoService;

/**
 *
 * @author konecta
 */

@Path("/administracion/puesto-trabajo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PuestoTrabajoResource {
    
    @Inject
    PuestoTrabajoService service;
    
    @POST
    @Path("/cargar-tabla")
    @Produces(MediaType.APPLICATION_JSON)
    public void cargarTabla(
            @QueryParam("anho") int anho, 
            @QueryParam("mes") int mes) {
        
        try {
            
            System.err.println("cargarTabla");
            
            service.cargarPuestosExistentes(anho, mes);
            /*List<Object[]> lista = service.listarPlanilla(anho, mes);
            BigDecimal maxNroPuesto = service.getMaximoNumeroPuesto();
            for (Object[] datos : lista) {
                service.cargarTabla(datos, anho, mes, maxNroPuesto);
            }*/
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        }
    }
    
}

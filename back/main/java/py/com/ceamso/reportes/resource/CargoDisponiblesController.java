package py.com.ceamso.reportes.resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.type.TypeReference;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableResource;
import py.com.ceamso.reportes.facade.CargoDisponibleFacade;
import py.com.ceamso.reportes.facade.CargoNoAsignadoFacade;
import py.com.ceamso.reportes.model.CargoDisponibleView;
import py.com.ceamso.reportes.model.CargoDisponibleView;
import py.com.ceamso.reportes.service.CargoDisponibleService;
import py.com.ceamso.utils.Utils;

@Path("/cargos-disponibles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CargoDisponiblesController extends ReadableResource<CargoDisponibleView, CargoDisponibleService> {
//public class CargoDisponiblesController extends ReadableResource<Cargo, CargoService> {
    @EJB
    private CargoDisponibleService service;

    @Override
    public CargoDisponibleService getService() {
        return service;
    }
    
    @Inject
    private CargoDisponibleFacade facade;
    
    public CargoDisponibleFacade getFacade(){
        return facade;
    }

    public Class<CargoDisponibleView> getDtoClass() {
        return CargoDisponibleView.class;
    }
    
     /**
     * Este servicio se encarga de obtener la lista de datos, que cumplen con
     * los criterios de filtrado, en forma de CSV
     *
     * @param pagina
     * @param cantidad
     * @param orderBy
     * @param orderDir
     * @param json
     * @return El archivo CSV
     */
    @GET
    @Path("/data/csv")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response getCsv(@QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad,
            @QueryParam("sortBy") @DefaultValue("id") String orderBy,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir,
            @QueryParam("filters") String json) {
        
        //se calcula el inicio de la grilla
        pagina = pagina > 0 ? pagina : 1;
        Integer inicio = (pagina - 1) * cantidad;
        
        //se parsea el json para consutrir el filtro
        HashMap<String, Object> filtros = null;
        if (json != null && json.trim().length() > 2) {
            try {
                filtros = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
                });
                filtros = setearFiltros(filtros, httpRequest.getPathInfo());
            } catch (Exception e) {
                throw new WebApplicationException(e.getMessage(),
                        Response.Status.INTERNAL_SERVER_ERROR);
            }
        }
        ListaResponse<CargoDisponibleView> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
        byte[] bytes = getFacade().getCSV(list);
        return Response.status(200).entity(bytes)
                .header("contentType", "application/x-download")
                .header("Content-Disposition", "attachment; filename=\""+genFileName()+".csv\"")
                .build();
    }
    
    @DELETE
    @Path("/{id}/eliminar")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean eliminarCargoVacante(@PathParam("id") Long id) {
        try {
            boolean eliminado;
            eliminado = getService().eliminarCargoVacante(id, httpRequest);
            return eliminado;
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Este servicio se encarga de obtener la lista de datos, que cumplen con
     * los criterios de filtrado, en forma de XLS
     *
     * @param pagina
     * @param cantidad
     * @param orderBy
     * @param orderDir
     * @param json
     * 
     * @return El archivo XLS
     */
    @GET
    @Path("/data/xls")
    @Produces("application/vnd.ms-excel")
    public Response getXls(@QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad,
            @QueryParam("sortBy") @DefaultValue("id") String orderBy,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir,
            @QueryParam("filters") String json) {
        
        //se calcula el inicio de la grilla
        pagina = pagina > 0 ? pagina : 1;
        Integer inicio = (pagina - 1) * cantidad;
        
        //se parsea el json para consutrir el filtro
        HashMap<String, Object> filtros = null;
        if (json != null && json.trim().length() > 2) {
            try {
                filtros = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
                });
                filtros = setearFiltros(filtros, httpRequest.getPathInfo());
            } catch (Exception e) {
                throw new WebApplicationException(e.getMessage(),
                        Response.Status.INTERNAL_SERVER_ERROR);
            }
        }
        
        ListaResponse<CargoDisponibleView> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
        byte[] out = getFacade().getCSV(list);
        return Response
                .ok(out)
                .header("contentType", "application/vnd.ms-excel")
                .header("Content-Disposition", "attachment; filename=\""+genFileName()+".xls\"")
                .build();
    }

    /**
     * Este servicio se encarga de obtener la lista de datos, que cumplen con
     * los criterios de filtrado, en forma de PDF
     * 
     * @param pagina
     * @param cantidad
     * @param orderBy
     * @param orderDir
     * @param json
     * 
     * @return El archivo PDF
     */
    @GET
    @Path("/data/pdf")
    @Produces("application/pdf")
    public Response getPdf(@QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad,
            @QueryParam("sortBy") @DefaultValue("id") String orderBy,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir,
            @QueryParam("filters") String json) {
                
        //se calcula el inicio de la grilla
        pagina = pagina > 0 ? pagina : 1;
        Integer inicio = (pagina - 1) * cantidad;
        
        //se parsea el json para consutrir el filtro
        HashMap<String, Object> filtros = null;
        if (json != null && json.trim().length() > 2) {
            try {
                filtros = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
                });
                filtros = setearFiltros(filtros, httpRequest.getPathInfo());
            } catch (Exception e) {
                throw new WebApplicationException(e.getMessage(),
                        Response.Status.INTERNAL_SERVER_ERROR);
            }
        }
        
        ListaResponse<CargoDisponibleView> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
        byte[] out = getFacade().getPDF(list);
        return Response
                .ok(out)
                .header("Content-Disposition", "attachment; filename=\""+genFileName()+".pdf\"")
                .build();
    }
    
    public String getDatasetName() {
        return "cargos-disponibles";
    }
    
    private String genFileName(){
        SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
        return getDatasetName() +"-"+  d.format(new Date());
    }
    
    /*@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<CargoDisponible> listar(
            @QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad,
            @QueryParam("sortBy") @DefaultValue("id") String orderBy,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir,
            @QueryParam("filters") String json) {
        
        //se calcula el inicio de la grilla
        pagina = pagina > 0 ? pagina : 1;
        Integer inicio = (pagina - 1) * cantidad;
        
        //se parsea el json para consutrir el filtro
        HashMap<String, Object> filtros = null;
        if (json != null && json.trim().length() > 2) {
            try {
                filtros = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
                });
                filtros = setearFiltros(filtros, httpRequest.getPathInfo());
            } catch (Exception e) {
                throw new WebApplicationException(e.getMessage(),
                        Response.Status.INTERNAL_SERVER_ERROR);
            }
        }
        return getService().listarCargosDisponibles(inicio, cantidad, orderBy, orderDir, filtros);
    }*/
    
}

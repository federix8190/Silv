package py.com.ceamso.administracion.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.type.TypeReference;
import py.com.ceamso.administracion.model.ConfiguracionCts;
import py.com.ceamso.administracion.model.Cts;
import py.com.ceamso.administracion.service.ConfiguracionCtsService;
import py.com.ceamso.administracion.service.CtsService;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableResource;
import py.com.ceamso.base.WritableResource;
import py.com.ceamso.administracion.facade.CtsFacade;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author mbaez
 */
@Path("/administracion/cts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CtsResource extends WritableResource<Cts, CtsService> {

    @Inject
    private CtsService service;
    
    @Inject
    private ConfiguracionCtsService configuracionCtsService;

    @Override
    public CtsService getService() {
        return service;
    }
    
    @Inject
    private CtsFacade facade;
    
    public CtsFacade getFacade(){
        return facade;
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public void calcular(Cts dto) {
        try {
            getService().insert(dto, httpRequest);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        }
    }
    
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public ConfiguracionCts test() throws AppException {
        return getService().test();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Cts obtener(@PathParam("id") Long id) {
        Cts dto = null;
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
    
    @GET
    @Path("/tramos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getNiveles() {
        
        try {
            
            return getService().getTramos();
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/montos")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<Cts> calcularMontos(
            @QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad,
            @QueryParam("sortBy") @DefaultValue("id") String orderBy,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir,
            @QueryParam("filters") String json) throws AppException {
        
        //ListaResponse<Cts> lista = listar(pagina, cantidad, orderBy, orderDir, json);
        
        List<Cts> listaCalculada = new ArrayList<Cts>();
        List<Cts> listaCts = getService().listarCts();
        Long rango = getService().getRango();
        Long maximo = listaCts.get(0).getMinimo();
        for (Cts cts : listaCts) {
            Cts ctsCalculado = new Cts();
            ctsCalculado.setNumeroTramo(cts.getNumeroTramo());
            ctsCalculado.setMinimo(maximo);
            Double peso = configuracionCtsService.obtener(cts.getNumeroTramo()).getPeso();
            System.err.println(cts.getNumeroTramo() + " : " + rango + " - " + peso + " - " + (new Double(rango) * peso / 100.0));
            Double tmp = new Double(maximo) + (new Double(rango) * peso / 100.0);
            maximo = tmp.longValue();
            ctsCalculado.setMaximo(maximo);
            listaCalculada.add(ctsCalculado);
        }
        return new ListaResponse(listaCts.size(), listaCalculada);
    }
    
    @GET
    @Path("/actual")
    @Produces(MediaType.APPLICATION_JSON)
    public Cts actual(){
        
        Calendar now = Calendar.getInstance();
        HashMap<String, Object> map = new HashMap<>();
        map.put("mes", new Long(now.get(Calendar.MONTH) + 1));
        map.put("anho", new Long(now.get(Calendar.YEAR)));
        map.put("numeroTramo", new Long(1));
        List<Cts> listMinimo = service.listar(0, -1, "id", "DESC", map).getRows();
        
        map.put("numeroTramo", new Long(30));
        List<Cts> listMaximo = service.listar(0, -1, "id", "DESC", map).getRows();
        
        Cts minimo = null;
        Cts maximo = null;
        if (listMinimo.size() > 0 && listMaximo.size() > 0) {
            minimo = listMinimo.get(0);
            maximo = listMaximo.get(0);
        }
        
        Cts actual = new Cts();
        
        if (minimo == null && maximo == null) {
                        
            System.err.println("minimo y maximo son null");
            map = new HashMap<>();
            map.put("numeroTramo", new Long(30));
            
            listMaximo = service.listar(0, -1, "id", "DESC", map).getRows();
            if(listMaximo.size() > 0) maximo = listMaximo.get(0);

            map = new HashMap<>();
            map.put("numeroTramo", new Long(1));
            
            listMinimo = service.listar(0, -1, "id", "DESC", map).getRows();
            if (listMinimo.size() >0 ) minimo = listMinimo.get(0);
             
            if (maximo != null) {
                actual.setMes(maximo.getMes());
                actual.setAnho(maximo.getAnho());
                actual.setMaximo(maximo.getMaximo());
            }

            if(minimo != null)
                actual.setMinimo(minimo.getMinimo());
            actual.setAsignado(false);
            
        } else {
            System.err.println("El cts actual corresponde al mes actual");
            actual.setMes(new Long(now.get(Calendar.MONTH)+1));
            actual.setAnho(new Long(now.get(Calendar.YEAR)));
            actual.setMinimo(minimo.getMinimo());
            actual.setMaximo(maximo.getMaximo());
            actual.setAsignado(true);
        }

        return actual;
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
        ListaResponse<Cts> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
        byte[] bytes = getFacade().getCSV(list);
        return Response.status(200).entity(bytes)
                .header("contentType", "application/x-download")
                .header("Content-Disposition", "attachment; filename=\""+genFileName()+".csv\"")
                .build();
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
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
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
        
        ListaResponse<Cts> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
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
        
        ListaResponse<Cts> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
        byte[] out = getFacade().getPDF(list);
        return Response
                .ok(out)
                .header("Content-Disposition", "attachment; filename=\""+genFileName()+".pdf\"")
                .build();
    }

    public String getDatasetName() {
        return "cts";
    }
    
    private String genFileName(){
        SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
        return getDatasetName() +"-"+  d.format(new Date());
    }
}

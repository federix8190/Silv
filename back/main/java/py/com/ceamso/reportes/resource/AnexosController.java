package py.com.ceamso.reportes.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import py.com.ceamso.reportes.service.AnexoService;
import py.com.ceamso.reportes.model.Anexos;

import javax.ejb.EJB;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.type.TypeReference;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableResource;
import py.com.ceamso.reportes.facade.AnexoFacade;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Respuesta;
import py.com.ceamso.utils.Utils;

@Path("/anexos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnexosController extends ReadableResource<Anexos, AnexoService> {

    @EJB
    private AnexoService service;      
    
    @Inject
    private AnexoFacade facade;
    
    @Context
    UriInfo info;

    
    public AnexoFacade getFacade(){
        return facade;
    }

    public AnexoService getService() {
        return service;
    }

    public Class<Anexos> getDtoClass() {
        return Anexos.class;
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Anexos obtener(@PathParam("id") String id) {
        Anexos dto = null;        
        try {
            String[] tokens = id.split("-");
            dto = getService().obtener(tokens);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
        if (dto == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return dto;
    }
    
    @POST
    @Path("/cargar-anexos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cargarAnexosHacienda(
            @QueryParam("anho") int anho, 
            @QueryParam("mes")int mes) {
        
        try {
            
            getService().cargarAnexosHacienda(anho, mes);
            return Response.ok().build();
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{cedula}/{anho}/{mes}/asignaciones")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Long> obtenerAsignaciones(@PathParam("cedula") Long cedula, 
            @PathParam("anho") int anho, 
            @PathParam("mes") int mes) {
        try {
            return getService().obtenerAsignaciones(cedula, anho, mes);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{cedula}/{anho}/{mes}/cptee-categoria")
    @Produces(MediaType.APPLICATION_JSON)
    public Long obtenerPorCategoria(@PathParam("cedula") Integer cedula, 
            @PathParam("anho") int anho, 
            @PathParam("mes") int mes) {
        try {
            return getService().obtenerPorCategoria(cedula, anho, mes);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Path("/asignar-legajos-cpt")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response asignarLegajosCpt(AsignarLegajos datos) {
        
        try {
            
            getService().asignarLegajosCpt(datos, httpRequest);
            return Response.ok().build();
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Path("/asignar-legajos-ceo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response asignarLegajosCeo(AsignarLegajos datos) {
        
        try {
            
            getService().asignarLegajosCeo(datos, httpRequest);
            return Response.ok().build();
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Este servicio se encarga de obtener la lista de datos, que cumplen con
     * los criterios de filtrado, en forma de CSV
     *
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
        
        ListaResponse<Anexos> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
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
        
        ListaResponse<Anexos> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
        System.out.println(list.getCount());
        
        byte[] out = getFacade().getCSV(list, filtros);
        return Response
                .ok(out)
                .header("contentType", "application/vnd.ms-excel")
                .header("Content-Disposition", "attachment; filename=\""+genFileName()+".csv\"")
                .build();
    }

    /**
     * Este servicio se encarga de obtener la lista de datos, que cumplen con
     * los criterios de filtrado, en forma de PDF
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
        
        ListaResponse<Anexos> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
        byte[] out = getFacade().getPDF(list);
        return Response
                .ok(out)
                .header("Content-Disposition", "attachment; filename=\""+genFileName()+".pdf\"")
                .build();
    }
    
    private Map<String, String[]> getParams() {
        return Utils.paramsToMap(info.getQueryParameters());
    }
    
    public String getDatasetName() {
        return "anexos";
    }
    
    private String genFileName(){
        SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
        return getDatasetName() +"-"+  d.format(new Date());
    }
    
    @GET
    @Path("/ultimo-periodo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUltimoPeriodo() {
        
    	HashMap<String, Object> periodo = service.getUltimoPeriodo();
    	ResponseBuilder response = Response.ok().entity(periodo);
		return response.build();
		
    }
    
    @GET
    @Path("/comparativo/estado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response estadoReporte(@QueryParam("idProceso") long idProceso) 
    		throws AppException, IOException {
        
    	String estado = service.getEstadoReporte(idProceso);
    	ResponseBuilder response = Response.ok().entity(new Respuesta(true, estado));
		return response.build();
		
    }
    
    @GET
    @Path("/comparativo/descargar")
    @Produces("application/vnd.ms-excel")
    public Response descargarComparativo(@QueryParam("idProceso") long idProceso) 
    		throws AppException, IOException {
        
    	String path = service.getPathReporte(idProceso);
    	File file = new File(path);
    	String[] tokens = path.split("/");
    	String fileName = tokens[tokens.length - 1];
    	
    	FileInputStream in = new FileInputStream(file);
    	byte[] bytes = new byte[(int)file.length()];
    	in.read(bytes);
    	 
    	//Hacemos el encode de los bytes leídos
    	String encodedBase64 = new String(Base64.encodeBase64(bytes));
    	in.close();
    	
    	ResponseBuilder response = Response.ok().entity(encodedBase64);
		response.header("Content-Disposition", "attachment; filename=" + fileName);
		return response.build();
		
    }
    
    @GET
    @Path("/comparativo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response comparativo(
    		@QueryParam("anhoInicio") int anhoInicio,
    		@QueryParam("mesInicio") int mesInicio,
    		@QueryParam("anhoFinal") int anhoFinal,
    		@QueryParam("mesFinal") int mesFinal) throws AppException, IOException {
        
    	//String yourSystemPath = System.getProperty("jboss.server.data.dir");
    	long idProceso = service.getIdProceso(httpRequest.getRemoteAddr());
    	new TareaAsincrona(anhoInicio, mesInicio, anhoFinal, mesFinal, service, idProceso).start();
		ResponseBuilder response = Response.ok(idProceso);
    	//ResponseBuilder response = Response.ok(yourSystemPath);
    	return response.build();
		
    }
    
    class TareaAsincrona extends Thread {
    	
    	@EJB
        private AnexoService service;
    	
    	int anhoInicio; 
    	int mesInicio; 
    	int anhoFinal; 
    	int mesFinal;
        long idProceso;
        
    	public TareaAsincrona(int anhoInicio, int mesInicio, int anhoFinal, int mesFinal, 
    			AnexoService service, long id) {
    		
    		this.anhoFinal = anhoFinal;
    		this.anhoInicio = anhoInicio;
    		this.mesFinal = mesFinal;
    		this.mesInicio = mesInicio;
    		this.service = service;
    		this.idProceso = id;
    	}
        
	    @Override    
	    public void run() {
	                
	        try {
	        	service.comparativo(anhoInicio, mesInicio, anhoFinal, mesFinal, idProceso);
			} catch (AppException e) {
				service.actualizarEstadoReporte("F", idProceso);
				e.printStackTrace();
			} catch (IOException e) {
				service.actualizarEstadoReporte("F", idProceso);
				e.printStackTrace();
			}
	    }
    }

}

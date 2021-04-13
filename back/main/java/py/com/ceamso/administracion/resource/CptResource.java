package py.com.ceamso.administracion.resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import py.com.ceamso.administracion.model.Cpt;
import py.com.ceamso.administracion.service.CptService;
import py.com.ceamso.base.WritableResource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.type.TypeReference;
import py.com.ceamso.administracion.facade.CptFacade;
import py.com.ceamso.administracion.model.Cts;
import py.com.ceamso.administracion.view.CptLegajosView;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.reportes.model.Legajo;

/**
 *
 * @author mbaez
 */
@Path("/administracion/cpt")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CptResource extends WritableResource<Cpt, CptService> {

    @Inject
    private CptService service;

    @Override
    public CptService getService() {
        return service;
    }
    
    @Inject
    private CptFacade facade;
    
    public CptFacade getFacade(){
        return facade;
    }
    
    @GET
    @Path("/niveles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getNiveles() {
        
        try {
            
            return getService().getNiveles();
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/configuracion-tramos")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<Long, List<Long>> getConfiguracionTramos(
            @QueryParam("anho") int anho, 
            @QueryParam("mes") int mes) {
        
        try {
            
            return getService().getConfiguracionTramos(anho, mes);
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{nivelCpt}/tramos")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<Cts> getTramosCpt(
            @PathParam("nivelCpt") Long nivelCpt,
            @QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir,
            @QueryParam("anho") long anho,
            @QueryParam("mes") long mes) {
        
        try {
            
            pagina = pagina > 0 ? pagina : 1;
            Integer inicio = (pagina - 1) * cantidad;
            return getService().getTramosCpt(nivelCpt, inicio, cantidad, orderDir, anho, mes);
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Path("/{nivelCpt}/tramos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response asignarTramosCpt(@PathParam("nivelCpt") Long nivelCpt, Cts cts) {
        
        try {
            
            return getService().asignarTramosCpt(nivelCpt, cts);
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    /*@GET
    @Path("/{id}/legajos-recomendados")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<Legajo> getLegajosRecomendados(@PathParam("id") Long id,
            @QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad) {
        
        try {
            
            pagina = pagina > 0 ? pagina : 1;
            Integer inicio = (pagina - 1) * cantidad;
            return getService().getLegajosRecomendados(id, inicio, cantidad);
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }*/

    /**
     * Se encarga de eliminar un registro de la base de datos.
     *
     * @param id el primary key del registro a eliminar
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public void eliminar(@PathParam("id") Long id) {
        try {
            getService().eliminar(id, httpRequest);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        }
    }
    
    @GET
    @Path("/{id}/legajos")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<CptLegajosView> getLegajos(@PathParam("id") Long id,
            @QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad,
            @QueryParam("sortBy") @DefaultValue("id") String orderBy,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir,
            @QueryParam("filters") String json) {
        
        if (id == -1) {
            return new ListaResponse<>();
        }
        
        try {
            
            // se calcula el inicio de la grilla
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
                    throw new WebApplicationException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
                }
            }
            return getService().getLegajos(id, inicio, cantidad, orderBy, orderDir, filtros);
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Path("/{id}/legajos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response asignarLegajos(@PathParam("id") Long id, Legajo legajo) {
        
        try {
            
            return getService().asignarLegajos(id, legajo, httpRequest);
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
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
        ListaResponse<Cpt> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
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
        
        ListaResponse<Cpt> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
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
        
        ListaResponse<Cpt> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
        byte[] out = getFacade().getPDF(list);
        return Response
                .ok(out)
                .header("Content-Disposition", "attachment; filename=\""+genFileName()+".pdf\"")
                .build();
    }
    
    public String getDatasetName() {
        return "cpt";
    }
    
    private String genFileName(){
        SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
        return getDatasetName() +"-"+  d.format(new Date());
    }
}

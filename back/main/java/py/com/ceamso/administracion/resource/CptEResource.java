package py.com.ceamso.administracion.resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import py.com.ceamso.administracion.model.CptE;
import py.com.ceamso.administracion.service.CptEService;
import py.com.ceamso.base.WritableResource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.type.TypeReference;
import py.com.ceamso.administracion.facade.CptEFacade;
import py.com.ceamso.administracion.view.CptEELegajosView;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.reportes.model.Legajo;

/**
 *
 * @author mbaez
 */


@Path("/administracion/cpt-ee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CptEResource extends WritableResource<CptE, CptEService> {

    @Inject
    private CptEService service;

    @Override
    public CptEService getService() {
        return service;
    }
    
    @Inject
    private CptEFacade facade;
    
    public CptEFacade getFacade(){
        return facade;
    }
    
    /*
    @GET
    @Path("/{id}/legajos")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<Legajo> getLegajos(@PathParam("id") Long id,
            @QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad,
            @QueryParam("sortBy") @DefaultValue("id") String orderBy,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir,
            @QueryParam("filters") String json) {
        
        try {
            
            // se calcula el inicio de la grilla
            pagina = pagina > 0 ? pagina : 1;
            Integer inicio = (pagina - 1) * cantidad;

            // se parsea el json para consutrir el filtro
            HashMap<String, Object> filtros = getFiltrosLegajos(json);
            return getService().getLegajos(id, inicio, cantidad, orderBy, orderDir, filtros);
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{id}/legajos-asignados")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<Legajo> getLegajosAsignados(@PathParam("id") Long id,
            @QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad,
            @QueryParam("sortBy") @DefaultValue("id") String orderBy,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir,
            @QueryParam("filters") String json) {
        
        try {
            
            // se calcula el inicio de la grilla
            pagina = pagina > 0 ? pagina : 1;
            Integer inicio = (pagina - 1) * cantidad;

            // se parsea el json para consutrir el filtro
            HashMap<String, Object> filtros = getFiltrosLegajos(json);
            return getService().getLegajosAsignados(id, inicio, cantidad, orderBy, orderDir, filtros);
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Path("/{id}/legajos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response asignarLegajos(@PathParam("id") Long id, Legajo legajo) {
        
        try {
            
            return getService().asignarLegajos(id, legajo);
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    */

    @GET
    @Path("/{id}/legajos")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<CptEELegajosView> getLegajos(@PathParam("id") Long id,
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
    
    //Obtiene el siguiente nro para CPTEE
    @GET
    @Path("/{nro}/obtenerNroCPT")
    @Produces(MediaType.APPLICATION_JSON)
    public Long obtenerNroCPT(@PathParam("nro") Long nro) {
        try {
            return getService().getDao().getCptAsignado(nro)+1;
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
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
        ListaResponse<CptE> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
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
        
        ListaResponse<CptE> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
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
        
        ListaResponse<CptE> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
        byte[] out = getFacade().getPDF(list);
        return Response
                .ok(out)
                .header("Content-Disposition", "attachment; filename=\""+genFileName()+".pdf\"")
                .build();
    }
    
    public String getDatasetName() {
        return "cpte";
    }
    
    private String genFileName(){
        SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
        return getDatasetName() +"-"+  d.format(new Date());
    }
}

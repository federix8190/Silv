package py.com.ceamso.administracion.resource;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import py.com.ceamso.administracion.model.Ceo;
import py.com.ceamso.administracion.model.Documento;
import py.com.ceamso.administracion.service.CeoService;
import py.com.ceamso.base.WritableResource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.type.TypeReference;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import py.com.ceamso.administracion.facade.CeoFacade;
import py.com.ceamso.administracion.view.CeoLegajosView;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.seguridad.resource.UsuarioController;
import py.com.ceamso.utils.Respuesta;

/**
 *
 * @author mbaez
 */
@Path("/administracion/ceo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CeoResource extends WritableResource<Ceo, CeoService> {

    @Inject
    private CeoService service;

    @Override
    public CeoService getService() {
        return service;
    }
    
    @Inject
    private CeoFacade facade;
    
    public CeoFacade getFacade(){
        return facade;
    }
    
    @GET
    @Path("/vigentes")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<Ceo> getByRange(
            @QueryParam("anho") int anho,
            @QueryParam("mes") int mes) {
    	
    	return getService().getByRange(anho, mes);
    }
    
    @GET
    @Path("/{id}/legajos")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<CeoLegajosView> getLegajos(@PathParam("id") Long id,
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
    
    @PUT
    @Path("/{id}/cambiar-estado")
    @Produces(MediaType.APPLICATION_JSON)
    public void cambiarEstado(@PathParam("id") Long id, Ceo dto) {
        try {
        	System.err.println("cambiarEstado Ceo");
            getService().cambiarEstado(id, dto, httpRequest);
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
        ListaResponse<Ceo> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
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
        
        ListaResponse<Ceo> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
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
        
        ListaResponse<Ceo> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
        byte[] out = getFacade().getPDF(list);
        return Response
                .ok(out)
                .header("Content-Disposition", "attachment; filename=\""+genFileName()+".pdf\"")
                .build();
    }
    
    @POST
    @Path("/subir-cv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response subirCV(MultipartFormDataInput input) {

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");
        if (inputParts == null || inputParts.size() == 0) {
        	System.err.println("Exportar datos CEO - Vacio");
            return Response.ok().build();
        }

        getService().actualizarCeo(inputParts, httpRequest);

        return Response.ok().build();
    }
    
    public String getDatasetName() {
        return "ceo";
    }
    
    private String genFileName(){
        SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
        return getDatasetName() +"-"+  d.format(new Date());
    }
}

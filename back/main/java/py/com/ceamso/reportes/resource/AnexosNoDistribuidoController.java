/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.type.TypeReference;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableResource;
import py.com.ceamso.reportes.facade.AnexosNoDistribuidoFacade;
import py.com.ceamso.reportes.model.Anexos;
import py.com.ceamso.reportes.model.AnexosNoDistribuido;
import py.com.ceamso.reportes.service.AnexosNoDistribuidoService;
import py.com.ceamso.utils.Utils;

/**
 *
 * @author daniel.rojas
 */

@Path("/anexos-no-distribuido")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnexosNoDistribuidoController extends ReadableResource<AnexosNoDistribuido, AnexosNoDistribuidoService>{
    
    @EJB
    AnexosNoDistribuidoService service;
    
    @Inject
    private AnexosNoDistribuidoFacade facade;
    
    @Context
    UriInfo info;

    
    public AnexosNoDistribuidoFacade getFacade(){
        return facade;
    }

    public AnexosNoDistribuidoService getService() {
        return service;
    }

    public Class<AnexosNoDistribuido> getDtoClass() {
        return AnexosNoDistribuido.class;
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
            @PathParam("mes") int mes, 
            @PathParam("anhoSF") int anhoSF, 
            @PathParam("mesSF") int mesSF) {
        try {
            return getService().obtenerPorCategoria(cedula, anho, mes, anhoSF, mesSF);
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
        
        ListaResponse<AnexosNoDistribuido> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
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
        
        ListaResponse<AnexosNoDistribuido> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
        System.out.println(list.getCount());
        
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
        
        ListaResponse<AnexosNoDistribuido> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
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
        return "anexos-no-distribuido";
    }
    
    private String genFileName(){
        SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
        return getDatasetName() +"-"+  d.format(new Date());
    }
}

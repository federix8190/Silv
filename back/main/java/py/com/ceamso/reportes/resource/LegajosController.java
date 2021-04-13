package py.com.ceamso.reportes.resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.type.TypeReference;
import py.com.ceamso.administracion.model.Departamento;
import py.com.ceamso.administracion.model.Distrito;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableResource;
import py.com.ceamso.reportes.dto.CargoDto;
import py.com.ceamso.reportes.dto.CarreraAdministrativaDto;
import py.com.ceamso.reportes.model.EstudiosConcluidos;
import py.com.ceamso.reportes.dto.FormacionAcademica;
import py.com.ceamso.reportes.dto.SancionPersonal;
import py.com.ceamso.reportes.dto.SumarioPersonal;
import py.com.ceamso.reportes.dto.RecorridoLaboral;
import py.com.ceamso.reportes.facade.LegajosFacade;
import py.com.ceamso.reportes.model.Apercibimientos;
import py.com.ceamso.reportes.model.CarreraAdministrativa;
import py.com.ceamso.reportes.model.CursoInformatica;
import py.com.ceamso.reportes.model.Destitucion;
import py.com.ceamso.reportes.model.DiasNoTrabajados;
import py.com.ceamso.reportes.model.Eventos;
import py.com.ceamso.reportes.model.ExperienciaLaboral;
import py.com.ceamso.reportes.model.FormacionAcademicaHacienda;
import py.com.ceamso.reportes.model.Idiomas;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.reportes.model.Multas;
import py.com.ceamso.reportes.model.MultasControl;
import py.com.ceamso.reportes.model.OtrosCursos;
import py.com.ceamso.reportes.model.OtrosEstudios;
import py.com.ceamso.reportes.model.Sobreseimiento;
import py.com.ceamso.reportes.model.Sumarios;
import py.com.ceamso.reportes.model.Suspensiones;
import py.com.ceamso.reportes.service.LegajosService;

@Path("/legajos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LegajosController extends ReadableResource<Legajo, LegajosService> {

    @EJB
    private LegajosService service;

    @Override
    public LegajosService getService() {
        return service;
    }
    
    @Inject
    private LegajosFacade facade;
    
    public LegajosFacade getFacade(){
        return facade;
    }

    public Class<Legajo> getDtoClass() {
        return Legajo.class;
    }
    
    @Deprecated
    @PUT
    @Path("/{id}/asignar-cpt-ee")
    @Produces(MediaType.APPLICATION_JSON)
    public Legajo asignarCptE(@PathParam("id") Long id, Legajo dto) {
        try {
            //getService().modificar(id, dto, httpRequest);
            return dto;
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{id}/cargos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CargoDto> getCargosLegajo(@PathParam("id") Long id) {
        try {
            return getService().getCargosLegajo(id);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/actualizar-datos/{anho}/{mes}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarLegajo(@PathParam("anho") int anho, @PathParam("mes") int mes) {
        try {
            //getService().actualizarLegajo(anho, mes);
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/vinculaciones")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getVinculaciones() {
        try {
            return getService().getVinculaciones();
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/orientacionFunc")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getOrientacionFunc() {
        try {
            return getService().getOrientacionFunc();
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/ambitos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAmbitos() {
        try {
            return getService().getAmbitos();
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/conceptos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getConceptos() {
        try {
            return getService().getConceptos();
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
    @Path("/candidatos/data/csv")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response getCandidatosCsv(@QueryParam("page") @DefaultValue("1") Integer pagina,
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
        ListaResponse<Legajo> list = getService().getCandidatos(inicio, cantidad, orderBy, orderDir, filtros);
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
    @Path("/candidatos/data/xls")
    @Produces("application/vnd.ms-excel")
    public Response getCandidatosXls(@QueryParam("page") @DefaultValue("1") Integer pagina,
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
        
        ListaResponse<Legajo> list = getService().getCandidatos(inicio, cantidad, orderBy, orderDir, filtros);
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
    @Path("/candidatos/data/pdf")
    @Produces("application/pdf")
    public Response getCandidatosPdf(@QueryParam("page") @DefaultValue("1") Integer pagina,
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
        
        ListaResponse<Legajo> list = getService().getCandidatos(inicio, cantidad, orderBy, orderDir, filtros);
        byte[] out = getFacade().getPDF(list);
        return Response
                .ok(out)
                .header("Content-Disposition", "attachment; filename=\""+genFileName()+".pdf\"")
                .build();
    }
    
    @GET
    @Path("/candidatos")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<Legajo> getCandidatos(
            @QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad,
            @QueryParam("sortBy") @DefaultValue("id") String orderBy,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir,
            @QueryParam("filters") String json) {
        
        try {
            
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
            return getService().getCandidatos(inicio, cantidad, orderBy, orderDir, filtros);
            
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
        ListaResponse<Legajo> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
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
        
        ListaResponse<Legajo> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
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
        
        ListaResponse<Legajo> list = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
        byte[] out = getFacade().getPDF(list);
        return Response
                .ok(out)
                .header("Content-Disposition", "attachment; filename=\""+genFileName()+".pdf\"")
                .build();
    }
    
    @GET
    @Path("/departamentos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Departamento> getDepartamentos() {
        try {
            return getService().getDepartamentos();
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{codigoDepto}/distritos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Distrito> getDistritos(@PathParam("codigoDepto") Long codigoDepto) {
        try {
            return getService().getDistritos(codigoDepto);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/estudios-concluidos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EstudiosConcluidos> getEstudiosConcluidos(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerEstudiosConcluidos(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/otros-cursos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OtrosCursos> getOtrosCursos(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerOtrosCursos(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/otros-estudios")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OtrosEstudios> getOtrosEstudios(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerOtrosEstudios(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/curso-informatica")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CursoInformatica> getCursoInformatica(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerCursoInformatica(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/idiomas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Idiomas> getIdiomas(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerIdiomas(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/multas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Multas> getMultas(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerMultas(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/multas-control")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MultasControl> getMultasControl(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerMultasControl(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/dias-no-trabajados")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DiasNoTrabajados> getDiasNoTrabajados(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerDiasNoTrabajados(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/experiencia-laboral")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ExperienciaLaboral> getExperienciaLaboral(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerExperienciaLaboral(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/formacion-academica")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FormacionAcademica> getFormacionAcademica(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerFormacionAcademica(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    @GET
    @Path("/{ci}/sancion-personal")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SancionPersonal> getSancionPersonal(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerSancionPersonal(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    @GET
    @Path("/{ci}/sumario-personal")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SumarioPersonal> getSumarioPersonal(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerSumarioPersonal(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/eventos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Eventos> getEventos(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerEventos(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/apercibimientos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Apercibimientos> getApercibimientos(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerApercibimientos(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/sumarios")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sumarios> getSumarios(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerSumarios(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/suspensiones")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Suspensiones> getSuspensiones(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerSuspensiones(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/destitucion")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Destitucion> getDestitucion(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerDestitucion(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/sobreseimiento")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sobreseimiento> getSobreseimiento(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerSobreseimiento(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/formacion-academica-hacienda")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FormacionAcademicaHacienda> getFormacionAcademicaHacienda(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerFormacionAcademicaHacienda(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/recorrido-laboral")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RecorridoLaboral> getRecorridoLaboral(@PathParam("ci") Integer ci) {
        try {
            return getService().obtenerRecorridoLaboral(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Path("/{ci}/carrera-administrativa")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarreraAdministrativaDto> getCarreraAdministrativa(@PathParam("ci") Integer ci) {
        try {
            return getService().getCarreraAdministrativa(ci);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public String getDatasetName() {
        return "candidatos";
    }
    
    private String genFileName(){
        SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
        return getDatasetName() +"-"+  d.format(new Date());
    }
}

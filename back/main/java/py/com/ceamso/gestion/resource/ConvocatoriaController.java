package py.com.ceamso.gestion.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.com.ceamso.base.WritableResource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.type.TypeReference;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import py.com.ceamso.administracion.model.Documento;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.config.Configuracion;

import py.com.ceamso.gestion.model.Convocatoria;
import py.com.ceamso.gestion.model.Interesado;
import py.com.ceamso.gestion.service.ConvocatoriaService;
import py.com.ceamso.seguridad.resource.UsuarioController;
import py.com.ceamso.utils.AppException;

@Path("/convocatorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ConvocatoriaController extends WritableResource<Convocatoria, ConvocatoriaService> {

    @EJB
    private ConvocatoriaService service;
    
    @Inject
    @Configuracion("convocatorias_location")
    private String convocatoriasLocation;

    @Override
    public ConvocatoriaService getService() {
        return service;
    }

    public Class<Convocatoria> getDtoClass() {
        return Convocatoria.class;
    }   
    
    /**
     * Este método se encarga de obtener la lista de interesados para una convocatoria.
     * 
     * @param id
     * @param pagina
     * @param cantidad
     * @param orderBy
     * @param orderDir
     * @param json
     * @return
     * @throws NoSuchFieldException
     * @throws AppException 
     */
    @GET
    @Path("/{id}/interesados")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<Interesado> getInteresados(@PathParam("id") Long id,
            @QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad,
            @QueryParam("sortBy") @DefaultValue("id") String orderBy,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir,
            @QueryParam("filters") String json) throws NoSuchFieldException, AppException {
        
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
        return getService().getInteresados(id, inicio, cantidad, orderBy, orderDir, filtros);
        
    }
    
    @POST
    @Path("/{id}/subir-pdf")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response subirPdf(@PathParam("id") Long id, MultipartFormDataInput input) {
        
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        
        List<InputPart> inputParts = uploadForm.get("file");
        if (inputParts == null || inputParts.size() == 0) {
            return Response.ok().build();
        }
        
        InputStream is = null;
        try {

            InputPart inputPart = inputParts.get(0);
            MultivaluedMap<String, String> header = inputPart.getHeaders();
            String path = convocatoriasLocation;
            String fileName = getFileName(header);
            fileName = id + "_" + fileName;
            String file = path + "/" + fileName;

            // Guardar archivo en disco
            is = inputPart.getBody(InputStream.class, null);
            byte[] bytes = IOUtils.toByteArray(is);
            writeFile(bytes, file);
            getService().subirPdf(id, file);

        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return Response.ok().build();
    }
    
    private void writeFile(byte[] content, String filename) throws IOException {

        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();

    }
    
    protected String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

}

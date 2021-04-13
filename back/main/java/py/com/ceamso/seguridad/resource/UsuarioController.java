package py.com.ceamso.seguridad.resource;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import py.com.ceamso.administracion.model.Documento;
import py.com.ceamso.administracion.service.DocumentoService;
import py.com.ceamso.administracion.service.UsuarioService;
import py.com.ceamso.base.WritableResource;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.seguridad.service.SessionService;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;
import py.com.ceamso.utils.Respuesta;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioController extends WritableResource<Usuario, UsuarioService> {

    @Inject
    private SessionService session;
    
    @Inject
    @Configuracion("esHacienda")
    private String esHacienda;

    @Inject
    private UsuarioService service;

    @Inject
    private DocumentoService documentoService;

    @Inject
    @Configuracion("cv_template_location")
    private String cvTemplateLocation;
    
    @Inject
    @Configuracion("cv_template_file")
    private String cvTemplateFile;
    
    @Inject
    @Configuracion("cv_users_location")
    private String cvUsersLocation;

    @Override
    public UsuarioService getService() {
        return service;
    }

    @GET
    @Path("/usuario-logueado")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getUsuarioLogueado() {

        return session.getCurrentUser();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Usuario insertar(Usuario dto) {
        try {
            Usuario user = session.getCurrentUser();
            return getService().insertar(dto, httpRequest, user.getId(), false);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    @POST
    @Path("/registro-online")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario registroOnline(Usuario dto) {
        try {
            return getService().insertar(dto, httpRequest, 0L, true);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    @POST
    @Path("/cambiar-password")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cambiarPassword(Usuario dto) {
        try {

            Long id = session.getCurrentUser().getId();
            return getService().cambiarPassword(id, dto, httpRequest);

        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Path("/{cedula}/resetear-password")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta resetearPassword(@PathParam("cedula") String cedula) {
        try {
            return getService().resetearPassword(cedula);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    @POST
    @Path("/subir-cv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response subirCV(MultipartFormDataInput input) {

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");
        if (inputParts == null || inputParts.size() == 0) {
            return Response.ok().build();
        }

        Usuario usuario = session.getCurrentUser();
        if (usuario == null) {
            return unauthorized(new Respuesta(false, "Usuario no logueado"));
        }

        Long userId = usuario.getId();
        InputStream is = null;

        try {

            InputPart inputPart = inputParts.get(0);
            MultivaluedMap<String, String> header = inputPart.getHeaders();
            //System.setProperty("jboss.server.data.dir", Constantes.PATH_DOCUMENTOS);
            String path = cvUsersLocation;
            String fileName = getFileName(header);
            fileName = userId + "_" + fileName;
            String file = path + "/" + fileName;

            // Guardar archivo en disco
            is = inputPart.getBody(InputStream.class, null);
            byte[] bytes = IOUtils.toByteArray(is);
            writeFile(bytes, file);

            // Registrar en la BBDD
            Documento d = documentoService.getDocumentoByUserId(userId);
            if (d == null) {
                d = new Documento();
                d.setUserId(userId);
                d.setFileName(fileName);
                d.setPath(path);
                documentoService.insertar(d, httpRequest);
            } else {
                d.setFileName(fileName);
                documentoService.modificar(d.getId(), d, httpRequest);
            }

        } catch (Exception e) {
            System.err.println("Error enviar CV : " + e.getMessage());
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
    
    @GET
    @Path("/{id}/tiene-cv")
    public Response tieneCV(@PathParam("id") Long userId) {
        
        Documento d = documentoService.getDocumentoByUserId(userId);
        if (d != null && d.getFileName() != null) {
            return Response.ok().build();
        }
        return Response.status(404)
                .entity(new Respuesta(false, "No se encontro el Curriculum del usuario"))
                .build();
    }
    
    @GET
    @Path("/tiene-opcion-cambio-password")
    public Response tieneOpcionCambioPassword() throws AppException {
        
    	if (esHacienda == null || esHacienda.equalsIgnoreCase("N")) {
    		Usuario usuario = session.getCurrentUser();
    		Usuario user = service.obtener(usuario.getId());
    		if (user.getCodigoExterno() == null) {
    			return Response.ok().entity(true).build();
    		} else {
    			return Response.ok().entity(false).build();
    		}
    	} else {
    		return Response.ok().entity(true).build();
    	}
    }

    @GET
    @Path("/{id}/descargar-cv")
    @Produces("application/vnd.oasis.opendocument.text, application/msword")
    public Response descargarCV(@PathParam("id") Long userId) {

        
        Documento d = documentoService.getDocumentoByUserId(userId);
        if (d != null && d.getFileName() != null) {
            String fileName = cvUsersLocation + d.getFileName();
            File f = new File(fileName);
            if (!f.exists()) {
                return Response.status(404)
                        .entity(new Respuesta(false, "No se encontro el Curriculum del usuario"))
                        .build();
            }
            String mt = new MimetypesFileTypeMap().getContentType(f);
            return Response.ok(f, mt)
                    .header("Content-Disposition", "attachment; filename=\"" + d.getFileName() + "\"")
                    .build();
        }
        
        return Response.status(404)
                .entity(new Respuesta(false, "No se encontro el Curriculum del usuario"))
                .build();
        
    }
    
    @GET
    @Path("/descargar-cv-template")
    @Produces("application/vnd.oasis.opendocument.text, application/msword, application/pdf, application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    //@Produces(MediaType.APPLICATION_OCTET_STREAM)
    //@Produces("application/vnd.oasis.opendocument.spreadsheet")
    public Response descargarCVTemplate(@Context HttpServletResponse response) {

        try {
            File file = new File(cvTemplateLocation + cvTemplateFile);
            if (!file.exists()) {
                throw new WebApplicationException(404);
            }
            ServletOutputStream outStream = response.getOutputStream();
            byte[] bbuf = new byte[1024];
            InputStream in = new FileInputStream(file);
            int length = 0;
            while ((in != null) && ((length = in.read(bbuf)) != -1)) {
                outStream.write(bbuf, 0, length);
            }
            in.close();
            outStream.flush();
            //String mt = new MimetypesFileTypeMap().getContentType(file);
            //return Response.ok(file, mt)
            return Response.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + cvTemplateFile + "\"")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * Obtiene el nombre del archivo a guardar de la cabecera HTTP
     *
     * @param header
     * @return
     */
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

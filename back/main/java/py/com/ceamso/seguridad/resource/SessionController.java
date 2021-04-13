package py.com.ceamso.seguridad.resource;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.xerces.dom.ElementNSImpl;
import org.w3c.dom.NodeList;
import py.com.ceamso.administracion.service.UsuarioService;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.config.model.Configuration;
import py.com.ceamso.config.service.ConfiguracionService;
import py.com.ceamso.reportes.service.AnexoService;
import py.com.ceamso.reportes.service.LegajosService;
import py.com.ceamso.utils.Mensajes;
import py.com.ceamso.utils.Respuesta;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.seguridad.service.RolPermisoService;
import py.com.ceamso.seguridad.service.SessionService;
import py.com.ceamso.shiro.Credenciales;
import py.com.ceamso.shiro.LoginResponse;
import py.com.ceamso.utils.AppException;
import static py.com.ceamso.utils.Constantes.CONFIG_WS_AUTENTICACION_FORMULARIO;
import static py.com.ceamso.utils.Constantes.CONFIG_WS_AUTENTICACION_ROL;
import static py.com.ceamso.utils.Constantes.CONFIG_WS_AUTENTICACION_URL;
import py.com.ceamso.utils.Utils;
import py.com.ceamso.ws.csj.autenticacion.Autenticacion;
import py.com.ceamso.ws.csj.autenticacion.AutenticacionSoap;
import py.com.ceamso.ws.csj.autenticacion.ObtenerAtributosMenuFormularioResponse;

@Path("/session")
public class SessionController {

    @EJB
    UsuarioService usuarioService;

    @EJB
    RolPermisoService rolPermisoService;
    
    @Inject
    LegajosService legajoService;
    
    @EJB
    AnexoService anexoService;
    
    @Inject
    private SessionService session;
    
    @Inject
    @Configuracion("autenticarWS")
    private String autenticarWS;
    
    //@Inject
    AutenticacionSoap wsAutenticacion;
    
    @Inject
    ConfiguracionService configuracionService;
    
    @GET
    @Path("/unauthorized")
    public Response unauthorized() {
        return Response.status(403).build();
    }

    @POST    
    @Produces(MediaType.APPLICATION_JSON)
    public Response session(Credenciales credenciales) throws AppException, Exception {

        Subject currentUser = SecurityUtils.getSubject();

        if (!validarParametros(credenciales)) {
            return badRequest(Mensajes.PARAMETROS_INVALIDO);
        }

        if (!currentUser.isAuthenticated()) {
            return autenticar(credenciales);
        } else {
            Usuario usuario = Utils.obtenerUsuarioAutenticado();
            List<String> permisos = new ArrayList<String>();
            if (usuario.getRol() != null) {
                permisos = rolPermisoService.getPermisos(usuario.getRol().getId());
            }
            LoginResponse resp = new LoginResponse(usuario.getId(), usuario.getNombre(), permisos);
            return ok(resp);
        }
    }

    @POST
    @Path("/cerrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cerrarSesion() {

        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
        	Usuario user = session.getCurrentUser();
        	Long idProceso = anexoService.getUltimoProceso(user.getId());
        	if (idProceso != null) {
        		anexoService.actualizarEstadoReporte("F", idProceso);
        	}
            currentUser.getSession().removeAttribute("usuario");
            currentUser.logout();
        }
        Respuesta resp = new Respuesta(true, "");
        return ok(resp);
    }
    
    private Response autenticar(Credenciales credenciales) throws AppException, Exception {

        try {

            System.err.println("Session Controller autenticar");
            String username = credenciales.getUsername();
            String password = credenciales.getPassword();

            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);
            token.setRememberMe(true);

            Usuario usuario = usuarioService.findByName(username);
            SecurityUtils.getSubject().getSession().setAttribute("usuario", usuario);
            
            if (usuario != null) {
                List<String> permisos = new ArrayList<String>();
                if (usuario.getCodigoExterno() != null) {
                    permisos = getPermisosWS(usuario.getCodigoExterno(), usuario.getCodigoRol());
                } else {
                    if (usuario.getRol() != null) {
                        permisos = rolPermisoService.getPermisos(usuario.getRol().getId());
                    }
                }
                Respuesta res = new LoginResponse(usuario.getId(), usuario.getNombre(), permisos);
                return ok(res);
            }
            return unauthorized(new Respuesta(false, Mensajes.USUARIO_NO_ENCONTRADO));

        } catch (IncorrectCredentialsException e) {
            System.err.println("IncorrectCredentialsException : " + e.getMessage());
            Respuesta res = new Respuesta(false, Mensajes.USUARIO_NO_VALIDO);
            return unauthorized(res);
        }
    }

    private boolean validarParametros(Credenciales credenciales) {

        if (credenciales == null
                || credenciales.getUsername() == null
                || credenciales.getPassword() == null
                || credenciales.getUsername().isEmpty()
                || credenciales.getPassword().isEmpty()) {
            return false;
        }
        return true;
    }
    
    protected Response ok(Object resp) {
        return Response.ok().entity(resp).build();
    }

    protected Response unauthorized(Object resp) {
        return Response.status(401).entity(resp).build();
    }
    
    protected Response badRequest(String mensaje) {
        Respuesta resp = new Respuesta(false, mensaje);
        return Response.status(400).entity(resp).build();
    }
    
    
    private List<String> getPermisosWS(int codigoUsuario, int codigoRol) throws Exception {
            
        wsAutenticacion = getAutenticacionSoap();
        List<String> permisos = new ArrayList<String>();
        Configuration config = configuracionService.obtener(CONFIG_WS_AUTENTICACION_FORMULARIO);
        Integer codigoFormulario = new Integer(config.getValue());
        config = configuracionService.obtener(CONFIG_WS_AUTENTICACION_ROL);
        Integer rol = new Integer(config.getValue());
        if (wsAutenticacion != null) {
            ObtenerAtributosMenuFormularioResponse.ObtenerAtributosMenuFormularioResult res;
            res = wsAutenticacion.obtenerAtributosMenuFormulario(codigoUsuario, 
                codigoFormulario, codigoRol);
            ElementNSImpl any = (ElementNSImpl) res.getAny();
            if (any != null && any.getFirstChild() != null) {
                NodeList n = any.getFirstChild().getChildNodes();
                for (int i = 0; i < n.getLength(); i++) {
                    String permiso = n.item(i).getChildNodes().item(1).getTextContent();
                    String acceso = n.item(i).getChildNodes().item(5).getTextContent();
                    System.err.println(permiso + " - " + acceso);
                    if (acceso.equals("true")) {
                            permisos.add(permiso);
                    }        
                }
            }
        }
        return permisos;
    }
    
    private AutenticacionSoap getAutenticacionSoap() throws Exception {
        Configuration config = configuracionService.obtener(CONFIG_WS_AUTENTICACION_URL);
        URL url = new URL(config.getValue());
        Autenticacion service = new Autenticacion(url);
        AutenticacionSoap port = service.getAutenticacionSoap();
        return port;
    }
}

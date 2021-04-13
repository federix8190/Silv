package py.com.ceamso.shiro;

import static py.com.ceamso.utils.Constantes.CONFIG_WS_AUTENTICACION_URL;
import static py.com.ceamso.utils.Constantes.CONFIG_WS_AUTENTICACION_ROL;
import static py.com.ceamso.utils.Constantes.CONFIG_WS_AUTENTICACION_APLICACION;
import static py.com.ceamso.utils.Constantes.CONFIG_WS_AUTENTICACION_FORMULARIO;
import static py.com.ceamso.utils.Constantes.CONFIG_AUTENTICAR_WS;

import java.net.URL;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.xerces.dom.ElementNSImpl;
import org.w3c.dom.NodeList;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.administracion.service.UsuarioService;
import py.com.ceamso.config.model.Configuration;
import py.com.ceamso.config.service.ConfiguracionService;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.reportes.service.LegajosService;
import py.com.ceamso.seguridad.model.Rol;
import py.com.ceamso.seguridad.service.RolPermisoService;
import py.com.ceamso.utils.Constantes;
import py.com.ceamso.utils.Utils;
import py.com.ceamso.ws.csj.autenticacion.Autenticacion;
import py.com.ceamso.ws.csj.autenticacion.AutenticacionSoap;
import py.com.ceamso.ws.csj.autenticacion.CControlAutenticacion;

/**
 *
 * @author Konecta
 */
public class KCredentialsMatcher extends SimpleCredentialsMatcher {

    @Inject
    UsuarioService usuarioService;
    
    RolPermisoService rolPermisoService;
    
    @Inject
    LegajosService legajoService;
    
    @Inject
    ConfiguracionService configuracionService;
    
    @javax.ws.rs.core.Context
    protected HttpServletRequest httpRequest;
        
    @Inject
    AutenticacionSoap wsAutenticacion;
        
    /**
     * Verifica las credenciales del usuario(username y password)
     *
     * @param tok tok.getPrincipal().toString() contiene el nombreusuario,
     * tok.getCredentials() el pass que ingreso
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken tok, AuthenticationInfo info) {

        String principal = tok.getPrincipal().toString();
        String encryptedToken = new Md5Hash(new String((char[]) tok.getCredentials()), principal).toString();

        try {
            
            Context ctx = new InitialContext();
            configuracionService = (ConfiguracionService) ctx.lookup(Constantes.EJB_JNDI_CONFIG_SERVICE);
            Configuration config = configuracionService.obtener(CONFIG_AUTENTICAR_WS);
            String autenticarWs = "N";
            if (config != null) {
                autenticarWs = config.getValue();
            }
            
            Usuario user = new Usuario();
            user.setUsername(principal);
            user.setPassword(encryptedToken);
                
            System.err.println("Autenticar por WS : " + autenticarWs + " - " + principal + " - " + Utils.isNumeric(principal));
            if (autenticarWs.equals("S")) {
                if (Utils.isNumeric(principal)) {
                    Long cedulaIdentidad = new Long(principal);
                    /*legajoService = (LegajosService) ctx.lookup(Constantes.EJB_JNDI_LEGAJO_SERVICE);
                    Legajo datosLegajo = legajoService.obtener(cedulaIdentidad);
                    System.err.println("Datos legajo : " + cedulaIdentidad + " - " + datosLegajo);
                    if (datosLegajo != null) {
                        String password = new String((char[]) tok.getCredentials());
                        return autenticarWS(principal, password);
                    } else {
                        return autenticarUsuario(user);
                    }*/

                    String password = new String((char[]) tok.getCredentials());
                    System.err.println("Autenticar por WS : " + principal + " - " + password);
                    return autenticarWS(principal, password);

                } else {
                    return autenticarUsuario(user);
                }
            } else {
                return autenticarUsuario(user);
            }
            //return autenticarUsuario(user);

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }
    
    private boolean autenticarUsuario(Usuario user) {
        try {
            Context ctx = new InitialContext();
            usuarioService = (UsuarioService) ctx.lookup(Constantes.EJB_JNDI_USUARIO_SERVICE);
            Usuario usuario = usuarioService.findByNombrePassword(user);
            if (usuario != null) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private boolean autenticarWS(String cedula, String password) throws Exception {
        
        wsAutenticacion = getAutenticacionSoap();
        Configuration config = configuracionService.obtener(CONFIG_WS_AUTENTICACION_APLICACION);        
        Integer aplicacion = new Integer(config.getValue());
        config = configuracionService.obtener(CONFIG_WS_AUTENTICACION_ROL);
        Integer rol = new Integer(config.getValue());        
        
        if (wsAutenticacion != null) {
            
            CControlAutenticacion res = wsAutenticacion.autenticar(cedula, password, aplicacion, rol);
            System.err.println("Resultado login WS : " + res.isResultadoLogin());
            if (res.isResultadoLogin()) {
                
                Context ctx = new InitialContext();
                usuarioService = (UsuarioService) ctx.lookup(Constantes.EJB_JNDI_USUARIO_SERVICE);
                Usuario usuario = usuarioService.findByCedula(cedula);
                
                // Controlar codigo de formulario
                                
                ElementNSImpl any = (ElementNSImpl) res.getFormularios().getAny();
                if (any.getFirstChild() != null 
                        && any.getFirstChild().getFirstChild() != null) {
                    System.err.println("Controlar codigo de formulario");
                    NodeList n1 = any.getFirstChild().getFirstChild().getChildNodes();
                    if (any != null && any.getFirstChild() != null 
                            && any.getFirstChild().getChildNodes() != null) {
                        
                        Integer codigoFormulario = null;
                        config = configuracionService.obtener(CONFIG_WS_AUTENTICACION_FORMULARIO);
                        if (config != null && config.getValue() != null) {
                            codigoFormulario = new Integer(config.getValue());
                        }
                        
                        NodeList n = any.getFirstChild().getChildNodes();
                        for (int i = 0; i < n.getLength(); i++) {
                            if (n.item(i).getChildNodes().item(1).getTextContent().equals("Formulario_Principal")) {
                                String elemento = n.item(i).getChildNodes().item(0).getTextContent();
                                System.err.println(elemento);
                                if (elemento != null) {
                                    Integer nuevoCodigoFormulario = new Integer(elemento);                            
                                    if (codigoFormulario == null) {
                                        Configuration c = new Configuration();
                                        c.setName(CONFIG_WS_AUTENTICACION_FORMULARIO);
                                        c.setValue(nuevoCodigoFormulario + "");
                                        configuracionService.insertar(c);
                                    } else if (!codigoFormulario.equals(nuevoCodigoFormulario)) {
                                        codigoFormulario = nuevoCodigoFormulario;
                                        config.setValue(nuevoCodigoFormulario + "");
                                        configuracionService.modificar(config, httpRequest);
                                    }
                                }
                            }
                        }
                    }
                }

                // Registrar el usuario si aun no existe en la BBDD
                int codigoRol = res.getRol().getCodigoRol();
                System.err.println("Codigo Rol : " + codigoRol);
                if (usuario == null) {
                    usuario = new Usuario();
                    usuario.setNombre(res.getNombre());
                    usuario.setApellido(res.getApellido());
                    usuario.setCedula(cedula);
                    usuario.setUsername(cedula);
                    usuario.setEmail(res.getEmail());
                    usuario.setCodigoExterno(res.getCodigoUsuario());
                    usuario.setInterno(Boolean.TRUE);
                    usuario.setRecibirNotificacion(Constantes.RECIBIR_NOTIFICACIONES_DEFAULT);
                    usuario.setCodigoRol(codigoRol);
                    rolPermisoService = (RolPermisoService) ctx.lookup(Constantes.EJB_JNDI_ROLES_SERVICE);
                    Rol rolUsuario = rolPermisoService.getRolByCodigo(res.getRol().getDescripcionRol());
                    usuario.setRol(rolUsuario);
                    usuarioService.insertarAutenticadoWS(usuario);
                    return true;
                } else {
                    usuarioService.actualizarRolUsuario(usuario, codigoRol);
                    if (usuario.getActivo()) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    private AutenticacionSoap getAutenticacionSoap() throws Exception {
        Configuration config = configuracionService.obtener(CONFIG_WS_AUTENTICACION_URL);
        URL url = new URL(config.getValue());
        Autenticacion service = new Autenticacion(url);
        AutenticacionSoap port = service.getAutenticacionSoap();
        return port;
    }
}

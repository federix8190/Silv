package py.com.ceamso.shiro;

import static py.com.ceamso.utils.Constantes.CONFIG_AUTENTICAR_WS;
import static py.com.ceamso.utils.Constantes.CONFIG_WS_AUTENTICACION_FORMULARIO;
import static py.com.ceamso.utils.Constantes.CONFIG_WS_AUTENTICACION_ROL;
import static py.com.ceamso.utils.Constantes.CONFIG_WS_AUTENTICACION_URL;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.enterprise.context.Dependent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.namespace.QName;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.xerces.dom.ElementNSImpl;
import org.w3c.dom.NodeList;
import py.com.ceamso.administracion.service.UsuarioService;
import py.com.ceamso.config.model.Configuration;
import py.com.ceamso.config.service.ConfiguracionService;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.reportes.service.LegajosService;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;
import py.com.ceamso.utils.Utils;
import py.com.ceamso.ws.csj.autenticacion.Autenticacion;
import py.com.ceamso.ws.csj.autenticacion.AutenticacionSoap;
import py.com.ceamso.ws.csj.autenticacion.ObtenerAtributosMenuFormularioResponse.ObtenerAtributosMenuFormularioResult;

/**
 *
 * @author Konecta
 */
@Dependent
public class KRealm extends AuthorizingRealm {
    
    UsuarioService usuarioService;
    
    LegajosService legajoService;
    
    AutenticacionSoap wsAutenticacion;
    
    ConfiguracionService configuracionService;

    /**
     * Obtiene los datos de autenticacion del usuario(username y password)
     * 
     * @param token
     * @return
     * @throws AuthenticationException 
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());
        return new SimpleAuthenticationInfo(username, password.toCharArray(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection prins) {

        SimpleAuthorizationInfo info = null;
        
        try {

            String username = prins.getPrimaryPrincipal().toString();
            Context ctx = new InitialContext();
            usuarioService = (UsuarioService) ctx.lookup(Constantes.EJB_JNDI_USUARIO_SERVICE);            
            Usuario usuario = usuarioService.findByName(username);
            
            if (usuario != null) {
                
                configuracionService = (ConfiguracionService) ctx.lookup(Constantes.EJB_JNDI_CONFIG_SERVICE);
                Configuration config = configuracionService.obtener(CONFIG_AUTENTICAR_WS);
                String autenticarWs = "N";
                if (config != null) {
                    autenticarWs = config.getValue();
                }
            
                Set<String> permisos = null;
                if (autenticarWs.equals("S")) {
                    if (Utils.isNumeric(username)) {
                        Long cedulaIdentidad = new Long(username);
                        /*legajoService = (LegajosService) ctx.lookup(Constantes.EJB_JNDI_LEGAJO_SERVICE);
                        Legajo datosLegajo = legajoService.obtener(cedulaIdentidad);
                        if (datosLegajo != null) {
                            permisos = getPermisosWS(usuario.getCodigoExterno());
                        } else {
                            permisos = getPermisos(usuario.getRol().getId());
                        }*/
                        permisos = getPermisosWS(usuario.getCodigoExterno(), usuario.getCodigoRol());
                    } else {
                        permisos = getPermisos(usuario.getRol().getId());
                    }
                } else {
                    permisos = getPermisos(usuario.getRol().getId());
                }
                //permisos = getPermisos(usuario.getRol().getId());
                info = new SimpleAuthorizationInfo(permisos);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return info;
    }
    
    private Set<String> getPermisos(Long rolId) throws AppException {
        
        Set<String> permisos = new TreeSet<String>();
        if (rolId == null) {
            return permisos;
        }
        List<String> listaPermisos = usuarioService.getPermisos(rolId);
        if (listaPermisos != null) {
            for (String permiso : listaPermisos) {
                permisos.add(permiso);
            }
        }
        return permisos;
    }
    
    private Set<String> getPermisosWS(int codigoUsuario, int codigoRol) throws Exception {
                
        Set<String> permisos = new TreeSet<String>();
        //return permisos;
        wsAutenticacion = getAutenticacionSoap();
        Configuration config = configuracionService.obtener(CONFIG_WS_AUTENTICACION_FORMULARIO);
        Integer codigoFormulario = new Integer(config.getValue());
        config = configuracionService.obtener(CONFIG_WS_AUTENTICACION_ROL);
        Integer rol = new Integer(config.getValue());
        if (wsAutenticacion != null) {
            ObtenerAtributosMenuFormularioResult res;
            res = wsAutenticacion.obtenerAtributosMenuFormulario(codigoUsuario, 
                        codigoFormulario, codigoRol);
            ElementNSImpl any = (ElementNSImpl) res.getAny();
            if (any != null && any.getFirstChild() != null) {
                NodeList n = any.getFirstChild().getChildNodes();
                for (int i = 0; i < n.getLength(); i++) {
                    String permiso = n.item(i).getChildNodes().item(1).getTextContent();
                    System.err.println(permiso);
                    permisos.add(permiso);
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

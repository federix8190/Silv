package py.com.ceamso.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.web.util.WebUtils;

/**
 * Allows access if current user has at least one role of the specified list.
 * <br/>
 * Basically, it's the same as {@link RolesAuthorizationFilter} but using
 * {@literal OR} instead of {@literal AND} on the specified roles.
 *
 * @see RolesAuthorizationFilter
 * @author Andy Belsky
 */
public class AnyOfRolesAuthorizationFilter extends RolesAuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response,
            Object mappedValue) throws IOException {

        final Subject subject = getSubject(request, response);
        final String[] rolesArray = (String[]) mappedValue;
        List<String> roles = Arrays.asList(rolesArray);
        
        if (!(request instanceof HttpServletRequest)) {
            throw new IOException("Can only process HttpServletRequest");
        }
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        String metodo = httpRequest.getMethod();
        String query = httpRequest.getQueryString();
        
        System.err.println("URL Query : " + query);
        
        // La peticion no requiere de roles
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        
        if (metodo.equals("POST") 
                && (path.contains("/usuarios/registro-online")
                    || path.contains("/resetear-password"))) {
            return true;
        }
        
        if (path.contains("/roles")) {
            if (subject.hasRole("CREAR_USUARIOS")) {
                return true;
            }
            return false;
        }
        
        if (metodo.equals("POST") && path.contains("/usuarios/subir-cv")) {
            return true;
        }
        
        if (metodo.equals("POST") && path.contains("/anexos/asignar-legajos")) {
            /*if (subject.hasRole("VER_ANEXOS")) {
                return true;
            }
            return false;*/
            return true;
        }
        
        if (metodo.equals("POST") && path.contains("/session")) {
            return true;
        }
        
        if (metodo.equals("GET") && path.contains("/anexos/test-consulta")) {
            return true;
        }
        
        if (metodo.equals("GET") && path.contains("/legajos/actualizar-datos/")) {
        	/*if (subject.isAuthenticated()) {
        		return true;
        	} else {
        		return false;
        	}*/
        	return true;
        }
        
        if (metodo.equals("GET") && 
        		(path.contains("/anexos/comparativo") 
        				|| (path.contains("/anexos/comparativo/descargar"))
        						|| (path.contains("/anexos/comparativo/estado"))
        						|| (path.contains("/anexos/ultimo-periodo")))) {
            return true;
        }
        
        if (metodo.equals("GET") && 
                (path.contains("administracion/cpt/niveles")
                || path.contains("administracion/cts/tramos")
                || path.contains("administracion/mecip")
                || path.contains("legajos/vinculaciones")
                || path.contains("legajos/ambitos")
                || path.contains("legajos/orientacionFunc"))) {
            return true;
        }
        
        /*if (metodo.equals("GET") && 
                (path.contains("legajos/vinculaciones")
                || path.contains("legajos/ambitos")
                || path.contains("legajos/orientacionFunc"))) {
            
        	System.err.println("Verificar permiso para listar anexo");
        	for (String rol : roles) {
        		System.err.println("Permiso : " + rol);
        	}
        	if (roles.contains("LISTAR_ANEXOS")) {
        		return true;
        	}
        	return false;
        }*/
        
        if (metodo.equals("GET")
                && path.contains("/anexos")
                && path.contains("/asignaciones")) {
            return true;
        }
                
        if (!subject.isAuthenticated()) {
            WebUtils.redirectToSavedRequest(request, response, "/api/session/unauthorized");
            return false;
        }
        
        if (path.contains("/usuarios/usuario-logueado")
                || path.contains("/usuarios/cambiar-password")
                || path.contains("/cte-api/api/convocatorias-publica")) {
            return true;
        }
                
        if (path.contains("/api/cargos-disponibles")
        		|| path.contains("/api/administracion/cpt")) {
        	if (subject.hasRole("LISTAR_CARGOS_DISPONIBLES")) {
                return true;
            } else {
            	return false;
            }
        }
        
        if (path.contains("/administracion/cpt/") && path.contains("/tramos")) {
            for (String roleName : rolesArray) {
                if (subject.hasRole(roleName)) {
                    return true;
                }
            }
            return false;
        }
        
        if (path.contains("/administracion/configuracion-cts")) {
            if (subject.hasRole("CONFIGURAR_CTS")) {
                return true;
            } else {
                return false;
            }
        }
        
        if (path.contains("/administracion/cts")) {
            if (subject.hasRole("CALCULAR_TRAMOS")) {
                return true;
            } else {
                return false;
            }
        }
        
        if (metodo.equals("PUT")) {
        	if (path.contains("/cargos-no-asignados/") && path.contains("cambiar-estado")) {
        		if (subject.hasRole("ACTIVAR_CARGO")) {
                    return true;
                } else {
                    return false;
                }
        	}
        }
        
        for (String roleName : rolesArray) {
            String accion = "";
            if (metodo.equals("GET")) {
                accion = "LISTAR_";
            } else if (metodo.equals("POST")) {
                accion = "CREAR_";
            } else if (metodo.equals("PUT")) {
                accion = "EDITAR_";
            } else if (metodo.equals("DELETE")) {
                accion = "ELIMINAR_";
            }
            if (subject.hasRole(accion + roleName)) {
                return true;
            }
        }

        return false;

    }
}

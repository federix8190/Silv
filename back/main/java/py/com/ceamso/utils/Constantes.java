package py.com.ceamso.utils;

import py.com.ceamso.administracion.service.UsuarioService;
import py.com.ceamso.config.service.ConfiguracionService;
import py.com.ceamso.reportes.service.LegajosService;
import py.com.ceamso.seguridad.service.RolPermisoService;

public class Constantes {

    // JNDI del EJB UsuarioService que se inyecta en el proceso de autenticacion
    public static final String EJB_JNDI_BASE = "java:global/cte-api/";
    
    public static final String EJB_JNDI_USUARIO_SERVICE = EJB_JNDI_BASE
            + UsuarioService.class.getSimpleName() + "!" 
            + UsuarioService.class.getCanonicalName();
    
    public static final String EJB_JNDI_LEGAJO_SERVICE = EJB_JNDI_BASE
            + LegajosService.class.getSimpleName() + "!" 
            + LegajosService.class.getCanonicalName();
    
    public static final String EJB_JNDI_CONFIG_SERVICE = EJB_JNDI_BASE
            + ConfiguracionService.class.getSimpleName() + "!" 
            + ConfiguracionService.class.getCanonicalName();
    
    public static final String EJB_JNDI_ROLES_SERVICE = EJB_JNDI_BASE
            + RolPermisoService.class.getSimpleName() + "!" 
            + RolPermisoService.class.getCanonicalName();

    public static final String PATH_DOCUMENTOS = System.getProperty("jboss.server.data.dir");
    
    public static final String SEXO_MASCULINO = "MASCULINO";
    public static final String SEXO_FEMENINO = "FEMENINO";

    public static final String ROL_PUBLICO = "PUBLICO";
    
    public static final String ESTADO_CARGO_DISPONIBLE = "DISPONIBLE";
    
    public static final Boolean RECIBIR_NOTIFICACIONES_DEFAULT = Boolean.TRUE;
    
    public static final String CONFIG_AUTENTICAR_WS = "autenticarWS";
    public static final String CONFIG_WS_AUTENTICACION_URL = "WebService.Autenticacion.url";
    public static final String CONFIG_WS_AUTENTICACION_ROL = "WebService.Autenticacion.rol";
    public static final String CONFIG_WS_AUTENTICACION_APLICACION = "WebService.Autenticacion.aplicacion";
    public static final String CONFIG_WS_AUTENTICACION_FORMULARIO = "WebService.Autenticacion.formulario";
    
    public static final String ESQUEMA_CTE_MH = "cte";
    public static final String ESQUEMA_CTE_PJ = "dbo";
    
    public static final String MENSAJE_ELIMINAR_REGISTRO = 
    		"No se puede el eliminar el registro debido a que se encuentra referenciado en otra entidad";

}

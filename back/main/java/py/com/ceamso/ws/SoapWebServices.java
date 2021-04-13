package py.com.ceamso.ws;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URL;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.utils.Constantes;
import py.com.ceamso.ws.csj.autenticacion.Autenticacion;
import py.com.ceamso.ws.csj.autenticacion.AutenticacionSoap;

public class SoapWebServices {

    @Inject
    @Configuracion(Constantes.CONFIG_WS_AUTENTICACION_URL)
    private String urlWsAutenticacion;
    

    /*
     * Qualifier utilizado para inyectar instancias en las clases del paquete wrapper.
     */
    @Qualifier
    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Wrapped {
    }
    
    @Produces
    public AutenticacionSoap getAutenticacionSoap() throws Exception {
        URL url = new URL(urlWsAutenticacion);
        Autenticacion service = new Autenticacion(url);
        AutenticacionSoap port = service.getAutenticacionSoap();        
        return port;
    }
    
}

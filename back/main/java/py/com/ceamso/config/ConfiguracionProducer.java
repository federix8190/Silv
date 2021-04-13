package py.com.ceamso.config;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import py.com.ceamso.config.model.Configuration;
import py.com.ceamso.config.service.ConfiguracionService;
import py.com.ceamso.utils.AppException;

public class ConfiguracionProducer {

    @Inject
    private ConfiguracionService service;

    @Produces
    @Configuracion
    public String obtenerConfiguracion(InjectionPoint ip) throws AppException {
        final String clave = ip.getAnnotated().getAnnotation(Configuracion.class).value();
        Configuration config = service.obtener(clave);
        if (config != null) {
            return config.getValue();
        }
        return null;
    }

}

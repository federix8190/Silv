package py.com.konecta;

import javax.enterprise.inject.Produces;

import org.jboss.resteasy.client.ProxyFactory;

import py.com.konecta.interfaces.RestApiInterface;

public class RestfulWebServices {

	@Produces
    public RestApiInterface getNotificacionesInterface() throws AppException {
        String url = "http://localhost:5000";
        RestApiInterface object = ProxyFactory.create(RestApiInterface.class, url);
        return object;
    }
	
}

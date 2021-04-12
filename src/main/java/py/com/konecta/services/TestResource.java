package py.com.konecta.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import py.com.konecta.interfaces.RestApiInterface;
import py.com.konecta.interfaces.dto.Usuario;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestResource {
	
	Logger log = Logger.getLogger(this.getClass().getCanonicalName());
	
	@EJB
	TestServices service;
	
	@Inject
    protected RestApiInterface restService;
	
	@GET
	public HashMap<String, String> test() {
		HashMap<String, String> res = new HashMap<String, String>();
		res.put("status", "0");
		res.put("mensaje", "Hola");
		return res;
	}
	
	@GET
	@Path("/usuarios")
	public Response getUsuarios() {
		
		try {
			
			HashMap<String, Object> r = new HashMap<String, Object>();
			String token = restService.getToken();
			r.put("token", token);
			
			Usuario datos = new Usuario();
			datos.setNombre("Juan");
			datos.setApellido("Perez");
			datos.setCorreo("jperez@test.com");
			datos.setId_ciudad(11L);
			Response res = restService.createUser(datos);
			r.put("status_1", res.getStatus());
			res.close();
			
			return Response.ok(r).build();
	    	
		} catch (Exception e) {
			
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}

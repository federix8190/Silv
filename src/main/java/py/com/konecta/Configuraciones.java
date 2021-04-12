package py.com.konecta;

import java.util.HashMap;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/config")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Configuraciones {
	
	@Inject
	Codigos cod;
	
	@GET
	public HashMap<String, String> getConfig() {
		
		HashMap<String, String> res = new HashMap<String, String>();
		res.put("APP_ID", cod.APP_ID);
		return res;
	}
	
	@POST
	public  Response cambiarConfig(HashMap<String, String> datos) {
		
		if (datos != null) {
			cod.APP_ID = datos.get("key");
		}
		return Response.ok().build();
	}

}

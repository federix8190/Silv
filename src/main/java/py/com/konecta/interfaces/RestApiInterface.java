package py.com.konecta.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.com.konecta.interfaces.dto.Usuario;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public interface RestApiInterface {
	
	@POST
	@Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    public String getToken();
	
	@POST
	@Path("/usuarios")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(Usuario datos);

}

package py.com.ceamso.base;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author mbaez
 * @param <G>
 * @param <S>
 */
public abstract class WritableResource<G extends WritableEntity, S extends WritableService<G>> 
        extends ReadableResource {
    
     /**
     * Retorna la referencia al service que es utilizado para obtener los datos.
     *
     * @return
     */
    @Override
    public abstract S getService();
    
    /**
     * Se encarga de insertar un nuevo registro.
     *
     * @param dto el DTO que se desea insertar en la base de datos.
     * @return el dto del recurso en formato json. Este DTO ya cuenta con el id
     * asignado por la secuencia.
     */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public G insertar(G dto) {
        try {
            return getService().insertar(dto, httpRequest);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    /**
     * Se encarga de actualizar un recurso ya existente.
     *
     * @param id
     * @param dto el DTO que se desea actualizar en la base de datos.
     * @return
     *
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public G modificar(@PathParam("id") Long id, G dto) {
        try {
            getService().modificar(id, dto, httpRequest);
            return dto;
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PUT
    @Path("/{id}/actualizar-estado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarEstado(@PathParam("id") Long id, boolean activo) {
        try {
            getService().actualizarEstado(id, activo, httpRequest);
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Se encarga de eliminar un registro de la base de datos.
     *
     * @param id el primary key del registro a eliminar
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void eliminar(@PathParam("id") Long id) {
        try {
            getService().eliminar(id);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage());
        }
    }
    
    protected Response unauthorized(Object resp) {
        return Response.status(401).entity(resp).build();
    }
}

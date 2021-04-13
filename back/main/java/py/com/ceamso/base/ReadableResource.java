package py.com.ceamso.base;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 * @author mbaez
 * @param <G>
 * @param <S>
 */
public abstract class ReadableResource<G, S extends ReadableService<G>> extends BaseClass {

    @Context
    protected HttpServletRequest httpRequest;

    /**
     * Retorna la referencia al service que es utilizado para obtener los datos.
     *
     * @return
     */
    public abstract S getService();

    /**
     * Mapper utilizado para parsear el json de filtros.
     */
    protected ObjectMapper mapper = new ObjectMapper();

    /**
     * Obtiene la lista páginada de los recursos.
     *
     * @param pagina número de página que se esta consultando, por defecto es
     * igual a 1;
     * @param cantidad el total de registros. Por defecto es igual a 20. Si se
     * le pasa -1 retorna todos los registros
     * @param orderBy columna por la cual se realizará el ordenado. Por defecto
     * es igual a id.
     * @param orderDir dirección de ordenado. Por defecto es igual a ASC.
     * @param json el json que corresponde a los criterios de filtrado. Por
     * defecto es igual a una cadena vacía.
     * @return La lista paginada de los recursos.
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaResponse<G> listar(
            @QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad,
            @QueryParam("sortBy") @DefaultValue("id") String orderBy,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir,
            @QueryParam("filters") String json) {
        
        //se calcula el inicio de la grilla
        pagina = pagina > 0 ? pagina : 1;
        Integer inicio = (pagina - 1) * cantidad;
        
        //se parsea el json para consutrir el filtro
        HashMap<String, Object> filtros = null;
        if (json != null && json.trim().length() > 2) {
            try {
                filtros = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
                });
                filtros = setearFiltros(filtros, httpRequest.getPathInfo());
            } catch (Exception e) {
                throw new WebApplicationException(e.getMessage(),
                        Response.Status.INTERNAL_SERVER_ERROR);
            }
        }
        return getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
    }

    /**
     * Este método se encarga de obtener un recurso por su id.
     *
     * @param id Idenfiticador del recurso.
     * @return el dto del recurso en formato json.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public G obtener(@PathParam("id") Long id) {
        G dto = null;
        try {
            dto = getService().obtener(id);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
        if (dto == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return dto;
    }
}

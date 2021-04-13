package py.com.ceamso.base;

import java.util.HashMap;
import py.com.ceamso.utils.AppException;

/**
 * Capa intermedia de interacción con la base de datos. Establece los métodos
 * CRUD para los recursos.
 *
 * @author mbaez
 * @param <G> Recurso al cual se aplicarán los metodos CRUD
 *
 */
public interface ReadableService<G> {

    /**
     * Se encarga de obtener un recurso por su id
     *
     * @param id Idenfificardor del recuro
     * @return Recurso que cumple con el criterio de filtrado. Si no existe
     * ningun registro retorna null.
     * @throws py.com.ceamso.utils.AppException
     */
    public G obtener(Long id) throws AppException;


    /**
     * Se encarga de recuperar la lista paginada y ordenado de recursos.
     *
     * @param inicio número de registro a partir del cual se obtendran los
     * datos.
     * @param cantidad cantidad de registros a obtener. Si es -1 se retorna la
     * lista completa.
     * @param orderBy nombre de la columna de ordenado
     * @param orderDir direccion de ordenado.
     * @param filtros el map de los atributos de filtrado.
     * @return La lista que cumple con los criterios de filtrado y ordenado.
     */
    public ListaResponse<G> listar(Integer inicio, Integer cantidad, String orderBy, String orderDir,
            HashMap<String, Object> filtros);

    /**
     * Se encarga de recuperar la lista paginada y ordenado de recursos.
     *
     * @param filtros el map de los atributos de filtrado.
     * @return La lista que cumple con los criterios de filtrado y ordenado.
     */
    public ListaResponse<G> listar(HashMap<String, Object> filtros);

    /**
     * Se encarga de recuperar la lista paginada y ordenado de recursos.
     *
     * @return La lista que cumple con los criterios de filtrado y ordenado.
     */
    public ListaResponse<G> listar();

}

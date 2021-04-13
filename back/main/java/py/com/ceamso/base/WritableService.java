package py.com.ceamso.base;

import javax.servlet.http.HttpServletRequest;
import py.com.ceamso.utils.AppException;

/**
 * Capa intermedia de interacción con la base de datos. Establece los métodos
 * CRUD para los recursos.
 *
 * @author mbaez
 * @param <G> Recurso al cual se aplicarán los metodos CRUD
 *
 */
public interface WritableService<G extends WritableEntity>  extends ReadableService<G>{

   
    /**
     * Se encarga de insertar un nuevo recurso
     *
     * @param entity Recurso a agregar.
     * @param httpRequest
     * @return El recurso actual. Si no existe ningun registro retorna null.
     * @throws py.com.ceamso.utils.AppException
     */
    public G insertar(G entity, HttpServletRequest httpRequest) throws AppException;

    /**
     * Se encarga de eliminar un recurso.
     *
     * @param id identificador del recurso a eliminar
     * @throws py.com.ceamso.utils.AppException
     */
    public void eliminar(Long id) throws AppException;

    /**
     * Se encarga de modificar un recurso.
     *
     * @param id identificador del recurso
     * @param entity El recurso a modificar.
     * @param httpRequest
     * @throws py.com.ceamso.utils.AppException
     */
    public void modificar(Long id, G entity, HttpServletRequest httpRequest) throws AppException;
    
    public void actualizarEstado(Long id, boolean activo, HttpServletRequest httpRequest) throws AppException;

}

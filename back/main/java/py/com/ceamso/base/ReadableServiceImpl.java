package py.com.ceamso.base;

import java.util.HashMap;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author mbaez
 * @param <G>
 * @param <D>
 */
public abstract class ReadableServiceImpl<G, D extends ReadableDAO<G>> implements ReadableService<G> {


    /**
     * Se encarga de retornar la instancia del dato a ser utilizado para las
     * operaciones.
     *
     * @return
     */
    public abstract D getDao();


    /**
     * @{@inheritDoc}
     */
    @Override
    public G obtener(Long id) throws AppException {
        try {
            G datos = getDao().get(id);
            return datos;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    @Override
    public ListaResponse<G> listar(Integer inicio, Integer cantidad, String orderBy, 
            String orderDir, HashMap<String, Object> filtros) {
        
        ListaResponse<G> res = getDao().listar(inicio, cantidad, orderBy, orderDir, filtros);
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListaResponse<G> listar(HashMap<String, Object> filtros) {
        return this.listar(null, null, "id", "asc", filtros);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListaResponse<G> listar() {
        return this.listar(null);
    }
}

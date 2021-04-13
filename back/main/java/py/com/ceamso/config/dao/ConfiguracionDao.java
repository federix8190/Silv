package py.com.ceamso.config.dao;

import java.util.HashMap;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.config.model.Configuration;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author konecta
 */
public class ConfiguracionDao extends WritableDAO<Configuration> {

    /**
     *
     * @{inheritDoc}
     */
    @Override
    public Class getEntity() {
        return Configuration.class;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ListaResponse<Configuration> listar(int inicio, int cantidad, String orderBy,
            String odrerDir, HashMap<String, Object> filtros) {
        filtros = filtros == null ? new HashMap<String, Object>() : filtros;
        filtros.put("isSecret", 0L);
        return super.listar(inicio, cantidad, orderBy, odrerDir, filtros);
    }
    
    /**
     *
     * @param id
     * @return
     * @throws AppException
     */    
    public Configuration get(String id) throws AppException {
        try {
            return (Configuration) em.find(getEntity(), id);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
}

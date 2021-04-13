package py.com.ceamso.administracion.service;

import java.util.HashMap;
import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.administracion.dao.ConfiguracionCtsDAO;
import py.com.ceamso.administracion.model.ConfiguracionCts;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.base.ListaResponse;

/**
 *
 * @author mbaez
 */
@Stateless
public class ConfiguracionCtsService extends WritableServiceImpl<ConfiguracionCts, ConfiguracionCtsDAO> {

    @Inject
    private ConfiguracionCtsDAO dao;

    /**
     *
     * @{@inheritDoc}
     */
    @Override
    public ConfiguracionCtsDAO getDao() {
        return dao;
    }

    public ConfiguracionCtsService() {
    }
    
    @Override
    public ListaResponse<ConfiguracionCts> listar(Integer inicio, Integer cantidad, String orderBy, 
            String orderDir, HashMap<String, Object> filtros) {
               
        if (orderBy == null || orderBy.equals("id")) {
            orderBy = "id";
        }
        ListaResponse<ConfiguracionCts> res = getDao().listar(inicio, cantidad, orderBy, orderDir, filtros);
        return res;
    }

}

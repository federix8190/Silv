package py.com.ceamso.config.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.config.dao.ConfiguracionDao;
import py.com.ceamso.config.model.Configuration;
import py.com.ceamso.utils.AppException;

@Stateless
public class ConfiguracionService extends WritableServiceImpl<Configuration, ConfiguracionDao> {

    @Inject
    private ConfiguracionDao dao;

    @Override
    public ConfiguracionDao getDao() {
        return dao;
    }

    public ConfiguracionService() {
    }

    public Configuration obtener(String id) throws AppException {
        try {
            return getDao().get(id);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
}

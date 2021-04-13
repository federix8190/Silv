package py.com.ceamso.administracion.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.administracion.dao.OrientacionFuncDAO;
import py.com.ceamso.administracion.model.OrientacionFunc;
import py.com.ceamso.base.ReadableServiceImpl;

/**
 *
 * @author konecta
 */
@Stateless
public class OrientacionFuncService extends ReadableServiceImpl<OrientacionFunc, OrientacionFuncDAO> {
    
    @Inject
    private OrientacionFuncDAO dao;
    
    @Override
    public OrientacionFuncDAO getDao() {
        return dao;
    }

    public OrientacionFuncService() {
    }
    
}
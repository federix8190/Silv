package py.com.ceamso.administracion.service;

import py.com.ceamso.base.WritableServiceImpl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.administracion.dao.CptPruebaDAO;
import py.com.ceamso.administracion.model.CptPrueba;

/**
 *
 * @author paraujo
 */
@Stateless
public class CptPruebaService extends WritableServiceImpl<CptPrueba, CptPruebaDAO> {

    @Inject
    private CptPruebaDAO dao;

    @Override
    public CptPruebaDAO getDao() {
        return dao;
    }
    
}

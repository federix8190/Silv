/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.facade;

import javax.inject.Inject;
import py.com.ceamso.administracion.dao.ConfiguracionCtsFacadeDAO;
import py.com.ceamso.administracion.model.ConfiguracionCts;
import py.com.ceamso.utils.tables.dao.GenericDao;
import py.com.ceamso.utils.tables.facade.DatatablesFacadeImpl;

/**
 *
 * @author daniel.rojas
 */
public class ConfiguracionCtsFacade extends DatatablesFacadeImpl<ConfiguracionCts> {

    @Inject
    ConfiguracionCtsFacadeDAO dao;

    @Override
    protected GenericDao<ConfiguracionCts> getDao() {
        return dao;
    }
}

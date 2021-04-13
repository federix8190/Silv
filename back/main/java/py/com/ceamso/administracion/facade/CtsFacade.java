/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.facade;

import javax.inject.Inject;
import py.com.ceamso.administracion.model.Cts;
import py.com.ceamso.administracion.dao.CtsFacadeDAO;
import py.com.ceamso.reportes.dao.LegajosFacadeDAO;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.utils.tables.dao.GenericDao;
import py.com.ceamso.utils.tables.facade.DatatablesFacadeImpl;

/**
 *
 * @author daniel
 */
public class CtsFacade extends DatatablesFacadeImpl<Cts> {

    @Inject
    CtsFacadeDAO dao;

    @Override
    protected GenericDao<Cts> getDao() {
        return dao;
    }
    
}

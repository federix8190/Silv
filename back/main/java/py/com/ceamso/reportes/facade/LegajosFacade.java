/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.facade;

import javax.inject.Inject;
import py.com.ceamso.reportes.dao.LegajosFacadeDAO;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.utils.tables.dao.GenericDao;
import py.com.ceamso.utils.tables.facade.DatatablesFacadeImpl;

/**
 *
 * @author daniel
 */
public class LegajosFacade extends DatatablesFacadeImpl<Legajo> {

    @Inject
    LegajosFacadeDAO dao;

    @Override
    protected GenericDao<Legajo> getDao() {
        return dao;
    }
}

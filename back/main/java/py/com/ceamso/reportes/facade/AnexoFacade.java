/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.facade;

import javax.inject.Inject;
import py.com.ceamso.reportes.dao.AnexoFacadeDAO;
import py.com.ceamso.reportes.model.Anexos;
import py.com.ceamso.utils.tables.dao.GenericDao;
import py.com.ceamso.utils.tables.facade.DatatablesFacadeImpl;

/**
 *
 * @author daniel
 */
public class AnexoFacade extends DatatablesFacadeImpl<Anexos> {

    @Inject
    AnexoFacadeDAO dao;

    @Override
    protected GenericDao<Anexos> getDao() {
        return dao;
    }

}


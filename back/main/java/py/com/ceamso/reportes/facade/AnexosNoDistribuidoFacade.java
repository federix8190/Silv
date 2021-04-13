/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.facade;

import javax.inject.Inject;
import py.com.ceamso.reportes.dao.AnexosNoDistribuidoFacadeDao;
import py.com.ceamso.reportes.model.AnexosNoDistribuido;
import py.com.ceamso.utils.tables.dao.GenericDao;
import py.com.ceamso.utils.tables.facade.DatatablesFacadeImpl;

/**
 *
 * @author daniel.rojas
 */
public class AnexosNoDistribuidoFacade extends DatatablesFacadeImpl<AnexosNoDistribuido> {

    @Inject
    AnexosNoDistribuidoFacadeDao dao;

    @Override
    protected GenericDao<AnexosNoDistribuido> getDao() {
        return dao;
    }
    
}

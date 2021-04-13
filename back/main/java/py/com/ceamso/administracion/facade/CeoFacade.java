/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.facade;

import javax.inject.Inject;
import py.com.ceamso.administracion.dao.CeoFacadeDAO;
import py.com.ceamso.administracion.model.Ceo;
import py.com.ceamso.utils.tables.dao.GenericDao;
import py.com.ceamso.utils.tables.facade.DatatablesFacadeImpl;

/**
 *
 * @author daniel.rojas
 */
public class CeoFacade extends DatatablesFacadeImpl<Ceo>{
    
    @Inject
    CeoFacadeDAO dao;

    @Override
    protected GenericDao<Ceo> getDao() {
        return dao;
    }
    
}

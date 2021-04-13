/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.facade;

import javax.inject.Inject;
import py.com.ceamso.administracion.dao.CuoFacadeDAO;
import py.com.ceamso.administracion.model.Cuo;
import py.com.ceamso.utils.tables.dao.GenericDao;
import py.com.ceamso.utils.tables.facade.DatatablesFacadeImpl;

/**
 *
 * @author daniel.rojas
 */
public class CuoFacade extends DatatablesFacadeImpl<Cuo>{
    
    @Inject
    CuoFacadeDAO dao;

    @Override
    protected GenericDao<Cuo> getDao() {
        return dao;
    }
    
}
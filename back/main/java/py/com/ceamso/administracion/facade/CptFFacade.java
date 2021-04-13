/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.facade;

import javax.inject.Inject;
import py.com.ceamso.administracion.dao.CptFFacadeDAO;
import py.com.ceamso.administracion.model.CptF;
import py.com.ceamso.utils.tables.dao.GenericDao;
import py.com.ceamso.utils.tables.facade.DatatablesFacadeImpl;

/**
 *
 * @author daniel.rojas
 */
public class CptFFacade extends DatatablesFacadeImpl<CptF>{
    
    @Inject
    CptFFacadeDAO dao;

    @Override
    protected GenericDao<CptF> getDao() {
        return dao;
    }
    
}

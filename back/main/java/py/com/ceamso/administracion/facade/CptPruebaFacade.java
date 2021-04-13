/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.facade;

import javax.inject.Inject;
import py.com.ceamso.administracion.dao.CptPruebaFacadeDAO;
import py.com.ceamso.administracion.model.CptF;
import py.com.ceamso.administracion.model.CptPrueba;
import py.com.ceamso.utils.tables.dao.GenericDao;
import py.com.ceamso.utils.tables.facade.DatatablesFacadeImpl;

/**
 *
 * @author daniel.rojas
 */
public class CptPruebaFacade extends DatatablesFacadeImpl<CptPrueba>{
    
    @Inject
    CptPruebaFacadeDAO dao;

    @Override
    protected GenericDao<CptPrueba> getDao() {
        return dao;
    }
    
}

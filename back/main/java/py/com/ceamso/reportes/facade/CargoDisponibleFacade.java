/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.facade;

import javax.inject.Inject;
import py.com.ceamso.utils.tables.dao.GenericDao;
import py.com.ceamso.utils.tables.facade.DatatablesFacadeImpl;
import py.com.ceamso.reportes.dao.CargoDisponibleFacadeDAO;
import py.com.ceamso.reportes.model.CargoDisponibleView;
/**
 *
 * @author daniel
 */
public class CargoDisponibleFacade extends DatatablesFacadeImpl<CargoDisponibleView>{
    
    @Inject
    CargoDisponibleFacadeDAO dao;

    @Override
    protected GenericDao<CargoDisponibleView> getDao() {
        return dao;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.facade;

import javax.inject.Inject;
import py.com.ceamso.reportes.dao.CargoVacanteFacadeDAO;
import py.com.ceamso.reportes.model.CargoVacante;
import py.com.ceamso.utils.tables.dao.GenericDao;
import py.com.ceamso.utils.tables.facade.DatatablesFacadeImpl;

/**
 *
 * @author daniel
 */
public class CargoVacanteFacade extends DatatablesFacadeImpl<CargoVacante>{
    
    @Inject
    CargoVacanteFacadeDAO dao;

    @Override
    protected GenericDao<CargoVacante> getDao() {
        return dao;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.facade;

import javax.inject.Inject;
import py.com.ceamso.administracion.dao.CongruenciaFacadeDAO;
import py.com.ceamso.reportes.dto.CongruenciaDTO;
import py.com.ceamso.utils.tables.dao.GenericDao;
import py.com.ceamso.utils.tables.facade.DatatablesFacadeImpl;

/**
 *
 * @author daniel.rojas
 */
public class CongruenciaFacade extends DatatablesFacadeImpl<CongruenciaDTO>{
    
    @Inject
    CongruenciaFacadeDAO dao;

    @Override
    protected GenericDao<CongruenciaDTO> getDao() {
        return dao;
    }
    
}

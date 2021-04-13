/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.administracion.dao.ClasificacionDAO;
import py.com.ceamso.administracion.model.Clasificacion;
import py.com.ceamso.base.WritableServiceImpl;

/**
 *
 * @author pablo
 */
@Stateless
public class ClasificacionService extends WritableServiceImpl<Clasificacion,ClasificacionDAO>{
    
    @Inject
    private ClasificacionDAO dao;

    @Override
    public ClasificacionDAO getDao() {
        return dao;
    }
    
}

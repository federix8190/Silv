/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.dao;

import py.com.ceamso.administracion.model.Distrito;
import py.com.ceamso.base.ReadableDAO;

/**
 *
 * @author daniel
 */
public class DistritoDAO extends ReadableDAO<Distrito>{
    
    @Override
    public Class getEntity() {
        return Distrito.class;
    }
}

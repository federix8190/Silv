/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.dao;

import javax.persistence.Query;
import py.com.ceamso.administracion.model.ConfiguracionCts;
import py.com.ceamso.base.ReadableDAO;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author mbaez
 */
public class ConfiguracionCtsDAO extends WritableDAO<ConfiguracionCts> {

    @Override
    public Class getEntity() {
        return ConfiguracionCts.class;
    }
    
    @Override
    public ConfiguracionCts get(Long id) throws AppException {
        try {
            return (ConfiguracionCts) em.find(getEntity(), id);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    @Override
    public void buildOrder(StringBuilder sb, String orderBy, String orderDir) {
        if (orderBy != null && !orderBy.isEmpty()) {
            if (orderBy.equals("minimo") || orderBy.equals("maximo")) {
                sb.append(" ORDER BY c.cts.");
                sb.append(orderBy).append(" ").append(orderDir);
            } else {
                sb.append(" ORDER BY c.");
                sb.append(orderBy).append(" ").append(orderDir);
            }
        }
    }
    
}

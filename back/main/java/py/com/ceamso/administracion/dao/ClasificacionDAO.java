/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.dao;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import py.com.ceamso.administracion.model.Clasificacion;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author pablo
 */
public class ClasificacionDAO extends WritableDAO<Clasificacion>{

    @Override
    public Class getEntity() {
        return Clasificacion.class;
    }
    
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1;
        sb.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {
                if (key.compareTo("denominacionCpt") == 0) {
                    sb.append(" LOWER(c.cpt.denominacion")
                        //.append(key)
                        .append(") LIKE LOWER(:")
                        .append(key)
                        .append(")");
                } else {
                    sb.append(" LOWER(c.")
                        .append(key)
                        .append(") LIKE LOWER(:")
                        .append(key)
                        .append(")");
                }
            } else {
                if (key.equals("nivel") || key.equals("subNivel") || key.equals("numeroGasto") || key.equals("titularUnidad")) {
                    sb.append("c.cpt." + key)
                        .append(" = :")
                        .append(key);
                } else if (key.equals("denominacionCpt")){
                    sb.append("c.cpt.denominacion")
                        .append(" = :")
                        .append(key);
                }else {
                    sb.append(key)
                        .append(" = :")
                        .append(key);
                }
            }
            //se añade el 'AND' si hay más caracteres.
            if (token < tokens) {
                sb.append(" AND ");
            }
            token++;
        }
    }
}

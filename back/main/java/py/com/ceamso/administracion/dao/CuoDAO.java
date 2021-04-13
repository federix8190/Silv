/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.dao;

import java.util.List;
import javax.persistence.Query;
import py.com.ceamso.administracion.model.Cuo;
import py.com.ceamso.base.WritableDAO;

/**
 *
 * @author vgayoso
 */
public class CuoDAO extends WritableDAO<Cuo> {

    /**
     * 
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return Cuo.class;
    }
    
    public Cuo getCuo(Integer nivel, Integer subNivel, Integer numero) {
    	
        String sql = "SELECT c FROM Cuo c WHERE c.nivel = :nivel AND ";
        sql = sql +  "c.subNivel = :subNivel AND c.numero = :numero";
        Query q = em.createQuery(sql);
        q.setParameter("nivel", nivel);
        q.setParameter("subNivel", subNivel);
        q.setParameter("numero", numero);
        List<Cuo> res = q.getResultList();
        if (res != null && res.size() > 0) {
            return res.get(0);
        }
        return null;
        
    }
    
    public boolean existeCuo(Integer nivel, Integer subNivel, Integer numero) {
        String sql = "SELECT c FROM Cuo c WHERE c.nivel = :nivel AND "
                + "c.subNivel = :subNivel AND c.numero = :numero";
        Query q = em.createQuery(sql);
        q.setParameter("nivel", nivel);
        q.setParameter("subNivel", subNivel);
        q.setParameter("numero", numero);
        List<Cuo> res = q.getResultList();
        if (res != null && res.size() > 0) {
            return true;
        }
        return false;
        
    }
    
    public boolean existeCuo(Integer nivel, Integer subNivel, Integer numero, Long id) {
        String sql = "SELECT c FROM Cuo c WHERE c.nivel = :nivel AND "
                + "c.subNivel = :subNivel AND c.numero = :numero AND c.id != :id";
        Query q = em.createQuery(sql);
        q.setParameter("id", id);
        q.setParameter("nivel", nivel);
        q.setParameter("subNivel", subNivel);
        q.setParameter("numero", numero);
        List<Cuo> res = q.getResultList();
        if (res != null && res.size() > 0) {
            return true;
        }
        return false;
        
    }
    
    @Override
    public void buildOrder(StringBuilder sb, String orderBy, String orderDir) {
        // Al reordenar CUO por nivel, el orden de subnivel y numero debe ser el inverso a nivel
        if (orderBy != null && !orderBy.isEmpty()) {
            if (orderBy.equals("nivel")) {
                if (orderDir.equals("ASC")) {
                    sb.append(" ORDER BY c.").append(orderBy).append(" ").append(orderDir);
                    sb.append(", subNivel DESC, numero DESC");
                } else {
                    sb.append(" ORDER BY c.").append(orderBy).append(" ").append(orderDir);
                    sb.append(", subNivel ASC, numero ASC");
                }
            } else {
                sb.append(" ORDER BY c.").append(orderBy).append(" ").append(orderDir);
            }
        }
    }
    
}

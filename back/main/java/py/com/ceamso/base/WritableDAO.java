/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.base;

import java.util.List;
import javax.persistence.Query;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author mbaez
 * @param <G>
 */
public abstract class WritableDAO<G extends WritableEntity> extends ReadableDAO<G>{

    /**
     *
     * @param id
     * @param dto
     * @throws AppException
     */
    public void modify(Long id, G dto) throws AppException {
        G entity = (G) em.find(getEntity(), id);
        if (entity == null) {
            throw new AppException(404, "Not Found");
        }
        em.merge(dto);
    }
    
    public void modify(G dto) throws AppException {
        em.merge(dto);
    }

    /**
     *
     * @param entity
     * @throws AppException
     */
    public void insert(G entity) throws AppException {
        //try {
            em.persist(entity);
        /*} catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /**
     *
     * @param id
     * @throws AppException
     */
    public void delete(Long id) throws AppException {
    	System.err.println("Eliminar registro " + id);
        G entity = (G) em.find(getEntity(), id);
        if (entity == null) {
            throw new AppException(404, "Not Found");
        }
        //em.remove(entity);
    }

    /**
     * @param id
     * @throws AppException
     */
    public G verificarConstraint(Long id, StringBuilder query) throws AppException {
        try {
            Query q = em.createQuery(query.toString());
            q.setParameter("id", id);
            List<G> rows = q.getResultList();
            if (rows.size() > 0) {
                return rows.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(500, e.getMessage());
        }
    }
}

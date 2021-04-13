package py.com.ceamso.administracion.dao;

import java.util.List;

import javax.persistence.Query;

import py.com.ceamso.administracion.model.OrientacionFunc;
import py.com.ceamso.base.ReadableDAO;

/**
 *
 * @author konecta
 */
public class OrientacionFuncDAO extends ReadableDAO<OrientacionFunc> {
    
    @Override
    public Class getEntity() {
        return OrientacionFunc.class;
    }
    
    public OrientacionFunc getOrientacion(String den) {
    	
    	String sql = "SELECT o FROM OrientacionFunc o WHERE o.nombreOrientacion = :den";
    	Query q = em.createQuery(sql);
    	q.setParameter("den", den);
    	List<OrientacionFunc> lista = q.getResultList();
    	if (lista != null && lista.size() > 0) {
    		return lista.get(0);
    	}
    	return null;
    }
}

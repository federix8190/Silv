package py.com.ceamso.administracion.dao;

import java.util.List;

import javax.persistence.Query;

import py.com.ceamso.administracion.model.Ambito;
import py.com.ceamso.administracion.model.Departamento;
import py.com.ceamso.base.ReadableDAO;

/**
 *
 * @author konecta
 */
public class AmbitoDAO extends ReadableDAO<Ambito> {

	@Override
	public Class getEntity() {
		return Ambito.class;
	}

	public List<Ambito> getAmbitos() {

		String sql = "SELECT a FROM Ambito a";
		Query q = em.createQuery(sql);
		return q.getResultList();
	}
}

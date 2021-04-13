package py.com.ceamso.administracion.dao;

import py.com.ceamso.administracion.model.Mecip;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.utils.AppException;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;
import py.com.ceamso.base.ListaResponse;

/**
 *
 * @author mbaez
 */
public class MecipDAO extends WritableDAO<Mecip> {

    /**
     * 
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return Mecip.class;
    }
    
    public boolean existeMecip(String codigo) {
        String sql = "SELECT c FROM Mecip c WHERE c.codigo = :codigo";
        Query q = em.createQuery(sql);
        q.setParameter("codigo", codigo);
        List<Mecip> res = q.getResultList();
        if (res != null && res.size() > 0) {
            return true;
        }
        return false;
        
    }
    
    public boolean existeMecip(String codigo, Long id) {
        String sql = "SELECT c FROM Mecip c WHERE c.codigo = :codigo AND c.id != :id";
        Query q = em.createQuery(sql);
        q.setParameter("codigo", codigo);
        q.setParameter("id", id);
        List<Mecip> res = q.getResultList();
        if (res != null && res.size() > 0) {
            return true;
        }
        return false;
        
    }
    
    public void insert(Mecip entity) throws AppException {
    	if (entity.getCodigo() != null) {
	    	String[] niveles = entity.getCodigo().split("\\.");
	    	if (niveles.length > 0) {
	    		entity.setNivel1(new Integer(niveles[0]));
	    	}
	    	if (niveles.length > 1) {
	    		entity.setNivel2(new Integer(niveles[1]));
	    	}
	    	if (niveles.length > 2) {
	    		entity.setNivel3(new Integer(niveles[2]));
	    	}
    	}
    	em.persist(entity);
    }
    
    /*public ListaResponse<Mecip> listar(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros) {
        
        StringBuilder query = new StringBuilder();
        query.append("select codigo, den from cte.mecip ");
        query.append("order by split_part(codigo, '.', 1)::int asc, codigo asc");
    }*/
    
    @Override
    public void buildOrder(StringBuilder sb, String orderBy, String orderDir) {
        if (orderBy != null && !orderBy.isEmpty()) {
            if (orderBy.equals("codigo")) {
                if (orderDir.equals("ASC")) {
                    sb.append(" ORDER BY c.nivel1 ASC, c.nivel2 ASC, c.nivel3 ASC ");
                } else {
                    sb.append(" ORDER BY c.nivel1 DESC, c.nivel2 DESC, c.nivel3 DESC ");
                }
            } else {
                sb.append(" ORDER BY c.").append(orderBy).append(" ").append(orderDir);
            }
        }
    }
    
    @Override
    public void setParametrers(Query q, HashMap<String, Object> filtros) {
        if (filtros == null) {
            return;
        }
        for (String key : filtros.keySet()) {
            Object value = filtros.get(key);
            if (filtros.get(key) instanceof String) {
            	if (key.equals("codigo")) {
            		value = value + "%";
            	} else {
            		value = "%" + value + "%";
            	}
            }
            q.setParameter(key, value);
        }
    }
}

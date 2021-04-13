package py.com.ceamso.base;

import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import py.com.ceamso.administracion.model.Categoria;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author mbaez
 * @param <G>
 */
public abstract class ReadableDAO<G> {

    @PersistenceContext(unitName = "CTEPostgresPU")
    protected EntityManager em;

    /**
     * Retorna el class de Entity utilizado para las operaciones del tipo CRUD.
     *
     * @return el class.
     */
    public abstract Class getEntity();

    /**
     *
     * @param sb
     * @param orderBy
     * @param orderDir
     */
    public void buildOrder(StringBuilder sb, String orderBy, String orderDir) {
        if (orderBy != null && !orderBy.isEmpty()) {
            sb.append(" ORDER BY c.").append(orderBy).append(" ").append(orderDir);
        }
    }

    /**
     *
     * @param sb
     * @param filtros
     */
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1;
        sb.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {
                sb.append(" LOWER(c.")
                        .append(key)
                        .append(") LIKE LOWER(:")
                        .append(key)
                        .append(")");
            } else {
                sb.append(key)
                        .append(" = :")
                        .append(key);
            }
            //se añade el 'AND' si hay más caracteres.
            if (token < tokens) {
                sb.append(" AND ");
            }
            token++;
        }
    }

    /**
     *
     * @param q
     * @param filtros
     */
    public void setParametrers(Query q, HashMap<String, Object> filtros) {
        if (filtros == null) {
            return;
        }
        for (String key : filtros.keySet()) {
            Object value = filtros.get(key);
            if (filtros.get(key) instanceof String) {
                value = "%" + value + "%";
            }
            q.setParameter(key, value);
        }
    }

    /**
     *
     * @param filtros
     * @return
     */
    private int count(HashMap<String, Object> filtros) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(c) FROM ")
                .append(getEntity().getCanonicalName())
                .append(" c");
        buildWhere(query, filtros);
        Query q = em.createQuery(query.toString());
        setParametrers(q, filtros);
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     *
     * @param inicio
     * @param cantidad
     * @param orderBy
     * @param odrerDir
     * @param filtros
     * @return
     */
    public ListaResponse<G> listar(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros) {
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT c FROM ")
                .append(getEntity().getCanonicalName())
                .append(" c");
        if (cantidad == -1) {
        	if (filtros == null) {
        		filtros = new HashMap<String, Object>();
        	}
        	if (!filtros.containsKey("activo") && !getEntity().equals(Categoria.class)) {
        		String className = getEntity().getCanonicalName();
        		if (!className.equals("py.com.ceamso.reportes.model.CargoConvocatoria") 
        				&& !className.equals("py.com.ceamso.reportes.model.AnexosNoDistribuido")
        				&& !className.equals("py.com.ceamso.reportes.model.Anexos")) {
        			filtros.put("activo", true);
        		}
        	}
        }
        buildWhere(query, filtros);
        buildOrder(query, orderBy, odrerDir);
        System.err.println(query.toString());
        Query q = em.createQuery(query.toString());
        setParametrers(q, filtros);
        q.setFirstResult(inicio);
        if (cantidad != -1) {            
            q.setMaxResults(cantidad);
        }
        List<G> list = q.getResultList();
        int total = count(filtros);
        //se construye la respuesta 
        ListaResponse<G> res = new ListaResponse<G>();
        res.setRows(list);
        res.setCount(total);
        return res;

    }

    /**
     *
     * @param id
     * @return
     * @throws AppException
     */
    public G get(Long id) throws AppException {
        try {
            return (G) em.find(getEntity(), id);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
}

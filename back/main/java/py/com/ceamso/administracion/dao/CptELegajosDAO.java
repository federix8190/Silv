package py.com.ceamso.administracion.dao;

import py.com.ceamso.administracion.view.CptEELegajosView;
import py.com.ceamso.base.ReadableDAO;
import py.com.ceamso.base.ListaResponse;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author konecta
 */
public class CptELegajosDAO extends ReadableDAO<CptEELegajosView> {

    @Override
    public Class getEntity() {
        return CptEELegajosView.class;
    }
    
    public ListaResponse<CptEELegajosView> listarLegajos(Integer inicio, Integer cantidad, 
            String orderBy, String orderDir, HashMap<String, Object> filtros) {
        
        if (orderBy.equals("id")) {
            orderBy = "orden";
        }
        
        /*if (filtros != null && filtros.containsKey("mes")) {
            filtros.remove("mes");
        }*/       
        StringBuilder sb = new StringBuilder(); 
        sb.append("SELECT c FROM CptEELegajosView c ");
        buildWhere(sb, filtros);
        buildOrder(sb, orderBy, orderDir);
        System.err.println(sb.toString());
        Query q = em.createQuery(sb.toString());
        setParametrers(q, filtros);
        q.setFirstResult(inicio);
        if (cantidad != -1) {            
            q.setMaxResults(cantidad);
        }
        List<CptEELegajosView> lista = q.getResultList();
        
        /*if (filtros != null && filtros.containsKey("mes")) {
            filtros.remove("mes");
        }*/
        sb = new StringBuilder();
        sb.append("SELECT COUNT(c) FROM CptEELegajosView c ");
        buildWhere(sb, filtros);
        System.err.println(sb.toString());
        q = em.createQuery(sb.toString());
        setParametrers(q, filtros);
        int count = ((Long) q.getSingleResult()).intValue();
        //int count = count(filtros);
        
        ListaResponse<CptEELegajosView> res = new ListaResponse<>();
        res.setRows(lista);
        res.setCount(count);
        return res;
    }
    
    @Override
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1;
        sb.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (key.equals("anho")) {
                sb.append("c.id.anho = :anho ");
            } else if (key.equals("mes")) {
                sb.append("c.id.mes = :mes ");
            } else if (key.equals("cedulaIdentidad")) {
                sb.append("c.id.cedulaIdentidad = :cedulaIdentidad ");
            } else if (key.equals("apellido")) {
                sb.append("LOWER(c.apellido) like LOWER(:apellido) ");
            } else if (key.equals("nombre")) {
                sb.append("LOWER(c.nombre) like LOWER(:nombre) ");
            } else if (key.equals("vinculacionFuncionario")) {
                sb.append("LOWER(c.vinculacionFuncionario) like LOWER(:vinculacionFuncionario) ");
            } else if (key.equals("numeroTramo")) {
                sb.append("LOWER(c.numeroTramo) like LOWER(:numeroTramo) ");
            } else if (key.equals("nivel")) {
                sb.append("LOWER(c.nivel) like LOWER(:nivel) ");
            } else if (key.equals("cargo")) {
                sb.append("LOWER(c.cargo) like LOWER(:cargo) ");
            } else if (key.equals("nombreDepartamento")) {
                sb.append("LOWER(c.nombreDepartamento) like LOWER(:nombreDepartamento) ");
            } else if (key.equals("nombreDistrito")) {
                sb.append("LOWER(c.nombreDistrito) like LOWER(:nombreDistrito) ");
            }
            //se añade el 'AND' si hay más caracteres.
            if (token < tokens) {
                sb.append(" AND ");
            }
            token++;
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
                value = "%" + value + "%";
            }
            q.setParameter(key, value);
        }
    }
    
    @Override
    public void buildOrder(StringBuilder sb, String orderBy, String orderDir) {
        if (orderBy != null && !orderBy.isEmpty()) {
            if (orderBy.equals("anho") || orderBy.equals("mes") || orderBy.equals("cedulaIdentidad")) {
                sb.append(" ORDER BY c.id.").append(orderBy).append(" ").append(orderDir);
            } else {
                sb.append(" ORDER BY c.").append(orderBy).append(" ").append(orderDir);
            }
        }
    }
    
}

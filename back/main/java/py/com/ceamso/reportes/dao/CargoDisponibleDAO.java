package py.com.ceamso.reportes.dao;

import java.util.HashMap;
import java.util.List;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import py.com.ceamso.base.ReadableDAO;
import py.com.ceamso.gestion.model.CargoDisponible;
import py.com.ceamso.reportes.model.CargoDisponibleView;
import py.com.ceamso.reportes.model.CargoVacante;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author konecta
 */
public class CargoDisponibleDAO extends ReadableDAO<CargoDisponibleView> {

    @Override
    public Class getEntity() {
        return CargoDisponibleView.class;
    }
    
     /**
     *
     * @param sb
     * @param filtros
     */
    @Override
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1;
        sb.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {
                if (key.equals("programa")) {
                    key = "descripcionPrograma";
                }else if (key.equals("subPrograma")) {
                    key = "descripcionSubprograma";
                }
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
            if (key.equals("programa")) {
                key = "descripcionPrograma";
            }else if (key.equals("subprograma")) {
                key = "descripcionSubprograma";
            }
            q.setParameter(key, value);
        }
    }
    
    public void eliminar(Long id) throws AppException {
        CargoDisponible entity = (CargoDisponible) em.find(CargoDisponible.class, id);
        if (entity == null) {
            throw new AppException(404, "Not Found");
        }
        em.remove(entity);
    }
    
    public boolean eliminarCargoVacante(Long id, HttpServletRequest httpRequest) throws AppException {
                System.out.println("paso por aqui");

        String sql = "SELECT c FROM Convocatoria c where c.idCargo= :id";
                System.out.println("sql: "+ sql);

        Query query = em.createQuery(sql);
        System.out.println("id: "+ id);
        query.setParameter("id", id);
        List<CargoDisponible> res = query.getResultList();
        if(res!=null && res.size()>0){
            return false; //no se puede eliminar el Cargo Vacante
        }else {
            CargoDisponible entity = (CargoDisponible) em.find(CargoDisponible.class, id);
            System.out.println("entity: "+ entity);
            if (entity == null) {
                throw new AppException(404, "Not Found");
            }
            em.remove(entity);
            return true;
        }
    }
    
}

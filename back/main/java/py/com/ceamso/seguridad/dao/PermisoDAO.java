package py.com.ceamso.seguridad.dao;

import java.util.List;
import javax.persistence.Query;
import py.com.ceamso.base.ReadableDAO;
import py.com.ceamso.seguridad.model.Permiso;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author konecta
 */
public class PermisoDAO extends ReadableDAO<Permiso> {

    @Override
    public Class getEntity() {
        return Permiso.class;
    }
    
    public PermisoDAO(){
    }
    
    public List<Permiso> getAll() {
        
        String sql = "SELECT p FROM Permiso p";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }
    
    public Permiso get(Integer id) throws AppException {
        try {
            return (Permiso) em.find(getEntity(), id);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
}

package py.com.ceamso.reportes.dao;

import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import py.com.ceamso.base.ReadableDAO;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.gestion.model.CargoDisponible;
import py.com.ceamso.reportes.model.CargoVacante;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;
import py.com.ceamso.utils.Utils;

/**
 *
 * @author konecta
 */
public class CargoVacanteDAO extends ReadableDAO<CargoVacante> {
    
    /*@Inject
    @Configuracion("esHacienda")
    private String esHacienda;*/

    @Override
    public Class getEntity() {
        return CargoVacante.class;
    }
    
    /*public void cambiarEstado(Long id, HttpServletRequest httpRequest) throws AppException {
        
        String sql = "SELECT c FROM CargoDisponible c WHERE c.id = :id";
        Query q = em.createQuery(sql);
        q.setParameter("id", id);
        List<CargoDisponible> list = q.getResultList();
        System.err.println("Datos : " + list.size() + " - " + id);
        if (list != null && list.size() > 0) {
            CargoDisponible entity = list.get(0);
            entity.setAsignable(true);
            entity.setFechaModificacion(new Date());
            entity.setUsuarioModificacion(Utils.obtenerUsuarioAutenticado().getId());
            entity.setIpModificacion(httpRequest.getRemoteAddr());
            em.merge(entity);
        }
    }*/
    
    public void cambiarEstado(Long id, CargoVacante datos, HttpServletRequest httpRequest) throws AppException {
        
        String sql = "SELECT c FROM CargoDisponible c WHERE c.id = :id";
        Query q = em.createQuery(sql);
        q.setParameter("id", id);
        List<CargoDisponible> list = q.getResultList();
        System.err.println("Datos : " + list.size() + " - " + id);
        if (list != null && list.size() > 0) {
            CargoDisponible entity = list.get(0);
            entity.setAsignable(datos.isAsignable());
            em.merge(entity);
        }
    }
    
}

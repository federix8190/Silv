package py.com.ceamso.reportes.dao;

import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import py.com.ceamso.base.ReadableDAO;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.gestion.model.CargoDisponible;
import py.com.ceamso.reportes.model.CargoNoAsignado;
import py.com.ceamso.reportes.model.CargoVacante;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;
import py.com.ceamso.utils.Utils;

/**
 *
 * @author konecta
 */
public class CargoNoAsignadoDAO extends ReadableDAO<CargoNoAsignado> {
    
    /*@Inject
    @Configuracion("esHacienda")
    private String esHacienda;*/

    @Override
    public Class getEntity() {
        return CargoNoAsignado.class;
    }
    
    public boolean cambiarEstado(Long id, CargoNoAsignado dto, HttpServletRequest httpRequest) 
            throws AppException {
        /*String schema;
        if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {
            schema = Constantes.ESQUEMA_CTE_MH;
        } else {
            schema = Constantes.ESQUEMA_CTE_PJ;
        }
        boolean disponible = !dto.getDisponible();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(schema).append(".anexo_liquidacion ");
        sql.append("SET disponible = :disponible WHERE codigo_anexo = :id");
        Query q = em.createNativeQuery(sql.toString());
        q.setParameter("disponible", disponible);
        q.setParameter("id", id);
        q.executeUpdate();*/
        
        String sql = "SELECT c FROM CargoDisponible c WHERE c.idCargoDisponible = :id";
        Query q = em.createQuery(sql);
        q.setParameter("id", id);
        List<CargoDisponible> list = q.getResultList();
        System.err.println("Datos : " + list.size() + " - " + id);
        if (list != null && list.size() > 0) {
            Long idCargo = list.get(0).getId();
            CargoDisponible entity = em.find(CargoDisponible.class, idCargo);
            if(entity.getIdCptEe() != null || entity.getIdCptEf() != null){
                return false;
            }else{
                em.remove(entity);
                return true;
            }
        } else {        
            CargoDisponible entity = new CargoDisponible(dto);
            entity.setFechaCreacion(new Date());
            entity.setUsuarioCreacion(Utils.obtenerUsuarioAutenticado().getId());
            entity.setIpCreacion(httpRequest.getRemoteAddr());
            entity.setActivo(true);
            em.persist(entity);
            return true;
        }
    }

}

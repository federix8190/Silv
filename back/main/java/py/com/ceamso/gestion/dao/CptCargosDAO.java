package py.com.ceamso.gestion.dao;

import java.util.HashMap;
import java.util.List;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.gestion.model.CptCargos;

import javax.ejb.Stateless;
import py.com.ceamso.gestion.model.CargoDisponible;
import py.com.ceamso.utils.AppException;

@Stateless
public class CptCargosDAO extends WritableDAO<CptCargos> {

    /**
     *
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return CptCargos.class;
    }

    public CptCargosDAO() {
    }

    public void actualizarCargoDisponible(Long idCargoDisponible, Long idCpt, Long idCpteE, Long idCpteF){
        CargoDisponible c = em.find(CargoDisponible.class, idCargoDisponible);
        if(c != null){
            c.setIdCpt(idCpt);
            c.setIdCptEe(idCpteE);
            c.setIdCptEf(idCpteF);
            em.merge(c);
        }
        
    }
}

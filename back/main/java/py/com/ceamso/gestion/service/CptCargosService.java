package py.com.ceamso.gestion.service;

import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.gestion.dao.CptCargosDAO;
import py.com.ceamso.gestion.model.CptCargos;
import py.com.ceamso.utils.AppException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Stateless
public class CptCargosService extends WritableServiceImpl<CptCargos, CptCargosDAO> {

    @Inject
    private CptCargosDAO dao;

    @Override
    public CptCargosDAO getDao() {
        return dao;
    }

    @Override
    public CptCargos insertar(CptCargos entity, HttpServletRequest httpRequest) throws AppException {
      /**  StringBuilder query = new StringBuilder();
        query.append("SELECT c " +
                "FROM CptCargos c " +
                "WHERE c.id = :id ");
        CptCargos cptCargos = getDao().verificarConstraint(entity.getIdCargoDisponible(), query);
        if(cptCargos != null){
            dao.delete(entity.getIdCargoDisponible());
        }
        return super.insertar(entity, httpRequest);**/
      getDao().actualizarCargoDisponible(entity.getIdCargoDisponible(), entity.getIdCpt(), entity.getIdCptEe(),
              entity.getIdCptEf());
      System.out.println("entity: "+ entity);
      return null;
    }
}

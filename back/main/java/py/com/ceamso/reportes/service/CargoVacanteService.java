package py.com.ceamso.reportes.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import py.com.ceamso.base.ReadableServiceImpl;
import py.com.ceamso.reportes.dao.CargoVacanteDAO;
import py.com.ceamso.reportes.model.CargoVacante;
import py.com.ceamso.utils.AppException;

@Stateless
public class CargoVacanteService extends ReadableServiceImpl<CargoVacante, CargoVacanteDAO> {
    
    @Inject
    private CargoVacanteDAO dao;

    @Override
    public CargoVacanteDAO getDao() {
        return dao;
    }

    public CargoVacanteService() {
    }
    
    public void cambiarEstado(Long id, CargoVacante entity, HttpServletRequest httpRequest) throws AppException {
        
        try {
            getDao().cambiarEstado(id, entity, httpRequest);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
        
}

package py.com.ceamso.reportes.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import py.com.ceamso.base.ReadableServiceImpl;
import py.com.ceamso.reportes.dao.CargoDisponibleDAO;
import py.com.ceamso.reportes.model.CargoDisponibleView;
import py.com.ceamso.utils.AppException;

@Stateless
public class CargoDisponibleService extends ReadableServiceImpl<CargoDisponibleView, CargoDisponibleDAO> {
    
    @Inject
    private CargoDisponibleDAO dao;

    @Override
    public CargoDisponibleDAO getDao() {
        return dao;
    }

    public CargoDisponibleService() {
    }
    
    public boolean eliminarCargoVacante(Long id, HttpServletRequest httpRequest) throws AppException {
        
        try {
            boolean eliminado;
            eliminado = getDao().eliminarCargoVacante(id, httpRequest);
            return eliminado;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
        
}

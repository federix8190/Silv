package py.com.ceamso.reportes.service;

import py.com.ceamso.base.ReadableServiceImpl;
import py.com.ceamso.reportes.dao.CargoNoAsignadoDAO;
import py.com.ceamso.reportes.model.CargoNoAsignado;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import py.com.ceamso.reportes.model.CargoVacante;
import py.com.ceamso.utils.AppException;

@Stateless
public class CargoNoAsignadoService extends ReadableServiceImpl<CargoNoAsignado, CargoNoAsignadoDAO> {

    @Inject
    private CargoNoAsignadoDAO dao;

    @Override
    public CargoNoAsignadoDAO getDao() {
        return dao;
    }

    public CargoNoAsignadoService() {
    }
    
    public boolean cambiarEstado(Long id, CargoNoAsignado entity, HttpServletRequest httpRequest) 
            throws AppException {
        
        try {
            boolean cambio = getDao().cambiarEstado(id, entity, httpRequest);
            return cambio;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
        
}

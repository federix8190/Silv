package py.com.ceamso.reportes.service;

import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableServiceImpl;
import py.com.ceamso.reportes.dao.CargoConvocatoriaDAO;
import py.com.ceamso.reportes.model.CargoConvocatoria;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;

@Stateless
public class CargoConvocatoriaService extends ReadableServiceImpl<CargoConvocatoria, CargoConvocatoriaDAO> {

    @Inject
    private CargoConvocatoriaDAO dao;
    
    /**
     *
     * @{@inheritDoc}
     */
    @Override
    public CargoConvocatoriaDAO getDao() {
        return dao;
    }

    public CargoConvocatoriaService() {
    }
    
}

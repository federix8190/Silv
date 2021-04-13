package py.com.ceamso.reportes.dao;

import java.util.List;
import javax.persistence.Query;
import py.com.ceamso.base.ReadableDAO;
import py.com.ceamso.reportes.model.CargoConvocatoria;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author konecta
 */
public class CargoConvocatoriaDAO extends ReadableDAO<CargoConvocatoria> {

    /**
     * 
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return CargoConvocatoria.class;
    }
    
}

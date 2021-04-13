package py.com.ceamso.seguridad.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.seguridad.dao.PermisoDAO;
import py.com.ceamso.seguridad.model.Permiso;

@Stateless
public class PermisoService {

    @Inject
    private PermisoDAO dao;
    
    public List<Permiso> getAll() {
        return dao.getAll();
    }
    
}

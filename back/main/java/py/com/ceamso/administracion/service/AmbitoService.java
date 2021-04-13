package py.com.ceamso.administracion.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.administracion.dao.AmbitoDAO;
import py.com.ceamso.administracion.model.Ambito;
import py.com.ceamso.administracion.model.Departamento;
import py.com.ceamso.base.ReadableServiceImpl;

/**
 *
 * @author konecta
 */
@Stateless
public class AmbitoService extends ReadableServiceImpl<Ambito, AmbitoDAO> {
    
    @Inject
    private AmbitoDAO dao;
    
    @Override
    public AmbitoDAO getDao() {
        return dao;
    }

    public AmbitoService() {
    }
    
    public List<Ambito> getAmbitos() {
        List<Ambito> lista = getDao().getAmbitos();
        //lista.add(new Ambito(0L, "TODOS"));
        return lista;
    }
    
    
}
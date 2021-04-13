package py.com.ceamso.administracion.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.administracion.dao.CategoriaDAO;
import py.com.ceamso.administracion.model.Categoria;
import py.com.ceamso.base.ReadableServiceImpl;

/**
 *
 * @author konecta
 */
@Stateless
public class CategoriaService extends ReadableServiceImpl<Categoria, CategoriaDAO> {
    
    @Inject
    private CategoriaDAO dao;
    
    @Override
    public CategoriaDAO getDao() {
        return dao;
    }

    public CategoriaService() {
    }
    
}

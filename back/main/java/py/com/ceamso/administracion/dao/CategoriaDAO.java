package py.com.ceamso.administracion.dao;

import py.com.ceamso.administracion.model.Categoria;
import py.com.ceamso.base.ReadableDAO;

/**
 *
 * @author konecta
 */
public class CategoriaDAO extends ReadableDAO<Categoria> {
    
    @Override
    public Class getEntity() {
        return Categoria.class;
    }
}

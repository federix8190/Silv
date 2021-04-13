package py.com.ceamso.administracion.resource;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import py.com.ceamso.administracion.model.Categoria;
import py.com.ceamso.administracion.service.CategoriaService;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableResource;

/**
 *
 * @author konecta
 */
@Path("/administracion/categoria")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CategoriaResource extends ReadableResource<Categoria, CategoriaService> {

    @EJB
    private CategoriaService service;

    @Override
    public CategoriaService getService() {
        return service;
    }

    public Class<Categoria> getDtoClass() {
        return Categoria.class;
    }

    @Override
    public ListaResponse<Categoria> listar(Integer pagina, Integer cantidad, String orderBy, String orderDir, String json) {
        return super.listar(pagina, cantidad, orderBy, "ASC", json); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

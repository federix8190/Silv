package py.com.ceamso.gestion.resource;

import py.com.ceamso.base.WritableResource;
import py.com.ceamso.gestion.model.CptCargos;
import py.com.ceamso.gestion.service.CptCargosService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/cpt-cargos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CptCargosController extends WritableResource<CptCargos, CptCargosService> {

    @EJB
    private CptCargosService service;

    @Override
    public CptCargosService getService() {
        return service;
    }
}

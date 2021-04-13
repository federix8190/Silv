package py.com.ceamso.administracion.resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import py.com.ceamso.administracion.model.CptF;
import py.com.ceamso.administracion.service.CptFService;
import py.com.ceamso.base.WritableResource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.type.TypeReference;
import py.com.ceamso.administracion.facade.CptFFacade;
import py.com.ceamso.administracion.model.CptPrueba;
import py.com.ceamso.administracion.service.CptPruebaService;
import py.com.ceamso.administracion.view.CptEFLegajosView;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.reportes.model.Legajo;

/**
 *
 * @author mbaez
 */
@Path("/administracion/cpt-prueba")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CptPruebaResource extends WritableResource<CptPrueba, CptPruebaService> {

    @Inject
    private CptPruebaService service;

    @Override
    public CptPruebaService getService() {
        return service;
    }
    
}

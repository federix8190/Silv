/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.resource;

import py.com.ceamso.reportes.dto.GestionPersonasDTO;
import py.com.ceamso.reportes.service.GestionPersonasService;
import py.com.ceamso.utils.AppException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author mbaez
 */
@Path("/reportes/gestion-personas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GestionPersonasResource {

    @Inject
    GestionPersonasService service;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<GestionPersonasDTO> getGestionPersonas(
            @QueryParam("desdeAnho") Integer anho1,
            @QueryParam("desdeMes") Integer mes1,
            @QueryParam("hastaAnho") Integer anho2,
            @QueryParam("hastaMes") Integer mes2,
            @QueryParam("vinculacion") String vinculacion,
            @QueryParam("idCptEf") String idCptEf,
            @QueryParam("ambito") String ambito,
            @QueryParam("sexo") String sexo,
            @QueryParam("idCpt") String idCpt,
            @QueryParam("idCptEe") String idCptEe,
            @QueryParam("idCeo") String idCeo) throws AppException, NoSuchFieldException {
    	 
    	return service.getListaGestionPersonas(anho1, mes1, anho2, mes2, vinculacion, idCptEf, 
        		ambito, sexo, idCpt, idCptEe,idCeo);
    }

    @GET
    @Path("/desarrollo-personal")
    @Produces(MediaType.APPLICATION_JSON)
    public List<GestionPersonasDTO> getDesarrolloPersonal(
            @QueryParam("desdeAnho") Integer anho1,
            @QueryParam("desdeMes") Integer mes1,
            @QueryParam("hastaAnho") Integer anho2,
            @QueryParam("hastaMes") Integer mes2,
            @QueryParam("vinculacion") String vinculacion,
            @QueryParam("idCptEf") String idCptEf,
            @QueryParam("ambito") String ambito,
            @QueryParam("sexo") String sexo,
            @QueryParam("idCpt") String idCpt,
            @QueryParam("idCptEe") String idCptee,
            @QueryParam("idCeo") String idCeo) throws AppException, NoSuchFieldException {
        return service.getListaDesarrolloPersonal(anho1, mes1, anho2, mes2, vinculacion, idCptEf, ambito, sexo,idCpt,idCptee,idCeo);
    }

    @GET
    @Path("/promocion-salarial")
    @Produces(MediaType.APPLICATION_JSON)
    public List<GestionPersonasDTO> getPromocionSalarial(
            @QueryParam("desdeAnho") Integer anho1,
            @QueryParam("desdeMes") Integer mes1,
            @QueryParam("hastaAnho") Integer anho2,
            @QueryParam("hastaMes") Integer mes2,
            @QueryParam("vinculacion") String vinculacion,
            @QueryParam("idCptEf") String idCptEf,
            @QueryParam("ambito") String ambito,
            @QueryParam("sexo") String sexo,
            @QueryParam("idCpt") String idCpt,
            @QueryParam("idCptEe") String idCptee,
            @QueryParam("idCeo") String idCeo) throws AppException, NoSuchFieldException {
    	
    	return service.getListaPromocionSalarial(anho1, mes1, anho2, mes2, vinculacion, idCptEf, 
        		ambito, sexo,idCpt,idCptee,idCeo);
    }
}

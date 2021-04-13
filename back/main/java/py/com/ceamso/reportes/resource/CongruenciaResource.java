/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.resource;

import py.com.ceamso.reportes.dto.CeldaMatriz;
import py.com.ceamso.reportes.dto.PuestoRemuneracionDTO;
import py.com.ceamso.reportes.service.CongruenciaService;
import py.com.ceamso.utils.AppException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import py.com.ceamso.reportes.dto.ResumenMatriz;

/**
 * @author mbaez
 */
@Path("/reportes/congruencia")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CongruenciaResource {

    @Inject
    CongruenciaService service;

    /**
     * @param anho
     * @param mes
     * @param vinculacion
     * @param conceptoPago
     * @param programa
     * @param subprograma
     * @return
     * @throws AppException
     */
    @GET
    @Path("/matriz")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<Long, HashMap<Long, CeldaMatriz>> getMatriz(
            @QueryParam("anho") Integer anho,
            @QueryParam("mes") Integer mes,
            @QueryParam("vinculacion") String vinculacion,
            @QueryParam("conceptoPago") String conceptoPago,
            @QueryParam("programa") String programa,
            @QueryParam("subprograma") String subprograma,
            @QueryParam("idCpt") String idCpt,
            @QueryParam("idCptee") String idCptee,
            @QueryParam("ambitoCptE") String ambito,
            @QueryParam("idCeo") String idCeo,
            @QueryParam("idCptEf") String idCptEf) throws AppException {
        
        return service.getMatriz(anho, mes, vinculacion, conceptoPago, programa, 
                subprograma, idCpt, idCptee, idCeo, idCptEf, ambito);
    }
    
    @GET
    @Path("/resumen-matriz")
    @Produces(MediaType.APPLICATION_JSON)
    public ResumenMatriz getResumenMatriz(
            @QueryParam("anho") Integer anho,
            @QueryParam("mes") Integer mes,
            @QueryParam("vinculacion") String vinculacion,
            @QueryParam("conceptoPago") String conceptoPago,
            @QueryParam("programa") String programa,
            @QueryParam("subprograma") String subprograma,
            @QueryParam("idCpt") String idCpt,
            @QueryParam("idCptee") String idCptee,
            @QueryParam("idCeo") String idCeo,
            @QueryParam("idCptEf") String idCptEf) throws AppException {
        
        return service.getResumenMatriz(anho, mes, vinculacion, conceptoPago, programa, 
                subprograma,idCpt,idCptee,idCeo,idCptEf);
    }

    @GET
    @Path("/puesto-remuneracion")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PuestoRemuneracionDTO> getPuestoRemuneracion(
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
        return service.getPuestoRemuneracion(anho1, mes1, anho2, mes2, vinculacion, idCptEf, ambito, sexo,idCpt,idCptee,idCeo);
    }
}

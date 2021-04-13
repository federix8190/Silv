/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.service;

import py.com.ceamso.reportes.dao.GestionPersonasDAO;
import py.com.ceamso.reportes.dto.GestionPersonasDTO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author mbaez
 */
@Stateless
public class GestionPersonasService {

    @Inject
    GestionPersonasDAO dao;

    public List<GestionPersonasDTO> getListaGestionPersonas(Integer anho1, Integer mes1, Integer anho2,
                                                       Integer mes2, String vinculacion, String idCptEf,
                                                       String ambito, String sexo,String idCpt,String idCptee,String idCeo) throws NoSuchFieldException {
        return dao.getListaGestionPersonas(anho1, mes1, anho2, mes2, vinculacion, idCptEf, ambito, sexo,idCpt,idCptee,idCeo);
    }

    public List<GestionPersonasDTO> getListaDesarrolloPersonal(Integer anho1, Integer mes1, Integer anho2,
                                                          Integer mes2, String vinculacion, String idCptEf,
                                                          String ambito, String sexo,String idCpt,String idCptee,String idCeo) throws NoSuchFieldException {
        return dao.getListaDesarrolloPersonal(anho1, mes1, anho2, mes2, vinculacion, idCptEf, ambito, sexo,idCpt,idCptee,idCeo);
    }

    public List<GestionPersonasDTO> getListaPromocionSalarial(Integer anho1, Integer mes1, Integer anho2,
                                                         Integer mes2, String vinculacion, String idCptEf,
                                                         String ambito, String sexo,String idCpt,String idCptee,String idCeo) throws NoSuchFieldException {
        return dao.getListaPromocionSalarial(anho1, mes1, anho2, mes2, vinculacion, idCptEf, ambito, sexo,idCpt,idCptee,idCeo);
    }

}

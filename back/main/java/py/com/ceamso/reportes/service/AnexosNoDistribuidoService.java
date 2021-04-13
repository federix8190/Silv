/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.administracion.dao.CeoDAO;
import py.com.ceamso.administracion.dao.CptDAO;
import py.com.ceamso.administracion.dao.CptEDAO;
import py.com.ceamso.administracion.dao.CptFDAO;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableServiceImpl;
import py.com.ceamso.reportes.dao.AnexosNoDistribuidoDao;
import py.com.ceamso.reportes.model.AnexosNoDistribuido;

/**
 *
 * @author daniel.rojas
 */

@Stateless
public class AnexosNoDistribuidoService extends ReadableServiceImpl<AnexosNoDistribuido, AnexosNoDistribuidoDao>{
    
    @Inject
    AnexosNoDistribuidoDao dao;
        
    @Inject
    private CptDAO cptDAO;
    
    @Inject
    private CptFDAO cptfDAO;
    
    @Inject
    private CptEDAO cpteDAO;
    
    @Inject
    private CeoDAO ceoDAO;
    
     /**
     *
     * @{@inheritDoc}
     */
    @Override
    public AnexosNoDistribuidoDao getDao() {
        return dao;
    }

    public AnexosNoDistribuidoService() {
    }
    
    @Override
    public ListaResponse<AnexosNoDistribuido> listar(Integer inicio, Integer cantidad, String orderBy, 
            String orderDir, HashMap<String, Object> filtros) {
        
        ListaResponse<AnexosNoDistribuido> res = getDao().listar(inicio, cantidad, orderBy, orderDir, filtros);
        return res;
    }
    
    public List<Long> obtenerAsignaciones(Long cedula, int anho, int mes) {
        List<Long> asignaciones = new ArrayList<>();
        System.err.println("obtenerAsignaciones : " + cedula + " - " + anho + " - " + mes);
        asignaciones.add(cptDAO.getCptAsignado(cedula, anho, mes));
        asignaciones.add(cptfDAO.getCptFAsignado(cedula, anho, mes));
        asignaciones.add(cpteDAO.getCptEAsignado(cedula, anho, mes));
        asignaciones.add(ceoDAO.getCeoAsignado(cedula, anho, mes));
        return asignaciones;
    }

    public Long obtenerPorCategoria(Integer cedula, int anho, int mes, int anhoSF, int mesSF) {
    
        return dao.getCptEPorCategori(cedula, anho, mes, anhoSF, mesSF);
    }
}

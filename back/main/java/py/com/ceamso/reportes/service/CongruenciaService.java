/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.service;

import py.com.ceamso.administracion.dao.CptDAO;
import py.com.ceamso.administracion.dao.CtsDAO;
import py.com.ceamso.reportes.dao.CongruenciaDAO;
import py.com.ceamso.reportes.dto.CeldaMatriz;
import py.com.ceamso.reportes.dto.MatrizDTO;
import py.com.ceamso.reportes.dto.PuestoRemuneracionDTO;
import py.com.ceamso.utils.AppException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import py.com.ceamso.reportes.dto.ResumenMatriz;

/**
 * @author mbaez
 */
@Stateless
public class CongruenciaService {

    @Inject
    CongruenciaDAO dao;

    @Inject
    CtsDAO ctsDao;

    @Inject
    CptDAO cptDao;

    /**
     * Este método se encarga de inicializar las celdas por cada nivel con el
     * valor cero.
     *
     * @param niveles
     * @return
     */
    private HashMap<Long, CeldaMatriz> initCPT(List<Long> niveles) {
        HashMap<Long, CeldaMatriz> row = new HashMap<>();
        for (Long nivel : niveles) {
            row.put(nivel, new CeldaMatriz(0L, 0));
        }
        return row;
    }

    /**
     * Este método se encarga de calcular la matriz de congruencia utilizado los
     * tramos salariales y los niveles de puestos de trabajo.
     *
     * @param anho
     * @param mes
     * @param vinculacion
     * @param conceptoPago
     * @param programa
     * @param subprograma
     * @return
     * @throws AppException
     */
    public HashMap<Long, HashMap<Long, CeldaMatriz>> getMatriz(Integer anho, Integer mes,
                String vinculacion, String conceptoPago, String programa, String subprograma,
                String idCpt,String idCptee,String idCeo,String idCptEf, String ambito) 
            throws AppException {

        List<Long> niveles = cptDao.getNiveles();
        List<Long> tramos = ctsDao.getTramos();
        HashMap<Long, HashMap<Long, CeldaMatriz>> map = new HashMap<>();

        // inicializa los tramos
        for (Long tramo : tramos) {
            map.put(tramo, initCPT(niveles));
        }

        HashMap<Long, List<Long>> configCptTramos = cptDao.getConfiguracionTramos(anho, mes);

        // completa la matriz con los valores
        List<MatrizDTO> matriz = getCPTvsCTS(anho, mes, vinculacion, conceptoPago, 
                programa, subprograma, idCpt, idCptee, idCeo, idCptEf, ambito);
        System.err.println("--------------- Matriz ---------------");
        for (MatrizDTO m : matriz) {
        	System.err.println("Cpt : " + m.getCpt() + " - Tramo: " + m.getCts() + " ---> " + m.getSize());
            int congruente = 0;
            List<Long> rango = configCptTramos.get(m.getCpt());
            if (rango != null) {
                Long min = rango.get(0);
                Long max = rango.get(rango.size() - 1);
                if (m.getSize() > 0) {
                    if (m.getCts() >= min && m.getCts() <= max) {
                        congruente = 1;
                    } else {
                        if (m.getCts() < min) {
                            congruente = 2;
                        } else {
                            congruente = 3;
                        }
                    }
                }
            }
            if (m.getCts() != null && m.getCpt() != null)
                map.get(m.getCts()).put(m.getCpt(), new CeldaMatriz(m.getSize(), congruente));
        }

        // Completa la matriz con los valores 0
        for (Long tramo : tramos) {
            for (Long cpt : niveles) {
                CeldaMatriz datos = map.get(tramo).get(cpt);
                if (datos.getCantidad() == 0) {
                    List<Long> rango = configCptTramos.get(cpt);
                    if (rango != null) {
                        Long min = rango.get(0);
                        Long max = rango.get(rango.size() - 1);
                        if (tramo >= min && tramo <= max) {
                            map.get(tramo).put(cpt, new CeldaMatriz(0L, 1));
                        } else {
                            if (tramo < min) {
                                map.get(tramo).put(cpt, new CeldaMatriz(0L, 0));
                            } else {
                                map.get(tramo).put(cpt, new CeldaMatriz(0L, 4));
                            }
                        }
                    }
                }
            }
        }

        return map;
    }
    
    public ResumenMatriz getResumenMatriz(Integer anho, Integer mes,
                String vinculacion, String conceptoPago, String programa, String subprograma,
                String idCpt,String idCptee,String idCeo,String idCptEf) 
            throws AppException {
        
        return dao.getResumenMatriz(anho, mes, vinculacion, conceptoPago, programa, 
                subprograma, idCpt, idCptee, idCeo, idCptEf);
    }

    private List<MatrizDTO> getCPTvsCTS(Integer anho, Integer mes,
                    String vinculacion, String conceptoPago, 
                    String programa, String subprograma,String idCpt,
                    String idCptee,String idCeo,String idCptEf, String ambito) {
        
        return dao.getCPTvsCTS(anho, mes, vinculacion, conceptoPago, programa, subprograma,
        		idCpt,idCptee,idCeo,idCptEf, ambito);
    }

    public List<PuestoRemuneracionDTO> getPuestoRemuneracion(Integer anho1, Integer mes1,
                                                             Integer anho2, Integer mes2,
                                                             String vinculacion, String idCptEf,
                                                             String ambito, String sexo,String idCpt,String idCptee,String idCeo) 
            throws NoSuchFieldException {
        
        return dao.getPuestoRemuneracion(anho1, mes1, anho2, mes2, vinculacion, idCptEf, ambito, sexo,idCpt,idCptee,idCeo);
    }
}

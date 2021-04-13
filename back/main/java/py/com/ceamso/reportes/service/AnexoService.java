package py.com.ceamso.reportes.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import py.com.ceamso.reportes.model.Anexos;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import py.com.ceamso.administracion.dao.CeoDAO;
import py.com.ceamso.administracion.dao.CptDAO;
import py.com.ceamso.administracion.dao.CptEDAO;
import py.com.ceamso.administracion.dao.CptFDAO;
import py.com.ceamso.administracion.dao.PuestoTrabajoDAO;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableServiceImpl;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.reportes.dao.AnexosDAO;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.reportes.resource.AsignarLegajos;
import py.com.ceamso.seguridad.service.SessionService;
import py.com.ceamso.utils.AppException;

@Stateless
public class AnexoService extends ReadableServiceImpl<Anexos, AnexosDAO> {
    
    @Inject
    @Configuracion("esHacienda")
    private String esHacienda;
    
    @Inject
    @Configuracion("path_reportes_comparativos")
    private String pathReportesComparativos;

    @Inject
    private AnexosDAO dao;
    
    @Inject
    private CptDAO cptDAO;
    
    @Inject
    private CptFDAO cptfDAO;
    
    @Inject
    private CptEDAO cpteDAO;
    
    @Inject
    private CeoDAO ceoDAO;
    
    @Inject
    private PuestoTrabajoDAO puestoTrabajoDAO;

    /**
     *
     * @{@inheritDoc}
     */
    @Override
    public AnexosDAO getDao() {
        return dao;
    }

    public AnexoService() {
    }
    
    @Override
    public ListaResponse<Anexos> listar(Integer inicio, Integer cantidad, String orderBy, 
            String orderDir, HashMap<String, Object> filtros) {
        
        ListaResponse<Anexos> res = getDao().listar(inicio, cantidad, orderBy, orderDir, filtros);
        //ListaResponse<Anexos> res = getDao().listarAnexos(inicio, cantidad, orderBy, orderDir, filtros);
        return res;
    }
        
    public Anexos obtener(String[] tokens) throws AppException {
        
        try {
                        
            String anho = tokens[0];
            String mes = tokens[1];
            String cedulaIdentidad = tokens[2];
            //Long numeroPuestoTrabajo = new Long(tokens[3]);
            //String linea = tokens[4];
            String objetoGasto = tokens[3];
            String concepto = "";
            if (tokens.length == 5) {
                concepto = tokens[4];
            }
            return getDao().get(new Integer(anho), new Integer(mes), 
                    new Integer(cedulaIdentidad), objetoGasto, 
                    concepto);//, numeroPuestoTrabajo);
        
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public void comparativo(int anhoInicio, int mesInicio, int anhoFinal, int mesFinal, long idProceso) 
    		throws AppException, IOException {
        
        try {
                        
            getDao().comparativo(anhoInicio, mesInicio, anhoFinal, mesFinal, idProceso);
        
        } catch (Exception e) {
        	getDao().actualizarEstadoReporte("F", idProceso);
        }
    }
    
    public void actualizarEstadoReporte(String estado, Long idProceso) {
    	getDao().actualizarEstadoReporte(estado, idProceso);
    }
    
    public long getIdProceso(String ipCreacion) throws IOException {
    	long id = getDao().getIdProceso(ipCreacion);
    	//getDao().actualizarPathReporte(pathReportesComparativos, id);
    	String dataPath = System.getProperty("jboss.server.data.dir");
    	getDao().actualizarPathReporte(dataPath, id);
    	return id;
    }
    
    public long getUltimoProceso(Long userId) {
    	return getDao().getUltimoProceso(userId);
    }
    
    public HashMap<String, Object> getUltimoPeriodo() {
    	return getDao().getUltimoPeriodo();
    }
    
    public String getPathReporte(Long idProceso) throws IOException {
    	return getDao().getPathReporte(idProceso);
    }
    
    public String getEstadoReporte(Long idProceso) throws IOException {
    	return getDao().getEstadoReporte(idProceso);
    }
    
    public void guardarAnexosHacienda() {
        getDao().guardarAnexosHacienda();
    }
    
    public void cargarAnexosHacienda(int anho, int mes) {
        getDao().cargarAnexosHacienda(anho, mes);
    }
    
    public void asignarLegajosCpt(AsignarLegajos datos, HttpServletRequest httpRequest) {
        Legajo legajo = new Legajo();
        legajo.setCedulaIdentidad(datos.getCedulaIdentidad());
        legajo.setAnho(datos.getAnho());
        legajo.setMes(datos.getMes());
        legajo.setAsignado(true);
        if (datos.getIdCpt() != null && datos.getIdCpt() != 0) {
            cptDAO.asignarCptLegajo(datos.getIdCpt(), legajo, httpRequest);
        } else {
            cptDAO.eliminarCptLegajo(datos.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            puestoTrabajoDAO.actualizarPuestoTrabajoCpt(null, legajo, false, httpRequest);
        }
        if (datos.getIdCptF() != null && datos.getIdCptF() != 0) {
            cptfDAO.asignarLegajos(datos.getIdCptF(), legajo, httpRequest);
        } else {
            cptfDAO.eliminarCptFLegajo(datos.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            puestoTrabajoDAO.actualizarPuestoTrabajoCptF(null, legajo, false, httpRequest);
        }
        if (esHacienda == null || !esHacienda.equals("S")) {
            if (datos.getIdCptE() != null && datos.getIdCptE() != 0) {
                cpteDAO.asignarLegajos(datos.getIdCptE(), legajo, httpRequest);
            } else {
                cpteDAO.eliminarCptELegajo(datos.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
                puestoTrabajoDAO.actualizarPuestoTrabajoCptE(null, legajo, false, httpRequest);
            }
        }
    }
    
    public void asignarLegajosCeo(AsignarLegajos datos, HttpServletRequest httpRequest) {
        Legajo legajo = new Legajo();
        legajo.setCedulaIdentidad(datos.getCedulaIdentidad());
        legajo.setAnho(datos.getAnho());
        legajo.setMes(datos.getMes());
        legajo.setAsignado(true);
        if (datos.getIdCeo() != null && datos.getIdCeo() != 0) {
            ceoDAO.asignarLegajos(datos.getIdCeo(), legajo, httpRequest);
        } else {
            ceoDAO.eliminarCeoLegajo(datos.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            puestoTrabajoDAO.actualizarPuestoTrabajoCeo(null, legajo, false, httpRequest);
        }
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

    public Long obtenerPorCategoria(Integer cedula, int anho, int mes) {
    
        return dao.getCptEPorCategori(cedula, anho, mes);
    }
}

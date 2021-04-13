package py.com.ceamso.administracion.service;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.administracion.dao.PuestoTrabajoDAO;

/**
 *
 * @author konecta
 */
@Stateless
public class PuestoTrabajoService {

    @Inject
    private PuestoTrabajoDAO dao;
    
    public void cargarTabla(int anho, int mes) {
        dao.cargarTabla(anho, mes);
        //dao.limpiarCptE();
    }
    
    public void cargarPuestosExistentes(int anho, int mes) {
        System.err.println("cargarTabla");
        dao.cargarPuestosExistentes(anho, mes);
    }
    
    public List<Object[]> listarPlanilla(int anho, int mes) {
        return dao.listarPlanilla(anho, mes);
    }
    
    public BigDecimal getMaximoNumeroPuesto() {
        return dao.getMaximoNumeroPuesto();
    }
    
    public void cargarTabla(Object[] datos, int anho, int mes, BigDecimal numeroPuestoActual) {
        dao.cargarTabla(datos, anho, mes, numeroPuestoActual);
    }
}

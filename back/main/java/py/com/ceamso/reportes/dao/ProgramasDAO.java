package py.com.ceamso.reportes.dao;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.ceamso.config.Configuracion;

/**
 *
 * @author konecta
 */
public class ProgramasDAO {

    @PersistenceContext(unitName = "CTEPostgresPU")
    protected EntityManager em;
    
    @Inject
    @Configuracion("esHacienda")
    private String esHacienda;

    public List<String> getProgramas() {
        
        String sql = "";
        if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {
            sql = "select distinct programa from cte.anexos";
        } else {
            sql = "select distinct descrip_programa_presup from programa_presupuesto "
                  + "order by descrip_programa_presup";
        }
        
        Query q = em.createNativeQuery(sql);
        List<String> res = q.getResultList();
        return res;
    }
    
    public List<String> getSubProgramas() {
        
        String sql = "";
        if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {
            sql = "select distinct subprograma from cte.anexos";
        } else {
            sql = "select distinct descrip_subprograma_presup from subprograma_presupuesto "
                    + "order by descrip_subprograma_presup";
        }
        Query q = em.createNativeQuery(sql);
        List<String> res = q.getResultList();
        return res;
    }
    
}

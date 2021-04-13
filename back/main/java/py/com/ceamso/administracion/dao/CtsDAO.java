package py.com.ceamso.administracion.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import py.com.ceamso.administracion.model.Cts;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableDAO;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author mbaez
 */
public class CtsDAO  extends WritableDAO<Cts> {
    
    @Inject
    @Configuracion("esHacienda")
    private String esHacienda;

    @Override
    public Class getEntity() {
        return Cts.class;
    }
    
    /*public Cts get(Long id) throws AppException {
        try {
            return (Cts) em.find(getEntity(), id);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }*/
    
    public List<Cts> listar() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c FROM Cts c WHERE c.id > 0 ORDER BY c.numeroTramo");
        Query q = em.createQuery(sql.toString());
        return q.getResultList();
    }
    
    public void actualizarTramos(Long anho, Long mes) {
        
        String schema = "dbo.";
        if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {
            schema = "cte.";
        }
        
        String sql = "update " + schema + "puesto_trabajo set numero_tramo = ( " +
            " select numero_tramo from " + schema + "cts " +
            " where presupuestado >= minimo " +
            " AND presupuestado < maximo " +
            " AND anho = :anho AND mes = :mes )" +
            " where anho = :anho and mes = :mes";
        
        Query q = em.createNativeQuery(sql);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        q.executeUpdate();
        
    }
    
    public Cts getCtsByTramo(Long numeroTramo, Long anho, Long mes) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c FROM Cts c WHERE c.numeroTramo = :numeroTramo ");
        sql.append("AND c.anho = :anho AND c.mes = :mes");
        Query q = em.createQuery(sql.toString());
        q.setParameter("numeroTramo", numeroTramo);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<Cts> lista = q.getResultList();
        if (lista.size() > 0) {
            return lista.get(0);
        }
        return null;
    }
    
    public ListaResponse<Cts> listarTramos(int inicio, int cantidad, String orderDir) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT c.numeroTramo FROM Cts c ORDER BY c.numeroTramo ");
        sql.append(orderDir);
        Query q = em.createQuery(sql.toString());
        q.setFirstResult(inicio);
        if (cantidad != -1) {            
            q.setMaxResults(cantidad);
        }
        List<Long> tramos = q.getResultList();
        List<Cts> lista = new ArrayList<>();
        for (Long tramo : tramos) {
            lista.add(new Cts(tramo));
        }
        
        sql = new StringBuilder();
        sql.append("SELECT COUNT(DISTINCT c.numeroTramo) FROM Cts c ");
        q = em.createQuery(sql.toString());
        int count = ((Long) q.getSingleResult()).intValue();
        
        ListaResponse<Cts> res = new ListaResponse<>();
        res.setRows(lista);
        res.setCount(count);
        return res;
    }
    
    public List<Long> getTramos(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT distinct c.numeroTramo FROM Cts c ORDER BY c.numeroTramo");
        Query q = em.createQuery(sql.toString());
        return q.getResultList();
   
    }
    
    public Long getRango() {
        String sql = "SELECT MIN(minimo) FROM Cts WHERE numeroTramo != 0";
        Query q = em.createQuery(sql);
        Long minimo = (Long) q.getSingleResult();
        sql = "SELECT MAX(maximo) FROM Cts";
        q = em.createQuery(sql);
        Long maximo = (Long) q.getSingleResult();
        return maximo - minimo;
    }
    
}

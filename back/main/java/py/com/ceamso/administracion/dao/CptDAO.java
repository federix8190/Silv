package py.com.ceamso.administracion.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import py.com.ceamso.administracion.model.Ceo;
import py.com.ceamso.administracion.model.Cpt;
import py.com.ceamso.administracion.model.CptLegajo;
import py.com.ceamso.administracion.model.CptLegajoPK;
import py.com.ceamso.administracion.model.CptTramos;
import py.com.ceamso.administracion.model.CptTramosPK;
import py.com.ceamso.administracion.model.Cts;
import py.com.ceamso.administracion.view.CptLegajosView;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author mbaez
 */
@Stateless
public class CptDAO extends WritableDAO<Cpt> {

    //@Inject
    //private CptLegajosDAO legajoDAO;
    
    @Inject
    private CptFDAO cptFDAO;
    
    @Inject
    private CptEDAO cptEDAO;
    
    @Inject
    private PuestoTrabajoDAO puestoTrabajoDAO;
    /**
     * 
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return Cpt.class;
    }
    
    public CptDAO(){
    }
    
    public void insert(Cpt entity) throws AppException {
        em.persist(entity);
    }    
    
    public List<Long> getNiveles() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT distinct c.nivel FROM Cpt c ");
        sql.append("WHERE c.nivel > 0 "); 
        sql.append("ORDER BY c.nivel");
        Query q = em.createQuery(sql.toString());
        return q.getResultList();
    }
    
    public List<Long> getTramosAsignados(Long nivelCpt, Long anho, Long mes) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.pk.numeroTramo FROM CptTramos c ");
        sql.append("WHERE c.pk.nivelCpt = :nivelCpt ");
        sql.append("AND c.pk.anho = :anho AND c.pk.mes = :mes");
        
        Query q = em.createQuery(sql.toString());
        q.setParameter("nivelCpt", nivelCpt);
        q.setParameter("anho", anho.intValue());
        q.setParameter("mes", mes.intValue());
        return q.getResultList();
    }
    
    public void asignarTramos(long nivelCpt, Cts cts) { // List<Cts> listaCts) {
        
        /*List<CptTramos> tramosAsignados = getTramosCpt(nivelCpt);
        for (CptTramos cptTramo : tramosAsignados) {
            em.remove(cptTramo);
        }
        
        for (Cts cts : listaCts) {
            if (cts.isAsignado()) {
                CptTramosPK pk = new CptTramosPK(nivelCpt, cts.getNumeroTramo(), 
                        cts.getAnho().intValue(), cts.getMes().intValue());
                CptTramos entity = new CptTramos(pk);
                em.persist(entity);
            }
        }*/
        
        if (cts.isAsignado()) {
            CptTramosPK pk = new CptTramosPK(nivelCpt, cts.getNumeroTramo(), 
                    cts.getAnho().intValue(), cts.getMes().intValue());
            CptTramos entity = new CptTramos(pk);
            em.persist(entity);
        } else {
            CptTramosPK pk = new CptTramosPK(nivelCpt, cts.getNumeroTramo(), 
                    cts.getAnho().intValue(), cts.getMes().intValue());
            CptTramos entity = em.find(CptTramos.class, pk);
            if (entity != null) {
                em.remove(entity);
            }
        }
    }
    
    @Override
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1, start = 0, end = 0;;
        StringBuilder where = new StringBuilder();
        where.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {
                if(key.equals("tituloUnidad")){
                    String param =filtros.get("tituloUnidad").toString();
                    System.out.println("param= "+ param);
                    if(param.compareTo("TODOS")!=0){
                        where.append(key)
                            .append(" = :")
                            .append(key);
                    }
                }else{
                    where.append(" LOWER(c.");
                    where.append(key)
                        .append(") LIKE LOWER(:")
                        .append(key)
                        .append(")");
                }
                
            } else {
                    where.append(key)
                        .append(" = :")
                        .append(key);
            }
            //se añade el 'AND' si hay más caracteres.
            end = where.length();
            start = end-4;
            if (token < tokens && where.toString().compareTo(" WHERE ") != 0 
                    && !where.substring(start, end).equals("AND ")) {
                where.append(" AND ");
            }
            token++;
        }
        if(where.toString().compareTo(" WHERE ") != 0){
            end = where.length();
            start = end-4;
            if(where.substring(start, end).equals("AND ")){
                where.delete(start, end);
            }
              sb.append(where);
        }
    }
    
    @Override
    public void buildOrder(StringBuilder sb, String orderBy, String orderDir) {
        if (orderBy != null && !orderBy.isEmpty()) {
            if(orderBy.equals("nivel,sub_nivel,nro_g")){
                sb.append(" ORDER BY ");
                sb.append("c.nivel ").append("DESC");
                sb.append(" ,c.subNivel ").append("ASC");
                sb.append(" ,c.numeroGasto ").append("ASC");
            }else if(orderBy.equals("nivel")){
                sb.append(" ORDER BY ");
                sb.append("c.nivel ").append(orderDir);
                sb.append(" ,c.subNivel ").append(orderDir);
                sb.append(" ,c.numeroGasto ").append(orderDir);
            }else {
                sb.append(" ORDER BY c.");
                sb.append(orderBy).append(" ").append(orderDir);
            }
        }
    }
    
    @Override
    public void setParametrers(Query q, HashMap<String, Object> filtros) {
        if (filtros == null) {
            return;
        }
        for (String key : filtros.keySet()) {
            Object value = filtros.get(key);
            if (filtros.get(key) instanceof String) {
                value = "%" + value + "%";
            }       
            if (key.equals("tituloUnidad")) {
                String param =filtros.get("tituloUnidad").toString();
                if(param.compareTo("TODOS")!=0){
                    q.setParameter(key, value);         
                }
            } else{
                q.setParameter(key, value); 
            }
        }
    }
    
    private List<CptTramos> getTramosCpt(Long nivelCpt) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c FROM CptTramos c ");
        sql.append("WHERE c.pk.nivelCpt = :nivelCpt");
        Query q = em.createQuery(sql.toString());
        q.setParameter("nivelCpt", nivelCpt);
        return q.getResultList();
    }
    
    public List<Long> getCptByNivel(Long nivel) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.id FROM Cpt c WHERE c.nivel = :nivel");
        Query q = em.createQuery(query.toString());
        q.setParameter("nivel", nivel);
        return q.getResultList();
    }
    
    public List<Integer> getTramos(Long idCpt) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.id.numeroTramo FROM CptTramos c ");
        query.append("WHERE c.pk.nivelCpt = :idCpt ORDER BY c.pk.numeroTramo");
        Query q = em.createQuery(query.toString());
        q.setParameter("idCpt", idCpt);
        return q.getResultList();
    }
    
    public HashMap<Long, List<Long>> getConfiguracionTramos(int anho, int mes) {
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT c FROM CptTramos c ");
        query.append("WHERE c.pk.anho = :anho AND c.pk.mes = :mes ");
        query.append("ORDER BY c.pk.nivelCpt, c.pk.numeroTramo");
        Query q = em.createQuery(query.toString());
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<CptTramos> cptTramos = q.getResultList();
        
        HashMap<Long, List<Long>> res = new HashMap<>();
        for (CptTramos ct : cptTramos) {
            if (ct != null && ct.getPk() != null) {
                Long nivel = ct.getPk().getNivelCpt();
                List<Long> tramos = new ArrayList<>();
                if (res.containsKey(nivel)) {
                    tramos = res.get(nivel);
                }
                tramos.add(ct.getPk().getNumeroTramo());
                res.put(nivel, tramos);
            }
        }
        return res;
    }
    
     private Map<Long, Long> getIdCi(){
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk.idCpt, c.pk.cedulaIdentidad FROM CptLegajo c ");
        Query q = em.createQuery(query.toString());
        
        List<Object[]> resultList = q.getResultList();
        Map<Long, Long> resultMap = new HashMap<Long, Long>(resultList.size());
        for (Object[] result : resultList)
          resultMap.put((Long)result[0], (Long)result[1]);

        return resultMap;
    }
    
    public Long getCptAsignado(Long cedulaIdentidad, int anho, int mes){
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk.idCpt FROM CptLegajo c ");
        query.append("WHERE c.pk.cedulaIdentidad = :cedulaIdentidad");
        query.append(" AND c.pk.anho = :anho AND c.pk.mes = :mes");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaIdentidad", cedulaIdentidad);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<Long> list = q.getResultList();
        
        Long res = new Long(0);
        if(list.size() > 0)
            res = list.get(0);
        
        return res;
    }
    
    public List<Long> getLegajosAsignados(Long idCpt, int anho, int mes) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk.cedulaIdentidad FROM CptLegajo c ");
        query.append("WHERE c.pk.idCpt = :idCpt ");
        query.append("AND c.pk.anho = :anho AND c.pk.mes = :mes");
        Query q = em.createQuery(query.toString());
        q.setParameter("idCpt", idCpt);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<Long> list = q.getResultList();
        return list;
    }
    
    public CptLegajoPK getCptLegajoAsignado(Long idCpt, Long cedulaIdentidad, int anho, int mes) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk FROM CptLegajo c ");
        query.append("WHERE c.pk.idCpt = :idCpt AND c.pk.cedulaIdentidad = :cedulaIdentidad");
        query.append(" AND c.pk.anho = :anho AND c.pk.mes = :mes");
        Query q = em.createQuery(query.toString());
        q.setParameter("idCpt", idCpt);
        q.setParameter("cedulaIdentidad", cedulaIdentidad);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<CptLegajoPK> list = q.getResultList();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    
    public void asignarLegajos(long idCpt, Legajo legajo, HttpServletRequest httpRequest) {
        
        Cpt cpt = null;
        try {
            cpt = get(idCpt);
        } catch (AppException ex) {
            Logger.getLogger(CptDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        if (legajo.isAsignado()) {
            eliminarCptLegajo(legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CptLegajoPK pk = new CptLegajoPK(idCpt, legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CptLegajo entity = new CptLegajo(pk);
            em.persist(entity);
            puestoTrabajoDAO.actualizarPuestoTrabajoCpt(cpt, legajo, Boolean.TRUE, httpRequest);
        } else {
            CptLegajoPK pk = new CptLegajoPK(idCpt, legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CptLegajo entity = em.find(CptLegajo.class, pk);
            if (entity != null) {
                em.remove(entity);
                puestoTrabajoDAO.actualizarPuestoTrabajoCpt(cpt, legajo, Boolean.FALSE, httpRequest);
            }
        }
        
        cptFDAO.desasignarCptFLegajo(legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
        cptEDAO.desasignarCptELegajo(legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
    }
     
    public void eliminarCptLegajo(Long cedulaIdentidad, int anho, int mes) {
        String sql = "SELECT c FROM CptLegajo c WHERE c.pk.cedulaIdentidad = :cedulaIdentidad";
        sql += " AND c.pk.anho = :anho AND c.pk.mes = :mes";
        Query query = em.createQuery(sql);
        query.setParameter("cedulaIdentidad", cedulaIdentidad);
        query.setParameter("anho", anho);
        query.setParameter("mes", mes);
        List<CptLegajo> res = query.getResultList();
        System.err.println("eliminarCptLegajo : " + res.size() + " - " + cedulaIdentidad + " - " + anho + " - " + mes);
        for (CptLegajo c : res) {
            em.remove(c);
        }
    }
    
    private void asignarCptGLegajos(Long idCpt, Legajo legajo, HttpServletRequest httpRequest){
        Cpt cpt = null;
        try {
            cpt = get(idCpt);
        } catch (AppException ex) {
            Logger.getLogger(CptDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        if (legajo.isAsignado()) {
            eliminarCptLegajo(legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CptLegajoPK pk = new CptLegajoPK(idCpt, legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CptLegajo entity = new CptLegajo(pk);
            em.persist(entity);
            
            puestoTrabajoDAO.actualizarPuestoTrabajoCpt(cpt, legajo, Boolean.TRUE, httpRequest);

        } else {
            CptLegajoPK pk = new CptLegajoPK(idCpt, legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CptLegajo entity = em.find(CptLegajo.class, pk);
            if (entity != null) {
                em.remove(entity);
                
                puestoTrabajoDAO.actualizarPuestoTrabajoCpt(cpt, legajo, Boolean.FALSE, httpRequest);
            }
        }
    }
    
    public void asignarCptLegajo(Long idCpt, Legajo legajo, HttpServletRequest httpRequest){
        asignarCptGLegajos(idCpt, legajo, httpRequest);
    }
    
    public boolean existeCpt(long nivel, int subNivel, int numeroGasto) {
        String sql = "SELECT c FROM Cpt c WHERE c.nivel = :nivel "
        		+ "AND c.subNivel = :subNivel AND c.numeroGasto = :numeroGasto";
        Query q = em.createQuery(sql);
        q.setParameter("nivel", nivel);
        q.setParameter("subNivel", subNivel);
        q.setParameter("numeroGasto", numeroGasto);
        List<Ceo> res = q.getResultList();
        if (res != null && res.size() > 0) {
            return true;
        }
        return false;
        
    }
    
    public boolean existeCpt(long nivel, int subNivel, int numeroGasto, long id) {
        String sql = "SELECT c FROM Cpt c WHERE c.nivel = :nivel "
        		+ "AND c.subNivel = :subNivel AND c.numeroGasto = :numeroGasto AND c.id != :id";
        Query q = em.createQuery(sql);
        q.setParameter("id", id);
        q.setParameter("nivel", nivel);
        q.setParameter("subNivel", subNivel);
        q.setParameter("numeroGasto", numeroGasto);
        List<Ceo> res = q.getResultList();
        if (res != null && res.size() > 0) {
            return true;
        }
        return false;
        
    }

}

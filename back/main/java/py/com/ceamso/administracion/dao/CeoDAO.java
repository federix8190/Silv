package py.com.ceamso.administracion.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.Query;
import py.com.ceamso.administracion.model.Ceo;
import py.com.ceamso.administracion.model.CeoLegajoPK;
import py.com.ceamso.administracion.model.Cuo;
import py.com.ceamso.administracion.model.CeoLegajo;
import py.com.ceamso.administracion.view.CeoLegajosView;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.utils.AppException;

import java.util.HashMap;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import py.com.ceamso.reportes.dao.AnexosDAO;

/**
 *
 * @author mbaez
 */
public class CeoDAO extends WritableDAO<Ceo> {
        
    @Inject
    private CeoLegajosDAO legajoDAO;
    
    @Inject
    private PuestoTrabajoDAO puestoTrabajoDAO;
    
    @Inject
    private AnexosDAO anexosDAO;

    /**
     * 
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return Ceo.class;
    }
    
    public ListaResponse<Ceo> getByRange(int anho, int mes) {
    	
    	String fecha = "'" + anho + "-" + mes + "-01'";
    	String sql = "SELECT id FROM cte.ceo WHERE (fin_vigencia is null) ";
    	sql = sql + " OR (inicio_vigencia  < " + fecha + " and fin_vigencia > " + fecha + ")";
    	Query q = em.createNativeQuery(sql);
    	System.err.println("Consulta : " + sql);
    	List<BigInteger> ids = q.getResultList();
    	System.err.println("Consulta : " + ids.size());
    	ListaResponse<Ceo> res = new ListaResponse<Ceo>();
    	if (ids != null && ids.size() > 0) {
    		List<Long> listaId = new ArrayList<>();
    		for (BigInteger i : ids) {
    			listaId.add(i.longValue());
    		}
    		sql = "SELECT c FROM Ceo c WHERE c.id IN :ids";
    		q = em.createQuery(sql);
    		q.setParameter("ids", listaId);
    		List<Ceo> lista = q.getResultList();
        	res.setRows(lista);
        	res.setCount(lista.size());
    	}
    	
    	return res;
    }
    
    @Override
    public Ceo get(Long id) throws AppException {
    	
    	try {
            
        	Ceo ceo = (Ceo) em.find(getEntity(), id);
        	String[] codigos = ceo.getCodigo().split("\\.");
        	ceo.setDireccion("");
        	ceo.setCoordinacion("");
        	
        	System.err.println("get Ceo : " + codigos.length);
        	if (codigos.length == 3) {
        		String codigoDireccion = codigos[0] + "." + codigos[1];
        		System.err.println("get Ceo : " + codigoDireccion);
        		Ceo direccion = getCeoByCodigo(codigoDireccion);
        		if (direccion != null) {
        			ceo.setDireccion(direccion.getCodigo() + " - " + direccion.getDenominacion());
        		}
        		
        	} else if (codigos.length == 4) {
        		
        		String codigoDireccion = codigos[0] + "." + codigos[1];
        		System.err.println("get Ceo : " + codigoDireccion);
        		Ceo direccion = getCeoByCodigo(codigoDireccion);
        		if (direccion != null) {
        			ceo.setDireccion(direccion.getCodigo() + " - " + direccion.getDenominacion());
        		}
        		
        		String codigoCoordinacion = codigos[0] + "." + codigos[1] + "." + codigos[2];
        		System.err.println("get Ceo : " + codigoCoordinacion);
        		Ceo coordinacion = getCeoByCodigo(codigoCoordinacion);
        		if (coordinacion != null) {
        			ceo.setCoordinacion(coordinacion.getCodigo() + " - " + coordinacion.getDenominacion());
        		}
        	}
            return ceo;
            
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public Ceo getCeoByCodigo(String codigo) {
        String sql = "SELECT c FROM Ceo c WHERE c.codigo = :codigo";
        Query q = em.createQuery(sql);
        q.setParameter("codigo", codigo);
        List<Ceo> res = q.getResultList();
        if (res != null && res.size() > 0) {
            return res.get(0);
        }
        return null;
        
    }
    
    public boolean existeCeo(String codigo) {
        String sql = "SELECT c FROM Ceo c WHERE c.codigo = :codigo";
        Query q = em.createQuery(sql);
        q.setParameter("codigo", codigo);
        List<Ceo> res = q.getResultList();
        if (res != null && res.size() > 0) {
            return true;
        }
        return false;
        
    }
    
    public boolean existeCeo(String codigo, Long id) {
        String sql = "SELECT c FROM Ceo c WHERE c.codigo = :codigo AND c.id != :id";
        Query q = em.createQuery(sql);
        q.setParameter("codigo", codigo);
        q.setParameter("id", id);
        List<Ceo> res = q.getResultList();
        if (res != null && res.size() > 0) {
            return true;
        }
        return false;
    }
    
    public void cambiarEstado(Long id, Ceo ceo) throws AppException {
    	
    	System.err.println("cambiarEstado Ceo");
    	String codigo = ceo.getCodigo();
    	codigo = codigo + "%";
    	String sql = "SELECT c FROM Ceo c WHERE c.codigo like :codigoCeo";
    	Query q = em.createQuery(sql);
    	q.setParameter("codigoCeo", codigo);
    	List<Ceo> lista = q.getResultList();
    	if (lista != null && lista.size() > 0) {
	    	for (Ceo c : lista) {
	    		System.err.println("Eliminar Ceo : " + c.getCodigo());
	    		c.setActivo(ceo.getActivo());
	    		em.merge(c);
	    	}
    	}
    }
    
    @Override
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1;
        sb.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {
                //String param = key;
                if (key.equals("denominacionCUO")) {
                    key = "denominacion";
                    //param = key;
                    sb.append(" LOWER(c.cuo.");
                }
                else {
                    sb.append(" LOWER(c.");
                }
                    sb.append(key)
                        .append(") LIKE LOWER(:")
                        .append(key)
                        .append(")");
            } else {
                if(key.equals("nivel") || key.equals("subNivel") || key.equals("numero")){
                    //sb.append(" LOWER(c.cuo.");
                    sb.append("c.cuo.")
                        .append(key)
                        .append(" = :")
                        .append(key);
                } else {
                    sb.append(key)
                        .append(" = :")
                        .append(key);
                }
            }
            //se añade el 'AND' si hay más caracteres.
            if (token < tokens) {
                sb.append(" AND ");
            }
            token++;
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
                if("codigo".compareTo(key) == 0)
                    value += "%";
                else
                    value = "%" + value + "%";
            }
            if (key.equals("denominacionCUO")) {
                key = "denominacion";
            }
            q.setParameter(key, value);
        }
    }
    
    @Override
    public void buildOrder(StringBuilder sb, String orderBy, String orderDir) {
        if (orderBy != null && !orderBy.isEmpty()) {
            System.err.println("orderBy"+ orderBy);
            if(orderBy.equals("nivel") || orderBy.equals("subNivel") || orderBy.equals("numero")){
                sb.append(" ORDER BY c.cuo.");
            }else if(orderBy.equals("denominacionCUO")){
                orderBy="denominacion";
                sb.append(" ORDER BY c.cuo.");
            }
            else{
                sb.append(" ORDER BY c.");
            }
            sb.append(orderBy).append(" ").append(orderDir);
        }
    }
    
    public void asignarLegajos(long idCeo, Legajo legajo, HttpServletRequest httpRequest) {
        Ceo ceo = null;
        try {
            ceo = get(idCeo);
        } catch (AppException ex) {
            Logger.getLogger(CeoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (legajo.isAsignado()) {
            eliminarCeoLegajo(legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CeoLegajoPK pk = new CeoLegajoPK(idCeo, legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CeoLegajo entity = new CeoLegajo(pk);
            em.persist(entity);
            
            puestoTrabajoDAO.actualizarPuestoTrabajoCeo(ceo, legajo, Boolean.TRUE, httpRequest);
        } else {
            CeoLegajoPK pk = new CeoLegajoPK(idCeo, legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CeoLegajo entity = em.find(CeoLegajo.class, pk);
            if (entity != null) {
                em.remove(entity);
                
                puestoTrabajoDAO.actualizarPuestoTrabajoCeo(ceo, legajo, Boolean.FALSE, httpRequest);
            }
        }
    }
    
    public void eliminarCeoLegajo(Long cedulaIdentidad, int anho, int mes) {
        String sql = "SELECT c FROM CeoLegajo c WHERE c.pk.cedulaIdentidad = :cedulaIdentidad";
        sql += " AND c.pk.anho = :anho AND c.pk.mes = :mes";
        Query query = em.createQuery(sql);
        query.setParameter("cedulaIdentidad", cedulaIdentidad);
        query.setParameter("anho", anho);
        query.setParameter("mes", mes);
        List<CeoLegajo> res = query.getResultList();
        for (CeoLegajo c : res) {
            em.remove(c);
        }
    }
    
    public ListaResponse<CeoLegajosView> getLegajos(int inicio, int cantidad, String orderBy, 
            String orderDir, HashMap<String, Object> filtros, Long idCeo) {
        
        Map<Long, Long> idCi = getIdCi();
        
        if (filtros == null) {
            filtros = new HashMap();
        }
        
        int anho = (Integer) filtros.get("anho");
        int mes = (Integer) filtros.get("mes");
        List<Long> asignados = getLegajosAsignados(idCeo, anho, mes);
        ListaResponse<CeoLegajosView> res = legajoDAO.listarLegajos(inicio, cantidad, orderBy, orderDir, filtros);
        List<CeoLegajosView> lista = res.getRows();
        List<CeoLegajosView> nuevaLista = new ArrayList<>();
        for (CeoLegajosView item : lista) {
            if (asignados.contains(item.getId().getCedulaIdentidad())) {
                Long cedula = item.getId().getCedulaIdentidad();
                CeoLegajoPK legajoAsignado = getCeoLegajoAsignado(idCeo, cedula, anho, mes);
                if (legajoAsignado != null) {
                    item.setAsignado(true);
                } else {
                    item.setAsignado(false);
                }
            } else {
                item.setAsignado(false);
            }
            
            Ceo ceo = null;/*
            try {
                Long idCeoAsociado = getCeoAsignado(item.getCedulaIdentidad());
                if(idCeoAsociado != 0){
                    ceo = get(idCeoAsociado);
                    item.setCeoActual(ceo);
                }
            } catch (AppException ex) {
                Logger.getLogger(CeoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
            if (idCi.containsKey(item.getId().getCedulaIdentidad())) {
                Long idCeoAsignado = idCi.get(item.getId().getCedulaIdentidad());
                try {
                    ceo = get(idCeoAsignado);
                } catch (AppException ex) {
                    Logger.getLogger(CeoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            
            nuevaLista.add(item);
        }
        res.setRows(nuevaLista);
        return res;
    }
    
    private Map<Long, Long> getIdCi(){
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk.idCeo, c.pk.cedulaIdentidad FROM CeoLegajo c ");
        Query q = em.createQuery(query.toString());
        
        List<Object[]> resultList = q.getResultList();
        Map<Long, Long> resultMap = new HashMap<Long, Long>(resultList.size());
        for (Object[] result : resultList)
          resultMap.put((Long)result[0], (Long)result[1]);

        return resultMap;
    }
    
    public Long getCeoAsignado(Long cedulaIdentidad, int anho, int mes){
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk.idCeo FROM CeoLegajo c ");
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
    
    private List<Long> getLegajosAsignados(Long idCeo, int anho, int mes) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk.cedulaIdentidad FROM CeoLegajo c ");
        query.append("WHERE c.pk.idCeo = :idCeo ");
        query.append("AND c.pk.anho = :anho AND c.pk.mes = :mes");
        Query q = em.createQuery(query.toString());
        q.setParameter("idCeo", idCeo);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<Long> list = q.getResultList();
        return list;
    }
    
    private CeoLegajoPK getCeoLegajoAsignado(Long idCeo, Long cedulaIdentidad, int anho, int mes) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk FROM CeoLegajo c ");
        query.append("WHERE c.pk.idCeo = :idCeo AND c.pk.cedulaIdentidad = :cedulaIdentidad");
        query.append(" AND c.pk.anho = :anho AND c.pk.mes = :mes");
        Query q = em.createQuery(query.toString());
        q.setParameter("idCeo", idCeo);
        q.setParameter("cedulaIdentidad", cedulaIdentidad);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<CeoLegajoPK> list = q.getResultList();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    
}

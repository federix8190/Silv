package py.com.ceamso.administracion.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import py.com.ceamso.administracion.model.CptFLegajo;
import py.com.ceamso.administracion.model.CptFLegajoPK;
import py.com.ceamso.administracion.model.Categoria;
import py.com.ceamso.administracion.model.CptE;
import py.com.ceamso.administracion.model.CptECategoria;
import py.com.ceamso.administracion.model.CptECategoriaPK;
import py.com.ceamso.administracion.model.CptF;
import py.com.ceamso.administracion.model.CptFMecip;
import py.com.ceamso.administracion.model.CptFMecipPK;
import py.com.ceamso.administracion.model.CptLegajoPK;
import py.com.ceamso.administracion.model.Mecip;
import py.com.ceamso.administracion.view.CptEFLegajosView;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.seguridad.service.SessionService;
import py.com.ceamso.utils.AppException;

/**
 * @author mbaez
 */
public class CptFDAO extends WritableDAO<CptF> {

    @Inject
    private CptFLegajosDAO legajoDAO;

    @Inject
    private CptDAO cptDAO;

    @Inject
    private PuestoTrabajoDAO puestoTrabajoDAO;

    @Inject
    private SessionService session;

    public List<BigInteger> lista;
    /**
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return CptF.class;
    }

    public Usuario getCurrentUser() {
        return session.getCurrentUser();
    }

    @Override
    public void insert(CptF entity) throws AppException {
    	List<Mecip> listaMecip = entity.getMecip();    	
    	entity.setMecip(null);
        if(entity.getCpt() != null ){        
            em.persist(entity);
            em.flush();
        }else{
            throw new AppException(0, "Debe seleccionar un CPT General" );
        }
        
        /* TODO no hace falta, porque gracias al mapeo de JPA se guarda en la tabla de cpt_ef_mecip */
        //List<Mecip> listaMecip = entity.getMecip();
        if (listaMecip.size() > 0) {
            Usuario user = getCurrentUser();
            System.err.println("Mecip : " + listaMecip.size());
            for (Mecip m : listaMecip) {
            	System.err.println("Mecip : " + m.getId() + " - " + m.getCodigo());
                CptFMecipPK pk = new CptFMecipPK(entity.getId(), m.getId());
                CptFMecip cptFMecip = new CptFMecip(pk);
                cptFMecip.setFechaCreacion(new Date());
                cptFMecip.setUsuarioCreacion(user.getId());
                cptFMecip.setIpCreacion(entity.getIpCreacion());
                cptFMecip.setActivo(true);
                em.persist(cptFMecip);
            }
        }
    }

    @Override
    public CptF get(Long id) throws AppException {
        CptF obj = super.get(id);
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT c ");
        sb.append(" FROM Mecip c");
        sb.append(" WHERE EXISTS (");
        sb.append(" SELECT fmecip.pk.idMecip ");
        sb.append(" FROM CptFMecip fmecip ");
        sb.append(" WHERE c.id = fmecip.pk.idMecip ");
        sb.append(" AND fmecip.pk.idCptF = :idCptF)");

        Query query = em.createQuery(sb.toString(), Mecip.class);
        query.setParameter("idCptF", id);
        List<Mecip> res = query.getResultList();
        obj.setMecip(res);
        return obj;
    }
    
    @Override
    public void modify(Long id, CptF dto) throws AppException {
        
    	List<Mecip> listaMecip = dto.getMecip();    	
    	dto.setMecip(null);
    	super.modify(id, dto);
        em.flush();

        eliminarCptFMecip(dto.getId());

        if (listaMecip.size() > 0) {
            Usuario user = getCurrentUser();
            System.err.println("Mecip : " + listaMecip.size());
            for (Mecip m : listaMecip) {
            	System.err.println("Mecip : " + m.getId() + " - " + m.getCodigo());
                CptFMecipPK pk = new CptFMecipPK(dto.getId(), m.getId());
                CptFMecip cptFMecip = new CptFMecip(pk);
                cptFMecip.setFechaCreacion(new Date());
                cptFMecip.setUsuarioCreacion(user.getId());
                cptFMecip.setIpCreacion(dto.getIpCreacion());
                cptFMecip.setActivo(true);
                em.persist(cptFMecip);
            }
        }
    }

    /**
     * @param id
     * @throws AppException
     */
    public void delete(Long id) throws AppException {
        CptF entity = (CptF) em.find(getEntity(), id);
        //eliminarCptFMecip(entity.getId());
        if (entity == null) {
            throw new AppException(404, "Not Found");
        }
        em.remove(entity);

    }
    
    public Long getCptAsignado(Long nro){
        StringBuilder query = new StringBuilder();
        query.append("SELECT count(c) FROM CptF c ");
        query.append("WHERE c.cpt.id = :nro");
        Query q = em.createQuery(query.toString());
        q.setParameter("nro", nro);
        List<Long> list = q.getResultList();
        
        Long res = new Long(0);
        if(list.size() > 0)
            res = list.get(0);
        
        return res;
    }

    public void asignarLegajos(long idCptF, Legajo legajo, HttpServletRequest httpRequest) {
        CptF cptF = null;
        try {
            cptF = get(idCptF);
        } catch (AppException ex) {
            Logger.getLogger(CptEDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (legajo.isAsignado()) {
            eliminarCptFLegajo(legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CptFLegajoPK pk = new CptFLegajoPK(idCptF, legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CptFLegajo entity = new CptFLegajo(pk);
            em.persist(entity);
            
            puestoTrabajoDAO.actualizarPuestoTrabajoCptF(cptF, legajo, Boolean.TRUE, httpRequest);
        } else {
            CptFLegajoPK pk = new CptFLegajoPK(idCptF, legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CptFLegajo entity = em.find(CptFLegajo.class, pk);
            if (entity != null) {
                em.remove(entity);
                puestoTrabajoDAO.actualizarPuestoTrabajoCptF(cptF, legajo, Boolean.FALSE, httpRequest);
            }
        }
        if (cptF != null) {
            cptDAO.asignarCptLegajo(cptF.getCpt().getId(), legajo, httpRequest);
            //cptDAO.eliminarCptLegajo(legajo.getCedulaIdentidad());
        }
    }

    private void eliminarCptFMecip(Long idCptF) {
        String sql = "SELECT c FROM CptFMecip c WHERE c.pk.idCptF = :idCptF";
        Query query = em.createQuery(sql);
        query.setParameter("idCptF", idCptF);
        List<CptFMecip> res = query.getResultList();
        for (CptFMecip c : res) {
            em.remove(c);
        }
    }

    public void eliminarCptFLegajo(Long cedulaIdentidad, int anho, int mes) {
        String sql = "SELECT c FROM CptFLegajo c WHERE c.pk.cedulaIdentidad = :cedulaIdentidad";
        sql += " AND c.pk.anho = :anho AND c.pk.mes = :mes";
        Query query = em.createQuery(sql);
        query.setParameter("cedulaIdentidad", cedulaIdentidad);
        query.setParameter("anho", anho);
        query.setParameter("mes", mes);
        List<CptFLegajo> res = query.getResultList();
        for (CptFLegajo c : res) {
            em.remove(c);
        }
    }

    public void desasignarCptFLegajo(Long cedulaIdentidad, int anho, int mes) {
        eliminarCptFLegajo(cedulaIdentidad, anho, mes);
    }

    public ListaResponse<CptEFLegajosView> getLegajos(int inicio, int cantidad, String orderBy,
                                                      String odrerDir, HashMap<String, Object> filtros, 
                                                      Long idCptF) {

        Map<Long, Long> idCi = getIdCi();

        int anho = (Integer) filtros.get("anho");
        int mes = (Integer) filtros.get("mes");
        List<Long> asignados = getLegajosAsignados(idCptF, anho, mes);
        ListaResponse<CptEFLegajosView> res = legajoDAO.listarLegajos(inicio, cantidad, orderBy, odrerDir, filtros);
        List<CptEFLegajosView> lista = res.getRows();
        List<CptEFLegajosView> nuevaLista = new ArrayList<>();
        for (CptEFLegajosView item : lista) {
            if (asignados.contains(item.getId().getCedulaIdentidad())) {
                Long cedula = item.getId().getCedulaIdentidad();
                CptFLegajoPK legajoAsignado = getCptFLegajoAsignado(idCptF, cedula, anho, mes);
                if (legajoAsignado != null) {
                    item.setAsignado(true);
                } else {
                    item.setAsignado(false);
                }
            } else {
                item.setAsignado(false);
            }
            CptF ceo = null;/*
            try {
                Long idCeoAsociado = getCptAsignado(item.getCedulaIdentidad());
                if(idCeoAsociado != 0){
                    ceo = get(idCeoAsociado);
                    item.setCptActual(ceo);
                }
            } catch (AppException ex) {
                Logger.getLogger(CeoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            if (idCi.containsKey(item.getId().getCedulaIdentidad())) {
                Long idCeoAsignado = idCi.get(item.getId().getCedulaIdentidad());
                try {
                    ceo = get(idCeoAsignado);
                    item.setCptActual(ceo);
                } catch (AppException ex) {
                    Logger.getLogger(CeoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            nuevaLista.add(item);
        }
        res.setRows(nuevaLista);
        return res;
    }
    
    private CptFLegajoPK getCptFLegajoAsignado(Long idCptF, Long cedulaIdentidad, int anho, int mes) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk FROM CptFLegajo c ");
        query.append("WHERE c.pk.idCptF = :idCptF AND c.pk.cedulaIdentidad = :cedulaIdentidad");
        query.append(" AND c.pk.anho = :anho AND c.pk.mes = :mes");
        Query q = em.createQuery(query.toString());
        q.setParameter("idCptF", idCptF);
        q.setParameter("cedulaIdentidad", cedulaIdentidad);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<CptFLegajoPK> list = q.getResultList();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    private Map<Long, Long> getIdCi() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk.idCptF, c.pk.cedulaIdentidad FROM CptFLegajo c ");
        Query q = em.createQuery(query.toString());

        List<Object[]> resultList = q.getResultList();
        Map<Long, Long> resultMap = new HashMap<Long, Long>(resultList.size());
        for (Object[] result : resultList)
            resultMap.put((Long) result[0], (Long) result[1]);
        System.err.println(resultList.size());
        return resultMap;
    }

    public Long getCptFAsignado(Long cedulaIdentidad, int anho, int mes) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk.idCptF FROM CptFLegajo c ");
        query.append("WHERE c.pk.cedulaIdentidad = :cedulaIdentidad");
        query.append(" AND c.pk.anho = :anho AND c.pk.mes = :mes");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaIdentidad", cedulaIdentidad);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<Long> list = q.getResultList();

        Long res = new Long(0);
        if (list.size() > 0)
            res = list.get(0);

        return res;
    }

    private List<Long> getLegajosAsignados(Long idCptF, int anho, int mes) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk.cedulaIdentidad FROM CptFLegajo c ");
        query.append("WHERE c.pk.idCptF = :idCptF ");
        query.append("AND c.pk.anho = :anho AND c.pk.mes = :mes");
        Query q = em.createQuery(query.toString());
        q.setParameter("idCptF", idCptF);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<Long> list = q.getResultList();
        return list;
    }
    
    public ListaResponse<CptF> listar(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros) {
    	
    	StringBuilder query = new StringBuilder();
        query.append("SELECT c FROM CptF c ");
        if (orderBy != null && orderBy.equals("mecip")) {
        	query.append("LEFT JOIN c.mecip m ");
        }
    	
        //String sql = "SELECT c FROM CptF c JOIN c.mecip m ";
        
        if (cantidad == -1) {
        	if (filtros == null) {
        		filtros = new HashMap<String, Object>();
        	}
        	if (!filtros.containsKey("activo")) {
        		filtros.put("activo", true);
        	}
        }
        
    	buildWhere(query, filtros);
        buildOrder(query, orderBy, odrerDir);
    	Query q = em.createQuery(query.toString());
    	System.err.println("listar CPTF : " + query.toString());
        setParametrers(q, filtros);
        
        q.setFirstResult(inicio);
        if (cantidad != -1) {            
            q.setMaxResults(cantidad);
        }
        
    	List<CptF> list = q.getResultList();
        int total = count(filtros, orderBy);
        //se construye la respuesta 
        ListaResponse<CptF> res = new ListaResponse<CptF>();
        res.setRows(list);
        res.setCount(total);
        return res;
    }
    
    private int count(HashMap<String, Object> filtros, String orderBy) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(c) FROM CptF c ");
        if (orderBy != null && orderBy.equals("mecip")) {
        	query.append("LEFT JOIN c.mecip m ");
        }
        buildWhere(query, filtros);
        Query q = em.createQuery(query.toString());
        setParametrers(q, filtros);
        return ((Long) q.getSingleResult()).intValue();
    }

    
    @Override
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1, start = 0, end = 0;
        ;
        int flag = 0;
        StringBuilder where = new StringBuilder();
        where.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {
                //String param = key;
                if (key.equals("denominacionCpt")) {
                    key = "denominacion";
                    //param = key;
                    where.append(" LOWER(c.cpt.");
                    flag = 1;
                } else if (key.equals("mecip")) {
                    String query = "select distinct c.id " + "from cte.cpt_ef c " +
                    "left join cte.cpt_ef_mecip cm on c.id = cm.id_cpt_ef " +
                    "left join cte.mecip m on m.id = cm.id_mecip " +
                    "where ";
                    
                    Object value = filtros.get("mecip");
                    
                    String values = (String) value;
                    String[] codigos = values.split(",");
                                        
                    for(int j=0; j < codigos.length; j++){
                        query += "m.codigo like :codigo" + j + " ";
                        
                        if(j+1 != codigos.length)
                            query += "OR ";
                    }
                    Query q = em.createNativeQuery(query.toString());
                    
                    for(int j=0;j < codigos.length; j++){
                        value = "%" + codigos[j] + "%";
                        q.setParameter("codigo"+j, value);//codigos[j]);
                    }
                                        
                    lista = q.getResultList();
                    
                    where.append(" c.id in :lista");
                    flag = 0;
                }else if (key.equals("titularUnidadCpt")) {
                    key = "tituloUnidad";
                    String param = filtros.get("titularUnidadCpt").toString();
                    System.out.println("param= " + param);
                    if (param.compareTo("TODOS") != 0) {
                        where.append(" LOWER(c.cpt.");
                        where.append(key)
                                .append(" = :")
                                .append(key);
                        flag = 1;
                    }
                } else if (key.equals("ambito")) {
                    key = "nombreAmbito";
                    //param = key;
                    where.append(" LOWER(c.ambito.");
                    flag = 1;
                } else if (key.equals("orientacionFunc")) {
                    key = "nombreOrientacion";
                    //param = key;
                    where.append(" LOWER(c.orientacionFunc.");
                    flag = 1;
                } else {
                    where.append(" LOWER(c.");
                    flag = 1;
                }
                if (flag == 1) {
                    where.append(key)
                            .append(") LIKE LOWER(:")
                            .append(key)
                            .append(")");
                    flag = 0;
                }

            } else {
                if (key.equals("titularUnidadCpt")) {
                    key = "tituloUnidad";
                    //param = key;
                    where.append(" c.cpt.");
                    where.append(key).append(" = :").append(key);
                } else if (key.equals("nivelCPT")) {
                    key = "nivel";
                    //param = key;
                    where.append(" c.cpt.");
                    where.append(key).append(" = :").append(key);
                } else if (key.equals("subNivelCpt")) {
                    key = "subNivel";
                    //param = key;
                    where.append(" c.cpt.");
                    where.append(key).append(" = :").append(key);
                } else if (key.equals("numeroCpt")) {
                    key = "numeroGasto";
                    //param = key;
                    where.append(" c.cpt.");
                    where.append(key).append(" = :").append(key);
                }else if (key.equals("idCpt")) {
                    //param = key;
                    where.append(" c.cpt.id = :idCpt");
                } else {
                	where.append(key).append(" = :").append(key);
                }
                
            }
            //se añade el 'AND' si hay más caracteres.
            end = where.length();
            start = end - 4;
            if (token < tokens && where.toString().compareTo(" WHERE ") != 0 
            		&& !where.substring(start, end).equals("AND ")) {
                where.append(" AND ");
                System.err.println("where: " + where);
            }
            token++;
        }
        if (where.toString().compareTo(" WHERE ") != 0) {
            end = where.length();
            start = end - 4;

            if (where.substring(start, end).equals("AND ")) {
                where.delete(start, end);
                System.out.println("Resultado: " + where);
            }
            sb.append(where);
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
            if (key.equals("titularUnidadCpt")) {
                key = "tituloUnidad";
                String param = filtros.get("titularUnidadCpt").toString();
                if (param.compareTo("TODOS") != 0) {
                    System.err.println("Parametros : " + key + " - " + value);
                    q.setParameter(key, value);
                }
            } else {
                if (key.equals("denominacionCpt")) {
                    key = "denominacion";
                } else if (key.equals("nivelCPT")) {
                    key = "nivel";
                } else if (key.equals("subNivelCpt")) {
                    key = "subNivel";
                } else if (key.equals("numeroCpt")) {
                    key = "numeroGasto";
                } else if (key.equals("codigoMecip")) {
                    key = "codigo";
                } else if (key.equals("ambito")) {
                    key = "nombreAmbito";
                } else if (key.equals("orientacionFunc")) {
                    key = "nombreOrientacion";
                } else if (key.equals("mecip")) {
                    key = "lista";
                    List<Long>list = new ArrayList<Long>();
                    for(BigInteger l : lista)
                        list.add(l.longValue());
                    
                    value = list;
                }
                q.setParameter(key, value);
            }
        }
    }

    @Override
    public void buildOrder(StringBuilder sb, String orderBy, String orderDir) {
        if (orderBy != null && !orderBy.isEmpty()) {
            System.out.println("orderBy= "+ orderBy+" orderDir= "+orderDir);
            if (orderBy.equals("id")) {
                sb.append(" ORDER BY ");
                sb.append("c.cpt.nivel ").append("DESC");
                sb.append(" ,c.cpt.subNivel ").append("ASC");
                sb.append(" ,c.cpt.numeroGasto ").append("ASC");
            } else if(orderBy.equals("nivelCpt")) {
                sb.append(" ORDER BY ");
                sb.append("c.cpt.nivel ").append(orderDir);
                sb.append(" ,c.cpt.subNivel ").append(orderDir);
                sb.append(" ,c.cpt.numeroGasto ").append(orderDir);
            } else if(orderBy.equals("subNivelCpt")) {
                sb.append(" ORDER BY ");
                sb.append(" c.cpt.subNivel ").append(orderDir);
            } else if(orderBy.equals("numeroCpt")) {
                sb.append(" ORDER BY ");
                sb.append(" c.cpt.numeroGasto ").append(orderDir);
            } else if(orderBy.equals("mecip")) {
                sb.append(" ORDER BY ");
                //sb.append(" c.mecip.codigo ").append(orderDir);
                sb.append(" m.codigo ").append(orderDir);
            } else {
                if (orderBy.equals("titularUnidadCpt")) {
                    orderBy = "tituloUnidad";
                    sb.append(" ORDER BY c.cpt.");
                } else if (orderBy.equals("codigoMecip")) {
                    orderBy = "codigo";
                    sb.append(" ORDER BY c.mecip.");
                } else {
                    sb.append(" ORDER BY c.");
                }
                sb.append(orderBy).append(" ").append(orderDir);
            }
        }
    }
    
    /*public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1;
        sb.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {
                 if (key.equals("denominacionMECIP")) {
                    key = "denominacion";
                  //  param = key;
                    sb.append(" LOWER(c.mecip.");
                } else if (key.equals("denominacionCpt")) {
                    key = "denominacion";
                  //  param = "denominacionCpt";
                    sb.append(" LOWER(c.cpt.");
                } else if (key.equals("codigoMecip")) {
                    key = "codigo";
                   // param = "denominacionCpt";
                    sb.append(" LOWER(c.mecip.");
                } else if (key.equals("subNivelCpt")) {
                    key = "subNivel";
                   // param = key;
                    sb.append(" LOWER(c.cpt.");
                } else if (key.equals("nroCpt")) {
                    key = "numeroGasto";
                   // param = key;
                    sb.append(" LOWER(c.cpt.");
                } else if (key.equals("titularUnidadCpt")) {
                    key = "tituloUnidad";
                   // param = key;
                    sb.append(" LOWER(c.cpt.");
                } else {
                    sb.append(" LOWER(c.");
                }
                sb.append(key)
                    .append(") LIKE LOWER(:")
                    .append(key)
                    .append(")");
            } else {
                sb.append(key)
                        .append(" = :")
                        .append(key);
            }
            //se añade el 'AND' si hay más caracteres.
            if (token < tokens) {
                sb.append(" AND ");
            }
            token++;
        }
    }
    
    
    /*public List<Long> getLegajosAsignados(Long idCptF) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.pk.cedulaIdentidad FROM CptFLegajo c ");
        //sql.append("WHERE c.id.idCptF = :idCptF");
        
        Query q = em.createQuery(sql.toString());
        //q.setParameter("idCptF", idCptF);
        return q.getResultList();
    }
    
    public List<CptFLegajo> getLegajosCpt(Long idCptF) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c FROM CptFLegajo c ");
        sql.append("WHERE c.pk.idCptF = :idCptF");
        
        Query q = em.createQuery(sql.toString());
        q.setParameter("idCptF", idCptF);
        return q.getResultList();
    }
    
    public void asignarLegajos(long idCptF, Legajo legajo) {
        
        if (legajo.isAsignado()) {
            CptFLegajoPK pk = new CptFLegajoPK(idCptF, legajo.getCedulaIdentidad());
            CptFLegajo entity = new CptFLegajo(pk);
            em.persist(entity);
        } else {
            CptFLegajoPK pk = new CptFLegajoPK(idCptF, legajo.getCedulaIdentidad());
            CptFLegajo entity = em.find(CptFLegajo.class, pk);
            if (entity != null) {
                em.remove(entity);
            }
        }
    }
    
    public ListaResponse<Legajo> getLegajosAsignados(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros, Long idCptF) {
                
        List<Legajo> legajos = getLegajos(inicio, cantidad, orderBy, odrerDir, filtros, idCptF, true);
        int total = count(filtros, idCptF, true);
        ListaResponse<Legajo> res = new ListaResponse<>();
        res.setRows(legajos);
        res.setCount(total);
        return res;
    }
    
    public ListaResponse<Legajo> getLegajos(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros, Long idCptF) {
                
        List<Legajo> asignados = getLegajos(0, -1, null, null, null, idCptF, true);
        List<Long> listaAsignados = new ArrayList<>();
        for (Legajo legajo : asignados) {
            listaAsignados.add(legajo.getCedulaIdentidad());
        }
        
        List<Legajo> legajos = getLegajos(inicio, cantidad, orderBy, odrerDir, filtros, idCptF, false);
        int total = count(filtros, idCptF, false);
        List<Legajo> lista = new ArrayList<>();
        for (Legajo legajo : legajos) {
            if (listaAsignados.contains(legajo.getCedulaIdentidad())) {
                legajo.setAsignado(true);
            } else {
                legajo.setAsignado(false);
            }
            lista.add(legajo);
        }
        
        //se construye la respuesta 
        ListaResponse<Legajo> res = new ListaResponse<>();
        res.setRows(lista);
        res.setCount(total);
        return res;

    }
    
    private List<Legajo> getLegajos(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros, Long idCptF, 
            boolean soloAsignados) {
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT new Legajo(c, l, cargo.descripcion, cargo.nivel) ");
        query.append("FROM Legajo c, LegajoCargo l, Cargo cargo ");
        buildWhereLegajo(query, filtros, soloAsignados);
        buildOrderLegajo(query, orderBy, odrerDir);
        Query q = em.createQuery(query.toString());
        System.err.println(query.toString());
        setParametrers(q, filtros, idCptF);
        q.setFirstResult(inicio);
        if (cantidad != -1) {
            q.setMaxResults(cantidad);
        }
        return q.getResultList();
    }
    
    private void buildOrderLegajo(StringBuilder sb, String orderBy, String orderDir) {
        if (orderBy != null && !orderBy.isEmpty()) {
            if (orderBy.equals("cargo")) {
                orderBy = "cargo.descripcion";
            } else if (orderBy.equals("vinculacionFuncionario")) {
                orderBy = "l.vinculacionFuncionario";
            } else if (orderBy.equals("funcionReal")) {
                orderBy = "l.funcionReal";
            } else {
                orderBy = "c." + orderBy;
            }
            sb.append(" ORDER BY ").append(orderBy).append(" ").append(orderDir);
        }
    }
    
    private void buildWhereLegajo(StringBuilder sb, HashMap<String, Object> filtros, boolean soloAsignados) {
               
        StringBuilder filtro = new StringBuilder();
        if (filtros == null || filtros.isEmpty()) {
            filtro.append("");
        } else {
            int tokens = filtros.keySet().size();
            int token = 1;
            filtro.append(" AND ");
            for (String key : filtros.keySet()) {
                if (key.equals("vinculacionFuncionario")) {
                    filtro.append(" LOWER(l.")
                            .append(key)
                            .append(") = LOWER(:")
                            .append(key)
                            .append(")");
                } else {
                    if (filtros.get(key) instanceof String) {
                        filtro.append(" LOWER(c.")
                                .append(key)
                                .append(") LIKE LOWER(:")
                                .append(key)
                                .append(")");
                    } else {
                        filtro.append("c.")
                            .append(key)
                            .append(" = :")
                            .append(key);
                    }
                }
                //se añade el 'AND' si hay más caracteres.
                if (token < tokens) {
                    filtro.append(" AND ");
                }
                token++;
            }
        }
        
        if (soloAsignados) {
            sb.append("WHERE c.cedulaIdentidad = l.pk.cedulaIdentidad ");
            sb.append("AND l.pk.idCargo = cargo.id ");
            sb.append("AND c.cedulaIdentidad IN ");
            sb.append("(SELECT cl.id.cedulaIdentidad FROM CptFLegajo cl ");
            sb.append("WHERE cl.id.idCptF = :idCptF)");
            sb.append(filtro);
        } else {
            sb.append("WHERE c.cedulaIdentidad = l.pk.cedulaIdentidad ");
            sb.append("AND l.pk.idCargo = cargo.id ");
            sb.append("AND ((c.cedulaIdentidad IN ");
            sb.append("(SELECT cl.id.cedulaIdentidad FROM CptFLegajo cl ");
            sb.append("WHERE cl.id.idCptF = :idCptF)");
            sb.append(filtro);
            sb.append(")");
            sb.append("OR (c.cedulaIdentidad NOT IN (SELECT cl.id.cedulaIdentidad FROM CptFLegajo cl)");
            sb.append(filtro);
            sb.append("))");
        }
    }
    
    private void setParametrers(Query q, HashMap<String, Object> filtros, Long idCptF) {
        q.setParameter("idCptF", idCptF);
        if (filtros == null) {
            return;
        }
        for (String key : filtros.keySet()) {
            Object value = filtros.get(key);
            if (filtros.get(key) instanceof String && !key.equals("vinculacionFuncionario")) {
                value = "%" + value + "%";
            }
            q.setParameter(key, value);
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
            if (key.equals("denominacionMECIP")) {
                key = "denominacion";
            }   
            if (key.equals("subNivelCpt")) {
                key = "subNivel";
            }
            if (key.equals("denominacionCpt")) {
                key = "denominacionCpt";
            }
            if (key.equals("nroCpt")) {
                key = "numeroGasto";
            } 
            if (key.equals("codigoMecip")) {
                key = "codigo";
            }
            if (key.equals("titularUnidadCpt")) {
                key = "tituloUnidad";
            } 
            q.setParameter(key, value);
        }
    }

    private int count(HashMap<String, Object> filtros, Long idCptF, boolean soloAsignados) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(c) FROM Legajo c, LegajoCargo l, Cargo cargo ");
        buildWhereLegajo(query, filtros, soloAsignados);
        Query q = em.createQuery(query.toString());
        setParametrers(q, filtros, idCptF);
        return ((Long) q.getSingleResult()).intValue();
    }*/

}

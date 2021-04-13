package py.com.ceamso.administracion.dao;

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
import py.com.ceamso.administracion.model.Categoria;
import py.com.ceamso.administracion.model.CptE;
import py.com.ceamso.administracion.model.CptECategoria;
import py.com.ceamso.administracion.model.CptECategoriaPK;
import py.com.ceamso.administracion.model.CptELegajo;
import py.com.ceamso.administracion.model.CptELegajoPK;
import py.com.ceamso.administracion.model.CptF;
import py.com.ceamso.administracion.model.CptFLegajoPK;
import py.com.ceamso.administracion.model.CptFMecip;
import py.com.ceamso.administracion.view.CptEELegajosView;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.seguridad.service.SessionService;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author mbaez
 */
public class CptEDAO extends WritableDAO<CptE> {
    
    @Inject
    private CptELegajosDAO legajoDAO;
    
    @Inject
    private CptDAO cptDAO;
    
    @Inject
    private PuestoTrabajoDAO puestoTrabajoDAO;
    
    @Inject
    private SessionService session;
    /**
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return CptE.class;
    }
    
    public Usuario getCurrentUser() {
        return session.getCurrentUser();
    }
    
    @Override
    public void insert(CptE entity) throws AppException {
        if(entity.getCpt() != null ){        
            em.persist(entity);
            em.flush();
        }else{
            throw new AppException(0, "Debe seleccionar un CPT General" );
        }
        
        if(entity.getCategoria().size() > 0){
            Usuario user = getCurrentUser();
            
            for(Categoria c : entity.getCategoria()){
                CptECategoriaPK pk = new CptECategoriaPK(entity.getId(), c.getNombre());
                CptECategoria cptECategoria = new CptECategoria(pk);
                cptECategoria.setFechaCreacion(new Date());
                cptECategoria.setUsuarioCreacion(user.getId());
                cptECategoria.setIpCreacion(entity.getIpCreacion());
                cptECategoria.setActivo(true);
                em.persist(cptECategoria);
            } 
        }
    }

    @Override
    public CptE get(Long id) throws AppException {
        CptE obj = super.get(id);
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT c ");
        sb.append(" FROM Categoria c");
        sb.append(" WHERE EXISTS (");
        sb.append(" SELECT ecate.pk.idCategoria ");
        sb.append(" FROM CptECategoria ecate ");
        sb.append(" WHERE c.nombre = ecate.pk.idCategoria ");
        sb.append(" AND ecate.pk.idCptE = :idCptE)");

        Query query = em.createQuery(sb.toString(), Categoria.class);
        query.setParameter("idCptE", id);
        List<Categoria> res = query.getResultList();
        obj.setCategoria(res);
        return obj;
    }

    @Override
    public void modify(Long id, CptE dto) throws AppException {
        super.modify(id, dto);
        em.flush();

        eliminarCptECategoria(dto.getId());

        if (dto.getCategoria().size() > 0) {
            Usuario user = getCurrentUser();

            for (Categoria m : dto.getCategoria()) {
                CptECategoriaPK pk = new CptECategoriaPK(dto.getId(), m.getNombre());
                CptECategoria cptECategoria = new CptECategoria(pk);
                cptECategoria.setFechaCreacion(new Date());
                cptECategoria.setUsuarioCreacion(user.getId());
                cptECategoria.setIpCreacion(dto.getIpCreacion());
                cptECategoria.setActivo(true);
                em.persist(cptECategoria);
            }
        }
    }
    
    /**
     *
     * @param id
     * @throws AppException
     */
    public void delete(Long id) throws AppException {
        CptE entity = (CptE) em.find(getEntity(), id);
        eliminarCptECategoria(entity.getId());
        if (entity == null) {
            throw new AppException(404, "Not Found");
        }
        em.remove(entity);
        
    }
    
    private void eliminarCptECategoria(Long idCptE) {
        String sql = "SELECT c FROM CptECategoria c WHERE c.pk.idCptE = :idCptE";
        Query query = em.createQuery(sql);
        query.setParameter("idCptE", idCptE);
        List<CptECategoria> res = query.getResultList();
        for (CptECategoria c : res) {
            em.remove(c);
        }
    }
    
    public void asignarLegajos(long idCptE, Legajo legajo, HttpServletRequest httpRequest) {
        CptE cptE = null;
        try {
            cptE = get(idCptE);
        } catch (AppException ex) {
            Logger.getLogger(CptEDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        if (legajo.isAsignado()) {
            eliminarCptELegajo(legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CptELegajoPK pk = new CptELegajoPK(idCptE, legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CptELegajo entity = new CptELegajo(pk);
            em.persist(entity); 
            
            puestoTrabajoDAO.actualizarPuestoTrabajoCptE(cptE, legajo, Boolean.TRUE, httpRequest);
        } else {
            CptELegajoPK pk = new CptELegajoPK(idCptE, legajo.getCedulaIdentidad(), legajo.getAnho(), legajo.getMes());
            CptELegajo entity = em.find(CptELegajo.class, pk);
            if (entity != null) {
                em.remove(entity);
                
                puestoTrabajoDAO.actualizarPuestoTrabajoCptE(cptE, legajo, Boolean.FALSE, httpRequest);
            }            
        }
                
        if (cptE != null && cptE.getCpt() != null) {
            cptDAO.asignarCptLegajo(cptE.getCpt().getId(), legajo, httpRequest);
            //cptDAO.eliminarCptLegajo(legajo.getCedulaIdentidad());
        }
            
    }
    
    public void eliminarCptELegajo(Long cedulaIdentidad, int anho, int mes) {
        String sql = "SELECT c FROM CptELegajo c WHERE c.pk.cedulaIdentidad = :cedulaIdentidad";
        sql += " AND c.pk.anho = :anho AND c.pk.mes = :mes";
        Query query = em.createQuery(sql);
        query.setParameter("cedulaIdentidad", cedulaIdentidad);
        query.setParameter("anho", anho);
        query.setParameter("mes", mes);
        List<CptELegajo> res = query.getResultList();
        for (CptELegajo c : res) {
            em.remove(c);
        }
    }
    
    public Long getCptEAsignado(Long cedulaIdentidad, int anho, int mes) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk.idCptE FROM CptELegajo c ");
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
    
    public void desasignarCptELegajo(Long cedulaIdentidad, int anho, int mes){
        eliminarCptELegajo(cedulaIdentidad, anho, mes);
    }
    
    public ListaResponse<CptEELegajosView> getLegajos(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros, Long idCptE) {
        
        Map<Long, Long> idCi = getIdCi();

        int anho = (Integer) filtros.get("anho");
        int mes = (Integer) filtros.get("mes");
        List<Long> asignados = getLegajosAsignados(idCptE, anho, mes);
        System.err.println("asignados : " + asignados.size());
        ListaResponse<CptEELegajosView> res = legajoDAO.listarLegajos(inicio, cantidad, orderBy, odrerDir, filtros);
        List<CptEELegajosView> lista = res.getRows();
        System.err.println("Legajos : " + lista.size());
        
        List<CptEELegajosView> nuevaLista = new ArrayList<>();
        for (CptEELegajosView item : lista) {
            if (asignados.contains(item.getId().getCedulaIdentidad())) {
                Long cedula = item.getId().getCedulaIdentidad();
                CptELegajoPK legajoAsignado = getCptFLegajoAsignado(idCptE, cedula, anho, mes);
                if (legajoAsignado != null) {
                    item.setAsignado(true);
                } else {
                    item.setAsignado(false);
                }
            } else {
                item.setAsignado(false);
            }
            CptE ceo = null;
            /*try {
                Long idCeoAsociado = getCptAsignado(item.getCedulaIdentidad());
                if(idCeoAsociado != 0){
                    ceo = get(idCeoAsociado);
                    item.setCptActual(ceo);
                }
            } catch (AppException ex) {
                Logger.getLogger(CeoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            /*if(idCi.containsKey(item.getId().getCedulaIdentidad())){
                Long idCeoAsignado = idCi.get(item.getId().getCedulaIdentidad());
                try {
                    ceo = get(idCeoAsignado);
                    item.setCptActual(ceo);
                } catch (AppException ex) {
                    Logger.getLogger(CeoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }*/
            nuevaLista.add(item);
        }
        res.setRows(nuevaLista);
        return res;
    }
    
    private CptELegajoPK getCptFLegajoAsignado(Long idCptE, Long cedulaIdentidad, int anho, int mes) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk FROM CptELegajo c ");
        query.append("WHERE c.pk.idCptE = :idCptE AND c.pk.cedulaIdentidad = :cedulaIdentidad");
        query.append(" AND c.pk.anho = :anho AND c.pk.mes = :mes");
        Query q = em.createQuery(query.toString());
        q.setParameter("idCptE", idCptE);
        q.setParameter("cedulaIdentidad", cedulaIdentidad);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<CptELegajoPK> list = q.getResultList();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    
    private Map<Long, Long> getIdCi(){
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk.idCptE, c.pk.cedulaIdentidad FROM CptELegajo c ");
        Query q = em.createQuery(query.toString());
        
        List<Object[]> resultList = q.getResultList();
        Map<Long, Long> resultMap = new HashMap<Long, Long>(resultList.size());
        for (Object[] result : resultList)
          resultMap.put((Long)result[0], (Long)result[1]);

        return resultMap;
    }
    
    public Long getCptAsignado(Long nro){
        StringBuilder query = new StringBuilder();
        query.append("SELECT count(c) FROM CptE c ");
        query.append("WHERE c.cpt.id = :nro");
        Query q = em.createQuery(query.toString());
        q.setParameter("nro", nro);
        List<Long> list = q.getResultList();
        
        Long res = new Long(0);
        if(list.size() > 0)
            res = list.get(0);
        
        return res;
    }
    
    
    private List<Long> getLegajosAsignados(Long idCptE, int anho, int mes) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.pk.cedulaIdentidad FROM CptELegajo c ");
        query.append("WHERE c.pk.idCptE = :idCptE ");
        query.append("AND c.pk.anho = :anho AND c.pk.mes = :mes");
        Query q = em.createQuery(query.toString());
        q.setParameter("idCptE", idCptE);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<Long> list = q.getResultList();
        return list;
    }
    
    @Override
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1, start = 0, end = 0;
        int flag = 0;
        StringBuilder where = new StringBuilder();
        System.err.println("Filtro CPT : " + filtros.get("idCpt"));
        
        where.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {
                //String param = key;
                if (key.equals("denominacionCpt")) {
                    key = "denominacion";
                    //param = key;
                    where.append(" LOWER(c.cpt.");
                    flag = 1;
                }else if (key.equals("tituloUnidadCpt")) {
                    key = "tituloUnidad";
                    String param =filtros.get("tituloUnidadCpt").toString();
                    System.out.println("param= "+ param);
                    if(param.compareTo("TODOS")!=0){
                        where.append(" LOWER(c.cpt.");
                        where.append(key)
                            .append(" = :")
                            .append(key);
                        flag = 1;
                    }
                }else if(key.equals("ambito")){
                    key = "nombreAmbito";
                    //param = key;
                    where.append(" LOWER(c.ambito.");
                    flag = 1;
                }else {
                    where.append(" LOWER(c.");
                    flag = 1;
                }
                if(flag == 1){
                    where.append(key)
                        .append(") LIKE LOWER(:")
                        .append(key)
                        .append(")");
                    flag = 0;
                }
                    
            } else {
                if (key.equals("tituloUnidadCpt")) {
                    key = "tituloUnidad";
                    //param = key;
                    where.append(" c.cpt.");
                    where.append(key).append(" = :").append(key);
                }else if (key.equals("subNivelCpt")) {
                    key = "subNivel";
                    //param = key;
                    where.append(" c.cpt.");
                    where.append(key).append(" = :").append(key);
                }else if (key.equals("numeroCpt")) {
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
            start = end-4;
            if (token < tokens && where.toString().compareTo(" WHERE ") != 0 && !where.substring(start, end).equals("AND ")) {
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
    public void setParametrers(Query q, HashMap<String, Object> filtros) {
        if (filtros == null) {
            return;
        }
        for (String key : filtros.keySet()) {
            Object value = filtros.get(key);
            if (filtros.get(key) instanceof String) {
                value = "%" + value + "%";
            }
            if (key.equals("tituloUnidadCpt")) {
                key = "tituloUnidad";
                String param =filtros.get("tituloUnidadCpt").toString();
                if(param.compareTo("TODOS")!=0){
                    System.err.println("Parametros : " + key + " - " + value);
                    q.setParameter(key, value);         
                }
            }
            else{
                if (key.equals("denominacionCpt")) {
                    key = "denominacion";
                    System.err.println("Parametros : " + key + " - " + value);
                }else if (key.equals("subNivelCpt")) {
                    key = "subNivel";
                    System.err.println("Parametros : " + key + " - " + value);
                } else if (key.equals("numeroCpt")) {
                    key = "numeroGasto";
                    System.err.println("Parametros : " + key + " - " + value);
                } else if (key.equals("ambito")) {
                    key = "nombreAmbito";
                    System.err.println("Parametros : " + key + " - " + value);
                } 
                q.setParameter(key, value);
            }
        }
    }
    
    @Override
    public void buildOrder(StringBuilder sb, String orderBy, String orderDir) {
        if (orderBy != null && !orderBy.isEmpty()) {
            System.out.println("orderBy= "+ orderBy+" orderDir= "+orderDir);
           if(orderBy.equals("id")){
                sb.append(" ORDER BY ");
                sb.append("c.cpt.nivel ").append("DESC");
                sb.append(" ,c.cpt.subNivel ").append("ASC");
                sb.append(" ,c.cpt.numeroGasto ").append("ASC");
            }else if(orderBy.equals("nivelCpt")){
                sb.append(" ORDER BY ");
                sb.append("c.cpt.nivel ").append(orderDir);
                sb.append(" ,c.cpt.subNivel ").append(orderDir);
                sb.append(" ,c.cpt.numeroGasto ").append(orderDir);
            }else if(orderBy.equals("subNivelCpt")){
                sb.append(" ORDER BY ");
                sb.append(" c.cpt.subNivel ").append(orderDir);
            }else if(orderBy.equals("numeroCpt")){
                sb.append(" ORDER BY ");
                sb.append(" c.cpt.numeroGasto ").append(orderDir);
            }else{
                if(orderBy.equals("tituloUnidadCpt")){
                    orderBy="tituloUnidad";
                    sb.append(" ORDER BY c.cpt.");
                }else{ 
                    sb.append(" ORDER BY c.");
                }
                sb.append(orderBy).append(" ").append(orderDir);
            }
        }
    }

    
    /*
    public List<Long> getLegajosAsignados(Long idCptE) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.pk.cedulaIdentidad FROM CptELegajo c ");
        sql.append("WHERE c.id.idCptE = :idCptE");
        Query q = em.createQuery(sql.toString());
        q.setParameter("idCptE", idCptE);
        return q.getResultList();
    }
        
    public void asignarLegajos(long idCptE, Legajo legajo) {
                
        if (legajo.isAsignado()) {
            CptELegajoPK pk = new CptELegajoPK(idCptE, legajo.getCedulaIdentidad());
            CptELegajo entity = new CptELegajo(pk);
            em.persist(entity);
        } else {
            CptELegajoPK pk = new CptELegajoPK(idCptE, legajo.getCedulaIdentidad());
            CptELegajo entity = em.find(CptELegajo.class, pk);
            if (entity != null) {
                em.remove(entity);
            }
        }
    }
    
    private List<CptELegajo> getLegajosCpt(Long idCptE) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c FROM CptELegajo c ");
        sql.append("WHERE c.pk.idCptE = :idCptE");
        Query q = em.createQuery(sql.toString());
        q.setParameter("idCptE", idCptE);
        return q.getResultList();
    }
    
    public ListaResponse<Legajo> getLegajosAsignados(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros, Long idCptE) {
                
        List<Legajo> legajos = getLegajos(inicio, cantidad, orderBy, odrerDir, filtros, idCptE, true);
        int total = count(filtros, idCptE, true);
        ListaResponse<Legajo> res = new ListaResponse<>();
        res.setRows(legajos);
        res.setCount(total);
        return res;
    }
        
    
    public ListaResponse<Legajo> getLegajos(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros, Long idCptE) {
                
        List<Legajo> asignados = getLegajos(0, -1, null, null, null, idCptE, true);
        List<Long> listaAsignados = new ArrayList<>();
        for (Legajo legajo : asignados) {
            listaAsignados.add(legajo.getCedulaIdentidad());
        }
        
        List<Legajo> legajos = getLegajos(inicio, cantidad, orderBy, odrerDir, filtros, idCptE, false);
        int total = count(filtros, idCptE, false);
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
            String odrerDir, HashMap<String, Object> filtros, Long idCptE, 
            boolean soloAsignados) {
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT new Legajo(c, l, cargo.descripcion, cargo.nivel) ");
        query.append("FROM Legajo c, LegajoCargo l, Cargo cargo ");
        buildWhereLegajo(query, filtros, soloAsignados);
        buildOrderLegajo(query, orderBy, odrerDir);        
        Query q = em.createQuery(query.toString());
        setParametrers(q, filtros, idCptE);
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
            //sb.append("AND l.pk.idCargo = cargo.id ");
            sb.append("AND c.cedulaIdentidad IN ");
            sb.append("(SELECT cl.id.cedulaIdentidad FROM CptELegajo cl ");
            sb.append("WHERE cl.id.idCptE = :idCptE)");
            sb.append(filtro);
        } else {
            sb.append("WHERE c.cedulaIdentidad = l.pk.cedulaIdentidad ");
            //sb.append("AND l.pk.idCargo = cargo.id ");
            sb.append("AND ((c.cedulaIdentidad IN ");
            sb.append("(SELECT cl.id.cedulaIdentidad FROM CptELegajo cl ");
            sb.append("WHERE cl.id.idCptE = :idCptE)");
            sb.append(filtro);
            sb.append(")");
            sb.append("OR (c.cedulaIdentidad NOT IN (SELECT cl.id.cedulaIdentidad FROM CptELegajo cl)");
            sb.append(filtro);
            sb.append("))");
        }
        
    }
    
    private void setParametrers(Query q, HashMap<String, Object> filtros, Long idCptE) {
        q.setParameter("idCptE", idCptE);
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

    private int count(HashMap<String, Object> filtros, Long idCptE, boolean soloAsignados) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(c) FROM Legajo c, LegajoCargo l, Cargo cargo ");
        buildWhereLegajo(query, filtros, soloAsignados);
        Query q = em.createQuery(query.toString());
        setParametrers(q, filtros, idCptE);
        return ((Long) q.getSingleResult()).intValue();
    }*/

}

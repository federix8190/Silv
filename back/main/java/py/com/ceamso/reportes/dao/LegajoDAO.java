package py.com.ceamso.reportes.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import py.com.ceamso.administracion.model.CeoLegajo;
import py.com.ceamso.administracion.model.CeoLegajoPK;
import py.com.ceamso.administracion.model.CptFLegajo;
import py.com.ceamso.administracion.model.CptFLegajoPK;
import py.com.ceamso.administracion.model.CptLegajo;
import py.com.ceamso.administracion.model.CptLegajoPK;
import py.com.ceamso.administracion.model.Departamento;
import py.com.ceamso.administracion.model.Distrito;
import py.com.ceamso.administracion.model.PuestoTrabajo;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableDAO;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.reportes.dto.CargoDto;
import py.com.ceamso.reportes.dto.CarreraAdministrativaDto;
import py.com.ceamso.reportes.model.EstudiosConcluidos;
import py.com.ceamso.reportes.dto.FormacionAcademica;
import py.com.ceamso.reportes.dto.RecorridoLaboral;
import py.com.ceamso.reportes.dto.SancionPersonal;
import py.com.ceamso.reportes.dto.SumarioPersonal;
import py.com.ceamso.reportes.model.Anexos;
import py.com.ceamso.reportes.model.Apercibimientos;
import py.com.ceamso.reportes.model.CarreraAdministrativa;
import py.com.ceamso.reportes.model.CursoInformatica;
import py.com.ceamso.reportes.model.Destitucion;
import py.com.ceamso.reportes.model.DiasNoTrabajados;
import py.com.ceamso.reportes.model.Eventos;
import py.com.ceamso.reportes.model.ExperienciaLaboral;
import py.com.ceamso.reportes.model.FormacionAcademicaHacienda;
import py.com.ceamso.reportes.model.Idiomas;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.reportes.model.LegajoCargo;
import py.com.ceamso.reportes.model.Multas;
import py.com.ceamso.reportes.model.MultasControl;
import py.com.ceamso.reportes.model.OtrosCursos;
import py.com.ceamso.reportes.model.OtrosEstudios;
import py.com.ceamso.reportes.model.Sobreseimiento;
import py.com.ceamso.reportes.model.Sumarios;
import py.com.ceamso.reportes.model.Suspensiones;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Mensajes;

/**
 *
 * @author konecta
 */
public class LegajoDAO extends ReadableDAO<Legajo> {
    
    @Inject
    @Configuracion("esHacienda")
    private String esHacienda;

    @Override
    public Class getEntity() {
        return Legajo.class;
    }
    
    @Override
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1;
        
        boolean filtroMatriz = false;
        if (filtros.containsKey("matriz")) {
            filtroMatriz = true;
        }
        
        StringBuilder where = new StringBuilder();
        where.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (filtroMatriz && (key.equals("matriz") || key.equals("numeroTramo") || key.equals("nivel"))) {
                break;
            } else {
                if ("antiguedadCargo".compareTo(key) == 0) {
                    where.append(key).append(" >= :").append(key);
                } else if (filtros.get(key) instanceof String) {
                    where.append(" LOWER(c.")
                        .append(key)
                        .append(") LIKE LOWER(:")
                        .append(key)
                        .append(")");
                } else {
                    where.append(key).append(" = :").append(key);
                }
                //se añade el 'AND' si hay más caracteres.
                if (token < tokens) {
                    where.append(" AND ");
                }
            }
            token++;
        }
        
        if (filtroMatriz) {
            where.append(" c.cedulaIdentidad IN :cedulas ");
        }
           
        sb.append(where.toString());
    }
    
    @Override
    public void setParametrers(Query q, HashMap<String, Object> filtros) {
        if (filtros == null) {
            return;
        }
        boolean filtroMatriz = false;
        if (filtros.containsKey("matriz")) {
            filtroMatriz = true;
        }
        for (String key : filtros.keySet()) {
            Object value = filtros.get(key);
            if (filtroMatriz && (key.equals("matriz") || key.equals("numeroTramo") || key.equals("nivel"))) {
                break;
            } else {
                if ("titulo".compareTo(key) == 0) {
                    String values = (String) value;
                    String[] titulos = values.split(",");
                    for(int j=0; j < titulos.length; j++){
                        q.setParameter(key+j, "%"+titulos[j]+"%");
                    }
                } else {
                    if (filtros.get(key) instanceof String) {
                        value = "%" + value + "%";
                    }
                    q.setParameter(key, value);
                }
            }
        }
        int anho = (Integer) filtros.get("anho");
        int mes = (Integer) filtros.get("mes");
        String vinculacion = (String) filtros.get("vinculacionFuncionario");
        Long nivel = (Long) filtros.get("nivel");
        Long tramo = (Long) filtros.get("numeroTramo");
        if (filtroMatriz) {
            List<Long> cedulas = getCedulasMatriz(anho, mes, vinculacion, tramo, nivel);
            q.setParameter("cedulas", cedulas);
            
        }
    }
    
    private List<Long> getCedulasMatriz(int anho, int mes, String vinculacion, Long tramo, Long nivel) {
        
        String esquema = "dbo";
        String listaConceptos = "('SUELDOS', 'HONORARIOS PROFESIONALES', 'JORNALES')";
        if (esHacienda()) {
            esquema = "cte";
            listaConceptos = "('SUELDO', 'HONORARIOS PROFESIONALES', 'JORNALES')";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s.cedula_identidad FROM (");
        sb.append("SELECT a.anho, a.mes, a.cedula_identidad, a.nivel_cpt, ");
        sb.append("a.vinculacion_funcionario, sum(a.presupuestado) as presupuestado ");
        sb.append("FROM " + esquema + ".v_cte_anexo_liquidacion a ");
        sb.append("WHERE a.nivel_cpt IS NOT NULL and a.anho = :anho AND a.mes = :mes ");
        if (vinculacion != null && !vinculacion.isEmpty() && !vinculacion.equalsIgnoreCase("TODOS")) {
            sb.append("AND a.vinculacion_funcionario = :vinculacion ");
        }        
        sb.append("AND a.concepto IN " + listaConceptos);
        sb.append("GROUP BY a.anho, a.mes, a.cedula_identidad, a.nivel_cpt, a.vinculacion_funcionario) s ");
        sb.append("LEFT JOIN " + esquema + ".cts cs ON presupuestado >= cs.minimo AND presupuestado < cs.maximo ");
        sb.append("AND cs.anho = s.anho AND cs.mes = s.mes ");
        sb.append("where cs.numero_tramo = :tramo and s.nivel_cpt = :nivel");
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        if (vinculacion != null && !vinculacion.isEmpty() && !vinculacion.equalsIgnoreCase("TODOS")) {
            q.setParameter("vinculacion", vinculacion);
        }
        q.setParameter("tramo", tramo);
        q.setParameter("nivel", nivel);
        List<Integer> res = q.getResultList();
        List<Long> lista = new ArrayList<>();
        if (res != null && res.size() > 0) {
            for (int cedula : res) {
                lista.add(new Long(cedula));
            }
            return lista;
        }
        lista.add(-1L);
        return lista;
    }
    
    public ListaResponse<Legajo> getCandidatos(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros, Long idCptEe, 
            Long idCptEf, Long numeroTramo) {
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT c FROM Legajo c ");
        buildWhereCandidatos(query, filtros, idCptEe, idCptEf, numeroTramo);
        buildOrder(query, orderBy, odrerDir);
        System.err.println(query.toString());
        Query q = em.createQuery(query.toString());
        setParametrersCandidatos(q, filtros, idCptEe, idCptEf, numeroTramo);
        q.setFirstResult(inicio);
        if (cantidad != -1) {            
            q.setMaxResults(cantidad);
        }
        List<Legajo> list = q.getResultList();
        
        query = new StringBuilder();
        query.append("SELECT COUNT(c) FROM Legajo c ");
        buildWhereCandidatos(query, filtros, idCptEe, idCptEf, numeroTramo);
        q = em.createQuery(query.toString());
        setParametrersCandidatos(q, filtros, idCptEe, idCptEf, numeroTramo);
        int total = ((Long) q.getSingleResult()).intValue();
        
        //se construye la respuesta 
        ListaResponse<Legajo> res = new ListaResponse<>();
        res.setRows(list);
        res.setCount(total);
        return res;

    }
    
    public void buildWhereCandidatos(StringBuilder sb, HashMap<String, Object> filtros,
            Long idCptEe, Long idCptEf, Long numeroTramo) {
        
        System.err.println("Parametros : " + numeroTramo + " - " + idCptEe + " - " + idCptEf);
        StringBuilder where = new StringBuilder();
        where.append(" WHERE numeroTramo < :numeroTramo ");
        
        if (idCptEe != null) {
            where.append(" AND idCptEe = :idCptEe ");
        }
        
        if (idCptEf != null) {
            where.append(" AND idCptEf = :idCptEf ");
        }
        
        if (filtros == null || filtros.isEmpty()) {
            sb.append(where.toString());
            return;
        }
        
        int tokens = filtros.keySet().size();
        int token = 1;
        where.append(" AND ");
        for (String key : filtros.keySet()) {
            if ("antiguedadCargo".compareTo(key) == 0) {
                where.append(key).append(" >= :").append(key);
            } else if (filtros.get(key) instanceof String) {
                where.append(" LOWER(c.")
                    .append(key)
                    .append(") LIKE LOWER(:")
                    .append(key)
                    .append(")");
            } else {
                where.append(key).append(" = :").append(key);
            }
            //se añade el 'AND' si hay más caracteres.
            if (token < tokens) {
                where.append(" AND ");
            }
            token++;
        }
        
        sb.append(where.toString());
    }
    
    public void setParametrersCandidatos(Query q, HashMap<String, Object> filtros,
            Long idCptEe, Long idCptEf, Long numeroTramo) {
        
        q.setParameter("numeroTramo", numeroTramo);
        
        if (idCptEe != null) {
            q.setParameter("idCptEe", idCptEe);
        }
        
        if (idCptEf != null) {
            q.setParameter("idCptEf", idCptEf);
        }
        
        if (filtros == null) {
            return;
        }
        
        for (String key : filtros.keySet()) {
            if (!key.equals("candidatos")) {
                Object value = filtros.get(key);
                if (filtros.get(key) instanceof String) {
                    value = "%" + value + "%";
                }
                q.setParameter(key, value);
            }
        }
    }
    
    public List<String> getVinculaciones() {
        
        String sql = "SELECT vinculacionFuncionario FROM Vinculacion";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }
    
    public List<String> getAmbitos() {
        
        String sql = "SELECT nombreAmbito FROM Ambito";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }
    
    public List<String> getOrientacionFunc() {
        
        String sql = "SELECT nombreOrientacion FROM OrientacionFunc";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }
    
    public List<String> getConceptos() {
        
        String sql = "SELECT c.concepto FROM Concepto c ";
        sql = sql + "WHERE c.concepto not like :concepto1 AND c.concepto != :concepto2 ";
        sql = sql + "AND c.concepto != :concepto3";
        Query q = em.createQuery(sql);
        q.setParameter("concepto1", "SUELDO%");
        q.setParameter("concepto2", "HONORARIOS PROFESIONALES");
        q.setParameter("concepto3", "JORNALES");
        return q.getResultList();
    }
    
    public List<Departamento> getDepartamentos() {
        
        String sql = "SELECT d FROM Departamento d";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }
    
    public List<Distrito> getDistritos(Long codigoDepto) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT d FROM Distrito d");
        query.append(" WHERE d.codigoDepto = :codigoDepto ");
        Query q = em.createQuery(query.toString());
        q.setParameter("codigoDepto", codigoDepto); 
        return q.getResultList();
    }
    
    public List<CargoDto> getCargosLegajo(Long cedulaIdentidad) {
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT new LegajoCargo(l, c.descripcion) ");
        query.append("FROM LegajoCargo l, Cargo c ");
        query.append("WHERE l.pk.cedulaIdentidad = :cedulaIdentidad ");
        query.append("AND l.pk.idCargo = c.id");
        
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaIdentidad", cedulaIdentidad);
        List<LegajoCargo> res = q.getResultList();
        List<CargoDto> lista = new ArrayList<>();
        for (LegajoCargo l : res) {
            lista.add(new CargoDto(l));
        }
        return lista;
    }
    
    public List<FormacionAcademica> obtenerFormacionAcademica(Integer ci) {
        
        if (esHacienda()) {
            return new ArrayList<>();
        }
        
        StoredProcedureQuery query = em.createStoredProcedureQuery("dbo.cte_obtener_formacion_academica")
            .registerStoredProcedureParameter("cedula_identidad", Integer.class, ParameterMode.IN)
            .setParameter("cedula_identidad", ci);
        query.execute();

        List<Object[]> lista = (List<Object[]>) query.getResultList();
        List<FormacionAcademica> formacionAcademica = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            Object[] datos = lista.get(i);
            formacionAcademica.add(new FormacionAcademica(datos));
        }
        return formacionAcademica;
    }
    
    public List<FormacionAcademicaHacienda> obtenerFormacionAcademicaHacienda(Integer ci) {
        
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT f FROM FormacionAcademicaHacienda f ");
        query.append("WHERE f.pk.cedulaIdentidad = :cedulaIdentidad");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaIdentidad", ci.toString());
        List<FormacionAcademicaHacienda> res = q.getResultList();
        return res;
    }
    
    public List<RecorridoLaboral> obtenerRecorridoLaboral(Integer ci) {
        
        if (esHacienda()) {
            return new ArrayList<>();            
        }
        
        StoredProcedureQuery query = em.createStoredProcedureQuery("dbo.cte_obtener_recorrido_laboral")
            .registerStoredProcedureParameter("cedula_identidad", Integer.class, ParameterMode.IN)
            .setParameter("cedula_identidad", ci);
        query.execute();

        List<Object[]> lista = (List<Object[]>) query.getResultList();
        List<RecorridoLaboral> recorridoLaboral = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            Object[] datos = lista.get(i);
            recorridoLaboral.add(new RecorridoLaboral(datos));
        }
        return recorridoLaboral;
    }
    
      public List<SancionPersonal> obtenerSancionPersonal(Integer ci) {
 
        if (esHacienda()) {
            return new ArrayList<>();            
        }
      
        StoredProcedureQuery query = em.createStoredProcedureQuery("dbo.cte_obtener_sancion_personal")
            .registerStoredProcedureParameter("cedula_identidad", Integer.class, ParameterMode.IN)
            .setParameter("cedula_identidad", ci);
 
        query.execute();
 
        List<Object[]> lista = (List<Object[]>) query.getResultList();
        List<SancionPersonal> sancionPersonal = new ArrayList<>();
 
        for (int i = 0; i < lista.size(); i++) {
            Object[] datos = lista.get(i);
            sancionPersonal.add(new SancionPersonal(datos));
        }
        return sancionPersonal;
    }
      
    public List<SumarioPersonal> obtenerSumarioPersonal(Integer ci) {
 
        if (esHacienda()) {
            return new ArrayList<>();            
        }
      
        StoredProcedureQuery query = em.createStoredProcedureQuery("dbo.cte_obtener_sumario_personal")
            .registerStoredProcedureParameter("cedula_identidad", Integer.class, ParameterMode.IN)
            .setParameter("cedula_identidad", ci);
 
        query.execute();
 
        List<Object[]> lista = (List<Object[]>) query.getResultList();
        List<SumarioPersonal> sumarioPersonal = new ArrayList<>();
 
        for (int i = 0; i < lista.size(); i++) {
            Object[] datos = lista.get(i);
            sumarioPersonal.add(new SumarioPersonal(datos));
        }
        return sumarioPersonal;
    }
 
    
    public List<CarreraAdministrativaDto> getCarreraAdministrativa(Integer ci) {
        
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT c FROM CarreraAdministrativa c ");
        query.append("WHERE c.cedulaIdentidad = :cedulaIdentidad ");
        query.append("AND c.tipo = :tipo ");
        query.append("ORDER BY c.pk.fechaDisposicion");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaIdentidad", ci.toString());
        q.setParameter("tipo", "DATOS ADMINISTRATIVOS PERMANENTES");        
        List<CarreraAdministrativa> res = q.getResultList();
        
        List<CarreraAdministrativaDto> lista = new ArrayList<>();
        for (CarreraAdministrativa item : res) {
            lista.add(new CarreraAdministrativaDto(item));
        }
        return lista;
        
    }
    
    public List<EstudiosConcluidos> obtenerEstudiosConcluidos(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT e FROM EstudiosConcluidos e ");
        query.append("WHERE e.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<EstudiosConcluidos> res = q.getResultList();
        return res;
    }
    
    public List<OtrosEstudios> obtenerOtrosEstudios(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT e FROM OtrosEstudios e ");
        query.append("WHERE e.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<OtrosEstudios> res = q.getResultList();
        return res;
    }
    
    public List<OtrosCursos> obtenerOtrosCursos(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT e FROM OtrosCursos e ");
        query.append("WHERE e.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<OtrosCursos> res = q.getResultList();
        return res;
    }
    
    public List<CursoInformatica> obtenerCursoInformatica(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT c FROM CursoInformatica c ");
        query.append("WHERE c.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<CursoInformatica> res = q.getResultList();
        return res;
    }
    
    public List<Idiomas> obtenerIdiomas(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT i FROM Idiomas i ");
        query.append("WHERE i.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<Idiomas> res = q.getResultList();
        return res;
    }
    
    public List<Multas> obtenerMultas(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT m FROM Multas m ");
        query.append("WHERE m.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<Multas> res = q.getResultList();
        return res;
    }
    
    public List<MultasControl> obtenerMultasControl(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT m FROM MultasControl m ");
        query.append("WHERE m.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<MultasControl> res = q.getResultList();
        return res;
    }
    
    public List<DiasNoTrabajados> obtenerDiasNoTrabajados(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT d FROM DiasNoTrabajados d ");
        query.append("WHERE d.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<DiasNoTrabajados> res = q.getResultList();
        return res;
    }
    
    public List<ExperienciaLaboral> obtenerExperienciaLaboral(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT e FROM ExperienciaLaboral e ");
        query.append("WHERE e.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<ExperienciaLaboral> res = q.getResultList();
        return res;
    }
    
    public List<Eventos> obtenerEventos(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT e FROM Eventos e ");
        query.append("WHERE e.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<Eventos> res = q.getResultList();
        return res;
    }
    
    public List<Apercibimientos> obtenerApercibimientos(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT a FROM Apercibimientos a ");
        query.append("WHERE a.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<Apercibimientos> res = q.getResultList();
        return res;
    }
    
    public List<Sumarios> obtenerSumarios(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT s FROM Sumarios s ");
        query.append("WHERE s.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<Sumarios> res = q.getResultList();
        return res;
    }
    
    public List<Suspensiones> obtenerSuspensiones(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT s FROM Suspensiones s ");
        query.append("WHERE s.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<Suspensiones> res = q.getResultList();
        return res;
    }
    
    public List<Destitucion> obtenerDestitucion(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT d FROM Destitucion d ");
        query.append("WHERE d.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<Destitucion> res = q.getResultList();
        return res;
    }
    
    public List<Sobreseimiento> obtenerSobreseimiento(Integer ci){
        if (!esHacienda()) {
            return new ArrayList<>();
        }
        
        StringBuilder query = new StringBuilder();
        query.append("SELECT s FROM Sobreseimiento s ");
        query.append("WHERE s.pk.cedulaFuncionario = :cedulaFuncionario ");
        Query q = em.createQuery(query.toString());
        q.setParameter("cedulaFuncionario", ci.toString());
        List<Sobreseimiento> res = q.getResultList();
        return res;
    }
    /*@Override
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1;
        
        boolean verCandidatos = false;
        if (filtros.containsKey("candidatos")) {
            String candidatos = (String) filtros.get("candidatos");
            if (candidatos != null && candidatos.equalsIgnoreCase("true")) {
                verCandidatos = true;
            }
        }
        
        sb.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {
                sb.append(" LOWER(c.")
                        .append(key)
                        .append(") LIKE LOWER(:")
                        .append(key)
                        .append(")");
            } else {
                if (key.equals("numeroTramo") && verCandidatos) {
                    if (key.equals("numeroTramo")) {
                        sb.append(key)
                            .append(" < :")
                            .append(key);
                    } else if (key.equals("numeroTramo")) {
                        ;
                    }
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
    }*/
    
    private boolean esHacienda() {
        return esHacienda != null && esHacienda.equalsIgnoreCase("S");
    }
    
    public void actualizarLegajo(int anho, int mes) {
    	
    	StringBuilder sql = new StringBuilder();
    	sql.append("select distinct a.id.cedulaIdentidad from Anexos a ");
    	sql.append("where a.id.anho = :anho and a.id.mes = :mes");
    	
    	Query q = em.createQuery(sql.toString());
    	//q.setMaxResults(2000);
    	q.setParameter("anho", anho);
    	q.setParameter("mes", mes);
    	
    	List<Integer> cedulas = q.getResultList();
    	
    	for (Integer cedula : cedulas) {
    		
    		// Datos del Anexo cargados para el periodo actual
    		Anexos datosAnexo = getAnexoActual(anho, mes, cedula);
    		if (datosAnexo != null) {
    			
    			String programa = datosAnexo.getPrograma();
    			String subPrograma = datosAnexo.getSubprograma();
    			String cargo = datosAnexo.getCargo();
    			String categoria = datosAnexo.getCategoria();
    			Long presupuestado = new Long(datosAnexo.getPresupuestado());
    			
    			Long idCeo = null;
    			Long idCpt = null;
				Long idCptF = null;
				
    			// Obtenemos los datos del periodo anterior
    			PuestoTrabajo puesto = getPeriodoAnterior(cedula);
    			if (puesto != null) {
    				
    				// Actualizar Datos CEO de acuerdo al programa/subprograma (Si son iguales)
    				String programaAnterior = puesto.getPrograma();
        			String subProgramaAnterior = puesto.getSubprograma();
        			if (programa.equals(programaAnterior) && subPrograma.equals(subProgramaAnterior)) {
        				if (puesto.getCeo() != null) {
	        				idCeo = puesto.getCeo().getId();
	        				CeoLegajoPK pk = new CeoLegajoPK(idCeo, cedula, anho, mes);
	        				CeoLegajo ceoLegajo = new CeoLegajo(pk);
	        				em.persist(ceoLegajo);
        				}
        			}
        			
        			// Actualizar Datos CPT de acuerdo al cargo/categoria/presupuesto (Si son iguales)
    				String cargoAnterior = puesto.getCargo();
        			String categoriaAnterior = puesto.getCategoria();
        			Long presupuestadoAnterior = puesto.getPresupuestado();
        			if (cargo.equals(cargoAnterior) && categoria.equals(categoriaAnterior)
        					&& presupuestado.equals(presupuestadoAnterior)) {
        				if (puesto.getCpt() != null) {
	        				idCpt = puesto.getCpt().getId();
	        				CptLegajoPK pk = new CptLegajoPK(idCpt, cedula, anho, mes);
	        				CptLegajo cptLegajo = new CptLegajo(pk);
	        				em.persist(cptLegajo);
        				}
        				if (puesto.getCptF() != null) {
	        				idCpt = puesto.getCpt().getId();
	        				CptFLegajoPK pk = new CptFLegajoPK(idCptF, cedula, anho, mes);
	        				CptFLegajo cptFLegajo = new CptFLegajo(pk);
	        				em.persist(cptFLegajo);
        				}
        			}
        			
    			}
        	
    		}
    		
    	}
    }
    
    private Anexos getAnexoActual(int anho, int mes, int cedula) {
    	
    	StringBuilder sql = new StringBuilder();
    	sql.append("select a from Anexos a ");
    	sql.append("where a.id.anho = :anho and a.id.mes = :mes ");
    	sql.append("AND a.id.cedulaIdentidad = :cedula ");
    	
    	Query q = em.createQuery(sql.toString());
    	q.setParameter("cedula", cedula);
    	q.setParameter("anho", anho);
    	q.setParameter("mes", mes);
    	
    	List<Anexos> lista = q.getResultList();
    	if (lista != null && lista.size() > 0) {
    		return lista.get(0);
    	}
    	return null;
    	
    }
    
    private PuestoTrabajo getPeriodoAnterior(int cedula) {
    	StringBuilder sql = new StringBuilder();
		sql.append("SELECT p FROM PuestoTrabajo p ");
		sql.append("WHERE p.id.cedulaIdentidad = :cedula ");
		sql.append("ORDER BY p.id.anho desc, p.id.mes desc");
		
		Query q = em.createQuery(sql.toString());
    	q.setParameter("cedula", cedula);
    	q.setMaxResults(1);
    	List<PuestoTrabajo> datosPuesto = q.getResultList();
    	if (datosPuesto != null && datosPuesto.size() > 0) {
    		return datosPuesto.get(0);
    	}
    	return null;
    }
}

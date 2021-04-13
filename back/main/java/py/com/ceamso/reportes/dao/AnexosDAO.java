package py.com.ceamso.reportes.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import py.com.ceamso.base.ReadableDAO;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.reportes.model.Anexos;
import py.com.ceamso.reportes.model.AnexosPK;
import py.com.ceamso.reportes.model.DetalleAnexo;
import py.com.ceamso.reportes.model.ReporteComparativo;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.seguridad.service.SessionService;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;

/**
 *
 * @author konecta
 */
public class AnexosDAO extends ReadableDAO<Anexos> {
    
    @Inject
    @Configuracion("esHacienda")
    private String esHacienda;
    
    @Inject
    private SessionService session;

    /**
     * 
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return Anexos.class;
    }
    
    public Long getCptEPorCategori(Integer cedulaIdentidad, int anho, int mes) {
        StringBuilder sql = new StringBuilder();
        
        sql.append("SELECT c.id FROM CptE c ");
        sql.append("INNER JOIN Anexos a ON c.nombreCategoria = a.categoria ");
        sql.append("WHERE a.id.cedulaIdentidad = :cedulaIdentidad");
        sql.append(" AND a.id.anho = :anho AND a.id.mes = :mes");
        
        Query q = em.createQuery(sql.toString());
        q.setParameter("cedulaIdentidad", cedulaIdentidad);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        
        List<Long> list = q.getResultList();
        
        Long res = new Long(0);
        if (list.size() > 0)
            res = list.get(0);
        
        return res;
    }
    
    /*@Override
    public ListaResponse<Anexos> listar(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros) {
        
        /*StringBuilder query = new StringBuilder();
        query.append("SELECT c FROM Anexos c ");
        //if (filtros.containsKey("mes")) filtros.remove("mes");
        buildWhere(query, filtros);
        buildOrder(query, orderBy, odrerDir);
        Query q = em.createQuery(query.toString());
        setParametrers(q, filtros);
        System.err.println(query.toString());
        List<Anexos> list = q.getResultList();
        int total = count(filtros);*/
        
        /*System.err.println("Listar anexos");
        StringBuilder query = new StringBuilder();
        query.append("SELECT c FROM Anexos c WHERE c.id.anho = :anho AND c.id.mes = :mes ");
        //query.append("AND c.id.concepto = :concepto AND c.vinculacionFuncionario = :vinculacionFuncionario");
        Query q = em.createQuery(query.toString());
        System.err.println(query.toString());
        q.setParameter("anho", filtros.get("anho"));
        q.setParameter("mes", filtros.get("mes"));
        //q.setParameter("concepto", "SUELDOS");
        //q.setParameter("vinculacionFuncionario", filtros.get("vinculacionFuncionario"));
        q.setFirstResult(inicio);
        if (cantidad != -1) {            
            q.setMaxResults(cantidad);
        }
        List<Anexos> list = q.getResultList();
        
        query = new StringBuilder();
        query.append("SELECT COUNT(c) FROM Anexos c WHERE c.id.anho = :anho AND c.id.mes = :mes ");
        //query.append("AND c.id.concepto = :concepto AND c.vinculacionFuncionario = :vinculacionFuncionario");
        q = em.createQuery(query.toString());
        q.setParameter("anho", filtros.get("anho"));
        q.setParameter("mes", filtros.get("mes"));
        //q.setParameter("concepto", "SUELDOS");
        //q.setParameter("vinculacionFuncionario", filtros.get("vinculacionFuncionario"));
        int total = ((Long) q.getSingleResult()).intValue();
        
        //se construye la respuesta 
        ListaResponse<Anexos> res = new ListaResponse<>();
        res.setRows(list);
        res.setCount(total);
        return res;
    }*/
    
    private int count(HashMap<String, Object> filtros) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(c) FROM Anexos c ");
        buildWhere(query, filtros);
        Query q = em.createQuery(query.toString());
        setParametrers(q, filtros);
        System.err.println(query.toString());
        return ((Long) q.getSingleResult()).intValue();
    }
                
    /*@Override
    public ListaResponse<Anexos> listar(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros) {
                
        //if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {
        //    return getAnexosHacienda();
        //}
        StringBuilder query = new StringBuilder();
        query.append("SELECT c FROM ")
                .append(getEntity().getCanonicalName())
                .append(" c")
                .append(" WHERE c.id.linea != null ");
        buildWhere(query, filtros);        
        Query q = em.createQuery(query.toString());
        setParametrers(q, filtros);
        if (cantidad !=-1) {
            q.setFirstResult(inicio).setMaxResults(cantidad);
        }
        List<Anexos> list = q.getResultList();
        System.err.println("Anexos : " + list.size());
        int total = count(filtros);
        //se construye la respuesta 
        ListaResponse<Anexos> res = new ListaResponse<Anexos>();
        res.setRows(list);
        res.setCount(total);
        return res;

    }*/   
        
    public Anexos get(int anho, int mes, int cedulaIdentidad, 
            String objetoGasto, String concepto)//, Long numeroPuestoTrabajo) 
            throws AppException {
        
        try {
            
            AnexosPK pk = new AnexosPK(anho, mes, objetoGasto, concepto, cedulaIdentidad);//, numeroPuestoTrabajo);
            Anexos anexo = (Anexos) em.find(getEntity(), pk);
            return anexo;
            
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    /**
     *
     * @param sb
     * @param filtros
     */
    /*@Override
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1, start = 0, end = 0;
        
        StringBuilder where = new StringBuilder();
        where.append(" WHERE ");
        
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {
                if (key.equals("concepto") || key.equals("objetoGasto")) {
                    where.append(" LOWER(c.id.")
                    .append(key)
                    .append(") = LOWER(:")
                    .append(key)
                    .append(")");
                } else {
                   if (key.equals("vinculacionFuncionario")) {
                        String param =filtros.get("vinculacionFuncionario").toString();
                        if(param.compareTo("TODOS")!=0){
                            where.append(" LOWER(c.")
                            .append(key)
                            .append(") = LOWER(:")
                            .append(key)
                            .append(")");
                        }
                    } else {
                        where.append(" LOWER(c.")
                            .append(key)
                            .append(") = LOWER(:")
                            .append(key)
                            .append(")");
                    }
                }  
            } else if (key.equals("presupuestado") || key.equals("nivelEntidad") 
                    || key.equals("fuenteFinanciamiento")
                    || key.equals("oee") || key.equals("presupuestado") || key.equals("devengado")
                    || key.equals("linea") || key.equals("numeroPuestoTrabajo") || key.equals("tramo")
                    || key.equals("minimo") || key.equals("maximo") || key.equals("titularUnidad")
                    || key.equals("nivelCuo") || key.equals("subNivelCuo") || key.equals("numeroCuo")) {
                where.append("c.")
                        .append(key)
                        .append(" = :")
                        .append(key);
            } else {
                where.append("c.id.")
                        .append(key)
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
    }*/
    
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
                if (key.equals("concepto") || key.equals("objetoGasto")) {
                    sb.append(" LOWER(c.id.")
                        .append(key)
                        .append(") LIKE LOWER(:")
                        .append(key)
                        .append(")");
                } else {
                    sb.append(" LOWER(c.")
                        .append(key)
                        .append(") LIKE LOWER(:")
                        .append(key)
                        .append(")");
                }
            } else {
                if (key.equals("cedulaIdentidad") || key.equals("anho") || key.equals("mes")) {
                    sb.append("c.id.").append(key).append(" = :").append(key);
                } else {
                    sb.append(key).append(" = :").append(key);
                }
            }
            //se añade el 'AND' si hay más caracteres.
            if (token < tokens) {
                sb.append(" AND ");
            }
            token++;
        }
    }
    
    /**
     *
     * @param sb
     * @param orderBy
     * @param orderDir
     */
    @Override
    public void buildOrder(StringBuilder sb, String orderBy, String orderDir) {
        System.err.println("Orden en anexos : " + orderBy);
        if (orderBy == null || orderBy.equals("id")) {
            orderBy = "orden";
        	//orderBy = "id";
            sb.append(" ORDER BY c.").append(orderBy).append(" ").append(orderDir);
        } else if (orderBy != null && !orderBy.isEmpty()) {
            if(orderBy.equals("anho") || orderBy.equals("mes") || orderBy.equals("cedulaIdentidad")
                    || orderBy.equals("concepto") || orderBy.equals("objetoGasto")){
                sb.append(" ORDER BY c.id.").append(orderBy).append(" ").append(orderDir);
            }else{
                sb.append(" ORDER BY c.").append(orderBy).append(" ").append(orderDir);
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
            /*if (key.equals("vinculacionFuncionario")) {
                String param =filtros.get("vinculacionFuncionario").toString();
                if(param.compareTo("TODOS")!=0){
                    System.err.println("Parametros : " + key + " - " + value);
                    q.setParameter(key, value);         
                }
            } else{
                System.err.println("Parametros : " + key + " - " + value);
                q.setParameter(key, value); 
            }*/
            q.setParameter(key, value); 
        }
    }
    
    /*public HashMap<Long, Anexos> getAnexos(List<Long> cargosIds) {
        
        HashMap<Long, Anexos> res = new HashMap<Long, Anexos>();
        if (cargosIds != null && cargosIds.size() > 0) {
            String sql = "SELECT a FROM Anexos a WHERE a.id.idCargo IN :cargos";
            Query q = em.createQuery(sql);
            q.setParameter("cargos", cargosIds);
            List<Anexos> anexos = q.getResultList();
            for (Anexos a : anexos) {
                res.put(a.getIdCargo(), a);
            }
        }
        return res;
    }*/
    
    public Anexos getAnexo(Long idCargo) {
        
        String sql = "SELECT a FROM Anexos a WHERE a.id.idCargo = :idCargo";
        Query q = em.createQuery(sql);
        q.setParameter("idCargo", idCargo);
        List<Anexos> anexos = q.getResultList();
        
        if (anexos != null && anexos.size() > 0) {
            return anexos.get(0);
        }
        return null;
    }
    
    public Integer[] getAnhoMes() {
        
        String sql = "SELECT MAX(a.id.anho), MAX(a.id.mes) FROM Anexos a where a.id.anho = (select max(v.id.anho) from Anexos v)";
        Query q = em.createQuery(sql);
        List<Integer[]> res = q.getResultList();
        if (res != null && res.size() > 0) {
            Object[] datos = res.get(0);
            Integer[] anhoMes = {0, 0};
            anhoMes[0] = (Integer) datos[0];
            anhoMes[1] = (Integer) datos[1];
            return anhoMes;
        }
        return null;
    }
    
    public ListaResponse<Anexos> getAnexosHacienda() {
        
        StoredProcedureQuery query = em.createStoredProcedureQuery("salarios.sfp_informe_mensual_v3_exportar")
            .registerStoredProcedureParameter("in_id_administracion", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter("in_tipo", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter("in_id_mes", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter("in_gestion", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter("in_gvacante", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter("in_id_funcionario", String.class, ParameterMode.IN)
            .setParameter("in_id_administracion", 1)
            .setParameter("in_tipo", 0)
            .setParameter("in_id_mes", 6)
            .setParameter("in_gestion", 2017)
            .setParameter("in_gvacante", 0)
            .setParameter("in_id_funcionario", "0");
        query.execute();

        List<Object[]> lista = (List<Object[]>) query.getResultList();
        List<Anexos> anexos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Object[] datos = lista.get(i);
            anexos.add(new Anexos(datos));
        }
        
        ListaResponse<Anexos> res = new ListaResponse<>();
        res.setRows(anexos);
        res.setCount(lista.size());
        return res;
    }
    
    public void cargarAnexosHacienda(int anho, int mes) {
                
        String sql = "INSERT INTO " + Constantes.ESQUEMA_CTE_MH + ".anexos("
            + "anho, mes, nivel_entidad, entidad, oee, linea, numero_puesto, cedula_identidad, "
            + "nombre, apellido, objeto_gasto, fuente_financiamiento, concepto, vinculacion_funcionario, "
            + "cargo, funcion_real, categoria, presupuestado, devengado, programa, subprograma, "
            + "id_ceo, codigo_ceo, denominacion_ceo, id_cuo, nivel_cuo, subnivel_cuo, numero_cuo, "
            + "denominacion_cuo, id_cpt, nivel_cpt, subnivel_cpt, numero_cpt, denominacion_cpt, titular_unidad, "
            + "id_cpt_ef, numero_secuencial, ambito_cpt_ef, codigo_proceso, denominacion_cpt_ef, "
            + "orientacion_funcional, id_cpt_ee, numero_secuencial_cpt_ee, ambito_cpt_ee, nivel_cpt_ee, "
            + "categoria_cpt_ee, denominacion_cpt_ee)"    
            + "select sft.anho, sft.mes, sft.nivel_entidad, sft.entidad, sft.oee, "
            + "	CASE WHEN sft.linea = '' THEN 0 ELSE CAST(sft.linea AS integer) END, pt.numero_puesto, "
            + "	CASE WHEN sft.cedula_funcionario = '' THEN 0 ELSE CAST(sft.cedula_funcionario AS integer) END, "
            + "	sft.nombre_funcionario, sft.apellido_funcionario, sft.objeto_gasto, " 
            + "	CASE WHEN sft.ff is null THEN 0 ELSE sft.ff END, "
            + "	sft.concepto, sft.estado, sft.cargo, sft.cargo, sft.categoria, " 
            + "	sft.presupuestado, sft.devengado, p.descripcion_programa, sp.descripcion_subprograma, "
            + "	pt.id_ceo, ceo.codigo, ceo.den, "
            + "	cuo.id, cuo.nivel, cuo.subnivel, cuo.numero, cuo.denominacion, "
            + "	pt.id_cpt, cpt.nivel, cpt.sub_nivel, cpt.nro_g, cpt.den, "
            + "CASE WHEN cpt.tit_unid = 'T' THEN true ELSE false END, "
            + "	pt.id_cpt_ef, cptf.nro, cptf.ambito, cptf.cod_proceso, cptf.den, cptf.orientacion_func, "
            + "	pt.id_cpt_ee, cpte.nro, cpte.ambito, cpte.nivel, cpte.categoria, cpte.den " 
            + "FROM salarios.sfp_informe_mensual_v3_exportar(3, 0, :mes, :anho, 0, '0') sft "
            + "LEFT JOIN legajos.funcionarios f on sft.cedula_funcionario = f.cedula_funcionario "
            + "LEFT JOIN salarios.funcionarios_categorias fc ON fc.id_funcionario_categoria = f.id_funcionario "
            + "LEFT JOIN salarios.detalles_categorias dc ON dc.id_detalle_categoria = fc.id_detalle_categoria "
            + "LEFT JOIN presupuesto.estructura_presupuestaria ep "
            + "on dc.id_estructura_presupuestaria = ep.id_estructura_presupuestaria "
            + "LEFT JOIN presupuesto.programa p ON ep.cod_programa = p.cod_programa AND ep.gestion = p.gestion "
            + "AND ep.cod_tipo = p.cod_tipo "
            + "LEFT JOIN presupuesto.subprograma sp ON p.cod_programa = sp.cod_programa "
            + "AND p.cod_tipo = sp.cod_tipo AND p.gestion = sp.gestion "
            + "AND ep.cod_subprograma = sp.cod_subprograma "
            + "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".puesto_trabajo pt on CAST(sft.cedula_funcionario AS integer) = pt.cedula_identidad "
            + "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".cpt on pt.id_cpt = cpt.id  " 
            + "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".cpt_ee cpte on pt.id_cpt_ee = cpte.id "
            + "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".cpt_ef cptf on pt.id_cpt_ee = cptf.id "
            + "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".ceo on pt.id_ceo = ceo.id "
            + "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".cuo on pt.id_cuo = cuo.id";
        
        Query query = em.createNativeQuery(sql);
        query.setParameter("anho", anho);
        query.setParameter("mes", mes);
        query.executeUpdate();
        
    }
    
    public void guardarAnexosHacienda() {
        
        StoredProcedureQuery query = em.createStoredProcedureQuery("salarios.sfp_informe_mensual_v3_exportar")
            .registerStoredProcedureParameter("in_id_administracion", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter("in_tipo", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter("in_id_mes", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter("in_gestion", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter("in_gvacante", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter("in_id_funcionario", String.class, ParameterMode.IN)
            .setParameter("in_id_administracion", 1)
            .setParameter("in_tipo", 0)
            .setParameter("in_id_mes", 6)
            .setParameter("in_gestion", 2017)
            .setParameter("in_gvacante", 0)
            .setParameter("in_id_funcionario", "0");
        query.execute();
        
        /*String sql = "select distinct sft.*, l.id_estructura_presupuestaria, "
                + "     ep.cod_programa, ep.cod_tipo, ep.gestion,  p.descripcion_programa "
                + "from salarios.sfp_informe_mensual_v3_exportar(1, 0, 1, 2016, 0, '0') sft "
                + "join v_legajo l "
                + "     on sft.cedula_funcionario = l.cedula_identidad::varchar " 
                + "left join presupuesto.estructura_presupuestaria ep "
                + "     on l.id_estructura_presupuestaria = ep.id_estructura_presupuestaria "
                + "left join presupuesto.programa p "
                + "     on ep.cod_programa = p.cod_programa and ep.gestion = p.gestion "
                + "     and ep.cod_tipo = p.cod_tipo";
        
        Query query = em.createNativeQuery(sql);*/

        List<Object[]> lista = (List<Object[]>) query.getResultList();
        System.err.println(lista.size());
        //List<Anexos> anexos = new ArrayList<>();
        /*for (int i = 0; i < 2000; i++) {
            Object[] datos = lista.get(i);
            Anexos anexo = new Anexos(datos);
            AnexosPK pk = anexo.getId();
            String sql = "SELECT d FROM DetalleAnexo d WHERE d.cedulaIdentidad = :cedulaIdentidad";
            Query q = em.createQuery(sql);
            q.setParameter("cedulaIdentidad", anexo.getCedulaIdentidad());
            
            List<DetalleAnexo> estructuraPresupuestaria = q.getResultList();
            if (estructuraPresupuestaria != null && estructuraPresupuestaria.size() > 0) {
                DetalleAnexo detalle = estructuraPresupuestaria.get(0);
                if (detalle != null) {
                    anexo.setPrograma(detalle.getDescripcionPrograma());
                    anexo.setCoordinacionDpto(detalle.getDescripcionSubPrograma());
                }
            }
            
            System.err.println(pk.getAnho() + " - " + pk.getMes() + " - " + pk.getCedulaIdentidad()
                + " - " + pk.getLinea() + " - " + pk.getConcepto() + " - " + pk.getObjetoGasto());
            //em.persist(anexo);
        }*/
        
    }
    
    public void comparativo(int anhoInicio, int mesInicio, int anhoFinal, int mesFinal, long idProceso) 
    		throws AppException, IOException {
    	
    	InputStream is = getClass().getResourceAsStream("/comparativo_mh.sql");
    	BufferedReader br1 = new BufferedReader(new InputStreamReader(is));
    	
    	StringBuilder sb = new StringBuilder();
    	try {
			String line = br1.readLine();
			while (line != null) {
				sb.append(line + "\n");
				line = br1.readLine();
			}
    	} finally {
    		is.close();
			br1.close();
		}
    	
    	String sql = sb.toString();
    	sql = sql.replace(":anhoFinal", anhoFinal + "");
    	sql = sql.replace(":anhoInicio", anhoInicio + "");
    	sql = sql.replace(":mesFinal", mesFinal + "");
    	sql = sql.replace(":mesInicio", mesInicio + "");
    	
    	System.err.println(sql);
    	sql = "SELECT id, codigo, den from cte.ceo";
    	Query query = em.createNativeQuery(sql);
    	List<Object[]> lista = (List<Object[]>) query.getResultList();
        
        System.err.println(lista.size());
        
        // Generar Reporte
        InputStream fis = getClass().getResourceAsStream("/TemplateComparativo.xlsx");
    	
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        /*int rowNum = 7;
        System.out.println("Creating excel");

        for (Object[] datos : lista) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 2;
            for (Object field : datos) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }
        
        // Vacantes Final
    	is = getClass().getResourceAsStream("/vacantes_final_mh.sql");
    	br1 = new BufferedReader(new InputStreamReader(is));
    	
    	sb = new StringBuilder();
    	try {
			String line = br1.readLine();
			int i = 1;
			while (line != null) {
				sb.append(line + "\n");
				line = br1.readLine();
			}
    	} finally {
    		is.close();
			br1.close();
		}
    	
    	sql = sb.toString();
    	sql = sql.replace(":anho", anhoFinal + "");
    	sql = sql.replace(":mes", mesFinal + "");
    	query = em.createNativeQuery(sql);
    	lista = (List<Object[]>) query.getResultList();
    	for (Object[] datos : lista) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 2;
            for (Object field : datos) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }
    	
    	// Vacantes Inicial
    	is = getClass().getResourceAsStream("/vacantes_inicio_mh.sql");
    	br1 = new BufferedReader(new InputStreamReader(is));
    	
    	sb = new StringBuilder();
    	try {
			String line = br1.readLine();
			int i = 1;
			while (line != null) {
				sb.append(line + "\n");
				line = br1.readLine();
			}
    	} finally {
    		is.close();
			br1.close();
		}
    	
    	sql = sb.toString();
    	sql = sql.replace(":anho", anhoInicio + "");
    	sql = sql.replace(":mes", mesInicio + "");
    	query = em.createNativeQuery(sql);
    	lista = (List<Object[]>) query.getResultList();  	
    	for (Object[] datos : lista) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 2;
            for (Object field : datos) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        try {
        	//File file = File.createTempFile("temp-file-name", ".tmp");
            //FileOutputStream outputStream = new FileOutputStream(file);
        	String path = getPathReporte(idProceso);
        	FileOutputStream outputStream = new FileOutputStream(path);
        	workbook.write(outputStream);
            workbook.close();
            actualizarEstadoReporte("T", idProceso);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            actualizarEstadoReporte("F", idProceso);
        } catch (IOException e) {
            e.printStackTrace();
            actualizarEstadoReporte("F", idProceso);
        } finally {
        	fis.close();
        }*/
    	
    	Writer writer = null;

    	try {
    		
    		String dataPath = System.getProperty("jboss.server.data.dir");
    	    /*writer = new BufferedWriter(new OutputStreamWriter(
    	          new FileOutputStream(dataPath + "/filename.txt"), "utf-8"));
    	    writer.write("Something");*/
    		FileOutputStream outputStream = new FileOutputStream(dataPath + "/comparativo.xls");
        	workbook.write(outputStream);
            workbook.close();
            
    	} catch (IOException ex) {
    	    // Report
    	} finally {
    		fis.close();
    	   //try {writer.close();} catch (Exception ex) {/*ignore*/}
    	}
        
    }
    
    public void actualizarEstadoReporte(String estado, Long idProceso) {
    	ReporteComparativo r = em.find(ReporteComparativo.class, idProceso);
    	if (r != null) {
    		r.setEstado(estado);
    		em.merge(r);
    		em.flush();
		}
    }
    
    public long getIdProceso(String ipCreacion) throws IOException {
    	Usuario user = getCurrentUser();
    	ReporteComparativo r = new ReporteComparativo();
    	r.setEstado("P");
    	r.setFechaCreacion(new Date());
    	r.setUsuarioCreacion(user.getId());
    	r.setIpCreacion(ipCreacion);
    	r.setPathArchivo("");
    	em.persist(r);
    	em.flush();
    	return r.getId();
    }
    
    public void actualizarPathReporte(String path, Long idProceso) {
    	ReporteComparativo r = em.find(ReporteComparativo.class, idProceso);
    	if (r != null) {
    		r.setPathArchivo(path + "/reporte_comparativo_" + idProceso + ".xls");
    		em.merge(r);
    		em.flush();
		}
    }
    
    public String getEstadoReporte(Long idProceso) throws IOException {
    	ReporteComparativo r = em.find(ReporteComparativo.class, idProceso);
    	if (r != null) {
    		return r.getEstado();
		}
    	return "";
    }
    
    public Long getUltimoProceso(Long userId) {
    	String sql = "SELECT MAX(r.id) FROM ReporteComparativo WHERE usuarioCreacion = :userId";
    	Query q = em.createQuery(sql);
    	q.setParameter("userId", userId);
    	Long id = (Long) q.getSingleResult();
    	return id;
    }
    
    public HashMap<String, Object> getUltimoPeriodo() {
    	String sql = "select distinct id.anho, id.mes from Anexos order by id.anho desc, id.mes desc";
    	Query q = em.createQuery(sql);
    	q.setMaxResults(10);
    	List<Object[]> lista = (List<Object[]>) q.getResultList();
    	HashMap<String, Object> res = new HashMap<String, Object>();
    	Object[] valores = lista.get(0);
    	res.put("anho", valores[0]);
    	res.put("mes", valores[1]);
    	return res;
    }
    
    public String getPathReporte(Long idProceso) throws IOException {
    	ReporteComparativo r = em.find(ReporteComparativo.class, idProceso);
    	if (r != null) {
    		return r.getPathArchivo();
		}
    	return "";
    }
    
    public Usuario getCurrentUser() {
        return session.getCurrentUser();
    }
    
}

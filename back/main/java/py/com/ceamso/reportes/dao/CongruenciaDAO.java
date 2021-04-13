/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.dao;

import java.math.BigDecimal;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.reportes.dto.MatrizDTO;
import py.com.ceamso.reportes.dto.PuestoRemuneracionDTO;
import py.com.ceamso.utils.Constantes;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import py.com.ceamso.reportes.dto.CeldaMatriz;
import py.com.ceamso.reportes.dto.ResumenMatriz;
import static py.com.ceamso.utils.Utils.round;

/**
 * @author mbaez
 */
public class CongruenciaDAO {
    
    private static final String ROJO = "red";
    private static final String VERDE = "#00ff00";
    private static final String AMARILLO = "yellow";

    @PersistenceContext(unitName = "CTEPostgresPU")
    protected EntityManager em;

    @Inject
    @Configuracion("esHacienda")
    private String esHacienda;

    public List<MatrizDTO> getCPTvsCTS(Integer anho, Integer mes, String vinculacion, 
    		String conceptoPago, String programa, String subprograma, String idCpt, 
    		String idCptee, String idCeo, String idCptEf, String ambito) {

        if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {

            StringBuilder query = new StringBuilder();
            /*query.append("SELECT t.nivel_cpt, ");
            query.append("case when t.numero_tramo is null THEN 30 else t.numero_tramo END AS numero_tramo, ");
            query.append("count(1) FROM (");
            query.append("SELECT s.*, cs.numero_tramo FROM (");
            query.append("SELECT a.anho, a.mes, a.cedula_identidad, a.nivel_cpt, ");
            query.append("a.vinculacion_funcionario, sum(a.presupuestado) as presupuestado ");
            query.append("FROM " + Constantes.ESQUEMA_CTE_MH + ".v_cte_anexo_liquidacion a ");
            query.append("WHERE a.nivel_cpt IS NOT NULL and a.anho = :anho AND a.mes = :mes ");*/
            query.append("SELECT t.nivel_cpt, case when t.numero_tramo is null THEN 30 else t.numero_tramo END AS numero_tramo, count(1) as cantidad ");
            query.append("FROM (SELECT s.*, cs.numero_tramo from ( ");
            query.append("select a.anho, a.mes, a.cedula_identidad, a.nivel_cpt, a.vinculacion_funcionario, sum(a.presupuestado) as presupuestado "); 
            query.append("from cte.v_cte_anexo_liquidacion a ");  
            query.append("join cte.cpt_legajos cptl on a.cedula_identidad = cptl.cedula_identidad and cptl.anho = a.anho and cptl.mes = a.mes "); 
            query.append("where a.anho = :anho and a.mes = :mes ");

            if (vinculacion != null && !vinculacion.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.vinculacion_funcionario = :vinculacion ");
            }

            if (programa != null && !programa.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.programa = :programa ");
            }

            if (subprograma != null && !subprograma.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.subprograma = :subprograma ");
            }

            if (conceptoPago == null || conceptoPago.isEmpty()) {
                query.append("AND a.concepto IN ('SUELDO', 'HONORARIOS PROFESIONALES', 'JORNALES', 'PERSONAL TECNICO') ");
            } else {
                conceptoPago = conceptoPago + "'SUELDO', 'HONORARIOS PROFESIONALES', 'JORNALES', 'PERSONAL TECNICO'";
                query.append("AND a.concepto IN (").append(conceptoPago).append(") ");
            }
            
            if (idCpt != null && !idCpt.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.id_cpt = :idcpt ");
            }
            if (idCptee != null && !idCptee.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.id_cpt_ee = :idcptee ");
            }
            if (idCeo != null && !idCeo.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.id_ceo = :idceo ");
            }
            if (idCptEf != null && !idCptEf.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.id_cpt_ef = :idcptef ");
            }

            /*query.append("GROUP BY a.anho, a.mes, a.cedula_identidad, a.nivel_cpt, a.vinculacion_funcionario) s ");
            query.append("LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".cts cs ");
            query.append("ON presupuestado >= cs.minimo AND presupuestado < cs.maximo ");
            query.append("AND cs.anho = s.anho AND cs.mes = s.mes) t ");
            query.append("GROUP BY t.nivel_cpt, numero_tramo ORDER BY nivel_cpt, numero_tramo");*/
            
            query.append("GROUP BY a.anho, a.mes, a.cedula_identidad, a.nivel_cpt, a.vinculacion_funcionario) s ");
            query.append("LEFT JOIN cte.cts cs ON presupuestado >= cs.minimo AND presupuestado < cs.maximo AND cs.anho = s.anho AND cs.mes = s.mes) t ");
            query.append("GROUP BY t.nivel_cpt, numero_tramo");

            Query q = em.createNativeQuery(query.toString());
            System.err.println(query.toString());
            q.setParameter("anho", anho);
            q.setParameter("mes", mes);
            if (vinculacion != null && !vinculacion.equalsIgnoreCase("TODOS")) {
                q.setParameter("vinculacion", vinculacion.toUpperCase());
            }
            if (programa != null && !programa.equalsIgnoreCase("TODOS")) {
                q.setParameter("programa", programa.toUpperCase());
            }
            if (subprograma != null && !subprograma.equalsIgnoreCase("TODOS")) {
                q.setParameter("subprograma", subprograma.toUpperCase());
            }
            if (idCpt != null && !idCpt.equalsIgnoreCase("TODOS")) {
                q.setParameter("idcpt", Integer.parseInt(idCpt));
            }
            if (idCptee != null && !idCptee.equalsIgnoreCase("TODOS")) {
                q.setParameter("idcptee", Integer.parseInt(idCptee));
            }
            if (idCeo != null && !idCeo.equalsIgnoreCase("TODOS")) {
               q.setParameter("idceo", Integer.parseInt(idCeo));
            }
            if (idCptEf != null && !idCptEf.equalsIgnoreCase("TODOS")) {
               q.setParameter("idcptef", Integer.parseInt(idCptEf));
            }
            
            List<Object[]> list = q.getResultList();
            List<MatrizDTO> matriz = new ArrayList<>();
            for (Object[] datos : list) {
                BigInteger row = (BigInteger) datos[1];
                String col = ((BigInteger) datos[0]).toString();
                BigInteger size = (BigInteger) datos[2];
                matriz.add(new MatrizDTO(row.longValue(), new Long(col), size.longValue()));
            }
            return matriz;

        } else {

            StringBuilder query = new StringBuilder();
            query.append("SELECT t.nivel_cpt, ");
            query.append("case when t.numero_tramo is null THEN 30 else t.numero_tramo END AS numero_tramo, ");
            query.append("count(1) FROM (");
            query.append("SELECT s.*, cs.numero_tramo FROM (");
            query.append("SELECT a.anho, a.mes, a.cedula_identidad, a.nivel_cpt, ");
            query.append("a.vinculacion_funcionario, sum(a.presupuestado) as presupuestado ");
            query.append("FROM " + Constantes.ESQUEMA_CTE_PJ + ".v_cte_anexo_liquidacion a ");
            query.append("WHERE a.nivel_cpt IS NOT NULL and a.anho = :anho AND a.mes = :mes ");
            // query.append("AND presupuestado < cs.maximo ");

            if (vinculacion != null && !vinculacion.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.vinculacion_funcionario = :vinculacion ");
            }

            if (programa != null && !programa.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.programa = :programa ");
            }

            if (subprograma != null && !subprograma.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.subprograma = :subprograma ");
            }
            
            if (ambito != null && !ambito.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.ambito_cpt_ee = :ambito ");
            }

            if (conceptoPago == null || conceptoPago.isEmpty()) {
                query.append("AND a.concepto IN ('SUELDOS', 'HONORARIOS PROFESIONALES', 'JORNALES') ");
            } else { // && conceptoPago.equalsIgnoreCase("SUELDOS")) {
                // query.append("AND (a.concepto = 'JORNALES' OR a.concepto =
                // 'SUELDO' ");
                // query.append("OR a.concepto = 'HONORARIOS PROFESIONALES') ");
                conceptoPago = conceptoPago + "'SUELDOS', 'HONORARIOS PROFESIONALES', 'JORNALES'";
                query.append("AND a.concepto IN (").append(conceptoPago).append(") ");
            }
            
            if (idCpt != null && !idCpt.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.id_cpt = :idcpt ");
            }
            if (idCptee != null && !idCptee.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.id_cpt_ee = :idcptee ");
            }
            if (idCeo != null && !idCeo.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.id_ceo = :idceo ");
            }
            if (idCptEf != null && !idCptEf.equalsIgnoreCase("TODOS")) {
                query.append(" AND a.id_cpt_ef = :idcptef ");
            }

            query.append("GROUP BY a.anho, a.mes, a.cedula_identidad, a.nivel_cpt, a.vinculacion_funcionario) s ");
            query.append("LEFT JOIN " + Constantes.ESQUEMA_CTE_PJ + ".cts cs ");
            query.append("ON presupuestado >= cs.minimo AND presupuestado < cs.maximo ");
            query.append("AND cs.anho = s.anho AND cs.mes = s.mes) t ");
            query.append("GROUP BY t.nivel_cpt, numero_tramo ORDER BY nivel_cpt, numero_tramo");

            Query q = em.createNativeQuery(query.toString());
            System.err.println("--------------- Matriz ------------------");
            System.err.println(query.toString());
            System.err.println("--------------- Matriz ------------------");
            q.setParameter("anho", anho);
            q.setParameter("mes", mes);
            if (vinculacion != null && !vinculacion.equalsIgnoreCase("TODOS")) {
                q.setParameter("vinculacion", vinculacion.toUpperCase());
            }
            if (programa != null && !programa.equalsIgnoreCase("TODOS")) {
                q.setParameter("programa", programa.toUpperCase());
            }
            if (subprograma != null && !subprograma.equalsIgnoreCase("TODOS")) {
                q.setParameter("subprograma", subprograma.toUpperCase());
            }
            if (ambito != null && !ambito.equalsIgnoreCase("TODOS")) {
                q.setParameter("ambito", ambito);
            }
            if (idCpt != null && !idCpt.equalsIgnoreCase("TODOS")) {
                q.setParameter("idcpt", idCpt.toUpperCase());
            }
            if (idCptee != null && !idCptee.equalsIgnoreCase("TODOS")) {
                q.setParameter("idcptee", idCptee.toUpperCase());
            }
            if (idCeo != null && !idCeo.equalsIgnoreCase("TODOS")) {
               q.setParameter("idceo", idCeo.toUpperCase());
            }
            if (idCptEf != null && !idCptEf.equalsIgnoreCase("TODOS")) {
               q.setParameter("idcptef", idCptEf.toUpperCase());
            }
            
            List<Object[]> list = q.getResultList();
            List<MatrizDTO> matriz = new ArrayList<>();
            for (Object[] datos : list) {
                BigDecimal row = (BigDecimal) datos[1];
                Integer col = Integer.parseInt(datos[0].toString());
                Integer size = Integer.parseInt(datos[2].toString());
                matriz.add(new MatrizDTO(row.longValue(), new Long(col), size.longValue()));
            }
            return matriz;
        }
    }
    
    public ResumenMatriz getResumenMatriz(Integer anho, Integer mes,
                String vinculacion, String conceptoPago, String programa, String subprograma,
                String idCpt,String idCptee,String idCeo,String idCptEf) {
        
        String esquema = Constantes.ESQUEMA_CTE_PJ;
        if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {
            esquema = Constantes.ESQUEMA_CTE_MH;
        }
        
        boolean filtrarVinculacion = false;
        if (vinculacion != null && !vinculacion.trim().isEmpty() 
                && !vinculacion.trim().equalsIgnoreCase("TODOS")) {
            filtrarVinculacion = true;
        }
        
        // Congruentes
        StringBuilder query = new StringBuilder();
        query.append("SELECT count(*) as col_0_0_ FROM %s.puesto_trabajo pt ");
        query.append("CROSS JOIN %s.cpt_tramos ct ");
        query.append("WHERE ct.nivel_cpt = pt.nivel_cpt AND ct.numero_tramo = pt.numero_tramo ");
        query.append("AND (pt.nivel_cpt is not null) ");
        query.append("AND pt.anho = :anho AND pt.mes = :mes AND ct.mes = :mes AND ct.anho = :anho");
        if (filtrarVinculacion) {
            query.append(" AND pt.vinculo_laboral = :vinculacion");
        }
        String sql = query.toString().replaceAll("%s", esquema);
        Query q = em.createNativeQuery(sql);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        if (filtrarVinculacion) {
            q.setParameter("vinculacion", vinculacion);
        }
        Object tmp = q.getSingleResult();
        int congruentes = 0;
        
        if (tmp.getClass().toString().contains("BigInteger")) {
            congruentes = ((BigInteger) q.getSingleResult()).intValue();
            System.err.println("Congruentes : " + sql + " - " + congruentes);
        } else {
            congruentes = (Integer) q.getSingleResult();
            System.err.println("Congruentes : " + sql + " - " + congruentes);
        }
        
        // SubCategorizados
        query = new StringBuilder();
        query.append("SELECT count(*) as col_0_0_ FROM %s.puesto_trabajo pt ");
        query.append("WHERE pt.numero_tramo < (SELECT MIN(ct.numero_tramo) ");
        query.append("FROM %s.cpt_tramos ct WHERE ct.nivel_cpt = pt.nivel_cpt)");
        query.append("AND (pt.nivel_cpt is not null) and pt.anho = :anho and pt.mes = :mes");
        if (filtrarVinculacion) {
            query.append(" AND pt.vinculo_laboral = :vinculacion");
        }
        sql = query.toString().replaceAll("%s", esquema);
        q = em.createNativeQuery(sql);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        if (filtrarVinculacion) {
            q.setParameter("vinculacion", vinculacion);
        }        
        tmp = q.getSingleResult();
        int subCategorizados = 0;
        if (tmp.getClass().toString().contains("BigInteger")) {
            subCategorizados = ((BigInteger) q.getSingleResult()).intValue();
        } else {
            subCategorizados = (Integer) q.getSingleResult();
        }
        
        // SobreCategorizados
        query = new StringBuilder();
        query.append("SELECT count(*) as col_0_0_ FROM %s.puesto_trabajo pt ");
        query.append("WHERE pt.numero_tramo > (SELECT MAX(ct.numero_tramo) ");
        query.append("FROM %s.cpt_tramos ct WHERE ct.nivel_cpt = pt.nivel_cpt) ");
        query.append("AND (pt.nivel_cpt is not null) and pt.anho = :anho and pt.mes = :mes");
        if (filtrarVinculacion) {
            query.append(" AND pt.vinculo_laboral = :vinculacion");
        }
        sql = query.toString().replaceAll("%s", esquema);
        q = em.createNativeQuery(sql);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        if (filtrarVinculacion) {
            q.setParameter("vinculacion", vinculacion);
        }
        tmp = q.getSingleResult();
        int sobreCategorizados = 0;
        if (tmp.getClass().toString().contains("BigInteger")) {
            sobreCategorizados = ((BigInteger) q.getSingleResult()).intValue();
        } else {
            sobreCategorizados = (Integer) q.getSingleResult();
        }
        
        int total = congruentes + subCategorizados + sobreCategorizados;
        double porcentajeCongruentes = calcularPorcentaje(total, congruentes);
        double porcentajeSubCategorizados = calcularPorcentaje(total, subCategorizados);
        double porcentajeSobreCategorizados = calcularPorcentaje(total, sobreCategorizados);
        
        ResumenMatriz res = new ResumenMatriz(congruentes, subCategorizados, sobreCategorizados, total);
        res.setPorcentajeCongruentes(porcentajeCongruentes);
        res.setPorcentajeSubCategorizados(porcentajeSubCategorizados);
        res.setPorcentajeSobreCategorizados(porcentajeSobreCategorizados);
        res.setColorCongruentes(getColorCongruente(porcentajeCongruentes));
        res.setColorSubCategorizados(getColorFueraRango(porcentajeSubCategorizados));
        res.setColorSobreCategorizados(getColorFueraRango(porcentajeSobreCategorizados));
        return res;
        
    }
    
    private double calcularPorcentaje(int total, int cantidad) {
        double porcentaje = cantidad * 100 / (double) total;
        porcentaje = round(porcentaje, 1);
        return porcentaje;
    }
    
    private String getColorCongruente(double porcentaje) {
        if (porcentaje < 50) {
            return ROJO;
        } else if (porcentaje >= 50 && porcentaje < 80) {
            return AMARILLO;
        } else {
            return VERDE;
        }
    }
    
    private String getColorFueraRango(double porcentaje) {
        if (porcentaje > 25) {
            return ROJO;
        } else if (porcentaje >= 10 && porcentaje <= 25) {
            return AMARILLO;
        } else {
            return VERDE;
        }
    }

    /*
	 * public List<MatrizDTO> getCPTvsCTS(Integer anho, Integer mes, String
	 * vinculacion, String conceptoPago) {
	 * 
	 * HashMap<String, Object> filtros = new HashMap<>(); filtros.put("anho",
	 * anho); filtros.put("mes", mes); if ((vinculacion != null &&
	 * "".compareTo(vinculacion) != 0)) { filtros.put("vinculacionFuncionario",
	 * vinculacion); } if ((conceptoPago != null && "".compareTo(conceptoPago)
	 * != 0)) { filtros.put("concepto", conceptoPago); }
	 * 
	 * StringBuilder sql = new StringBuilder(); sql.append("SELECT new "
	 * ).append(MatrizDTO.class.getCanonicalName()); sql.append(
	 * "(cargo.numeroTramo, cargo.nivel, count(cargo))"); sql.append(
	 * " FROM Cargo cargo"); buildWhere(sql, filtros); sql.append(
	 * " GROUP BY cargo.numeroTramo, cargo.nivel"); Query q =
	 * em.createQuery(sql.toString(), MatrizDTO.class); setParametrers(q,
	 * filtros); List<MatrizDTO> list = q.getResultList(); return list; }
     */
    public List<PuestoRemuneracionDTO> getPuestoRemuneracion(Integer anho1, Integer mes1, 
            Integer anho2, Integer mes2, String vinculacion, String idCptEf, 
            String ambito, String sexo,String idCpt,String idCptee,String idCeo) throws NoSuchFieldException {

        List<PuestoRemuneracionDTO> restultList = new ArrayList<PuestoRemuneracionDTO>();

        StringBuilder sb = new StringBuilder();

        sb.append(" SELECT new ").append(PuestoRemuneracionDTO.class.getCanonicalName());
        // sb.append(" (anex.id.mes, anex.id.anho)");
        sb.append(" (puesto.pk.mes, puesto.pk.anho)");
        // sb.append(" FROM Anexos anex");
        sb.append(" FROM PuestoTrabajo puesto");
        if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {
            // sb.append(" WHERE TO_DATE(CAST(anex.id.anho as text) || '-' ||
            // CAST(anex.id.mes as text) || '-' || '1', 'YYYY/MM/DD')");
            sb.append(" WHERE TO_DATE(CAST(puesto.pk.anho as text) || '-' || CAST(puesto.pk.mes as text) ");
            sb.append("|| '-' || '1', 'YYYY/MM/DD')");
            sb.append(" BETWEEN TO_DATE('" + anho1 + "-" + mes1 + "-1', 'YYYY/MM/DD') " 
                    + " AND TO_DATE('" + anho2 + "-"
                    + mes2 + "-1', 'YYYY/MM/DD')");
        } else {
            // sb.append(" WHERE CONVERT(DATETIME,CONVERT(varchar(10),
            // anex.id.anho) + '-' + CONVERT(varchar(10), anex.id.mes) + '-' +
            // '1', 102)");
            sb.append(" WHERE CONVERT(DATETIME,CONVERT(varchar(10), puesto.pk.anho) + '-' ");
            sb.append(" + CONVERT(varchar(10), puesto.pk.mes) + '-' + '1', 102)");
            sb.append(" BETWEEN CONVERT(DATETIME, '" + anho1 + "-" + mes1 + "-1', 102) " 
                    + " AND CONVERT(DATETIME,'"
                    + anho2 + "-" + mes2 + "-1', 102)");
        }
        sb.append(" and puesto.nivelCtp != null");
        // sb.append(" GROUP BY anex.id.mes, anex.id.anho");
        // sb.append(" ORDER BY anex.id.anho, anex.id.mes");
        sb.append(" GROUP BY puesto.pk.mes, puesto.pk.anho");
        sb.append(" ORDER BY puesto.pk.anho,puesto.pk.mes");
        Query q = em.createQuery(sb.toString(), PuestoRemuneracionDTO.class);
        System.err.println("getPuestoRemuneracion : " + sb.toString());
        List<PuestoRemuneracionDTO> fechas = q.getResultList();
        for (PuestoRemuneracionDTO fecha : fechas) {
            restultList.add(puestoRemuneracion(fecha.getAnho(), fecha.getMes(), 
                    vinculacion, idCptEf, ambito, sexo,idCpt,idCptee,idCeo));
        }
        return restultList;
    }

    public PuestoRemuneracionDTO puestoRemuneracion(Integer anho, Integer mes, String vinculacion, String idCptEf,
            String ambito, String sexo,String idCpt,String idCptee,String idCeo) {

        StringBuilder sqlEnRango = new StringBuilder();
        StringBuilder sqlSubRango = new StringBuilder();
        StringBuilder sqlSobreRango = new StringBuilder();

        HashMap<String, Object> filtros = new HashMap<String, Object>();
        filtros.put("vinculacion", vinculacion);
        filtros.put("idCptEf", idCptEf);
        filtros.put("ambito", ambito);
        filtros.put("sexo", sexo);
        filtros.put("idCpt", idCpt);
        filtros.put("idCptEe", idCptee);
        filtros.put("idCeo", idCeo);

        sqlEnRango.append(" SELECT count(*)");
        sqlEnRango.append(" FROM PuestoTrabajo pt, CptTramos cptt");
        sqlEnRango.append(" WHERE cptt.pk.nivelCpt = pt.nivelCtp");
        sqlEnRango.append(" AND cptt.pk.numeroTramo = pt.numeroTramo");
        sqlEnRango.append(" AND pt.nivelCtp is not null");
        sqlEnRango.append(" AND pt.pk.anho = " + anho);
        sqlEnRango.append(" AND pt.pk.mes = " + mes);
        sqlEnRango.append(" AND cptt.pk.mes = " + mes);
        sqlEnRango.append(" AND cptt.pk.anho = " + anho);
        buildFiltros(sqlEnRango, filtros);
        Query q = em.createQuery(sqlEnRango.toString());
        Long enRango = (Long) q.getSingleResult();
        System.err.println(sqlEnRango.toString());

        sqlSubRango.append(" SELECT count(*)");
        sqlSubRango.append(" FROM PuestoTrabajo pt");
        sqlSubRango.append(" WHERE pt.numeroTramo < " + " (SELECT MIN(tram.pk.numeroTramo) " + " FROM CptTramos tram "
                + " WHERE tram.pk.nivelCpt = pt.nivelCtp)");
        sqlSubRango.append(" AND pt.nivelCtp is not null");
        sqlSubRango.append(" AND pt.pk.anho = " + anho);
        sqlSubRango.append(" AND pt.pk.mes = " + mes);
        buildFiltros(sqlSubRango, filtros);
        q = em.createQuery(sqlSubRango.toString());
        System.err.println(sqlSubRango.toString());
        Long subRango = (Long) q.getSingleResult();

        sqlSobreRango.append(" SELECT count(*)");
        sqlSobreRango.append(" FROM PuestoTrabajo pt");
        sqlSobreRango.append(" WHERE pt.numeroTramo > " + " (SELECT MAX(tram.pk.numeroTramo) " + " FROM CptTramos tram "
                + " WHERE tram.pk.nivelCpt = pt.nivelCtp)");
        sqlSobreRango.append(" AND pt.nivelCtp is not null");
        sqlSobreRango.append(" AND pt.pk.anho = " + anho);
        sqlSobreRango.append(" AND pt.pk.mes = " + mes);
        buildFiltros(sqlSobreRango, filtros);
        q = em.createQuery(sqlSobreRango.toString());
        System.err.println(sqlSobreRango.toString());
        Long sobreRango = (Long) q.getSingleResult();

        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT new ").append(PuestoRemuneracionDTO.class.getCanonicalName());
        sb.append(" (" + mes + ", " + anho + ", " + enRango + "L, " + sobreRango + "L, " 
                + subRango + "L, count(*))");
        sb.append(" FROM PuestoTrabajo pt");
        sb.append(" WHERE pt.nivelCtp is not null");
        sb.append(" AND pt.pk.anho = " + anho);
        sb.append(" AND pt.pk.mes = " + mes);
        buildFiltros(sb, filtros);
        q = em.createQuery(sb.toString());
        //System.err.println(sb.toString());

        return (PuestoRemuneracionDTO) q.getSingleResult();
    }

    /**
     * @param sb
     * @param filtros
     */
    public void buildFiltros(StringBuilder sb, HashMap<String, Object> filtros) {

        String vinculacion = String.valueOf(filtros.get("vinculacion"));
        String idCptEf = String.valueOf(filtros.get("idCptEf"));
        String ambito = String.valueOf(filtros.get("ambito"));
        String sexo = String.valueOf(filtros.get("sexo"));
        String idCpt = String.valueOf(filtros.get("idCpt"));
        String idCptee = String.valueOf(filtros.get("idCptEe"));
        String idCeo = String.valueOf(filtros.get("idCeo"));
        

        if (vinculacion != null && !vinculacion.equalsIgnoreCase("TODOS")) {
            sb.append(" AND pt.vinculoLaboral = '" + vinculacion + "'");
        }
        if (idCptEf != null && !idCptEf.equalsIgnoreCase("TODOS")) {
            sb.append(" AND pt.cptF.id = " + Integer.valueOf(idCptEf));
        }
        if (ambito != null && !ambito.equalsIgnoreCase("TODOS")) {
            sb.append(" AND pt.cptE.ambito.nombreAmbito = '" + ambito + "'");
        }
        if (sexo != null && !sexo.equalsIgnoreCase("TODOS")) {
            if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {

            } else {
                if (sexo.equals("M")) {
                    sexo = "1";
                } else {
                    sexo = "0";
                }

            }

            sb.append(" AND pt.sexo = '" + sexo + "'");
        }
        if (idCpt != null && !idCpt.equalsIgnoreCase("TODOS")) {
            sb.append(" AND pt.cpt.id = " + Integer.valueOf(idCpt));
        }
        if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {
        	
        }else{
        	 if (idCptee != null && !idCptee.equalsIgnoreCase("TODOS")) {
                 sb.append(" AND pt.cptE.id = " + Integer.valueOf(idCptee));
             }
        }
       
        if (idCeo != null && !idCeo.equalsIgnoreCase("TODOS")) {
            sb.append(" AND pt.ceo.id = " + Integer.valueOf(idCeo));
        }
    }

    /**
     * @param sb
     * @param filtros
     */
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1;
        sb.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {

                sb.append(" LOWER(cargo.").append(key).append(") LIKE LOWER(:").append(key).append(")");
            } else {
                sb.append("cargo.").append(key).append(" = :").append(key);
            }
            // se añade el 'AND' si hay más caracteres.
            if (token < tokens) {
                sb.append(" AND ");
            }
            token++;
        }
    }

    /**
     * @param q
     * @param filtros
     */
    public void setParametrers(Query q, HashMap<String, Object> filtros) {
        if (filtros == null) {
            return;
        }
        for (String key : filtros.keySet()) {
            Object value = filtros.get(key);
            if (filtros.get(key) instanceof String) {
                if (key.equals("vinculacionFuncionario") && filtros.get(key).equals("TODOS")) {
                    value = "";
                }
                value = "%" + value + "%";
            }
            q.setParameter(key, value);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.dao;

import py.com.ceamso.config.Configuracion;
import py.com.ceamso.reportes.dto.GestionPersonasDTO;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.toIntExact;

import java.math.BigDecimal;
import java.math.BigInteger;

import py.com.ceamso.utils.Constantes;

/**
 * @author marcelo.szcerba
 */
public class GestionPersonasDAO {

    @Inject
    @Configuracion("esHacienda")
    private String esHacienda;

    @PersistenceContext(unitName = "CTEPostgresPU")
    protected EntityManager em;

    private boolean esHacienda() {
        return esHacienda != null && esHacienda.equalsIgnoreCase("S");
    }

    public List<GestionPersonasDTO> getListaGestionPersonas(Integer anho1, Integer mes1, Integer anho2, 
            Integer mes2, String vinculacion, String idCptEf, String ambito, String sexo, String idCpt, 
            String idCptee, String idCeo) throws NoSuchFieldException {

        List<GestionPersonasDTO> restultList = new ArrayList<GestionPersonasDTO>();

        StringBuilder sb = new StringBuilder();

        sb.append(" SELECT new ").append(GestionPersonasDTO.class.getCanonicalName());
        sb.append(" (pt.pk.mes,pt.pk.anho)");
        sb.append(" FROM PuestoTrabajo pt");

        if (esHacienda()) {
            sb.append(" WHERE TO_DATE(CAST(pt.pk.anho as text) || '-' || ");
            sb.append(" CAST(pt.pk.mes as text) || '-' || '1', 'YYYY/MM/DD')");
            sb.append(" BETWEEN TO_DATE('" + anho1 + "-" + mes1 + "-1', 'YYYY/MM/DD') " + " AND TO_DATE('" 
            		+ anho2 + "-" + mes2 + "-1', 'YYYY/MM/DD')");
        } else {
            sb.append(" WHERE CONVERT(DATETIME,CONVERT(varchar(10), pt.pk.anho) ");
            sb.append(" + '-' + CONVERT(varchar(10), pt.pk.mes) + '-' + '1', 102)");
            sb.append(" BETWEEN CONVERT(DATETIME, '" + anho1 + "-" + mes1 + "-1', 102) ");
            sb.append(" AND CONVERT(DATETIME,'" + anho2 + "-" + mes2 + "-1', 102)");
        }
        sb.append(" GROUP BY pt.pk.mes, pt.pk.anho");
        sb.append(" ORDER BY pt.pk.anho, pt.pk.mes");

        Query q = em.createQuery(sb.toString(), GestionPersonasDTO.class);
        List<GestionPersonasDTO> fechas = q.getResultList();
        Integer mesInicial = 0;
        Integer anhoInicial = 0;
        for (GestionPersonasDTO fecha : fechas) {
            if (restultList.size() == 0) {
                mesInicial = fecha.getMes();
                anhoInicial = fecha.getAnho();
                System.err.println("Parametros : " + anhoInicial + " - " + mesInicial);
                restultList.add(getGestionPersonas(anhoInicial, mesInicial, null, null, 
                        vinculacion, idCptEf, ambito, sexo, idCpt, idCptee, idCeo));
            } else {
            	System.err.println("Parametros : " + anhoInicial + " ---- " + mesInicial + " - " 
            				+ fecha.getAnho() + " - " + fecha.getMes());
                restultList.add(getGestionPersonas(anhoInicial, mesInicial, fecha.getAnho(), fecha.getMes(),
                        vinculacion, idCptEf, ambito, sexo, idCpt, idCptee, idCeo));
            }
        }
        return restultList;
    }

    public GestionPersonasDTO getGestionPersonas(Integer anho1, Integer mes1, Integer anho2, Integer mes2,
            String vinculacion, String idCptEf, String ambito, String sexo, String idCpt, String idCptEe,
            String idCeo) {

        HashMap<String, Object> filtros = new HashMap<String, Object>();

        filtros.put("vinculacion", vinculacion);
        filtros.put("idCptEf", idCptEf);
        filtros.put("idCptEe", idCptEe);
        filtros.put("ambito", ambito);
        filtros.put("sexo", sexo);
        filtros.put("idCpt", idCpt);
        filtros.put("idCeo", idCeo);

        StringBuilder sqlAnho = new StringBuilder();

        String esquema = Constantes.ESQUEMA_CTE_PJ;
        if (esHacienda()) {
            esquema = Constantes.ESQUEMA_CTE_MH;
        }

        if (anho2 == null && mes2 == null) {
        	
            // cantidad de cargos totales correspondiente al primer año
            if (esHacienda()) {
            	
	        	/*sqlAnho.append(" SELECT new ").append(GestionPersonasDTO.class.getCanonicalName());
	            sqlAnho.append(" (" + mes1 + ", " + anho1 + ", COUNT(DISTINCT puesto.pk.cedulaIdentidad), 0, 0, 0)");
	            sqlAnho.append(" FROM PuestoTrabajo puesto");
	            if (esHacienda()) {
	                sqlAnho.append(" WHERE TO_DATE(CAST(puesto.pk.anho as text) || '-' ");
	                sqlAnho.append("|| CAST(puesto.pk.mes as text) || '-' || '1', 'YYYY/MM/DD')");
	                sqlAnho.append(" <= TO_DATE('" + anho1 + "-" + mes1 + "-1', 'YYYY/MM/DD')");
	            } else {
	                sqlAnho.append(" WHERE CONVERT(DATETIME,CAST(puesto.pk.anho as text) + '-' ");
	                sqlAnho.append(" + CAST(puesto.pk.mes as text) + '-' + '1', 102)");
	                sqlAnho.append(" <= CONVERT(DATETIME,'" + anho1 + "-" + mes1 + "-1', 102)");
	            }
	            sqlAnho.append(" AND puesto.pk.cedulaIdentidad != 0");
	            filtros.put("tableKey", "puesto");
	            buildFiltros(sqlAnho, filtros);*/
            	// Total de cedulas
            	long cargosTotales = getCargosTotalesHacienda(anho1, mes1, vinculacion, ambito, 
            			sexo, idCpt, idCptEe, idCptEf, idCeo);
	            GestionPersonasDTO dto = new GestionPersonasDTO(mes1, anho1, cargosTotales, 0, 0, 0);
	            return dto;
	            
            } else {
            
	            // Total de cedulas
            	long cargosTotales = getCargosTotales(anho1, mes1, vinculacion, ambito, 
            			sexo, idCpt, idCptEe, idCptEf, idCeo);
	            GestionPersonasDTO dto = new GestionPersonasDTO(mes1, anho1, cargosTotales, 0, 0, 0);
	            return dto;
            }
            
        } else {
        	
            boolean acum = false;

            Long egresos = 0L;
            Long ingresos = 0L;
            Query qIngresos;
            Query qEgresos;

            if (acum == false) {
                /* comparacion contra la fecha base */

                // cantidad de egresos
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT ABS(");
                filtros.put("anho1", anho1);
                filtros.put("mes1", mes1);
                filtros.put("anho2", anho2);
                filtros.put("mes2", mes2);
                sb.append(buildQuery(filtros));
                sb.append(") from PuestoTrabajo");
                qEgresos = em.createQuery(sb.toString());
                List<Long> res = (List<Long>) qEgresos.getResultList();
                egresos = res.get(0);

                // cantidad de ingresos
                sb = new StringBuilder();
                sb.append("SELECT ABS(");
                filtros.put("anho1", anho2);
                filtros.put("mes1", mes2);
                filtros.put("anho2", anho1);
                filtros.put("mes2", mes1);

                sb.append(buildQuery(filtros));
                sb.append(") from PuestoTrabajo");
                qIngresos = em.createQuery(sb.toString());
                res = (List<Long>) qIngresos.getResultList();
                ingresos = res.get(0);
                
            } else {
                /* comparacion acumulable */
                int difA = (anho2 - anho1) * 12;
                int difM = difA + mes2 - mes1;
                int vAnho1, vMes1, vAnho2, vMes2 = 0;
                egresos = 0L;
                ingresos = 0L;
                StringBuilder sb = new StringBuilder();
                if (difA != 0 || difM != 0) {
                    // cantidad de egresos
                    sb.append("SELECT ABS(");
                    for (int i = mes1; i - mes1 + 1 <= difM; i++) {
                        vAnho1 = ((i - 1) / 12 > 0) ? (anho1 + (i - 1) / 12) : anho1;
                        vMes1 = ((i - 1) / 12 > 0) ? ((i - 1) / 12 + (i - 1) % 12) : i;
                        vAnho2 = (i / 12 > 0) ? anho1 + (i / 12) : anho1;
                        vMes2 = ((i / 12) > 0) ? ((i / 12) + (i % 12)) : i + 1;
                        filtros.put("anho1", vAnho1);
                        filtros.put("mes1", vMes1);
                        filtros.put("anho2", vAnho2);
                        filtros.put("mes2", vMes2);
                        sb.append(buildQuery(filtros));
                        if (i - mes1 + 2 <= difM) {
                            sb.append(" + ");
                        } else {
                            sb.append(") from PuestoTrabajo");
                        }
                    }
                    System.out.println("egreso sql");
                    qEgresos = em.createQuery(sb.toString());
                    egresos = (Long) qEgresos.getSingleResult();
                    sb = new StringBuilder();

                    // cantidad de ingresos
                    sb = new StringBuilder();
                    sb.append("SELECT ABS(");
                    for (int i = mes1; i - mes1 + 1 <= difM; i++) {
                        vAnho1 = ((i - 1) / 12 > 0) ? (anho1 + (i - 1) / 12) : anho1;
                        vMes1 = ((i - 1) / 12 > 0) ? ((i - 1) / 12 + (i - 1) % 12) : i;
                        vAnho2 = (vMes1 == 1) ? (anho1 - 1) : anho1;
                        vMes2 = (vMes1 == 1) ? 12 : vMes1 - 1;
                        filtros.put("anho1", vAnho1);
                        filtros.put("mes1", vMes1);
                        filtros.put("anho2", vAnho2);
                        filtros.put("mes2", vMes2);
                        sb.append(buildQuery(filtros));
                        if (i - mes1 + 2 <= difM) {
                            sb.append(" + ");
                        } else {
                            sb.append(") from PuestoTrabajo");
                        }
                    }

                    qIngresos = em.createQuery(sb.toString());
                    ingresos = (Long) qIngresos.getSingleResult();

                } else {
                    // cantidad de egresos
                    sb.append("SELECT ABS(");
                    vAnho2 = (mes1 == 12) ? (anho1 + 1) : anho1;
                    vMes2 = (mes1 == 12) ? 1 : mes1 + 1;
                    filtros.put("anho1", anho1);
                    filtros.put("mes1", mes1);
                    filtros.put("anho2", vAnho2);
                    filtros.put("mes2", vMes2);
                    sb.append(buildQuery(filtros));
                    sb.append(") from PuestoTrabajo");
                    System.out.println("egreso sql else");
                    qEgresos = em.createQuery(sb.toString());
                    egresos = (Long) qEgresos.getSingleResult();

                    // cantidad de ingresos
                    sb = new StringBuilder();
                    sb.append("SELECT ABS(");
                    vAnho2 = (mes1 == 1) ? (anho1 - 1) : anho1;
                    vMes2 = (mes1 == 1) ? 12 : (mes1 - 1);
                    filtros.put("anho2", vAnho2);
                    filtros.put("mes2", vMes2);
                    sb.append(buildQuery(filtros));
                    sb.append(") from PuestoTrabajo");
                    System.out.println("ingres sql else");
                    qIngresos = em.createQuery(sb.toString());
                    ingresos = (Long) qIngresos.getSingleResult();
                }
            }
            
            // Nuevos Cargos
            StringBuilder sbCargos = new StringBuilder();
            sbCargos.append("SELECT CASE WHEN s.cantidad1 > s.cantidad2 THEN s.cantidad1 - s.cantidad2 ");
            sbCargos.append("ELSE 0 END FROM (select (select count(distinct cedula_identidad) ");
            sbCargos.append("FROM ").append(esquema).append(".v_cte_anexo_liquidacion WHERE anho = ").append(anho2);
            sbCargos.append(" AND mes = ").append(mes2).append(") as cantidad1, t.cantidad2 ");
            sbCargos.append("FROM (SELECT count(distinct cedula_identidad) as cantidad2 ");
            sbCargos.append("FROM ").append(esquema).append(".v_cte_anexo_liquidacion WHERE anho = ").append(anho1);
            sbCargos.append(" AND mes = ").append(mes1).append(") t) s");
            Query qCantCargo = em.createNativeQuery(sbCargos.toString());

            Long nuevosCargos = 0l;
            if (esHacienda()) {
                BigInteger tmp = (BigInteger) qCantCargo.getSingleResult();
                if (tmp != null) {
                    nuevosCargos = tmp.longValue();
                }
            } else {
                Integer tmp = (Integer) qCantCargo.getSingleResult();
                if (tmp != null) {
                    nuevosCargos = tmp.longValue();
                }
            }

            // Query qNuevosCargos = em.createQuery(sb.toString());
            // Long nuevosCargos = (Long) qNuevosCargos.getSingleResult();
            // Long nuevosCargos = 0L;
            // cantidad de cargos correspondientes al año 2
            
            if (esHacienda()) {
            
	            /*sqlAnho.append(" SELECT new ").append(GestionPersonasDTO.class.getCanonicalName());
	            sqlAnho.append(" (" + mes2 + ", " + anho2 + ", COUNT(DISTINCT puesto.pk.cedulaIdentidad), ");
	            sqlAnho.append(toIntExact(egresos) + ", " + toIntExact(ingresos) + ", ");
	            sqlAnho.append(toIntExact(nuevosCargos) + ")");
	            sqlAnho.append(" FROM PuestoTrabajo puesto");
	            if (esHacienda()) {
	                sqlAnho.append(" WHERE TO_DATE(CAST(puesto.pk.anho as text) || '-' ");
	                sqlAnho.append(" || CAST(puesto.pk.mes as text) || '-' || '1', 'YYYY/MM/DD')");
	                sqlAnho.append("");
	                sqlAnho.append(" <= TO_DATE('" + anho2 + "-" + mes2 + "-1', 'YYYY/MM/DD')");
	            } else {
	                sqlAnho.append(" WHERE CONVERT(DATETIME,CAST(puesto.pk.anho as text) + '-' ");
	                sqlAnho.append(" + CAST(puesto.pk.mes as text) + '-' + '1', 102)");
	                sqlAnho.append(" <= CONVERT(DATETIME,'" + anho2 + "-" + mes2 + "-1', 102)");
	            }
	            sqlAnho.append(" AND puesto.pk.cedulaIdentidad != 0");
	            filtros.put("tableKey", "puesto");
	            buildFiltros(sqlAnho, filtros);*/
            	long cargosTotales = getCargosTotalesHacienda(anho2, mes2, vinculacion, ambito, 
            			sexo, idCpt, idCptEe, idCptEf, idCeo);
	            GestionPersonasDTO dto = new GestionPersonasDTO(mes2, anho2, new Long(cargosTotales), 
	            		toIntExact(egresos), toIntExact(ingresos), toIntExact(nuevosCargos));
	            return dto;
	            
            } else {
            
            	long cargosTotales = getCargosTotales(anho2, mes2, vinculacion, ambito, 
            			sexo, idCpt, idCptEe, idCptEf, idCeo);
	            GestionPersonasDTO dto = new GestionPersonasDTO(mes2, anho2, new Long(cargosTotales), 
	            		toIntExact(egresos), toIntExact(ingresos), toIntExact(nuevosCargos));
	            return dto;
            }
        }

        /*Query qFirst = em.createQuery(sqlAnho.toString(), GestionPersonasDTO.class);
        System.err.println(sqlAnho.toString());
        return (GestionPersonasDTO) qFirst.getSingleResult();*/
    }

    public List<GestionPersonasDTO> getListaDesarrolloPersonal(Integer anho1, Integer mes1, Integer anho2, Integer mes2,
            String vinculacion, String idCptEf, String ambito, String sexo, String idCpt, String idCptee, String idCeo)
            throws NoSuchFieldException {

        List<GestionPersonasDTO> restultList = new ArrayList<GestionPersonasDTO>();

        StringBuilder sb = new StringBuilder();

        sb.append(" SELECT new ").append(GestionPersonasDTO.class.getCanonicalName());
        sb.append(" (puesto.pk.mes, puesto.pk.anho)");
        sb.append(" FROM PuestoTrabajo puesto");

        if (esHacienda()) {
            sb.append(" WHERE TO_DATE(CAST(puesto.pk.anho as text) || '-' ");
            sb.append(" || CAST(puesto.pk.mes as text) || '-' || '1', 'YYYY/MM/DD')");
            sb.append(" BETWEEN TO_DATE('" + anho1 + "-" + mes1 + "-1', 'YYYY/MM/DD') " + " AND TO_DATE('" + anho2 + "-"
                    + mes2 + "-1', 'YYYY/MM/DD')");
        } else {
            sb.append(" WHERE CONVERT(DATETIME,CONVERT(varchar(10), puesto.pk.anho) ");
            sb.append(" + '-' + CONVERT(varchar(10), puesto.pk.mes) + '-' + '1', 102)");
            sb.append(" BETWEEN CONVERT(DATETIME, '" + anho1 + "-" + mes1 + "-1', 102) " + " AND CONVERT(DATETIME,'"
                    + anho2 + "-" + mes2 + "-1', 102)");
        }
        sb.append(" GROUP BY puesto.pk.mes, puesto.pk.anho");
        sb.append(" ORDER BY puesto.pk.anho, puesto.pk.mes");

        Query q = em.createQuery(sb.toString(), GestionPersonasDTO.class);
        List<GestionPersonasDTO> fechas = q.getResultList();
        Integer mesInicial = 0;
        Integer anhoInicial = 0;
        for (GestionPersonasDTO fecha : fechas) {
            if (restultList.size() == 0) {
                mesInicial = fecha.getMes();
                anhoInicial = fecha.getAnho();
                restultList.add(getDesarrolloPersonal(anhoInicial, mesInicial, null, null, vinculacion, idCptEf, ambito,
                        sexo, idCpt, idCptee, idCeo));
            } else {
                restultList.add(getDesarrolloPersonal(anhoInicial, mesInicial, fecha.getAnho(), fecha.getMes(),
                        vinculacion, idCptEf, ambito, sexo, idCpt, idCptee, idCeo));
            }
        }
        return restultList;
    }

    public GestionPersonasDTO getDesarrolloPersonal(Integer anho1, Integer mes1, Integer anho2, Integer mes2,
            String vinculacion, String idCptEf, String ambito, String sexo, String idCpt, String idCptee,
            String idCeo) {

        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("vinculacion", vinculacion);
        filtros.put("idCptEf", idCptEf);
        filtros.put("ambito", ambito);
        filtros.put("sexo", sexo);
        filtros.put("idCpt", idCpt);
        filtros.put("idCptEe", idCptee);
        filtros.put("idCeo", idCeo);

        StringBuilder sqlAnho = new StringBuilder();

        if (anho2 == null && mes2 == null) {
        	
            // cantidad de cargos totales correspondiente al primer año
        	if (esHacienda()) {
        		
	            /*sqlAnho.append(" SELECT new ").append(GestionPersonasDTO.class.getCanonicalName());
	            sqlAnho.append(" (" + mes1 + ", " + anho1);
	            sqlAnho.append(", COUNT(DISTINCT puesto.pk.cedulaIdentidad), 0, 0, 0.0, 0.0, 0L, 0L)");
	            sqlAnho.append(" FROM PuestoTrabajo puesto");
	            if (esHacienda()) {
	                sqlAnho.append(" WHERE TO_DATE(CAST(puesto.pk.anho as text) || '-' ");
	                sqlAnho.append(" || CAST(puesto.pk.mes as text) || '-' || '1', 'YYYY/MM/DD')");
	                sqlAnho.append(" <= TO_DATE('" + anho1 + "-" + mes1 + "-1', 'YYYY/MM/DD')");
	            } else {
	                sqlAnho.append(" WHERE CONVERT(DATETIME,CAST(puesto.pk.anho as text) + '-' ");
	                sqlAnho.append("+ CAST(puesto.pk.mes as text) + '-' + '1', 102)");
	                sqlAnho.append(" <= CONVERT(DATETIME,'" + anho1 + "-" + mes1 + "-1', 102)");
	            }
	            sqlAnho.append(" AND puesto.pk.cedulaIdentidad != 0");
	            filtros.put("tableKey", "puesto");
	            buildFiltros(sqlAnho, filtros);*/
        		
        		long cargosTotales = getCargosTotalesHacienda(anho1, mes1, vinculacion, ambito, 
            			sexo, idCpt, idCptee, idCptEf, idCeo);
	            GestionPersonasDTO dto = new GestionPersonasDTO(mes1, anho1, new Long(cargosTotales), 
	            		0, 0, 0.0, 0.0, 0L, 0L);
	            return dto;
	            
        	} else {
        		
        		long cargosTotales = getCargosTotales(anho1, mes1, vinculacion, ambito, 
            			sexo, idCpt, idCptee, idCptEf, idCeo);
	            GestionPersonasDTO dto = new GestionPersonasDTO(mes1, anho1, new Long(cargosTotales), 
	            		0, 0, 0.0, 0.0, 0L, 0L);
	            return dto;
        	}
        	
        } else {

            boolean acum = false;
            Long egresos = 0L;
            Long ingresos = 0L;
            Double egresosTramos = 0D;
            Double ingresosTramos = 0D;
            Double egresosSalarios = 0D;
            Double ingresosSalarios = 0D;
            Object[] promTramo;

            /* comparacion contra la fecha base */
            if (acum == false) {
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT ABS(");
                filtros.put("anho1", anho1);
                filtros.put("mes1", mes1);
                filtros.put("anho2", anho2);
                filtros.put("mes2", mes2);
                sb.append(buildQuery(filtros));
                sb.append(") from PuestoTrabajo");

                Query qEgresos = em.createQuery(sb.toString());
                System.err.println("Egresos : " + sb.toString());
                List<Long> res = (List<Long>) qEgresos.getResultList();
                egresos = res.get(0);
                
                // promedio de tramos y salarios en egresos
                promTramo = buildQueryTramo(filtros);
                if (promTramo[0] != null || promTramo[1] != null) {
                    egresosTramos += promTramo[0] != null ? (Double) promTramo[0] : 0D;
                    egresosSalarios += promTramo[1] != null ? (Double) promTramo[1] : 0D;
                }
                // cantidad de ingresos
                sb = new StringBuilder();
                sb.append("SELECT ABS(");
                filtros.put("anho1", anho2);
                filtros.put("mes1", mes2);
                filtros.put("anho2", anho1);
                filtros.put("mes2", mes1);
                sb.append(buildQuery(filtros));
                sb.append(") from PuestoTrabajo");
                Query qIngresos = em.createQuery(sb.toString());
                res = (List<Long>) qIngresos.getResultList();
                ingresos = res.get(0);

                // promedio de tramos y salarios en ingresos
                promTramo = buildQueryTramo(filtros);
                if (promTramo[0] != null || promTramo[1] != null) {
                    ingresosTramos += promTramo[0] != null ? (Double) promTramo[0] : 0D;
                    ingresosSalarios += promTramo[1] != null ? (Double) promTramo[1] : 0D;
                }

            } else {

                int difA = (anho2 - anho1) * 12;
                int difM = difA + mes2 - mes1;
                int vAnho1, vMes1, vAnho2, vMes2 = 0;

                int cont = 0;
                StringBuilder sb = new StringBuilder();
                // Object[] promTramo;

                if (difA != 0 || difM != 0) {
                    // cantidad de egresos
                    sb.append("SELECT ABS(");
                    for (int i = mes1; i - mes1 + 1 <= difM; i++) {
                        vAnho1 = ((i - 1) / 12 > 0) ? (anho1 + (i - 1) / 12) : anho1;
                        vMes1 = ((i - 1) / 12 > 0) ? ((i - 1) / 12 + (i - 1) % 12) : i;
                        vAnho2 = (i / 12 > 0) ? anho1 + (i / 12) : anho1;
                        vMes2 = ((i / 12) > 0) ? ((i / 12) + (i % 12)) : i + 1;
                        filtros.put("anho1", vAnho1);
                        filtros.put("mes1", vMes1);
                        filtros.put("anho2", vAnho2);
                        filtros.put("mes2", vMes2);
                        sb.append(buildQuery(filtros));
                        if (i - mes1 + 2 <= difM) {
                            sb.append(" + ");
                        } else {
                            sb.append(") from PuestoTrabajo");
                        }
                    }
                    Query qEgresos = em.createQuery(sb.toString());
                    egresos = (Long) qEgresos.getSingleResult();

                    // promedio de tramos y salarios en egresos
                    for (int i = mes1; i - mes1 + 1 <= difM; i++) {
                        vAnho1 = ((i - 1) / 12 > 0) ? (anho1 + (i - 1) / 12) : anho1;
                        vMes1 = ((i - 1) / 12 > 0) ? ((i - 1) / 12 + (i - 1) % 12) : i;
                        vAnho2 = (i / 12 > 0) ? anho1 + (i / 12) : anho1;
                        vMes2 = ((i / 12) > 0) ? ((i / 12) + (i % 12)) : i + 1;
                        filtros.put("anho1", vAnho1);
                        filtros.put("mes1", vMes1);
                        filtros.put("anho2", vAnho2);
                        filtros.put("mes2", vMes2);
                        promTramo = buildQueryTramo(filtros);
                        if (promTramo[0] != null || promTramo[1] != null) {
                            egresosTramos += promTramo[0] != null ? (Double) promTramo[0] : 0D;
                            egresosSalarios += promTramo[1] != null ? (Double) promTramo[1] : 0D;
                            cont++;
                        }
                    }
                    egresosTramos = egresosTramos / cont;
                    egresosSalarios = egresosSalarios / cont;

                    // cantidad de ingresos
                    cont = 0;
                    sb = new StringBuilder();
                    sb.append("SELECT ABS(");
                    for (int i = mes1; i - mes1 + 1 <= difM; i++) {
                        vAnho1 = ((i - 1) / 12 > 0) ? (anho1 + (i - 1) / 12) : anho1;
                        vMes1 = ((i - 1) / 12 > 0) ? ((i - 1) / 12 + (i - 1) % 12) : i;
                        vAnho2 = (vMes1 == 1) ? (anho1 - 1) : anho1;
                        vMes2 = (vMes1 == 1) ? 12 : vMes1 - 1;
                        filtros.put("anho1", vAnho1);
                        filtros.put("mes1", vMes1);
                        filtros.put("anho2", vAnho2);
                        filtros.put("mes2", vMes2);
                        sb.append(buildQuery(filtros));
                        if (i - mes1 + 2 <= difM) {
                            sb.append(" + ");
                        } else {
                            sb.append(") from PuestoTrabajo");
                        }
                    }
                    Query qIngresos = em.createQuery(sb.toString());
                    ingresos = (Long) qIngresos.getSingleResult();

                    // promedio de tramos y salarios en ingresos
                    for (int i = mes1; i - mes1 + 1 <= difM; i++) {
                        vAnho1 = ((i - 1) / 12 > 0) ? (anho1 + (i - 1) / 12) : anho1;
                        vMes1 = ((i - 1) / 12 > 0) ? ((i - 1) / 12 + (i - 1) % 12) : i;
                        vAnho2 = (vMes1 == 1) ? (anho1 - 1) : anho1;
                        vMes2 = (vMes1 == 1) ? 12 : vMes1 - 1;
                        filtros.put("anho1", vAnho1);
                        filtros.put("mes1", vMes1);
                        filtros.put("anho2", vAnho2);
                        filtros.put("mes2", vMes2);
                        promTramo = buildQueryTramo(filtros);
                        if (promTramo[0] != null || promTramo[1] != null) {
                            ingresosTramos += promTramo[0] != null ? (Double) promTramo[0] : 0D;
                            ingresosSalarios += promTramo[1] != null ? (Double) promTramo[1] : 0D;
                            cont++;
                        }
                    }
                    ingresosTramos = ingresosTramos / cont;
                    ingresosSalarios = ingresosSalarios / cont;

                } else {
                    // cantidad de egresos
                    sb.append("SELECT ABS(");
                    vAnho2 = (mes1 == 12) ? (anho1 + 1) : anho1;
                    vMes2 = (mes1 == 12) ? 1 : mes1 + 1;
                    filtros.put("anho1", anho1);
                    filtros.put("mes1", mes1);
                    filtros.put("anho2", vAnho2);
                    filtros.put("mes2", vMes2);
                    sb.append(buildQuery(filtros));
                    sb.append(") from PuestoTrabajo");
                    Query qEgresos = em.createQuery(sb.toString());
                    egresos = (Long) qEgresos.getSingleResult();

                    // promedio de tramos y salarios en egresos
                    promTramo = buildQueryTramo(filtros);
                    if (promTramo[0] != null || promTramo[1] != null) {
                        egresosTramos += promTramo[0] != null ? (Double) promTramo[0] : 0D;
                        egresosSalarios += promTramo[1] != null ? (Double) promTramo[1] : 0D;
                        cont++;
                    }
                    egresosTramos = egresosTramos / cont;
                    egresosSalarios = egresosSalarios / cont;

                    // cantidad de ingresos
                    cont = 0;
                    sb = new StringBuilder();
                    sb.append("SELECT ABS(");
                    vAnho2 = (mes1 == 1) ? (anho1 - 1) : anho1;
                    vMes2 = (mes1 == 1) ? 12 : (mes1 - 1);
                    filtros.put("anho2", vAnho2);
                    filtros.put("mes2", vMes2);
                    sb.append(buildQuery(filtros));
                    sb.append(") from PuestoTrabajo");
                    Query qIngresos = em.createQuery(sb.toString());
                    ingresos = (Long) qIngresos.getSingleResult();

                    // promedio de tramos y salarios en egresos
                    promTramo = buildQueryTramo(filtros);
                    if (promTramo[0] != null || promTramo[1] != null) {
                        // listTramo.add(promTramo);
                        ingresosTramos += promTramo[0] != null ? (Double) promTramo[0] : 0D;
                        ingresosSalarios += promTramo[1] != null ? (Double) promTramo[1] : 0D;
                        cont++;
                    }
                    ingresosTramos = ingresosTramos / cont;
                    ingresosSalarios = ingresosSalarios / cont;
                }
            }

            if (esHacienda()) {
            	
            	/*sqlAnho.append(" SELECT new ").append(GestionPersonasDTO.class.getCanonicalName());
                sqlAnho.append(" (" + mes2 + ", " + anho2 + ", COUNT(DISTINCT puesto.pk.cedulaIdentidad), "
                        + toIntExact(egresos) + ", " + toIntExact(ingresos) + ", " + egresosTramos + ", " 
                		+ ingresosTramos + ", " + egresosSalarios.longValue() + "L, " 
                        + ingresosSalarios.longValue() + "L)");
                sqlAnho.append(" FROM PuestoTrabajo puesto");
                if (esHacienda()) {
                    sqlAnho.append(" WHERE TO_DATE(CAST(puesto.pk.anho as text) || '-' ");
                    sqlAnho.append(" || CAST(puesto.pk.mes as text) || '-' || '1', 'YYYY/MM/DD')");
                    sqlAnho.append(" <= TO_DATE('" + anho2 + "-" + mes2 + "-1', 'YYYY/MM/DD')");
                } else {
                    sqlAnho.append(" WHERE CONVERT(DATETIME,CAST(puesto.pk.anho as text) + '-' ");
                    sqlAnho.append(" + CAST(puesto.pk.mes as text) + '-' + '1', 102)");
                    sqlAnho.append(" <= CONVERT(DATETIME,'" + anho2 + "-" + mes2 + "-1', 102)");
                }
                sqlAnho.append(" AND puesto.pk.cedulaIdentidad != 0");
                filtros.put("tableKey", "puesto");
                buildFiltros(sqlAnho, filtros);*/
            	
            	long cargosTotales = getCargosTotalesHacienda(anho2, mes2, vinculacion, ambito, 
            			sexo, idCpt, idCptee, idCptEf, idCeo);
            	
	            GestionPersonasDTO dto = new GestionPersonasDTO(mes2, anho2, new Long(cargosTotales), 
	            		toIntExact(egresos), toIntExact(ingresos), egresosTramos, ingresosTramos, 
	            		egresosSalarios.longValue(), ingresosSalarios.longValue());
	            
	            return dto;
                
            } else {
            	
            	long cargosTotales = getCargosTotales(anho2, mes2, vinculacion, ambito, 
            			sexo, idCpt, idCptee, idCptEf, idCeo);
            	
	            GestionPersonasDTO dto = new GestionPersonasDTO(mes2, anho2, new Long(cargosTotales), 
	            		toIntExact(egresos), toIntExact(ingresos), egresosTramos, ingresosTramos, 
	            		egresosSalarios.longValue(), ingresosSalarios.longValue());
	            
	            return dto;
	            
            }
        }

        //Query qFirst = em.createQuery(sqlAnho.toString(), GestionPersonasDTO.class);

        // Query qSecond = em.createQuery(sqlAnhoHasta.toString(),
        // GestionPersonasDTO.class);
        // qSecond.setParameter("a2", String.valueOf(anho2));
        // qSecond.setParameter("m2", String.valueOf(mes2));
        //
        // List<GestionPersonasDTO> first = qFirst.getResultList();
        // List<GestionPersonasDTO> second = qSecond.getResultList();
        // first.addAll(second);
        // List<GestionPersonasDTO> list = first;
        //return (GestionPersonasDTO) qFirst.getSingleResult();
    }

    public List<GestionPersonasDTO> getListaPromocionSalarial(Integer anho1, Integer mes1, Integer anho2, Integer mes2,
            String vinculacion, String idCptEf, String ambito, String sexo, String idCpt, String idCptee, String idCeo)
            throws NoSuchFieldException {

        List<GestionPersonasDTO> restultList = new ArrayList<GestionPersonasDTO>();

        StringBuilder sb = new StringBuilder();

        sb.append(" SELECT new ").append(GestionPersonasDTO.class.getCanonicalName());
        sb.append(" (puesto.pk.mes, puesto.pk.anho)");
        sb.append(" FROM PuestoTrabajo puesto");

        if (esHacienda()) {
            sb.append(
                    " WHERE TO_DATE(CAST(puesto.pk.anho as text) || '-' || CAST(puesto.pk.mes as text) || '-' || '1', 'YYYY/MM/DD')");
            sb.append("");
            sb.append(" BETWEEN TO_DATE('" + anho1 + "-" + mes1 + "-1', 'YYYY/MM/DD') ");
            sb.append(" AND TO_DATE('" + anho2 + "-" + mes2 + "-1', 'YYYY/MM/DD')");
        } else {
            sb.append(
                    " WHERE CONVERT(DATETIME,CONVERT(varchar(10), puesto.pk.anho) + '-' + CONVERT(varchar(10), puesto.pk.mes) + '-' + '1', 102)");
            sb.append("");
            sb.append(" BETWEEN CONVERT(DATETIME, '" + anho1 + "-" + mes1 + "-1', 102) " + " AND CONVERT(DATETIME,'"
                    + anho2 + "-" + mes2 + "-1', 102)");
        }
        sb.append(" GROUP BY puesto.pk.mes, puesto.pk.anho");
        sb.append(" ORDER BY puesto.pk.anho, puesto.pk.mes");

        Query q = em.createQuery(sb.toString(), GestionPersonasDTO.class);
        List<GestionPersonasDTO> fechas = q.getResultList();
        Integer mesInicial = 0;
        Integer anhoInicial = 0;
        for (GestionPersonasDTO fecha : fechas) {
            if (restultList.size() == 0) {
                mesInicial = fecha.getMes();
                anhoInicial = fecha.getAnho();
                restultList.add(getPromocionSalarial(anhoInicial, mesInicial, null, null, vinculacion, idCptEf, ambito,
                        sexo, idCpt, idCptee, idCeo));
            } else {
                restultList.add(getPromocionSalarial(anhoInicial, mesInicial, fecha.getAnho(), fecha.getMes(),
                        vinculacion, idCptEf, ambito, sexo, idCpt, idCptee, idCeo));
            }
        }
        return restultList;
    }

    public GestionPersonasDTO getPromocionSalarial(Integer anho1, Integer mes1, Integer anho2, Integer mes2,
            String vinculacion, String idCptEf, String ambito, String sexo, String idCpt, String idCptee,
            String idCeo) {

        HashMap<String, Object> filtros = new HashMap<String, Object>();

        filtros.put("vinculacion", vinculacion);
        filtros.put("idCptEf", idCptEf);
        filtros.put("ambito", ambito);
        filtros.put("sexo", sexo);
        filtros.put("idCpt", idCpt);
        filtros.put("idCptEe", idCptee);
        filtros.put("idCeo", idCeo);

        StringBuilder sqlAnho = new StringBuilder();

        if (anho2 == null && mes2 == null) {

            // cantidad de cargos totales correspondiente al primer año
        	
        	if (esHacienda()) {
        		
	            /*sqlAnho.append(" SELECT new ").append(GestionPersonasDTO.class.getCanonicalName());
	            sqlAnho.append(
	                    " (" + mes1 + ", " + anho1 + ", COUNT(DISTINCT puesto.pk.cedulaIdentidad), 0, 0D, 0L, 0D, 0L)");
	            sqlAnho.append("");
	            sqlAnho.append("");
	            sqlAnho.append(" FROM PuestoTrabajo puesto");
	
	            if (esHacienda()) {
	                sqlAnho.append(
	                        " WHERE TO_DATE(CAST(puesto.pk.anho as text) || '-' || CAST(puesto.pk.mes as text) || '-' || '1', 'YYYY/MM/DD')");
	                sqlAnho.append("");
	                sqlAnho.append(" <= TO_DATE('" + anho1 + "-" + mes1 + "-1', 'YYYY/MM/DD')");
	            } else {
	                sqlAnho.append(
	                        " WHERE CONVERT(DATETIME,CAST(puesto.pk.anho as text) + '-' + CAST(puesto.pk.mes as text) + '-' + '1', 102)");
	                sqlAnho.append("");
	                sqlAnho.append("");
	                sqlAnho.append(" <= CONVERT(DATETIME,'" + anho1 + "-" + mes1 + "-1', 102)");
	            }
	            sqlAnho.append(" AND puesto.pk.cedulaIdentidad != 0");
	            filtros.put("tableKey", "puesto");
	            buildFiltros(sqlAnho, filtros);*/
        		
        		long cargosTotales = getCargosTotalesHacienda(anho1, mes1, vinculacion, 
        				ambito, sexo, idCpt, idCptee, idCptEf, idCeo);
	            GestionPersonasDTO dto = new GestionPersonasDTO(mes1, anho1, 
	            		new Long(cargosTotales), 0, 0D, 0L, 0D, 0L);
	            
	            return dto;
	            
        	} else {
        		
        		long cargosTotales = getCargosTotales(anho1, mes1, vinculacion, ambito, 
            			sexo, idCpt, idCptee, idCptEf, idCeo);
	            GestionPersonasDTO dto = new GestionPersonasDTO(mes1, anho1, 
	            		new Long(cargosTotales), 0, 0D, 0L, 0D, 0L);
	            
	            return dto;
        	}

        } else {

            boolean acum = false;
            Long promocionados = 0L;
            Double tramosActual = 0D;
            Double salariosActual = 0D;
            Double tramosInicial = 0D;
            Double salariosInicial = 0D;
            List<Integer> listCedulas = new ArrayList<>();
            /* comparacion contra la fecha base */
            if (acum == false) {
                StringBuilder sb = new StringBuilder();
                List<Object[]> promTramo;
                List<StringBuilder> listSbCedulas = new ArrayList<StringBuilder>();
                // cantidad de promocionados
                sb.append("SELECT ABS(");
                filtros.put("anho1", anho1);
                filtros.put("mes1", mes1);
                filtros.put("anho2", anho2);
                filtros.put("mes2", mes2);
                sb.append(buildPromocion(filtros));
                sb.append(") from PuestoTrabajo");
                Query qPromocionados = em.createQuery(sb.toString());
                promocionados = (Long) qPromocionados.getSingleResult();

                // promedio de tramos y salarios actuales en promocionados
                filtros.put("anho1", anho1);
                filtros.put("mes1", mes1);
                filtros.put("anho2", anho2);
                filtros.put("mes2", mes2);
                promTramo = buildPromocionActual(filtros);
                listSbCedulas.add(buildSbCedulas(filtros));
                if (promTramo.size() > 0) {
                    tramosActual = promTramo.get(0)[0] != null ? (Double) promTramo.get(0)[0] : 0D;
                    salariosActual = promTramo.get(0)[1] != null ? (Double) promTramo.get(0)[1] : 0D;
                }
                filtros.put("anho1", anho1);
                filtros.put("mes1", mes1);
                promTramo = buildPromocionInicial(filtros);
                if (promTramo.get(0)[0] != null || promTramo.get(0)[1] != null) {
                    tramosInicial = promTramo.get(0)[0] != null ? (Double) promTramo.get(0)[0] : 0D;
                    salariosInicial = promTramo.get(0)[1] != null ? (Double) promTramo.get(0)[1] : 0D;

                }

            } else {
                int difA = (anho2 - anho1) * 12;
                int difM = difA + mes2 - mes1;
                int vAnho1, vMes1, vAnho2, vMes2 = 0;
                promocionados = 0L;
                tramosActual = 0D;
                salariosActual = 0D;
                tramosInicial = 0D;
                salariosInicial = 0D;
                listCedulas = new ArrayList<>();
                int cont = 0;
                StringBuilder sb = new StringBuilder();
                List<Object[]> promTramo;
                List<StringBuilder> listSbCedulas = new ArrayList<StringBuilder>();
                // cantidad de promocionados
                sb.append("SELECT ABS(");
                for (int i = mes1; i - mes1 + 1 <= difM; i++) {
                    vAnho1 = ((i - 1) / 12 > 0) ? (anho1 + (i - 1) / 12) : anho1;
                    vMes1 = ((i - 1) / 12 > 0) ? ((i - 1) / 12 + (i - 1) % 12) : i;
                    vAnho2 = (i / 12 > 0) ? anho1 + (i / 12) : anho1;
                    vMes2 = ((i / 12) > 0) ? ((i / 12) + (i % 12)) : i + 1;
                    filtros.put("anho1", vAnho1);
                    filtros.put("mes1", vMes1);
                    filtros.put("anho2", vAnho2);
                    filtros.put("mes2", vMes2);
                    sb.append(buildPromocion(filtros));
                    if (i - mes1 + 2 <= difM) {
                        sb.append(" + ");
                    } else {
                        sb.append(") from PuestoTrabajo");
                    }
                }
                Query qPromocionados = em.createQuery(sb.toString());
                promocionados = (Long) qPromocionados.getSingleResult();

                // promedio de tramos y salarios actuales en promocionados
                for (int i = mes1; i - mes1 <= difM; i++) {
                    vAnho1 = ((i - 1) / 12 > 0) ? (anho1 + (i - 1) / 12) : anho1;
                    vMes1 = ((i - 1) / 12 > 0) ? ((i - 1) / 12 + (i - 1) % 12) : i;
                    vAnho2 = (vMes1 == 1) ? (anho1 - 1) : anho1;
                    vMes2 = (vMes1 == 1) ? 12 : vMes1 - 1;
                    filtros.put("anho1", vAnho1);
                    filtros.put("mes1", vMes1);
                    filtros.put("anho2", vAnho2);
                    filtros.put("mes2", vMes2);
                    promTramo = buildPromocionActual(filtros);
                    listSbCedulas.add(buildSbCedulas(filtros));
                    if (promTramo.size() > 0) {
                        tramosActual = promTramo.get(0)[0] != null ? (Double) promTramo.get(0)[0] : 0D;
                        salariosActual = promTramo.get(0)[1] != null ? (Double) promTramo.get(0)[1] : 0D;
                    }
                }

                cont = 0;
                filtros.put("anho1", anho1);
                filtros.put("mes1", mes1);
                promTramo = buildPromocionInicial(filtros);
                if (promTramo.get(0)[0] != null || promTramo.get(0)[1] != null) {
                    tramosInicial = promTramo.get(0)[0] != null ? (Double) promTramo.get(0)[0] : 0D;
                    salariosInicial = promTramo.get(0)[1] != null ? (Double) promTramo.get(0)[1] : 0D;
                    cont++;
                }

            }
            
            if (esHacienda()) {
            	
            	/*sqlAnho.append(" SELECT new ").append(GestionPersonasDTO.class.getCanonicalName());
                sqlAnho.append(" (" + mes2 + ", " + anho2 + ", COUNT(DISTINCT puesto.pk.cedulaIdentidad), "
                        + toIntExact(promocionados) + ", " + tramosInicial + ", " + salariosInicial.longValue() + "L, "
                        + tramosActual + ", " + salariosActual.longValue() + "L)");
                sqlAnho.append(" FROM PuestoTrabajo puesto");
                sqlAnho.append(
                        " WHERE TO_DATE(CAST(puesto.pk.anho as text) || '-' || CAST(puesto.pk.mes as text) || '-' || '1', 'YYYY/MM/DD')");
                sqlAnho.append("");
                sqlAnho.append(" <= TO_DATE('" + anho2 + "-" + mes2 + "-1', 'YYYY/MM/DD')");
                sqlAnho.append(" AND puesto.pk.cedulaIdentidad != 0");
	            filtros.put("tableKey", "puesto");
	            buildFiltros(sqlAnho, filtros);*/
            	
            	long cargosTotales = getCargosTotalesHacienda(anho2, mes2, vinculacion, 
            			ambito, sexo, idCpt, idCptee, idCptEf, idCeo);
            	
	            GestionPersonasDTO dto = new GestionPersonasDTO(mes2, anho2, new Long(cargosTotales), 
	            		toIntExact(promocionados), tramosInicial, salariosInicial.longValue(),
	                    tramosActual, salariosActual.longValue());
	            
	            return dto;
            
            } else {
	            
            	long cargosTotales = getCargosTotales(anho2, mes2, vinculacion, ambito, 
            			sexo, idCpt, idCptee, idCptEf, idCeo);
            	
	            GestionPersonasDTO dto = new GestionPersonasDTO(mes2, anho2, new Long(cargosTotales), 
	            		toIntExact(promocionados), tramosInicial, salariosInicial.longValue(),
	                    tramosActual, salariosActual.longValue());
	            
	            return dto;
            }
        }
        
        //Query qFirst = em.createQuery(sqlAnho.toString(), GestionPersonasDTO.class);
        //return (GestionPersonasDTO) qFirst.getSingleResult();
    }

    public String buildQuery(HashMap<String, Object> filtros) {
        String listCedulas = getCedula(filtros);
        StringBuilder sb = new StringBuilder();
        sb.append(" (SELECT COUNT(DISTINCT puesto.pk.cedulaIdentidad) ");
        sb.append(" FROM PuestoTrabajo puesto ");
        sb.append(" WHERE puesto.pk.cedulaIdentidad != 0 ");
        sb.append(" AND puesto.pk.mes = " + filtros.get("mes1"));
        sb.append(" AND puesto.pk.anho = " + filtros.get("anho1"));
        filtros.put("tableKey", "puesto");
        buildFiltros(sb, filtros);
        sb.append(" AND NOT EXISTS (" + listCedulas + ")) ");

        return sb.toString();
    }

    public Object[] buildQueryTramo(HashMap<String, Object> filtros) {
        String listCedulas = getCedula(filtros);
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT AVG(puesto.numeroTramo), AVG(puesto.presupuestado)");
        sb.append(" FROM PuestoTrabajo puesto ");
        sb.append(" WHERE puesto.pk.cedulaIdentidad != 0 ");
        sb.append(" AND puesto.pk.mes =" + filtros.get("mes1"));
        sb.append(" AND puesto.pk.anho =" + filtros.get("anho1"));
        sb.append(" AND NOT EXISTS (" + listCedulas + ") ");
        filtros.put("tableKey", "puesto");
        buildFiltros(sb, filtros);
        Query qTramos = em.createQuery(sb.toString());
        System.out.println(sb.toString());
        return (Object[]) qTramos.getSingleResult();

    }

    public String buildPromocion(HashMap<String, Object> filtros) {
        String listCedulasTramos = getCedulaTramos(filtros);
        StringBuilder sb = new StringBuilder();
        sb.append(" (SELECT COUNT(*) ");
        sb.append(" FROM PuestoTrabajo puesto ");
        sb.append(" WHERE puesto.pk.cedulaIdentidad != 0 ");
        sb.append(" AND puesto.pk.mes = " + filtros.get("mes1"));
        sb.append(" AND puesto.pk.anho = " + filtros.get("anho1"));
        filtros.put("tableKey", "puesto");
        buildFiltros(sb, filtros);
        sb.append(" AND EXISTS (" + listCedulasTramos + ")) ");

        return sb.toString();
    }

    public List<Object[]> buildPromocionActual(HashMap<String, Object> filtros) {
        String listCedulasTramos = getCedulaTramosActual(filtros);
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT AVG(puesto.numeroTramo), AVG(puesto.presupuestado)");
        sb.append(" FROM PuestoTrabajo puesto ");
        sb.append(" WHERE puesto.pk.cedulaIdentidad != 0 ");
        sb.append(" AND puesto.pk.mes = " + filtros.get("mes2"));
        sb.append(" AND puesto.pk.anho = " + filtros.get("anho2"));
        sb.append(" AND EXISTS (" + listCedulasTramos + ") ");
        filtros.put("tableKey", "puesto");
        buildFiltros(sb, filtros);
        Query qTramos = em.createQuery(sb.toString());
        return (List<Object[]>) qTramos.getResultList();
    }

    public StringBuilder buildSbCedulas(HashMap<String, Object> filtros) {
        String listCedulasTramos = getCedulaTramos(filtros);
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT puesto.pk.cedulaIdentidad ");
        sb.append(" FROM PuestoTrabajo puesto ");
        sb.append(" WHERE puesto.pk.cedulaIdentidad != 0 ");
        sb.append(" AND puesto.pk.mes = " + filtros.get("mes1"));
        sb.append(" AND puesto.pk.anho = " + filtros.get("anho1"));
        sb.append(" AND EXISTS (" + listCedulasTramos + ") ");
        sb.append(" AND puesto.pk.cedulaIdentidad = a.pk.cedulaIdentidad ");
        filtros.put("tableKey", "puesto");
        buildFiltros(sb, filtros);

        return sb;
    }

    public List<Object[]> buildPromocionInicial(HashMap<String, Object> filtros) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT AVG(a.numeroTramo), AVG(a.presupuestado) ");
        sb.append(" FROM PuestoTrabajo a ");
        sb.append(" WHERE a.pk.cedulaIdentidad != 0 ");
        sb.append(" AND a.pk.mes = " + filtros.get("mes1"));
        sb.append(" AND a.pk.anho = " + filtros.get("anho1"));
        filtros.put("tableKey", "a");
        buildFiltros(sb, filtros);

		StringBuilder listCedulas = new StringBuilder();
		listCedulas.append(" SELECT c.pk.cedulaIdentidad ");
		listCedulas.append(" FROM PuestoTrabajo c ");
		listCedulas.append(" WHERE c.pk.cedulaIdentidad != 0 ");
		listCedulas.append(" AND c.pk.mes = " + filtros.get("mes2"));
		listCedulas.append(" AND c.pk.anho = " + filtros.get("anho2"));
		listCedulas.append(" AND c.pk.cedulaIdentidad = a.pk.cedulaIdentidad ");
                listCedulas.append(" AND c.vinculoLaboral = a.vinculoLaboral ");
		listCedulas.append(" AND c.categoria != a.categoria ");
                listCedulas.append(" AND c.presupuestado != a.presupuestado ");
		//listCedulas.append(" AND c.presupuestado > a.presupuestado ");
		filtros.put("tableKey", "c");
		buildFiltros(listCedulas, filtros);
        sb.append(" AND EXISTS (" + listCedulas + ") ");

        Query qTramos = em.createQuery(sb.toString());

        return (List<Object[]>) qTramos.getResultList();
    }

    public String getCedula(HashMap<String, Object> filtros) {

        StringBuilder sqlNotIn = new StringBuilder();

        sqlNotIn.append(" SELECT c.id.cedulaIdentidad ");
        sqlNotIn.append(" FROM PuestoTrabajo c ");
        sqlNotIn.append(" WHERE c.id.cedulaIdentidad != 0 ");
        sqlNotIn.append(" AND c.id.mes = " + filtros.get("mes2"));
        sqlNotIn.append(" AND c.id.anho = " + filtros.get("anho2"));
        sqlNotIn.append(" AND c.id.cedulaIdentidad = puesto.pk.cedulaIdentidad ");
        filtros.put("tableKey", "c");
        buildFiltros(sqlNotIn, filtros);
        return sqlNotIn.toString();
    }

    public String getCedulaTramos(HashMap<String, Object> filtros) {
    	

        StringBuilder sqlNotIn = new StringBuilder();
		sqlNotIn.append(" SELECT c.pk.cedulaIdentidad ");
		sqlNotIn.append(" FROM PuestoTrabajo c ");
		sqlNotIn.append(" WHERE c.pk.cedulaIdentidad != 0 ");
		sqlNotIn.append(" AND c.pk.mes = " + filtros.get("mes2"));
		sqlNotIn.append(" AND c.pk.anho = " + filtros.get("anho2"));
		sqlNotIn.append(" AND c.pk.cedulaIdentidad = puesto.pk.cedulaIdentidad ");
                sqlNotIn.append(" AND c.vinculoLaboral = puesto.vinculoLaboral ");
		sqlNotIn.append(" AND c.categoria != puesto.categoria ");
                sqlNotIn.append(" AND c.presupuestado != puesto.presupuestado ");
		//sqlNotIn.append(" AND c.presupuestado < puesto.presupuestado ");
		filtros.put("tableKey", "c");
		buildFiltros(sqlNotIn, filtros);
		return sqlNotIn.toString();
	}
    public String getCedulaTramosActual(HashMap<String, Object> filtros) {

            StringBuilder sqlNotIn = new StringBuilder();
		sqlNotIn.append(" SELECT c.pk.cedulaIdentidad ");
		sqlNotIn.append(" FROM PuestoTrabajo c ");
		sqlNotIn.append(" WHERE c.pk.cedulaIdentidad != 0 ");
		sqlNotIn.append(" AND c.pk.mes = " + filtros.get("mes1"));
		sqlNotIn.append(" AND c.pk.anho = " + filtros.get("anho1"));
		sqlNotIn.append(" AND c.pk.cedulaIdentidad = puesto.pk.cedulaIdentidad ");
                sqlNotIn.append(" AND c.vinculoLaboral = puesto.vinculoLaboral ");
		sqlNotIn.append(" AND c.categoria != puesto.categoria ");
                sqlNotIn.append(" AND c.presupuestado != puesto.presupuestado ");
		//sqlNotIn.append(" AND c.presupuestado < puesto.presupuestado ");
		filtros.put("tableKey", "c");
		buildFiltros(sqlNotIn, filtros);
		return sqlNotIn.toString();
	}
		
    /**
     * @param sb
     * @param filtros
     */
    public void buildFiltros(StringBuilder sb, HashMap<String, Object> filtros) {

        String cptTable = "cptE";
        if (esHacienda()) {
            cptTable = "cptF";
        }

        String tableKey = String.valueOf(filtros.get("tableKey"));
        String vinculacion = String.valueOf(filtros.get("vinculacion"));
        String idCptEf = String.valueOf(filtros.get("idCptEf"));
        String idCptEe = String.valueOf(filtros.get("idCptEe"));
        String ambito = String.valueOf(filtros.get("ambito"));
        String sexo = String.valueOf(filtros.get("sexo"));
        String idCpt = String.valueOf(filtros.get("idCpt"));
        String idCptee = String.valueOf(filtros.get("idCptEe"));
        String idCeo = String.valueOf(filtros.get("idCeo"));

        if (vinculacion != null && !vinculacion.equalsIgnoreCase("TODOS")) {
            sb.append(" AND " + tableKey + ".vinculoLaboral = '" + vinculacion + "'");
        }
        if (idCptEf != null && !idCptEf.equalsIgnoreCase("TODOS")) {
            sb.append(" AND " + tableKey + ".cptF.id = " + Integer.valueOf(idCptEf));
        }
        if (idCptEe != null && !idCptEe.equalsIgnoreCase("TODOS")) {
            sb.append(" AND " + tableKey + ".cptE.id = " + Integer.valueOf(idCptEe));
        }
        if (ambito != null && !ambito.equalsIgnoreCase("TODOS")) {
            sb.append(" AND " + tableKey + "." + cptTable +".ambito.nombreAmbito = '" );
            sb.append(ambito + "'");
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

            sb.append(" AND  " + tableKey + ".sexo = '" + sexo + "'");
        }
        if (idCpt != null && !idCpt.equalsIgnoreCase("TODOS")) {
            sb.append(" AND " + tableKey + ".cpt.id = " + Integer.valueOf(idCpt));
        }
        if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {

        } else {
            if (idCptee != null && !idCptee.equalsIgnoreCase("TODOS")) {
                sb.append(" AND " + tableKey + ".cptE.id = " + Integer.valueOf(idCptee));
            }
        }

        if (idCeo != null && !idCeo.equalsIgnoreCase("TODOS")) {
            sb.append(" AND " + tableKey + ".ceo.id = " + Integer.valueOf(idCeo));
        }
    }

    public void buildNativeFiltros(StringBuilder sb, HashMap<String, Object> filtros) {

        String esquema = Constantes.ESQUEMA_CTE_PJ;
        String cptTable = "cpt_ee";
        String idCptColumn = "id_cpt_ee";

        if (esHacienda()) {
            esquema = Constantes.ESQUEMA_CTE_MH;
            cptTable = "cpt_ef";
            idCptColumn = "id_cpt_ef";
        }
        String tableKey = String.valueOf(filtros.get("tableKey"));
        String vinculacion = String.valueOf(filtros.get("vinculacion"));
        String idCptEf = String.valueOf(filtros.get("idCptEf"));
        String ambito = String.valueOf(filtros.get("ambito"));
        String sexo = String.valueOf(filtros.get("sexo"));
        String idCpt = String.valueOf(filtros.get("idCpt"));
        String idCptee = String.valueOf(filtros.get("idCptee"));
        String idCeo = String.valueOf(filtros.get("idCeo"));

        if (vinculacion != null && !vinculacion.equalsIgnoreCase("TODOS")) {
            sb.append(" AND " + tableKey + ".vinculo_laboral = '" + vinculacion + "'");
        }
        if (idCptEf != null && !idCptEf.equalsIgnoreCase("TODOS")) {
            sb.append(" AND " + tableKey + "." + idCptColumn + " = " + Integer.valueOf(idCptEf));
        }
        if (ambito != null && !ambito.equalsIgnoreCase("TODOS")) {
            sb.append(" AND " + tableKey + ".ambito_" + cptTable + " = ( select " + cptTable
                + ".id from " + esquema + "." + cptTable + ", " + esquema + ".ambito where "
                + cptTable + ".ambito_id = ambito.id and ambito.id = " + ambito + ")");
        }
        if (sexo != null && !sexo.equalsIgnoreCase("TODOS")) {
            if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {
                ;
            } else {
                if (sexo.equals("M")) {
                    sexo = "1";
                } else {
                    sexo = "0";
                }
            }
            sb.append(" AND  " + tableKey + ".sexo = '" + sexo + "'");
        }

        if (idCpt != null && !idCpt.equalsIgnoreCase("TODOS")) {
            sb.append(" AND " + tableKey + ".id_cpt = " + Integer.valueOf(idCpt));
        }
        if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {

        } else {
            if (idCptee != null && !idCptee.equalsIgnoreCase("TODOS")) {
                sb.append(" AND " + tableKey + ".id_cpt_ee = " + Integer.valueOf(idCptee));
            }
        }

        if (idCeo != null && !idCeo.equalsIgnoreCase("TODOS")) {
            sb.append(" AND " + tableKey + ".id_ceo = " + Integer.valueOf(idCeo));
        }
    }
    
    public long getCargosTotales(int anho, int mes, String vinculacion) {
    	
    	StringBuilder sql = new StringBuilder();
    	sql.append("SELECT count(DISTINCT ps.cedula_identidad) FROM dbo.planilla_sueldo ps ");
    	sql.append(" WHERE ps.ejercicio = " + anho + " and ps.mes= " + mes);
        sql.append(" AND ps.objeto in (111,144,145)");
        
        if (vinculacion != null && !vinculacion.equalsIgnoreCase("TODOS")) {
        	sql.append(" AND ps.vinculo_laboral = '" + vinculacion + "'");
        }
        
        Query q = em.createNativeQuery(sql.toString());
        int total = (Integer) q.getSingleResult();
        
        // Cargos vacantes
        sql = new StringBuilder();
        sql.append("SELECT count(1) from dbo.cargos_disponibles c ");
        sql.append(" WHERE c.ejercicio = " + anho + " and c.mes = " + mes);
        sql.append(" AND concepto = 'SUELDOS'");
        q = em.createNativeQuery(sql.toString());
        int vacantes = (Integer) q.getSingleResult();
        total = total + vacantes;
        return new Long(total);
        
    }
    
    /**
     * Obtiene los cargos totales para el periodo.
     * Hace la sumatoria del total de cedulas distintas mas los cargos vacantes.
     */
    public long getCargosTotales(int anho, int mes, String vinculacion, String ambito,
    		String sexo, String idCpt, String idCptEe, String idCptEf, String idCeo) {
    	
    	StringBuilder sql = new StringBuilder();
    	boolean contarVacantes = true;
    	sql.append("SELECT count(DISTINCT ps.cedula_identidad) FROM dbo.planilla_sueldo ps ");
        
        if (idCpt != null && !idCpt.equalsIgnoreCase("TODOS")) {
        	sql.append("JOIN dbo.cpt_legajos cptl ON ps.cedula_identidad = cptl.cedula_identidad ");
        	sql.append("AND cptl.anho = ps.ejercicio and cptl.mes = ps.mes and cptl.id_cpt = " + idCpt);
        	contarVacantes = false;
        }
        if (idCeo != null && !idCeo.equalsIgnoreCase("TODOS")) {
        	sql.append("JOIN dbo.ceo_legajos ceol ON ps.cedula_identidad = ceol.cedula_identidad ");
        	sql.append("AND ceol.anho = ps.ejercicio AND ceol.mes = ps.mes AND ceol.id_ceo = " + idCeo);
        	contarVacantes = false;
        }
        
        if (idCptEe != null && !idCptEe.equalsIgnoreCase("TODOS")) {
        	sql.append("JOIN dbo.cpt_ee_legajos cptel ON ");
        	sql.append("ps.cedula_identidad = cptel.cedula_identidad ");
        	sql.append("AND cptel.anho = ps.ejercicio AND cptel.mes = ps.mes ");
        	sql.append("AND cptel.id_cpt_ee = " + idCptEe);
        	if (ambito != null && !ambito.equalsIgnoreCase("TODOS")) {
        		if (ambito.equalsIgnoreCase("Jurisdiccional")) {
            		ambito = "J";
            	} else {
            		ambito = "A";
            	}
        		sql.append("JOIN dbo.cpt_ee cpte on cptel.id_cpt_ee = cpte.id AND cpte.ambito = '" + ambito + "'");
        	}
        	contarVacantes = false;
        }
        
        if (ambito != null && !ambito.equalsIgnoreCase("TODOS") 
        		&& (idCptEe == null || idCptEe.equalsIgnoreCase("TODOS"))) {
        	
        	contarVacantes = false;
        	if (ambito.equalsIgnoreCase("Jurisdiccional")) {
        		ambito = "J";
        	} else {
        		ambito = "A";
        	}
        	sql.append("JOIN dbo.cpt_ee_legajos cptel on ps.cedula_identidad = cptel.cedula_identidad ");
        	sql.append("AND cptel.anho = ps.ejercicio AND cptel.mes = ps.mes ");
        	sql.append("JOIN dbo.cpt_ee cpte on cptel.id_cpt_ee = cpte.id AND cpte.ambito = '" + ambito + "'");
        }
        
        if (idCptEf != null && !idCptEf.equalsIgnoreCase("TODOS")) {
        	sql.append("JOIN dbo.cpt_ef_legajos cptfl ON ");
        	sql.append("ps.cedula_identidad = cptfl.cedula_identidad ");
        	sql.append("AND cptfl.anho = ps.ejercicio AND cptfl.mes = ps.mes ");
        	sql.append("AND cptfl.id_cpt_ef = " + idCptEf);
        	contarVacantes = false;
        }
        
        if (sexo != null && !sexo.equalsIgnoreCase("TODOS")) {
        	int genero = 0;
        	if (sexo.equalsIgnoreCase("M")) {
        		genero = 1;
        	}
        	sql.append("JOIN dbo.legajo_personal l ON ps.cedula_identidad = l.cedula_identidad ");
        	sql.append(" AND l.sexo = " + genero);
        	contarVacantes = false;
        }
        
        sql.append(" WHERE ps.ejercicio = " + anho + " and ps.mes= " + mes);
        sql.append(" AND ps.objeto in (111,144,145)");
        if (vinculacion != null && !vinculacion.equalsIgnoreCase("TODOS")) {
        	sql.append(" AND ps.vinculo_laboral = '" + vinculacion + "'");
        	contarVacantes = false;
        }
        
        Query q = em.createNativeQuery(sql.toString());
        int total = (Integer) q.getSingleResult();
        
        // Cargos vacantes
        int vacantes = 0;
        if (contarVacantes) {
	        sql = new StringBuilder();
	        sql.append("SELECT count(1) from dbo.cargos_disponibles c ");
	        sql.append(" WHERE c.ejercicio = " + anho + " and c.mes = " + mes);
	        sql.append(" AND concepto = 'SUELDOS'");
	        q = em.createNativeQuery(sql.toString());
	        vacantes = (Integer) q.getSingleResult();
        }
        total = total + vacantes;
        return new Long(total);
        
    }
    
    public long getCedulasSinRepetir(int anho, int mes) {
    	
    	StringBuilder sb = new StringBuilder(); 
    	sb.append("select count(DISTINCT ps.cedula_identidad) FROM dbo.planilla_sueldo ps ");
    	sb.append("WHERE ps.ejercicio = %s and ps.mes= %s AND ps.objeto in (111,144,145)");
    	String sql = String.format(sb.toString(), anho, mes);
    	Query q = em.createNativeQuery(sql);
    	int total = (Integer) q.getSingleResult();
    	return new Long(total);
    }
    
    public long getTotalByVinculo(int anho, int mes, String vinculo) {
    	
    	StringBuilder sb = new StringBuilder(); 
    	sb.append("select count(DISTINCT ps.cedula_identidad) FROM dbo.planilla_sueldo ps ");
    	sb.append("WHERE ps.ejercicio = %s and ps.mes= %s AND ps.objeto in (111,144,145) ");
    	sb.append("and ps.vinculo_laboral='%s'");
    	String sql = String.format(sb.toString(), anho, mes, vinculo);
    	Query q = em.createNativeQuery(sql);
    	int total = (Integer) q.getSingleResult();
    	return new Long(total);
    }
    
    /**
     * Obtiene los cargos totales para el periodo.
     * Hace la sumatoria del total de cedulas distintas mas los cargos vacantes.
     */
    public long getCargosTotalesHacienda(int anho, int mes, String vinculacion, String ambito,
    		String sexo, String idCpt, String idCptEe, String idCptEf, String idCeo) {
    	
    	boolean contarVacantes = true;
    	
    	StringBuilder sql = new StringBuilder();
    	sql.append("SELECT count(DISTINCT ps.cedula_identidad) FROM cte.v_cte_anexo_liquidacion ps ");
        
        if (idCpt != null && !idCpt.equalsIgnoreCase("TODOS")) {
        	sql.append("JOIN cte.cpt_legajos cptl ON ps.cedula_identidad = cptl.cedula_identidad ");
        	sql.append("AND cptl.anho = ps.anho and cptl.mes = ps.mes and cptl.id_cpt = " + idCpt);
        	contarVacantes = false;
        }
        if (idCeo != null && !idCeo.equalsIgnoreCase("TODOS")) {
        	sql.append("JOIN cte.ceo_legajos ceol ON ps.cedula_identidad = ceol.cedula_identidad ");
        	sql.append("AND ceol.anho = ps.anho AND ceol.mes = ps.mes AND ceol.id_ceo = " + idCeo);
        	contarVacantes = false;
        }
        if (idCptEf != null && !idCptEf.equalsIgnoreCase("TODOS")) {
        	sql.append("JOIN cte.cpt_ef_legajos cptfl ON ");
        	sql.append("ps.cedula_identidad = cptfl.cedula_identidad ");
        	sql.append("AND cptfl.anho = ps.anho AND cptfl.mes = ps.mes ");
        	sql.append("AND cptfl.id_cpt_ef = " + idCptEf);
        	if (ambito != null && !ambito.equalsIgnoreCase("TODOS")) {
        		if (ambito.equalsIgnoreCase("Jurisdiccional")) {
            		ambito = "J";
            	} else {
            		ambito = "A";
            	}
        		sql.append("JOIN cte.cpt_ef cptf on cptfl.id_cpt_ef = cptf.id AND cptf.ambito = '" + ambito + "'");
        	}
        	contarVacantes = false;
        }
        if (ambito != null && !ambito.equalsIgnoreCase("TODOS") 
        		&& (idCptEf == null || idCptEf.equalsIgnoreCase("TODOS"))) {
        	
        	contarVacantes = false;
        	if (ambito.equalsIgnoreCase("Jurisdiccional")) {
        		ambito = "J";
        	} else {
        		ambito = "A";
        	}
        	sql.append("JOIN cte.cpt_ef_legajos cptfl on ps.cedula_identidad = cptfl.cedula_identidad ");
        	sql.append("AND cptfl.anho = ps.anho AND cptfl.mes = ps.mes ");
        	sql.append("JOIN cte.cpt_ef cptf on cptfl.id_cpt_ef = cptf.id AND cpte.ambito = '" + ambito + "'");
        }
        
        if (sexo != null && !sexo.equalsIgnoreCase("TODOS")) {
        	sql.append("JOIN cte.v_cte_funcionario l ON ps.cedula_identidad = l.cedula_identidad ");
        	sql.append(" AND l.sexo = '" + sexo + "' ");
        	contarVacantes = false;
        }
        
        sql.append(" WHERE ps.anho = " + anho + " and ps.mes= " + mes);
        sql.append(" AND ps.concepto in ('SUELDO','HONORARIOS PROFESIONALES','JORNALES')");
        
        if (vinculacion != null && !vinculacion.equalsIgnoreCase("TODOS")) {
        	sql.append(" AND ps.vinculacion_funcionario = '" + vinculacion + "'");
        	contarVacantes = false;
        }
        
        Query q = em.createNativeQuery(sql.toString());
        System.err.println(sql.toString());
        BigInteger total = (BigInteger) q.getSingleResult();
        
        // Cargos vacantes
        BigInteger vacantes = BigInteger.ZERO;
        if (contarVacantes) {
	        sql = new StringBuilder();
	        sql.append("SELECT count(1) from cte.v_cte_anexo_liquidacion c ");
	        sql.append(" WHERE c.anho = " + anho + " and c.mes = " + mes);
	        sql.append(" AND (c.nombre = 'VACANTE' OR c.apellido = 'VACANTE')");
	        q = em.createNativeQuery(sql.toString());
	        vacantes = (BigInteger) q.getSingleResult();
        }
        
        Long cantidad = total.longValue() + vacantes.longValue();
        return cantidad;
        
    }
    
    public long getCedulasSinRepetirHacienda(int anho, int mes) {
    	
    	StringBuilder sb = new StringBuilder(); 
    	sb.append("select count(DISTINCT ps.cedula_identidad) FROM cte.v_cte_anexo_liquidacion ps ");
    	sb.append("WHERE ps.anho = %s and ps.mes= %s ");
    	sb.append("AND ps.concepto in ('SUELDO','HONORARIOS PROFESIONALES','JORNALES') ");
    	String sql = String.format(sb.toString(), anho, mes);
    	Query q = em.createNativeQuery(sql);
    	BigInteger total = (BigInteger) q.getSingleResult();
    	return total.longValue();
    }
    
    public long getTotalByVinculoHacienda(int anho, int mes, String vinculo) {
    	
    	StringBuilder sb = new StringBuilder(); 
    	sb.append("select count(DISTINCT ps.cedula_identidad) FROM cte.v_cte_anexo_liquidacion ps ");
    	sb.append("WHERE ps.anho = %s and ps.mes= %s ");
    	sb.append("AND ps.concepto in ('SUELDO','HONORARIOS PROFESIONALES','JORNALES') ");
    	sb.append("AND ps.vinculacion_funcionario='%s'");
    	String sql = String.format(sb.toString(), anho, mes, vinculo);
    	Query q = em.createNativeQuery(sql);
    	BigInteger total = (BigInteger) q.getSingleResult();
    	return total.longValue();
    }
    
    /**
     * Obtiene los cargos totales para el periodo.
     * Hace la sumatoria del total de cedulas distintas mas los cargos vacantes.
     */
    public long getCargosTotalesHacienda(int anho, int mes, String vinculacion) {
    	
    	StringBuilder sql = new StringBuilder();
    	sql.append("SELECT count(DISTINCT ps.cedula_identidad) FROM cte.v_cte_anexo_liquidacion ps ");
    	sql.append(" WHERE ps.anho = " + anho + " and ps.mes= " + mes);
        sql.append(" AND ps.concepto in ('SUELDO','HONORARIOS PROFESIONALES','JORNALES')");
        
    	if (vinculacion != null && !vinculacion.equalsIgnoreCase("TODOS")) {
        	sql.append(" AND ps.vinculacion_funcionario = '" + vinculacion + "'");
        }
        
        Query q = em.createNativeQuery(sql.toString());
        BigInteger total = (BigInteger) q.getSingleResult();
        
        // Cargos vacantes
        sql = new StringBuilder();
        sql.append("SELECT count(1) from cte.v_cte_anexo_liquidacion c ");
        sql.append(" WHERE c.anho = " + anho + " and c.mes = " + mes);
        sql.append(" AND (c.nombre = 'VACANTE' OR c.apellido = 'VACANTE')");
        q = em.createNativeQuery(sql.toString());
        BigInteger vacantes = (BigInteger) q.getSingleResult();
        
        Long cantidad = total.longValue() + vacantes.longValue();
        return cantidad;
    	
    }
}

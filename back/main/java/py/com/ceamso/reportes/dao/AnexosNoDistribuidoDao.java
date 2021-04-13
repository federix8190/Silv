/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.dao;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.Query;
import org.hibernate.transform.Transformers;
import org.jboss.resteasy.util.DateUtil.DateParseException;


import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableDAO;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.reportes.model.AnexosNoDistribuido;
import py.com.ceamso.utils.Constantes;

/**
 *
 * @author daniel.rojas
 */
public class AnexosNoDistribuidoDao extends ReadableDAO<AnexosNoDistribuido> {
	
	@Inject
    @Configuracion("esHacienda")
    private String esHacienda;
     
    /**
     * 
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return AnexosNoDistribuido.class;
    }
    
    private static final Map<String, String> filtrosMap;
    static {
        Map<String, String> map = new HashMap<String, String>();
        map.put("cedulaIdentidad", "a.cedula_identidad");
        map.put("apellido", "a.apellido");
        map.put("nombre", "a.nombre");
        map.put("fechaNacimiento", "fun.fecha_nacimiento");
        map.put("sexo", "fun.sexo");
        map.put("profesion", "fa.descripcion_profesion");
        map.put("antiguedad", "fun.antiguedad");
        //map.put("anhoSF", "b.anho");
        //map.put("mesSF", "b.mes");
        map.put("nivelEntidadSF", "b.nivel_entidad");
        map.put("entidadSF", "b.entidad");
        map.put("oeeSF", "b.oee");
        map.put("lineaSF", "b.linea");
        map.put("fuenteFinanciamientoSF", "b.fuente_financiamiento");
        map.put("programaSF", "b.programa");
        map.put("subprogramaSF", "b.subprograma");
        map.put("dependenciaSF", "b.dependencia");
        map.put("categoriaSalarialSF", "b.categoria");
        map.put("conceptoSF", "b.concepto");
        map.put("presupuestadoSF", "b.presupuestado");
        map.put("regimenSF", "b.vinculacion_funcionario");
        map.put("vacanteSF", "b.cv2.asignable");
        map.put("objetoGastoSF", "b.objeto_gasto");
        map.put("devengadoSF", "b.devengado");
        map.put("cargoSF", "b.cargo");
        map.put("funcionRealSF", "b.funcion_real");
        map.put("numeroPuestoTrabajoSF", "pt2.numero_puesto");
        map.put("codigoCeoSF", "ceo2.codigo");
        map.put("denominacionCeoSF", "ceo2.den");
        map.put("orientacionFuncionalSF", "of2.nombre");
        map.put("nivelCuoSF", "cuo2.nivel");
        map.put("subnivelCuoSF", "cuo2.subnivel");
        map.put("numeroCuoSF", "cuo2.numero");
        map.put("denominacionCuoSF", "cuo2.denominacion");
        map.put("nivelCptSF", "cpt2.nivel");
        map.put("subnivelCptSF", "cpt2.sub_nivel");
        map.put("numeroCptSF", "cpt2.nro_g");
        map.put("denominacionCptSF", "cpt2.den");
        map.put("titularUnidadSF", "cpt2.tit_unid");
        map.put("numeroSecuencialSF", "cpt_ef2.nro");
        map.put("ambitoCptEfSF", "ambito2.nombre");
        map.put("codigoProcesoSF", "cpt_ef2.cod_proceso");
        map.put("denominacionCptFSF", "cpt_ef2.den");
        map.put("numeroTramoSF", "cs2.numero_tramo");
        map.put("minimoSF", "cs2.minimo");
        map.put("maximoSF", "cs2.maximo");
        //map.put("anho", "a.anho");
        //map.put("mes", "a.mes");
        map.put("nivelEntidad", "a.nivel_entidad");
        map.put("entidad", "a.entidad");
        map.put("oee", "a.oee");
        map.put("linea", "a.linea");
        map.put("fuenteFinanciamiento", "a.fuente_financiamiento");
        map.put("programa", "a.programa");
        map.put("subprograma", "a.subprograma");
        map.put("dependencia", "a.dependencia");
        map.put("categoriaSalarial", "a.categoria");
        map.put("concepto", "a.concepto");
        map.put("presupuestado", "a.presupuestado");
        map.put("regimen", "a.vinculacion_funcionario");
        map.put("vacante", "a.cv1.asignable");
        map.put("objetoGasto", "a.objeto_gasto");
        map.put("devengado", "a.devengado");
        map.put("cargo", "a.cargo");
        map.put("funcionReal", "a.funcion_real");
        map.put("numeroPuestoTrabajo", "pt1.numero_puesto");
        map.put("codigoCeo", "ceo1.codigo");
        map.put("denominacionCeo", "ceo1.den");
        map.put("orientacionFuncional", "of1.nombre");
        map.put("nivelCuo", "cuo1.nivel");
        map.put("subnivelCuo", "cuo1.subnivel");
        map.put("numeroCuo", "cuo1.numero");
        map.put("denominacionCuo", "cuo1.denominacion");
        map.put("nivelCpt", "cpt1.nivel");
        map.put("subnivelCpt", "cpt1.sub_nivel");
        map.put("numeroCpt", "cpt1.nro_g");
        map.put("denominacionCpt", "cpt1.den");
        map.put("titularUnidad", "cpt1.tit_unid");
        map.put("numeroSecuencial", "cpt_ef1.nro");
        map.put("ambitoCptEf", "ambito1.nombre");
        map.put("codigoProceso", "cpt_ef1.cod_proceso");
        map.put("denominacionCptF", "cpt_ef1.den");
        map.put("numeroTramo", "cs1.numero_tramo");
        map.put("minimo", "cs1.minimo");
        map.put("maximo", "cs1.maximo");
        filtrosMap = Collections.unmodifiableMap(map);
    }
    
   public Long getCptEPorCategori(Integer cedulaIdentidad, int anho, int mes, int anhoSF, int mesSF) {
        StringBuilder sql = new StringBuilder();
        
        sql.append("SELECT c.id FROM CptE c ");
        sql.append("INNER JOIN AnexosNoDistribuido a ON c.nombreCategoria = a.categoria ");
        sql.append("WHERE a.id.cedulaIdentidad = :cedulaIdentidad");
        sql.append(" AND a.id.anho = :anho AND a.id.mes = :mes and a.id.anhoSF = :anhoSF and a.id.mesSF = :mesSF");
        
        Query q = em.createQuery(sql.toString());
        q.setParameter("cedulaIdentidad", cedulaIdentidad);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        q.setParameter("anhoSF", anhoSF);
        q.setParameter("mesSF", mesSF);
        
        List<Long> list = q.getResultList();
        
        Long res = new Long(0);
        if (list.size() > 0)
            res = list.get(0);
        
        return res;
    }
    
    public String buildWhere(HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty() || filtros.size() == 4) {
            return "";
        }  
                
        filtros.remove("anho");
        filtros.remove("anhoSF");
        filtros.remove("mes");
        filtros.remove("mesSF");
        
        int tokens = filtros.keySet().size();
        int token = 1;
        
        String where = " AND ";
        for (String key : filtros.keySet()) {
            
            if(filtrosMap.get(key) != null){
                if (filtros.get(key) instanceof String) {
                    where += "LOWER(" + filtrosMap.get(key) + ") LIKE LOWER(" + filtros.get(key) + ")";

                } else {
                    where += filtrosMap.get(key) + " = " + filtros.get(key);
                }
                
                if (token < tokens) {
                    where += " AND ";
                }
                token++;
            }
            
        }
        return where;
    }
    
    @Override
    public ListaResponse<AnexosNoDistribuido> listar(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros) {
   
    	if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {
    		return listarHacienda(inicio, cantidad, orderBy, odrerDir, filtros);
    	} else {
    		return listarPalacio(inicio, cantidad, orderBy, odrerDir, filtros);
    	}
    }
    
    private ListaResponse<AnexosNoDistribuido> listarPalacio(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros) {
    	
    	int anhoI = (Integer) filtros.get("anho"); 
    	int mesI = (Integer) filtros.get("mes"); 
    	int anhoF = (Integer) filtros.get("anhoSF"); 
    	int mesF = (Integer)filtros.get("mesSF");
    	
    	StringBuilder sql = new StringBuilder();
    	sql.append("select top 20 t.*, cpt1.nivel, cpt1.sub_nivel, cpt1.nro_g, cpt1.den, cpt1.tit_unid, ");
    	sql.append("cpte1.nro, cpte1.ambito, cpte1.den, cpt2.nivel, cpt2.sub_nivel, cpt2.nro_g, cpt2.den, ");
    	sql.append("cpt2.tit_unid, cpte2.nro, cpte2.ambito, cpte2.den from (");
    	sql.append("select distinct case when (dbo.getCaso(ps.cedula_identidad, :anhoI,6,:anhoF,6) = 1) then 'X' else '' end as caso1,");
    	sql.append("case when (dbo.getCaso(ps.cedula_identidad, :anhoI,6,:anhoF,6) = 2) then 'X' else '' end as caso2,");
    	sql.append("case when (dbo.getCaso(ps.cedula_identidad, :anhoI,6,:anhoF,6) = 3) then 'X' else '' end as caso3,");
    	sql.append("case when (dbo.getCaso(ps.cedula_identidad, :anhoI,6,:anhoF,6) = 4) then 'X' else '' end as caso4,");
    	sql.append("ps.cedula_identidad, (select top 1 apellido from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad) as apellido,");
    	sql.append("(select top 1 nombre from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad) as nombre,");
    	sql.append("Convert(date, l.fecha_nacimiento) as fecha_nacimiento, '' as fecha_ingreso, CASE WHEN l.sexo = 1 THEN 'M' ELSE 'F' END AS sexo,");
    	sql.append("'' as profesion_f, '' as nivel_educativo_f, '' as orientacion_f, YEAR(getdate()) - YEAR(l.fecha_nacimiento) as edad_f, ");
    	sql.append("(select antiguedad from dbo.funcionario_antiguedad where cedula_identidad = ps.cedula_identidad) as antiguedad_f, ");
    	sql.append(":anhoF as anhoF, :mesF as mesF, '13' as nivel_entidad_f, 'CORTE SUPREMA DE JUSTICIA' as entidad_f, '1' as oee_f,");
    	sql.append("(select top 1 linea_presup from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append("and ejercicio=:anhoF and mes= :mesF and objeto in (111,145,144)) as linea_f,");
    	sql.append("(select top 1 fuente_financiamiento from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append("and ejercicio=:anhoF and mes= :mesF and objeto in (111,145,144)) as fuente_financiamiento_f,");
    	sql.append("(select top 1 presupuestado from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append("and ejercicio=:anhoF and mes= :mesF and objeto in (111,145,144)) as presupuesto_f,");
    	sql.append("(select top 1 devengado from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append("and ejercicio=:anhoI and mes= :mesF and objeto in (111,145,144)) as devengado_f,");
    	sql.append("(select top 1 descrip_objeto_gasto from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append("and ejercicio=:anhoF and mes= :mesF and objeto in (111,145,144)) as concpepto_f,");
    	sql.append("(select top 1 categoria_personal from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append("and ejercicio=:anhoF and mes= :mesF and objeto in (111,145,144)) as categoria_f,");
    	sql.append("(select top 1 vinculo_laboral from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append("and ejercicio=:anhoF and mes= :mesF and objeto in (111,145,144)) as vinculo_laboral_f,");
    	sql.append("(select top 1 titulo from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append("and ejercicio=:anhoF and mes= :mesF and objeto in (111,145,144)) as dependencia_f,");
    	sql.append("(select top 1 descrip_programa_presup from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append("and ejercicio=:anhoF and mes= :mesF and objeto in (111,145,144)) as programa_f,");
    	sql.append("(select top 1 descrip_subprograma_presup from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append("and ejercicio=:anhoF and mes= :mesF and objeto in (111,145,144)) as subprograma_f,");
    	sql.append("(select top 1 nombre_cargo from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append("and ejercicio=:anhoF and mes= :mesF and objeto in (111,145,144)) as cargo_f,");
    	sql.append("(select top 1 numero_puesto from dbo.puesto_trabajo where cedula_identidad = ps.cedula_identidad and anho=:anhoF and mes= :mesF) as numero_puesto_f,");
    	sql.append("case when pog1.objeto_gasto  like '%111%' then 'X' else '' end as objeto_111_f,");
    	sql.append("case when pog1.objeto_gasto  like '%113%' then 'X' else '' end as objeto_113_f,");
    	sql.append("case when pog1.objeto_gasto  like '%122%' then 'X' else '' end as objeto_122_f,");
    	sql.append("case when pog1.objeto_gasto  like '%123%' then 'X' else '' end as objeto_123_f,");
    	sql.append("case when pog1.objeto_gasto  like '%131%' then 'X' else '' end as objeto_131_f,");
    	sql.append("case when pog1.objeto_gasto  like '%133%' then 'X' else '' end as objeto_133_f,");
    	sql.append("case when pog1.objeto_gasto  like '%137%' then 'X' else '' end as objeto_137_f,");
    	sql.append("case when pog1.objeto_gasto  like '%142%' then 'X' else '' end as objeto_142_f,");
    	sql.append("case when pog1.objeto_gasto  like '%144%' then 'X' else '' end as objeto_144_f,");
    	sql.append("case when pog1.objeto_gasto  like '%145%' then 'X' else '' end as objeto_145_f,");
    	sql.append("case when pog1.objeto_gasto  like '%199%' then 'X' else '' end as objeto_199_f,");
    	sql.append("(select id_cpt from dbo.cpt_legajos where cedula_identidad = ps.cedula_identidad and anho=:anhoF) as id_cpt_f,");
    	sql.append("(select id_cpt_ee from dbo.cpt_ee_legajos where cedula_identidad = ps.cedula_identidad and anho=:anhoF) as id_cpt_ee_f,");
    	sql.append("p1.numero_tramo as numero_tramo_f,");
    	sql.append("case when p1.numero_tramo > (select max(numero_tramo) from dbo.cpt_tramos where nivel_cpt = p1.nivel_cpt and anho = :anhoF and mes = :mesF) ");
    	sql.append("then 'X' else '' end as sobre_f,");
    	sql.append("case when p1.numero_tramo <= (select max(numero_tramo) from dbo.cpt_tramos where nivel_cpt = p1.nivel_cpt and anho = :anhoF and mes = :mesF)");
    	sql.append("and p1.numero_tramo >= (select min(numero_tramo) from dbo.cpt_tramos where nivel_cpt = p1.nivel_cpt and anho = :anhoF and mes = :mesF)");
    	sql.append("then 'X' else '' end as dentro_f,");
    	sql.append("case when p1.numero_tramo < (select min(numero_tramo) from dbo.cpt_tramos where nivel_cpt = p1.nivel_cpt and anho = :anhoF and mes = :mesF) ");
    	sql.append("then 'X' else '' end as debajo_f,");
    	sql.append(":anhoI as anhoI, :mesI as mesI, '13' as nivel_entidad_I, 'CORTE SUPREMA DE JUSTICIA' as entidad_I, '1' as oee_I,");
    	sql.append("'' as profesion_I, '' as nivel_educativo_I, '' as orientacion_I, YEAR(getdate()) - YEAR(l.fecha_nacimiento) - 2 as edad_I, ");
    	sql.append(" (select antiguedad from dbo.funcionario_antiguedad where cedula_identidad = ps.cedula_identidad) - 2 as antiguedad_I, ");
    	sql.append(" (select top 1 linea_presup from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append(" and ejercicio=:anhoI and mes= :mesI and objeto in (111,145,144)) as linea_I,");
    	sql.append(" (select top 1 fuente_financiamiento from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append(" and ejercicio=:anhoI and mes= :mesI and objeto in (111,145,144)) as fuente_financiamiento_I,");
    	sql.append(" (select top 1 presupuestado from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append(" and ejercicio=:anhoI and mes= :mesI and objeto in (111,145,144)) as presupuesto_I,");
    	sql.append(" (select top 1 devengado from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append(" and ejercicio=:anhoI and mes= :mesI and objeto in (111,145,144)) as devengado_I,");
    	sql.append(" (select top 1 descrip_objeto_gasto from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append(" and ejercicio=:anhoI and mes= :mesI and objeto in (111,145,144)) as concpepto_I,");
    	sql.append(" (select top 1 categoria_personal from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append(" and ejercicio=:anhoI and mes= :mesI and objeto in (111,145,144)) as categoria_I,");
    	sql.append(" (select top 1 vinculo_laboral from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append(" and ejercicio=:anhoI and mes= :mesI and objeto in (111,145,144)) as vinculo_laboral_I,");
    	sql.append(" (select top 1 titulo from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append(" and ejercicio=:anhoI and mes= :mesI and objeto in (111,145,144)) as dependencia_I,");
    	sql.append(" (select top 1 descrip_programa_presup from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append(" and ejercicio=:anhoI and mes= :mesI and objeto in (111,145,144)) as programa_I,");
    	sql.append(" (select top 1 descrip_subprograma_presup from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append(" and ejercicio=:anhoI and mes= :mesI and objeto in (111,145,144)) as subprograma_I,");
    	sql.append(" (select top 1 nombre_cargo from dbo.planilla_sueldo where cedula_identidad=ps.cedula_identidad ");
    	sql.append(" and ejercicio=:anhoI and mes= :mesI and objeto in (111,145,144)) as cargo_I,");
    	sql.append(" (select top 1 numero_puesto from dbo.puesto_trabajo where cedula_identidad = ps.cedula_identidad and anho=:anhoI and mes= :mesI) as numero_puesto_I,");
    	sql.append(" case when pog2.objeto_gasto  like '%111%' then 'X' else '' end as objeto_111_I,");
    	sql.append(" case when pog2.objeto_gasto  like '%113%' then 'X' else '' end as objeto_113_I,");
    	sql.append(" case when pog2.objeto_gasto  like '%122%' then 'X' else '' end as objeto_122_I,");
    	sql.append(" case when pog2.objeto_gasto  like '%123%' then 'X' else '' end as objeto_123_I,");
    	sql.append(" case when pog2.objeto_gasto  like '%131%' then 'X' else '' end as objeto_131_I,");
    	sql.append(" case when pog2.objeto_gasto  like '%133%' then 'X' else '' end as objeto_133_I,");
    	sql.append(" case when pog2.objeto_gasto  like '%137%' then 'X' else '' end as objeto_137_I,");
    	sql.append(" case when pog2.objeto_gasto  like '%142%' then 'X' else '' end as objeto_142_I,");
    	sql.append(" case when pog2.objeto_gasto  like '%144%' then 'X' else '' end as objeto_144_I,");
    	sql.append(" case when pog2.objeto_gasto  like '%145%' then 'X' else '' end as objeto_145_I,");
    	sql.append(" case when pog2.objeto_gasto  like '%199%' then 'X' else '' end as objeto_199_I,");
    	sql.append(" (select id_cpt from dbo.cpt_legajos where cedula_identidad = ps.cedula_identidad and anho=:anhoI) as id_cpt_I,");
    	sql.append(" (select id_cpt_ee from dbo.cpt_ee_legajos where cedula_identidad = ps.cedula_identidad and anho=:anhoI) as id_cpt_ee_I,");
    	sql.append(" p2.numero_tramo as numero_tramo_I,");
    	sql.append(" case when p2.numero_tramo > (select max(numero_tramo) from dbo.cpt_tramos where nivel_cpt = p2.nivel_cpt and anho = :anhoI and mes = :mesI) ");
    	sql.append(" then 'X' else '' end as sobre_I,");
    	sql.append(" case when p2.numero_tramo <= (select max(numero_tramo) from dbo.cpt_tramos where nivel_cpt = p2.nivel_cpt and anho = :anhoI and mes = :mesI)");
    	sql.append(" and p2.numero_tramo >= (select min(numero_tramo) from dbo.cpt_tramos where nivel_cpt = p2.nivel_cpt and anho = :anhoI and mes = :mesI)");
    	sql.append(" then 'X' else '' end as dentro_I,");
    	sql.append(" case when p2.numero_tramo < (select min(numero_tramo) from dbo.cpt_tramos where nivel_cpt = p2.nivel_cpt and anho = :anhoI and mes = :mesI) ");
    	sql.append(" then 'X' else '' end as debajo_I");
    	sql.append(" from dbo.planilla_sueldo ps ");
    	sql.append(" left join dbo.legajo_personal l on ps.cedula_identidad = l.cedula_identidad");
    	sql.append(" left join dbo.persona_objeto_gasto pog1 on ps.cedula_identidad = pog1.cedula_identidad and pog1.anho=:anhoF and pog1.mes= :mesF");
    	sql.append(" left join dbo.persona_objeto_gasto pog2 on ps.cedula_identidad = pog2.cedula_identidad and pog2.anho=:anhoI and pog2.mes= :mesI");
    	sql.append(" left join dbo.puesto_trabajo p1 on ps.cedula_identidad=p1.cedula_identidad and p1.anho=:anhoF");
    	sql.append(" left join dbo.puesto_trabajo p2 on ps.cedula_identidad=p2.cedula_identidad and p2.anho=:anhoF");
    	sql.append(" where ps.objeto in (111,145,144) and ");
    	sql.append(" ((ps.ejercicio = :anhoF  and ps.mes = :mesF) or (ps.ejercicio = :anhoI and ps.mes = :mesI)) ");
    	sql.append(" and ps.cedula_identidad <> 999999999 and ps.cedula_identidad <> 0) t");
    	sql.append(" left join dbo.cpt_ee cpte1 on t.id_cpt_ee_f = cpte1.id");
    	sql.append(" left join dbo.cpt_ee cpte2 on t.id_cpt_ee_I = cpte2.id");
    	sql.append(" left join dbo.cpt cpt1 on cpte1.id_cpt = cpt1.id");
    	sql.append(" left join dbo.cpt cpt2 on cpte2.id_cpt = cpt2.id");

    	Query q = em.createNativeQuery(sql.toString());
    	q.setParameter("anhoI", anhoI);
    	q.setParameter("anhoF", anhoF);
    	q.setParameter("mesI", mesI);
    	q.setParameter("mesF", mesF);
    	System.err.println("-------------------------------");
    	System.err.println(sql.toString());
    	System.err.println("-------------------------------");
    	
    	List<Object[]> lista = q.getResultList();
        List<AnexosNoDistribuido> list = new ArrayList<AnexosNoDistribuido>();
        
        for (Object[] datos : lista) {
        	int cedula = (Integer) datos[0];
        	String apellido = (String) datos[1];
        	String nombre = (String) datos[2];
        	Date fechaNacimiento = (Date) datos[3];
        	String sexo = (String) datos[4];
        	sexo = sexo.equals("M") ? "Masculino" : "Femenino";
        	int linea = (Integer) datos[5];
        	int oee = (Integer) datos[6];
        	String vinculacion = (String) datos[7];
        	String cargo = (String) datos[8];
        	int presupuestado = (Integer) datos[9];
        	String denominacionCpt = (String) datos[10];
        	BigInteger nivelCpt = (BigInteger) datos[11];
            Integer subnivelCpt = (Integer) datos[12];
            Integer numeroCpt = (Integer) datos[13];
            AnexosNoDistribuido anexo = new AnexosNoDistribuido();
            anexo.setCedulaIdentidad(cedula);
            anexo.setApellido(apellido);
            anexo.setNombre(nombre);
            anexo.setFechaNacimiento(fechaNacimiento);
            anexo.setSexo(sexo);
            anexo.setLinea(linea);
            anexo.setOee(oee);
            anexo.setVinculacion(vinculacion);
            anexo.setCargo(cargo);
            anexo.setPresupuestado(presupuestado);
            anexo.setDenominacionCpt(denominacionCpt);
            anexo.setNivelCpt(nivelCpt);
            anexo.setSubnivelCpt(subnivelCpt);
            anexo.setNumeroCpt(numeroCpt);
            list.add(anexo);
        }
        
        int total = 100;//count(filtros, anho, mes, anhoSF, mesSF); 
        ListaResponse<AnexosNoDistribuido> res = new ListaResponse<AnexosNoDistribuido>();
        res.setRows(list);
        res.setCount(total);
        return res;
    
    }
    
    private ListaResponse<AnexosNoDistribuido> listarHacienda(int inicio, int cantidad, String orderBy, 
            String odrerDir, HashMap<String, Object> filtros) {
    	String sql = "SELECT DISTINCT " +
        "a.cedula_identidad AS cedulaIdentidad, " +
        "a.apellido, " +
        "a.nombre, " +
        "fun.fecha_nacimiento AS fechaNacimiento, " +
        "'2018-11-27' AS fechaIngreso, " +
        "fun.sexo, " +
        "fa.descripcion_profesion AS profesion, " +
        "CASE" +
        " WHEN CAST(fa.descripcion_grado AS text) ~~ 'PRIMARIA' AND NOT (CAST(fa.descripcion_anhos AS text) ~~ 'CULMINADO' OR CAST(fa.descripcion_anhos AS text) ~~ 'SEXTO') THEN 3" +
        " WHEN CAST(fa.descripcion_grado AS text) ~~ 'PRIMARIA' AND (CAST(fa.descripcion_anhos AS text) ~~ 'CULMINADO' OR CAST(fa.descripcion_anhos AS text) ~~ 'SEXTO') THEN 2" +
        " WHEN CAST(fa.descripcion_grado AS text) ~~ 'SECUNDARIA' THEN 1" +
        " ELSE 0 " +
        "END AS nivelEducativo, " +
        "CASE" +
        " WHEN CAST(fa.descripcion_carrera AS text) ~~ '%HACIENDA%' THEN 0" +
        " WHEN CAST(fa.descripcion_carrera AS text) ~~ '%DERECHO%' OR CAST(fa.descripcion_carrera AS text) ~~ '%ABOGA%' THEN 1" +
        " WHEN (CAST(fa.descripcion_carrera AS text) ~~ '%INFO%' OR CAST(fa.descripcion_carrera AS text) ~~ '%DE SISTEMAS%') AND (CAST(fa.descripcion_carrera AS text) !~~ '%DERECHO%' OR CAST(fa.descripcion_carrera AS text) !~~ '%ABOGA%') THEN 2" +
        " WHEN CAST(fa.descripcion_carrera AS text) ~~ '%SOCIALES%' THEN 3" +
        " ELSE 4 " +
        "END AS orientacion," +
        "CAST(date_part(CAST('year' AS text), age(CAST(fun.fecha_nacimiento AS timestamp with time zone))) AS integer) AS edad," +
        "fun.antiguedad, " +
        "b.anho AS anhoSF, " +
        "b.mes AS mesSF, " +
        "b.nivel_entidad AS nivelEntidadSF, " +
        "b.entidad AS entidadSF, " +
        "b.oee AS oeeSF, " +
        "b.linea AS lineaSF, " +
        "b.fuente_financiamiento AS fuenteFinanciamientoSF, " +
        "b.programa AS programaSF, " +
        "b.subprograma AS subprogramaSF, " +
        "b.dependencia AS dependenciaSF, " +
        "b.categoria AS categoriaSalarialSF, " +
        "b.concepto AS conceptoSF, " +
        "b.presupuestado AS presupuestadoSF, " +
        "b.vinculacion_funcionario AS regimenSF, " +
        "cv2.asignable AS vacanteSF, " +
        "(select array_to_string(array_agg(DISTINCT objeto_gasto), CAST( ';'  as text)) from " + Constantes.ESQUEMA_CTE_MH + ".anexos where cedula_identidad = b.cedula_identidad and anho = " + ((Integer)filtros.get("anho")).toString()  + " and mes = " + ((Integer)filtros.get("mes")).toString()  + ") AS objetoGastoSF, " +
        "b.devengado AS devengadoSF, " +
        "b.cargo AS cargoSF, " +
        "b.funcion_real AS funcionRealSF, " +
        "pt2.numero_puesto AS numeroPuestoTrabajoSF, " +
        "ceo2.codigo AS codigoCeoSF, " +
        "ceo2.den AS denominacionCeoSF, " +
        "of2.nombre AS orientacionFuncionalSF, " +
        "cuo2.nivel AS nivelCuoSF, " +
        "cuo2.subnivel AS subnivelCuoSF, " +
        "cuo2.numero AS numeroCuoSF, " +
        "cuo2.denominacion AS denominacionCuoSF, " +
        "cpt2.nivel AS nivelCptSF, " +
        "cpt2.sub_nivel AS subnivelCptSF, " +
        "cpt2.nro_g AS numeroCptSF, " +
        "cpt2.den AS denominacionCptSF, " +
        "cpt2.tit_unid AS titularUnidadSF, " +
        "cpt_ef2.nro AS numeroSecuencialSF, " +
        "ambito2.nombre AS ambitoCptEfSF, " +
        "cpt_ef2.cod_proceso AS codigoProcesoSF, " +
        "cpt_ef2.den AS denominacionCptFSF, " +
        "cs2.numero_tramo AS numeroTramoSF, " +
        "cs2.minimo AS minimoSF, " +
        "cs2.maximo AS maximoSF, " +
        "a.anho AS anho, " +
        "a.mes AS mes, " +
        "a.nivel_entidad AS nivelEntidad, " +
        "a.entidad AS entidad, " +
        "a.oee AS oee, " +
        "a.linea AS linea, " +
        "a.fuente_financiamiento AS fuenteFinanciamiento, " +
        "a.programa AS programa, " +
        "a.subprograma AS subprograma, " +
        "a.dependencia AS dependencia, " +
        "a.categoria AS categoriaSalarial, " +
        "a.concepto AS concepto, " +
        "a.presupuestado AS presupuestado, " +
        "a.vinculacion_funcionario AS regimen, " +
        "cv1.asignable AS vacante, " +
        "(select array_to_string(array_agg(DISTINCT objeto_gasto), CAST( ';'  as text))from " + Constantes.ESQUEMA_CTE_MH + ".anexos where cedula_identidad = a.cedula_identidad and anho = " + ((Integer)filtros.get("anho")).toString()  + " and mes = " + ((Integer)filtros.get("mes")).toString()  + ") AS objetoGasto, " +
        "a.devengado AS devengado, " +
        "a.cargo AS cargo, " +
        "a.funcion_real AS funcionReal, " +
        "pt1.numero_puesto AS numeroPuestoTrabajo, " +
        "ceo1.codigo AS codigoCeo, " +
        "ceo1.den AS denominacionCeo, " +
        "of1.nombre AS orientacionFuncional, " +
        "cuo1.nivel AS nivelCuo, " +
        "cuo1.subnivel AS subnivelCuo, " +
        "cuo1.numero AS numeroCuo, " +
        "cuo1.denominacion AS denominacionCuo, " +
        "cpt1.nivel AS nivelCpt, " +
        "cpt1.sub_nivel AS subnivelCpt, " +
        "cpt1.nro_g AS numeroCpt, " +
        "cpt1.den AS denominacionCpt, " +
        "cpt1.tit_unid AS titularUnidad, " +
        "cpt_ef1.nro AS numeroSecuencial, " +
        "ambito1.nombre AS ambitoCptEf, " +
        "cpt_ef1.cod_proceso AS codigoProceso, " +
        "cpt_ef1.den AS denominacionCptF, " +
        "cs1.numero_tramo AS numeroTramo, " +
        "cs1.minimo AS minimo, " +
        "cs1.maximo AS maximo " +
        "from " + Constantes.ESQUEMA_CTE_MH + ".anexos a " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".v_" + Constantes.ESQUEMA_CTE_MH + "_funcionario fun ON a.cedula_identidad = fun.cedula_identidad " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".anexos b on a.cedula_identidad = b.cedula_identidad and b.anho = " + ((Integer)filtros.get("anhoSF")).toString()  + " and b.mes = " + ((Integer)filtros.get("mesSF")).toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt_legajos cptl1 on a.cedula_identidad = cptl1.cedula_identidad and cptl1.anho = " + ((Integer)filtros.get("anho")).toString()  + " and cptl1.mes = " + ((Integer)filtros.get("mes")).toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt_legajos cptl2 on a.cedula_identidad = cptl2.cedula_identidad and cptl2.anho = " + ((Integer)filtros.get("anhoSF")).toString()  + " and cptl2.mes = " + ((Integer)filtros.get("mesSF")).toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt cpt1 on cptl1.id_cpt = cpt1.id " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt cpt2 on cptl2.id_cpt = cpt2.id " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".ceo_legajos ceol1 on a.cedula_identidad = ceol1.cedula_identidad and ceol1.anho = " + ((Integer)filtros.get("anho")).toString()  + " and ceol1.mes = " + ((Integer)filtros.get("mes")).toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".ceo_legajos ceol2 on a.cedula_identidad = ceol2.cedula_identidad and ceol2.anho = " + ((Integer)filtros.get("anhoSF")).toString()  + " and ceol2.mes = " + ((Integer)filtros.get("mesSF")).toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".ceo ceo1 on ceol1.id_ceo = ceo1.id " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".ceo ceo2 on ceol2.id_ceo = ceo2.id " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt_ef_legajos cpt_efl1 on a.cedula_identidad = cpt_efl1.cedula_identidad and cpt_efl1.anho = " + ((Integer)filtros.get("anho")).toString()  + " and cpt_efl1.mes = " + ((Integer)filtros.get("mes")).toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt_ef_legajos cpt_efl2 on a.cedula_identidad = cpt_efl2.cedula_identidad and cpt_efl2.anho = " + ((Integer)filtros.get("anhoSF")).toString()  + " and cpt_efl2.mes = " + ((Integer)filtros.get("mesSF")).toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt_ef cpt_ef1 on cpt_efl1.id_cpt_ef = cpt_ef1.id " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt_ef cpt_ef2 on cpt_efl2.id_cpt_ef = cpt_ef2.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".v_cte_cargos_vacantes cv1 ON CAST(a.cargo as text) = CAST(cv1.nombre as text) AND cv1.anho = " + ((Integer)filtros.get("anho")).toString()  + " AND cv1.mes = " + ((Integer)filtros.get("mes")).toString()  + " " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".v_cte_cargos_vacantes cv2 ON CAST(b.cargo as text) = CAST(cv2.nombre as text) AND cv2.anho = " + ((Integer)filtros.get("anhoSF")).toString()  + " AND cv2.mes = " + ((Integer)filtros.get("mesSF")).toString()  + " " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".v_ultima_formacion_academica fa ON CAST(replace(CAST(fa.cedula_funcionario as text), CAST('.' as text), CAST('' as text)) as integer) = fun.cedula_identidad " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".puesto_trabajo pt1 ON a.cedula_identidad = pt1.cedula_identidad and pt1.anho = " + ((Integer)filtros.get("anho")).toString()  + " AND pt1.mes = " + ((Integer)filtros.get("mes")).toString()  + " " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".puesto_trabajo pt2 ON b.cedula_identidad = pt2.cedula_identidad and pt2.anho = " + ((Integer)filtros.get("anhoSF")).toString()  + " AND pt2.mes = " + ((Integer)filtros.get("mesSF")).toString()  + " " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".orientacion_funcional of1 ON cpt_ef1.orientacion_func_id = of1.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".orientacion_funcional of2 ON cpt_ef2.orientacion_func_id = of2.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".cuo cuo1 ON ceo1.id_cuo = cuo1.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".cuo cuo2 ON ceo2.id_cuo = cuo2.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".ambito ambito1 ON cpt_ef1.ambito_id = ambito1.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".ambito ambito2 ON cpt_ef2.ambito_id = ambito2.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".cts cs1 ON CAST(a.presupuestado as numeric) >= CAST(cs1.minimo as numeric) AND CAST(a.presupuestado as numeric)  < CAST(cs1.maximo as numeric) AND cs1.anho = " + ((Integer)filtros.get("anho")).toString()  + " AND cs1.mes = " + ((Integer)filtros.get("mes")).toString()  + " " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".cts cs2 ON CAST(b.presupuestado as numeric) >= CAST(cs2.minimo as numeric) AND CAST(b.presupuestado as numeric)  < CAST(cs2.maximo as numeric) AND cs2.anho = " + ((Integer)filtros.get("anho")).toString()  + " AND cs2.mes = " + ((Integer)filtros.get("mes")).toString()  + " " +
        "where a.anho = " + ((Integer)filtros.get("anho")).toString()  + " and a.mes = " + ((Integer)filtros.get("mes")).toString()  + " and a.cedula_identidad > 0 and a.cedula_identidad < 99999999 " +
        "and a.concepto in ('SUELDO', 'JORNALES', 'HONORARIOS PROFESIONALES') and b.concepto in ('SUELDO', 'JORNALES', 'HONORARIOS PROFESIONALES') ";
        
        Integer anho = (Integer)filtros.get("anho"), mes = (Integer)filtros.get("mes"), anhoSF = (Integer)filtros.get("ahoSF"), mesSF = (Integer)filtros.get("mesSF");
        
        String aditionalWhere = buildWhere(filtros);
        String limit = cantidad == -1 ? "" : " limit " + String.valueOf(cantidad);
        String order = orderBy.equals("id") ? filtrosMap.get("cedulaIdentidad") : filtrosMap.get(orderBy);
        sql += aditionalWhere + " ORDER BY " + order + " " + odrerDir + limit;
                
        Query query = em.createNativeQuery(sql);
        System.err.println(sql);
        List<Object[]> lista = query.getResultList();
        List<AnexosNoDistribuido> list = new ArrayList<AnexosNoDistribuido>();
        
        for(Object[] o : lista){
            Date date = new Date();
            try{
                date = new SimpleDateFormat("yyyy-MM-dd").parse((String)o[4]);
            } catch(ParseException e){
                
            }
            AnexosNoDistribuido anexo = new AnexosNoDistribuido(
                    (Integer)o[0],
                    (String)o[1],
                    (String)o[2],
                    (Date)o[3],
                    date,
                    (String)o[5],
                    (String)o[6],
                    (Integer)o[7],
                    (Integer)o[8],
                    (Integer)o[9],
                    (Integer)o[10],
                    (Integer)o[11],
                    (Integer)o[12],
                    (Integer)o[13],
                    (String)o[14],
                    (Integer)o[15],
                    (Integer)o[16],
                    (Integer)o[17],
                    (String)o[18],
                    (String)o[19],
                    (String)o[20],
                    (String)o[21],
                    (String)o[22],
                    (Integer)o[23],
                    (String)o[24],
                    (Boolean)o[25],
                    (String)o[26],
                    (Integer)o[27],
                    (String)o[28],
                    (String)o[29],
                    (BigInteger)o[30],
                    (String)o[31],
                    (String)o[32],
                    (String)o[33],
                    (Integer)o[34],
                    (Integer)o[35],
                    (Integer)o[36],
                    (String)o[37],
                    (BigInteger)o[38],
                    (Integer)o[39],
                    (Integer)o[40],
                    (String)o[41],
                    (Boolean)o[42],
                    (String)o[43],
                    (String)o[44],
                    (String)o[45],
                    (String)o[46],
                    (BigInteger)o[47],
                    (BigInteger)o[48],
                    (BigInteger)o[49],
                    (Integer)o[50],
                    (Integer)o[51],
                    (Integer)o[52],
                    (String)o[53],
                    (Integer)o[54],
                    (Integer)o[55],
                    (Integer)o[56],
                    (String)o[57],
                    (String)o[58],
                    (String)o[59],
                    (String)o[60],
                    (String)o[61],
                    (Integer)o[62],
                    (String)o[63],
                    (Boolean)o[64],
                    (String)o[65],
                    (Integer)o[66],
                    (String)o[67],
                    (String)o[68],
                    (BigInteger)o[69],
                    (String)o[70],
                    (String)o[71],
                    (String)o[72],
                    (Integer)o[73],
                    (Integer)o[74],
                    (Integer)o[75],
                    (String)o[76],
                    (BigInteger)o[77],
                    (Integer)o[78],
                    (Integer)o[79],
                    (String)o[80],
                    (Boolean)o[81],
                    (String)o[82],
                    (String)o[83],
                    (String)o[84],
                    (String)o[85],
                    (BigInteger)o[86],
                    (BigInteger)o[87],
                    (BigInteger)o[88],
                    0
            );
            list.add(anexo);
        }
        
        int total = count(filtros, anho, mes, anhoSF, mesSF);
        //se construye la respuesta 
        ListaResponse<AnexosNoDistribuido> res = new ListaResponse<AnexosNoDistribuido>();
        res.setRows(list);
        res.setCount(total);
        return res;
    }
  
    private int count(HashMap<String, Object> filtros, Integer anho, Integer mes, Integer anhoSF, Integer mesSF) {
        String sql = "SELECT COUNT(*) FROM (SELECT DISTINCT " +
        "a.cedula_identidad AS cedulaIdentidad, " +
        "a.apellido, " +
        "a.nombre, " +
        "fun.fecha_nacimiento AS fechaNacimiento, " +
        "'2018-11-27' AS fechaIngreso, " +
        "fun.sexo, " +
        "fa.descripcion_profesion AS profesion, " +
        "CASE" +
        " WHEN CAST(fa.descripcion_grado AS text) ~~ 'PRIMARIA' AND NOT (CAST(fa.descripcion_anhos AS text) ~~ 'CULMINADO' OR CAST(fa.descripcion_anhos AS text) ~~ 'SEXTO') THEN 3" +
        " WHEN CAST(fa.descripcion_grado AS text) ~~ 'PRIMARIA' AND (CAST(fa.descripcion_anhos AS text) ~~ 'CULMINADO' OR CAST(fa.descripcion_anhos AS text) ~~ 'SEXTO') THEN 2" +
        " WHEN CAST(fa.descripcion_grado AS text) ~~ 'SECUNDARIA' THEN 1" +
        " ELSE 0 " +
        "END AS nivelEducativo, " +
        "CASE" +
        " WHEN CAST(fa.descripcion_carrera AS text) ~~ '%HACIENDA%' THEN 0" +
        " WHEN CAST(fa.descripcion_carrera AS text) ~~ '%DERECHO%' OR CAST(fa.descripcion_carrera AS text) ~~ '%ABOGA%' THEN 1" +
        " WHEN (CAST(fa.descripcion_carrera AS text) ~~ '%INFO%' OR CAST(fa.descripcion_carrera AS text) ~~ '%DE SISTEMAS%') AND (CAST(fa.descripcion_carrera AS text) !~~ '%DERECHO%' OR CAST(fa.descripcion_carrera AS text) !~~ '%ABOGA%') THEN 2" +
        " WHEN CAST(fa.descripcion_carrera AS text) ~~ '%SOCIALES%' THEN 3" +
        " ELSE 4 " +
        "END AS orientacion," +
        "CAST(date_part(CAST('year' AS text), age(CAST(fun.fecha_nacimiento AS timestamp with time zone))) AS integer) AS edad," +
        "fun.antiguedad, " +
        "b.anho AS anhoSF, " +
        "b.mes AS mesSF, " +
        "b.nivel_entidad AS nivelEntidadSF, " +
        "b.entidad AS entidadSF, " +
        "b.oee AS oeeSF, " +
        "b.linea AS lineaSF, " +
        "b.fuente_financiamiento AS fuenteFinanciamientoSF, " +
        "b.programa AS programaSF, " +
        "b.subprograma AS subprogramaSF, " +
        "b.dependencia AS dependenciaSF, " +
        "b.categoria AS categoriaSalarialSF, " +
        "b.concepto AS conceptoSF, " +
        "b.presupuestado AS presupuestadoSF, " +
        "b.vinculacion_funcionario AS regimenSF, " +
        "cv2.asignable AS vacanteSF, " +
        "(select array_to_string(array_agg(DISTINCT objeto_gasto), CAST(';' as text)) from " + Constantes.ESQUEMA_CTE_MH + ".anexos where cedula_identidad = b.cedula_identidad and anho = " + ((Integer)filtros.get("anho")).toString()  + " and mes = " + ((Integer)filtros.get("mes")).toString()  + ") AS objetoGastoSF, " +
        "b.devengado AS devengadoSF, " +
        "b.cargo AS cargoSF, " +
        "b.funcion_real AS funcionRealSF, " +
        "pt2.numero_puesto AS numeroPuestoTrabajoSF, " +
        "ceo2.codigo AS codigoCeoSF, " +
        "ceo2.den AS denominacionCeoSF, " +
        "of2.nombre AS orientacionFuncionalSF, " +
        "cuo2.nivel AS nivelCuoSF, " +
        "cuo2.subnivel AS subnivelCuoSF, " +
        "cuo2.numero AS numeroCuoSF, " +
        "cuo2.denominacion AS denominacionCuoSF, " +
        "cpt2.nivel AS nivelCptSF, " +
        "cpt2.sub_nivel AS subnivelCptSF, " +
        "cpt2.nro_g AS numeroCptSF, " +
        "cpt2.den AS denominacionCptSF, " +
        "cpt2.tit_unid AS titularUnidadSF, " +
        "cpt_ef2.nro AS numeroSecuencialSF, " +
        "ambito2.nombre AS ambitoCptEfSF, " +
        "cpt_ef2.cod_proceso AS codigoProcesoSF, " +
        "cpt_ef2.den AS denominacionCptFSF, " +
        "cs2.numero_tramo AS numeroTramoSF, " +
        "cs2.minimo AS minimoSF, " +
        "cs2.maximo AS maximoSF, " +
        "a.anho AS anho, " +
        "a.mes AS mes, " +
        "a.nivel_entidad AS nivelEntidad, " +
        "a.entidad AS entidad, " +
        "a.oee AS oee, " +
        "a.linea AS linea, " +
        "a.fuente_financiamiento AS fuenteFinanciamiento, " +
        "a.programa AS programa, " +
        "a.subprograma AS subprograma, " +
        "a.dependencia AS dependencia, " +
        "a.categoria AS categoriaSalarial, " +
        "a.concepto AS concepto, " +
        "a.presupuestado AS presupuestado, " +
        "a.vinculacion_funcionario AS regimen, " +
        "cv1.asignable AS vacante, " +
        "(select array_to_string(array_agg(DISTINCT objeto_gasto), CAST( ';'  as text))from " + Constantes.ESQUEMA_CTE_MH + ".anexos where cedula_identidad = a.cedula_identidad and anho = " + ((Integer)filtros.get("anho")).toString()  + " and mes = " + ((Integer)filtros.get("mes")).toString()  + ") AS objetoGasto, " +
        "a.devengado AS devengado, " +
        "a.cargo AS cargo, " +
        "a.funcion_real AS funcionReal, " +
        "pt1.numero_puesto AS numeroPuestoTrabajo, " +
        "ceo1.codigo AS codigoCeo, " +
        "ceo1.den AS denominacionCeo, " +
        "of1.nombre AS orientacionFuncional, " +
        "cuo1.nivel AS nivelCuo, " +
        "cuo1.subnivel AS subnivelCuo, " +
        "cuo1.numero AS numeroCuo, " +
        "cuo1.denominacion AS denominacionCuo, " +
        "cpt1.nivel AS nivelCpt, " +
        "cpt1.sub_nivel AS subnivelCpt, " +
        "cpt1.nro_g AS numeroCpt, " +
        "cpt1.den AS denominacionCpt, " +
        "cpt1.tit_unid AS titularUnidad, " +
        "cpt_ef1.nro AS numeroSecuencial, " +
        "ambito1.nombre AS ambitoCptEf, " +
        "cpt_ef1.cod_proceso AS codigoProceso, " +
        "cpt_ef1.den AS denominacionCptF, " +
        "cs1.numero_tramo AS numeroTramo, " +
        "cs1.minimo AS minimo, " +
        "cs1.maximo AS maximo " +
        "from " + Constantes.ESQUEMA_CTE_MH + ".anexos a " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".v_" + Constantes.ESQUEMA_CTE_MH + "_funcionario fun ON a.cedula_identidad = fun.cedula_identidad " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".anexos b on a.cedula_identidad = b.cedula_identidad and b.anho = " + anhoSF.toString()  + " and b.mes = " + mesSF.toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt_legajos cptl1 on a.cedula_identidad = cptl1.cedula_identidad and cptl1.anho = " + anho.toString()  + " and cptl1.mes = " + mes.toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt_legajos cptl2 on a.cedula_identidad = cptl2.cedula_identidad and cptl2.anho = " + anhoSF.toString()  + " and cptl2.mes = " + mesSF.toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt cpt1 on cptl1.id_cpt = cpt1.id " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt cpt2 on cptl2.id_cpt = cpt2.id " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".ceo_legajos ceol1 on a.cedula_identidad = ceol1.cedula_identidad and ceol1.anho = " + anho.toString()  + " and ceol1.mes = " + mes.toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".ceo_legajos ceol2 on a.cedula_identidad = ceol2.cedula_identidad and ceol2.anho = " + anhoSF.toString()  + " and ceol2.mes = " + mesSF.toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".ceo ceo1 on ceol1.id_ceo = ceo1.id " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".ceo ceo2 on ceol2.id_ceo = ceo2.id " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt_ef_legajos cpt_efl1 on a.cedula_identidad = cpt_efl1.cedula_identidad and cpt_efl1.anho = " + anho.toString()  + " and cpt_efl1.mes = " + mes.toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt_ef_legajos cpt_efl2 on a.cedula_identidad = cpt_efl2.cedula_identidad and cpt_efl2.anho = " + anhoSF.toString()  + " and cpt_efl2.mes = " + mesSF.toString()  + " " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt_ef cpt_ef1 on cpt_efl1.id_cpt_ef = cpt_ef1.id " +
        "left join " + Constantes.ESQUEMA_CTE_MH + ".cpt_ef cpt_ef2 on cpt_efl2.id_cpt_ef = cpt_ef2.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".v_cte_cargos_vacantes cv1 ON CAST(a.cargo as text) = CAST(cv1.nombre as text) AND cv1.anho = " + anho.toString()  + " AND cv1.mes = " + mes.toString()  + " " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".v_cte_cargos_vacantes cv2 ON CAST(b.cargo as text) = CAST(cv2.nombre as text) AND cv2.anho = " + anhoSF.toString()  + " AND cv2.mes = " + mesSF.toString()  + " " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".v_ultima_formacion_academica fa ON CAST(replace(CAST(fa.cedula_funcionario as text), CAST('.' as text), CAST('' as text)) as integer) = fun.cedula_identidad " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".puesto_trabajo pt1 ON a.cedula_identidad = pt1.cedula_identidad and pt1.anho = " + anho.toString()  + " AND pt1.mes = " + mes.toString()  + " " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".puesto_trabajo pt2 ON b.cedula_identidad = pt2.cedula_identidad and pt2.anho = " + anhoSF.toString()  + " AND pt2.mes = " + mesSF.toString()  + " " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".orientacion_funcional of1 ON cpt_ef1.orientacion_func_id = of1.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".orientacion_funcional of2 ON cpt_ef2.orientacion_func_id = of2.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".cuo cuo1 ON ceo1.id_cuo = cuo1.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".cuo cuo2 ON ceo2.id_cuo = cuo2.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".ambito ambito1 ON cpt_ef1.ambito_id = ambito1.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".ambito ambito2 ON cpt_ef2.ambito_id = ambito2.id " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".cts cs1 ON CAST(a.presupuestado as numeric) >= CAST(cs1.minimo as numeric) AND CAST(a.presupuestado as numeric)  < CAST(cs1.maximo as numeric) AND cs1.anho = " + anho.toString()  + " AND cs1.mes = " + mes.toString()  + " " +
        "LEFT JOIN " + Constantes.ESQUEMA_CTE_MH + ".cts cs2 ON CAST(b.presupuestado as numeric) >= CAST(cs2.minimo as numeric) AND CAST(b.presupuestado as numeric)  < CAST(cs2.maximo as numeric) AND cs2.anho = " + anho.toString()  + " AND cs2.mes = " + mes.toString()  + " " +
        "where a.anho = " + anho.toString()  + " and a.mes = " + mes.toString()  + " and a.cedula_identidad > 0 and a.cedula_identidad < 99999999 " +
        "and a.concepto in ('SUELDO', 'JORNALES', 'HONORARIOS PROFESIONALES') and b.concepto in ('SUELDO', 'JORNALES', 'HONORARIOS PROFESIONALES')) AnexosNoDistribuido ";
        String aditionalWhere = buildWhere(filtros);
        sql += aditionalWhere;

        System.err.println(sql);
        Query q = em.createNativeQuery(sql);
       //q.getSingleResult() != null ? ((BigInteger) q.getSingleResult()).intValue() : 0;
        System.err.println(q.getSingleResult());
        int resultado =  0;
        return resultado;
    }
}

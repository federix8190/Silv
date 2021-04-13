/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import py.com.ceamso.administracion.model.Ceo;
import py.com.ceamso.administracion.model.Cpt;
import py.com.ceamso.administracion.model.CptE;
import py.com.ceamso.administracion.model.CptF;
import py.com.ceamso.administracion.model.PuestoTrabajo;
import py.com.ceamso.administracion.model.PuestoTrabajoPK;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.reportes.dao.AnexosDAO;
import py.com.ceamso.reportes.model.Anexos;
import py.com.ceamso.reportes.model.ClasificacionAnexo;
import py.com.ceamso.reportes.model.ClasificacionAnexoPk;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;
import py.com.ceamso.utils.Utils;

/**
 *
 * @author daniel
 */
public class PuestoTrabajoDAO extends WritableDAO<PuestoTrabajo>{

    @PersistenceContext(unitName = "CTEPostgresPU")
    protected EntityManager em;
    
    @Inject
    @Configuracion("esHacienda")
    private String esHacienda;
    
    /**
     * 
     * @{@inheritDoc}
     */
    public Class getEntity() {
        return PuestoTrabajo.class;
    }
    
    public PuestoTrabajoDAO(){
        
    }
    
    public void cargarPuestosExistentes(int anho, int mes) {
        System.err.println("cargarTabla");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ps.cedula_identidad, ps.categoria_personal, ");
        sb.append("ps.nombre_cargo, cptl.id_cpt, cptel.id_cpt_ee, cptfl.id_cpt_ef, ");
        sb.append("ceol.id_ceo, ceo.id_cuo, cpt.nivel, cs.numero_tramo, ");
        sb.append("(SELECT MAX(numero_puesto) from dbo.puesto_trabajo ");
        sb.append("where cedula_identidad = ps.cedula_identidad), ");
        sb.append("ps.vinculo_laboral, ps.programa, ps.subprograma, lp.sexo ");
        sb.append("FROM dbo.planilla_sueldo ps ");
        sb.append("LEFT JOIN dbo.cpt_legajos cptl on ps.cedula_identidad = cptl.cedula_identidad ");
        sb.append("AND ps.ejercicio = cptl.anho and ps.mes = cptl.mes ");
        sb.append("LEFT JOIN dbo.legajo_personal lp on ps.cedula_identidad = lp.cedula_identidad ");
        sb.append("LEFT JOIN dbo.cpt on cptl.id_cpt = cpt.id ");
        sb.append("LEFT JOIN dbo.ceo_legajos ceol on ps.cedula_identidad = ceol.cedula_identidad ");
        sb.append("AND ps.ejercicio = ceol.anho and ps.mes = cptl.mes ");
        sb.append("LEFT JOIN dbo.cpt_ee_legajos cptel on ps.cedula_identidad = cptel.cedula_identidad ");
        sb.append("AND ps.ejercicio = cptel.anho and ps.mes = cptel.mes ");
        sb.append("LEFT JOIN dbo.cpt_ef_legajos cptfl on ps.cedula_identidad = cptfl.cedula_identidad ");
        sb.append("AND ps.ejercicio = cptfl.anho and ps.mes = cptfl.mes ");
        sb.append("LEFT JOIN dbo.ceo on ceol.id_ceo = ceo.id ");
        sb.append("JOIN dbo.cts AS cs ON ps.presupuestado >= cs.minimo ");
        sb.append("AND ps.presupuestado < cs.maximo and cs.anho = ps.ejercicio and cs.mes = ps.mes ");
        sb.append("where ps.ejercicio = :anho and ps.mes = :mes ");
        sb.append("AND (descrip_objeto_gasto = 'SUELDOS' or descrip_objeto_gasto = 'JORNALES' ");
        sb.append("or descrip_objeto_gasto = 'HONORARIOS PROFESIONALES')");
        
        System.err.println("");
        System.err.println("");
        System.err.println(sb.toString());
        System.err.println("");
        System.err.println("");
        
        /*Query q = em.createNativeQuery(sb.toString());
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);*/
        
    }
    
    public List<Object[]> listarPlanilla(int anho, int mes) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ps.cedula_identidad, ps.categoria_personal, ");
        sb.append("ps.nombre_cargo, cptl.id_cpt, cptel.id_cpt_ee, cptfl.id_cpt_ef, ");
        sb.append("ceol.id_ceo, ceo.id_cuo, cpt.nivel, cs.numero_tramo, ");
        sb.append("(SELECT MAX(numero_puesto) from dbo.puesto_trabajo ");
        sb.append("where cedula_identidad = ps.cedula_identidad), ");
        sb.append("ps.vinculo_laboral, ps.programa, ps.subprograma, lp.sexo ");
        sb.append("FROM dbo.planilla_sueldo ps ");
        sb.append("LEFT JOIN dbo.cpt_legajos cptl on ps.cedula_identidad = cptl.cedula_identidad ");
        sb.append("AND ps.ejercicio = cptl.anho and ps.mes = cptl.mes ");
        sb.append("LEFT JOIN dbo.legajo_personal lp on ps.cedula_identidad = lp.cedula_identidad ");
        sb.append("LEFT JOIN dbo.cpt on cptl.id_cpt = cpt.id ");
        sb.append("LEFT JOIN dbo.ceo_legajos ceol on ps.cedula_identidad = ceol.cedula_identidad ");
        sb.append("AND ps.ejercicio = ceol.anho and ps.mes = cptl.mes ");
        sb.append("LEFT JOIN dbo.cpt_ee_legajos cptel on ps.cedula_identidad = cptel.cedula_identidad ");
        sb.append("AND ps.ejercicio = cptel.anho and ps.mes = cptel.mes ");
        sb.append("LEFT JOIN dbo.cpt_ef_legajos cptfl on ps.cedula_identidad = cptfl.cedula_identidad ");
        sb.append("AND ps.ejercicio = cptfl.anho and ps.mes = cptfl.mes ");
        sb.append("LEFT JOIN dbo.ceo on ceol.id_ceo = ceo.id ");
        sb.append("JOIN dbo.cts AS cs ON ps.presupuestado >= cs.minimo ");
        sb.append("AND ps.presupuestado < cs.maximo and cs.anho = ps.ejercicio and cs.mes = ps.mes ");
        sb.append("where ps.ejercicio = :anho and ps.mes = :mes ");
        sb.append("AND (descrip_objeto_gasto = 'SUELDOS' or descrip_objeto_gasto = 'JORNALES' ");
        sb.append("or descrip_objeto_gasto = 'HONORARIOS PROFESIONALES')");
        
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<Object[]> res = q.getResultList();
        return res;
    }
    
    public BigDecimal getMaximoNumeroPuesto() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT MAX(numero_puesto) FROM dbo.puesto_trabajo");
        Query q = em.createNativeQuery(sb.toString());
        BigDecimal numeroPuestoActual = (BigDecimal) q.getSingleResult();
        System.err.println("numero Puesto Actual : " + numeroPuestoActual);
        if (numeroPuestoActual == null) {
            numeroPuestoActual = new BigDecimal(0);
        }
        return numeroPuestoActual;
    }
    
    public void cargarTabla(Object[] datos, int anho, int mes, BigDecimal numeroPuestoActual) {
        Integer cedulaIdentidad = (Integer) datos[0];
        String categoria = (String) datos[1];
        String cargo = (String) datos[2];
        BigDecimal idCpt = (BigDecimal) datos[3];
        BigDecimal idCptE = (BigDecimal) datos[4];
        BigDecimal idCptF = (BigDecimal) datos[5];
        BigDecimal idCeo = (BigDecimal) datos[6];
        BigDecimal idCuo = (BigDecimal) datos[7];
        Integer nivel = (Integer) datos[8];
        BigDecimal tramo = (BigDecimal) datos[9];
        BigDecimal numeroPuesto = (BigDecimal) datos[10];
        String vinculacion = (String) datos[11];
        String programa = (String) datos[12];
        String subprograma = (String) datos[13];
        Boolean sexo = (Boolean) datos[14];

        if (numeroPuesto == null) {
            int valor = numeroPuestoActual.intValue();
            valor++;
            numeroPuestoActual = new BigDecimal(valor);
            numeroPuesto = numeroPuestoActual;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("insert into dbo.puesto_trabajo (");
        sb.append("numero_puesto, anho, mes, cedula_identidad, categoria, cargo, ");
        sb.append("id_cpt, id_cpt_ee, id_cpt_ef, id_ceo, id_cuo, nivel_cpt, numero_tramo, ");
        sb.append("vinculo_laboral, programa, subprograma, sexo, ");
        sb.append("activo, fecha_creacion, ip_creacion, usuario_creacion)");
        sb.append("values(:numeroPuesto, :anho, :mes, :cedula_identidad, :categoria, :cargo,");
        sb.append(":id_cpt, :id_cpt_ee, :id_cpt_ef, :id_ceo, :id_cuo, :nivel_cpt, :numero_tramo, ");
        sb.append(":vinculo_laboral, :programa, :subprograma, :sexo, ");
        sb.append("1, getDate(), '127.0.0.1', 1)");
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("numeroPuesto", numeroPuesto);
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        q.setParameter("cedula_identidad", cedulaIdentidad);
        q.setParameter("categoria", categoria);
        q.setParameter("cargo", cargo);
        q.setParameter("id_cpt", idCpt);
        q.setParameter("id_cpt_ee", idCptE);
        q.setParameter("id_cpt_ef", idCptF);
        q.setParameter("id_ceo", idCeo);
        q.setParameter("id_cuo", idCuo);
        q.setParameter("nivel_cpt", nivel);
        q.setParameter("numero_tramo", tramo);
        q.setParameter("vinculo_laboral", vinculacion);
        q.setParameter("programa", programa);
        q.setParameter("subprograma", subprograma);
        q.setParameter("sexo", sexo);
        q.executeUpdate();
    }
    
    public void cargarTabla(int anho, int mes) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ps.cedula_identidad, ps.categoria_personal, ");
        sb.append("ps.nombre_cargo, cptl.id_cpt, cptel.id_cpt_ee, cptfl.id_cpt_ef, ");
        sb.append("ceol.id_ceo, ceo.id_cuo, cpt.nivel, cs.numero_tramo, ");
        sb.append("(SELECT MAX(numero_puesto) from dbo.puesto_trabajo ");
        sb.append("where cedula_identidad = ps.cedula_identidad), ");
        sb.append("ps.vinculo_laboral, ps.programa, ps.subprograma, lp.sexo ");
        sb.append("FROM dbo.planilla_sueldo ps ");
        sb.append("LEFT JOIN dbo.cpt_legajos cptl on ps.cedula_identidad = cptl.cedula_identidad ");
        sb.append("AND ps.ejercicio = cptl.anho and ps.mes = cptl.mes ");
        sb.append("LEFT JOIN dbo.legajo_personal lp on ps.cedula_identidad = lp.cedula_identidad ");
        sb.append("LEFT JOIN dbo.cpt on cptl.id_cpt = cpt.id ");
        sb.append("LEFT JOIN dbo.ceo_legajos ceol on ps.cedula_identidad = ceol.cedula_identidad ");
        sb.append("AND ps.ejercicio = ceol.anho and ps.mes = cptl.mes ");
        sb.append("LEFT JOIN dbo.cpt_ee_legajos cptel on ps.cedula_identidad = cptel.cedula_identidad ");
        sb.append("AND ps.ejercicio = cptel.anho and ps.mes = cptel.mes ");
        sb.append("LEFT JOIN dbo.cpt_ef_legajos cptfl on ps.cedula_identidad = cptfl.cedula_identidad ");
        sb.append("AND ps.ejercicio = cptfl.anho and ps.mes = cptfl.mes ");
        sb.append("LEFT JOIN dbo.ceo on ceol.id_ceo = ceo.id ");
        sb.append("JOIN dbo.cts AS cs ON ps.presupuestado >= cs.minimo ");
        sb.append("AND ps.presupuestado < cs.maximo and cs.anho = ps.ejercicio and cs.mes = ps.mes ");
        sb.append("where ps.ejercicio = :anho and ps.mes = :mes ");
        sb.append("AND (descrip_objeto_gasto = 'SUELDOS' or descrip_objeto_gasto = 'JORNALES' ");
        sb.append("or descrip_objeto_gasto = 'HONORARIOS PROFESIONALES')");
        
        Query q = em.createNativeQuery(sb.toString());
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        List<Object[]> res = q.getResultList();
        System.err.println("Total de filas : " + res.size());
        
        sb = new StringBuilder();
        sb.append("SELECT MAX(numero_puesto) FROM dbo.puesto_trabajo");
        q = em.createNativeQuery(sb.toString());
        BigDecimal numeroPuestoActual = (BigDecimal) q.getSingleResult();
        System.err.println("numero Puesto Actual : " + numeroPuestoActual);
        if (numeroPuestoActual == null) {
            numeroPuestoActual = new BigDecimal(0);
        }
        
        for (Object[] datos : res) {
            Integer cedulaIdentidad = (Integer) datos[0];
            String categoria = (String) datos[1];
            String cargo = (String) datos[2];
            BigDecimal idCpt = (BigDecimal) datos[3];
            BigDecimal idCptE = (BigDecimal) datos[4];
            BigDecimal idCptF = (BigDecimal) datos[5];
            BigDecimal idCeo = (BigDecimal) datos[6];
            BigDecimal idCuo = (BigDecimal) datos[7];
            Integer nivel = (Integer) datos[8];
            BigDecimal tramo = (BigDecimal) datos[9];
            BigDecimal numeroPuesto = (BigDecimal) datos[10];
            String vinculacion = (String) datos[11];
            String programa = (String) datos[12];
            String subprograma = (String) datos[13];
            Boolean sexo = (Boolean) datos[14];
            
            if (numeroPuesto == null) {
                int valor = numeroPuestoActual.intValue();
                valor++;
                numeroPuestoActual = new BigDecimal(valor);
                numeroPuesto = numeroPuestoActual;
            }
            sb = new StringBuilder();
            sb.append("insert into dbo.puesto_trabajo (");
            sb.append("numero_puesto, anho, mes, cedula_identidad, categoria, cargo, ");
            sb.append("id_cpt, id_cpt_ee, id_cpt_ef, id_ceo, id_cuo, nivel_cpt, numero_tramo, ");
            sb.append("vinculo_laboral, programa, subprograma, sexo, ");
            sb.append("activo, fecha_creacion, ip_creacion, usuario_creacion)");
            sb.append("values(:numeroPuesto, :anho, :mes, :cedula_identidad, :categoria, :cargo,");
            sb.append(":id_cpt, :id_cpt_ee, :id_cpt_ef, :id_ceo, :id_cuo, :nivel_cpt, :numero_tramo, ");
            sb.append(":vinculo_laboral, :programa, :subprograma, :sexo, ");
            sb.append("1, getDate(), '127.0.0.1', 1)");
            q = em.createNativeQuery(sb.toString());
            q.setParameter("numeroPuesto", numeroPuesto);
            q.setParameter("anho", anho);
            q.setParameter("mes", mes);
            q.setParameter("cedula_identidad", cedulaIdentidad);
            q.setParameter("categoria", categoria);
            q.setParameter("cargo", cargo);
            q.setParameter("id_cpt", idCpt);
            q.setParameter("id_cpt_ee", idCptE);
            q.setParameter("id_cpt_ef", idCptF);
            q.setParameter("id_ceo", idCeo);
            q.setParameter("id_cuo", idCuo);
            q.setParameter("nivel_cpt", nivel);
            q.setParameter("numero_tramo", tramo);
            q.setParameter("vinculo_laboral", vinculacion);
            q.setParameter("programa", programa);
            q.setParameter("subprograma", subprograma);
            q.setParameter("sexo", sexo);
            q.executeUpdate();
        }
    }
    
    public void limpiarCptE() {
        String sql = "SELECT DISTINCT cedula_identidad FROM dbo.cpt_ee_legajos where anho = 2018 and mes = 1";
        Query q = em.createNativeQuery(sql);
        List<BigDecimal> lista = q.getResultList();
        int count = 0;
        for (BigDecimal cedulaIdentidad : lista) {
            sql = "SELECT id_cpt_ee FROM dbo.cpt_ee_legajos where cedula_identidad = :cedula";
            q = em.createNativeQuery(sql);
            q.setParameter("cedula", cedulaIdentidad);
            List<BigDecimal> cpt = q.getResultList();
            if (cpt.size() > 1) {
                sql = "DELETE dbo.cpt_ee_legajos where cedula_identidad = :cedula AND id_cpt_ee = :id_cpt_ee";
                q = em.createNativeQuery(sql);
                q.setParameter("cedula", cedulaIdentidad);
                q.setParameter("id_cpt_ee", cpt.get(0));
                q.executeUpdate();
                count++;
            }
        }
        System.err.println("count : " + count);
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
                sb.append(" LOWER(c.")
                    .append(key)
                    .append(") LIKE LOWER(:")
                    .append(key)
                    .append(")");
                
            } else {
                if(key.equals("anho") || key.equals("mes") || key.equals("cedulaIdentidad")){
                    sb.append("pk."+key)
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
    
    public void actualizarPuestoTrabajoCptE(CptE cptE, Legajo legajo, Boolean asignar, 
            HttpServletRequest httpRequest) {
        
        HashMap<String, Object> filtros = new HashMap<String, Object>();
        filtros.put("cedulaIdentidad", legajo.getCedulaIdentidad().intValue());
        filtros.put("anho", legajo.getAnho());
        filtros.put("mes", legajo.getMes());
        PuestoTrabajo puestoTrabajo = null;
        
        ListaResponse<PuestoTrabajo> list = listar(0, -1, "", "", filtros);
        boolean insertar = false;
        if (list.getCount() > 0) {
            for (PuestoTrabajo p : list.getRows())
                puestoTrabajo = p;
 
            if (asignar) {
                if(cptE != null){
                    puestoTrabajo.setCptE(cptE);
                    if(cptE.getCpt() != null)
                        puestoTrabajo.setCpt(cptE.getCpt());
                }
            } else {
                puestoTrabajo.setCptE(null);
                //puestoTrabajo.setCpt(null);
            }
        } else {
            filtros = new HashMap<String, Object>();    
            filtros.put("cedulaIdentidad", legajo.getCedulaIdentidad().intValue());
            list = listar(0, -1, "", "", filtros);
            
            Long nroPuesto = new Long(0);
            if(list.getCount()>0)
                nroPuesto = list.getRows().get(0).getNumeroPuesto();
                
        
            puestoTrabajo = new PuestoTrabajo();
            puestoTrabajo.setNumeroPuesto(nroPuesto);
            PuestoTrabajoPK pk = new PuestoTrabajoPK();
            pk.setCedulaIdentidad(legajo.getCedulaIdentidad().intValue());
            pk.setAnho(legajo.getAnho());
            pk.setMes(legajo.getMes());
            puestoTrabajo.setNumeroTramo(legajo.getNumeroTramo());
            puestoTrabajo.setNivelCtp(legajo.getNivel());
            puestoTrabajo.setPk(pk);
            insertar = true;
        }
        
        try {
            if (insertar) {
                insertarPuestoTrabajo(puestoTrabajo, httpRequest);
            } else {
                puestoTrabajo.setUsuarioModificacion(Utils.obtenerUsuarioAutenticado().getId());
                puestoTrabajo.setFechaModificacion(new Date());
                puestoTrabajo.setIpModificacion(httpRequest.getRemoteAddr());
                em.merge(puestoTrabajo);
            }
        } catch (Exception ex) {
            Logger.getLogger(PuestoTrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (esHacienda()) {
            return;
        }
        
        ClasificacionAnexoPk pk = new ClasificacionAnexoPk(legajo.getAnho(), 
                legajo.getMes(), legajo.getCedulaIdentidad().intValue());
        ClasificacionAnexo clasificacion = em.find(ClasificacionAnexo.class, pk);
        try {
            
            insertar = false;
            if (clasificacion == null) {
                clasificacion = new ClasificacionAnexo();
                clasificacion.setId(pk);
                insertar = true;
            }
            
            if (asignar) {
                if (cptE != null) {
                    clasificacion.setIdCptE(cptE.getId());
                    clasificacion.setAmbitoCptE(cptE.getAmbito().getNombre());
                    //clasificacion.setCategoriaCptE(cptE.getCategoria());
                    clasificacion.setDenominacionCptE(cptE.getDenominacion());
                    clasificacion.setNumeroSecuencialCptE(cptE.getNumero());
                    clasificacion.setNivelCptE(cptE.getNivel());
                }
            } else {
                clasificacion.setIdCptE(null);
                clasificacion.setAmbitoCptE(null);
                //clasificacion.setCategoriaCptE(null);
                clasificacion.setDenominacionCptE(null);
                clasificacion.setNumeroSecuencialCptE(null);
                clasificacion.setNivelCptE(null);
            }
            
            if (insertar) {
                em.persist(clasificacion);
            } else {
                em.merge(clasificacion);
            }
        } catch (Exception ex) {
            Logger.getLogger(PuestoTrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void actualizarPuestoTrabajoCptF(CptF cptF, Legajo legajo, Boolean asignar, HttpServletRequest httpRequest) {
        
        HashMap<String, Object> filtros = new HashMap<String, Object>();
        filtros.put("cedulaIdentidad", legajo.getCedulaIdentidad().intValue());
        filtros.put("anho", legajo.getAnho());
        filtros.put("mes", legajo.getMes());
        PuestoTrabajo puestoTrabajo = null;
        
        ListaResponse<PuestoTrabajo> list = listar(0, -1, "", "", filtros);
        boolean insertar = false;
        if (list.getCount() > 0) {
            for(PuestoTrabajo p : list.getRows())
                puestoTrabajo = p;
            
            if (puestoTrabajo == null) {
                puestoTrabajo = new PuestoTrabajo();
            }
        
            if(asignar){
                if(cptF != null){
                    puestoTrabajo.setCptF(cptF);
                    if(cptF.getCpt() != null)
                        puestoTrabajo.setCpt(cptF.getCpt());
                }
            }else {
                puestoTrabajo.setCptF(null);
            }
        } else {
            filtros = new HashMap<String, Object>();    
            filtros.put("cedulaIdentidad", legajo.getCedulaIdentidad().intValue());
            list = listar(0, -1, "", "", filtros);
            
            Long nroPuesto = new Long(0);
            if(list.getCount()>0)
                nroPuesto = list.getRows().get(0).getNumeroPuesto();
                
        
            puestoTrabajo = new PuestoTrabajo();
            puestoTrabajo.setNumeroPuesto(nroPuesto);
            PuestoTrabajoPK pk = new PuestoTrabajoPK();
            pk.setCedulaIdentidad(legajo.getCedulaIdentidad().intValue());
            pk.setAnho(legajo.getAnho());
            pk.setMes(legajo.getMes());
            puestoTrabajo.setNumeroTramo(legajo.getNumeroTramo());
            puestoTrabajo.setNivelCtp(legajo.getNivel());
            puestoTrabajo.setPk(pk);
            insertar = true;
        }
        try {
            if (insertar) {
                insertarPuestoTrabajo(puestoTrabajo, httpRequest);
            } else {
                puestoTrabajo.setUsuarioModificacion(Utils.obtenerUsuarioAutenticado().getId());
                puestoTrabajo.setFechaModificacion(new Date());
                puestoTrabajo.setIpModificacion(httpRequest.getRemoteAddr());
                em.merge(puestoTrabajo);
            }
        } catch (Exception ex) {
            Logger.getLogger(PuestoTrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (esHacienda()) {
            if(!asignar) cptF = null;
            //actualizarAnexosCptF(legajo, cptF);
            
        } else {
        
            ClasificacionAnexoPk pk = new ClasificacionAnexoPk(legajo.getAnho(), legajo.getMes(), legajo.getCedulaIdentidad().intValue());
            ClasificacionAnexo clasificacion = em.find(ClasificacionAnexo.class, pk);
            try {

                insertar = false;
                if (clasificacion == null) {
                    clasificacion = new ClasificacionAnexo();
                    clasificacion.setId(pk);
                    insertar = true;
                }

                if (asignar) {
                    if (cptF != null) {
                        clasificacion.setIdCptF(cptF.getId());
                        clasificacion.setAmbitoCptF(cptF.getAmbito().getNombre());
                        clasificacion.setCodigoProceso(cptF.getCodProceso());
                        clasificacion.setDenominacionCptF(cptF.getDen());
                        clasificacion.setNumeroSecuencialCptF(cptF.getNro());
                        clasificacion.setOrientacionFuncional(cptF.getOrientacionFunc().getNombre());
                    }
                } else {
                    clasificacion.setIdCptF(null);
                    clasificacion.setAmbitoCptF(null);
                    clasificacion.setCodigoProceso(null);
                    clasificacion.setDenominacionCptF(null);
                    clasificacion.setNumeroSecuencialCptF(null);
                    clasificacion.setOrientacionFuncional(null);
                }

                if (insertar) {
                    em.persist(clasificacion);
                } else {
                    em.merge(clasificacion);
                }
            } catch (Exception ex) {
                Logger.getLogger(PuestoTrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void actualizarPuestoTrabajoCpt(Cpt cpt, Legajo legajo, Boolean asignar, HttpServletRequest httpRequest) {
        
        HashMap<String, Object> filtros = new HashMap<String, Object>();
        filtros.put("cedulaIdentidad", legajo.getCedulaIdentidad().intValue());
        filtros.put("anho", legajo.getAnho());
        filtros.put("mes", legajo.getMes());
        PuestoTrabajo puestoTrabajo = null;
        
        ListaResponse<PuestoTrabajo> list = listar(0, -1, "", "", filtros);
        boolean insertar = false;
        if (list.getCount() > 0) {
            for(PuestoTrabajo p : list.getRows())
                puestoTrabajo = p;
        } else {
            filtros = new HashMap<String, Object>();    
            filtros.put("cedulaIdentidad", legajo.getCedulaIdentidad().intValue());
            list = listar(0, -1, "", "", filtros);
            
            Long nroPuesto = new Long(0);
            if(list.getCount()>0)
                nroPuesto = list.getRows().get(0).getNumeroPuesto();
                
        
            puestoTrabajo = new PuestoTrabajo();
            puestoTrabajo.setNumeroPuesto(nroPuesto);
            PuestoTrabajoPK pk = new PuestoTrabajoPK();
            pk.setCedulaIdentidad(legajo.getCedulaIdentidad().intValue());
            pk.setAnho(legajo.getAnho());
            pk.setMes(legajo.getMes());
            puestoTrabajo.setNumeroTramo(legajo.getNumeroTramo());
            puestoTrabajo.setNivelCtp(legajo.getNivel());
            puestoTrabajo.setPk(pk);
            insertar = true;
        }
        
        if(asignar) {
            if (cpt != null) {
                puestoTrabajo.setCpt(cpt);
            }
        } else {
            puestoTrabajo.setCpt(null);
        }
        
        try {
            if (insertar) {
                insertarPuestoTrabajo(puestoTrabajo, httpRequest);
            } else {
                puestoTrabajo.setUsuarioModificacion(Utils.obtenerUsuarioAutenticado().getId());
                puestoTrabajo.setFechaModificacion(new Date());
                puestoTrabajo.setIpModificacion(httpRequest.getRemoteAddr());
                em.merge(puestoTrabajo);
            }
        } catch (Exception ex) {
            System.err.println("Error al crear puesto de trabajo : " + ex.getMessage());
            Logger.getLogger(PuestoTrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (esHacienda()) {
            if(!asignar) cpt = null;
            //actualizarAnexosCpt(legajo, cpt);
        
        } else {
        
            ClasificacionAnexoPk pk = new ClasificacionAnexoPk(legajo.getAnho(), legajo.getMes(), legajo.getCedulaIdentidad().intValue());
            ClasificacionAnexo clasificacion = em.find(ClasificacionAnexo.class, pk);
            try {

                insertar = false;
                if (clasificacion == null) {
                    clasificacion = new ClasificacionAnexo();
                    clasificacion.setId(pk);
                    insertar = true;
                }

                if (asignar) {
                    if (cpt != null) {
                        clasificacion.setIdCpt(cpt.getId());
                        clasificacion.setNivelCpt("" + cpt.getNivel());
                        clasificacion.setSubNivelCpt("" + cpt.getSubNivel());
                        clasificacion.setNumeroCpt(cpt.getNumeroGasto() + "");
                        clasificacion.setDenominacionCpt(cpt.getDenominacion());
                        clasificacion.setTitularUnidad(cpt.getTituloUnidad());
                    }
                } else {
                    clasificacion.setIdCpt(null);
                    clasificacion.setNivelCpt(null);
                    clasificacion.setSubNivelCpt(null);
                    clasificacion.setNumeroCpt(null);
                    clasificacion.setDenominacionCpt(null);
                    clasificacion.setTitularUnidad(null);
                }

                if (insertar) {
                    em.persist(clasificacion);
                } else {
                    em.merge(clasificacion);
                }            
            
            } catch (Exception ex) {
                Logger.getLogger(PuestoTrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void actualizarPuestoTrabajoCeo(Ceo ceo, Legajo legajo, Boolean asignar, HttpServletRequest httpRequest) {
        
        HashMap<String, Object> filtros = new HashMap<String, Object>();
        filtros.put("cedulaIdentidad", legajo.getCedulaIdentidad().intValue());
        filtros.put("anho", legajo.getAnho());
        filtros.put("mes", legajo.getMes());
        PuestoTrabajo puestoTrabajo = null;
        
        boolean insertar = false;
        ListaResponse<PuestoTrabajo> list = listar(0, -1, "", "", filtros);
        if (list.getCount() > 0) {
            List<PuestoTrabajo> lista = list.getRows();
            puestoTrabajo = lista.get(0);
        } else {
            filtros = new HashMap<String, Object>();    
            filtros.put("cedulaIdentidad", legajo.getCedulaIdentidad().intValue());
            list = listar(0, -1, "", "", filtros);
            
            Long nroPuesto = new Long(0);
            if(list.getCount()>0)
                nroPuesto = list.getRows().get(0).getNumeroPuesto();
                
        
            puestoTrabajo = new PuestoTrabajo();
            puestoTrabajo.setNumeroPuesto(nroPuesto);
            PuestoTrabajoPK pk = new PuestoTrabajoPK();
            pk.setCedulaIdentidad(legajo.getCedulaIdentidad().intValue());
            pk.setAnho(legajo.getAnho());
            pk.setMes(legajo.getMes());
            puestoTrabajo.setNumeroTramo(legajo.getNumeroTramo());
            puestoTrabajo.setNivelCtp(legajo.getNivel());
            puestoTrabajo.setPk(pk);
            insertar = true;
        }
            
        if (asignar) {
            if(ceo != null){
                puestoTrabajo.setCeo(ceo);
                if(ceo.getCuo() != null)
                    puestoTrabajo.setCuo(ceo.getCuo());
            }
        } else {
            puestoTrabajo.setCeo(null);
            puestoTrabajo.setCuo(null);
        }
        
        try {
            if (insertar) {
                insertarPuestoTrabajo(puestoTrabajo, httpRequest);
            } else {
                puestoTrabajo.setUsuarioModificacion(Utils.obtenerUsuarioAutenticado().getId());
                puestoTrabajo.setFechaModificacion(new Date());
                puestoTrabajo.setIpModificacion(httpRequest.getRemoteAddr());
                em.merge(puestoTrabajo);
            }
        } catch (Exception ex) {
            Logger.getLogger(PuestoTrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (esHacienda()) {
            if(!asignar) ceo = null;
            //actualizarAnexosCeo(legajo, ceo);
            
        } else {
        
            ClasificacionAnexoPk pk = new ClasificacionAnexoPk(legajo.getAnho(), legajo.getMes(), legajo.getCedulaIdentidad().intValue());
            ClasificacionAnexo clasificacion = em.find(ClasificacionAnexo.class, pk);

            try {

                insertar = false;
                if (clasificacion == null) {
                    clasificacion = new ClasificacionAnexo();
                    clasificacion.setId(pk);
                    insertar = true;
                }

                if (asignar) {
                    if (ceo != null) {
                        clasificacion.setIdCeo(ceo.getId());
                        clasificacion.setCodigoCeo(ceo.getCodigo());
                        clasificacion.setDenominacionCeo(ceo.getDenominacion());
                        if (ceo.getCuo() != null) {
                            clasificacion.setIdCuo(ceo.getCuo().getId());
                            clasificacion.setDenominacionCuo(ceo.getCuo().getDenominacion());
                            clasificacion.setNivelCuo(ceo.getCuo().getNivel());
                            clasificacion.setSubNivelCuo(ceo.getCuo().getSubNivel());
                            clasificacion.setNumeroCuo(ceo.getCuo().getNumero());
                        }
                    }
                } else {
                    clasificacion.setIdCeo(null);
                    clasificacion.setCodigoCeo(null);
                    clasificacion.setDenominacionCeo(null);
                    clasificacion.setIdCuo(null);
                    clasificacion.setDenominacionCuo(null);
                    clasificacion.setNivelCuo(null);
                    clasificacion.setSubNivelCuo(null);
                    clasificacion.setNumeroCuo(null);
                }

                if (insertar) {
                    em.persist(clasificacion);
                } else {
                    em.merge(clasificacion);
                }

            } catch (Exception ex) {
                Logger.getLogger(PuestoTrabajoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void insertarPuestoTrabajo(PuestoTrabajo puestoTrabajo, HttpServletRequest httpRequest) {
        puestoTrabajo.setCargo("");
        puestoTrabajo.setCategoria("");
        puestoTrabajo.setUsuarioCreacion(Utils.obtenerUsuarioAutenticado().getId());
        puestoTrabajo.setFechaCreacion(new Date());
        puestoTrabajo.setIpCreacion(httpRequest.getRemoteAddr());
        em.persist(puestoTrabajo);
    }
    
    /*private void actualizarAnexosCpt(Legajo legajo, Cpt cpt) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE "  + Constantes.ESQUEMA_CTE_MH + ".anexos ");
        sql.append("SET id_cpt = :idCpt, nivel_cpt = :nivelCpt, subnivel_cpt = :subnivelCpt, ");
        sql.append("numero_cpt = :numeroCpt, denominacion_cpt = :denominacionCpt, ");
        sql.append("titular_unidad = :titularUnidad ");
        sql.append("WHERE cedula_identidad = :cedulaIdentidad ");
        sql.append("AND anho = :anho AND mes = :mes");
        
        Query q = em.createNativeQuery(sql.toString());
        q.setParameter("cedulaIdentidad", legajo.getCedulaIdentidad().intValue());
        q.setParameter("anho", legajo.getAnho());
        q.setParameter("mes", legajo.getMes());
        
        if (cpt != null) {
            q.setParameter("idCpt", cpt.getId());
            q.setParameter("nivelCpt", cpt.getNivel());
            q.setParameter("subnivelCpt", cpt.getSubNivel());
            q.setParameter("numeroCpt", cpt.getNumeroGasto());
            q.setParameter("denominacionCpt", cpt.getDenominacion());
            q.setParameter("titularUnidad", cpt.getTituloUnidad());
        } else {
            q.setParameter("idCpt", 0);
            q.setParameter("nivelCpt", null);
            q.setParameter("subnivelCpt", null);
            q.setParameter("numeroCpt", null);
            q.setParameter("denominacionCpt", null);
            q.setParameter("titularUnidad", false);
        }
        q.executeUpdate();
    }
    
    private void actualizarAnexosCeo(Legajo legajo, Ceo ceo) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE "  + Constantes.ESQUEMA_CTE_MH + ".anexos ");
        sql.append("SET id_ceo = :idCeo, codigo_ceo = :codigoCeo, denominacion_ceo = :denominacionCeo, ");
        sql.append("id_cuo = :idCuo, nivel_cuo = :nivelCuo, subnivel_cuo = :subnivelCuo, ");
        sql.append("numero_cuo = :numeroCuo, denominacion_cuo = :denominacionCuo ");
        sql.append("WHERE cedula_identidad = :cedulaIdentidad ");
        sql.append("AND anho = :anho AND mes = :mes");
        
        Query q = em.createNativeQuery(sql.toString());
        q.setParameter("cedulaIdentidad", legajo.getCedulaIdentidad().intValue());
        q.setParameter("anho", legajo.getAnho());
        q.setParameter("mes", legajo.getMes());
        
        if (ceo != null) {
            q.setParameter("idCeo", ceo.getId());
            q.setParameter("codigoCeo", ceo.getCodigo());
            q.setParameter("denominacionCeo", ceo.getDenominacion());
            if (ceo.getCuo() != null) {
                q.setParameter("idCuo", ceo.getCuo().getId());
                q.setParameter("nivelCuo", ceo.getCuo().getNivel());
                q.setParameter("subnivelCuo", ceo.getCuo().getSubNivel());
                q.setParameter("numeroCuo", ceo.getCuo().getNumero());
                q.setParameter("denominacionCuo", ceo.getCuo().getDenominacion());
            } else {
                q.setParameter("idCuo", null);
                q.setParameter("nivelCuo", null);
                q.setParameter("subnivelCuo", null);
                q.setParameter("numeroCuo", null);
                q.setParameter("denominacionCuo", null);
            }
        } else {
            q.setParameter("idCeo", 0);
            q.setParameter("codigoCeo", null);
            q.setParameter("denominacionCeo", null);
            q.setParameter("idCuo", 0);
            q.setParameter("nivelCuo", 0);
            q.setParameter("subnivelCuo", 0);
            q.setParameter("numeroCuo", 0);
            q.setParameter("denominacionCuo", null);
        }
        
        q.executeUpdate();
    }
    
    private void actualizarAnexosCptF(Legajo legajo, CptF cptF) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE "  + Constantes.ESQUEMA_CTE_MH + ".anexos ");
        sql.append("SET id_cpt_ef = :idCptF, ambito_cpt_ef = :ambitoCptF, codigo_proceso = :codigoProceso, ");
        sql.append("denominacion_cpt_ef = :denominacionCptF, numero_secuencial = :numeroSecuencial, ");
        sql.append("orientacion_funcional = :orientacionFuncional ");
        sql.append("WHERE cedula_identidad = :cedulaIdentidad ");
        sql.append("AND anho = :anho AND mes = :mes");
        
        Query q = em.createNativeQuery(sql.toString());
        q.setParameter("cedulaIdentidad", legajo.getCedulaIdentidad().intValue());
        q.setParameter("anho", legajo.getAnho());
        q.setParameter("mes", legajo.getMes());
        
        if (cptF != null) {
            q.setParameter("idCptF", cptF.getId());
            q.setParameter("ambitoCptF", cptF.getAmbito());
            q.setParameter("codigoProceso", cptF.getCodProceso());
            q.setParameter("denominacionCptF", cptF.getDen());
            q.setParameter("numeroSecuencial", cptF.getNro());
            q.setParameter("orientacionFuncional", cptF.getOrientacionFunc());
        } else {
            q.setParameter("idCptF", 0);
            q.setParameter("ambitoCptF", null);
            q.setParameter("codigoProceso", null);
            q.setParameter("denominacionCptF", null);
            q.setParameter("numeroSecuencial", null);
            q.setParameter("orientacionFuncional", null);
        }
        q.executeUpdate();
    }
    
    private List<Anexos> getByCedulaAnhoMes(Long cedulaIdentidad, int anho, int mes) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM Anexos a ");
        sql.append("WHERE a.id.cedulaIdentidad = :cedulaIdentidad ");
        sql.append("AND a.id.anho = :anho AND a.id.mes = :mes");
        Query q = em.createQuery(sql.toString());
        q.setParameter("cedulaIdentidad", cedulaIdentidad.intValue());
        q.setParameter("anho", anho);
        q.setParameter("mes", mes);
        return q.getResultList();
    }*/
    
    private boolean esHacienda() {
        if (esHacienda != null && esHacienda.equalsIgnoreCase("S")) {
            return true;
        }
        return false;
    }
}

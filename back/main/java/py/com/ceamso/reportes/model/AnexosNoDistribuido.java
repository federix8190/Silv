/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import py.com.ceamso.base.BaseModel;

/**
 *
 * @author daniel.rojas
 */
public class AnexosNoDistribuido extends BaseModel implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private static final Map<String, Integer> objetoGastoMap;
    static {
        Map<String, Integer> aMap = new HashMap<String, Integer>();
        aMap.put("111", 0);
        aMap.put("113", 1);
        aMap.put("123", 2);
        aMap.put("125", 3);
        aMap.put("131", 4);
        aMap.put("133", 5);
        aMap.put("137", 6);
        aMap.put("141", 7);
        aMap.put("144", 8);
        aMap.put("145", 9);
        aMap.put("199", 10);
        aMap.put("849", 11);
        objetoGastoMap = Collections.unmodifiableMap(aMap);
    }
 
    private Integer cedulaIdentidad;
    
    private String apellido;

    private String nombre;

    private Date fechaNacimiento;

    private Date fechaIngreso;

    private String sexo;
    
    private String vinculacion;

    private String profesion;

    private Integer nivelEducativo;

    private Integer orientacion;

    private Integer edad;

    private Integer antiguedad;
    
    private Integer anhoSF;

    private Integer mesSF;

    private Integer nivelEntidadSF;

    private String entidadSF;

    private Integer oeeSF;

    private Integer lineaSF;
    
    private String vinculacionSF;

    private Integer fuenteFinanciamientoSF;

    private String programaSF;

    private String subprogramaSF;

    private String dependenciaSF;

    private String categoriaSalarialSF;

    private String conceptoSF;

    private Integer presupuestadoSF;

    private String regimenSF;
    
    private Boolean vacanteSF;

    private String objetoGastoSF;

    private Integer devengadoSF;

    private String cargoSF;

    private String funcionRealSF;

    private BigInteger numeroPuestoTrabajoSF;

    private String codigoCeoSF;

    private String denominacionCeoSF;

    private String orientacionFuncionalSF;

    private Integer nivelCuoSF;

    private Integer subnivelCuoSF;

    private Integer numeroCuoSF;

    private String denominacionCuoSF;

    private BigInteger nivelCptSF;

    private Integer subnivelCptSF;

    private Integer numeroCptSF;

    private String denominacionCptSF;

    private Boolean titularUnidadSF;

    private String numeroSecuencialSF;

    private String ambitoSF;

    private String codigoProcesoSF;

    private String denominacionCptFSF;

    private BigInteger numeroTramoSF;

    private BigInteger minimoSF;

    private BigInteger maximoSF;
    
    private Integer anho;

    private Integer mes;

    private Integer nivelEntidad;

    private String entidad;

    private Integer oee;

    private Integer linea;

    private Integer fuenteFinanciamiento;

    private String programa;

    private String subprograma;

    private String dependencia;

    private String categoriaSalarial;

    private String concepto;

    private Integer presupuestado;

    private String regimen;
    
    private Boolean vacante;

    private String objetoGasto;

    private Integer devengado;

    private String cargo;

    private String funcionReal;

    private BigInteger numeroPuestoTrabajo;

    private String codigoCeo;

    private String denominacionCeo;

    private String orientacionFuncional;

    private Integer nivelCuo;

    private Integer subnivelCuo;

    private Integer numeroCuo;

    private String denominacionCuo;

    private BigInteger nivelCpt;

    private Integer subnivelCpt;

    private Integer numeroCpt;

    private String denominacionCpt;

    private Boolean titularUnidad;

    private String numeroSecuencial;

    private String ambito;

    private String codigoProceso;

    private String denominacionCptF;

    private BigInteger numeroTramo;

    private BigInteger minimo;

    private BigInteger maximo;
    
    private Integer caso;
    
    public AnexosNoDistribuido(){
        
    }

    public AnexosNoDistribuido(Integer cedulaIdentidad, String apellido, String nombre, Date fechaNacimiento, 
    		Date fechaIngreso, String sexo, String profesion, Integer nivelEducativo, Integer orientacion, 
    		Integer edad, Integer antiguedad, Integer anhoSF, Integer mesSF, Integer nivelEntidadSF, 
    		String entidadSF, Integer oeeSF, Integer lineaSF, Integer fuenteFinanciamientoSF, 
    		String programaSF, String subprogramaSF, String dependenciaSF, String categoriaSalarialSF, 
    		String conceptoSF, Integer presupuestadoSF, String regimenSF, Boolean vacanteSF, 
    		String objetoGastoSF, Integer devengadoSF, String cargoSF, String funcionRealSF, 
    		BigInteger numeroPuestoTrabajoSF, String codigoCeoSF, String denominacionCeoSF, 
    		String orientacionFuncionalSF, Integer nivelCuoSF, Integer subnivelCuoSF, Integer numeroCuoSF, 
    		String denominacionCuoSF, BigInteger nivelCptSF, Integer subnivelCptSF, Integer numeroCptSF, 
    		String denominacionCptSF, Boolean titularUnidadSF, String numeroSecuencialSF, String ambitoCptSF, 
    		String codigoProcesoSF, String denominacionCptFSF, BigInteger numeroTramoSF, BigInteger minimoSF, 
    		BigInteger maximoSF, Integer anho, Integer mes, Integer nivelEntidad, String entidad, Integer oee, 
    		Integer linea, Integer fuenteFinanciamiento, String programa, String subprograma, String dependencia, 
    		String categoriaSalarial, String concepto, Integer presupuestado, String regimen, Boolean vacante, 
    		String objetoGasto, Integer devengado, String cargo, String funcionReal, 
    		BigInteger numeroPuestoTrabajo, String codigoCeo, String denominacionCeo, 
    		String orientacionFuncional, Integer nivelCuo, Integer subnivelCuo, Integer numeroCuo, 
    		String denominacionCuo, BigInteger nivelCpt, Integer subnivelCpt, Integer numeroCpt, 
    		String denominacionCpt, Boolean titularUnidad, String numeroSecuencial, String ambitoCptEf, 
    		String codigoProceso, String denominacionCptF, BigInteger numeroTramo, BigInteger minimo, 
    		BigInteger maximo, Integer caso) {
    	
        this.cedulaIdentidad = cedulaIdentidad;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaIngreso = fechaIngreso;
        this.sexo = sexo;
        this.profesion = profesion;
        this.nivelEducativo = nivelEducativo;
        this.orientacion = orientacion;
        this.edad = edad;
        this.antiguedad = antiguedad;
        this.anhoSF = anhoSF;
        this.mesSF = mesSF;
        this.nivelEntidadSF = nivelEntidadSF;
        this.entidadSF = entidadSF;
        this.oeeSF = oeeSF;
        this.lineaSF = lineaSF;
        this.fuenteFinanciamientoSF = fuenteFinanciamientoSF;
        this.programaSF = programaSF;
        this.subprogramaSF = subprogramaSF;
        this.dependenciaSF = dependenciaSF;
        this.categoriaSalarialSF = categoriaSalarialSF;
        this.conceptoSF = conceptoSF;
        this.presupuestadoSF = presupuestadoSF;
        this.regimenSF = regimenSF;
        this.vacanteSF = vacanteSF;
        this.objetoGastoSF = objetoGastoSF;
        this.devengadoSF = devengadoSF;
        this.cargoSF = cargoSF;
        this.funcionRealSF = funcionRealSF;
        this.numeroPuestoTrabajoSF = numeroPuestoTrabajoSF;
        this.codigoCeoSF = codigoCeoSF;
        this.denominacionCeoSF = denominacionCeoSF;
        this.orientacionFuncionalSF = orientacionFuncionalSF;
        this.nivelCuoSF = nivelCuoSF;
        this.subnivelCuoSF = subnivelCuoSF;
        this.numeroCuoSF = numeroCuoSF;
        this.denominacionCuoSF = denominacionCuoSF;
        this.nivelCptSF = nivelCptSF;
        this.subnivelCptSF = subnivelCptSF;
        this.numeroCptSF = numeroCptSF;
        this.denominacionCptSF = denominacionCptSF;
        this.titularUnidadSF = titularUnidadSF;
        this.numeroSecuencialSF = numeroSecuencialSF;
        this.ambitoSF = ambitoCptSF;
        this.codigoProcesoSF = codigoProcesoSF;
        this.denominacionCptFSF = denominacionCptFSF;
        this.numeroTramoSF = numeroTramoSF;
        this.minimoSF = minimoSF;
        this.maximoSF = maximoSF;
        this.anho = anho;
        this.mes = mes;
        this.nivelEntidad = nivelEntidad;
        this.entidad = entidad;
        this.oee = oee;
        this.linea = linea;
        this.fuenteFinanciamiento = fuenteFinanciamiento;
        this.programa = programa;
        this.subprograma = subprograma;
        this.dependencia = dependencia;
        this.categoriaSalarial = categoriaSalarial;
        this.concepto = concepto;
        this.presupuestado = presupuestado;
        this.regimen = regimen;
        this.vacante = vacante;
        this.objetoGasto = objetoGasto;
        this.devengado = devengado;
        this.cargo = cargo;
        this.funcionReal = funcionReal;
        this.numeroPuestoTrabajo = numeroPuestoTrabajo;
        this.codigoCeo = codigoCeo;
        this.denominacionCeo = denominacionCeo;
        this.orientacionFuncional = orientacionFuncional;
        this.nivelCuo = nivelCuo;
        this.subnivelCuo = subnivelCuo;
        this.numeroCuo = numeroCuo;
        this.denominacionCuo = denominacionCuo;
        this.nivelCpt = nivelCpt;
        this.subnivelCpt = subnivelCpt;
        this.numeroCpt = numeroCpt;
        this.denominacionCpt = denominacionCpt;
        this.titularUnidad = titularUnidad;
        this.numeroSecuencial = numeroSecuencial;
        this.ambito = ambitoCptEf;
        this.codigoProceso = codigoProceso;
        this.denominacionCptF = denominacionCptF;
        this.numeroTramo = numeroTramo;
        this.minimo = minimo;
        this.maximo = maximo;
        this.caso = caso;
    }

    public Integer getAnho() {
        return anho;
    }

    public void setAnho(Integer anho) {
        this.anho = anho;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnhoSF() {
        return anhoSF;
    }

    public void setAnhoSF(Integer anhoSF) {
        this.anhoSF = anhoSF;
    }

    public Integer getMesSF() {
        return mesSF;
    }

    public void setMesSF(Integer mesSF) {
        this.mesSF = mesSF;
    }

    public Integer getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(Integer cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }
    
    public String getNivelEducativoFile() {
       if(this.nivelEducativo == 0) return "UNIVERSITARIO";
       else if(this.nivelEducativo == 1) return "SECUNDARIO";
       else if(this.nivelEducativo == 2) return "PRIMARIA COMPL.";
       else return "PRIMARIA INCOMPL.";
    }

    public Integer getNivelEducativo() {
        return nivelEducativo;
    }

    public void setNivelEducativo(Integer nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }
    
    public String getOrientacionFile() {
        if(this.orientacion == 0) return "MISIONAL MH";
        else if(this.orientacion == 1) return "DERECHO";
        else if(this.orientacion == 2) return "INFORMÁTICA";
        else if(this.orientacion == 3) return "SOCIALES";
        else return "OTROS";
    }

    public Integer getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Integer orientacion) {
        this.orientacion = orientacion;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Integer antiguedad) {
        this.antiguedad = antiguedad;
    }

    public Integer getNivelEntidadSF() {
        return nivelEntidadSF;
    }

    public void setNivelEntidadSF(Integer nivelEntidadSF) {
        this.nivelEntidadSF = nivelEntidadSF;
    }

    public String getEntidadSF() {
        return entidadSF;
    }

    public void setEntidadSF(String entidadSF) {
        this.entidadSF = entidadSF;
    }

    public Integer getOeeSF() {
        return oeeSF;
    }

    public void setOeeSF(Integer oeeSF) {
        this.oeeSF = oeeSF;
    }

    public Integer getLineaSF() {
        return lineaSF;
    }

    public void setLineaSF(Integer lineaSF) {
        this.lineaSF = lineaSF;
    }

    public Integer getFuenteFinanciamientoSF() {
        return fuenteFinanciamientoSF;
    }

    public void setFuenteFinanciamientoSF(Integer fuenteFinanciamientoSF) {
        this.fuenteFinanciamientoSF = fuenteFinanciamientoSF;
    }

    public String getProgramaSF() {
        return programaSF;
    }

    public void setProgramaSF(String programaSF) {
        this.programaSF = programaSF;
    }

    public String getSubprogramaSF() {
        return subprogramaSF;
    }

    public void setSubprogramaSF(String subprogramaSF) {
        this.subprogramaSF = subprogramaSF;
    }

    public String getDependenciaSF() {
        return dependenciaSF;
    }

    public void setDependenciaSF(String dependenciaSF) {
        this.dependenciaSF = dependenciaSF;
    }

    public String getCategoriaSalarialSF() {
        return categoriaSalarialSF;
    }

    public void setCategoriaSalarialSF(String categoriaSalarialSF) {
        this.categoriaSalarialSF = categoriaSalarialSF;
    }

    public String getConceptoSF() {
        return conceptoSF;
    }

    public void setConceptoSF(String conceptoSF) {
        this.conceptoSF = conceptoSF;
    }

    public Integer getPresupuestadoSF() {
        return presupuestadoSF;
    }

    public void setPresupuestadoSF(Integer presupuestadoSF) {
        this.presupuestadoSF = presupuestadoSF;
    }

    public String getRegimenSF() {
        return regimenSF;
    }

    public void setRegimenSF(String regimenSF) {
        this.regimenSF = regimenSF;
    }

    public String getObjetoGastoSFFile() {
        String[] oGs = {";", ";", ";", ";", ";", ";", ";", ";", ";", ";", ";", ""};
        String[] splitOG = objetoGastoSF.split(";");
        String respuesta = "";
        
        for(String s : splitOG){
            if(s.equals("849")) oGs[objetoGastoMap.get(s)] = "X";
            else oGs[objetoGastoMap.get(s)] = "X;";
        }
        
        for(String s : oGs)
            respuesta += s;
        
        System.out.println(respuesta);
        
        return respuesta;
    }

    public String getObjetoGastoSF(){
        return this.objetoGastoSF;
    }
    
    public void setObjetoGastoSF(String objetoGastoSF) {
        this.objetoGastoSF = objetoGastoSF;
    }

    public Integer getDevengadoSF() {
        return devengadoSF;
    }

    public void setDevengadoSF(Integer devengadoSF) {
        this.devengadoSF = devengadoSF;
    }

    public String getCargoSF() {
        return cargoSF;
    }

    public void setCargoSF(String cargoSF) {
        this.cargoSF = cargoSF;
    }

    public String getFuncionRealSF() {
        return funcionRealSF;
    }

    public void setFuncionRealSF(String funcionRealSF) {
        this.funcionRealSF = funcionRealSF;
    }

    public BigInteger getNumeroPuestoTrabajoSF() {
        return numeroPuestoTrabajoSF;
    }

    public void setNumeroPuestoTrabajoSF(BigInteger numeroPuestoTrabajoSF) {
        this.numeroPuestoTrabajoSF = numeroPuestoTrabajoSF;
    }

    public String getCodigoCeoSF() {
        return codigoCeoSF;
    }

    public void setCodigoCeoSF(String codigoCeoSF) {
        this.codigoCeoSF = codigoCeoSF;
    }

    public String getDenominacionCeoSF() {
        return denominacionCeoSF;
    }

    public void setDenominacionCeoSF(String denominacionCeoSF) {
        this.denominacionCeoSF = denominacionCeoSF;
    }

    public String getOrientacionFuncionalSF() {
        return orientacionFuncionalSF;
    }

    public void setOrientacionFuncionalSF(String orientacionFuncionalSF) {
        this.orientacionFuncionalSF = orientacionFuncionalSF;
    }

    public Integer getNivelCuoSF() {
        return nivelCuoSF;
    }

    public void setNivelCuoSF(Integer nivelCuoSF) {
        this.nivelCuoSF = nivelCuoSF;
    }

    public Integer getSubnivelCuoSF() {
        return subnivelCuoSF;
    }

    public void setSubnivelCuoSF(Integer subnivelCuoSF) {
        this.subnivelCuoSF = subnivelCuoSF;
    }

    public Integer getNumeroCuoSF() {
        return numeroCuoSF;
    }

    public void setNumeroCuoSF(Integer numeroCuoSF) {
        this.numeroCuoSF = numeroCuoSF;
    }

    public String getDenominacionCuoSF() {
        return denominacionCuoSF;
    }

    public void setDenominacionCuoSF(String denominacionCuoSF) {
        this.denominacionCuoSF = denominacionCuoSF;
    }

    public BigInteger getNivelCptSF() {
        return nivelCptSF;
    }

    public void setNivelCptSF(BigInteger nivelCptSF) {
        this.nivelCptSF = nivelCptSF;
    }

    public Integer getSubnivelCptSF() {
        return subnivelCptSF;
    }

    public void setSubnivelCptSF(Integer subnivelCptSF) {
        this.subnivelCptSF = subnivelCptSF;
    }

    public Integer getNumeroCptSF() {
        return numeroCptSF;
    }

    public void setNumeroCptSF(Integer numeroCptSF) {
        this.numeroCptSF = numeroCptSF;
    }

    public String getDenominacionCptSF() {
        return denominacionCptSF;
    }

    public void setDenominacionCptSF(String denominacionCptSF) {
        this.denominacionCptSF = denominacionCptSF;
    }

    public Boolean getTitularUnidadSF() {
        return titularUnidadSF;
    }

    public void setTitularUnidadSF(Boolean titularUnidadSF) {
        this.titularUnidadSF = titularUnidadSF;
    }

    public String getNumeroSecuencialSF() {
        return numeroSecuencialSF;
    }

    public void setNumeroSecuencialSF(String numeroSecuencialSF) {
        this.numeroSecuencialSF = numeroSecuencialSF;
    }

    public String getAmbitoCptSF() {
        return ambitoSF;
    }

    public void setAmbitoCptSF(String ambitoCptSF) {
        this.ambitoSF = ambitoCptSF;
    }

    public String getCodigoProcesoSF() {
        return codigoProcesoSF;
    }

    public void setCodigoProcesoSF(String codigoProcesoSF) {
        this.codigoProcesoSF = codigoProcesoSF;
    }

    public String getDenominacion_cpt_ef_sf() {
        return denominacionCptFSF;
    }

    public void setDenominacion_cpt_ef_sf(String denominacionCptFSF) {
        this.denominacionCptFSF = denominacionCptFSF;
    }

    public BigInteger getNumeroTramoSF() {
        return numeroTramoSF;
    }

    public void setNumeroTramoSF(BigInteger numeroTramoSF) {
        this.numeroTramoSF = numeroTramoSF;
    }

    public BigInteger getMinimoSF() {
        return minimoSF;
    }

    public void setMinimoSF(BigInteger minimoSF) {
        this.minimoSF = minimoSF;
    }

    public BigInteger getMaximoSF() {
        return maximoSF;
    }

    public void setMaximoSF(BigInteger maximoSF) {
        this.maximoSF = maximoSF;
    }

    public Integer getNivelEntidad() {
        return nivelEntidad;
    }

    public void setNivelEntidad(Integer nivelEntidad) {
        this.nivelEntidad = nivelEntidad;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public Integer getOee() {
        return oee;
    }

    public void setOee(Integer oee) {
        this.oee = oee;
    }

    public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }

    public Integer getFuenteFinanciamiento() {
        return fuenteFinanciamiento;
    }

    public void setFuenteFinanciamiento(Integer fuenteFinanciamiento) {
        this.fuenteFinanciamiento = fuenteFinanciamiento;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getSubprograma() {
        return subprograma;
    }

    public void setSubprograma(String subprograma) {
        this.subprograma = subprograma;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getCategoriaSalarial() {
        return categoriaSalarial;
    }

    public void setCategoriaSalarial(String categoriaSalarial) {
        this.categoriaSalarial = categoriaSalarial;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getPresupuestado() {
        return presupuestado;
    }

    public void setPresupuestado(Integer presupuestado) {
        this.presupuestado = presupuestado;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getObjetoGastoFile() {
        String[] oGs = {";", ";", ";", ";", ";", ";", ";", ";", ";", ";", ";", ""};
        String[] splitOG = objetoGasto.split(";");
        String respuesta = "";
        
        for(String s : splitOG){
            if(s.equals("849")) oGs[objetoGastoMap.get(s)] = "X";
            else oGs[objetoGastoMap.get(s)] = "X;";
        }
        for(String s : oGs)
            respuesta += s;
        
        return respuesta;
    }

    public String getObjetoGasto(){
        return this.objetoGasto;
    }
    
    public void setObjetoGasto(String objetoGasto) {
        this.objetoGasto = objetoGasto;
    }

    public Integer getDevengado() {
        return devengado;
    }

    public void setDevengado(Integer devengado) {
        this.devengado = devengado;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFuncionReal() {
        return funcionReal;
    }

    public void setFuncionReal(String funcionReal) {
        this.funcionReal = funcionReal;
    }

    public BigInteger getNumeroPuestoTrabajo() {
        return numeroPuestoTrabajo;
    }

    public void setNumeroPuestoTrabajo(BigInteger numeroPuestoTrabajo) {
        this.numeroPuestoTrabajo = numeroPuestoTrabajo;
    }

    public String getCodigoCeo() {
        return codigoCeo;
    }

    public void setCodigoCeo(String codigoCeo) {
        this.codigoCeo = codigoCeo;
    }

    public String getDenominacionCeo() {
        return denominacionCeo;
    }

    public void setDenominacionCeo(String denominacionCeo) {
        this.denominacionCeo = denominacionCeo;
    }

    public String getOrientacionFuncional() {
        return orientacionFuncional;
    }

    public void setOrientacionFuncional(String orientacionFuncional) {
        this.orientacionFuncional = orientacionFuncional;
    }

    public Integer getNivelCuo() {
        return nivelCuo;
    }

    public void setNivelCuo(Integer nivelCuo) {
        this.nivelCuo = nivelCuo;
    }

    public Integer getSubnivelCuo() {
        return subnivelCuo;
    }

    public void setSubnivelCuo(Integer subnivelCuo) {
        this.subnivelCuo = subnivelCuo;
    }

    public Integer getNumeroCuo() {
        return numeroCuo;
    }

    public void setNumeroCuo(Integer numeroCuo) {
        this.numeroCuo = numeroCuo;
    }

    public String getDenominacionCuo() {
        return denominacionCuo;
    }

    public void setDenominacionCuo(String denominacionCuo) {
        this.denominacionCuo = denominacionCuo;
    }

    public BigInteger getNivelCpt() {
        return nivelCpt;
    }

    public void setNivelCpt(BigInteger nivelCpt) {
        this.nivelCpt = nivelCpt;
    }

    public Integer getSubnivelCpt() {
        return subnivelCpt;
    }

    public void setSubnivelCpt(Integer subnivelCpt) {
        this.subnivelCpt = subnivelCpt;
    }

    public Integer getNumeroCpt() {
        return numeroCpt;
    }

    public void setNumeroCpt(Integer numeroCpt) {
        this.numeroCpt = numeroCpt;
    }

    public String getDenominacionCpt() {
        return denominacionCpt;
    }

    public void setDenominacionCpt(String denominacionCpt) {
        this.denominacionCpt = denominacionCpt;
    }

    public Boolean getTitularUnidad() {
        return titularUnidad;
    }

    public void setTitularUnidad(Boolean titularUnidad) {
        this.titularUnidad = titularUnidad;
    }

    public String getNumeroSecuencial() {
        return numeroSecuencial;
    }

    public void setNumeroSecuencial(String numeroSecuencial) {
        this.numeroSecuencial = numeroSecuencial;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getCodigoProceso() {
        return codigoProceso;
    }

    public void setCodigoProceso(String codigoProceso) {
        this.codigoProceso = codigoProceso;
    }

    public String getDenominacion_cpt_ef() {
        return denominacionCptF;
    }

    public void setDenominacion_cpt_ef(String denominacionCptF) {
        this.denominacionCptF = denominacionCptF;
    }

    public BigInteger getNumeroTramo() {
        return numeroTramo;
    }

    public void setNumeroTramo(BigInteger numeroTramo) {
        this.numeroTramo = numeroTramo;
    }

    public BigInteger getMinimo() {
        return minimo;
    }

    public void setMinimo(BigInteger minimo) {
        this.minimo = minimo;
    }

    public BigInteger getMaximo() {
        return maximo;
    }

    public void setMaximo(BigInteger maximo) {
        this.maximo = maximo;
    }

    public Boolean getVacanteSF() {
        return vacanteSF;
    }

    public void setVacanteSF(Boolean vacanteSF) {
        this.vacanteSF = vacanteSF;
    }

    public Boolean getVacante() {
        return vacante;
    }

    public void setVacante(Boolean vacante) {
        this.vacante = vacante;
    }

    public String getVinculacion() {
		return vinculacion;
	}

	public void setVinculacion(String vinculacion) {
		this.vinculacion = vinculacion;
	}

	public String getVinculacionSF() {
		return vinculacionSF;
	}

	public void setVinculacionSF(String vinculacionSF) {
		this.vinculacionSF = vinculacionSF;
	}

	public String getDenominacionCptFSF() {
		return denominacionCptFSF;
	}

	public void setDenominacionCptFSF(String denominacionCptFSF) {
		this.denominacionCptFSF = denominacionCptFSF;
	}

	public String getDenominacionCptF() {
		return denominacionCptF;
	}

	public void setDenominacionCptF(String denominacionCptF) {
		this.denominacionCptF = denominacionCptF;
	}

	public Integer getCaso() {
        return caso;
    }

    public void setCaso(Integer caso) {
        this.caso = caso;
    }

    public String getBlank(){
        return "";
    }
}

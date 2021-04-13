package py.com.ceamso.reportes.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Transient;
import py.com.ceamso.base.BaseModel;

@Entity
@Table(name = "v_cte_anexo_liquidacion")
public class Anexos extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AnexosPK id;
    
    // Datos generales
    
    @Column(name = "fuente_financiamiento")
    private Integer fuenteFinanciamiento;
       
    @Column(name = "nivel_entidad")
    private Integer nivelEntidad;

    @Column(name = "entidad")
    private String entidad;
    
    @Column(name = "dependencia")
    private String dependencia;

    @Column(name = "oee")
    private Integer oee;

    @Column(name = "programa")
    private String programa;

    @Column(name = "subprograma")
    private String subprograma;
    
    @Column(name = "programa_presupuestario")
    private String programaPresupuestario;

    @Column(name = "subprograma_presupuestario")
    private String subProgramPresupuestario;
    
    @Column(name = "categoria")
    private String categoria;

    @Column(name = "presupuestado")
    private Integer presupuestado;

    @Column(name = "devengado")
    private Integer devengado;

    @Column(name = "vinculacion_funcionario")
    private String vinculacionFuncionario;
    
    @Column(name = "linea")
    private Integer linea;
    
    @Column(name = "numero_puesto")
    private Long numeroPuestoTrabajo;
    
    // Datos personales
    
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;
    
    // Datos ocupacionales

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "funcion_real")
    private String funcionReal;

    // Datos del tramo salarial

    @Column(name = "numero_tramo")    
    private Integer tramo;
    
    @Column(name = "minimo")
    private Long minimo;

    @Column(name = "maximo")
    private Long maximo;
    
    // Datos CPT
    @Column(name = "id_cpt")
    private Long idCpt;
    
    @Column(name = "nivel_cpt")
    private Long nivelCpt;
    
    @Column(name = "subnivel_cpt")
    private Integer subNivelCpt;
    
    @Column(name = "numero_cpt")
    private Integer numeroCpt;
    
    @Column(name = "denominacion_cpt")
    private String denominacionCpt;
    
    @Column(name = "titular_unidad")
    private Boolean titularUnidad;
    
    // Datos CPT EF
    @Column(name = "id_cpt_ef")
    private Long idCptF;
    
    @Column(name = "ambito_cpt_ef")
    private String ambitoCptF;
    
    @Column(name = "codigo_proceso")
    private String codigoProceso;
    
    @Column(name = "denominacion_cpt_ef")
    private String denominacionCptF;
    
    @Column(name = "numero_secuencial")
    private String numeroSecuencialCptF;
    
    @Column(name = "orientacion_funcional")
    private String orientacionFuncional;
    
    // Datos CPT EE
    @Column(name = "id_cpt_ee")
    private Long idCptE;
    
    @Column(name = "ambito_cpt_ee")
    private String ambitoCptE;
    
    @Column(name = "categoria_cpt_ee")
    private String categoriaCptE;
    
    @Column(name = "denominacion_cpt_ee")
    private String denominacionCptE;
    
    @Column(name = "nivel_cpt_ee")
    private String nivelCptE;
    
    @Column(name = "numero_secuencial_cpt_ee")
    private String numeroSecuencialCptE;
    
    // Datos CEO
    @Column(name = "id_ceo")
    private Long idCeo;
    
    @Column(name = "codigo_ceo")
    private String codigoCeo;
    
    @Column(name = "denominacion_ceo")
    private String denominacionCeo;

    // Datos CUO
    @Column(name = "id_cuo")
    private Long idCuo;
    
    @Column(name = "nivel_cuo")
    private Integer nivelCuo;
    
    @Column(name = "subnivel_cuo")
    private Integer subNivelCuo;
    
    @Column(name = "numero_cuo")
    private Integer numeroCuo;
    
    @Column(name = "denominacion_cuo")
    private String denominacionCuo;

    @Column(name = "sexo")
    private String sexo;
    
    @Column(name = "orden")
    private Integer orden;
    
    @Transient
    private Integer anho;
    
    @Transient
    private Integer mes;
    
    @Transient
    private Integer cedulaIdentidad;
    
    @Transient
    private String objetoGasto;
    
    @Transient
    private String concepto;
    
    public Anexos() {
    }

    public Anexos(Integer nivelEntidad, String entidad, Integer oee, String programa,
            String direccion, String subprograma, String categoria,
            Integer presupuestado, Integer devengado, String vinculacionFuncionario,
            String cargo, String funcionReal) {

        this.nivelEntidad = nivelEntidad;
        this.entidad = entidad;
        this.oee = oee;
        this.programa = programa;
        this.subprograma = subprograma;
        this.categoria = categoria;
        this.presupuestado = presupuestado;
        this.devengado = devengado;
        this.vinculacionFuncionario = vinculacionFuncionario;
        this.cargo = cargo;
        this.funcionReal = funcionReal;
    } 
    
    public Anexos(Object[] datos) {
        
        Integer anho = (Integer) datos[0];
        Integer mes = (Integer) datos[1];
        Integer cedulaIdentidad = (Integer) datos[2];
        String concepto = (String) datos[3];
        String objetoGasto = (String) datos[4];
        AnexosPK pk = new AnexosPK(anho, mes, objetoGasto, concepto, cedulaIdentidad);
        this.id = pk;
        
        this.nivelEntidad = (Integer) datos[5];
        this.entidad = ((Integer) datos[6]).toString();
        this.oee = (Integer) datos[7];
        
        /*String lineaString = (String) datos[5];
        String cedulaIdentidadString = (String) datos[7];
        Integer objetoGasto = (Integer) datos[12]; 
        String concepto = (String) datos[17];
        
        Integer linea = 0;
        try {
            linea = new Integer(lineaString);
        } catch (Exception e){
        }
        
        Integer cedulaIdentidad = 0;
        try {
            cedulaIdentidad = new Integer(cedulaIdentidadString);
        } catch (Exception e){
        }
            
        
        this.linea = linea;
        //this.programa = programa;
        //this.direccion = direccion;
        //this.coordinacionDpto = coordinacionDpto;
        this.vinculacionFuncionario = (String) datos[10];
        this.categoria = (String) datos[14];
        this.fuenteFinanciamiento = (Integer) datos[13];
        this.presupuestado = ((BigDecimal) datos[15]).intValue();
        this.devengado = ((BigDecimal) datos[16]).intValue();
        this.cargo = (String) datos[20];
        //this.funcionReal = funcionReal;*/
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

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
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
    
    public String getProgramaPresupuestario() {
        return programaPresupuestario;
    }

    public void setProgramaPresupuestario(String programaPresupuestario) {
        this.programaPresupuestario = programaPresupuestario;
    }

    public String getSubProgramPresupuestario() {
        return subProgramPresupuestario;
    }

    public void setSubprogramaPresupuestario(String subProgramPresupuestario) {
        this.subProgramPresupuestario = subProgramPresupuestario;
    }

    public Long getMinimo() {
        return minimo;
    }

    public void setMinimo(Long minimo) {
        this.minimo = minimo;
    }

    public Long getMaximo() {
        return maximo;
    }

    public void setMaximo(Long maximo) {
        this.maximo = maximo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getPresupuestado() {
        return presupuestado;
    }

    public void setPresupuestado(Integer presupuestado) {
        this.presupuestado = presupuestado;
    }

    public Integer getDevengado() {
        return devengado;
    }

    public void setDevengado(Integer devengado) {
        this.devengado = devengado;
    }

    public String getVinculacionFuncionario() {
        return vinculacionFuncionario;
    }

    public void setVinculacionFuncionario(String vinculacionFuncionario) {
        this.vinculacionFuncionario = vinculacionFuncionario;
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

    public AnexosPK getId() {
        return id;
    }

    public void setId(AnexosPK id) {
        this.id = id;
    }

    public String getPk() {
        return getAnho() + "-" + getMes() + "-" + getCedulaIdentidad() 
                //+ "-" + getNumeroPuesto() // + "-" + getLinea() 
                + "-" + getObjetoGasto() + "-" + getId().getConcepto();
    }

    public int getAnho() {
        return getId().getAnho();
    }

    public int getMes() {
        return getId().getMes();
    }

    /*public int getLinea() {
        return getId().getLinea();
    }*/

    public int getCedulaIdentidad() {
        return getId().getCedulaIdentidad();
    }
    
    /*public Long getNumeroPuesto() {
        return getId().getNumeroPuestoTrabajo();
    }*/
    
    public Long getNumeroPuestoTrabajo() {
        return numeroPuestoTrabajo;
    }

    public void setNumeroPuestoTrabajo(Long numeroPuestoTrabajo) {
        this.numeroPuestoTrabajo = numeroPuestoTrabajo;
    }

    public String getObjetoGasto() {
        return getId().getObjetoGasto();
    }
    
    public String getConcepto() {
        return getId().getConcepto();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public Integer getFuenteFinanciamiento() {
        return fuenteFinanciamiento;
    }

    public void setFuenteFinanciamiento(Integer fuenteFinanciamiento) {
        this.fuenteFinanciamiento = fuenteFinanciamiento;
    }

    public Integer getTramo() {
        return tramo;
    }

    public void setTramo(Integer tramo) {
        this.tramo = tramo;
    }

    public Long getIdCpt() {
        return idCpt;
    }

    public void setIdCpt(Long idCpt) {
        this.idCpt = idCpt;
    }

    public Long getNivelCpt() {
        return nivelCpt;
    }

    public void setNivelCpt(Long nivelCpt) {
        this.nivelCpt = nivelCpt;
    }

    public Integer getSubNivelCpt() {
        return subNivelCpt;
    }

    public void setSubNivelCpt(Integer subNivelCpt) {
        this.subNivelCpt = subNivelCpt;
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

    public Long getIdCptF() {
        return idCptF;
    }

    public void setIdCptF(Long idCptF) {
        this.idCptF = idCptF;
    }

    public String getAmbitoCptF() {
        return ambitoCptF;
    }

    public void setAmbitoCptF(String ambitoCptF) {
        this.ambitoCptF = ambitoCptF;
    }

    public String getCodigoProceso() {
        return codigoProceso;
    }

    public void setCodigoProceso(String codigoProceso) {
        this.codigoProceso = codigoProceso;
    }

    public String getDenominacionCptF() {
        return denominacionCptF;
    }

    public void setDenominacionCptF(String denominacionCptF) {
        this.denominacionCptF = denominacionCptF;
    }

    public String getNumeroSecuencialCptF() {
        return numeroSecuencialCptF;
    }

    public void setNumeroSecuencialCptF(String numeroSecuencialCptF) {
        this.numeroSecuencialCptF = numeroSecuencialCptF;
    }

    public String getOrientacionFuncional() {
        return orientacionFuncional;
    }

    public void setOrientacionFuncional(String orientacionFuncional) {
        this.orientacionFuncional = orientacionFuncional;
    }

    public Long getIdCptE() {
        return idCptE;
    }

    public void setIdCptE(Long idCptE) {
        this.idCptE = idCptE;
    }

    public String getAmbitoCptE() {
        return ambitoCptE;
    }

    public void setAmbitoCptE(String ambitoCptE) {
        this.ambitoCptE = ambitoCptE;
    }

    public String getCategoriaCptE() {
        return categoriaCptE;
    }

    public void setCategoriaCptE(String categoriaCptE) {
        this.categoriaCptE = categoriaCptE;
    }

    public String getDenominacionCptE() {
        return denominacionCptE;
    }

    public void setDenominacionCptE(String denominacionCptE) {
        this.denominacionCptE = denominacionCptE;
    }

    public String getNivelCptE() {
        return nivelCptE;
    }

    public void setNivelCptE(String nivelCptE) {
        this.nivelCptE = nivelCptE;
    }

    public String getNumeroSecuencialCptE() {
        return numeroSecuencialCptE;
    }

    public void setNumeroSecuencialCptE(String numeroSecuencialCptE) {
        this.numeroSecuencialCptE = numeroSecuencialCptE;
    }

    public Long getIdCeo() {
        return idCeo;
    }

    public void setIdCeo(Long idCeo) {
        this.idCeo = idCeo;
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

    public Long getIdCuo() {
        return idCuo;
    }

    public void setIdCuo(Long idCuo) {
        this.idCuo = idCuo;
    }

    public Integer getNivelCuo() {
        return nivelCuo;
    }

    public void setNivelCuo(Integer nivelCuo) {
        this.nivelCuo = nivelCuo;
    }

    public Integer getSubNivelCuo() {
        return subNivelCuo;
    }

    public void setSubNivelCuo(Integer subNivelCuo) {
        this.subNivelCuo = subNivelCuo;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    
}

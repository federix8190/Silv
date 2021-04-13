package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import py.com.ceamso.base.BaseModel;

@Entity
@Table(name = "v_cte_funcionario")
public class Legajo extends BaseModel implements Serializable {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");

    @Id
    @Column(name = "cedula_identidad")
    private Long cedulaIdentidad;
    
    @Column(name = "anho")
    private Integer anho;
    
    @Column(name = "mes")
    private Integer mes;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "sexo")
    private String sexo;
    
    @Column(name = "codigo_depto")
    private String codigoDepartamento;
    
    @Column(name = "nombre_depto")
    private String nombreDepartamento;
    
    @Column(name = "codigo_distrito")
    private String codigoDistrito;
    
    @Column(name = "nombre_distrito")
    private String nombreDistrito;
    
    @Column(name = "lista_grado_academico")
    private String titulo;
    
    @Column(name = "cargo")
    private String cargo;
    
    @Column(name = "antiguedad")
    private Integer antiguedadCargo;
    
    @Column(name = "funcion_real")
    private String funcionReal;
    
    @Column(name = "vinculacion_funcionario")
    private String vinculacionFuncionario;

    @Transient
    private String fechaNacimientoString;
    
    //@Transient
    @Column(name = "numero_tramo")
    private Long numeroTramo;
/*    
    @Column(name="nombre_depto")
    private String nombreDepto;

    @Column(name="nombre_distrito")
    private String nombreDistrito;*/

    @Column(name="ubicacion_fisica")
    private String ubicacionFisica;
    
    @Column(name="tipo_discapacidad")
    private String tipoDiscapacidad;
    
    // Datos CPT
    @Column(name = "id_cpt")
    private Long idCpt;
    
    @Column(name = "nivel_cpt")
    private Long nivel;
    
    @Column(name = "subnivel_cpt")
    private String subNivelCpt;
    
    @Column(name = "numero_cpt")
    private String numeroCpt;
    
    @Column(name = "denominacion_cpt")
    private String denominacionCpt;
    
    @Column(name = "titular_unidad")
    private Boolean titularUnidad;
    
    // Datos CPT EF
    @Column(name = "id_cpt_ef")
    private Long idCptEf;
    
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
    
    @Column(name = "codigo_mecip")
    private String codigoMecip;
    
    // Datos CPT EE
    @Column(name = "id_cpt_ee")
    private Long idCptEe;
    
    @Column(name = "ambito_cpt_ee")
    private String ambitoCptE;
    
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
    
    @Column(name = "programa")
    private String programa;
    
    @Column(name = "subprograma")
    private String subprograma;

    @Transient
    private boolean asignado;

    @Transient
    private boolean sinAsignar;
    
    @Transient
    private String candidatos;
    
    @Transient
    private String matriz;
    
    public Legajo() {
    }
    
    public Legajo(Legajo l, Long nivel, String nombreCargo) {
        this.cedulaIdentidad = l.getCedulaIdentidad();
        this.apellido = l.getApellido();
        this.nombre = l.getNombre();
        this.sexo = l.getSexo();
        this.nivel = nivel;
        this.cargo = nombreCargo;
        //this.localidad = l.getLocalidad();
        this.vinculacionFuncionario = l.getVinculacionFuncionario();
        if (l.getFechaNacimiento() != null) {
            this.fechaNacimientoString = SDF.format(l.getFechaNacimiento());
        }
    }
    
    /*public Legajo(Legajo l, LegajoCargo cargo, String nombreCargo, Long nivel) {
        this.cedulaIdentidad = l.getCedulaIdentidad();
        this.apellido = l.getApellido();
        this.nombre = l.getNombre();
        this.sexo = l.getSexo();
        this.cargo = nombreCargo;
        this.nivel = nivel;
        //this.localidad = l.getLocalidad();
        this.funcionReal = cargo.getFuncionReal();
        this.vinculacionFuncionario = cargo.getVinculacionFuncionario();
        if (l.getFechaNacimiento() != null) {
            this.fechaNacimientoString = SDF.format(l.getFechaNacimiento());
        }
    }*/

    public Long getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(Long cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }    

    public String getFechaNacimientoString() {
        if (this.getFechaNacimiento() != null) {
            this.fechaNacimientoString = SDF.format(this.getFechaNacimiento());
        }
        return fechaNacimientoString;
    }

    public void setFechaNacimientoString(String fechaNacimientoString) {
        this.fechaNacimientoString = fechaNacimientoString;
    }

    /*public String getListaGradoAcademico() {
        return listaGradoAcademico;
    }

    public void setListaGradoAcademico(String listaGradoAcademico) {
        this.listaGradoAcademico = listaGradoAcademico;
    }    

    /*public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }*/

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getAntiguedadCargo() {
        return antiguedadCargo;
    }

    public void setAntiguedadCargo(Integer antiguedadCargo) {
        this.antiguedadCargo = antiguedadCargo;
    }

    public String getFuncionReal() {
        return funcionReal;
    }

    public void setFuncionReal(String funcionReal) {
        this.funcionReal = funcionReal;
    }

    public String getVinculacionFuncionario() {
        return vinculacionFuncionario;
    }

    public void setVinculacionFuncionario(String vinculacionFuncionario) {
        this.vinculacionFuncionario = vinculacionFuncionario;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getCodigoDistrito() {
        return codigoDistrito;
    }

    public void setCodigoDistrito(String codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }        

    public boolean isAsignado() {
        return asignado;
    }

    public void setAsignado(boolean asignado) {
        this.asignado = asignado;
    }

    public boolean isSinAsignar() {
        return sinAsignar;
    }

    public void setSinAsignar(boolean sinAsignar) {
        this.sinAsignar = sinAsignar;
    }

    public Long getNumeroTramo() {
        return numeroTramo;
    }

    public void setNumeroTramo(Long numeroTramo) {
        this.numeroTramo = numeroTramo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUbicacionFisica() {
        return ubicacionFisica;
    }

    public void setUbicacionFisica(String ubicacionFisica) {
        this.ubicacionFisica = ubicacionFisica;
    }

    public String getTipoDiscapacidad() {
        return tipoDiscapacidad;
    }

    public void setTipoDiscapacidad(String tipoDiscapacidad) {
        this.tipoDiscapacidad = tipoDiscapacidad;
    }
    
    public Long getIdCptEe() {
        return idCptEe;
    }

    public void setIdCptEe(Long idCptEe) {
        this.idCptEe = idCptEe;
    }

    public Long getIdCptEf() {
        return idCptEf;
    }

    public void setIdCptEf(Long idCptEf) {
        this.idCptEf = idCptEf;
    }

    public String getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(String candidatos) {
        this.candidatos = candidatos;
    }

    public Long getIdCpt() {
        return idCpt;
    }

    public void setIdCpt(Long idCpt) {
        this.idCpt = idCpt;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public String getSubNivelCpt() {
        return subNivelCpt;
    }

    public void setSubNivelCpt(String subNivelCpt) {
        this.subNivelCpt = subNivelCpt;
    }

    public String getNumeroCpt() {
        return numeroCpt;
    }

    public void setNumeroCpt(String numeroCpt) {
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

    public String getCodigoMecip() {
		return codigoMecip;
	}

	public void setCodigoMecip(String codigoMecip) {
		this.codigoMecip = codigoMecip;
	}

	public String getOrientacionFuncional() {
        return orientacionFuncional;
    }

    public void setOrientacionFuncional(String orientacionFuncional) {
        this.orientacionFuncional = orientacionFuncional;
    }

    public String getAmbitoCptE() {
        return ambitoCptE;
    }

    public void setAmbitoCptE(String ambitoCptE) {
        this.ambitoCptE = ambitoCptE;
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

    public String getMatriz() {
        return matriz;
    }

    public void setMatriz(String matriz) {
        this.matriz = matriz;
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
      
}

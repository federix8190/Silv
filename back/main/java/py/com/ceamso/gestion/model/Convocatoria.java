package py.com.ceamso.gestion.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import py.com.ceamso.base.WritableEntity;
import py.com.ceamso.reportes.model.CargoDisponibleView;

@Entity
@Table(name = "convocatoria")
public class Convocatoria extends WritableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "codigo_concurso")
    private Integer codigoConcurso;

    @Basic(optional = false)
    @Column(name = "publica")
    private boolean publica;
    
    @Basic(optional = false)
    @Column(name = "anho")
    private Integer anho;
    
    @Basic(optional = false)
    @Column(name = "mes")
    private Integer mes;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "inicio_vigencia")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date inicioVigencia;
    
    @Column(name = "fin_vigencia")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date finVigencia;

    /*@JoinColumn(name = "id_cargo", referencedColumnName = "id")
    @ManyToOne
    private CargoDisponible cargo;*/

    @Transient
    private boolean esInteresado;
    
    // Datos convocatoria
    
    @Column(name = "id_cargo")
    private Long idCargo;
    
    @Column(name = "id_cpt_ef")
    private Long idCptF;
    
    @Column(name = "den_cpt_ef")
    private String denCptF;
    
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "linea")
    private Long linea;

    @Column(name = "categoria")
    private String categoria;
    
    @Column(name = "presupuestado")    
    private BigDecimal presupuestado;
    
    @Column(name = "departamento")
    private String departamento;

    @Column(name = "programa")
    private String programa;
    
    @Column(name = "subprograma")
    private String subprograma;
    
    @Column(name = "descripcion_programa")
    private String descripcionPrograma;
    
    @Column(name = "descripcion_subprograma")
    private String descripcionSubprograma;
    
    @Column(name = "numero_tramo")
    private Long numeroTramo;
    
    @Column(name = "nivel_cpt")
    private Long nivel;
    
    @Column(name = "pdf_location")
    private String pdfLocation;

    public Convocatoria() {
    }    

    public Convocatoria(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoConcurso() {
        return codigoConcurso;
    }

    public void setCodigoConcurso(Integer codigoConcurso) {
        this.codigoConcurso = codigoConcurso;
    }

    public boolean isPublica() {
        return publica;
    }

    public void setPublica(boolean publica) {
        this.publica = publica;
    }

    /*public CargoDisponible getCargo() {
        return cargo;
    }

    public void setCargo(CargoDisponible cargo) {
        this.cargo = cargo;
    }*/

    public int getAnho() {
		return anho;
	}

	public void setAnho(int anho) {
		this.anho = anho;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public boolean isEsInteresado() {
        return esInteresado;
    }

    public void setEsInteresado(boolean esInteresado) {
        this.esInteresado = esInteresado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(Date inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public Date getFinVigencia() {
        return finVigencia;
    }

    public void setFinVigencia(Date finVigencia) {
        this.finVigencia = finVigencia;
    }        

    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getLinea() {
        return linea;
    }

    public void setLinea(Long linea) {
        this.linea = linea;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPresupuestado() {
        return presupuestado;
    }

    public void setPresupuestado(BigDecimal presupuestado) {
        this.presupuestado = presupuestado;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
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

    public Long getNumeroTramo() {
        return numeroTramo;
    }

    public void setNumeroTramo(Long numeroTramo) {
        this.numeroTramo = numeroTramo;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public String getDescripcionPrograma() {
        return descripcionPrograma;
    }

    public void setDescripcionPrograma(String descripcionPrograma) {
        this.descripcionPrograma = descripcionPrograma;
    }

    public String getDescripcionSubprograma() {
        return descripcionSubprograma;
    }

    public void setDescripcionSubprograma(String descripcionSubprograma) {
        this.descripcionSubprograma = descripcionSubprograma;
    }

    public String getPdfLocation() {
        return pdfLocation;
    }

    public void setPdfLocation(String pdfLocation) {
        this.pdfLocation = pdfLocation;
    }

	public Long getIdCptF() {
		return idCptF;
	}

	public void setIdCptF(Long idCptF) {
		this.idCptF = idCptF;
	}

	public void setAnho(Integer anho) {
		this.anho = anho;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public String getDenCptF() {
		return denCptF;
	}

	public void setDenCptF(String denCptF) {
		this.denCptF = denCptF;
	}
    
    
    
}

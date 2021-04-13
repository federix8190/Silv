package py.com.ceamso.reportes.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import py.com.ceamso.base.BaseModel;

@Entity
@Table(name = "v_cte_cargos_vacantes")
public class CargoVacante extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "linea")
    private Long linea;
    
    @Column(name = "anho")
    private Integer anho;
    
    @Column(name = "mes")
    private Integer mes;

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
    
    @Column(name = "tipo_presupuesto")
    private String tipoPresupuesto;
    
    @Column(name = "numero_tramo")
    private Long numeroTramo;
    
    @Column(name = "nivel_cpt")
    private Long nivel;
    
    @Column(name = "id_cpt")
    private Long idCpt;
    
    @Column(name = "id_cpt_ee")
    private Long idCptEe;

    @Column(name = "id_cpt_ef")
    private Long idCptEf;
    
    @Column(name = "cpt_ee")
    private String cptEe;

    @Column(name = "cpt_ef")
    private String cptEf;
    
    @Column(name = "descripcion_programa")
    private String descripcionPrograma;

    @Column(name = "descripcion_subprograma")
    private String descripcionSubprograma;
    
    @Column(name = "descripcion_tipo_presupuesto")
    private String descripcionTipoPresupuesto;
    
    @Column(name = "asignable")
    private Boolean asignable;
    
    public CargoVacante() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Long getIdCpt() {
        return idCpt;
    }

    public void setIdCpt(Long idCpt) {
        this.idCpt = idCpt;
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

    public String getCptEe() {
        return cptEe;
    }

    public void setCptEe(String cptEe) {
        this.cptEe = cptEe;
    }

    public String getCptEf() {
        return cptEf;
    }

    public void setCptEf(String cptEf) {
        this.cptEf = cptEf;
    }

    public Boolean isAsignable() {
        return asignable;
    }

    public void setAsignable(Boolean asignable) {
        this.asignable = asignable;
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

    public String getTipoPresupuesto() {
        return tipoPresupuesto;
    }

    public void setTipoPresupuesto(String tipoPresupuesto) {
        this.tipoPresupuesto = tipoPresupuesto;
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

    public String getDescripcionTipoPresupuesto() {
        return descripcionTipoPresupuesto;
    }

    public void setDescripcionTipoPresupuesto(String descripcionTipoPresupuesto) {
        this.descripcionTipoPresupuesto = descripcionTipoPresupuesto;
    }

    
    public void setLinkCandidatos(String linkCandidatos) {
    }
    
    public String getLinkCandidatos() {
        StringBuilder sb = new StringBuilder();
        sb.append("anho=");
        sb.append(getAnho());
        sb.append("&mes=");
        sb.append(getMes());
        sb.append("&numeroTramo=");
        sb.append(getNumeroTramo());
        if (getIdCpt() != null) {
            sb.append("&idCpt=");
            sb.append(getIdCpt());
        }
        if (getIdCptEe() != null) {
            sb.append("&idCptEe=");
            sb.append(getIdCptEe());
        }
        if (getIdCptEf() != null) {
            sb.append("&idCptEf=");
            sb.append(getIdCptEf());
        }
        return sb.toString();
    }
 
}

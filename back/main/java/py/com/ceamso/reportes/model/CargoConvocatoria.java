package py.com.ceamso.reportes.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_cte_cargo_convocatoria")
public class CargoConvocatoria  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "linea")
    private Long linea;
    
    @Column(name = "anho")
    private int anho;
    
    @Column(name = "mes")
    private int mes;

    @Column(name = "descripcion_subprograma")
    private String descripcionSubprograma;

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
    
    @Column(name = "numero_tramo")
    private Long numeroTramo;
    
    @Column(name = "nivel_cpt")
    private Long nivel;
    
    @Column(name = "id_cpt_ee")
    private Long idCptEe;

    @Column(name = "id_cpt_ef")
    private Long idCptEf;
    
    @Column(name = "cpt_ee")
    private String cptEe;

    @Column(name = "cpt_ef")
    private String cptEf;
    
    public CargoConvocatoria() {
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

    public String getDescripcionSubprograma() {
        return descripcionSubprograma;
    }

    public void setDescripcionSubprograma(String descripcionSubprograma) {
        this.descripcionSubprograma = descripcionSubprograma;
    }    
 
}

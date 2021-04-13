package py.com.ceamso.reportes.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import py.com.ceamso.base.BaseModel;

@Entity
@Table(name = "v_cte_cargos_no_asignados")
public class CargoNoAsignado extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "linea")
    private Long linea;
    
    @Column(name = "categoria")
    private String categoria;
    
    @Column(name = "anho")
    private Integer anho;
    
    @Column(name = "mes")
    private Integer mes;

//    @Column(name = "circunscripcion")
//    private String circunscripcion;

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

    @Transient
    private String direccion;

    @Column(name = "numero_tramo")
    private Long numeroTramo;
    
    @Column(name = "descrip_programa_presup")
    private String descripcionPrograma;

    @Column(name = "descrip_subprograma_presup")
    private String descripcionSubprograma;
    
    @Column(name = "descrip_tipo_presupuesto")
    private String descripcionTipoPresupuesto;

    //@Column(name = "nivel_cpt")
    //private Long nivel;
    
    @Column(name = "disponible")
    private Boolean disponible;

    public CargoNoAsignado() {
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

    public BigDecimal getPresupuestado() {
        return presupuestado;
    }

    public void setPresupuestado(BigDecimal presupuestado) {
        this.presupuestado = presupuestado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getNumeroTramo() {
        return numeroTramo;
    }

    public void setNumeroTramo(Long numeroTramo) {
        this.numeroTramo = numeroTramo;
    }

    /*public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }*/

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
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

//    public String getCircunscripcion() {
//        return circunscripcion;
//    }
//
//    public void setCircunscripcion(String circunscripcion) {
//        this.circunscripcion = circunscripcion;
//    }

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
    
    

}

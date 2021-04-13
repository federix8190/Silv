package py.com.ceamso.administracion.dto;

import java.io.Serializable;

import py.com.ceamso.administracion.model.Cpt;

public class ClasificadorPuestoTrabajoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long nivel;
    private Integer subNivel;
    private Integer numeroGasto;
    private Boolean tituloUnidad;
    private String denominacion;

    public ClasificadorPuestoTrabajoDto() {
    }

    public ClasificadorPuestoTrabajoDto(Cpt datos) {
        this.id = datos.getId();
        this.nivel = datos.getNivel();
        this.subNivel = datos.getSubNivel();
        this.numeroGasto = datos.getNumeroGasto();
        this.tituloUnidad = datos.getTituloUnidad();
        this.denominacion = datos.getDenominacion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public Integer getSubNivel() {
        return subNivel;
    }

    public void setSubNivel(Integer subNivel) {
        this.subNivel = subNivel;
    }

    public Integer getNumeroGasto() {
        return numeroGasto;
    }

    public void setNumeroGasto(Integer numeroGasto) {
        this.numeroGasto = numeroGasto;
    }

    public Boolean getTituloUnidad() {
        return tituloUnidad;
    }

    public void setTituloUnidad(Boolean tituloUnidad) {
        this.tituloUnidad = tituloUnidad;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

}

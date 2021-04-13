package py.com.ceamso.administracion.dto;

import py.com.ceamso.administracion.model.CptE;
import py.com.ceamso.administracion.model.Ambito;

public class ClasificadorPuestoTrabajoEscalafonarioDto {

    private Long id;
    private String nro;
    private ClasificadorPuestoTrabajoDto cpt;
    private Long nivelCpt;
    private String ambito;
    private String nivel;
    private String categoria;
    private String den;

    public ClasificadorPuestoTrabajoEscalafonarioDto() {
    }

    public ClasificadorPuestoTrabajoEscalafonarioDto(CptE datos) {
        this.id = datos.getId();
        this.nro = datos.getNumero();
        if (datos.getCpt() != null) {
            this.cpt = new ClasificadorPuestoTrabajoDto(datos.getCpt());
            this.nivelCpt = datos.getCpt().getNivel();
        }
        this.ambito = datos.getAmbito().getNombre();
        this.nivel = datos.getNivel();
        //this.categoria = datos.getCategoria();
        this.den = datos.getDenominacion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public ClasificadorPuestoTrabajoDto getCpt() {
        return cpt;
    }

    public void setCpt(ClasificadorPuestoTrabajoDto cpt) {
        this.cpt = cpt;
    }

    public Long getNivelCpt() {
        return nivelCpt;
    }

    public void setNivelCpt(Long nivelCpt) {
        this.nivelCpt = nivelCpt;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDen() {
        return den;
    }

    public void setDen(String den) {
        this.den = den;
    }

}

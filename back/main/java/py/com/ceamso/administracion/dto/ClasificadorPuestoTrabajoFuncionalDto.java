package py.com.ceamso.administracion.dto;

import py.com.ceamso.administracion.model.CptF;

public class ClasificadorPuestoTrabajoFuncionalDto {

    private Long id;
    private String nro;
    private ClasificadorPuestoTrabajoDto cpt;
    private Long nivelCpt;
    private String ambito;
    private String codProceso;
    private String orientacionFunc;
    private String den;

    public ClasificadorPuestoTrabajoFuncionalDto() {
    }

    public ClasificadorPuestoTrabajoFuncionalDto(CptF datos) {
        this.id = datos.getId();
        this.nro = datos.getNro();
        if (datos.getCpt() != null) {
            this.cpt = new ClasificadorPuestoTrabajoDto(datos.getCpt());
            this.nivelCpt = datos.getCpt().getNivel();
        }
        this.ambito = datos.getAmbito().getNombre();
        this.codProceso = datos.getCodProceso();
        this.orientacionFunc = datos.getOrientacionFunc().getNombre();
        this.den = datos.getDen();
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

    public String getCodProceso() {
        return codProceso;
    }

    public void setCodProceso(String codProceso) {
        this.codProceso = codProceso;
    }

    public String getOrientacionFunc() {
        return orientacionFunc;
    }

    public void setOrientacionFunc(String orientacionFunc) {
        this.orientacionFunc = orientacionFunc;
    }

    public String getDen() {
        return den;
    }

    public void setDen(String den) {
        this.den = den;
    }

}

package py.com.ceamso.administracion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import py.com.ceamso.base.WritableEntity;

@Entity
@Table(name = "configuracion_cts")
public class ConfiguracionCts extends WritableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "numero_tramo")
    private Long numeroTramo;

    @Column(name = "peso")
    private Double peso;
    /*
    @JoinColumn(name = "numero_tramo", referencedColumnName = "numero_tramo", insertable=false, updatable=false)
    @ManyToOne
    private Cts cts;
*/
    public ConfiguracionCts() {
    }

    public ConfiguracionCts(Long numeroTramo, Double peso) {
        this.numeroTramo = numeroTramo;
        this.peso = peso;
    }

    public Long getNumeroTramo() {
        return numeroTramo;
    }

    public void setNumeroTramo(Long numeroTramo) {
        this.numeroTramo = numeroTramo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }
/*
    public Cts getCts() {
        return cts;
    }

    public void setCts(Cts cts) {
        this.cts = cts;
    }
        */
}

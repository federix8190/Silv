package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class CptTramosPK implements Serializable {
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel_cpt")
    private long nivelCpt;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_tramo")
    private long numeroTramo;
    
    @Column(name = "anho")
    private int anho;
    
    @Column(name = "mes")
    private int mes;

    public CptTramosPK() {        
    }
    
    public CptTramosPK(long nivelCpt, long numeroTramo, int anho, int mes) {
        this.nivelCpt = nivelCpt;
        this.numeroTramo = numeroTramo;
        this.anho = anho;
        this.mes = mes;
    }

    public long getNivelCpt() {
        return nivelCpt;
    }

    public void setNivelCpt(long nivelCpt) {
        this.nivelCpt = nivelCpt;
    }

    public long getNumeroTramo() {
        return numeroTramo;
    }

    public void setNumeroTramo(long numeroTramo) {
        this.numeroTramo = numeroTramo;
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
    
}

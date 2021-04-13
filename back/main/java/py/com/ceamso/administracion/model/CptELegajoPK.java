package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class CptELegajoPK implements Serializable {
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cpt_ee")
    private long idCptE;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "cedula_identidad")
    private long cedulaIdentidad;
    
    @Column(name = "anho")
    private int anho;
    
    @Column(name = "mes")
    private int mes;

    public CptELegajoPK() {        
    }
    
    public CptELegajoPK(long idCptE, long cedulaIdentidad, int anho, int mes) {
        this.idCptE = idCptE;
        this.cedulaIdentidad = cedulaIdentidad;
        this.anho = anho;
        this.mes = mes;
    }

    public long getIdCptE() {
        return idCptE;
    }

    public void setIdCptE(long idCptE) {
        this.idCptE = idCptE;
    }

    public long getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(long cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
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

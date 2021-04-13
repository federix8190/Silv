/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author daniel
 */
@Embeddable
public class CptLegajoPK implements Serializable {
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cpt")
    private long idCpt;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "cedula_identidad")
    private long cedulaIdentidad;
    
    @Column(name = "anho")
    private int anho;
    
    @Column(name = "mes")
    private int mes;

    public CptLegajoPK() {        
    }
    
    public CptLegajoPK(long idCpt, long cedulaIdentidad, int anho, int mes) {
        this.idCpt = idCpt;
        this.cedulaIdentidad = cedulaIdentidad;
        this.anho = anho;
        this.mes = mes;
    }

    public long getIdCpt() {
        return idCpt;
    }

    public void setIdCpt(long idCpt) {
        this.idCpt = idCpt;
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

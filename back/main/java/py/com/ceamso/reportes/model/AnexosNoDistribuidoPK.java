/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 *
 * @author daniel.rojas
 */
@Embeddable
public class AnexosNoDistribuidoPK implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "anho")
    private Integer anho;

    @Column(name = "mes")
    private Integer mes;
    
    @Column(name = "anho_sf")
    private Integer anhoSF;

    @Column(name = "mes_sf")
    private Integer mesSF;
    
    @Column(name = "cedula_identidad")
    private Integer cedulaIdentidad;
    
    public AnexosNoDistribuidoPK(){
        
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

    public Integer getAnhoSF() {
        return anhoSF;
    }

    public void setAnhoSF(Integer anhoSF) {
        this.anhoSF = anhoSF;
    }

    public Integer getMesSF() {
        return mesSF;
    }

    public void setMesSF(Integer mesSF) {
        this.mesSF = mesSF;
    }

    public Integer getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(Integer cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }
    
}

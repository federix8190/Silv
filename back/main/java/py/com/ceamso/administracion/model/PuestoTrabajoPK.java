/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 *
 * @author daniel
 */
@Embeddable
public class PuestoTrabajoPK implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "cedula_identidad")
    private Integer cedulaIdentidad;
    
    @Column(name = "anho")
    private Integer anho;

    @Column(name = "mes")
    private Integer mes;

    public PuestoTrabajoPK(){
        
    }
    
    public PuestoTrabajoPK(Integer cedulaIdentidad, Integer anho, Integer mes) {
        this.cedulaIdentidad = cedulaIdentidad;
        this.anho = anho;
        this.mes = mes;
    }
    
    public Integer getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(Integer cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
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
    
    
    
}

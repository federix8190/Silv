/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "v_cte_distritos")
public class Distrito implements Serializable {
    
    @Id
    @Column(name = "codigo_distrito")
    private Long codigo; 
    
    @Column(name = "codigo_depto")
    private Long codigoDepto; 
    
    @Column(name = "nombre_distrito")
    private String nombre;

    public Distrito() { 
    }
    
    public Distrito(Long codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Long getCodigoDepto() {
        return codigoDepto;
    }

    public void setCodigoDepto(Long codigoDepto) {
        this.codigoDepto = codigoDepto;
    }
    
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

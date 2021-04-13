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
@Table(name = "v_cte_departamentos")
public class Departamento implements Serializable {
    
    @Id
    @Column(name = "codigo_depto")
    private Long codigo; 
    
    @Column(name = "nombre_depto")
    private String nombre;

    public Departamento() {  
    }
    
    public Departamento(Long codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "v_idiomas")
public class Idiomas implements Serializable{
    
    @EmbeddedId
    private IdiomasPK pk;
    
    @Column(name = "desc_habla")
    private String descripcionHabla;
    
    @Column(name = "desc_lee")
    private String descripcionLee;
    
    @Column(name = "desc_escribe")
    private String descripcionEscribe;
    
    public Idiomas(){
        
    }

    public IdiomasPK getPk() {
        return pk;
    }

    public void setPk(IdiomasPK pk) {
        this.pk = pk;
    }

    public String getDescripcionHabla() {
        return descripcionHabla;
    }

    public void setDescripcionHabla(String descripcionHabla) {
        this.descripcionHabla = descripcionHabla;
    }

    public String getDescripcionLee() {
        return descripcionLee;
    }

    public void setDescripcionLee(String descripcionLee) {
        this.descripcionLee = descripcionLee;
    }

    public String getDescripcionEscribe() {
        return descripcionEscribe;
    }

    public void setDescripcionEscribe(String descripcionEscribe) {
        this.descripcionEscribe = descripcionEscribe;
    }
    
    public Long getIdFuncionario(){
        return pk.getIdFuncionario();
    }
    
    public String getCedulaFuncionario() {
        return pk.getCedulaFuncionario();
    }
    
    public String getDescripcionIdioma(){
        return pk.getDescripcionIdioma();
    }
}

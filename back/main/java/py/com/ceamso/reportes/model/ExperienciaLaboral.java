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
@Table(name = "v_experiencia_laboral")
public class ExperienciaLaboral implements Serializable{
    
    @EmbeddedId
    private ExperienciaLaboralPK pk;
	
    @Column(name = "actividades")
    private String actividades;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;
    
    public ExperienciaLaboral(){
        
    }

    public ExperienciaLaboralPK getPk() {
        return pk;
    }

    public void setPk(ExperienciaLaboralPK pk) {
        this.pk = pk;
    }

    public String getActividades() {
        return actividades;
    }

    public void setActividades(String actividades) {
        this.actividades = actividades;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getCedulaFuncionario(){
        return pk.getCedulaFuncionario();
    }
    
    public String getInstitucion(){
        return pk.getInstitucion();
    }
    
    public String getFuncion(){
        return pk.getFuncion();
    }
    
    public String getInicio(){
        return pk.getInicio();
    }
    
    public String getFin(){
        return pk.getFin();
    }

}

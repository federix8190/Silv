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
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "v_estudios_concluidos")
public class EstudiosConcluidos implements Serializable{
    
    @EmbeddedId
    private EstudiosConcluidosPK pk;
    
    @Column(name = "nombre_funcionario")
    private String nombreFuncionario;
    
    @Column(name = "descripcion_carrera")
    private String descripcionCarrera;
    
    @Column(name = "descripcion_profesion")
    private String descripcionProfesion;
    
    @Column(name = "observacion")
    private String observacion;

    public EstudiosConcluidos(){
        
    }
    
    public Long getIdFuncionario() {
        return pk.getIdFuncionario();
    }

    public String getCedulaFuncionario() {
        return pk.getCedulaFuncionario();
    }

    public String getNombreFuncionario() {
        return nombreFuncionario;
    }

    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

    public String getDescripcionGrado() {
        return pk.getDescripcionGrado();
    }

    public String getDescripcionCarrera() {
        return descripcionCarrera;
    }

    public void setDescripcionCarrera(String descripcionCarrera) {
        this.descripcionCarrera = descripcionCarrera;
    }

    public String getDescripcionProfesion() {
        return descripcionProfesion;
    }

    public void setDescripcionProfesion(String descripcionProfesion) {
        this.descripcionProfesion = descripcionProfesion;
    }

    public String getNombreInstitucion() {
        return pk.getNombreInstitucion();
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
}

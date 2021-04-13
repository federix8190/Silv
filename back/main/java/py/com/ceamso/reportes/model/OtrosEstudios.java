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
@Table(name = "v_otros_estudios")
public class OtrosEstudios implements Serializable{
    
    @EmbeddedId
    private OtrosEstudiosPK pk;
    
    @Column(name = "cursando")
    private String cursando;
    
    @Column(name = "fecha_finalizacion")
    private String fechaFinalizacion;
    
    @Column(name = "descripcion_anhos")
    private String descripcionAnhos;
    
    @Column(name = "nombre_funcionario")
    private String nombreFuncionario;
    
    @Column(name = "descripcion_profesion")
    private String descripcionProfesion;
    
    @Column(name = "observacion")
    private String observacion;
    
    public OtrosEstudios(){
        
    }

    public OtrosEstudiosPK getPk() {
        return pk;
    }

    public void setPk(OtrosEstudiosPK pk) {
        this.pk = pk;
    }

    public String getCursando() {
        return cursando;
    }

    public void setCursando(String cursando) {
        this.cursando = cursando;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getDescripcionAnhos() {
        return descripcionAnhos;
    }

    public void setDescripcionAnhos(String descripcionAnhos) {
        this.descripcionAnhos = descripcionAnhos;
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
        return pk.getDescripcionCarrera();
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

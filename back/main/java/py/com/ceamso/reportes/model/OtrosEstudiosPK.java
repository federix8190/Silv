/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author daniel
 */
@Embeddable
public class OtrosEstudiosPK implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "descripcion_carrera")
    private String descripcionCarrera;
    
    @Column(name = "id_funcionario")
    private Long idFuncionario;
    
    @Column(name = "cedula_funcionario")
    private String cedulaFuncionario;
   
    @Column(name = "descripcion_grado")
    private String descripcionGrado;
    
    @Column(name = "nombre_institucion")
    private String nombreInstitucion;
    
    public OtrosEstudiosPK(){
        
    }

    public String getDescripcionCarrera() {
        return descripcionCarrera;
    }

    public void setDescripcionCarrera(String descripcionCarrera) {
        this.descripcionCarrera = descripcionCarrera;
    }

    public Long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getCedulaFuncionario() {
        return cedulaFuncionario;
    }

    public void setCedulaFuncionario(String cedulaFuncionario) {
        this.cedulaFuncionario = cedulaFuncionario;
    }

    public String getDescripcionGrado() {
        return descripcionGrado;
    }

    public void setDescripcionGrado(String descripcionGrado) {
        this.descripcionGrado = descripcionGrado;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }
    
}

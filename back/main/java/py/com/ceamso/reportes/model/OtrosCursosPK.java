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
public class OtrosCursosPK implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "id_funcionario")
    private Long idFuncionario;
    
    @Column(name = "cedula_funcionario")
    private String cedulaFuncionario;
   
    @Column(name = "descripcion_tipo")
    private String descripcionTipo;
    
    @Column(name = "nombre_institucion")
    private String nombreInstitucion;

    public OtrosCursosPK(){
        
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

    public String getDescripcionTipo() {
        return descripcionTipo;
    }

    public void setDescripcionGrado(String descripcionTipoo) {
        this.descripcionTipo = descripcionTipo;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }
    
    
}

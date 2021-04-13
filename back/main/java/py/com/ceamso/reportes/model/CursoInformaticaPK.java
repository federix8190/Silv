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
public class CursoInformaticaPK implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "id_funcionario")
    private Long idFuncionario;
    
    @Column(name = "cedula_funcionario")
    private String cedulaFuncionario;
   
    @Column(name = "descripcion_curso")
    private String descripcionCurso;
    
    @Column(name = "nombre_institucion")
    private String nombreInstitucion;
    
    public CursoInformaticaPK(){
        
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

    public String getDescripcionCurso() {
        return descripcionCurso;
    }

    public void setDescripcionCurso(String descripcionCurso) {
        this.descripcionCurso = descripcionCurso;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }
    
}

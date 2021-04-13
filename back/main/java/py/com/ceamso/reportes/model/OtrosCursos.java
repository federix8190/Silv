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
@Table(name = "v_otros_cursos")
public class OtrosCursos implements Serializable {
    
    @EmbeddedId
    private OtrosCursosPK pk;
    
    @Column(name = "descripcion_curso")
    private String descripcionCurso;
    
    @Column(name = "fecha_finalizacion")
    private String fechaFinalizacion;
    
    @Column(name = "observacion")
    private String observacion;
    
    public OtrosCursos(){
        
    }

    public OtrosCursosPK getPk() {
        return pk;
    }

    public void setPk(OtrosCursosPK pk) {
        this.pk = pk;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDescripcionCurso() {
        return descripcionCurso;
    }

    public void setDescripcionCurso(String descripcionCurso) {
        this.descripcionCurso = descripcionCurso;
    }
    
    public Long getIdFuncionario() {
        return pk.getIdFuncionario();
    }

    public String getCedulaFuncionario() {
        return pk.getCedulaFuncionario();
    }
    
    public String getDescripcionTipo() {
        return pk.getDescripcionTipo();
    }

    public String getNombreInstitucion() {
        return pk.getNombreInstitucion();
    }
}
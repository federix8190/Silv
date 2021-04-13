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
@Table(name = "v_curso_informatica")
public class CursoInformatica implements Serializable{
    
    @EmbeddedId
    private CursoInformaticaPK pk;
    
    @Column(name = "fecha_finalizacion")
    private String fechaFinalizacion;
    
    @Column(name = "observacion")
    private String observacion;
    
    public CursoInformatica(){
        
    }

    public CursoInformaticaPK getPk() {
        return pk;
    }

    public void setPk(CursoInformaticaPK pk) {
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
    
     public Long getIdFuncionario() {
        return pk.getIdFuncionario();
    }

    public String getCedulaFuncionario() {
        return pk.getCedulaFuncionario();
    }
    
    public String getDescripcionCurso(){
        return pk.getDescripcionCurso();
    }
    
    public String getNombreInstitucion() {
        return pk.getNombreInstitucion();
    }
}

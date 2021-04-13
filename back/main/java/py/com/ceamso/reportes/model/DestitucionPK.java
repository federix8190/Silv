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
public class DestitucionPK implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "id_funcionario")
    private Long idFuncionario;
    
    @Column(name = "cedula_funcionario")
    private String cedulaFuncionario;
    
    @Column(name = "disposicion")
    private String disposicion;
    
    @Column(name = "fecha_disposicion")
    private String fechaDisposicion;
    
    @Column(name = "detalle")
    private String detalle;
    
    public DestitucionPK(){
        
    }

    public Long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getDisposicion() {
        return disposicion;
    }

    public void setDisposicion(String disposicion) {
        this.disposicion = disposicion;
    }

    public String getFechaDisposicion() {
        return fechaDisposicion;
    }

    public void setFechaDisposicion(String fechaDisposicion) {
        this.fechaDisposicion = fechaDisposicion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getCedulaFuncionario() {
        return cedulaFuncionario;
    }

    public void setCedulaFuncionario(String cedulaFuncionario) {
        this.cedulaFuncionario = cedulaFuncionario;
    }
    
}

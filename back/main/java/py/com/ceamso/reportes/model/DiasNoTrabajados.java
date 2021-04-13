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
@Table(name = "v_dias_no_trabajados")
public class DiasNoTrabajados implements Serializable{
    
    @EmbeddedId
    private DiasNoTrabajadosPK pk;
    
    @Column(name = "tipo")
    private String tipo;

    @Column(name = "gestion")
    private Long gestion;

    @Column(name = "id_mes")
    private Integer idMes;

    @Column(name = "mes")
    private String mes;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "fecha_proc")
    private String fechaProc;

    @Column(name = "dias_trabajados")
    private Integer diasTrabajados;
    
    public DiasNoTrabajados(){
        
    }

    public DiasNoTrabajadosPK getPk() {
        return pk;
    }

    public void setPk(DiasNoTrabajadosPK pk) {
        this.pk = pk;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getGestion() {
        return gestion;
    }

    public void setGestion(Long gestion) {
        this.gestion = gestion;
    }

    public Integer getIdMes() {
        return idMes;
    }

    public void setIdMes(Integer idMes) {
        this.idMes = idMes;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaProc() {
        return fechaProc;
    }

    public void setFechaProc(String fechaProc) {
        this.fechaProc = fechaProc;
    }

    public Integer getDiasTrabajados() {
        return diasTrabajados;
    }

    public void setDiasTrabajados(Integer diasTrabajados) {
        this.diasTrabajados = diasTrabajados;
    }
    
    public Long getIdFuncionario() {
        return pk.getIdFuncionario();
    }

    public String getCedulaFuncionario() {
        return pk.getCedulaFuncionario();
    }

    public Integer getNroDocumento() {
        return pk.getNroDocumento();
    }
}

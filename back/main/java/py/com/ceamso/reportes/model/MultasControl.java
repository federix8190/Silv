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
@Table(name = "v_multas_control")
public class MultasControl implements Serializable{
    
    @EmbeddedId
    private MultasControlPK pk;
    
    @Column(name = "tipo")
    private String tipo;

    @Column(name = "fec_resol")
    private String fechaResolucion;

    @Column(name = "tipo_multa")
    private String tipoMulta;

    @Column(name = "gestion")
    private Long gestion;

    @Column(name = "id_mes")
    private Integer idMes;

    @Column(name = "mes")
    private String mes;

    @Column(name = "cant_dias_resolucion")
    private Integer cantDiasResolucion;
    
    public MultasControl(){
        
    }

    public MultasControlPK getPk() {
        return pk;
    }

    public void setPk(MultasControlPK pk) {
        this.pk = pk;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(String fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public String getTipoMulta() {
        return tipoMulta;
    }

    public void setTipoMulta(String tipoMulta) {
        this.tipoMulta = tipoMulta;
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

    public Integer getCantDiasResolucion() {
        return cantDiasResolucion;
    }

    public void setCantDiasResolucion(Integer cantDiasResolucion) {
        this.cantDiasResolucion = cantDiasResolucion;
    }
    
    public Long getIdFuncionario() {
        return pk.getIdFuncionario();
    }

    public String getCedulaFuncionario() {
        return pk.getCedulaFuncionario();
    }

    public Integer getResolucionNro() {
        return pk.getResolucionNro();
    }
}

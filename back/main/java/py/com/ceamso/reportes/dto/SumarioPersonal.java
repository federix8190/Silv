/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.dto;

import java.sql.Timestamp;

/**
 *
 * @author pc17
 */
public class SumarioPersonal {
    private String sumario; 
    private String nroDocumento; 
    private Timestamp fechaDocumento; 
    private String tipoSumario; 
     
    public SumarioPersonal() { 
    } 
    public SumarioPersonal(Object[] datos) { 
        this.sumario = (String) datos[0]; 
        this.nroDocumento = (String) datos[1]; 
        this.fechaDocumento = (Timestamp) datos[2]; 
        this.tipoSumario = (String) datos[3]; 
    } 
    public SumarioPersonal(String sumario, String nroDocumento, Timestamp fechaDocumento, String tipoSumario) { 
        this.sumario = sumario; 
        this.nroDocumento = nroDocumento; 
        this.fechaDocumento = fechaDocumento; 
        this.tipoSumario = tipoSumario; 
    } 

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public void setFechaDocumento(Timestamp fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public void setTipoSumario(String tipoSumario) {
        this.tipoSumario = tipoSumario;
    }

    public String getSumario() {
        return sumario;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public Timestamp getFechaDocumento() {
        return fechaDocumento;
    }

    public String getTipoSumario() {
        return tipoSumario;
    }

}

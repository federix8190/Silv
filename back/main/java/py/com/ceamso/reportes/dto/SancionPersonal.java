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
public class SancionPersonal { 
    private String sancion; 
    private String numeroDocumento; 
    private Timestamp fechaDocumento; 
    private String tipoSancion; 
     
    public SancionPersonal() { 
    } 
    public SancionPersonal(Object[] datos) { 
        this.sancion = (String) datos[0]; 
        this.numeroDocumento = (String) datos[1]; 
        this.fechaDocumento = (Timestamp) datos[2]; 
        this.tipoSancion = (String) datos[3]; 
    } 
    public SancionPersonal(String sancion, String numeroDocumento, Timestamp fechaDocumento, String tipoSancion) { 
        this.sancion = sancion; 
        this.numeroDocumento = numeroDocumento; 
        this.fechaDocumento = fechaDocumento; 
        this.tipoSancion = tipoSancion; 
    } 
 
    public void setSancion(String sancion) { 
        this.sancion = sancion; 
    } 
 
    public void setNumeroDocumento(String numeroDocumento) { 
        this.numeroDocumento = numeroDocumento; 
    } 
 
    public void setFechaDocumento(Timestamp fechaDocumento) { 
        this.fechaDocumento = fechaDocumento; 
    } 
 
    public void setTipoSancion(String tipoSancion) { 
        this.tipoSancion = tipoSancion; 
    } 
 
    public String getSancion() { 
        return sancion; 
    } 
 
    public String getNumeroDocumento() { 
        return numeroDocumento; 
    } 
 
    public Timestamp getFechaDocumento() { 
        return fechaDocumento; 
    } 
 
    public String getTipoSancion() { 
        return tipoSancion; 
    } 
 
} 
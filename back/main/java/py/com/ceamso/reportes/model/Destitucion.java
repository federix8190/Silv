/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "v_destitucion")
public class Destitucion implements Serializable{
    
    @EmbeddedId
    DestitucionPK pk;
    
    public Destitucion(){
        
    }

    public Destitucion(DestitucionPK pk) {
        this.pk = pk;
    }
    
    public Long getIdFuncionario(){
        return pk.getIdFuncionario();
    }
    
    public String getCedulaFuncionario(){
        return pk.getCedulaFuncionario();
    }
    
    public String getDetalle(){
        return pk.getDetalle();
    }
    
    public String getDisposicion(){
        return pk.getDisposicion();
    }
    
    public String getFechaDisposicion(){
        return pk.getFechaDisposicion();
    }
    
}

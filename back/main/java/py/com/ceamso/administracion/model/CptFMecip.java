/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import py.com.ceamso.base.WritableEntity;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "cpt_ef_mecip")
public class CptFMecip extends WritableEntity implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CptFMecipPK pk;
    
    public CptFMecip(){
        
    }
    
    public CptFMecip(CptFMecipPK pk){
        this.pk = pk;
    }

    public CptFMecipPK getPk() {
        return pk;
    }

    public void setPk(CptFMecipPK pk) {
        this.pk = pk;
    }
    
    public Long getIdCptF(){
        return pk.getIdCptF();
    }
    
    public Long getIdMecip(){
        return pk.getIdMecip();
    }
}
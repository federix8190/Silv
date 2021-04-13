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
@Table(name = "cpt_ee_categoria")
public class CptECategoria extends WritableEntity implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CptECategoriaPK pk;

    public CptECategoria(){
        
    }
    
    public CptECategoria(CptECategoriaPK pk) {
        this.pk = pk;
    }

    public CptECategoriaPK getPk() {
        return pk;
    }

    public void setPk(CptECategoriaPK pk) {
        this.pk = pk;
    }
    
    public Long getIdCptE(){
        return pk.getIdCptE();
    }
    
    public String getIdCategoria(){
        return pk.getIdCategoria();
    }
}

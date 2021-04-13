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

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "cpt_legajos")
public class CptLegajo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private CptLegajoPK pk;

    public CptLegajo() {
    }
    
    public CptLegajo(CptLegajoPK pk) {
        this.pk = pk;
    }

    public CptLegajoPK getPk() {
        return pk;
    }

    public void setPk(CptLegajoPK pk) {
        this.pk = pk;
    }
    
}

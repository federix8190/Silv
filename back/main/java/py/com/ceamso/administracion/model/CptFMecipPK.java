/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author daniel
 */
@Embeddable
public class CptFMecipPK implements Serializable {
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cpt_ef")
    private long idCptF;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_mecip")
    private long idMecip;
    
    public CptFMecipPK(){
        
    }

    public CptFMecipPK(long idCptF, long idMecip) {
        this.idCptF = idCptF;
        this.idMecip = idMecip;
    }

    public long getIdCptF() {
        return idCptF;
    }

    public void setIdCptF(long idCptF) {
        this.idCptF = idCptF;
    }

    public long getIdMecip() {
        return idMecip;
    }

    public void setIdMecip(long idMecip) {
        this.idMecip = idMecip;
    }
    
}

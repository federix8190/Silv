/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author daniel
 */
@Embeddable
public class CptECategoriaPK implements Serializable{
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cpt_ee")
    private long idCptE;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_categoria")
    private String idCategoria;
    
    public CptECategoriaPK(){
        
    }

    public CptECategoriaPK(long idCptE, String idCategoria) {
        this.idCptE = idCptE;
        this.idCategoria = idCategoria;
    }

    public long getIdCptE() {
        return idCptE;
    }

    public void setIdCptF(long idCptE) {
        this.idCptE = idCptE;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }
    
}

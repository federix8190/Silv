package py.com.ceamso.seguridad.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author konecta
 */
@Embeddable
public class RolPermisoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_permiso")
    private int idPermiso;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_rol")
    private long idRol;

    public RolPermisoPK() {
    }

    public RolPermisoPK(int idPermiso, long idRol) {
        this.idPermiso = idPermiso;
        this.idRol = idRol;
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public long getIdRol() {
        return idRol;
    }

    public void setIdRol(long idRol) {
        this.idRol = idRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPermiso;
        hash += (int) idRol;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolPermisoPK)) {
            return false;
        }
        RolPermisoPK other = (RolPermisoPK) object;
        if (this.idPermiso != other.idPermiso) {
            return false;
        }
        if (this.idRol != other.idRol) {
            return false;
        }
        return true;
    }
    
}

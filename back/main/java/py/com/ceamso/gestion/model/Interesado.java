package py.com.ceamso.gestion.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import py.com.ceamso.seguridad.model.Usuario;

@Entity
@Table(name = "interesado")
public class Interesado implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private InteresadoPK pk;
    
    /*@Basic(optional = false)
    @Column(name = "id_usuario_interesado")
    private long idUsuarioInteresado;*/
    
    @JoinColumn(name = "id_usuario_interesado", referencedColumnName = "id", insertable=false, updatable=false)
    @ManyToOne(optional = false)
    private Usuario usuarioInteresado;
    
    /*@Basic(optional = false)
    @Column(name = "id_convocatoria")
    private long idConvocatoria;*/
    
    @Basic(optional = false)
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    public Interesado() {
    }

    public Interesado(InteresadoPK pk) {
        this.pk = pk;
    }

    /*public Interesado(Integer id, long idUsuarioInteresado, long idConvocatoria, Date fechaCreacion) {
        this.id = id;
        this.idUsuarioInteresado = idUsuarioInteresado;
        this.idConvocatoria = idConvocatoria;
        this.fechaCreacion = fechaCreacion;
    }*/

    public InteresadoPK getPk() {
        return pk;
    }

    public void setPk(InteresadoPK pk) {
        this.pk = pk;
    }

    public Usuario getUsuarioInteresado() {
        return usuarioInteresado;
    }

    public void setUsuarioInteresado(Usuario usuarioInteresado) {
        this.usuarioInteresado = usuarioInteresado;
    }

    /*public long getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(long idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }*/

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pk != null ? pk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Interesado)) {
            return false;
        }
        Interesado other = (Interesado) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

}

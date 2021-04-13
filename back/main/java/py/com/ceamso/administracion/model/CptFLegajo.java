package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cpt_ef_legajos")
public class CptFLegajo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private CptFLegajoPK pk;

    public CptFLegajo() {
    }
    
    public CptFLegajo(CptFLegajoPK pk) {
        this.pk = pk;
    }

    public CptFLegajoPK getPk() {
        return pk;
    }

    public void setPk(CptFLegajoPK pk) {
        this.pk = pk;
    }
    
}

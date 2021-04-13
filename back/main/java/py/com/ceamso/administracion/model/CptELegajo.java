package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cpt_ee_legajos")
public class CptELegajo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private CptELegajoPK pk;

    public CptELegajo() {
    }
    
    public CptELegajo(CptELegajoPK pk) {
        this.pk = pk;
    }

    public CptELegajoPK getPk() {
        return pk;
    }

    public void setPk(CptELegajoPK pk) {
        this.pk = pk;
    }
    
}

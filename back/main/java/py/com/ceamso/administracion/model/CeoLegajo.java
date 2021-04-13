package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "ceo_legajos")
public class CeoLegajo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private CeoLegajoPK pk;

    public CeoLegajo() {
    }
    
    public CeoLegajo(CeoLegajoPK pk) {
        this.pk = pk;
    }

    public CeoLegajoPK getPk() {
        return pk;
    }

    public void setPk(CeoLegajoPK pk) {
        this.pk = pk;
    }
    
}

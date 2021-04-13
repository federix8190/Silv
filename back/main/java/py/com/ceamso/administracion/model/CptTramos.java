package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cpt_tramos")
public class CptTramos implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private CptTramosPK pk;

    public CptTramos() {
    }
    
    public CptTramos(CptTramosPK pk) {
        this.pk = pk;
    }

    public CptTramosPK getPk() {
        return pk;
    }

    public void setPk(CptTramosPK pk) {
        this.pk = pk;
    }
    
}

package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LegajoCargoPK implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "cedula_identidad")
    private Long cedulaIdentidad;
    
    @Column(name = "id_cargo")
    private Long idCargo;
    
    public LegajoCargoPK() {        
    }

    public Long getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(Long cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }

    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }
    
}

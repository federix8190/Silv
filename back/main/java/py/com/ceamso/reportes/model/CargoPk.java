package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author konecta
 */
@Embeddable
public class CargoPk implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "cedula_identidad")
    private Long cedulaIdentidad;

    @Column(name = "descripcion")
    private String descripcion;
    
    public CargoPk() {
    }
    
    public CargoPk(Long cedulaIdentidad, String descripcion) {
        this.cedulaIdentidad = cedulaIdentidad;
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Long getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(Long cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }
    
}

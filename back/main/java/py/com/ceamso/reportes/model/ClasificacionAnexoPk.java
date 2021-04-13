package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ClasificacionAnexoPk implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "anho")
    private Integer anho;

    @Column(name = "mes")
    private Integer mes;
    
    @Column(name = "cedula_identidad")
    private Integer cedulaIdentidad;
    
    public ClasificacionAnexoPk() {
    }
    
    public ClasificacionAnexoPk(Integer anho, Integer mes, Integer cedulaIdentidad) {
        this.anho = anho;
        this.mes = mes;
        this.cedulaIdentidad = cedulaIdentidad;
    }

    public Integer getAnho() {
        return anho;
    }

    public void setAnho(Integer anho) {
        this.anho = anho;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(Integer cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }
    
}

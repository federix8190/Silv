package py.com.ceamso.administracion.view;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CptEELegajosViewPk implements Serializable {

    @Column(name = "cedula_identidad")
    private Long cedulaIdentidad;    
        
    @Column(name = "anho")
    private Integer anho;
    
    @Column(name = "mes")
    private Integer mes;
    
    public CptEELegajosViewPk() {
    }

    public Long getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(Long cedulaIdentidad) {
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
    
}


package py.com.ceamso.reportes.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;

/**
 *
 * @author konecta
 */
@Embeddable
public class CarreraAdministrativaPk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_funcionario")    
    private Integer idFuncionario;
    
    @Column(name = "nro_disposicion")    
    private String numeroDisposicion;
    
    @Column(name = "fecha_dispos")    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaDisposicion;
    
    public CarreraAdministrativaPk() {        
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNumeroDisposicion() {
        return numeroDisposicion;
    }

    public void setNumeroDisposicion(String numeroDisposicion) {
        this.numeroDisposicion = numeroDisposicion;
    }

    public Date getFechaDisposicion() {
        return fechaDisposicion;
    }

    public void setFechaDisposicion(Date fechaDisposicion) {
        this.fechaDisposicion = fechaDisposicion;
    }
        
}

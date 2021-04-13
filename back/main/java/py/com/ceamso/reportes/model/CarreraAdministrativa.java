package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "vista_carrera_administrativa")
public class CarreraAdministrativa implements Serializable {
    
    @EmbeddedId
    private CarreraAdministrativaPk pk;
    
    @Column(name = "tipo") 
    private String tipo;
    
    @Column(name = "nombre") 
    private String nombre;
        
    @Column(name = "cedula_funcionario") 
    private String cedulaIdentidad;
    
    @Column(name = "detalle") 
    private String detalle;
    
    @Column(name = "disposicion") 
    private String disposicion;
    
    @Column(name = "fecha_disposicion") 
    private String fechaDisposicion;
    
    public CarreraAdministrativa() {        
    }

    public CarreraAdministrativaPk getPk() {
        return pk;
    }

    public void setPk(CarreraAdministrativaPk pk) {
        this.pk = pk;
    }

    public String getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(String cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getDisposicion() {
        return disposicion;
    }

    public void setDisposicion(String disposicion) {
        this.disposicion = disposicion;
    }

    public String getFechaDisposicion() {
        return fechaDisposicion;
    }

    public void setFechaDisposicion(String fechaDisposicion) {
        this.fechaDisposicion = fechaDisposicion;
    }
        
}

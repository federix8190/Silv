package py.com.ceamso.reportes.dto;

import py.com.ceamso.reportes.model.CarreraAdministrativa;

/**
 *
 * @author konecta
 */
public class CarreraAdministrativaDto {

    private String tipo;
    private String nombre;
    private String cedulaIdentidad;
    private String detalle;
    private String disposicion;    
    private String fechaDisposicion;

    public CarreraAdministrativaDto(CarreraAdministrativa datos) {
        this.tipo = datos.getTipo();
        this.nombre = datos.getNombre();
        this.cedulaIdentidad = datos.getCedulaIdentidad();
        this.detalle = datos.getDetalle();
        this.disposicion = datos.getDisposicion();
        this.fechaDisposicion = datos.getFechaDisposicion().replace("/", "-");
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

    public String getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(String cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
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

package py.com.ceamso.seguridad.dto;

import java.util.List;
import py.com.ceamso.seguridad.model.Permiso;

public class RolDto {
    
    private Long id;
    private String codigo;
    private String descripcion;
    private List<Permiso> permisos;

    public RolDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }
       
}

package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author konecta
 */
@Embeddable
public class FormacionAcademicaHaciendaPk implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "cedula_funcionario")    
    private String cedulaIdentidad;
    
    @Column(name = "id_carrera")    
    private Integer idCarrera;
    
    @Column(name = "id_grado_academico")    
    private Integer id_grado_academico;
    
    @Column(name = "id_institucion_docencia")    
    private Integer idInstitucion;
    
    public FormacionAcademicaHaciendaPk() {        
    }

    public String getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(String cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Integer idCarrera) {
        this.idCarrera = idCarrera;
    }

    public Integer getId_grado_academico() {
        return id_grado_academico;
    }

    public void setId_grado_academico(Integer id_grado_academico) {
        this.id_grado_academico = id_grado_academico;
    }

    public Integer getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Integer idInstitucion) {
        this.idInstitucion = idInstitucion;
    }       
    
}

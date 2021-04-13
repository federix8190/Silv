package py.com.ceamso.reportes.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import py.com.ceamso.reportes.dto.FormacionAcademica;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "v_formacion_academica")
public class FormacionAcademicaHacienda implements Serializable {

    @EmbeddedId
    private FormacionAcademicaHaciendaPk pk;
    
    @Column(name = "descripcion_carrera") 
    private String carrera;
    
    @Column(name = "descripcion_grado") 
    private String gradoAcademico;
    
    @Column(name = "descripcion_profesion") 
    private String profesion;
    
    @Column(name = "nombre_institucion") 
    private String institucion;
    
    @Column(name = "descripcion_anhos") 
    private String situacionEstudio;
    
    @Column(name = "fecha_inicio") 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    
    @Column(name = "fecha_finalizacion") 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFinalizacion;
    
    public FormacionAcademicaHacienda() {        
    }

    public FormacionAcademicaHaciendaPk getPk() {
        return pk;
    }

    public void setPk(FormacionAcademicaHaciendaPk pk) {
        this.pk = pk;
    }           

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getGradoAcademico() {
        return gradoAcademico;
    }

    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getSituacionEstudio() {
        return situacionEstudio;
    }

    public void setSituacionEstudio(String situacionEstudio) {
        this.situacionEstudio = situacionEstudio;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }
        
}

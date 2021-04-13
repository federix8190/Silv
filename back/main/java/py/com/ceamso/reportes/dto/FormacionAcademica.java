package py.com.ceamso.reportes.dto;

/**
 *
 * @author daniel.rojas
 */
public class FormacionAcademica {
    
    private String estudioProfesion;
    private String titulo;
    private String situacionEstudio;
    private Integer anhoTitulo;
    
    public FormacionAcademica() {
    }
    public FormacionAcademica(Object[] datos) {
        this.estudioProfesion = (String) datos[0];
        this.titulo = (String) datos[1];
        this.situacionEstudio = (String) datos[2];
        this.anhoTitulo = (Integer) datos[3];
    }
    public FormacionAcademica(String estudioProfesion, String titulo, String situacionEstudio, Integer anhoTitulo) {
        this.estudioProfesion = estudioProfesion;
        this.titulo = titulo;
        this.situacionEstudio = situacionEstudio;
        this.anhoTitulo = anhoTitulo;
    }

    public String getEstudioProfesion() {
        return estudioProfesion;
    }

    public void setEstudioProfesion(String estudioProfesion) {
        this.estudioProfesion = estudioProfesion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSituacionEstudio() {
        return situacionEstudio;
    }

    public void setSituacionEstudio(String situacionEstudio) {
        this.situacionEstudio = situacionEstudio;
    }

    public Integer getAnhoTitulo() {
        return anhoTitulo;
    }

    public void setAnhoTitulo(Integer anhoTitulo) {
        this.anhoTitulo = anhoTitulo;
    }
    
    
}

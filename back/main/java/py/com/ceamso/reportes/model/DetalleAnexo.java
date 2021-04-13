package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "detalle_anexo")
public class DetalleAnexo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id_estructura_presupuestaria")
    private Long idEstructuraPresupuestaria;
    
    @Column(name = "cedula_identidad")
    private Integer cedulaIdentidad;
    
    @Column(name = "cod_programa")
    private Integer codigoPrograma;
    
    @Column(name = "descripcion_programa")
    private String descripcionPrograma;
    
    @Column(name = "cod_subprograma")
    private Integer codigoSubPrograma;
    
    @Column(name = "descripcion_subprograma")
    private String descripcionSubPrograma;
    
    public DetalleAnexo() {
    }

    public Long getIdEstructuraPresupuestaria() {
        return idEstructuraPresupuestaria;
    }

    public void setIdEstructuraPresupuestaria(Long idEstructuraPresupuestaria) {
        this.idEstructuraPresupuestaria = idEstructuraPresupuestaria;
    }

    public Integer getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(Integer cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }

    public Integer getCodigoPrograma() {
        return codigoPrograma;
    }

    public void setCodigoPrograma(Integer codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public String getDescripcionPrograma() {
        return descripcionPrograma;
    }

    public void setDescripcionPrograma(String descripcionPrograma) {
        this.descripcionPrograma = descripcionPrograma;
    }

    public Integer getCodigoSubPrograma() {
        return codigoSubPrograma;
    }

    public void setCodigoSubPrograma(Integer codigoSubPrograma) {
        this.codigoSubPrograma = codigoSubPrograma;
    }

    public String getDescripcionSubPrograma() {
        return descripcionSubPrograma;
    }

    public void setDescripcionSubPrograma(String descripcionSubPrograma) {
        this.descripcionSubPrograma = descripcionSubPrograma;
    }
    
}

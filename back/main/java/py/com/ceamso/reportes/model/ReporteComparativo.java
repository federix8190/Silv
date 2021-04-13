package py.com.ceamso.reportes.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import py.com.ceamso.base.WritableEntity;

@Entity
@Table(name = "reporte_comparativo")
@DynamicInsert
public class ReporteComparativo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario_creacion")
    private Long usuarioCreacion;
    
    @Column(name = "ip_creacion")
    private String ipCreacion;
    
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "path_archivo")
    private String pathArchivo;
    
    public ReporteComparativo() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(Long usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getIpCreacion() {
		return ipCreacion;
	}

	public void setIpCreacion(String ipCreacion) {
		this.ipCreacion = ipCreacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPathArchivo() {
		return pathArchivo;
	}

	public void setPathArchivo(String pathArchivo) {
		this.pathArchivo = pathArchivo;
	}
    
    
    

}

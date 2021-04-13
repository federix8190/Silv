package py.com.ceamso.administracion.model;

import org.hibernate.annotations.DynamicInsert;
import py.com.ceamso.administracion.dto.ClasificadorPuestoTrabajoDto;
import py.com.ceamso.base.WritableEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cpt")
@DynamicInsert
public class Cpt extends WritableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nivel")
    private Long nivel;

    @Column(name = "sub_nivel")
    private Integer subNivel;

    @Column(name = "nro_g")
    private Integer numeroGasto;

    @Column(name = "tit_unid")
    private Boolean tituloUnidad;

    @Column(name = "den")
    private String denominacion;

    @Column(name = "disponible")
    private String disponible;
    
    @Column(name = "inicio_vigencia")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date inicioVigencia;
    
    @Column(name = "fin_vigencia")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date finVigencia;

    public Cpt() {
    }

    public Cpt(ClasificadorPuestoTrabajoDto dto) {
        this.nivel = dto.getNivel();
        this.subNivel = dto.getSubNivel();
        this.numeroGasto = dto.getNumeroGasto();
        this.tituloUnidad = dto.getTituloUnidad();
        this.denominacion = dto.getDenominacion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public Integer getSubNivel() {
        return subNivel;
    }

    public void setSubNivel(Integer subNivel) {
        this.subNivel = subNivel;
    }

    public Integer getNumeroGasto() {
        return numeroGasto;
    }

    public void setNumeroGasto(Integer numeroGasto) {
        this.numeroGasto = numeroGasto;
    }

    public Boolean getTituloUnidad() {
        return tituloUnidad;
    }

    public void setTituloUnidad(Boolean tituloUnidad) {
        this.tituloUnidad = tituloUnidad;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

	public Date getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(Date inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public Date getFinVigencia() {
		return finVigencia;
	}

	public void setFinVigencia(Date finVigencia) {
		this.finVigencia = finVigencia;
	}
    
}

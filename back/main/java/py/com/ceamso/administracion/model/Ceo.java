package py.com.ceamso.administracion.model;

import org.hibernate.annotations.DynamicInsert;
import py.com.ceamso.base.WritableEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ceo")
@DynamicInsert
public class Ceo extends WritableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "den")
    private String denominacion;

    @Column(name = "disponible")
    private String disponible;
    
    @JoinColumn(name = "id_cuo", referencedColumnName = "id")
    @ManyToOne
    private Cuo cuo;
    
    @Transient
    private String denominacionCUO;
    
    @Transient
    private Integer nivel;
    
    @Transient
    private Integer subNivel;
    
    @Transient
    private Integer numero;
    
    @Transient
    private String direccion;
    
    @Transient
    private String coordinacion;
    
    @Transient
    private String nombreOrientacionFuncional;
    
    @Column(name = "inicio_vigencia")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date inicioVigencia;
    
    @Column(name = "fin_vigencia")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date finVigencia;
    
    @JoinColumn(name = "orientacion_func_id", referencedColumnName = "id")
    @ManyToOne
    private OrientacionFunc orientacionFunc;

    public Ceo() {
    }

    public Ceo(Ceo dto, String denominacion) {
        this.codigo = dto.codigo;        
        this.denominacion = denominacion;
        this.inicioVigencia = new Date();
        this.setActivo(true);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }    

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }
    
    public Cuo getCuo() {
        return cuo;
    }

    public void setCuo(Cuo cuo) {
        this.cuo = cuo;
    }

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCoordinacion() {
		return coordinacion;
	}

	public void setCoordinacion(String coordinacion) {
		this.coordinacion = coordinacion;
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

	public OrientacionFunc getOrientacionFunc() {
		return orientacionFunc;
	}

	public void setOrientacionFunc(OrientacionFunc orientacionFunc) {
		this.orientacionFunc = orientacionFunc;
	}
	
	public String getNombreOrientacionFuncional(){
        if (orientacionFunc == null) {
            return "";
        }
        return orientacionFunc.getNombre();
    }
    
    /*public String getDenominacionCuo(){
        if(cuo != null) return cuo.getDenominacion();
        else return null;
    }
    
    public Integer getNivelCuo(){
        if(cuo != null)
            return cuo.getNivel();
        else return null;
    }*/
}
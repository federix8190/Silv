package py.com.ceamso.administracion.model;

import org.hibernate.annotations.DynamicInsert;
import py.com.ceamso.administracion.dto.ClasificadorPuestoTrabajoFuncionalDto;
import py.com.ceamso.base.WritableEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cpt_ef")
@DynamicInsert
public class CptF extends WritableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nro")
    private String nro;

    @JoinColumn(name = "id_cpt", referencedColumnName = "id")
    @ManyToOne
    private Cpt cpt;
    
    //@JoinColumn(name = "id_mecip", referencedColumnName = "id")
    //@ManyToOne
    //private Mecip mecip;

    @JoinColumn(name = "ambito_id", referencedColumnName = "id")
    @ManyToOne
    private Ambito ambito;

    @Column(name = "nivel_cpt")
    private Long nivelCpt;

    @Column(name = "cod_proceso")
    private String codProceso;

    @JoinColumn(name = "orientacion_func_id", referencedColumnName = "id")
    @ManyToOne
    private OrientacionFunc orientacionFunc;

    @Column(name = "den")
    private String den;

    @Column(name = "disponible")
    private String disponible;
    
    @Transient
    private String denominacionMECIP;
    
    @Transient
    private String denominacionCpt;
    
    //@Transient
    //private String codigoMecip;
    
    @Transient
    private Integer subNivelCpt;
    
    @Transient
    private Long nivelCPT;
    
    @Transient
    private Integer numeroCpt;

    @Transient
    private Boolean titularUnidadCpt;
    
    @Transient
    private String denominacion;
    
    @Transient
    private String claseGeneral;
    
    @Transient
    private String tituloUnidadCpt;
    
    @Transient
    private String nombreAmbito;
    
    @Transient
    private String nombreOrientacionFuncional;
    
    @Transient
    private String codigoMecip;
    
    @Transient
    private Long idCpt;
    
    //@OneToMany(mappedBy = "idCptEf", fetch = FetchType.LAZY)
    //@JsonBackReference
    //private List<Legajo> legajo;

    /*@OneToMany
    @JoinTable(
            name="cpt_ef_mecip",
            joinColumns = @JoinColumn( name="id_cpt_ef"),
            inverseJoinColumns = @JoinColumn( name="id_mecip")
    )*/
    @Transient
    private List<Mecip> mecip = new ArrayList<Mecip>();
    
    public CptF() {
    }

    public CptF(CptF dto) {
        this.nro = dto.nro;
        this.cpt = dto.cpt;
        this.ambito = dto.ambito;
        this.codProceso = dto.codProceso;
        this.orientacionFunc = dto.orientacionFunc;
        this.den = dto.den;
        this.nivelCpt = dto.getNivelCpt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public Cpt getCpt() {
        return cpt;
    }

    public void setCpt(Cpt cpt) {
        this.cpt = cpt;
    }

    public Ambito getAmbito() {
        return ambito;
    }

    public void setAmbito(Ambito ambito) {
        this.ambito = ambito;
    }

    public String getCodProceso() {
        return codProceso;
    }

    public void setCodProceso(String codProceso) {
        this.codProceso = codProceso;
    }

    public OrientacionFunc getOrientacionFunc() {
        return orientacionFunc;
    }

    public void setOrientacionFunc(OrientacionFunc orientacionFunc) {
        this.orientacionFunc = orientacionFunc;
    }

    public String getDen() {
        return den;
    }

    public void setDen(String den) {
        this.den = den;
    }

    /*public List<Legajo> getLegajo() {
        return legajo;
    }

    public void setLegajo(List<Legajo> legajo) {
        this.legajo = legajo;
    }*/

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public Long getNivelCpt() {
        return nivelCpt;
    }

    public void setNivelCpt(Long nivelCpt) {
        this.nivelCpt = nivelCpt;
    }
    /*
    public Mecip getMecip() {
        return mecip;
    }

    public void setMecip(Mecip mecip) {
        this.mecip = mecip;
    }*/

    public List<Mecip> getMecip() {
        return mecip;
    }

    public void setMecip(List<Mecip> mecip) {
        this.mecip = mecip;
    }

    public Integer getSubNivelCpt() {
        return cpt.getSubNivel();
    }

    public Integer getNumeroCpt() {
        return cpt.getNumeroGasto();
    }

    public Boolean getTituloUnidadCpt() {
        return cpt.getTituloUnidad();
    }
    
    public String getClaseGeneral(){
        return cpt.getDenominacion();
    }
    
    public String getNombreAmbito(){
        if (ambito == null) {
            return "";
        }
        return ambito.getNombre();
    }
    
    public String getCodigoMecip(){
        String codigos = "";
        if (mecip != null) {
	        for(Mecip m : mecip){
	            codigos += m.getCodigo() + ",";
	        }
        }
        return codigos.replaceAll("(,)*$", "");
    }
    
    public String getNombreOrientacionFuncional(){
        if (orientacionFunc == null) {
            return "";
        }
        return orientacionFunc.getNombre();
    }

	public void setClaseGeneral(String claseGeneral) {
		this.claseGeneral = claseGeneral;
	}
    
    
}

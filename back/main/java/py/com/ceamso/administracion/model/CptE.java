package py.com.ceamso.administracion.model;

import org.hibernate.annotations.DynamicInsert;
import py.com.ceamso.administracion.dto.ClasificadorPuestoTrabajoEscalafonarioDto;
import py.com.ceamso.base.WritableEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cpt_ee")
@DynamicInsert
public class CptE extends WritableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nro")
    private String numero;

    @JoinColumn(name = "id_cpt", referencedColumnName = "id")
    @ManyToOne
    private Cpt cpt;

   // @Column(name = "ambito")
   // private String ambito;

    @Column(name = "nivel")
    private String nivel;

    @Column(name = "nivel_cpt")
    private Long nivelCpt;
    
    @JoinColumn(name = "ambito_id", referencedColumnName = "id")
    @ManyToOne
    private Ambito ambito;

    @Column(name = "categoria")
    private String nombreCategoria;

    @Column(name = "den")
    private String denominacion;

    @Column(name = "disponible")
    private String disponible;
    
    @Transient
    private List<Categoria> categoria;
    
    @Transient
    private String denominacionCpt;
    
    @Transient
    private Integer subNivelCpt;
    
    @Transient
    private Integer numeroCpt;
    
    @Transient
    private Boolean tituloUnidadCpt;

    @Transient
    private String claseGeneral;
    
    @Transient
    private String nombreAmbito;
    
    @Transient
    private String nombreOrientacionFuncional;
    
    @Transient
    private String codigoMecip;
    
    @Transient
    private Long idCpt;
    
    //@OneToMany(mappedBy = "idCptEe", fetch = FetchType.LAZY)
    //@JsonBackReference
    //private List<Legajo> legajo;

    public CptE() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public List<Categoria> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<Categoria> categoria) {
        this.categoria = categoria;
    }
    
    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
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

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public Integer getSubNivelCpt() {
        if(cpt != null) return cpt.getSubNivel();
        else return null;
    }

    public Integer getNumeroCpt() {
        if(cpt != null) return cpt.getNumeroGasto();
        else return null;
    }

    public Boolean getTituloUnidadCpt() {
        if(cpt != null) return cpt.getTituloUnidad();
        else return null;
    }
    
    public String getClaseGeneral(){
        if(cpt != null) return cpt.getDenominacion();
        else return null;
    }
    
    public String getNombreAmbito(){
        if (ambito == null) {
            return "";
        }
        return ambito.getNombre();
    }
}

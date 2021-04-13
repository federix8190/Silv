/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import py.com.ceamso.base.WritableEntity;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "puesto_trabajo")
@DynamicInsert
public class PuestoTrabajo extends WritableEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PuestoTrabajoPK pk;
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_puesto")
    private Long numeroPuesto;
    
    @Column(name = "numero_tramo")
    private Long numeroTramo;
    
    @Column(name = "nivel_cpt")
    private Long nivelCtp;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "cargo")
    private String cargo;

    @JoinColumn(name = "id_cpt", referencedColumnName = "id")
    @ManyToOne
    private Cpt cpt;

    @JoinColumn(name = "id_cpt_ee", referencedColumnName = "id")
    @ManyToOne
    private CptE cptE;

    @JoinColumn(name = "id_cpt_ef", referencedColumnName = "id")
    @ManyToOne
    private CptF cptF;

    @JoinColumn(name = "id_ceo", referencedColumnName = "id")
    @ManyToOne
    private Ceo ceo;

    @JoinColumn(name = "id_cuo", referencedColumnName = "id")
    @ManyToOne
    private Cuo cuo;
    
    @Column(name = "vinculo_laboral")
    private String vinculoLaboral;
    
    @Column(name = "sexo")
    private String sexo;
    
    @Column(name = "presupuestado")
    private Long presupuestado;
    
    @Column(name = "programa")
    private String programa;
    
    @Column(name = "subprograma")
    private String subprograma;
/*
    @Column(name = "anho")
    private Integer anho;

    @Column(name = "mes")
    private Integer mes;
*/
    public PuestoTrabajo(){
        
    }
    
    public PuestoTrabajo(PuestoTrabajoPK pk, Long numeroPuesto, String categoria, 
    		String cargo, Cpt cpt, CptE cptE, CptF cptF, Ceo ceo, Cuo cuo) {
        this.pk = pk;
        this.numeroPuesto = numeroPuesto;
        this.categoria = categoria;
        this.cargo = cargo;
        this.cpt = cpt;
        this.cptE = cptE;
        this.cptF = cptF;
        this.ceo = ceo;
        this.cuo = cuo;
    }

    public PuestoTrabajoPK getPk() {
        return pk;
    }

    public void setPk(PuestoTrabajoPK pk) {
        this.pk = pk;
    }
    
    public Long getNumeroPuesto() {
        return this.numeroPuesto;
    }

    public void setNumeroPuesto(Long numeroPuesto) {
        this.numeroPuesto = numeroPuesto;
    }
    
    public Integer getCedulaIdentidad() {
        return this.pk.getCedulaIdentidad();
    }

    public Long getNumeroTramo() {
        return numeroTramo;
    }

    public void setNumeroTramo(Long numeroTramo) {
        this.numeroTramo = numeroTramo;
    }

    public Long getNivelCtp() {
        return nivelCtp;
    }

    public void setNivelCtp(Long nivelCtp) {
        this.nivelCtp = nivelCtp;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Cpt getCpt() {
        return cpt;
    }

    public void setCpt(Cpt cpt) {
        this.cpt = cpt;
    }

    public CptE getCptE() {
        return cptE;
    }

    public void setCptE(CptE cptE) {
        this.cptE = cptE;
    }

    public CptF getCptF() {
        return cptF;
    }

    public void setCptF(CptF cptF) {
        this.cptF = cptF;
    }

    public Ceo getCeo() {
        return ceo;
    }

    public void setCeo(Ceo ceo) {
        this.ceo = ceo;
    }

    public Cuo getCuo() {
        return cuo;
    }

    public void setCuo(Cuo cuo) {
        this.cuo = cuo;
    }

    public Integer getAnho() {
        return this.pk.getAnho();
    }

    public Integer getMes() {
        return this.pk.getMes();
    }

	public String getVinculoLaboral() {
		return vinculoLaboral;
	}

	public void setVinculoLaboral(String vinculoLaboral) {
		this.vinculoLaboral = vinculoLaboral;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Long getPresupuestado() {
		return presupuestado;
	}

	public void setPresupuestado(Long presupuestado) {
		this.presupuestado = presupuestado;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getSubprograma() {
		return subprograma;
	}

	public void setSubprograma(String subprograma) {
		this.subprograma = subprograma;
	}
	
	

}

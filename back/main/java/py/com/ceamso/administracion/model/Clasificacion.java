/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.DynamicInsert;
import py.com.ceamso.base.WritableEntity;

/**
 *
 * @author pablo
 */
@Entity
@Table(name="clasificacion")
@DynamicInsert
public class Clasificacion extends WritableEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "denominacion")
    private String denominacion;

    @Column(name = "codigo")
    private String codigo;
    
    @JoinColumn(name = "id_cpt", referencedColumnName = "id")
    @ManyToOne
    private Cpt cpt;
    
    @Transient
    private Integer subNivel;
    
    @Transient
    private Long nivel;
    
    @Transient
    private Integer numeroGasto;

    @Transient
    private Boolean titularUnidad;
    
    @Transient
    private String denominacionCpt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cpt getCpt() {
        return cpt;
    }

    public void setCpt(Cpt cpt) {
        this.cpt = cpt;
    }
    
    
}

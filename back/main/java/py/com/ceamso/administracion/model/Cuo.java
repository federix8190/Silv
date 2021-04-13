package py.com.ceamso.administracion.model;

import org.hibernate.annotations.DynamicInsert;
import py.com.ceamso.base.WritableEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cuo")
@DynamicInsert
public class Cuo extends WritableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nivel")
    private Integer nivel;
    
    @Column(name = "subnivel")
    private Integer subNivel;
    
    @Column(name = "numero")
    private Integer numero;
    
    @Column(name = "denominacion")
    private String denominacion;


    public Cuo() {
    }

    public Cuo(Cuo dto) {
        this.nivel = dto.nivel;
        this.subNivel = dto.subNivel;
        this.numero = dto.numero;
        this.denominacion = dto.getDenominacion();
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getSubNivel() {
        return subNivel;
    }

    public void setSubNivel(Integer subnivel) {
        this.subNivel = subnivel;
    }
    public Integer getNumero() {
        return numero;
    }

    public void setNumeroro(Integer numero) {
        this.numero = numero;
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

}

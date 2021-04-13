package py.com.ceamso.administracion.model;

import org.hibernate.annotations.DynamicInsert;
import py.com.ceamso.base.WritableEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mecip")
@DynamicInsert
public class Mecip extends WritableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "den")
    private String denominacionMECIP;

    @Column(name = "disponible")
    private String disponible;
    
    @Column(name = "nivel1")
    private Integer nivel1;
    
    @Column(name = "nivel2")
    private Integer nivel2;
    
    @Column(name = "nivel3")
    private Integer nivel3;

    public Mecip() {
    }

    public Mecip(Mecip dto) {
        this.codigo = dto.codigo;        
        this.denominacionMECIP = dto.getDenominacionMECIP();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }    

    public String getDenominacionMECIP() {
        return denominacionMECIP;
    }

    public void setDenominacionMECIP(String denominacion) {
        this.denominacionMECIP = denominacion;
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

    public Integer getNivel1() {
        return nivel1;
    }

    public void setNivel1(Integer nivel1) {
        this.nivel1 = nivel1;
    }

    public Integer getNivel2() {
        return nivel2;
    }

    public void setNivel2(Integer nivel2) {
        this.nivel2 = nivel2;
    }

    public Integer getNivel3() {
        return nivel3;
    }

    public void setNivel3(Integer nivel3) {
        this.nivel3 = nivel3;
    }
    
}

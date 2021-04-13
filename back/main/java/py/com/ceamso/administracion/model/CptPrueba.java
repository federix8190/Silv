package py.com.ceamso.administracion.model;

import org.hibernate.annotations.DynamicInsert;
import py.com.ceamso.base.WritableEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cpt_prueba")
@DynamicInsert
public class CptPrueba extends WritableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public CptPrueba() {
    }

    public CptPrueba(CptPrueba cptp) {
        this.codigo = cptp.getCodigo();
        this.denominacion = cptp.getDenominacion();
    }

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

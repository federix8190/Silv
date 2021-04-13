package py.com.ceamso.administracion.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import py.com.ceamso.base.WritableEntity;

@Entity
@Table(name = "cts")
public class Cts extends WritableEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_tramo")
    private Long numeroTramo;

    /*@Column(name = "fecha")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;*/
    
    @Column(name = "anho")
    private Long anho;
    
    @Column(name = "mes")
    private Long mes;

    @Column(name = "minimo")
    private Long minimo;

    @Column(name = "maximo")
    private Long maximo;
    
    @Transient
    private boolean asignado;

    public Cts() {
    }

    public Cts(Cts dto) {
        //this.fecha = dto.fecha;
        this.minimo = dto.minimo;
        this.maximo = dto.maximo;
    } 
    
    public Cts(Long numeroTramo) {
        this.numeroTramo = numeroTramo;
        this.minimo = 0L;
        this.maximo = 0L;
    } 

    /*public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }*/

    public Long getAnho() {
        return anho;
    }

    public void setAnho(Long anho) {
        this.anho = anho;
    }

    public Long getMes() {
        return mes;
    }

    public void setMes(Long mes) {
        this.mes = mes;
    }

    public Long getMinimo() {
        return minimo;
    }

    public void setMinimo(Long minimo) {
        this.minimo = minimo;
    }

    public Long getMaximo() {
        return maximo;
    }

    public void setMaximo(Long maximo) {
        this.maximo = maximo;
    }

    public Long getNumeroTramo() {
        return numeroTramo;
    }

    public void setNumeroTramo(Long numeroTramo) {
        this.numeroTramo = numeroTramo;
    }

    public boolean isAsignado() {
        return asignado;
    }

    public void setAsignado(boolean asignado) {
        this.asignado = asignado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}

package py.com.ceamso.gestion.model;

import py.com.ceamso.base.WritableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cpt_cargos")
public class CptCargos extends WritableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_cargo_disponible")
    private Long idCargoDisponible;

    @Column(name = "id_cpt")
    private Long idCpt;

    @Column(name = "id_cpt_ef")
    private Long idCptEf;

    @Column(name = "id_cpt_ee")
    private Long idCptEe;

    public CptCargos() {
    }

    public CptCargos(Long idCargoDisponible, Long idCptEf, Long idCptEe) {
        this.idCargoDisponible = idCargoDisponible;
        this.idCptEf = idCptEf;
        this.idCptEe = idCptEe;
    }

    public Long getIdCargoDisponible() {
        return idCargoDisponible;
    }

    public void setIdCargoDisponible(Long idCargoDisponible) {
        this.idCargoDisponible = idCargoDisponible;
    }

    public Long getIdCptEf() {
        return idCptEf;
    }

    public void setIdCptEf(Long idCptEf) {
        this.idCptEf = idCptEf;
    }

    public Long getIdCptEe() {
        return idCptEe;
    }

    public void setIdCptEe(Long idCptEe) {
        this.idCptEe = idCptEe;
    }

    public Long getIdCpt() {
        return idCpt;
    }

    public void setIdCpt(Long idCpt) {
        this.idCpt = idCpt;
    }
        
}

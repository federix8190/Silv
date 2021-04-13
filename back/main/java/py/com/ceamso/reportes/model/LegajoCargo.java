package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "v_legajo_cargo")
public class LegajoCargo implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private LegajoCargoPK pk;
    
    @Transient
    private String nombre;

    @Column(name = "funcion_real")
    private String funcionReal;
    
    @Column(name = "vinculacion_funcionario")
    private String vinculacionFuncionario;    
    
    public LegajoCargo() {        
    }
    
    public LegajoCargo(LegajoCargo l, String cargo) {
        this.pk = l.getPk();
        this.funcionReal = l.getFuncionReal();
        this.vinculacionFuncionario = l.getVinculacionFuncionario();
        this.nombre = cargo;
    }

    public LegajoCargoPK getPk() {
        return pk;
    }

    public void setPk(LegajoCargoPK pk) {
        this.pk = pk;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFuncionReal() {
        return funcionReal;
    }

    public void setFuncionReal(String funcionReal) {
        this.funcionReal = funcionReal;
    }
    
    public String getVinculacionFuncionario() {
        return vinculacionFuncionario;
    }

    public void setVinculacionFuncionario(String vinculacionFuncionario) {
        this.vinculacionFuncionario = vinculacionFuncionario;
    }
        
}

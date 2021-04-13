package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_cte_vinculacion")
public class Vinculacion implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "vinculacion_funcionario")
    private String vinculacionFuncionario;
    
    public Vinculacion() {
    }

    public String getVinculacionFuncionario() {
        return vinculacionFuncionario;
    }

    public void setVinculacionFuncionario(String vinculacionFuncionario) {
        this.vinculacionFuncionario = vinculacionFuncionario;
    }
      
}

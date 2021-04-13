package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "v_cte_categorias")
public class Categoria implements Serializable {
    
    @Id
    @Column(name = "descrip_categoria_personal")
    private String nombre;
    
    public Categoria() {        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
       
}

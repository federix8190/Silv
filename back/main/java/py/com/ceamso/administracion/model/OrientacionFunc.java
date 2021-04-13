
package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import py.com.ceamso.base.WritableEntity;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "orientacion_funcional")
public class OrientacionFunc extends WritableEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nombre")
    private String nombreOrientacion;
    
    public OrientacionFunc() {        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombreOrientacion;
    }

    public void setNombre(String nombre) {
        this.nombreOrientacion = nombre;
    }
       
}

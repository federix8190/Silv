package py.com.ceamso.administracion.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "ambito")
public class Ambito implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nombre")
    private String nombreAmbito;
    
    public Ambito() {        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombreAmbito;
    }

    public void setNombre(String nombre) {
        this.nombreAmbito = nombre;
    }
       
}

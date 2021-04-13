
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="circunscripcion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="aplicacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="rol" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "circunscripcion",
    "aplicacion",
    "rol",
    "usuario"
})
@XmlRootElement(name = "ObtenerCircunscripcionPorAplicacionRol")
public class ObtenerCircunscripcionPorAplicacionRol {

    protected int circunscripcion;
    protected int aplicacion;
    protected int rol;
    protected int usuario;

    /**
     * Gets the value of the circunscripcion property.
     * 
     */
    public int getCircunscripcion() {
        return circunscripcion;
    }

    /**
     * Sets the value of the circunscripcion property.
     * 
     */
    public void setCircunscripcion(int value) {
        this.circunscripcion = value;
    }

    /**
     * Gets the value of the aplicacion property.
     * 
     */
    public int getAplicacion() {
        return aplicacion;
    }

    /**
     * Sets the value of the aplicacion property.
     * 
     */
    public void setAplicacion(int value) {
        this.aplicacion = value;
    }

    /**
     * Gets the value of the rol property.
     * 
     */
    public int getRol() {
        return rol;
    }

    /**
     * Sets the value of the rol property.
     * 
     */
    public void setRol(int value) {
        this.rol = value;
    }

    /**
     * Gets the value of the usuario property.
     * 
     */
    public int getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     */
    public void setUsuario(int value) {
        this.usuario = value;
    }

}

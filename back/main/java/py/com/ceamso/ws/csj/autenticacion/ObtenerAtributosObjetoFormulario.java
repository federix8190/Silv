
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
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="formulario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="rol" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "usuario",
    "formulario",
    "rol"
})
@XmlRootElement(name = "ObtenerAtributosObjetoFormulario")
public class ObtenerAtributosObjetoFormulario {

    protected int usuario;
    protected int formulario;
    protected int rol;

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

    /**
     * Gets the value of the formulario property.
     * 
     */
    public int getFormulario() {
        return formulario;
    }

    /**
     * Sets the value of the formulario property.
     * 
     */
    public void setFormulario(int value) {
        this.formulario = value;
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

}

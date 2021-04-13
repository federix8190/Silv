
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
 *         &lt;element name="aplicacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "aplicacion"
})
@XmlRootElement(name = "ObtenerRolesPorAplicacion")
public class ObtenerRolesPorAplicacion {

    protected int aplicacion;

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

}

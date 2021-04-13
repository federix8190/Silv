
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="ObtenerClaseAtributoAplicacionResult" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
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
    "obtenerClaseAtributoAplicacionResult"
})
@XmlRootElement(name = "ObtenerClaseAtributoAplicacionResponse")
public class ObtenerClaseAtributoAplicacionResponse {

    @XmlElement(name = "ObtenerClaseAtributoAplicacionResult")
    protected Object obtenerClaseAtributoAplicacionResult;

    /**
     * Gets the value of the obtenerClaseAtributoAplicacionResult property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getObtenerClaseAtributoAplicacionResult() {
        return obtenerClaseAtributoAplicacionResult;
    }

    /**
     * Sets the value of the obtenerClaseAtributoAplicacionResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setObtenerClaseAtributoAplicacionResult(Object value) {
        this.obtenerClaseAtributoAplicacionResult = value;
    }

}

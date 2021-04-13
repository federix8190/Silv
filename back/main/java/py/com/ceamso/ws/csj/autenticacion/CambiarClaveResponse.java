
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
 *         &lt;element name="CambiarClaveResult" type="{http://www.csj.gov.py/}TEstadoCambioClave"/>
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
    "cambiarClaveResult"
})
@XmlRootElement(name = "CambiarClaveResponse")
public class CambiarClaveResponse {

    @XmlElement(name = "CambiarClaveResult", required = true)
    protected TEstadoCambioClave cambiarClaveResult;

    /**
     * Gets the value of the cambiarClaveResult property.
     * 
     * @return
     *     possible object is
     *     {@link TEstadoCambioClave }
     *     
     */
    public TEstadoCambioClave getCambiarClaveResult() {
        return cambiarClaveResult;
    }

    /**
     * Sets the value of the cambiarClaveResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link TEstadoCambioClave }
     *     
     */
    public void setCambiarClaveResult(TEstadoCambioClave value) {
        this.cambiarClaveResult = value;
    }

}


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
 *         &lt;element name="AutenticarResult" type="{http://www.csj.gov.py/}cControlAutenticacion" minOccurs="0"/>
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
    "autenticarResult"
})
@XmlRootElement(name = "AutenticarResponse")
public class AutenticarResponse {

    @XmlElement(name = "AutenticarResult")
    protected CControlAutenticacion autenticarResult;

    /**
     * Gets the value of the autenticarResult property.
     * 
     * @return
     *     possible object is
     *     {@link CControlAutenticacion }
     *     
     */
    public CControlAutenticacion getAutenticarResult() {
        return autenticarResult;
    }

    /**
     * Sets the value of the autenticarResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CControlAutenticacion }
     *     
     */
    public void setAutenticarResult(CControlAutenticacion value) {
        this.autenticarResult = value;
    }

}

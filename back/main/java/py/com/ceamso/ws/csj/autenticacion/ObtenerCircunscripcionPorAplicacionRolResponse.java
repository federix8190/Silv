
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
 *         &lt;element name="ObtenerCircunscripcionPorAplicacionRolResult" type="{http://www.csj.gov.py/}cCircunscripcion" minOccurs="0"/>
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
    "obtenerCircunscripcionPorAplicacionRolResult"
})
@XmlRootElement(name = "ObtenerCircunscripcionPorAplicacionRolResponse")
public class ObtenerCircunscripcionPorAplicacionRolResponse {

    @XmlElement(name = "ObtenerCircunscripcionPorAplicacionRolResult")
    protected CCircunscripcion obtenerCircunscripcionPorAplicacionRolResult;

    /**
     * Gets the value of the obtenerCircunscripcionPorAplicacionRolResult property.
     * 
     * @return
     *     possible object is
     *     {@link CCircunscripcion }
     *     
     */
    public CCircunscripcion getObtenerCircunscripcionPorAplicacionRolResult() {
        return obtenerCircunscripcionPorAplicacionRolResult;
    }

    /**
     * Sets the value of the obtenerCircunscripcionPorAplicacionRolResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCircunscripcion }
     *     
     */
    public void setObtenerCircunscripcionPorAplicacionRolResult(CCircunscripcion value) {
        this.obtenerCircunscripcionPorAplicacionRolResult = value;
    }

}

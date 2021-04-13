
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
 *         &lt;element name="usuarioSolicitante" type="{http://www.csj.gov.py/}cControlAutenticacion" minOccurs="0"/>
 *         &lt;element name="claveActual" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nuevaClave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="renuevaClave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "usuarioSolicitante",
    "claveActual",
    "nuevaClave",
    "renuevaClave"
})
@XmlRootElement(name = "CambiarClave")
public class CambiarClave {

    protected CControlAutenticacion usuarioSolicitante;
    protected String claveActual;
    protected String nuevaClave;
    protected String renuevaClave;

    /**
     * Gets the value of the usuarioSolicitante property.
     * 
     * @return
     *     possible object is
     *     {@link CControlAutenticacion }
     *     
     */
    public CControlAutenticacion getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    /**
     * Sets the value of the usuarioSolicitante property.
     * 
     * @param value
     *     allowed object is
     *     {@link CControlAutenticacion }
     *     
     */
    public void setUsuarioSolicitante(CControlAutenticacion value) {
        this.usuarioSolicitante = value;
    }

    /**
     * Gets the value of the claveActual property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveActual() {
        return claveActual;
    }

    /**
     * Sets the value of the claveActual property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveActual(String value) {
        this.claveActual = value;
    }

    /**
     * Gets the value of the nuevaClave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNuevaClave() {
        return nuevaClave;
    }

    /**
     * Sets the value of the nuevaClave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNuevaClave(String value) {
        this.nuevaClave = value;
    }

    /**
     * Gets the value of the renuevaClave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRenuevaClave() {
        return renuevaClave;
    }

    /**
     * Sets the value of the renuevaClave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRenuevaClave(String value) {
        this.renuevaClave = value;
    }

}

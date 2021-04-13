
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cAuditoria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cAuditoria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="m_sNombrePropiedad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="m_sValorOriginal" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="m_sNuevoValor" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="m_sCambioValor" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cAuditoria", propOrder = {
    "msNombrePropiedad",
    "msValorOriginal",
    "msNuevoValor",
    "msCambioValor"
})
public class CAuditoria {

    @XmlElement(name = "m_sNombrePropiedad")
    protected String msNombrePropiedad;
    @XmlElement(name = "m_sValorOriginal")
    protected Object msValorOriginal;
    @XmlElement(name = "m_sNuevoValor")
    protected Object msNuevoValor;
    @XmlElement(name = "m_sCambioValor")
    protected boolean msCambioValor;

    /**
     * Gets the value of the msNombrePropiedad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMSNombrePropiedad() {
        return msNombrePropiedad;
    }

    /**
     * Sets the value of the msNombrePropiedad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMSNombrePropiedad(String value) {
        this.msNombrePropiedad = value;
    }

    /**
     * Gets the value of the msValorOriginal property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getMSValorOriginal() {
        return msValorOriginal;
    }

    /**
     * Sets the value of the msValorOriginal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setMSValorOriginal(Object value) {
        this.msValorOriginal = value;
    }

    /**
     * Gets the value of the msNuevoValor property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getMSNuevoValor() {
        return msNuevoValor;
    }

    /**
     * Sets the value of the msNuevoValor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setMSNuevoValor(Object value) {
        this.msNuevoValor = value;
    }

    /**
     * Gets the value of the msCambioValor property.
     * 
     */
    public boolean isMSCambioValor() {
        return msCambioValor;
    }

    /**
     * Sets the value of the msCambioValor property.
     * 
     */
    public void setMSCambioValor(boolean value) {
        this.msCambioValor = value;
    }

}

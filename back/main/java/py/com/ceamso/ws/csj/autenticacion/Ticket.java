
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Ticket complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Ticket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="KeyTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoUsuario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CodigoCircunscripcion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FechaValidacion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="JsonTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ticket", propOrder = {
    "keyTicket",
    "codigoUsuario",
    "codigoCircunscripcion",
    "fechaValidacion",
    "jsonTicket"
})
public class Ticket {

    @XmlElement(name = "KeyTicket")
    protected String keyTicket;
    @XmlElement(name = "CodigoUsuario")
    protected int codigoUsuario;
    @XmlElement(name = "CodigoCircunscripcion")
    protected int codigoCircunscripcion;
    @XmlElement(name = "FechaValidacion", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaValidacion;
    @XmlElement(name = "JsonTicket")
    protected String jsonTicket;

    /**
     * Gets the value of the keyTicket property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyTicket() {
        return keyTicket;
    }

    /**
     * Sets the value of the keyTicket property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyTicket(String value) {
        this.keyTicket = value;
    }

    /**
     * Gets the value of the codigoUsuario property.
     * 
     */
    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    /**
     * Sets the value of the codigoUsuario property.
     * 
     */
    public void setCodigoUsuario(int value) {
        this.codigoUsuario = value;
    }

    /**
     * Gets the value of the codigoCircunscripcion property.
     * 
     */
    public int getCodigoCircunscripcion() {
        return codigoCircunscripcion;
    }

    /**
     * Sets the value of the codigoCircunscripcion property.
     * 
     */
    public void setCodigoCircunscripcion(int value) {
        this.codigoCircunscripcion = value;
    }

    /**
     * Gets the value of the fechaValidacion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaValidacion() {
        return fechaValidacion;
    }

    /**
     * Sets the value of the fechaValidacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaValidacion(XMLGregorianCalendar value) {
        this.fechaValidacion = value;
    }

    /**
     * Gets the value of the jsonTicket property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJsonTicket() {
        return jsonTicket;
    }

    /**
     * Sets the value of the jsonTicket property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJsonTicket(String value) {
        this.jsonTicket = value;
    }

}

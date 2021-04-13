
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
 *         &lt;element name="DatosTicketResult" type="{http://www.csj.gov.py/}Ticket" minOccurs="0"/>
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
    "datosTicketResult"
})
@XmlRootElement(name = "DatosTicketResponse")
public class DatosTicketResponse {

    @XmlElement(name = "DatosTicketResult")
    protected Ticket datosTicketResult;

    /**
     * Gets the value of the datosTicketResult property.
     * 
     * @return
     *     possible object is
     *     {@link Ticket }
     *     
     */
    public Ticket getDatosTicketResult() {
        return datosTicketResult;
    }

    /**
     * Sets the value of the datosTicketResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ticket }
     *     
     */
    public void setDatosTicketResult(Ticket value) {
        this.datosTicketResult = value;
    }

}

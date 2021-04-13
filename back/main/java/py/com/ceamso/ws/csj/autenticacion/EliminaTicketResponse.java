
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
 *         &lt;element name="EliminaTicketResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "eliminaTicketResult"
})
@XmlRootElement(name = "EliminaTicketResponse")
public class EliminaTicketResponse {

    @XmlElement(name = "EliminaTicketResult")
    protected boolean eliminaTicketResult;

    /**
     * Gets the value of the eliminaTicketResult property.
     * 
     */
    public boolean isEliminaTicketResult() {
        return eliminaTicketResult;
    }

    /**
     * Sets the value of the eliminaTicketResult property.
     * 
     */
    public void setEliminaTicketResult(boolean value) {
        this.eliminaTicketResult = value;
    }

}

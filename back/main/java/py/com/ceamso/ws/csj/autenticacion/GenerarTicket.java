
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
 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="json" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="circunscripcion" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "key",
    "json",
    "usuario",
    "circunscripcion"
})
@XmlRootElement(name = "GenerarTicket")
public class GenerarTicket {

    protected String key;
    protected String json;
    protected int usuario;
    protected int circunscripcion;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the json property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJson() {
        return json;
    }

    /**
     * Sets the value of the json property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJson(String value) {
        this.json = value;
    }

    /**
     * Gets the value of the usuario property.
     * 
     */
    public int getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     */
    public void setUsuario(int value) {
        this.usuario = value;
    }

    /**
     * Gets the value of the circunscripcion property.
     * 
     */
    public int getCircunscripcion() {
        return circunscripcion;
    }

    /**
     * Sets the value of the circunscripcion property.
     * 
     */
    public void setCircunscripcion(int value) {
        this.circunscripcion = value;
    }

}

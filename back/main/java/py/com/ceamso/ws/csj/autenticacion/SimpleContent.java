
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
 *     &lt;extension base="{http://www.w3.org/2001/XMLSchema}annotated">
 *       &lt;choice>
 *         &lt;element name="restriction" type="{http://www.w3.org/2001/XMLSchema}simpleRestrictionType"/>
 *         &lt;element name="extension" type="{http://www.w3.org/2001/XMLSchema}simpleExtensionType"/>
 *       &lt;/choice>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "extension",
    "restriction"
})
@XmlRootElement(name = "simpleContent", namespace = "http://www.w3.org/2001/XMLSchema")
public class SimpleContent
    extends Annotated
{

    @XmlElement(namespace = "http://www.w3.org/2001/XMLSchema")
    protected SimpleExtensionType extension;
    @XmlElement(namespace = "http://www.w3.org/2001/XMLSchema")
    protected SimpleRestrictionType restriction;

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleExtensionType }
     *     
     */
    public SimpleExtensionType getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleExtensionType }
     *     
     */
    public void setExtension(SimpleExtensionType value) {
        this.extension = value;
    }

    /**
     * Gets the value of the restriction property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleRestrictionType }
     *     
     */
    public SimpleRestrictionType getRestriction() {
        return restriction;
    }

    /**
     * Sets the value of the restriction property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleRestrictionType }
     *     
     */
    public void setRestriction(SimpleRestrictionType value) {
        this.restriction = value;
    }

}


package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
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
 *         &lt;element name="ObtenerObjetosFormularioPorUsuarioResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.w3.org/2001/XMLSchema}schema"/>
 *                   &lt;any/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "obtenerObjetosFormularioPorUsuarioResult"
})
@XmlRootElement(name = "ObtenerObjetosFormularioPorUsuarioResponse")
public class ObtenerObjetosFormularioPorUsuarioResponse {

    @XmlElement(name = "ObtenerObjetosFormularioPorUsuarioResult")
    protected ObtenerObjetosFormularioPorUsuarioResponse.ObtenerObjetosFormularioPorUsuarioResult obtenerObjetosFormularioPorUsuarioResult;

    /**
     * Gets the value of the obtenerObjetosFormularioPorUsuarioResult property.
     * 
     * @return
     *     possible object is
     *     {@link ObtenerObjetosFormularioPorUsuarioResponse.ObtenerObjetosFormularioPorUsuarioResult }
     *     
     */
    public ObtenerObjetosFormularioPorUsuarioResponse.ObtenerObjetosFormularioPorUsuarioResult getObtenerObjetosFormularioPorUsuarioResult() {
        return obtenerObjetosFormularioPorUsuarioResult;
    }

    /**
     * Sets the value of the obtenerObjetosFormularioPorUsuarioResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObtenerObjetosFormularioPorUsuarioResponse.ObtenerObjetosFormularioPorUsuarioResult }
     *     
     */
    public void setObtenerObjetosFormularioPorUsuarioResult(ObtenerObjetosFormularioPorUsuarioResponse.ObtenerObjetosFormularioPorUsuarioResult value) {
        this.obtenerObjetosFormularioPorUsuarioResult = value;
    }


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
     *         &lt;element ref="{http://www.w3.org/2001/XMLSchema}schema"/>
     *         &lt;any/>
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
        "schema",
        "any"
    })
    public static class ObtenerObjetosFormularioPorUsuarioResult {

        @XmlElement(namespace = "http://www.w3.org/2001/XMLSchema", required = true)
        protected Schema schema;
        @XmlAnyElement(lax = true)
        protected Object any;

        /**
         * Gets the value of the schema property.
         * 
         * @return
         *     possible object is
         *     {@link Schema }
         *     
         */
        public Schema getSchema() {
            return schema;
        }

        /**
         * Sets the value of the schema property.
         * 
         * @param value
         *     allowed object is
         *     {@link Schema }
         *     
         */
        public void setSchema(Schema value) {
            this.schema = value;
        }

        /**
         * Gets the value of the any property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getAny() {
            return any;
        }

        /**
         * Sets the value of the any property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setAny(Object value) {
            this.any = value;
        }

    }

}

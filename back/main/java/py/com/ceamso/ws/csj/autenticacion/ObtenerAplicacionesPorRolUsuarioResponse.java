
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
 *         &lt;element name="ObtenerAplicacionesPorRolUsuarioResult" minOccurs="0">
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
    "obtenerAplicacionesPorRolUsuarioResult"
})
@XmlRootElement(name = "ObtenerAplicacionesPorRolUsuarioResponse")
public class ObtenerAplicacionesPorRolUsuarioResponse {

    @XmlElement(name = "ObtenerAplicacionesPorRolUsuarioResult")
    protected ObtenerAplicacionesPorRolUsuarioResponse.ObtenerAplicacionesPorRolUsuarioResult obtenerAplicacionesPorRolUsuarioResult;

    /**
     * Gets the value of the obtenerAplicacionesPorRolUsuarioResult property.
     * 
     * @return
     *     possible object is
     *     {@link ObtenerAplicacionesPorRolUsuarioResponse.ObtenerAplicacionesPorRolUsuarioResult }
     *     
     */
    public ObtenerAplicacionesPorRolUsuarioResponse.ObtenerAplicacionesPorRolUsuarioResult getObtenerAplicacionesPorRolUsuarioResult() {
        return obtenerAplicacionesPorRolUsuarioResult;
    }

    /**
     * Sets the value of the obtenerAplicacionesPorRolUsuarioResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObtenerAplicacionesPorRolUsuarioResponse.ObtenerAplicacionesPorRolUsuarioResult }
     *     
     */
    public void setObtenerAplicacionesPorRolUsuarioResult(ObtenerAplicacionesPorRolUsuarioResponse.ObtenerAplicacionesPorRolUsuarioResult value) {
        this.obtenerAplicacionesPorRolUsuarioResult = value;
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
    public static class ObtenerAplicacionesPorRolUsuarioResult {

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

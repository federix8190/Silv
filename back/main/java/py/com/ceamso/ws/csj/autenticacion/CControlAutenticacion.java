
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cControlAutenticacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cControlAutenticacion">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.csj.gov.py/}cUsuarios">
 *       &lt;sequence>
 *         &lt;element name="EstadoUsuario" type="{http://www.csj.gov.py/}TEstadoUsuario"/>
 *         &lt;element name="EstadoAutenticacion" type="{http://www.csj.gov.py/}TEstadoAutenticacion"/>
 *         &lt;element name="VigenciaClave" type="{http://www.csj.gov.py/}VigenciaClave" minOccurs="0"/>
 *         &lt;element name="ResultadoLogin" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="RolAplicacion" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="dsRoles" minOccurs="0">
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
 *         &lt;element name="Formularios" minOccurs="0">
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
 *         &lt;element name="Rol" type="{http://www.csj.gov.py/}cRol" minOccurs="0"/>
 *         &lt;element name="Aplicacion" type="{http://www.csj.gov.py/}cAplicaciones" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cControlAutenticacion", propOrder = {
    "estadoUsuario",
    "estadoAutenticacion",
    "vigenciaClave",
    "resultadoLogin",
    "rolAplicacion",
    "dsRoles",
    "formularios",
    "rol",
    "aplicacion"
})
public class CControlAutenticacion
    extends CUsuarios
{

    @XmlElement(name = "EstadoUsuario", required = true)
    protected TEstadoUsuario estadoUsuario;
    @XmlElement(name = "EstadoAutenticacion", required = true)
    protected TEstadoAutenticacion estadoAutenticacion;
    @XmlElement(name = "VigenciaClave")
    protected VigenciaClave vigenciaClave;
    @XmlElement(name = "ResultadoLogin")
    protected boolean resultadoLogin;
    @XmlElement(name = "RolAplicacion")
    protected Object rolAplicacion;
    protected CControlAutenticacion.DsRoles dsRoles;
    @XmlElement(name = "Formularios")
    protected CControlAutenticacion.Formularios formularios;
    @XmlElement(name = "Rol")
    protected CRol rol;
    @XmlElement(name = "Aplicacion")
    protected CAplicaciones aplicacion;

    /**
     * Gets the value of the estadoUsuario property.
     * 
     * @return
     *     possible object is
     *     {@link TEstadoUsuario }
     *     
     */
    public TEstadoUsuario getEstadoUsuario() {
        return estadoUsuario;
    }

    /**
     * Sets the value of the estadoUsuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link TEstadoUsuario }
     *     
     */
    public void setEstadoUsuario(TEstadoUsuario value) {
        this.estadoUsuario = value;
    }

    /**
     * Gets the value of the estadoAutenticacion property.
     * 
     * @return
     *     possible object is
     *     {@link TEstadoAutenticacion }
     *     
     */
    public TEstadoAutenticacion getEstadoAutenticacion() {
        return estadoAutenticacion;
    }

    /**
     * Sets the value of the estadoAutenticacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TEstadoAutenticacion }
     *     
     */
    public void setEstadoAutenticacion(TEstadoAutenticacion value) {
        this.estadoAutenticacion = value;
    }

    /**
     * Gets the value of the vigenciaClave property.
     * 
     * @return
     *     possible object is
     *     {@link VigenciaClave }
     *     
     */
    public VigenciaClave getVigenciaClave() {
        return vigenciaClave;
    }

    /**
     * Sets the value of the vigenciaClave property.
     * 
     * @param value
     *     allowed object is
     *     {@link VigenciaClave }
     *     
     */
    public void setVigenciaClave(VigenciaClave value) {
        this.vigenciaClave = value;
    }

    /**
     * Gets the value of the resultadoLogin property.
     * 
     */
    public boolean isResultadoLogin() {
        return resultadoLogin;
    }

    /**
     * Sets the value of the resultadoLogin property.
     * 
     */
    public void setResultadoLogin(boolean value) {
        this.resultadoLogin = value;
    }

    /**
     * Gets the value of the rolAplicacion property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getRolAplicacion() {
        return rolAplicacion;
    }

    /**
     * Sets the value of the rolAplicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setRolAplicacion(Object value) {
        this.rolAplicacion = value;
    }

    /**
     * Gets the value of the dsRoles property.
     * 
     * @return
     *     possible object is
     *     {@link CControlAutenticacion.DsRoles }
     *     
     */
    public CControlAutenticacion.DsRoles getDsRoles() {
        return dsRoles;
    }

    /**
     * Sets the value of the dsRoles property.
     * 
     * @param value
     *     allowed object is
     *     {@link CControlAutenticacion.DsRoles }
     *     
     */
    public void setDsRoles(CControlAutenticacion.DsRoles value) {
        this.dsRoles = value;
    }

    /**
     * Gets the value of the formularios property.
     * 
     * @return
     *     possible object is
     *     {@link CControlAutenticacion.Formularios }
     *     
     */
    public CControlAutenticacion.Formularios getFormularios() {
        return formularios;
    }

    /**
     * Sets the value of the formularios property.
     * 
     * @param value
     *     allowed object is
     *     {@link CControlAutenticacion.Formularios }
     *     
     */
    public void setFormularios(CControlAutenticacion.Formularios value) {
        this.formularios = value;
    }

    /**
     * Gets the value of the rol property.
     * 
     * @return
     *     possible object is
     *     {@link CRol }
     *     
     */
    public CRol getRol() {
        return rol;
    }

    /**
     * Sets the value of the rol property.
     * 
     * @param value
     *     allowed object is
     *     {@link CRol }
     *     
     */
    public void setRol(CRol value) {
        this.rol = value;
    }

    /**
     * Gets the value of the aplicacion property.
     * 
     * @return
     *     possible object is
     *     {@link CAplicaciones }
     *     
     */
    public CAplicaciones getAplicacion() {
        return aplicacion;
    }

    /**
     * Sets the value of the aplicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link CAplicaciones }
     *     
     */
    public void setAplicacion(CAplicaciones value) {
        this.aplicacion = value;
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
    public static class DsRoles {

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
    public static class Formularios {

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

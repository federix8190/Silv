
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cAplicaciones complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cAplicaciones">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oAplicacionesAuditado" type="{http://www.csj.gov.py/}ArrayOfCAuditoria" minOccurs="0"/>
 *         &lt;element name="DescripcionAplicacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Siglas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PathAplicacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Credenciales" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoAplicacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cAplicaciones", propOrder = {
    "oAplicacionesAuditado",
    "descripcionAplicacion",
    "siglas",
    "pathAplicacion",
    "credenciales",
    "codigoAplicacion"
})
@XmlSeeAlso({
    APLICACION.class
})
public class CAplicaciones {

    protected ArrayOfCAuditoria oAplicacionesAuditado;
    @XmlElement(name = "DescripcionAplicacion")
    protected String descripcionAplicacion;
    @XmlElement(name = "Siglas")
    protected String siglas;
    @XmlElement(name = "PathAplicacion")
    protected String pathAplicacion;
    @XmlElement(name = "Credenciales")
    protected String credenciales;
    @XmlElement(name = "CodigoAplicacion")
    protected int codigoAplicacion;

    /**
     * Gets the value of the oAplicacionesAuditado property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public ArrayOfCAuditoria getOAplicacionesAuditado() {
        return oAplicacionesAuditado;
    }

    /**
     * Sets the value of the oAplicacionesAuditado property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public void setOAplicacionesAuditado(ArrayOfCAuditoria value) {
        this.oAplicacionesAuditado = value;
    }

    /**
     * Gets the value of the descripcionAplicacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionAplicacion() {
        return descripcionAplicacion;
    }

    /**
     * Sets the value of the descripcionAplicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionAplicacion(String value) {
        this.descripcionAplicacion = value;
    }

    /**
     * Gets the value of the siglas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglas() {
        return siglas;
    }

    /**
     * Sets the value of the siglas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglas(String value) {
        this.siglas = value;
    }

    /**
     * Gets the value of the pathAplicacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathAplicacion() {
        return pathAplicacion;
    }

    /**
     * Sets the value of the pathAplicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathAplicacion(String value) {
        this.pathAplicacion = value;
    }

    /**
     * Gets the value of the credenciales property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCredenciales() {
        return credenciales;
    }

    /**
     * Sets the value of the credenciales property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCredenciales(String value) {
        this.credenciales = value;
    }

    /**
     * Gets the value of the codigoAplicacion property.
     * 
     */
    public int getCodigoAplicacion() {
        return codigoAplicacion;
    }

    /**
     * Sets the value of the codigoAplicacion property.
     * 
     */
    public void setCodigoAplicacion(int value) {
        this.codigoAplicacion = value;
    }

}

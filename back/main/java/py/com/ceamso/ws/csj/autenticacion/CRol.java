
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for cRol complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cRol">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oRolAuditado" type="{http://www.csj.gov.py/}ArrayOfCAuditoria" minOccurs="0"/>
 *         &lt;element name="DescripcionRol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoRol" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DetalleRol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechaRegistro" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="UsuarioRegistrador" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cRol", propOrder = {
    "oRolAuditado",
    "descripcionRol",
    "codigoRol",
    "detalleRol",
    "fechaRegistro",
    "usuarioRegistrador"
})
public class CRol {

    protected ArrayOfCAuditoria oRolAuditado;
    @XmlElement(name = "DescripcionRol")
    protected String descripcionRol;
    @XmlElement(name = "CodigoRol")
    protected int codigoRol;
    @XmlElement(name = "DetalleRol")
    protected String detalleRol;
    @XmlElement(name = "FechaRegistro", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaRegistro;
    @XmlElement(name = "UsuarioRegistrador")
    protected int usuarioRegistrador;

    /**
     * Gets the value of the oRolAuditado property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public ArrayOfCAuditoria getORolAuditado() {
        return oRolAuditado;
    }

    /**
     * Sets the value of the oRolAuditado property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public void setORolAuditado(ArrayOfCAuditoria value) {
        this.oRolAuditado = value;
    }

    /**
     * Gets the value of the descripcionRol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionRol() {
        return descripcionRol;
    }

    /**
     * Sets the value of the descripcionRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionRol(String value) {
        this.descripcionRol = value;
    }

    /**
     * Gets the value of the codigoRol property.
     * 
     */
    public int getCodigoRol() {
        return codigoRol;
    }

    /**
     * Sets the value of the codigoRol property.
     * 
     */
    public void setCodigoRol(int value) {
        this.codigoRol = value;
    }

    /**
     * Gets the value of the detalleRol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetalleRol() {
        return detalleRol;
    }

    /**
     * Sets the value of the detalleRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetalleRol(String value) {
        this.detalleRol = value;
    }

    /**
     * Gets the value of the fechaRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Sets the value of the fechaRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaRegistro(XMLGregorianCalendar value) {
        this.fechaRegistro = value;
    }

    /**
     * Gets the value of the usuarioRegistrador property.
     * 
     */
    public int getUsuarioRegistrador() {
        return usuarioRegistrador;
    }

    /**
     * Sets the value of the usuarioRegistrador property.
     * 
     */
    public void setUsuarioRegistrador(int value) {
        this.usuarioRegistrador = value;
    }

}

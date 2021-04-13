
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for cObjetos complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cObjetos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oObjetosAuditado" type="{http://www.csj.gov.py/}ArrayOfCAuditoria" minOccurs="0"/>
 *         &lt;element name="Visible" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SoloLectura" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CodigoObjeto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CodigoFormulario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DescripcionObjeto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "cObjetos", propOrder = {
    "oObjetosAuditado",
    "visible",
    "soloLectura",
    "codigoObjeto",
    "codigoFormulario",
    "descripcionObjeto",
    "fechaRegistro",
    "usuarioRegistrador"
})
public class CObjetos {

    protected ArrayOfCAuditoria oObjetosAuditado;
    @XmlElement(name = "Visible")
    protected boolean visible;
    @XmlElement(name = "SoloLectura")
    protected boolean soloLectura;
    @XmlElement(name = "CodigoObjeto")
    protected int codigoObjeto;
    @XmlElement(name = "CodigoFormulario")
    protected int codigoFormulario;
    @XmlElement(name = "DescripcionObjeto")
    protected String descripcionObjeto;
    @XmlElement(name = "FechaRegistro", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaRegistro;
    @XmlElement(name = "UsuarioRegistrador")
    protected int usuarioRegistrador;

    /**
     * Gets the value of the oObjetosAuditado property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public ArrayOfCAuditoria getOObjetosAuditado() {
        return oObjetosAuditado;
    }

    /**
     * Sets the value of the oObjetosAuditado property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public void setOObjetosAuditado(ArrayOfCAuditoria value) {
        this.oObjetosAuditado = value;
    }

    /**
     * Gets the value of the visible property.
     * 
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the value of the visible property.
     * 
     */
    public void setVisible(boolean value) {
        this.visible = value;
    }

    /**
     * Gets the value of the soloLectura property.
     * 
     */
    public boolean isSoloLectura() {
        return soloLectura;
    }

    /**
     * Sets the value of the soloLectura property.
     * 
     */
    public void setSoloLectura(boolean value) {
        this.soloLectura = value;
    }

    /**
     * Gets the value of the codigoObjeto property.
     * 
     */
    public int getCodigoObjeto() {
        return codigoObjeto;
    }

    /**
     * Sets the value of the codigoObjeto property.
     * 
     */
    public void setCodigoObjeto(int value) {
        this.codigoObjeto = value;
    }

    /**
     * Gets the value of the codigoFormulario property.
     * 
     */
    public int getCodigoFormulario() {
        return codigoFormulario;
    }

    /**
     * Sets the value of the codigoFormulario property.
     * 
     */
    public void setCodigoFormulario(int value) {
        this.codigoFormulario = value;
    }

    /**
     * Gets the value of the descripcionObjeto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionObjeto() {
        return descripcionObjeto;
    }

    /**
     * Sets the value of the descripcionObjeto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionObjeto(String value) {
        this.descripcionObjeto = value;
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

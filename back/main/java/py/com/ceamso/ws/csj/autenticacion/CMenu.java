
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for cMenu complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cMenu">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oMenuAuditado" type="{http://www.csj.gov.py/}ArrayOfCAuditoria" minOccurs="0"/>
 *         &lt;element name="Acceso" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CodigoMenu" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CodigoFormulario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DescripcionMenu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "cMenu", propOrder = {
    "oMenuAuditado",
    "acceso",
    "codigoMenu",
    "codigoFormulario",
    "descripcionMenu",
    "fechaRegistro",
    "usuarioRegistrador"
})
public class CMenu {

    protected ArrayOfCAuditoria oMenuAuditado;
    @XmlElement(name = "Acceso")
    protected boolean acceso;
    @XmlElement(name = "CodigoMenu")
    protected int codigoMenu;
    @XmlElement(name = "CodigoFormulario")
    protected int codigoFormulario;
    @XmlElement(name = "DescripcionMenu")
    protected String descripcionMenu;
    @XmlElement(name = "FechaRegistro", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaRegistro;
    @XmlElement(name = "UsuarioRegistrador")
    protected int usuarioRegistrador;

    /**
     * Gets the value of the oMenuAuditado property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public ArrayOfCAuditoria getOMenuAuditado() {
        return oMenuAuditado;
    }

    /**
     * Sets the value of the oMenuAuditado property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public void setOMenuAuditado(ArrayOfCAuditoria value) {
        this.oMenuAuditado = value;
    }

    /**
     * Gets the value of the acceso property.
     * 
     */
    public boolean isAcceso() {
        return acceso;
    }

    /**
     * Sets the value of the acceso property.
     * 
     */
    public void setAcceso(boolean value) {
        this.acceso = value;
    }

    /**
     * Gets the value of the codigoMenu property.
     * 
     */
    public int getCodigoMenu() {
        return codigoMenu;
    }

    /**
     * Sets the value of the codigoMenu property.
     * 
     */
    public void setCodigoMenu(int value) {
        this.codigoMenu = value;
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
     * Gets the value of the descripcionMenu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionMenu() {
        return descripcionMenu;
    }

    /**
     * Sets the value of the descripcionMenu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionMenu(String value) {
        this.descripcionMenu = value;
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

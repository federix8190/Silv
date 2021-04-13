
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for cUsuarios complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cUsuarios">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oUsuariosAuditado" type="{http://www.csj.gov.py/}ArrayOfCAuditoria" minOccurs="0"/>
 *         &lt;element name="Estado" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Usuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Clave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Apellido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumeroDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sexo" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="Foto" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="UsuarioRegistrador" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Telefono1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Telefono2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechaRegistro" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FechaActualizacion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CodigoCargo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CodigoUsuario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DescripcionCargo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cert" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cUsuarios", propOrder = {
    "oUsuariosAuditado",
    "estado",
    "usuario",
    "clave",
    "nombre",
    "apellido",
    "numeroDocumento",
    "email",
    "sexo",
    "foto",
    "usuarioRegistrador",
    "telefono1",
    "telefono2",
    "fechaRegistro",
    "fechaActualizacion",
    "codigoCargo",
    "codigoUsuario",
    "descripcionCargo",
    "cert"
})
@XmlSeeAlso({
    CControlAutenticacion.class
})
public class CUsuarios {

    protected ArrayOfCAuditoria oUsuariosAuditado;
    @XmlElement(name = "Estado")
    protected int estado;
    @XmlElement(name = "Usuario")
    protected String usuario;
    @XmlElement(name = "Clave")
    protected String clave;
    @XmlElement(name = "Nombre")
    protected String nombre;
    @XmlElement(name = "Apellido")
    protected String apellido;
    @XmlElement(name = "NumeroDocumento")
    protected String numeroDocumento;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "Sexo")
    @XmlSchemaType(name = "unsignedByte")
    protected short sexo;
    @XmlElement(name = "Foto")
    protected byte[] foto;
    @XmlElement(name = "UsuarioRegistrador")
    protected int usuarioRegistrador;
    @XmlElement(name = "Telefono1")
    protected String telefono1;
    @XmlElement(name = "Telefono2")
    protected String telefono2;
    @XmlElement(name = "FechaRegistro", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaRegistro;
    @XmlElement(name = "FechaActualizacion", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaActualizacion;
    @XmlElement(name = "CodigoCargo")
    protected int codigoCargo;
    @XmlElement(name = "CodigoUsuario")
    protected int codigoUsuario;
    @XmlElement(name = "DescripcionCargo")
    protected String descripcionCargo;
    @XmlElement(name = "Cert")
    protected byte[] cert;

    /**
     * Gets the value of the oUsuariosAuditado property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public ArrayOfCAuditoria getOUsuariosAuditado() {
        return oUsuariosAuditado;
    }

    /**
     * Sets the value of the oUsuariosAuditado property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public void setOUsuariosAuditado(ArrayOfCAuditoria value) {
        this.oUsuariosAuditado = value;
    }

    /**
     * Gets the value of the estado property.
     * 
     */
    public int getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     */
    public void setEstado(int value) {
        this.estado = value;
    }

    /**
     * Gets the value of the usuario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuario(String value) {
        this.usuario = value;
    }

    /**
     * Gets the value of the clave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClave() {
        return clave;
    }

    /**
     * Sets the value of the clave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClave(String value) {
        this.clave = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the apellido property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Sets the value of the apellido property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellido(String value) {
        this.apellido = value;
    }

    /**
     * Gets the value of the numeroDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Sets the value of the numeroDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroDocumento(String value) {
        this.numeroDocumento = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the sexo property.
     * 
     */
    public short getSexo() {
        return sexo;
    }

    /**
     * Sets the value of the sexo property.
     * 
     */
    public void setSexo(short value) {
        this.sexo = value;
    }

    /**
     * Gets the value of the foto property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFoto() {
        return foto;
    }

    /**
     * Sets the value of the foto property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFoto(byte[] value) {
        this.foto = value;
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

    /**
     * Gets the value of the telefono1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono1() {
        return telefono1;
    }

    /**
     * Sets the value of the telefono1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono1(String value) {
        this.telefono1 = value;
    }

    /**
     * Gets the value of the telefono2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono2() {
        return telefono2;
    }

    /**
     * Sets the value of the telefono2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono2(String value) {
        this.telefono2 = value;
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
     * Gets the value of the fechaActualizacion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * Sets the value of the fechaActualizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaActualizacion(XMLGregorianCalendar value) {
        this.fechaActualizacion = value;
    }

    /**
     * Gets the value of the codigoCargo property.
     * 
     */
    public int getCodigoCargo() {
        return codigoCargo;
    }

    /**
     * Sets the value of the codigoCargo property.
     * 
     */
    public void setCodigoCargo(int value) {
        this.codigoCargo = value;
    }

    /**
     * Gets the value of the codigoUsuario property.
     * 
     */
    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    /**
     * Sets the value of the codigoUsuario property.
     * 
     */
    public void setCodigoUsuario(int value) {
        this.codigoUsuario = value;
    }

    /**
     * Gets the value of the descripcionCargo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    /**
     * Sets the value of the descripcionCargo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionCargo(String value) {
        this.descripcionCargo = value;
    }

    /**
     * Gets the value of the cert property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getCert() {
        return cert;
    }

    /**
     * Sets the value of the cert property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setCert(byte[] value) {
        this.cert = value;
    }

}

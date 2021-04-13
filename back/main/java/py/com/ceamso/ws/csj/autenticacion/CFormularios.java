
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for cFormularios complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cFormularios">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oFormulariosAuditado" type="{http://www.csj.gov.py/}ArrayOfCAuditoria" minOccurs="0"/>
 *         &lt;element name="Agregar" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Editar" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Consultar" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Borrar" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Imprimir" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Exportar" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CodigoFormulario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DescripcionFormulario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechaRegistro" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="UsuarioRegistrador" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "cFormularios", propOrder = {
    "oFormulariosAuditado",
    "agregar",
    "editar",
    "consultar",
    "borrar",
    "imprimir",
    "exportar",
    "codigoFormulario",
    "descripcionFormulario",
    "fechaRegistro",
    "usuarioRegistrador",
    "codigoAplicacion"
})
@XmlSeeAlso({
    CFormularioMantenimientoNormas.class,
    CFormularioMantenimientoGacetas.class
})
public class CFormularios {

    protected ArrayOfCAuditoria oFormulariosAuditado;
    @XmlElement(name = "Agregar")
    protected boolean agregar;
    @XmlElement(name = "Editar")
    protected boolean editar;
    @XmlElement(name = "Consultar")
    protected boolean consultar;
    @XmlElement(name = "Borrar")
    protected boolean borrar;
    @XmlElement(name = "Imprimir")
    protected boolean imprimir;
    @XmlElement(name = "Exportar")
    protected boolean exportar;
    @XmlElement(name = "CodigoFormulario")
    protected int codigoFormulario;
    @XmlElement(name = "DescripcionFormulario")
    protected String descripcionFormulario;
    @XmlElement(name = "FechaRegistro", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaRegistro;
    @XmlElement(name = "UsuarioRegistrador")
    protected int usuarioRegistrador;
    @XmlElement(name = "CodigoAplicacion")
    protected int codigoAplicacion;

    /**
     * Gets the value of the oFormulariosAuditado property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public ArrayOfCAuditoria getOFormulariosAuditado() {
        return oFormulariosAuditado;
    }

    /**
     * Sets the value of the oFormulariosAuditado property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public void setOFormulariosAuditado(ArrayOfCAuditoria value) {
        this.oFormulariosAuditado = value;
    }

    /**
     * Gets the value of the agregar property.
     * 
     */
    public boolean isAgregar() {
        return agregar;
    }

    /**
     * Sets the value of the agregar property.
     * 
     */
    public void setAgregar(boolean value) {
        this.agregar = value;
    }

    /**
     * Gets the value of the editar property.
     * 
     */
    public boolean isEditar() {
        return editar;
    }

    /**
     * Sets the value of the editar property.
     * 
     */
    public void setEditar(boolean value) {
        this.editar = value;
    }

    /**
     * Gets the value of the consultar property.
     * 
     */
    public boolean isConsultar() {
        return consultar;
    }

    /**
     * Sets the value of the consultar property.
     * 
     */
    public void setConsultar(boolean value) {
        this.consultar = value;
    }

    /**
     * Gets the value of the borrar property.
     * 
     */
    public boolean isBorrar() {
        return borrar;
    }

    /**
     * Sets the value of the borrar property.
     * 
     */
    public void setBorrar(boolean value) {
        this.borrar = value;
    }

    /**
     * Gets the value of the imprimir property.
     * 
     */
    public boolean isImprimir() {
        return imprimir;
    }

    /**
     * Sets the value of the imprimir property.
     * 
     */
    public void setImprimir(boolean value) {
        this.imprimir = value;
    }

    /**
     * Gets the value of the exportar property.
     * 
     */
    public boolean isExportar() {
        return exportar;
    }

    /**
     * Sets the value of the exportar property.
     * 
     */
    public void setExportar(boolean value) {
        this.exportar = value;
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
     * Gets the value of the descripcionFormulario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionFormulario() {
        return descripcionFormulario;
    }

    /**
     * Sets the value of the descripcionFormulario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionFormulario(String value) {
        this.descripcionFormulario = value;
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

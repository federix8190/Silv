
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for VigenciaClave complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VigenciaClave">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Estado" type="{http://www.csj.gov.py/}EstadoVigenciaClave"/>
 *         &lt;element name="DiasVigencia" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FechaUltimaModificacion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VigenciaClave", propOrder = {
    "estado",
    "diasVigencia",
    "fechaUltimaModificacion"
})
public class VigenciaClave {

    @XmlElement(name = "Estado", required = true)
    protected EstadoVigenciaClave estado;
    @XmlElement(name = "DiasVigencia")
    protected int diasVigencia;
    @XmlElement(name = "FechaUltimaModificacion", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaUltimaModificacion;

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link EstadoVigenciaClave }
     *     
     */
    public EstadoVigenciaClave getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoVigenciaClave }
     *     
     */
    public void setEstado(EstadoVigenciaClave value) {
        this.estado = value;
    }

    /**
     * Gets the value of the diasVigencia property.
     * 
     */
    public int getDiasVigencia() {
        return diasVigencia;
    }

    /**
     * Sets the value of the diasVigencia property.
     * 
     */
    public void setDiasVigencia(int value) {
        this.diasVigencia = value;
    }

    /**
     * Gets the value of the fechaUltimaModificacion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    /**
     * Sets the value of the fechaUltimaModificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaUltimaModificacion(XMLGregorianCalendar value) {
        this.fechaUltimaModificacion = value;
    }

}


package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cCircunscripcion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cCircunscripcion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oCircunscripcionAuditado" type="{http://www.csj.gov.py/}ArrayOfCAuditoria" minOccurs="0"/>
 *         &lt;element name="NombreCircunscripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CadenaConexion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoCircunscripcion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cCircunscripcion", propOrder = {
    "oCircunscripcionAuditado",
    "nombreCircunscripcion",
    "cadenaConexion",
    "codigoCircunscripcion"
})
public class CCircunscripcion {

    protected ArrayOfCAuditoria oCircunscripcionAuditado;
    @XmlElement(name = "NombreCircunscripcion")
    protected String nombreCircunscripcion;
    @XmlElement(name = "CadenaConexion")
    protected String cadenaConexion;
    @XmlElement(name = "CodigoCircunscripcion")
    protected int codigoCircunscripcion;

    /**
     * Gets the value of the oCircunscripcionAuditado property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public ArrayOfCAuditoria getOCircunscripcionAuditado() {
        return oCircunscripcionAuditado;
    }

    /**
     * Sets the value of the oCircunscripcionAuditado property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCAuditoria }
     *     
     */
    public void setOCircunscripcionAuditado(ArrayOfCAuditoria value) {
        this.oCircunscripcionAuditado = value;
    }

    /**
     * Gets the value of the nombreCircunscripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreCircunscripcion() {
        return nombreCircunscripcion;
    }

    /**
     * Sets the value of the nombreCircunscripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreCircunscripcion(String value) {
        this.nombreCircunscripcion = value;
    }

    /**
     * Gets the value of the cadenaConexion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCadenaConexion() {
        return cadenaConexion;
    }

    /**
     * Sets the value of the cadenaConexion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCadenaConexion(String value) {
        this.cadenaConexion = value;
    }

    /**
     * Gets the value of the codigoCircunscripcion property.
     * 
     */
    public int getCodigoCircunscripcion() {
        return codigoCircunscripcion;
    }

    /**
     * Sets the value of the codigoCircunscripcion property.
     * 
     */
    public void setCodigoCircunscripcion(int value) {
        this.codigoCircunscripcion = value;
    }

}

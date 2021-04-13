
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="CodigoUsuario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CodigoRol" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CodigoAplicacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SIGLAS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "codigoUsuario",
    "codigoRol",
    "codigoAplicacion",
    "siglas"
})
@XmlRootElement(name = "ObtenerClaseAtributoAplicacion")
public class ObtenerClaseAtributoAplicacion {

    @XmlElement(name = "CodigoUsuario")
    protected int codigoUsuario;
    @XmlElement(name = "CodigoRol")
    protected int codigoRol;
    @XmlElement(name = "CodigoAplicacion")
    protected int codigoAplicacion;
    @XmlElement(name = "SIGLAS")
    protected String siglas;

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

    /**
     * Gets the value of the siglas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSIGLAS() {
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
    public void setSIGLAS(String value) {
        this.siglas = value;
    }

}

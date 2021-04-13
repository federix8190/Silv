
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cFormularioMantenimientoGacetas complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cFormularioMantenimientoGacetas">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.csj.gov.py/}cFormularios">
 *       &lt;sequence>
 *         &lt;element name="MenuInicio" type="{http://www.csj.gov.py/}cMenu" minOccurs="0"/>
 *         &lt;element name="MenuRegistro" type="{http://www.csj.gov.py/}cMenu" minOccurs="0"/>
 *         &lt;element name="CampoGaceta" type="{http://www.csj.gov.py/}cCampo" minOccurs="0"/>
 *         &lt;element name="CampoTipo" type="{http://www.csj.gov.py/}cCampo" minOccurs="0"/>
 *         &lt;element name="CampoDocumento" type="{http://www.csj.gov.py/}cCampo" minOccurs="0"/>
 *         &lt;element name="CampoFechaNorma" type="{http://www.csj.gov.py/}cCampo" minOccurs="0"/>
 *         &lt;element name="CampoEstadoPublicacion" type="{http://www.csj.gov.py/}cCampo" minOccurs="0"/>
 *         &lt;element name="CampoInstituciones" type="{http://www.csj.gov.py/}cCampo" minOccurs="0"/>
 *         &lt;element name="CampoFechaPublicacion" type="{http://www.csj.gov.py/}cCampo" minOccurs="0"/>
 *         &lt;element name="CampoFechaEntradaVigencia" type="{http://www.csj.gov.py/}cCampo" minOccurs="0"/>
 *         &lt;element name="CampoFechaPromulgacion" type="{http://www.csj.gov.py/}cCampo" minOccurs="0"/>
 *         &lt;element name="CampoRegistrador" type="{http://www.csj.gov.py/}cCampo" minOccurs="0"/>
 *         &lt;element name="CampoFechaRegistro" type="{http://www.csj.gov.py/}cCampo" minOccurs="0"/>
 *         &lt;element name="ObjetoCambiarDocumento" type="{http://www.csj.gov.py/}cObjetos" minOccurs="0"/>
 *         &lt;element name="ObjetoEliminarRegistro" type="{http://www.csj.gov.py/}cObjetos" minOccurs="0"/>
 *         &lt;element name="ObjetoNavegacion" type="{http://www.csj.gov.py/}cObjetos" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cFormularioMantenimientoGacetas", propOrder = {
    "menuInicio",
    "menuRegistro",
    "campoGaceta",
    "campoTipo",
    "campoDocumento",
    "campoFechaNorma",
    "campoEstadoPublicacion",
    "campoInstituciones",
    "campoFechaPublicacion",
    "campoFechaEntradaVigencia",
    "campoFechaPromulgacion",
    "campoRegistrador",
    "campoFechaRegistro",
    "objetoCambiarDocumento",
    "objetoEliminarRegistro",
    "objetoNavegacion"
})
public class CFormularioMantenimientoGacetas
    extends CFormularios
{

    @XmlElement(name = "MenuInicio")
    protected CMenu menuInicio;
    @XmlElement(name = "MenuRegistro")
    protected CMenu menuRegistro;
    @XmlElement(name = "CampoGaceta")
    protected CCampo campoGaceta;
    @XmlElement(name = "CampoTipo")
    protected CCampo campoTipo;
    @XmlElement(name = "CampoDocumento")
    protected CCampo campoDocumento;
    @XmlElement(name = "CampoFechaNorma")
    protected CCampo campoFechaNorma;
    @XmlElement(name = "CampoEstadoPublicacion")
    protected CCampo campoEstadoPublicacion;
    @XmlElement(name = "CampoInstituciones")
    protected CCampo campoInstituciones;
    @XmlElement(name = "CampoFechaPublicacion")
    protected CCampo campoFechaPublicacion;
    @XmlElement(name = "CampoFechaEntradaVigencia")
    protected CCampo campoFechaEntradaVigencia;
    @XmlElement(name = "CampoFechaPromulgacion")
    protected CCampo campoFechaPromulgacion;
    @XmlElement(name = "CampoRegistrador")
    protected CCampo campoRegistrador;
    @XmlElement(name = "CampoFechaRegistro")
    protected CCampo campoFechaRegistro;
    @XmlElement(name = "ObjetoCambiarDocumento")
    protected CObjetos objetoCambiarDocumento;
    @XmlElement(name = "ObjetoEliminarRegistro")
    protected CObjetos objetoEliminarRegistro;
    @XmlElement(name = "ObjetoNavegacion")
    protected CObjetos objetoNavegacion;

    /**
     * Gets the value of the menuInicio property.
     * 
     * @return
     *     possible object is
     *     {@link CMenu }
     *     
     */
    public CMenu getMenuInicio() {
        return menuInicio;
    }

    /**
     * Sets the value of the menuInicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link CMenu }
     *     
     */
    public void setMenuInicio(CMenu value) {
        this.menuInicio = value;
    }

    /**
     * Gets the value of the menuRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link CMenu }
     *     
     */
    public CMenu getMenuRegistro() {
        return menuRegistro;
    }

    /**
     * Sets the value of the menuRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link CMenu }
     *     
     */
    public void setMenuRegistro(CMenu value) {
        this.menuRegistro = value;
    }

    /**
     * Gets the value of the campoGaceta property.
     * 
     * @return
     *     possible object is
     *     {@link CCampo }
     *     
     */
    public CCampo getCampoGaceta() {
        return campoGaceta;
    }

    /**
     * Sets the value of the campoGaceta property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCampo }
     *     
     */
    public void setCampoGaceta(CCampo value) {
        this.campoGaceta = value;
    }

    /**
     * Gets the value of the campoTipo property.
     * 
     * @return
     *     possible object is
     *     {@link CCampo }
     *     
     */
    public CCampo getCampoTipo() {
        return campoTipo;
    }

    /**
     * Sets the value of the campoTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCampo }
     *     
     */
    public void setCampoTipo(CCampo value) {
        this.campoTipo = value;
    }

    /**
     * Gets the value of the campoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link CCampo }
     *     
     */
    public CCampo getCampoDocumento() {
        return campoDocumento;
    }

    /**
     * Sets the value of the campoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCampo }
     *     
     */
    public void setCampoDocumento(CCampo value) {
        this.campoDocumento = value;
    }

    /**
     * Gets the value of the campoFechaNorma property.
     * 
     * @return
     *     possible object is
     *     {@link CCampo }
     *     
     */
    public CCampo getCampoFechaNorma() {
        return campoFechaNorma;
    }

    /**
     * Sets the value of the campoFechaNorma property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCampo }
     *     
     */
    public void setCampoFechaNorma(CCampo value) {
        this.campoFechaNorma = value;
    }

    /**
     * Gets the value of the campoEstadoPublicacion property.
     * 
     * @return
     *     possible object is
     *     {@link CCampo }
     *     
     */
    public CCampo getCampoEstadoPublicacion() {
        return campoEstadoPublicacion;
    }

    /**
     * Sets the value of the campoEstadoPublicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCampo }
     *     
     */
    public void setCampoEstadoPublicacion(CCampo value) {
        this.campoEstadoPublicacion = value;
    }

    /**
     * Gets the value of the campoInstituciones property.
     * 
     * @return
     *     possible object is
     *     {@link CCampo }
     *     
     */
    public CCampo getCampoInstituciones() {
        return campoInstituciones;
    }

    /**
     * Sets the value of the campoInstituciones property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCampo }
     *     
     */
    public void setCampoInstituciones(CCampo value) {
        this.campoInstituciones = value;
    }

    /**
     * Gets the value of the campoFechaPublicacion property.
     * 
     * @return
     *     possible object is
     *     {@link CCampo }
     *     
     */
    public CCampo getCampoFechaPublicacion() {
        return campoFechaPublicacion;
    }

    /**
     * Sets the value of the campoFechaPublicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCampo }
     *     
     */
    public void setCampoFechaPublicacion(CCampo value) {
        this.campoFechaPublicacion = value;
    }

    /**
     * Gets the value of the campoFechaEntradaVigencia property.
     * 
     * @return
     *     possible object is
     *     {@link CCampo }
     *     
     */
    public CCampo getCampoFechaEntradaVigencia() {
        return campoFechaEntradaVigencia;
    }

    /**
     * Sets the value of the campoFechaEntradaVigencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCampo }
     *     
     */
    public void setCampoFechaEntradaVigencia(CCampo value) {
        this.campoFechaEntradaVigencia = value;
    }

    /**
     * Gets the value of the campoFechaPromulgacion property.
     * 
     * @return
     *     possible object is
     *     {@link CCampo }
     *     
     */
    public CCampo getCampoFechaPromulgacion() {
        return campoFechaPromulgacion;
    }

    /**
     * Sets the value of the campoFechaPromulgacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCampo }
     *     
     */
    public void setCampoFechaPromulgacion(CCampo value) {
        this.campoFechaPromulgacion = value;
    }

    /**
     * Gets the value of the campoRegistrador property.
     * 
     * @return
     *     possible object is
     *     {@link CCampo }
     *     
     */
    public CCampo getCampoRegistrador() {
        return campoRegistrador;
    }

    /**
     * Sets the value of the campoRegistrador property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCampo }
     *     
     */
    public void setCampoRegistrador(CCampo value) {
        this.campoRegistrador = value;
    }

    /**
     * Gets the value of the campoFechaRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link CCampo }
     *     
     */
    public CCampo getCampoFechaRegistro() {
        return campoFechaRegistro;
    }

    /**
     * Sets the value of the campoFechaRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCampo }
     *     
     */
    public void setCampoFechaRegistro(CCampo value) {
        this.campoFechaRegistro = value;
    }

    /**
     * Gets the value of the objetoCambiarDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link CObjetos }
     *     
     */
    public CObjetos getObjetoCambiarDocumento() {
        return objetoCambiarDocumento;
    }

    /**
     * Sets the value of the objetoCambiarDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link CObjetos }
     *     
     */
    public void setObjetoCambiarDocumento(CObjetos value) {
        this.objetoCambiarDocumento = value;
    }

    /**
     * Gets the value of the objetoEliminarRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link CObjetos }
     *     
     */
    public CObjetos getObjetoEliminarRegistro() {
        return objetoEliminarRegistro;
    }

    /**
     * Sets the value of the objetoEliminarRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link CObjetos }
     *     
     */
    public void setObjetoEliminarRegistro(CObjetos value) {
        this.objetoEliminarRegistro = value;
    }

    /**
     * Gets the value of the objetoNavegacion property.
     * 
     * @return
     *     possible object is
     *     {@link CObjetos }
     *     
     */
    public CObjetos getObjetoNavegacion() {
        return objetoNavegacion;
    }

    /**
     * Sets the value of the objetoNavegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link CObjetos }
     *     
     */
    public void setObjetoNavegacion(CObjetos value) {
        this.objetoNavegacion = value;
    }

}

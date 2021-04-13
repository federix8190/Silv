
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cFormularioMantenimientoNormas complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cFormularioMantenimientoNormas">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.csj.gov.py/}cFormularios">
 *       &lt;sequence>
 *         &lt;element name="MenuConsulta" type="{http://www.csj.gov.py/}cMenu" minOccurs="0"/>
 *         &lt;element name="CampoFechadePublicacion" type="{http://www.csj.gov.py/}cCampo" minOccurs="0"/>
 *         &lt;element name="CampoDescripcion" type="{http://www.csj.gov.py/}cCampo" minOccurs="0"/>
 *         &lt;element name="ObjetoOpcionPublicar" type="{http://www.csj.gov.py/}cObjetos" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cFormularioMantenimientoNormas", propOrder = {
    "menuConsulta",
    "campoFechadePublicacion",
    "campoDescripcion",
    "objetoOpcionPublicar"
})
public class CFormularioMantenimientoNormas
    extends CFormularios
{

    @XmlElement(name = "MenuConsulta")
    protected CMenu menuConsulta;
    @XmlElement(name = "CampoFechadePublicacion")
    protected CCampo campoFechadePublicacion;
    @XmlElement(name = "CampoDescripcion")
    protected CCampo campoDescripcion;
    @XmlElement(name = "ObjetoOpcionPublicar")
    protected CObjetos objetoOpcionPublicar;

    /**
     * Gets the value of the menuConsulta property.
     * 
     * @return
     *     possible object is
     *     {@link CMenu }
     *     
     */
    public CMenu getMenuConsulta() {
        return menuConsulta;
    }

    /**
     * Sets the value of the menuConsulta property.
     * 
     * @param value
     *     allowed object is
     *     {@link CMenu }
     *     
     */
    public void setMenuConsulta(CMenu value) {
        this.menuConsulta = value;
    }

    /**
     * Gets the value of the campoFechadePublicacion property.
     * 
     * @return
     *     possible object is
     *     {@link CCampo }
     *     
     */
    public CCampo getCampoFechadePublicacion() {
        return campoFechadePublicacion;
    }

    /**
     * Sets the value of the campoFechadePublicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCampo }
     *     
     */
    public void setCampoFechadePublicacion(CCampo value) {
        this.campoFechadePublicacion = value;
    }

    /**
     * Gets the value of the campoDescripcion property.
     * 
     * @return
     *     possible object is
     *     {@link CCampo }
     *     
     */
    public CCampo getCampoDescripcion() {
        return campoDescripcion;
    }

    /**
     * Sets the value of the campoDescripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCampo }
     *     
     */
    public void setCampoDescripcion(CCampo value) {
        this.campoDescripcion = value;
    }

    /**
     * Gets the value of the objetoOpcionPublicar property.
     * 
     * @return
     *     possible object is
     *     {@link CObjetos }
     *     
     */
    public CObjetos getObjetoOpcionPublicar() {
        return objetoOpcionPublicar;
    }

    /**
     * Sets the value of the objetoOpcionPublicar property.
     * 
     * @param value
     *     allowed object is
     *     {@link CObjetos }
     *     
     */
    public void setObjetoOpcionPublicar(CObjetos value) {
        this.objetoOpcionPublicar = value;
    }

}

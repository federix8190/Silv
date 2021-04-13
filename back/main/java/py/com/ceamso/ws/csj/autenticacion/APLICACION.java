
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for APLICACION complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="APLICACION">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.csj.gov.py/}cAplicaciones">
 *       &lt;sequence>
 *         &lt;element name="pFormularioMantenimientoGacetas" type="{http://www.csj.gov.py/}cFormularioMantenimientoGacetas" minOccurs="0"/>
 *         &lt;element name="pFormularioMantenimientoNormas" type="{http://www.csj.gov.py/}cFormularioMantenimientoNormas" minOccurs="0"/>
 *         &lt;element name="FormularioMantenimientoGacetas" type="{http://www.csj.gov.py/}cFormularioMantenimientoGacetas" minOccurs="0"/>
 *         &lt;element name="FormularioMantenimientoNormas" type="{http://www.csj.gov.py/}cFormularioMantenimientoNormas" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APLICACION", propOrder = {
    "pFormularioMantenimientoGacetas",
    "pFormularioMantenimientoNormas",
    "formularioMantenimientoGacetas",
    "formularioMantenimientoNormas"
})
public class APLICACION
    extends CAplicaciones
{

    protected CFormularioMantenimientoGacetas pFormularioMantenimientoGacetas;
    protected CFormularioMantenimientoNormas pFormularioMantenimientoNormas;
    @XmlElement(name = "FormularioMantenimientoGacetas")
    protected CFormularioMantenimientoGacetas formularioMantenimientoGacetas;
    @XmlElement(name = "FormularioMantenimientoNormas")
    protected CFormularioMantenimientoNormas formularioMantenimientoNormas;

    /**
     * Gets the value of the pFormularioMantenimientoGacetas property.
     * 
     * @return
     *     possible object is
     *     {@link CFormularioMantenimientoGacetas }
     *     
     */
    public CFormularioMantenimientoGacetas getPFormularioMantenimientoGacetas() {
        return pFormularioMantenimientoGacetas;
    }

    /**
     * Sets the value of the pFormularioMantenimientoGacetas property.
     * 
     * @param value
     *     allowed object is
     *     {@link CFormularioMantenimientoGacetas }
     *     
     */
    public void setPFormularioMantenimientoGacetas(CFormularioMantenimientoGacetas value) {
        this.pFormularioMantenimientoGacetas = value;
    }

    /**
     * Gets the value of the pFormularioMantenimientoNormas property.
     * 
     * @return
     *     possible object is
     *     {@link CFormularioMantenimientoNormas }
     *     
     */
    public CFormularioMantenimientoNormas getPFormularioMantenimientoNormas() {
        return pFormularioMantenimientoNormas;
    }

    /**
     * Sets the value of the pFormularioMantenimientoNormas property.
     * 
     * @param value
     *     allowed object is
     *     {@link CFormularioMantenimientoNormas }
     *     
     */
    public void setPFormularioMantenimientoNormas(CFormularioMantenimientoNormas value) {
        this.pFormularioMantenimientoNormas = value;
    }

    /**
     * Gets the value of the formularioMantenimientoGacetas property.
     * 
     * @return
     *     possible object is
     *     {@link CFormularioMantenimientoGacetas }
     *     
     */
    public CFormularioMantenimientoGacetas getFormularioMantenimientoGacetas() {
        return formularioMantenimientoGacetas;
    }

    /**
     * Sets the value of the formularioMantenimientoGacetas property.
     * 
     * @param value
     *     allowed object is
     *     {@link CFormularioMantenimientoGacetas }
     *     
     */
    public void setFormularioMantenimientoGacetas(CFormularioMantenimientoGacetas value) {
        this.formularioMantenimientoGacetas = value;
    }

    /**
     * Gets the value of the formularioMantenimientoNormas property.
     * 
     * @return
     *     possible object is
     *     {@link CFormularioMantenimientoNormas }
     *     
     */
    public CFormularioMantenimientoNormas getFormularioMantenimientoNormas() {
        return formularioMantenimientoNormas;
    }

    /**
     * Sets the value of the formularioMantenimientoNormas property.
     * 
     * @param value
     *     allowed object is
     *     {@link CFormularioMantenimientoNormas }
     *     
     */
    public void setFormularioMantenimientoNormas(CFormularioMantenimientoNormas value) {
        this.formularioMantenimientoNormas = value;
    }

}


package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EstadoVigenciaClave.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EstadoVigenciaClave">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Vencida"/>
 *     &lt;enumeration value="Vigente"/>
 *     &lt;enumeration value="VenceHoy"/>
 *     &lt;enumeration value="PeriodoTolencia"/>
 *     &lt;enumeration value="SinHistorialCambios"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EstadoVigenciaClave")
@XmlEnum
public enum EstadoVigenciaClave {

    @XmlEnumValue("Vencida")
    VENCIDA("Vencida"),
    @XmlEnumValue("Vigente")
    VIGENTE("Vigente"),
    @XmlEnumValue("VenceHoy")
    VENCE_HOY("VenceHoy"),
    @XmlEnumValue("PeriodoTolencia")
    PERIODO_TOLENCIA("PeriodoTolencia"),
    @XmlEnumValue("SinHistorialCambios")
    SIN_HISTORIAL_CAMBIOS("SinHistorialCambios");
    private final String value;

    EstadoVigenciaClave(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EstadoVigenciaClave fromValue(String v) {
        for (EstadoVigenciaClave c: EstadoVigenciaClave.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

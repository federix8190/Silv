
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TEstadoCambioClave.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TEstadoCambioClave">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ClaveActualIncorrecta"/>
 *     &lt;enumeration value="ClaveNuevaIgualAnterior"/>
 *     &lt;enumeration value="ClavesNoConfirmadas"/>
 *     &lt;enumeration value="ClaveNoCumpleSeguridad"/>
 *     &lt;enumeration value="ProblemasAlCambiarClave"/>
 *     &lt;enumeration value="ClaveSinMinumoCaracteres"/>
 *     &lt;enumeration value="ClaveFueUtilizada"/>
 *     &lt;enumeration value="ClaveCambiadaExitosamente"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TEstadoCambioClave")
@XmlEnum
public enum TEstadoCambioClave {

    @XmlEnumValue("ClaveActualIncorrecta")
    CLAVE_ACTUAL_INCORRECTA("ClaveActualIncorrecta"),
    @XmlEnumValue("ClaveNuevaIgualAnterior")
    CLAVE_NUEVA_IGUAL_ANTERIOR("ClaveNuevaIgualAnterior"),
    @XmlEnumValue("ClavesNoConfirmadas")
    CLAVES_NO_CONFIRMADAS("ClavesNoConfirmadas"),
    @XmlEnumValue("ClaveNoCumpleSeguridad")
    CLAVE_NO_CUMPLE_SEGURIDAD("ClaveNoCumpleSeguridad"),
    @XmlEnumValue("ProblemasAlCambiarClave")
    PROBLEMAS_AL_CAMBIAR_CLAVE("ProblemasAlCambiarClave"),
    @XmlEnumValue("ClaveSinMinumoCaracteres")
    CLAVE_SIN_MINUMO_CARACTERES("ClaveSinMinumoCaracteres"),
    @XmlEnumValue("ClaveFueUtilizada")
    CLAVE_FUE_UTILIZADA("ClaveFueUtilizada"),
    @XmlEnumValue("ClaveCambiadaExitosamente")
    CLAVE_CAMBIADA_EXITOSAMENTE("ClaveCambiadaExitosamente");
    private final String value;

    TEstadoCambioClave(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TEstadoCambioClave fromValue(String v) {
        for (TEstadoCambioClave c: TEstadoCambioClave.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

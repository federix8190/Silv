
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TEstadoAutenticacion.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TEstadoAutenticacion">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NoAutenticado"/>
 *     &lt;enumeration value="CredencialesInvalidas"/>
 *     &lt;enumeration value="CredencialesCorrectasSinRol"/>
 *     &lt;enumeration value="CredencialesCorrectasConRol"/>
 *     &lt;enumeration value="Inactivo"/>
 *     &lt;enumeration value="BloqueadoIntentosFallidos"/>
 *     &lt;enumeration value="RequiereCambioContrasena"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TEstadoAutenticacion")
@XmlEnum
public enum TEstadoAutenticacion {

    @XmlEnumValue("NoAutenticado")
    NO_AUTENTICADO("NoAutenticado"),
    @XmlEnumValue("CredencialesInvalidas")
    CREDENCIALES_INVALIDAS("CredencialesInvalidas"),
    @XmlEnumValue("CredencialesCorrectasSinRol")
    CREDENCIALES_CORRECTAS_SIN_ROL("CredencialesCorrectasSinRol"),
    @XmlEnumValue("CredencialesCorrectasConRol")
    CREDENCIALES_CORRECTAS_CON_ROL("CredencialesCorrectasConRol"),
    @XmlEnumValue("Inactivo")
    INACTIVO("Inactivo"),
    @XmlEnumValue("BloqueadoIntentosFallidos")
    BLOQUEADO_INTENTOS_FALLIDOS("BloqueadoIntentosFallidos"),
    @XmlEnumValue("RequiereCambioContrasena")
    REQUIERE_CAMBIO_CONTRASENA("RequiereCambioContrasena");
    private final String value;

    TEstadoAutenticacion(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TEstadoAutenticacion fromValue(String v) {
        for (TEstadoAutenticacion c: TEstadoAutenticacion.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

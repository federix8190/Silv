
package py.com.ceamso.ws.csj.autenticacion;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TEstadoUsuario.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TEstadoUsuario">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Inactivo"/>
 *     &lt;enumeration value="Activo"/>
 *     &lt;enumeration value="BloqueadoIntentosFallidos"/>
 *     &lt;enumeration value="RequiereCambioContrasena"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TEstadoUsuario")
@XmlEnum
public enum TEstadoUsuario {

    @XmlEnumValue("Inactivo")
    INACTIVO("Inactivo"),
    @XmlEnumValue("Activo")
    ACTIVO("Activo"),
    @XmlEnumValue("BloqueadoIntentosFallidos")
    BLOQUEADO_INTENTOS_FALLIDOS("BloqueadoIntentosFallidos"),
    @XmlEnumValue("RequiereCambioContrasena")
    REQUIERE_CAMBIO_CONTRASENA("RequiereCambioContrasena");
    private final String value;

    TEstadoUsuario(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TEstadoUsuario fromValue(String v) {
        for (TEstadoUsuario c: TEstadoUsuario.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

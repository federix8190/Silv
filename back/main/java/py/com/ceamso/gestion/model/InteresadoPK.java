package py.com.ceamso.gestion.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InteresadoPK implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "id_usuario_interesado")
    private Long idUsuario;
    
    @Column(name = "id_convocatoria")
    private Long idConvocatoria;
    
    public InteresadoPK() {
    }
    
    public InteresadoPK(Long idUsuario, Long idConvocatoria) {
        this.idUsuario = idUsuario;
        this.idConvocatoria = idConvocatoria;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(Long idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }
    
}

package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "clasificacion_anexo")
public class ClasificacionAnexo implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ClasificacionAnexoPk id;
    
    // Datos CPT
    @Column(name = "id_cpt")
    private Long idCpt;
    
    @Column(name = "nivel_cpt")
    private String nivelCpt;
    
    @Column(name = "subnivel_cpt")
    private String subNivelCpt;
    
    @Column(name = "numero_cpt")
    private String numeroCpt;
    
    @Column(name = "denominacion_cpt")
    private String denominacionCpt;
    
    @Column(name = "titular_unidad")
    private Boolean titularUnidad;
    
    // Datos CPT EF
    @Column(name = "id_cpt_ef")
    private Long idCptF;
    
    @Column(name = "ambito_cpt_ef")
    private String ambitoCptF;
    
    @Column(name = "codigo_proceso")
    private String codigoProceso;
    
    @Column(name = "denominacion_cpt_ef")
    private String denominacionCptF;
    
    @Column(name = "numero_secuencial")
    private String numeroSecuencialCptF;
    
    @Column(name = "orientacion_funcional")
    private String orientacionFuncional;
    
    // Datos CPT EE
    @Column(name = "id_cpt_ee")
    private Long idCptE;
    
    @Column(name = "ambito_cpt_ee")
    private String ambitoCptE;
    
    @Column(name = "categoria")
    private String categoriaCptE;
    
    @Column(name = "denominacion_cpt_ee")
    private String denominacionCptE;
    
    @Column(name = "nivel_cpt_ee")
    private String nivelCptE;
    
    @Column(name = "numero_secuencial_cpt_ee")
    private String numeroSecuencialCptE;
    
    // Datos CEO
    @Column(name = "id_ceo")
    private Long idCeo;
    
    @Column(name = "codigo_ceo")
    private String codigoCeo;
    
    @Column(name = "denominacion_ceo")
    private String denominacionCeo;

    // Datos CUO
    @Column(name = "id_cuo")
    private Long idCuo;
    
    @Column(name = "nivel_cuo")
    private Integer nivelCuo;
    
    @Column(name = "subnivel_cuo")
    private Integer subNivelCuo;
    
    @Column(name = "numero_cuo")
    private Integer numeroCuo;
    
    @Column(name = "denominacion_cuo")
    private String denominacionCuo;
    
    public ClasificacionAnexo() {
    }

    public ClasificacionAnexoPk getId() {
        return id;
    }

    public void setId(ClasificacionAnexoPk id) {
        this.id = id;
    }

    public Long getIdCpt() {
        return idCpt;
    }

    public void setIdCpt(Long idCpt) {
        this.idCpt = idCpt;
    }

    public String getNivelCpt() {
        return nivelCpt;
    }

    public void setNivelCpt(String nivelCpt) {
        this.nivelCpt = nivelCpt;
    }

    public String getSubNivelCpt() {
        return subNivelCpt;
    }

    public void setSubNivelCpt(String subNivelCpt) {
        this.subNivelCpt = subNivelCpt;
    }

    public String getNumeroCpt() {
        return numeroCpt;
    }

    public void setNumeroCpt(String numeroCpt) {
        this.numeroCpt = numeroCpt;
    }

    public String getDenominacionCpt() {
        return denominacionCpt;
    }

    public void setDenominacionCpt(String denominacionCpt) {
        this.denominacionCpt = denominacionCpt;
    }

    public Boolean getTitularUnidad() {
        return titularUnidad;
    }

    public void setTitularUnidad(Boolean titularUnidad) {
        this.titularUnidad = titularUnidad;
    }

    public Long getIdCptF() {
        return idCptF;
    }

    public void setIdCptF(Long idCptF) {
        this.idCptF = idCptF;
    }

    public String getAmbitoCptF() {
        return ambitoCptF;
    }

    public void setAmbitoCptF(String ambitoCptF) {
        this.ambitoCptF = ambitoCptF;
    }

    public String getCodigoProceso() {
        return codigoProceso;
    }

    public void setCodigoProceso(String codigoProceso) {
        this.codigoProceso = codigoProceso;
    }

    public String getDenominacionCptF() {
        return denominacionCptF;
    }

    public void setDenominacionCptF(String denominacionCptF) {
        this.denominacionCptF = denominacionCptF;
    }

    public String getNumeroSecuencialCptF() {
        return numeroSecuencialCptF;
    }

    public void setNumeroSecuencialCptF(String numeroSecuencialCptF) {
        this.numeroSecuencialCptF = numeroSecuencialCptF;
    }

    public String getOrientacionFuncional() {
        return orientacionFuncional;
    }

    public void setOrientacionFuncional(String orientacionFuncional) {
        this.orientacionFuncional = orientacionFuncional;
    }

    public Long getIdCptE() {
        return idCptE;
    }

    public void setIdCptE(Long idCptE) {
        this.idCptE = idCptE;
    }

    public String getAmbitoCptE() {
        return ambitoCptE;
    }

    public void setAmbitoCptE(String ambitoCptE) {
        this.ambitoCptE = ambitoCptE;
    }

    public String getCategoriaCptE() {
        return categoriaCptE;
    }

    public void setCategoriaCptE(String categoriaCptE) {
        this.categoriaCptE = categoriaCptE;
    }

    public String getDenominacionCptE() {
        return denominacionCptE;
    }

    public void setDenominacionCptE(String denominacionCptE) {
        this.denominacionCptE = denominacionCptE;
    }

    public String getNivelCptE() {
        return nivelCptE;
    }

    public void setNivelCptE(String nivelCptE) {
        this.nivelCptE = nivelCptE;
    }

    public String getNumeroSecuencialCptE() {
        return numeroSecuencialCptE;
    }

    public void setNumeroSecuencialCptE(String numeroSecuencialCptE) {
        this.numeroSecuencialCptE = numeroSecuencialCptE;
    }

    public Long getIdCeo() {
        return idCeo;
    }

    public void setIdCeo(Long idCeo) {
        this.idCeo = idCeo;
    }

    public String getCodigoCeo() {
        return codigoCeo;
    }

    public void setCodigoCeo(String codigoCeo) {
        this.codigoCeo = codigoCeo;
    }

    public String getDenominacionCeo() {
        return denominacionCeo;
    }

    public void setDenominacionCeo(String denominacionCeo) {
        this.denominacionCeo = denominacionCeo;
    }

    public Long getIdCuo() {
        return idCuo;
    }

    public void setIdCuo(Long idCuo) {
        this.idCuo = idCuo;
    }

    public Integer getNivelCuo() {
        return nivelCuo;
    }

    public void setNivelCuo(Integer nivelCuo) {
        this.nivelCuo = nivelCuo;
    }

    public Integer getSubNivelCuo() {
        return subNivelCuo;
    }

    public void setSubNivelCuo(Integer subNivelCuo) {
        this.subNivelCuo = subNivelCuo;
    }

    public Integer getNumeroCuo() {
        return numeroCuo;
    }

    public void setNumeroCuo(Integer numeroCuo) {
        this.numeroCuo = numeroCuo;
    }

    public String getDenominacionCuo() {
        return denominacionCuo;
    }

    public void setDenominacionCuo(String denominacionCuo) {
        this.denominacionCuo = denominacionCuo;
    }
    
}

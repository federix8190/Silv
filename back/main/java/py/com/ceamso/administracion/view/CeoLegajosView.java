package py.com.ceamso.administracion.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import py.com.ceamso.administracion.model.Ceo;
import py.com.ceamso.reportes.model.AnexosPK;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "v_cte_ceo_legajos")
public class CeoLegajosView implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    @EmbeddedId
    private CeoLegajosViewPk id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "sexo")
    private String sexo;
    
    @Column(name = "nombre_depto")
    private String nombreDepartamento;
    
    @Column(name = "nombre_distrito")
    private String nombreDistrito;
    
    //@Column(name = "id_cargo")
    @Transient
    private Long idCargo;
    
    @Column(name = "cargo")    
    private String cargo;
    
    @Column(name = "funcion_real")
    private String funcionReal;
    
    @Column(name = "vinculacion_funcionario")
    private String vinculacionFuncionario;

    @Transient
    private String fechaNacimientoString;
        
    @Column(name = "nivel_cpt")
    private Long nivel;
    
    @Transient
    private boolean asignado;
    
    @Column(name = "id_ceo")
    private Long ceoId;
    
    @Column(name = "denominacion_ceo")
    private String ceoActual;
    
    @Column(name = "codigo_ceo")
    private String ceoCodigo;

    @Column(name = "orden")
    private int orden;
    
    public CeoLegajosView() {
    }     

    /*public Integer getAnho() {
        return anho;
    }

    public void setAnho(Integer anho) {
        this.anho = anho;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }*/

    public CeoLegajosViewPk getId() {
        return id;
    }

    public void setId(CeoLegajosViewPk id) {
        this.id = id;
    }
    
    public Long getCeoId() {
        return ceoId;
    }

    public void setCeoId(Long ceoId) {
        this.ceoId = ceoId;
    }
    
    public String getCeoActual() {
        return ceoActual;
    }

    public void setCeoActual(String ceoActual) {
        this.ceoActual = ceoActual;
    }

    public String getCeoCodigo() {
        return ceoCodigo;
    }

    public void setCeoCodigo(String ceoCodigo) {
        this.ceoCodigo = ceoCodigo;
    }
    
    /*public Long getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(Long cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }*/

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }    

    public String getFechaNacimientoString() {
        if (this.getFechaNacimiento() != null) {
            this.fechaNacimientoString = SDF.format(this.getFechaNacimiento());
        }
        return fechaNacimientoString;
    }

    public void setFechaNacimientoString(String fechaNacimientoString) {
        this.fechaNacimientoString = fechaNacimientoString;
    }

    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFuncionReal() {
        return funcionReal;
    }

    public void setFuncionReal(String funcionReal) {
        this.funcionReal = funcionReal;
    }

    public String getVinculacionFuncionario() {
        return vinculacionFuncionario;
    }

    public void setVinculacionFuncionario(String vinculacionFuncionario) {
        this.vinculacionFuncionario = vinculacionFuncionario;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }        

    public boolean isAsignado() {
        return asignado;
    }

    public void setAsignado(boolean asignado) {
        this.asignado = asignado;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
    
}

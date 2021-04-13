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
import py.com.ceamso.administracion.model.Cpt;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "v_cte_cpt_legajos")
public class CptLegajosView implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    /*@Id
    @Column(name = "cedula_identidad")
    private Long cedulaIdentidad;*/
    
    @EmbeddedId
    private CptLegajosViewPk id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "sexo")
    private String sexo;
    
    //@Column(name = "id_cargo")
    @Transient
    private Long idCargo;
    
    @Column(name = "cargo")    
    private String cargo;
    
    @Column(name = "funcion_real")
    private String funcionReal;
    
    @Column(name = "vinculacion_funcionario")
    private String vinculacionFuncionario;
    
    @Column(name = "nombre_depto")
    private String nombreDepartamento;
    
    @Column(name = "nombre_distrito")
    private String nombreDistrito;

    @Transient
    private String fechaNacimientoString;
        
    @Column(name = "nivel_cpt")
    private Long nivel;
    
    @Transient
    private boolean asignado;

    @Column(name = "orden")
    private int orden;
    
    @Column(name = "denominacion_cpt")
    private String denominacionCpt;
    
    @Column(name = "numero_tramo")
    private Long numeroTramo;
    
    @Transient
    private Cpt cptActual;
    
    public CptLegajosView() {
    }        

    public Cpt getCptActual() {
        return cptActual;
    }

    public void setCptActual(Cpt cptActual) {
        this.cptActual = cptActual;
    }

    /*public Long getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(Long cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }*/

    public CptLegajosViewPk getId() {
        return id;
    }

    public void setId(CptLegajosViewPk id) {
        this.id = id;
    }

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

    public String getDenominacionCpt() {
        return denominacionCpt;
    }

    public void setDenominacionCpt(String denominacionCpt) {
        this.denominacionCpt = denominacionCpt;
    }

    public Long getNumeroTramo() {
        return numeroTramo;
    }

    public void setNumeroTramo(Long numeroTramo) {
        this.numeroTramo = numeroTramo;
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
    
}

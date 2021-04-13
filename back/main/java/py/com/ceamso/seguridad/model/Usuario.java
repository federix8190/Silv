package py.com.ceamso.seguridad.model;

import py.com.ceamso.base.WritableEntity;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import py.com.ceamso.seguridad.dto.RegistroOnLine;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "usuario")
public class Usuario extends WritableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "nombre")
    private String nombre;
    
    @Basic(optional = false)
    //@NotNull
    @Size(min = 1, max = 500)
    @Column(name = "apellido")
    private String apellido;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;

    @Basic(optional = false)
    @NotNull
    @Column(name = "recibir_notificacion")
    private Boolean recibirNotificacion;

    @Size(max = 20)
    @Column(name = "cedula", unique=true)
    private String cedula;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    
    @Transient
    private String password2;
    
    @Transient
    private String passwordAnterior;

    //@Column(name = "rol")
    //private Long rol;
    
    @JoinColumn(name = "rol", referencedColumnName = "id")
    @ManyToOne
    private Rol rol;
    
    @Transient
    private String nombreRol;

    @Basic(optional = false)
    @NotNull
    @Column(name = "interno")
    private Boolean interno;
    
    @Column(name = "codigo_externo")
    private Integer codigoExterno;

    @Column(name = "codigo_rol")
    private Integer codigoRol;

    public Usuario() {
    }

    public Usuario(RegistroOnLine datos, String username, String password) {
        this.cedula = datos.getCedula();
        this.nombre = datos.getNombre();
        this.email = datos.getEmail();
        this.username = username;
        this.password = password;
        this.recibirNotificacion = datos.getRecibirNotificacion();
    }

    /*public Usuario(CrearUsuarioDto datos, String username, String password) {
        this.cedula = datos.getCedula();
        this.nombre = datos.getNombre();
        this.email = datos.getEmail();
        this.username = username;
        this.password = password;
        this.recibirNotificacion = datos.getRecibirNotificacion();
    }*/

    public Usuario(Long id) {
        this.id = id;
    }

    public Usuario(Long id, String nombre, String email, boolean recibirNotificacion, boolean interno) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.recibirNotificacion = recibirNotificacion;
        this.interno = interno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getRecibirNotificacion() {
        return recibirNotificacion;
    }

    public void setRecibirNotificacion(boolean recibirNotificacion) {
        this.recibirNotificacion = recibirNotificacion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Boolean getInterno() {
        return interno;
    }

    public void setInterno(Boolean interno) {
        this.interno = interno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getPasswordAnterior() {
        return passwordAnterior;
    }

    public void setPasswordAnterior(String passwordAnterior) {
        this.passwordAnterior = passwordAnterior;
    }

    /*public Long getRol() {
        return rol;
    }

    public void setRol(Long rol) {
        this.rol = rol;
    }*/

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Integer getCodigoExterno() {
        return codigoExterno;
    }

    public void setCodigoExterno(Integer codigoExterno) {
        this.codigoExterno = codigoExterno;
    }

    public Integer getCodigoRol() {
        return codigoRol;
    }

    public void setCodigoRol(Integer codigoRol) {
        this.codigoRol = codigoRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.ceamso.model.Usuario[ id=" + id + " ]";
    }

}

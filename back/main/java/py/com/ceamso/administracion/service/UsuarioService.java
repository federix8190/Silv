package py.com.ceamso.administracion.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.hibernate.exception.ConstraintViolationException;
import py.com.ceamso.administracion.dao.UsuarioDAO;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.exceptions.BusinessException;
import py.com.ceamso.seguridad.dao.RolPermisoDAO;
import py.com.ceamso.seguridad.model.Rol;

import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Mensajes;
import py.com.ceamso.utils.Respuesta;
import py.com.ceamso.utils.Utils;
import py.com.ceamso.validator.BeanValidatorUtils;

@Stateless
public class UsuarioService extends WritableServiceImpl<Usuario, UsuarioDAO> {

    @Inject
    private UsuarioDAO dao;
    
    @Inject
    private RolPermisoDAO rolPermisoDao;
    
    @Resource(lookup = "java:jboss/mail/CTEMailSession")
    private Session mailSession;
    
    @Inject
    @Configuracion("url_aplicacion")
    private String url;

    @Override
    public UsuarioDAO getDao() {
        return dao;
    }

    public UsuarioService() {
    }

    //@Override
    public Usuario insertar(Usuario entity, HttpServletRequest httpRequest, 
            Long userId, boolean esRegistroOnLine) throws AppException {
        
        try {
            
            String clave = null;
            
            if (esRegistroOnLine) {
                if (entity.getPassword() == null || entity.getPassword2() == null) {
                    throw new AppException(400, "Debe proveer todos los datos");
                }
                if (!entity.getPassword().equals(entity.getPassword2())) {
                    throw new AppException(400, "La contraseña de confirmación no coincide");
                }
            } else {
                clave = Utils.randomString(20);
                entity.setPassword(clave);
            }
            
            String encryptedToken = new Md5Hash(entity.getPassword(), entity.getCedula()).toString();
            entity.setUsername(entity.getCedula());
            entity.setPassword(encryptedToken);
            entity.setFechaCreacion(new Date());
            entity.setUsuarioCreacion(userId);
            entity.setIpCreacion(httpRequest.getRemoteAddr());
            entity.setActivo(true);
            
            if (esRegistroOnLine) {
                entity.setInterno(false);
                Rol rol = rolPermisoDao.getRolPublico();
                entity.setRol(rol);
            } else {
                entity.setInterno(true);
                entity.setRecibirNotificacion(false);
                Rol rol = entity.getRol();
                if (rol == null) {
                    throw new AppException(400, Mensajes.ROL_INVALIDO);
                }
                entity.setRol(rol);
            }
            
            validate(entity);
            getDao().insert(entity);
            if (clave != null) {
                //System.err.println("Clave de acceso : " + clave);
                String msj = "SISTEMA CONCURSABILIDAD TRANSPARENCIA EQUIDAD (CTE) \n";
                msj = msj + url;//"http://192.168.2.89:88";
                msj = msj + "\n\nUsuario: " + entity.getCedula();
                msj = msj + "\nContraseña: " + clave;
                send(entity.getEmail(), "Clave de acceso", msj);
            }
            return entity;
            
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    /**
     * Se encarga de disaprar los beans validations
     *
     * @param obj
     */
    @Override
    public void validate(Usuario obj) {
        String mensaje = "";
        Usuario usuario = null;
        try {
            BeanValidatorUtils.validate(obj);
            try {
                usuario = getDao().findByCedula(obj.getCedula());
                if(usuario != null && usuario.getId() != obj.getId()){
                    mensaje += "Ya existe un usuario con esa cédula.\n";
                }
                
                usuario = getDao().findByEmail(obj.getEmail());
                if(usuario != null && usuario.getId() != obj.getId()){
                    mensaje += "Ya existe un usuario con ese email.\n";
                }
                
            } catch (AppException ex) {
                Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
            }
            if("".compareTo(mensaje) != 0)
                throw new RuntimeException(mensaje);
        } catch (IllegalArgumentException | BusinessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }    
    
    public Response cambiarPassword(Long id, Usuario dto, HttpServletRequest httpRequest) 
            throws AppException {
        
        try {
            
            if (dto.getPassword() == null || dto.getPassword2() == null) {
        	throw new AppException(400, "Debe proveer todos los datos");
            }
            if (!dto.getPassword().equals(dto.getPassword2())) {
        	throw new AppException(400, "La contraseña de confirmación no coincide");
            }
            validarPassword(dto.getPassword());
            
            Usuario entity = obtener(id);
            String passwordUsuario = new Md5Hash(dto.getPasswordAnterior(), entity.getUsername()).toString();
            if (!passwordUsuario.equals(entity.getPassword())) {
                Respuesta res = new Respuesta(false, "La contraseña no es correcta");
                return Response.status(400).entity(res).build();
            }
            
            String encryptedToken = new Md5Hash(dto.getPassword(), entity.getUsername()).toString();
            entity.setPassword(encryptedToken);
            Usuario user = getCurrentUser();
            entity.setFechaModificacion(new Date());
            entity.setUsuarioModificacion(user.getId());
            entity.setIpModificacion(httpRequest.getRemoteAddr());
            validate(entity);
            getDao().modify(id, entity);
            return Response.ok().build();
            
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public Respuesta resetearPassword(String cedula) throws AppException {
        
        try {
            
            Usuario entity = dao.findByName(cedula);
            if (entity == null) {
                return new Respuesta(false, "El usuario no existe");
            }
            String clave = Utils.randomString(20);
            String encryptedToken = new Md5Hash(clave, cedula).toString();
            entity.setPassword(encryptedToken);
            getDao().modify(entity.getId(), entity);
            
            if (clave != null) {
                String msj = "SISTEMA CONCURSABILIDAD TRANSPARENCIA EQUIDAD (CTE) \n";
                msj = msj + url;//"http://192.168.2.89:88";
                msj = msj + "\n\nUsuario: " + entity.getCedula();
                msj = msj + "\nContraseña: " + clave;
                send(entity.getEmail(), "Clave de acceso", msj);
            }
            return new Respuesta(true, "Se le enviara un nuevo password a su correo");
        } catch (Exception e) {
            return new Respuesta(false, "No se pudo realizar la operación");
        }
    }
    
    public Response send(String to, String subject, String mensaje) throws MessagingException {
        
        try {
            Message message = new MimeMessage(mailSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(mensaje);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
        return Response.ok().build();
    }
    
    public Usuario insertarAutenticadoWS(Usuario entity) throws AppException {
        try {
            entity.setFechaCreacion(new Date());
            entity.setUsuarioCreacion(0L);
            entity.setIpCreacion("");
            entity.setActivo(true);
            validate(entity);
            getDao().insert(entity);
            return entity;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    public Usuario actualizarRolUsuario(Usuario entity, int codigoRol) throws AppException {
        try {
            entity.setFechaModificacion(new Date());
            entity.setCodigoRol(codigoRol);
            validate(entity);
            getDao().modify(entity);
            return entity;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
     
    public Usuario findByName(String username) throws AppException {
        return getDao().findByName(username);
    }
    
    public Usuario findByCedula(String username) throws AppException {
        return getDao().findByCedula(username);
    }

    public Usuario findByNombrePassword(Usuario user) {
        return getDao().findByNombrePassword(user);
    }
    
    public List<String> getPermisos(Long rol) throws AppException {
        return rolPermisoDao.getPermisos(rol);
    }
    
    private void validarPassword(String password) throws AppException {
        
        if (password.length() < 8) {
            throw new AppException(400, "La contraseña debe tener al menos 8 caracteres");
        }
        char ch;
        boolean tieneMayuscula = false;
        boolean tieneNumero = false;
        for (int i = 0; i < password.length(); i++) {
            ch = password.charAt(i);
            if( Character.isDigit(ch)) {
                tieneNumero = true;
            } else if (Character.isUpperCase(ch)) {
                tieneMayuscula = true;
            }
            if (tieneMayuscula && tieneNumero) {
                return;
            }
        }
        
        if (!tieneMayuscula) {
            throw new AppException(400, "La contraseña debe tener al menos una Mayúscula");
        }
        if (!tieneNumero) {
            throw new AppException(400, "La contraseña debe tener al menos un Número");
        }
        
    }

}

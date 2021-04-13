package py.com.ceamso.seguridad.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import py.com.ceamso.administracion.model.Cpt;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.exceptions.BusinessException;
import py.com.ceamso.seguridad.dao.RolPermisoDAO;
import py.com.ceamso.seguridad.dto.RolDto;
import py.com.ceamso.seguridad.model.Permiso;
import py.com.ceamso.seguridad.model.Rol;
import py.com.ceamso.seguridad.model.RolPermiso;
import py.com.ceamso.seguridad.model.RolPermisoPK;
import py.com.ceamso.seguridad.model.Usuario;

import py.com.ceamso.utils.AppException;
import py.com.ceamso.validator.BeanValidatorUtils;

@Stateless
public class RolPermisoService extends WritableServiceImpl<Rol, RolPermisoDAO>  {

    @Inject
    private RolPermisoDAO dao;
    
    @Override
    public RolPermisoDAO getDao() {
        return dao;
    }

    public RolPermisoService() {
    }
    
    public List<Rol> getAll() {
        return getDao().getAll();
    }
       
    public Rol insertar(RolDto dto, HttpServletRequest httpRequest) throws AppException {
        
        try {
            
            Rol entity = new Rol();
            entity.setCodigo(dto.getCodigo());
            entity.setDescripcion(dto.getDescripcion());
            Usuario user = getCurrentUser();
            entity.setFechaCreacion(new Date());
            entity.setUsuarioCreacion(user.getId());
            entity.setIpCreacion(httpRequest.getRemoteAddr());
            entity.setActivo(Boolean.TRUE);
            if(validar(dto)){
                getDao().insert(entity);
                getDao().insertarRolPermiso(entity.getId(), dto.getPermisos());
            }

            return entity;
            
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public Boolean validar(RolDto dto) {
        
        try {
        	Rol rol = getRolByCodigo(dto.getCodigo());
            if(rol != null && rol.getId() != dto.getId() && rol.getActivo() != false) {
                throw new RuntimeException("El código ya existe.\n");
            }
        
            int cont = 0;
            for (Permiso permiso : dto.getPermisos()) {
                if (!permiso.isActivo()) {
                    cont++;
                }
            }
            if(cont == dto.getPermisos().size())
            	throw new RuntimeException("El rol debe de contener al menos un permiso.\n");
            
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
        
        return true;
    }
        
    public void modificar(Long id, RolDto dto, HttpServletRequest httpRequest) throws AppException {
        
        try {
            Usuario user = getCurrentUser();
            validar(dto);
            Rol entity = obtener(id);
            entity.setCodigo(dto.getCodigo());
            entity.setDescripcion(dto.getDescripcion());
            entity.setFechaModificacion(new Date());
            entity.setUsuarioModificacion(user.getId());
            entity.setIpModificacion(httpRequest.getRemoteAddr());
            getDao().modify(id, entity);
            
            try {
            	getDao().actualizarRolPermiso(entity.getId(), dto.getPermisos());
            } catch (Exception e) {
            	e.printStackTrace();
            }
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public void eliminar(Long id, HttpServletRequest httpRequest) throws AppException {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT r FROM Rol r WHERE r.id = :id " +
                    "AND EXISTS (SELECT u FROM Usuario u WHERE u.rol = r.id)");
            Rol rol = getDao().verificarConstraint(id, query);
            if (rol != null) {
                rol.setActivo(false);
                modificar(id, rol, httpRequest);
            } else {
                getDao().eliminarRolPermisos(id);
                eliminar(id);
            }
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public List<Permiso> getPermisosRol(Long idRol) {
        return getDao().getPermisosRol(idRol);
    }

    public Rol getRolByCodigo(String codigo) {
        return dao.getRolByCodigo(codigo);
    }
    
    public Rol getRolPublico() throws AppException {
        return dao.getRolPublico();
    }
    
    public List<String> getPermisos(Long rol) throws AppException {
        return dao.getPermisos(rol);
    }

}

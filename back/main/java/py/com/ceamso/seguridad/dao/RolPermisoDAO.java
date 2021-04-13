package py.com.ceamso.seguridad.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.seguridad.model.Permiso;
import py.com.ceamso.seguridad.model.Rol;
import py.com.ceamso.seguridad.model.RolPermiso;
import py.com.ceamso.seguridad.model.RolPermisoPK;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;

/**
 *
 * @author konecta
 */
public class RolPermisoDAO extends WritableDAO<Rol> {
	
	@Inject
    private PermisoDAO permisoDAO;

    @Override
    public Class getEntity() {
        return Rol.class;
    }
    
    public RolPermisoDAO(){
    }
    
    public List<Rol> getAll() {
        
        String sql = "SELECT r FROM Rol r";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }
    
    public RolPermiso getRolPermiso(long idRol, int idPermiso) {
                
        try {
	    	
        	String sql = "SELECT r FROM RolPermiso r "
	        		+ "WHERE r.rolPermisoPK.idRol = :idRol "
	        		+ "AND r.rolPermisoPK.idPermiso = :idPermiso";
	        
	        Query q = em.createQuery(sql);
	        q.setParameter("idRol", idRol);
	        q.setParameter("idPermiso", idPermiso);
	        RolPermiso r = (RolPermiso) q.getSingleResult();
	        return r;
	        
        } catch (Exception e) {
        	return null;
        }
    }
    
    public Rol getRolByCodigo(String codigo) {
                
        String sql = "SELECT r FROM Rol r "
                + "WHERE r.codigo = :codigo AND r.activo = true";
        Query q = em.createQuery(sql);
        q.setParameter("codigo", codigo);
        List<Rol> roles = q.getResultList();
        if (roles != null && roles.size() > 0) {
            return roles.get(0);
        }
        return null;
    }
    
    public Rol insertar(Rol entity) throws AppException {
        em.persist(entity);
        return entity;
    }
    
    public void insertarRolPermiso(long idRol, List<Permiso> permisos) {
        
        for (Permiso permiso : permisos) {
            if (permiso.isActivo()) {
                RolPermisoPK pk = new RolPermisoPK(permiso.getId(), idRol);
                RolPermiso entity = new RolPermiso(pk);
                em.persist(entity);
            }
        }
    }
    
    public void eliminarRolPermisos(long idRol) {
        List<RolPermiso> rolesPermisos = getRolPermisos(idRol);
        for (RolPermiso r : rolesPermisos) {
            em.remove(r);
        }
    }
    
    public void actualizarRolPermiso(long idRol, List<Permiso> permisos) throws AppException {
    	
    	List<Integer> permisosActuales = getPermisosActuales(idRol);
    	List<Integer> permisosNuevos = new ArrayList<Integer>();
    	List<Integer> permisosEliminar = new ArrayList<Integer>();
    	List<Integer> permisosAgregar = new ArrayList<Integer>();
    	
    	for (Permiso p : permisos) {
    		if (p.isActivo()) {
    			permisosNuevos.add(p.getId());
    		}
    	}
    	
    	for (Integer p : permisosNuevos) {
    		if (!permisosActuales.contains(p)) {
    			System.err.println("Agregar Permiso : " + p);
    			permisosAgregar.add(p);
    		}
    	}
    	
    	for (Integer p : permisosActuales) {
    		if (!permisosNuevos.contains(p)) {
    			System.err.println("Eliminar Permiso : " + p);
    			permisosEliminar.add(p);
    		}
    	} 
    	
    	for (Integer p : permisosAgregar) {
    		RolPermisoPK pk = new RolPermisoPK(p, idRol);
            RolPermiso entity = new RolPermiso(pk);
            em.persist(entity);
    	}
    	
    	//System.err.println("Cantidad Eliminar Permiso : " + permisosEliminar.size());
    	for (Integer p : permisosEliminar) {
    		RolPermiso r = getRolPermiso(idRol, p);
    		em.remove(r);
    	}
    	
    	//System.err.println("Permisos agregar : " + permisosAgregar.size());
    	//System.err.println("Permisos eliminar : " + permisosEliminar.size());
        
        /*List<RolPermiso> rolesPermisos = getRolPermisos(idRol);
        System.err.println("Permisos : " + rolesPermisos.size());
        for (RolPermiso r : rolesPermisos) {
            em.remove(r);
        }
        
        for (Permiso permiso : permisos) {
            if (permiso.isActivo()) {
                RolPermisoPK pk = new RolPermisoPK(permiso.getId(), idRol);
                RolPermiso entity = new RolPermiso(pk);
                em.persist(entity);
            }
        }*/
    }
    
    public List<RolPermiso> getRolPermisos(Long idRol) {
                
        String sql = "SELECT r FROM RolPermiso r WHERE r.rolPermisoPK.idRol = :idRol ";
        Query q = em.createQuery(sql);
        q.setParameter("idRol", idRol);
        return q.getResultList();
    }
    
    public List<Permiso> getPermisosRol(Long idRol) {
        
        // Obtener los permisos asociados al rol
        String sql = "SELECT p.id FROM Permiso p, RolPermiso r "
                + "WHERE r.rolPermisoPK.idPermiso = p.id "
                + "AND r.rolPermisoPK.idRol = :idRol";
        
        Query q = em.createQuery(sql);
        q.setParameter("idRol", idRol);
        List<Integer> permisosRol = q.getResultList();
        
        // Obtener todos los permisos
        sql = "SELECT p FROM Permiso p";
        q = em.createQuery(sql);
        List<Permiso> permisos = q.getResultList();
        
        // Preparar respuesta
        List<Permiso> res = new ArrayList();
        for (Permiso p : permisos) {
            if (permisosRol.contains(p.getId())) {
                p.setActivo(true);
            } else {
                p.setActivo(false);
            }
            res.add(p);
        }
        return res;
    }
    
    public List<Integer> getPermisosActuales(Long idRol) {
    	
    	String sql = "SELECT rp.rolPermisoPK.idPermiso FROM RolPermiso rp "
    			+ "WHERE rp.rolPermisoPK.idRol = :idRol ";
    	
    	Query q = em.createQuery(sql);
    	q.setParameter("idRol", idRol);
        List<Integer> permisos = q.getResultList();
        return permisos;
    }

    public Rol getRolPublico() throws AppException {
        
        try {
            
            Query q = em.createQuery("SELECT r FROM Rol r WHERE r.codigo = :codigo");
            
            @SuppressWarnings("unchecked")
            List<Rol> res = q.setParameter("codigo", Constantes.ROL_PUBLICO).getResultList();
            if (res != null && res.size() > 0) {
                return res.get(0);
            }
            return null;

        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public List<String> getPermisos(Long rol) throws AppException {

        try {
            Query q = em.createQuery("SELECT r.permiso.nombre FROM RolPermiso r "
                    + "WHERE r.rolPermisoPK.idRol = :idRol");
            
            @SuppressWarnings("unchecked")
            List<String> permisos = q.setParameter("idRol", rol).getResultList();
            return permisos;

        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
}

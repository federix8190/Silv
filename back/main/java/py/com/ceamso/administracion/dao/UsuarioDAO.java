package py.com.ceamso.administracion.dao;

import java.util.HashMap;
import java.util.List;
import javax.persistence.Query;
import org.apache.shiro.crypto.hash.Md5Hash;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Mensajes;

/**
 *
 * @author konecta
 */
public class UsuarioDAO extends WritableDAO<Usuario> {

    /**
     *
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return Usuario.class;
    }

    public Usuario findByName(String username) throws AppException {

        try {

            String sql = "SELECT u FROM Usuario u WHERE u.username = :username";
            Query q = em.createQuery(sql);
            q.setParameter("username", username);
            List<Usuario> res = q.getResultList();
            if (res == null || res.size() == 0) {
                return null;
            }
            return res.get(0);

        } catch (Exception e) {
            throw new AppException(500, "Error interno del servidor");
        }
    }
    public Usuario findByEmailAndId(String email, Long id) throws AppException {

        try {

            String sql = "SELECT u FROM Usuario u WHERE u.email = :email AND u.id <> :id";
            Query q = em.createQuery(sql);
            q.setParameter("email", email);
            q.setParameter("id", id);
            List<Usuario> res = q.getResultList();
            if (res == null || res.size() == 0) {
                return null;
            }
            return res.get(0);

        } catch (Exception e) {
            throw new AppException(500, "Error interno del servidor");
        }
    }
    
    public Usuario findByCedulaAndId (String cedula, Long id) throws AppException {

        try {

            String sql = "SELECT u FROM Usuario u WHERE u.email = :email AND u.id <> :id";
            Query q = em.createQuery(sql);
            q.setParameter("cedula", cedula);
            q.setParameter("id", id);
            List<Usuario> res = q.getResultList();
            if (res == null || res.size() == 0) {
                return null;
            }
            return res.get(0);

        } catch (Exception e) {
            throw new AppException(500, "Error interno del servidor");
        }
    }
    
    public Usuario findByEmail(String email) throws AppException {

        try {

            String sql = "SELECT u FROM Usuario u WHERE u.email = :email";
            Query q = em.createQuery(sql);
            q.setParameter("email", email);
            List<Usuario> res = q.getResultList();
            if (res == null || res.size() == 0) {
                return null;
            }
            return res.get(0);

        } catch (Exception e) {
            throw new AppException(500, "Error interno del servidor");
        }
    }
    
    public List<Usuario> getUsuariosNotificaciones() throws AppException {

        try {

            String sql = "SELECT u FROM Usuario u WHERE u.recibirNotificacion = :recibirNotificacion";
            Query q = em.createQuery(sql);
            q.setParameter("recibirNotificacion", true);
            List<Usuario> res = q.getResultList();
            if (res == null || res.size() == 0) {
                return null;
            }
            return res;

        } catch (Exception e) {
            throw new AppException(500, "Error interno del servidor");
        }
    }
    
    public Usuario findByCedula(String cedula) throws AppException {

        try {

            String sql = "SELECT u FROM Usuario u WHERE u.cedula = :cedula";
            Query q = em.createQuery(sql);
            q.setParameter("cedula", cedula);
            List<Usuario> res = q.getResultList();
            if (res == null || res.size() == 0) {
                return null;
            }
            return res.get(0);

        } catch (Exception e) {
            throw new AppException(500, "Error interno del servidor");
        }
    }

    public Usuario findByNombrePassword(Usuario user) {

        try {

            Query q = em.createQuery("SELECT u FROM Usuario u "
                    + "WHERE u.username = :username AND u.password = :password "
                    + "AND u.activo = :activo");

            q.setParameter("username", user.getUsername());
            q.setParameter("password", user.getPassword());
            q.setParameter("activo", true);
            Usuario usuario = (Usuario) q.getSingleResult();
            
            System.err.println("Activo ? " + usuario.getActivo());

            return usuario;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Long id) throws AppException {

        Usuario entity = em.find(Usuario.class, id);
        if (entity == null) {
            throw new AppException(404, Mensajes.USUARIO_NO_ENCONTRADO);
        }

        if (entity.getActivo()) {
            entity.setActivo(Boolean.FALSE);
        } else {
            entity.setActivo(Boolean.TRUE);
        }
        em.merge(entity);
    }
    
    @Override
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1, start = 0, end = 0;
        
        int flag = 0;
        StringBuilder where = new StringBuilder();
        where.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {
            	if (key.equals("nombreRol")) {
            		key = "descripcion";
            		where.append(" LOWER(c.rol.");
            	} else {
            		where.append(" LOWER(c.");
            	}
            	where.append(key)
	                .append(") LIKE LOWER(:")
	                .append(key)
	                .append(")");
            } else {
            	where.append(key).append(" = :").append(key);
            }
            //se añade el 'AND' si hay más caracteres.
            end = where.length();
            start = end - 4;
            if (token < tokens && where.toString().compareTo(" WHERE ") != 0 
            		&& !where.substring(start, end).equals("AND ")) {
                where.append(" AND ");
                System.err.println("where: " + where);
            }
            token++;
        }
        if (where.toString().compareTo(" WHERE ") != 0) {
            end = where.length();
            start = end - 4;

            if (where.substring(start, end).equals("AND ")) {
                where.delete(start, end);
                System.out.println("Resultado: " + where);
            }
            sb.append(where);
        }
    }
    
    @Override
    public void setParametrers(Query q, HashMap<String, Object> filtros) {
        if (filtros == null) {
            return;
        }
        for (String key : filtros.keySet()) {
            Object value = filtros.get(key);
            if (filtros.get(key) instanceof String) {
            	if (key.equals("nombreRol")) {
            		key = "descripcion";
            	}
                value = "%" + value + "%";
            }
            q.setParameter(key, value);
        }
    }

}

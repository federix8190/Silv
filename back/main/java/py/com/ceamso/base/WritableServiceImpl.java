package py.com.ceamso.base;

import java.util.Date;
import java.util.HashMap;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import py.com.ceamso.exceptions.BusinessException;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.seguridad.service.SessionService;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Mensajes;
import py.com.ceamso.validator.BeanValidatorUtils;

/**
 *
 * @author mbaez
 * @param <G>
 * @param <D>
 */
public abstract class WritableServiceImpl<G extends WritableEntity, D extends WritableDAO<G>> extends ReadableServiceImpl<G, D>
        implements WritableService<G> {

    @Inject
    private SessionService session;


    /**
     * Se ecarga de recuperar los datos del usuario logeado.
     *
     * @return
     */
    public Usuario getCurrentUser() {
        return session.getCurrentUser();
    }

    /**
     * Se encarga de disaprar los beans validations
     *
     * @param obj
     */
    public void validate(G obj) {
        try {
            BeanValidatorUtils.validate(obj);
        } catch (IllegalArgumentException | BusinessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public G obtener(Long id) throws AppException {
        try {
            G entity =  getDao().get(id);
            //if (entity.getActivo() != null && entity.getActivo()) {
            if (entity == null) {
            	throw new AppException(404, Mensajes.RECURSO_NO_ENCONTRADO);
            }
            return entity;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public void modificar(Long id, G entity, HttpServletRequest httpRequest) throws AppException {
        try {
            Usuario user = getCurrentUser();
            entity.setFechaModificacion(new Date());
            entity.setUsuarioModificacion(user.getId());
            entity.setIpModificacion(httpRequest.getRemoteAddr());
            validate(entity);
            getDao().modify(id, entity);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    @Override
    public void actualizarEstado(Long id, boolean activo, HttpServletRequest httpRequest) 
    		throws AppException {
    	
        try {
            Usuario user = getCurrentUser();
            G entity = obtener(id);
            entity.setFechaModificacion(new Date());
            entity.setUsuarioModificacion(user.getId());
            entity.setIpModificacion(httpRequest.getRemoteAddr());
            entity.setActivo(activo);
            getDao().modify(id, entity);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public G insertar(G entity, HttpServletRequest httpRequest) throws AppException {
        try {
            Usuario user = getCurrentUser();
            entity.setFechaCreacion(new Date());
            entity.setUsuarioCreacion(user.getId());
            entity.setIpCreacion(httpRequest.getRemoteAddr());
            entity.setActivo(true);
            validate(entity);
            getDao().insert(entity);
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(500, e.getMessage());
        }
    }

    
    public G insertar(G entity) throws AppException {
        try {
            entity.setActivo(true);
            entity.setUsuarioCreacion(0L);
            entity.setFechaCreacion(new Date());
            entity.setIpCreacion("");
            validate(entity);
            getDao().insert(entity);
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(500, e.getMessage());
        }
    }
    
    public void modificar(G entity, HttpServletRequest httpRequest) throws AppException {
        try {
            Usuario user = getCurrentUser();
            entity.setFechaModificacion(new Date());
            entity.setUsuarioModificacion(0L);
            entity.setIpModificacion(httpRequest.getRemoteAddr());
            validate(entity);
            getDao().modify(entity);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void eliminar(Long id) throws AppException {
        try {
            getDao().delete(id);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    @Override
    public ListaResponse<G> listar(Integer inicio, Integer cantidad, String orderBy, 
            String orderDir, HashMap<String, Object> filtros) {
        ListaResponse<G> res = getDao().listar(inicio, cantidad, orderBy, orderDir, filtros);
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListaResponse<G> listar(HashMap<String, Object> filtros) {
        return this.listar(null, null, "id", "asc", filtros);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListaResponse<G> listar() {
        return this.listar(null);
    }
}

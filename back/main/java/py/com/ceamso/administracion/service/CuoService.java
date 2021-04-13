/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.service;

import java.util.Date;
import py.com.ceamso.administracion.dao.CuoDAO;
import py.com.ceamso.administracion.model.Cuo;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import py.com.ceamso.seguridad.model.Usuario;

/**
 * @author vgayoso
 */
@Stateless
public class CuoService extends WritableServiceImpl<Cuo, CuoDAO> {

    @Inject
    private CuoDAO dao;

    /**
     * @{@inheritDoc}
     */
    @Override
    public CuoDAO getDao() {
        return dao;
    }

    public CuoService() {
    }
    
    @Override
    public Cuo insertar(Cuo entity, HttpServletRequest httpRequest) throws AppException {
        try {
            if (dao.existeCuo(entity.getNivel(), entity.getSubNivel(), entity.getNumero())) {
                throw new AppException(500, "El código de CUO ya existe");
            }
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
    
    @Override
    public void modificar(Long id, Cuo entity, HttpServletRequest httpRequest) throws AppException {
        try {
        	if (dao.existeCuo(entity.getNivel(), entity.getSubNivel(), entity.getNumero(), id)) {
                throw new AppException(500, "El código de CUO ya existe");
            }
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

    /**
     * {@inheritDoc}
     */
    public void eliminar(Long id, HttpServletRequest httpRequest) throws AppException {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT c " +
                    "FROM Cuo c " +
                    "WHERE c.id = :id " +
                    "AND EXISTS (SELECT ca FROM Ceo ca WHERE ca.cuo.id = c.id)");
            Cuo cuoFk = getDao().verificarConstraint(id, query);
            if (cuoFk != null) {
                //cuoFk.setActivo(false);
                //modificar(id, cuoFk, httpRequest);
            	throw new AppException(500, Constantes.MENSAJE_ELIMINAR_REGISTRO);
            } else {
                eliminar(id);
            }
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.administracion.service;

import py.com.ceamso.administracion.dao.MecipDAO;
import py.com.ceamso.administracion.model.Ceo;
import py.com.ceamso.administracion.model.Mecip;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * @author vgayoso
 */
@Stateless
public class MecipService extends WritableServiceImpl<Mecip, MecipDAO> {

    @Inject
    private MecipDAO dao;

    /**
     * @{@inheritDoc}
     */
    @Override
    public MecipDAO getDao() {
        return dao;
    }

    public MecipService() {
    }
    
    @Override
    public Mecip insertar(Mecip entity, HttpServletRequest httpRequest) throws AppException {
        try {
            if (dao.existeMecip(entity.getCodigo())) {
                throw new AppException(500, "El código de Mecip ya existe");
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
    public void modificar(Long id, Mecip entity, HttpServletRequest httpRequest) throws AppException {
        try {
        	if (dao.existeMecip(entity.getCodigo(), id)) {
                throw new AppException(500, "El código de Mecip ya existe");
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
                    "FROM Mecip c " +
                    "WHERE c.id = :id " +
                    "AND EXISTS (SELECT ca.pk.idMecip FROM CptFMecip ca WHERE ca.pk.idMecip = :id)");
            Mecip mecipFk = getDao().verificarConstraint(id, query);
            if (mecipFk != null) {
                //mecipFk.setActivo(false);
                //modificar(id, mecipFk, httpRequest);
            	throw new AppException(500, Constantes.MENSAJE_ELIMINAR_REGISTRO);
            } else {
                eliminar(id);
            }
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
}

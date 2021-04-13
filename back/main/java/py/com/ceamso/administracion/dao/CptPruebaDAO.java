package py.com.ceamso.administracion.dao;

import java.math.BigInteger;
import java.util.List;
import javax.inject.Inject;
import py.com.ceamso.administracion.model.CptPrueba;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.seguridad.service.SessionService;

/**
 * @author mbaez
 */
public class CptPruebaDAO extends WritableDAO<CptPrueba> {

    @Inject
    private SessionService session;

    public List<BigInteger> lista;
    /**
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return CptPrueba.class;
    }

    public Usuario getCurrentUser() {
        return session.getCurrentUser();
    }

}

package py.com.ceamso.gestion.service;

import java.util.HashMap;
import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.gestion.dao.ConvocatoriaDAO;
import py.com.ceamso.gestion.model.Convocatoria;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.utils.AppException;

@Stateless
public class ConvocatoriaPublicaService extends WritableServiceImpl<Convocatoria, ConvocatoriaDAO> {

    @Inject
    private ConvocatoriaDAO dao;

    /**
     *
     * @{@inheritDoc}
     */
    @Override
    public ConvocatoriaDAO getDao() {
        return dao;
    }

    @Override
    public ListaResponse<Convocatoria> listar(Integer inicio, Integer cantidad,
            String orderBy, String orderDir, HashMap<String, Object> filtros) {

        if (filtros == null) {
            filtros = new HashMap<String, Object>();
        }
        filtros.put("publica", true);
        System.err.println("Listar convocatorias-publica : " + filtros.get("publica") + " - " + filtros.get("descripcion"));
        ListaResponse<Convocatoria> res = getDao().listar(inicio, cantidad, orderBy, orderDir, filtros);
        return res;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public Convocatoria obtener(Long id) throws AppException {
        try {
            Convocatoria c = getDao().get(id);
            Usuario user = getCurrentUser();
            c.setEsInteresado(getDao().estaInteresado(user, c));
            return c;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    public ConvocatoriaPublicaService() {
    }

    public boolean meInteresa(Usuario usuario, Convocatoria dto) {
        return getDao().meInteresa(usuario, dto);
    }

}

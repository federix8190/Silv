package py.com.ceamso.gestion.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import py.com.ceamso.base.WritableDAO;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.gestion.model.Convocatoria;
import py.com.ceamso.gestion.model.Interesado;
import py.com.ceamso.gestion.model.InteresadoPK;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.utils.AppException;

/**
 *
 * @author mbaez
 */
@Stateless
public class ConvocatoriaDAO extends WritableDAO<Convocatoria> {

    /**
     *
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return Convocatoria.class;
    }

    public ConvocatoriaDAO() {
    }
    
    public void subirPdf(Long id, String path) throws AppException {
        try {
            Convocatoria c = get(id);
            if (c == null) {
                throw new AppException(404, "Convocatoria con id = " + id + " no existe");
            }
            c.setPdfLocation(path);
            em.merge(c);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    public ListaResponse<Interesado> getInteresados(Long idConvocatoria, int inicio,
            int cantidad, String orderBy, String odrerDir, HashMap<String, Object> filtros) {

        StringBuilder query = new StringBuilder();
        query.append("SELECT c FROM Interesado c WHERE c.pk.idConvocatoria = :idConvocatoria");
        buildWhere(query, filtros, idConvocatoria);
        Query q = em.createQuery(query.toString());
        q.setParameter("idConvocatoria", idConvocatoria);
        setParametrers(q, filtros);
        q.setFirstResult(inicio).setMaxResults(cantidad);
        List<Interesado> list = q.getResultList();
        int total = count(filtros, idConvocatoria);
        //se construye la respuesta 
        ListaResponse<Interesado> res = new ListaResponse<>();
        res.setRows(list);
        res.setCount(total);
        return res;
    }

    private void buildWhere(StringBuilder sb, HashMap<String, Object> filtros, Long idConvocatoria) {

        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1;
        sb.append(" AND ");
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {
                sb.append(" LOWER(c.usuarioInteresado.")
                        .append(key)
                        .append(") LIKE LOWER(:")
                        .append(key)
                        .append(")");
            } else {
                sb.append("c.usuarioInteresado.").append(key)
                        .append(" = :")
                        .append(key);
            }
            //se añade el 'AND' si hay más caracteres.
            if (token < tokens) {
                sb.append(" AND ");
            }
            token++;
        }
    }

    /**
     *
     * @param sb
     * @param filtros
     */
    /*@Override
    public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1;
        sb.append(" WHERE ");

        for (String key : filtros.keySet()) {
            if (key.equals("publica")) {
                sb.append(key)
                        .append(" = :")
                        .append(key);
                //se añade el 'AND' si hay más caracteres.
                if (token < tokens) {
                    sb.append(" AND ");
                }
                token++;
            } else {
                if (filtros.get(key) instanceof String) {
                    sb.append(" LOWER(c.cargo.")
                            .append(key)
                            .append(") LIKE LOWER(:")
                            .append(key)
                            .append(")");
                } else {
                    sb.append("c.cargo.")
                            .append(key)
                            .append(" = :")
                            .append(key);
                }
                //se añade el 'AND' si hay más caracteres.
                if (token < tokens) {
                    sb.append(" AND ");
                }
                token++;
            }
        }

    }*/

    private int count(HashMap<String, Object> filtros, Long idConvocatoria) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(c) FROM Interesado c WHERE c.pk.idConvocatoria = :idConvocatoria ");
        buildWhere(query, filtros, idConvocatoria);
        Query q = em.createQuery(query.toString());
        q.setParameter("idConvocatoria", idConvocatoria);
        setParametrer(q, filtros, idConvocatoria);
        return ((Long) q.getSingleResult()).intValue();
    }

    private void setParametrer(Query q, HashMap<String, Object> filtros, Long idConvocatoria) {

        if (filtros == null) {
            return;
        }
        for (String key : filtros.keySet()) {
            Object value = filtros.get(key);
            if (filtros.get(key) instanceof String) {
                value = "%" + value + "%";
            }
            q.setParameter(key, value);
        }
    }

    public boolean meInteresa(Usuario usuario, Convocatoria dto) {

        String sql = "SELECT t FROM Interesado t "
                + "WHERE t.pk.idUsuario = :idUsuario "
                + "AND t.pk.idConvocatoria = :idConvocatoria";

        Query q = em.createQuery(sql);
        q.setParameter("idUsuario", usuario.getId());
        q.setParameter("idConvocatoria", dto.getId());
        List<Interesado> res = q.getResultList();

        if (res != null && res.size() == 0) {
            InteresadoPK pk = new InteresadoPK(usuario.getId(), dto.getId());
            Interesado entity = new Interesado();
            entity.setPk(pk);
            //entity.setUsuarioInteresado(usuario);
            //entity.setIdConvocatoria(dto.getId());
            entity.setFechaCreacion(new Date());
            em.persist(entity);
            return true;
        } else {
            Interesado entity = res.get(0);
            em.remove(entity);
            return false;
        }
    }

    public boolean estaInteresado(Usuario usuario, Convocatoria dto) {

        String sql = "SELECT t FROM Interesado t "
                + "WHERE t.pk.idUsuario = :idUsuario "
                + "AND t.pk.idConvocatoria = :idConvocatoria";

        Query q = em.createQuery(sql);
        q.setParameter("idUsuario", usuario.getId());
        q.setParameter("idConvocatoria", dto.getId());
        List<Interesado> res = q.getResultList();

        if (res != null && res.size() > 0) {
            return true;
        }
        return false;
    }

}

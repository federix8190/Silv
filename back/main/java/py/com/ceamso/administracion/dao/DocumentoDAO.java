package py.com.ceamso.administracion.dao;

import java.util.List;
import javax.persistence.Query;
import py.com.ceamso.administracion.model.Documento;
import py.com.ceamso.base.WritableDAO;

public class DocumentoDAO extends WritableDAO<Documento> {

    /**
     * 
     * @{@inheritDoc}
     */
    @Override
    public Class getEntity() {
        return Documento.class;
    }
    
    public Documento getDocumentoByUserId(Long userId) {
        
        String sql = "SELECT d FROM Documento d WHERE d.userId = :userId";
        Query q = em.createQuery(sql);
        q.setParameter("userId", userId);
        List<Documento> lista = q.getResultList();
        if (lista != null && lista.size() > 0) {
            return lista.get(0);
        }
        return null;
    }
    
}

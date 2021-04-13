package py.com.ceamso.administracion.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.administracion.dao.DocumentoDAO;
import py.com.ceamso.administracion.model.Documento;
import py.com.ceamso.base.WritableServiceImpl;

@Stateless
public class DocumentoService  extends WritableServiceImpl<Documento, DocumentoDAO> {

    @Inject
    private DocumentoDAO dao;

    /**
     *
     * @{@inheritDoc}
     */
    @Override
    public DocumentoDAO getDao() {
        return dao;
    }

    public DocumentoService() {
    }
    
    public Documento getDocumentoByUserId(Long userId) {
        
        return getDao().getDocumentoByUserId(userId);
    }
    
}

package py.com.ceamso.gestion.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import py.com.ceamso.administracion.dao.UsuarioDAO;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.gestion.dao.ConvocatoriaDAO;
import py.com.ceamso.gestion.model.Convocatoria;
import py.com.ceamso.gestion.model.Interesado;
import py.com.ceamso.reportes.dao.AnexosDAO;
import py.com.ceamso.reportes.model.Anexos;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Utils;

/**
 *
 * @author mbaez
 */
@Stateless
public class ConvocatoriaService extends WritableServiceImpl<Convocatoria, ConvocatoriaDAO> {

    @Inject
    private ConvocatoriaDAO dao;
    
    @Inject
    private AnexosDAO anexosDAO;
    
    @Inject
    private UsuarioDAO usuarioDAO;
    
    @Resource(lookup = "java:jboss/mail/CTEMailSession")
    private Session mailSession;

    @Override
    public ConvocatoriaDAO getDao() {
        return dao;
    }
    
    @Override
    public Convocatoria insertar(Convocatoria entity, HttpServletRequest httpRequest) throws AppException {
        try {
            System.err.println("Crear convocatoria");
            Usuario user = getCurrentUser();
            entity.setFechaCreacion(new Date());
            entity.setUsuarioCreacion(user.getId());
            entity.setIpCreacion(httpRequest.getRemoteAddr());
            entity.setActivo(true);
            validate(entity);
            getDao().insert(entity);
            List<Usuario> usuarios = usuarioDAO.getUsuariosNotificaciones();
            if (usuarios != null && usuarios.size() > 0) {
                System.err.println("Enviar notificaciones : " + usuarios.size());
                String[] emails = new String[usuarios.size()];
                for (int i = 0; i < usuarios.size(); i++) {
                    if (usuarios.get(i) != null && usuarios.get(i).getEmail() != null) {
                        System.err.println("Enviar notificacion : " + usuarios.get(i).getEmail());
                        emails[i] = usuarios.get(i).getEmail();
                    }
                }
                Utils.send(mailSession, emails, entity);
            }
            return entity;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }        
    
    /*@Override
    public ListaResponse<Convocatoria> listar(Integer inicio, Integer cantidad, 
            String orderBy, String orderDir, HashMap<String, Object> filtros) {
        
        ListaResponse<Convocatoria> res = getDao().listar(inicio, cantidad, orderBy, orderDir, filtros);
        List<Convocatoria> convocatorias = res.getRows();
        /*List<Long> idCargos = new ArrayList<Long>();
        for (Convocatoria c : convocatorias) {
            idCargos.add(c.getCargo().getId());
        }*/
        
        // TODO arreglar
        //HashMap<Long, Anexos> anexos = anexosDAO.getAnexos(idCargos);
        
        //List<Convocatoria> lista = new ArrayList<Convocatoria>();
        /*for (Convocatoria convocatoria : convocatorias) {
            Anexos anexo = anexos.get(convocatoria.getCargo().getId());
            Cargo cargo = convocatoria.getCargo();
            if (anexo != null) {
                cargo.setObjetoGasto(anexo.getObjetoGasto());
                cargo.setFuenteFinanciamiento(anexo.getFuenteFinanciamiento());
                cargo.setDepartamento(anexo.getCoordinacionDpto());
                cargo.setPrograma(anexo.getPrograma());
                cargo.setConcepto(anexo.getConcepto());
                cargo.setDireccion(anexo.getDireccion());
                convocatoria.setCargo(cargo);
            }
            lista.add(convocatoria);
        }*/
        
        /*res.setRows(lista);
        return res;        
    }*/
    
    /**
     * @{@inheritDoc}
     */
    @Override
    public Convocatoria obtener(Long id) throws AppException {
        try {
            Convocatoria c = getDao().get(id);
            Usuario user = getCurrentUser();
            c.setEsInteresado(getDao().estaInteresado(user, c));
            /*Anexos anexo = anexosDAO.getAnexo(c.getCargo().getId());
            if (anexo != null) {
                Cargo cargo = c.getCargo();
                cargo.setObjetoGasto(anexo.getObjetoGasto());
                cargo.setFuenteFinanciamiento(anexo.getFuenteFinanciamiento());
                cargo.setDepartamento(anexo.getCoordinacionDpto());
                cargo.setPrograma(anexo.getPrograma());
                // TODO arreglar
                //cargo.setConcepto(anexo.getConcepto());
                cargo.setDireccion(anexo.getDireccion());
                c.setCargo(cargo);
            }*/
            return c;
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public void subirPdf(Long id, String path) throws AppException {
        try {
            getDao().subirPdf(id, path);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    public ConvocatoriaService() {
    }

    public void meInteresa(Usuario usuario, Convocatoria dto) {
        getDao().meInteresa(usuario, dto);
    }
    
    public ListaResponse<Interesado> getInteresados(Long idConvocatoria, Integer inicio, 
            Integer cantidad, String orderBy, String orderDir,
            HashMap<String, Object> filtros) throws AppException {
        
        try {
            return getDao().getInteresados(idConvocatoria, inicio, cantidad, orderBy, orderDir, filtros);            
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }        
    }

}

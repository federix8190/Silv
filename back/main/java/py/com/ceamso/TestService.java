package py.com.ceamso;

import com.sun.mail.smtp.SMTPTransport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import py.com.ceamso.administracion.dao.UsuarioDAO;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.gestion.dao.ConvocatoriaDAO;
import py.com.ceamso.gestion.model.Convocatoria;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Utils;

@Stateless
public class TestService extends WritableServiceImpl<Convocatoria, ConvocatoriaDAO> {

    @Resource(lookup = "java:jboss/mail/CTEMailSession")
    private Session mailSession;
    
    @Inject
    private UsuarioDAO usuarioDAO;
    
    @Inject
    private ConvocatoriaDAO convocatoriaDAO;
    
    @PersistenceContext(unitName = "CTEPostgresPU")
    protected EntityManager em;
    
    @Override
    public Convocatoria insertar(Convocatoria c, HttpServletRequest httpRequest) throws AppException {
        
        /*try {
            Message message = new MimeMessage(mailSession);
            Address[] addresses = {
                new InternetAddress("federico.torres@konecta.com.py")
            };
            message.addRecipients(Message.RecipientType.BCC, addresses);
            message.setSubject("Test");
            message.setText("Testeando envio masivo de emails");
            Transport.send(message);
        } catch (MessagingException e) {
            System.err.println("Error al enviar mail : " + e.getMessage());
            e.printStackTrace();
        }*/
        
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
            c = convocatoriaDAO.get(12L);
            try {
                Utils.send(mailSession, emails, c);
            } catch (MessagingException ex) {
                Logger.getLogger(TestService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        
        return c;
    }
    
    public List<String> getAnexos() {
        
        /*String sql = "select distinct sft.*, l.id_estructura_presupuestaria, "
                + "     ep.cod_programa, ep.cod_tipo, ep.gestion,  p.descripcion_programa "
                + "from salarios.sfp_informe_mensual_v3_exportar(1, 0, 1, 2016, 0, '0') sft "
                + "join v_legajo l "
                + "     on sft.cedula_funcionario = l.cedula_identidad::varchar " 
                + "left join presupuesto.estructura_presupuestaria ep "
                + "     on l.id_estructura_presupuestaria = ep.id_estructura_presupuestaria "
                + "left join presupuesto.programa p "
                + "     on ep.cod_programa = p.cod_programa and ep.gestion = p.gestion "
                + "     and ep.cod_tipo = p.cod_tipo";*/
        String sql = "INSERT INTO cte.anexos_test("
            + "anho, mes, nivel_entidad, entidad, oee, linea, numero_puesto, cedula_identidad, "
            + "nombre, apellido, objeto_gasto, fuente_financiamiento, concepto, vinculacion_funcionario, "
            + "cargo, funcion_real, categoria, presupuestado, devengado, programa, subprograma, "
            + "id_ceo, codigo_ceo, denominacion_ceo, id_cuo, nivel_cuo, subnivel_cuo, numero_cuo, "
            + "denominacion_cuo, id_cpt, nivel_cpt, subnivel_cpt, numero_cpt, denominacion_cpt, titular_unidad, "
            + "id_cpt_ef, numero_secuencial, ambito_cpt_ef, codigo_proceso, denominacion_cpt_ef, "
            + "orientacion_funcional, id_cpt_ee, numero_secuencial_cpt_ee, ambito_cpt_ee, nivel_cpt_ee, "
            + "categoria_cpt_ee, denominacion_cpt_ee)"    
            + "select sft.anho, sft.mes, sft.nivel_entidad, sft.entidad, sft.oee, "
            + "	CASE WHEN sft.linea = '' THEN 0 ELSE CAST(sft.linea AS integer) END, pt.numero_puesto, "
            + "	CASE WHEN sft.cedula_funcionario = '' THEN 0 ELSE CAST(sft.cedula_funcionario AS integer) END, "
            + "	sft.nombre_funcionario, sft.apellido_funcionario, sft.objeto_gasto, " 
            + "	CASE WHEN sft.ff is null THEN 0 ELSE sft.ff END, "
            + "	sft.concepto, sft.estado, sft.cargo, sft.cargo, sft.categoria, " 
            + "	sft.presupuestado, sft.devengado, p.descripcion_programa, sp.descripcion_subprograma, "
            + "	pt.id_ceo, ceo.codigo, ceo.den, "
            + "	cuo.id, cuo.nivel, cuo.subnivel, cuo.numero, cuo.denominacion, "
            + "	pt.id_cpt, cpt.nivel, cpt.sub_nivel, cpt.nro_g, cpt.den, cpt.tit_unid, "
            + "	pt.id_cpt_ef, cptf.nro, cptf.ambito, cptf.cod_proceso, cptf.den, cptf.orientacion_func, "
            + "	pt.id_cpt_ee, cpte.nro, cpte.ambito, cpte.nivel, cpte.categoria, cpte.den " 
            + "FROM salarios.sfp_informe_mensual_v3_exportar(3, 0, 6, 2017, 0, '0') sft "
            + "LEFT JOIN legajos.funcionarios f on sft.cedula_funcionario = f.cedula_funcionario "
            + "LEFT JOIN salarios.funcionarios_categorias fc ON fc.id_funcionario_categoria = f.id_funcionario "
            + "LEFT JOIN salarios.detalles_categorias dc ON dc.id_detalle_categoria = fc.id_detalle_categoria "
            + "LEFT JOIN presupuesto.estructura_presupuestaria ep "
            + "on dc.id_estructura_presupuestaria = ep.id_estructura_presupuestaria "
            + "LEFT JOIN presupuesto.programa p ON ep.cod_programa = p.cod_programa AND ep.gestion = p.gestion "
            + "AND ep.cod_tipo = p.cod_tipo "
            + "LEFT JOIN presupuesto.subprograma sp ON p.cod_programa = sp.cod_programa "
            + "AND ep.cod_subprograma = sp.cod_subprograma "
            + "AND ep.cod_tipo = sp.gestion "
            + "LEFT JOIN cte.puesto_trabajo pt on CAST(sft.cedula_funcionario AS integer) = pt.cedula_identidad "
            + "LEFT JOIN cte.cpt on pt.id_cpt = cpt.id  " 
            + "LEFT JOIN cte.cpt_ee cpte on pt.id_cpt_ee = cpte.id "
            + "LEFT JOIN cte.cpt_ef cptf on pt.id_cpt_ee = cptf.id "
            + "LEFT JOIN cte.ceo on pt.id_ceo = ceo.id "
            + "LEFT JOIN cte.cuo on pt.id_cuo = cuo.id limit 20";
        Query query = em.createNativeQuery(sql);
        query.executeUpdate();
        /*List<Object[]> res = query.getResultList();
        /*for (Object[] datos : res) {
            lista.add((String) datos[7]);
        }*/
        List<String> lista = new ArrayList<>();
        return lista;
        
    }

    @Override
    public ConvocatoriaDAO getDao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

package py.com.ceamso;

import com.sun.mail.smtp.SMTPTransport;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.utils.Constantes;
import py.com.ceamso.utils.Utils;

import py.com.ceamso.administracion.model.Cpt;
import py.com.ceamso.administracion.service.CptService;
import py.com.ceamso.gestion.model.Convocatoria;
import py.com.ceamso.utils.AppException;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestController {
    
    @Resource(lookup = "java:jboss/mail/CTEMailSession")
    private Session mailSession;
    
    @EJB
    private TestService service;
	
    @EJB
    private CptService cptService;
    
    /*@Inject
    @Configuracion("MAIL.SMTP_HOST")
    private String smtpHost;
    
    @Inject
    @Configuracion("MAIL.USERN_EMAIL")
    private String user;
    
    @Inject
    @Configuracion("MAIL.PASSWORD_EMAIL")
    private String password;
    
    @Inject
    @Configuracion("MAIL.SMTP_PORT")
    private String smtpPort;*/
    
    @GET
    @Path("/cpt")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getNiveles() {
        
        try {
            
            //return cptService.getNiveles();
            return service.getAnexos();
            
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
		
    @GET
    @Path("/send-email")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response testSendEmail() throws MessagingException, AppException {
        
        String email = "federico.torres@konecta.com.py";
        Convocatoria c = null;
        service.insertar(c, null);
        //Utils.send(email, "", "Test Email", "Testando envio de email", smtpHost, user, password, smtpPort);
        
        /*Properties props = System.getProperties();
        Session session = mailSession.getInstance(props);
        final MimeMessage msg = new MimeMessage(session);
        //msg.setFrom(new InternetAddress(user));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));

        msg.setSubject("Test");
        msg.setContent("Testenado ...", "text/html; charset=utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
        //t.connect(smtpHost, user, password);
        t.connect();
        t.sendMessage(msg, msg.getAllRecipients());      
        t.close();*/
        
        
        
        return Response.ok().build();
    }
    
}

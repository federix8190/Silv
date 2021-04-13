package py.com.ceamso.utils;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.shiro.SecurityUtils;

import com.google.gson.Gson;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.mail.smtp.SMTPTransport;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;
import javax.mail.Address;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import py.com.ceamso.gestion.model.Convocatoria;

import py.com.ceamso.seguridad.model.Usuario;

public class Utils {

    static final String TOKENS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";
    static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String NUMEROS = "0123456789";

    public static Usuario obtenerUsuarioAutenticado() {
        Usuario usuario = null;
        Object user = SecurityUtils.getSubject().getSession().getAttribute("usuario");
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        usuario = gson.fromJson(userJson, Usuario.class);
        return usuario;
    }
    
    public static double round (double value, int precision) {
    	int scale = (int) Math.pow(10, precision);
    	return (double) Math.round(value * scale) / scale;
    }

    public static String formatearMonto(Integer monto) {
        if (monto == null) {
            return "";
        }
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        return formatea.format(monto);
    }

    public static Date sumarDias(Date fecha, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        /*cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR, 23);*/
        cal.add(Calendar.DATE, dias);
        return cal.getTime();
    }

    public static int getNroDiaActual(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    public static Date addSeccondsToCurrentDate(int secconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, secconds);
        Date d = calendar.getTime();
        return d;
    }

    public static boolean isNumeric(String cadena) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }

    public static String generarPin() {
        String clave = "";
        Random random = new Random((int) System.currentTimeMillis());
        for (int i = 0; i < 4; i++) {
            int nroRam = random.nextInt(10);
            clave += nroRam;
        }
        return clave;
    }

    public static String randomString(int len) {
        Random random = new Random();
        int posicionMayuscula = 0;
        int posicionNumero = 0;
        int i = 0;
        while (posicionMayuscula == posicionNumero) {
            if (i == 10) {
                posicionMayuscula = 3;
                posicionNumero = 4;
            } else {
                posicionMayuscula = random.nextInt(8);
                posicionNumero = random.nextInt(8);
            }
            i++;
        }

        SecureRandom rnd = new SecureRandom(longToBytes(new Date().getTime()));
        StringBuilder sb = new StringBuilder(len);
        for (i = 0; i < len; i++) {
            if (i == posicionMayuscula) {
                sb.append(MAYUSCULAS.charAt(rnd.nextInt(MAYUSCULAS.length())));
            } else if (i == posicionNumero) {
                sb.append(NUMEROS.charAt(rnd.nextInt(NUMEROS.length())));
            }
            sb.append(TOKENS.charAt(rnd.nextInt(TOKENS.length())));
        }
        return sb.toString();
    }

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }
    
    public static Integer[] getMesAnterior() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int anho = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Integer[] datos = {0,0};
        if (mes == 0) {
            datos[0] = anho - 1;
            datos[1] = 11;
        } else {
            datos[0] = anho;
            datos[1] = mes - 1;
        }
        return datos;
    }
	    
    @SuppressWarnings("restriction")
    public static void send(String recipientEmail, String ccEmail, String title, 
            String message, String smtpHost, String user, String password, 
            String port) throws AddressException, MessagingException {
        
    	Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        // Get a Properties object
        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);
        
        final MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(user));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

        if (ccEmail.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
        }

        msg.setSubject(title);
        msg.setContent(message, "text/html; charset=utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
        t.connect(smtpHost, user, password);
        t.sendMessage(msg, msg.getAllRecipients());      
        t.close();
    }
    
    public static Response send(Session mailSession, String[] emails, Convocatoria convocatoria) 
            throws MessagingException {
        
        try {
            Message message = new MimeMessage(mailSession);
            Address[] addresses = new Address[emails.length];
            for (int i = 0; i < emails.length; i++) {
                addresses[i] = new InternetAddress(emails[i]);
            }
            message.addRecipients(Message.RecipientType.BCC, addresses);
            message.setSubject("Convocatoria");
            StringBuilder sb = new StringBuilder();
            sb.append("Se ha creado una nueva convocatoria \n\n");
            sb.append("Descripción:    ");
            sb.append(convocatoria.getDescripcion());            
            sb.append("\n");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sb.append("Vigencia:         ");
            sb.append(sdf.format(convocatoria.getInicioVigencia()));
            sb.append(" - ");
            sb.append(sdf.format(convocatoria.getFinVigencia()));
            sb.append("\n");
            sb.append("Cargo:             ");
            sb.append(convocatoria.getNombre());
            sb.append("\n");
            sb.append("Categoría:       ");
            sb.append(convocatoria.getCategoria());
            sb.append("\n");
            sb.append("Presupuestado: ");
            sb.append(convocatoria.getPresupuestado());
            sb.append("\n");
            sb.append("Departamento:  ");
            sb.append(convocatoria.getDepartamento());
            sb.append("\n");
            message.setText(sb.toString());
            Transport.send(message);
        } catch (MessagingException e) {
            System.err.println("Error al enviar mail : " + e.getMessage());
            e.printStackTrace();
        }
        
        return Response.ok().build();
    }

    public static Map<String, String[]> paramsToMap(
            MultivaluedMap<String, String> parameters) {
        Map<String, String[]> map = new HashMap<>();
        Set<String> keys = parameters.keySet();
        for (String key : keys) {
            List<String> list = parameters.get(key);
            String[] a = new String[]{};
            map.put(key, (String[]) list.toArray(a));
        }
        return map;
    }

    public static boolean isAssignableFrom(Class classTarget, Class classSource,
            String attribute) {
        boolean isAssignable = !getType(classTarget, attribute).
                isAssignableFrom(classSource);
        return isAssignable;
    }
    
    public static <T> T valueOf(Class<T> type, String value) {
        try {
            Constructor<T> constructor = type.getConstructor(String.class);
            return constructor.newInstance(value);
        } catch (NoSuchMethodException | SecurityException
                | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            return null;
        }
    }
    
    public static Class getType(Class bean, String attribute) {
        try {
            return bean.getDeclaredField(attribute).getType();
        } catch (NoSuchFieldException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

}

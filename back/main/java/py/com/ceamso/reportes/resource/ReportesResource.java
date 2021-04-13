package py.com.ceamso.reportes.resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jboss.resteasy.util.Base64;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.ws.rs.QueryParam;

import py.com.ceamso.reportes.dto.CongruenciaDTO;
import py.com.ceamso.reportes.dto.MatrizCongruenciaDTO;
import py.com.ceamso.reportes.dto.PromocionesSalarialesDTO;
import py.com.ceamso.reportes.dto.PuestoRemuneracionDTO;
import py.com.ceamso.utils.ReportesService;

@Path("/reportes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReportesResource {

    @Inject
    private ReportesService reportesService;

    @GET
    @Path("/reporteexcell")
    @Produces("application/vnd.ms-excel")
    public Response getReporte(@QueryParam("parametros") String jsonString) {

        try {
            HashMap<String, Object> parametros = new ObjectMapper().readValue(jsonString, HashMap.class);
            HashMap<String, Object> map = new HashMap<String, Object>();
            JRBeanCollectionDataSource ds = null;

            String reportes = parametros.get("reporte").toString();
            int ini = jsonString.indexOf("[");
            int fin = jsonString.lastIndexOf("]") + 1;

            String nombreReporte = "";
            if (reportes.equals("PS")) {
                map.put("TITULO", "Índice de Promoción Salarial");
                nombreReporte = "promocion_salarial";
            } else if (reportes.equals("DP")) {
                map.put("TITULO", "Índice de Desarrollo de Personal");
                nombreReporte = "desarrollo_personal";
            } else if (reportes.equals("VP")) {
                map.put("TITULO", "Índice de Variacion de la Dotación de Personal");
                nombreReporte = "variacion_personal";
            } else if (reportes.equals("PR")) {
                map.put("TITULO", "Índice de Congruencia Puesto-Remuneración");
                nombreReporte = "puesto_remuneracion";
                  
                Collection<CongruenciaDTO> listadatos = new ObjectMapper().readValue(jsonString.substring(ini, fin),
                        new TypeReference<Collection<CongruenciaDTO>>() { 
                });
                ds = new JRBeanCollectionDataSource(listadatos);
            } else if (reportes.equals("MC")) {
                map.put("TITULO", "Matriz Índice de Congruencia");
                nombreReporte = "matriz_congruencia";
                String imgBase64 = parametros.get("imagen").toString().split(",")[1];
             // decode base64 encoded image
                
                // buffered image from the decoded bytes 
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(Base64.decode(imgBase64)));
                if (image == null) {
                     System.out.print("error al generar imagen");                }
               
                 ;
                String targetPath = System.getProperty("java.io.tmpdir");
        		Calendar fecha = Calendar.getInstance();
        		int anho = fecha.get(Calendar.YEAR);
        		int mes = fecha.get(Calendar.MONTH) + 1;
        		int dia = fecha.get(Calendar.DAY_OF_MONTH);
        		String nombre = nombreReporte +"-"+dia + "-" + mes + "-" + anho + ".png";
        		String outputPath = targetPath + "/" + nombre;
        		System.out.println(outputPath);
        		int contador = 1;
        		File archivo = null;
        		boolean disponible = false;
        		while (!disponible) {
        			archivo = new File(targetPath + "/" + nombre);
        			System.out.println(targetPath + "/" + nombre);
        			if (archivo.exists()) {
        				if (contador == 1) {
        					int indice = nombre.lastIndexOf(".");
        					nombre = nombre.substring(0, indice) + "(" + contador + ")" + nombre.substring(indice);
        				} else {
        					int abreP = nombre.lastIndexOf("(") + 1;
        					int cierraP = nombre.lastIndexOf(")");
        					nombre = nombre.substring(0, abreP) + contador + nombre.substring(cierraP);
        				}
        				contador++;
        			} else {
        				disponible = true;
        			}
        		}
        		  outputPath = targetPath + "/" + nombre;
        		  File f = new File(outputPath);
        		        // write the image
        	          ImageIO.write(image, "png", f);
        	          FileInputStream in = new FileInputStream(f.getAbsolutePath());
        	          map.put("CONTEXT",in);

                     ds = new JRBeanCollectionDataSource( Arrays.asList("a","b"));
        	          
            }

            
            if (!reportes.equals("MC") && !reportes.equals("PR")) {

                Collection<PromocionesSalarialesDTO> listadatos = new ObjectMapper().readValue(
                        jsonString.substring(ini, fin), new TypeReference<Collection<PromocionesSalarialesDTO>>() {
                });
                ds = new JRBeanCollectionDataSource(listadatos);
            }
            
            if (!reportes.equals("MC")){
            	 map.put("CPTEF", parametros.get("CPTEF").toString());
                 map.put("VINCULACION", parametros.get("VINCULACION").toString());
                 map.put("AMBITO", parametros.get("AMBITO").toString());
                 map.put("GENERO", parametros.get("GENERO").toString());
                 map.put("CPTEE", parametros.get("CPTEE").toString());
                 map.put("CPT", parametros.get("CPT").toString());
                 map.put("CEO", parametros.get("CEO").toString());
            	
            }
           

            String path = reportesService.generarReporte(ds, nombreReporte, map);

            File file = new File(path);

            ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition", "attachment; filename=" + file.getName());
            return response.build();
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}

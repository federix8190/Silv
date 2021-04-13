package py.com.ceamso.utils;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import com.lowagie.text.pdf.codec.Base64;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

@Stateless(name = "ReportesService", description = "Servicios de generacion de reportes")
public class ReportesService {

	// @Resource(mappedName = "java:/CTEDS")
	// DataSource ds;
	String  template = "/JasperReports_tmpl/";

	public String generarReporte(JRBeanCollectionDataSource ds,String nombreReporte,HashMap map) throws Exception {
		
		JasperDesign jd = JRXmlLoader.load(this.getClass().getResourceAsStream(template+nombreReporte+".jrxml"));
		// Compilamos el informe jrxml
		JasperReport report = JasperCompileManager.compileReport(jd);
		// Rellenamos el informe con la conexion creada y sus parametros
		// establecidos
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, ds);
		byte[] fileBytes = null;
		String targetPath = System.getProperty("java.io.tmpdir");
		Calendar fecha = Calendar.getInstance();
		int anho = fecha.get(Calendar.YEAR);
		int mes = fecha.get(Calendar.MONTH) + 1;
		int dia = fecha.get(Calendar.DAY_OF_MONTH);
		String nombre = nombreReporte +"-"+dia + "-" + mes + "-" + anho + ".xls";
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
                  /*
		JRXlsExporter exporterXLS = new JRXlsExporter();
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		exporterXLS.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputPath);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporterXLS.exportReport();*/
                 
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputPath));
                SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
                configuration.setOnePagePerSheet(false);
                configuration.setDetectCellType(true);
                configuration.setWhitePageBackground(false);
                configuration.setRemoveEmptySpaceBetweenRows(true);
                exporter.setConfiguration(configuration);

                exporter.exportReport();
 
	   return outputPath;
	}

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import py.com.ceamso.base.BaseModel;

/**
 *
 * @author daniel
 */
public class DownloadManager <T extends BaseModel> {

    protected static Logger log = Logger.getLogger(DownloadManager.class.getName());

    /**
     * Se encarga de generar un csv descargable a partir de los datos.
     *
     * @param registros todos los registros del origen de datos
     * @return la cadena de bytes que representa la planilla descargable
     */
    public byte[] toCsv(List<T> registros, long cargosTotales, long cedulas, long permanentesMasVacantes,
    		long permanentes, long contratados, long vacantes) {
    	
        log.log(Level.INFO, "Generando CSV para un total de {0} registros", registros.size());
        StringBuilder fila = new StringBuilder();
        byte[] fileByte;
        //se procesa la cabecera
        if (registros.size() <= 0) {
            return null;
        }
        T tmp = registros.get(0);
        /*
        fila.append(tmp.fileds()).append("\n");
        fileByte = fila.toString().getBytes();*/
        Date start = new Date();
        int index = 0;
        
        String[] headers = tmp.getFields();
        
        Class objClass = getClass();
        String headers1 = "Cédula Identidad;Apellido;Nombre;Fecha de Nacimiento;Fecha de Ingreso;Género"
        		+ ";Profesión;Nivel Educativo;Orientación;Edad;Antigüedad;Año SF;Mes SF;Nivel Entidad SF;"
        		+ "Entidad SF;OEE SF;Línea SF;Fuente Financiamiento SF;Programa SF;Subprograma SF;Dependencia SF;Categ. Salarial SF;Concepto SF;Presupuestado SF;Regimen SF;Vacante SF;111;113;123;125;131;133;137;141;144;145;199;849;Devengado SF;Cargo SF;Función Real SF;Nro. Puesto de Trabajo SF;Código CEO SF;Denominación CEO SF;Orientación Funcional SF;Nivel CUO SF;Subnivel CUO SF;Nro. CUO SF;Denominacion CUO SF;Nivel CPT SF;Subnivel CPT SF;Nro. CPT SF;Denominación CPT SF;Titula Unidad SF;Nro. Secuencial SF;Ámbito CPT EF SF;Código Proceso SF;Denominación CPT EF SF;Nro. Tramo SF;Mínimo SF;Máximo SF;Año;Mes;Nivel Entidad;Entidad;OEE;Línea;Fuente Financiamiento;Programa;Subprograma;Dependencia;Categ. Salarial;Concepto;Presupuestado;Regimen;Vacante;111;113;123;125;131;133;137;141;144;145;199;849;Devengado;Cargo;Función Real;Nro. Puesto de Trabajo;Código CEO;Denominación CEO;Orientación Funcional;Nivel CUO;Subnivel CUO;Nro. CUO;Denominacion CUO;Nivel CPT;Subnivel CPT;Nro. CPT;Denominación CPT;Titula Unidad;Nro. Secuencial;Ámbito CPT EF;Código Proceso;Denominación CPT EF;Nro. Tramo;Mínimo;Máximo";
        
        Boolean esAnexosNoDistribuido = false;
        if(headers.length > 0 && headers[headers.length - 1].compareTo("&nbsp;") ==  0){
            headers[headers.length - 1] = "";
            esAnexosNoDistribuido = true;
        }
        
        String header = "";
        for(String head : headers)
            header += head + ";";
        
        fila.append(";;;;;Total de cargos;").append(cargosTotales).append("\n");
        fila.append(";;;;;Total de cedulas sin repetir;").append(cedulas).append("\n");
        fila.append(";;;;;Total de permanentes + vacantes;").append(permanentesMasVacantes).append("\n");
        fila.append(";;;;;Total de permanentes ocupados;").append(permanentes).append("\n");
        fila.append(";;;;;Total de contratados;").append(contratados).append("\n");
        fila.append(";;;;;Total de vacantes;").append(vacantes).append("\n");
        fila.append("\n");
        fila.append("\n");
        fila.append("\n");
        
        if(esAnexosNoDistribuido)
            fila.append(header).append("\n").append(headers1).append("\n");
        else
            fila.append(header).append("\n");
        
        fileByte = fila.toString().getBytes();
        fila = new StringBuilder();
        
        for (T row : registros) {
            String[] columnas = row.getVaules();
            String columna = "";
            for(String c : columnas){
                if(c == null || c == "null" || "null".compareTo(c) == 0)
                    c = "";
                columna += c + ";";
            }
            fila.append(columna).append("\n");
            if(index % 20 == 0 && index != 0){
                fileByte = extendArray(fileByte, fila.toString());
                //fileByte = ArrayUtils.addAll(fileByte, fila.toString().getBytes());
                fila = new StringBuilder();
            }
            index ++;
        }
        fileByte = extendArray(fileByte, fila.toString());
        
        
        /*
        for (T row : registros) {
            fila.append(row.toString()).append("\n");
            //para controlar el cecimiento del string se limpia el buffer cada 20 líneas
            if (index % 20 == 0) {
                fileByte = extendArray(fileByte, fila.toString());
                fila = new StringBuilder();
            }
            index++;
        }*/
        
        
        Date end = new Date();
        log.log(Level.INFO, "Tiempo csv :{0}", (end.getTime() - start.getTime()));
        return fileByte;
    }

    /**
     * Se encarga de transformar la lista de registros a un xls descargable.
     *
     * @param registros todos los registros del origen de datos
     * @return la cadena de bytes que representa la planilla descargable
     * @throws BadRequestException Si ocurrio un error al generar el xls
     */
    public byte[] toXls(List<T> registros) throws RuntimeException {
        log.log(Level.INFO, "Generando XLS para un total de {0} registros", registros.size());
        //ByteArrayOutputStream out = new ByteArrayOutputStream();
        // Se crea una hoja dentro del libro
        HSSFWorkbook libro = new HSSFWorkbook();
        HSSFSheet hoja = libro.createSheet("Datos");
        int columnIndex = 0;
        //se calcula la cantidad de columnas del pdftable
        T first = registros.get(0);
        if (first == null) {
            throw new RuntimeException("No existen registros!");
        }
        //se construye el header
        String[] headers = first.getFields();
        HSSFRow header = hoja.createRow(0);
        for (String head : headers) {
            HSSFCell celda = header.createCell(columnIndex);
            // Se establece el valor de la celda
            celda.setCellValue(head);
            // se actualiza el puntero de las columnas
            columnIndex++;
        }
        Integer rowIndex = 1;
        //se construye el cuerpo del grid
        Date start = new Date();
        for (T row : registros) {
            HSSFRow fila = hoja.createRow(rowIndex);
            // Se inicializa el puntero de las celdas
            columnIndex = 0;
            String[] columnas = row.getVaules();
            // se procesan las celdas
            for (String cell : columnas) {
                //System.out.println(cell);
                // Se crea una celda dentro de la fila
                HSSFCell celda = fila.createCell(columnIndex);
                // Se establece el valor de la celda
                celda.setCellValue(cell);
                // se actualiza el puntero de las columnas
                columnIndex++;
            }
            rowIndex++;
        }
        Date end = new Date();
        log.log(Level.INFO, "Tiempo xls :{0}", (end.getTime() - start.getTime()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            libro.write(out);
            out.close();
        } catch (IOException ex) {
            throw new RuntimeException("Ocurrió un error al generar el xls");
        }
        return out.toByteArray();
    }

    /**
     * Se encarga de transformar la lista de registros a un pdf descargable.
     *
     * @param registros todos los registros del origen de datos
     * @return la cadena de bytes que representa la planilla descargable
     * @throws BadRequestException Si ocurrio un error al generar el xls
     */
    public byte[] toPDF(List<T> registros) throws RuntimeException {
        log.log(Level.INFO, "Generando PDF para un total de {0} registros", registros.size());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //We will create output PDF document objects at this point
        Document pdfOut = new Document(PageSize.LEGAL.rotate());

        try {
            PdfWriter.getInstance(pdfOut, out);
        } catch (DocumentException ex) {
            throw new RuntimeException("No puede generar el PDF!");
        }
        pdfOut.open();
        //se calcula la cantidad de columnas del pdftable
        T first = registros.get(0);
        int columns = 0;
        if (first == null) {
            throw new RuntimeException("No existen registros!");
        }
        //se construye el header
        String[] headers = first.getFields();
        columns = headers.length;
        PdfPTable pdfTable = new PdfPTable(columns);
        for (String head : headers) {
            pdfTable.addCell(new PdfPCell(new Phrase(head)));
        }
        //se construye el cuerpo del grid
        Date start = new Date();
        for (T row : registros) {
            // Se inicializa el puntero de las celdas
            // int columnIndex = 0;
            String[] columnas = row.getVaules();
            // se procesan las celdas
            for (String cell : columnas) {
                // Se crea una celda dentro de la fila
                // Se establece el valor de la celda
                pdfTable.addCell(cell);
                // se actualiza el puntero de las columnas
                // columnIndex++;
            }
            // rowIndex++;
        }
        Date end = new Date();
        log.log(Level.INFO, "Tiempo pdf :{0}", (end.getTime() - start.getTime()));

        try {
            //Finally add the table to PDF document
            pdfOut.add(pdfTable);
        } catch (DocumentException ex) {
            throw new RuntimeException("No puede escribir el PDF!");
        }
        // se cierra el document
        pdfOut.close();
        return out.toByteArray();
    }

    /**
     * Se encarga de escribir los datos en el array de bytes.
     *
     * @param array
     * @param value
     * @return
     */
    private static byte[] extendArray(byte[] array, String value) {
        byte[] strBytes = value.getBytes();
        int size = strBytes.length + array.length;
        byte[] tmp = new byte[size];
        return ArrayUtils.addAll(array, strBytes);
    }

    
}

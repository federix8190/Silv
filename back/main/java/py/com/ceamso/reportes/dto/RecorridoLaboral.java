package py.com.ceamso.reportes.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author daniel.rojas
 */
public class RecorridoLaboral {
    
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
   
    private String nroDocNombramiento;
    private String fechaNombramiento; 
    private String nombreCargo;
    private String categoriaPersonal;
    private Boolean actual;
    private String ubicacion;
    private String departamento;
    private String distrito;


    public RecorridoLaboral(){
    }
    
    public RecorridoLaboral(Object[] datos){
        
        this.nroDocNombramiento = (String) datos[0];
        Date fecha = (Date) datos[1];
        if (fecha != null) {
            this.fechaNombramiento = SDF.format(fecha);
        }
        this.nombreCargo = (String) datos[2];
        this.categoriaPersonal = (String) datos[3];
        this.actual = (Boolean) datos[4];
        this.ubicacion = (String) datos[5];
        this.departamento = (String) datos[6];
        this.distrito = (String) datos[7];
    }

    /*public RecorridoLaboral(String nroDocNombramiento, Date fechaNombramiento, String nombreCargo, String categoriaPersonal, Boolean actual, String ubicacion, String departamento, String distrito) {
        this.nroDocNombramiento = nroDocNombramiento;
        this.fechaNombramiento = fechaNombramiento;
        this.nombreCargo = nombreCargo;
        this.categoriaPersonal = categoriaPersonal;
        this.actual = actual;
        this.ubicacion = ubicacion;
        this.departamento = departamento;
        this.distrito = distrito;
    }*/

    public String getNroDocNombramiento() {
        return nroDocNombramiento;
    }

    public void setNroDocNombramiento(String nroDocNombramiento) {
        this.nroDocNombramiento = nroDocNombramiento;
    }

    public String getFechaNombramiento() {
        return fechaNombramiento;
    }

    public void setFechaNombramiento(String fechaNombramiento) {
        this.fechaNombramiento = fechaNombramiento;
    }
    
    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getCategoriaPersonal() {
        return categoriaPersonal;
    }

    public void setCategoriaPersonal(String categoriaPersonal) {
        this.categoriaPersonal = categoriaPersonal;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public Boolean getActual() {
        return actual;
    }

    public void setActual(Boolean actual) {
        this.actual = actual;
    }
    
    
}

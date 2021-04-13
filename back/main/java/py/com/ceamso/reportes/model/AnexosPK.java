package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 *
 * @author konecta
 */
@Embeddable
public class AnexosPK implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "anho")
    private Integer anho;

    @Column(name = "mes")
    private Integer mes;
    
    //@Column(name = "linea")
    //private Integer linea;
    
    @Column(name = "cedula_identidad")
    private Integer cedulaIdentidad;
    
    @Column(name = "objeto_gasto")
    private String objetoGasto;
    
    @Column(name = "concepto")    
    private String concepto;
    
    @Transient
    private String cargo;

    @Transient
    private String vinculacionFuncionario;
    
    @Transient
    private Integer presupuestado;
    
    
    @Transient
    private String sexo;
    //@Column(name = "numero_puesto")
    //private Long numeroPuestoTrabajo;

    //@Column(name = "fuente_financiamiento")
    //private Integer fuenteFinanciamiento;
    
    //@Column(name = "id_cargo")
    //private Long idCargo;
    
    public AnexosPK() {        
    }
    
    public AnexosPK(Integer anho, Integer mes, /*Integer linea,*/ String objetoGasto,             
                    String concepto, Integer cedulaIdentidad//, Long numeroPuestoTrabajo
                    // Integer fuenteFinanciamiento, Long idCargo
            ) {
        
        this.anho = anho;
        this.mes = mes;
        //this.linea = linea;
        this.objetoGasto = objetoGasto;
        //this.fuenteFinanciamiento = fuenteFinanciamiento;
        this.concepto = concepto;
        this.cedulaIdentidad = cedulaIdentidad;
        //this.numeroPuestoTrabajo = numeroPuestoTrabajo;
        //this.idCargo = idCargo;
    }
    
    /*public AnexosPK(Integer anho, Integer mes, Integer linea, String objetoGasto, 
            Integer fuenteFinanciamiento, String concepto, Integer cedulaIdentidad) {
        
        this.anho = anho;
        this.mes = mes;
        this.linea = linea;
        this.objetoGasto = objetoGasto;
        //this.fuenteFinanciamiento = fuenteFinanciamiento;
        //this.concepto = concepto;
        this.cedulaIdentidad = cedulaIdentidad;
    }*/
    
    public Integer getAnho() {
        return anho;
    }

    public void setAnho(Integer anho) {
        this.anho = anho;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }
    
    /*public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }*/

    public String getObjetoGasto() {
        return objetoGasto;
    }

    public void setObjetoGasto(String objetoGasto) {
        this.objetoGasto = objetoGasto;
    }

    /*public Long getNumeroPuestoTrabajo() {
        return numeroPuestoTrabajo;
    }

    public void setNumeroPuestoTrabajo(Long numeroPuestoTrabajo) {
        this.numeroPuestoTrabajo = numeroPuestoTrabajo;
    }*/       

    /*public Integer getFuenteFinanciamiento() {
        return fuenteFinanciamiento;
    }

    public void setFuenteFinanciamiento(Integer fuenteFinanciamiento) {
        this.fuenteFinanciamiento = fuenteFinanciamiento;
    }*/
    
    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    
    public Integer getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(Integer cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }

    /*public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }*/
    
}

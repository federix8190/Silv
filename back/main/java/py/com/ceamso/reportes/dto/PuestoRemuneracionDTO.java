package py.com.ceamso.reportes.dto;

import py.com.ceamso.base.BaseModel;

public class PuestoRemuneracionDTO extends BaseModel{

    private Integer mes;
    private Integer anho;
    private Long enRango;
    private Long sobreRango;
    private Long subRango;
    private Long totalCargos;

    public PuestoRemuneracionDTO(Integer mes, Integer anho, Long enRango, Long sobreRango, Long subRango, Long totalCargos) {
        this.mes = mes;
        this.anho = anho;
        this.enRango = enRango;
        this.sobreRango = sobreRango;
        this.subRango = subRango;
        this.totalCargos = totalCargos;
    }
    public PuestoRemuneracionDTO(){
    	
    }
    public PuestoRemuneracionDTO(Integer mes, Integer anho) {
        this.mes = mes;
        this.anho = anho;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnho() {
        return anho;
    }

    public void setAnho(Integer anho) {
        this.anho = anho;
    }

    public Long getEnRango() {
        return enRango;
    }

    public void setEnRango(Long enRango) {
        this.enRango = enRango;
    }

    public Long getSobreRango() {
        return sobreRango;
    }

    public void setSobreRango(Long sobreRango) {
        this.sobreRango = sobreRango;
    }

    public Long getSubRango() {
        return subRango;
    }

    public void setSubRango(Long subRango) {
        this.subRango = subRango;
    }

    public Long getTotalCargos() {
        return totalCargos;
    }

    public void setTotalCargos(Long totalCargos) {
        this.totalCargos = totalCargos;
    }
}

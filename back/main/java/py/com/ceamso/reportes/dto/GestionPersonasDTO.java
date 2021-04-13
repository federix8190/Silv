/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.dto;

import java.io.Serializable;

/**
 * @author mbaez
 */
public class GestionPersonasDTO implements Serializable {

    private Long totalCargos;
    private int egresos;
    private int ingresos;
    private int nuevosCargos;
    private int promocionadas;
    private Double promTramosEgresos;
    private Double promTramosIngresos;
    private Long promSalariosEgresos;
    private Long promSalariosIngresos;
    private Double promTramosInicial;
    private Long promSalariosInicial;
    private Double promTramosActual;
    private Long promSalariosActual;
    private Integer mes;
    private Integer anho;

    public GestionPersonasDTO(Integer mes, Integer anho, Long totalCargos, int egresos, 
    		int ingresos, int nuevosCargos) {
        this.mes = mes;
        this.anho = anho;
        this.totalCargos = totalCargos;
        this.egresos = egresos;
        this.ingresos = ingresos;
        this.nuevosCargos = nuevosCargos;
    }
    public GestionPersonasDTO(){}

    public GestionPersonasDTO(Integer mes, Integer anho, Long totalCargos, int egresos, 
    		int ingresos, Double promTramosEgresos, Double promTramosIngresos, 
    		Long promSalariosEgresos, Long promSalariosIngresos) {
    	
        this.mes = mes;
        this.anho = anho;
        this.totalCargos = totalCargos;
        this.egresos = egresos;
        this.ingresos = ingresos;
        this.promTramosEgresos = promTramosEgresos;
        this.promTramosIngresos = promTramosIngresos;
        this.promSalariosEgresos = promSalariosEgresos;
        this.promSalariosIngresos = promSalariosIngresos;
    }

    /**
     * Constructor para Promoción Salarial
     * @param mes
     * @param anho
     * @param totalCargos
     * @param promocionadas
     * @param promTramosInicial
     * @param promSalariosInicial
     * @param promTramosActual
     * @param promSalariosActual
     */
    public GestionPersonasDTO(Integer mes, Integer anho, Long totalCargos, int promocionadas, Double promTramosInicial, Long promSalariosInicial, Double promTramosActual, Long promSalariosActual) {
        this.mes = mes;
        this.anho = anho;
        this.totalCargos = totalCargos;
        this.promocionadas = promocionadas;
        this.promTramosInicial = promTramosInicial;
        this.promSalariosInicial = promSalariosInicial;
        this.promTramosActual = promTramosActual;
        this.promSalariosActual = promSalariosActual;
    }

    /**
     * Constructor para los meses del año
     * @param mes
     * @param anho
     */
    public GestionPersonasDTO(Integer mes, Integer anho) {
        this.mes = mes;
        this.anho = anho;
    }

    public Long getTotalCargos() {
        return totalCargos;
    }

    public void setTotalCargos(Long totalCargos) {
        this.totalCargos = totalCargos;
    }

    public int getEgresos() {
        return egresos;
    }

    public void setEgresos(int egresos) {
        this.egresos = egresos;
    }

    public int getIngresos() {
        return ingresos;
    }

    public void setIngresos(int ingresos) {
        this.ingresos = ingresos;
    }

    public int getNuevosCargos() {
        return nuevosCargos;
    }

    public void setNuevosCargos(int nuevosCargos) {
        this.nuevosCargos = nuevosCargos;
    }

    public Double getPromTramosEgresos() {
        return promTramosEgresos;
    }

    public void setPromTramosEgresos(Double promTramosEgresos) {
        this.promTramosEgresos = promTramosEgresos;
    }

    public Double getPromTramosIngresos() {
        return promTramosIngresos;
    }

    public void setPromTramosIngresos(Double promTramosIngresos) {
        this.promTramosIngresos = promTramosIngresos;
    }

    public Long getPromSalariosEgresos() {
        return promSalariosEgresos;
    }

    public void setPromSalariosEgresos(Long promSalariosEgresos) {
        this.promSalariosEgresos = promSalariosEgresos;
    }

    public Long getPromSalariosIngresos() {
        return promSalariosIngresos;
    }

    public void setPromSalariosIngresos(Long promSalariosIngresos) {
        this.promSalariosIngresos = promSalariosIngresos;
    }

    public int getPromocionadas() {
        return promocionadas;
    }

    public void setPromocionadas(int promocionadas) {
        this.promocionadas = promocionadas;
    }

    public Double getPromTramosInicial() {
        return promTramosInicial;
    }

    public void setPromTramosInicial(Double promTramosInicial) {
        this.promTramosInicial = promTramosInicial;
    }

    public Double getPromTramosActual() {
        return promTramosActual;
    }

    public void setPromTramosActual(Double promTramosActual) {
        this.promTramosActual = promTramosActual;
    }

    public Long getPromSalariosInicial() {
        return promSalariosInicial;
    }

    public void setPromSalariosInicial(Long promSalariosInicial) {
        this.promSalariosInicial = promSalariosInicial;
    }

    public Long getPromSalariosActual() {
        return promSalariosActual;
    }

    public void setPromSalariosActual(Long promSalariosActual) {
        this.promSalariosActual = promSalariosActual;
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
}

package py.com.ceamso.reportes.dto;

import py.com.ceamso.reportes.model.LegajoCargo;

public class CargoDto {    

    private String nombre;
    private String funcionReal;

    public CargoDto() {
    }
    
    public CargoDto(String nombre, String funcionReal) {
        this.nombre = nombre;
        this.funcionReal = funcionReal;
    }

    public CargoDto(LegajoCargo datos) {
        this.nombre = datos.getNombre();
        this.funcionReal = datos.getFuncionReal();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFuncionReal() {
        return funcionReal;
    }

    public void setFuncionReal(String funcionReal) {
        this.funcionReal = funcionReal;
    }
    
}

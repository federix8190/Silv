package py.com.ceamso.reportes.dto;

public class ResumenMatriz {
    
    private int congruentes;
    private int subCategorizados;
    private int sobreCategorizados;
    private int total;
    private double porcentajeCongruentes;
    private double porcentajeSubCategorizados;
    private double porcentajeSobreCategorizados;
    private String colorCongruentes;
    private String colorSubCategorizados;
    private String colorSobreCategorizados;
    
    public ResumenMatriz() {        
    }
    
    public ResumenMatriz(int congruentes, int subCategorizados, int sobreCategorizados, int total) {
        this.congruentes = congruentes;
        this.subCategorizados = subCategorizados;
        this.sobreCategorizados = sobreCategorizados;
        this.total = total;
    }

    public int getCongruentes() {
        return congruentes;
    }

    public void setCongruentes(int congruentes) {
        this.congruentes = congruentes;
    }

    public int getSubCategorizados() {
        return subCategorizados;
    }

    public void setSubCategorizados(int subCategorizados) {
        this.subCategorizados = subCategorizados;
    }

    public int getSobreCategorizados() {
        return sobreCategorizados;
    }

    public void setSobreCategorizados(int sobreCategorizados) {
        this.sobreCategorizados = sobreCategorizados;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getPorcentajeCongruentes() {
        return porcentajeCongruentes;
    }

    public void setPorcentajeCongruentes(double porcentajeCongruentes) {
        this.porcentajeCongruentes = porcentajeCongruentes;
    }

    public double getPorcentajeSubCategorizados() {
        return porcentajeSubCategorizados;
    }

    public void setPorcentajeSubCategorizados(double porcentajeSubCategorizados) {
        this.porcentajeSubCategorizados = porcentajeSubCategorizados;
    }

    public double getPorcentajeSobreCategorizados() {
        return porcentajeSobreCategorizados;
    }

    public void setPorcentajeSobreCategorizados(double porcentajeSobreCategorizados) {
        this.porcentajeSobreCategorizados = porcentajeSobreCategorizados;
    }

    public String getColorCongruentes() {
        return colorCongruentes;
    }

    public void setColorCongruentes(String colorCongruentes) {
        this.colorCongruentes = colorCongruentes;
    }

    public String getColorSubCategorizados() {
        return colorSubCategorizados;
    }

    public void setColorSubCategorizados(String colorSubCategorizados) {
        this.colorSubCategorizados = colorSubCategorizados;
    }

    public String getColorSobreCategorizados() {
        return colorSobreCategorizados;
    }

    public void setColorSobreCategorizados(String colorSobreCategorizados) {
        this.colorSobreCategorizados = colorSobreCategorizados;
    }        
    
}

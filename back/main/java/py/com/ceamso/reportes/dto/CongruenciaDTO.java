package py.com.ceamso.reportes.dto;

public class CongruenciaDTO  extends PuestoRemuneracionDTO{

	private Double pctSubRango ;
	private Double pctEnRango ;
	private Double pctSobreRango ;
	private Double pctTotal;
	private Double difEnRango;
	private Double difSubRango;
	private Double difSobreRango;
	
	public Double getPctSubRango() {
		return pctSubRango;
	}
	public void setPctSubRango(Double pctSubRango) {
		this.pctSubRango = pctSubRango;
	}
	public Double getPctEnRango() {
		return pctEnRango;
	}
	public void setPctEnRango(Double pctEnRango) {
		this.pctEnRango = pctEnRango;
	}
	public Double getPctSobreRango() {
		return pctSobreRango;
	}
	public void setPctSobreRango(Double pctSobreRango) {
		this.pctSobreRango = pctSobreRango;
	}
	public double getPctTotal() {
		return pctTotal;
	}
	public void setPctTotal(double pctTotal) {
		this.pctTotal = pctTotal;
	}
	public Double getDifEnRango() {
		return difEnRango;
	}
	public void setDifEnRango(Double difEnRango) {
		this.difEnRango = difEnRango;
	}
	public Double getDifSubRango() {
		return difSubRango;
	}
	public void setDifSubRango(Double difSubRango) {
		this.difSubRango = difSubRango;
	}
	public Double getDifSobreRango() {
		return difSobreRango;
	}
	public void setDifSobreRango(Double difSobreRango) {
		this.difSobreRango = difSobreRango;
	}
 
	
}

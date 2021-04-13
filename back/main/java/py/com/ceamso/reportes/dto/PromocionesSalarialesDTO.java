package py.com.ceamso.reportes.dto;

public class PromocionesSalarialesDTO extends GestionPersonasDTO {
	/* atributos para promociones salariales */
	private Double pctPromocionadas;
	private Double varTramoSalarial;
	private Long varSalarioBasico;
	private Double pctSalario;
	/* atributos para desarrollo personal */
	private Double varTramos;
	private Long varSalarios;
	/* atriubtos para variacion personal */
	private Double pctEgresos;
	private Double pctIngresos;
	private Double pctCargos;
	private int variacion;
	private Double pctVariacion;

	public Double getPctPromocionadas() {
		return pctPromocionadas;
	}

	public void setPctPromocionadas(Double pctPromocionadas) {
		this.pctPromocionadas = pctPromocionadas;
	}

	public Double getVarTramoSalarial() {
		return varTramoSalarial;
	}

	public void setVarTramoSalarial(Double varTramoSalarial) {
		this.varTramoSalarial = varTramoSalarial;
	}

	public Long getVarSalarioBasico() {
		return varSalarioBasico;
	}

	public void setVarSalarioBasico(Long varSalarioBasico) {
		this.varSalarioBasico = varSalarioBasico;
	}

	public Double getPctSalario() {
		return pctSalario;
	}

	public void setPctSalario(Double pctSalario) {
		this.pctSalario = pctSalario;
	}

	public Double getVarTramos() {
		return varTramos;
	}

	public void setVarTramos(Double varTramos) {
		this.varTramos = varTramos;
	}

	public Long getVarSalarios() {
		return varSalarios;
	}

	public void setVarSalarios(Long varSalarios) {
		this.varSalarios = varSalarios;
	}

	public Double getPctEgresos() {
		return pctEgresos;
	}

	public void setPctEgresos(Double pctEgresos) {
		this.pctEgresos = pctEgresos;
	}

	public Double getPctIngresos() {
		return pctIngresos;
	}

	public void setPctIngresos(Double pctIngresos) {
		this.pctIngresos = pctIngresos;
	}

	public Double getPctCargos() {
		return pctCargos;
	}

	public void setPctCargos(Double pctCargos) {
		this.pctCargos = pctCargos;
	}

	public int getVariacion() {
		return variacion;
	}

	public void setVariacion(int variacion) {
		this.variacion = variacion;
	}

	public Double getPctVariacion() {
		return pctVariacion;
	}

	public void setPctVariacion(Double pctVariacion) {
		this.pctVariacion = pctVariacion;
	}

}

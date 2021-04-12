package py.com.konecta.services;

import java.util.List;
import java.util.ArrayList;

public class ListaPaginada<T> {
	
	public static final int TODOS = -1;

	private List<T> lista;
	private int pagina;
	private int cantidad;
	private int total;
	
	public ListaPaginada() {
		
		this.lista = new ArrayList<T>();
	}
	
	public ListaPaginada(List<T> lista) {
		
		this.lista = lista;
		this.cantidad = lista.size();
	}

	public ListaPaginada(List<T> lista, int total, int pagina, int cantidad) {
		this.lista = lista;
		this.pagina = pagina;
		this.cantidad = cantidad;
		this.total = total;
	}
	
	public ListaPaginada(List<T> lista, int inicio, int total) {
		this.lista = lista;
		this.pagina = pagina;
		this.cantidad = lista.size();
		this.total = total;
	}

	public List<T> getLista() {
		return lista;
	}

	public void setLista(List<T> lista) {
		this.lista = lista;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}

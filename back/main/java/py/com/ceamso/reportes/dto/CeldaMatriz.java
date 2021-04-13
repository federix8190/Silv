package py.com.ceamso.reportes.dto;

/**
 *
 * @author konecta
 */
public class CeldaMatriz {
    
    private Long cantidad;
    private int congruente;
    
    public CeldaMatriz(Long cantidad, int congruente) {
        this.cantidad = cantidad;
        this.congruente = congruente;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public int getCongruente() {
        return congruente;
    }

    public void setCongruente(int congruente) {
        this.congruente = congruente;
    }
    
}

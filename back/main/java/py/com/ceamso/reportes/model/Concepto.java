package py.com.ceamso.reportes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_cte_concepto")
public class Concepto implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "concepto")
    private String concepto;
    
    public Concepto() {
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    
}

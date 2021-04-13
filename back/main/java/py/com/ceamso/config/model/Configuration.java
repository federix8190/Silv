package py.com.ceamso.config.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import py.com.ceamso.base.WritableEntity;

/**
 *
 * @author konecta
 */
@Entity
@Table(name = "configuration")
public class Configuration extends WritableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "name")
    private String name;
    
    @Column(name = "value")
    private String value;
    
    @Column(name = "is_secret")
    private Integer isSecret;

    public Configuration() {
    }
    
    public Configuration(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getIsSecret() {
        return isSecret;
    }

    public void setIsSecret(Integer isSecret) {
        this.isSecret = isSecret;
    }        
    
}

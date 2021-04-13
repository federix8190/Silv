/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.dto;

import java.io.Serializable;

/**
 *
 * @author daniel
 */
public class DescargasDTO implements Serializable {

    private String fileName;
    private Long anho;

    public DescargasDTO(Object[] row) {
        this.fileName = row[0].toString();
        if (row.length > 1) {
            Double d = Double.parseDouble(row[1].toString());
            this.anho = d.longValue();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getAnho() {
        return anho;
    }

    public void setAnho(Long anho) {
        this.anho = anho;
    }

    
}

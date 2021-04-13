/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.reportes.dto;

import java.io.Serializable;

/**
 *
 * @author mbaez
 */
public class MatrizDTO implements Serializable {

    private Long size;
    private Long cts;
    private Long cpt;

    public MatrizDTO(Long row, Long col, Long size) {
        this.cts = row;
        this.cpt = col;
        this.size = size;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getCts() {
        return cts;
    }

    public void setCts(Long cts) {
        this.cts = cts;
    }

    public Long getCpt() {
        return cpt;
    }

    public void setCpt(Long cpt) {
        this.cpt = cpt;
    }
}

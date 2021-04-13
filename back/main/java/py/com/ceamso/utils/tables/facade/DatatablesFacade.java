/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.utils.tables.facade;

import java.util.Map;
import py.com.ceamso.base.ListaResponse;

/**
 *
 * @author daniel
 */
public interface DatatablesFacade<T> {

    /**
     * Se encarga de obtener todos los datos teniendo en cuenta los criterios de
     * filtrado y genera un csv.
     *
     * @param list
     * @return
     */
    byte[] getCSV(ListaResponse<T> list);

    /**
     * Se encarga de obtener todos los datos teniendo en cuenta los criterios de
     * filtrado y genera un xls.
     *
     * @param list
     * @return
     */
    public byte[] getXLS(ListaResponse<T> list);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.utils.tables.dao;

import py.com.ceamso.utils.tables.filter.BaseFilter;
import java.util.List;
import java.util.Map;
import py.com.ceamso.reportes.dto.DescargasDTO;

/**
 *
 * @author daniel
 */
public interface GenericDao<T> {

    List<DescargasDTO> getDescargas();

    List<T> getEntities(List<String> names, List<List<BaseFilter<?>>> filters,
            Integer pageSize, Integer offset, Map<String, String> defaultFilters);

    Integer getEntitiesCount(Map<String, String> defaultFilters);

    Integer getFilteredEntitiesCount(List<String> names,
            List<List<BaseFilter<?>>> filters,
            Map<String, String> defaultFilters);

    List<T> getAllFilteredEntities(List<List<BaseFilter<?>>> filters);

    List<String> getWords(String column, BaseFilter<?> filter,
            Integer pageSize, Integer offset);

    Integer getWordsCount(String column, BaseFilter<?> filter);
}

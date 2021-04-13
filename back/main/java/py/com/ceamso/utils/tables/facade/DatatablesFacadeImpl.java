/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.utils.tables.facade;

import py.com.ceamso.utils.tables.filter.StringFilter;
import py.com.ceamso.utils.tables.filter.BaseFilter;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import py.com.ceamso.base.BaseModel;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableDAO;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.reportes.dao.AnexosDAO;
import py.com.ceamso.reportes.dao.GestionPersonasDAO;
import py.com.ceamso.utils.DownloadManager;
import py.com.ceamso.utils.Utils;
import py.com.ceamso.utils.tables.dao.GenericDao;

/**
 *
 * @author daniel
 */
public abstract class DatatablesFacadeImpl<T extends BaseModel> implements DatatablesFacade<T> {

    @PersistenceContext(unitName = "CTEPostgresPU")
    private EntityManager em;
    
    @Inject
    @Configuracion("esHacienda")
    private String esHacienda;
    
    @Inject
    GestionPersonasDAO gestionPersonasDAO;
    
    private boolean esHacienda() {
        return esHacienda != null && esHacienda.equalsIgnoreCase("S");
    }

    protected abstract GenericDao getDao();
    protected static Logger log = Logger.getLogger(DatatablesFacadeImpl.class.getName());
    
    private List<T> getAllEntities(Map<String, String[]> queryString, Map<String, String> defaultFilters) {
        System.out.println(queryString);
        List<String> attributes = getParamsByIndex(queryString, "columns\\[([0-9]*)\\]\\[data\\]");
        List<String> searches = getParamsByIndex(queryString, "columns\\[([0-9]*)\\]\\[search\\]\\[value\\]");
        List<Integer> sortIndexes = toIntList(getParamsByIndex(queryString, "order\\[([0-9]*)\\]\\[column\\]"));
        List<String> extraData = getParamsByIndex(queryString, "columns\\[([0-9]*)\\]\\[extraData\\]");

        if (!isSorted(sortIndexes)) {
            String msg = "DatatablesFacadeImpl<" + this.getEntityClass() + ">: La lista de indices de ordenamiento debe estar ordenada";
            log.info(msg);
            throw new RuntimeException(msg);
        }
        List<String> sortDirs = getParamsByIndex(queryString, "order\\[([0-9]*)\\]\\[dir\\]");
        String globalSearch = queryString.get("search[value]")[0];
        Integer pageSize = -1;
        Integer offset = Integer.parseInt(queryString.get("start")[0]);
        Integer draw = Integer.parseInt(queryString.get("draw")[0]);
        List<List<BaseFilter<?>>> filters = getFilters(attributes, searches, extraData, sortIndexes, sortDirs);
        filters.add(getGlobalFilters(attributes, globalSearch));
        //filters = removeEmptyFilters(filters);
        System.out.println("invoking select entities..");
        List<T> entities = getDao().getEntities(attributes, filters, pageSize, offset, defaultFilters);
        return entities;
    }
    
    @Override
    public byte[] getCSV(ListaResponse<T> list) {
    	byte[] datos = {};
    	return datos;
    }

    /**
     * {@inheritDoc}
     */
    
    public byte[] getCSV(ListaResponse<T> list, HashMap<String, Object> filtros) {
    	
        List<T> entities = new ArrayList<T>();
        for(T resp : list.getRows())
            entities.add(resp);
        
        int anho = (Integer) filtros.get("anho");
        int mes = (Integer) filtros.get("mes");
        String vinculacion = (String) filtros.get("vinculacion");
        String ambito = (String) filtros.get("ambito");
        String sexo = (String) filtros.get("sexo");
        String idCpt = (String) filtros.get("idCpt");
        String idCptEe = (String) filtros.get("idCptEe");
        String idCptEf = (String) filtros.get("idCptEf");
        String idCeo = (String) filtros.get("idCeo");
        
        long cargosTotales = 0L;
        if (esHacienda()) {
        	cargosTotales = gestionPersonasDAO.getCargosTotalesHacienda(anho, mes, vinculacion, 
        			ambito, sexo, idCpt, idCptEe, idCptEf, idCeo);
        } else {
        	cargosTotales = gestionPersonasDAO.getCargosTotales(anho, mes, vinculacion, 
        			ambito, sexo, idCpt, idCptEe, idCptEf, idCeo);
        }
        
        long cedulas = 0L;
        if (esHacienda()) {
        	cedulas = gestionPersonasDAO.getCedulasSinRepetirHacienda(anho, mes);
        } else {
        	cedulas = gestionPersonasDAO.getCedulasSinRepetir(anho, mes);
        }
        
        vinculacion = "PERMANENTE";
        long permanentesMasVacantes = 0L;
        long permanentes =  0L;
        long contratados = 0L;
        
        if (esHacienda()) {
        	permanentesMasVacantes = gestionPersonasDAO.getCargosTotalesHacienda(anho, mes, vinculacion);
        	permanentes = gestionPersonasDAO.getTotalByVinculoHacienda(anho, mes, "PERMANENTE");
            contratados = gestionPersonasDAO.getTotalByVinculoHacienda(anho, mes, "CONTRATADO");
        } else {
        	permanentesMasVacantes = gestionPersonasDAO.getCargosTotales(anho, mes, vinculacion);
        	permanentes = gestionPersonasDAO.getTotalByVinculo(anho, mes, "PERMANENTE");
            contratados = gestionPersonasDAO.getTotalByVinculo(anho, mes, "CONTRATADO");
        }
        
        long vacantes = permanentesMasVacantes - permanentes;
        
        //getAllEntities(queryString, defaultFilters);
        DownloadManager<T> d = new DownloadManager<T>();
        return d.toCsv(entities, cargosTotales, cedulas, permanentesMasVacantes, permanentes, contratados, vacantes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getXLS(ListaResponse<T> list) {
        List<T> entities = new ArrayList<T>();
        for(T resp : list.getRows())
            entities.add(resp);
        
        DownloadManager<T> d = new DownloadManager<T>();
        return d.toXls(entities);
    }

    /**
     * {@inheritDoc}
     */
    public byte[] getPDF(ListaResponse<T> list) {
        List<T> entities = new ArrayList<T>();
        for(T resp : list.getRows())
            entities.add(resp);
        DownloadManager<T> d = new DownloadManager<T>();
        return d.toPDF(entities);
    }
    
    private List<BaseFilter<?>> getGlobalFilters(List<String> attributes, String globalSearch) {
        List<BaseFilter<?>> result = new ArrayList<>();
        for (String attr : attributes) {
            StringFilter f = new StringFilter(getEntityClass(), attr);
            f.setLike(globalSearch);
            if (!f.isEmpty()) {
                result.add(f);
            }
        }
        return result;
    }

    private List<List<BaseFilter<?>>> getFilters(List<String> attrs,
            List<String> searches, List<String> extraData,
            List<Integer> sortIndexes, List<String> sortDirs) {

        Pattern p = Pattern.compile("_[><=]");
        List<List<BaseFilter<?>>> result = new ArrayList<>();

        for (int i = 0; i < attrs.size(); i++) {
            StringFilter f = new StringFilter(getEntityClass(), attrs.get(i));
            if (!searches.get(i).isEmpty()) {
                //check is the attribute have the pattern p for perfomance
                Matcher m = p.matcher(searches.get(i));
                if (m.find()) {
                    //separate value and operator
                    String[] parts = searches.get(i).split("_");
                    if (parts.length == 2) {
                        String value = parts[0];
                        String operator = parts[1];
                        if (operator.equals(">")) {
                            f.setUpper(value);
                        } else if (operator.equals("<")) {
                            f.setLower(value);
                        } else {
                            //like do the = operator for default
                            f.setLike(value);
                        }
                    }
                    log.info(f.toString());
                } else {
                    f.setLike(searches.get(i));
                }
            }

            System.out.println("f = " + f.toString());
            if (sortIndexes != null && sortIndexes.indexOf(i) >= 0) {
                f.setSortAsc(sortDirs.get(sortIndexes.indexOf(i)).equals("asc"));
                f.setSortDesc(sortDirs.get(sortIndexes.indexOf(i)).equals("desc"));
            }
            List<BaseFilter<?>> singleFilter = new ArrayList<>();
            singleFilter.add(f);
            if (extraData != null && !extraData.get(i).isEmpty()) {
                Object value = Utils.valueOf(
                        Utils.getType(getEntityClass(), extraData.get(i)),
                        f.getLike());
                if (value != null) {
                    StringFilter fExtra
                            = new StringFilter(getEntityClass(), extraData.get(i));
                    fExtra.setLike(String.valueOf(value));
                    singleFilter.add(fExtra);
                }
            }
            System.out.println("singleFilter = " + singleFilter);
            if (!f.isEmpty()) {
                result.add(singleFilter);
            }
        }
        return result;
    }

    private List<String> getColumnPaths(List<BaseFilter<?>> filters) {
        List<String> result = new ArrayList<>();
        for (BaseFilter<?> f : filters) {
            result.add(f.getPath());
        }
        return null;
    }
    
    private List<String> getParamsByIndex(Map<String, String[]> queryString, String regex) {
        return getParamsByIndex(queryString, regex, false);
    }

    private List<String> getParamsByIndex(Map<String, String[]> queryString, String regex, Boolean ignoreBlanks) {
        Pattern p = Pattern.compile(regex);
        Map<Integer, String> elemsByIndex = new TreeMap<>();

        for (String key : queryString.keySet()) {
            Matcher m = p.matcher(key);
            if (m.find()) {
                elemsByIndex.put(Integer.parseInt(m.group(1)), queryString.get(key)[0]);
            }

        }

        List<String> result = new ArrayList<>();
        for (Integer i : elemsByIndex.keySet()) {
            if (!(elemsByIndex.get(i).isEmpty() && ignoreBlanks)) {
                result.add(elemsByIndex.get(i));
            }
        }

        return result;
    }

    private Boolean isSorted(List<? extends Comparable> list) {
        boolean sorted = true;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                sorted = false;
            }
        }

        return sorted;
    }

    private List<Integer> toIntList(List<String> list) {
        List<Integer> result = new ArrayList<>();
        for (String e : list) {
            result.add(Integer.parseInt(e));
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getEntityClass() {

        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        return (Class<T>) genericSuperclass
                .getActualTypeArguments()[0];
    }

}

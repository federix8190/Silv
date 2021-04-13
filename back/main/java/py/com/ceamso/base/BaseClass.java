package py.com.ceamso.base;

import java.math.BigDecimal;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import py.com.ceamso.administracion.model.*;
import py.com.ceamso.reportes.model.AnexosPK;
import py.com.ceamso.reportes.model.CargoDisponibleView;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.seguridad.model.Rol;
import py.com.ceamso.seguridad.model.Usuario;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import py.com.ceamso.gestion.model.Convocatoria;
import py.com.ceamso.reportes.dto.CongruenciaDTO;
import py.com.ceamso.reportes.model.Anexos;
import py.com.ceamso.reportes.model.AnexosNoDistribuido;
import py.com.ceamso.reportes.model.CargoNoAsignado;
import py.com.ceamso.reportes.model.CargoVacante;

/**
 *
 * @author konecta
 */
public class BaseClass {
    
    protected ObjectMapper mapper = new ObjectMapper();
 
    protected HashMap<String, Object> setearFiltros(HashMap<String, Object> filtros, String path) 
            throws NoSuchFieldException {
        
        HashMap<String, Object> res = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : filtros.entrySet()) {
            try {
                String clave = entry.getKey();
                if (entry.getValue().toString().equals("true") 
                        || entry.getValue().toString().equals("false")) {
                    res.remove(clave);
                    if (entry.getValue().toString().equals("true")) {
                        res.put(clave, true);
                    } else {
                        res.put(clave, false);
                    }
                    //res.put(clave, entry.getValue());
                } else {
                    Class c = getClas(path);
                    String tipo = c.getDeclaredField(clave).getGenericType().toString();
                    System.err.println("Datos filtro : " + clave + " - " + tipo);
                    if (tipo.contains("java.lang.Long")) {
                        try {
                            //Long valorNumerico = new Long(valor);
//                            Long valorNumerico = (Long) entry.getValue();
                            Long valorNumerico = Long.parseLong(entry.getValue().toString());
                            res.put(clave, valorNumerico);
                        } catch (Exception e) {
                        }
                    } else if (tipo.contains("java.lang.Integer")) {
                        try {
                            Integer valorNumerico = new Integer(entry.getValue().toString());
                            res.put(clave, valorNumerico);
                        } catch (Exception e) {
                        }
                    }  else if (tipo.contains("java.math.BigDecimal") ){
                        try {
                            BigDecimal valorNumerico = new BigDecimal(entry.getValue().toString());
                            res.put(clave, valorNumerico);
                        } catch (Exception e) {
                        }
                    } else if (tipo.contains("java.lang.Boolean")) {
                        try {
                            String valor = (String) entry.getValue();
                            if (valor.compareTo("TODOS") == 0) {
                                res.remove(clave);
                                res.put(clave, valor);
                            } else {
                                Boolean valorBoolean = new Boolean(valor);
                                res.put(clave, valorBoolean);
                            }
                        } catch (Exception e) {
                            res.remove(clave);
                        }
                    } else {
                        String valor = (String) entry.getValue();
                        res.put(clave, valor);
                    }
                }
            } catch (Exception e) {
            }
        }
        
        return res;
    }
    
    private Class getClas(String path) {
        if (path.equals("/administracion/cts") 
                || path.equals("/administracion/cts/")
                || path.equals("/administracion/cts/data/csv")
                || path.equals("/administracion/cts/data/xls")
                || path.equals("/administracion/cts/data/pdf")) {
            return Cts.class;
        } else if (path.equals("/administracion/cpt-ef") 
                || path.equals("/administracion/cpt-ef/")
                || path.equals("/administracion/cpt-ef/data/csv")
                || path.equals("/administracion/cpt-ef/data/xls")
                || path.equals("/administracion/cpt-ef/data/pdf")){
            return CptF.class;
        } else if (path.equals("/administracion/cpt-ee") 
                || path.equals("/administracion/cpt-ee/")
                || path.equals("/administracion/cpt-ee/data/csv")
                || path.equals("/administracion/cpt-ee/data/xls")
                || path.equals("/administracion/cpt-ee/data/pdf")){
            return CptE.class;
        } else if (path.equals("/administracion/cpt") 
                || path.equals("/administracion/cpt/")
                || path.equals("/administracion/cpt/data/csv")
                || path.equals("/administracion/cpt/data/xls")
                || path.equals("/administracion/cpt/data/pdf")) {
            return Cpt.class;
        } else if (path.equals("/administracion/clasificacion") 
                || path.equals("/administracion/clasificacion/")
                || path.equals("/administracion/clasificacion/data/csv")
                || path.equals("/administracion/clasificacion/data/xls")
                || path.equals("/administracion/clasificacion/data/pdf")) {
            return Clasificacion.class;
        } else if (path.equals("/administracion/ceo") 
                || path.equals("/administracion/ceo/")
                || path.equals("/administracion/ceo/data/csv")
                || path.equals("/administracion/ceo/data/xls")
                || path.equals("/administracion/ceo/data/pdf")) {
            return Ceo.class;
        } else if (path.equals("/administracion/configuracion-cts") 
                || path.equals("/administracion/configuracion-cts/")
                || path.equals("/administracion/configuracion-cts/data/csv")
                || path.equals("/administracion/configuracion-cts/data/xls")
                || path.equals("/administracion/configuracion-cts/data/pdf")) {
            return ConfiguracionCts.class;
        } else if (path.equals("/administracion/cuo") 
                || path.equals("/administracion/cuo/")
                || path.equals("/administracion/cuo/data/csv")
                || path.equals("/administracion/cuo/data/xls")
                || path.equals("/administracion/cuo/data/pdf")) {
            return Cuo.class;
        } else if (path.equals("/administracion/mecip") 
                || path.equals("/administracion/mecip/")
                || path.equals("/administracion/mecip/data/csv")
                || path.equals("/administracion/mecip/data/xls")
                || path.equals("/administracion/mecip/data/pdf")) {
            return Mecip.class;
        } else if (path.equals("/administracion/congruencias") 
                || path.equals("/administracion/congruencias/")
                || path.equals("/administracion/congruencias/data/csv")
                || path.equals("/administracion/congruencias/data/xls")
                || path.equals("/administracion/congruencias/data/pdf")) {
            return CongruenciaDTO.class;
        } else if (path.equals("/anexos")
                || path.equals("/anexos/")
                || path.equals("/anexos/data/csv")
                || path.equals("/anexos/data/xls")
                || path.equals("/anexos/data/pdf")) {
            return Anexos.class;
        /*} else if (path.equals("/cargo") 
                || path.equals("/cargo/")) {
            return Cargo.class;*/
        } else if (path.equals("/cargos-no-asignados")
                || path.equals("/cargos-no-asignados/")
                || path.equals("/cargos-no-asignados/data/csv")
                || path.equals("/cargos-no-asignados/data/xls")
                || path.equals("/cargos-no-asignados/data/pdf")) {
            return CargoNoAsignado.class;
        } else if (path.equals("/cargos-disponibles")
                || path.equals("/cargos-disponibles/")
                || path.equals("/cargos-disponibles/data/csv")
                || path.equals("/cargos-disponibles/data/xls")
                || path.equals("/cargos-disponibles/data/pdf")){
            return CargoDisponibleView.class;
        } else if (path.equals("/cargos-vacantes")
                || path.equals("/cargos-vacantes/")
                || path.equals("/cargos-vacantes/data/csv")
                || path.equals("/cargos-vacantes/data/xls")
                || path.equals("/cargos-vacantes/data/pdf")){
            return CargoVacante.class;
        } else if (path.equals("/convocatorias-publica") 
                || path.equals("/convocatorias-publica/")) {
            return Convocatoria.class;
        } else if (path.equals("/convocatorias") 
                || path.equals("/convocatorias/")) {
            return Convocatoria.class;
        } else if (path.contains("/interesados")) {
            return Usuario.class;
        } else if (path.contains("/legajos") || path.contains("/legajos/")
                || path.contains("/legajos/candidatos") 
                || path.contains("/legajos/candidatos/")) {
            return Legajo.class;
        } else if (path.equals("/usuarios") || path.equals("/usuarios/")) {
            return Usuario.class;
        } else if (path.equals("/seguridad/roles") || path.equals("/seguridad/roles/")) {
            return Rol.class;
        } else if (path.equals("/administracion/cuo") 
                || path.equals("/administracion/cuo/")) {
            return Cuo.class;    
        } else if (path.equals("/administracion/mecip") 
                || path.equals("/administracion/mecip/")) {
            return Mecip.class;
        } else if (path.equals("/anexos-no-distribuido")
                || path.equals("/anexos-no-distribuido/")
                || path.equals("/anexos-no-distribuido/data/csv")
                || path.equals("/anexos-no-distribuido/data/xls")
                || path.equals("/anexos-no-distribuido/data/pdf")) {
            return AnexosNoDistribuido.class;
        } else {
            return null;
        }
    }
    
    protected HashMap<String, Object> getFiltrosLegajos(String json) {
        HashMap<String, Object> filtros = null;
        if (json != null && json.trim().length() > 2) {
            try {
                filtros = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
                });
            } catch (Exception e) {
                throw new WebApplicationException(e.getMessage(),
                        Response.Status.INTERNAL_SERVER_ERROR);
            }
        }

        if (filtros != null) {
            for (Map.Entry<String, Object> entry : filtros.entrySet()) {
                String clave = entry.getKey();
                String valor = (String) entry.getValue();
                if (clave.equals("cedulaIdentidad")) {
                    try {
                        filtros.put(clave, new Long(valor));
                    } catch (Exception e){
                        filtros.remove(clave);
                    }
                }
            }
        }
        return filtros;
    }
    
}

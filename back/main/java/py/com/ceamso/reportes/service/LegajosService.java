package py.com.ceamso.reportes.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.administracion.model.Departamento;
import py.com.ceamso.administracion.model.Distrito;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.base.ReadableServiceImpl;
import py.com.ceamso.config.Configuracion;
import py.com.ceamso.reportes.dao.AnexosDAO;

import py.com.ceamso.reportes.dao.LegajoDAO;
import py.com.ceamso.reportes.dto.CargoDto;
import py.com.ceamso.reportes.dto.CarreraAdministrativaDto;
import py.com.ceamso.reportes.model.EstudiosConcluidos;
import py.com.ceamso.reportes.dto.FormacionAcademica;
import py.com.ceamso.reportes.dto.RecorridoLaboral;
import py.com.ceamso.reportes.dto.SancionPersonal;
import py.com.ceamso.reportes.dto.SumarioPersonal;
import py.com.ceamso.reportes.model.Apercibimientos;
import py.com.ceamso.reportes.model.CarreraAdministrativa;
import py.com.ceamso.reportes.model.CursoInformatica;
import py.com.ceamso.reportes.model.Destitucion;
import py.com.ceamso.reportes.model.DiasNoTrabajados;
import py.com.ceamso.reportes.model.Eventos;
import py.com.ceamso.reportes.model.ExperienciaLaboral;
import py.com.ceamso.reportes.model.FormacionAcademicaHacienda;
import py.com.ceamso.reportes.model.Idiomas;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.reportes.model.Multas;
import py.com.ceamso.reportes.model.MultasControl;
import py.com.ceamso.reportes.model.OtrosCursos;
import py.com.ceamso.reportes.model.OtrosEstudios;
import py.com.ceamso.reportes.model.Sobreseimiento;
import py.com.ceamso.reportes.model.Sumarios;
import py.com.ceamso.reportes.model.Suspensiones;

@Stateless
public class LegajosService extends ReadableServiceImpl<Legajo, LegajoDAO> {

    @Inject
    private LegajoDAO dao;
    
//    @Inject
//    private AnexosDAO anexosDAO;
    
    @Inject
    @Configuracion("esHacienda")
    private String esHacienda;

    /**
     *
     * @{@inheritDoc}
     */
    @Override
    public LegajoDAO getDao() {
        return dao;
    }

    public LegajosService() {
    }
    
    public List<String> getVinculaciones() {
        List<String> lista = getDao().getVinculaciones();
        lista.add("TODOS");
        return lista;
    }
    
    public List<String> getAmbitos() {
        List<String> lista = getDao().getAmbitos();
        lista.add("TODOS");
        return lista;
    }
    
    public List<String> getOrientacionFunc() {
        List<String> lista = getDao().getOrientacionFunc();
        lista.add("TODOS");
        return lista;
    }
    
    public List<String> getConceptos() {
        List<String> lista = getDao().getConceptos();
        return lista;
    }
    
    public List<Departamento> getDepartamentos() {
        List<Departamento> lista = getDao().getDepartamentos();
        lista.add(new Departamento(0L, "TODOS"));
        return lista;
    }
    
    public List<Distrito> getDistritos(Long codigoDepto) {
        List<Distrito> lista = getDao().getDistritos(codigoDepto);
        lista.add(new Distrito(0L, "TODOS"));
        return lista;
    }
    
    public List<CargoDto> getCargosLegajo(Long cedulaIdentidad) {
        return getDao().getCargosLegajo(cedulaIdentidad);
    }
    
    @Override
    public ListaResponse<Legajo> listar(Integer inicio, Integer cantidad, String orderBy, 
            String orderDir, HashMap<String, Object> filtros) {
        
        if (filtros == null) {
            filtros = new HashMap();
        }
        
        if (filtros.containsKey("vinculacionFuncionario")) {
            String vinculacion = (String) filtros.get("vinculacionFuncionario");
            if (vinculacion.equalsIgnoreCase("TODOS")) {
                filtros.remove("vinculacionFuncionario");
            }
        }
        
        if (filtros.containsKey("nombreDepartamento")) {
            if (filtros.get("nombreDepartamento").equals("TODOS")) {
                filtros.remove("nombreDepartamento");
            }
        }
        
        if (filtros.containsKey("nombreDistrito")) {
            if (filtros.get("nombreDistrito").equals("TODOS")) {
                filtros.remove("nombreDistrito");
            }
        }
        
        if (orderBy.equals("id")) {
            orderBy = "apellido";
        }
        
//        if (!filtros.containsKey("anho") && !filtros.containsKey("mes")) {
//            Integer[] anhoMes = anexosDAO.getAnhoMes();
//            filtros.put("anho", anhoMes[0]);
//            filtros.put("mes", anhoMes[1]);
//        }
        
        ListaResponse<Legajo> res = getDao().listar(inicio, cantidad, orderBy, orderDir, filtros);
        return res;
    }
    
    public ListaResponse<Legajo> getCandidatos(Integer inicio, Integer cantidad, String orderBy, 
            String orderDir, HashMap<String, Object> filtros) {
        
        if (filtros == null) {
            filtros = new HashMap();
        }
        
        if (filtros.containsKey("vinculacionFuncionario")) {
            if (filtros.get("vinculacionFuncionario").equals("TODOS")) {
                filtros.remove("vinculacionFuncionario");
            }
        }
        
        if (filtros.containsKey("nombreDepartamento")) {
            if (filtros.get("nombreDepartamento").equals("TODOS")) {
                filtros.remove("nombreDepartamento");
            }
        }
        
        if (filtros.containsKey("nombreDistrito")) {
            if (filtros.get("nombreDistrito").equals("TODOS")) {
                filtros.remove("nombreDistrito");
            }
        }
        
        if (orderBy.equals("id")) {
            orderBy = "apellido";
        }
        
        Long numeroTramo = (Long) filtros.get("numeroTramo");
        filtros.remove("numeroTramo");
        
        Long idCptEe = null;
        Long idCptEf = null;
        
        if (filtros.containsKey("idCptEe")) {
            idCptEe = (Long) filtros.get("idCptEe");
            filtros.remove("idCptEe");
        }
        
        if (filtros.containsKey("idCptEf")) {
            idCptEf = (Long) filtros.get("idCptEf");
            filtros.remove("idCptEf");
        }
        
        ListaResponse<Legajo> res = getDao().getCandidatos(inicio, cantidad, 
                orderBy, orderDir, filtros, idCptEe, idCptEf, numeroTramo);
        return res;
    }
    
    public List<EstudiosConcluidos> obtenerEstudiosConcluidos(Integer ci) {
        
        return getDao().obtenerEstudiosConcluidos(ci);        
    }
    
    public List<OtrosEstudios> obtenerOtrosEstudios(Integer ci) {
        
        return getDao().obtenerOtrosEstudios(ci);        
    }
    
    public List<OtrosCursos> obtenerOtrosCursos(Integer ci) {
        
        return getDao().obtenerOtrosCursos(ci);        
    }
    
    public List<CursoInformatica> obtenerCursoInformatica(Integer ci) {
        
        return getDao().obtenerCursoInformatica(ci);        
    }
    
    public List<Idiomas> obtenerIdiomas(Integer ci) {
        
        return getDao().obtenerIdiomas(ci);        
    }
    
    public List<MultasControl> obtenerMultasControl(Integer ci) {
        
        return getDao().obtenerMultasControl(ci);        
    }
    
    public List<Multas> obtenerMultas(Integer ci) {
        
        return getDao().obtenerMultas(ci);        
    }
    
    public List<DiasNoTrabajados> obtenerDiasNoTrabajados(Integer ci) {
        
        return getDao().obtenerDiasNoTrabajados(ci);        
    }
    
    public List<ExperienciaLaboral> obtenerExperienciaLaboral(Integer ci) {
        
        return getDao().obtenerExperienciaLaboral(ci);        
    }
    
    public List<Eventos> obtenerEventos(Integer ci) {
        
        return getDao().obtenerEventos(ci);        
    }
    
    public List<Apercibimientos> obtenerApercibimientos(Integer ci) {
        
        return getDao().obtenerApercibimientos(ci);        
    }
    
    public List<Sumarios> obtenerSumarios(Integer ci) {
        
        return getDao().obtenerSumarios(ci);        
    }
    
    public List<Suspensiones> obtenerSuspensiones(Integer ci) {
        
        return getDao().obtenerSuspensiones(ci);        
    }
    
    public List<Destitucion> obtenerDestitucion(Integer ci) {
        
        return getDao().obtenerDestitucion(ci);        
    }
    
    public List<Sobreseimiento> obtenerSobreseimiento(Integer ci) {
        
        return getDao().obtenerSobreseimiento(ci);        
    }
    
    public List<FormacionAcademica> obtenerFormacionAcademica(Integer ci) {
        
        return getDao().obtenerFormacionAcademica(ci);        
    }
    
    public List<SancionPersonal> obtenerSancionPersonal(Integer ci) {
        
        return getDao().obtenerSancionPersonal(ci);        
    }
    
    public List<SumarioPersonal> obtenerSumarioPersonal(Integer ci) {
        
        return getDao().obtenerSumarioPersonal(ci);        
    }
    
    public List<FormacionAcademicaHacienda> obtenerFormacionAcademicaHacienda(Integer ci) {
        
        return getDao().obtenerFormacionAcademicaHacienda(ci);        
    }
    
    public List<RecorridoLaboral> obtenerRecorridoLaboral(Integer ci) {
        
        List<RecorridoLaboral> res = getDao().obtenerRecorridoLaboral(ci);
        return res;
    }
    
    public List<CarreraAdministrativaDto> getCarreraAdministrativa(Integer ci) {
        
        return getDao().getCarreraAdministrativa(ci);        
    }
    
    //@Schedule(hour = "*", minute = "*/5")
    public void actualizarLegajo() {
    	
    	getDao().actualizarLegajo(2018, 4);
    }

}

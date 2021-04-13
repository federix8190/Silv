/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.ceamso.base;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import static org.apache.bval.jsr303.Jsr303Features.Property.PropertyDescriptor;

/**
 *
 * @author daniel
 */
public class BaseModel {
    public String fileds() {
        StringBuilder str = new StringBuilder();
        Class objClass = getClass();
        try {
            // Get the public methods associated with this class.
            Field[] fields = objClass.getDeclaredFields();
            for (Field f : fields) {
                if (f.isAnnotationPresent(Column.class)) {
                    str = str.append(f.getName()).append(";");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str.toString();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Class objClass = getClass();
        try {
            // Get the public methods associated with this class.
            Field[] fields = objClass.getDeclaredFields();
            for (Field f : fields) {
                if (f.isAnnotationPresent(Column.class)) {
                    String methodName = "get";
                    methodName += f.getName().substring(0, 1).toUpperCase();
                    methodName += f.getName().substring(1);
                    Method method = objClass.getMethod(methodName);
                    str = str.append(method.invoke(this)).append(";");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str.toString();
    }
    
    public void setVaules(String[] values) {
        ;
    }

    /**
     * Se encarga de obtener los valores de todos los atributos del objeto.
     *
     * @return
     */
    public String[] getVaules() {
        Class objClass = getClass();
        String[] val = null;
        int i = 0;
        try {
            if("py.com.ceamso.reportes.model.Anexos".compareTo(objClass.getName())== 0) {
            	
                /*String[] methods = {"getAnho", "getMes","getNivelEntidad", "getEntidad", "getOee", "getLinea", 
                		"getObjetoGasto", "getFuenteFinanciamiento", "getPrograma", "getCoordinacionDpto", 
                		"getCategoria", "getConcepto", "getPresupuestado", "getDevengado", "getCedulaIdentidad", 
                		"getApellido", "getNombre", "getCargo", "getFuncionReal", "getNumeroPuestoTrabajo", 
                		"getCodigoCeo", "getDenominacionCeo", "getNivelCpt", "getSubNivelCpt", "getNumeroCpt", 
                		"getDenominacionCpt", "getTitulaUnidad", "getNumeroSecuencialCptE", "getAmbitoCptE", 
                		"getNivelCptE", "getCategoriaCptE", "getDenominacionCptE", "getNumeroSecuencialCptF", 
                		"getAmbitoCptF", "getNivelCptF", "getCategoriaCptF", "getDenominacionCptF", "getTramo", 
                		"getMinimo", "getMaximo"};*/
            	
            	String[] methods = {
            			"getAnho", "getMes", "getOee", "getLinea", "getObjetoGasto","getPrograma", 
            			"getSubprograma", "getDependencia", "getCategoria", "getConcepto", "getPresupuestado", 
            			"getDevengado", "getVinculacionFuncionario", "getCedulaIdentidad", "getNombre", 
            			"getApellido", "getCargo", "getFuncionReal", "getNivelCpt", "getSubNivelCpt", 
            			"getNumeroCpt", "getDenominacionCpt", "getTitulaUnidad", "getNumeroSecuencialCptE", 
            			"getAmbitoCptE", "getDenominacionCptE", "getDenominacionCptF", "getTramo", 
            			"getMinimo", "getMaximo"
            	};
                
                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            }
            else if("py.com.ceamso.reportes.model.CargoNoAsignado".compareTo(objClass.getName())== 0) {
                String[] methods = {"getAnho", "getMes", "getNombre", "getCategoria", "getPresupuestado", "getDepartamento", "getPrograma", "getSubprograma", "getTipoPresupuesto"};

                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            } else if("py.com.ceamso.reportes.model.CargoDisponibleView".compareTo(objClass.getName())== 0) {
                String[] methods = {"getAnho", "getMes", "getNombre", "getCategoria", "getPresupuestado", "getDepartamento", "getPrograma", "getSubprograma", "getTipoPresupuesto", "getCptEe", "getCptEf"};

                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            } else if("py.com.ceamso.reportes.model.CargoVacante".compareTo(objClass.getName())== 0) {
                String[] methods = {"getAnho", "getMes", "getNombre", "getCategoria", "getPresupuestado", "getDepartamento", "getPrograma", "getSubprograma", "getTipoPresupuesto", "getCptEe", "getCptEf"};

                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            } else if("py.com.ceamso.reportes.model.Legajo".compareTo(objClass.getName())== 0) {
                String[] methods = {"getAnho", "getMes", "getCedulaIdentidad", "getNombre", "getApellido", "getVinculacionFuncionario", "getNombreDepartamento", "getNombreDistrito", "getCargo", "getAntiguedadCargo", "getTitulo", "getNumeroTramo", "getNivel", "getSubNivelCpt", "getNumeroCpt", "getDenominacionCpt", "getTitularUnidad", "getNumeroSecuenciaCptE", "getAmbitoCptE", "getNivelCptE", "getDenominacionCptE", "getNumeroSecuenciaCptF", "getAmbitoCptF", "getDenominacionCptF", "getOrientacionFuncional", "getNivelCuo", "getSubNivelCuo", "getNumeroSecuencialCuo", "getDenominacionCuo"};

                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            } else if("py.com.ceamso.administracion.model.Cts".compareTo(objClass.getName())== 0) {
                String[] methods = {"getAnho", "getMes", "getNumeroTramo", "getMinimo", "getMaximo"};

                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            } else if("py.com.ceamso.administracion.model.Cpt".compareTo(objClass.getName())== 0) {
                String[] methods = {"getNivel", "getSubNivel", "getNumeroGasto", "getDenominacion", "getTituloUnidad"};

                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            } else if("py.com.ceamso.administracion.model.CptE".compareTo(objClass.getName())== 0) {
                String[] methods = {"getNivelCpt", "getSubnivelCpt", "getNumeroCpt", "getClaseGeneral", "getTituloUnidadCpt", "getNumero", "getNombreAmbito", "getNombreCategoria", "getNivel", "getDenominacion"};

                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            } else if("py.com.ceamso.administracion.model.CptF".compareTo(objClass.getName())== 0) {
                String[] methods = {"getNivelCpt", "getSubnivelCpt", "getNumeroCpt", "getClaseGeneral", "getTituloUnidadCpt", "getNumero", "getNombreAmbito", "getDen", "getNombreOrientacionFuncional", "getCodigoMecip"};

                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            } else if("py.com.ceamso.administracion.model.Ceo".compareTo(objClass.getName())== 0) {
                String[] methods = {"getCodigo", "getDenominacion", "getNivelCuo", "getDenominacionCuo"};

                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            } else if("py.com.ceamso.administracion.model.Cuo".compareTo(objClass.getName())== 0) {
                String[] methods = {"getNivel", "getSubNivel", "getNumero", "getDenominacion"};

                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            }  else if("py.com.ceamso.administracion.model.Mecip".compareTo(objClass.getName())== 0) {
                String[] methods = {"getCodigo", "getDenominacionMECIP"};

                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            } else if("py.com.ceamso.administracion.model.ConfiguracionCts".compareTo(objClass.getName())== 0) {
                String[] methods = {"getNumeroTramo", "getPeso", "getMinimo", "getMaximo"};

                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            } else if("py.com.ceamso.reportes.model.AnexosNoDistribuido".compareTo(objClass.getName())== 0) {
                String[] methods = {"getCedulaIdentidad", "getApellido", "getNombre", "getFechaNacimiento", "getFechaIngreso", "getSexo", "getProfesion", "getNivelEducativoFile", "getOrientacionFile", "getEdad", "getAntiguedad", "getAnhoSF","getMesSF", "getNivelEntidadSF", "getEntidadSF", "getOeeSF", "getLineasSF", "getFuenteFinanciamientoSF", "getProgramaSF", "getSubprogramaSF", "getDependenciaSF", "getCategoriaSalarialSF", "getConceptoSF", "getPresupuestadoSF", "getRegimenSF", "getVacanteSF", "getObjetoGastoSFFile", "getDevengadoSF", "getCargoSF", "getFuncionRealSF", "getNumeroPuestoTrabajoSF", "getCodigoCeoSF", "getDenominacionCeoSF", "getOrientacionFuncionalSF", "getNivelCuoSF", "getSubnivelCuoSF", "getNumeroCuoSF", "getDenominacionCuoSF", "getNivelCptSF", "getSubnivelCptSF", "getNumeroCptSF", "getDenominacionCptSF", "getTitulaUnidadSF", "getNumeroSecuencialSF", "getAmbitoCptEfSF", "getCodigoProcesoSF", "getDenominacion_cpt_ef_sf", "getNumeroTramoSF", "getMinimoSF", "getMaximoSF", "getAnho","getMes", "getNivelEntidad", "getEntidad", "getOee", "getLineas", "getFuenteFinanciamiento", "getPrograma", "getSubprograma", "getDependencia", "getCategoriaSalarial", "getConcepto", "getPresupuestado", "getRegimen", "getVacante", "getObjetoGastoFile", "getDevengado", "getCargo", "getFuncionReal", "getNumeroPuestoTrabajo", "getCodigoCeo", "getDenominacionCeo", "getOrientacionFuncional", "getNivelCuo", "getSubnivelCuo", "getNumeroCuo", "getDenominacionCuo", "getNivelCpt", "getSubnivelCpt", "getNumeroCpt", "getDenominacionCpt", "getTitulaUnidad", "getNumeroSecuencial", "getAmbitoCptEf", "getCodigoProceso", "getDenominacion_cpt_ef_", "getNumeroTramo", "getMinimo", "getMaximo"};

                val = new String[methods.length];

                for(String m : methods){
                    Method method = null;
                    try {
                        method = objClass.getMethod(m);
                        val[i] = method.invoke(this) + "";
                     } catch (Exception ex) {
                        //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            } else {
                
                // Get the public methods associated with this class.
                Field[] fields = objClass.getDeclaredFields();
                val = new String[fields.length];
                for (Field f : fields) {
                    if (f.isAnnotationPresent(Column.class)) {
                        String methodName = "get";
                        methodName += f.getName().substring(0, 1).toUpperCase();
                        methodName += f.getName().substring(1);
                        Method method = objClass.getMethod(methodName);
                        val[i] = method.invoke(this) + "";
                        i++;
                    }
                }

            }

        } catch (Exception ex) {
            //Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return val;
    }
    
    public void setFields(String[] datos) {
        ;
    }

    /**
     * Se encarga de obtener los valores de todos los nombres de los atirubutos
     * de la clase.
     *
     * @return
     */
    public String[] getFields() {
    	
        Class objClass = getClass();
        String[] val = null;
        
        if ("py.com.ceamso.reportes.model.Anexos".compareTo(objClass.getName()) == 0) {
        	
            /*String[] v = {"Año", "Mes", "Nivel_Enti", "Entidad", "OEE", "Linea", "Objeto_Gto", "F.F", 
            		"Programa", "SubPrograma", "Categ.", "Concepto", "Presupuestado", "Devengado", 
            		"Cedula", "Apellidos", "Nombres", "Cargos", "Funcion Real", "Nro. Puesto Trabajo", 
            		"Cód. CEO", "Denonminación CEO", "Nivel CPT", "Subnivel CPT", "Nro. CPT", "Denominación CPT",
            		"Titular Unidad", "Nro. CPT-EE", "Ámbito CPT-EE", "Nivel CPT-EE", "Categoría CPT-EE", 
            		"Denominación CPT-EE", "Nro. CPT-EF", "Ámbito CPT-EF", "Nivel CPT-EF", "Categoría CPT-EF", 
            		"Denominación CPT-EF", "Nro. Tramo", "Mínimo del Tramo", "Máximo del Tramo"};*/
        	
        	String[] v = {"Año", "Mes", "OEE", "Linea", "Objeto_Gto", "Programa", "SubPrograma", "Dependencia",
	        			"Categoria", "Concepto", "Presupuestado", "Devengado", "Vinculacion Funcionario",
	        			"Cedula", "Nombres", "Apellidos", "Cargos", "Funcion Real", 
	        			"Nivel CPT", "Subnivel CPT", "Nro. CPT", "Denominación CPT", "Titular Unidad",
	        			"Nro Secuencial", "Ámbito", "CPT EE", "CPT EF", "Nro. Tramo", "Mínimo", "Máximo"};
            return v;
            
        } else if("py.com.ceamso.reportes.model.CargoNoAsignado".compareTo(objClass.getName()) == 0) {
        	
            String[] v = {"Año", "Mes", "Descripción", "Categoría", "Salario Presupuestado", "Departamento", 
            		"Programa", "Subprograma", "Tipo Presupuesto"};
            return v;
            
        } else if("py.com.ceamso.reportes.model.CargoDisponibleView".compareTo(objClass.getName()) == 0) {
        	
            String[] v = {"Año", "Mes", "Descripción", "Categoría", "Salario Presupuestado", "Departamento", 
            		"Programa", "Subprograma", "Tipo Presupuesto", "CptEe", "CptEf"};
            return v;
            
        } else if ("py.com.ceamso.reportes.model.CargoVacante".compareTo(objClass.getName()) == 0) {
            
        	String[] v = {"Año", "Mes", "Descripción", "Categoría", "Salario Presupuestado", "Departamento", 
            		"Programa", "Subprograma", "Tipo Presupuesto", "CptEe", "CptEf"};
            return v;
            
        } else if("py.com.ceamso.reportes.model.Legajo".compareTo(objClass.getName()) == 0){
            String[] v = {"Año", "Mes", "CI", "Nombre", "Apellido", "Vinculación", "Circunscripción", "Distrito", "Cargo", "Antigüedad", "Título", "Tramo", "Nivel CPT", "Subnivel CPT", "Número CPT", "Denominación CPT", "Título Unidad", "Número CPT EE", "Ámbito CPT EE", "Nivel CPT EE", "Denominación CPTEE", "Número CPT EF", "Ámbito CPT EF", "Denominación CPT EF", "Orientación Funcional", "Nivel CUO", "Subnivel CUO", "Número CUO", "Denominación CUO"};
            return v;
        } else if("py.com.ceamso.administracion.model.Cts".compareTo(objClass.getName()) == 0){
            String[] v = {"Año", "Mes", "Número Tramo", "Mínimo", "Máximo"};
            return v;
        } else if("py.com.ceamso.administracion.model.Cpt".compareTo(objClass.getName()) == 0){
            String[] v = {"Nivel", "SubNivel", "Número Gasto", "Denominación del Puesto", "Titula Unidad"};
            return v;
        } else if("py.com.ceamso.administracion.model.CptE".compareTo(objClass.getName()) == 0){
            String[] v = {"Nivel G", "SubNivel G", "Nro. G", "Clase General", "Titular Unidad", "Nro. Secuencial", "Ámbito", "Categoría", "Nivel", "Clase EE"};
            return v;
        } else if("py.com.ceamso.administracion.model.CptF".compareTo(objClass.getName()) == 0){
            String[] v = {"Nivel G", "SubNivel G", "Nro. G", "Clase General", "Titular Unidad", "Nro. Secuencial", "Ámbito", "Clase EE", "Orientación Funcional", "Código Mecip"};
            return v;
        } else if("py.com.ceamso.administracion.model.Ceo".compareTo(objClass.getName()) == 0){
            String[] v = {"Código", "Denominación", "Nivel CUO", "Denominación CUO"};
            return v;
        } else if("py.com.ceamso.administracion.model.Cuo".compareTo(objClass.getName()) == 0){
            String[] v = {"Nivel", "SubNivel", "Número", "Denominación"};
            return v;
        } else if("py.com.ceamso.administracion.model.Mecip".compareTo(objClass.getName()) == 0){
            String[] v = {"Código", "Denominación"};
            return v;
        } else if("py.com.ceamso.administracion.model.ConfiguracionCts".compareTo(objClass.getName()) == 0){
            String[] v = {"Número Tramo", "Peso", "Mínimo", "Máximo"};
            return v;
        } else if("py.com.ceamso.reportes.model.AnexosNoDistribuido".compareTo(objClass.getName()) == 0){
            String[] v = {"Datos Personales","","","","","","Profesión","Clasificación de Datos Educativos","","","","Datos Base Situación Final","","Datos Presupuestarios y Salariales","","","","","","","","","","","","","","","","","","","","","","","","","","Datos Ocupacionales","","","Datos Estructura","","","","","","","Datos CPT Específico Funcional","","","","","","","","","Clasificador de Tramos Salariales","","","Datos Base Situación Inicial","","Datos Presupuestarios y Salariales","","","","","","","","","","","","","","","","","","","","","","","","","","Datos Ocupacionales","","","Datos Estructura","","","","","","","Datos CPT Específico Funcional","","","","","","","","","Clasificador de Tramos Salariales","","&nbsp;"};
            return v;
        } else {
            try {
                // Get the public methods associated with this class.
                Field[] fields = objClass.getDeclaredFields();
                Method[] methods = objClass.getMethods();

                for(Method m : methods){
                    String methodName = "get";
                    if(m.getName().contains(methodName)){

                    }
                }

                val = new String[fields.length];
                int i = 0;

                for (Field f : fields) {
                    if (f.isAnnotationPresent(Column.class)) {
                        val[i] = f.getName();
                        i++;
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return val;
    }

}

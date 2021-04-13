package py.com.ceamso.administracion.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import py.com.ceamso.administracion.dao.CeoDAO;
import py.com.ceamso.administracion.dao.CuoDAO;
import py.com.ceamso.administracion.dao.OrientacionFuncDAO;
import py.com.ceamso.administracion.model.Ceo;
import py.com.ceamso.administracion.model.Cuo;
import py.com.ceamso.administracion.model.OrientacionFunc;
import py.com.ceamso.base.WritableServiceImpl;
import py.com.ceamso.utils.AppException;
import py.com.ceamso.utils.Constantes;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import py.com.ceamso.administracion.view.CeoLegajosView;
import py.com.ceamso.base.ListaResponse;
import py.com.ceamso.reportes.dao.AnexosDAO;
import py.com.ceamso.reportes.model.Legajo;
import py.com.ceamso.seguridad.model.Usuario;
import py.com.ceamso.seguridad.resource.UsuarioController;

/**
 * @author mbaez
 */
@Stateless
public class CeoService extends WritableServiceImpl<Ceo, CeoDAO> {

    @Inject
    private CeoDAO dao;
    
    @Inject
    private CuoDAO cuoDAO;
    
    @Inject
    private AnexosDAO anexosDAO;
    
    @Inject
    private OrientacionFuncDAO orientacionFuncDAO;

    /**
     * @{@inheritDoc}
     */
    @Override
    public CeoDAO getDao() {
        return dao;
    }

    public CeoService() {
    }
    
    public ListaResponse<Ceo> getByRange(int anho, int mes) {
    	
    	return getDao().getByRange(anho, mes);
    }
    
    public void actualizarCeo(List<InputPart> inputParts, HttpServletRequest httpRequest) {
    	
    	InputStream is = null;
        XSSFWorkbook workbook = null;
        Usuario user = getCurrentUser();

        try {

            InputPart inputPart = inputParts.get(0);
            MultivaluedMap<String, String> header = inputPart.getHeaders();
            
            // Guardar archivo en disco
            is = inputPart.getBody(InputStream.class, null);
            //byte[] bytes = IOUtils.toByteArray(is);
            
            workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            DataFormatter dataFormatter = new DataFormatter(java.util.Locale.US);
            dataFormatter.addFormat("m/d/yy", new java.text.SimpleDateFormat("M/d/yyyy"));
            
            int rowNumber = 0;
            while (rowIterator.hasNext()) {
            	
                Row row = rowIterator.next();
                
                rowNumber++;
                
                if (rowNumber > 7) {
                	
	                Iterator<Cell> cellIterator = row.cellIterator();
	                int cellNumber = 0;
	                Ceo entity = new Ceo();
	                String codigo = null;
	                boolean sinCambio = false;
	                boolean nuevo = false;
	                boolean modificado = false;
	                boolean nuevaDenominacion = false;
	                boolean eliminado = false;
	                boolean orientacion1 = false;
	                boolean orientacion2 = false;
	                boolean orientacion3 = false;
	                boolean orientacion4 = false;
	                boolean orientacion5 = false;
	                Integer nivel = null;
	                Integer subNivel = null;
	                Integer numero = null;
	                Date inicioVigencia = null;
	                Date finVigencia = null;
	                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	                String fechaInicio = null;
	                
	                while (cellIterator.hasNext()) {
	                	
	                    Cell cell = cellIterator.next();
	                    String cellValue = dataFormatter.formatCellValue(cell);
	                    if (cellValue != null) cellValue = cellValue.trim();
	                    
	                    if (cellNumber == 1) {
	                    	codigo = cellValue.trim();
	                    	//System.err.println("Codigo : " + codigo);
	                    	if (codigo != null && !codigo.isEmpty()) {
		                    	int len = codigo.length() - 1;
		                    	//System.err.println("Cambiar Codigo : " + codigo.substring(len));
		                		if (codigo.substring(len).equals(".")) {
		                			codigo = codigo.substring(0, len);
		                		}
		                		codigo = getCodigo(codigo);
		                    	entity.setCodigo(codigo);
	                    	}
	                    } else if (cellNumber == 2) {
	                    	entity.setDenominacion(cellValue);
	                    } else if (cellNumber == 5) {
	                    	if (cellValue != null && !cellValue.isEmpty()) {
	                    		if (cellValue.equalsIgnoreCase("X")) {
	                    			orientacion1 = true;
	                    		}
	                    	}
	                    } else if (cellNumber == 6) {
	                    	if (cellValue != null && !cellValue.isEmpty()) {
	                    		if (cellValue.equalsIgnoreCase("X")) {
	                    			orientacion2 = true;
	                    		}
	                    	}
	                    } else if (cellNumber == 7) {
	                    	if (cellValue != null && !cellValue.isEmpty()) {
	                    		if (cellValue.equalsIgnoreCase("X")) {
	                    			orientacion3 = true;
	                    		}
	                    	}
	                    } else if (cellNumber == 8) {
	                    	if (cellValue != null && !cellValue.isEmpty()) {
	                    		if (cellValue.equalsIgnoreCase("X")) {
	                    			orientacion4 = true;
	                    		}
	                    	}
	                    } else if (cellNumber == 9) {
	                    	if (cellValue != null && !cellValue.isEmpty()) {
	                    		if (cellValue.equalsIgnoreCase("X")) {
	                    			orientacion5 = true;
	                    		}
	                    	}
	                    } else if (cellNumber == 10) {
	                    	if (cellValue != null && !cellValue.isEmpty()) {
		                    	try {
		                    		//System.err.println("Fecha : " + cellValue);
		                    		String[] componentesFecha = cellValue.split("/");
		                    		if (componentesFecha.length == 3) {
		                    			fechaInicio = componentesFecha[2] + "-" + componentesFecha[0] + "-" + componentesFecha[1];
				                    	inicioVigencia = format.parse(fechaInicio);
				                    	entity.setInicioVigencia(inicioVigencia);
		                    		}
		                    	} catch (ParseException e) {
		                    		//System.err.println("Error fecha inicio : " + cellValue + " - " + e.getMessage());
		                    	}
	                    	}
	                    } else if (cellNumber == 12) {
	                    	if (cellValue != null && !cellValue.isEmpty()) {
		                    	try {
		                    		String[] componentesFecha = cellValue.split("/");
		                    		if (componentesFecha.length == 3) {
		                    			String fecha = componentesFecha[2] + "-" + componentesFecha[0] + "-" + componentesFecha[1];
				                    	finVigencia = format.parse(fecha);
				                    	entity.setFinVigencia(finVigencia);
		                    		}
		                    	} catch (ParseException e) {
		                    		;
		                    	}
	                    	}
	                    } else if (cellNumber == 16) {
	                    	if (cellValue != null && !cellValue.isEmpty() 
	                    			&& cellValue.equalsIgnoreCase("X")) {
	                    		sinCambio = true;
	                    	}
	                    } else if (cellNumber == 17) {
	                    	if (cellValue != null && !cellValue.isEmpty() 
	                    			&& cellValue.equalsIgnoreCase("X")) {
	                    		nuevo = true;
	                    	}
	                    } else if (cellNumber == 14) {
	                    	if (cellValue != null && !cellValue.isEmpty()) {
	                    		
	                    		nuevaDenominacion = true;
	                    		try {
		                    		String[] componentesFecha = cellValue.split("/");
		                    		if (componentesFecha.length == 3) {
		                    			String fecha = componentesFecha[2] + "-" + componentesFecha[0] + "-" + componentesFecha[1];
				                    	finVigencia = format.parse(fecha);
				                    	entity.setFinVigencia(finVigencia);
		                    		}
		                    	} catch (ParseException e) {
		                    		;
		                    	}
	                    	}
	                    } else if (cellNumber == 18) {
	                    	if (cellValue != null && !cellValue.isEmpty() 
	                    			&& cellValue.equalsIgnoreCase("X")) {
	                    		modificado = true;
	                    	}
	                    } else if (cellNumber == 20) {
	                    	if (cellValue != null && !cellValue.isEmpty() 
	                    			&& cellValue.equalsIgnoreCase("X")) {
	                    		eliminado = true;
	                    	}
	                    } else if (cellNumber == 24) {
	                    	if (cellValue != null && !cellValue.isEmpty()) {
	                    		nivel = new Integer(cellValue);
	                    	}
	                    } else if (cellNumber == 25) {
	                    	if (cellValue != null && !cellValue.isEmpty()) {
                    			subNivel = new Integer(cellValue);
	                    	}
                    	} else if (cellNumber == 26) {
	                    	if (cellValue != null && !cellValue.isEmpty()) {
                    			numero = new Integer(cellValue);
	                    	}
                    	}
	                    cellNumber++;
	                }
	                
	                Cuo cuo = null;
	                if (nivel != null) {
	                	cuo = cuoDAO.getCuo(nivel, subNivel, numero);
	                }
	                
	                OrientacionFunc orientacion = null; 
	                if (orientacion1) {
	                	orientacion = orientacionFuncDAO.getOrientacion("Conducción política");
	                } else if (orientacion2) {
	                	orientacion = orientacionFuncDAO.getOrientacion("Conduccion de gestión");
	                } else if (orientacion3) {
	                	orientacion = orientacionFuncDAO.getOrientacion("Poducción externa para la sociedad");
	                } else if (orientacion4) {
	                	orientacion = orientacionFuncDAO.getOrientacion("Prod Ext P/ Adm Públ");
	                } else if (orientacion5) {
	                	orientacion = orientacionFuncDAO.getOrientacion("Prod Interna institucion");
	                }
	                
	                if (orientacion != null) {
	                	System.err.println("orientacion : " + orientacion.getId() + " - " + orientacion.getNombre());
	                }
	                
	                if (codigo != null && !codigo.isEmpty()) {
	                	
	                	Ceo ceo = dao.getCeoByCodigo(codigo);
	                	
	                	if (ceo == null) {
		                	//System.err.println("Insertar CEO : " + codigo + " - " + fechaInicio + " - " + nivel + "." + subNivel + "." + numero);
		                	if (eliminado || finVigencia != null) {
		                		entity.setActivo(false);
		                	} else {
		                		entity.setActivo(true);
		                	}
		                	if (cuo != null) {
		                		entity.setCuo(cuo);
		                	}
		                	
		                	if (orientacion != null) {
		                		entity.setOrientacionFunc(orientacion);
			                }
		                	
			                entity.setFechaCreacion(new Date());
			                entity.setUsuarioCreacion(user.getId());
			                entity.setIpCreacion(httpRequest.getRemoteAddr());
			                getDao().insert(entity);
	                	
	                	} else {
	                		
	                		if (orientacion != null) {
			                	ceo.setOrientacionFunc(orientacion);
			                }
	                		
	                		if (nuevaDenominacion) {
	                			
	                			boolean activo = ceo.getActivo();
	                			if (activo) {
	                				System.err.println("Crear : " + codigo);
		                			ceo.setActivo(false);
		                			ceo.setFinVigencia(finVigencia);
		                			dao.modify(ceo);
		                			
		                			if (eliminado || finVigencia != null) {
				                		entity.setActivo(false);
				                	} else {
				                		entity.setActivo(true);
				                	}
				                	if (cuo != null) {
				                		entity.setCuo(cuo);
				                	}
					                entity.setFechaCreacion(new Date());
					                entity.setUsuarioCreacion(user.getId());
					                entity.setIpCreacion(httpRequest.getRemoteAddr());
					                getDao().insert(entity);
	                			}
	                		} else if (eliminado) {
	                			
	                			ceo.setActivo(false);
	                			ceo.setFinVigencia(finVigencia);
	                			dao.modify(ceo);
	                			
	                		} else if (modificado) {
	                			
	                			ceo.setActivo(false);
	                			ceo.setFinVigencia(finVigencia);
	                			dao.modify(ceo);
	                			
	                		}
	                		
	                	}
	                }
                }
            }

        } catch (Exception e) {
        	
            System.err.println("Exportar datos CEO Error enviar : " + e.getMessage());
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
            
        } finally {
            if (is != null) {
                try {
                    if (is != null) is.close();
                    if (workbook != null) workbook.close();
                } catch (IOException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private String getCodigo(String codigo) {
    	String res = "";
    	for (int i = 0; i < codigo.length(); i++) {
    		char c = codigo.charAt(i);
    		if (c != ' ') {
    			res = res + c;
    		}
    	}
    	return res;
    }
    
    @Override
    public Ceo insertar(Ceo entity, HttpServletRequest httpRequest) throws AppException {
        try {
            if (dao.existeCeo(entity.getCodigo())) {
                throw new AppException(500, "El código de CEO ya existe");
            }
            Usuario user = getCurrentUser();
            entity.setFechaCreacion(new Date());
            entity.setUsuarioCreacion(user.getId());
            entity.setIpCreacion(httpRequest.getRemoteAddr());
            entity.setActivo(true);
            validate(entity);
            getDao().insert(entity);
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(500, e.getMessage());
        }
    }
    
    @Override
    public void modificar(Long id, Ceo entity, HttpServletRequest httpRequest) throws AppException {
        try {
        	if (dao.existeCeo(entity.getCodigo(), id)) {
                throw new AppException(500, "El código de CEO ya existe");
            }
            Usuario user = getCurrentUser();
            entity.setFechaModificacion(new Date());
            entity.setUsuarioModificacion(user.getId());
            entity.setIpModificacion(httpRequest.getRemoteAddr());
            validate(entity);
            getDao().modify(id, entity);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void eliminar(Long id, HttpServletRequest httpRequest) throws AppException {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT c " +
                    "FROM Ceo c " +
                    "WHERE c.id = :id " +
                    "AND EXISTS (SELECT ce.pk.idCeo FROM CeoLegajo ce WHERE ce.pk.idCeo = :id)");
            System.err.println("Eliminar CEO : " + query.toString());
            Ceo ceoFk = getDao().verificarConstraint(id, query);
            if (ceoFk != null) {
                //ceoFk.setActivo(false);
                //modificar(id, ceoFk, httpRequest);
            	throw new AppException(500, Constantes.MENSAJE_ELIMINAR_REGISTRO);
            } else {
                eliminar(id);
            }
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    public void cambiarEstado(Long id, Ceo entity, HttpServletRequest httpRequest) throws AppException {
        try {
        	System.err.println("cambiarEstado Ceo");
            Usuario user = getCurrentUser();
            entity.setFechaModificacion(new Date());
            entity.setUsuarioModificacion(user.getId());
            entity.setIpModificacion(httpRequest.getRemoteAddr());
            validate(entity);
            getDao().cambiarEstado(id, entity);
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public Response asignarLegajos(Long id, Legajo legajo, HttpServletRequest httpRequest) throws AppException {
        
        try {
            getDao().asignarLegajos(id, legajo, httpRequest);
            return Response.ok().build();
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
    
    public ListaResponse<CeoLegajosView> getLegajos(Long id, Integer inicio, Integer cantidad, 
            String orderBy, String orderDir, HashMap<String, Object> filtros) {
        
        if (filtros != null && filtros.containsKey("vinculacionFuncionario")) {
            if (filtros.get("vinculacionFuncionario").equals("TODOS")) {
                filtros.remove("vinculacionFuncionario");
            }
        }
        if (orderBy.equals("id")) {
            orderBy = "orden";
        }
        
//        if(filtros == null) {
//            filtros = new HashMap<String, Object>();
//        }
//        
//        if ((!filtros.containsKey("anho") && !filtros.containsKey("mes"))) {
//            Integer[] anhoMes = anexosDAO.getAnhoMes();
//            filtros.put("anho", anhoMes[0]);
//            filtros.put("mes", anhoMes[1]);
//        }
        
        return getDao().getLegajos(inicio, cantidad, orderBy, orderDir, filtros, id);
                
    }
}

package py.com.ceamso.reportes.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import py.com.ceamso.reportes.dao.ProgramasDAO;

/**
 *
 * @author konecta
 */
@Stateless
public class ProgramasService {
    
    @Inject
    private ProgramasDAO dao;
    
    public List<String> getProgramas() {
        List<String> programas = dao.getProgramas();
        List<String> lista = new ArrayList<>();
        lista.add("TODOS");
        lista.addAll(programas);
        return lista;
    }
    
    public List<String> getSubProgramas() {
        List<String> subprogramas = dao.getSubProgramas();
        List<String> lista = new ArrayList<>();
        lista.add("TODOS");
        lista.addAll(subprogramas);
        return lista;
    }
    
}

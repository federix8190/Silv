package py.com.konecta.services;

import java.util.HashSet;
import java.util.Set;
import javax.ejb.Stateless;

@Stateless
public class UsuarioService {
	
	public Set<String> getPermisosUsuario(String userName) {
    	
		Set<String> res = new HashSet<>(); 
		res.add("CHATBOT");
		res.add("DEUDAS");
		return res;
	}
	
	public boolean esUsuarioValido(String user, String password) {
		
		// TODO password encriptado ----> cambiar123
		if (user != null && !user.isEmpty() && password != null && !password.isEmpty()) {
			if (user.equals("federix") ) {//&& password.equals("2c1674378b9515fdf71dbe7640c1922f")) {
				return true;
			}
		}
		return false;
	}

}

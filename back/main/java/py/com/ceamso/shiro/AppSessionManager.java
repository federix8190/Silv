package py.com.ceamso.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

public class AppSessionManager extends DefaultWebSessionManager {
	
	@Override
	protected void afterExpired(Session session) {
		super.afterExpired(session);
		//System.err.println("La Session del usuario ha expirada");
	}

}

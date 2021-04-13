package py.com.ceamso.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

public class AppSessionListener implements SessionListener {
	
	@Override
	public void onExpiration(Session session) {
		System.err.println("La Session del usuario ha expirada");
	}

	@Override
	public void onStart(Session arg0) {
		System.err.println("Starting Session ");
		
	}

	@Override
	public void onStop(Session arg0) {
		System.err.println("Stopping Session ");
		
	}

}

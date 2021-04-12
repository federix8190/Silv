package py.com.konecta;

public class AppException extends Exception {

    int status;
	String mensaje;
    String codigo;
    String estado;
    String mensajeLog;
    
    public AppException(String message) {
		super(message);
	}
    
    public AppException(int status, String msg) {
    	this.status = status;
    	this.mensaje = msg;
    }
    
    public static class IllegalArgument extends AppException {

		private static final long serialVersionUID = -8765864922699868014L;

		public IllegalArgument(String message) {
			super(message);
		}

	}

}

/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso usuarios.
 *
 * @name gfd.service#UsuarioService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('UsuarioService', ['$http', 'Upload', 'BaseService', function ($http, Upload, BaseService) {

    return angular.extend({}, BaseService, {
        
        recurso: "/usuarios/",

        /**
         * Se sobrescribe el método base para invocar a la url del recurso paginado.
         * Esto es debido a que se utiliza un json server para simular una api rest.
         * @function
         */
        /*listar: function () {
            return $http.get(App.REST_BASE + this.recurso);
        },*/

        /**
         * Servicio para obtener los datos del usuario logueado.
         * @function
         */
        getDatosUsuarioLogueado: function (data) {
            return $http.get(App.REST_BASE + this.recurso + "usuario-logueado").then(
                function (success) {
                	var response = {
                        status: success.status,
                        data: success.data
                    };
                    return response;
                },
                function (failResults) {
                    var response = {
                        status: failResults.status,
                        result: failResults.data,
                        data: null
                    };
                    return response;
                }
            );
        },
		
		uploadFile: function (params) {
            return Upload.upload({
                url: App.REST_BASE + this.recurso + 'subir-cv',
                data: params
            });
        },

        tieneCV: function (id) {
            return $http.get(App.REST_BASE + this.recurso + id + '/tiene-cv');
        },

        tieneOpcionCambioPassword: function () {
            return $http.get(App.REST_BASE + this.recurso + '/tiene-opcion-cambio-password');
        },

        /**
         * Resetea el password del usuario, y lo envia por correo electronico.
         * @function
         */
        resetPassword: function (cedula) {
            return $http.post(App.REST_BASE + this.recurso + cedula + '/resetear-password');
        }
		
    });
}]);

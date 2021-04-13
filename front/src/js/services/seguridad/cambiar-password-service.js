/**
 * @Class
 * Definición del service que se encarga del Cambio de password
 *
 * @name gfd.service#CambiarPasswordService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('CambiarPasswordService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        
        recurso: "/usuarios/cambiar-password",

        /**
         * Resetea el password del usuario, y lo envia por correo electronico.
         * @function
         */
        resetPassword: function (cedula) {
            return $http.post(App.REST_BASE + '/usuarios/' + cedula + '/resetear-password');
        }
    });
}]);

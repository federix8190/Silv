/*
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios
 * para realizar las operaciones relacionadas con la sessión del usuario.
 *
 * @name cm.service#SessionService
 * @author <a href="mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.service('SessionService', ['$http', function ($http) {
    return {
        /**
         * Realiza un get para obtener los datos de usuario logeado
         * @function
         */
        loadUserInfo: function () {
            return $http.get(App.REST_BASE + '/session/me/');
        }
    }
}]);

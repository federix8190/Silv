/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso roles.
 *
 */
app.service('RolService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        recurso: "/seguridad/roles/",
		/**
		 * Obtiene los permisos del rol.
		 */
		getPermisosRol: function (idRol) {
            return $http.get(App.REST_BASE + this.recurso + idRol + "/permisos");
        },
		
		listarRoles: function () {
            return $http.get(App.REST_BASE + this.recurso + 'todos');
        }
    });
}]);

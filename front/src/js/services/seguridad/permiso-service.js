/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso permisos.
 *
 */
app.service('PermisoService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        recurso: "/seguridad/permisos/"
    });
}]);

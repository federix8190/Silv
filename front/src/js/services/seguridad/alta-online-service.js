/**
 * @Class
 * Definición del service que se encarga del Registro online.
 *
 * @name gfd.service#AltaOnLineService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('AltaOnLineService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        recurso: "/usuarios/registro-online"
    });
}]);

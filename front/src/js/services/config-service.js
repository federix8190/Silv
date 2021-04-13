/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso config.
 *
 * @name gfd.service#ConfigService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('ConfigService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        recurso: "/config/",
		listar: function () {
            return $http.get(App.REST_BASE + this.recurso);
        }
    });
}]);

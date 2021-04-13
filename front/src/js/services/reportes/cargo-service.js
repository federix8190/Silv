/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso cargo.
 *
 * @name gfd.service#CargoService
 * @author <a href="mailto:marcelo.szcerba@konecta.com.py">Marcelo Szcerba</a>
 */
app.service('CargoService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        recurso: "/cargo/"	
    });
}]);
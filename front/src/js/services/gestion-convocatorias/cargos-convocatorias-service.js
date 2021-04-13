/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso cargos-disponibles.
 *
 * @name gfd.service#CargosConvocatoriaService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('CargosConvocatoriaService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        
		recurso: "/cargos-convocatorias/",
		
		/**
		 * Servicio para listar todos los cargos disponibles
		 */
		listarCargos: function (anho, mes) {
            return $http.get(App.REST_BASE + this.recurso + "?count=-1&anho=" 
            	+ anho + "&mes=" + mes + "&sortBy=nombre&sortOrder=ASC");
        }
    });
}]);
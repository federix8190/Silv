/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso cpt.
 *
 * @name gfd.service#CptService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('CptLegajosService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {

        recurso: "/administracion/cpt/",
		
		/**
		 * Obtiene los legajos
		 */		
        listar: function (params) {
            return $http.get(App.REST_BASE + this.recurso + localStorage.getItem('idCpt') + '/legajos', {
                params: params
            });
        },

		asignarLegajo: function (id, legajo) {
            return $http.post(App.REST_BASE + this.recurso + id + '/legajos', legajo);
        }

    });
}]);

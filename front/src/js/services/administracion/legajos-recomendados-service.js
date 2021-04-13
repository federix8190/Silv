/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso cpt.
 *
 * @name gfd.service#CptService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('LegajosRecomendadosService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {

        recurso: "/administracion/cpt/",

        listar: function (params) {
            return $http.get(App.REST_BASE + this.recurso + '1' + '/legajos-recomendados', {
                params: params
            });
        },

        asignarTramos: function (params) {
            return $http.post(App.REST_BASE + this.recurso + params.id + '/tramos', params.tramos);
        },
		
		listarLegajos: function (params) {
            return $http.get(App.REST_BASE + this.recurso + params.id + '/legajos-recomendados', {
                params: params
            });
        }

    });
}]);

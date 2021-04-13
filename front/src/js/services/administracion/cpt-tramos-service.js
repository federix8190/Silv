/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso cpt.
 *
 * @name gfd.service#CptService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('CptTramosService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {

        recurso: "/administracion/cpt/",

        listar: function (params) {
            return $http.get(App.REST_BASE + this.recurso + localStorage.getItem('nivelCpt') 
                    + '/tramos?anho=' + localStorage.getItem('anho') 
                    + "&mes=" + localStorage.getItem('mes'), {
                params: params
            });
        },
		
		listarTramos: function (params, anho, mes) {
            return $http.get(App.REST_BASE + this.recurso + params.id 
                    + '/tramos?anho=' + anho + "&mes=" + mes, {
                params: params
            });
        },

        asignarTramos: function (params) {
            return $http.post(App.REST_BASE + this.recurso + params.id + '/tramos', params.tramos);
        }

    });
}]);

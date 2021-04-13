/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso convocatorias.
 *
 * @name gfd.service#ConvocatoriaService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('ConvocatoriaPublicaService', ['$http', 'BaseService', function ($http, BaseService) {
    //return {
    return angular.extend({}, BaseService, {
        recurso: '/convocatorias-publica/',
		
		/**
         * Obtiene la lista de interesados.
         */
        getInteresados: function (id) {
            return $http.get(App.REST_BASE + this.recurso + id + "/interesados");
        },
        
        /**
         * Servicio para convocatorias
         * @function
         */
        meInteresa: function (data) {
            return $http.post(App.REST_BASE + this.recurso + "me-interesa", data).then(
                function (success) {
                    var response = {
                        status: success.status,
                        data: success.data
                    };
                    return response;
                },
                function (failResults) {
                    var response = {
                        status: failResults.status,
                        result: failResults.data,
                        data: null
                    };
                    return response;
                }
            );
        }
    })
}]);

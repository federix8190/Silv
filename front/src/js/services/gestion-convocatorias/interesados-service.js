/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso interesados.
 *
 * @name gfd.service#ConvocatoriaService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('InteresadosService', ['$http', '$location', 'BaseService', function ($http, $location, BaseService) {
    
	return angular.extend({}, BaseService, {
        //recurso: '/convocatorias/' + localStorage.getItem('idConvocatoria') + '/interesados'
        listar: function (params) {
            return $http.get(App.REST_BASE + '/convocatorias/' + localStorage.getItem('idConvocatoria') + '/interesados', {
                params: params
            });
        }
    })
}]);

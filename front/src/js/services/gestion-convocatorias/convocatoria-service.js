/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso convocatorias.
 *
 * @name gfd.service#ConvocatoriaService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('ConvocatoriaService', ['$http', 'BaseService', 'Upload', 
        function ($http, BaseService, Upload) {
    
    return angular.extend({}, BaseService, {
        recurso: '/convocatorias/',

        obtener: function (params) {
            return $http.get(App.REST_BASE + this.recurso + params, {}).then(
                function (success) {
                    var datos = success.data;
                    datos.inicioVigencia = new Date(success.data.inicioVigencia);
                    datos.finVigencia = new Date(success.data.finVigencia);
                    var response = {
                        status: success.status,
                        data: datos
                    };
                    return response;
                });
        },
		
		/**
         * Obtiene la lista de interesados.
         */
        getInteresados: function (id) {
            return $http.get(App.REST_BASE + this.recurso + id + "/interesados");
        },

        uploadFile: function (id, file) {
            var fd = new FormData();
            fd.append("file", file);
            return $http.post(App.REST_BASE + this.recurso + id + '/subir-pdf', fd, {
                headers: {'Content-Type': undefined}
            });
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

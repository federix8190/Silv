/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso ceo.
 *
 * @name gfd.service#CeoService
 * @author <a href="mailto:marcelo.szcerba@konecta.com.py">Marcelo Szcerba</a>
 */
app.service('CeoService', ['$http', 'BaseService', 'Upload', function ($http, BaseService, Upload) {

    return angular.extend({}, BaseService, {
        
		recurso: "/administracion/ceo/",
		
		listarTodos: function (anho, mes) {
            return $http.get(App.REST_BASE + this.recurso + 'vigentes?anho=' + anho + '&mes=' + mes);
        },
		
		/**
		 * Obtiene los legajos
		 */		
		listarLegajos: function (params) {
            return $http.get(App.REST_BASE + this.recurso + params.id + '/legajos', {
                params: params
            });
        },
		
		asignarLegajo: function (id, legajo) {
            return $http.post(App.REST_BASE + this.recurso + id + '/legajos', legajo);
        },

        cambiarEstado: function (id, params) {
            return $http.put(App.REST_BASE + this.recurso + params.id + '/cambiar-estado', params);
        },

        uploadFile: function (params) {
            return Upload.upload({
                url: App.REST_BASE + this.recurso + 'subir-cv',
                data: params
            });
        },

        getCSV: function (filtros) {
            var filters = {};
            filters.count = -1;
            filters.filters = filtros;

            if(filters.filters.programa == "TODOS")
                delete filters.filters.programa;
            if(filters.filters.subprograma == "TODOS")
                delete filters.filters.subprograma;

            return $http.get(App.REST_BASE + this.recurso + "data/csv",{
                params: filters
            });
        },
        getXLS: function (filtros) {
            var filters = {};
            filters.count = -1;
            filters.filters = filtros;

            if(filters.filters.programa == "TODOS")
                delete filters.filters.programa;
            if(filters.filters.subprograma == "TODOS")
                delete filters.filters.subprograma;

            
            return $http.get(App.REST_BASE + this.recurso + "data/xls",{
                params: filters
            });
        },
        getPDF: function (filtros) {
            var filters = {};
            filters.count = -1;
            filters.filters = filtros;
            
            return $http.get(App.REST_BASE + this.recurso + "data/pdf",{
                params: filters
            });
        }
    });
}]);

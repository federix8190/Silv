/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso cpt.
 *
 * @name gfd.service#CptService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('CptService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {

        recurso: "/administracion/cpt/",

        /**
         * Servicio para listar todos los cpt
         */
        listarTodos: function () {
            return $http.get(App.REST_BASE + this.recurso + '?count=-1');
        },
		
		getNiveles: function () {
            return $http.get(App.REST_BASE + this.recurso + 'niveles');
        },

        getConfiguracionTramos: function (anho, mes) {
            return $http.get(App.REST_BASE + this.recurso + 'configuracion-tramos?anho='+anho+'&mes='+mes);
        },
		
		listarTramos: function (id) {
            return $http.get(App.REST_BASE + this.recurso + id + '/tramos');
        },
		
		asignarTramos: function (params) {
            return $http.post(App.REST_BASE + this.recurso + params.id + '/tramos', params.tramos);
        },
		
		listarLegajosRecomendados: function (params) {
            return $http.get(App.REST_BASE + this.recurso + params.id + '/legajos-recomendados', {
                params: params
            });
        },
		
		listarLegajosAsignados: function (params) {
            return $http.get(App.REST_BASE + this.recurso + params.id + '/legajos-asignados', {
                params: params
            });
        },
        getLegajosAsignados: function (params) {
            return $http.get(App.REST_BASE + this.recurso + params.id + '/legajos-asignados', {
                params: params
            });
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

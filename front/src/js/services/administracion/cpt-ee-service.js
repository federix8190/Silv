/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso cpt-ee.
 *
 * @name gfd.service#CptService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('CptEEService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        
		recurso: "/administracion/cpt-ee/",
        
        obtenerNroCPT: function (params) {
            return $http.get(App.REST_BASE + this.recurso + params + '/obtenerNroCPT' );
        },

        listarTodos: function () {
            return $http.get(App.REST_BASE + this.recurso + '?count=-1');
        },
        listByCpt: function (idCpt) {
            return $http.get(App.REST_BASE + this.recurso + 
                '?count=-1&filters=%7B%22idCpt%22:%22' + idCpt + '%22%7D');
        },

		listarLegajos: function (params) {
            return $http.get(App.REST_BASE + this.recurso + params.id + '/legajos', {
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

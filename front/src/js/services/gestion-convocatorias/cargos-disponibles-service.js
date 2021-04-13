/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso cargos-disponibles.
 *
 * @name gfd.service#CargosDisponiblesService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('CargosDisponiblesService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        
		recurso: "/cargos-disponibles/",
		
		/**
		 * Servicio para listar todos los cargos disponibles
		 */
		listarCargos: function () {
            return $http.get(App.REST_BASE + this.recurso + "?count=-1&sortBy=cargo&sortOrder=ASC");
        },

        eliminarCargoVacante: function (params) {
            return $http.delete(App.REST_BASE + this.recurso + params + "/eliminar");
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
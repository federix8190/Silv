/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso cpt-ef.
 *
 * @name gfd.service#ClasificacionService
 * @author <a href="mailto:marcelo.szcerba@konecta.com.py">Pablo Araujo</a>
 */
app.service('ClasificacionService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        
        recurso: "/administracion/clasificacion/",
        
        obtenerNroCPT: function (params) {
            return $http.get(App.REST_BASE + this.recurso + params + '/obtenerNroCPT' );
        },
        
        // Para cuando se quiera traer todos los registros, ejemplo en un selector
		listarTodos: function () {
            return $http.get(App.REST_BASE + this.recurso + '?count=-1');
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

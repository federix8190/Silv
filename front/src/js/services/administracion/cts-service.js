/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso Cts.
 *
 * @name gfd.service#CtsService
 * @author <a href="mailto:marcelo.szcerba@konecta.com.py">Marcelo Szcerba</a>
 */
app.service('CtsService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        recurso: "/administracion/cts/",

        getTramos: function () {
            return $http.get(App.REST_BASE + this.recurso + 'tramos');
        },
        getActual: function () {
            return $http.get(App.REST_BASE + this.recurso + 'actual');
        },
        getCSV: function (filtros) {
            var filters = {};
            filters.count = -1;
            filters.filters = filtros;

            return $http.get(App.REST_BASE + this.recurso + "data/csv",{
                params: filters
            });
        },
        getXLS: function (filtros) {
            var filters = {};
            filters.count = -1;
            filters.filters = filtros;
            
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

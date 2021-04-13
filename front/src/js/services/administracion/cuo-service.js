
app.service('CuoService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        recurso: "/administracion/cuo/",

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

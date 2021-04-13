/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso candidatso.
 *
 * @name gfd.service#LegajoService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('CandidatosCargoService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        
        recurso: "/legajos/candidatos/",
        getCSV: function (filtros) {
            var filters = {};
            filters.count = -1;
            filters.filters = filtros;

            if(filters.filters.titularUnidad === "TODOS")
                delete filters.filters.titularUnidad;
            if(filters.filters.programa === "TODOS")
                delete filters.filters.programa;
            if(filters.filters.subprograma === "TODOS")
                delete filters.filters.subprograma;
            if(filters.filters.ambitoCptF === "TODOS")
                delete filters.filters.ambitoCptF;
            if(filters.filters.ambitoCptE === "TODOS")
                delete filters.filters.ambitoCptE;
            if(filters.filters.orientacionFuncional === "TODOS")
                delete filters.filters.orientacionFuncional;
            if(filters.filters.numeroTramo === "TODOS")
                delete filters.filters.numeroTramo;
            if(filters.filters.vinculacionFuncionario === "TODOS")
                delete filters.filters.vinculacionFuncionario;

            return $http.get(App.REST_BASE + this.recurso + "data/csv",{
                params: filters
            });
        },
        getXLS: function (filtros) {
            var filters = {};
            filters.count = -1;
            filters.filters = filtros;

            if(filters.filters.titularUnidad === "TODOS")
                delete filters.filters.titularUnidad;
            if(filters.filters.programa === "TODOS")
                delete filters.filters.programa;
            if(filters.filters.subprograma === "TODOS")
                delete filters.filters.subprograma;
            if(filters.filters.ambitoCptF === "TODOS")
                delete filters.filters.ambitoCptF;
            if(filters.filters.ambitoCptE === "TODOS")
                delete filters.filters.ambitoCptE;
            if(filters.filters.orientacionFuncional === "TODOS")
                delete filters.filters.orientacionFuncional;
            if(filters.filters.numeroTramo === "TODOS")
                delete filters.filters.numeroTramo;
            
            return $http.get(App.REST_BASE + this.recurso + "data/xls",{
                params: filters
            });
        },
        getPDF: function (filtros) {
            var filters = {};
            filters.count = -1;
            filters.filters = filtros;

            if(filters.filters.titularUnidad === "TODOS")
                delete filters.filters.titularUnidad;
            if(filters.filters.programa === "TODOS")
                delete filters.filters.programa;
            if(filters.filters.subprograma === "TODOS")
                delete filters.filters.subprograma;
            if(filters.filters.ambitoCptF === "TODOS")
                delete filters.filters.ambitoCptF;
            if(filters.filters.ambitoCptE === "TODOS")
                delete filters.filters.ambitoCptE;
            if(filters.filters.orientacionFuncional === "TODOS")
                delete filters.filters.orientacionFuncional;
            if(filters.filters.numeroTramo === "TODOS")
                delete filters.filters.numeroTramo;
            
            return $http.get(App.REST_BASE + this.recurso + "data/pdf",{
                params: filters
            });
        }

    });
}]);

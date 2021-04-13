/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para realizar las operaciones sobre el recurso anexo.
 *
 * @name gfd.service#AnexoService
 * @author <a href="mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.service('AnexoService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        
        recurso: "/anexos/",
		
		obtenerAsignaciones: function (cedula, anho, mes) {
            return $http.get(App.REST_BASE + this.recurso + cedula + '/' + anho 
                + '/' + mes + '/asignaciones');
        },

        obtenerPorCategoria: function (cedula, anho, mes) {
            return $http.get(App.REST_BASE + this.recurso + cedula + '/' + anho 
                + '/' + mes + '/cptee-categoria');
        },
		
		asignarLegajoCpt: function (params) {
            return $http.post(App.REST_BASE + this.recurso + 'asignar-legajos-cpt', params);
        },

        asignarLegajoCeo: function (params) {
            return $http.post(App.REST_BASE + this.recurso + 'asignar-legajos-ceo', params);
        },

        getEstadoReporte: function (id) {
            return $http.get(App.REST_BASE + this.recurso + 'comparativo/estado?idProceso=' + id);
        },

        getUltimoPeriodo: function (id) {
            return $http.get(App.REST_BASE + this.recurso + 'ultimo-periodo');
        },

        generarReporte: function (anhoInicio, mesInicio, anhoFinal, mesFinal) {
            return $http.get(App.REST_BASE + this.recurso 
                + 'comparativo?anhoInicio=' + anhoInicio
                + '&mesInicio=' + mesInicio
                + '&anhoFinal=' + anhoFinal
                + '&mesFinal=' + mesFinal);
        },

        /**
         * Se sobrescribe el método base para invocar a la url del recurso paginado.
         * Esto es debido a que se utiliza un json server para simular una api rest.
         * @function
         */
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
            
            return $http.get(App.REST_BASE + this.recurso + "data/pdf",{
                params: filters
            });
        }
    });
}]);

/**
 * Created by Home on 23/05/2018.
 */
app.service('GenerarReporteService', ['$http', function ($http) {

    return {
        recurso: "/reportes/",




        generarReporte: function (param) {
            var parametros ={};
             parametros.parametros =   param;

            return $http.get(App.REST_BASE + this.recurso + "reporteexcell",{params:parametros});


        },

        generarXLS: function (params) {
            return $http.get(App.REST_BASE + this.recurso + "promocion-salarial/", {params:params});
        }
    };
}]);

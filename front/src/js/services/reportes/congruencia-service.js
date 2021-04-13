/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para obtener los datos para los reportes de congruencia.
 *
 * @name #CongruenciaService
 * @author <a href = "mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.service('CongruenciaService', ['$http', function ($http) {

    return {
        recurso: "/reportes/congruencia/",

        /**
         * Se sobrescribe el método base para invocar a la url del recurso paginado.
         * Esto es debido a que se utiliza un json server para simular una api rest.
         * @function
         */
        getMatrizCongruencia: function (params) {
            return $http.get(App.REST_BASE + this.recurso + "matriz", {params:params});
        },

        /**
         * Trae el resumen de los datos mostrados en la matriz.
         * @function
         */
        getResumenMatriz: function (params) {
            return $http.get(App.REST_BASE + this.recurso + "resumen-matriz", {params:params});
        },

        /**
         * Se sobrescribe el método base para invocar a la url del recurso paginado.
         * Esto es debido a que se utiliza un json server para simular una api rest.
         * @function
         */
        getPuestoRemuneracion: function (params) {
            return $http.get(App.REST_BASE + this.recurso+"puesto-remuneracion", {params:params});
        }
    };
}]);

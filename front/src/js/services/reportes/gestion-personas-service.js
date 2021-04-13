/**
 * @Class
 * Definición del service que se encarga de la comunicación con la capa de servicios 
 * para obtener los datos para los reportes de congruencia.
 *
 * @name #CongruenciaService
 * @author <a href = "mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.service('GestionPersonasService', ['$http', function ($http) {

    return {
        recurso: "/reportes/gestion-personas/",

        /**
         * Se sobrescribe el método base para invocar a la url del recurso paginado.
         * Esto es debido a que se utiliza un json server para simular una api rest.
         * @function
         */
        getGestionPersonas: function (params) {
            return $http.get(App.REST_BASE + this.recurso, {params:params});
        },

        getDesarrolloPersonal: function (params) {
            return $http.get(App.REST_BASE + this.recurso + "desarrollo-personal/", {params:params});
        },

        getPromocionSalarial: function (params) {
            return $http.get(App.REST_BASE + this.recurso + "promocion-salarial/", {params:params});
        }
    };
}]);


app.service('AmbitoService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        recurso: "/administracion/ambito/",

        listarTodos: function () {
            return $http.get(App.REST_BASE + this.recurso + '?count=-1');
        },
        ObtenterAmbitos: function () {
            return $http.get(App.REST_BASE + this.recurso + 'ambitos');
        }
    });
}]);

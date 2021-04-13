
app.service('CategoriaService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        recurso: "/administracion/categoria/",

        listarTodos: function () {
            return $http.get(App.REST_BASE + this.recurso + '?count=-1');
        },
    });
}]);

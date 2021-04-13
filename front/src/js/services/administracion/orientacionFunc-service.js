
app.service('OrientacionFuncService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        recurso: "/administracion/orientacionfunc/",

        listarTodos: function () {
            return $http.get(App.REST_BASE + this.recurso + '?count=-1');
        },
    });
}]);

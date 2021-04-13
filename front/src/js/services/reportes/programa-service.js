
app.service('ProgramaService', ['$http', 'BaseService', function ($http, BaseService) {

    return angular.extend({}, BaseService, {
        recurso: "/programas/",
		
		listarSubProgramas: function (params) {
            return $http.get(App.REST_BASE + this.recurso + "subprogramas", {
                params: params
            });
        }
    });
}]);

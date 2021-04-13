app.service('LoginService', ['$http', function ($http) {
    return {
        recurso: '/session',
        /**
         * Servicio para inciar session
         * @function
         */
        iniciarSession: function (data) {
            return $http.post(App.REST_BASE + this.recurso, data);
        },
        /**
         * Servicio para cerrar session
         * @function
         */
        cerrarSession: function (data) {
            return $http.post(App.REST_BASE + this.recurso + '/cerrar', data).then(
                function (success) {
                    var response = {
                        status: success.status,
                        data: success.data
                    };
                    return response;
                },
                function (failResults) {
                    var response = {
                        status: failResults.status,
                        result: failResults.data,
                        data: null
                    };
                    return response;
                }
            );
        }
    }
}]);

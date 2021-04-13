/**
 * @class
 * Controller que implementa el formulario administración de usuarios
 *
 */
app.controller('UsuarioViewCtrl', ['$scope', 'UsuarioService', '$location', '$controller', 
        '$routeParams', '$rootScope',
    function ($scope, service, $location, $controller, $routeParams, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $scope.urlDescarga = App.REST_BASE + '/usuarios/' + $routeParams.id + '/descargar-cv';
        $scope.tieneCV = false;
        $rootScope.currentPage = 'AdministrarUsuarios';

        function tieneCV() {
            $scope.service.tieneCV($routeParams.id)
                .then(function (response) {
                    console.log("OK : ", response);
                    if (response) {
                        $scope.tieneCV = true;
                    }
                }, function (response) {
                    //Message.error(response.data.mensaje);
                    //$scope.tieneCV = true;
                });
        };

        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteViewCtrl', {
                "$scope": $scope
            }));

            tieneCV();
        })();
    }
]);

/**
 * @class
 * Controller que implementa el formulario administración de roles
 *
 */
app.controller('RolViewCtrl', ['$scope', 'RolService', '$controller','$routeParams', '$rootScope',
    function ($scope, service, $controller, $routeParams, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $rootScope.currentPage = 'AdministrarRol';

        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteViewCtrl', {
                "$scope": $scope
            }));

            service.getPermisosRol($routeParams.id).then(
                function(response) {
                    if (response.data) {
                        $scope.permisos = response.data;
                    } else {
                        console.error('Error listar permisos : ' + response.result);
                    }
                }
            );
        })();
    }
]);

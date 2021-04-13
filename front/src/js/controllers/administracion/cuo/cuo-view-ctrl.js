
app.controller('CuoViewCtrl', ['$scope', 'CuoService', '$controller', '$rootScope',
    function ($scope, service, $controller, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $rootScope.currentPage = 'AdministrarCuo';

        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteViewCtrl', {
                "$scope": $scope
            }));
        })();
    }
]);

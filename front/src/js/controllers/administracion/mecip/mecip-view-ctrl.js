
app.controller('MecipViewCtrl', ['$scope', 'MecipService', '$controller', '$rootScope',
function ($scope, service, $controller, $rootScope) {

    /**
     * Service utilizdo para recuperar los datos y realizar las operaciones.
     * @field
     * @type {Object}
     */
    $scope.service = service;
    $rootScope.currentPage = 'AdministrarMecip';

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

/**
 * @class
 * Controller que implementa el formulario administración de plantillas
 *
 * @name angular-keycloak-seed.controller##EmpresaViewCtrl
 * @author <a href = "mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.controller('CptEEViewCtrl', ['$scope', 'CptEEService', '$controller', '$rootScope',
    function ($scope, service, $controller, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $rootScope.currentPage = 'AdministrarCptE';

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

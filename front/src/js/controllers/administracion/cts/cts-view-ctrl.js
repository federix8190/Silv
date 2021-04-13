/**
 * @class
 * Controller que implementa el formulario administración de plantillas
 *
 * @name angular-keycloak-seed.controller##CtsViewCtrl
 * @author <a href = "mailto:marcelo.szcerba@konecta.com.py">Marcelo Szcerba</a>
 */
app.controller('CtsViewCtrl', ['$scope', 'CtsService', '$controller', '$rootScope',
    function ($scope, service, $controller, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $rootScope.currentPage = 'AdministrarCts';

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

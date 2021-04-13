/**
 * @class
 * Controller que implementa el formulario administración de anexos
 *
 * @name angular-keycloak-seed.controller##AnexoViewCtrl
 * @author <a href = "mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.controller('AnexoViewCtrl', ['$scope', 'AnexoService', '$controller',
    function ($scope, service, $controller) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

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

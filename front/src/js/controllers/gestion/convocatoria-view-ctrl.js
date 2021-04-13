/**
 * @class
 * Controller que implementa el formulario administración de Convocatorias
 *
 * @name angular-keycloak-seed.controller##ConvocatoriaViewCtrl
 * @author <a href = "mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.controller('ConvocatoriaViewCtrl', ['$scope', 'ConvocatoriaService', '$routeParams', '$location', '$controller',
    function ($scope, service, $routeParams, $location, $controller) {

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
            angular.extend(this, $controller('BaseCteListCtrl', {
                "$scope": $scope
            }));
            $scope.config.header = header;
            $scope.footer = false;
            $scope.path = "";
            var tokens = $location.$$path.split("/");
            for(var i=0; i<tokens.length-2; i++){
                $scope.path += tokens[i] + "/";
            }
            //se elimina la última /
            $scope.path = $scope.path.substr(0, $scope.path.length -1);
        })();
    }
]);

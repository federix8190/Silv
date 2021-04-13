/**
 * @class
 * Controller que implementa el formulario administración de plantillas
 *
 * @name angular-keycloak-seed.controller##EmpresaViewCtrl
 * @author <a href = "mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.controller('InteresadoViewCtrl', ['$scope', 'LegajoService', '$location', '$controller',
    function ($scope, service, $location, $controller) {

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

            $scope.pathActual = $scope.path;
            $scope.path = "";
            var tokens = $location.$$path.split("/");
            for(var i=0;i<tokens.length-4;i++){
                $scope.path += tokens[i] +"/";
            }
            //se elimina la última /
            $scope.path = $scope.path.substr(0, $scope.path.length -1);
        })();
    }
]);

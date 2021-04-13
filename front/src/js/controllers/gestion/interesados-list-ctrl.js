/**
 * @class
 * Controller que implementa la busqueda y listado de Interesados.
 *
 * @name angular-keycloak-seed.controller##InteresadosListCtrl
 * @author <a href = "mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.controller('InteresadosListCtrl', ['$scope', 'InteresadosService', '$routeParams', '$location', '$controller',
    function ($scope, service, $routeParams, $location, $controller) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        localStorage.setItem('idConvocatoria', $routeParams.id);
        $scope.service = service;

        $scope.verDatosInteresado = function(usuario) {
            if (usuario.interno) {
                $location.path('/reportes/legajos/' + usuario.cedula + '/ver');
            } else {
                console.log($scope.pathActual + '/' + usuario.id + '/datos');
                $location.path('/usuarios/' + usuario.id + '/ver');
            }
        };

        var header = [
            {
                "name": "CI",
                "key": "cedula"
            }, {
                "name": "Apellido",
                "key": "apellido"
            }, {
                "name": "Nombre",
                "key": "nombre"
            }, {
                "key": "",
                "name": "Acción"
            }
        ];

        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteListCtrl', {
                "$scope": $scope
            }));
            $scope.config.header = header;
            $scope.footer = false;

            $scope.pathActual = $scope.path;
            $scope.path = "";
            var tokens = $location.$$path.split("/");
            for(var i=0;i<tokens.length-2;i++){
                $scope.path += tokens[i] +"/";
            }
            //se elimina la última /
            $scope.path = $scope.path.substr(0, $scope.path.length -1);
        })();
}]);

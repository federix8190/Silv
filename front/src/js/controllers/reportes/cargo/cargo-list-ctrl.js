/**
 * @class
 * Controller que implementa la busqueda y listado de Cargo.
 *
 * @name angular-keycloak-seed.controller##CargoListCtrl
 * @author <a href = "mailto:marcelo.szcerba@konecta.com.py">Marcelo Szcerba</a>
 */
app.controller('CargoListCtrl', ['$scope', 'CargoService', '$controller',
    function ($scope, service, $controller) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

        /**
         * Configuraciones de la cabecera de la grilla.
         * @private
         * @type {Array}
         */
        var header = [
            {
                "name": "Descripción",
                "key": "descripcion"
            },
            /*{
                "name": "Línea",
                "key": "linea"
            },*/
            {
                "name": "Categoría",
                "key": "categoria"
            },
            {
                "name": "Salario Presupuestado",
                "key": "presupuestado"
            },
            {
                "name": "Funcion real",
                "key": "funcionReal"
            },
            {
                "name": "Estado",
                "key": "estado"
            },
            {
                "name": "Acción",
                "key": ""
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
            $scope.config.recurso = 'cargo';
            $scope.footer = false;
        })();
}]);

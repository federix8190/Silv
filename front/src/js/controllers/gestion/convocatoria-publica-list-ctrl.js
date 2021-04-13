/**
 * @class
 * Controller que implementa la busqueda y listado de Convocatorias.
 *
 * @name angular-keycloak-seed.controller##ConvocatoriaListCtrl
 * @author <a href = "mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.controller('ConvocatoriaPublicaListCtrl', ['$scope', 'ConvocatoriaPublicaService', '$controller',
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
            }, {
                "name": "Presupuestado",
                "key": "presupuestado"
            }, {
                "name": "Cargo",
                "key": "nombre"
            }, {
                "name": "Departamento",
                "key": "departamento"
            }, {
                "name": "Categoría",
                "key": "categoria"
            }, {
                "name": "Programa",
                "key": "descripcionPrograma"
            }, {
                "name": "Subprograma",
                "key": "descripcionSubprograma"
            }, {
                // "name": "Tramo",
                // "key": "tramo"
                // }, {
                "name": "Fecha de Creación",
                "key": "fechaCreacion"
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
            console.log('prueba')
            angular.extend(this, $controller('BaseCteListCtrl', {
                "$scope": $scope
            }));
            $scope.config.header = header;
            //$scope.config.recurso = 'convocatorias';
			$scope.footer = false;
        })();
}]);

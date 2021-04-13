/**
 * @class
 * Controller que implementa la busqueda y listado de Convocatorias.
 *
 * @name angular-keycloak-seed.controller##ConvocatoriaListCtrl
 * @author <a href = "mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.controller('ConvocatoriaListCtrl', ['$scope', 'ConvocatoriaService', '$controller',
    function ($scope, service, $controller) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $scope.scope = "#lista-convocatorias";

        /**
         * Configuraciones de la cabecera de la grilla.
         * @private
         * @type {Array}
         */
        var header = [
            {
                "name": "Código Concurso",
                "key": "codigoConcurso"
            }, {
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
            angular.extend(this, $controller('BaseCteListCtrl', {
                "$scope": $scope
            }));
            $scope.footer = true;
            $scope.config.header = header;
            $scope.config.recurso = 'convocatorias';

            $scope.filterBy = {};
            var now = new Date();
            var mesActual = now.getMonth();
            if (mesActual == 0) {
                $scope.filterBy.mes = "12";
                $scope.filterBy.anho = now.getFullYear() - 1;
            } else {
                $scope.filterBy.mes = (now.getMonth()) + "";
                $scope.filterBy.anho = now.getFullYear();
            }

            var filters = {
                anho : $scope.filterBy.anho,
                mes : $scope.filterBy.mes
            };
            $scope.initFilters(filters);

        })();
    }]);

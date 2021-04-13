/**
 * @class
 * Controller que implementa la busqueda y listado de Cargo vacantes.
 *
 * @name angular-keycloak-seed.controller##CargosVacantesListCtrl
 * @author <a href = "mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */

app.controller('CargosVacantesListCtrl2', ['$scope', 'CargosVacantesService','CptService','CtsService', '$controller',
    function ($scope, service, CptService, CtsService, $controller) {
        
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
        var header = [{
                "name": "Descripción",
                "key": "nombre"
            }, {
                "name": "Categoría",
                "key": "categoria"
            }, {
                "name": "Salario Presupuestado",
                "key": "presupuestado"
            }, {
                "name": "CPT EE",
                "key": "cptEe"
            }, {
                "name": "CPT EF",
                "key": "cptEf"
            }, {
                "name": "Acción",
                "key": ""
            }
        ];

        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            /*CptService.getNiveles().then(
				function(response) {
					if (response.data) {
						$scope.listaCpt = response.data;
						$scope.cpt = $scope.listaCpt[0];
					}
				}
            );
            CtsService.getTramos().then(
                function (response) {
                    if (response.data) {
                        $scope.listaTramos = response.data;
                        if ($scope.numeroTramo != undefined) {
                            if ($scope.listaTramos.indexOf('0') != -1) {
                                $scope.tramo = $scope.listaTramos[$scope.numeroTramo];
                            } else {
                                $scope.tramo = $scope.listaTramos[$scope.numeroTramo - 1];
                            }
                        } else {
                            $scope.tramo = $scope.listaTramos[$scope.listaTramos.length - 1];
                        }
                    } else {
                        console.error('Error listar cts : ' + response.result);
                    }
                }
            );*/
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteListCtrl', {
                "$scope": $scope
            }));
            $scope.config.header = header;
            $scope.config.recurso = 'cargo';
            $scope.footer = false;

            
        })();
}]);

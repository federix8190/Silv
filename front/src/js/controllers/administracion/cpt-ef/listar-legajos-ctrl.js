"use strict";

app.controller('ListarCptFLegajoCtrl', ['$scope', 'CptEFService', 'LegajoService', '$controller', '$routeParams', '$location',
    function ($scope, service, LegajoService, $controller, $routeParams, $location) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $scope.parametrosListado = {};

        var header = [
            {
                "key": "apellido",
                "name": "Apellido"
            }, {
                "key": "nombre",
                "name": "Nombre"
            }, {
                "key": "cedulaIdentidad",
                "name": "CI"
            }, {
                "key": "localidad",
                "name": "Localidad"
            }, {
                "key": "nivel",
                "name": "Nivel CPT"
            }, {
                "key": "vinculacionFuncionario",
                "name": "Vinculación"
            }, {
                "key": "cargo",
                "name": "Cargo"
            }, {
                "key": "funcionReal",
                "name": "Funcion real"
            }
        ];

        $scope.updateVinculacion = function(vinculacion) {
            var newFilter = $scope.parametrosListado.filters;
            newFilter.vinculacionFuncionario = vinculacion;
            $scope.parametrosListado.filters = newFilter;
            /*return $scope.service.listarLegajosAsignados($scope.parametrosListado)
                .then(function (response) {
                    $scope.loading = false;
                    $scope.config.rows = response.data.rows;
                    $scope.config.pagination.size = response.data.count;
                    $scope.config.pagination.pages = Math.ceil(response.data.count / $scope.config.pagination.count);
                    return $scope.config;
                }, function (response) {
                    $scope.loading = null;
                    $scope.config.rows = [];
                    $scope.config.pagination.size = 0;
                    $scope.config.pagination.pages = 0;
                    return $scope.config;
                });*/
        };

        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseListCtrl', {
                "$scope": $scope
            }));
            $scope.config.header = header;
            $scope.footer = false;
			
			$scope.getResource = function (params, paramsObj) {
				paramsObj.sortOrder = paramsObj.sortOrder == 'dsc' ? "DESC" : "ASC";
				$scope.loading = true;
				$scope.config.pagination.page = paramsObj.page;
				$scope.config.pagination.count = paramsObj.count;
				if (paramsObj.filters) {
					$scope.deleteUndefinedValues(paramsObj.filters);
				}
                paramsObj.id = $routeParams.id;
                $scope.parametrosListado = paramsObj;
				return $scope.service.listarLegajosAsignados(paramsObj)
					.then(function (response) {
						$scope.loading = false;
						$scope.config.rows = response.data.rows;
						$scope.config.pagination.size = response.data.count;
						$scope.config.pagination.pages = Math.ceil(response.data.count / $scope.config.pagination.count);
						return $scope.config;
					}, function (response) {
						$scope.loading = null;
						$scope.config.rows = [];
						$scope.config.pagination.size = 0;
						$scope.config.pagination.pages = 0;
						return $scope.config;
					});
            };
            
            LegajoService.getVinculaciones().then(
                function(response) {
                    if (response.data) {
                        $scope.listaVinculaciones = response.data;
                    }
                }
            );

        })();
    }
]);

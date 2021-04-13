"use strict";

app.controller('AsignarCptFLegajoCtrl', ['$scope', 'CptEFService', 'LegajoService', '$controller', '$routeParams', '$location',
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
                "key": "",
                "name": ""
            }, {
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
            console.log('vinculacion Seleccionada : ', vinculacion);
            var newFilter = $scope.parametrosListado.filters;
            newFilter.vinculacionFuncionario = vinculacion;
            $scope.parametrosListado.filters = newFilter;
            /*return $scope.service.listarLegajos($scope.parametrosListado)
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
		
		function guardarSuccess(response) {
            $scope.disabledButtonSave = false;
            Message.ok("Operación realizada exitosamente.");
			$location.path('/administracion/cpt-ef');
        };
		
		function guardarError(response) {
            $scope.disabledButtonSave = false;
			if (response != undefined && response.data != undefined && response.data.mensaje != undefined) {
				Message.error(response.data.mensaje);
			} else {
            	Message.error("No se pudo realizar la operación");
			}
        };
		
		$scope.guardar = function () {
			$scope.disabledButtonSave = true;
			var params = {};
			params.id = $routeParams.id;
			params.legajos = $scope.config.rows;
			service.asignarLegajos(params).then(guardarSuccess, guardarError);
        };
        
        $scope.asignarLegajo = function(row) {
            var legajo = {};
            legajo.cedulaIdentidad = row.cedulaIdentidad;
            legajo.asignado = row.asignado;
            service.asignarLegajo($routeParams.id, legajo).then(
                function(response) {
                    if (response.status != 200) {
                        console.error('Error al asignar legajo');
                    }
                }
            );
        };

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
				return $scope.service.listarLegajos(paramsObj)
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

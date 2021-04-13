"use strict";

app.controller('LegajosCtrl', ['$scope', 'LegajosRecomendadosService', 'CptService', '$controller', '$location',
    function ($scope, service, cptService, $controller, $location) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
		$scope.cptSeleccionado = 0;
		
		$scope.updateCpt = function(cpt) {
			console.log('Cpt Seleccionado : ', cpt);
			var paramsObj = {};
			paramsObj.sortOrder = paramsObj.sortOrder == 'dsc' ? "DESC" : "ASC";
			$scope.loading = true;
			$scope.config.pagination.page = paramsObj.page;
			$scope.config.pagination.count = paramsObj.count;
			if (paramsObj.filters) {
				$scope.deleteUndefinedValues(paramsObj.filters);
			}
			paramsObj.id = cpt;
			$scope.service.listarLegajos(paramsObj)
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
            },  {
                "key": "vinculacionFuncionario",
                "name": "Vinculación"
            }, {
                "key": "cargo",
                "name": "Cargo"
            }, {
                "key": "funcionReal",
                "name": "Funcion real"
            }, {
                "key": "",
                "name": "Acción"
            }
        ];
		
		function guardarSuccess(response) {
            $scope.disabledButtonSave = false;
            Message.ok("Operación realizada exitosamente.");
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
			params.id = $scope.cptSeleccionado;
			params.tramos = $scope.config.rows;
			service.asignarTramos(params).then(guardarSuccess, guardarError);
		};
		
		/**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
			cptService.getNiveles().then(
				function(response) {
					if (response.data) {
						$scope.listaCpt = response.data;
						$scope.cpt = $scope.listaCpt[0];
					}
				}
			);

            // se hereda del controller base
            angular.extend(this, $controller('BaseCteListCtrl', {
                "$scope": $scope
            }));
            $scope.config.header = header;
            $scope.footer = false;

        })();
    }
]);

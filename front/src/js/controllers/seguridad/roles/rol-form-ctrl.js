"use strict";

app.controller('RolFormCtrl', ['$scope', 'RolService', 'PermisoService', '$routeParams', 
		'$controller', '$location', '$rootScope',
    function ($scope, service, PermisoService, $routeParams, $controller, $location, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $rootScope.currentPage = 'ListarRol';
		$scope.uri = "/seguridad/roles/";

		$scope.seleccionarTodosCheck = false;
		
		$scope.seleccionarTodos = function() {
			$scope.seleccionarTodosCheck = !$scope.seleccionarTodosCheck;
			for (var i = 0; i < $scope.permisos.length; i++) {
				$scope.permisos[i].activo = $scope.seleccionarTodosCheck;
			}
		};

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteFormCtrl', {
                "$scope": $scope
            }));
			
			$scope.guardar = function () {
				var dto = {};
				dto.codigo = $scope.recurso.codigo;
				dto.descripcion = $scope.recurso.descripcion
				dto.permisos = $scope.permisos;
				if (this.isCrear()) {
					service.crear(dto).then($scope.guardarSuccess, $scope.guardarError);
				} else {
					dto.id = $scope.recurso.id;
					service.actualizar(dto).then($scope.guardarSuccess, $scope.guardarError);
				}
			};
			
			if ($scope.isCrear()) {
				PermisoService.listar().then(
					function(response) {
						if (response.data) {
							$scope.permisos = response.data;
						} else {
							console.error('Error listar permisos : ' + response.result);
						}
					}
				);
			} else {
				service.getPermisosRol($routeParams.id).then(
					function(response) {
						if (response.data) {
							$scope.permisos = response.data;
						} else {
							console.error('Error listar permisos : ' + response.result);
						}
					}
				);
			}
			
		})();

		$scope.guardarSuccess = function (data){
            $scope.disabledButtonSave = false;
            Message.ok("El rol se ha registrado exitosamente.");
            $location.url($scope.uri);
        };
		
		$scope.guardarError = function (data) {
            $scope.disabledButtonSave = false;
            Message.error(data.data.mensaje);
        };
    }
]);

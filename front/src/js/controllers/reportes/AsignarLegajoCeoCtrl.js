"use strict";

app.controller('AsignarLegajoCeoCtrl', ['$scope', 'CeoService', 'AnexoService', 
			'$controller', '$routeParams', '$location', '$rootScope',
	function ($scope, CeoService, AnexoService, $controller, $routeParams, $location, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
		$scope.c = {};
		$scope.ceo = {};
		$scope.idCeo = 0;			
		
		$scope.onChangeCeo = function(ceo) {
            $scope.idCeo = ceo.id;
        };

		function guardarSuccess(response) {
            $scope.disabledButtonSave = false;
            Message.ok("Operación realizada exitosamente.");
			//$location.path('/reportes/anexos/');
        };
		
		function guardarError(response) {
            $scope.disabledButtonSave = false;
			if (response != undefined && response.data != undefined && response.data.mensaje != undefined) {
				Message.error(response.data.mensaje);
			} else {
            	Message.error("No se pudo realizar la operación");
			}
        };
		
        $scope.initPopup = function() {
            var e = document.getElementById('parent');
            e.onmouseover = function() {
              document.getElementById('popup').style.display = 'block';
            };
            e.onmouseout = function() {
              document.getElementById('popup').style.display = 'none';
            };
        };
		
		$scope.listarCeo = function(idCeo) {
			var permisos = localStorage.getItem('permisos');
			if (permisos.indexOf('CLASIFICAR_CEO') == -1) {
				return;
			}
			CeoService.listarTodos($scope.anho, $scope.mes).then(
				function(response) {
					if (response.data) {
						$scope.listaCeo = response.data.rows;
						for (var i = 0; i < response.data.count; i++) {
							var ceo = $scope.listaCeo[i];
							if (ceo.id == idCeo) {
								$scope.ceo = ceo;
							}
						}
					}
				}
			);
		};		

		/*function listAll(){
			$scope.listarCpt($scope.idCpt);
			$scope.listarCptF($scope.idCptF);
			$scope.listarCeo($scope.idCeo);
		}*/

		$scope.tienePermiso = function (permiso) {
			var permisos = localStorage.getItem('permisos');
            if (permisos !== null && permisos.length > 0) {
                var listaPermisos = permisos.split(',');
                if (listaPermisos.indexOf(permiso) != -1) {
                    return true;
                }
            }
            return false;
        };

        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteFormCtrl', {
                "$scope": $scope
            }));

			var tokens = $routeParams.id.split('-');
			$scope.anho = tokens[0];
			$scope.mes = tokens[1];
			$scope.cedulaIdentidad = tokens[2];
			
			$scope.guardar = function () {
            	$scope.disabledButtonSave = true;
				var params = {};
				params.cedulaIdentidad = $scope.cedulaIdentidad;
				params.idCeo = $scope.idCeo;
				params.anho = $scope.recurso.anho;
				params.mes = $scope.recurso.mes;
				console.log("Datos : ", params.idCpt, ", ", params.idCeo);
				console.log("Anho mes : ", $scope.recurso.anho, ' - ', $scope.recurso.mes);
				AnexoService.asignarLegajoCeo(params).then(guardarSuccess, guardarError);
			};
			
			AnexoService.obtenerAsignaciones($scope.cedulaIdentidad, $scope.anho, $scope.mes).then(
				function(response) {
					if (response.data) {
						$scope.asignaciones = response.data;
						$scope.idCeo = $scope.asignaciones[3];
						$scope.listarCeo($scope.idCeo);
					}
				}
			);

        })();
    }
]);

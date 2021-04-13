"use strict";

app.controller('AsignarLegajoCtrl', ['$scope', 'CptService', 'CptEFService', 
		'CptEEService', 'AnexoService', '$controller', '$routeParams', '$location', '$rootScope',
	function ($scope, CptService, CptFService, CptEService, AnexoService,  
			$controller, $routeParams, $location, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
		$scope.c = {};
		$scope.c.cpt = {};
		$scope.cptF = {};
		$scope.cptE = {};
		
		$scope.idCpt = 0;
		$scope.idCptF = 0;
		$scope.idCptE = 0;
		
		$scope.onChangeCpt = function(cpt) {
			console.log('onChangeCpt : ', cpt.id, ' - ', cpt.denominacion);
			if (cpt != null && cpt != undefined) {
				$scope.idCpt = cpt.id;
				$scope.idCptF = 0;
				$scope.idCptE = 0;
				$scope.listarCptE(cpt.id);
				$scope.listarCptF(cpt.id);
			}
        };
		
		$scope.onChangeCptF = function(cptF) {
			if (cptF != null && cptF != undefined) {
				$scope.idCptF = cptF.id;
				/*$scope.idCpt = cptF.cpt.id;
				$scope.idCptE = 0;
				$scope.listarCptE(null);
				console.log('onChangeCptF : ', cptF.cpt.id);
				$scope.listarCpt(cptF.cpt.id);*/
			}
		};
		
		$scope.onChangeCptE = function(cptE) {
			if (cptE != null && cptE != undefined) {
				$scope.idCptE = cptE.id;
				/*$scope.idCpt = cptE.cpt.id;
				$scope.idCptF = 0;
				$scope.listarCptF(null);
				$scope.listarCpt(cptE.cpt.id);*/
			}
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
		
		/*$scope.asignarLegajo = function(row) {
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
        };*/

        $scope.initPopup = function() {
            var e = document.getElementById('parent');
            e.onmouseover = function() {
              document.getElementById('popup').style.display = 'block';
            };
            e.onmouseout = function() {
              document.getElementById('popup').style.display = 'none';
            };
        };
		
		$scope.listarCpt = function(idCpt) {
			var permisos = localStorage.getItem('permisos');
			if (permisos.indexOf('CLASIFICAR_CPT') == -1) {
				return;
			}
			CptService.listarTodos().then(
				function(response) {
					if (response.data) {
						$scope.listaCpt = response.data.rows;
						for (var i = 0; i < response.data.count; i++) {
							var cpt = $scope.listaCpt[i];
							if (cpt.id == idCpt) {
								$scope.c.cpt = cpt;
								$scope.listarCptE(idCpt);
								$scope.listarCptF(idCpt);
							}
						}
					}
				}
			);
		};
		
		$scope.listarCptF = function(idCpt) {
			var permisos = localStorage.getItem('permisos');
			if (permisos.indexOf('CLASIFICAR_CPT') == -1) {
				return;
			}
			CptFService.listByCpt(idCpt).then(
				function(response) {
					if (response.data) {
						$scope.listaCptF = response.data.rows;
						var idCptF = $scope.asignaciones[1];
						for (var i = 0; i < response.data.count; i++) {
							var cptF = $scope.listaCptF[i];
							if (cptF.id == idCptF) {
								$scope.cptF = cptF;
							}
						}
					}
				}
			);
		};

		$scope.listarCptE = function(idCpt) {
			var permisos = localStorage.getItem('permisos');
			if (permisos.indexOf('CLASIFICAR_CPT') == -1) {
				return;
			}
			CptEService.listByCpt(idCpt).then(
				function(response) {
					if (response.data) {
						$scope.listaCptE = response.data.rows;
						var idCptE = $scope.asignaciones[2];
						if (idCptE != 0) {
							for (var i = 0; i < response.data.count; i++) {
								var cptE = $scope.listaCptE[i];
								if (cptE.id == idCptE) {
									$scope.cptE = cptE;
								}
							}
						}
					}
				}
			);
		};

		$scope.obtenerPorCategoria = function(){

			AnexoService.obtenerPorCategoria($scope.cedulaIdentidad, $scope.anho, $scope.mes).then(
				function(response) {
					if (response.data) {
						var idCptE = response.data;
						var cptE = {};
						var idCpt = 0;
						CptEService.listarTodos().then(
							function(response) {
								if (response.data) {
									$scope.listaCptE = response.data.rows;
									for (var i = 0; i < response.data.count; i++) {
										cptE = $scope.listaCptE[i];
										if (cptE.id == idCptE) {
											$scope.cptE = cptE;
											idCpt = cptE.cpt.id;
										}
									}
									CptService.listarTodos().then(
										function(response) {
											if (response.data) {
												$scope.listaCpt = response.data.rows;
												for (var i = 0; i < response.data.count; i++) {
													var cpt = $scope.listaCpt[i];
													
													if (cpt.id == idCpt) {
														$scope.c.cpt = cpt;
													}
												}				
											}
										}
									);
								}
							}
						);
					} else {
						listAll();
					}
				}
			);
		};

		function listAll(){
			$scope.listarCpt($scope.idCpt);
			$scope.listarCptF($scope.idCptF);
			$scope.listarCeo($scope.idCeo);
		}

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
				params.idCpt = $scope.idCpt;
				params.idCptF = $scope.idCptF;
				params.idCptE = $scope.idCptE;
				params.anho = $scope.recurso.anho;
				params.mes = $scope.recurso.mes;
				console.log("Datos : ", params.idCpt, ", ", params.idCptF, ", ", params.idCptE);
				console.log("Anho mes : ", $scope.recurso.anho, ' - ', $scope.recurso.mes);
				AnexoService.asignarLegajoCpt(params).then(guardarSuccess, guardarError);
			};
			
			AnexoService.obtenerAsignaciones($scope.cedulaIdentidad, $scope.anho, $scope.mes).then(
				function(response) {
					if (response.data) {
						$scope.asignaciones = response.data;
						$scope.idCpt = $scope.asignaciones[0];
						$scope.idCptF = $scope.asignaciones[1];
						$scope.idCptE = $scope.asignaciones[2];
						$scope.listarCpt($scope.idCpt);
					} else {
						console.error('Error listar cpt : ' + response.result);
					}
				}
			);

        })();
    }
]);

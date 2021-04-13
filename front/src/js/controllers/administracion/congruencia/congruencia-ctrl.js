"use strict";

app.controller('CongruenciaCtrl', ['$scope', 'CptTramosService', 'CptService', '$controller', '$location',
    function ($scope, service, CptService, $controller, $location) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
		$scope.cptSeleccionado = 0;
        
        $scope.changeAnho = function(e) {
            localStorage.setItem('anho', $scope.filterBy.anho);
        };
        
		$scope.updateAnho = function(e) {
            if (e == undefined || e.keyCode == 13) {
                $scope.aplicarFiltros();
            }
		};

        $scope.updateCpt = function(cpt) {
            localStorage.setItem('nivelCpt', $scope.filterBy.cpt);
            $scope.aplicarFiltros();
        };

        $scope.updateMes = function(mes) {
            localStorage.setItem('mes', $scope.filterBy.mes);
            $scope.aplicarFiltros();
        };

        var header = [
			{
                "key": "",
                "name": ""
            }, {
                "name": "Número Tramo",
                "key": "numeroTramo"
            },            
            {
                "name": "Mínimo",
                "key": "minimo"
            },
            {
                "name": "Máximo",
                "key": "maximo"
            }            
        ];
		
		function guardarSuccess(response) {
            //$scope.disabledButtonSave = false;
            //Message.ok("Operación realizada exitosamente.");
        };
		
		function guardarError(response) {
            $scope.disabledButtonSave = false;
			if (response != undefined && response.data != undefined && response.data.mensaje != undefined) {
				Message.error(response.data.mensaje);
			} else {
            	Message.error("No se pudo realizar la operación");
			}
        };
		
		$scope.guardar = function (tramo, asignado) {
			$scope.disabledButtonSave = true;
			var params = {};
            params.id = $scope.cptSeleccionado;
            var row = {};
            row.numeroTramo = tramo;
            row.asignado = asignado;
            row.anho = $scope.filterBy.anho;
            row.mes = $scope.filterBy.mes;
			params.tramos = row;//$scope.config.rows;
			service.asignarTramos(params).then(guardarSuccess, guardarError);
        };
        
        $scope.sorterFunc = function(cpt){
            return parseInt(cpt);
        };

        function initFilters() {
            var now = new Date();
            $scope.filterBy = {
                cpt: 1,
                anho : now.getFullYear(),
                mes : (now.getMonth()) + ""
            }
            localStorage.setItem('anho', $scope.filterBy.anho);
            localStorage.setItem('mes', $scope.filterBy.mes);
        }
		
		/**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            // se hereda del controller base
            localStorage.setItem('nivelCpt', 1);
            angular.extend(this, $controller('BaseCteListCtrl', {
                "$scope": $scope
            }));
            $scope.config.header = header;
            $scope.footer = false;    
			
            initFilters(); 

			CptService.getNiveles().then(
                function(response) {
                    if (response.data) {
                        response.data.find(function(row, index){
                            if(row == 'TODOS'){
                                return response.data.splice(index, 1)
                            }
                        });
                        $scope.listaCpt = response.data;
                        $scope.cpt = $scope.listaCpt[0];
                        $scope.cptSeleccionado = $scope.cpt;
                    } else {
                        console.error('Error listar cpts : ' + response.result);
                    }
                }
            );            

        })();
        
    }
]);

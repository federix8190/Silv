"use strict";

app.controller('ConvocatoriaFormCtrl', ['$scope', 'ConvocatoriaService', 
                'CargosConvocatoriaService', '$controller', '$location',
                'CptEEService', 'CptEFService',
    function ($scope, service, CargoService, $controller, $location, CptEService, CptFService) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $scope.listaCargos = [];
        $scope.listaCptF = [];
        $scope.mostrarDatosCargo = false;
        $scope.uri = "/gestion/convocatorias/";
        $scope.cargo = {};
        $scope.PeriodoSeleccionado = false;
        
        $scope.buscar = function () {
            console.log('Buscar datos : ', $scope.recurso.anho, ' - ', $scope.recurso.mes);
            cargarCargos(0);
        };

        function secureList() {
            if (typeof $scope.getResource == 'undefined') {
                return;
            }
            $scope.parentFunction = $scope.getResource;
            $scope.getResource = function () {
                var resp = $scope.parentFunction();
                resp.then(function () {
                    console.log('Datos listos');
                });
                return resp;
            }
        }

        function cargarCargos(idCargoActual) {
            CargoService.listarCargos($scope.recurso.anho, $scope.recurso.mes).then(
                function(response) {
                    if (response.data) {
                        $scope.limpiarDatosCargo();
                        $scope.listaCargos = response.data.rows;
                        $scope.PeriodoSeleccionado = true;
						if (!$scope.isCrear()) {
                            //var idCargoActual = $scope.recurso.idCargo;
                            console.log('Cargo actual : ', idCargoActual);
                            for (var i = 0; i < $scope.listaCargos.length; i++) {
                                if ($scope.listaCargos[i].id === idCargoActual) {  
                                    $scope.mostrarDatosCargo = true;
                                    $scope.cargo = $scope.listaCargos[i];
                                }
                            }
                        }
                    } else {
                        console.error('Error listar cargos : ' + response.result);
                    }
                }
            );
        }

        /**
         * Se encarga de injectar el file dentro del model cuando se sube un archivo.
         * @function
         */
        $scope.uploadFiles = function (file, errFiles) {
            $scope.archivo = file;
            var reader = new FileReader();
            reader.onload = $scope.fileIsLoaded;
            reader.readAsDataURL(file);
        };

        function numberWithCommas(x) {
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }

        $scope.fileIsLoaded = function (e) {
            console.log($scope.archivo);
            $scope.size = '(' + numberWithCommas(Math.ceil($scope.archivo.size / 1024)) + ' K)';
            $scope.fileName = $scope.archivo.name;
            $scope.archivoCargado = true;
            $scope.$apply(function () {});
        };

        $scope.$watch('recurso', function (newVal, oldVal) {
            if (newVal.id) {
                console.log('Editar cargo ', $scope.recurso.idCargo);
                cargarCargos($scope.recurso.idCargo);
                var idCptF = $scope.recurso.idCptF;
                CptFService.listarTodos().then(
                    function(response) {
                        if (response.data) {
                            $scope.listaCptF = response.data.rows;
                            for (var i = 0; i < $scope.listaCptF.length; i++) {
                                if ($scope.listaCptF[i].id === idCptF) {
                                    $scope.cptF = $scope.listaCptF[i];
                                }
                            }
                        }
                    }
                );
            }
        });

        $scope.updateCptF = function(cptF) {
            var sinDatos = "Sin datos";
            $scope.recurso.denCptF = cptF.den;
            $scope.recurso.nivel = cptF.nivelCpt;
            $scope.recurso.idCptF = cptF.id;
            //$scope.mostrarDatosCargo = false;            

        };

        $scope.updateCargo = function(cargo) {
            console.log('Cargo seleccionado : ', cargo);
            if (cargo == undefined || cargo == null) {
                return;
            }
            
            $scope.mostrarDatosCargo = true;
            var sinDatos = "Sin datos";
            $scope.recurso.nombre = cargo.nombre == null ? sinDatos : cargo.nombre;
            //$scope.recurso.fuenteFinanciamiento = cargo.fuenteFinanciamiento == null ? sinDatos :  cargo.fuenteFinanciamiento;
            //$scope.recurso.estructura = cargo.estructura == null ? sinDatos :  cargo.estructura;
            $scope.recurso.departamento = cargo.departamento == null ? sinDatos :  cargo.departamento;
            $scope.recurso.linea = cargo.linea == null ? sinDatos :  cargo.linea;
            $scope.recurso.programa = cargo.programa == null ? sinDatos :  cargo.programa;
            //$scope.recurso.objetoGasto = cargo.objetoGasto == null ? sinDatos :  cargo.objetoGasto;
            //$scope.recurso.direccion = cargo.direccion == null ? sinDatos :  cargo.direccion;
            $scope.recurso.categoria = cargo.categoria == null ? sinDatos :  cargo.categoria;
            //$scope.recurso.concepto = cargo.concepto == null ? sinDatos :  cargo.concepto;
            $scope.recurso.presupuestado = cargo.presupuestado == null ? sinDatos :  cargo.presupuestado;
            $scope.cargo = cargo;
            $scope.recurso.idCargo = cargo.id;
		};

        $scope.limpiarDatosCargo = function() {
            var sinDatos = "Sin datos";
            $scope.recurso.nombre = null;
            //$scope.recurso.fuenteFinanciamiento = cargo.fuenteFinanciamiento == null ? sinDatos :  cargo.fuenteFinanciamiento;
            //$scope.recurso.estructura = cargo.estructura == null ? sinDatos :  cargo.estructura;
            $scope.recurso.departamento = null;
            $scope.recurso.linea = null;
            $scope.recurso.programa = null;
            //$scope.recurso.objetoGasto = cargo.objetoGasto == null ? sinDatos :  cargo.objetoGasto;
            //$scope.recurso.direccion = cargo.direccion == null ? sinDatos :  cargo.direccion;
            $scope.recurso.categoria = null;
            //$scope.recurso.concepto = cargo.concepto == null ? sinDatos :  cargo.concepto;
            $scope.recurso.presupuestado = null;
            $scope.cargo = {};
            $scope.recurso.idCargo = 0;
        };

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteFormCtrl', {
                "$scope": $scope
            }));
			
			if ($scope.isCrear()) {
				//cargarCargos();
                var now = new Date();
                var mesActual = now.getMonth();
                if (mesActual == 0) {
                    $scope.recurso.mes = "12";
                    $scope.recurso.anho = now.getFullYear() - 1;
                } else {
                    $scope.recurso.mes = (now.getMonth()) + "";
                    $scope.recurso.anho = now.getFullYear();
                }
                CptFService.listarTodos().then(
                    function(response) {
                        if (response.data) {
                            $scope.listaCptF = response.data.rows;
                        }
                    }
                );
			}            
            
        })();

        /*$scope.guardar = function () {
            $scope.disabledButtonSave = true;
            console.log('Guardando convocatoria ...');
        };*/

        $scope.guardarSuccess = function (data) {
            if ($scope.archivo == undefined) {
                if (data.status == 200) {
                    Message.ok("Los datos de la convocatoria se han registrado exitosamente.");
                    $location.url($scope.uri);
                } else {
                    Message.error("No se pudo realizar la operación");
                }
                return;
            }
            var id = data.data.id;
            service.uploadFile(id, $scope.archivo).then(
                function (success) {
                    if (data.status == 200) {
                        Message.ok("Los datos de la convocatoria se han registrado exitosamente.");
                        $location.url($scope.uri);
                    } else {
                        Message.error("No se pudo realizar la operación");
                    }
                },
                function (failResults) {
                    Message.error("No se pudo realizar la operación");
                }
            );
        };
    }
]);

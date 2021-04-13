"use strict";

app.controller('CptEFFormCtrl', ['$scope', 'CptEFService', 'CptService', 'MecipService', 
        'AmbitoService', 'OrientacionFuncService', '$controller', '$location', '$rootScope',
    function ($scope, service, CptService, MecipService, AmbitoService, 
        OrientacionFuncService, $controller, $location, $rootScope) {

        /**
         * Service utilizado para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

        $scope.uri = "/administracion/cpt-ef/";
        $rootScope.currentPage = 'AdministrarCptF';

        $scope.obtenerNumeroCPTEE = function(cpt) {
            console.log("cpt.id: ", cpt.id);
            $scope.service.obtenerNroCPT(cpt.id).then(
                function(response) {
                    if (response.data) {
                        $scope.recurso.nro = cpt.nivel + "." + cpt.subNivel + "." + cpt.numeroGasto
                        + "." + response.data;
                    } else {
                        console.error('Error listar cargos : ' + response.result);
                    }
                }
            );
        };

        $scope.updateMecip = function (codigoMecip) {
            console.log('codigoMecip anterior : ', $scope.recurso.codProceso);
            console.log('codigoMecip Seleccionado : ', codigoMecip);
            $scope.recurso.codProceso = codigoMecip;
        };

        $scope.$watch('recurso', function (newVal, oldVal) {
            if (newVal.id) {
                CptService.listarTodos().then(
                    function (response) {
                        if (response.data) {
                            $scope.listaCpt = response.data.rows;
                            if (!$scope.isCrear()) {
                                var idCptActual = $scope.recurso.cpt.id;
                                for (var i = 0; i < $scope.listaCpt.length; i++) {
                                    if ($scope.listaCpt[i].id === idCptActual) {
                                        $scope.recurso.cpt = $scope.listaCpt[i];
                                    }
                                }
                            }
                        } else {
                            console.error('Error listar cargos : ' + response.result);
                        }
                    }
                );

                MecipService.listarTodos().then(
                    function (response) {
                        if (response.data) {
                            $scope.listaMecip = response.data.rows;
                            var auxMecip = [];
                            if (!$scope.isCrear()) {
                                for (var i = 0; i < $scope.recurso.mecip.length; i++) {
                                    for(var j = 0; j < $scope.listaMecip.length; j++){
                                        if ($scope.listaMecip[j].id === $scope.recurso.mecip[i].id) {
                                            auxMecip.push($scope.listaMecip[j]);
                                        }
                                    }
                                }
                                $scope.recurso.mecip = auxMecip;
                            }
                        } else {
                            console.error('Error listar : ' + response.result);
                        }
                    }
                );

                AmbitoService.listarTodos().then(
                    function(response) {
                        if (response.data) {
                            $scope.listaAmbito = response.data.rows;
                            if (!$scope.isCrear()) {
                                var idAmbitoActual = $scope.recurso.ambito.id;
                                for (var i = 0; i < $scope.listaAmbito.length; i++) {
                                    if ($scope.listaAmbito[i].id === idAmbitoActual) {
                                        $scope.recurso.ambito = $scope.listaAmbito[i];
                                    }
                                }
                            }
                        } else {
                            console.error('Error listar ambitos : ' + response.result);
                        }
                    }
                );


                OrientacionFuncService.listarTodos().then(
                    function(response) {
                        if (response.data) {
                            $scope.listaOrientacionFunc = response.data.rows;
                            if (!$scope.isCrear()) {
                                var idOrientacionFuncActual = $scope.recurso.orientacionFunc.id;
                                for (var i = 0; i < $scope.listaOrientacionFunc.length; i++) {
                                    if ($scope.listaOrientacionFunc[i].id === idOrientacionFuncActual) {
                                        $scope.recurso.orientacionFunc = $scope.listaOrientacionFunc[i];
                                    }
                                }
                            }
                        } else {
                            console.error('Error listar Orientaciones funcionales : ' + response.result);
                        }
                    }
                );
            }
        });

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteFormCtrl', {
                "$scope": $scope
            }));

            if ($scope.isCrear()) {
                CptService.listarTodos().then(
                    function (response) {
                        if (response.data) {
                            $scope.listaCpt = response.data.rows;
                        } else {
                            console.error('Error listar cargos : ' + response.result);
                        }
                    }
                );

                MecipService.listarTodos().then(
                    function (response) {
                        if (response.data) {
                            $scope.listaMecip = response.data.rows;
                        } else {
                            console.error('Error listar : ' + response.result);
                        }
                    }
                );

                AmbitoService.listarTodos().then(
                    function(response) {
                        if (response.data) {
                            $scope.listaAmbito = response.data.rows;
                        } else {
                            console.error('Error listar ambitos : ' + response.result);
                        }
                    }
                );


                OrientacionFuncService.listarTodos().then(
                    function(response) {
                        if (response.data) {
                            $scope.listaOrientacionFunc = response.data.rows;
                        } else {
                            console.error('Error listar Orientaciones funcionales : ' + response.result);
                        }
                    }
                );
            }

        })();

        $scope.guardarSuccess = function (data){
            $scope.disabledButtonSave = false;
            Message.ok("Los datos del CPT EF se han registrado exitosamente.");
            $location.url($scope.uri);
        };
    }
]);

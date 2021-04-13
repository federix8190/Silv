"use strict";

app.controller('CptCargoFormCtrl2',
    ['$scope', 'CargosDisponiblesService', 'CptEEService', 'CptEFService', 'CptService',
        'CategoriaService', '$controller', 'CptCargosService','$routeParams',
        function ($scope, service, CptEService, CptFService, CptService,
                  CategoriaService, $controller, CptCargosService, $routeParams) {

            /**
             * Service utilizdo para recuperar los datos y realizar las operaciones.
             * @field
             * @type {Object}
             */
            $scope.service = service;

            $scope.$watch('recurso', function (newVal, oldVal) {
                if ($scope.recurso.id !== undefined) {
                    console.log('Datos cargados : ', $scope.recurso.idCptEf);
                    CptService.listarTodos().then(
                        function (response) {
                            if (response.data) {
                                $scope.listaCpt = response.data.rows;
                                cargarCptF();
                                cargarCptE();
                            } else {
                                console.error('Error listar cargos : ' + response.result);
                            }
                        }
                    );
                }
            });

            function cargarCptF() {
                CptFService.listarTodos().then(
                    function (response) {
                        if (response.data) {
                            $scope.listaCptF = response.data.rows;
                            if (!$scope.isCrear()) {
                                var idCptEf = $scope.recurso.idCptEf;
                                var cptActual = null;
                                for (var i = 0; i < $scope.listaCptF.length; i++) {
                                    if ($scope.listaCptF[i].id === idCptEf) {
                                        $scope.recurso.cptF = $scope.listaCptF[i];
                                        cptActual = $scope.recurso.cptF.cpt.id;
                                    }
                                }
                                if (cptActual != null) {
                                    for (var i = 0; i < $scope.listaCpt.length; i++) {
                                        if ($scope.listaCpt[i].id === cptActual) {
                                            $scope.recurso.cpt = $scope.listaCpt[i];
                                        }
                                    }    
                                }
                            }
                        }
                    }
                );
            }

            function cargarCptE() {
                CptEService.listarTodos().then(
                    function (response) {
                        if (response.data) {
                            $scope.listaCptE = response.data.rows;
                            if (!$scope.isCrear()) {
                                var idCptEe = $scope.recurso.idCptEe;
                                var cptActual = null;
                                for (var i = 0; i < $scope.listaCptE.length; i++) {
                                    if ($scope.listaCptE[i].id === idCptEe) {
                                        $scope.recurso.cptE = $scope.listaCptE[i];
                                        cptActual = $scope.recurso.cptE.cpt.id;
                                    }
                                }
                                if (cptActual != null) {
                                    for (var i = 0; i < $scope.listaCpt.length; i++) {
                                        if ($scope.listaCpt[i].id === cptActual) {
                                            $scope.recurso.cpt = $scope.listaCpt[i];
                                        }
                                    }    
                                }
                            }
                        }
                    }
                );
            }

            $scope.$watch('recurso.cpt', function (newVal, oldVal) {
                console.log('watch :', oldVal, ' - ', newVal);
                if (oldVal == undefined) {
                    return;
                }
                if (newVal) {
                    CptEService.listar({filters: {nivelCpt: newVal.nivel}}).then(
                        function (response) {
                            if (response.data) {
                                $scope.listaCptE = response.data.rows;
                                // if (!$scope.isCrear()) {
                                //     var idCptActual = $scope.recurso.cpt.id;
                                //     for (var i = 0; i < $scope.listaCpt.length; i++) {
                                //         if ($scope.listaCpt[i].id === idCptActual) {
                                //             $scope.recurso.cpt = $scope.listaCpt[i];
                                //         }
                                //     }
                                // }
                            } else {
                                console.error('Error listar cargos : ' + response.result);
                            }
                        }
                    );
                    CptFService.listar({filters: {nivelCpt: newVal.nivel}}).then(
                        function (response) {
                            if (response.data) {
                                $scope.listaCptF = response.data.rows;
                                // if (!$scope.isCrear()) {
                                //     var idCptActual = $scope.recurso.cpt.id;
                                //     for (var i = 0; i < $scope.listaCpt.length; i++) {
                                //         if ($scope.listaCpt[i].id === idCptActual) {
                                //             $scope.recurso.cpt = $scope.listaCpt[i];
                                //         }
                                //     }
                                // }
                            } else {
                                console.error('Error listar cargos : ' + response.result);
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

            })();


            /**
             * Se encarga de persistir los datos del modelo.
             */
            $scope.guardar = function () {
                $scope.disabledButtonSave = true;
                var bodyData = {
                    idCargoDisponible: $routeParams.id,
                    idCptEf: $scope.recurso.cptF ? $scope.recurso.cptF.id : "",
                    idCptEe: $scope.recurso.cptE ? $scope.recurso.cptE.id : ""
                };
                CptCargosService.crear(bodyData)
                    .then(this.guardarSuccess, this.crearRecursoError);
            };
        }
    ]);

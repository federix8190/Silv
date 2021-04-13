"use strict";

app.controller('CptCargoFormCtrl',
    ['$scope', 'CargosDisponiblesService', 'CptEEService', 'CptEFService', 'CptService',
        '$controller', 'CptCargosService','$routeParams',
        function ($scope, service, CptEService, CptFService, CptService,
                  $controller, CptCargosService, $routeParams) {

            /**
             * Service utilizdo para recuperar los datos y realizar las operaciones.
             * @field
             * @type {Object}
             */
            $scope.service = service;
            $scope.uri = "/cargos/cargos-disponibles/";

            $scope.$watch('recurso', function (newVal, oldVal) {
                console.log('Cargar Datos :', newVal);

                CptService.listarTodos().then(
                    function (response) {
                        if (response.data) {
                            $scope.listaCpt = response.data.rows;
                            if (!$scope.isCrear()) {
                                if ($scope.recurso.idCpt != undefined 
                                        && $scope.recurso.idCpt != null) {
                                    var cptActual = $scope.recurso.idCpt;
                                    console.log('CPT Actual : ', cptActual);
                                    for (var i = 0; i < $scope.listaCpt.length; i++) {
                                        if ($scope.listaCpt[i].id === cptActual) {
                                            $scope.recurso.cpt = $scope.listaCpt[i];
                                        }
                                    }
                                }
                            }
                        } else {
                            console.error('Error listar cargos : ' + response.result);
                        }
                    }
                );

            });

            $scope.$watch('recurso.cpt', function (newVal, oldVal) {

                if (newVal) {
                    console.log('Cargar CPT :', newVal.id);
                    var idCpt = newVal.id;
                    console.log('Cargar CPT EE y CPT EF : ', newVal);                   

                    CptEService.listByCpt(idCpt).then(
                        function(response) {
                            if (response.data) {
                                $scope.listaCptE = response.data.rows;
                                var idCptE = $scope.recurso.idCptEe;
                                if (idCptE != null && idCptE != undefined) {
                                    for (var i = 0; i < response.data.count; i++) {
                                        var cptE = $scope.listaCptE[i];
                                        if (cptE.id == idCptE) {
                                            $scope.recurso.cptE = cptE;
                                        }
                                    }
                                }
                            }
                        }
                    );

                    CptFService.listByCpt(idCpt).then(
                        function(response) {
                            if (response.data) {
                                $scope.listaCptF = response.data.rows;
                                var idCptF = $scope.recurso.idCptEf;
                                if (idCptF != null && idCptF != undefined) {
                                    for (var i = 0; i < response.data.count; i++) {
                                        var cptF = $scope.listaCptF[i];
                                        if (cptF.id == idCptF) {
                                            $scope.recurso.cptF = cptF;
                                        }
                                    }
                                }
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

            function guardarSuccess(response) {
                Message.ok("Operación realizada exitosamente.");
            };

            /**
             * Se encarga de persistir los datos del modelo.
             */
            $scope.guardar = function () {
                $scope.disabledButtonSave = true;
                var bodyData = {
                    idCargoDisponible: $routeParams.id,
                    idCpt: $scope.recurso.cpt ? $scope.recurso.cpt.id : "",
                    idCptEf: $scope.recurso.cptF ? $scope.recurso.cptF.id : "",
                    idCptEe: $scope.recurso.cptE ? $scope.recurso.cptE.id : ""
                };
                CptCargosService.crear(bodyData).then(this.guardarSuccess, this.crearRecursoError);
            };
        }
    ]);

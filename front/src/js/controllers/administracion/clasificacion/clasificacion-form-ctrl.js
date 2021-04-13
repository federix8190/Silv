"use strict";

app.controller('ClasificacionFormCtrl', ['$scope', 'ClasificacionService', 'CptService', 
        '$controller', '$location',
    function ($scope, service, CptService, 
        $controller, $location) {

        /**
         * Service utilizado para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

        $scope.uri = "/administracion/clasificacion/";

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
            }

        })();

        $scope.guardarSuccess = function (data){
            $scope.disabledButtonSave = false;
            Message.ok("Los datos de Clasificacion se han registrado exitosamente.");
            $location.url($scope.uri);
        };
    }
]);

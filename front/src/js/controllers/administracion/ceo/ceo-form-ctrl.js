"use strict";

app.controller('CeoFormCtrl', ['$scope', 'CeoService', 'CuoService', '$controller', 
        '$location', '$rootScope', 'OrientacionFuncService',
    function ($scope, service, CuoService, $controller, $location, $rootScope, OrientacionFuncService) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

        $scope.uri = "/administracion/ceo/";
        $rootScope.currentPage = 'AdministrarCeo';
        $scope.activo = true;
        $scope.pathCeo = $location.path();
        
        $scope.$watch('recurso', function (newVal, oldVal) {
            if (!$scope.isCrear()) {
                $scope.inicioVigencia = new Date($scope.recurso.inicioVigencia);
                $scope.finVigencia = new Date($scope.recurso.finVigencia);
            }
            if (newVal.id) {
                // Datos del CUO
                CuoService.listarTodos().then(
                    function(response) {
                        if (response.data) {
                            $scope.listaCuo = response.data.rows;
                            if (!$scope.isCrear()) {
                                var idCuoActual = $scope.recurso.cuo.id;
                                for (var i = 0; i < $scope.listaCuo.length; i++) {
                                    if ($scope.listaCuo[i].id === idCuoActual) {
                                        $scope.recurso.cuo = $scope.listaCuo[i];
                                    }
                                }
                            }
                        } else {
                            console.error('Error listar configuraciones : ' + response.result);
                        }
                    }
                );

                // Orientacion Funcional
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
            angular.extend(this, $controller('BaseCeoFormCtrl', {
                "$scope": $scope
            }));

            if ($scope.isCrear()) {
                // Listar CUO
                CuoService.listarTodos().then(
                    function(response) {
                        if (response.data) {
                            $scope.listaCuo = response.data.rows;
                        } else {
                            console.error('Error listar configuraciones : ' + response.result);
                        }
                    }
                );

                // Listar Orientacion Funcional
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
            Message.ok("Los datos del CEO se han registrado exitosamente.");
            $location.url($scope.uri);
        };

        $scope.inicioVigenciaChange = function (fecha) {
            $scope.recurso.inicioVigencia = fecha;
        };

        $scope.finVigenciaChange = function (fecha) {
            $scope.recurso.finVigencia = fecha;
        };
    }
]);

"use strict";

app.controller('CptEEFormCtrl', ['$scope', 'CptEEService', 'CptService', 'CategoriaService', 
    'AmbitoService', '$controller', '$location', '$rootScope',
    function ($scope, service, CptService, CategoriaService, AmbitoService, $controller, 
            $location, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

        $scope.uri = "/administracion/cpt-ee/";
        $rootScope.currentPage = 'AdministrarCptE';
        
        $scope.onChangeAmbito = function(ambito) {
            console.log('ambito : ', ambito);
        };   

        $scope.obtenerNumeroCPTEE = function(cpt) {
            console.log("cpt.id: ", cpt.id);
            $scope.service.obtenerNroCPT(cpt.id).then(
                function(response) {
                    if (response.data) {
                        $scope.recurso.numero = cpt.nivel + "." + cpt.subNivel + "." + cpt.numeroGasto
                        + "." + response.data;
                        console.log("cantidad ", $scope.cantidad);                        
                    } else {
                        console.error('Error listar cargos : ' + response.result);
                    }
                }
            );
        };

        $scope.$watch('recurso', function (newVal, oldVal) {
            if (newVal.id) {
                CptService.listarTodos().then(
                    function(response) {
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
                CategoriaService.listarTodos().then(
                    function(response) {
                        if (response.data) {
                            $scope.listaCategoria = response.data.rows;
                            var auxCategoria = [];
                            if (!$scope.isCrear()) {
                                for (var i = 0; i < $scope.recurso.categoria.length; i++) {
                                    for(var j = 0; j < $scope.listaCategoria.length; j++){
                                        if ($scope.listaCategoria[j].nombre === $scope.recurso.categoria[i].nombre) {
                                            auxCategoria.push($scope.listaCategoria[j]);
                                        }
                                    }
                                }
                                $scope.recurso.categoria = auxCategoria;
                            }
                        } else {
                            console.error('Error listar categorias : ' + response.result);
                        }
                    }
                );

                AmbitoService.listarTodos().then(
                    function(response) {
                        if (response.data) {
                            console.log('ambito : ', $scope.recurso.ambito.nombre);
                            $scope.listaAmbito = response.data.rows;
                            var ambitoActual = $scope.recurso.ambito.nombre
                            for (var i = 0; i < $scope.listaAmbito.length; i++) {
                                if ($scope.listaAmbito[i].nombre == ambitoActual) {
                                    $scope.recurso.ambito = $scope.listaAmbito[i];
                                }
                            }
                        } else {
                            console.error('Error listar ambitos : ' + response.result);
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
                    function(response) {
                        if (response.data) {
                            $scope.listaCpt = response.data.rows;
                        } else {
                            console.error('Error listar cargos : ' + response.result);
                        }
                    }
                );
                CategoriaService.listarTodos().then(
                    function(response) {
                        if (response.data) {
                            $scope.listaCategoria = response.data.rows;
                        } else {
                            console.error('Error listar categorias : ' + response.result);
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
            }            

        })();

        $scope.guardarSuccess = function (data){
            $scope.disabledButtonSave = false;
            Message.ok("Los datos del CPT EE se han registrado exitosamente.");
            $location.url($scope.uri);
        };
    }
]);

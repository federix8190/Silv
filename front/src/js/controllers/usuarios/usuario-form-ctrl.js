"use strict";

app.controller('UsuarioFormCtrl', ['$scope', 'UsuarioService', 'RolService', '$routeParams', 
        '$controller', '$location', '$rootScope',
    function ($scope, service, RolService, $routeParams, $controller, $location, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

        $scope.uri = "/usuarios/";
        $rootScope.currentPage = 'AdministrarUsuarios';

        function cargarRoles() {
            RolService.listarRoles().then(
                function(response) {
                    if (response.data) {
                        $scope.listaRoles = response.data;
                        var i;
                        for(i=0; i<$scope.listaRoles.length; i++){
                            delete $scope.listaRoles[i].vaules;
                            delete $scope.listaRoles[i].fields;
                        }
                        console.log('Roles listo');
                        if (!$scope.isCrear()) {
                            var idRolActual = $scope.recurso.rol.id;
                            console.log('Rol actual : ', idRolActual);
                            for (var i = 0; i < $scope.listaRoles.length; i++) {
                                if ($scope.listaRoles[i].id === idRolActual) {
                                    console.log('Rol encontrado');
                                    $scope.recurso.rol = $scope.listaRoles[i];
                                }
                            }
                        }
                    } else {
                        console.error('Error listar roles : ' + response.result);
                    }
                }
            );
        }

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteFormCtrl', {
                "$scope": $scope
            }));
            /*$scope.guardar = function () {
                console.log('Guardando datos : ', $scope.recurso.rol);
            }*/
            if ($scope.isCrear()) {
                cargarRoles();
            } else {
                setTimeout(cargarRoles, 1000);
            }
        })();

        $scope.guardarError = function (data) {
            $scope.disabledButtonSave = false;
            Message.error(data.data.mensaje);
        };

        $scope.guardarSuccess = function (data){
            $scope.disabledButtonSave = false;
            Message.ok("El usuario se ha registrado exitosamente.");
            $location.url($scope.uri);
        };

            $scope.guardar = function () {
                if (this.isCrear()) {
                    this.crearRecurso();
                } else {
                    delete $scope.recurso.rol.vaules;
                    delete $scope.recurso.rol.fields;
                    delete $scope.recurso.vaules;
                    delete $scope.recurso.fields;
                    this.editarRecurso();
                }
            };

            $scope.crearRecurso = function () {
                $scope.disabledButtonSave = true;
                this.service.crear($scope.recurso)
                    .then(this.guardarSuccess, this.guardarError);
            };

    
            $scope.editarRecurso = function () {
                console.log("holi", $scope.recurso);
                $scope.disabledButtonSave = true;
                return this.service.actualizar($scope.recurso)
                    .then(this.guardarSuccess, this.guardarError);
            };
    }
]);

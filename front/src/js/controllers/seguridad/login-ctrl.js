"use strict";

app.controller('LoginCtrl', ['$scope', 'LoginService', 'ConfigService', '$location', '$rootScope', 
    function ($scope, service, configService, $location, $rootScope) {

        $scope.usuario = {};

        $scope.registrarse = function () {
            $location.path('/seguridad/registro-online');
        };

        $scope.tieneRegistro = function() {
            if (localStorage.getItem('registro_online') == 'S') {
                return true;
            } else {
                return false;
            }
        };

        $scope.login = function () {
            service.iniciarSession($scope.usuario)
                .then(function (response) {
                    console.log(response)
                    if (response.data) {
                        localStorage.setItem('userId', response.data.userId);
                        localStorage.setItem('user', response.data.usuario);
                        localStorage.setItem('permisos', response.data.permisos);
                        var expira = new Date();
                        expira.setHours(expira.getHours() + 1);
                        localStorage.setItem('expira', expira);
                        $location.path('/dashboard');
                    } else {
                        console.error('Error : ' + response.result);
                    }
                }).catch(function (data) {
                    //Message.error("Error al iniciar sesión");
                });

        };
    }
]);

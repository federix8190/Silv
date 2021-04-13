"use strict";

app.controller('ResetearContrasenaCtrl', ['$scope', 'CambiarPasswordService', '$controller', 
    function ($scope, service, $controller) {

        
        $scope.service = service;
        $scope.uri = "/seguridad/resetear-contrasena/";
        $scope.cedula = null;

        function guardarError(response) {
            $scope.disabledButtonSave = false;
            console.log("response",response);
			if (response != undefined && response.data != undefined && response.data.mensaje != undefined) {
				Message.error(response.data.mensaje);
			} else {
            	Message.error("No se pudo realizar la operación");
			}
        };

        function guardarSuccess(response) {
            $scope.disabledButtonSave = false;
            console.log(response.data.exitoso);
            if (response.data.exitoso) {
                Message.ok(response.data.mensaje);
            } else {
                Message.error(response.data.mensaje);
            }
            //$location.path('/');
        };

        $scope.enviar = function(cedula) {
            if (cedula == undefined) {
                Message.error("Debe ingresar su Número de cedula");
            } else {
                $scope.disabledButtonSave = true;
                $scope.service.resetPassword(cedula).then(guardarSuccess, guardarError);
            }
        };

        /*(function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseFormCtrl', {
                "$scope": $scope
            }));
            $scope.enviar = function() {
                $scope.disabledButtonSave = true;
            	$scope.service.resetPassword($scope.cedula).then(guardarSuccess, guardarError);
			};
        })();*/
    }
]);

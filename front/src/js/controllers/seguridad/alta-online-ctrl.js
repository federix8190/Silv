"use strict";

app.controller('AltaOnLineCtrl', ['$scope', 'AltaOnLineService', 'ConfigService', '$controller', 
    function ($scope, service, configService, $controller) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

        $scope.uri = "/seguridad/registro-online/";

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
            Message.ok("El usuario ha sido registrado exitosamente.");
            $location.path('/');
        };

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseFormCtrl', {
                "$scope": $scope
            }));
            $scope.guardar = function() {
                $scope.disabledButtonSave = true;
            	$scope.service.crear($scope.recurso).then(guardarSuccess, guardarError);
			};
        })();
    }
]);

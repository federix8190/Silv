"use strict";

app.controller('CambiarPasswordCtrl', ['$scope', 'CambiarPasswordService', '$controller', 'AppServices', '$location',
    function ($scope, service, $controller, AppServices, $location) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
		
		function guardarSuccess(response) {
            $scope.disabledButtonSave = false;
            Message.ok("La contraseña se ha modificado exitosamente.");
            $location.path('/perfil-usuario');
        };
		
		function guardarError(response) {
            $scope.disabledButtonSave = false;
			if (response != undefined && response.data != undefined && response.data.mensaje != undefined) {
				Message.error(response.data.mensaje);
			} else {
            	Message.error("No se pudo realizar la operación");
			}
        };

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteFormCtrl', {
                "$scope": $scope
            }));
			$scope.guardar = function() {
                $scope.disabledButtonSave = true;
            	$scope.service.crear($scope.recurso).then(guardarSuccess, guardarError);
			};
        })();
    }
]);

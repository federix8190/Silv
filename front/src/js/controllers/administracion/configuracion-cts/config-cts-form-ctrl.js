"use strict";

app.controller('ConfigCtsFormCtrl', ['$scope', 'ConfigCtsService', '$controller', 
        '$location', '$rootScope',
    function ($scope, service, $controller, $location, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $rootScope.currentPage = 'AdministrarConfigCts';
        $scope.uri = "/administracion/configuracion-cts/";

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteFormCtrl', {
                "$scope": $scope
            }));
        })();

        /**
         * Se encarga de actualizar los datos del recurso.
         */
        $scope.editarRecurso = function () {
            $scope.disabledButtonSave = true;
            return this.service.actualizar($scope.recurso)
                .then(this.guardarSuccess, this.editarRecursoError);
        };

        $scope.guardarSuccess = function (data){
            $scope.disabledButtonSave = false;
            Message.ok("Los datos de la Configuración del CTS se han registrado exitosamente.");
            $location.url($scope.uri);
        };
    }
]);

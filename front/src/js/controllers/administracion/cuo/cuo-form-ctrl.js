"use strict";

app.controller('CuoFormCtrl', ['$scope', 'CuoService', '$controller', '$location', '$rootScope',
    function ($scope, service, $controller, $location, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $rootScope.currentPage = 'AdministrarCuo';
        $scope.uri = "/administracion/cuo/";

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteFormCtrl', {
                "$scope": $scope
            }));
        })();

        $scope.guardarSuccess = function (data){
            $scope.disabledButtonSave = false;
            Message.ok("Los datos del CUO se han registrado exitosamente.");
            $location.url($scope.uri);
        };

    }
]);

"use strict";

app.controller('MecipFormCtrl', ['$scope', 'MecipService', '$controller', '$location', '$rootScope',
    function ($scope, service, $controller, $location, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

        $scope.uri = "/administracion/mecip/";
        $rootScope.currentPage = 'AdministrarMecip';

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteFormCtrl', {
                "$scope": $scope
            }));
        })();

        $scope.guardarSuccess = function (data){
            $scope.disabledButtonSave = false;
            Message.ok("Los datos del MECIP han sido registrados exitosamente.");
            $location.url($scope.uri);
        };
    }
]);

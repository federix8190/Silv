"use strict";

app.controller('CtsFormCtrl', ['$scope', 'CtsService', '$controller', '$location', '$rootScope',
    function ($scope, service, $controller, $location, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $scope.actual = {};

        $scope.uri = "/administracion/cts/";
        $rootScope.currentPage = 'AdministrarCts';

        $scope.meses = [
            'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio',
            'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
        ];

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteFormCtrl', {
                "$scope": $scope
            }));

            service.getActual().then(
                function (response) {
                    console.log(response);
                    $scope.recurso = {};//response.data;
                    $scope.actual = response.data;
                    console.log($scope.actual.anho, ' - ', $scope.actual.mes);
                    var now = new Date();
                    $scope.recurso.mes = (now.getMonth()) + "";
                    $scope.recurso.anho = now.getFullYear();
                    $scope.recurso.minimo = $scope.actual.minimo;
                    $scope.recurso.maximo = $scope.actual.maximo;

                    console.log($scope.recurso.anho, ' - ', $scope.recurso.mes);
                },
                function (response) {
                    Message.error("Ocurrió un error al recuperar el cts actual.");
                }
            );
        })();

        $scope.guardarSuccess = function (data){
            $scope.disabledButtonSave = false;
            Message.ok("Los datos del CTS se han registrado exitosamente.");
            $location.url($scope.uri);
        };
    }
]);

"use strict";

app.controller('CptFormCtrl', ['$scope', 'CptService', '$controller', 'AppServices', 
        '$location', '$rootScope',
    function ($scope, service, $controller, AppServices, $location, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $scope.inicioVigencia = new Date();
        $scope.finVigencia = new Date();
        $scope.activo = true;

        $scope.uri = "/administracion/cpt/";

        $scope.$watch('recurso', function (newVal, oldVal) {
            if (!$scope.isCrear()) {
                $scope.inicioVigencia = new Date($scope.recurso.inicioVigencia);
                $scope.finVigencia = new Date($scope.recurso.finVigencia);
            }
        });

        /*function mayus(e) {
            return e.toUpperCase();
        }

        $scope.onChangeInputText = function(e) {
            var id = e.currentTarget.id;
            var texto = $('#' + id)[0].value;
            $('#' + id)[0].value = mayus(texto);
        };*/

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCptFormCtrl', {
                "$scope": $scope
            }));

        })();

        $scope.guardarSuccess = function (data){
            $scope.disabledButtonSave = false;
            Message.ok("Los datos del CPT se han registrado exitosamente.");
            $location.url($scope.uri);
        };

        $scope.inicioVigenciaChange = function (fecha) {
            $scope.recurso.inicioVigencia = fecha;
        };

        $scope.finVigenciaChange = function (fecha) {
            $scope.recurso.finVigencia = fecha;
        };
    }
]);

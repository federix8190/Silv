"use strict";

app.controller('CargoFormCtrl', ['$scope', 'CargoService', '$controller',
    function ($scope, service, $controller) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

        $scope.uri = "/administracion/cargo/";

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteFormCtrl', {
                "$scope": $scope
            }));
        })();
    }
]);

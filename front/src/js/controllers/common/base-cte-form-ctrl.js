app.controller('BaseCteFormCtrl', ['$scope', '$controller', '$location',
    function ($scope, $controller, $location) {

        $scope.hasRole = function (roleName) {
            return true;
        };

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseFormCtrl', {
                "$scope": $scope
            }));

            var permisos = localStorage.getItem('permisos');
            var listaPermisos = [];
            if (permisos) {
                listaPermisos = permisos.split(',');
            }
            var sjs = new SecurityJS({
                scope: this.scope,
                data: listaPermisos
            });

            $scope.guardarSuccess = function (response) {
                $scope.disabledButtonSave = false;
                Message.ok("Los datos se han guardado exitosamente.");
                $location.url($scope.uri);
            };

            $scope.crearRecursoError = function (data) {
                $scope.disabledButtonSave = false;
                var mensaje = data.data.mensaje;
                if (mensaje != undefined && mensaje != null) {
                    Message.error(mensaje);
                } else {
                    Message.error("No se pudo guardar los datos");
                }
            };

            $scope.editarRecursoError = function (data) {
                $scope.disabledButtonSave = false;
                var mensaje = data.data.mensaje;
                if (mensaje != undefined && mensaje != null) {
                    Message.error(mensaje);
                } else {
                    Message.error("No se pudo guardar los datos");
                }
            };

        })();
    }
]);
app.controller('BaseCteViewCtrl', ['$scope', '$controller', '$location',
    function ($scope, $controller, $location) {

        $scope.hasRole = function (roleName) {
            return true;
        };

        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseViewCtrl', {
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

        })();
    }
]);
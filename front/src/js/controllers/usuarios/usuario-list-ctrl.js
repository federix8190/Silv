/**
 * @class
 * Controller que implementa la busqueda y listado de Usuarios.
 *
 */
app.controller('UsuarioListCtrl', ['$scope', 'UsuarioService', '$controller', '$route', '$rootScope',
    function ($scope, service, $controller, $route, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

        /**
         * Configuraciones de la cabecera de la grilla.
         * @private
         * @type {Array}
         */
        var header = [
            {
                "name": "Nombre",
                "key": "nombre"
            }, {
                "name": "Apellido",
                "key": "apellido"
            }, {
                "name": "Número de cedula",
                "key": "cedula"
            }, {
                "name": "Correo electrónico",
                "key": "email"
            }, {
                "name": "Rol",
                "key": "rol"
            }, {
                "key": "",
                "name": "Acción"
            }
        ];

        $rootScope.$on('$routeChangeStart', function (e, next, current) {
            $rootScope.nombre = $scope.filterBy.nombre;
            $rootScope.apellido = $scope.filterBy.apellido;
            $rootScope.cedula = $scope.filterBy.cedula;
            $rootScope.nombreRol = $scope.filterBy.nombreRol;
        });

        function initFilters() {
            if ($rootScope.cargarFiltro) {
                $scope.filterBy.nombre = $rootScope.nombre;
                $scope.filterBy.apellido = $rootScope.apellido;
                $scope.filterBy.cedula = $rootScope.cedula;
                $scope.filterBy.nombreRol = $rootScope.nombreRol;
                $scope.buscar();
            } else {
                $scope.filtros = {
                    //activo: true,
                    //tituloUnidad: "TODOS"
                };
                $scope.initFilters($scope.filtros);
            }
        }

        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteListCtrl', {
                "$scope": $scope
            }));
            $scope.config.header = header;
            initFilters();
        })();

        /**
         * Override de la función limpiar base
         * Se encarga de limpiar los criterios del filtrado.
         * @function
         */
        $scope.limpiar = function () {
            // $scope.filterBy = {
            //     activo : true
            // };
            $scope.initFilters($scope.filterBy);
        };

        $scope.resetearClave = function (recurso) {
            if (window.confirm("¿Está seguro que desea resetear la contraseña el usuario?")) {
                $scope.service.resetPassword(recurso.cedula).then(guardarSuccess, guardarError);
            }
        };

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
            console.log(response.data.exitoso);
            if (response.data.exitoso) {
                Message.ok(response.data.mensaje);
            } else {
                Message.error(response.data.mensaje);
            }
            //$location.path('/');
        };

        /**
         * Se encarga de cambiar el estado del usuario
         * @function
         */
        $scope.cambiarEstado = function (recurso) {
            var operacion = 'Activar';
            $scope.mensajeExitoso = 'El usuario se ha activado exitosamente';
            if (recurso.activo) {
                operacion = 'Desactivar';
                $scope.mensajeExitoso = 'El usuario se ha desactivado exitosamente';
            }
            if (window.confirm("¿Está seguro de " + operacion + " el usuario?"))
                this.service.eliminar(this.getPrimaryKey(recurso))
                    .then(cambiarEstadoSuccess, cambiarEstadoError);
        };

        function cambiarEstadoSuccess(response) {
            Message.ok($scope.mensajeExitoso);
            $route.reload();
        }

        function cambiarEstadoError(data) {
            Message.error("No se pudo realizar la operación");
        }
    }]);

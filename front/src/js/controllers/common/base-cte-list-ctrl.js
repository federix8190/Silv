app.controller('BaseCteListCtrl', ['$scope', '$controller', '$location', '$route',
    function ($scope, $controller, $location, $route) {

        function verificarPermiso(lista, permiso) {
            if (lista.indexOf(permiso) == -1) {
                $scope.footer = false;
            }
        }

        /**
         * Se encarga de verificar si tiene el permiso para crear.
         */
        function verificarPermisoAgregar() {
            var permisos = localStorage.getItem('permisos');
            var listaPermisos = [];
            if (permisos) {
                listaPermisos = permisos.split(',');
            }
            if ($scope.path == '/administracion/cpt') {
                verificarPermiso(listaPermisos, 'CREAR_CPT');
            } else if ($scope.path == '/administracion/cpt-ee') {
                verificarPermiso(listaPermisos, 'CREAR_CPT_EE');
            } else if ($scope.path == '/administracion/cpt-ef') {
                verificarPermiso(listaPermisos, 'CREAR_CPT_EF');
            } else if ($scope.path == '/administracion/ceo') {
                verificarPermiso(listaPermisos, 'CREAR_CEO');
            } else if ($scope.path == '/administracion/cuo') {
                verificarPermiso(listaPermisos, 'CREAR_CUO');
            } else if ($scope.path == '/administracion/mecip') {
                verificarPermiso(listaPermisos, 'CREAR_MECIP');
            } else if ($scope.path == '/gestion/convocatorias') {
                verificarPermiso(listaPermisos, 'CREAR_CONVOCATORIAS');
            }
        }

        /**
         * Se encarga de inicializar el control de acceso y visibilidad.
         */
        function securize() {
            //console.log('Ejecutar securize');
            var permisos = localStorage.getItem('permisos');
            var listaPermisos = [];
            if (permisos) {
                listaPermisos = permisos.split(',');
            }
            var sjs = new SecurityJS({
                data: listaPermisos
            });
        }

        function secureList() {}

        $scope.hasRole = function (roleName) {
            return true;
        };

        function eliminarRecursoSuccess(response) {
            Message.ok("El registro se ha eliminado exitosamente.");
            //$location.url($scope.path);
            $route.reload();
        }

        function eliminarRecursoError(data) {
            console.log('eliminarRecursoError : ', data);
            var mensaje = data.data.mensaje;
            if (mensaje != undefined && mensaje != null) {
                Message.error(mensaje);
            } else {
                Message.error("No se pudo realizar la operación");
            }
        };

        /**
         * Constructor de la clase.
         * @constructor
         */
        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseListCtrl', {
                "$scope": $scope
            }));

            $scope.parentFunction = $scope.getResource;
            //secureList();
            verificarPermisoAgregar();
            $scope.filtros = {};
        })();


        /**
         * Se sobrescribe la función get resoruce del padre.
         * @param {*} params 
         * @param {*} paramsObj 
         */
        $scope.getResource = function (params, paramsObj) {
            //console.log("getResoruce");
            securize();
            console.log('llamando a getResource');
            return $scope.parentFunction(params, paramsObj);
            resp.then(function () {
                //$scope.loading = true;
                console.log('Recibiendo respuesta del servicio : ');
                setTimeout(securize, 1000);
                //securize();
            });
            return resp;
        };

        /**
         * Se encarga de aplicar los criterios de filtrado cuando se dispara un
         * evento.
         * @param {*} e 
         */
        $scope.aplicarFiltros = function (e) {
            if (e == undefined || e.keyCode == 13) {
                $scope.buscar();
            }
        };

        /**
         * Se encarga de eliminar un registro de la lista.
         * @param {*} recurso 
         */
        $scope.eliminar = function (recurso) {
            $('.remove-row').css('color', '#333538');
            if (window.confirm("¿Está seguro de eliminar el recurso?"))
                this.service.eliminar(this.getPrimaryKey(recurso))
                .then(eliminarRecursoSuccess, eliminarRecursoError);
        };

        /**
         * Elimina los elementos del objeto que son nulos
         * @function
         */
        $scope.deleteUndefinedValues = function (object) {
            for (var key in object) {
                if (!object[key] ||
                    object[key] == "TODOS" //se eliminan los todos
                    ||
                    typeof object[key] == "object") { //se elimina los objetos del filtro.
                    delete object[key];
                }
            }
        };

        /**
         * Se encarga de limpiar los criterios del filtrado.
         * @function
         */
        $scope.limpiar = function () {
            $scope.filterBy = {};
            $scope.filterByModel = {};
            $scope.filtros = {};
        };

        $scope.cambiarEstado = function (recurso) {
            $('.remove-row').css('color', '#333538');
            var operacion = 'Activar';
            console.log('init operacion : ', recurso.activo);
            $scope.mensajeExitoso = 'El Registro se ha activado exitosamente';
            if (recurso.activo) {
                operacion = 'Desactivar';
            }
            if (window.confirm("¿Está seguro de " + operacion + " el Registro?")) {
                var activar = true;
                if (recurso.activo) {
                    activar = false;
                    $scope.mensajeExitoso = 'El Registro se ha desactivado exitosamente';
                }
                this.service.actualizarEstado(recurso.id, activar).then(cambiarEstadoSuccess, cambiarEstadoError);
            } else {
                console.log('cancelo operacion : ', recurso.activo);
            }
        };

        $scope.tienePermiso = function (permiso) {
            var permisos = localStorage.getItem('permisos');
            if (permisos !== null && permisos.length > 0) {
                var listaPermisos = permisos.split(',');
                if (listaPermisos.indexOf(permiso) != -1) {
                    return true;
                }
            }
            return false;
        };

        function cambiarEstadoSuccess(response) {
            Message.ok($scope.mensajeExitoso);
            $route.reload();
        }

        function cambiarEstadoError(data) {
            Message.error("No se pudo realizar la operación");
        }
    }
]);
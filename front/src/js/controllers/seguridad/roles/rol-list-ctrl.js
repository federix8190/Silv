/**
 * @class
 * Controller que implementa la busqueda y listado de roles.
 *
 */
app.controller('RolListCtrl', ['$scope', 'RolService', '$controller', '$route', '$rootScope',
    function ($scope, service, $controller, $route, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $rootScope.currentPage = 'AdministrarRol';

        /**
         * Configuraciones de la cabecera de la grilla.
         * @private
         * @type {Array}
         */
        var header = [
            {
                "name": "Código",
                "key": "codigo"
            }, {
                "name": "Descripción",
                "key": "descripcion"
            }, {
                "key": "",
                "name": "Acción"
            }
        ];

        $rootScope.$on('$routeChangeStart', function (e, next, current) {
            $rootScope.codigo = $scope.filterBy.codigo;
            $rootScope.descripcion = $scope.filterBy.descripcion;
        });

        function initFilters() {
            if ($rootScope.cargarFiltro) {
                $scope.filterBy.codigo = $rootScope.codigo;
                $scope.filterBy.descripcion = $rootScope.descripcion;
                $scope.buscar();
            } else {
                $scope.filtros = {
                    activo: true
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
            //$scope.filterBy.activo = true;
            initFilters();
        })();

    }]);

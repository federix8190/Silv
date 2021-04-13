/**
 * @class
 * Controller que implementa la busqueda y listado de cuo.
 *
 * @name angular-keycloak-seed.controller##CeoListCtrl
 */
app.controller('CuoListCtrl', ['$scope', 'CuoService', '$controller', '$route', '$rootScope',
    function ($scope, service, $controller, $route, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $rootScope.currentPage = 'ListarCuo';

        $scope.soloActivos = false;
        $scope.seleccionarTodos = function() {
            $scope.soloActivos = !$scope.soloActivos;
            $scope.filterBy.activo = $scope.soloActivos;
        };

        /**
         * Configuraciones de la cabecera de la grilla.
         * @private
         * @type {Array}
         */
        var header = [            
            {
                "name": "Nivel",
                "key": "nivel"
            },
            {
                "name": "Subnivel",
                "key": "subNivel"
            },
            {
                "name": "Número",
                "key": "numero"
            },            
            {
                "name": "Denominación",
                "key": "denominacion"
            },
            {
                "name": "Acción",
                "key": ""
            }
        ];

        $scope.getCSV = function(){
            var a = document.createElement("a");
            document.body.appendChild(a);
            
            service.getCSV($scope.filterBy).then(function (response) {
                var file = new Blob([response.data], {
                    encoding: "UTF-8",
                    type: 'application/csv;charset=UTF-8'
                });
                var fileURL = URL.createObjectURL(file);
                var name = getFileNameFromHttpResponse(response);
                a.href = fileURL;
                a.download = name;
                a.click();
            });
        };

        $scope.getXLS = function(){
            var a = document.createElement("a");
            document.body.appendChild(a);
            
            service.getXLS($scope.filterBy).then(function (response) {
                var file = new Blob([response.data], {
                    encoding: "UTF-8",
                    type: 'application/vnd.ms-excel'
                });
                var fileURL = URL.createObjectURL(file);
                var name = getFileNameFromHttpResponse(response);
                a.href = fileURL;
                a.download = name;
                a.click();
            });
        };

        $scope.getPDF = function(){
            var a = document.createElement("a");
            document.body.appendChild(a);
            
            service.getPDF($scope.filterBy).then(function (response) {
                var file = new Blob([response.data], {
                    encoding: "UTF-8",
                    type: 'application/pdf'
                });
                var fileURL = URL.createObjectURL(file);
                var name = getFileNameFromHttpResponse(response);
                a.href = fileURL;
                a.download = name;
                a.click();
            });
        };

        function getFileNameFromHttpResponse(httpResponse) {
            var contentDispositionHeader = httpResponse.headers('Content-Disposition');
            var result = contentDispositionHeader.split(';')[1].trim().split('=')[1];
            return result.replace(/"/g, '');
        }

        $rootScope.$on('$routeChangeStart', function (e, next, current) {
            $rootScope.nivel = $scope.filterBy.nivel;
            $rootScope.subNivel = $scope.filterBy.subNivel;
            $rootScope.numero = $scope.filterBy.numero;
            $rootScope.denominacion = $scope.filterBy.denominacion;
        });

        /**
         * Se encarga de inicializar los criterios de filtrado
         */
        function initFilters() {
            if ($rootScope.cargarFiltro) {
                $scope.filterBy.nivel = $rootScope.nivel;
                $scope.filterBy.subNivel = $rootScope.subNivel;
                $scope.filterBy.numero = $rootScope.numero;
                $scope.filterBy.denominacion = $rootScope.denominacion;
                $scope.buscar();
            } else {
                $scope.filterBy = {
                    //activo: true
                }
                $scope.initFilters($scope.filterBy);
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
            $rootScope.cargarFiltro = false;
            initFilters();
        };        

    }]);

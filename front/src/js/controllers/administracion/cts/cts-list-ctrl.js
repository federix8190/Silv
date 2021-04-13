/**
 * @class
 * Controller que implementa la busqueda y listado de Cts.
 *
 * @name angular-keycloak-seed.controller#CtsListCtrl
 * @author <a href = "mailto:marcelo.szcerba@konecta.com.py">Marcelo Szcerba</a>
 */
app.controller('CtsListCtrl', ['$scope', 'CtsService', '$controller', '$location', '$rootScope',
    function ($scope, service, $controller, $location, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $rootScope.currentPage = 'ListarCts';
        $scope.soloActivos = false;
        $scope.seleccionarTodos = function() {
            $scope.soloActivos = !$scope.soloActivos;
            $scope.filterBy.activo = $scope.soloActivos;
        };

        $scope.meses = [
            'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio',
            'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
        ];

        /**
         * Configuraciones de la cabecera de la grilla.
         * @private
         * @type {Array}
         */
        var header = [{
                "name": "Número Tramo",
                "key": "numeroTramo"
            },
            {
                "name": "Mínimo",
                "key": "minimo"
            },
            {
                "name": "Máximo",
                "key": "maximo"
            },
            {
                "name": "Mes",
                "key": "mes"
            },
            {
                "name": "Año",
                "key": "anho"
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
        /**
         * Se encarga de recuperar la lista de tramos para poblar el combo
         * de tramos del panel de filtros.
         */
        function getTramos() {
            service.getTramos().then(
                function (response) {
                    $scope.listaTramos = response.data;
                },
                function (response) {
                    Message.error("Ocurrió un error al recuperar la lista de tramos.");
                }
            );
        }

        $rootScope.$on('$routeChangeStart', function (e, next, current) {
            $rootScope.numeroTramo = $scope.filterBy.numeroTramo;
            $rootScope.mes = $scope.filterBy.mes;
            $rootScope.anho = $scope.filterBy.anho;
        });

        /** 
         * Se encarga de inicializar los filtros.
         */
        function initFilters() {
            if ($rootScope.cargarFiltro) {
                $scope.filterBy.numeroTramo = $rootScope.numeroTramo;
                $scope.filterBy.mes = $rootScope.mes;
                $scope.filterBy.anho = $rootScope.anho;
                $scope.buscar();
            } else {
                $scope.filters = {};
                var now = new Date();
                var mesActual = now.getMonth();
                if (mesActual == 0) {
                    $scope.filterBy.mes  = "12";
                    $scope.filterBy.anho = now.getFullYear() - 1;
                } else {
                    $scope.filterBy.mes = (now.getMonth()) + "";
                    $scope.filterBy.anho = now.getFullYear();
                }
                $scope.filterBy.numeroTramo = "TODOS";
                //initFilters($scope.filters);
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
            $scope.footer = true;
            initFilters();
            getTramos();
        })();
    }
]);
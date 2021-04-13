/**
 * @class
 * Controller que implementa la busqueda y listado de Cts.
 *
 * @name angular-keycloak-seed.controller##ConfigCtsListCtrl
 * @author <a href = "mailto:marcelo.szcerba@konecta.com.py">Marcelo Szcerba</a>
 */
app.controller('ConfigCtsListCtrl', ['$scope', 'ConfigCtsService', '$controller', 
        '$location', '$rootScope',
    function ($scope, service, $controller, $location, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $rootScope.currentPage = 'ListarConfigCts';
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
        var header = [{
                "name": "Número Tramo",
                "key": "numeroTramo"
            }, {
                "name": "Peso",
                "key": "peso"
            }, /*{
                "name": "Mínimo",
                "key": "minimo"
            }, {
                "name": "Máximo",
                "key": "maximo"
            },*/ {
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
            $rootScope.numeroTramo = $scope.filterBy.numeroTramo;
        });

        function initFilters() {
            if ($rootScope.cargarFiltro) {
                $scope.filterBy.numeroTramo = $rootScope.numeroTramo;
                $scope.buscar();
            } else {
                $scope.filtros = {
                    activo: true
                }
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
            $scope.footer = true;
            initFilters();    
        })();
    }
]);
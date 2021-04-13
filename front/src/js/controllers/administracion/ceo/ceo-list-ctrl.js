/**
 * @class
 * Controller que implementa la busqueda y listado de ceo.
 *
 * @name angular-keycloak-seed.controller##CeoListCtrl
 * @author <a href = "mailto:marcelo.szcerba@konecta.com.py">Marcelo Szcerba</a>
 */
app.controller('CeoListCtrl', ['$scope', '$http', 'CeoService', '$controller', '$route', '$rootScope',
    function ($scope, $http, service, $controller, $route, $rootScope) {

        /**
         * Service utilizado para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $scope.exportando = false;
        $scope.disabledButtonExportar = true;
        $rootScope.currentPage = 'ListarCeo';

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
                "name": "Código",
                "key": "codigo"
            },            
            {
                "name": "Denominación",
                "key": "denominacion"
            },
            {
                "name": "Nivel CUO",
                "key": "nivel"
            },
            {
                "name": "Denominación CUO",
                "key": "denominacionCUO"
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
            $rootScope.codigo = $scope.filterBy.codigo;
            $rootScope.denominacion = $scope.filterBy.denominacion;
            $rootScope.nivel = $scope.filterBy.nivel;
            $rootScope.subNivel = $scope.filterBy.subNivel;
            $rootScope.numero = $scope.filterBy.numero;
            $rootScope.denominacionCUO = $scope.filterBy.denominacionCUO;
        });
        
        function initFilters() {
            if ($rootScope.cargarFiltro) {
                $scope.filterBy.codigo = $rootScope.codigo;
                $scope.filterBy.denominacion = $rootScope.denominacion;
                $scope.filterBy.nivel = $rootScope.nivel;
                $scope.filterBy.subNivel = $rootScope.subNivel;
                $scope.filterBy.numero = $rootScope.numero;
                $scope.filterBy.denominacionCUO = $rootScope.denominacionCUO;
                $scope.buscar();
            } else {
                $scope.filtros = {
                //    activo: true
                }
                $scope.initFilters($scope.filtros);
            }
        }

        $scope.uploadFiles = function (file, errFiles) {
            $scope.archivo = file;
            var reader = new FileReader();
            reader.onload = $scope.fileIsLoaded;
            reader.readAsDataURL(file);
        };

        function numberWithCommas(x) {
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }

        $scope.fileIsLoaded = function (e) {
            console.log($scope.archivo);
            $scope.size = '(' + numberWithCommas(Math.ceil($scope.archivo.size / 1024)) + ' K)';
            $scope.fileName = $scope.archivo.name;
            $scope.archivoCargado = true;
            $scope.disabledButtonExportar = false;
            $scope.$apply(function () {});
        };

        $scope.exportar = function () {
            $scope.exportando = true;
            $scope.disabledButtonExportar = true;
            console.log('exportar archivo !!!');
            /*$scope.file = $scope.archivo;
            service.uploadFile($scope.archivo).success(function (data) {
                    $scope.exportando = false;
                    $scope.disabledButtonExportar = false;
                    Message.ok("Archivo enviado correctamente");
                }).error(function (data, code) {
                    $scope.exportando = false;
                    $scope.disabledButtonExportar = false;
                    Message.error("No se pudo realizar la operación");
                });*/

            var backendUrl = '/cte-api/api/administracion/ceo/subir-cv';
            var fd = new FormData();

            fd.append('file', $scope.archivo, 'filename.ods');

            $http.post(backendUrl, fd, {
                // this cancels AngularJS normal serialization of request
                transformRequest: angular.identity,
                // this lets browser set `Content-Type: multipart/form-data` 
                // header and proper data boundary
                headers: {'Content-Type': undefined}
            })
            .then(function successCallback(response) {
                $scope.exportando = false;
                    $scope.disabledButtonExportar = false;
                    Message.ok("Archivo enviado correctamente");
            }, function errorCallback(response) {
                $scope.exportando = false;
                    $scope.disabledButtonExportar = false;
                    Message.error("No se pudo realizar la operación");
            });
            
        };
        
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
            //$scope.filterBy.disponible = "S";
            initFilters();
        })();

        /**
         * Override de la función limpiar base
         * Se encarga de limpiar los criterios del filtrado.
         * @function
         */
        $scope.limpiar = function () {
            initFilters();
        };

    }]);

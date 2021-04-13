/**
 * @class
 * Controller que implementa la busqueda y listado de cpt-ef.
 *
 * @name angular-keycloak-seed.controller##CptEFListCtrl
 * @author <a href = "mailto:marcelo.szcerba@konecta.com.py">Marcelo Szcerba</a>
 */
app.controller('CptEFListCtrl', ['$scope', 'CptEFService', 'LegajoService', '$controller', 
        '$route', '$rootScope',
    function ($scope, service, LegajoService, $controller, $route, $rootScope) {

        /**
         * Service utilizado para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $rootScope.currentPage = 'ListarCptF';

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
        
        if ($rootScope.esHacienda) {

            var header = [
                {
                    "name": "Nivel G",
                    "key": "nivelCpt"
                }, {
                    "name": "SubNivel G",
                    "key": "subNivelCpt"
                }, {
                    "name": "Nro G",
                    "key": "numeroCpt"
                }, {
                    "name": "Clase General",
                    "key": "cpt"
                }, {
                    "name": "Titular Unidad",
                    "key": "titularUnidadCpt"
                }, {
                    "name": "Nro. Secuencial",
                    "key": "nro"
                }, {
                    "name": "Clase EF",
                    "key": "den"
                }, {
                    "name": "Orientación Funcional",
                    "key": "orientacionFunc"
                }, {
                    "name": "Código Mecip",
                    "key": "mecip"
                }, {
                    "name": "Acción",
                    "key": ""
                }
            ];

        } else {
            var header = [
                {
                    "name": "Nivel G",
                    "key": "nivelCpt"
                }, {
                    "name": "SubNivel G",
                    "key": "subNivelCpt"
                }, {
                    "name": "Nro G",
                    "key": "numeroCpt"
                }, {
                    "name": "Clase General",
                    "key": "cpt"
                }, {
                    "name": "Titular Unidad",
                    "key": "titularUnidadCpt"
                }, {
                    "name": "Nro. Secuencial",
                    "key": "nro"
                }, {
                    "name": "Ámbito",
                    "key": "ambito"
                }, {
                    "name": "Clase EF",
                    "key": "den"
                }, {
                    "name": "Orientación Funcional",
                    "key": "orientacionFunc"
                }, {
                    "name": "Código Mecip",
                    "key": "mecip"
                }, {
                    "name": "Acción",
                    "key": ""
                }
            ];
        }

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
            $rootScope.nivelCPT = $scope.filterBy.nivelCPT;
            $rootScope.subNivelCpt = $scope.filterBy.subNivelCpt;
            $rootScope.numeroCpt = $scope.filterBy.numeroCpt;
            $rootScope.denominacionCpt = $scope.filterBy.denominacionCpt;
            $rootScope.titularUnidadCpt = $scope.filterBy.titularUnidadCpt;
            $rootScope.nro = $scope.filterBy.nro;
            $rootScope.ambito = $scope.filterBy.ambito;
            $rootScope.den = $scope.filterBy.den;
            $rootScope.orientacionFunc = $scope.filterBy.orientacionFunc;
            $rootScope.mecip = $scope.filterBy.mecip;
        });

        /** 
         * Se encarga de inicializar los fitros
         */
        function initFilters() {
            console.log('initFilters : ', $rootScope.cargarFiltro);
            if ($rootScope.cargarFiltro) {
                $scope.filterBy.nivelCPT = $rootScope.nivelCPT;
                $scope.filterBy.subNivelCpt = $rootScope.subNivelCpt;
                $scope.filterBy.numeroCpt = $rootScope.numeroCpt;
                $scope.filterBy.denominacionCpt = $rootScope.denominacionCpt;
                $scope.filterBy.titularUnidadCpt = $rootScope.titularUnidadCpt;
                $scope.filterBy.nro = $rootScope.nro;
                $scope.filterBy.ambito = $rootScope.ambito;
                $scope.filterBy.den = $rootScope.den;
                $scope.filterBy.orientacionFunc = $rootScope.orientacionFunc;
                $scope.filterBy.mecip = $rootScope.mecip;
                $scope.buscar();
            } else {
                //$scope.initFilters($scope.filterBy);
                //$scope.filterBy.titularUnidadCpt = "TODOS";
                $scope.filtros = {
                    //activo: true,
                    titularUnidadCpt: "TODOS"
                };
                $scope.initFilters($scope.filtros);
            }
        }

        function getAmbitos() {
            LegajoService.getAmbitos().then(
                function (response) {
                    $scope.listaAmbitos = response.data;
                },
                function () {
                    Message.error("Ocurrió un error al recuperar la lista de ámbitos.");
                }
            );
        }

        function getOrientacionFunc() {
            LegajoService.getOrientacionFunc().then(
                function (response) {
                    $scope.listaOrientacionFunc = response.data;
                },
                function () {
                    Message.error("Ocurrió un error al recuperar la lista de orientaciones funcionales.");
                }
            );
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
            getAmbitos();   
            getOrientacionFunc();         
        })();

        /**
         * Override de la función limpiar base
         * Se encarga de limpiar los criterios del filtrado.
         * @function
         */
        $scope.limpiar = function () {
            initFilters();
        }

        $scope.cantidadHeader = function () {
            var colspanCptGral = 0;
            var colspanCptEF = 0;

            $scope.visibleCptGral = false;
            $scope.visibleCptEF = false;

            for (var idx in $scope.config.header) {
                var keyEx = $scope.config.header[idx].key;
                var visible = $scope.config.header[idx].visible;
                if((keyEx == 'numeroCpt' && visible) || (keyEx == 'subNivelCpt' && visible) || (keyEx == 'nivelCpt' && visible) || (keyEx == 'cpt' && visible) || (keyEx == 'titularUnidadCpt' && visible)){
                    colspanCptGral++;
                } else if ((keyEx == 'nro' && visible) || (keyEx == 'ambito' && visible)  || (keyEx == 'den' && visible)
                    || (keyEx == 'orientacionFunc' && visible)) {
                    colspanCptEF++;
                } 
            }
            if(colspanCptGral > 0){
                $scope.visibleCptGral = true
            }
            if(colspanCptEF > 0){
                $scope.visibleCptEF = true
            }

            $scope.colspanCptGral = colspanCptGral;
            $scope.colspanCptEF = colspanCptEF;

            return true;
        }

        /**
         * Se encarga de eliminar el recurso
         * @function
         */
        $scope.eliminar = function (recurso) {
            if (window.confirm("¿Está seguro de eliminar el recurso?"))
                this.service.eliminar(this.getPrimaryKey(recurso))
                    .then(eliminarRecursoSuccess, eliminarRecursoError);
        };

        function eliminarRecursoSuccess(response) {
            Message.ok("El registro se ha eliminado exitosamente.");
            $route.reload();
        }

        function eliminarRecursoError(data) {
            Message.error("No se pudo realizar la operación");
        }
    }]);

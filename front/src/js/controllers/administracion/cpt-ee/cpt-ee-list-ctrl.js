/**
 * @class
 * Controller que implementa la busqueda y listado de Empresas.
 *
 * @name angular-keycloak-seed.controller##EmpresaListCtrl
 * @author <a href = "mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.controller('CptEEListCtrl', ['$scope', 'CptEEService', 'LegajoService', '$controller', 
        '$route', '$rootScope',
    function ($scope, service, LegajoService, $controller, $route, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $rootScope.currentPage = 'ListarCptE';

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
                "name": "Nivel G",
                "key": "nivelCpt"
            },
            {
                "name": "SubNivel G",
                "key": "subNivelCpt"
            },
            {
                "name": "Nro G",
                "key": "numeroCpt"
            },
            {
                "name": "Clase General",
                "key": "cpt"
            },
            {
                "name": "Titular Unidad",
                "key": "tituloUnidadCpt"
            },
            {
                "name": "Nro. Secuencial",
                "key": "numero"
            },
            {
                "name": "Ámbito",
                "key": "ambito"
            },
            {
                "name": "Categoría",
                "key": "nombreCategoria"
            },
            {
                "name": "Nivel",
                "key": "nivel"
            },
            {
                "name": "Clase EE",
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
            $rootScope.nivelCPT = $scope.filterBy.nivelCPT;
            $rootScope.subNivelCpt = $scope.filterBy.subNivelCpt;
            $rootScope.numeroCpt = $scope.filterBy.numeroCpt;
            $rootScope.denominacionCpt = $scope.filterBy.denominacionCpt;
            $rootScope.titularUnidadCpt = $scope.filterBy.titularUnidadCpt;
            $rootScope.numero = $scope.filterBy.numero;
            $rootScope.ambito = $scope.filterBy.ambito;
            $rootScope.nombreCategoria = $scope.filterBy.nombreCategoria;
            $rootScope.nivel = $scope.filterBy.nivel;
            $rootScope.denominacion = $scope.filterBy.denominacion;
        });

        function initFilters() {
            if ($rootScope.cargarFiltro) {
                $scope.filterBy.nivelCPT = $rootScope.nivelCPT;
                $scope.filterBy.subNivelCpt = $rootScope.subNivelCpt;
                $scope.filterBy.numeroCpt = $rootScope.numeroCpt;
                $scope.filterBy.denominacionCpt = $rootScope.denominacionCpt;
                $scope.filterBy.titularUnidadCpt = $rootScope.titularUnidadCpt;
                $scope.filterBy.numero = $rootScope.numero;
                $scope.filterBy.ambito = $rootScope.ambito;
                $scope.filterBy.nombreCategoria = $rootScope.nombreCategoria;
                $scope.filterBy.nivel = $rootScope.nivel;
                $scope.filterBy.denominacion = $rootScope.denominacion;
                $scope.buscar();
            } else {
                $scope.filtros = {
                    //activo: true
                }
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
            $scope.config.recurso = 'cpt-ee';
            initFilters();
            getAmbitos();
        })();

        $scope.cantidadHeader = function () {
            var colspanCptGral = 0;
            var colspanCptEE = 0;

            $scope.visibleCptGral = false;
            $scope.visibleCptEE = false;

            for (var idx in $scope.config.header) {
                var keyEx = $scope.config.header[idx].key;
                var visible = $scope.config.header[idx].visible;
                console.log("keyEx",keyEx);
                console.log("visible",visible);
                if((keyEx == 'subNivelCpt' && visible) || (keyEx == 'numeroCpt' && visible) || (keyEx == 'nivelCpt' && visible) || (keyEx == 'cpt' && visible) || (keyEx == 'tituloUnidadCpt' && visible)){
                    colspanCptGral++;
                } else if ((keyEx == 'numero' && visible) || (keyEx == 'ambito' && visible)  || (keyEx == 'nivel' && visible)
                    || (keyEx == 'denominacion' && visible)) {
                    colspanCptEE++;
                } 
            }
            if(colspanCptGral > 0){
                $scope.visibleCptGral = true
            }
            if(colspanCptEE > 0){
                $scope.visibleCptEE = true
            }

            $scope.colspanCptGral = colspanCptGral;
            $scope.colspanCptEE = colspanCptEE;
            return true;
        }

        /**
         * Override de la función limpiar base
         * Se encarga de limpiar los criterios del filtrado.
         * @function
         */
        $scope.limpiar = function () {
            initFilters();
        };

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
    }
]);
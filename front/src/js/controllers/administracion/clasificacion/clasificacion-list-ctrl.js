/**
 * @class
 * Controller que implementa la busqueda y listado de cpt-ef.
 *
 * @name angular-keycloak-seed.controller##ClasificacionListCtrl
 * @author <a href = "mailto:marcelo.szcerba@konecta.com.py">Marcelo Szcerba</a>
 */
app.controller('ClasificacionListCtrl', ['$scope', 'ClasificacionService', '$controller', '$route',
    function ($scope, service, $controller, $route) {

        /**
         * Service utilizado para recuperar los datos y realizar las operaciones.
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
                "name": "Nivel G",
                "key": "nivel"
            },
            {
                "name": "SubNivel G",
                "key": "subNivel"
            },
            {
                "name": "Nro G",
                "key": "numeroGasto"
            },
            {
                "name": "Clase General",
                "key": "cpt"
            },
            {
                "name": "Titular Unidad",
                "key": "titularUnidad"
            },
            {
                "name": "Código",
                "key": "codigo"
            },
            {
                "name": "Denominacion",
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

        /** 
         * Se encarga de inicializar los fitros
         */
        function initFilters() {
            $scope.initFilters($scope.filterBy);
            $scope.filterBy.titularUnidadCpt = "TODOS";
            
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
            $scope.filterBy.activo = true;
            initFilters();       
        })();

        $scope.cantidadHeader = function () {
            var colspanCptGral = 0;
            var colspanClasificacion = 0;

            $scope.visibleCptGral = false;
            $scope.visibleClasificacion = false;

            for (var idx in $scope.config.header) {
                var keyEx = $scope.config.header[idx].key;
                var visible = $scope.config.header[idx].visible;
                if((keyEx == 'numeroGasto' && visible) || (keyEx == 'subNivel' && visible) 
                        || (keyEx == 'nivel' && visible) || (keyEx == 'cpt' && visible) 
                        || (keyEx == 'titularUnidad' && visible)){
                    colspanCptGral++;
                } else if ((keyEx == 'codigo' && visible) || (keyEx == 'ambito' && visible)  
                    || (keyEx == 'denomonacion' && visible)
                    || (keyEx == 'orientacionFunc' && visible)) {
                    colspanClasificacion++;
                } 
            }
            if(colspanCptGral > 0){
                $scope.visibleCptGral = true
            }
            if(colspanClasificacion > 0){
                $scope.visibleClasificacion = true
            }

            $scope.colspanCptGral = colspanCptGral;
            $scope.colspanClasificacion = colspanClasificacion;

            return true;
        }


        /**
         * Se encarga de inicializar los criterios de filtrado
         */
        function initFilters() {
            $scope.filterBy = {
                activo: true,
                titularUnidadCpt: "TODOS"
            }
            $scope.initFilters($scope.filterBy);
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
    }]);

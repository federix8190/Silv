/**
 * @class
 * Controller que implementa la busqueda y listado de Cargo vacantes.
 *
 * @name angular-keycloak-seed.controller##CargosVacantesListCtrl
 * @author <a href = "mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */

app.controller('CargosDisponiblesListCtrl', ['$scope', 'CargosDisponiblesService', 'LegajoService',
        'ProgramaService', 'CptService','CtsService', '$controller', '$rootScope',
    function ($scope, service, LegajoService, ProgramaService, CptService, CtsService, $controller, $rootScope) {
        
        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $scope.paramsObj = {};

        /**
         * Configuraciones de la cabecera de la grilla.
         * @private
         * @type {Array}
         */

        if ($rootScope.esHacienda) {
            var header = [{
                    "name": "Año",
                    "key": "anho"
                }, {
                    "name": "Mes",
                    "key": "mes"
                }, {
                    "name": "Descripción",
                    "key": "nombre"
                }, {
                    "name": "Categoría",
                    "key": "categoria"
                }, /*{
                    "name": "Línea",
                    "key": "linea"
                },*/ {
                    "name": "Salario Presupuestado",
                    "key": "presupuestado"
                }, {
                    "name": "Programa",
                    "key": "descripcionPrograma"
                }, {
                    "name": "Subprograma",
                    "key": "descripcionSubprograma"
                }, /*{
                    "name": "Título",
                    "key": "departamento",
                    visible: false
                },*/ {
                    "name": "CPT EF",
                    "key": "cptEf"
                }, {
                    "name": "Línea",
                    "key": "linea"
                }, {
                    "name": "Acción",
                    "key": ""
                }
            ];
        } else {
            var header = [{
                "name": "Año",
                "key": "anho"
            }, {
                "name": "Mes",
                "key": "mes"
            }, {
                "name": "Descripción",
                "key": "nombre"
            }, {
                "name": "Categoría",
                "key": "categoria"
            }, /*{
                "name": "Línea",
                "key": "linea"
            },*/ {
                "name": "Salario Presupuestado",
                "key": "presupuestado"
            }, {
                "name": "Programa",
                "key": "descripcionPrograma"
            }, {
                "name": "Subprograma",
                "key": "descripcionSubprograma"
            }, {
                "name": "CPT EF",
                "key": "cptEf"
            }, {
                "name": "CPT EE",
                "key": "cptEe"
            }, {
                "name": "Departamento",
                "key": "departamento",
                "visible": false
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

        /** 
         * Recupera la lista de departamentos para poblar el combo de departamentos.
         */
        function getDepartamentos() {
            LegajoService.getDepartamentos().then(
                function (response) {
                    $scope.listaDepartamentos = response.data;
                    $scope.departamento = $scope.listaDepartamentos[$scope.listaDepartamentos.length - 1];
                }, function () {
                    Message.error("Ocurrió un error al recuperar la lista de departamentos.");
                }
            );
        }

        /**
         * Se encarga de recuperar la lista de programas.
         *
         * @function
         */
        function getProgramas() {
            var params = {
                count: -1,
                sortOrder: "DESC"
            };
            return ProgramaService.listar(params).then(
                function (response) {
                    $scope.listaProgramas = response.data;
                },
                function () {
                    Message.error("Ocurrió un error al recuperar la lista de programas");
            });

        }
            
        /**
         * Se encarga de recuperar la lista de subprogramas.
         *
         * @function
         */
        function getSubProgramas() {
            var params = {
                count: -1,
                sortOrder: "DESC"
            };
            
            return ProgramaService.listarSubProgramas(params).then(
                function (response) {
                    $scope.listaSubProgramas = response.data;
                },
                function () {
                    Message.error("Ocurrió un error al recuperar la lista de subprogramas");
                });
        }

        /** 
         * Handler del change del combo de departamentos
         */
        $scope.onChangeDepartamento = function (circunscripcion) {
            $scope.filterBy.circunscripcion = circunscripcion;
        };

        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            getSubProgramas();
            getProgramas();
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteListCtrl', {
                "$scope": $scope
            }));
            $scope.config.header = header;
            $scope.config.recurso = 'cargo';
            $scope.footer = true;
            //getDepartamentos();
            $scope.filterBy = {};
            var now = new Date();
            $scope.filterBy.mes = (now.getMonth()) + "";
            $scope.filterBy.anho = now.getFullYear();

            var filters = {
                anho : now.getFullYear(),
                mes : (now.getMonth()) + ""
            };
            $scope.initFilters(filters);
        })();

        /**
         * Se encarga de recuperar la lista paginada de los datos.
         * @function
         */
        $scope.getResource = function (params, paramsObj) {
            $scope.initHeader($scope.config.header);
            paramsObj.sortOrder = paramsObj.sortOrder == 'dsc' ? "DESC" : "ASC";
            $scope.loading = true;
            $scope.config.pagination.page = paramsObj.page == 0 ? $scope.init.page : paramsObj.page;
            $scope.config.pagination.count = paramsObj.count == 0 ? $scope.init.count : paramsObj.count;
            if (paramsObj.filters) {
                $scope.deleteUndefinedValues(paramsObj.filters);
            }
            $scope.paramsObj = paramsObj;
            return this.service.listar(paramsObj)
                .then(function (response) {
                    $scope.loading = false;
                    $scope.config.rows = response.data.rows;
                    $scope.config.pagination.size = response.data.count;
                    $scope.config.pagination.pages = Math.ceil(response.data.count / $scope.config.pagination.count);
                    if (response.data.count == 0) {
                        $scope.noData();
                        $scope.config.rows = [{}];
                    }
                    return $scope.config;
                }, function (response) {
                    $scope.noData();
                    $scope.config.rows = [];
                    return $scope.config;
                });
        };

        $scope.eliminar = function(e, cargo) {
            e.preventDefault();
            console.log(cargo.idCptEe);
            if (cargo.idCptEe !== null || cargo.idCptEf !== null) {
                Message.error("El cargo ya se encuentra asignado a un CPT");
                return;
            } else {
                service.eliminar(cargo.id).then(
                    function (response) {
                        if (response.status == 200 || response.status == 204) {
                            $scope.getResource(null, $scope.paramsObj);
                            Message.ok("Operación exitosa.");
                        } else {
                            Message.error("No se pudo realizar la operación");
                        }
                    }
                );
            }
        };
}]);

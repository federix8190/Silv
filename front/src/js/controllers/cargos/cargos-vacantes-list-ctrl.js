/**
 * @class
 * Controller que implementa la busqueda y listado de Cargo Vacantes.
 *
 * @name angular-keycloak-seed.controller##CargosVacantesCtrl
 * @author <a href = "mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */

app.controller('CargosVacantesCtrl', ['$scope', 'CargosVacantesService', 'CargosDisponiblesService', 'LegajoService',
        'CptService','CtsService', 'ProgramaService', '$controller', '$route', '$rootScope',
    function ($scope, service, CargosDisponiblesService, LegajoService, CptService, CtsService, ProgramaService, $controller, $route, $rootScope) {
        
        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;


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
                }, {
                    "name": "Salario Presupuestado",
                    "key": "presupuestado"
                }, {
                    "name": "CPT EF",
                    "key": "cptEf"
                }, {
                    "name": "Programa",
                    "key": "programa"
                }, {
                    "name": "Subprograma",
                    "key": "subprograma"
                }, {
                    "name": "Departamento",
                    "key": "departamento",
                    "visible": false
                }, {
                    "name": "Línea",
                    "key": "linea",
                    "visible": false
                }, {
                    "name": "Asignar a Convocatoria",
                    "key": ""
                }, {
                    "name": "Acción",
                    "key": ""
                },
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
            }, {
                "name": "Salario Presupuestado",
                "key": "presupuestado"
            }, {
                "name": "CPT EE",
                "key": "cptEe"
            }, {
                "name": "CPT EF",
                "key": "cptEf"
            }, {
                "name": "Programa",
                "key": "programa"
            }, {
                "name": "Subprograma",
                "key": "subprograma"
            }, {
                "name": "Departamento",
                "key": "departamento",
                "visible": false
            }, {
                "name": "Asignar a Convocatoria",
                "key": ""
            }, {
                "name": "Acción",
                "key": ""
            },
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

        $scope.eliminar = function(e, cargo) {
            e.preventDefault();
            console.log(cargo);
            CargosDisponiblesService.eliminarCargoVacante(cargo.id).then(
                    function (response) {
                           console.log("eliminado ", response.data);                      
                        if (response.data) {
                            Message.ok("Operación exitosa. Cargo vacante eliminado");
                            $route.reload();                        
                        } else {
                            Message.error("No se pudo realizar la operación. El cargo se encuentra asociado a una convocatoria");
                        }
                    }
                );
        };

        $scope.activarCargo = function(e, cargo) {
            //e.preventDefault();
            console.log('Disponible : ', cargo.asignable);
            console.log('Cargo : ', cargo);

            service.cambiarEstado(cargo).then(
                function (response) {
                    console.log('Status server : ', response.status);
                    if (response.status == 200) {
                        console.log('Disponible : ', cargo.asignable);
                        Message.ok("Operación exitosa.");
                    } else {
                        cargo.asignable = !cargo.asignable;
                        Message.error("No se pudo realizar la operación");
                    }
                }
            );
        };
}]);

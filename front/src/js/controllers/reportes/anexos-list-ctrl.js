/*jslint node: true */
/*jslint nomen: true */
/*global angular, _ */
"use strict";

app.controller('AnexoListCtrl', ['$scope', 'AnexoService', 'LegajoService', 'ProgramaService', '$controller',
    function ($scope, service, LegajoService, ProgramaService, $controller) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

        $scope.listaSubProgramas = [];
        $scope.listaProgramas = [];
        $scope.listaVinculaciones = [];
        $scope.vinculacion = null;

        var header = [];
        if($scope.esHacienda){
            header = [{
                "key": "anho",
                "name": "Año",
            }, {
                "key": "mes",
                "name": "Mes"
            }, {
                "key": "nivelEntidad",
                "name": "Nivel Entidad"
            }, {
                "key": "entidad",
                "name": "Entidad"
            }, {
                "key": "oee",
                "name": "OEE"
            }, {
                "key": "linea",
                "name": "Línea"
            }, {
                "key": "objetoGasto",
                "name": "Objeto Gasto"
            }, {
                "key": "fuenteFinanciamiento",
                "name": "Fuente de Financiamiento"
            }, {
                "key": "programa",
                "name": "Programa"
            }, {
                "key": "subprograma",
                "name": "SubPrograma"
            }, {
                "key": "programaPresupuestario",
                "name": "Programa Presupuestario"
            }, {
                "key": "subProgramPresupuestario",
                "name": "SubPrograma Presupuestario"
            }, {
                "key": "dependencia",
                "name": "Dependencia"
            }, {
                "key": "categoria",
                "name": "Categoría"
            }, {
                "key": "concepto",
                "name": "Concepto"
            }, {
                "key": "presupuestado",
                "name": "Presupuestado"
            }, {
                "key": "devengado",
                "name": "Devengado"
            }, {
                "key": "vinculacionFuncionario",
                "name": "Vinculación"
			}, {
                "key": "cedulaIdentidad",
                "name": "Cédula Identidad"
            }, {
                "key": "apellido",
                "name": "Apellidos"
            }, {
                "key": "nombre",
                "name": "Nombres"
            }, {
                "key": "cargo",
                "name": "Cargo"
        	}, {
                "key": "funcionReal",
                "name": "Función Real"
        	}, {
                "key": "numeroPuestoTrabajo",
                "name": "Número Puesto Trabajo"
        	}, {
                "key": "codigoCeo",
                "name": "Código CEO"
        	}, {
                "key": "denominacionCeo",
                "name": "Denominación CEO"
        	}, {
                "key": "nivelCuo",
                "name": "Código CUO"
        	}, {
                "key": "denominacionCuo",
                "name": "Denominación CUO"
        	}, {
                "key": "nivelCpt",
                "name": "Nivel"
        	}, {
                "key": "subNivelCpt",
                "name": "Sub Nivel"
        	}, {
                "key": "numeroCpt",
                "name": "Número"
        	}, {
                "key": "denominacionCpt",
                "name": "Denominación"
        	}, {
                "key": "titularUnidad",
                "name": "Titular Unidad"
            }, {
                "key": "numeroSecuencialCptF",
                "name": "Número Secuencial"
        	}, /*{
                "key": "ambitoCptF",
                "name": "Ámbito"
        	},*/ {
                "key": "codigoProceso",
                "name": "Código Mecip"
        	}, {
                "key": "denominacionCptF",
                "name": "Denominación"
        	}, {
                "key": "orientacionFuncional",
                "name": "Orientación Funcional"
        	}, {
                "key": "tramo",
                "name": "Tramo"
        	}, {
                "key": "minimo",
                "name": "Monto Mínimo"
        	}, {
                "key": "maximo",
                "name": "Monto Máximo"
        	}, {
                "key": "",
                "name": "Acción"
            }];
        } else {
            header = [{
                "key": "anho",
                "name": "Año",
            }, {
                "key": "mes",
                "name": "Mes"
            }, {
                "key": "nivelEntidad",
                "name": "Nivel Entidad"
            }, {
                "key": "entidad",
                "name": "Entidad"
            }, {
                "key": "oee",
                "name": "OEE"
            }, {
                "key": "objetoGasto",
                "name": "Objeto Gasto"
            }, {
                "key": "fuenteFinanciamiento",
                "name": "Fuente de Financiamiento"
            }, {
                "key": "programa",
                "name": "Programa"
            }, {
                "key": "subprograma",
                "name": "Sub Programa"
            }, {
                "key": "dependencia",
                "name": "Dependencia"
            }, {
                "key": "categoria",
                "name": "Categoria"
            }, {
                "key": "concepto",
                "name": "Concepto"
            }, {
                "key": "presupuestado",
                "name": "Presupuestado"
            }, {
                "key": "devengado",
                "name": "Devengado"
            }, {
                "key": "vinculacionFuncionario",
                "name": "Vinculación"
			}, {
                "key": "cedulaIdentidad",
                "name": "Cedula Identidad"
            }, {
                "key": "apellido",
                "name": "Apellidos"
            }, {
                "key": "nombre",
                "name": "Nombres"
            }, {
                "key": "cargo",
                "name": "Cargo"
        	}, {
                "key": "funcionReal",
                "name": "Función Real"
        	}, {
                "key": "numeroPuestoTrabajo",
                "name": "Número Puesto Trabajo"
        	}, {
                "key": "codigoCeo",
                "name": "Código CEO"
        	}, {
                "key": "denominacionCeo",
                "name": "Denominación CEO"
        	}, {
                "key": "nivelCuo",
                "name": "Código CUO"
        	}, {
                "key": "denominacionCuo",
                "name": "Denominación CUO"
        	}, {
                "key": "nivelCpt",
                "name": "Nivel"
        	}, {
                "key": "subNivelCpt",
                "name": "Sub Nivel"
        	}, {
                "key": "numeroCpt",
                "name": "Número"
        	}, {
                "key": "denominacionCpt",
                "name": "Denominación"
        	}, {
                "key": "titularUnidad",
                "name": "Titular Unidad"
            }, {
                "key": "numeroSecuencialCptE",
                "name": "Número Secuencial"
        	}, {
                "key": "ambitoCptE",
                "name": "Ámbito"
        	}, {
                "key": "nivelCptE",
                "name": "Nivel"
        	}, {
                "key": "categoriaCptE",
                "name": "Categoría"
        	}, {
                "key": "denominacionCptE",
                "name": "Denominación"
            }, {
                "key": "numeroSecuencialCptF",
                "name": "Número Secuencial"
        	}, {
                "key": "ambitoCptF",
                "name": "Ámbito"
        	}, {
                "key": "codigoProceso",
                "name": "Código Mecip"
        	}, {
                "key": "denominacionCptF",
                "name": "Denominación"
        	}, {
                "key": "orientacionFuncional",
                "name": "Orientación Funcional"
        	}, {
                "key": "tramo",
                "name": "Tramo"
        	}, {
                "key": "minimo",
                "name": "Monto Mínimo"
        	}, {
                "key": "maximo",
                "name": "Monto Máximo"
        	}, {
                "key": "",
                "name": "Acción"
            }];
        }

        $scope.updateVinculacion = function (vinculacion) {
            $scope.vinculacionFuncionario = vinculacion;
        };

        function getFileNameFromHttpResponse(httpResponse) {
            var contentDispositionHeader = httpResponse.headers('Content-Disposition');
            var result = contentDispositionHeader.split(';')[1].trim().split('=')[1];
            return result.replace(/"/g, '');
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

        $scope.headers = ['Id', 'Año', 'Mes', 'Apellido', 'Nombre', 'Cédula', 'Cargo', 'Categoría', 'Concepto', 'Coordinación Dpto.', 'Devengado', 'Dirección', 'Entidad', 'Fuente de Financiamiento', 'Función Real', 'Id Cargo', 'Línea', 'Nivel Entidad', 'Objeto Gasto', 'Oee', 'Presupuestado', 'Programa', 'Vinculación Funcionario'];
        $scope.order = ['pk', 'anho', 'mes', 'apellido', 'nombre', 'cedulaIdentidad', 'cargo', 'categoria', 'concepto', 'subprograma', 'devengado', 'direccion', 'entidad', 'fuenteFinanciamiento', 'funcionReal', 'idCargo', 'linea', 'nivelEntidad', 'objetoGasto', 'oee', 'presupuestado', 'programa', 'vinculacionFuncionario'];

        /** 
         * se encarga de recuperar la lista de vinculaciones.
         */
        function getVinculaciones() {
            LegajoService.getVinculaciones().then(
                function (response) {
                    if (response.data) {
                        $scope.listaVinculaciones = response.data;
                        $scope.vinculacion = $scope.listaVinculaciones[0]; 
                    }
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

        /*$scope.isOrder = function(row) {
            if(row.idCeo == null){
                return 1;
            } else{
                if($scope.esHacienda && row.idCpt == null && row.idCptEF == null){
                    return 1;
                }
                else if(!$scope.esHacienda && row.idCpt == null && row.idCptEF == null && row.idCptEE){
                    return 1;
                }
                else
                    return 2;
            }
        };*/
            
        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            getVinculaciones();
            getSubProgramas();
            getProgramas();
            getAmbitos();   
            getOrientacionFunc();  
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteListCtrl', {
                "$scope": $scope
            }));
            $scope.config.header = header;
            initFiltros();
            //$scope.footer = false;
       

        /**
         * Se encarga de recuperar la lista paginada de los datos.
         * @function
         */
        $scope.service.getResource = function (params, paramsObj) {
            $scope.initHeader($scope.config.header);
            paramsObj.sortOrder = paramsObj.sortOrder == 'dsc' ? "DESC" : "ASC";
            $scope.loading = true;
            $scope.config.pagination.page = paramsObj.page == 0 ? $scope.init.page : paramsObj.page;
            $scope.config.pagination.count = paramsObj.count == 0 ? $scope.init.count : paramsObj.count;
            if (paramsObj.filters) {
                $scope.deleteUndefinedValues(paramsObj.filters);
            }
            return $scope.listar(paramsObj)
                .then(function (response) {
                    $scope.loading = false;
                    $scope.config.rows = response.data.rows;
                    $scope.config.rows.sort(function(a, b){
                        if ($scope.isOrder(a) > $scope.isOrder(b)) {
                            return 1;
                          }
                          if ($scope.isOrder(a) < $scope.isOrder(b)) {
                            return -1;
                          }
                          // a must be equal to b
                          return 0;
                    });
                    
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

    })();

        /**
         * Se encarga de inicializar los filtros de la mantriz de congruencia.
         */
        function initFiltros() {
            $scope.filterBy = {};
            var now = new Date();
            var mesActual = now.getMonth();
            if (mesActual == 0) {
                $scope.filterBy.mes = "12";
                $scope.filterBy.anho = now.getFullYear() - 1;
            } else {
                $scope.filterBy.mes = (now.getMonth()) + "";
                $scope.filterBy.anho = now.getFullYear();
            }
            $scope.filterBy.vinculacionFuncionario = "PERMANENTE";
            $scope.filterBy.concepto = "SUELDO";
            $scope.filterBy.programa ="TODOS";
            $scope.filterBy.subprograma= "TODOS";
            $scope.filterBy.titularUnidad= "TODOS";

            var filters = {
                vinculacionFuncionario: "PERMANENTE",
                concepto: "SUELDO",
                programa: "TODOS",
                subprograma: "TODOS",
                titularUnidad: "TODOS",
                anho : $scope.filterBy.anho,
                mes : $scope.filterBy.mes
            };
            $scope.initFilters(filters);
        }

        $scope.cantidadHeader = function () {
            var colspanGrales = 0;
            var colspanPresupuestariosEstructura = 0;
            var colspanCargosPresupuestarios = 0;
            var colspanEstadoCargo = 0;
            var colspanPersonales = 0;
            var colspanOcupacionales = 0;
            var colspanEstructura = 0;
            var colspanCptGral = 0;
            var colspanCptEE = 0;
            var colspanCptEf = 0;
            var colspanTramosSalariales = 0;

            $scope.visibleGrales = false;
            $scope.visiblePresupuestariosEstructura = false;
            $scope.visibleCargosPresupuestarios = false;
            $scope.visibleEstadoCargo = false;
            $scope.visiblePersonales = false;
            $scope.visibleOcupacionales = false;
            $scope.visibleEstructura = false;
            $scope.visibleCptGral = false;
            $scope.visibleCptEE = false;
            $scope.visibleCptEf = false;
            $scope.visibleTramosSalariales = false;

            for (var idx in $scope.config.header) {
                var keyEx = $scope.config.header[idx].key;
                var visible = $scope.config.header[idx].visible;
                if((keyEx == 'anho' && visible) || (keyEx == 'mes' && visible) || (keyEx == 'nivelEntidad' && visible)
                    || (keyEx == 'entidad' && visible) || (keyEx == 'oee' && visible) || (keyEx == 'linea' && visible)
                    || (keyEx == 'objetoGasto' && visible) || (keyEx == 'fuenteFinanciamiento' && visible)){
                         colspanGrales++;
                } else if ((keyEx == 'programa' && visible) || (keyEx == 'subprograma' && visible) 
                    || (keyEx == 'dependencia' && visible)) {
                    colspanPresupuestariosEstructura++;
                } else if ((keyEx == 'categoria' && visible) || (keyEx == 'concepto' && visible) 
                    || (keyEx == 'presupuestado' && visible) || (keyEx == 'devengado' && visible)) {
                    colspanCargosPresupuestarios++;
                } else if ((keyEx == 'vinculacionFuncionario' && visible)) {
                    colspanEstadoCargo++;
                } else if ((keyEx == 'cedulaIdentidad' && visible) || (keyEx == 'apellido' && visible) 
                    || (keyEx == 'nombre' && visible)) {
                    colspanPersonales++;
                } else if ((keyEx == 'cargo' && visible) || (keyEx == 'funcionReal' && visible) 
                    || (keyEx == 'numeroPuestoTrabajo' && visible)) {
                    colspanOcupacionales++;
                } else if ((keyEx == 'codigoCeo' && visible) || (keyEx == 'denominacionCeo' && visible)
                    || (keyEx == 'nivelCuo' && visible) || (keyEx == 'denominacionCuo' && visible)) {
                    colspanEstructura++;
                } else if ((keyEx == 'nivelCpt' && visible) || (keyEx == 'subNivelCpt' && visible) || 
                    (keyEx == 'numeroCpt' && visible) || (keyEx == 'denominacionCpt' && visible) 
                    || (keyEx == 'titularUnidad' && visible)) {
                        colspanCptGral++;
                } else if ((keyEx == 'numeroSecuencialCptE' && visible) || (keyEx == 'ambitoCptE' && visible)
                    || (keyEx == 'nivelCptE' && visible) || (keyEx == 'categoriaCptE' && visible) 
                    || (keyEx == 'denominacionCptE' && visible)) {
                        colspanCptEE++;
                } else if ((keyEx == 'numeroSecuencialCptF' && visible) || (keyEx == 'ambitoCptF' && visible) 
                    || (keyEx == 'codigoProceso' && visible) || (keyEx == 'denominacionCptF' && visible) 
                    || (keyEx == 'orientacionFuncional' && visible)) {
                        colspanCptEf++;
                } else if ((keyEx == 'tramo' && visible) || (keyEx == 'minimo' && visible) 
                    || (keyEx == 'maximo' && visible)) {
                    colspanTramosSalariales++;
                 } 
            }
            if(colspanGrales > 0){
                $scope.visibleGrales = true
            }
            if(colspanPresupuestariosEstructura > 0){
                $scope.visiblePresupuestariosEstructura = true
            }
            if(colspanCargosPresupuestarios > 0){
                $scope.visibleCargosPresupuestarios = true
            }
            if(colspanEstadoCargo > 0){
                $scope.visibleEstadoCargo = true
            }
            if(colspanPersonales > 0){
                $scope.visiblePersonales = true
            }
            if(colspanOcupacionales > 0){
                $scope.visibleOcupacionales = true
            }
            if(colspanEstructura > 0){
                $scope.visibleEstructura = true
            }
            if(colspanCptGral > 0){
                $scope.visibleCptGral = true
            }
            if(colspanCptEE > 0){
                $scope.visibleCptEE = true
            }
            if(colspanCptEf > 0){
                $scope.visibleCptEf = true
            }
            if(colspanTramosSalariales > 0){
                $scope.visibleTramosSalariales = true
            }

            $scope.colspanGrales = colspanGrales;
            $scope.colspanPresupuestariosEstructura = colspanPresupuestariosEstructura;
            $scope.colspanCargosPresupuestarios = colspanCargosPresupuestarios;
            $scope.colspanEstadoCargo = colspanEstadoCargo;
            $scope.colspanPersonales = colspanPersonales;
            $scope.colspanOcupacionales = colspanOcupacionales;
            $scope.colspanEstructura = colspanEstructura;
            $scope.colspanCptGral = colspanCptGral;
            $scope.colspanCptEE = colspanCptEE;
            $scope.colspanCptEf = colspanCptEf;
            $scope.colspanTramosSalariales = colspanTramosSalariales;

            return true;
        }
    }
]);
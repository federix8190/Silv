/*jslint node: true */
/*jslint nomen: true */
/*global angular, _ */
"use strict";

app.controller('AnexoNoDistribuidoListCtrl', ['$scope', 'AnexoNoDistribuidoService', 'LegajoService', 'ProgramaService', '$controller',
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
        $scope.regimen = null;
        $scope.regimenSF = null;

        var header = [];
        if($scope.esHacienda){
            header = [{
                "key": "cedulaIdentidad",
                "name": "Cédula Identidad"
            }, {
                "key": "apellido",
                "name": "Apellidos"
            }, {
                "key": "nombre",
                "name": "Nombres"
            },          {
                "key": "anhoSF",
                "name": "Año SF",
            }, {
                "key": "mesSF",
                "name": "Mes SF"
            }, {
                "key": "nivelEntidadSF",
                "name": "Nivel Entidad SF"
            }, {
                "key": "entidadSF",
                "name": "Entidad SF"
            }, {
                "key": "oeeSF",
                "name": "OEE SF"
            }, {
                "key": "lineaSF",
                "name": "Línea SF"
            }, {
                "key": "objetoGastoSF",
                "name": "Objeto Gasto SF"
            }, {
                "key": "fuenteFinanciamientoSF",
                "name": "Fuente de Financiamiento SF"
            }, {
                "key": "programaSF",
                "name": "Programa SF"
            }, {
                "key": "subprogramaSF",
                "name": "Sub Programa SF"
            }, {
                "key": "dependenciaSF",
                "name": "Dependencia SF"
            }, {
                "key": "categoriaSF",
                "name": "Categoría SF"
            }, {
                "key": "conceptoSF",
                "name": "Concepto SF"
            }, {
                "key": "presupuestadoSF",
                "name": "Presupuestado SF"
            }, {
                "key": "devengadoSF",
                "name": "Devengado SF"
            }, {
                "key": "regimenSF",
                "name": "Régimen SF"
            }, {
                "key": "cargoSF",
                "name": "Cargo SF"
            }, {
                "key": "funcionRealSF",
                "name": "Función Real SF"
            }, {
                "key": "numeroPuestoTrabajoSF",
                "name": "Número Puesto Trabajo SF"
            }, {
                "key": "codigoCeoSF",
                "name": "Código CEO SF"
            }, {
                "key": "denominacionCeoSF",
                "name": "Denominación CEO SF"
            },  {
                "key": "nivelCuoSF",
                "name": "Código CUO SF"
            }, {
                "key": "denominacionCuoSF",
                "name": "Denominación CUO SF"
            }, {
                "key": "nivelCptSF",
                "name": "Nivel SF"
            }, {
                "key": "subNivelCptSF",
                "name": "Sub Nivel SF"
            }, {
                "key": "denominacionCptSF",
                "name": "Denominación SF"
            }, {
                "key": "titularUnidadSF",
                "name": "Titular Unidad SF"
            }, {
                "key": "numeroSecuencialCptF",
                "name": "Número Secuencial"
            }, {
                "key": "numeroSecuencialCptFSF",
                "name": "Número Secuencial SF"
            }, {
                "key": "codigoProcesoSF",
                "name": "Código Mecip SF"
            }, {
                "key": "denominacionCptFSF",
                "name": "Denominación SF"
            }, {
                "key": "orientacionFuncionalSF",
                "name": "Orientación Funcional SF"
            }, {
                "key": "numeroTramoSF",
                "name": "Tramo SF"
            }, {
                "key": "minimoSF",
                "name": "Monto Mínimo SF"
            }, {
                "key": "maximoSF",
                "name": "Monto Máximo SF"
            }, {
                "key": "anho",
                "name": "Año",
            },{
                "key": "mes",
                "name": "Mes"
            }, , {
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
                "name": "Sub Programa"
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
                "key": "regimen",
                "name": "Régimen"
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
                "key": "numeroCptSF",
                "name": "Número SF"
            }, {
                "key": "denominacionCpt",
                "name": "Denominación"
            }, {
                "key": "titularUnidad",
                "name": "Titular Unidad"
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
                "key": "numeroTramo",
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
                "key": "caso1",
                "name": "Caso 1",
            }, {
                "key": "caso2",
                "name": "Caso 2",
            }, {
                "key": "caso3",
                "name": "Caso 3",
            }, {
                "key": "caso4",
                "name": "Caso 4",
            }, {
                "key": "cedulaIdentidad",
                "name": "Cedula Identidad",
            }, {
                "key": "apellido",
                "name": "Apellidos"
            }, {
                "key": "nombre",
                "name": "Nombres"
            }, {
                "key": "fechaNacimiento",
                "name": "Fecha de Nacimiento"
            }, {
                "key": "sexo",
                "name": "Genero"
            }, {
                "key": "edad",
                "name": "Edad"
            }, {
                "key": "antiguedad",
                "name": "antiguedad"
            }, {
                "key": "anhoSF",
                "name": "Año"
            }, {
                "key": "mesSF",
                "name": "Mes"
            }, {
                "key": "lineaSF",
                "name": "Linea"
            }, {
                "key": "programaSF",
                "name": "Programa",
            }, {
                "key": "subprogramaSF",
                "name": "SubPrograma"            
            }, {
                "key": "dependenciaSF",
                "name": "Dependencia"
            }, {
                "key": "categoriaSalarialSF",
                "name": "Categoria"
            }, {
                "key": "conceptoSF",
                "name": "Concepto"
            }, {
                "key": "presupuestadoSF",
                "name": "Presupuestado"
            }, {
                "key": "devengadoSF",
                "name": "Devengado"
            }, {
                "key": "regimenSF",
                "name": "Vinculación"
            }, {
                "key": "cargoSF",
                "name": "Cargo"
        	}, {
                "key": "funcionRealSF",
                "name": "Función Real"
        	}, {
                "key": "numeroPuestoTrabajoSF",
                "name": "Número Puesto Trabajo"
        	}, {
                "key": "codigoCeoSF",
                "name": "Código CEO"
        	}, {
                "key": "denominacionCeoSF",
                "name": "Denominación CEO"        	
        	}, {
                "key": "nivelCptSF",
                "name": "Nivel"
        	}, {
                "key": "subNivelCptSF",
                "name": "Sub Nivel"
        	}, {
                "key": "numeroCptSF",
                "name": "Número"
        	}, {
                "key": "denominacionCptSF",
                "name": "Denominación"
        	}, {
                "key": "titularUnidadSF",
                "name": "Titular Unidad"
            }, {
                "key": "numeroSecuencialSF",
                "name": "Número Secuencial"
        	}, {
                "key": "ambitoSF",
                "name": "Ámbito"
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
                "key": "denominacion_cpt_ef",
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
            $scope.regimen = vinculacion;
        };

        $scope.updateVinculacionSF = function (vinculacion) {
            $scope.regimenSF = vinculacion;
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

        /** 
         * se encarga de recuperar la lista de vinculaciones.
         */
        function getVinculaciones() {
            LegajoService.getVinculaciones().then(
                function (response) {
                    if (response.data) {
                        $scope.listaVinculaciones = response.data;
                        $scope.regimen = $scope.listaVinculaciones[0];
                        $scope.regimenSF = $scope.listaVinculaciones[0]; 
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
            $scope.filterBy.mes = (now.getMonth() + 1) + "";
            $scope.filterBy.anho = now.getFullYear();
            $scope.filterBy.mesSF = (now.getMonth() + 1) + "";
            $scope.filterBy.anhoSF = now.getFullYear();


            var filters = {
                anhoSF : now.getFullYear(),
                mesSF : (now.getMonth() + 1) + "",
                anho : now.getFullYear(),
                mes : (now.getMonth() + 1) + ""
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

            var colspanGralesSF = 0;
            var colspanPresupuestariosEstructuraSF = 0;
            var colspanCargosPresupuestariosSF = 0;
            var colspanEstadoCargoSF = 0;
            var colspanOcupacionalesSF = 0;
            var colspanEstructuraSF = 0;
            var colspanCptGralSF = 0;
            var colspanCptEESF = 0;
            var colspanCptEfSF = 0;
            var colspanTramosSalarialesSF = 0;

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

            $scope.visibleGralesSF = false;
            $scope.visiblePresupuestariosEstructuraSF = false;
            $scope.visibleCargosPresupuestariosSF = false;
            $scope.visibleEstadoCargoSF = false;
            $scope.visibleOcupacionalesSF = false;
            $scope.visibleEstructuraSF = false;
            $scope.visibleCptGralSF = false;
            $scope.visibleCptEESF = false;
            $scope.visibleCptEfSF = false;
            $scope.visibleTramosSalarialesSF = false;


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
                } else if ((keyEx == 'regimen' && visible)) {
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
                } else if ((keyEx == 'numeroTramo' && visible) || (keyEx == 'minimo' && visible) 
                    || (keyEx == 'maximo' && visible)) {
                    colspanTramosSalariales++;
                 } else if((keyEx == 'anhoSF' && visible) || (keyEx == 'mesSF' && visible) || (keyEx == 'nivelEntidadSF' && visible)
                    || (keyEx == 'entidadSF' && visible) || (keyEx == 'oeeSF' && visible) || (keyEx == 'lineaSF' && visible)
                    || (keyEx == 'objetoGastoSF' && visible) || (keyEx == 'fuenteFinanciamientoSF' && visible)){
                         colspanGralesSF++;
                } else if ((keyEx == 'programaSF' && visible) || (keyEx == 'subprogramaSF' && visible) 
                    || (keyEx == 'dependenciaSF' && visible)) {
                    colspanPresupuestariosEstructuraSF++;
                } else if ((keyEx == 'categoriaSF' && visible) || (keyEx == 'conceptoSF' && visible) 
                    || (keyEx == 'presupuestadoSF' && visible) || (keyEx == 'devengadoSF' && visible)) {
                    colspanCargosPresupuestariosSF++;
                } else if ((keyEx == 'regimenSF' && visible)) {
                    colspanEstadoCargoSF++;
                } else if ((keyEx == 'cargoSF' && visible) || (keyEx == 'funcionRealSF' && visible) 
                    || (keyEx == 'numeroPuestoTrabajoSF' && visible)) {
                    colspanOcupacionalesSF++;
                } else if ((keyEx == 'codigoCeoSF' && visible) || (keyEx == 'denominacionCeoSF' && visible)
                    || (keyEx == 'nivelCuoSF' && visible) || (keyEx == 'denominacionCuoSF' && visible)) {
                    colspanEstructuraSF++;
                } else if ((keyEx == 'nivelCptSF' && visible) || (keyEx == 'subNivelCptSF' && visible) || 
                    (keyEx == 'numeroCptSF' && visible) || (keyEx == 'denominacionCptSF' && visible) 
                    || (keyEx == 'titularUnidadSF' && visible)) {
                        colspanCptGralSF++;
                } else if ((keyEx == 'numeroSecuencialCptESF' && visible) || (keyEx == 'ambitoCptESF' && visible)
                    || (keyEx == 'nivelCptESF' && visible) || (keyEx == 'categoriaCptESF' && visible) 
                    || (keyEx == 'denominacionCptESF' && visible)) {
                        colspanCptEESF++;
                } else if ((keyEx == 'numeroSecuencialCptFSF' && visible) || (keyEx == 'ambitoCptFSF' && visible) 
                    || (keyEx == 'codigoProcesoSF' && visible) || (keyEx == 'denominacionCptFSF' && visible) 
                    || (keyEx == 'orientacionFuncionalSF' && visible)) {
                        colspanCptEfSF++;
                } else if ((keyEx == 'numeroTramoSF' && visible) || (keyEx == 'minimoSF' && visible) 
                    || (keyEx == 'maximoSF' && visible)) {
                    colspanTramosSalarialesSF++;
                 } 
            }
            if(colspanGralesSF > 0){
                $scope.visibleGralesSF = true
            }
            if(colspanPresupuestariosEstructuraSF > 0){
                $scope.visiblePresupuestariosEstructuraSF = true
            }
            if(colspanCargosPresupuestariosSF > 0){
                $scope.visibleCargosPresupuestariosSF = true
            }
            if(colspanEstadoCargoSF > 0){
                $scope.visibleEstadoCargoSF = true
            }
            if(colspanPersonales > 0){
                $scope.visiblePersonales = true
            }
            if(colspanOcupacionalesSF > 0){
                $scope.visibleOcupacionalesSF = true
            }
            if(colspanEstructuraSF > 0){
                $scope.visibleEstructuraSF = true
            }
            if(colspanCptGralSF > 0){
                $scope.visibleCptGralSF = true
            }
            if(colspanCptEESF > 0){
                $scope.visibleCptEESF = true
            }
            if(colspanCptEfSF > 0){
                $scope.visibleCptEfSF = true
            }
            if(colspanTramosSalarialesSF > 0){
                $scope.visibleTramosSalarialesSF = true
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


            $scope.colspanGralesSF = colspanGralesSF;
            $scope.colspanPresupuestariosEstructuraSF = colspanPresupuestariosEstructuraSF;
            $scope.colspanCargosPresupuestariosSF = colspanCargosPresupuestariosSF;
            $scope.colspanEstadoCargoSF = colspanEstadoCargoSF;
            $scope.colspanPersonales = colspanPersonales;
            $scope.colspanOcupacionalesSF = colspanOcupacionalesSF;
            $scope.colspanEstructuraSF = colspanEstructuraSF;
            $scope.colspanCptGralSF = colspanCptGralSF;
            $scope.colspanCptEESF = colspanCptEESF;
            $scope.colspanCptEfSF = colspanCptEfSF;
            $scope.colspanTramosSalarialesSF = colspanTramosSalarialesSF;
            $scope.colspanGrales = colspanGrales;
            $scope.colspanPresupuestariosEstructura = colspanPresupuestariosEstructura;
            $scope.colspanCargosPresupuestarios = colspanCargosPresupuestarios;
            $scope.colspanEstadoCargo = colspanEstadoCargo;
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
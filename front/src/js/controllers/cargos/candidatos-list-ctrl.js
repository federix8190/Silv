"use strict";

app.controller('CandidatosCargosVacantesCtrl', ['$scope', 'CandidatosCargoService', 'LegajoService', 
        'CptService', 'CtsService', '$controller', '$location', '$timeout', '$rootScope',
    function ($scope, service, LegajoService, CptService, CtsService, $controller, $location, $timeout, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $scope.parametrosListado = {};
        $scope.listaTramos = [];
        $scope.departamento = null;
        $scope.distrito = null;
        $scope.vinculacionFuncionario = null;
        $scope.tramo = null;
        
        if ($rootScope.esHacienda) {
            var header = [{
                "key": "anho",
                "name": "Año",
            }, {
                "key": "mes",
                "name": "Mes",
            }, {
                "key": "cedulaIdentidad",
                "name": "CI"
            }, {
                "key": "nombre",
                "name": "Nombre"
            }, {
                "key": "apellido",
                "name": "Apellido"
            }, {
                "key": "vinculacionFuncionario",
                "name": "Vinculación"
            }, {
                "key": "cargo",
                "name": "Cargo"
            }, {
                "key": "antiguedadCargo",
                "name": "Antiguedad",
                "visible": false
            }, {
                "key": "numeroTramo",
                "name": "Tramo",
                "visible": false
            }, {
                "key": "nivel",
                "name": "Nivel CPT"
            }, {
                "key": "subNivelCpt",
                "name": "Subnivel CPT",
                "visible": false
            }, {
                "key": "numeroCpt",
                "name": "Número CPT",
                "visible": false
            }, {
                "key": "denominacionCpt",
                "name": "Denominación CPT",
                "visible": false
            }, {
                "key": "titularUnidad",
                "name": "Título Unidad",
                "visible": false
            }, {
                "key": "numeroSecuencialCptF",
                "name": "Número CPT EF",
                "visible": false
            }, {
                "key": "ambitoCptF",
                "name": "Ámbito CPT EF"
            }, {
                "key": "denominacionCpteF",
                "name": "Denominación CPT EF",
                "visible": false
            }, {
                "key": "orientacionFuncional",
                "name": "Orientación Funcional",
                "visible": false
            }, {
                "key": "denominacionCeo",
                "name": "CEO",
            }, {
                "key": "denominacionCuo",
                "name": "CUO",
            }, {
                "key": "programa",
                "name": "Programa",
            }, {
                "key": "subprograma",
                "name": "Subprograma",
            },{
                "key": "",
                "sortable": false,
                "name": "Acción"
            }];
        }else{
            var header = [{
                "key": "anho",
                "name": "Año",
            }, {
                "key": "mes",
                "name": "Mes",
            }, {
                "key": "cedulaIdentidad",
                "name": "CI"
            }, {
                "key": "nombre",
                "name": "Nombre"
            }, {
                "key": "apellido",
                "name": "Apellido"
            }, {
                "key": "vinculacionFuncionario",
                "name": "Vinculación"
            }, {
                "key": "nombreDepartamento",
                "name": "Circunscripción",
                "visible": true
            }, {
                "key": "nombreDistrito",
                "name": "Distrito",
                "visible": true
            }, {
                "key": "cargo",
                "name": "Cargo"
            }, {
                "key": "antiguedadCargo",
                "name": "Antiguedad",
                "visible": false
            }, {
                "key": "titulo",
                "name": "Título"
            }, {
                "key": "numeroTramo",
                "name": "Tramo",
                "visible": false
            }, {
                "key": "nivel",
                "name": "Nivel CPT"
            }, {
                "key": "subNivelCpt",
                "name": "Subnivel CPT",
                "visible": false
            }, {
                "key": "numeroCpt",
                "name": "Número CPT",
                "visible": false
            }, {
                "key": "denominacionCpt",
                "name": "Denominación CPT",
                "visible": false
            }, {
                "key": "titularUnidad",
                "name": "Título Unidad",
                "visible": false
            }, {
                "key": "numeroSecuencialCptE",
                "name": "Número CPT EE",
                "visible": false
            }, {
                "key": "ambitoCptE",
                "name": "Ámbito CPT EE",
                "visible": false
            }, {
                "key": "nivelCptE",
                "name": "Nivel CPT EE",
                "visible": false
            }, {
                "key": "denominacionCptE",
                "name": "Denominación CPT EE",
                "visible": false
            }, {
                "key": "numeroSecuencialCptF",
                "name": "Número CPT EF",
                "visible": false
            }, {
                "key": "ambitoCptF",
                "name": "Ámbito CPT EF"
            }, {
                "key": "denominacionCpteF",
                "name": "Denominación CPT EF",
                "visible": false
            }, {
                "key": "orientacionFuncional",
                "name": "Orientación Funcional",
                "visible": false
            },{
                "key": "",
                "sortable": false,
                "name": "Acción"
            }];
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
         * Se encarga de transformar un lista simple a una lista
         * de objetos
         * @param {*} key el nombre del atributo
         * @param {*} attr 
         * @param {*} list 
         */
        function arrayToObject(key, attr, list) {
            $scope[key] = [];
            for (var idx in list) {
                var el = {};
                el[attr] = list[idx];
                $scope[key].push(el);
            }
        }

        /** 
         * Recupera la lista de vinculaciones para poblar el combo de vinculaciones.
         */
        function getVinculaciones() {
            LegajoService.getVinculaciones().then(
                function (response) {
                    $scope.listaVinculaciones = response.data;
                }, function () {
                    Message.error("Ocurrió un error al recuperar la lista de vinculaciones.");
                }
            );
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

        function getCpt() {
            CptService.listarTodos().then(
                function (response) {
                    if (response.data) {
                        $scope.listaCpt = response.data.rows;
                    } else {
                        console.error('Error listar cargos : ' + response.result);
                    }
                }
            );
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
            $scope.footer = true;
            initFiltros();
            console.log($location.search().idCptEf);
            $scope.filterBy.numeroTramo = $location.search().numeroTramo;
            $scope.filterBy.idCpt = $location.search().idCpt;
            $scope.filterBy.idCptEf = $location.search().idCptEf;
            $scope.filterBy.idCptEe = $location.search().idCptEe;
            $scope.filterBy.anho = $location.search().anho;
            $scope.filterBy.mes = $location.search().mes;
            $scope.idCpt = $scope.filterBy.idCpt;
            $scope.idCptEe = $scope.filterBy.idCptEe;
            $scope.idCptEf = $scope.filterBy.idCptEf;
            //se cargan los combos
            getDepartamentos();
            getVinculaciones();
            getAmbitos();
            getOrientacionFunc();
            //getCpt();
        })();

        /** 
         * Se encarga de inicializar los fitros
         */
        function initFiltros() {
            
            var now = new Date();

            var filters = {
                vinculacionFuncionario: "TODOS",
                anho : now.getFullYear(),
                mes : (now.getMonth()) + ""
            };
            $scope.initFilters(filters);

            $scope.filterBy = {
                anho : now.getFullYear(),
                mes : (now.getMonth()) + "",
                titularUnidad: "TODOS",
                vinculacionFuncionario: "TODOS",
            };

            $scope.filtros = {
                cpt: "true",
                cptee: "true",
                cptef: "true"
            };
        }

        /** 
         * Se encarga de limpiar los criterios de busqueda y recarga la grilla.
         */
        $scope.limpiar = function () {
            initFiltros();
        };

        $scope.onChangeFiltroCpt = function () {
            if ($scope.filtros.cpt == 'true') {
                $scope.filterBy.idCpt = $scope.idCpt;
            } else {
                $scope.filterBy.idCpt = null;
            }
        };

        $scope.onChangeFiltroCptE = function () {
            if ($scope.filtros.cptee == 'true') {
                $scope.filterBy.idCptEe = $scope.idCptEe;
            } else {
                $scope.filterBy.idCptEe = null;
            }
        };

        $scope.onChangeFiltroCptF = function () {
            if ($scope.filtros.cptef == 'true') {
                $scope.filterBy.idCptEf = $scope.idCptEf;
            } else {
                $scope.filterBy.idCptEf = null;
            }
        };

        /**
         * Se encarga de actualizar la vinculación del funcionario.
         * @param {*} vinculacion 
         */
        $scope.updateVinculacion = function (vinculacion) {
            $scope.vinculacionFuncionario = vinculacion;
            $scope.filterBy.vinculacionFuncionario = vinculacion;
        };

        /** 
         * Handler del change del combo de departamentos
         */
        $scope.onChangeDepartamento = function (d) {
            $scope.filterBy.nombreDepartamento = d.nombre;
            LegajoService.getDistritos(d.codigo).then(function (response) {
                $scope.listaDistritos = response.data;
            }, function () {
                Message.error("No se pudo recuperar la lista de distritos");
            });
        };

        /** 
         * Handler del change del combo de distritos.
         */
        $scope.onChangeDistrito = function (d) {
            $scope.filterBy.nombreDistrito = d.nombre;
        };

    }
]);
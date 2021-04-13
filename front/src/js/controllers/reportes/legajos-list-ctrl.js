"use strict";

app.controller('LegajoListCtrl', ['$scope', 'LegajoService', 'CptService', 'CtsService', 
        '$controller', '$location', '$timeout', '$rootScope',
    function ($scope, service, CptService, CtsService, $controller, $location, $timeout, $rootScope) {

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
        $scope.vinculacion = null;
        $scope.tramo = null;
        $scope.listadoNormal = true;

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
            }, /*{
                "key": "antiguedadCargo",
                "name": "Antiguedad",
                "visible": false
            }, {
                "key": "titulo",
                "name": "Título",
                "visible": false
            },*/ {
                "key": "numeroTramo",
                "name": "Tramo"
            }, {
                "key": "nivel",
                "name": "Nivel CPT"
            }, {
                "key": "subNivelCpt",
                "name": "Subnivel CPT"
            }, {
                "key": "numeroCpt",
                "name": "Número CPT"
            }, {
                "key": "denominacionCpt",
                "name": "Denominacion CPT"
            }, {
                "key": "titularUnidad",
                "name": "Titular Unidad",
                "visible": false
            },{
                "key": "numeroSecuencialCptF",
                "name": "Número Secuencial CPT EF",
                "visible": false
            }, {
                "key": "ambitoCptF",
                "name": "Ámbito CPT EF",
                "visible": false
            }, {
                "key": "denominacionCptF",
                "name": "Denominación CPT EF"
            }, {
                "key": "codigoMecip",
                "name": "Código Mecip"
            }, {
                "key": "orientacionFuncional",
                "name": "Orientacion Funcional",
                "visible": false
            }, {
                "key": "codigoCeo",
                "name": "Código CEO",
                "visible": true
            }, {
                "key": "denominacionCeo",
                "name": "Denominación CEO",
                "visible": true
            }, {
                "key": "nivelCuo",
                "name": "Nivel CUO",
                "visible": true
            }, {
                "key": "subNivelCuo",
                "name": "Subnivel CUO",
                "visible": true
            }, {
                "key": "numeroCuo",
                "name": "Número CUO",
                "visible": true
            }, {
                "key": "denominacionCuo",
                "name": "Denominación CUO",
                "visible": true
            }, {
                "key": "",
                "sortable": false,
                "name": "Acción"
            }];

        } else {

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
                "visible": false
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
                "name": "Título",
                "visible": false
            }, {
                "key": "numeroTramo",
                "name": "Tramo"
            }, {
                "key": "nivel",
                "name": "Nivel CPT"
            }, {
                "key": "subNivelCpt",
                "name": "Subnivel CPT"
            }, {
                "key": "numeroCpt",
                "name": "Número CPT"
            }, {
                "key": "denominacionCpt",
                "name": "Denominacion CPT"
            }, {
                "key": "titularUnidad",
                "name": "Titular Unidad",
                "visible": false
            }, {
                "key": "numeroSecuencialCptE",
                "name": "Número Secuencial CPT EE ",
                "visible": false
            }, {
                "key": "ambitoCptE",
                "name": "Ambito CPT EE",
                "visible": false
            }, {
                "key": "nivelCptE",
                "name": "Nivel CPT EE",
                "visible": false
            }, {
                "key": "denominacionCptE",
                "name": "Denominación CPT EE"
            }, {
                "key": "numeroSecuencialCptF",
                "name": "Número Secuencial CPT EF",
                "visible": false
            }, {
                "key": "ambitoCptF",
                "name": "Ámbito CPT EF",
                "visible": false
            }, {
                "key": "denominacionCptF",
                "name": "Denominación CPT EF"
            }, {
                "key": "codigoMecip",
                "name": "Código Mecip"
            }, {
                "key": "orientacionFuncional",
                "name": "Orientacion Funcional",
                "visible": false
            }, {
                "key": "codigoCeo",
                "name": "Código CEO",
                "visible": false
            }, {
                "key": "denominacionCeo",
                "name": "Denominación CEO",
                "visible": false
            }, {
                "key": "nivelCuo",
                "name": "Nivel CUO",
                "visible": false
            }, {
                "key": "subNivelCuo",
                "name": "Subnivel CUO",
                "visible": false
            }, {
                "key": "numeroCuo",
                "name": "Número CUO",
                "visible": false
            }, {
                "key": "denominacionCuo",
                "name": "Denominación CUO",
                "visible": false
            }, {
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
            service.getVinculaciones().then(
                function (response) {
                    $scope.listaVinculaciones = response.data;
                },
                function () {
                    Message.error("Ocurrió un error al recuperar la lista de vinculaciones.");
                }
            );
        }

        function getAmbitos() {
            service.getAmbitos().then(
                function (response) {
                    $scope.listaAmbitos = response.data;
                },
                function () {
                    Message.error("Ocurrió un error al recuperar la lista de ámbitos.");
                }
            );
        }

        function getOrientacionFunc() {
            service.getOrientacionFunc().then(
                function (response) {
                    $scope.listaOrientacionFunc = response.data;
                },
                function () {
                    Message.error("Ocurrió un error al recuperar la lista de orientaciones funcionales.");
                }
            );
        }

        /** 
         * Recupera la lista de departamentos para poblar el combo de departamentos.
         */
        function getDepartamentos() {
            service.getDepartamentos().then(
                function (response) {
                    $scope.listaDepartamentos = response.data;
                    $scope.departamento = $scope.listaDepartamentos[$scope.listaDepartamentos.length - 1];
                }, function () {
                    Message.error("Ocurrió un error al recuperar la lista de departamentos.");
                }
            );
        }

        /** 
         * Recupera la lista de niveles para poblar el combo de niveles cpt.
         */
        function getNivelesCPT(nivel) {
            $scope.listaCpt = [];
            CptService.getNiveles().then(
                function (response) {
                    arrayToObject("listaCpt", "nivel", response.data);
                    for (var i in $scope.listaCpt) {
                        if ($scope.listaCpt[i].nivel == nivel) {
                            $scope.filterBy.cpt = {
                                nivel: nivel
                            };
                        }
                    }
                },
                function () {
                    Message.error("Ocurrió un error al recuperar la lista de niveles CPT.");
                }
            );
        }

        /** 
         * Recupera la lista de tramos salariales para poblar el combo de tramos.
         */
        function getTramosSalariales(numeroTramo) {
            CtsService.getTramos().then(
                function (response) {
                    arrayToObject("listaTramos", "numeroTramo", response.data);
                    for (var i in $scope.listaTramos) {
                        if ($scope.listaTramos[i].numeroTramo == numeroTramo) {
                            $scope.filterBy.tramo = {
                                numeroTramo: numeroTramo
                            };
                        }
                    }
                },
                function () {
                    Message.error("Ocurrió un error al recuperar la lista de tramos.");
                }
            );
        }

        /*$rootScope.$on('$routeChangeSuccess', function (e, currentRoute, previousRoute) {
            if (previousRoute.$$route !== undefined) {
                console.log('previousRoute : ', previousRoute.$$route.templateUrl );
            }
        });*/

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
            
            console.log('Anho : ', $location.search().anho);
            if ($location.search().anho !== undefined) {
                $scope.listadoNormal = false;
                $scope.filterBy.nivel = $location.search().nivel;
                $scope.filterBy.numeroTramo = $location.search().numeroTramo;
                $scope.filterBy.anho = $location.search().anho;
                $scope.filterBy.mes = $location.search().mes;
                $scope.filterBy.matriz = $location.search().matriz;
            }

            initFiltros();
            //se cargan los combos
            getDepartamentos();

            
            getNivelesCPT($location.search().nivel);
            getTramosSalariales($location.search().numeroTramo);
            getVinculaciones();
            getAmbitos();
            getOrientacionFunc();

        })();

        /** 
         * Se encarga de inicializar los fitros
         */
        function initFiltros() {
            
            var now = new Date();
            var mesActual = now.getMonth();
            var mes = (now.getMonth()) + "";
            var anho = now.getFullYear();

            if (mesActual == 0) {
                mes = "12";
                anho = now.getFullYear() - 1;
            }

            var filters = {
                vinculacionFuncionario: "PERMANENTE",
                numeroTramo: "TODOS",
                nivel: "TODOS",
                anho : anho,
                mes : mes
            };
            $scope.initFilters(filters);

            if ($scope.listadoNormal) {
                $scope.filterBy = {
                    anho : anho,
                    mes : mes,
                    vinculacionFuncionario: "TODOS",
                    titularUnidad: "TODOS"
                };
            }

            $scope.filterBy.tramo = {
                numeroTramo: "TODOS"
            };

            $scope.filterBy.cpt = {
                nivel: "TODOS"
            };
        }


        /** 
         * Se encarga de limpiar los criterios de busqueda y recarga la grilla.
         */
        $scope.limpiar = function () {
            initFiltros();
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
         * Para ordenar los niveles de cpt (1-10). La opcion 'TODOS' va primero, por eso se retorna 0.
         */
        $scope.sorterFunc = function (cpt) {
            if (cpt == 'TODOS') {
                return 0;
            }
            return parseInt(cpt);
        };

        /** 
         * Handler del change del combo de departamentos
         */
        $scope.onChangeDepartamento = function (d) {
            $scope.filterBy.nombreDepartamento = d.nombre;
            service.getDistritos(d.codigo).then(function (response) {
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

        /**
         *  Handler del change del combo de niveles
         */
        $scope.onChangeCpt = function () {
            $scope.filterBy.nivel = $scope.filterBy.cpt.nivel;
        };

        /**
         *  Handler del change del combo de tramos
         */
        $scope.onChangeTramo = function () {
            $scope.filterBy.numeroTramo = $scope.filterBy.tramo.numeroTramo;

        };
    }
]);
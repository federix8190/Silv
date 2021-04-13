/**
 * @class
 * Controller que implementa la asignacion masiva de legajos.
 *
 * @name angular-keycloak-seed.controller##AsignacionCeoCtrl
 * @author <a href = "mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.controller('AsignacionCeoCtrl', ['$scope', 'CeoService', 'CtsService', 'CptService', 
            'LegajoService', '$controller', '$route', '$rootScope',
    function ($scope, service, CtsService, CptService, LegajoService, $controller, $route, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
		$scope.ceo = {};
        $scope.parametrosListado = {};
        $scope.cargarListaLegajos = false;

        /**
         * Configuraciones de la cabecera de la grilla.
         * @private
         * @type {Array}
         */
        if ($rootScope.esHacienda) {

            var header = [{
                    "key": "",
                    "name": ""
                }, {
                    "key": "anho",
                    "name": "Año"
                }, {
                    "key": "mes",
                    "name": "Mes"
                }, {
                    "key": "cedulaIdentidad",
                    "name": "CI"
                }, {
                    "key": "apellido",
                    "name": "Apellido"
                }, {
                    "key": "nombre",
                    "name": "Nombre"
                }, {
                    "key": "ceoActual",
                    "name": "CEO (Actual)"
                }, {
                    "key": "cargo",
                    "name": "Cargo"
                }, {
                    "key": "nombreDepartamento",
                    "name": "Circunscripción",
                    "visible": false
                }, {
                    "key": "nombreDistrito",
                    "name": "Distrito",
                    "visible": false
                }
            ];

        } else {

            var header = [{
                    "key": "",
                    "name": ""
                }, {
                    "key": "anho",
                    "name": "Año"
                }, {
                    "key": "mes",
                    "name": "Mes"
                }, {
                    "key": "cedulaIdentidad",
                    "name": "CI"
                }, {
                    "key": "apellido",
                    "name": "Apellido"
                }, {
                    "key": "nombre",
                    "name": "Nombre"
                }, {
                    "key": "ceoActual",
                    "name": "CEO (Actual)"
                }, {
                    "key": "cargo",
                    "name": "Cargo"
                }, {
                    "key": "nombreDepartamento",
                    "name": "Circunscripción",
                    "visible": false
                }, {
                    "key": "nombreDistrito",
                    "name": "Distrito",
                    "visible": false
                }
            ];
        }

        /**
         * Se encarga de recuperar la lista de vinculaciones para poblar el combo
         * de vinculaciones.
         */
        function getVinculaciones() {
            LegajoService.getVinculaciones().then(
                function (response) {
                    if (response.data) {
                        $scope.listaVinculaciones = response.data;
                    }
                },
                function (response) {
                    Message.error("Ocurrió un errror al recuperar la lista de vinculaciones.");
                }
            );
        }
        /** 
         * Se encarga de recuperar la lista de niveles CPT disponibles para poblar el combo 
         * de niveles.
         */
        function getNivelesCPT() {
            CptService.getNiveles().then(
                function (response) {
                    if (response.data) {
                        $scope.listaCpt = response.data;
                        // Para que por defecto se seleccione la opcion 'TODOS', se elige el utlimo de la lista de Niveles
                        $scope.cpt = $scope.listaCpt[$scope.listaCpt.length - 1];
                        $scope.cptSeleccionado = $scope.cpt;
                    } else {
                        console.error('Error listar cpts : ' + response.result);
                    }
                },
                function (response) {
                    Message.error("No se pudo recuperar la lista de niveles");
                }
            );

        }

        function aplicarFiltros(e) {
            if(e==undefined || e.keyCode==13){
                $scope.parametrosListado.filters = $scope.filtros;
                $scope.parametrosListado.page = 1;
                $scope.getResource(null, $scope.parametrosListado);
            }
        }

        function initFilters() {
            var now = new Date();
            $scope.filterBy = {
                //activo: true,
                tramo: "TODOS",
                nivel: "TODOS",
                vinculacionFuncionario: "TODOS",
                anho : now.getFullYear(),
                mes : (now.getMonth()) + ""
            }
            //$scope.initFilters($scope.filtros);
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
            getVinculaciones();
            getNivelesCPT();
                        
            initFilters(); 
            $scope.config.header = header;
            $scope.footer = false;
			/*service.listarTodos().then(
				function(response) {
					if (response.data) {
						$scope.listaCeo = response.data.rows;
						$scope.ceo = $scope.listaCeo[0];
						//aplicarFiltros();
						$scope.getResource(null, $scope.parametrosListado);
					}
				}
			);*/
        })();

        $scope.onChangeCeo = function(ceo) {
			$scope.ceo = ceo;
            $scope.getListaLegajos(null, $scope.paramsObj);
        };

        /**
         * Override de la función limpiar base
         * Se encarga de limpiar los criterios del filtrado.
         * @function
         */
        $scope.limpiar = function () {
            initFilters();
        };
			
	    $scope.getResource = function (params, paramsObj) {
            if (!$scope.cargarListaLegajos) {
                return $scope.getListaCeo(params, paramsObj);
            } else {
                return $scope.getListaLegajos(params, paramsObj);
            }
        };

        $scope.getListaLegajos = function (params, paramsObj) {
            $scope.initHeader($scope.config.header);
            paramsObj.sortOrder = paramsObj.sortOrder == 'dsc' ? "DESC" : "ASC";
            $scope.loading = true;
            $scope.config.pagination.page = paramsObj.page == 0 ? $scope.init.page : paramsObj.page;
            $scope.config.pagination.count = paramsObj.count == 0 ? $scope.init.count : paramsObj.count;
            if (paramsObj.filters) {
                $scope.deleteUndefinedValues(paramsObj.filters);
            }
            paramsObj.id = $scope.ceo.id;
            $scope.paramsObj = paramsObj;
            return $scope.service.listarLegajos(paramsObj)
                .then(function (response) {
                    $scope.configurarGrilla(response);
                    return $scope.config;
                }, function (response) {
                    $scope.noData();
                    $scope.config.rows = [];
                    return $scope.config;
                });
        };

        $scope.getListaCeo = function (params, paramsObj) {

            paramsObj.id = "-1";
            return $scope.service.listarLegajos(paramsObj)
                .then(function (response) {
                    $scope.configurarGrilla(response);
                    $scope.service.listarTodos().then(
                        function(response) {
                            $scope.listaCeo = response.data.rows;
                            $scope.ceo = $scope.listaCeo[0];
                            $scope.cargarListaLegajos = true;
                            $scope.buscar();
                        }
                    );
                    return $scope.config;
                }, function (response) {
                    $scope.noData();
                    $scope.config.rows = [];
                    return $scope.config;
                });
        };

        $scope.configurarGrilla = function(response) {
            $scope.loading = false;
            $scope.config.rows = response.data.rows;
            $scope.config.pagination.size = response.data.count;
            $scope.config.pagination.pages = Math.ceil(response.data.count / $scope.config.pagination.count);
            if (response.data.count == 0) {
                $scope.noData();
                $scope.config.rows = [{}];
            }
        }
			
        /**
         * Sobrescribe el método que se encarga de aplicar los criterios de filtrado.
         * @param {*} e 
         */
        /*$scope.aplicarFiltros = function (e) {
                if(e==undefined || e.keyCode==13){
                    $scope.parametrosListado.filters = $scope.filtros;
                    $scope.parametrosListado.page = 1;
                    $scope.getResource(null, $scope.parametrosListado);
                }
        };*/
		
		/**
		 * Para ordenar los niveles de cpt (1-10). La opcion 'TODOS' va primero, por eso se retorna 0.
		 */
		$scope.sorterFunc = function(cpt) {
			if (cpt == 'TODOS') {
				return 0;
			}
            return parseInt(cpt);
        };
		
		/**
		 * Agrega el prefijo para cada nivel de cpt (A excepcion de la opcion 'TODOS')
		 */
		$scope.getPrefijoNivel = function(cpt) {
			if (cpt == 'TODOS') {
				return '';
			}
			return 'Nivel ';
		};
		
		/*$scope.onChangeNivelCpt = function(nivel) {
            //$scope.nivel = nivel;
        };
		
		$scope.onChangeVinculacion = function(vinculacion) {
            $scope.vinculacion = vinculacion;
        };*/
		
		$scope.asignarLegajo = function(row) {
            var legajo = {};
            legajo.cedulaIdentidad = row.id.cedulaIdentidad;
            legajo.asignado = row.asignado;
            legajo.anho = row.id.anho;
            legajo.mes = row.id.mes;
            service.asignarLegajo($scope.ceo.id, legajo).then(
                function(response) {
                    if (response.status != 200) {
                        if (row.asignado == true) {
							Message.error('Ocurrió un error al asignar el Legajo');
						} else {
							Message.error('Ocurrió un error al desasignar el Legajo');
						}
                    } else {
						if (legajo.asignado == true) {
                            row.orden = 2;
                            row.ceoActual = $scope.ceo.denominacion;
                        } else {
                            row.orden = 1;
                            row.ceoActual = '';
                        }
					}
                }
            );
        };

        CtsService.getTramos().then(
            function (response) {
                if (response.data) {
                    $scope.listaTramos = response.data;
                    if ($scope.numeroTramo != undefined) {
                        if ($scope.listaTramos.indexOf('0') != -1) {
                            $scope.tramo = $scope.listaTramos[$scope.numeroTramo];
                        } else {
                            $scope.tramo = $scope.listaTramos[$scope.numeroTramo - 1];
                        }
                    } else {
                        $scope.tramo = $scope.listaTramos[$scope.listaTramos.length - 1];
                    }
                } else {
                    console.error('Error listar cts : ' + response.result);
                }
            }
        );
        
    }
]);
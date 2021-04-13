/**
 * @class
 * Controller que implementa la asignacion masiva de legajos.
 *
 * @name angular-keycloak-seed.controller##AsignacionCptEFCtrl
 * @author <a href = "mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.controller('AsignacionCptCtrl', ['$scope', 'CptService', 'CtsService', 'LegajoService', 
            '$controller', '$route', '$rootScope',
    function ($scope, service, CtsService, LegajoService, $controller, $route, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
		$scope.cpt = {};
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
                    "key": "cptActual",
                    "name": "CPT (Actual)"
                }, {
                    "key": "cargo",
                    "name": "Cargo"
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
                    "key": "cptActual",
                    "name": "CPT (Actual)"
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

        function initFilters() {
            var now = new Date();
            $scope.filterBy = {
                numeroTramo: "TODOS",
                vinculacionFuncionario: "TODOS",
                anho : now.getFullYear(),
                mes : (now.getMonth()) + ""
            }
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
            
            initFilters();
            $scope.config.header = header;
            $scope.footer = false;
			
			LegajoService.getVinculaciones().then(
                function(response) {
                    if (response.data) {
                        $scope.listaVinculaciones = response.data;
                    }
                }
            );
			
			getTramos();
            getDepartamentos();
			
        })();

        $scope.onChangeCpt = function(cpt) {
            $scope.cpt = cpt;
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
                return $scope.getListaCpt(params, paramsObj);
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
            paramsObj.id = $scope.cpt.id;
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

        $scope.getListaCpt = function (params, paramsObj) {

            paramsObj.id = "-1";
            return $scope.service.listarLegajos(paramsObj)
                .then(function (response) {
                    $scope.configurarGrilla(response);
                    $scope.service.listarTodos().then(
                        function(response) {
                            $scope.listaCpt = response.data.rows;
                            $scope.cpt = $scope.listaCpt[0];
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
         * Recupera la lista de tramos.
         */
        function getTramos() {
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
		
		$scope.asignarLegajo = function(row) {
            var legajo = {};
            legajo.cedulaIdentidad = row.id.cedulaIdentidad;
            legajo.asignado = row.asignado;
            legajo.anho = row.id.anho;
            legajo.mes = row.id.mes;
            service.asignarLegajo($scope.cpt.id, legajo).then(
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
                            row.denominacionCpt = $scope.cpt.denominacion;
                        } else {
                            row.orden = 1;
                            row.denominacionCpt = '';
                        }
					}
                }
            );
        };
        
    }]);

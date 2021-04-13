/**
 * @class
 * Controller que implementa el formulario administración de plantillas
 *
 * @name angular-keycloak-seed.controller##LegajoViewCtrl
 * @author <a href = "mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.controller('LegajoViewCtrl', ['$scope', 'LegajoService', '$controller', '$rootScope',
                'CptEEService', 'CptEFService', '$timeout',
    function ($scope, service, $controller, $rootScope,
              cptEEService, cptEFService, $timeout) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

        $scope.loading = true;
        $scope.loadingCptF = true;

        $scope.reload = function () {
        };
        $scope.reloadCptF = function () {
        };

        $scope.formacionAcademica = [];
        $scope.recorridoLaboral = [];
        $scope.sancionPersonal = [];
        $scope.sumarioPersonal = [];
        $scope.estudiosConcluidos = [];
        $scope.otrosEstudios = [];
        $scope.otrosCursos = [];
        $scope.cursoInformatica = [];
        $scope.idiomas = [];
        $scope.multas = [];
        $scope.multasControl = [];
        $scope.diasNoTrabajados = [];
        $scope.experienciaLaboral = [];
        $scope.eventos = [];
        $scope.apercibimientos = [];
        $scope.suspensiones = [];
        $scope.sumarios = [];
        $scope.destitucion = [];
        $scope.sobreseimiento = [];

        $scope.formacionAcademicaHacienda = [];
        $scope.carreraAdministrativa = [];

        /**
         * Inicializacion de objeto
         */
        $scope.filterBy = {};
        $scope.filterByCptF = {};

        $scope.$watch('recurso.idCptEe', function (newVal, oldVal) {
            console.log('watch para CptEE : ', newVal, ' ', oldVal);
            if (newVal) {
                $scope.loading = true;
                $scope.reload();
            }else{
                $scope.loading = null;
            }
        });

        $scope.$watch('recurso.idCptEf', function (newVal, oldVal) {
            console.log('watch para CptEF : ', newVal, ' ', oldVal);
            if (newVal) {
                $scope.loadingCptF = true;
                $scope.reloadCptF();
            }else{
                $scope.loadingCptF = null;
            }
        });

        /**
         * @field
         * Parametros de configuración de la grilla
         */
        $scope.init = {
            'count': 20,
            'page': 1,
            'sortBy': "id",
            'sortOrder': 'DESC',
            'filterBase': 1
        };
        /**
         * Array que contiene los datos
         * @type Array
         * @field
         */
        $scope.config = {
            "header": [
                {
                    "name": "Denominación",
                    "key": "denominacion"
                },
                {
                    "name": "Número",
                    "key": "numero"
                },
                {
                    "name": "Ámbito",
                    "key": "ambito"
                },
                {
                    "name": "Nivel",
                    "key": "nivel"
                },
                {
                    "name": "Categoría",
                    "key": "categoria"
                },
                {
                    "name": "CPT General",
                    "key": "cpt"
                },
                {
                    "name": "Acción",
                    "key": ""
                }
            ],
            "rows": [],
            "pagination": {
                "count": $scope.init.count,
                "page": $scope.init.page,
                "pages": 0,
                "size": 0
            },
            "ssortBy": $scope.init.sortBy,
            "sortOrder": $scope.init.sortOrder,
            "recurso": 'cpt-ee'
        };

        /**
         * Array que contiene los datos
         * @type Array
         * @field
         */
        $scope.configCptF = {
            "header": [
                {
                    "name": "Denominación",
                    "key": "den"
                },
                {
                    "name": "Número",
                    "key": "nro"
                },
                {
                    "name": "Ámbito",
                    "key": "ambito"
                },
                {
                    "name": "Cod. Proceso",
                    "key": "codProceso"
                },
                {
                    "name": "Orientación Func.",
                    "key": "orientacionFunc"
                },
                {
                    "name": "CPT General",
                    "key": "cpt"
                },
                {
                    "name": "Acción",
                    "key": ""
                }
            ],
            "rows": [],
            "pagination": {
                "count": $scope.init.count,
                "page": $scope.init.page,
                "pages": 0,
                "size": 0
            },
            "ssortBy": $scope.init.sortBy,
            "sortOrder": $scope.init.sortOrder,
            "recurso": 'cpt-ee'
        };

        /**
         * Se encarga de obtener las audiencias asociadas.
         */
        $scope.setearCptE = function (params, paramsObj) {
            console.log('setearCptE');
            paramsObj.sortOrder = paramsObj.sortOrder == 'dsc' ? "DESC" : "ASC";
            $scope.loading = true;
            $scope.config.pagination.page = paramsObj.page;
            $scope.config.pagination.count = paramsObj.count;
            //paramsObj.filters = {
              //  id: $scope.recurso.idCptEe.id.toString()
            //};
            if (paramsObj.filters) {
                $scope.deleteUndefinedValues(paramsObj.filters);
            }
            return cptEEService.listar(paramsObj)
                .then(function (response) {
                    $scope.loading = false;
                    $scope.config.rows = response.data.rows;
                    $scope.config.pagination.size = response.data.count;
                    $scope.config.pagination.pages = Math.ceil(response.data.count / $scope.config.pagination.count);
                    return $scope.config;
                }, function (response) {
                    $scope.reload();
                    $scope.loading = null;
                    $scope.config.rows = [];
                    $scope.config.pagination.size = 0;
                    $scope.config.pagination.pages = 0;
                    return $scope.config;
                });
        };
        /**
         * Se encarga de obtener las audiencias asociadas.
         */
        $scope.setearCptF = function (params, paramsObj) {
            console.log('setearCptF');
            paramsObj.sortOrder = paramsObj.sortOrder == 'dsc' ? "DESC" : "ASC";
            $scope.loadingCptF = true;
            $scope.configCptF.pagination.page = paramsObj.page;
            $scope.configCptF.pagination.count = paramsObj.count;
            if (paramsObj.filters) {
                $scope.deleteUndefinedValues(paramsObj.filters);
            }
            return cptEFService.listar(paramsObj)
                .then(function (response) {
                    $scope.loadingCptF = false;
                    $scope.configCptF.rows = response.data.rows;
                    $scope.configCptF.pagination.size = response.data.count;
                    $scope.configCptF.pagination.pages = Math.ceil(response.data.count / $scope.configCptF.pagination.count);
                    return $scope.configCptF;
                }, function (response) {
                    $scope.loadingCptF = null;
                    $scope.configCptF.rows = [];
                    $scope.configCptF.pagination.size = 0;
                    $scope.configCptF.pagination.pages = 0;
                    return $scope.configCptF;
                });
        };
        /**
         * Elimina los elementos del objeto que son nulos
         * @function
         */
        $scope.deleteUndefinedValues = function (object) {
            for (var key in object) {
                if (!object[key]) {
                    delete object[key];
                }
            }
        };

        function cargarCptF() {
            cptEFService.listar().then(
                function(response) {
                    if (response.data) {
                        $scope.listaCptF = response.data.rows;
                    } else {
                        console.error('Error listar cptF : ' + response.result);
                    }
                }
            );
        }

        function cargarCptE() {
            cptEEService.listar().then(
                function(response) {
                    if (response.data) {
                        $scope.listaCptE = response.data.rows;
                    } else {
                        console.error('Error listar cptE : ' + response.result);
                    }
                }
            );
        }

        $scope.agregarCptEE = function () {
            console.log('agregarCptEE : ', $scope.recurso.cedulaIdentidad + ' ', + $scope.filterBy.idCptEe);
            $scope.recurso.idCptEe = $scope.filterBy.idCptEe;
            service.asignarCptE($scope.recurso);
        };

        $scope.agregarCptEF = function () {
            console.log('agregarCptEF : ', $scope.recurso.cedulaIdentidad + ' ', + $scope.filterBy.idCptEf);
            $scope.recurso.idCptEf = $scope.filterBy.idCptEf;
            service.asignarCptE($scope.recurso);
        };

        $scope.$watch('recurso', function (newVal, oldVal) {
            if (newVal.cedulaIdentidad) {

                $scope.loadingRecorrido = true;
                    $scope.loadingFormacion = true;
                    $scope.loadingSancion = true;
                    $scope.loadingSumario = true;
                
                if ($rootScope.esHacienda == false) {

                    service.getRecorridoLaboral(newVal.cedulaIdentidad)
                        .then(function (response) {
                            if (response.data == null || response.data.length == 0) {
                                $scope.loadingRecorrido = null;
                            } else {
                                $scope.loadingRecorrido = false;
                            }
                            $scope.recorridoLaboral = response.data;
                        }, function (response) {
                            console.log(response);
                        });

                    service.getFormacionAcademica(newVal.cedulaIdentidad)
                        .then(function (response) {
                            if (response.data == null || response.data.length == 0) {
                                $scope.loadingFormacion = false;
                            } else {
                                $scope.loadingFormacion = false;
                            }
                            $scope.formacionAcademica = response.data;
                        }, function (response) {
                            console.log(response);
                        });
                        
                    service.getSancionPersonal(newVal.cedulaIdentidad)
                        .then(function (response) {
                            if (response.data == null || response.data.length == 0) {
                                $scope.loadingSancion = null;
                            } else {
                                $scope.loadingSancion = false;
                            }
                            $scope.sancionPersonal = response.data;
                        }, function (response) {
                            console.log(response);
                        });

                    service.getSumarioPersonal(newVal.cedulaIdentidad)
                        .then(function (response) {
                            if (response.data == null || response.data.length == 0) {
                                $scope.loadingSumario = null;
                            } else {
                                $scope.loadingSumario = false;
                            }
                            $scope.sumarioPersonal = response.data;
                        }, function (response) {
                            console.log(response);
                        });
                
                } else {

                    $scope.loadingCarreraAdministrativa = true;

                    service.getSobreseimiento(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.sobreseimiento = response.data;
                        }, function (response) {
                            console.log(response);
                        }); 

                    service.getDestitucion(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.destitucion = response.data;
                        }, function (response) {
                            console.log(response);
                        }); 
                    
                    service.getSuspensiones(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.suspensiones = response.data;
                        }, function (response) {
                            console.log(response);
                        }); 
                    
                    service.getSumarios(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.sumarios = response.data;
                        }, function (response) {
                            console.log(response);
                        }); 

                    service.getApercibimientos(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.apercibimientos = response.data;
                        }, function (response) {
                            console.log(response);
                        }); 
                    
                    service.getEventos(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.eventos = response.data;
                        }, function (response) {
                            console.log(response);
                        }); 
                    
                    service.getExperienciaLaboral(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.experienciaLaboral = response.data;
                        }, function (response) {
                            console.log(response);
                        }); 

                    service.getDiasNoTrabajados(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.diasNoTrabajados = response.data;
                        }, function (response) {
                            console.log(response);
                        });    
                        
                    service.getMultas(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.multas = response.data;
                        }, function (response) {
                            console.log(response);
                        }); 

                    service.getMultasControl(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.multasControl = response.data;
                        }, function (response) {
                            console.log(response);
                        });    

                    service.getIdiomas(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.idiomas = response.data;
                        }, function (response) {
                            console.log(response);
                        }); 

                    service.getCursoInformatica(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.cursoInformatica = response.data;
                        }, function (response) {
                            console.log(response);
                        });    

                    service.getOtrosCursos(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.otrosCursos = response.data;
                        }, function (response) {
                            console.log(response);
                        });

                    service.getOtrosEstudios(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.otrosEstudios = response.data;
                        }, function (response) {
                            console.log(response);
                        });

                    service.getEstudiosConcluidos(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.estudiosConcluidos = response.data;
                        }, function (response) {
                            console.log(response);
                        });

                    service.getFormacionAcademicaHacienda(newVal.cedulaIdentidad)
                        .then(function (response) {
                            $scope.formacionAcademicaHacienda = response.data;
                        }, function (response) {
                            console.log(response);
                        });

                    service.getCarreraAdministrativa(newVal.cedulaIdentidad)
                        .then(function (response) {
                            if (response.data == null || response.data.length == 0) {
                                $scope.loadingCarreraAdministrativa = null;
                            } else {
                                $scope.loadingCarreraAdministrativa = false;
                            }
                            $scope.carreraAdministrativa = response.data;
                        }, function (response) {
                            console.log(response);
                        });   
                }             
            }
        });

        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteViewCtrl', {
                "$scope": $scope
            }));
            //cargarCptE();
            //cargarCptF();
            // $scope.getCteEAsociadas()
        })();
    }
]);
app.filter("emptyToEnd", function () {
    return function (array, key) {
        if (!angular.isArray(array)) return;
        var present = array.filter(function (item) {
            return item[key];
        });
        var empty = array.filter(function (item) {
            return !item[key]
        });
        return present.concat(empty);
    };
});
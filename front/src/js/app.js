var tableToExcel = (function() {
    var uri = 'data:application/vnd.ms-excel;base64,',
        template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
        base64 = function(s) {
            return window.btoa(unescape(encodeURIComponent(s)))
        },
        format = function(s, c) {
            return s.replace(/{(\w+)}/g, function(m, p) {
                return c[p];
            })
        }
    return function(table, name) {
        if (!table.nodeType) table = document.getElementById(table)
        var ctx = {
            worksheet: name || 'Worksheet',
            table: table.innerHTML
        }
        window.location.href = uri + base64(format(template, ctx))
    }
})();

/*
 * Se configuran las rutas de la aplicación
 */
app.config(['$routeProvider', '$httpProvider',
    function ($routeProvider, $httpProvider) {

        $httpProvider.interceptors.push('authInterceptor');
        //$httpProvider.responseInterceptors.push('redirectInterceptor');

        /*
         * Route resolver que se utiliza para restringir las páginas que necesitan 
         * que el usuario este logeado en el sistema para acceder a al misma. 
         */
        var resolve = {
            init: ['$rootScope','$location', function ($rootScope, $location) {
                $rootScope.urlActual = $location.url();
                console.log('resolve : ', $rootScope.urlActual);
            }
        ]};

        /*
         * Se definen las rutas de la aplicación
         */
        $routeProvider
            .when('/', {
                templateUrl: 'partials/public-partial.html'
            })
            .when('/login', {
                templateUrl: 'partials/seguridad/login-partial.html',
                controller: 'LoginCtrl',
                resolve: {
                    init: ['$location', 'ConfigService', function ($location, configService) {
                        configService.obtener('registro_online').then(
                            function (response) {
                                if (response.data) {
                                    localStorage.setItem('registro_online', response.data.value);
                                }
                            }
                        );
                    }]
                }
            })
            .when('/seguridad/resetear-contrasena', {
                controller: 'ResetearContrasenaCtrl',
                templateUrl: 'partials/seguridad/resetear-contrasena-partial.html'
            })
            .when('/dashboard', {
                templateUrl: 'partials/dashboard-partial.html',
                controller: 'DashboardCtrl',
                resolve: resolve
            })
            .when('/perfil-usuario', {
                templateUrl: 'partials/perfil-usuario-partial.html',
                controller: 'PerfilCtrl'
            })
            .when('/perfil-usuario/cambiar-password', {
                templateUrl: 'partials/cambiar-password-partial.html',
                controller: 'CambiarPasswordCtrl'
            })
            .when('/seguridad/registro-online', {
                templateUrl: 'partials/seguridad/alta-online-partial.html',
                controller: 'AltaOnLineCtrl',
                resolve: {
                    init: ['$location', 'ConfigService', function ($location, configService) {
                        configService.obtener('registro_online').then(
                            function (response) {
                                if (response.data) {
                                    if (response.data.value == 'N') {
                                        $location.path('/');
                                    }
                                } else {
                                    console.error('Error listar cargos : ' + response.result);
                                }
                            }
                        );
                    }]
                }
            })
            /* Convocatorias */
            .when('/convocatorias', {
                templateUrl: 'partials/gestion/convocatoria-publica-list-partial.html',
                controller: 'ConvocatoriaPublicaListCtrl',
                resolve: resolve
            })
            .when('/convocatorias/:id/ver', {
                templateUrl: 'partials/gestion/convocatoria-publica-view-partial.html',
                controller: 'ConvocatoriaPublicaViewCtrl',
                resolve: resolve
            })
            .when('/gestion/convocatorias', {
                templateUrl: 'partials/gestion/convocatoria-list-partial.html',
                controller: 'ConvocatoriaListCtrl',
                resolve: resolve
            })
            .when('/gestion/convocatorias/crear', {
                templateUrl: 'partials/gestion/convocatoria-form-partial.html',
                controller: 'ConvocatoriaFormCtrl',
                resolve: resolve
            })
            .when('/gestion/convocatorias/:id/editar', {
                templateUrl: 'partials/gestion/convocatoria-form-partial.html',
                controller: 'ConvocatoriaFormCtrl',
                resolve: resolve
            })
            .when('/gestion/convocatorias/:id/ver', {
                templateUrl: 'partials/gestion/convocatoria-view-partial.html',
                controller: 'ConvocatoriaViewCtrl',
                resolve: resolve
            })
            .when('/usuarios/:id/ver', {
                templateUrl: 'partials/usuarios/usuario-view-partial.html',
                controller: 'UsuarioViewCtrl',
                resolve: resolve
            })
            /* CPT */
            .when('/administracion/cpt', {
                templateUrl: 'partials/administracion/cpt/cpt-list-partial.html',
                controller: 'CptListCtrl',
                resolve: resolve
            })
            .when('/administracion/cpt/crear', {
                templateUrl: 'partials/administracion/cpt/cpt-form-partial.html',
                controller: 'CptFormCtrl',
                resolve: resolve
            })
            .when('/administracion/cpt/:id/editar', {
                templateUrl: 'partials/administracion/cpt/cpt-form-partial.html',
                controller: 'CptFormCtrl',
                resolve: resolve
            })
            .when('/administracion/cpt/:id/ver', {
                templateUrl: 'partials/administracion/cpt/cpt-view-partial.html',
                controller: 'CptViewCtrl',
                resolve: resolve
            })
            /* CPT - Tramos */
            .when('/administracion/congruencias', {
                templateUrl: 'partials/administracion/congruencia/congruencias-partial.html',
                controller: 'CongruenciaCtrl',
                resolve: resolve
            })
            /* Congruencia */
            .when('/administracion/legajos', {
                templateUrl: 'partials/administracion/legajos/legajos-partial.html',
                controller: 'LegajosCtrl',
                resolve: resolve
            })
            /* CPT EE */
            .when('/administracion/cpt-ee', {
                templateUrl: 'partials/administracion/cpt-ee/cpt-ee-list-partial.html',
                controller: 'CptEEListCtrl',
                resolve: resolve
            })
            .when('/administracion/cpt-ee/crear', {
                templateUrl: 'partials/administracion/cpt-ee/cpt-ee-form-partial.html',
                controller: 'CptEEFormCtrl',
                resolve: resolve
            })
            .when('/administracion/cpt-ee/:id/editar', {
                templateUrl: 'partials/administracion/cpt-ee/cpt-ee-form-partial.html',
                controller: 'CptEEFormCtrl',
                resolve: resolve
            })
            .when('/administracion/cpt-ee/:id/ver', {
                templateUrl: 'partials/administracion/cpt-ee/cpt-ee-view-partial.html',
                controller: 'CptEEViewCtrl',
                resolve: resolve
            })
            /* CPT EF */
            .when('/administracion/cpt-ef', {
                templateUrl: 'partials/administracion/cpt-ef/cpt-ef-list-partial.html',
                controller: 'CptEFListCtrl',
                resolve: resolve
            })
            .when('/administracion/cpt-ef/crear', {
                templateUrl: 'partials/administracion/cpt-ef/cpt-ef-form-partial.html',
                controller: 'CptEFFormCtrl',
                resolve: resolve
            })
            .when('/administracion/cpt-ef/:id/editar', {
                templateUrl: 'partials/administracion/cpt-ef/cpt-ef-form-partial.html',
                controller: 'CptEFFormCtrl',
                resolve: resolve
            })
            .when('/administracion/cpt-ef/:id/ver', {
                templateUrl: 'partials/administracion/cpt-ef/cpt-ef-view-partial.html',
                controller: 'CptEFViewCtrl',
                resolve: resolve
            })
            /* CLASIFICACION */
            .when('/administracion/clasificacion', {
                templateUrl: 'partials/administracion/clasificacion/clasificacion-list-partial.html',
                controller: 'ClasificacionListCtrl',
                resolve: resolve
            })
            .when('/administracion/clasificacion/crear', {
                templateUrl: 'partials/administracion/clasificacion/clasificacion-form-partial.html',
                controller: 'ClasificacionFormCtrl',
                resolve: resolve
            })
            .when('/administracion/clasificacion/:id/editar', {
                templateUrl: 'partials/administracion/clasificacion/clasificacion-form-partial.html',
                controller: 'ClasificacionFormCtrl',
                resolve: resolve
            })
            .when('/administracion/clasificacion/:id/ver', {
                templateUrl: 'partials/administracion/clasificacion/clasificacion-view-partial.html',
                controller: 'ClasificacionViewCtrl',
                resolve: resolve
            })
            /* CEO */
            .when('/administracion/ceo', {
                templateUrl: 'partials/administracion/ceo/ceo-list-partial.html',
                controller: 'CeoListCtrl',
                resolve: resolve
            })
            .when('/administracion/ceo/crear', {
                templateUrl: 'partials/administracion/ceo/ceo-form-partial.html',
                controller: 'CeoFormCtrl',
                resolve: resolve
            })
            .when('/administracion/ceo/:id/editar', {
                templateUrl: 'partials/administracion/ceo/ceo-form-partial.html',
                controller: 'CeoFormCtrl',
                resolve: resolve
            })
            .when('/administracion/ceo/:id/ver', {
                templateUrl: 'partials/administracion/ceo/ceo-view-partial.html',
                controller: 'CeoViewCtrl',
                resolve: resolve
            })
            /* CUO */
            .when('/administracion/cuo', {
                templateUrl: 'partials/administracion/cuo/cuo-list-partial.html',
                controller: 'CuoListCtrl',
                resolve: resolve
            })
            .when('/administracion/cuo/crear', {
                templateUrl: 'partials/administracion/cuo/cuo-form-partial.html',
                controller: 'CuoFormCtrl',
                resolve: resolve
            })
            .when('/administracion/cuo/:id/editar', {
                templateUrl: 'partials/administracion/cuo/cuo-form-partial.html',
                controller: 'CuoFormCtrl',
                resolve: resolve
            })
            .when('/administracion/cuo/:id/ver', {
                templateUrl: 'partials/administracion/cuo/cuo-view-partial.html',
                controller: 'CuoViewCtrl',
                resolve: resolve
            })
            /* MECIP */
            .when('/administracion/mecip', {
                templateUrl: 'partials/administracion/mecip/mecip-list-partial.html',
                controller: 'MecipListCtrl',
                resolve: resolve
            })
            .when('/administracion/mecip/crear', {
                templateUrl: 'partials/administracion/mecip/mecip-form-partial.html',
                controller: 'MecipFormCtrl',
                resolve: resolve
            })
            .when('/administracion/mecip/:id/editar', {
                templateUrl: 'partials/administracion/mecip/mecip-form-partial.html',
                controller: 'MecipFormCtrl',
                resolve: resolve
            })
            .when('/administracion/mecip/:id/ver', {
                templateUrl: 'partials/administracion/mecip/mecip-view-partial.html',
                controller: 'MecipViewCtrl',
                resolve: resolve
            })
            /* CTS */
            .when('/administracion/cts', {
                templateUrl: 'partials/administracion/cts/cts-list-partial.html',
                controller: 'CtsListCtrl',
                resolve: resolve
            })
            .when('/administracion/cts/:id/ver', {
                templateUrl: 'partials/administracion/cts/cts-view-partial.html',
                controller: 'CtsViewCtrl',
                resolve: resolve
            })
            .when('/administracion/calcular-tramos', {
                templateUrl: 'partials/administracion/cts/cts-form-partial.html',
                controller: 'CtsFormCtrl',
                resolve: resolve
            })
            /* CONFIGURACION CTS */
            .when('/administracion/configuracion-cts', {
                templateUrl: 'partials/administracion/configuracion-cts/config-cts-list-partial.html',
                controller: 'ConfigCtsListCtrl',
                resolve: resolve
            })
            .when('/administracion/configuracion-cts/:id/ver', {
                templateUrl: 'partials/administracion/configuracion-cts/config-cts-view-partial.html',
                controller: 'ConfigCtsViewCtrl',
                resolve: resolve
            })
            .when('/administracion/configuracion-cts/crear', {
                templateUrl: 'partials/administracion/configuracion-cts/config-cts-form-partial.html',
                controller: 'ConfigCtsFormCtrl',
                resolve: resolve
            })
            .when('/administracion/configuracion-cts/:id/editar', {
                templateUrl: 'partials/administracion/configuracion-cts/config-cts-form-partial.html',
                controller: 'ConfigCtsFormCtrl',
                resolve: resolve
            })

            // TODO esta vista ya no se usara
            /*.when('/administracion/cargos-vacantes', {
                templateUrl: 'partials/administracion/cargos-vacantes/cargo-list-partial.html',
                controller: 'CargosVacantesListCtrl',
                resolve: resolve
            })*/

            // Cargos
            .when('/cargos/cargos-no-asignados', {
                templateUrl: 'partials/cargos/cargos-no-asignados-partial.html',
                controller: 'CargosNoAsignadosListCtrl',
                resolve: resolve
            })
            .when('/cargos/cargos-disponibles', {
                templateUrl: 'partials/cargos/cargos-disponibles-partial.html',
                controller: 'CargosDisponiblesListCtrl',
                resolve: resolve
            })
            .when('/cargos/cargos-vacantes', {
                templateUrl: 'partials/cargos/cargos-vacantes-partial.html',
                controller: 'CargosVacantesCtrl',
                resolve: resolve
            })
            .when('/cargos/cargos-vacantes/candidatos', {
                templateUrl: 'partials/cargos/candidatos-list-partial.html',
                controller: 'CandidatosCargosVacantesCtrl',
                resolve: resolve
            })
            .when('/cargos/cargos-disponibles/:id/asignar', {
                templateUrl: 'partials/cargos/cpt-cargo-form-partial.html',
                controller: 'CptCargoFormCtrl',
                resolve: resolve
            })

            // Usuarios
            .when('/usuarios', {
                templateUrl: 'partials/usuarios/usuario-list-partial.html',
                controller: 'UsuarioListCtrl',
                resolve: resolve
            })
            .when('/usuarios/crear', {
                templateUrl: 'partials/usuarios/usuario-form-partial.html',
                controller: 'UsuarioFormCtrl',
                resolve: resolve
            })
            .when('/usuarios/:id/editar', {
                templateUrl: 'partials/usuarios/usuario-form-partial.html',
                controller: 'UsuarioFormCtrl',
                resolve: resolve
            })
            .when('/usuarios/:id/ver', {
                templateUrl: 'partials/usuarios/usuario-view-partial.html',
                controller: 'UsuarioViewCtrl',
                resolve: resolve
            })

            // roles
            .when('/seguridad/roles', {
                templateUrl: 'partials/seguridad/rol-list-partial.html',
                controller: 'RolListCtrl',
                resolve: resolve
            })
            .when('/seguridad/roles/crear', {
                templateUrl: 'partials/seguridad/rol-form-partial.html',
                controller: 'RolFormCtrl',
                resolve: resolve
            })
            .when('/seguridad/roles/:id/editar', {
                templateUrl: 'partials/seguridad/rol-form-partial.html',
                controller: 'RolFormCtrl',
                resolve: resolve
            })
            .when('/seguridad/roles/:id/ver', {
                templateUrl: 'partials/seguridad/rol-view-partial.html',
                controller: 'RolViewCtrl',
                resolve: resolve
            })

            /* Reportes */
            .when('/reportes/cargo', {
                templateUrl: 'partials/reportes/cargo/cargo-list-partial.html',
                controller: 'CargoListCtrl',
                resolve: resolve
            })
            .when('/reportes/cargo/:id/ver', {
                templateUrl: 'partials/reportes/cargo/cargo-view-partial.html',
                controller: 'CargoViewCtrl',
                resolve: resolve
            })
            .when('/reportes/legajos/:id/ver', {
                templateUrl: 'partials/reportes/legajos-view-partial.html',
                controller: 'LegajoViewCtrl',
                resolve: resolve
            })
            .when('/reportes/legajos', {
                templateUrl: 'partials/reportes/legajos-list-partial.html',
                controller: 'LegajoListCtrl',
                resolve: resolve
            })
            .when('/reportes/anexos', {
                templateUrl: 'partials/reportes/anexos-list-partial.html',
                controller: 'AnexoListCtrl',
                resolve: resolve
            })
            .when('/reportes/anexos/comparativo', {
                templateUrl: 'partials/reportes/anexos-comparativo-partial.html',
                controller: 'AnexoComparativoCtrl',
                resolve: resolve
            })
            .when('/reportes/anexos/:id/ver', {
                templateUrl: 'partials/reportes/anexos-view-partial.html',
                controller: 'AnexoViewCtrl',
                resolve: resolve
            })
            .when('/reportes/anexos-no-distribuido', {
                templateUrl: 'partials/reportes/anexos-no-distribuido-list-partial.html',
                controller: 'AnexoNoDistribuidoListCtrl',
                resolve: resolve
            })
            .when('/reportes/matriz-congruencia', {
                templateUrl: 'partials/reportes/congruencia/matriz-congruencia-partial.html',
                controller: 'MatrizCongruenciaCtrl',
                resolve: resolve
            })
            .when('/reportes/puesto-remuneracion', {
                templateUrl: 'partials/reportes/congruencia/puesto-remuneracion-partial.html',
                controller: 'PuestoRemuneracionCtrl',
                resolve: resolve
            })
            .when('/reportes/variacion-personal', {
                templateUrl: 'partials/reportes/gestion-personas/variacion-personal-partial.html',
                controller: 'VariacionPersonalCtrl',
                resolve: resolve
            })
            .when('/reportes/desarrollo-personal', {
                templateUrl: 'partials/reportes/gestion-personas/desarrollo-personal-partial.html',
                controller: 'DesarrolloPersonalCtrl',
                resolve: resolve
            })
            .when('/reportes/promocion-salarial', {
                templateUrl: 'partials/reportes/gestion-personas/promocion-salarial-partial.html',
                controller: 'PromocionSalarialCtrl',
                resolve: resolve
            })
            /* Asignacion masiva */
            .when('/asignacion-legajos/ceo', {
                templateUrl: 'partials/asignacion-legajos/ceo-partial.html',
                controller: 'AsignacionCeoCtrl',
                resolve: resolve
            })
            .when('/asignacion-legajos/cpt-ef', {
                templateUrl: 'partials/asignacion-legajos/cpt-ef-partial.html',
                controller: 'AsignacionCptEFCtrl',
                resolve: resolve
            })
            .when('/asignacion-legajos/cpt', {
                templateUrl: 'partials/asignacion-legajos/cpt-partial.html',
                controller: 'AsignacionCptCtrl',
                resolve: resolve
            })
            .when('/asignacion-legajos/cpt-ee', {
                templateUrl: 'partials/asignacion-legajos/cpt-ee-partial.html',
                controller: 'AsignacionCptEECtrl',
                resolve: resolve
            })
            //Asignación de Convocatorias
            .when('/asignacion-legajos/convocatoria', {
                templateUrl: 'partials/asignacion-legajos/convocatorias/asignacion-convocatorias-list-partial.html',
                controller: 'AsignacionConvocatoriaListCtrl',
                resolve: resolve
            })
            //finaly
            .otherwise({
                redirectTo: '/dashboard'
            });
    }
]);


angular.element(document).ready(function ($http) {
    angular.bootstrap(document, [App.MODULE_NAME]);
});

/**
 * Se configura para que google analytis que trackee las páginas visitadas.
 */
app.run(['$rootScope', '$location', '$window', '$timeout', 'AppServices', 
    function ($rootScope, $location, $window, $timeout, AppServices) {

    console.log('App run init !!! ', $rootScope.idProceso);
    $rootScope.esHacienda = true;
    $rootScope.idProceso = null;
    $rootScope.urlApiRest = window.location.protocol + '//' + window.location.hostname;
    var port = window.location.port;
    if (port != undefined && port.length > 0) {
        $rootScope.urlApiRest += ':' + port;
    }

    // initialise google analytics
    //$window.ga('create', 'UA-XXXXXXXX-X', 'auto');
    // track pageview on state change
    $rootScope.$on('$routeChangeStart', function (event) {
        //$window.ga('send', 'pageview', $location.path());
    });

    /*$.get("/cte-api/config/url_api_rest", 
        function(data, status) {
            $rootScope.urlApiRest = data.value;
        }
    );*/

    checkExpiration();
	
	/**
	 * Handerl del evento redirect de SecurityJS, en caso de que un usuario no cuente con un 
	 * rol epecifico, redirige a una pagina.
	 * @param {object} options [[Description]]
	 */
	SecurityJS.prototype.redirect = function (options) {
		console.log('Redirect');
		if (options.require !== undefined && options.require.length > 0) {
			console.log('Control de permisos');
			if (options.require[0] == 'LISTAR_CONVOCATORIASs') {
				console.log('No tiene permiso para listar');
				$location.path('/concursabilidad/convocatorias/crear');
				return;
			} else if (options.require[0] == 'LISTAR_CPT') {
				$location.path('/dashboard');
				return;
			}
		}
		$location.path('/dashboard');
	};

    /** 
     * Handerl del evento redirect de SecurityJS, en caso de que un usuario no cuente con un 
     * rol epecifico, redirige a una pagina.
     * @param {object} options [[Description]]
     */
    SecurityJS.prototype.redirect = function (options) {
        $location.path('/dashboard');
    };

    $rootScope.goto = function (url) {
        if (AppServices.tienePermiso(url)) {
            $location.path(url);
        } else {
            console.log('No tiene los permisos para acceder a esta pantalla');
        }
    };
    $rootScope.gotoWithParam = function (url, id) {
        if (AppServices.tienePermiso(url)) {
            url = url.replace("{id}", id);
            $location.path(url);
        } else {
            console.log('No tiene los permisos para acceder a esta pantalla');
        }
    };

}]);
/**
 * @class
 * Controller que implementa la lógica del header del portal y maneja la interacción 
 * con la sesión del keycloak
 * 
 * @name angular-keycloak-seed.controller#HeaderCtrl
 * @author <a href = "mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.controller('HeaderCtrl', ['$scope', 'LoginService', '$location', 'CtsService', '$rootScope',
    function ($scope, loginService, $location, ctsService, $rootScope) {

        $scope.user = {};

        /**
         * Contiene la información del usuario logeado en el sistema
         */
        $scope.isLogin = false;

        $scope.menuAdmin = [
            {
                'nombre' : "Buscar CPT",
                'href' : "#!/administracion/cpt",
                'title' : "Buscar Clasificador de puesto de trabajo (CPT)",
                'permiso' : "LISTAR_CPT",
                'icon' : 'fa fa-fw  fa-sitemap',
                'modulo' : '/administracion/cpt'
            }, { 
                'nombre' : "Buscar CPT EF",
                'href' : "#!/administracion/cpt-ef",
                'title' : "Buscar Clasificador de puesto de trabajo específico funcional (CPT-EF)",
                'permiso' : "LISTAR_CPT_EF",
                'icon' : 'fa fa-fw  fa-street-view',
                'modulo' : '/administracion/cpt-ef'    
            }, { 
                'nombre' : "Buscar CPT EE",
                'href' : "#!/administracion/cpt-ee",
                'title' : "Buscar Clasificador de puesto de trabajo específico escalafonario(CPT-EE)",
                'permiso' : "LISTAR_CPT_EE",
                'icon' : 'fa fa-fw  fa-sliders',
                'modulo' : '/administracion/cpt-ee'
            }, { 
                'nombre' : "Buscar CEO",
                'href' : "#!/administracion/ceo",
                'title' : "Buscar Configuración estructural organizativa (CEO)",
                'permiso' : "LISTAR_CEO",
                'icon' : 'fa fa-fw  fa-building-o',
                'modulo' : '/administracion/ceo'
            }, { 
                'nombre' : "Buscar CUO",
                'href' : "#!/administracion/cuo",
                'title' : "Buscar Configuración estructural organizativa (CEO)",
                'permiso' : "LISTAR_CUO",
                'icon' : 'fa fa-fw  fa-building-o',
                'modulo' : '/administracion/cuo'
            }, { 
                'nombre' : "Buscar Mapa de Procesos",
                'href' : "#!/administracion/mecip",
                'title' : "Buscar Modelo Estándar de Control Interno del Paraguay (MECIP)",
                'permiso' : "LISTAR_MECIP",
                'icon' : 'fa fa-fw  fa-building-o',
                'modulo' : '/administracion/mecip'
            }, { 
                'nombre' : "Configurar CTS",
                'href' : "#!/administracion/configuracion-cts",
                'title' : "Configurar Clasificación de los tramos salariales (CTS)",
                'permiso' : "CONFIGURAR_CTS",
                'icon' : 'fa fa-fw  fa-map-signs',
                'modulo' : '/administracion/configuracion-cts'
            }, { 
                'nombre' : "Configurar Congruencia",
                'href' : "#!/administracion/congruencias",
                'title' : "Configurar Congruencia",
                'permiso' : "CONFIGURAR_CONGRUENCIA",
                'icon' : 'fa fa-fw  fa-line-chart',
                'modulo' : '/administracion/congruencias'
            }, { 
                'nombre' : "Calcular Tramos",
                'href' : "#!/administracion/calcular-tramos",
                'title' : "Calcular Tramos",
                'permiso' : "CALCULAR_TRAMOS",
                'icon' : 'fa fa-fw  fa-map-signs',
                'modulo' : '/administracion/calcular-tramos'
            }
        ];

        $scope.menuExplorar = [
            {
                'nombre' : "Explorar CTS",
                'href' : "#!/administracion/cts",
                'title' : "Explorar Clasificación de los tramos salariales (CTS)",
                'permiso' : "LISTAR_CTS",
                'icon' : 'fa fa-fw  fa-map-signs',
                'modulo' : '/administracion/cts'
            }, {
                'nombre' : "Explorar Legajos",
                'href' : "#!/reportes/legajos",
                'title' : "Explorar Legajos",
                'permiso' : "LISTAR_LEGAJOS",
                'icon' : 'fa fa-fw  fa-id-card-o',
                'modulo' : '/reportes/legajos'
            }, {
                'nombre' : "Explorar Anexo de Personal",
                'href' : "#!/reportes/anexos",
                'title' : "Consultar Anexo de Personal",
                'permiso' : "LISTAR_ANEXOS",
                'icon' : 'fa fa-fw  fa-tasks',
                'modulo' : '/reportes/anexos'
            }
        ];

        $scope.menuCargos = [
            {
                'nombre' : "Cargos No Asignados",
                'href' : "#!/cargos/cargos-no-asignados",
                'title' : "Cargos No Asignados",
                'permiso' : "LISTAR_CARGOS_NO_ASIGNADOS",
                'icon' : 'fa fa-fw  fa-suitcase',
                'modulo' : '/cargos/cargos-no-asignados'
            }, {
                'nombre' : "Cargos Disponibles",
                'href' : "#!/cargos/cargos-disponibles",
                'title' : "Cargos Disponibles",
                'permiso' : "LISTAR_CARGOS_DISPONIBLES",
                'icon' : 'fa fa-fw  fa-suitcase',
                'modulo' : '/cargos/cargos-disponibles'
            }, {
                'nombre' : "Cargos Vacantes",
                'href' : "#!/cargos/cargos-vacantes",
                'title' : "Cargos Vacantes",
                'permiso' : "LISTAR_CARGOS_VACANTES",
                'icon' : 'fa fa-fw  fa-street-view',
                'modulo' : '/cargos/cargos-vacantes'
            }
        ];

        $scope.menuUsuarios = [
            {
                'nombre' : "Gestionar Usuarios",
                'href' : "#!/usuarios",
                'title' : "Gestionar Usuarios",
                'permiso' : "LISTAR_USUARIOS",
                'icon' : 'fa fa-fw  fa-user-circle',
                'modulo' : '/usuarios'
            }, {
                'nombre' : "Gestionar Roles",
                'href' : "#!/seguridad/roles",
                'title' : "Gestionar Roles",
                'permiso' : "LISTAR_ROLES",
                'icon' : 'fa fa-fw  fa-key',
                'modulo' : '/seguridad/roles'
            }
        ];

        $scope.menuReportes = [
            {
                'nombre' : "Matriz de Congruencia",
                'href' : "#!/reportes/matriz-congruencia",
                'title' : "Matriz de Congruencia",
                'permiso' : "REPORTES",
                'icon' : 'fa fa-fw  fa-calendar',
                'modulo' : '/reportes/matriz-congruencia'
            }, {
                'nombre' : "Puesto - Remuneración",
                'href' : "#!/reportes/puesto-remuneracion",
                'title' : "Puesto - Remuneración",
                'permiso' : "REPORTES",
                'icon' : 'fa fa-fw  fa-suitcase',
                'modulo' : '/reportes/puesto-remuneracion'
            }, {
                'nombre' : "Variación Personal",
                'href' : "#!/reportes/variacion-personal",
                'title' : "Variación Personal",
                'permiso' : "REPORTES",
                'icon' : 'fa fa-fw  fa-group',
                'modulo' : '/reportes/variacion-personal'
            }, {
                'nombre' : "Desarrollo Personal",
                'href' : "#!/reportes/desarrollo-personal",
                'title' : "Desarrollo Personal",
                'permiso' : "REPORTES",
                'icon' : 'fa fa-fw  fa-arrow-circle-up',
                'modulo' : '/reportes/desarrollo-personal'
            }, {
                'nombre' : "Promoción Salarial",
                'href' : "#!/reportes/promocion-salarial",
                'title' : "Promoción Salarial",
                'permiso' : "REPORTES",
                'icon' : 'fa fa-fw  fa-dollar',
                'modulo' : '/reportes/promocion-salarial'
            }, {
                'nombre' : "Anexos Comparativo",
                'href' : "#!/reportes/anexos/comparativo",
                'title' : "Anexos Comparativo",
                'permiso' : "REPORTES",
                'icon' : 'fa fa-fw  fa-tasks',
                'modulo' : '/reportes/anexos/comparativo'
            }
        ];

        $scope.menuConvocatorias = [
            {
                'nombre' : "Convocatorias",
                'href' : "#!/convocatorias",
                'title' : "Convocatorias",
                'permiso' : "",
                'icon' : 'fa fa-fw  fa-bullhorn',
                'modulo' : '/convocatorias'
            }, {
                'nombre' : "Gestionar Convocatorias",
                'href' : "#!/gestion/convocatorias",
                'title' : "Gestionar Convocatorias",
                'permiso' : "LISTAR_CONVOCATORIAS",
                'icon' : 'fa fa-fw  fa-bullhorn',
                'modulo' : '/gestion/convocatorias'
            }
        ];

        $scope.menu = [
            $scope.menuAdmin, $scope.menuExplorar, $scope.menuCargos, 
            $scope.menuUsuarios, $scope.menuReportes, $scope.menuConvocatorias
        ];

        $scope.tienePermiso = function (index, pos) {
            var permisos = localStorage.getItem('permisos');
            var permiso = $scope.menu[pos][index].permiso;
            if (permiso == '')
                return true;
            if (permisos !== null && permisos.length > 0) {
                if (permisos != undefined) {
                    var listaPermisos = permisos.split(',');
                    if (listaPermisos != undefined && listaPermisos.indexOf(permiso) != -1) {
                        return true;
                    }
                }
            }
            return false;
        };

        $scope.marcarMenu = function (index, pos) {
            var modulo = $scope.menu[pos][index].modulo;
            //console.log('marcarMenu : ', $rootScope.urlActual);
            if (match($rootScope.urlActual, modulo)) {
                return 'menu-actual';
            } else {
                return '';
            }
        };

        function match(url, modulo) {
            //console.log('marcarMenu : ' + url);
            if (modulo != undefined && url != undefined) {
                var tokensModulo = modulo.split('/');
                var tokensUrl = url.split('/');
                if (tokensModulo.length > tokensUrl) {
                    return false;
                }
                for (var i = 0; i < tokensModulo.length; i++) {
                    //console.log('Tokens : ',  tokensUrl[i], ' - ',  tokensModulo[i]);
                    if (tokensUrl[i] != tokensModulo[i]) {
                        return false;
                    }
                }
                return true;
            }
        };

        /**
         * Se encarga de cerrar la sesión del usuario.
         */
        $scope.logout = function () {
            loginService.cerrarSession().then(
                function (response) {
                    if (response.data) {
                        $scope.isLogin = false;
                        $scope.user = null;
                        localStorage.setItem('userId', -1);
                        localStorage.setItem('user', null);
                        localStorage.setItem('permisos', null);
                        localStorage.clear();
                        $rootScope.idProceso = null;
                        $location.path('/login');
                    } else {
                        console.error('Error : ' + response.result);
                    }
                }
            );
            $("#main-page")
                .removeClass("compress")
                .addClass("no-compress");
        };

        /**
         * Se encarga de redirigir a la pantalla de Perfil del usuario.
         */
        $scope.verPerfil = function () {
            $location.path('/perfil-usuario');
        };

        /**
         * Se encarga de verificar si el usuario esta logeado.
         * @returns {boolean} true si esta logeado, false en caso contrario.
         */
        $scope.isLoggedIn = function () {
            var userId = localStorage.getItem('userId');
            $scope.isLogin = userId == -1 || userId == null ? false : true;
            return $scope.isLogin;
        };

        $scope.isLoginView = function () {
            var urlActual = document.location.hash;
            if (urlActual != undefined) {
                if (urlActual.indexOf('login') !== -1) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Se encarga de invocar al login del keycloak
         */
        $scope.login = function () {
            $scope.isLogin = false;
            //getUserInfo();
            $location.path('/login');
        };

        /**
         * Se recupera los datos del usuario logeado y se injecta en la variable user. 
         */
        function initSession() {
            if (!$scope.isLoggedIn()) {
                return;
            }
            getUserInfo();
            //se muestra el sidebar
            $("#main-page")
                .removeClass("no-compress")
                .addClass("compress");
        };

        /**
         * Se recupera los datos del usuario logeado y se injecta en la variable user. 
         */
        function getUserInfo() {
            $scope.user.name = localStorage.getItem('user');
            $scope.user.id = localStorage.getItem('userId');
        };

        $scope.actual = false;
        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            initSession();
            $scope.$watch('user', function () {
                if (!$scope.isLoggedIn()) {
                    initSession();
                }
            });

            //$('ul.side-nav').removeClass('compress');
            //$('a.btn').removeClass('compress');
            $('#main-page').removeClass('compress');
            if ($scope.isLoggedIn() && $scope.isLogin) {
                var permisos = localStorage.getItem('permisos');
                if (permisos.includes('CTS')) {
                    ctsService.getActual().then(
                        function (response) {
                            console.log(response);
                            $scope.actual = response.data.asignado;
                        },
                        function (response) {
                        }
                    );
                } else {
                    $scope.actual = true;
                }
            }

        })();
    }
]);


app.directive('addRemoveClass', [function() {
    return{
        restrict: 'A',
        link: function(scope, element, attrs, controller){
            scope.$watch(function() {
                return element.attr('class');
            }, function(newValue) {
            });

            /**
             * Si el usuario realizó colapsó o comprimió el sidebar se recupera
             * la configuración del storage del browser, para recordar su seleccion.
             */

            var $page = $(".page-wrapper");
            var clazz = "compress";
            var state = "";
            if (state) {
                $page.addClass(state);
                $(element).addClass(state);
                $(element).find(".btn").addClass(state);
            } else {
                $page.removeClass(clazz);
                $(element).removeClass(clazz);
                $(element).find(".btn").removeClass(clazz);
            }
        }

    }
}]);
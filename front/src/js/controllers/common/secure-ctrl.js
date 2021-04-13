/**
 * @class
 * Controller que implementa el formulario de alta y modificación de aplicaciones móviles.
 *
 * @name cm.controller.gestion#ApliacionFormCtrl
 * @author <a href = "mailto:hugo.lopez@konecta.com.py"> Hugo López </a>
 */
app.controller('SecureCtrl', ['$scope', '$location', function ($scope, $location) {

    /**
     * Se encarga de inicializar el control de acceso y visibilidad.
     */
    function securize() {
        //console.log('chekear permisos');
        var opts = {};
        // var resourceAccess = keycloakLauncher.keycloak.resourceAccess;
        //opts.data = resourceAccess['cm-api'] ? resourceAccess['cm-api'].roles : null;
        //console.log(opts);
        //if (opts.data != null) {
            var permisos = localStorage.getItem('permisos');
            var listaPermisos = [];
            if (permisos) {
                listaPermisos = permisos.split(',');
            }
            console.log('chekear Permisos ', listaPermisos.length);
            var sjs = new SecurityJS({
                //scope: this.scope,
                data : listaPermisos
            });
        //}
    }

    function secureList() {
        if (typeof $scope.getResource == 'undefined') {
            return;
        }
        $scope.parentFunction = $scope.getResource;
        $scope.getResource = function (params, paramsObj) {
            var resp = $scope.parentFunction(params, paramsObj);
            resp.then(function () {
                console.log('Datos de la tabla listos');
                //localStorage.setItem('permisos', 'LISTAR_LEGAJOS,LISTAR_ANEXOS,VER_ANEXO,VER_LEGAJO');
                securize();
            });
            return resp;
        }
    }

    /**
     * Constructor / Entrypoint
     * @constructor
     */
    (function initialize() {
        securize();
        secureList();
    })();
}]);

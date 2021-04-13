/**
 * @class
 * Controller que implementa el formulario administración de Convocatorias
 *
 * @name angular-keycloak-seed.controller##ConvocatoriaViewCtrl
 * @author <a href = "mailto:federico.torres@konecta.com.py">Federico Torres</a>
 */
app.controller('ConvocatoriaPublicaViewCtrl', ['$scope', 'ConvocatoriaPublicaService', '$controller', '$routeParams',
    function ($scope, service, $controller, $routeParams) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;

        $scope.$watch('recurso.esInteresado', function (newVal, oldVal) {
            console.log('Datos listos ', newVal);
        });

        $scope.meInteresa = function () {
            service.meInteresa($scope.recurso)
                .then(function (response) {
                    $scope.service.obtener($routeParams.id)
                        .then(function (response) {
                            console.log('Datos listos');
                                $scope.recurso = response.data;
                                console.log($scope.recurso.esInteresado);
                            },function (data, code) {
                                Message.error("No se pudo realizar la operación");
                            });
                Message.ok("Operación exitosa");
            }).catch(function (data) {
                Message.error("Error al iniciar sesión");
            });
        };

        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            // se hereda del controller base
            angular.extend(this, $controller('BaseCteViewCtrl', {
                "$scope": $scope
            }));
            $scope.footer = false;
        })();
    }
]);

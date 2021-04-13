"use strict";

app.controller('PerfilCtrl', ['$scope', 'UsuarioService', '$location', '$controller',
    function ($scope, service, $location, $controller) {

        $scope.uri = "/perfil-usuario/";
        $scope.recurso = {};
        $scope.archivoCargado = false;
        $scope.mostrarCambioPassword = false;
        $scope.fileName = 'No se ha cargado ningun archivo';
        $scope.urlDescarga = App.REST_BASE + '/usuarios/descargar-cv-template';


        $scope.cambiarPassword = function () {
            if ($scope.mostrarCambioPassword) {
                $location.path($scope.uri + 'cambiar-password');
            }
        };

        function verificarCambioPassword() {
            service.tieneOpcionCambioPassword()
                .then(function (response) {
                    console.log(response);
                    if (response.data) {
                        $scope.mostrarCambioPassword = response.data;
                    } else {
                        console.error('Error : ' + response.result);
                    }
                }).catch(function (data) {
                    Message.error("Error");
                });
        };
		
		/**
         * Se encarga de injectar el file dentro del model cuando se sube un archivo.
         * @function
         */
        $scope.uploadFiles = function (file, errFiles) {
            $scope.archivo = file;
            var reader = new FileReader();
            reader.onload = $scope.fileIsLoaded;
            reader.readAsDataURL(file);
        };

        function numberWithCommas(x) {
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }

        $scope.fileIsLoaded = function (e) {
            console.log($scope.archivo);
            $scope.size = '(' + numberWithCommas(Math.ceil($scope.archivo.size / 1024)) + ' K)';
            $scope.fileName = $scope.archivo.name;
            $scope.archivoCargado = true;
            $scope.$apply(function () {});
        };

        $scope.descargarTemplate = function () {
            service.descargarTemplate();
        };
		
		$scope.guardar = function () {
			$scope.recurso.file = $scope.archivo;

            service.uploadFile($scope.recurso).success(function (data) {
                    Message.ok("Archivo enviado correctamente")
                }).error(function (data, code) {
                    Message.error("No se pudo realizar la operación");
                });
		};

        service.getDatosUsuarioLogueado().then(
            function(response) {
                if (response.data) {
                    $scope.recurso.nombre = response.data.nombre;
                    $scope.recurso.cedula = response.data.cedula;
                    $scope.recurso.email = response.data.email;
                } else {
                    console.error('Error : ' + response.result);
                }
            }
        );

        /**
         * Constructor / Entrypoint
         * @constructor
         */
        (function initialize() {
            verificarCambioPassword();
        })();
    }
]);

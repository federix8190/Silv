/**
 * @class
 * Controller que implementa los filtros utilizados en los reportes
 *
 * @name #FiltrosReportesCtrl
 * @author <a href = "mailto:marcelo.szcerba@konecta.com.py">Marcelo Szcerba</a>
 */
app.controller('FiltrosReportesCtrl',
    ['$scope', 'CptEFService','LegajoService','AmbitoService','CptService','CptEEService','CeoService',
        function ($scope, serviceCptEF,LegajoService,AmbitoService,CptService,CptEEService,CeoService) {

            var now = new Date();
            var mesActual = now.getMonth();
            /**
             * Función que se ejecuta al cambiar un valor del filtro CPT EF
             * @param cpt_ef - objeto que contiene los detalles del cpt-f seleccionado
             */
            $scope.onChangeCptF = function (cpt_ef) {
                $scope.filtros.idCptEf = cpt_ef.id;
            };
            /**
             * Función encargada de invocar al rest del reporte desde el botón aplicar en filtros
             */
            $scope.aplicarFiltros = function () {
                $scope.getData();
            };
            /**
             * Función encargada de listar todos los CPT-F para los filtros
             */
            $scope.listarCptF = function () {
                serviceCptEF.listarTodos().then(
                    function (response) {
                        if (response.data) {
                            $scope.listaCptF = response.data.rows;
                            $scope.listaCptF.splice(0, 0, {"den": "TODOS", "id": "TODOS"});
                            $scope.cpt_ef = $scope.listaCptF[0];
                        }
                    }
                );
            };

            $scope.listarAmbitos = function () {
                $scope.listAmbito="";
                AmbitoService.ObtenterAmbitos().then(
                    function (response) {
                        if (response.data) {
                            $scope.listAmbito = response.data
                             $scope.listAmbito.splice(0, 0, {"nombre": "TODOS", "id": "TODOS"});
                            $scope.sambito = $scope.listAmbito[0];
                        }
                    }
                );
            };

            $scope.onChangeAmbito = function (sambito) {
                $scope.filtros.ambito = sambito.nombre;
            };
            /**
             * Recupera la lista de vinculaciones para poblar el combo de vinculaciones.
             */
            $scope.listaVinculaciones={};
            function getVinculaciones() {
                LegajoService.getVinculaciones().then(
                    function (response) {
                        $scope.listaVinculaciones = response.data;
                    },
                    function () {
                        Message.error("Ocurrió un error al recuperar la lista de vinculaciones.");
                    }
                );
            }
           ;


            /**
             * Función que se ejecuta al cambiar un valor del filtro CPT
             * @param cpt - objeto que contiene los detalles del cpt seleccionado
             */
            $scope.onChangeCpt = function (cpt) {
                $scope.filtros.idCpt = cpt.id;
            };
            $scope.listarCpt = function () {
                CptService.listarTodos().then(
                    function (response) {
                        if (response.data) {
                            $scope.listaCpt = response.data.rows;
                            $scope.listaCpt.splice(0, 0, {"denominacion": "TODOS", "id": "TODOS"});
                            $scope.cpt = $scope.listaCpt[0];
                        }
                    }
                );
            };


            /**
             * Función que se ejecuta al cambiar un valor del filtro CPT EE
             * @param cptee - objeto que contiene los detalles del cptee seleccionado
             */
            $scope.onChangeCptee = function (cptee) {
                $scope.filtros.idCptEe = cptee.id;
            };
            $scope.listarCptEE = function () {
                CptEEService.listarTodos().then(
                    function (response) {
                        if (response.data) {
                            $scope.listaCptee = response.data.rows;
                            $scope.listaCptee.splice(0, 0, {"denominacion": "TODOS", "id": "TODOS"});
                            $scope.cptee = $scope.listaCptee[0];
                        }
                    }
                );
            };

            /**
             * Función que se ejecuta al cambiar un valor del filtro CEO
             * @param cptee - objeto que contiene los detalles del cptee seleccionado
             */
            $scope.onChangeCeo = function (ceo) {
                $scope.filtros.idCeo = ceo.id;
            };
            $scope.listarCeo = function () {
                var date = new Date();
                $scope.anho = date.getFullYear();
                $scope.mes = date.getMonth() + 1;
                CeoService.listarTodos($scope.anho, $scope.mes).then(
                    function (response) {
                        if (response.data) {
                            $scope.listaCeo = response.data.rows;
                            $scope.listaCeo.splice(0, 0, {"denominacion": "TODOS", "id": "TODOS"});
                            $scope.ceo = $scope.listaCeo[0];
                        }
                    }
                );
            };


            /**
             * @constructor
             */
            (function initialize() {
                $scope.listarAmbitos();
                $scope.listarCptF();
                getVinculaciones();
                 $scope.listarCpt();
                $scope.listarCptEE();
                $scope.listarCeo();
                $scope.filtros = {};
                $scope.filtros.desdeAnho = now.getFullYear();
                $scope.filtros.desdeMes = 1;
                
                if (mesActual == 0) {
                    $scope.filtros.hastaMes = 12;
                    $scope.filtros.hastaAnho = now.getFullYear() - 1;
                    $scope.filtros.desdeAnho = $scope.filtros.hastaAnho;
                } else {
                    $scope.filtros.hastaAnho = now.getFullYear();
                    $scope.filtros.hastaMes = mesActual;
                }

                $scope.filtros.vinculacion = "TODOS";
                $scope.filtros.ambito = "TODOS";
                $scope.filtros.sexo = "TODOS";
                $scope.filtros.idCptEf = "TODOS";
                $scope.filtros.idCpt= "TODOS";
                $scope.filtros.idCptEe ="TODOS";
                $scope.filtros.idCeo ="TODOS";

            })();
        }
    ]);
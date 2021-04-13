/**
 * @class
 * Controller que implementa la matriz de congruencia.
 *
 * @name #MatrizCongruenciaCtrl
 * @author <a href = "mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.controller('VariacionPersonalCtrl',
    ['$scope', '$q', 'GestionPersonasService', '$controller',
        function ($scope, $q, service, $controller) {
            /**
             * Se encarga de recuperar la lista de clasificadores de tramos
             * salariales.
             *
             * @function
             */
            $scope.getGestionPersonas= function(params) {
                return service.getGestionPersonas(params)
            };
            /**
             * Se encarga de recuperar los datos de forma sincrona.
             * @function
             */
            $scope.getData = function () {
                var promises = [];

                $scope.datos = [];
                $scope.datos1 = [];
                $scope.categorias = [];
                $scope.egresos = [];
                $scope.ingresos = [];
                $scope.variacion = [];

                $scope.puestos = [];
                $scope.loading = true;
                promises.push($scope.getGestionPersonas($scope.filtros));
                $q.all(promises).then(
                    function (response) {
                        $scope.loading = false;
                        $scope.datos =response;
                        $scope.puestos = response[0].data;
                        // $scope.puestos[0].anho = $scope.filtros.desdeAnho;
                        // $scope.puestos[0].mes = $scope.filtros.desdeMes;
                        // $scope.puestos[1].anho = $scope.filtros.hastaAnho;
                        // $scope.puestos[1].mes = $scope.filtros.hastaMes;

                        $scope.puestos.find(function (row, i) {
                            if (i > 0) {
                                if ($scope.puestos[i - 1].totalCargos) {

                                    $scope.puestos[i].pctEgresos =
                                        Math.round(($scope.puestos[i].egresos * 100 / $scope.puestos[i - 1].totalCargos) * 100) / 100;
                                    $scope.puestos[i].pctIngresos =
                                        Math.round(($scope.puestos[i].ingresos * 100 / $scope.puestos[i - 1].totalCargos) * 100) / 100;
                                    $scope.puestos[i].pctCargos =
                                        Math.round(($scope.puestos[i].nuevosCargos * 100 / $scope.puestos[i - 1].totalCargos) * 100) / 100;
                                    $scope.puestos[i].variacion =
                                        $scope.puestos[i].ingresos - $scope.puestos[i].egresos 
                                            + $scope.puestos[i].nuevosCargos;
                                    $scope.puestos[i].pctVariacion =
                                        Math.round(($scope.puestos[i].variacion * 100 / $scope.puestos[i - 1].totalCargos) * 100) / 100;
                                } else {
                                    $scope.puestos[i].pctEgresos = 0;
                                    $scope.puestos[i].pctIngresos = 0;
                                    $scope.puestos[i].pctCargos = 0;
                                    $scope.puestos[i].variacion = 0;
                                    $scope.puestos[i].pctVariacion = 0;
                                }
                            } else {
                                // $scope.puestos[i].egresos = "";
                                // $scope.puestos[i].pctEgresos = "";
                                // $scope.puestos[i].ingresos = "";
                                // $scope.puestos[i].pctIngresos = "";
                                // $scope.puestos[i].pctCargos = "";
                                // $scope.puestos[i].nuevosCargos = "";
                                // $scope.puestos[i].variacion = "";
                                // $scope.puestos[i].pctVariacion = "";
                            }
                        });

                        for (var i=1; i< $scope.puestos.length; i++){
                            $scope.egresos.push($scope.puestos[i].pctEgresos);
                            $scope.ingresos.push($scope.puestos[i].pctIngresos);
                            $scope.variacion.push($scope.puestos[i].pctVariacion);
                            $scope.categorias.push($scope.puestos[i].mes.toString() + "-" + $scope.puestos[i].anho.toString());

                            $scope.datos.push(
                                {
                                    name: $scope.puestos[i].mes.toString() + "-" + $scope.puestos[i].anho.toString(),
                                    data: [$scope.puestos[i].pctEgresos, $scope.puestos[i].pctIngresos, $scope.puestos[i].pctVariacion]
                                }
                            );

                        }

                        $scope.datos1.push(
                            {
                                name: 'Egresos',
                                data: $scope.egresos
                            },
                            {
                                name: 'Ingresos',
                                data: $scope.ingresos
                            },
                            {
                                name: 'Variación',
                                data: $scope.variacion
                            }
                         );

                         Highcharts.chart('container', {
                            chart: {
                                type: 'column'
                            },
                            title: {
                                text: 'INDICE DE VARIACION DE LA DOTACION DE PERSONAL - IVDP'
                            },
                            xAxis: {
                                //categories: $scope.categorias
                                categories: ['Egresos', 'Ingresos', 'Variación']
                            },
                            credits: {
                                enabled: false
                            },
                            series: $scope.datos
                        });

                        Highcharts.chart('container1', {

                            title: {
                                text: 'INDICE DE VARIACION DE LA DOTACION DE PERSONAL - IVDP'
                            },
                        
                            subtitle: {
                                text: ''
                            },
                            xAxis: {
                                //categories: $scope.categorias
                                categories: $scope.categorias
                            },
                            yAxis: {
                                title: {
                                    text: ''
                                }
                            },
                            legend: {
                                layout: 'vertical',
                                align: 'right',
                                verticalAlign: 'middle'
                            },
                        /*
                            plotOptions: {
                                series: {
                                    label: {
                                        connectorAllowed: false
                                    },
                                    pointStart: 2010
                                }
                            },
                        */
                            series: $scope.datos1,
                        
                            responsive: {
                                rules: [{
                                    condition: {
                                        maxWidth: 500
                                    },
                                    chartOptions: {
                                        legend: {
                                            layout: 'horizontal',
                                            align: 'center',
                                            verticalAlign: 'bottom'
                                        }
                                    }
                                }]
                            }
                        
                        });

                    }, function (response) {

                    });
            };
            $scope.generarReporte = function(){


                var parametros ={};
                parametros.parametros =   $scope.datos[0].data;
                parametros.reporte = "VP";
                parametros.CPTEF=$scope.filtros.idCptEf;
                parametros.VINCULACION=$scope.filtros.vinculacion;
                parametros.AMBITO=$scope.filtros.ambito;
                parametros.GENERO=$scope.filtros.sexo;
                parametros.CPTEE=$scope.filtros.idCptee;
                parametros.CPT= $scope.filtros.idCpt;
                parametros.CEO= $scope.filtros.idCeo;
                var urlEncondeURIComponent = encodeURIComponent(angular.toJson(parametros));
                var urlFinal =App.REST_BASE +'/reportes/reporteexcell?parametros='+urlEncondeURIComponent;
                window.open(urlFinal);

            };
            /**
             * @constructor
             */
            (function initialize() {
                $scope.matriz = {};
                $scope.total = {
                    tramo: {},
                    nivel: {}
                };
                angular.extend(this, $controller('FiltrosReportesCtrl', {
                    "$scope": $scope
                }));
              //  $scope.getData();
            })();
        }
    ]);


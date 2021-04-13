/**
 * @class
 * Controller que implementa la matriz de congruencia.
 *
 * @name #MatrizCongruenciaCtrl
 * @author <a href = "mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.controller('PuestoRemuneracionCtrl',
    ['$scope', '$q', 'CongruenciaService', '$controller',
        function ($scope, $q, service, $controller) {
            /**
             * Se encarga de recuperar la lista de clasificadores de tramos
             * salariales.
             *
             * @function
             */
            $scope.getPuestoRemuneracion = function (params) {
                return service.getPuestoRemuneracion(params)
            };

            $scope.getBackgroundColorSobreCategorizados = function (porcentaje) {
                if (porcentaje > 25) {
                    return 'red';
                } else if (porcentaje < 10) {
                    return '#00ff00';
                } else {
                    return 'yellow';
                }
            };

            $scope.getTextColorSobreCategorizados = function (porcentaje) {
                if (porcentaje > 25) {
                    return 'white';
                } else {
                    return 'black';
                }
            };

            $scope.getBackgroundColorEnRango = function (porcentaje) {
                if (porcentaje < 50) {
                    return 'red';
                } else if (porcentaje > 80) {
                    return '#00ff00';
                } else {
                    return 'yellow';
                }
            };

            $scope.getTextColorEnRango = function (porcentaje) {
                if (porcentaje < 50) {
                    return 'white';
                } else {
                    return 'black';
                }
            };

            $scope.getBackgroundColorSubCategorizados = function (porcentaje) {
                if (porcentaje > 25) {
                    return 'red';
                } else if (porcentaje < 10) {
                    return '#00ff00';
                } else {
                    return 'yellow';
                }
            };

            $scope.getTextColorSubCategorizados = function (porcentaje) {
                if (porcentaje > 25) {
                    return 'white';
                } else {
                    return 'black';
                }
            };

            /**
             * Se encarga de recuperar los datos de forma sincrona.
             * @function
             */
            $scope.getData = function () {
                $scope.categorias = [];
                $scope.datos = [];
                $scope.datos1 = [];
                $scope.subCat = [];
                $scope.enRang = [];
                $scope.sobCat = [];
                
                var promises = [];
                $scope.puestos = [];
                $scope.loading = true;

                promises.push($scope.getPuestoRemuneracion($scope.filtros));
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
                            if ($scope.puestos[i].totalCargos > 0) {
                                $scope.puestos[i].pctSubRango =
                                    Math.round(($scope.puestos[i].subRango * 100 / $scope.puestos[i].totalCargos) * 100) / 100;
                                $scope.puestos[i].pctEnRango =
                                    Math.round(($scope.puestos[i].enRango * 100 / $scope.puestos[i].totalCargos) * 100) / 100;
                                $scope.puestos[i].pctSobreRango =
                                    Math.round(($scope.puestos[i].sobreRango * 100 / $scope.puestos[i].totalCargos) * 100) / 100;
                                $scope.puestos[i].pctTotal =
                                    Math.round(($scope.puestos[i].totalCargos * 100 / $scope.puestos[i].totalCargos) * 100) / 100;
                            } else {
                                $scope.puestos[i].pctSubRango = 0;
                                $scope.puestos[i].pctEnRango = 0;
                                $scope.puestos[i].pctSobreRango = 0;
                                $scope.puestos[i].pctTotal = 0;
                            }
                        });
                        $scope.enRang.push($scope.puestos[0].pctEnRango);
                        $scope.subCat.push($scope.puestos[0].pctSubRango);
                        $scope.sobCat.push($scope.puestos[0].pctSobreRango);
                        $scope.categorias.push($scope.puestos[0].mes.toString() + "-" + $scope.puestos[0].anho.toString());

                        $scope.datos.push(
                            {
                                name: $scope.puestos[0].mes.toString() + "-" + $scope.puestos[0].anho.toString(),
                                data: [
                                    $scope.puestos[0].pctSubRango, 
                                    $scope.puestos[0].pctEnRango, 
                                    $scope.puestos[0].pctSobreRango
                                ]
                            }
                        );

                        for (var i=1; i< $scope.puestos.length; i++) {
                            $scope.puestos[i].difEnRango = $scope.puestos[i].pctEnRango - $scope.puestos[0].pctEnRango;
                            $scope.puestos[i].difSubRango = $scope.puestos[i].pctSubRango - $scope.puestos[0].pctSubRango;
                            $scope.puestos[i].difSobreRango = $scope.puestos[i].pctSobreRango - $scope.puestos[0].pctSobreRango;

                            $scope.enRang.push($scope.puestos[i].pctEnRango);
                            $scope.subCat.push($scope.puestos[i].pctSubRango);
                            $scope.sobCat.push($scope.puestos[i].pctSobreRango);

                            $scope.categorias.push($scope.puestos[i].mes.toString() + "-" + $scope.puestos[i].anho.toString());

                            $scope.datos.push(
                                {
                                    name: $scope.puestos[i].mes.toString() + "-" + $scope.puestos[i].anho.toString(),
                                    data: [$scope.puestos[i].pctSubRango, $scope.puestos[i].pctEnRango, $scope.puestos[i].pctSobreRango]
                                }
                            );
                        }

                        $scope.datos1.push(
                            {
                                name: 'Sub Cat',
                                data: $scope.subCat
                            },
                            {
                                name: 'En Rango',
                                data: $scope.enRang
                            },
                            {
                                name: 'Sob Cat',
                                data: $scope.sobCat
                            }
                         );

                         Highcharts.chart('container', {
                            chart: {
                                type: 'column'
                            },
                            title: {
                                text: 'INDICE DE CONGRUENCIA PUESTO-REMUNERACION IC-PR'
                            },
                            xAxis: {
                                //categories: $scope.categorias
                                categories: ['Sub Cat', 'En Rango', 'Sob Cat']
                            },
                            credits: {
                                enabled: false
                            },
                            series: $scope.datos
                        });

                        Highcharts.chart('container1', {

                            title: {
                                text: 'INDICE DE CONGRUENCIA PUESTO-REMUNERACION IC-PR'
                            },
                        
                            subtitle: {
                                text: 'EVOLUCION DEL INDICE DE CONGRUENCIA'
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

            
/*
            Highcharts.chart('container', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'INDICE DE CONGRUENCIA PUESTO-REMUNERACION IC-PR'
                },
                subtitle: {
                    text: 'EVOLUCION DEL INDICE DE CONGRUENCIA'
                },
                xAxis: {
                    categories: $scope.categorias,
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: ''
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: $scope.datos
            });
    */

            $scope.generarReporte = function(){


                var parametros ={};
                parametros.parametros =   $scope.datos[0].data;
                parametros.reporte = "PR";
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
               // $scope.getData();
            })();
        }
    ]);


/**
 * @class
 * Controller que implementa la matriz de congruencia.
 *
 * @name #MatrizCongruenciaCtrl
 * @author <a href = "mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.controller('PromocionSalarialCtrl',
    ['$scope', '$q', 'GestionPersonasService', '$controller','GenerarReporteService',
        function ($scope, $q, service, $controller,GenerarReporteService) {
            /**
             * Se encarga de recuperar la lista de clasificadores de tramos
             * salariales.
             *
             * @function
             */
            $scope.getPromocionSalarial = function (params) {
                return service.getPromocionSalarial(params)
            };
            /**
             * Se encarga de recuperar los datos de forma sincrona.
             * @function
             */
            $scope.getData = function () {
                $scope.categorias = [];
                $scope.datos = [];
                $scope.datos1 = [];
                $scope.tramoSalarialInicial = [];
                $scope.tramoSalarialActual = [];
                $scope.variacion = [];
    
                $scope.datos2 = [];
                $scope.datos3 = [];
                $scope.tramoSalarialInicial1 = [];
                $scope.tramoSalarialActual1 = [];
                $scope.variacion1 = [];
                
                var promises = [];
                promises.push($scope.getPromocionSalarial($scope.filtros));
                $scope.puestos = [];
                $scope.loading = true;
                $q.all(promises).then(
                    function (response) {
                        $scope.loading = false;
                        $scope.datos =response;
                        $scope.puestos = response[0].data;
                        $scope.puestos.find(function (row, i) {
                            if (i > 0) {
                                if ($scope.puestos[i - 1].totalCargos) {
                                    $scope.puestos[i].promTramosActual =$scope.puestos[i].promTramosActual.toFixed(2);
                                    $scope.puestos[i].promTramosInicial=  Math.round($scope.puestos[i].promTramosInicial *100 )/100;
                                    $scope.puestos[i].pctPromocionadas =
                                        Math.round((($scope.puestos[i].promocionadas * 100) / $scope.puestos[i - 1].totalCargos) * 100) / 100;
                                    $scope.puestos[i].varTramoSalarial =  Math.round(($scope.puestos[i].promTramosActual - $scope.puestos[i].promTramosInicial)*100)/100;
                                    $scope.puestos[i].varSalarioBasico = $scope.puestos[i].promSalariosActual - $scope.puestos[i].promSalariosInicial;
                                    $scope.puestos[i].pctSalario = $scope.puestos[i].varSalarioBasico ?
                                        (Math.round(($scope.puestos[i].varSalarioBasico * 100 / $scope.puestos[i].promSalariosInicial) * 100) / 100)
                                        : 0;
                                } else {
                                    $scope.puestos[i].pctPromocionadas = 0;
                                    $scope.puestos[i].varTramoSalarial = 0;
                                    $scope.puestos[i].varSalarioBasico = 0;
                                    $scope.puestos[i].pctSalario = 0;
                                }
                            } else {
                                $scope.puestos[i].promocionadas = "";
                                $scope.puestos[i].promTramosInicial = "";
                                $scope.puestos[i].promSalariosInicial = "";
                                $scope.puestos[i].promTramosActual = "";
                                $scope.puestos[i].promSalariosActual = "";
                            }
                        });

                        for (var i=1; i< $scope.puestos.length; i++){
                            $scope.tramoSalarialInicial.push($scope.puestos[i].promTramosInicial);
                            $scope.tramoSalarialActual.push($scope.puestos[i].promTramosActual);
                            $scope.variacion.push($scope.puestos[i].varTramoSalarial);

                            $scope.tramoSalarialInicial1.push($scope.puestos[i].promSalariosInicial);
                            $scope.tramoSalarialActual1.push($scope.puestos[i].promSalariosActual);
                            $scope.variacion1.push($scope.puestos[i].varSalarioBasico);
                            $scope.categorias.push($scope.puestos[i].mes.toString() + "-" + $scope.puestos[i].anho.toString());

                            $scope.datos.push(
                                {
                                    name: $scope.puestos[i].mes.toString() + "-" + $scope.puestos[i].anho.toString(),
                                    data: [$scope.puestos[i].promTramosInicial, $scope.puestos[i].promTramosActual, $scope.puestos[i].varTramoSalarial]
                                }
                            );

                            $scope.datos2.push(
                                {
                                    name: $scope.puestos[i].mes.toString() + "-" + $scope.puestos[i].anho.toString(),
                                    data: [$scope.puestos[i].promSalariosInicial, $scope.puestos[i].promSalariosActual, $scope.puestos[i].varSalarioBasico]
                                }
                            );

                        }

                        $scope.datos1.push(
                            {
                                name: 'Promedio Tramo Salarial Inicial',
                                data: $scope.tramoSalarialInicial
                            },
                            {
                                name: 'Promedio Tramo Salarial Actual',
                                data: $scope.tramoSalarialActual
                            },
                            {
                                name: 'Variación Tramo Salarial',
                                data: $scope.variacion
                            }
                         );

                         $scope.datos3.push(
                            {
                                name: 'Promedio Salarial Inicial',
                                data: $scope.tramoSalarialInicial1
                            },
                            {
                                name: 'Promedio Salarial Actual',
                                data: $scope.tramoSalarialActual1
                            },
                            {
                                name: 'Variación Salarial',
                                data: $scope.variacion1
                            }
                         );

                         Highcharts.chart('container', {
                            chart: {
                                type: 'column'
                            },
                            title: {
                                text: 'INDICE DE PROMOCION SALARIAL - IPS'
                            },
                            xAxis: {
                                //categories: $scope.categorias
                                categories: ['Promedio Tramo Salarial Inicial', 'Promedio Tramo Salarial Actual', 'Variación Tramo Salarial']
                            },
                            credits: {
                                enabled: false
                            },
                            series: $scope.datos
                        });

                        Highcharts.chart('container1', {

                            title: {
                                text: 'INDICE DE PROMOCION SALARIAL - IPS'
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

                        Highcharts.chart('container2', {
                            chart: {
                                type: 'column'
                            },
                            title: {
                                text: 'INDICE DE PROMOCION SALARIAL - IPS'
                            },
                            xAxis: {
                                //categories: $scope.categorias
                                categories: ['Promedio Salarial Inicial', 'Promedio Salarial Actual', 'Variación Salarial']
                            },
                            credits: {
                                enabled: false
                            },
                            series: $scope.datos2
                        });

                        Highcharts.chart('container3', {

                            title: {
                                text: 'INDICE DE PROMOCION SALARIAL - IPS'
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
                            series: $scope.datos3,
                        
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
            function getFileNameFromHttpResponse(httpResponse) {
                var contentDispositionHeader = httpResponse.headers('Content-Disposition');
                var result = contentDispositionHeader.split(';')[1].trim().split('=')[1];
                return result.replace(/"/g, '');
            }

            $scope.generarReporte = function(){

                var parametros ={};
                parametros.parametros =   $scope.datos[0].data;
                parametros.reporte = "PS";
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


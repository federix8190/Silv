/**
 * @class
 * Controller que implementa la matriz de congruencia.
 *
 * @name #MatrizCongruenciaCtrl
 * @author <a href = "mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.controller('DesarrolloPersonalCtrl',
    ['$scope', '$q', 'GestionPersonasService', '$controller',
        function ($scope, $q, service, $controller) {
            /**
             * Se encarga de recuperar la lista de clasificadores de tramos
             * salariales.
             *
             * @function
             */
            $scope.getDesarrolloPersonal = function(params) {
                return service.getDesarrolloPersonal(params)
            };
            /**
             * Se encarga de recuperar los datos de forma sincrona.
             * @function
             */
            $scope.getData = function() {
                
                $scope.datos = [];
                $scope.datos1 = [];
                $scope.datos2 = [];
                $scope.datos3 = [];
                $scope.tramoSalarialIngresos = [];
                $scope.tramoSalarialEgresos = [];
                $scope.variacion = [];
                $scope.promSalarialIngresos = [];
                $scope.promSalarialEgresos = [];
                $scope.variacion1 = [];
                $scope.categorias = [];

                $scope.arr = [];
                
                $scope.menorMayor = function(eg, ing){
                    return eg <= ing ? eg : ing;
                };

                $scope.mayorMenor = function(eg, ing){
                    return eg >= ing ? eg : ing;
                };

                $scope.mayorIngreso = function(eg, ing){
                    return ing > eg;
                };

                $scope.mayorEgreso = function(eg, ing){
                    return eg > ing;
                };

                var promises = [];
                $scope.puestos = [];
                $scope.loading = true;
                promises.push($scope.getDesarrolloPersonal($scope.filtros));
                $q.all(promises).then(
                    function (response) {
                        $scope.loading = false;
                        $scope.datos =response;
                        $scope.puestos = response[0].data;


                        for (var i=1; i< $scope.puestos.length; i++){
                            $scope.puestos[i].promTramosEgresos = Math.round($scope.puestos[i].promTramosEgresos * 100) / 100;
                            $scope.puestos[i].promTramosIngresos = Math.round($scope.puestos[i].promTramosIngresos * 100) / 100;
                            $scope.puestos[i].varTramos = $scope.puestos[i].promTramosEgresos - $scope.puestos[i].promTramosIngresos;
                            $scope.puestos[i].varSalarios = $scope.puestos[i].promSalariosEgresos - $scope.puestos[i].promSalariosIngresos;
                        }

                        for (i=1; i< $scope.puestos.length; i++){
                            $scope.tramoSalarialIngresos.push($scope.puestos[i].promTramosIngresos);
                            $scope.tramoSalarialEgresos.push($scope.puestos[i].promTramosEgresos);
                            $scope.variacion.push($scope.puestos[i].varTramos);

                            $scope.promSalarialIngresos.push($scope.puestos[i].promSalariosIngresos);
                            $scope.promSalarialEgresos.push($scope.puestos[i].promSalariosEgresos);
                            $scope.variacion1.push($scope.puestos[i].varSalarios);
                            $scope.categorias.push($scope.puestos[i].mes.toString() + "-" + $scope.puestos[i].anho.toString());

                            $scope.datos.push(
                                {
                                    name: $scope.puestos[i].mes.toString() + "-" + $scope.puestos[i].anho.toString(),
                                    data: [$scope.puestos[i].promTramosIngresos, $scope.puestos[i].promTramosEgresos, $scope.puestos[i].varTramos]
                                }
                            );

                            $scope.datos2.push(
                                {
                                    name: $scope.puestos[i].mes.toString() + "-" + $scope.puestos[i].anho.toString(),
                                    data: [$scope.puestos[i].promSalariosIngresos, $scope.puestos[i].promSalariosEgresos, $scope.puestos[i].varSalarios]
                                }
                            );

                        }

                        $scope.datos1.push(
                            {
                                name: 'Promedio Tramo Salarial Ingresos',
                                data: $scope.tramoSalarialIngresos
                            },
                            {
                                name: 'Promedio Tramo Salarial Egresos',
                                data: $scope.tramoSalarialEgresos
                            },
                            {
                                name: 'Variación Tramo Salarial',
                                data: $scope.variacion
                            }
                         );

                         $scope.datos3.push(
                            {
                                name: 'Promedio Salarial Ingresos',
                                data: $scope.promSalarialIngresos
                            },
                            {
                                name: 'Promedio Salarial Egresos',
                                data: $scope.promSalarialEgresos
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
                                text: 'INDICE DE DESARROLLO DE PERSONAL - IDP'
                            },
                            xAxis: {
                                //categories: $scope.categorias
                                categories: ['Promedio Tramo Salarial Ingresos', 'Promedio Tramo Salarial Egresos', 'Variación Tramo Salarial']
                            },
                            credits: {
                                enabled: false
                            },
                            series: $scope.datos
                        });

                        Highcharts.chart('container1', {

                            title: {
                                text: 'INDICE DE DESARROLLO DE PERSONAL - IDP'
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
                                text: 'INDICE DE DESARROLLO DE PERSONAL - IDP'
                            },
                            xAxis: {
                                //categories: $scope.categorias
                                categories: ['Promedio Salarial Ingresos', 'Promedio Salarial Egresos', 'Variación Salarial']
                            },
                            credits: {
                                enabled: false
                            },
                            series: $scope.datos2
                        });

                        Highcharts.chart('container3', {

                            title: {
                                text: 'INDICE DE DESARROLLO DE PERSONAL - IDP'
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
            $scope.generarReporte = function(){


                var parametros ={};
                parametros.parametros =   $scope.datos[0].data;
                parametros.reporte = "DP";
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
                //$scope.getData();
            })();
        }
    ]);
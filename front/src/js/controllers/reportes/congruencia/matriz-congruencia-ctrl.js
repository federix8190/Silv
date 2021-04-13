/**
 * @class
 * Controller que implementa la matriz de congruencia.
 *
 * @name #MatrizCongruenciaCtrl
 * @author <a href = "mailto:maximiliano.baez@konecta.com.py">Maximiliano Báez</a>
 */
app.controller('MatrizCongruenciaCtrl', ['$scope', '$q', 'CongruenciaService', 'CtsService', 'CptService', 
        'ProgramaService', '$location', 'LegajoService', 'CptEEService','CeoService','CptEFService',
    function ($scope, $q, service, ctsService, cptService, ProgramaService, $location, LegajoService,
        CptEEService,CeoService,CptEFService) {

        /**
         * Alamacena los tramos salariales
         * @field
         */
        $scope.cts = [];
        $scope.listaTest = ['A','B','C','D','E','F','G','H'];
        /**
         * Indica si los datos si se estan cargando o no.
         * @field
         */
        $scope.loading = null;

        /**
         * Resumen de la matriz
         */
        $scope.congruentes = 0;
        $scope.subCategorizados = 0;
        $scope.sobreCategorizados = 0;
        $scope.totalMatriz = 0;
        $scope.resumenMatriz = {};
        $scope.porcentajeCongruentes = 0.0;
        $scope.porcentajeSubCategorizados = 0.0;
        $scope.porcentajeSobreCategorizados = 0.0;
        $scope.colorSobreCategorizados = '';
        $scope.colorCongruentes = '';
        $scope.colorSubCategorizados = '';

        /**
         * Lista de clasificadores de puestos de trabajo.
         */
        $scope.cpt = [];
        /**
         * Alamacena el resumen de los datos por tramo y nivel cpt.
         */
        $scope.matriz = {};

        /**
         * Almacena los filtros aplicables a la matriz de congruencia.
         */
        $scope.filtros = {};

        //$scope.concepto = {};
        $scope.conceptos = {};

        $scope.parametrosListado = {};

        /**
         * Almacena los datos totales de cada nivel y tramo salarial.
         */
        $scope.total = {
            tramo: {},
            nivel: {}
        };

        $scope.seleccionarTodosCheck = false;
		
		$scope.seleccionarTodos = function() {
            for (var i in $scope.listaConceptos) {
                if ($scope.seleccionarTodosCheck) {
                    $scope.conceptos[$scope.listaConceptos[i]] = true;
                } else {
                    $scope.conceptos[$scope.listaConceptos[i]] = false;
                }
            }

            $scope.filtros.conceptoPago = "";
            if ($scope.seleccionarTodosCheck) {
                for (var i in $scope.conceptos) {
                    if ($scope.conceptos[i] == true) {
                        $scope.filtros.conceptoPago = $scope.filtros.conceptoPago + "'" + i + "', ";
                    }
                }
            }
            console.log('Concepto : ', $scope.filtros.conceptoPago);
		};

        /**
         * Se encarga de recuperar la lista de clasificadores de puestos de
         * trabajo.
         *
         * @function
         */
        function getCpt() {
            var params = {
                count: -1,
                sortBy: "nivel",
                sortOrder: "ASC"
            };
            return cptService.listar(params);
        }

        /**
         * Se encarga de recuperar las configuraciones de los tramos salariales
         */
        function getConfiguracionTramos() {
            return cptService.getConfiguracionTramos($scope.filtros.anho, $scope.filtros.mes);
        }

        /**
         * Se encarga de crear una lista de niveles de cpt sin elementos 
         * repetidos.
         * 
         * @param {*} cpt 
         */
        function distinctCPT(cpt) {
            var data = [];
            var keyMap = {};
            for (var i = 0; i < cpt.rows.length; i++) {
                var c = cpt.rows[i];
                var idx = keyMap[c.nivel];
                if (idx != undefined) {
                    data[idx].values.push(c);
                } else {
                    if (c.nivel != null && c.nivel > 0) {
                        keyMap[c.nivel] = data.length;
                        data.push({
                            key: c.nivel,
                            values: [c]
                        });
                    }
                }
            }
            data.sort(sortByProperty('key'));
            return data;
        }

        var sortByProperty = function (property) {

            return function (x, y) {
        
                return ((x[property] === y[property]) ? 0 : ((x[property] > y[property]) ? 1 : -1));
        
            };
        
        };

        $scope.sorterFunc = function (cpt) {
            return parseInt(cpt.key);
        };
        $scope.conceptoSelected = [];
        $scope.onChangeConcepto = function (concepto) {
            //console.log($scope.conceptoSelected);
            
            $scope.filtros.conceptoPago = "";
            for(var i=0; i<$scope.conceptoSelected.length; i++){
                $scope.filtros.conceptoPago = $scope.filtros.conceptoPago + "'" + $scope.conceptoSelected[i] + "', ";
            }/*
            for (var i in $scope.conceptos) {
                if ($scope.conceptos[i] == true) {
                    $scope.filtros.conceptoPago = $scope.filtros.conceptoPago + "'" + i + "', ";
                }
            }*/
            console.log('Concepto : ', $scope.filtros.conceptoPago);
            //getData();
        };
/*
        $scope.onChangeConcepto = function (concepto) {
            
            for (var i in $scope.conceptos) {
                if ($scope.conceptos[i] == true) {
                    $scope.filtros.conceptoPago = $scope.filtros.conceptoPago + "'" + i + "', ";
                }
            }
            console.log('Concepto : ', $scope.filtros.conceptoPago);
            //getData();
        };*/

        /**
         * Se encarga de recuperar la lista de clasificadores de tramos
         * salariales.
         *
         * @function
         */
        function getCts() {
            var params = {
                count: -1,
                sortBy: "numeroTramo",
                sortOrder: "DESC",
                filters : {}
            };
            params.filters.anho = $scope.filtros.anho;
            params.filters.mes = $scope.filtros.mes;
            return ctsService.listar(params)
        }

        /**
         * Se encarga de recuperar la lista de programas.
         *
         * @function
         */
        function getProgramas() {
            var params = {
                count: -1,
                sortOrder: "DESC"
            };
            return ProgramaService.listar(params)
        }

        /**
         * Se encarga de recuperar la lista de subprogramas.
         *
         * @function
         */
        function getSubProgramas() {
            var params = {
                count: -1,
                sortOrder: "DESC"
            };
            return ProgramaService.listarSubProgramas(params)
        }

        /**
         * Se encarga de recuperar la lista de vinculaciones.
         *
         * @function
         */
        function getVinculaciones() {
            var params = {
                count: -1,
                sortOrder: "DESC"
            };
            return LegajoService.getVinculaciones(params)
        }

        /**
         * Se encarga de recuperar la lista de conceptos.
         *
         * @function
         */
        function getConceptos() {
            var params = {
                count: -1,
                sortOrder: "DESC"
            };
            return LegajoService.getConceptos(params)
        }

        function getAmbitos() {
            /*LegajoService.getAmbitos().then(
                function (response) {
                    $scope.listaAmbitos = response.data;
                },
                function () {
                    Message.error("Ocurrió un error al recuperar la lista de ámbitos.");
                }
            );*/
            return LegajoService.getAmbitos();
        }

        function getProgramasSubProgramas() {
            $scope.loading =  true;
            var promises = [];
            promises.push(getProgramas());
            promises.push(getSubProgramas());
            promises.push(getVinculaciones());
            promises.push(getConceptos());
            promises.push(getAmbitos());
            $q.all(promises).then(
                function (response) {
                    $scope.listaProgramas = response[0].data;
                    $scope.listaSubProgramas = response[1].data;
                    $scope.listaVinculaciones = response[2].data;
                    $scope.listaConceptos = response[3].data;
                    $scope.listaAmbitos = response[4].data;
                    for (var i in $scope.listaConceptos) {
                        $scope.conceptos[$scope.listaConceptos[i]] = false;
                    }
                    $scope.filtros.programa = "TODOS";
                    $scope.filtros.subprograma = "TODOS";
                    $scope.filtros.vinculacion = "PERMANENTE";
                    $scope.loading =  false;
                },
                function (response) {
                    Message.error("Ocurrio un error al recuperar los datos necesarios para construir la matriz");
                    $scope.loading =  false;
                });
        }

        /**
         * Se encarga de recuperar los datos de forma sincrona. Todos los promises se 
         * encolan y se dispara el handler unicamente cuando todos hayan finalizado. Si
         * Una petición falla, todo el conjunto falla.
         * 
         * @function
         */
        function getData() {
            console.log('getData');
            $scope.loading =  true;
            var promises = [];
            promises.push(getCts());
            promises.push(getCpt());
            promises.push(getCongruencia());
            promises.push(getConfiguracionTramos());
            promises.push(getProgramas());
            promises.push(getSubProgramas());
            promises.push(getAmbitos());
            promises.push(getResumenMatriz());
            $q.all(promises).then(
                function (response) {
                    $scope.cts = response[0].data;
                    $scope.cpt = distinctCPT(response[1].data);
                    $scope.matriz = response[2].data;
                    $scope.cptTramos = response[3].data;
                    $scope.listaProgramas = response[4].data;
                    $scope.listaSubProgramas = response[5].data;
                    $scope.listaAmbitos = response[6].data;
                    $scope.resumenMatriz = response[7].data;
                    $scope.congruentes = $scope.resumenMatriz['congruentes'];
                    $scope.subCategorizados = $scope.resumenMatriz['subCategorizados'];
                    $scope.sobreCategorizados = $scope.resumenMatriz['sobreCategorizados']; 
                    $scope.totalMatriz = $scope.resumenMatriz['total']; 
                    $scope.porcentajeCongruentes = $scope.resumenMatriz['porcentajeCongruentes']; 
                    $scope.porcentajeSubCategorizados = $scope.resumenMatriz['porcentajeSubCategorizados']; 
                    $scope.porcentajeSobreCategorizados = $scope.resumenMatriz['porcentajeSobreCategorizados'];
                    $scope.colorSobreCategorizados = $scope.resumenMatriz['colorSobreCategorizados'];
                    $scope.colorCongruentes = $scope.resumenMatriz['colorCongruentes'];
                    $scope.colorSubCategorizados = $scope.resumenMatriz['colorSubCategorizados'];
                    procesar();

                    if ($scope.colorSubCategorizados == 'red') {
                        $scope.textColorSubCategorizados = 'white';
                    } else {
                        $scope.textColorSubCategorizados = 'black';
                    }
                    if ($scope.colorSobreCategorizados == 'red') {
                        $scope.textColorSobreCategorizados = 'white';
                    } else {
                        $scope.textColorSobreCategorizados = 'black';
                    }
                    if ($scope.colorCongruentes == 'red') {
                        $scope.textColorCongruentes = 'white';
                    } else {
                        $scope.textColorCongruentes = 'black';
                    }

                    for (tramo in $scope.matriz) {
                        for (nivel in $scope.matriz[tramo]) {
                            var congruente = $scope.matriz[tramo][nivel].congruente;
                            if (congruente == 1) {
                                $scope.matriz[tramo][nivel].bgColor = '#b4f49f';
                            } else if (congruente == 0 || congruente == 2) {
                                $scope.matriz[tramo][nivel].bgColor = '#ffe0e6';
                            } else {
                                $scope.matriz[tramo][nivel].bgColor = '#d0d0f2';
                            }
                        }
                    }
                    $scope.loading =  false;
                },
                function (response) {
                    Message.error("Ocurrio un error al recuperar los datos necesarios para construir la matriz");
                    $scope.loading =  false;
                });
        }

        /** 
         * Handler del evento click del boton, se encarga de disparar la carga
         * de la matriz con los nuevos criterios de filtrado.
         */
        $scope.aplicarFiltrosBtn = function() {
            getData();
        };

        /**
         * Se encarga de aplicar los filtros cuand se dispara un evento
         * @param {Event} e 
         */
        function aplicarFiltros(e) {
            if(e == undefined || e.keyCode == 13) {
                getData();
            }
        }

        /** 
         * Se encarga de disparar la recarga de la matriz de congruencia.
         */
        $scope.actualizarMatriz = function () {
            getData();
        };

        $scope.getColorCongruenteNivel = function (index, key) {
            if ($scope.congruencia != undefined && $scope.total != undefined 
                    && $scope.total.nivel != undefined && $scope.total.nivel[key] != undefined) {
                var porcentaje = $scope.congruencia[index + 1] / $scope.total.nivel[key].cnt * 100;
                if (isNaN(porcentaje)) {
                    return 'red';
                }
                if (porcentaje < 50) {
                    return 'red';
                } else if (porcentaje >= 50 && porcentaje < 80) {
                    return 'yellow';
                } else {
                    return '#00ff00';
                }
            }
        };

        $scope.getTextColorCongruenteNivel = function (index, key) {
            if ($scope.congruencia != undefined && $scope.total != undefined 
                    && $scope.total.nivel != undefined && $scope.total.nivel[key] != undefined) {
                var porcentaje = $scope.congruencia[index + 1] / $scope.total.nivel[key].cnt * 100;
                if (isNaN(porcentaje)) {
                    return 'white';
                }
                if (porcentaje < 50) {
                    return 'white';
                } else {
                    return 'black';
                }
            }
        };
        
        /**
         * Se encarga de recuperar los datos referente a la congruencia de la matriz
         * de acuerdo a los criterios de filtrado.
         * @function
         */
        function getCongruencia() {
            return service.getMatrizCongruencia($scope.filtros);
        }

        function getResumenMatriz() {
            return service.getResumenMatriz($scope.filtros);
        }

        /**
         * Se encarga de pre-calcular el valor de la matriz;
         */
        function procesar() {

            console.log('procesar 1 : ', $scope.cts, ' - ', $scope.cts.rows);

            var total = 0;
            $scope.total = {
                tramo: {},
                nivel: {}
            };

            if ($scope.cts.rows == undefined || $scope.cts.rows.length == 0) {
                $scope.loading = false;
                return;
            }

            console.log('procesar 2');

            $scope.maximoTramo = $scope.cts.rows[0].numeroTramo;
            for (tramo in $scope.matriz) {
                $scope.total.tramo[tramo] = {
                    cnt: 0,
                    p: 0
                };
                for (nivel in $scope.matriz[tramo]) {
                    if (!$scope.total.nivel[nivel]) {
                        $scope.total.nivel[nivel] = {
                            cnt: 0,
                            p: 0
                        };
                    }
                    $scope.total.tramo[tramo].cnt += $scope.matriz[tramo][nivel].cantidad;
                    $scope.total.nivel[nivel].cnt += $scope.matriz[tramo][nivel].cantidad;
                    total += $scope.matriz[tramo][nivel].cantidad;
                }
            }
            $scope.total.nivel.cnt = total;
            //se calculan los porcentajes de los tramos
            for (tramo in $scope.matriz) {
                $scope.total.tramo[tramo].p = $scope.total.tramo[tramo].cnt * 100 / total;
            }
            // Se construyen los links para cada combinacion de nivel y tramo
            var cantidadCts = $scope.cts.count;
            var cantidadCpt = $scope.cpt.length;
            $scope.links ={};
            //$scope.links = new Array(cantidadCts);
            /*for (var i = 0; i < cantidadCts; i++) {
                $scope.links[i] = new Array(cantidadCpt);
            }*/

            //console.log('Matriz : ', $scope.matriz);

            for (tramo in $scope.matriz) {
                $scope.links[tramo] ={}
                var nroTramo = (tramo - 1) < 0 ? tramo : tramo - 1;
                for (cpt in $scope.matriz[tramo]) {
                    //console.log('Matriz : ', nroTramo, ' - ', cpt, ' - ', matriz[c.numeroTramo][i.key].cantidad);
                    //$scope.links[nroTramo][cpt - 1] = '#!/reportes/legajos?nivel=' + (cpt - 1)
                    $scope.links[tramo][cpt] = '#!/reportes/legajos?nivel=' + cpt
                        + '&numeroTramo=' + tramo //nroTramo*/
                        + '&anho=' + $scope.filtros.anho + '&mes=' + $scope.filtros.mes
                        + '&vinculacionFuncionario=' + $scope.filtros.vinculacion
                        + '&matriz=SI';
                        //+ '&conceptoPago=' + $scope.filtros.conceptoPago;
                }
            }
            //console.log($scope.links);
            
            $scope.cpt.find(
                function (row) {
                    $scope.congruencia[row.key] = 0;
                    $scope.pctCongruencia[row.key] = 0;
                    var cts = Object.keys($scope.matriz);
                    for(var i = 0; i < cts.length; i++){
                        if($scope.matriz[cts[i]][row.key].congruente == 1){
                            $scope.congruencia[row.key] += $scope.matriz[cts[i]][row.key].cantidad
                        }
                    }
                }
            )
        };

        /**
         * Se encarga de inicializar los filtros de la mantriz de congruencia.
         */
        function initFiltros() {
            $scope.filtros = {};
            var now = new Date();
            var mesActual = now.getMonth();
            if (mesActual == 0) {
                $scope.filtros.mes = "12";
                $scope.filtros.anho = now.getFullYear() - 1;
            } else {
                //se pone el mes como cadena porque el value del los selects se establecen como cadena.
                $scope.filtros.mes = (now.getMonth()) + "";
                $scope.filtros.anho = now.getFullYear();
            }
            $scope.filtros.vinculacion = "PERMANENTE";
            $scope.filtros.conceptoPago = "";
            $scope.filtros.idCptee="TODOS";
            $scope.filtros.idCpt="TODOS";
            $scope.filtros.idCeo="TODOS";
        }
        $scope.generarReporte = function(){
            var img =new Image();
            var parametros ={};
            html2canvas($('#matriz')[0]).then(function(canvas) {
                img = canvas.toDataURL("image/png");

                function saveAs(uri, filename) {
                    var link = document.createElement('a');
                    if (typeof link.download === 'string') {
                        document.body.appendChild(link); // Firefox requires the link to be in the body
                        link.download = filename;
                        link.href = uri;
                        link.click();
                        document.body.removeChild(link); // remove the link when done
                    } else {
                        location.replace(uri);
                    }
                }
                parametros.reporte = "MC";
                parametros.CPTEE=$scope.filtros.idCptee;
                parametros.CPT= $scope.filtros.idCpt;
                parametros.CEO= $scope.filtros.idCeo;
                parametros.imagen=img;

                var uri = img.replace(/^data:image\/[^;]/, 'data:application/octet-stream');

               /* var urlEncondeURIComponent = encodeURIComponent(angular.toJson(parametros));
                var urlFinal =App.REST_BASE +'/reportes/reporteexcell?parametros='+urlEncondeURIComponent;
                window.open(urlFinal);*/
                saveAs(uri, 'Matriz_congruencia.png');
            });




            $scope.datosAEnviar = [];

            //se setea el detalle
           /* for (i=30;i > 0 ;i--){
                $scope.total.nivel;
                $scope.cpt;
                $scope.datosAEnviar.push({
                    cero:  i,
                    uno:  $scope.matriz[i][1].cantidad,
                    dos:  $scope.matriz[i][2].cantidad,
                    tres:  $scope.matriz[i][3].cantidad,
                    cuatro:  $scope.matriz[i][4].cantidad,
                    cinco:  $scope.matriz[i][5].cantidad,
                    seis:  $scope.matriz[i][6].cantidad,
                    siete:  $scope.matriz[i][7].cantidad,
                    ocho:  $scope.matriz[i][8].cantidad,
                    nueve:  $scope.matriz[i][9].cantidad,
                    diez: $scope.matriz[i][10].cantidad,
                    cnt:$scope.total.tramo[i].cnt,
                    prct: Math.round($scope.total.tramo[i].p*100)/100 +"%"

                });
            }

            $scope.datosAEnviar.push({
                cero:  "PUESTOS",
                uno:  $scope.total.nivel[$scope.cpt[0].key].cnt,
                dos:  $scope.total.nivel[$scope.cpt[1].key].cnt,
                tres:  $scope.total.nivel[$scope.cpt[2].key].cnt,
                cuatro:  $scope.total.nivel[$scope.cpt[3].key].cnt,
                cinco:  $scope.total.nivel[$scope.cpt[4].key].cnt,
                seis:  $scope.total.nivel[$scope.cpt[5].key].cnt,
                siete:  $scope.total.nivel[$scope.cpt[6].key].cnt,
                ocho:  $scope.total.nivel[$scope.cpt[7].key].cnt,
                nueve:  $scope.total.nivel[$scope.cpt[8].key].cnt,
                diez: $scope.total.nivel[$scope.cpt[9].key].cnt,
                cnt:"",
                prct: "100%"

            });
            $scope.datosAEnviar.push({
                cero:  "CONGRUENCIA%",
                uno: $scope.congruencia[1] +"-"+ (Math.round( $scope.congruencia[1]/$scope.total.nivel[$scope.cpt[0].key].cnt*100))+"%",
                dos: $scope.congruencia[2] +"-"+ (Math.round($scope.congruencia[2]/$scope.total.nivel[$scope.cpt[1].key].cnt*100))+"%",
                tres: $scope.congruencia[3] +"-"+ (Math.round($scope.congruencia[3]/$scope.total.nivel[$scope.cpt[2].key].cnt*100))+"%",
                cuatro: $scope.congruencia[4] +"-"+ (Math.round($scope.congruencia[4]/$scope.total.nivel[$scope.cpt[3].key].cnt*100))+"%",
                cinco: $scope.congruencia[5] + "-"+(Math.round($scope.congruencia[5]/$scope.total.nivel[$scope.cpt[4].key].cnt*100))+"%",
                seis: $scope.congruencia[6] + "-"+(Math.round($scope.congruencia[6]/ $scope.total.nivel[$scope.cpt[5].key].cnt*100))+"%",
                siete: $scope.congruencia[7] +"-"+ (Math.round($scope.congruencia[7]/$scope.total.nivel[$scope.cpt[6].key].cnt*100))+"%",
                ocho: $scope.congruencia[8] +"-"+ (Math.round($scope.congruencia[8]/$scope.total.nivel[$scope.cpt[7].key].cnt*100))+"%",
                nueve: $scope.congruencia[9] + "-"+ (Math.round($scope.congruencia[9]/$scope.total.nivel[$scope.cpt[8].key].cnt*100))+"%",
                diez:$scope.congruencia[10] + "-"+(Math.round( $scope.congruencia[10]/$scope.total.nivel[$scope.cpt[9].key].cnt*100))+"%",
                cnt:"",
                prct: ""

            });


            parametros.parametros =$scope.datosAEnviar;*/
          /*  parametros.reporte = "MC";
            parametros.imagen=img;
            var urlEncondeURIComponent = encodeURIComponent(angular.toJson(parametros));
            var urlFinal =App.REST_BASE +'/reportes/reporteexcell?parametros='+urlEncondeURIComponent;
            window.open(urlFinal);*/

        };



        /**
         * Función que se ejecuta al cambiar un valor del filtro CPT
         * @param cpt - objeto que contiene los detalles del cpt seleccionado
         */
        $scope.onChangeCpt = function (cpt) {
            $scope.filtros.idCpt = cpt.id;
        };
        $scope.listarCpt = function () {
            cptService.listarTodos().then(
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
            $scope.filtros.idCptee = cptee.id;
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
         * Función que se ejecuta al cambiar un valor del filtro CPT EF
         * @param cpt_ef - objeto que contiene los detalles del cpt-f seleccionado
         */
        $scope.onChangeCptF = function (cpt_ef) {
            $scope.filtros.idCptEf = cpt_ef.id;
        };
        /**
         * Función encargada de listar todos los CPT-F para los filtros
         */
        $scope.listarCptF = function () {
            CptEFService.listarTodos().then(
                function (response) {
                    if (response.data) {
                        $scope.listaCptF = response.data.rows;
                        $scope.listaCptF.splice(0, 0, {"den": "TODOS", "id": "TODOS"});
                        $scope.cpt_ef = $scope.listaCptF[0];
                    }
                }
            );
        };


        /**
         * Costructor de la calse.
         * @constructor
         */
        (function initialize() {
            //se recuperan los datos.
            $scope.congruencia = [];
            $scope.pctCongruencia = [];
            initFiltros();
            getProgramasSubProgramas();
            $scope.listarCptF();
            $scope.listarCpt();
            $scope.listarCptEE();
            $scope.listarCeo();
        })();
    }
]);
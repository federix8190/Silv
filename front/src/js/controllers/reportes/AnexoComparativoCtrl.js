/*jslint node: true */
/*jslint nomen: true */
/*global angular, _ */
"use strict";

app.controller('AnexoComparativoCtrl', ['$scope', 'AnexoService', '$controller', '$rootScope',
    function ($scope, service, $controller, $rootScope) {

        /**
         * Service utilizdo para recuperar los datos y realizar las operaciones.
         * @field
         * @type {Object}
         */
        $scope.service = service;
        $scope.anhoInicio;
        $scope.mesInicio;
        $scope.anhoFinal;
        $scope.mesFinal;

        /*$.get("/cte-api/api/config/url_api_rest", 
	        function(data, status) {
	            $rootScope.urlApiRest = data.value;
	        }
	    );*/

        $scope.generarReporte = function (data) {
            //$scope.disabledButtonSave = true;
            if ($scope.anhoInicio == null || $scope.anhoInicio == undefined) {
            	Message.error("Debe completar todos los parametros del reporte"); 
            	return;
            }
			console.log('generarReporte : ', $scope.anhoInicio);

			service.generarReporte($scope.anhoInicio, $scope.mesInicio, 
				$scope.anhoFinal, $scope.mesFinal).then(function(response) {

        		console.log(response.data);
        		if (response.status == 200) {
            		Message.ok("El reporte se esta generando.");
            		$rootScope.idProceso = response.data;
            	} else {
            		Message.error("No se pudo realizar la operación"); 
            		return;
            	}
				
				setInterval(function() {
	            	var idProceso = $rootScope.idProceso;
	            	console.log('idProceso : ', $rootScope.idProceso);
	            	if (idProceso != undefined && idProceso != null) {
		    			$.get("/cte-api/api/anexos/comparativo/estado?idProceso=" + idProceso, 
		    				function(data, status) {
							  	console.log('checkFinished : ' + status + ' - ' + data);
							  	if (data.mensaje == 'T') {
							  		console.log('Procesos terminado');
							  		$rootScope.idProceso = null;
							  		$.ajax({
									    url : "/cte-api/api/anexos/comparativo/descargar?idProceso=" 
									    		+ idProceso,
									    //contentType: "application/vnd.ms-excel",
									    beforeSend : function(xhr) {
									        //Aquí podemos mostrar un loader
									    },
									    success : function(data, status, xhr) {
									        //Si se han devuelto datos
									        if (data != null && data != "FAIL") {
									            var b64Data = data;
									            //Obtenemos el tipo de los datos
									            var contentType = xhr.getResponseHeader("Content-Type");
									            //Obtenemos el nombre del fichero a desgargar
									            var filename = xhr.getResponseHeader("Content-disposition");
									            filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
									            var sliceSize = 512;
									            var byteCharacters = window.atob(b64Data);
									            var byteArrays = [];
									 
									            for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
									                var slice = byteCharacters.slice(offset, offset + sliceSize);
									                var byteNumbers = new Array(slice.length);
									                for (var i = 0; i < slice.length; i++) {
									                    byteNumbers[i] = slice.charCodeAt(i);
									                }
									                var byteArray = new Uint8Array(byteNumbers);
									                byteArrays.push(byteArray);
									            }
									            //Tras el procesado anterior creamos un objeto blob
									            var blob = new Blob(byteArrays, {
									                type : contentType
									            });
									            // IE 10+
									            if (navigator.msSaveBlob) {
									                navigator.msSaveBlob(blob, filename);
									            } else {
									            	//Descargamos el fichero obtenido en la petición ajax
									            	console.log('descargando archivo');
									                var url = URL.createObjectURL(blob);
									                console.log('descargando archivo');
									                var link = document.createElement('a');
									                link.href = url;
									                link.download = filename;
									                document.body.appendChild(link);
									                link.click();
									                document.body.removeChild(link);
									            }
									        }
									    },
									    complete : function(xhr, status) {
									        if (xhr.readyState == 4) {
									            if (xhr.status == 200) {
									                var contentLength = xhr.getResponseHeader("Content-Length");
									                if (contentLength && contentLength == 0)
									                    alert("No se ha podido descargar el archivo");
									            }
									        }
									    }
									});
							  	}
							}
						);
					} else {
						console.log('No hay procesos pendientes');
					}
				}, 10 * 1000); 
			},function(response) {
			  	Message.error("No se pudo realizar la operación"); 
			}).catch(function(error){
			  	console.log(error);
			});
            
        };

        $scope.checkFinished=function(response) {
        	service.getEstadoReporte(3).then(function(response) {
        	console.log('checkFinished : ' + response);
        	window.setTimeout(function(){
        		$scope.checkFinished(response)}, 10000);
        	}
        )
    };
}]);
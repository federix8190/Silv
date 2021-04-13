"use strict";

app.controller('DashboardCtrl', ['$scope', 'AnexoService', '$location', '$controller',
    function ($scope, service, $location, $controller) {

    var MESES = ['', 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 
            'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];

    service.getUltimoPeriodo().then(
        function(response) {
            if (response.data) {
                console.log('ultimo-periodo : ', response.data.anho + ' - ' + response.data.mes);
                $scope.periodo = MESES[response.data.mes] + ' de ' + response.data.anho;
            } else {
                console.error('Error al traer el ultimo-periodo ' + response.result);
            }
        }
    );

}]);
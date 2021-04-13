
/**
 * directiva Base para las grillas con filtrado
 */
app.directive('listaDirective2', ['$compile', function ($compile) {
    return {
        link: function( scope, element, attributes ) {
            element
                .find("div.form")
                .append( '&nbsp;<a class="btn btn-default" onclick="aplicarFiltros()"><i class="fa fa-check"></i>&#xA0;Aplicar</a>' )
        }
    };
}]);
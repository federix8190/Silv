/**
 * Esta directiva se encarga de validar que el valor ingresado en un Input sea un
 * número válido
 * @class
 * @name validateNumber
 * @author <a href="mailto:marcelo.szcerba@konecta.com.py">Marcelo Szcerba</a>
 */
app.directive('validateNumber', function() {

    return {
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {
                if (viewValue) {
                    var FLOAT_REGEXP = /^\-?\d+((\.|\,)\d+)?$/;
                    if (FLOAT_REGEXP.test(viewValue)) {
                        ctrl.$setValidity('numerico', true);
                        return viewValue;
                    } else {
                        ctrl.$setValidity('numerico', false);
                        return viewValue;
                    }
                } else {
                    ctrl.$setValidity('numerico', true);
                }
            });
        }
    };
});

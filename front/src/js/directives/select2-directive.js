/** 
 * @class
 */
app.directive('select', [function () {
    var $el = null;
    return {
        restrict: 'E',
        transclude: true,
        scope: false,
        link: function (scope, element, attrs, ctrl, transclude) {
            var model = $(element).attr("ng-model");

            transclude(scope, function (clone) {
                element.append(clone);
                setTimeout(function () {
                    $el = $(element).select2({
                        tags: "true",
                        placeholder: "Seleccione un elemento",
                    });
                }, 700);
            });

            scope.$watch(model, function (val) {
                var value = val;
                if (typeof val == "object") {
                    for (var key in val) {
                        value = val[key];
                    }
                }
                $(element).val(value).trigger('change.select2');
            });
        }
    }
}]);
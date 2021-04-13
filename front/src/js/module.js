/*
 * App setup del proyecto, se registra el módulo y sus depenencias
 */
var app = angular.module(App.MODULE_NAME, [
    'ngRoute',  //modulo para el ruteo
    'ngTasty', // modulo para implementar grillas paginadas
    'ngFileUpload', // modulo que permite la carga de archivos
    'ngSanitize', 
    'ngCsv'
]);

app.directive('capitalize', function() {
    return {
      require: 'ngModel',
      link: function(scope, element, attrs, modelCtrl) {
        var capitalize = function(inputValue) {
          if (inputValue == undefined) inputValue = '';
          var capitalized = inputValue.toUpperCase();
          if (capitalized !== inputValue) {
            // see where the cursor is before the update so that we can set it back
            var selection = element[0].selectionStart;
            modelCtrl.$setViewValue(capitalized);
            modelCtrl.$render();
            // set back the cursor after rendering
            element[0].selectionStart = selection;
            element[0].selectionEnd = selection;
          }
          return capitalized;
        }
        modelCtrl.$parsers.push(capitalize);
        capitalize(scope[attrs.ngModel]); // capitalize initial value
      }
    };
  });
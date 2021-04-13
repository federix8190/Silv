app.directive('ul', function($compile) {
    return {
        restrict: 'E',
        link: function(scope, element, attrs) {
           if(attrs.class === 'pagination'){

               var elCopyDer = angular.copy(element[0].childNodes[5].childNodes[1]);
               var parentCopyDer = angular.copy(element[0].childNodes[5]);

               var elCopyIzq = angular.copy(element[0].childNodes[1].childNodes[1]);
               var parentCopyIzq = angular.copy(element[0].childNodes[1]);

               elCopyDer.removeAttribute("ng-click");
               elCopyDer.setAttribute("ng-click", "page.get(pagination.size)");
               elCopyDer.text = '»»';

               elCopyIzq.removeAttribute("ng-click");
               elCopyIzq.setAttribute("ng-click", "page.get(1)");
               elCopyIzq.text = '««';

               parentCopyDer.childNodes[1].remove();
               parentCopyDer.insertBefore(elCopyDer, parentCopyDer.childNodes[1]);

               parentCopyIzq.childNodes[1].remove();
               parentCopyIzq.insertBefore(elCopyIzq, parentCopyIzq.childNodes[1]);

               element[0].append(parentCopyDer);
               element[0].prepend(parentCopyIzq);
               $compile(element.contents())(scope);

           }
           
        }
    };
});

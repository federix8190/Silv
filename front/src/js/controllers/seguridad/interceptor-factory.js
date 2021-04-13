/**
 * @class
 * Interceptor que se encarga de configurar los headers para los request. Añade
 * el header Autorization con el valor del access token del keycloak.
 * 
  */
app.factory('authInterceptor', function ($q, $location) {
    return {
        response: function (response) {
            //console.log('authInterceptor : ', $location.$$path);
            return response;
        },
        responseError: function (response) {
            console.log('Status code : ' + response.status);
            if (response.status == 403) {
                localStorage.setItem('userId', -1);
                localStorage.setItem('user', null);
                localStorage.setItem('permisos', null);
                localStorage.clear();                
                $location.path('/login');
            } else if (response.status == 401) {
                if ($location.$$path == '/login') {
                    Message.error(response.data.mensaje);
                } else {
                    Message.error("No tiene permiso para realizar esta operación");
                }
            }
            return $q.reject(response);
        }
    };
});
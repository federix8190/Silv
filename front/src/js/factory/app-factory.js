app.factory('AppServices', function () {
    return {
        tienePermiso: function (url) {
            if (url.substr(url.length - 1) == '/') {
                url = url.substr(0, url.length - 1);
            }
            console.log('Path : ', url);
            var permisos = localStorage.getItem('permisos');
            if (permisos !== null && permisos.length > 0) {
                var listaPermisos = permisos.split(',');
                for (var i = 0; i < listaPermisos.length; i++) {
                    var urlPermiso = listaPermisos[i];
                    if (urlPermiso == url) {
                        return true;
                    }
                }
            }
            return false;
        },
        getUrlGenerica: function (url) {
            var urlGenerica = '';
            var tokens = url.split('/');
            for (var i = 0; i < tokens.length; i++) {
                var token = tokens[i];
                if (token.length > 0) {
                    if (isNaN(token)) {
                        urlGenerica += "/" + token;
                    } else {
                        urlGenerica += "/" + '{id}';
                    }
                }
            }
            return urlGenerica;
        }
    };
});
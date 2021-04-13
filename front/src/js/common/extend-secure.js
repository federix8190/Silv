
/**
 * Se encarga de checkear validez del token de seguirdad de shiro.
 */
function checkExpiration($location) {
    setInterval(function () {
        var expira = localStorage.getItem('expira');
        var now = new Date();
        if (now > expira) {
            localStorage.clear();
            localStorage.setItem('userId', -1);
            localStorage.setItem('user', null);
            $location.path('/login');
        }
    }, 60000);
}

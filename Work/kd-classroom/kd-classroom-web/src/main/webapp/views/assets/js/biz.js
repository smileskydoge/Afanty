
function isCookieTimeOut() {
    var cookieVal = $.cookie('pcLoginUserObj');
    if (cookieVal === undefined || cookieVal === null || cookieVal === '') {
        window.location.href = '/views/html/teacher/login.html';
        return true;
    }
    return false;
}
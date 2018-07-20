var sign = 'booksroo_classroom_1528179259557';

var rootPath = getRootPath();
// console.info('rootPath: ' + rootPath);

//js获取项目根路径，如： http://localhost:8083/uimcardprj
function getRootPath() {
    // //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    // var curWwwPath = window.document.location.href;
    // //获取主机地址之后的目录，如： /uimcardprj/share/meun.jsp
    // var pathName = window.document.location.pathname;
    // var pos = curWwwPath.indexOf(pathName);
    // //获取主机地址，如： http://localhost:8083
    // var localhostPath = curWwwPath.substring(0, pos);
    // //获取带"/"的项目名，如：/uimcardprj
    // var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    // var root = localhostPath + projectName;
    //
    // if (root.indexOf('kd-classroom')) return root;
    //
    // return '';

    return '';
    // return '/kd-classroom-web';
}

function isFail(resp) {
    // if (!resp.success) {
    //     layer.msg('操作失败: '+resp.message);
    //     return true;
    // }
    // return false;
    return isFailDialog(resp);
}

function isFailDialog(resp) {
    if (!resp.success) {
        promptTitleDialog('操作失败', resp.message);
        return true;
    }
    return false;
}


function promptDialog(msg) {
    promptTitleDialog('提示', msg);
}

function promptFailDialog(msg) {
    promptTitleDialog('操作失败', msg);
}

function promptTitleDialog(title, msg) {
    layer.open({
        title: title,
        content: msg
    });
}

function isEmptyStr(name) {
    return name === undefined || name == null || name === '';
}

function emptyToStr(obj) {
    if (obj === undefined || obj === null || obj === '') return '-';

    return obj;
}

//获取url带的参数
function getUrlParam() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

function transferNoToCh(no) {
    if (no === 1) return '一';
    if (no === 2) return '二';
    if (no === 3) return '三';
    if (no === 4) return '四';
    if (no === 5) return '五';
    if (no === 6) return '六';
    if (no === 7) return '七';
    if (no === 8) return '八';
    if (no === 9) return '九';
    if (no === 10) return '十';

    return '';
}
/**
 * 读取cookie
 * @method get
 * @param {String} name cookie名
 * @param {Object} [o] 参数
 *   @param {Function(value)} [o.encode=encodeURIComponent] 编码函数
 *   @param {Function(value)} [o.decode=decodeURIComponent] 解码函数
 * @return {String} cookie值
 */
var getCookie = function(name, o) {
    name = '; ' + encodeURIComponent(name) + '=';
    var cookie = '; ' + document.cookie, beginPos = cookie.indexOf(name), endPos;

    if (beginPos === -1) { return null; }

    beginPos += name.length;
    endPos = cookie.indexOf(';', beginPos);
    if (endPos === -1) { endPos = cookie.length; }

    return decodeURIComponent( cookie.substring(beginPos, endPos) );
}

/**
 * 写入cookie
 * @method set
 * @param {String} name cookie名
 * @param {String} value cookie值
 * @param {Object} [o] 参数
 *   @param {String} [o.domain] 所在域
 *   @param {String} [o.path] 所在路径
 *   @param {Date|Number|String} [o.expires] 过期时间。可传入带单位的时间值，例如'1 hour'，
 *     支持的时间单位有sec、min、hour、day，month、year
 *   @param {Boolean} [o.secure] 是否只在https连接中有效
 *   @param {Function(value)} [o.encode=encodeURIComponent] 编码函数
 */
var setCookie = function (name, value,expires,domain,path,secure) {

    // 时间单位
    var TimeUnit = {
        SEC: 1000,
        MIN: 60 * 1000,
        HOUR: 60 * 60 * 1000,
        DAY: 24 * 60 * 60 * 1000,
        MONTH: 30 * 24 * 60 * 60 * 1000,
        YEAR: 365 * 24 * 60 * 60 * 1000
    };

    // 匹配带单位的时间值
    var rTime = /^(\d+(?:\.\d+)?)\s*([a-z]+?)s?$/i;
    function isDate(value) { return toString.call(value) === '[object Date]'; }
    // 转换为时间数字
    function toTimeSpan(val) {
        if ( rTime.test(val) ) {
            var unit = RegExp.$2.toUpperCase();
            // 无此时间单位，抛出异常
            if ( !TimeUnit.hasOwnProperty(unit) ) {
                throw new Error('not such time unit(' + RegExp.$2 + ')');
            }

            return parseFloat(RegExp.$1) * TimeUnit[unit];
        }

        return parseFloat(val) || 0;
    }

    var expires = expires, text = encodeURIComponent(name) + '=' + encodeURIComponent(value);
    if (typeof expires === 'string') { expires = toTimeSpan(expires); }
    if (typeof expires === 'number') {
        var d = new Date();
        d.setTime(d.getTime() + expires);
        expires = d;
    }
    try{
        if ( isDate(expires) ) { text += '; expires=' + expires.toUTCString(); }
    }catch (ex){}

    if (path) { text += '; path=' + path; }
    if (domain) { text += '; domain=' + domain; }
    if (secure === true) { text += '; secure'; }

    document.cookie = text;
}

/*
 * 删除cookie
 * 删除的cookie名
 * 要删除的cookie有效路径
 * 要删除的cookie的域名
 */
var deleteCookie = function(name,path,domain) {
    if (exports.__getCookie(name)) {
        document.cookie = name + "=" +
        ((path) ? "; path=" + path : "") +
        ((domain) ? "; domain=" + domain : "") +
        "; expires=Thu, 1 Jan 1970 00:00:00 UTC";//Thu, 01-Jan-70 00:00:01 GMT";
    }
}

/*
 * 禁用cookie
 */
var cookiesDisabled = function() {
    var result=true;
    // some browser versions support this - use it if possible
    if (navigator.cookiesEnabled)
        return false;
    // else try to set and read a cookie
    document.cookie = "testcookie=yes;";
    var cookieSet = document.cookie;
    if (cookieSet.indexOf("testcookie=yes") > -1) {
        result=false;
    }
    document.cookie = "testcookie=;expires=;";
    return result;
}
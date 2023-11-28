/**
 * 쿠키 관리자
 *
 * @example 쿠키 가져오기: cookieManager.getCookie('the_cookie');
 */
var cookieManager = {
    // 쿠키 설정
    setCookie : function(name, value, expireDay = null, path = null) {
        var secure = false;
        var expireDate = new Date();
        if (expireDay != null) expireDate.setDate(expireDate.getDate() + expireDay);
        document.cookie = name + "=" + escape(value)
            + ((expireDay == null) ? "" : ("; expires=" + expireDate.toGMTString()))
            + ((path == null) ? "" : ("; path=" + path))
            + "; domain=localhost"
            + "; secure"
            + ";SameSite=None";
    },
    // 쿠키 가져오기
    getCookie : function(name) {
        var nameOfCookie = name + "=";
        var x = 0;
        while (x <= document.cookie.length) {
            var y = (x + nameOfCookie.length);
            if (document.cookie.substring(x, y) == nameOfCookie) {
                if ((endOfCookie = document.cookie.indexOf(";", y)) == -1)
                    endOfCookie = document.cookie.length;
                return unescape(document.cookie.substring(y, endOfCookie));
            }
            x = document.cookie.indexOf(" ", x) + 1;
            if (x == 0) break;
        }
        return "";
    },
    // 쿠키 삭제
    clearCookie : function(name) {
        var expireDate = new Date();
        expireDate.setDate(expireDate.getDate() - 1);
        document.cookie = name + "=" + "; expires=" + expireDate.toGMTString();
    },
    // PCID 생성
    makePCID : function(name, length) {
        var today = new Date();
        var expireDay = 365 * 10;
        var cookie = this.getCookie(name);
        if (cookie) return false;
        var values = new Array();
        for (var i=0; i<length; i++) {
            values[i] = "" + Math.random();
        }
        var value = today.getTime();
        for (i=0; i<length; i++) {
            value += values[i].charAt(2);
        }
        this.setCookie(name, value, expireDay, "/", "localhost");
    }
};

cookieManager.makePCID("PCID", 10);

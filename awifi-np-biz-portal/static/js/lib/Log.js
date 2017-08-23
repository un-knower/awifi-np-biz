/**
 * 日志输出工具
 * @author: 卢海宁<luhaining1226@126.com>
 * @create: 2015-01-27
 */
var Log = (function () {
    // 是否可用
    var enable = false;
    // 初始化
    var init = function () {

    }
    // 获取时间
    var getTime = function () {
        var d = new Date();
        var s = d.toLocaleString();
        return s;
    }
    // 获取内容
    var getInfo = function (info, title) {
        var msg = '------------------\r\n' + (title || '日志') + " | " + getTime() + '\r\n------------------\r\n' + info + '\r\n';
        return msg;
    }

    return {
        open: function (str, title) {
            enable = true;
        },
        close: function () {
            enable = false;
        },
        log: function (str, title) {
            if (!enable) {
                return;
            }
            if (window.console) {
                console.log(getInfo(str, title));
            }
        }
    }
})();
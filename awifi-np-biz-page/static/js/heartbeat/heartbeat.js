/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-06-28 20:27
 * 创建作者: 许小满 17757128113@189.cn
 * 文件名称: heartbeat
 * 版本: v1.0
 * 功能: 每个1秒向后端发送请求
 * 修改记录:
 */
var pageHeartbeat = {
    url: '/media/json/heartbeat.json',
    heartbeat: function (time) {
        var _this = this;
        var option = {};
        var url = _this.url + '?t=' + time;
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'JSON',
            header: {
                'cache-control': 'no-cache'
            },
            data: option,
            //async:false,//true 异步(默认)、false 同步
            success: function (data) {

            },
            error: function (XHR, textStatus, errorThrown) {

            },
            complete: function (XHR, textStatus) {

            }
        });
    }
};
try{
    $(document).ready(function () {
        /*setInterval(function(){
            pageHeartbeat.heartbeat(new Date().getTime());
        }, 1000);*/
    });
}catch(e){}
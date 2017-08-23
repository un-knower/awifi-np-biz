/******************************
 * 版权所有：浙江信产无线运营分公司 保留所有权利
 * 创建日期: 2016/2/19 10:08
 * 创建作者：XUXIAOMAN
 * 文件名称：pv.js
 * 版   本： 1.0
 * 功   能：
 *****************************************/

var pvCount = {
    url: '/pv/push',
    getVisitDate: function () {//获取访问日期
        var date = new Date();//当前时间
        var year = date.getFullYear();//当前年份
        var month = date.getMonth() + 1;//当前月份
        if (month <= 9) {
            month = '0' + month;
        }
        var day = date.getDate();//当前天
        if (day <= 9) {
            day = '0' + day;
        }
        var hours = date.getHours();//当前时
        if (hours <= 9) {
            hours = '0' + hours;
        }
        var visitDate = year.toString() + month.toString() + day.toString() + hours.toString();//访问时间，格式：yyyyMMddHH24
        return visitDate;
    },
    count: function () {
        var _this = this;
        var option = {
            customerId: _CUSTOMER_ID,//客户id
            devId: _DEV_ID,//设备ID
            pageType: _PAGE_TYPE,//站点页面类型:1 引导页、2 认证页、3 过渡页、4 导航页
            num: _NUM,//站点页面序号
            userMac: _USER_MAC,//用户终端MAC
            visitDate: _this.getVisitDate()//访问日期
        };
        $.ajax({
            url: _this.url,
            type: 'POST',
            dataType: 'JSON',
            header: {
                'cache-control': 'no-cache'
            },
            data: option,
            //async:false,//true 异步(默认)、false 同步
            success: function (data) {
                /*if(data.result != 'OK'){
                 alert(data.message);
                 return;
                 }
                 alert('pv 发送成功！');*/
            },
            error: function (XHR, textStatus, errorThrown) {

            },
            complete: function (XHR, textStatus) {

            }
        });
    }
};

try{
    pvCount.count();
}catch(e){}

//$(document).ready(function () {
    //pvCount.count();
//});



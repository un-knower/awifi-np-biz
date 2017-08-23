/**
 * Created by xujiapan on 2017/7/7.
 */

var info_url = '/timebuysrv/time/info'; //时间组件全信息接口
var login_url = '/timebuysrv/login/save';   //用户登录接口
var sms_send_url = '/timebuysrv/sms//send'; //发送验证码接口
var access_auth_url = '/timebuysrv/access/auth'; //正式放通/临时放通接口
var get_pkg_url = '/timebuysrv/time/pkg/get'; //领取商户免费套餐接口
var get_paylist_url = '/timebuysrv/time/consume'; //个人消费列表接口

var timeInterval;
var auth_code_timer = null;
var auth_code_timing = 60;

var paylist_pagecount = 10;
var paylist_pageno = 0;

var timbuy_info = {
    buyurl:'',          //时长购买地址
    vipmsg:'尊敬的电信乐享套餐用户您好，您可享受爱WiFi免费上网服务。',
    buyoutmsg:'尊敬的用户您好，如有爱WiFi上网需求，请联系企业管理员',
    notimemsg:'您暂时无法上网，请购买上网时长'
};

$().ready(function () {

    $('.container').css('width', $(window).width());
    $('.container').css('min-height', $(window).height());
    $('.nav-title').html(_CUSTOMER_NAME ? _CUSTOMER_NAME : 'aWiFi');

    getTimeInfo();
});

function getTimeInfo() {
    //上传至正式环境测试时显示如下代码
    if(_DEV_ID == '{@devId@}') {
        return;
    };

    var usertype = _LOGIN_TYPE;  //NEW_USER:新用户  EXEMPT_AUTH_USER:免认证用户
    if(usertype == 'authed') {
        usertype = 'EXEMPT_AUTH_USER';
    }else if(usertype == 'unauth') {
        usertype = 'NEW_USER';
    }
    var option = {
        logId:_GLOBAL_KEY,
        deviceId:_DEV_ID,
        mobilePhone:_USER_PHONE,
        userMac:_USER_MAC,
        userIP:_USER_IP,
        gwAddress:_GW_ADDRESS,
        gwPort:_GW_PORT,
        nasName:_NAS_NAME,
        userType:usertype,
        token:'',
        url:'',
    };

    $.ajax({
        type: 'POST',
        url: info_url,
        data: option,
        success: function (data) {
            // console.log(data);
            if(data.code == '0') {
                var temp_data = data.data;

                //根据user对象判断是否一键登录
                if(temp_data.user) {

                    //获取时长购买buyurl
                    if(temp_data.buyurl) {
                        timbuy_info.buyurl = temp_data.buyurl;
                    }
                    if(temp_data.timeInfo) {
                        //判断是否为电信CRM
                        if(temp_data.timeInfo.vip) {

                            $('.vip-msg').html(timbuy_info.vipmsg);
                            $('.content-login').css('display', 'none');
                            $('.content-fastlogin').css('display', 'none');
                            $('.content-vip').css('display', 'block');
                            $('.content-buyout').css('display', 'none');
                            $('.content-timebuy').css('display', 'none');
                            $('.content-changebtn .changeaccount').css('display', 'block');
                            $('.content-changebtn .timebuydetail').css('display', 'block');
                            $('.content-details').css('display', 'none');
                        }else {

                            //判断是否为园区买断
                            if(temp_data.timeInfo.buyout) {
                                if(temp_data.timeInfo.endTime != null && temp_data.timeInfo.endTime != 'undefined') {
                                    if((temp_data.timeInfo.endTime - temp_data.nowDate) > 0) { //计算剩余时长
                                        lefttime = temp_data.timeInfo.endTime - temp_data.nowDate;

                                        clearInterval(timeInterval);
                                        timeInterval = setInterval(
                                            function() {
                                                var _html = '';
                                                if (lefttime <= 0) {
                                                    clearInterval(timeInterval);
                                                    _html = timbuy_info.buyoutmsg;
                                                } else {
                                                    var d = Math.floor(lefttime
                                                        / 1000 / 60 / 60 / 24);
                                                    var h = Math.floor(lefttime
                                                        / 1000 / 60 / 60 % 24);
                                                    var m = Math
                                                        .floor(lefttime / 1000 / 60 % 60);
                                                    var s = Math
                                                        .floor(lefttime / 1000 % 60);
                                                    h = h < 10 ? '0' + h : h;
                                                    m = m < 10 ? '0' + m : m;
                                                    s = s < 10 ? '0' + s : s;
                                                    _html = '您的剩余上网时长：' + d + '天' + h
                                                        + ':' + m + ':' + s;
                                                }
                                                $(".buyout-msg").html(_html);
                                                lefttime = lefttime - 1000;
                                                timbuy_info.lefttime = lefttime;
                                                // console.log(_state.lefttime);
                                            }, 1000);

                                        //如果有时长 直接做放通操作
                                        accessAuth('0');

                                    }else {
                                        $(".buyout-msg").html(timbuy_info.buyoutmsg);
                                    }
                                }else {
                                    $(".buyout-msg").html(timbuy_info.buyoutmsg);
                                }

                                $('.content-login').css('display', 'none');
                                $('.content-fastlogin').css('display', 'none');
                                $('.content-vip').css('display', 'none');
                                $('.content-buyout').css('display', 'block');
                                $('.content-timebuy').css('display', 'none');
                                $('.content-changebtn .changeaccount').css('display', 'block');
                                $('.content-changebtn .timebuydetail').css('display', 'block');
                                $('.content-details').css('display', 'none');

                            }else {
                                if(temp_data.timeInfo.endTime != null && temp_data.timeInfo.endTime != 'undefined') {
                                    if((temp_data.timeInfo.endTime - temp_data.nowDate) > 0) { //计算剩余时长
                                        lefttime = temp_data.timeInfo.endTime - temp_data.nowDate;

                                        timeOutInterval(lefttime);

                                        //如果有时长 直接做放通操作
                                        accessAuth('0');

                                    }else {
                                        $(".timebuy-msg").html(timbuy_info.notimemsg);
                                    }
                                }else {
                                    $(".timebuy-msg").html(timbuy_info.notimemsg);
                                }


                                //判断是否获取免费礼包
                                if(temp_data.timeInfo.canGetFreePkg) {
                                    $('.content-timebuy .getfreepkg').css('display', 'block');
                                }else {
                                    $('.content-timebuy .getfreepkg').css('display', 'none');
                                }

                                $('.content-login').css('display', 'none');
                                $('.content-fastlogin').css('display', 'none');
                                $('.content-vip').css('display', 'none');
                                $('.content-buyout').css('display', 'none');
                                $('.content-timebuy').css('display', 'block');
                                $('.content-changebtn .changeaccount').css('display', 'block');
                                $('.content-changebtn .timebuydetail').css('display', 'block');
                                $('.content-details').css('display', 'none');
                            }

                        }
                    }else {

                    }
                }else {
                    if(data.data.canFastLogin) { //一键登录
                        $('.fastlogin-user').html("当前用户：" + _USER_PHONE);

                        $('.content-login').css('display', 'none');
                        $('.content-fastlogin').css('display', 'block');
                        $('.content-vip').css('display', 'none');
                        $('.content-buyout').css('display', 'none');
                        $('.content-timebuy').css('display', 'none');
                        $('.content-changebtn .changeaccount').css('display', 'block');
                        $('.content-changebtn .timebuydetail').css('display', 'none');
                        $('.content-details').css('display', 'none');
                    }else {                         //登录
                        $('.content-login').css('display', 'block');
                        $('.content-fastlogin').css('display', 'none');
                        $('.content-vip').css('display', 'none');
                        $('.content-timebuy').css('display', 'none');
                        $('.content-changebtn .changeaccount').css('display', 'none');
                        $('.content-changebtn .timebuydetail').css('display', 'none');
                        $('.content-details').css('display', 'none');
                    }
                }
            }
        },
        error: function () {

        }
    });
}


function timeOutInterval(time) {
    var lefttime = time;

    clearInterval(timeInterval);
    timeInterval = setInterval(
        function() {
            var _html = '';
            if (lefttime <= 0) {
                clearInterval(timeInterval);
                _html = timbuy_info.notimemsg;
            } else {
                var d = Math.floor(lefttime
                    / 1000 / 60 / 60 / 24);
                var h = Math.floor(lefttime
                    / 1000 / 60 / 60 % 24);
                var m = Math
                    .floor(lefttime / 1000 / 60 % 60);
                var s = Math
                    .floor(lefttime / 1000 % 60);
                h = h < 10 ? '0' + h : h;
                m = m < 10 ? '0' + m : m;
                s = s < 10 ? '0' + s : s;
                _html = '您的剩余上网时长：' + d + '天' + h
                    + ':' + m + ':' + s;
            }
            $(".timebuy-msg").html(_html);
            lefttime = lefttime - 1000;
            timbuy_info.lefttime = lefttime;
            // console.log(_state.lefttime);
        }, 1000);
}

/**
 * 触发事件
 * 切换账号
 */
function changeToAccount() {
    // alert('切换账号');
    $('.content-login').css('display', 'block');
    $('.content-fastlogin').css('display', 'none');
    $('.content-vip').css('display', 'none');
    $('.content-buyout').css('display', 'none');
    $('.content-timebuy').css('display', 'none');
    $('.content-changebtn .changeaccount').css('display', 'none');
    $('.content-changebtn .timebuydetail').css('display', 'none');
    $('.content-details').css('display', 'none');

    document.getElementById('icon').className = 'close';
}

/**
 * 触发事件
 * 打开消费详情
 */
function openDetail() {
    if($('.content-details').css('display') == 'none') {
        $('.content-details').css('display', 'block');
        document.getElementById('icon').className = 'open';

        getPayList(paylist_pagecount, paylist_pageno);

    }else {
        $('.content-details').css('display', 'none');
        document.getElementById('icon').className = 'close';

        var inserthtml = ("<li id='paymore'><div class='detail-btn' onClick='pageChange()'><div class='detail-img'></div></div></li>");
        document.getElementById('user-timemsg').innerHTML = inserthtml;

        paylist_pageno = 0;
    }
}

/**
 * 触发事件
 * 消费详情分页
 */
function pageChange() {
    getPayList(paylist_pagecount, paylist_pageno);
}

/**
 * 触发事件
 * 一键登录
 */
function getFastLogin() {
    var phone = _USER_PHONE;
    getLoginSave(phone, '');
}

/**
 * 触发事件
 * 登录校验
 */
function getLogin() {
    var temp_phone = $("#acount").val();
    var temp_code = $("#code").val();
    // alert(temp_phone + "~~~~~~" + temp_code);

    if(temp_phone === '' || !(/^(1[3|4|5|7|8][0-9][0-9]{8})?$/).test(temp_phone)){
        alert('请输入正确的手机号码');
        return;
    }
    if(temp_code === '' || !(/^\d{4}$/).test(temp_code)){
        alert('请输入正确的验证码');
        return;
    }

    getLoginSave(temp_phone, temp_code);
}

function check_timer(){
    auth_code_timing = auth_code_timing - 1;
    $('#smscode').val('重新获取' + '(' + auth_code_timing + ')');

    if(auth_code_timing <= 0){
        clearInterval(auth_code_timer);
        $('#smscode')[0].className = '';
        $('#smscode').val('获取验证码');
        auth_code_timing = 60;
    }
}

function getCode() {
    var temp_phone = $("#acount").val();
    if(temp_phone === '' || !(/^(1[3|4|5|7|8][0-9][0-9]{8})?$/).test(temp_phone)){
        alert('请输入正确的手机号码');
        return;
    }
    if(auth_code_timing != 60){
        return;
    }

    $('#smscode')[0].className = 'inactive';
    check_timer();
    auth_code_timer = setInterval(check_timer, 1000);
    sendSms(temp_phone);
}

/**
 * 触发事件
 * 获取免费礼包
 */
function getFreePkg() {
    getPkg();
}

function getFreePkgSuccess(data) {
    alert('领取成功');
    var lefttime = data.end_time - data.nowTime;
    timeOutInterval(lefttime);
}

/**
 * 触发事件
 * 购买时长
 */
function timeBuy() {
    //判断网络是否能上网操作
    if(isOnline) {
        if(timbuy_info.buyurl) {
            window.location.href = timbuy_info.buyurl;
        }
    }else {
        accessAuth('1');
    }
}

/**
 * 登录接口
 * @param phone
 * @param code
 * @param func
 */
function getLoginSave(phone, code) {
    $.ajax({
        type:'POST',
        url:login_url,
        data:{username:phone, captcha:code},
        success:function (data) {
            // data = eval('(' + data + ')');
            // console.log(data);
            if(data.code == '0') {
                // alert('完成登录，其他设备上的账号已下线');

                getTimeInfo();
            }else {
                alert(data.msg);
            }
        },
        error:function () {

        }
    })
}

/**
 * 发送短信接口
 * @param phone
 * @param func
 */
function sendSms(phone) {
    $.ajax({
        type:'POST',
        url:sms_send_url,
        data:{mobile:phone, type:'login'},
        success:function (data) {
            // console.log(data);
            if(data.code == '0') {

            }else {
                // alert('验证码发送失败！');
                alert(data.msg);
            }

        },
        error:function () {

        }
    })
}

/**
 * 领取商户免费套餐接口
 */
function getPkg() {
    $.ajax({
        type:'GET',
        url:get_pkg_url,
        data:{},
        success:function (data) {
            // console.log(data);
            if(data.code == '0') {
                getFreePkgSuccess(data.data);
                $('.content-timebuy .getfreepkg').css('display', 'none');
            }else {
                alert(data.msg);
            }

        },
        error:function () {

        }
    })
}

/**
 * 消费详情列表接口
 * @param pagesize
 * @param pageno
 * @param func
 */
function getPayList(pagesize, pageno) {
    $.ajax({
        type:'GET',
        url:get_paylist_url,
        data:{pageSize:pagesize, pageNo:pageno},
        success:function (data) {
            // console.log(data);
            if(data.code == '0') {
                paylist_pageno++;

                document.getElementById('user-iphone').innerHTML = "当前账号：" + data.data.phone;

                if(!data.data.endDate || data.data.endDate == null) {
                    document.getElementById('user-endtime').innerHTML = '有效期至：';
                }else {
                    document.getElementById('user-endtime').innerHTML = '有效期至：' + getLocalTime(data.data.endDate);
                }

                if(data.data.records.length == 0) {
                    // alert(111);
                    $("#paymore").hide();
                }else {

                    var thisNode = document.getElementById("user-timemsg");
                    thisNode.removeChild(document.getElementById("paymore"));

                    var inserthtml = '';
                    for (var i = 0; i <= data.data.records.length; i++) {
                        if(i == data.data.records.length) {
                            inserthtml += ("<li id='paymore'><div class='detail-btn' onClick='pageChange()'><div class='detail-img'></div></div></li>");
                        }else {
                            var temp = data.data.records[i];
                            var temp_time = getLocalTime(temp.createDate);
                            var temp_price = '';
                            if(temp.consumeType == 3) {
                                temp_price = "充值金额：¥" + temp.payNum + "元";
                            }else if(temp.consumeType == 2) {
                                temp_price = "充值时长：" + temp.addDay + "天";
                            }
                            inserthtml += ("<li><div class='time'>"+ temp_time +"</div><div class='price'>"+ temp_price +"</div></li>");
                        }


                    }

                    var html = document.getElementById('user-timemsg').innerHTML;

                    document.getElementById('user-timemsg').innerHTML = html + inserthtml;
                }


            }else {
                alert(data.msg);
            }

        },
        error:function () {

        }
    })
}

/**
 * 正式放通/临时放通接口
 * @param paytype 0:正式放通 1：临时放通
 */
function accessAuth(paytype) {
    $.ajax({
        type:'POST',
        url:access_auth_url,
        data:{paytype:paytype},
        success:function (data) {
            // console.log(data);
            if(data.code == '0') {
                smartWifiAuth(data.data.token, data.data, data.data.paytype, timbuy_info.buyurl);
            }else {
                alert(data.msg);
            }
        },
        error:function () {

        }
    })
}

/**
 * 设备放通上网
 */
function smartWifiAuth(token, data, paytype, buyurl) {
    var gw_address = data.poratlParam.gwAddress;
    var gw_url = data.poratlParam.url;
    var user_mac = data.poratlParam.userMac;
    var ac_name = data.poratlParam.nasName;
    var gw_port = data.poratlParam.gwPort;

    if (gw_port && gw_port != '') {
        gw_address += ':' + gw_port;
    }

    //http://1.1.1.1:8000/smartwifi/auth?url=123&user_mac=FAFAFAFAFAFA&token=KMZO3AWR0N@51awifi.com&ac_name=test&callback=jQuery1112006337355542446876_1494404273089&_=1494404273090
    var authUrl = 'http://' + gw_address + '/smartwifi/auth?url=' + gw_url + '&user_mac=' + user_mac + '&token=' + token + '&ac_name=' + ac_name;

    $.ajax({
        type:'POST',
        url:authUrl,
        dataType : 'JSONP',
        jsonp : 'callback',
        async : false,
        header : {
            'cache-control' : 'no-cache'
        },
        success:function (data) {
            // console.log("请求sb上网成功");
            // console.log(data);
        },
        error:function (xhr) {
            // console.log(xhr)
        },
        complete:function (xhr) {
            // console.log('准备跳转服务');
            if(paytype == '1') {
                if(buyurl != '') {
                    window.location.href = buyurl;
                }else {
                    alert('支付服务失败，请稍后再试');
                }
            }else {

            }
        }
    })
}

/**
 * 判断本地网络是否可上网
 */
function isOnline(){
    var img = new Image();
    img.src = "http://www.baidu.com/img/baidu_jgylogo3.gif?" + new Date().getTime();
    img.onload = function(){
        // alert("上网了！");
        // func(true);
        return true;
    };
    img.onerror = function(){
        // alert("断网了！");
        // func(false);
        return false;
    };
}

/**
 * 设置时间显示格式
 */
function add_zero(m){
    return m < 10 ? '0' + m:m;
}
function getLocalTime(nS) {
    var temp = new Date(parseInt(nS));
    var year=temp.getFullYear();
    var month=temp.getMonth()+1;
    var date=temp.getDate();
    var hour=temp.getHours();
    var minute=temp.getMinutes();
    var second=temp.getSeconds();
    return year + "年" + add_zero(month) + "月" + add_zero(date) + "日 " + add_zero(hour) + ":" + add_zero(minute) + ":" + add_zero(second);
}
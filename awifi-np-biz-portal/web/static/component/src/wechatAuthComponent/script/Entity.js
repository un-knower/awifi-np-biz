/**
 * 微信连Wi-Fi协议3.1供运营商portal呼起微信浏览器使用
 * @Auther: zhuxh
 * @Date: 2016-3-24
 * 微信文档页面地址 http://mp.weixin.qq.com/wiki/10/0ef643c7147fdf689e0a780d8c08ab96.html
 */

var loadIframe = null;
var noResponse = null;
var callUpTimestamp = 0;
// 胖ap认证放行接口地址
var fatApAuthUrl = '/smartwifi/auth';

function putNoResponse(ev) {
    clearTimeout(noResponse);
}

// 错误提示
function errorJump() {
    var now = new Date().getTime();
    if ((now - callUpTimestamp) > 5 * 1000) {
        return;
    }
    alert('该手机未安装微信或自动跳转微信失败请手动打开微信，如果已跳转请忽略此提示');
}

myHandler = function (error) {
    errorJump();
};

// 创建iframe
function createIframe() {
    var iframe = document.createElement("iframe");
    iframe.style.cssText = "display:none;width:0px;height:0px;";
    document.body.appendChild(iframe);
    loadIframe = iframe;
}

//注册回调函数
function jsonpCallback(result) {
    if (result && result.success) {
        var ua = navigator.userAgent;
        if (ua.indexOf("iPhone") != -1 || ua.indexOf("iPod") != -1 || ua.indexOf("iPad") != -1) { //iPhone
            document.location = result.data;
        } else {
            if ('false' == 'true') {
                alert('[强制]该浏览器不支持自动跳转微信请手动打开微信\n如果已跳转请忽略此提示');
                return;
            }
            createIframe();
            callUpTimestamp = new Date().getTime();
            loadIframe.src = result.data;
            noResponse = setTimeout(function () {
                errorJump();
            }, 4 * 1000);
        }
    } else if (result && !result.success) {
        alert(result.data);
    }
}

// 延后2秒关闭背景层
function wechatLoadHide() {
    var entity = $('._Entity_');
    var weiwangpu_load = entity.find('#weiwangpu_load');

    setTimeout(function () {
        weiwangpu_load.hide();
    }, 2000);
}

// 呼起微信APP
function redirectWechat(data) {
    try {
        var eval_code = data.data;
        eval(eval_code);
    } catch(e) {
        alert('自动跳转打开微信失败请手动打开微信');
    }
}

/**
 * 胖AP认证放行
 * @param token token参数
 * @param data 接口返回data数据
 */
function fatAPAuthRequest(data) {
    // 无返回值
    var url = 'http://' + _GW_ADDRESS + ':' + _GW_PORT + fatApAuthUrl; //认证地址
    var req_params = {
        token: data.token, //token
        url: _URL //用户浏览器输入的被拦截前的url原始地址
    };

    $.ajax({
        url: url,
        // async: false, // 同步请求设备不成功结果不执行success/error/complete
        cache: false,
        dataType: 'jsonp',
        jsonp: 'callback',
        header:{
            'cache-control': 'no-cache'
        },
        data: req_params,
        timeout: 5000,
        success: function(resp, textStatus) {
            // redirectWechat(data);
        },
        error: function(XHR, textStatus, errorThrown) {
            //因该接口重定向，导致无法判断接口执行是否成功
            // redirectWechat(data);
        },
        complete: function(XHR, textStatus) {
            redirectWechat(data);
            wechatLoadHide();
        }
    });
}

/**
 * 认证前页微信认证
 * @param state对象数据
 * @param divId Div唯一标识
 * @returns {{render: render}}
 * @Auther: zhuxh
 * @Date: 2016-3-22
 */

var _Entity_ = function (state, divId) {

    //初始化图片
    var _state = state || {
            btnText: '微信认证上网', //按钮文字
            btnClass: 'st1' // 按钮样式
        };

    // 判断变量值是否未定义
    var defString = function (varValue) {
        var value = (typeof varValue == "undefined") ? "" : varValue;
        if (/^(undefined|null)$/i.test(varValue)) {
            value = "";
        }
        return value;
    };

    var WechatAuthComponent = React.createClass({
        getInitialState: function() {
            return _state;
        },

        //根据state变化来渲染组件
        render: function () {
            var state = this.state;
            var _btnClass = state.btnClass ? state.btnClass : 'st1';
            var _btnText = state.btnText ? state.btnText : '微信认证上网';
            return (
                <div className={"_Entity_ clearfix " + _btnClass}>
                    <div className="weiwangpu_wp">
                        <input className="weiwangpu_btn" id="weiwangpu_btn" name="weiwangpu_btn" type="button" value={_btnText} onClick={this.requestApi} />
                    </div>
                    <div id="weiwangpu_load">
                        <div className="loading"></div>
                        <div className="tips_content"></div>
                    </div>
                </div>
            );
        },

        requestApi: function () {
            var entity = $('._Entity_');
            var weiwangpu_load = entity.find('#weiwangpu_load'),
                weiwangpu_btn = entity.find('#weiwangpu_btn');
            weiwangpu_load.show();

            // 页面跳转
            function redirect_ie8() {
                var url = '/ie8?';
                var url_params = {
                    global_key: _GLOBAL_KEY, //全局日志key
                    global_value: _GLOBAL_VALUE, //全局日志value
                    dev_id: _DEV_ID, //设备id
                    dev_mac: _DEV_MAC, //设备mac
                    ssid: _SSID, //ssid,
                    user_ip: _USER_IP, //用户ip
                    user_mac: _USER_MAC, //用户mac地址
                    user_phone: _USER_PHONE, //用户手机号
                    url: _URL, //用户浏览器输入的被拦截前的url原始地址
                    login_type: _LOGIN_TYPE, //登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;welcome 代表认证已放行（如果是胖AP需要用token调用设备总线接口），可以上外网
                    gw_address: _GW_ADDRESS, //胖AP类设备网关
                    gw_port: _GW_PORT, //胖AP类设备端口
                    nas_name: _NAS_NAME, //nas设备名称，NAS认证必填
                    cvlan: _CVLAN, //cvlan
                    pvlan: _PVLAN, //pvlan
                    token: _TOKEN, //用户token
                    platform: defString(_PLATFORM), //省分平台-前缀
                    customer_id: _CUSTOMER_ID, //客户id
                    cascade_label: _CASCADE_LABEL//客户层级
                };
                location.href = url + unescape($.param(url_params));
            }

            // PC端判断
            if (!/mobile/i.test(navigator.userAgent)) {
                // 添加提示
                entity.find(".tips_content").text("您当前为桌面电脑，即将为您跳转至指定页面...");
                // 定时跳转
                setTimeout(function () {
                    redirect_ie8();
                }, 1500);
                return ;
            }

            // 微信登录按钮恢复
            function wechatMessage(data) {
                // 仅失败时提示(成功message为空)
                if (data && data.message) {
                    alert(data.message);
                }
            }

            // 请求TOE微信接口
            $.ajax({
                url: '/wechat/wechatCertification/',
                type: 'GET',
                data: {
                    global_key: _GLOBAL_KEY, // 全局日志key
                    global_value: _GLOBAL_VALUE, // 全局日志value
                    auth_type: 'unauth', // UNAUTH未认证(认证页) AUTHED已认证(过渡页、导航页)
                    ssid: _SSID, // 热点名称
                    dev_id: _DEV_ID, // 设备id
                    dev_mac: _DEV_MAC, // 设备mac
                    ac_name: _NAS_NAME, // 设备名称
                    user_ip: _USER_IP, // 用户ip
                    user_mac: _USER_MAC, // 用户mac
                    site_id: _SITE_ID, // 站点id
                    customer_id: _CUSTOMER_ID, // 客户id
                    from: _URL, // 用户浏览器输入的被拦截前的url原始地址
                    platform: defString(_PLATFORM), //省分平台-前缀
                    terminalType: $.browser["terminalType"], // 终端类型
                    terminalBrand: $.browser["terminalBrand"], // 终端品牌
                    terminalVersion: $.browser["terminalVersion"], // 终端版本
                    termimalInfo: encodeURIComponent(navigator.userAgent) // Header中UserAgent值
                },
                dataType: 'JSON',
                header: {
                    'cache-control': 'no-cache'
                },
                success: function (data, textStatus, jqXHR) {
                    if (data.result == 'OK') {
                        if (typeof data.token !== 'undefined') {
                            // 胖AP/光猫流程 -- 未知是否成功
                            fatAPAuthRequest(data);
                            // 请求设备接口提示信息
                            wechatMessage(data);
                        } else {
                            // 瘦AP流程
                            redirectWechat(data);
                        }
                    } else {
                        // 请求TOE接口失败
                        wechatMessage(data);
                    }
                },
                error: function (XHR, textStatus, errorThrown) {
                    alert(textStatus || '系统异常，请稍后再试...');
                },
                complete: function (xhr) {
                    weiwangpu_btn.removeAttr('disabled', true);
                }
            });
        }
    });

    // OtherReact 在这里定义

    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
    function render() {
        return ReactDOM.render(<WechatAuthComponent />, document.getElementById(divId));
    }

    return {
        render: render
    }
};

//页面可见性API属性和事件visibilitychange
$(function () {
    document.addEventListener('visibilitychange', putNoResponse, false);
});
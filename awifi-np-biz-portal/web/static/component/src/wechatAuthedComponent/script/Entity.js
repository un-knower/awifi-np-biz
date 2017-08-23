/**
 * 微信连Wi-Fi协议3.1供运营商portal呼起微信浏览器使用
 * @Auther: zhuxh
 * @Date: 2016-3-25
 */

var loadIframe = null;
var noResponse = null;
var callUpTimestamp = 0;

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
        if (ua.indexOf("iPhone") != -1 || ua.indexOf("iPod") != -1 || ua.indexOf("iPad") != -1) {   //iPhone
            document.location = result.data;
        } else {
            createIframe();
            callUpTimestamp = new Date().getTime();
            loadIframe.src = result.data;
            noResponse = setTimeout(function () {
                errorJump();
            }, 60 * 1000);
        }
    } else if (result && !result.success) {
        alert(result.data);
    }
}

// 错误提示
function errorJump() {
    var now = new Date().getTime();
    if ((now - callUpTimestamp) > 60 * 1000) {
        return;
    }
    alert('该手机未安装微信或自动跳转微信失败请手动打开微信，如果已跳转请忽略此提示');
}

function putNoResponse(ev) {
    clearTimeout(noResponse);
}

//微信文档页面地址
//http://mp.weixin.qq.com/wiki/10/0ef643c7147fdf689e0a780d8c08ab96.html

//页面可见性API属性和事件visibilitychange
document.addEventListener('visibilitychange', putNoResponse, false);


/**
 * 认证前页微信认证
 * @param state对象数据
 * @param divId Div唯一标识
 * @returns {{render: render}}
 * @Auther: zhuxh
 * @Date: 2016-3-22
 */

var _Entity_ = function (state, divId) {
    var _state = state || {};

    var WechatAuthedComponent = React.createClass({

        // 当前组件加载完成，自动呼起微信，进行认证上网
        componentDidMount: function() {
            if (_DEV_ID !== '{@devId@}') {
                this.requestApi();
            }
        },
        render: function () {
            return (
                <div className="_Entity_ clearfix">
                    { (_DEV_ID === '{@devId@}') ? <div className="entity_tips">自动呼起微信，进行认证上网</div> : ''}
                    <div id="weiwangpu_load">
                        <div className="loading"></div>
                    </div>
                </div>
            );
        },

        requestApi: function () {
            var entity = $('._Entity_');
            var weiwangpu_load = entity.find('#weiwangpu_load');
            weiwangpu_load.show();
            // 请求TOE微信接口
            $.ajax({
                url: '/wechat/wechatCertification/',
                type: 'GET',
                data: {
                    global_key: _GLOBAL_KEY, // 全局日志key
                    global_value: _GLOBAL_VALUE, // 全局日志value
                    auth_type: 'authed', // UNAUTH未认证(认证页) AUTHED已认证(过渡页、导航页)
                    ssid: _SSID, // 热点名称
                    dev_id: _DEV_ID, // 设备id
                    dev_mac: _DEV_MAC, // 设备mac
                    ac_name: _NAS_NAME, // 设备名称
                    user_ip: _USER_IP, // 用户ip
                    user_mac: _USER_MAC, // 用户mac
                    user_phone: _USER_PHONE, // 用户手机号
                    site_id: _SITE_ID, // 站点id
                    customer_id: _CUSTOMER_ID, // 客户id
                    from: _URL // 用户浏览器输入的被拦截前的url原始地址
                },
                dataType: 'JSON',
                header: {
                    'cache-control': 'no-cache'
                },
                success: function (data, textStatus, jqXHR) {
                    if (data.result == 'OK') {
                        setTimeout(function () {
                            weiwangpu_load.hide();
                        }, 2000);

                        try {
                            eval(data.data);
                        } catch (e) {
                            alert('自动跳转打开微信失败请手动打开微信');
                        }
                    } else {
                        weiwangpu_load.hide();
                        alert(data.message);
                    }
                },
                error: function (XHR, textStatus, errorThrown) {
                    weiwangpu_load.hide();
                    alert(textStatus || '系统异常，请稍后再试...');
                },
                complete: function (xhr) {

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
        return ReactDOM.render(<WechatAuthedComponent />, document.getElementById(divId));
    }

    return {
        render: render
    }
};
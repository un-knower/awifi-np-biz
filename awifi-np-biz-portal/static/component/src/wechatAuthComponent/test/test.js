
// http://portalrs.51awifi.com/resource/component/script/wechatutil.js
// http://admin.51awifi.com/media/component/201511/27/_1RqIhYcYp7p6ZyC4tEvNzBwM/script/Entity.js
/**
 * 微信连Wi-Fi协议3.1供运营商portal呼起微信浏览器使用
 * @Auther: zhuxh
 * @Date: 2016-3-24
 */

var loadIframe = null;
var noResponse = null;
var callUpTimestamp = 0;
// 胖ap认证放行接口地址
var fatApAuthUrl = '/smartwifi/auth';

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
 * 胖AP认证放行
 * @param token token参数
 * @param data 接口返回data数据
 */
function fatAPAuthRequest(data) {
    // 返回结果数据
    var ret_result = 0;
    var url = 'http://' + _GW_ADDRESS + ':' + _GW_PORT + fatApAuthUrl; //认证地址
    var req_params = {
        token: data.token, //token
        url: _URL //用户浏览器输入的被拦截前的url原始地址
    };

    function redirectWechat(data) {
        try {
            eval(data.data);
            ret_result = 1;
        } catch(e) {
            alert('自动跳转打开微信失败请手动打开微信');
        }
    }

    $.ajax({
        url: url,
        async: false,
        cache: false,
        dataType: 'jsonp',
        jsonp: 'callback',
        header:{
            'cache-control': 'no-cache'
        },
        data: req_params,
        timeout: 5000,
        success: function(data, textStatus, jqXHR){
            redirectWechat(data);
        },
        error: function(XHR, textStatus, errorThrown) {
            //因该接口重定向，导致无法判断接口执行是否成功
            redirectWechat(data);
        },
        complete: function(XHR, textStatus) {
            redirectWechat(data);
        }
    });

    return ret_result;
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
                    </div>
                </div>
            );
        },

        requestApi: function () {
            var entity = $('._Entity_');
            var weiwangpu_load = entity.find('#weiwangpu_load'),
                weiwangpu_btn = entity.find('#weiwangpu_btn');
            weiwangpu_load.show();

            // 微信登录按钮恢复
            function wechatBtnEnable(data) {
                weiwangpu_load.hide();
                weiwangpu_btn.removeAttr('disabled', true);
                alert(data.message);
            }

            // 延后2秒关闭背景层
            function wechatLoadHide() {
                setTimeout(function () {
                    weiwangpu_load.hide();
                }, 2000);
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
                    from: _URL // 用户浏览器输入的被拦截前的url原始地址
                },
                dataType: 'JSON',
                header: {
                    'cache-control': 'no-cache'
                },
                success: function (data, textStatus, jqXHR) {
                    if (data.result == 'OK') {
                        if (typeof data.token !== 'undefined') {
                            // 胖AP/光猫流程
                            if (fatAPAuthRequest(data) === 0) {
                                // 请求设备接口失败
                                wechatBtnEnable(data);
                            } else {
                                // 请求设备接口成功
                                wechatLoadHide();
                            }
                        } else {
                            // 瘦AP流程
                            wechatLoadHide();

                            try {
                                eval(data.data);
                            } catch (e) {
                                alert('自动跳转打开微信失败请手动打开微信');
                            }
                        }
                    } else {
                        // 请求TOE接口失败
                        wechatBtnEnable(data);
                    }
                },
                error: function (XHR, textStatus, errorThrown) {
                    weiwangpu_load.hide();
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

// 拉portal调用方式
// _Entity_(state, divId).render(); 组件拉了多次则调用多次

/*
 1) 写入页面中相应的Div元素，每个div的id保证唯一性
 2）合并多个组件js文件至一个js文件中，且在合并的js文件末尾加个要调用 ReactJs 的方法
 3）合并多个组件css文件至一个css文件中
 */


var _Setting_ = function (entity, divId) {

    var setting = null;

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var _state = entity.state || {
            btnText: '微信认证上网', //按钮文字
            btnClass: 'st1' // 按钮样式
        };

    var WechatAuthSetting = React.createClass({
        getInitialState: function() {
            return _state;
        },
        render: function () {
            var state = this.state;
            var _btnClass = state.btnClass ? state.btnClass : 'st1';
            var _btnText = state.btnText ? state.btnText : '微信认证上网';
            return (
                <div className="container">
                    <form className="form-horizontal">
                        <div className="form-group">
                            <label for="content" className="col-sm-2 control-label">微信认证按钮文字：</label>
                            <div className="col-sm-9">
                                <input className="form-control" id="btntext" name="btntext" placeholder="请输入文本内容" defaultValue={_btnText} />
                            </div>
                        </div>
                        <div id="styles" className="form-group">
                            <label className="col-sm-2 control-label">按钮配色：</label>
                            <div className="col-sm-9">
                                <label className="radio-inline">
                                    <input type="radio" name="btncolor" value="st1" defaultChecked={_btnClass == 'st1'} />
                                    <div className="st1">按&ensp;钮</div>
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="btncolor" value="st2" defaultChecked={_btnClass == 'st2'} />
                                    <div className="st2">按&ensp;钮</div>
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="btncolor" value="st3" defaultChecked={_btnClass == 'st3'} />
                                    <div className="st3">按&ensp;钮</div>
                                </label>
                            </div>
                            <div className="col-sm-9">
                                <label className="radio-inline">
                                    <input type="radio" name="btncolor" value="st4" defaultChecked={_btnClass == 'st4'} />
                                    <div className="st4">按&ensp;钮</div>
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="btncolor" value="st5" defaultChecked={_btnClass == 'st5'} />
                                    <div className="st5">按&ensp;钮</div>
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="btncolor" value="st6" defaultChecked={_btnClass == 'st6'} />
                                    <div className="st6">按&ensp;钮</div>
                                </label>
                            </div>
                        </div>
                        <div className="form-group">
                            <div className="col-sm-offset-2 col-sm-9 tac">
                                <button id="save" type="button" className="btn btn-danger btn-sm" onClick={this.handlerSaveData}>保&emsp;存</button>
                            </div>
                        </div>
                    </form>
                </div>
            );
        },
        handlerSaveData: function () {
            var btntext = $('#btntext').val(),
                btncolor = $('input[name="btncolor"]:checked').val();
            _state.btnClass = btncolor;
            _state.btnText = btntext;
            setStates(_state);
        }
    });

    /**
     * 渲染DOM
     */
    function render() {
        setting = ReactDOM.render(<WechatAuthSetting />, document.getElementById(divId));
        return setting;
    }

    /**
     * 暴露对象属性及方法
     */
    return {
        setting: setting,
        setStates: setStates,
        render: render
    }
};

var entity = _Entity_(null, 'entity').render();

var setting = _Setting_(entity, 'setting').render();
/**
 * 引用外部css组件
 * @param state对象数据
 * @param divId Div唯一标识
 * @returns {{render: render}}
 * @Auther: 朱学煌
 * @Date: 2016-4-8
 */

var _Entity_ = function (state, divId) {
    var _state = state || {
            cssSrc: '',  // css文件路径
            cssHref: 'http://201512awifi.alltosun.net/api/get_background_css' //第三方接口地址
        };

    var CssComponent = React.createClass({

        getInitialState: function () {
            return _state;
        },

        //在组件渲染之前获得背景图
        componentWillMount: function () {
            if (_DEV_ID == '{@devId@}') {
                var encryptionUrl = "http://127.0.0.1/app/encryption"; //加密地址

                var cssUrl = this.state.cssHref; //获取背景图片地址
                cssUrl += this.state.cssHref.indexOf('?') != -1 ? 'params=' : '?params=';
                var ajaxSuccess = function (data) {//参数加密成功以后执行
                    if (data.result == 'OK') {//接口返回参数加密成功后执行
                        cssUrl += data.params;
                        // head头部引用css文件
                        $('head').append('<link href="'+ cssUrl +'" rel="stylesheet" type="text/css" />');
                    }
                };
                var option = {
                    deviceId: _DEV_ID, // 设备id
                    userIp: _USER_IP, // 用户ip
                    userMac: _USER_MAC, // 用户MAC
                    userPhone: _USER_PHONE, // 用户手机号
                    terminalType: $.browser.mobileOS, // 终端类型*/
                    customerId: _CUSTOMER_ID // 营业厅id
                };

                $.ajax({
                    url: encryptionUrl,
                    dataType: 'JSONP',
                    jsonp: 'callback',
                    header: {
                        'cache-control': 'no-cache'
                    },
                    data: option,
                    success: ajaxSuccess
                });
            }
        },
        render: function () {
            return (
                <div className="_Entity_">
                    { (_DEV_ID === '{@devId@}') ? <div className="entity_tips">引用外部css资源文件</div> : ''}
                </div>
            );
        }
    });

    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
    function render() {
        return ReactDOM.render(<CssComponent />, document.getElementById(divId));
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

    var CssSettingComponent = React.createClass({
        getInitialState: function () {
            return {
                cssHref: entity.state.cssHref
            };
        },
        eventCssHref: function (event) {
            var _state = {
                'cssHref': event.target.value
            };
            setStates(_state);
        },
        render: function () {
            return (
                <div className="container _Setting_">
                    <form className="form-horizontal">
                        <div className="form-group">
                            <label for="url" className="col-sm-2 control-label">URL：</label>
                            <div className="col-sm-9">
                                <input type="text" className="form-control" id="url" value={this.state.cssHref} onChange={this.eventCssHref}/>
                            </div>
                        </div>
                    </form>
                </div>
            );
        }
    });

    function render() {
        setting = ReactDOM.render(<CssSettingComponent />, document.getElementById(divId));
        return setting;
    }

    return {
        setting: setting,
        setStates: setStates,
        render: render
    }
};

var entity = _Entity_('', 'entity').render();

var setting = _Setting_(entity, 'setting').render();
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
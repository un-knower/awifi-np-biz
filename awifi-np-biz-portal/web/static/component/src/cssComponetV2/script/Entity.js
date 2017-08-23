var _Entity_ = function (state, divId) {
    var _state = state || {
            cssHref: '' //第三方接口地址
        };

    var CssComponent = React.createClass({

        getInitialState: function () {
            return _state;
        },

        //在组件渲染之前获得背景图
        componentWillMount: function () {
            if (_DEV_ID != '{@devId@}') {
                var encryptionUrl = "/app/getCssSource"; //加密地址
                var option = {
                    deviceId: _DEV_ID, // 设备id
                    devMac: _DEV_MAC, // 设备mac
                    userIp: _USER_IP, // 用户ip
                    userMac: _USER_MAC, // 用户MAC
                    userPhone: _USER_PHONE, // 用户手机号
                    terminalType: $.browser.mobileOS, // 终端类型
                    customerId: _CUSTOMER_ID, // 营业厅id
                    url: escape(this.state.cssHref) // 请求第3方资源url
                };
                var api_url = encryptionUrl + (this.state.cssHref.indexOf('?') != -1 ? '&' : '?') + $.param(option);
                // head头部引用css文件
                $('head').append('<link href="'+ api_url +'" rel="stylesheet" type="text/css" />');
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
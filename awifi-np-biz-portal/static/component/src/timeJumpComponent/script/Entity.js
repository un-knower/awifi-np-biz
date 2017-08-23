
var _Entity_ = function (state, divId) {

    var _state = state || {
            style: 'st1', //样式
            mode: 'btn', // btn按钮  time倒计时
            btnText: '下一页',
            timer:'5'
        };

    var EntityReact = React.createClass({
        getInitialState: function () {
            return _state;
        },

        componentDidMount: function () {
            var t = null;
            var count = _state.timer;
            if (_DEV_ID != '{@devId@}') {
                if (_state.mode == 'btn') {
                    // 按钮
                    $('.open-next-page a').click(function () {
                        redirect();
                    });
                } else {
                    // 倒计时
                    var open_next_page = $('.open-next-page-text span');
                    open_next_page.text('倒计时... '+count+'秒');
                    t = setInterval(function () {
                        count--;
                        if (count == 0) {
                            clearInterval(t);
                            redirect();
                        } else {
                            open_next_page.text('倒计时... '+count+'秒');
                        }
                    }, 1000);
                }
            }
        },

        render: function () {
            return (
                <div className={'_Entity_ ' + this.state.style}>
                    {this.state.mode == 'btn' ?
                        (<div className="open-next-page">
                            <a href="javascript:;" title={this.state.btnText}>{this.state.btnText}</a>
                        </div>) :
                        (<div className="open-next-page-text">
                            <span>{this.state.btnText}</span>
                        </div>)
                    }
                </div>
            );
        }
    });

    /**
     * 页面跳转
     */
    function redirect(){
        var url = '/site?';
        url += 'global_key=' + _GLOBAL_KEY;//全局日志key

        url += appendURLParam('global_value', _GLOBAL_VALUE);//全局日志value
        //设备信息
        url += appendURLParam('dev_id', _DEV_ID);//设备id
        url += appendURLParam('dev_mac', _DEV_MAC);//设备mac
        url += appendURLParam('ssid', _SSID);//ssid
        url += appendURLParam('gw_address', _GW_ADDRESS);//胖AP类设备网关
        url += appendURLParam('gw_port', _GW_PORT);//胖AP类设备端口
        url += appendURLParam('nas_name', _NAS_NAME);//nas设备名称，NAS认证必填
        url += appendURLParam('cvlan', _CVLAN);//cvlan
        url += appendURLParam('pvlan', _PVLAN);//pvlan
        url += appendURLParam('longitude', _LONGITUDE);//经度
        url += appendURLParam('latitude', _LATITUDE);//维度
        //商户信息
        url += appendURLParam('customer_id', _CUSTOMER_ID);//商户id
        url += appendURLParam('customer_name', _CUSTOMER_NAME);//商户名称
        url += appendURLParam('cascade_label', _CASCADE_LABEL);//商户层级
        //用户信息
        url += appendURLParam('user_ip', _USER_IP);//用户ip
        url += appendURLParam('user_mac', _USER_MAC);//用户mac地址
        url += appendURLParam('user_phone', _USER_PHONE);//用户手机号
        url += appendURLParam('login_type', _LOGIN_TYPE);//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;welcome 代表认证已放行（如果是胖AP需要用token调用设备总线接口），可以上外网
        //站点信息
        url += appendURLParam('site_id', _SITE_ID);//站点id
        url += appendURLParam('page_type', _PAGE_TYPE);//页面类型
        url += appendURLParam('num', _NUM);//页面序号

        url += appendURLParam('url', _URL);//用户浏览器输入的被拦截前的url原始地址
        url += appendURLParam('platform', _PLATFORM);//用户浏览器输入的被拦截前的url原始地址
        url += appendURLParam('token', _TOKEN);//用户浏览器输入的被拦截前的url原始地址

        window.location.href = url;
    }

    /** url拼接参数，屏蔽为空的参数 */
    function appendURLParam(key, value){
        if(!value || value === ''){
            return '';
        }
        var url = '&' + key + '=' + value;
        return url;
    }

    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
    function render() {
        return ReactDOM.render(<EntityReact />, document.getElementById(divId));
    }

    return {
        render: render
    }
};

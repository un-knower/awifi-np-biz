
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
        url+= 'global_key=' + _GLOBAL_KEY;//全局日志key
        url+= '&global_value=' + _GLOBAL_VALUE;//全局日志value
        url+= '&dev_id=' + _DEV_ID;//设备id
        url+= '&dev_mac=' + _DEV_MAC;//设备mac
        url+= '&ssid=' + _SSID;//ssid

        url+= '&user_ip=' + _USER_IP;//用户ip
        url+= '&user_mac=' + _USER_MAC;//用户mac地址
        url+= '&user_phone=' + _USER_PHONE;//用户手机号
        url+= '&url=' + _URL;//用户浏览器输入的被拦截前的url原始地址
        url+= '&login_type=' + _LOGIN_TYPE;//登录类型: authed 代表已认证过，控制页面显示“免费上网”；unauth 代表未认证过，页面显示 “手机号认证”或“用户名认证”;welcome 代表认证已放行（如果是胖AP需要用token调用设备总线接口），可以上外网
        url+= '&gw_address=' + _GW_ADDRESS;//胖AP类设备网关
        url+= '&gw_port=' + _GW_PORT;//胖AP类设备端口
        url+= '&nas_name=' + _NAS_NAME;//nas设备名称，NAS认证必填
        url+= '&cvlan=' + _CVLAN;//cvlan
        url+= '&pvlan=' + _PVLAN;//pvlan
        url+= '&token=' + _TOKEN;//用户token

        url+= '&site_id=' + _SITE_ID;//站点id
        url+= '&page_type=' + _PAGE_TYPE;//页面类型
        url+= '&num=' + _NUM;//页面序号

        url+= '&customer_id=' + _CUSTOMER_ID;//客户id
        url+= '&cascade_label=' + _CASCADE_LABEL;//客户层级

        window.location.href = url;
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

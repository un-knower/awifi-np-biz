var _Entity_ = function (state, divId) {

    var _state = state || {
            aHref: ''//第三方接口地址
        };

    var EntityReact = React.createClass({

        getInitialState: function () {
            return _state;
        },
        componentDidMount: function(){//在组件渲染之前获得背景图
            // 相对于最外层Div定位
            $('.awifi-container').addClass('containerCenter');

            if(_DEV_ID != '{@devId@}'){
                var encryptionUrl="/app/encryption";//加密地址
                var imgUrl=this.state.aHref;//获取背景图片地址
                imgUrl+=this.state.aHref.indexOf('?') != -1 ? 'params=' : '?params=';
                var OnGetMemberSuccess = function (data) {//参数加密成功以后执行
                    if(data.result == 'OK'){//接口返回参数加密成功后执行
                        imgUrl+=data.params;
                        $("#backgroundPic").html("<img src='"+imgUrl+"'/>");
                    }
                };
                var option = {
                    deviceId:_DEV_ID,// 设备id
                    userIp:_USER_IP,// 用户ip
                    userMac:_USER_MAC,// 用户MAC
                    userPhone:_USER_PHONE,// 用户手机号
                    terminalType:$.browser.mobileOS,// 终端类型
                    customerId:_CUSTOMER_ID,// 营业厅id
                };
                $.ajax({
                    url: encryptionUrl,
                    dataType: 'JSONP',
                    jsonp: 'callback',
                    header:{
                        'cache-control': 'no-cache'
                    },
                    data:option,
                    success: OnGetMemberSuccess
                });
            }
        },
        render: function () {
            if(_DEV_ID == '{@devId@}') {
                return (
                    <div id="backgroundPic" className="backgroundPic">背景组件</div>
                )
            } else {
                return (
                    <div class="_Entity_">
                        <div id="backgroundPic" className="backgroundPic_phone"></div>
                    </div>
                )
            }
        }
    });

    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
    function render() {
        return React.render(<EntityReact />, document.getElementById(divId));
    }

    return {
        render: render
    }
};
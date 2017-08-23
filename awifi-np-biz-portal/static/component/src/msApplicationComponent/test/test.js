
/** ---------------------------------------  中间内容区域 开始  ----------------------------------------------------------------------------*/

var _Entity_ = function (state, divId) {

    var app_url = '/mersrv/ms/merchant/app/list';
    var app_search_url = '/mersrv/ms/merchant/app/';
    var callWechatUrl = '/wechat/wechatCertification';//呼起微信接口地址
    /**
     * 呼起微信
     * @param _this this
     */
    function callWechat(_this){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option ={
            global_key: _GLOBAL_KEY, // 全局日志key
            global_value: _GLOBAL_VALUE, // 全局日志value
            customer_id: _CUSTOMER_ID, // 客户id

            dev_id: _DEV_ID, // 设备id
            ac_name: _NAS_NAME, // 设备名称
            dev_mac: _DEV_MAC, // 设备mac
            ssid: _SSID, // 热点名称

            user_phone: _USER_PHONE,// 用户手机号
            user_mac: _USER_MAC, // 用户mac
            user_ip: _USER_IP, // 用户ip

            auth_type: 'authed', // unauth(认证页) authed已认证(过渡页、导航页)
            platform: _PLATFORM //省分平台-前缀
        };
        //var params = 'params=' + JSON.stringify(option);
        $.ajax({
            url: callWechatUrl,
            type: 'GET',
            dataType: 'JSON',
            header: {
                'cache-control': 'no-cache'
            },
            data: option,
            success: function (data) {
                var code = data.result;
                if(code != 'OK'){
                    alert(data.message);
                    return;
                }
                var wechatJs = data.data;
                if(wechatJs && wechatJs.indexOf('true') > 0){//呼起微信成功
                    try{
                        var wechatUrl = "weixin://" + wechatJs.split("weixin://")[1].split("\",\"success")[0];
                        window.location.href = wechatUrl;
                    }catch(e){
                        // console.log(e)
                    }
                } else {//呼起微信失败
                    alert('自动跳转打开微信失败请手动打开微信');
                }

            },
            error: function (xhr, textStatus) {
                alert(textStatus || '系统异常，请稍后再试...');
            }
        });
    }

    var _state = state || {
            app_list:[],
            show:true
        };

    var EntityReact = React.createClass({
        getInitialState: function () {
            // console.log(_state);
            return _state;
        },
        componentDidMount: function(){

            //上传至正式环境测试时显示如下代码
            if(_DEV_ID == '{@devId@}') {
                return;
            }

            var _this = this;
            $.ajax({
                type:'GET',
                url:app_url,
                data:{merchantId:_CUSTOMER_ID},
                success: function (data) {
                    // console.log(data);
                    if(data.code == 0) {
                        _state.app_list = data.data.appList;
                        if(data.data.appList.length == 0 || !_state.app_list) {
                            _state.show = false;
                        }else {
                            _state.show = true;
                        }
                    }else {
                        _state.show = false;
                    }
                    _this.setAuthState();
                },
                error: function () {
                    _state.show = false;
                    _this.setAuthState();
                }
            });
        },
        //更新组件状态
        setAuthState: function () {
            this.setState(_state);
            // console.log(_state);
        },
        openDetail: function (item, eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            if(_USER_ID == '' || _USER_ID == null || !_USER_ID) {
                alert('用户未登录');
                return;
            }

            if(item.appCode == 'wxgz') { //wxgz:微信关注

                callWechat(this);//调用呼起微信接口

            }else if(item.appCode == 'read') { //阅读
                if(item.linkUrl) {
                    document.location.href = item.linkUrl;
                }
            }
            else{ //member:会员卡 coupon:优惠券 pay:自助买单

                $.ajax({
                    type:'GET',
                    url:app_search_url + item.appId + '?merchantId=' + _CUSTOMER_ID + '&userId=' + _USER_ID,
                    data:{},
                    success: function (data) {
                        // console.log(data);
                        if(data.code == 0) {
                            var temp_data = data.data;
                            if(temp_data.userOpenId != null && temp_data.userOpenId != undefined){
                                if(item.appCode == 'member') {
                                    document.location.href = "http://wx.wangjia100.cn/index.php?s=/addon/Awifi/Member/show/merchantOpenId/"+temp_data.merchantOpenId+"/userOpenId/"+temp_data.userOpenId+"/timestamp/"+temp_data.timestamp+"/token/"+temp_data.token;
                                }else if(item.appCode == 'coupon') {
                                    document.location.href = "http://wx.wangjia100.cn/index.php?s=/addon/Awifi/Coupon/myCoupon/merchantOpenId/"+temp_data.merchantOpenId+"/userOpenId/"+temp_data.userOpenId+"/timestamp/"+temp_data.timestamp+"/token/"+temp_data.token;
                                }else if(item.appCode == 'pay') {
                                    document.location.href = "http://wx.wangjia100.cn/index.php?s=/addon/Awifi/Pay/pay/merchantOpenId/"+temp_data.merchantOpenId+"/userOpenId/"+temp_data.userOpenId+"/timestamp/"+temp_data.timestamp+"/token/"+temp_data.token;
                                }
                            }else{
                                alert('应用跳转失败，请稍后再试');
                            }
                        }
                    },
                    error: function () {

                    }
                })
            }



        },
        render: function () {
            var _this = this;

            var temp_minheight;
            if(_state.show) {
                temp_minheight = '80px';
            }else {
                temp_minheight = '140px';
            }

            if(!_state.app_list){
                _state.app_list=[];
            }
            var applist = _state.app_list.map(function (item, index) {
                return <div className="module" key={index} onClick={_this.openDetail.bind(_this, item, 'click')} onTouchStart={_this.openDetail.bind(_this, item, 'touch')}>
                    <div className="image">
                        <img src={item.appIcon} key={index} alt={index} />
                    </div>
                    <div className="title">{item.appName}</div>
                </div>
            });

            return (
            <div className="_Entity_">
                <div className="container" style={{minHeight:temp_minheight}}>
                    <div className="title_content">
                        <span>店铺活动</span>
                    </div>
                    {_state.show ? <div className="app_content">{applist}</div>
                        : <div className="no_app_content">
                            <div className="no_module"><span>商户暂未配置活动</span></div>
                        </div>}

                    <div className="app_line"></div>
                </div>
            </div>
            );
        }
    });


    /**
     * 判断触发的事件是否有效
     * 其中：click适用于PC端、touch适用于移动端
     * @param eventType 事件类型：click 单击时间、touch 触摸事件
     * @returns {boolean} true 有效、false 无效
     * @auth 许小满
     * @date 2016-10-18 18:09:15
     */
    function isEventValid(eventType){
        var isMobileTerminal = /Mobile/.test(navigator.userAgent);//判断浏览器是否为移动端
        if(eventType == null || eventType == undefined){
            alert('eventType.');
            return false;
        }
        //1.eventType'click'时，PC端有效，移动端无效
        if(eventType === 'click'){
            return !isMobileTerminal;
        }
        //2.eventType'touch'时，PC端无效，移动端有效
        else if(eventType === 'touch'){
            return isMobileTerminal;
        }
        //3.其它情况提示错误信息
        else{
            alert('eventType['+eventType+']超出了范围[click/touch].');
            return false;
        }
    }

    // OtherReact 在这里定义

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

/** ---------------------------------------  中间内容区域 结束  ----------------------------------------------------------------------------*/

/** ---------------------------------------  右侧配置区域 开始  ----------------------------------------------------------------------------*/


var _Setting_ = function (entity, divId) {
    var setting = null;

    var _state = entity.state || {

        };

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var SettingReact = React.createClass({
        getInitialState: function() {

            return _state;
        },
        componentDidMount: function () {

        },
        //保存按钮点击事件
        handleSaveClick: function(){

        },
        render: function () {
            return (
                <div className="container">
                <form className="form-horizontal">
                <div className="form-group control-label">&emsp;此组件无需进行设置（应用列表）</div>
            </form>
            </div>
            );
        }
    });

    function render() {
        setting = ReactDOM.render(<SettingReact />, document.getElementById(divId));
        return setting;
    }

    return {
        setting: setting,
        setStates: setStates,
        render: render
    }
};

/** ---------------------------------------  右侧配置区域 结束  ----------------------------------------------------------------------------*/

/**
 * DOM 渲染
 */
var entity = _Entity_('', 'entity').render();
var setting = _Setting_(entity, 'setting').render();





var _Entity_ = function (state, divId) {

    var info_url = '/timebuysrv/time/info'; //时间组件全信息接口
    var login_url = '/timebuysrv/login/save';   //用户登录接口
    var sms_send_url = '/timebuysrv/sms//send'; //发送验证码接口
    var access_auth_url = '/timebuysrv/access/auth'; //正式放通/临时放通接口
    var get_pkg_url = '/timebuysrv/time/pkg/get'; //领取商户免费套餐接口
    var get_paylist_url = '/timebuysrv/time/consume'; //个人消费列表接口

    var timeInterval;

    var _state = state || {
            isLogin:false,
            canfastlogin:false, //是否一键登录
            vip:false,          //是否vip用户
            freepkg:false,      //是否领取过免费体验包
            havetime:false,     //是否有上网时长
            lefttime:'',
            buytime:true,       //是否允许时长购买
            merchantid:'',      //商户id
            buyurl:'',          //时长购买地址

            setting_fastlogin:true,
            setting_fastlogin_name:'',
            setting_buytime:true,
            setting_buytime_name:'',
            setting_freepkg:true,
            setting_freepkg_name:'',
        };

    var EntityReact = React.createClass({
        getInitialState: function () {
            // console.log(_state);

            return _state;
        },
        componentDidMount: function(){
            getTimeInfo(this, '0');
        },
        //更新组件状态
        setAuthState: function () {
            this.setState(_state);
            // console.log(_state);
        },
        render: function () {
            return (
                <div className="_Entity_">
                    {!this.state.isLogin && !this.state.canfastlogin ? <LoginComponent setAuthState={this.setAuthState}/> : ''}
                    {!this.state.isLogin && this.state.canfastlogin ? <FastLoginComponent setAuthState={this.setAuthState}/> : ''}
                    {this.state.isLogin && this.state.vip ? <VipComponent setAuthState={this.setAuthState}/> : ''}
                    {this.state.isLogin  && !this.state.vip&& this.state.freepkg ? <FreePkgComponent setAuthState={this.setAuthState}/> : ''}
                    {this.state.isLogin && !this.state.vip && this.state.buytime ? <TimeBuyComponent setAuthState={this.setAuthState}/> : ''}
                    {this.state.isLogin  && !this.state.vip&& !this.state.freepkg && !this.state.buytime ? <BuyoutComponent setAuthState={this.setAuthState}/> : ''}
                </div>
            );
        }
    });

    /**
     * 一键登录组件
     * @type {*}
     */
    var FastLoginComponent = React.createClass({
        option:{
            isopen:false,
        },
        getInitialState: function () {
            return this.option;
        },
        fastLoginClick: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }

            var phone = _USER_PHONE;
            getLoginSave(phone, '', this);
        },
        openDetailClick: function () {
            //更新当前组件的消费详情按钮状态
            this.option.isopen = !this.option.isopen;
            this.setState(this.option);
            // console.log(this.option);
        },
        showLoginComponent: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }

            //显示登录组件时，更新当前组件的消费详情按钮状态
            this.option.isopen = false;
            this.setState(this.option);

            //显示登录组件时，通知父组件隐藏当前组件
            _state.canfastlogin = false;
            _state.isLogin = false;
            this.props.setAuthState();
        },
        render: function () {

            var transform = '';
            var margintop = '';
            if(this.option.isopen) {
                transform ='rotate(45deg)';
                margintop = '6';
            } else {
                transform ='rotate(-135deg)';
                margintop = '2';
            }

            var title = '当前用户：' + _USER_PHONE;

            return(
                <div className="timebuy">
                    <div className="timebuy-row">
                        <div className="message">{title}</div>
                    </div>
                    <div className="timebuy-row">
                        <button className="timebuy-login" onClick={this.fastLoginClick.bind(this, 'click')} onTouchStart={this.fastLoginClick.bind(this, 'touch')}>一键登录</button>
                    </div>
                    <div className="timebuy-row">
                        <div className="left">
                            <button className="timebuy-account" onClick={this.showLoginComponent.bind(this, 'click')} onTouchStart={this.showLoginComponent.bind(this, 'touch')}>切换账号</button>
                        </div>
                        {/*<div className="right">*/}
                        {/*<button className="timebuy-detail" onClick={this.openDetailClick}>消费详情</button>*/}
                        {/*<div className="icon" style={{transform:transform, marginTop:margintop + 'px'}}></div>*/}
                        {/*</div>*/}
                    </div>
                    {this.option.isopen ? <DetailComponent /> : ''}
                </div>
            )
        }
    });

    /**
     * VIP组件
     * @type {*}
     */
    var VipComponent = React.createClass({
        option:{
            isopen:false,
        },
        getInitialState: function () {
            return this.option;
        },
        openDetailClick: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //更新当前组件的消费详情按钮状态
            this.option.isopen = !this.option.isopen;
            this.setState(this.option);
            // console.log(this.option);
        },
        showLoginComponent: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //显示登录组件时，更新当前组件的消费详情按钮状态
            this.option.isopen = false;
            this.setState(this.option);

            //显示登录组件时，通知父组件隐藏当前组件
            _state.isLogin = false;
            this.props.setAuthState();
        },
        componentDidMount: function () {
            accessAuth('0');
        },
        render: function () {

            var transform = '';
            var margintop = '';
            if(this.option.isopen) {
                transform ='rotate(45deg)';
                margintop = '6';
            } else {
                transform ='rotate(-135deg)';
                margintop = '2';
            }

            return(
                <div className="timebuy">
                    <div className="timebuy-row">
                        <div className="message">尊敬的电信乐享套餐用户,您好，您可享受爱WiFi免费上网服务</div>
                    </div>
                    <div className="timebuy-row">
                        <div className="left">
                            <button className="timebuy-account" onClick={this.showLoginComponent.bind(this, 'click')} onTouchStart={this.showLoginComponent.bind(this, 'touch')}>切换账号</button>
                        </div>
                        <div className="right">
                            <button className="timebuy-detail" onClick={this.openDetailClick.bind(this, 'click')} onTouchStart={this.openDetailClick.bind(this, 'touch')}>消费详情</button>
                            <div className="icon" style={{transform:transform, marginTop:margintop + 'px'}}></div>
                        </div>
                    </div>
                    {this.option.isopen ? <DetailComponent /> : ''}
                </div>
            )
        }
    });

    /**
     * 企业买断园区组件
     * @type {*}
     */
    var BuyoutComponent = React.createClass({
        option:{
            isopen:false,
        },
        getInitialState: function () {
            return this.option;
        },
        openDetailClick: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //更新当前组件的消费详情按钮状态
            this.option.isopen = !this.option.isopen;
            this.setState(this.option);
            // console.log(this.option);
        },
        showLoginComponent: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //显示登录组件时，更新当前组件的消费详情按钮状态
            this.option.isopen = false;
            this.setState(this.option);

            //显示登录组件时，通知父组件隐藏当前组件
            _state.isLogin = false;
            this.props.setAuthState();
        },
        componentDidMount: function () {
            // alert('componentDidMount');
            timeOutInterval('尊敬的用户您好，如有爱WiFi上网需求，请联系企业管理员');

            if(_state.havetime) {
                accessAuth('0');
            }
        },
        componentWillUnmount: function(){
            clearInterval(timeInterval);
        },
        render: function () {

            var transform = '';
            var margintop = '';
            if(this.option.isopen) {
                transform ='rotate(45deg)';
                margintop = '6';
            } else {
                transform ='rotate(-135deg)';
                margintop = '2';
            }

            return(
                <div className="timebuy">
                    <div className="timebuy-row">
                        <div className="message"></div>
                    </div>
                    <div className="timebuy-row">
                        <div className="left">
                            <button className="timebuy-account" onClick={this.showLoginComponent.bind(this, 'click')} onTouchStart={this.showLoginComponent.bind(this, 'touch')}>切换账号</button>
                        </div>
                        <div className="right">
                            <button className="timebuy-detail" onClick={this.openDetailClick.bind(this, 'click')} onTouchStart={this.openDetailClick.bind(this, 'touch')}>消费详情</button>
                            <div className="icon" style={{transform:transform, marginTop:margintop + 'px'}}></div>
                        </div>
                    </div>
                    {this.option.isopen ? <DetailComponent /> : ''}
                </div>
            )
        }
    });

    /**
     * 免费体验包组件
     * @type {*}
     */
    var FreePkgComponent = React.createClass({
        option:{
            isopen:false,
        },
        getInitialState: function () {
            return this.option;
        },
        openDetailClick: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //更新当前组件的消费详情按钮状态
            this.option.isopen = !this.option.isopen;
            this.setState(this.option);
            // console.log(this.option);
        },
        showLoginComponent: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //显示登录组件时，更新当前组件的消费详情按钮状态
            this.option.isopen = false;
            this.setState(this.option);

            //显示登录组件时，通知父组件隐藏当前组件
            _state.isLogin = false;
            this.props.setAuthState();
        },
        getFreePkg:function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            getPkg(this.freePkgSuccess);
        },
        freePkgSuccess: function (data) {

            alert('领取成功');
            // console.log(data);

            var lefttime = data.end_time - data.nowTime;
            _state.lefttime = lefttime;
            _state.freepkg = false;
            _state.buytime = true;
            _state.havetime = true;
            // console.log(_state);
            this.props.setAuthState();

            clearInterval(timeInterval);
            this.timeoutChange();

            smartWifiAuth(data.token, data, '0', data.buyurl);
        },
        componentDidMount: function () {
            // alert('componentDidMount');
            clearInterval(timeInterval);
            this.timeoutChange();
        },
        timeoutChange:function () {
            timeOutInterval('');
        },
        componentWillUnmount: function(){
            clearInterval(timeInterval);
        },
        render: function () {

            var transform = '';
            var margintop = '';
            if(this.option.isopen) {
                transform ='rotate(45deg)';
                margintop = '6';
            } else {
                transform ='rotate(-135deg)';
                margintop = '2';
            }

            return(
                <div className="timebuy">
                    <div className="timebuy-row">
                        <div className="message"></div>
                    </div>
                    <div className="timebuy-row">
                        <button className="timebuy-login" onClick={this.getFreePkg.bind(this, 'click')} onTouchStart={this.getFreePkg.bind(this, 'touch')}>免费体验包</button>
                    </div>
                    <div className="timebuy-row">
                        <div className="left">
                            <button className="timebuy-account" onClick={this.showLoginComponent.bind(this, 'click')} onTouchStart={this.showLoginComponent.bind(this, 'touch')}>切换账号</button>
                        </div>
                        <div className="right">
                            <button className="timebuy-detail" onClick={this.openDetailClick.bind(this, 'click')} onTouchStart={this.openDetailClick.bind(this, 'touch')}>消费详情</button>
                            <div className="icon" style={{transform:transform, marginTop:margintop + 'px'}}></div>
                        </div>
                    </div>
                    {this.option.isopen ? <DetailComponent /> : ''}
                </div>
            )
        }
    });

    /**
     * 时长购买组件
     * @type {*}
     */
    var TimeBuyComponent = React.createClass({
        option:{
            isopen:false,
        },
        getInitialState: function () {
            return this.option;
        },
        openDetailClick: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //更新当前组件的消费详情按钮状态
            this.option.isopen = !this.option.isopen;
            this.setState(this.option);
            // console.log(this.option);
        },
        showLoginComponent: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //显示登录组件时，更新当前组件的消费详情按钮状态
            this.option.isopen = false;
            this.setState(this.option);

            //显示登录组件时，通知父组件隐藏当前组件
            _state.isLogin = false;
            this.props.setAuthState();
        },
        timebuyclick: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //判断网络是否能上网操作
            isOnline(this.isOnlineStatus);
        },
        isOnlineStatus: function (status) {
            // alert(status);
            if(status) {
                // console.log('直接跳转buyurl');
                // console.log(_state.buyurl);
                if(_state.buyurl) {
                    window.location.href = _state.buyurl;
                }

            }else {
                accessAuth('1');
            }
        },
        componentDidMount: function () {
            // alert('componentDidMount');

            timeOutInterval('');
        },
        componentWillUnmount: function(){
            clearInterval(timeInterval);
        },
        render: function () {
            // alert('this.option.isopen===' + this.option.isopen);
            var transform = '';
            var margintop = '';
            if(this.option.isopen) {
                transform ='rotate(45deg)';
                margintop = '6';
            } else {
                transform ='rotate(-135deg)';
                margintop = '2';
            }

            return(
                <div className="timebuy">
                    <div className="timebuy-row">
                        <div className="message"></div>
                    </div>
                    <div className="timebuy-row" style={{display:(_state.buytime ? 'block' : 'none')}}>
                        <button className="timebuy-login" onClick={this.timebuyclick.bind(this, 'click')} onTouchStart={this.timebuyclick.bind(this, 'touch')}>时长购买</button>
                    </div>
                    <div className="timebuy-row">
                        <div className="left">
                            <button className="timebuy-account" onClick={this.showLoginComponent.bind(this, 'click')} onTouchStart={this.showLoginComponent.bind(this, 'touch')}>切换账号</button>
                        </div>
                        <div className="right">
                            <button className="timebuy-detail" onClick={this.openDetailClick.bind(this, 'click')} onTouchStart={this.openDetailClick.bind(this, 'touch')}>消费详情</button>
                            <div className="icon" style={{transform:transform, marginTop:margintop + 'px'}}></div>
                        </div>
                    </div>
                    {this.option.isopen ? <DetailComponent /> : ''}
                </div>

            )
        }
    });

    /**
     * 登录组件
     * @type {*}
     */
    var login_code_timer;
    var LoginComponent = React.createClass({
        second: 60,// 倒计时
        option:{
            codeBtnDisabled:'',
            codeBtnDsp:'获取验证码',

        },
        showTimeBuyComponent: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            var temp_phone = $("#telephone").val();
            var temp_code = $("#code").val();
            // alert(temp_phone + "~~~~~~" + temp_code);

            if(temp_phone === '' || !(/^(1[3|4|5|7|8][0-9][0-9]{8})?$/).test(temp_phone)){
                alert('请输入正确的手机号码');
                return;
            }
            if(temp_code === '' || !(/^\d{4}$/).test(temp_code)){
                alert('请输入正确的验证码');
                return;
            }
            getLoginSave(temp_phone, temp_code, this);

        },
        sendSms: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            var temp_phone = $("#telephone").val();
            if(temp_phone === '' || !(/^(1[3|4|5|7|8][0-9][0-9]{8})?$/).test(temp_phone)){
                alert('请输入正确的手机号码');
                return;
            }

            sendSms(temp_phone, this.sendSmsSuccess);
        },
        sendSmsSuccess: function () {
            var _this = this;
            var leftTime = _this.second;//剩余时间
            this.option.codeBtnDisabled = 'disabled';
            this.option.codeBtnDsp = '重新获取' + '(' + leftTime + ')';//重新获取
            _this.setState(this.option);
            leftTime --;
            login_code_timer = setInterval(function(){
                if(leftTime <= 0){
                    clearInterval(login_code_timer);
                    _this.option.codeBtnDisabled = '';
                    _this.option.codeBtnDsp = '获取验证码';//获取验证码
                    _this.setState(_this.option);
                    return;
                }
                _this.option.codeBtnDsp = '重新获取' + '(' + leftTime + ')';//重新获取
                _this.setState(_this.option);
                leftTime --;
            }, 1000);
        },
        componentWillUnmount: function(){
            var _this = this;
            clearInterval(login_code_timer);
            _this.option.codeBtnDisabled = '';
            _this.option.codeBtnDsp = '获取验证码';//获取验证码
            _this.setState(_this.option);
        },
        render: function () {
            return(
                <div className="common">
                    <div className="input-row">
                        <input className="telephone" type="text" placeholder="请输入手机号码" maxLength="11" id="telephone"/>
                    </div>
                    <div className="input-row">
                        <input className="code" type="text" placeholder="请输入验证码" maxLength="4" id="code"/>
                        <button className="get-code" onClick={this.sendSms.bind(this, 'click')} onTouchStart={this.sendSms.bind(this, 'touch')} disabled={this.option.codeBtnDisabled}>{this.option.codeBtnDsp}</button>
                    </div>
                    <div className="input-row">
                        <button className="login-btn" onClick={this.showTimeBuyComponent.bind(this, 'click')} onTouchStart={this.showTimeBuyComponent.bind(this, 'touch')}>登录</button>
                    </div>
                </div>
            )
        }
    });

    /**
     * 消费详情组件
     * @type {*}
     */
    var DetailComponent = React.createClass({
        option:{
            pagesize:10, //分页大小
            pageno:0, //当前页
            phone:'',
            enddate:'',
            list:[],
        },
        getInitialState: function () {
            return this.option;
        },
        componentDidMount: function(){
            getPayList(this.option.pagesize, this.option.pageno, this.payListSuccess);
        },
        componentWillUnmount: function () {
            this.option.pageno = 0;
            this.option.phone = '';
            this.option.enddate = '';
            this.option.list =[];
        },
        payListSuccess: function (data) {

            this.option.phone = data.phone;

            if(!data.endDate || data.endDate == null) {
                this.option.enddate = '';
            }else {
                this.option.enddate = getLocalTime(data.endDate);
            }


            if(data.records.length == 0) {
                // alert(111);
                $("#paymore").hide();
            }else {
                var temp_list = new Array();
                temp_list = this.option.list;
                $.each(data.records, function (i, item) {
                    temp_list.push(item);

                });

                this.option.list = temp_list;
            }

            this.setState(this.option);
            // console.log(this.option);

        },
        pageChange: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            // alert('分页');
            this.option.pageno = this.option.pageno + 1;
            getPayList(this.option.pagesize, this.option.pageno, this.payListSuccess);
        },
        render: function () {
            if(this.option.list.length > 0) {
                var payList = this.option.list.map(function (pay ,i) {
                    return(
                        <div className="detail-row" key={i}>
                            <div className="time">{getLocalTime(pay.createDate)}</div>
                            <div className="price" style={{display: (pay.consumeType == 3 ? 'block' : 'none')}}>{"充值金额：¥" + pay.payNum + "元"}</div>
                            <div className="price" style={{display: (pay.consumeType == 2 ? 'block' : 'none')}}>{"充值时长：" + pay.addDay + "天"}</div>
                        </div>
                    );
                });
            }else {

            }

            return(
                <div className="detail">
                    <div className="detail-title">
                        <div className="title-font">时长信息</div>
                    </div>
                    <div className="detail-list">
                        <div className="detail-content">当前账号：{this.option.phone}</div>
                        <div className="detail-content">有效期至：{this.option.enddate}</div>
                    </div>
                    <div className="detail-title">
                        <div className="title-font">充值记录</div>
                    </div>
                    <div className="detail-list">
                        {payList}
                    </div>
                    <div className="detail-list" id="paymore">
                        <div className="detail-btn" onClick={this.pageChange.bind(this, 'click')} onTouchStart={this.pageChange.bind(this, 'touch')}>
                            <div className="detail-img"></div>
                        </div>
                    </div>
                </div>
            )
        }
    });

    /**
     * 时长倒计时
     * @param prompt 倒计时提示文字描述，为空是展现默认提示
     */
    function timeOutInterval(prompt) {
        if(prompt == null || prompt == '' || prompt == undefined) {
            prompt = '您暂时无法上网，请购买上网时长';
        }
        if(_state.havetime) {
            // 倒计时
            // var lefttime = 60000/60*3;
            var lefttime = _state.lefttime;
            timeInterval = setInterval(
                function() {
                    var _html = '';
                    if (lefttime <= 0) {
                        clearInterval(timeInterval);
                        _html = prompt;
                    } else {
                        var d = Math.floor(lefttime
                            / 1000 / 60 / 60 / 24);
                        var h = Math.floor(lefttime
                            / 1000 / 60 / 60 % 24);
                        var m = Math
                            .floor(lefttime / 1000 / 60 % 60);
                        var s = Math
                            .floor(lefttime / 1000 % 60);
                        h = h < 10 ? '0' + h : h;
                        m = m < 10 ? '0' + m : m;
                        s = s < 10 ? '0' + s : s;
                        _html = '您的剩余上网时长：' + d + '天' + h
                            + ':' + m + ':' + s;
                    }
                    $(".message").html(_html);
                    lefttime = lefttime - 1000;
                    _state.lefttime = lefttime;
                    // console.log(_state.lefttime);
                }, 1000);
        } else{
            $(".message").html(prompt);
        }

        // console.log(_state);
        // console.log(this.option);
    }

    /**
     * 时间组件全信息接口
     * type: 首次进入页面获取时为0； 登陆接口调用是为1；
     */
    function getTimeInfo(_this, type) {
        var leftTime;

        //上传至正式环境测试时显示如下代码
        if(_DEV_ID == '{@devId@}') {
            return;
        };

        var usertype = _LOGIN_TYPE;  //NEW_USER:新用户  EXEMPT_AUTH_USER:免认证用户
        if(usertype == 'authed') {
            usertype = 'EXEMPT_AUTH_USER';
        }else if(usertype == 'unauth') {
            usertype = 'NEW_USER';
        }
        var option = {
            logId:_GLOBAL_KEY,
            deviceId:_DEV_ID,
            mobilePhone:_USER_PHONE,
            userMac:_USER_MAC,
            userIP:_USER_IP,
            gwAddress:_GW_ADDRESS,
            gwPort:_GW_PORT,
            nasName:_NAS_NAME,
            userType:usertype,
            token:'',
            url:'',
        };

        $.ajax({
            type:'POST',
            url:info_url,
            data:option,
            success: function (data) {
                // data = eval('(' + data + ')');
                // console.log(data);
                // alert(data.code);
                if(data.code == '0') {
                    var temp_data = data.data;

                    //根据user对象判断是否一键登录
                    if(temp_data.user) {

                        if(temp_data.user.userId) {
                            _USER_ID = temp_data.user.userId;
                        }

                        //获取时长购买buyurl
                        if(temp_data.buyurl) {
                            _state.buyurl = temp_data.buyurl;
                        }
                        _state.isLogin = true;
                        if(temp_data.timeInfo) {
                            //判断是否为电信CRM
                            if(temp_data.timeInfo.vip) {
                                _state.vip = true;
                                _state.btntitle = '';
                            }else {
                                _state.vip = false;
                                if((temp_data.timeInfo.endTime - temp_data.nowDate) > 0) { //计算剩余时长
                                    _state.havetime = true;
                                    leftTime = temp_data.timeInfo.endTime - temp_data.nowDate;
                                    _state.lefttime = leftTime;

                                    //如果有时长 直接做放通操作
                                    accessAuth('0');

                                }else {
                                    _state.havetime = false;
                                    _state.lefttime = '0';
                                }

                                //判断是否获取免费礼包
                                if(temp_data.timeInfo.canGetFreePkg) {
                                    _state.freepkg = true;
                                    _state.buytime = false;
                                }else {
                                    _state.freepkg = false;
                                    _state.buytime = true;
                                }
                            }
                        }else {

                        }
                    }else {
                        _state.isLogin = false;
                        if(data.data.canFastLogin) { //一键登录
                            _state.canfastlogin = true;
                        }else {                         //登录组件
                            _state.canfastlogin = false;
                        }
                    }

                    //portal管理页面内配置，判断优先级最高
                    if(!_state.setting_fastlogin) {
                        _state.canfastlogin = false;
                    }
                    if(!_state.setting_buytime) {
                        _state.buytime = false;
                        _state.freepkg = false;
                    }else {
                        if(!_state.setting_freepkg) {
                            _state.freepkg = false;
                            _state.buytime = true;
                        }
                    }

                    //获取商户信息
                    if(temp_data.merchant) {
                        _state.merchantid = temp_data.merchant.merchantId;
                    }

                    //获取时长购买buyurl
                    if(temp_data.buyurl) {
                        _state.buyurl = temp_data.buyurl;
                    }

                    //根据type状态刷新父组件的render
                    if(type == '0') {
                        _this.setAuthState();
                    }
                    else if(type == '1') {
                        _this.props.setAuthState();
                    }
                }
            },
            error: function () {

            }
        });
    }

    /**
     * 登录接口
     * @param phone
     * @param code
     * @param func
     */
    function getLoginSave(phone, code, _this) {
        $.ajax({
            type:'POST',
            url:login_url,
            data:{username:phone, captcha:code},
            success:function (data) {
                // data = eval('(' + data + ')');
                // console.log(data);
                if(data.code == '0') {
                    // alert('完成登录，其他设备上的账号已下线');
                    _state.isLogin = true;
                    // _this.props.setAuthState();

                    _USER_ID = data.data.user.userId;

                    getTimeInfo(_this, '1');
                }else {
                    alert(data.msg);
                }
            },
            error:function () {

            }
        })
    }

    /**
     * 发送短信接口
     * @param phone
     * @param func
     */
    function sendSms(phone, func) {
        $.ajax({
            type:'POST',
            url:sms_send_url,
            data:{mobile:phone, type:'login'},
            success:function (data) {
                // console.log(data);
                if(data.code == '0') {
                    func();
                }else {
                    // alert('验证码发送失败！');
                    alert(data.msg);
                }

            },
            error:function () {

            }
        })
    }

    /**
     * 领取商户免费套餐接口
     */
    function getPkg(func) {
        $.ajax({
            type:'GET',
            url:get_pkg_url,
            data:{},
            success:function (data) {
                // console.log(data);
                if(data.code == '0') {
                    func(data.data);
                }else {
                    alert(data.msg);
                }

            },
            error:function () {

            }
        })
    }

    /**
     * 消费详情列表接口
     * @param pagesize
     * @param pageno
     * @param func
     */
    function getPayList(pagesize, pageno, func) {
        $.ajax({
            type:'GET',
            url:get_paylist_url,
            data:{pageSize:pagesize, pageNo:pageno},
            success:function (data) {
                // console.log(data);
                if(data.code == '0') {
                    func(data.data);
                }else {
                    alert(data.msg);
                }

            },
            error:function () {

            }
        })
    }

    /**
     * 正式放通/临时放通接口
     * @param paytype 0:正式放通 1：临时放通
     */
    function accessAuth(paytype) {
        $.ajax({
            type:'POST',
            url:access_auth_url,
            data:{paytype:paytype},
            success:function (data) {
                // console.log(data);
                if(data.code == '0') {
                    smartWifiAuth(data.data.token, data.data, data.data.paytype, _state.buyurl);
                }else {
                    alert(data.msg);
                }
            },
            error:function () {

            }
        })
    }

    /**
     * 设备放通上网
     */
    function smartWifiAuth(token, data, paytype, buyurl) {
        var gw_address = data.poratlParam.gwAddress;
        var gw_url = data.poratlParam.url;
        var user_mac = data.poratlParam.userMac;
        var ac_name = data.poratlParam.nasName;
        var gw_port = data.poratlParam.gwPort;

        if (gw_port && gw_port != '') {
            gw_address += ':' + gw_port;
        }

        //http://1.1.1.1:8000/smartwifi/auth?url=123&user_mac=FAFAFAFAFAFA&token=KMZO3AWR0N@51awifi.com&ac_name=test&callback=jQuery1112006337355542446876_1494404273089&_=1494404273090
        var authUrl = 'http://' + gw_address + '/smartwifi/auth?url=' + gw_url + '&user_mac=' + user_mac + '&token=' + token + '&ac_name=' + ac_name;

        $.ajax({
            type:'POST',
            url:authUrl,
            dataType : 'JSONP',
            jsonp : 'callback',
            async : false,
            header : {
                'cache-control' : 'no-cache'
            },
            success:function (data) {
                // console.log("请求sb上网成功");
                // console.log(data);
            },
            error:function (xhr) {
                // console.log(xhr)
            },
            complete:function (xhr) {
                // console.log('准备跳转服务');
                if(paytype == '1') {
                    if(buyurl != '') {
                        window.location.href = buyurl;
                    }else {
                        alert('支付服务失败，请稍后再试');
                    }
                }else {

                }
            }
        })
    }

    /**
     * 设置时间显示格式
     */
    function add_zero(m){
        return m < 10 ? '0' + m:m;
    }
    function getLocalTime(nS) {
        var temp = new Date(parseInt(nS));
        var year=temp.getFullYear();
        var month=temp.getMonth()+1;
        var date=temp.getDate();
        var hour=temp.getHours();
        var minute=temp.getMinutes();
        var second=temp.getSeconds();
        return year + "年" + add_zero(month) + "月" + add_zero(date) + "日 " + add_zero(hour) + ":" + add_zero(minute) + ":" + add_zero(second);
    }

    /**
     * 判断本地网络是否可上网
     */
    function isOnline(func){
        var img = new Image();
        img.src = "http://www.baidu.com/img/baidu_jgylogo3.gif?" + new Date().getTime();
        img.onload = function(){
            // alert("上网了！");
            func(true);
        };
        img.onerror = function(){
            // alert("断网了！");
            func(false);
        };
    }

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
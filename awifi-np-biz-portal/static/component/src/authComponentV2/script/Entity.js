/**
 * 认证组件
 * @author 许小满
 * @date 2016年01月07日
 */
var _Entity_ = function (state, divId) {

    //state默认值
    var stateDefault = {
        style: 'st1',//样式
        active: 'mobile',//选中：mobile 手机号、userPass 静态用户名、voice语音
        display: 'mobile',//显示：free 免费上网、mobile 手机号、userPass 静态用户名、voice语音
        tabNum: 3,//tab数量
        //手机号
        hasMobile: true,//是否包含功能点：手机号认证
        mobileLoginBtnSty: 'hide',//登录按钮是否显示
        hasMsgSend: false,//是否包含功能点：短信回发
        //用户名
        hasUserPass: true,//是否包含功能点：静态用户名认证
        //语音认证
        hasVoice: true,//是否包含功能点：语音认证
        //免费上网
        hasFree: true,//是否包含功能点：免费上网
        hasAutoAuth: true, // 是否自动登录上网
        autoAuthSecond: 3,//自动登录上网-倒计时秒数
        /** 修改密码、忘记密码 新增参数 */
        userName: '',//用户名
        mobile: '',//手机号
        hideAuthDivStyle: '',//控制主组件是否隐藏显示
        hasModefiyPassword: true,//是否包含功能点：修改密码
        showModifyPasswordForSetNewPassword: false,//是否显示：修改密码--设置新密码组件
        hasForgetPassword: true,//是否包含功能点：忘记密码
        showForgetPasswordForCheckMobile: false,//是否显示：忘记密码--验证手机号组件
        showForgetPasswordForSetNewPassword: false,//是否显示：忘记密码--设置新密码组件
        showWelcome: false, //是否显示欢迎组件
        openBlacklist: false, //开始黑名单，默认关闭
        openMessage: true, //开启中英文，默认开启
        language: 'cn',//语言，cn 中文、en 英文
        message: null,//国际化
        layoutHelpShow: false, // 默认不显示弹出层
        titleFontColor: '' //标题字体颜色
    };

    var _state = state || stateDefault;

    //当从数据库中获取的_state存在新增参数为空的情况，需要额外设置初始值
    setDefaultState();

    //当用户为老用户时，默认显示“免费上网”
    var oldActive = _state.active;
    var oldDisplay = _state.display;
    if(_state.hasFree && _LOGIN_TYPE === 'authed'){
        _state.active = 'free';
        _state.display = 'free';
    }
    if(_LOGIN_TYPE === 'welcome'){
        _state.hasFree = false;//是否包含功能点：免费上网
        _state.display = 'free';//显示：free 免费上网、mobile 手机号、userPass 静态用户名
        _state.hasMobile = false;//是否包含功能点：手机号认证
        _state.hasUserPass = false;//是否包含功能点：静态用户名认证
        _state.showWelcome = true;
    }

    //中英文--中文
    var message_cn = {
        //公共
        freeLoginBtnDsp: '免费登录',//免费登录
        reacquire: '重新获取',//重新获取
        modifyPassword: '修改密码',//修改密码
        forgetPassword: '忘记密码',//忘记密码
        submitBtn: '提交',//提交
        cancelBtn: '取消',//取消
        changeAuthType: '切换认证',//切换认证

        //手机号认证
        mobileTitle: (_state.message && _state.language == 'cn') ? _state.message.mobileTitle : '手机号',//tab标签显示值
        getAuthCodeBtnDsp: '获取验证码',//获取验证码
        inputCellphonePlaceholder: '请输入手机号码',//请输入手机号码
        inputAuthCodePlaceholder: '请输入验证码',//请输入验证码
        alertAgreeDisclaimer: '请先同意免责声明',//请先同意免责声明
        alertInputCellphone: '请输入正确的手机号码',//请输入正确的手机号码
        alertInputAuthCode: '请输入正确的验证码',//请输入正确的验证码
        getAuthCodeFailedClick: '验证码获取失败请点击',//验证码获取失败请点击
        getAuthCodeFailedSendMessage : '发送100100到10690498229982',//发送100100到10690498229982

        //用户名认证
        userPassTitle: (_state.message && _state.language == 'cn') ? _state.message.userPassTitle : '静态用户名',//tab标签显示值
        inputUserNamePlaceholder: '请输入' + ((_state.message && _state.language == 'cn') ? _state.message.userPassTitle : '静态用户名'),//请输入静态用户名
        inputPasswordPlaceholder: '请输入密码',//请输入密码

        //语音认证
        voiceTitle: (_state.message && _state.language == 'cn') ? _state.message.voiceTitle : '语音',
        voiceSubmitBtn: '点击拨号',
        voiceMessage: '请保证您输入的号码与拨号手机一致',
        voiceHelpTitle: '使用说明',
        alertIVRCellphoneCheck: '手机号码只能是数字',

        //免费上网认证
        freeButtonDsp: (_state.message && _state.language == 'cn') ? _state.message.freeButtonDsp : '点击免费上网',//按钮显示值
        autoAuthComponentPrefix: '欢迎您再次访问，',//欢迎您再次访问，
        autoAutheComponentSuffix: '秒',//秒倒计时


        //修改密码--设置新密码
        alertInputOldPassword: '请输入旧密码',//请输入旧密码
        alertInputNewPassword: '请输入新密码',//请输入新密码
        alertInputConfirmPassword: '请输入确认密码',//请输入确认密码
        alertCheckPassword: '新密码必须由4-20位不包含汉字的字符组成！',//新密码必须由4-20位不包含汉字的字符组成！
        alertCheckPasswordSame: '旧密码与新密码不允许相同，请重新输入！',//旧密码与新密码不允许相同，请重新输入！
        alertCheckConfirmPassword: '确认密码必须由4-20位不包含汉字的字符组成！',//确认密码必须由4-20位不包含汉字的字符组成！
        alertCheckNewConfirmPasswordSame: '新密码与确认密码不一致，请重新输入！',//新密码与确认密码不一致，请重新输入！

        //忘记密码--验证手机号
        inputCellphoneConfirmPlaceholder: '请输入手机号进行验证',//请输入手机号进行验证

        //忘记密码--设置新密码
        alertPasswordSuccess: '密码修改成功，请使用新密码重新登陆！',//密码修改成功，请使用新密码重新登陆！
        alertPasswordFail: '密码修改失败！',//密码修改失败！
        alertSendAuthCodeFail: '验证码发送失败！',//验证码发送失败！
        alertAuthCodeCheckFail: '验证码校验失败！',//验证码校验失败！

        //欢迎组件，认证已放行时显示
        welcomeComponentPrefix: '欢迎您再次访问，您已成功联网, ',//欢迎您再次访问，您已成功联网,
        welcomeComponentSuffix: '秒倒计时',//秒倒计时

        //黑名单
        alertBlacklistCheckFail: '您无法使用该网络，请与管理员联系。',//您无法使用该网络，请与管理员联系。

        //格式化 发送验证码 错误消息
        alertGetAuthCodeFail: '抱歉，获取验证码失败.',//抱歉，获取验证码失败.
        alertCheckAuthCodeSended: '抱歉，验证码发送过于频繁，您可使用获取的验证码认证上网.',//抱歉，验证码发送过于频繁，您可使用获取的验证码认证上网.
        alertAuthCodeNoResponse: '抱歉，获取验证码失败（错误信息：接口无响应）',//抱歉，获取验证码失败（错误信息：接口无响应）
        alertSorryAuthCodeCheckFail: '抱歉，获取验证码失败（错误信息：',//抱歉，获取验证码失败（错误信息：

        //格式化 认证 错误消息
        alertAuthFail: '抱歉，认证失败.',//抱歉，认证失败.
        alertAuthCodeError: '抱歉，验证码校验失败，请重新输入.',//抱歉，验证码校验失败，请重新输入.
        alertAuthCodeExpired: '抱歉，验证码已失效，请重新获取.',//抱歉，验证码已失效，请重新获取.
        alertAuthNoResponse: '抱歉，认证失败（错误信息：接口无响应）',//抱歉，认证失败（错误信息：接口无响应）
        alertDuplicateChallengeRequest: '请求处理中，请勿重复认证。',//请求处理中，请勿重复认证。
        alertSorryAuthCheckFail: '抱歉，认证失败（错误信息：' //抱歉，认证失败（错误信息：
    };
    //中英文--英文
    var message_en ={
        //公共
        freeLoginBtnDsp: 'Freelogin',//免费登录
        reacquire: 'Get again',//重新获取
        modifyPassword: 'Change password',//修改密码
        forgetPassword: 'Forgot password',//忘记密码
        submitBtn: 'Submit',//提交
        cancelBtn: 'Cancel',//取消
        changeAuthType: 'Switch',//切换认证

        //手机号认证
        mobileTitle: 'Telephone',//tab标签显示值
        getAuthCodeBtnDsp: 'Get auth code',//获取验证码
        inputCellphonePlaceholder: 'Enter the phone number',//请输入手机号码
        inputAuthCodePlaceholder: 'Enter the auth code',//请输入验证码
        alertAgreeDisclaimer: 'Please agree to the disclaimer',//请先同意免责声明
        alertInputCellphone: 'Please enter the correct phone number',//请输入正确的手机号码
        alertInputAuthCode: 'Please enter the correct auth code',//请输入正确的验证码
        getAuthCodeFailedClick: 'Please click when code get failed',//验证码获取失败请点击
        getAuthCodeFailedSendMessage : 'Send 100100 to 10690498229982',//发送100100到10690498229982

        //用户名认证
        userPassTitle: 'Account',//tab标签显示值
        inputUserNamePlaceholder: 'Enter your username',//请输入静态用户名
        inputPasswordPlaceholder: 'Enter your password',//请输入密码

        //语音认证
        voiceTitle: 'Voice',
        voiceSubmitBtn: 'click to get the voice verification',
        voiceMessage: 'Please make sure your phone number is in line with the number you dialed.',
        voiceHelpTitle: 'instruction',
        alertIVRCellphoneCheck: 'The phone number must be number.',

        //免费上网认证
        freeButtonDsp: 'Free WiFi',//按钮显示值
        autoAuthComponentPrefix: 'Welcome back',//欢迎您再次访问，
        autoAutheComponentSuffix: 's',//秒倒计时

        //修改密码--设置新密码
        alertInputOldPassword: 'Enter old password',//请输入旧密码
        alertInputNewPassword: 'Enter new password',//请输入新密码
        alertInputConfirmPassword: 'Enter new password again',//请输入确认密码
        alertCheckPassword: '4 to 20 does not contain Chinese characters',//新密码必须由4-20位不包含汉字的字符组成！
        alertCheckPasswordSame: 'Old password and new password is not allowed,Please enter again',//旧密码与新密码不允许相同，请重新输入！
        alertCheckConfirmPassword: '4 to 20 does not contain Chinese characters',//确认密码必须由4-20位不包含汉字的字符组成！
        alertCheckNewConfirmPasswordSame: 'Password does not match the confirm password,Please enter again!',//新密码与确认密码不一致，请重新输入！

        //忘记密码--验证手机号
        inputCellphoneConfirmPlaceholder: 'Enter the phone number',//请输入手机号进行验证

        //忘记密码--设置新密码
        alertPasswordSuccess: 'Password changed ok,please login in with the new password',//密码修改成功，请使用新密码重新登陆！
        alertPasswordFail: 'Password change failed!',//密码修改失败！
        alertSendAuthCodeFail: 'Auth code sent failed',//验证码发送失败！
        alertAuthCodeCheckFail: 'Auth code check failed!',//验证码校验失败！

        //欢迎组件，认证已放行时显示
        welcomeComponentPrefix: 'Welcome back',//欢迎您再次访问，您已成功联网,
        welcomeComponentSuffix: 's',//秒倒计时

        //黑名单
        alertBlacklistCheckFail: 'You can not use the network, please contact the administrator.',//您无法使用该网络，请与管理员联系。

        //格式化 发送验证码 错误消息
        alertGetAuthCodeFail: 'Sorry,get auth code failed',//抱歉，获取验证码失败.
        alertCheckAuthCodeSended: 'Sorry, send auth code too frequently,you can use the code you got resently on the internet! ',//抱歉，验证码发送过于频繁，您可使用获取的验证码认证上网.
        alertAuthCodeNoResponse: 'Sorry, failed to get auth code (error message: No response interface)',//抱歉，获取验证码失败（错误信息：接口无响应）
        alertSorryAuthCodeCheckFail: 'Sorry, failed to get auth code (error message:',//抱歉，获取验证码失败（错误信息：

        //格式化 认证 错误消息
        alertAuthFail: 'Sorry, authentication failed.',//抱歉，认证失败.
        alertAuthCodeError: 'sorry, auth code check failed, please enter again.',//抱歉，验证码校验失败，请重新输入.
        alertAuthCodeExpired: 'Sorry, auth code is failure, please get it again.',//抱歉，验证码已失效，请重新获取.
        alertAuthNoResponse: 'Sorry, authentication failed (error message: No response interface)',//抱歉，认证失败（错误信息：接口无响应）
        alertDuplicateChallengeRequest: 'In processing, please do not duplicate the operation.',//请求处理中，请勿重复认证。
        alertSorryAuthCheckFail: 'Sorry, authentication failed (error message:' //抱歉，认证失败（错误信息：
    };

    if(!_state.language || _state.language === 'cn'){
        _state.message = message_cn;
    }else{
        _state.message = message_en;
    }

    showOrHideQRCode();//显示或隐藏二维码认证

    var fatApAuthUrl = '/smartwifi/auth';//胖ap认证放行接口地址

    //内部接口
    var smsUrl = '/auth/sendSmsCode';//发送短信验证码接口地址
    var phoneAuthUrl = '/auth/phoneAuth';//手机认证接口地址
    var userLoginUrl = '/staticuser/phoneAuth';//静态用户登录接口地址
    var modifyPwdUrl = '/staticuser/modifypwd';//静态用户名认证--修改密码接口地址
    var sendAuthcodeUrl = '/authcode/sendauthcode';//静态用户名认证--忘记密码--发送验证码接口地址
    var checkAuthcodeUrl = '/authcode/iscorrect';//静态用户名认证--忘记密码--校验验证码接口地址
    var modifyPwdByCellphoneUrl = '/staticuser/modifypwdbycellphone';//静态用户名认证--忘记密码--修改密码接口地址
    var checkBlackByCellphoneUrl = '/blackuser/checkbyphone';//黑名单--校验手机号是否在黑名单里接口地址
    var authVersion = 'v1.1';//接入系统版本号
    var ivrCallUrl = '/ivr/call';//IVR语音认证拨号接口地址
    var ivrPollUrl = '/ivr/poll';//IVR语音认证放行轮询接口地址
    var getQRCodeUrl = '/pagesrv/qrcode';//获取二维码图片接口地址
    var qrCodeHeartBeatUrl = '/pagesrv/qrcode/heartbeat';//二维码认证—心跳轮询接口地址


    /**
     * 认证组件(顶层组件)
     */
    var AuthComponent = React.createClass({
        getInitialState: function() {
            return _state;
        },
        //tab点击事件
        handleTabClick: function(e){
            var tabObj = e.target;
            var childNodes = tabObj.childNodes;
            var tabContent = childNodes[0].innerHTML;
            if(tabContent == undefined){
                tabContent = tabObj.innerHTML;
            }
            //选中 手机号认证
            if(tabContent === _state.message.mobileTitle){
                _state.active = 'mobile';
                _state.display = 'mobile';
            }
            //选中 静态用户名认证
            else if(tabContent === _state.message.userPassTitle){
                _state.active = 'userPass';
                _state.display = 'userPass';
            }
            //选中 语音认证
            else if(tabContent === _state.message.voiceTitle){
                _state.active = 'voice';
                _state.display = 'voice';
            }
            this.setState(_state);
        },
        //更新Auth组件状态
        setAuthState: function () {
            this.setState(_state);
        },
        render: function() {
            /* 设置tab宽度样式 */
            var tabClass = '';
            if(this.state.tabNum == 2){
                tabClass = 'resetWidth';
            }
            return (
                <div className={"_Entity_ " + this.state.style}>
                    <div className={ (this.state.tabNum > 1 ? 'model clearfix ' : '') + (this.state.display=='free' ? 'hide' : '') + ' ' + this.state.hideAuthDivStyle}>
                        { this.state.hasMobile && this.state.tabNum > 1 ? <MobileAuthTabComponent active={ this.state.active } click={ this.handleTabClick } title={ this.state.message.mobileTitle } tabClass={ tabClass } titleFontColor={this.state.titleFontColor} /> : '' }
                        { this.state.hasUserPass && this.state.tabNum > 1 ? <UserPassAuthTabComponent active={ this.state.active } click={ this.handleTabClick } title={ this.state.message.userPassTitle } tabClass={ tabClass } titleFontColor={this.state.titleFontColor} /> : '' }
                        { this.state.hasVoice && this.state.tabNum > 1 ? <VoiceAuthTabComponent active={ this.state.active } click={ this.handleTabClick } title={ this.state.message.voiceTitle } tabClass={ tabClass } titleFontColor={this.state.titleFontColor} /> : '' }
                    </div>
                    { this.state.openMessage && !this.state.hasQRCode ? <MessageComponent setAuthState={ this.setAuthState } display={ this.state.display } /> : '' }
                    <div className={ 'forms clearfix ' + this.state.hideAuthDivStyle }>
                        { this.state.hasFree && this.state.display == 'free' ?
                            (_state.hasAutoAuth ?
                                <AutoAuthComponent setAuthState={ this.setAuthState } /> :
                                <FreeAuthComponent buttonDsp={ this.state.message.freeButtonDsp } setAuthState={ this.setAuthState } />)
                            : '' }
                        { this.state.hasMobile && !this.state.hasQRCode ? <MobileAuthComponent display={ this.state.display }
                                                                                               setAuthState={ this.setAuthState }
                                                                                               mobileLoginBtnSty={ this.state.mobileLoginBtnSty }
                                                                                               hasMsgSend={this.state.hasMsgSend }
                                                                                               hasQRCode={this.state.hasQRCode }
                        /> : '' }
                        { this.state.hasMobile && this.state.hasQRCode ? <QRCodeAuthComponent display={ this.state.display }
                                                                                              setAuthState={ this.setAuthState }
                        /> : '' }
                        { this.state.hasUserPass ? <UserPassAuthComponent display={ this.state.display }
                                                                          setAuthState={ this.setAuthState }
                                                                          userName={ this.state.userName }
                                                                          hasModefiyPassword={ this.state.hasModefiyPassword }
                                                                          hasForgetPassword={ this.state.hasForgetPassword }
                                                                          titleFontColor={this.state.titleFontColor}
                        /> : '' }
                        { this.state.hasVoice ? <VoiceAuthComponent  display={ this.state.display } titleFontColor={this.state.titleFontColor} /> : ''}
                    </div>
                    { this.state.showModifyPasswordForSetNewPassword ? <ModifyPasswordForSetNewPasswordComponent setAuthState={ this.setAuthState } /> : '' }
                    { this.state.showForgetPasswordForCheckMobile ? <ForgetPasswordForCheckMobileComponent setAuthState={ this.setAuthState } /> : '' }
                    { this.state.showForgetPasswordForSetNewPassword ? <ForgetPasswordForSetNewPasswordComponent setAuthState={ this.setAuthState } /> : '' }
                    { this.state.showWelcome ? <WelcomeComponent/> : '' }

                </div>
            );
        }
    });

    /**
     *  手机号认证Tab组件
     */
    var MobileAuthTabComponent = React.createClass({
        tabClick: function(eventType, e){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            this.props.click(e);
        },
        render: function() {
            return (
                <div className={ this.props.active=='mobile' ? 'active ' + this.props.tabClass : this.props.tabClass }
                     style={ this.props.titleFontColor ? {color: this.props.titleFontColor} : {} }
                     onClick={ this.tabClick.bind(this, 'click') } onTouchStart={ this.tabClick.bind(this, 'touch') }
                ><div className="top_title">{ this.props.title }</div>
                </div>
            );
        }
    });

    /**
     *  静态用户名认证Tab组件
     */
    var UserPassAuthTabComponent = React.createClass({
        tabClick: function(eventType, e){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            this.props.click(e);
        },
        render: function() {
            return (
                <div className={ this.props.active=='userPass' ? 'active ' + this.props.tabClass : this.props.tabClass}
                     style={ this.props.titleFontColor ? {color: this.props.titleFontColor} : {} }
                     onClick={ this.tabClick.bind(this, 'click') } onTouchStart={ this.tabClick.bind(this, 'touch') }
                ><div className="top_title">{ this.props.title }</div>
                </div>
            );
        }
    });

    /**
     *  语音认证Tab组件
     */
    var VoiceAuthTabComponent = React.createClass({
        tabClick: function(eventType, e){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            this.props.click(e);
        },
        render : function(){
            return (
                <div className={ this.props.active == 'voice' ? 'active ' + this.props.tabClass : this.props.tabClass}
                     style={ this.props.titleFontColor ? {color: this.props.titleFontColor} : {} }
                     onClick={ this.tabClick.bind(this, 'click') } onTouchStart={ this.tabClick.bind(this, 'touch') }
                ><div className="top_title">{ this.props.title }</div>
                </div>
            )
        }
    });

    /**
     *  手机号认证组件
     */
    var MobileAuthComponent = React.createClass({
        second: 60,// 倒计时
        option: {
            getAuthCodeBtnDsp: _state.message.getAuthCodeBtnDsp,//获取验证码 按钮显示值
            getAuthCodeBtnSty: '',//获取验证码按钮样式
            getAuthCodeBtnDisabled: '',//获取验证码按钮变disabled
            loginDisabled: '',//登录按钮变disabled
            mobile: '',//手机号
            authCode: ''//验证码
        },
        getInitialState: function() {
            return this.option;
        },
        //获取验证码
        getAuthCode: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            if(this.option.getAuthCodeBtnDisabled == 'disabled'){//因disabled针对onTouch事件无效，加此判断，防止重复执行
                return;
            }
            var _this = this;
            //开启黑名单
            if(_state.openBlacklist){
                var mobile = _this.state.mobile;//手机号
                if(mobile === '' || !(/^(1[3|4|5|7|8][0-9][0-9]{8})?$/).test(mobile)){
                    alert(_state.message.alertInputCellphone);//请输入正确的手机号码
                    return;
                }
                //3.发送短信验证码
                checkBlackByCellphone(mobile, _this.sendAuthCode);
            }
            //关闭黑名单
            else{
                _this.sendAuthCode();
            }
        },
        //发送短信验证码，按钮同时进入倒计时
        sendAuthCode: function(){
            var _this = this;
            var mobile = this.state.mobile;//手机号
            if(mobile === '' || !(/^(1[3|4|5|7|8][0-9][0-9]{8})?$/).test(mobile)){
                alert(_state.message.alertInputCellphone);//请输入正确的手机号码
                return;
            }
            var leftTime = _this.second;//剩余时间
            this.option.getAuthCodeBtnSty = 'disaButton';
            this.option.getAuthCodeBtnDisabled = 'disabled';
            this.option.getAuthCodeBtnDsp = _state.message.reacquire + '(' + leftTime + ')';//重新获取
            _this.setState(this.option);
            leftTime --;
            var timer = setInterval(function(){
                if(leftTime <= 0){
                    clearInterval(timer);
                    _this.option.getAuthCodeBtnDisabled = '';
                    _this.option.getAuthCodeBtnSty = '';
                    _this.option.getAuthCodeBtnDsp = _state.message.getAuthCodeBtnDsp;//获取验证码
                    _this.setState(_this.option);
                    return;
                }
                _this.option.getAuthCodeBtnDsp = _state.message.reacquire + '(' + leftTime + ')';//重新获取
                _this.setState(_this.option);
                leftTime --;
            }, 1000);
            sendSmsCode(mobile, _this);//发送短信验证码
        },
        //手机号change事件
        handleMobileChange: function(e){
            this.option.mobile = e.target.value;
            this.setState(this.option);
        },
        //验证码change事件
        handleAuthCodeChange: function(e){
            this.option.authCode = e.target.value;
            this.setState(this.option);
        },
        //显示登陆按钮
        showLoginBtn: function(){
            _state.mobileLoginBtnSty = '';
            this.props.setAuthState(_state);
        },
        //登录按钮变灰
        disabledButton: function(){
            this.option.loginDisabled = 'disabled';
            this.option.loginBtnSty = 'disaButton';
            this.setState(this.option);//登录按钮变灰
        },
        //登录按钮恢复
        enableButton: function(){
            this.option.loginDisabled = '';
            this.option.loginBtnSty = '';
            this.setState(this.option);//登录按钮变灰
        },
        //免费登录
        handleLogin: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            if(this.option.loginDisabled == 'disabled'){//因disabled针对onTouch事件无效，加此判断，防止重复执行
                return;
            }
            if(_DISCLAIMER === 'refuse'){// 判断免责声明
                alert(_state.message.alertAgreeDisclaimer);//请先同意免责声明
                return;
            }
            var mobile = this.state.mobile;//手机号
            var authCode = this.state.authCode;//验证码
            if(mobile === '' || !(/^(1[3|4|5|7|8][0-9][0-9]{8})?$/).test(mobile)){
                alert(_state.message.alertInputCellphone);//请输入正确的手机号码
                return;
            }
            if(authCode === '' || !(/^[0-9]{6}$/).test(authCode)){
                alert(_state.message.alertInputAuthCode);//请输入正确的验证码
                return;
            }
            this.disabledButton();//登录按钮变灰
            phoneAuth(mobile, authCode, '1',this);
        },
        //切换二维码点击事件
        handleQRCodeSwitchClick(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            _state.hasQRCode = true;
            this.props.setAuthState();
        },
        render: function() {
            //中英文特殊处理
            var curGetAuthCodeBtnDsp = this.state.getAuthCodeBtnDsp;
            if(curGetAuthCodeBtnDsp === message_cn.getAuthCodeBtnDsp || curGetAuthCodeBtnDsp  === message_en.getAuthCodeBtnDsp){
                this.state.getAuthCodeBtnDsp = _state.message.getAuthCodeBtnDsp;
            }
            return (
                <div className={ 'form-item qrcode' + (this.props.display=='mobile' ? ' show' : ' hide') }>
                    <div className={ 'inputWord' + (_hasQRCode ? ' show' : ' hide') }
                         onClick={ this.handleQRCodeSwitchClick.bind(this, 'click') } onTouchStart={ this.handleQRCodeSwitchClick.bind(this, 'touch') }
                    ></div>
                    <div className="input-control">
                        <em className="icon-phone"></em>
                        <input type="text" onChange={ this.handleMobileChange } value={ this.state.mobile } placeholder={ _state.message.inputCellphonePlaceholder } />
                    </div>
                    <div className="input-control">
                        <em className="icon-lock"></em>
                        <input type="text" onChange={ this.handleAuthCodeChange } value={ this.state.authCode } placeholder={ _state.message.inputAuthCodePlaceholder } />
                        <input type="button" disabled={ this.state.getAuthCodeBtnDisabled } className={ this.state.getAuthCodeBtnSty }
                               onClick={ this.getAuthCode.bind(this, 'click') } onTouchStart={ this.getAuthCode.bind(this, 'touch') } value={ this.state.getAuthCodeBtnDsp } />
                    </div>
                    <div className="btn-login">
                        <input type="button" value={ _state.message.freeLoginBtnDsp } className={ this.props.mobileLoginBtnSty } disabled={ this.state.loginDisabled }
                               onClick={ this.handleLogin.bind(this, 'click') } onTouchStart={ this.handleLogin.bind(this, 'touch') } />
                    </div>
                    { this.props.hasMsgSend ? <MessageSendComponent /> : '' }
                </div>
            );
        }
    });

    /**
     *  二维码认证组件
     */
    var QRCodeAuthComponent = React.createClass({
        //默认state
        option: {
            timer: null,//二维码-心跳轮询定时对象
            redisKey: '',//redis缓存key
            qrCodeImgURL: '',//二维码图片地址
            qrCodeStatus: 'loading'//二维码加载状态：loading代表加载中、loaded代表已加载、invalid代表失效
        },
        //获取实例的初始化状态
        getInitialState: function() {
            return this.option;
        },
        //组件将要被装载
        componentWillMount: function(){
            getQRCode(this);
        },
        //二维码--点击图片刷新点击事件
        handleRefreshQRCodeImgClick: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            this.option.qrCodeStatus = 'loading';//将状态设置为：加载中
            this.setState(this.option);
            getQRCode(this);
        },
        //二维码--请点击刷新按钮点击事件
        handleRefreshQRCodeBtnClick: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            this.option.qrCodeStatus = 'loading';//将状态设置为：加载中
            this.setState(this.option);
            getQRCode(this);
        },
        //切换手机号点击事件
        handleMobileSwitchClick(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //如果timer不为空时,需要销毁上一次的定时器
            var timer = this.option.timer;
            if(timer){
                clearInterval(timer);
                this.option.timer = '';
            }
            //隐藏二维码认证
            _state.hasQRCode = false;
            this.props.setAuthState();
        },
        //登录按钮恢复
        enableButton: function(){
            //隐藏二维码认证
            _state.hasQRCode = false;
            this.props.setAuthState();
        },
        //二维码心跳接口触发
        startQRCodeHeartBeat: function(){
            var _this = this;
            var redisKey = this.state.redisKey;//redis缓存key
            //如果timer不为空时,需要销毁上一次的定时器
            var timer = this.option.timer;
            if(timer){
                clearInterval(timer);
            }
            //重新创建定时器
            var newTimer = setInterval(function(){//每隔3秒轮询一次
                qrCodeHeartBeat(redisKey, newTimer, _this);
            }, 3000);
            this.option.timer = newTimer;
        },
        render: function(){
            return (
                <div className={ this.props.display=='mobile' ? 'show' : 'hide' } >
                    <div className="qrcode relative">
                        <span className="exchange qrcodeInfo"
                              onClick={ this.handleMobileSwitchClick.bind(this, 'click') } onTouchStart={ this.handleMobileSwitchClick.bind(this, 'touch') }
                        ></span>
                        <div className={ 'qr-code-loading relative' + (this.state.qrCodeStatus === 'loading' ? ' show' : ' hide') } ></div>
                        <div className={ 'relative top' + (this.state.qrCodeStatus === 'loaded' ? ' show' : ' hide') } >
                            <img src={ this.state.qrCodeImgURL } className="qrCodeImg" title="点击刷新"
                                 onClick={ this.handleRefreshQRCodeImgClick.bind(this, 'click') } onTouchStart={ this.handleRefreshQRCodeImgClick.bind(this, 'touch') } />
                        </div>
                        <div className={ 'relative top' + (this.state.qrCodeStatus === 'invalid' ? ' show' : ' hide') } >
                            <img src={ this.state.qrCodeImgURL } className="qrCodeImg"/>
                            <div className="qr-code-error"></div>
                            <p className="error-text timeout-error-text">
                                二维码已失效
                                <a className="sm-btn"
                                   onClick={ this.handleRefreshQRCodeBtnClick.bind(this, 'click') } onTouchStart={ this.handleRefreshQRCodeBtnClick.bind(this, 'touch') }
                                >请点击刷新</a>
                            </p>
                        </div>
                        <div className="scanCode"><span className="icon"></span>
                            请打开手机移动网络扫码！
                        </div>
                    </div>
                </div>
            );
        }
    });

    /**
     *  静态用户名认证组件
     */
    var UserPassAuthComponent = React.createClass({
        option: {
            getAuthCodeBtnSty: '',
            getAuthCodeBtnDisabled: '',//按钮变disabled
            loginBtnSty: '',//登录按钮样式
            loginDisabled: '',//登录按钮变disabled
            hasModefiyPassword: true,//是否包含功能点：修改密码
            hasForgetPassword: true,//是否包含功能点：忘记密码
            userName: '',//静态用户名
            password: ''//密码
        },
        getInitialState: function() {
            this.option.hasModefiyPassword = this.props.hasModefiyPassword;
            this.option.hasForgetPassword = this.props.hasForgetPassword;
            this.option.userName = this.props.userName;
            return this.option;
        },
        componentWillReceiveProps: function(parentProps){
            this.option.hasModefiyPassword = this.props.hasModefiyPassword;
            this.option.hasForgetPassword = this.props.hasForgetPassword;
            this.option.userName = parentProps.userName;
            this.setState(this.option);
        },
        //静态用户名change事件
        handleUserNameChange: function(e){
            var userName = e.target.value;
            this.option.userName = userName;
            this.setState(this.option);
            //更新Auth组件状态
            _state.userName = userName;
            this.props.setAuthState();
        },
        //密码change事件
        handlePasswordChange: function(e){
            this.option.password = e.target.value;
            this.setState(this.option);
        },
        //登录按钮变灰
        disabledButton: function(){
            this.option.loginDisabled = 'disabled';
            this.option.loginBtnSty = 'disaButton';
            this.setState(this.option);//登录按钮变灰
        },
        //登录按钮恢复
        enableButton: function(){
            this.option.loginDisabled = '';
            this.option.loginBtnSty = '';
            this.setState(this.option);//登录按钮变灰
        },
        //登录按钮点击事件
        handleLoginClick: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            if(this.option.loginDisabled == 'disabled'){//因disabled针对onTouch事件无效，加此判断，防止重复执行
                return;
            }
            if(_DISCLAIMER === 'refuse'){// 判断免责声明
                alert(_state.message.alertAgreeDisclaimer);//请先同意免责声明
                return;
            }
            var userName = this.state.userName;//静态用户名
            var password = this.state.password;//密码
            if(userName === ''){
                alert(_state.message.inputUserNamePlaceholder);//请输入用户名
                return;
            }
            if(password === ''){
                alert(_state.message.inputPasswordPlaceholder);//请输入密码
                return;
            }
            this.disabledButton();//登录按钮变灰
            userLogin(userName, password, this);
        },
        //修改密码
        handModifyPassword: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //1.隐藏认证主组件内容
            _state.hideAuthDivStyle = 'hide';
            //2.显示修改密码--设置新密码组件
            _state.showModifyPasswordForSetNewPassword = true;
            //3.更新Auth组件状态
            this.props.setAuthState();
        },
        //忘记密码
        handForgetPassword: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //1.隐藏认证主组件内容
            _state.hideAuthDivStyle = 'hide';
            //2.显示忘记密码--验证手机号组件
            _state.showForgetPasswordForCheckMobile = true;
            //3.更新Auth组件状态
            this.props.setAuthState();
        },
        render: function() {
            return (
                <div className={ 'form-item ' + (this.props.display=='userPass' ? 'show' : 'hide') }>
                    <div className="input-control resetPadding">
                        <input type="text" value={ this.state.userName } onChange={ this.handleUserNameChange } placeholder={ _state.message.inputUserNamePlaceholder } />
                    </div>
                    <div className="input-control resetPadding">
                        <input type="password" value={ this.state.password } onChange={ this.handlePasswordChange } placeholder={ _state.message.inputPasswordPlaceholder } />
                        <input type="button" onClick={ this.handleLoginClick.bind(this, 'click') } onTouchStart={ this.handleLoginClick.bind(this, 'touch') } className={ this.state.loginBtnSty } disabled={ this.state.loginDisabled } value={ _state.message.freeLoginBtnDsp } />
                    </div>
                    <div className="optArea">
                        <span className={this.state.hasModefiyPassword ? '' : 'hide'} style={ this.props.titleFontColor ? {color: this.props.titleFontColor} : {} }
                              onClick={ this.handModifyPassword.bind(this, 'click') } onTouchStart={ this.handModifyPassword.bind(this, 'touch') } >{ _state.message.modifyPassword }</span>
                        <span className={this.state.hasModefiyPassword && this.state.hasForgetPassword ? '' : 'hide'} style={ this.props.titleFontColor ? {color: this.props.titleFontColor} : {} } >|</span>
                        <span className={this.state.hasForgetPassword ? '' : 'hide'} style={ this.props.titleFontColor ? {color: this.props.titleFontColor} : {} }
                              onClick={ this.handForgetPassword.bind(this, 'click') } onTouchStart={ this.handForgetPassword.bind(this, 'touch') } >{ _state.message.forgetPassword }</span>
                    </div>
                </div>
            );
        }
    });

    /**
     * IVR认证组件
     */
    var VoiceAuthComponent = React.createClass({
        second: 60,// 倒计时
        timer: null,//倒计时-对象
        option: {
            loginBtnDsp: _state.message.voiceSubmitBtn,//获取验证码 按钮显示值
            loginBtnSty: '',//登录按钮样式
            loginDisabled: '',//登录按钮变disabled
            country_list: {
                "355":"Albania","213":"Algeria","93":"Afghanistan","54":"Argentina","971":"United Arab Emirates",
                "968":"Oman","994":"Azerbaijan","247":"Ascension","20":"Egypt","251":"Ethiopia","353":"Ireland",
                "372":"Estonia","376":"Andorra","244":"Angola","1264":"Anguilla","1268":"Antigua and Barbuda",
                "43":"Austria","61":"Australia","1246":"Barbados","675":"Papua New Cuinea","1242":"Bahamas",
                "92":"Pakistan","595":"Paraguay","973":"Bahrain","507":"Panama","55":"Brazil","375":"Belarus",
                "1441":"Bermuda Is.","359":"Bulgaria","229":"Benin","32":"Belgium","354":"Iceland","1787":"Puerto Rico",
                "48":"Poland","591":"Bolivia","501":"Belize","267":"Botswana","226":"Burkina-faso","257":"Burundi",
                "850":"North Korea","45":"Denmark","49":"Germany","684":"Samoa Eastern","228":"Togo","1890":"Dominica Rep.",
                "7":"Russia","593":"Ecuador","33":"France","689":"French Polynesia","594":"French Guiana","63":"Philippines",
                "679":"Fiji","358":"Finland","220":"Gambia","242":"Congo","57":"Colombia","506":"Costa Rica","1809":"Grenada",
                "995":"Georgia","53":"Cuba","1671":"Guam","592":"Guyana","327":"Kazakstan","509":"Haiti","82":"Korea",
                "31":"Netherlands","599":"Netheriands Antilles","504":"Honduras","253":"Djibouti","331":"Kyrgyzstan",
                "224":"Guinea","233":"Ghana","241":"Gabon","855":"Kampuchea (Cambodia)","420":"Czech Republic",
                "263":"Zimbabwe","237":"Cameroon","974":"Qatar","1345":"Cayman Is.","225":"Ivory Coast","965":"Kuwait",
                "254":"Kenya","682":"Cook Is.","371":"Latvia","266":"Lesotho","856":"Laos","961":"Lebanon","370":"Lithuania",
                "231":"Liberia","218":"Libya","423":"Liechtenstein","262":"Reunion","352":"Luxembourg","40":"Romania",
                "261":"Madagascar","960":"Maldives","356":"Malta","265":"Malawi","60":"Malaysia","223":"Mali","1670":"Mariana Is",
                "596":"Martinique","230":"Mauritius","1":"U.S.A(Canada)","976":"Mongolia","1664":"Montserrat Is",
                "880":"Bangladesh","51":"Peru","95":"Burma","373":"Moldova, Republic of","212":"Morocco","377":"Monaco",
                "258":"Mozambique","52":"Mexico","264":"Namibia","27":"South Africa","381":"Yugoslavia","674":"Nauru",
                "505":"Nicaragua","977":"Nepal","227":"Niger","234":"Nigeria","47":"Norway","351":"Portugal","81":"Japan",
                "46":"Sweden","41":"Switzerland","503":"EI Salvador","232":"Sierra Leone","221":"Senegal","357":"Cyprus",
                "248":"Seychelles","966":"Saudi Arabia","239":"Sao Tome and Principe","1758":"Saint Lueia","378":"San Marino",
                "1784":"St.Vincent","94":"Sri Lanka","421":"Slovakia","386":"Slovenia","268":"Swaziland","249":"Sudan",
                "597":"Suriname","677":"Solomon Is","252":"Somali","992":"Tajikstan","66":"Thailand","255":"Tanzania",
                "676":"Tonga","216":"Tunisia","90":"Turkey","993":"Turkmenistan","502":"Guatemala","58":"Venezuela",
                "673":"Brunei","256":"Uganda","380":"Ukraine","598":"Uruguay","30":"Greece","34":"Spain","685":"Samoa Western",
                "65":"Singapore","64":"New Zealand","36":"Hungary","963":"Syria","1876":"Jamaica","374":"Armenia","967":"Yemen",
                "964":"Iraq","98":"Iran","972":"Israel","39":"Italy","91":"India","62":"Indonesia","44":"United Kiongdom",
                "962":"Jordan","84":"Vietnam","260":"Zambia","243":"Zaire","235":"Chad","350":"Gibraltar","56":"Chile",
                "236":"Central African Republic","86":"China","852":"Hongkong","853":"Macao","886":"Taiwan"
            },
            code: '86',//国家区号
            country: 'China',//国家名称
            mobile: ''//手机号
        },
        getInitialState: function() {
            return this.option;
        },
        // 国家编号change事件
        handCountryChange: function(e){
            var code = e.target.value;
            var country = this.state.country_list[code];
            this.option.code = code;
            this.option.country = country;
            this.setState(this.option);
        },
        //手机号change事件
        handleMobileChange: function(e){
            this.option.mobile = e.target.value;
            this.setState(this.option);
        },
        //重置60秒倒计时
        resetTimeInterval: function(){
            clearInterval(this.timer);
            this.option.loginDisabled = '';
            this.option.loginBtnSty = '';
            this.option.loginBtnDsp = _state.message.voiceSubmitBtn;//点击拨号
            this.setState(this.option);
        },
        //点击拨号 事件
        handleLogin: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            if(this.option.loginDisabled == 'disabled'){//因disabled针对onTouch事件无效，加此判断，防止重复执行
                return;
            }
            var _this = this;
            if(_DISCLAIMER === 'refuse'){// 判断免责声明
                alert(_state.message.alertAgreeDisclaimer);//请先同意免责声明
                return;
            }
            var mobile = _this.state.mobile;//手机号
            if(mobile === ''){
                alert(_state.message.inputCellphonePlaceholder);//请输入手机号码
                return;
            }
            if(!(/^[0-9]\d*/).test(mobile)){
                alert(_state.message.alertIVRCellphoneCheck);//手机号码只能是数字
                return;
            }
            var leftTime = _this.second;//剩余时间
            this.option.loginBtnSty = 'disaButton';
            this.option.loginDisabled = 'disabled';
            this.option.loginBtnDsp = _state.message.voiceSubmitBtn + '(' + leftTime + ')';//点击拨号
            _this.setState(this.option);
            leftTime --;
            _this.timer = setInterval(function(){
                if(leftTime <= 0){
                    clearInterval(_this.timer);
                    _this.option.loginDisabled = '';
                    _this.option.loginBtnSty = '';
                    _this.option.loginBtnDsp = _state.message.voiceSubmitBtn;//点击拨号
                    _this.setState(_this.option);
                    return;
                }
                _this.option.loginBtnDsp = _state.message.voiceSubmitBtn + '(' + leftTime + ')';//点击拨号
                _this.setState(_this.option);
                leftTime --;
            }, 1000);
            ivrCall(mobile, _this.state.code, _this.state.country, _this.resetTimeInterval, this);
        },
        render: function() {
            //中英文特殊处理
            var curLoginBtnDsp = this.state.loginBtnDsp;
            if(curLoginBtnDsp === message_cn.voiceSubmitBtn || curLoginBtnDsp  === message_en.voiceSubmitBtn){
                this.state.loginBtnDsp = _state.message.voiceSubmitBtn;
            }
            //循环生成国家编号
            var options = [];
            for (var i in this.state.country_list) {
                options.push(<option key={i} value={i}>+{i}&nbsp;&nbsp;&nbsp;&nbsp;{this.state.country_list[i]}</option>);
            }
            return (
                <div className={ 'form-item ' + (this.props.display=='voice' ? 'show' : 'hide') }>
                    <div className="input-control resetPadding">
                        <div className="countryInfo">
                            <select defaultValue="86" onChange={ this.handCountryChange }>
                                { options }
                            </select>
                        </div>
                        <table>
                            <tbody>
                            <tr>
                                <td className="code">+{ this.state.code }</td>
                                <td className="country">{ this.state.country }</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div className="input-control resetPadding">
                        <input type="text" value={ this.state.mobile } onChange={ this.handleMobileChange } placeholder={ _state.message.inputCellphonePlaceholder }/>
                    </div>
                    <div className="btn-login">
                        <input type="button" className={ this.state.loginBtnSty } disabled={ this.state.loginDisabled } value={ this.state.loginBtnDsp }
                               onClick={ this.handleLogin.bind(this, 'click') } onTouchStart={ this.handleLogin.bind(this, 'touch') } />
                    </div>
                    <div className="voiceMessage" style={ this.props.titleFontColor ? {color: this.props.titleFontColor} : {} } >{ _state.message.voiceMessage}</div>
                </div>
            );
        }
    });

    /**
     * 免费上网组件
     * 认证过后在有效时间内只需点击免费上网即可
     */
    var FreeAuthComponent = React.createClass({
        option: {
            loginBtnSty: '',//登录按钮样式
            loginDisabled: ''//登录按钮变disabled
        },
        getInitialState: function() {
            return this.option;
        },
        //登录按钮变灰
        disabledButton: function(){
            this.option.loginDisabled = 'disabled';
            this.option.loginBtnSty = 'disaButton';
            this.setState(this.option);//登录按钮变灰
        },
        //登录按钮恢复
        enableButton: function(){
            this.option.loginDisabled = '';
            this.option.loginBtnSty = '';
            this.setState(this.option);//登录按钮变灰
        },
        //切换认证按钮
        handSwitchAuth: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            _state.active = oldActive;
            _state.display = oldDisplay;
            this.props.setAuthState();
        },
        //免费上网点击
        handleLogin: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            if(this.option.loginDisabled == 'disabled'){//因disabled针对onTouch事件无效，加此判断，防止重复执行
                return;
            }
            //开启黑名单
            if(_state.openBlacklist){
                checkBlackByCellphone(_USER_PHONE, this.freeAuth);
            }
            //关闭黑名单
            else{
                this.freeAuth();
            }
        },
        //免费上网认证
        freeAuth: function(){
            if(_DISCLAIMER === 'refuse'){// 判断免责声明
                alert(_state.message.alertAgreeDisclaimer);//请先同意免责声明
                return;
            }
            this.disabledButton();//登录按钮变灰
            var mobile = _USER_PHONE;
            phoneAuth(mobile, '', '4',this);
        },
        render: function() {
            return (
                <div className="form-item show">
                    <div className="input-control btn-free">
                        <input type="button" className={ 'free ' + this.state.loginBtnSty } disabled={ this.state.loginDisabled } value={ this.props.buttonDsp }
                               onClick={ this.handleLogin.bind(this, 'click') } onTouchStart={ this.handleLogin.bind(this, 'touch') } />
                    </div>
                    <div className="changeAuth" onClick={ this.handSwitchAuth.bind(this, 'click') } onTouchStart={ this.handSwitchAuth.bind(this, 'touch') }>{ _state.message.changeAuthType }</div>
                </div>
            );
        }
    });

    /**
     * 自动登录上网组件
     * 认证过后在有效时间内自动登录上网
     */
    var AutoAuthComponent = React.createClass({
        option: {
            second: _state.autoAuthSecond,//倒计时
            autoAuthSty: 'autoAuth',//自动登录上网样式
            loadingSty: 'loading hide'//loading样式
        },
        getInitialState: function() {
            return this.option;
        },
        //调用接入认证放行接口失败后的回调函数
        enableButton: function(){
            _state.hasAutoAuth = false;//屏蔽自动登录功能，显示免费上网功能
            this.props.setAuthState();
        },
        componentDidMount: function(){
            if(_DEV_ID === '{@devId@}'){
                return;
            }
            var _this = this;
            var leftTime = _this.state.second;//剩余时间
            var timer = setInterval(function(){
                if(leftTime <= 1){
                    clearInterval(timer);
                    //_this.option.autoAuthSty = 'hide';//隐藏自动登录信息
                    _this.option.loadingSty = 'loading';//显示加载中...图片
                    _this.option.second = leftTime;
                    _this.setState(_this.option);
                    //调用认证放行接口
                    var mobile = _USER_PHONE;
                    phoneAuth(mobile, '', '4',_this);
                }else{
                    _this.setState({second: leftTime});
                }
                leftTime --;
            }, 1000);
        },
        render: function() {
            return (
                <div className="welcome">
                    <div className={ this.state.autoAuthSty } >{ _state.message.autoAuthComponentPrefix } <span className="time">{ this.state.second }</span> { _state.message.autoAutheComponentSuffix }</div>
                    <div className={ this.state.loadingSty } ></div>
                </div>
            );
        }
    });

    /**
     * 修改密码--设置新密码
     */
    var ModifyPasswordForSetNewPasswordComponent = React.createClass({
        option: {
            loginBtnSty: '',//登录按钮样式
            loginDisabled: '',//登录按钮变disabled
            userName: '',//静态用户名
            oldPassword: '',//旧密码
            newPassword: '',//新密码
            confirmPassword: ''//确认密码
        },
        getInitialState: function() {
            this.option.userName = _state.userName;
            return this.option;
        },
        //用户名变更事件
        handleUserNameChange: function(e){
            var userName = e.target.value;
            this.option.userName = userName;
            this.setState(this.option);
            //更新Auth组件状态
            _state.userName = userName;
            this.props.setAuthState();
        },
        //旧密码变更事件
        handleOldPasswordChange: function(e){
            this.option.oldPassword = e.target.value;
            this.setState(this.option);
        },
        //新密码变更事件
        handleNewPasswordChange: function(e){
            this.option.newPassword = e.target.value;
            this.setState(this.option);
        },
        //确认密码变更事件
        handleConfirmPasswordChange: function(e){
            this.option.confirmPassword = e.target.value;
            this.setState(this.option);
        },
        //登录按钮变灰
        disabledButton: function(){
            this.option.loginDisabled = 'disabled';
            this.option.loginBtnSty = 'disaButton';
            this.setState(this.option);//登录按钮变灰
        },
        //登录按钮恢复
        enableButton: function(){
            this.option.loginDisabled = '';
            this.option.loginBtnSty = '';
            this.setState(this.option);//登录按钮变灰
        },
        //提交
        handleSubmit: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            if(this.option.loginDisabled == 'disabled'){//因disabled针对onTouch事件无效，加此判断，防止重复执行
                return;
            }
            //参数校验
            var userName = this.state.userName;//用户名
            if(userName === ''){
                alert(_state.message.inputUserNamePlaceholder);//请输入用户名
                return;
            }
            var oldPassword = this.state.oldPassword;//旧密码
            if(oldPassword === ''){
                alert(_state.message.alertInputOldPassword);//请输入旧密码
                return;
            }
            var newPassword = this.state.newPassword;//新密码
            if(newPassword === ''){
                alert(_state.message.alertInputNewPassword);//请输入新密码
                return;
            }
            if(!(/^[^\u4e00-\u9fa5]{4,20}$/).test(newPassword)){
                alert(_state.message.alertCheckPassword);//新密码必须由4-20位不包含汉字的字符组成！
                return;
            }
            if(oldPassword === newPassword){
                alert(_state.message.alertCheckPasswordSame);//旧密码与新密码不允许相同，请重新输入！
                return;
            }
            var confirmPassword = this.state.confirmPassword;//确认密码
            if(confirmPassword === ''){
                alert(_state.message.alertInputConfirmPassword);//请输入确认密码
                return;
            }
            if(!(/^[^\u4e00-\u9fa5]{4,20}$/).test(confirmPassword)){
                alert(_state.message.alertCheckConfirmPassword);//确认密码必须由4-20位不包含汉字的字符组成！
                return;
            }
            if(newPassword != confirmPassword){
                alert(_state.message.alertCheckNewConfirmPasswordSame);//新密码与确认密码不一致，请重新输入！
                return;
            }
            this.disabledButton();//登录按钮变灰
            modifyPwd(userName, oldPassword, newPassword, confirmPassword, this);
        },
        //取消
        handleCancel: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //1.显示认证主组件内容
            _state.hideAuthDivStyle = '';
            //2.显示修改密码--设置新密码组件
            _state.showModifyPasswordForSetNewPassword = false;
            //3.更新Auth组件状态
            this.props.setAuthState();
        },
        //跳转到下一场景
        forward: function(){
            //1.显示认证主组件内容
            _state.hideAuthDivStyle = '';
            //2.显示修改密码--设置新密码组件
            _state.showModifyPasswordForSetNewPassword = false;
            //3.更新Auth组件状态
            this.props.setAuthState();
        },
        //清除用户输入
        clearInput: function(){
            this.option.oldPassword = '';
            this.option.newPassword = '';
            this.option.confirmPassword = '';
            this.setState(this.option);
        },
        render: function() {
            return (
                <div className="forms clearfix">
                    <div className="form-item show">
                        <div className="input-control resetPadding">
                            <input type="text" value={ this.state.userName } placeholder={ _state.message.inputUserNamePlaceholder } onChange={ this.handleUserNameChange } />
                        </div>
                        <div className="input-control resetPadding">
                            <input type="password" value={ this.state.oldPassword } placeholder={ _state.message.alertInputOldPassword } onChange={ this.handleOldPasswordChange } />
                        </div>
                        <div className="input-control resetPadding">
                            <input type="password" value={ this.state.newPassword } placeholder={ _state.message.alertInputNewPassword } onChange={ this.handleNewPasswordChange } />
                        </div>
                        <div className="input-control resetPadding">
                            <input type="password" value={ this.state.confirmPassword } placeholder={ _state.message.alertInputConfirmPassword } onChange={ this.handleConfirmPasswordChange } />
                            <input type="button" value={ _state.message.submitBtn } className={ 'submit ' + this.state.loginBtnSty } disabled={ this.state.loginDisabled }
                                   onClick={ this.handleSubmit.bind(this, 'click') } onTouchStart={ this.handleSubmit.bind(this, 'touch') } />
                            <input type="button" className="cancel" value={ _state.message.cancelBtn } onClick={ this.handleCancel.bind(this, 'click') } onTouchStart={ this.handleCancel.bind(this, 'touch') } />
                        </div>
                    </div>
                </div>
            );
        }
    });

    /**
     * 忘记密码--验证手机号
     */
    var ForgetPasswordForCheckMobileComponent = React.createClass({
        second: 60,//// 倒计时
        option: {
            getAuthCodeBtnDsp: _state.message.getAuthCodeBtnDsp,//获取验证码 按钮显示值
            getAuthCodeBtnSty: '',//获取验证码按钮样式
            getAuthCodeBtnDisabled: '',//获取验证码按钮变disabled
            loginBtnSty: '',//登录按钮样式
            loginDisabled: '',//登录按钮变disabled
            mobile: '',//手机号
            authCode: ''//验证码
        },
        getInitialState: function() {
            return this.option;
        },
        //获取验证码
        getAuthCode: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            if(this.option.getAuthCodeBtnDisabled == 'disabled'){//因disabled针对onTouch事件无效，加此判断，防止重复执行
                return;
            }
            var _this = this;
            var mobile = this.state.mobile;//手机号
            if(mobile === '' || !(/^(1[3|4|5|7|8][0-9][0-9]{8})?$/).test(mobile)){
                alert(_state.message.alertInputCellphone);//请输入正确的手机号码
                return;
            }
            var leftTime = _this.second;//剩余时间
            this.option.getAuthCodeBtnSty = 'disaButton';
            this.option.getAuthCodeBtnDisabled = 'disabled';
            this.option.getAuthCodeBtnDsp = _state.message.reacquire + '(' + leftTime + ')';//重新获取
            _this.setState(this.option);
            leftTime --;
            var timer = setInterval(function(){
                if(leftTime <= 0){
                    clearInterval(timer);
                    _this.option.getAuthCodeBtnDisabled = '';
                    _this.option.getAuthCodeBtnSty = '';
                    _this.option.getAuthCodeBtnDsp = _state.message.getAuthCodeBtnDsp;//获取验证码
                    _this.setState(_this.option);
                    return;
                }
                _this.option.getAuthCodeBtnDsp = _state.message.reacquire +'(' + leftTime + ')';//重新获取
                _this.setState(_this.option);
                leftTime --;
            }, 1000);
            sendAuthcode(mobile, this);//发送短信验证码
        },
        //手机号change事件
        handleMobileChange: function(e){
            this.option.mobile = e.target.value;
            this.setState(this.option);
        },
        //验证码change事件
        handleAuthCodeChange: function(e){
            this.option.authCode = e.target.value;
            this.setState(this.option);
        },
        //登录按钮变灰
        disabledButton: function(){
            this.option.loginDisabled = 'disabled';
            this.option.loginBtnSty = 'disaButton';
            this.setState(this.option);//登录按钮变灰
        },
        //登录按钮恢复
        enableButton: function(){
            this.option.loginDisabled = '';
            this.option.loginBtnSty = '';
            this.setState(this.option);//登录按钮变灰
        },
        //提交
        handleSubmit: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            if(this.option.loginDisabled == 'disabled'){//因disabled针对onTouch事件无效，加此判断，防止重复执行
                return;
            }
            //参数校验
            var mobile = this.state.mobile;//手机号
            var authCode = this.state.authCode;//验证码
            if(mobile === '' || !(/^(1[3|4|5|7|8][0-9][0-9]{8})?$/).test(mobile)){
                alert(_state.message.alertInputCellphone);//请输入正确的手机号码
                return;
            }
            if(authCode === '' || !(/^[0-9]{6}$/).test(authCode)){
                alert(_state.message.alertInputAuthCode);//请输入正确的验证码
                return;
            }
            this.disabledButton();//登录按钮变灰
            checkAuthcode(mobile, authCode, this);
        },
        //取消
        handleCancel: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //1.显示 认证主组件内容
            _state.hideAuthDivStyle = '';
            //2.隐藏 忘记密码--验证手机号组件
            _state.showForgetPasswordForCheckMobile = false;
            //3.更新Auth组件状态
            this.props.setAuthState();
        },
        //跳转到下一个场景
        forward: function(){
            //1.显示 是否显示：忘记密码--设置新密码组件
            _state.showForgetPasswordForSetNewPassword = true;
            //2.隐藏 忘记密码--验证手机号组件
            _state.showForgetPasswordForCheckMobile = false;
            //3.更新Auth组件状态
            this.props.setAuthState();
        },
        render: function() {
            //中英文特殊处理
            var curGetAuthCodeBtnDsp = this.state.getAuthCodeBtnDsp;
            if(curGetAuthCodeBtnDsp === message_cn.getAuthCodeBtnDsp || curGetAuthCodeBtnDsp  === message_en.getAuthCodeBtnDsp){
                this.state.getAuthCodeBtnDsp = _state.message.getAuthCodeBtnDsp;
            }
            return (
                <div className="forms clearfix">
                    <div className="form-item show">
                        <div className="input-control resetPadding">
                            <input type="text" value={ this.state.mobile } placeholder={ _state.message.inputCellphoneConfirmPlaceholder } onChange={ this.handleMobileChange } />
                            <input type="button" value={ this.state.getAuthCodeBtnDsp } disabled={ this.state.getAuthCodeBtnDisabled } className={ this.state.getAuthCodeBtnSty }
                                   onClick={ this.getAuthCode.bind(this, 'click') } onTouchStart={ this.getAuthCode.bind(this, 'touch') } />
                        </div>
                        <div className="input-control resetPadding">
                            <input type="text" value={ this.state.authCode } placeholder={ _state.message.inputAuthCodePlaceholder } onChange={ this.handleAuthCodeChange } />
                            <input type="button" value={ _state.message.submitBtn } className={ 'submit ' + this.state.loginBtnSty } disabled={ this.state.loginDisabled }
                                   onClick={ this.handleSubmit.bind(this, 'click') } onTouchStart={ this.handleSubmit.bind(this, 'touch') } />
                            <input type="button" className="cancel" value={ _state.message.cancelBtn } onClick={ this.handleCancel.bind(this, 'click') } onTouchStart={ this.handleCancel.bind(this, 'touch') } />
                        </div>
                    </div>
                </div>
            );
        }
    });

    /**
     * 忘记密码--设置新密码
     */
    var ForgetPasswordForSetNewPasswordComponent = React.createClass({
        option: {
            loginBtnSty: '',//登录按钮样式
            loginDisabled: '',//登录按钮变disabled
            newPassword: '',//新密码
            confirmPassword: ''//确认密码
        },
        getInitialState: function() {
            return this.option;
        },
        //新密码变更事件
        handleNewPasswordChange: function(e){
            this.option.newPassword = e.target.value;
            this.setState(this.option);
        },
        //确认密码变更事件
        handleConfirmPasswordChange: function(e){
            this.option.confirmPassword = e.target.value;
            this.setState(this.option);
        },
        //登录按钮变灰
        disabledButton: function(){
            this.option.loginDisabled = 'disabled';
            this.option.loginBtnSty = 'disaButton';
            this.setState(this.option);//登录按钮变灰
        },
        //登录按钮恢复
        enableButton: function(){
            this.option.loginDisabled = '';
            this.option.loginBtnSty = '';
            this.setState(this.option);//登录按钮变灰
        },
        //提交
        handleSubmit: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            if(this.option.loginDisabled == 'disabled'){//因disabled针对onTouch事件无效，加此判断，防止重复执行
                return;
            }
            //参数校验
            var newPassword = this.state.newPassword;//新密码
            if(newPassword === ''){
                alert(_state.message.alertInputNewPassword);//请输入新密码
                return;
            }
            if(!(/^[^\u4e00-\u9fa5]{4,20}$/).test(newPassword)){
                alert(_state.message.alertCheckPassword);//新密码必须由4-20位不包含汉字的字符组成！
                return;
            }
            var confirmPassword = this.state.confirmPassword;//确认密码
            if(confirmPassword === ''){
                alert(_state.message.alertInputConfirmPassword);//请输入确认密码
                return;
            }
            if(!(/^[^\u4e00-\u9fa5]{4,20}$/).test(confirmPassword)){
                alert(_state.message.alertCheckConfirmPassword);//确认密码必须由4-20位不包含汉字的字符组成！
                return;
            }
            if(newPassword != confirmPassword){
                alert(_state.message.alertCheckNewConfirmPasswordSame);//新密码与确认密码不一致，请重新输入！
                return;
            }
            this.disabledButton();//登录按钮变灰
            var mobile = _state.mobile;
            modifyPwdByCellphone(mobile, newPassword, confirmPassword, this);
        },
        //取消
        handleCancel: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            //1.显示 认证主组件内容
            _state.hideAuthDivStyle = '';
            //2.隐藏 忘记密码--设置新密码组件
            _state.showForgetPasswordForSetNewPassword = false;
            //3.更新Auth组件状态
            this.props.setAuthState();
        },
        //跳转到下一场景
        forward: function(){
            //1.显示 认证主组件内容
            _state.hideAuthDivStyle = '';
            //2.隐藏 忘记密码--设置新密码组件
            _state.showForgetPasswordForSetNewPassword = false;
            //3.更新Auth组件状态
            this.props.setAuthState();
        },
        render: function() {
            return (
                <div className="forms clearfix">
                    <div className="form-item show">
                        <div className="input-control resetPadding">
                            <input type="password" value={ this.state.newPassword } placeholder={ _state.message.alertInputNewPassword } onChange={ this.handleNewPasswordChange } />
                        </div>
                        <div className="input-control resetPadding">
                            <input type="password" value={ this.state.confirmPassword } placeholder={ _state.message.alertInputConfirmPassword } onChange={ this.handleConfirmPasswordChange } />
                            <input type="button" value={ _state.message.submitBtn } className={ 'submit ' + this.state.loginBtnSty } disabled={ this.state.loginDisabled }
                                   onClick={ this.handleSubmit.bind(this, 'click') } onTouchStart={ this.handleSubmit.bind(this, 'touch') } />
                            <input type="button" className="cancel" value={ _state.message.cancelBtn } onClick={ this.handleCancel.bind(this, 'click') } onTouchStart={ this.handleCancel.bind(this, 'touch') } />
                        </div>
                    </div>
                </div>
            );
        }
    });

    /**
     * 中英文组件
     */
    var MessageComponent = React.createClass({
        option: {
            language: 'cn'
        },
        getInitialState: function() {
            return this.option;
        },
        //设置语言--中文
        setLanguageCn : function(eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            this.option.language = 'cn';
            this.setState(this.option);
            _state.language = 'cn';
            _state.message = message_cn;
            this.props.setAuthState(_state);
        },
        //设置语言--英文
        setLanguageEn : function(eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            this.option.language = 'en';
            this.setState(this.option);
            _state.language = 'en';
            _state.message = message_en;
            this.props.setAuthState(_state);
        },
        changeLayoutShow : function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            _state.layoutHelpShow = true;  //显示弹出层
            //根据language来显示对应的使用说明信息
            if (_state.language === 'cn'){
                $('.cnInfo').addClass('show');
            } else if (this.option.language === 'en'){
                $('.enInfo').addClass('show');
            }
            $('.helpContent').addClass('show');

            this.setState(_state);
        },
        changeLayoutHide : function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            _state.layoutHelpShow = false;  //关闭弹出层
            //取消所有show样式
            $('.enInfo').removeClass('show');
            $('.cnInfo').removeClass('show');
            $('.helpContent').removeClass('show');
            this.setState(_state);
        },
        render: function() {
            return (
                <div className="language">
                    <div className={ this.props.display=='voice' ? 'show' : 'hide'}>
                        <div className="helpMessage" onClick={ this.changeLayoutShow.bind(this, 'click') } onTouchStart={ this.changeLayoutShow.bind(this, 'touch') } >{ _state.message.voiceHelpTitle }</div>
                        <div className="helpContent">
                            <div className="contentPosition">
                                <div className="contentBody">
                                    <div className="top">
                                        <div className="closeHelp" onClick={ this.changeLayoutHide.bind(this, 'click') } onTouchStart={ this.changeLayoutHide.bind(this, 'touch') } >x</div>
                                    </div>
                                    <div className="middle">
                                        <div className="cnInfo">
                                            操作说明<br/>
                                            1.选择国家编码；<br/>
                                            2.输入手机号；<br/>
                                            3.点击拨号，进行语音认证，听到中英文语音播报后完成认证；<br/>
                                            温馨提示<br/>
                                            1.苹果等自动弹Portal的手机需在语音播报后再次连接SSID直接上网；<br/>
                                            2.若您无法达到拨号界面，请直接致电+86-0571-87939222。
                                        </div>
                                        <div className="enInfo">
                                            Instruction<br/>
                                            1.Choose the country code;<br/>
                                            2.Please enter the phone number;<br/>
                                            3.Click on the dial,with voice certification, after hearing the voice in both English and Chinese to complete certification.<br/>
                                            Note<br/>
                                            1.Apple mobile phone should connect SSID again after Voice and surf the internet;<br/>
                                            2.If you unable to get dial interface,please call +86-0571-87939222 directly.
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className={ this.state.language === 'en' ? 'en checked' : 'en' } onClick={ this.setLanguageEn.bind(this, 'click') } onTouchStart={ this.setLanguageEn.bind(this, 'touch') } >EN</div>
                    <div className={ this.state.language === 'cn' ? 'cn checked' : 'cn' } onClick={ this.setLanguageCn.bind(this, 'click') } onTouchStart={ this.setLanguageCn.bind(this, 'touch') } >CN</div>
                </div>
            );
        }
    });

    /**
     * 欢迎组件，认证已放行时显示
     */
    var WelcomeComponent = React.createClass({
        option: {
            second: 3//倒计时
        },
        getInitialState: function() {
            return this.option;
        },
        componentDidMount: function(){
            if(_DEV_ID === '{@devId@}'){
                return;
            }
            var _this = this;
            var leftTime = _this.state.second;//剩余时间
            var timer = setInterval(function(){
                if(leftTime <= 0){
                    clearInterval(timer);
                    //1.当设备为胖AP时，调用设备总线接口进行放行
                    if(_TOKEN != '{@token@}' && _TOKEN != ''){
                        fatAPAuth(_TOKEN, _this);
                    }
                    //2.当设备不为胖AP时，直接跳转至下一页
                    else{
                        redirect();//页面跳转
                    }
                }else{
                    _this.setState({second: leftTime});
                }
                leftTime --;
            }, 1000);
        },
        render: function() {
            return (
                <div className="welcome">{ _state.message.welcomeComponentPrefix } <span className="time">{ this.state.second }</span> { _state.message.welcomeComponentSuffix }</div>
            );
        }
    });

    /**
     * 短信回发组件，内嵌于手机号认证组件
     */
    var MessageSendComponent = React.createClass({
        render: function() {
            return (
                <div className="optArea">
                    <div className="operLink"><a href="tel:10690498229982">{ _state.message.getAuthCodeFailedClick }</a></div>
                    <div className="infoMess">{ _state.message.getAuthCodeFailedSendMessage }</div>
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
     * 发送短信验证码
     */
    function sendSmsCode(mobile, _this){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option = {
            globalKey: _GLOBAL_KEY,//全局key
            globalValue: _GLOBAL_VALUE,//全局value
            version: authVersion,//版本
            phoneNumber: mobile,//手机号
            userMac: _USER_MAC,//用户终端MAC
            devId: _DEV_ID, //设备ID
            platform: _PLATFORM, //省分平台-前缀
            customerId: _CUSTOMER_ID //客户id
        };
        $.ajax({
            url: smsUrl,
            type: 'POST',
            dataType: 'JSON',
            header:{
                'cache-control': 'no-cache'
            },
            data:option,
            //async:false,//true 异步(默认)、false 同步
            success:function(data, textStatus, jqXHR){
                if(data.result === 'OK'){//接口执行成功
                    _this.showLoginBtn();//显示登陆按钮
                }else{
                    var message = data.message;
                    formatSMSError(message);
                    _this.showLoginBtn();//显示登陆按钮
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert('Sorry,sendSmsCode failure.');
                _this.showLoginBtn();//显示登陆按钮
            },
            complete: function(XHR, textStatus){

            }
        });
    }

    /**
     * 手机认证接口
     * @param type 1: 手机号+验证码; 2: 手机号认证; 3: IVR语音认证; 4: 免认证
     */
    function phoneAuth(mobile, authCode, type, _this){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        if(_DEV_ID == null || _DEV_ID == undefined || _DEV_ID == ''){
            formatAuthError("params_error[1001]");
            _this.enableButton();//登录按钮恢复
            return;
        }
        var option ={
            globalKey: _GLOBAL_KEY,//全局key
            globalValue: _GLOBAL_VALUE,//全局value
            version: authVersion,//版本
            plateformName: 'ToE',//平台名称
            type: type,//类型
            traceType: 'phone',//phone:手机号，username：用户名，passport：护照号，identity：身份证号
            traceValue: '',//当traceType为非phone时，必填，填写对应值
            phoneNumber: mobile,//手机号
            userMac: _USER_MAC,//用户终端MAC
            devId: _DEV_ID,//设备ID
            smsCode: authCode,//验证码,type=1时必填
            userIp: _USER_IP,//用户IP
            apMac: _DEV_MAC,//设备MAC
            ssId: _SSID,//SSID
            acName: _NAS_NAME,//nas设备名称，NAS认证必填
            platform: _PLATFORM, //省分平台-前缀
            customerId: _CUSTOMER_ID,//客户id
            cascadeLabel: _CASCADE_LABEL//客户层级
        };
        $.ajax({
            url: phoneAuthUrl,
            type: 'POST',
            dataType: 'JSON',
            header:{
                'cache-control': 'no-cache'
            },
            data:option,
            //async:false,//true 异步(默认)、false 同步
            success:function(data, textStatus, jqXHR){
                if(data.result === 'OK'){//接口执行成功
                    if(_USER_PHONE === ''){//手机号为空时，更新手机号信息
                        _USER_PHONE = mobile;
                    }
                    //当为胖AP时，调用设备总线接口放行
                    var token = data.token;
                    if(token != null && token != undefined && $.trim(token) != ''){
                        fatAPAuth(token, _this);
                        return;
                    }
                    redirect();//页面跳转
                }else{
                    formatAuthError(data.message);
                    _this.enableButton();//登录按钮恢复
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert('Sorry,authentication failure.');
                _this.enableButton();//登录按钮恢复
            },
            complete: function(XHR, textStatus){

            }
        });
    }

    /**
     * 静态用户登录
     * @param userName 静态用户名
     * @param password 密码
     * @param _this 组件对象
     */
    function userLogin(userName, password, _this){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option ={
            globalKey: _GLOBAL_KEY,//全局key
            globalValue: _GLOBAL_VALUE,//全局value
            version: authVersion,//版本
            plateformName: 'ToE',//平台名称
            traceType: 'phone',//phone:手机号，username：用户名，passport：护照号，identity：身份证号
            traceValue: '',//当traceType为非phone时，必填，填写对应值
            userMac: _USER_MAC,//用户终端MAC
            devId: _DEV_ID,//设备ID
            userIp: _USER_IP,//用户IP
            apMac: _DEV_MAC,//设备MAC
            ssId: _SSID,//SSID
            acName: _NAS_NAME,//nas设备名称，NAS认证必填
            platform: _PLATFORM,//省分平台-前缀

            customerId: _CUSTOMER_ID,//客户id
            cascadeLabel: _CASCADE_LABEL,//客户层级
            userName: userName,//静态用户名
            password: password//密码
        };
        $.ajax({
            url: userLoginUrl,
            type: 'POST',
            dataType: 'JSON',
            header:{
                'cache-control': 'no-cache'
            },
            data:option,
            //async:false,//true 异步(默认)、false 同步
            success:function(data, textStatus, jqXHR){
                if(data.result === 'OK'){//接口执行成功
                    var mobile = data.phoneNumber;//手机号
                    if(_USER_PHONE === ''){//手机号为空时，更新手机号信息
                        _USER_PHONE = mobile;
                    }
                    //当为胖AP时，调用设备总线接口放行
                    var token = data.token;
                    if(token != null && token != undefined && $.trim(token) != ''){
                        fatAPAuth(token, _this);
                        return;
                    }
                    redirect();//页面跳转
                }else{
                    formatAuthError(data.message);
                    _this.enableButton();//登录按钮恢复
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert('Sorry,authentication failure.');
                _this.enableButton();//登录按钮恢复
            },
            complete: function(XHR, textStatus){

            }
        });
    }

    /**
     * 接口一 ： IVR语音认证-保存参数及日志
     * @param mobile 手机号
     * @param countryCode 国家编号
     * @param countryName 国家名称
     * @param resetTimeInterval 重置60秒倒计时
     * @param _this
     */
    function ivrCall(mobile, countryCode, countryName, resetTimeInterval, _this){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option ={
            globalKey: _GLOBAL_KEY,//全局key
            globalValue: _GLOBAL_VALUE,//全局value
            version: authVersion,//版本
            plateformName: 'ToE',//平台名称
            traceType: 'username',//phone:手机号，username：用户名，passport：护照号，identity：身份证号
            traceValue: mobile,//当traceType为非phone时，必填，填写对应值
            phoneNumber: mobile,//手机号
            userMac: _USER_MAC,//用户终端MAC
            devId: _DEV_ID,//设备ID
            userIp: _USER_IP,//用户IP
            apMac: _DEV_MAC,//设备MAC
            ssId: _SSID,//SSID
            acName: _NAS_NAME,//nas设备名称，NAS认证必填

            customerId: _CUSTOMER_ID,//客户id
            cascadeLabel: _CASCADE_LABEL,//客户层级
            countryCode: countryCode,//国家编号
            countryName: countryName//国家名称
        };
        $.ajax({
            url: ivrCallUrl,
            type: 'POST',
            dataType: 'JSON',
            header:{
                'cache-control': 'no-cache'
            },
            data:option,
            //async:false,//true 异步(默认)、false 同步
            success:function(data, textStatus, jqXHR){
                if(data.result === 'OK'){//接口执行成功
                    if(_USER_PHONE === ''){//手机号为空时，更新手机号信息
                        _USER_PHONE = mobile;
                    }
                    ivrPoll(mobile, resetTimeInterval, _this);//接口三 ： IVR语音认证-放行轮询接口
                    var ivrPhone = data.ivrPhone;//IRV语音号码

                    // 语音认证
                    var phonePrefix;
                    if (countryCode==86){//国内号码
                        phonePrefix = '';
                    }else{
                        phonePrefix = '+86';
                    }
                    window.location.href = 'tel:' + phonePrefix + ivrPhone;
                }else{
                    formatAuthError(data.message);
                    //_this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
                    resetTimeInterval();//重置60秒倒计时
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert('Sorry,authentication failure.');
                //_this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
                resetTimeInterval();//重置60秒倒计时
            },
            complete: function(XHR, textStatus){

            }
        });
    }

    var ivrPollTimer;//接口三 ： IVR语音认证-放行轮询接口-定时器

    /**
     * 接口三 ： IVR语音认证-放行轮询接口
     * @param mobile 手机号
     */
    function ivrPoll(mobile, resetTimeInterval, _this){
        //如果重复调用，先清除上一次timer
        if(ivrPollTimer != null){
            clearInterval(ivrPollTimer);
        }
        //每隔5秒调用 IVR语音认证-放行轮询接口
        ivrPollTimer = setInterval(function(){
            sendIvrPoll(mobile, _this);
        }, 5000);
        //发送 接口三 ： IVR语音认证-放行轮询接口 请求
        function sendIvrPoll(mobile, _this){
            var option ={
                phoneNumber: mobile//手机号
            };
            $.ajax({
                url: ivrPollUrl,
                type: 'POST',
                dataType: 'JSON',
                header:{
                    'cache-control': 'no-cache'
                },
                data:option,
                //async:false,//true 异步(默认)、false 同步
                success:function(data, textStatus, jqXHR){
                    if(data.result === 'FAIL'){//接口执行失败
                        var message = data.message;
                        if(message === null || message === undefined || message === 'phoneNumberNull'){//获取页面参数，如果手机号[userPhone]为空，终止定时器
                            clearInterval(ivrPollTimer);//终止定时器
                            resetTimeInterval();//重置登录按钮timer
                        }else if(message === 'redisKeyNotExist'){//从redis获取redisValue,如果不存在，终止定时器
                            clearInterval(ivrPollTimer);//终止定时器
                            resetTimeInterval();//重置登录按钮timer
                        }else if(message === 'IVRNoResponse'){//IVR网关尚未推送数据，隔5秒后继续执行

                        }else{//调用接入放行接口失败或其它接口异常
                            alert(message);
                            clearInterval(ivrPollTimer);//终止定时器
                            resetTimeInterval();//重置登录按钮timer
                        }
                    }else{//接口执行成功
                        //当为胖AP时，调用设备总线接口放行
                        var token = data.token;
                        if(token != null && token != undefined && $.trim(token) != ''){
                            fatAPAuth(token, _this);
                            return;
                        }
                        redirect();//页面跳转
                    }
                },
                error: function(XHR, textStatus, errorThrown) {
                    //clearInterval(ivrPollTimer);//终止定时器
                    //resetTimeInterval();//重置登录按钮timer
                },
                complete: function(XHR, textStatus){

                }
            });
        }
    }

    /**
     * 胖AP认证放行
     * @param token token参数
     //*/
    function fatAPAuth(token, _this){
        var url = 'http://' + _GW_ADDRESS + ':' + _GW_PORT + fatApAuthUrl;//认证地址
        var option ={
            token: token,//token
            url: _URL //用户浏览器输入的被拦截前的url原始地址
        };
        $.ajax({
            url: url,
            dataType: 'jsonp',
            jsonp: 'callback',
            header:{
                'cache-control': 'no-cache'
            },
            data:option,
            timeout: 5000,
            //async:false,//true 异步(默认)、false 同步
            success:function(data, textStatus, jqXHR){
                try{
                    redirect();//页面跳转
                }catch(e){
                    _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                //因该接口重定向，导致无法判断接口执行是否成功
                try{
                    redirect();//页面跳转
                }catch(e){
                    _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
                }
            },
            complete: function(XHR, textStatus){
                try{
                    redirect();//页面跳转
                }catch(e){
                    _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
                }
            }
        });
    }

    /**
     * 静态用户名认证--修改密码
     * @param userName 静态用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param confirmPassword 确认密码
     * @param _this 组件对象
     */
    function modifyPwd(userName, oldPassword, newPassword, confirmPassword, _this){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option ={
            userName: userName,//静态用户名
            oldPassword: oldPassword,//旧密码
            newPassword: newPassword,//新密码
            confirmPassword: confirmPassword,//确认密码
            customerId: _CUSTOMER_ID //客户id
        };
        $.ajax({
            url: modifyPwdUrl,
            type: 'POST',
            dataType: 'JSON',
            header:{
                'cache-control': 'no-cache'
            },
            data: option,
            //async:false,//true 异步(默认)、false 同步
            success:function(data, textStatus, jqXHR){
                if(data.result === 'OK'){//接口执行成功
                    alert(_state.message.alertPasswordSuccess);//密码修改成功，请使用新密码重新登陆！
                    _this.clearInput();//清空上次输入的内容
                    _this.enableButton();//登录按钮恢复
                    _this.forward();//返回Auth组件
                }else{
                    alert(data.message);
                    _this.enableButton();//登录按钮恢复
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert(_state.message.alertPasswordFail);//密码修改失败！
                _this.enableButton();//登录按钮恢复
            },
            complete: function(XHR, textStatus){

            }
        });
    }

    /**
     * 静态用户名认证--忘记密码--发送验证码
     * @param mobile 手机号
     * @param _this 组件对象
     */
    function sendAuthcode(mobile, _this){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option ={
            cellphone: mobile,//手机号
            customerId: _CUSTOMER_ID //客户id
        };
        $.ajax({
            url: sendAuthcodeUrl ,
            type: 'POST',
            dataType: 'JSON',
            header:{
                'cache-control': 'no-cache'
            },
            data: option,
            //async:false,//true 异步(默认)、false 同步
            success:function(data, textStatus, jqXHR){
                if(data.result === 'OK'){//接口执行成功

                }else{
                    alert(data.message);
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert(_state.message.alertSendAuthCodeFail);//验证码发送失败！
            },
            complete: function(XHR, textStatus){
                _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
            }
        });
    }

    /**
     * 静态用户名认证--忘记密码--校验验证码
     * @param mobile 手机号
     * @param authCode 验证码
     * @param _this 组件对象
     */
    function checkAuthcode(mobile, authCode, _this){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option ={
            cellphone: mobile,//手机号
            authCode: authCode,//验证码
            customerId: _CUSTOMER_ID //客户id
        };
        $.ajax({
            url: checkAuthcodeUrl  ,
            type: 'POST',
            dataType: 'JSON',
            header:{
                'cache-control': 'no-cache'
            },
            data: option,
            //async:false,//true 异步(默认)、false 同步
            success:function(data, textStatus, jqXHR){
                if(data.result === 'OK'){//接口执行成功
                    _state.mobile = mobile;
                    _this.enableButton();//登录按钮恢复
                    _this.forward();
                }else{
                    alert(data.message);
                    _this.enableButton();//登录按钮恢复
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert(_state.message.alertAuthCodeCheckFail);//验证码校验失败！
                _this.enableButton();//登录按钮恢复
            },
            complete: function(XHR, textStatus){

            }
        });
    }

    /**
     * 静态用户名认证--忘记密码--修改密码
     * @param mobile 手机号
     * @param newPassword 新密码
     * @param confirmPassword 确认密码
     * @param _this 组件对象
     */
    function modifyPwdByCellphone(mobile, newPassword, confirmPassword, _this){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option ={
            cellphone: mobile,//手机号
            newPassword: newPassword,//新密码
            confirmPassword: confirmPassword,//确认密码
            customerId: _CUSTOMER_ID //客户id
        };
        $.ajax({
            url: modifyPwdByCellphoneUrl   ,
            type: 'POST',
            dataType: 'JSON',
            header:{
                'cache-control': 'no-cache'
            },
            data: option,
            //async:false,//true 异步(默认)、false 同步
            success:function(data, textStatus, jqXHR){
                if(data.result === 'OK'){//接口执行成功
                    alert(_state.message.alertPasswordSuccess);//密码修改成功，请使用新密码重新登陆！
                    _this.enableButton();//登录按钮恢复
                    _this.forward();
                }else{
                    alert(data.message);
                    _this.enableButton();//登录按钮恢复
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert(_state.message.alertPasswordFail);//密码修改失败！
                _this.enableButton();//登录按钮恢复
            },
            complete: function(XHR, textStatus){

            }
        });
    }

    /**
     * 通过手机号校验是否在黑名单里
     * @param cellphone 手机号
     * @param func 接口执行成功后的回调方法
     */
    function checkBlackByCellphone(cellphone, func){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option ={
            cellphone: cellphone,//手机号
            customerId: _CUSTOMER_ID//客户id
        };
        $.ajax({
            url: checkBlackByCellphoneUrl,
            type: 'POST',
            dataType: 'JSON',
            header: {
                'cache-control': 'no-cache'
            },
            data: option,
            success: function (data) {
                if(data.result == 'FAIL'){
                    alert(data.message);
                    return;
                }
                if(data.isBlacklist == 'yes'){
                    alert(_state.message.alertBlacklistCheckFail);//您无法使用该网络，请与管理员联系。
                }else{
                    func();
                }
            },
            error: function (xhr, textStatus) {
                alert(textStatus || '系统异常，请稍后再试...');
            }
        });
    }

    /**
     * 获取二维码图片
     * @param _this this
     * @author 许小满
     * @date 2017-08-07 18:53:12
     */
    function getQRCode(_this){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option ={
            logId: _GLOBAL_KEY,//日志id
            merchantId: _CUSTOMER_ID,//商户id

            deviceId: _DEV_ID,//设备id
            nasName: _NAS_NAME,//NAS设备名称
            devMac: _DEV_MAC,//设备MAC
            ssid: _SSID,//SSID

            userMac: _USER_MAC,//用户MAC
            userIp: _USER_IP,//用户ip

            platform: _PLATFORM,//省分平台-前缀
        };
        var params = 'params=' + encodeURIComponent(JSON.stringify(option));
        $.ajax({
            url: getQRCodeUrl,
            type: 'GET',
            dataType: 'JSON',
            header: {
                'cache-control': 'no-cache'
            },
            data: params,
            success: function (data) {
                var code = data.code;
                if(code !== '0'){
                    alert(data.msg);
                    return;
                }
                var qrCodeData = data.data;
                _this.option.redisKey = qrCodeData.redisKey;
                _this.option.qrCodeImgURL = qrCodeData.qrCode;
                _this.option.qrCodeStatus = 'loaded';//将状态设置为：已加载
                _this.setState(_this.option);
                //二维码心跳接口触发
                _this.startQRCodeHeartBeat();
            },
            error: function (xhr, textStatus) {
                alert('获取二维码失败[' + textStatus + ']!');
                _this.enableButton();
            }
        });
    }

    /**
     * 二维码认证—心跳轮询接口
     * @param redisKey redis缓存key
     * @param timer this
     * @param _this this
     * @author 许小满
     * @date 2017-08-07 18:53:12
     */
    function qrCodeHeartBeat(redisKey, timer, _this){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option ={
            redisKey: redisKey //redis缓存key
        };
        var params = 'params=' + encodeURIComponent(JSON.stringify(option));
        $.ajax({
            url: qrCodeHeartBeatUrl,
            type: 'GET',
            dataType: 'JSON',
            header: {'cache-control': 'no-cache'},
            data: params,
            success: function (data) {
                var code = data.code;
                if(code !== '0'){//接口执行异常
                    formatAuthError(data.msg);//提示错误信息
                    return;
                }
                var returnData = data.data;
                var status = returnData.status;//状态：0代表redis缓存已失效、1代表认证中、2代表认证成功
                if(status === 0){//当redis缓存已失效时,清除定时器
                    if(timer){
                        clearInterval(timer);
                    }
                    _this.option.qrCodeStatus = 'invalid';//将状态设置为：已失效
                    _this.setState(_this.option);
                } else if(status === 1) {//当认证中时,继续心跳轮询

                } else if(status === 2) {//当认证成功时,跳转至下一页
                    //清除定时器
                    if(timer){
                        clearInterval(timer);
                    }
                    var token = returnData.token;
                    //当token为空时,直接跳至下一页
                    if(!token){
                        redirect();
                    }
                    //当token不为空时,调用设备总线接口进行放行
                    else {
                        fatAPAuth(token, _this);
                    }
                } else if(status === 3) {//当认证失败时,清除定时器
                    formatAuthError(data.msg);//提示错误信息
                    if(timer){
                        clearInterval(timer);
                    }
                    _this.option.qrCodeStatus = 'invalid';//将状态设置为：已失效
                    _this.setState(_this.option);
                } else {
                    alert('status['+ status +']超出了范围[0|1|2|3]！');
                }
            },
            error: function (xhr, textStatus) {
                alert(textStatus || '系统异常，请稍后再试...');
            }
        });
    }

    /**
     * 显示或隐藏二维码认证
     * @author 许小满
     * @date 2017-08-08 12:31:11
     */
    var _hasQRCode;//记录第一次是否需要显示二维码,该值在页面加载完成后赋值一次,而且不允许修改
    function showOrHideQRCode(){
        if(_DEV_ID == '{@devId@}'){//测试环境不显示二维码
            _hasQRCode = false;
            _state.hasQRCode = false;
            return;
        }
        var isIpad = /iPad/.test(navigator.userAgent);//判断浏览器是否为ipad
        var isMobileTerminal = /Mobile/.test(navigator.userAgent);//判断浏览器是否为移动端
        var isPC = (isIpad || !isMobileTerminal);//判断是否为PC端或ipad
        var isNewUser = _LOGIN_TYPE === 'unauth';//判断是否为新用户
        var isPortalVersion5 = _PORTAL_VERSION === '5';//判断是否为5.x
        if(isPC && isNewUser && isPortalVersion5){
            _hasQRCode = true;
            _state.hasQRCode = true;
        } else {
            _hasQRCode = false;
            _state.hasQRCode = false;
        }
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

    /**
     * 设置state默认值
     * @auth 许小满
     * @date 2016-11-18 15:36:15
     */
    function setDefaultState(){
        for (var item in stateDefault) {
            if (!_state.hasOwnProperty(item)) {
                _state[item] = stateDefault[item];
            }
        }
    }

    /**
     * 格式化 发送验证码 错误消息
     * @param message 接口错误消息
     */
    function formatSMSError(message){
        if(message == null || message == undefined){
            alert(_state.message.alertGetAuthCodeFail);//抱歉，获取验证码失败.
        }else if(message == 'sms_code_sended'){
            alert(_state.message.alertCheckAuthCodeSended);//抱歉，验证码发送过于频繁，您可使用获取的验证码认证上网.
        }else if(message == '接口无返回值！' || message == '接口返回值不允许为空！'){
            alert(_state.message.alertAuthCodeNoResponse);//抱歉，获取验证码失败（错误信息：接口无响应）
        }else{
            if(message.length >= 30){
                message = message.substring(0,30) + '......';
            }
            alert(_state.message.alertSorryAuthCodeCheckFail + message +'）');//抱歉，获取验证码失败（错误信息：
        }
    }

    /**
     * 格式化 认证 错误消息
     * @param message 接口错误消息
     */
    function formatAuthError(message){
        if(message == null || message == undefined){
            alert(_state.message.alertAuthFail);//抱歉，认证失败.
        }else if(message == 'sms_code_error'){
            alert(_state.message.alertAuthCodeError);//抱歉，验证码校验失败，请重新输入.
        }else if(message == 'sms_code_expired'){
            alert(_state.message.alertAuthCodeExpired);//抱歉，验证码已失效，请重新获取.
        }else if(message == '接口无返回值！' || message == '接口返回值不允许为空！'){
            alert(_state.message.alertAuthNoResponse);//抱歉，认证失败（错误信息：接口无响应）
        }else if(message == 'duplicate challenge request'){
            alert(message.alertDuplicateChallengeRequest);//请求处理中，请勿重复认证。
        }else{
            if(message.length >= 30){
                message = message.substring(0,30) + '......';
            }
            alert(_state.message.alertSorryAuthCheckFail+ message +'）');//抱歉，认证失败（错误信息：
        }
    }

    /**
     * 制作页面时返回React对象
     */
    function render() {
        return ReactDOM.render(<AuthComponent />, document.getElementById(divId));
    }

    /**
     * 暴露render方法
     */
    return {
        render: render
    }
};
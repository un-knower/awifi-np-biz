var _Entity_ = function (state, divId) {
    var _state = state || {
            style: 'st1',//样式
            active: 'mobile',//选中：mobile 手机号、userPass 静态用户名
            display: 'mobile',//显示：free 免费上网、mobile 手机号、userPass 静态用户名
            tabNum: 2,//tab数量
            //免费上网
            hasFree: true,//是否包含功能点：免费上网
            freeButtonDsp: '点击免费上网',//按钮显示值
            //手机号
            hasMobile: true,//是否包含功能点：手机号认证
            mobileTitle: '手机号认证',//tab标签显示值
            //用户名
            hasUserPass: true,//是否包含功能点：静态用户名认证
            userPassTitle: '静态用户名认证',//tab标签显示值
            /** 修改密码、忘记密码 新增参数 */
            userName: '',//用户名
            mobile: '',//手机号
            hideAuthDivStyle: '',//控制主组件是否隐藏显示
            hasModefiyPassword: true,//是否包含功能点：修改密码
            showModifyPasswordForSetNewPassword: false,//是否显示：修改密码--设置新密码组件
            hasForgetPassword: true,//是否包含功能点：忘记密码
            showForgetPasswordForCheckMobile: false,//是否显示：忘记密码--验证手机号组件
            showForgetPasswordForSetNewPassword: false,//是否显示：忘记密码--设置新密码组件
            showWelcome: false //是否显示欢迎组件
        };
    //当用户为老用户时，默认显示“免费上网”
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

    var fatApAuthUrl = '/smartwifi/auth';//胖ap认证放行接口地址

    //内部接口
    var smsUrl = '/auth/sendSmsCode';//发送短信验证码接口地址
    var phoneAuthUrl = '/auth/phoneAuth';//手机认证接口地址
    var userLoginUrl = '/staticuser/phoneAuth';//静态用户登录接口地址
    var modifyPwdUrl = '/staticuser/modifypwd';//静态用户名认证--修改密码接口地址
    var sendAuthcodeUrl = '/authcode/sendauthcode';//静态用户名认证--忘记密码--发送验证码接口地址
    var checkAuthcodeUrl = '/authcode/iscorrect';//静态用户名认证--忘记密码--校验验证码接口地址
    var modifyPwdByCellphoneUrl = '/staticuser/modifypwdbycellphone';//静态用户名认证--忘记密码--修改密码接口地址
    var authVersion = 'v1.1';//接入系统版本号

    //本地环境
    //var netconfigUrl = 'http://127.0.0.1:91/netConfig/check';//在线时长控制地址
    //var checkBlackByCellphoneUrl = 'http://127.0.0.1:91/blacklist/phoneisexistinblacklist';//通过手机号校验是否在黑名单里地址
    //var checkBlackByUserNameUrl = 'http://127.0.0.1:91/blacklist/isexistinblacklist';//通过用户名校验是否在黑名单里地址
    //var authlogUrl =  'http://127.0.0.1:91/authlog/add';//保存用户认证记录地址
    //生产环境
    var netconfigUrl = 'http://2etrade.51awifi.com/netConfig/check';//在线时长控制地址
    var checkBlackByCellphoneUrl = 'http://2etrade.51awifi.com/blacklist/phoneisexistinblacklist';//通过手机号校验是否在黑名单里地址
    var checkBlackByUserNameUrl = 'http://2etrade.51awifi.com/blacklist/isexistinblacklist';//通过用户名校验是否在黑名单里地址
    var authlogUrl =  'http://2etrade.51awifi.com/authlog/add';//保存用户认证记录地址


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
            if(tabContent === _state.mobileTitle){
                _state.active = 'mobile';
                _state.display = 'mobile';
            }
            //选中 静态用户名认证
            else if(tabContent === _state.userPassTitle){
                _state.active = 'userPass';
                _state.display = 'userPass';
            }
            this.setState(_state);
        },
        //更新Auth组件状态
        setAuthState: function () {
            this.setState(_state);
        },
        render: function() {
            return (
                <div className={"_Entity_ " + this.state.style}>
                    <div className={ (this.state.tabNum > 1 ? 'model clearfix ' : '') + (this.state.display=='free' ? 'hide' : '') + ' ' + this.state.hideAuthDivStyle}>
                        { this.state.hasMobile && this.state.tabNum > 1 ? <MobileAuthTabComponent active={ this.state.active } click={ this.handleTabClick } title={ this.state.mobileTitle }/> : '' }
                        { this.state.hasUserPass && this.state.tabNum > 1 ? <UserPassAuthTabComponent active={ this.state.active } click={ this.handleTabClick } title={ this.state.userPassTitle }/> : '' }
                    </div>
                    <div className={ 'forms clearfix ' + this.state.hideAuthDivStyle }>
                        { this.state.hasFree ? <FreeAuthComponent display={ this.state.display } buttonDsp={ this.state.freeButtonDsp } /> : '' }
                        { this.state.hasMobile ? <MobileAuthComponent display={ this.state.display } /> : '' }
                        { this.state.hasUserPass ? <UserPassAuthComponent display={ this.state.display }
                                                                          setAuthState={ this.setAuthState }
                                                                          userName={ this.state.userName }
                                                                          hasModefiyPassword={ this.state.hasModefiyPassword }
                                                                          hasForgetPassword={ this.state.hasForgetPassword }
                        />
                            : '' }
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
        render: function() {
            return (
                <div className={ this.props.active=='mobile' ? 'active' : ''}
                     onClick={ this.props.click }
                ><div className="top_title">{ this.props.title }</div>
                </div>
            );
        }
    });

    /**
     *  静态用户名认证Tab组件
     */
    var UserPassAuthTabComponent = React.createClass({
        render: function() {
            return (
                <div className={ this.props.active=='userPass' ? 'active' : ''}
                     onClick={ this.props.click }
                ><div className="top_title">{ this.props.title }</div>
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
            handleLogin: function(){
                var _this = this;
                //1.校验时长
                checkNetconfig('freeauth', _checkBlackByCellphone);
                //2.校验黑名单
                function _checkBlackByCellphone(){
                    //3.免费上网认证
                    checkBlackByCellphone(_USER_PHONE, _this.freeAuth);
                }
            },
            //免费上网认证
            freeAuth: function(){
                if(_DISCLAIMER === 'refuse'){// 判断免责声明
                    alert('请先同意免责声明');
                }
                this.setState({loginDisabled: 'disabled', loginBtnSty: 'disaButton'});//登录按钮变灰
                var mobile = _USER_PHONE;
                phoneAuth(mobile, '', '4',this);
            },
            render: function() {
                return (
                    <div className= { 'form-item '+ (this.props.display=='free' ? 'show' : 'hide') }>
                        <div className="input-control">
                            <input type="button" className={ 'free ' + this.state.loginBtnSty } disabled={ this.state.loginDisabled } onClick={ this.handleLogin } value={ this.props.buttonDsp }/>
                        </div>
                    </div>
                );
            }
        });

    /**
     *  手机号认证组件
     */
    var MobileAuthComponent = React.createClass({
        second: 60,// 倒计时
        option: {
            getAuthCodeBtnDsp: '获取验证码',//获取验证码 按钮显示值
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
        getAuthCode: function(){
            var _this = this;
            //1.校验在线时长
            checkNetconfig('mobile', _checkBlackByCellphone);
            //2.校验黑名单
            function _checkBlackByCellphone(){
                var mobile = _this.state.mobile;//手机号
                if(mobile === '' || !(/^(1[3|4|5|7|8][0-9][0-9]{8})?$/).test(mobile)){
                    alert('请输入正确的手机号码');
                    return;
                }
                //3.发送短信验证码
                checkBlackByCellphone(mobile, _this.sendAuthCode);
            }
        },
        //发送短信验证码，按钮同时进入倒计时
        sendAuthCode: function(){
            var _this = this;
            var mobile = this.state.mobile;//手机号
            var leftTime = _this.second;//剩余时间
            this.option.getAuthCodeBtnSty = 'disaButton';
            this.option.getAuthCodeBtnDisabled = 'disabled';
            this.option.getAuthCodeBtnDsp = '重新获取(' + leftTime + ')';
            _this.setState(this.option);
            leftTime --;
            var timer = setInterval(function(){
                if(leftTime <= 0){
                    clearInterval(timer);
                    _this.option.getAuthCodeBtnDisabled = '';
                    _this.option.getAuthCodeBtnSty = '';
                    _this.option.getAuthCodeBtnDsp = '获取验证码';
                    _this.setState(_this.option);
                    return;
                }
                _this.option.getAuthCodeBtnDsp = '重新获取(' + leftTime + ')';
                _this.setState(_this.option);
                leftTime --;
            }, 1000);
            sendSmsCode(mobile);//发送短信验证码
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
        //免费登录
        handleLogin: function(){
            if(_DISCLAIMER === 'refuse'){// 判断免责声明
                alert('请先同意免责声明');
            }
            var mobile = this.state.mobile;//手机号
            var authCode = this.state.authCode;//验证码
            if(mobile === '' || !(/^(1[3|4|5|7|8][0-9][0-9]{8})?$/).test(mobile)){
                alert('请输入正确的手机号码');
                return;
            }
            if(authCode === '' || !(/^[0-9]{6}$/).test(authCode)){
                alert('请输入正确的验证码');
                return;
            }
            this.setState({loginDisabled: 'disabled', loginBtnSty: 'disaButton'});//登录按钮变灰
            phoneAuth(mobile, authCode, '1',this);
        },
        render: function() {
            return (
                <div className={ 'form-item ' + (this.props.display=='mobile' ? 'show' : 'hide') }>
                    <div className="input-control">
                        <input type="text" onChange={ this.handleMobileChange } value={ this.state.mobile } placeholder="请输入手机号码"/>
                        <input type="button" disabled={ this.state.getAuthCodeBtnDisabled } className={ this.state.getAuthCodeBtnSty } onClick={ this.getAuthCode } value={ this.state.getAuthCodeBtnDsp }/>
                    </div>
                    <div className="input-control">
                        <input type="text" onChange={ this.handleAuthCodeChange } value={ this.state.authCode } placeholder="请输入验证码" />
                        <input type="button" value="免费登录" className={ this.state.loginBtnSty } disabled={ this.state.loginDisabled } onClick={ this.handleLogin } />
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
        //登录按钮点击事件
        handleLoginClick: function(){
            var _this = this;
            //1.校验在线时长
            checkNetconfig('username', _checkBlackByUserName);
            //2.校验黑名单
            function _checkBlackByUserName(){
                var userName = _this.state.userName;//静态用户名
                if(userName === ''){
                    alert('请输入用户名');
                    return;
                }
                checkBlackByUserName(userName, _this.userNameAuth);
            }
            //3.静态用户名认证
        },
        //静态用户名认证
        userNameAuth: function(){
            if(_DISCLAIMER === 'refuse'){// 判断免责声明
                alert('请先同意免责声明');
            }
            var userName = this.state.userName;//静态用户名
            var password = this.state.password;//密码
            if(password === ''){
                alert('请输入密码');
                return;
            }
            this.setState({loginDisabled: 'disabled', loginBtnSty: 'disaButton'});//登录按钮变灰
            userLogin(userName, password, this);
        },
        //修改密码
        handModifyPassword: function(){
            //1.隐藏认证主组件内容
            _state.hideAuthDivStyle = 'hide';
            //2.显示修改密码--设置新密码组件
            _state.showModifyPasswordForSetNewPassword = true;
            //3.更新Auth组件状态
            this.props.setAuthState();
        },
        //忘记密码
        handForgetPassword: function(){
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
                    <div className="input-control">
                        <input type="text" value={ this.state.userName } onChange={ this.handleUserNameChange } placeholder="请输入静态用户名"/>
                    </div>
                    <div className="input-control">
                        <input type="password" value={ this.state.password } onChange={ this.handlePasswordChange } placeholder="请输入密码"/>
                        <input type="button" onClick={ this.handleLoginClick } className={ this.state.loginBtnSty } disabled={ this.state.loginDisabled } value="免费登录"/>
                    </div>
                    <div className="optArea">
                        <span className={this.state.hasModefiyPassword ? '' : 'hide'} onClick={ this.handModifyPassword }>修改密码</span>
                        <span className={this.state.hasModefiyPassword && this.state.hasForgetPassword ? '' : 'hide'} >|</span>
                        <span className={this.state.hasForgetPassword ? '' : 'hide'} onClick={ this.handForgetPassword }>忘记密码</span>
                    </div>
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
        //提交
        handleSubmit: function(){
            //参数校验
            var userName = this.state.userName;//用户名
            if(userName === ''){
                alert('请输入用户名');
                return;
            }
            var oldPassword = this.state.oldPassword;//旧密码
            if(oldPassword === ''){
                alert('请输入旧密码');
                return;
            }
            var newPassword = this.state.newPassword;//新密码
            if(newPassword === ''){
                alert('请输入新密码');
                return;
            }
            if(!(/^[^\u4e00-\u9fa5]{4,20}$/).test(newPassword)){
                alert('新密码必须由4-20位不包含汉字的字符组成！');
                return;
            }
            if(oldPassword === newPassword){
                alert('旧密码与新密码不允许相同，请重新输入！');
                return;
            }
            var confirmPassword = this.state.confirmPassword;//确认密码
            if(confirmPassword === ''){
                alert('请输入确认密码');
                return;
            }
            if(!(/^[^\u4e00-\u9fa5]{4,20}$/).test(confirmPassword)){
                alert('确认密码必须由4-20位不包含汉字的字符组成！');
                return;
            }
            if(newPassword != confirmPassword){
                alert('新密码与确认密码不一致，请重新输入！');
                return;
            }
            this.setState({loginDisabled: 'disabled', loginBtnSty: 'disaButton'});//登录按钮变灰
            modifyPwd(userName, oldPassword, newPassword, confirmPassword, this);
        },
        //取消
        handleCancel: function(){
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
                        <div className="input-control">
                            <input type="text" value={ this.state.userName } placeholder="请输入用户名" onChange={ this.handleUserNameChange } />
                        </div>
                        <div className="input-control">
                            <input type="password" value={ this.state.oldPassword } placeholder="请输入旧密码" onChange={ this.handleOldPasswordChange } />
                        </div>
                        <div className="input-control">
                            <input type="password" value={ this.state.newPassword } placeholder="请输入新密码" onChange={ this.handleNewPasswordChange } />
                        </div>
                        <div className="input-control">
                            <input type="password" value={ this.state.confirmPassword } placeholder="请输入重复新密码" onChange={ this.handleConfirmPasswordChange } />
                            <input type="button" value="提交" onClick={ this.handleSubmit } className={ 'submit ' + this.state.loginBtnSty } disabled={ this.state.loginDisabled }/>
                            <input type="button" className="cancel" value="取消" onClick={ this.handleCancel }/>
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
            getAuthCodeBtnDsp: '获取验证码',//获取验证码 按钮显示值
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
        getAuthCode: function(){
            var _this = this;
            var mobile = this.state.mobile;//手机号
            if(mobile === '' || !(/^(1[3|4|5|7|8][0-9][0-9]{8})?$/).test(mobile)){
                alert('请输入正确的手机号码');
                return;
            }
            var leftTime = _this.second;//剩余时间
            this.option.getAuthCodeBtnSty = 'disaButton';
            this.option.getAuthCodeBtnDisabled = 'disabled';
            this.option.getAuthCodeBtnDsp = '重新获取(' + leftTime + ')';
            _this.setState(this.option);
            leftTime --;
            var timer = setInterval(function(){
                if(leftTime <= 0){
                    clearInterval(timer);
                    _this.option.getAuthCodeBtnDisabled = '';
                    _this.option.getAuthCodeBtnSty = '';
                    _this.option.getAuthCodeBtnDsp = '获取验证码';
                    _this.setState(_this.option);
                    return;
                }
                _this.option.getAuthCodeBtnDsp = '重新获取(' + leftTime + ')';
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
        //提交
        handleSubmit: function(){
            //参数校验
            var mobile = this.state.mobile;//手机号
            var authCode = this.state.authCode;//验证码
            if(mobile === '' || !(/^(1[3|4|5|7|8][0-9][0-9]{8})?$/).test(mobile)){
                alert('请输入正确的手机号码');
                return;
            }
            if(authCode === '' || !(/^[0-9]{6}$/).test(authCode)){
                alert('请输入正确的验证码');
                return;
            }
            this.setState({loginDisabled: 'disabled', loginBtnSty: 'disaButton'});//登录按钮变灰
            checkAuthcode(mobile, authCode, this);
        },
        //取消
        handleCancel: function(){
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
            return (
                <div className="forms clearfix">
                    <div className="form-item show">
                        <div className="input-control">
                            <input type="text" value={ this.state.mobile } placeholder="请输入手机号进行验证" onChange={ this.handleMobileChange } />
                            <input type="button" value={ this.state.getAuthCodeBtnDsp } onClick={ this.getAuthCode } disabled={ this.state.getAuthCodeBtnDisabled } className={ this.state.getAuthCodeBtnSty } />
                        </div>
                        <div className="input-control">
                            <input type="text" value={ this.state.authCode } placeholder="请输入验证码" onChange={ this.handleAuthCodeChange } />
                            <input type="button" value="提交" onClick={ this.handleSubmit } className={ 'submit ' + this.state.loginBtnSty } disabled={ this.state.loginDisabled } />
                            <input type="button" className="cancel" value="取消" onClick={ this.handleCancel } />
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
        //提交
        handleSubmit: function(){
            //参数校验
            var newPassword = this.state.newPassword;//新密码
            if(newPassword === ''){
                alert('请输入新密码');
                return;
            }
            if(!(/^[^\u4e00-\u9fa5]{4,20}$/).test(newPassword)){
                alert('新密码必须由4-20位不包含汉字的字符组成！');
                return;
            }
            var confirmPassword = this.state.confirmPassword;//确认密码
            if(confirmPassword === ''){
                alert('请输入确认密码');
                return;
            }
            if(!(/^[^\u4e00-\u9fa5]{4,20}$/).test(confirmPassword)){
                alert('确认密码必须由4-20位不包含汉字的字符组成！');
                return;
            }
            if(newPassword != confirmPassword){
                alert('新密码与确认密码不一致，请重新输入！');
                return;
            }
            this.setState({loginDisabled: 'disabled', loginBtnSty: 'disaButton'});//登录按钮变灰
            var mobile = _state.mobile;
            modifyPwdByCellphone(mobile, newPassword, confirmPassword, this);
        },
        //取消
        handleCancel: function(){
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
                        <div className="input-control">
                            <input type="password" value={ this.state.newPassword } placeholder="请输入新密码" onChange={ this.handleNewPasswordChange } />
                        </div>
                        <div className="input-control">
                            <input type="password" value={ this.state.confirmPassword } placeholder="请输入重复新密码" onChange={ this.handleConfirmPasswordChange } />
                            <input type="button" value="提交" onClick={ this.handleSubmit } className={ 'submit ' + this.state.loginBtnSty } disabled={ this.state.loginDisabled } />
                            <input type="button" className="cancel" value="取消" onClick={ this.handleCancel }/>
                        </div>
                    </div>
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
                <div className="welcome">欢迎您再次访问，您已成功联网, <span className="time">{ this.state.second }</span> 秒倒计时</div>
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
    function sendSmsCode(mobile){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option = {
            globalKey: _GLOBAL_KEY,//全局key
            globalValue: _GLOBAL_VALUE,//全局value
            version: authVersion,//版本
            phoneNumber: mobile,//手机号
            userMac: _USER_MAC,//用户终端MAC
            devId: _DEV_ID //设备ID
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

                }else{
                    formatSMSError(data.message);
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert('Sorry,sendSmsCode failure.');
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
            acName: _NAS_NAME//nas设备名称，NAS认证必填
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
                    //保存认证记录
                    var authType = type == '1' ? 'mobile' : 'freeauth';
                    saveAuthLog(authType);
                    //当为胖AP时，调用设备总线接口放行
                    var token = data.token;
                    if(token != null && token != undefined && $.trim(token) != ''){
                        fatAPAuth(token, _this);
                        return;
                    }
                    redirect();//页面跳转
                }else{
                    formatAuthError(data.message);
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert('Sorry,authentication failure.');
            },
            complete: function(XHR, textStatus){
                _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
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

            customerId: _CUSTOMER_ID,//客户id
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
                    _USER_PHONE = mobile;
                    //保存认证记录
                    saveAuthLog('username', userName);
                    //当为胖AP时，调用设备总线接口放行
                    var token = data.token;
                    if(token != null && token != undefined && $.trim(token) != ''){
                        fatAPAuth(token, _this);
                        return;
                    }
                    redirect();//页面跳转
                }else{
                    formatAuthError(data.message);
                    _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert('Sorry,authentication failure.');
                _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
            },
            complete: function(XHR, textStatus){

            }
        });
    }

    /**
     * 胖AP认证放行
     * @param token token参数
     */
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
                    alert('密码修改成功，请使用新密码重新登陆！');
                    _this.clearInput();//清空上次输入的内容
                    _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
                    _this.forward();//返回Auth组件
                }else{
                    alert(data.message);
                    _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert('密码修改失败！');
                _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
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
                alert('验证码发送失败！');
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
                    _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
                    _this.forward();
                }else{
                    alert(data.message);
                    _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert('验证码校验失败！');
                _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
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
                    alert('密码修改成功，请使用新密码重新登陆！');
                    _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
                    _this.forward();
                }else{
                    alert(data.message);
                    _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
                }
            },
            error: function(XHR, textStatus, errorThrown) {
                alert('密码修改失败！');
                _this.setState({loginDisabled: '', loginBtnSty: ''});//登录按钮恢复
            },
            complete: function(XHR, textStatus){

            }
        });
    }

    /**
     * 在线时长控制
     * @param authType 认证类型：mobile、username、freeauth
     * @param func 接口执行成功后的回调方法
     */
    function checkNetconfig(authType, func){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option ={
            'auth_type': authType,//认证类型
            'user_mac': _USER_MAC,//用户终端mac
            'merchant_id': _CUSTOMER_ID,//商户id
            'dev_mac': _DEV_MAC//设备mac
        };
        try {
            $.ajax({
                url: netconfigUrl,
                type: 'GET',
                dataType: 'JSONP',
                jsonp: 'callback',
                header: {
                    'cache-control': 'no-cache'
                },
                data: option,
                success: function (data) {
                    if(data.result == 'FAIL'){
                        alert('系统异常，请稍后再试...');
                        console.log(data.message);
                        return;
                    }
                    func();
                },
                error: function (xhr, textStatus) {
                    alert(textStatus || '系统异常，请稍后再试...');
                }
            });
        } catch (e) {
            console.log(e);
        }
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
            merchantId: _CUSTOMER_ID,//客户id
            apMac: _DEV_MAC//设备MAC
        };
        $.ajax({
            url: checkBlackByCellphoneUrl,
            type: 'GET',
            dataType: 'JSONP',
            jsonp: 'callback',
            header: {
                'cache-control': 'no-cache'
            },
            data: option,
            success: function (data) {
                if(data.result == 'FAIL'){
                    alert('系统异常，请稍后再试...');
                    console.log(data.message);
                    return;
                }
                if(data.isBlacklist == 'yes'){
                    alert('该用户已拉黑');
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
     * 通过手机号校验是否在黑名单里
     * @param params 参数
     * @param func 接口执行成功后的回调方法
     */
    function checkBlackByUserName(userName, func){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var option ={
            userName: userName,//用户名
            merchantId: _CUSTOMER_ID,//客户id
            apMac: _DEV_MAC//设备MAC
        };
        $.ajax({
            url: checkBlackByUserNameUrl,
            type: 'GET',
            dataType: 'JSONP',
            jsonp: 'callback',
            header: {
                'cache-control': 'no-cache'
            },
            data: option,
            success: function (data) {
                if(data.result == 'FAIL'){
                    alert('系统异常，请稍后再试...');
                    console.log(data.message);
                    return;
                }
                if(data.isBlacklist == 'yes'){
                    alert('该用户已拉黑');
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
     * 保存用户认证记录
     * @param authType 认证类型：freeauth、mobile、username
     * @param userName 用户名
     */
    function saveAuthLog(authType, userName){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        if(userName == null || userName == undefined){
            userName = '';
        }
        var option ={
            auth_type: authType,//认证类型
            auth_id: _USER_PHONE,//手机号
            username: userName,//用户名
            dev_id: _DEV_ID,//设备类型
            mac: _USER_MAC,//用户MAC
            mobile_mac: _USER_MAC,//用户MAC
            device_mac: _DEV_MAC,//设备MAC
            user_ip: _USER_IP,//用户ip
            ac_name: _NAS_NAME,//设备名称
            merchant_id: _CUSTOMER_ID,//商户id
            terminal_type: $.browser.mobileOS //终端类型
        };
        try {
            $.ajax({
                url: authlogUrl,
                type: 'GET',
                async: false,
                dataType: 'JSONP',
                jsonp: 'callback',
                header: {
                    'cache-control': 'no-cache'
                },
                data: option,
                success: function (data) {
                    console.log(data);
                }
            });
        } catch (e) {
            console.log(e);
        }
    }

    /**
     * 格式化 发送验证码 错误消息
     * @param message 接口错误消息
     */
    function formatSMSError(message){
        if(message == null || message == undefined){
            alert('抱歉，获取验证码失败.');
        }else if(message == 'sms_code_sended'){
            alert('抱歉，验证码发送过于频繁，您可使用获取的验证码认证上网.');
        }else if(message == '接口无返回值！' || message == '接口返回值不允许为空！'){
            alert('抱歉，获取验证码失败（错误信息：接口无响应）');
        }else{
            if(message.length >= 30){
                message = message.substring(0,30) + '......';
            }
            alert('抱歉，获取验证码失败（错误信息：'+ message +'）');
        }
    }

    /**
     * 格式化 认证 错误消息
     * @param message 接口错误消息
     */
    function formatAuthError(message){
        if(message == null || message == undefined){
            alert('抱歉，认证失败.');
        }else if(message == 'sms_code_error'){
            alert('抱歉，验证码校验失败，请重新输入.');
        }else if(message == 'sms_code_expired'){
            alert('抱歉，验证码已失效，请重新获取.');
        }else if(message == '接口无返回值！' || message == '接口返回值不允许为空！'){
            alert('抱歉，认证失败（错误信息：接口无响应）');
        }else{
            if(message.length >= 30){
                message = message.substring(0,30) + '......';
            }
            alert('抱歉，认证失败（错误信息：'+ message +'）');
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
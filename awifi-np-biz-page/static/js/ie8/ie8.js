
var smsUrl = '/auth/sendSmsCode';//发送短信验证码接口地址
var phoneAuthUrl = '/auth/phoneAuth';//手机认证接口地址
var userLoginUrl = '/staticuser/phoneAuth';//静态用户登录接口地址
var fatApAuthUrl = '/smartwifi/auth';//胖ap认证放行接口地址
var authVersion = 'v1.1';//接入系统版本号

var auth_code_timer = null;
var auth_code_timing = 60;
var in_commit = false;

var language = 'cn';

/** 当用户为新用户时，默认显示 手机号认证、用户名认证  */
if(_LOGIN_TYPE === 'unauth'){
    showMobileUserPassAuth();
}
/** 当用户为老用户时，默认显示“免费上网”  */
else{
    showFreeAuth();
}

//中英文--中文
var message_cn = {
    //公共
    loginButton:'免费登录',
    freeLoginBtnDsp: '点击免费上网',
    changeAuthType: '切换认证',

    //手机号
    mobileTitle: '手机号',
    inputCellphonePlaceholder:'输入手机号码',
    alertInputCellphone: '请输入正确的手机号',
    inputAuthCodePlaceholder: '输入验证码',
    getAuthCodeBtnDsp: '点击获取',
    reacquire: '重新获取',
    alertInputAuthCode: '请输入正确的验证码',
    alertAgreeDisclaimer: '请先同意免责声明',//请先同意免责声明

    //用户名
    userPassTitle: '用户名',
    inputUserNamePlaceholder: '输入用户名',
    inputPasswordPlaceholder:'输入密码',

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
    alertSorryAuthCheckFail: '抱歉，认证失败（错误信息：',//抱歉，认证失败（错误信息：

    a: ''
};

//中英文--英文
var message_en = {
    //公共
    loginButton:'Free Login',
    freeLoginBtnDsp: 'Click to Free Login',
    changeAuthType: 'Switch',

    //手机号
    mobileTitle: 'Telephone',
    inputCellphonePlaceholder: 'Enter phone number',
    alertInputCellphone: 'Please enter the correct phone number',
    inputAuthCodePlaceholder: 'Enter auth code',
    getAuthCodeBtnDsp: 'get auth code',
    reacquire: 'Get again',
    alertInputAuthCode: 'Please enter the correct auth code',
    alertAgreeDisclaimer: 'Please agree to the disclaimer',//请先同意免责声明

    //用户名
    userPassTitle: 'UserName',
    inputUserNamePlaceholder: 'Enter username',
    inputPasswordPlaceholder:'Enter password',

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
    alertSorryAuthCheckFail: 'Sorry, authentication failed (error message:',//抱歉，认证失败（错误信息：

    a: ''
};

var message = message_cn;

function get_mobile_number(){
    var mobile = $('input[name=mobile]').val();
    var reg = new RegExp(/^1[34578]\d{9}$/);
    if(reg.test(mobile)){
        return mobile;
    }
    if (language == 'cn'){
        alert(message_cn.alertInputCellphone);
    } else if (language == 'en'){
        alert(message_en.alertInputCellphone);
    }
}

function check_timer(){
    auth_code_timing = auth_code_timing - 1;
    if (language == 'cn'){
        $('.code a')[0].innerText = message_cn.reacquire + '(' + auth_code_timing + ')';
    } else if (language == 'en'){
        $('.code a')[0].innerText = message_en.reacquire + '(' + auth_code_timing + ')';
    }

    if(auth_code_timing <= 0){
        clearInterval(auth_code_timer);
        $('.code a')[0].className = '';
        if (language == 'cn'){
            $('.code a')[0].innerText = message_cn.getAuthCodeBtnDsp;
        } else if (language == 'en'){
            $('.code a')[0].innerText = message_en.getAuthCodeBtnDsp;
        }
        auth_code_timing = 60
    }
}

// 获取验证码
function get_auth_code(){
    // 手机号检查 && 防止重复触发
    var mobile = get_mobile_number();
    if(!mobile || auth_code_timing != 60){
        return;
    }
    $('.code a')[0].className = 'inactive';
    check_timer();
    auth_code_timer = setInterval(check_timer, 1000);
    sendSmsCode(mobile);//发送短信验证码
}

// 短信认证
function doAuth(){
    if(!$('.container .agreement input')[0].checked){
        alert(message.alertAgreeDisclaimer);//请先同意用户使用协议
        return;
    }
    var mobile = get_mobile_number();
    if(!mobile){
        return;
    }
    var auth_code = $('input[name=auth_code]').val();
    if(!(/^[0-9]{6}$/).test(auth_code)){
        alert(message.alertInputAuthCode);//请输入正确的验证码
        return;
    }
    disabledButton('mobileAuth');//按钮变回
    phoneAuth(mobile, auth_code, 1, 'mobileAuth');//手机认证接口
}

//用户名认证
function  userAuth(){
    if(!$('.container .agreement input')[0].checked){
        alert(message.alertAgreeDisclaimer);//请先同意用户使用协议
        return;
    }
    var userName = $('#userName').val();//静态用户名
    var password = $('#pwd').val();//密码
    if(isBlank(userName) || userName === message.inputUserNamePlaceholder){
        alert(message.inputUserNamePlaceholder);//请输入用户名
        return;
    }
    if(isBlank(password) || password === message.inputPasswordPlaceholder){
        alert(message.inputPasswordPlaceholder);//请输入密码
        return;
    }
    disabledButton('userNameAuth');//按钮变灰，防止重复提交
    userLogin(userName, password);
}

// 免认证
function doFree(){
    var mobile = _USER_PHONE;
    disabledButton('clickFreeLogin');//按钮变灰，防止重复提交
    phoneAuth(mobile, '', '4', 'clickFreeLogin');
}

$().ready(function() {
    $(".container .agreement a").hover(
        //移入
        function () {
            $(".container.authed .commit .free").css("display","none");
            $(".container .statement").css("display","block");
        },
        //移出
        function () {
            $(".container .statement").css("display","none");
            $(".container.authed .commit .free").css("display","block");
        }
    );
    $(".container .mob input[name=mobile]").focus(function(e){
        if(e.target.value == message_cn.inputCellphonePlaceholder){
            e.target.value = '';
            e.target.className = 'active';
        } else if (e.target.value == message_en.inputCellphonePlaceholder) {
            e.target.value = '';
            e.target.className = 'active';
        }
    });
    $(".container .code input[name=auth_code]").focus(function(e){
        if(e.target.value == message_cn.inputAuthCodePlaceholder){
            e.target.value = '';
            e.target.className = 'active';
        } else if (e.target.value == message_en.inputAuthCodePlaceholder) {
            e.target.value = '';
            e.target.className = 'active';
        }
    });
    $(".container .mob input[name=mobile]").blur(function(e){
        if (language == 'cn' && e.target.value == ''){
            e.target.value = message_cn.inputCellphonePlaceholder;
            e.target.className = '';
        } else if (language == 'en' && e.target.value == '') {
            e.target.value = message_en.inputCellphonePlaceholder;
            e.target.className = '';
        }
    });
    $(".container .code input[name=auth_code]").blur(function(e){
        if (language == 'cn' && e.target.value == ''){
            e.target.value = message_cn.inputAuthCodePlaceholder;
            e.target.className = '';
        } else if (language == 'en' && e.target.value == '') {
            e.target.value = message_en.inputAuthCodePlaceholder;
            e.target.className = '';
        }
    });
    $(".container .mob input[name=userName]").focus(function(e){
        if(e.target.value == message_cn.inputUserNamePlaceholder){
            e.target.value = '';
            e.target.className = 'active';
        } else if (e.target.value == message_en.inputUserNamePlaceholder) {
            e.target.value = '';
            e.target.className = 'active';
        }
    });
    $(".container .code input[name=pwd]").focus(function(e){
        if(e.target.value == message_cn.inputPasswordPlaceholder){
            e.target.value = '';
            e.target.className = 'active';
            $('#pwd').attr('type', 'password');
        } else if (e.target.value == message_en.inputPasswordPlaceholder) {
            e.target.value = '';
            e.target.className = 'active';
            $('#pwd').attr('type', 'password');
        }
    });
    $(".container .mob input[name=userName]").blur(function(e){
        if (language == 'cn' && e.target.value == ''){
            e.target.value = message_cn.inputUserNamePlaceholder;
            e.target.className = '';
        } else if (language == 'en' && e.target.value == '') {
            e.target.value = message_en.inputUserNamePlaceholder;
            e.target.className = '';
        }
    });
    $(".container .code input[name=pwd]").blur(function(e){
        if (language == 'cn' && e.target.value == ''){
            e.target.value = message_cn.inputPasswordPlaceholder;
            e.target.className = '';
            $('#pwd').attr('type', 'text');
        } else if (language == 'en' && e.target.value == '') {
            e.target.value = message_en.inputPasswordPlaceholder;
            e.target.className = '';
            $('#pwd').attr('type', 'text');
        }
    });

    //tab切换
    $('.navs').on('click', 'li', function (e) {
        var $el = $(this);
        var idx = $el.index();
        $el.parent().find('.active').removeClass('active');
        $el.addClass('active');
        var $panel = $('.nav-panel').hide().eq(idx).show();
    });

    //中英文切换
    $('.cn').on('click', function(){
        var $language = $('.language');
        $language.removeClass('enBg');
        $language.addClass('cnBg');
        language = 'cn';
        changeTabValue();
    });
    $('.en').on('click', function(){
        var $language = $('.language');
        $language.removeClass('cnBg');
        $language.addClass('enBg');
        language = 'en';
        changeTabValue();
    });

    //中英文切换时，初始化文字显示
    function changeTabValue (){
        var $mobile = $('input[name=mobile]');//手机号
        var mobile = $mobile.val();//手机号

        var $authCode = $('input[name=auth_code]');//验证码
        var authCode = $authCode.val();//验证码

        var $userName = $('input[name=userName]');//用户名
        var userName = $userName.val();//用户名

        var $pwd = $('input[name=pwd]');//密码
        var pwd = $pwd.val();//密码

        if (language == 'cn') {
            message = message_cn;
            //tab
            $('#mobileTab').text(message_cn.mobileTitle);
            $('#userNameTab').text(message_cn.userPassTitle);

            //手机号认证
            if(isBlank(mobile) || mobile === message_en.inputCellphonePlaceholder){//手机号
                $mobile.val(message_cn.inputCellphonePlaceholder);
            }
            if(isBlank(authCode) || authCode === message_en.inputAuthCodePlaceholder){//验证码
                $authCode.val(message_cn.inputAuthCodePlaceholder);
            }
            $('#getAuthCode').text(message_cn.getAuthCodeBtnDsp);
            $('#mobileAuth').val(message_cn.loginButton);

            //用户名认证
            if(isBlank(userName) || userName === message_en.inputUserNamePlaceholder){//用户名
                $userName.val(message_cn.inputUserNamePlaceholder);
            }
            if(isBlank(pwd) || pwd === message_en.inputPasswordPlaceholder){//密码
                $pwd.val(message_cn.inputPasswordPlaceholder);
            }

            $('#userNameAuth').val(message_cn.loginButton);
            //点击免费上网
            $('#clickFreeLogin').val(message_cn.freeLoginBtnDsp);
            //切换认证
            $('#changeToAuth').text(message_cn.changeAuthType);
        } else if (language == 'en'){
            message = message_en;
            //tab
            $('#mobileTab').text(message_en.mobileTitle);
            $('#userNameTab').text(message_en.userPassTitle);

            //手机号认证
            if(isBlank(mobile) || mobile === message_cn.inputCellphonePlaceholder){//手机号
                $mobile.val(message_en.inputCellphonePlaceholder);
            }
            if(isBlank(authCode) || authCode === message_cn.inputAuthCodePlaceholder){//验证码
                $authCode.val(message_en.inputAuthCodePlaceholder);
            }
            $('#getAuthCode').text(message_en.getAuthCodeBtnDsp);
            $('#mobileAuth').val(message_en.loginButton);

            //用户名认证
            if(isBlank(userName) || userName === message_cn.inputUserNamePlaceholder){//用户名
                $userName.val(message_en.inputUserNamePlaceholder);
            }
            if(isBlank(pwd) || pwd === message_cn.inputPasswordPlaceholder){//密码
                $pwd.val(message_en.inputPasswordPlaceholder);
            }
            $('#userNameAuth').val(message_en.loginButton);
            //点击免费上网
            $('#clickFreeLogin').val(message_en.freeLoginBtnDsp);
            //切换认证
            $('#changeToAuth').text(message_en.changeAuthType);
        }
    }
});

(function() {

    var BrowserDetect = {
        init: function () {
            this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
            this.version = this.searchVersion(navigator.userAgent)
                || this.searchVersion(navigator.appVersion)
                || "an unknown version";
            this.OS = this.searchString(this.dataOS) || "an unknown OS";
        },
        searchString: function (data) {
            for (var i=0;i<data.length;i++)	{
                var dataString = data[i].string;
                var dataProp = data[i].prop;
                this.versionSearchString = data[i].versionSearch || data[i].identity;
                if (dataString) {
                    if (dataString.indexOf(data[i].subString) != -1)
                        return data[i].identity;
                }
                else if (dataProp)
                    return data[i].identity;
            }
        },
        searchVersion: function (dataString) {
            var index = dataString.indexOf(this.versionSearchString);
            if (index == -1) return;
            return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
        },
        dataBrowser: [
            {
                string: navigator.userAgent,
                subString: "Chrome",
                identity: "Chrome"
            },
            { 	string: navigator.userAgent,
                subString: "OmniWeb",
                versionSearch: "OmniWeb/",
                identity: "OmniWeb"
            },
            {
                string: navigator.vendor,
                subString: "Apple",
                identity: "Safari",
                versionSearch: "Version"
            },
            {
                prop: window.opera,
                identity: "Opera"
            },
            {
                string: navigator.vendor,
                subString: "iCab",
                identity: "iCab"
            },
            {
                string: navigator.vendor,
                subString: "KDE",
                identity: "Konqueror"
            },
            {
                string: navigator.userAgent,
                subString: "Firefox",
                identity: "Firefox"
            },
            {
                string: navigator.vendor,
                subString: "Camino",
                identity: "Camino"
            },
            {		// for newer Netscapes (6+)
                string: navigator.userAgent,
                subString: "Netscape",
                identity: "Netscape"
            },
            {
                string: navigator.userAgent,
                subString: "MSIE",
                identity: "Explorer",
                versionSearch: "MSIE"
            },
            {
                string: navigator.userAgent,
                subString: "Gecko",
                identity: "Mozilla",
                versionSearch: "rv"
            },
            { 		// for older Netscapes (4-)
                string: navigator.userAgent,
                subString: "Mozilla",
                identity: "Netscape",
                versionSearch: "Mozilla"
            }
        ],
        dataOS : [
            {
                string: navigator.platform,
                subString: "Win",
                identity: "Windows"
            },
            {
                string: navigator.platform,
                subString: "Mac",
                identity: "Mac"
            },
            {
                string: navigator.userAgent,
                subString: "iPhone",
                identity: "iPhone/iPod"
            },
            {
                string: navigator.platform,
                subString: "Linux",
                identity: "Linux"
            }
        ]

    };

    BrowserDetect.init();

    window.$.client = { os : BrowserDetect.OS, browser : BrowserDetect.browser };

})();

/**
 * 页面跳转
 */
function redirect(){
    var url = '/ie8?';
    url+= '&global_key=' + _GLOBAL_KEY;//全局日志key
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
    url+= '&platform=' + _PLATFORM;//省分平台-前缀

    url+= '&customer_id=' + _CUSTOMER_ID;//客户id
    url+= '&cascade_label=' + _CASCADE_LABEL;//客户层级

    url+= '&pageType=4';//页面序号
    url+= '&num=1';//页面序号

    window.location.href = url;
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
        devId: _DEV_ID, //设备ID
        platform: _PLATFORM //省分平台-前缀
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
function phoneAuth(mobile, authCode, type, buttonId){
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
        acName: _NAS_NAME,//nas设备名称，NAS认证必填
        platform: _PLATFORM //省分平台-前缀
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
                    fatAPAuth(token, buttonId);
                    return;
                }
                redirect();//页面跳转
            }else{
                formatAuthError(data.message);
                enableButton(buttonId);//登录按钮恢复
            }
        },
        error: function(XHR, textStatus, errorThrown) {
            alert('Sorry,authentication failure.');
            enableButton(buttonId);//登录按钮恢复
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
function userLogin(userName, password){
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
                    fatAPAuth(token);
                    return;
                }
                redirect();//页面跳转
            }else{
                formatAuthError(data.message);
                enableButton('userNameAuth');//登录按钮恢复
            }
        },
        error: function(XHR, textStatus, errorThrown) {
            alert('Sorry,authentication failure.');
            enableButton('userNameAuth');//登录按钮恢复
        },
        complete: function(XHR, textStatus){

        }
    });
}

/**
 * 胖AP认证放行
 * @param token token参数
 //*/
function fatAPAuth(token, buttonId){
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
                enableButton(buttonId);//登录按钮恢复
            }
        },
        error: function(XHR, textStatus, errorThrown) {
            //因该接口重定向，导致无法判断接口执行是否成功
            try{
                redirect();//页面跳转
            }catch(e){
                enableButton(buttonId);//登录按钮恢复
            }
        },
        complete: function(XHR, textStatus){
            try{
                redirect();//页面跳转
            }catch(e){
                enableButton(buttonId);//登录按钮恢复
            }
        }
    });
}

/**
 * 格式化 发送验证码 错误消息
 * @param message 接口错误消息
 */
function formatSMSError(errorMessage){
    if(errorMessage == null || errorMessage == undefined){
        alert(message.alertGetAuthCodeFail);//抱歉，获取验证码失败.
    }else if(errorMessage == 'sms_code_sended'){
        alert(message.alertCheckAuthCodeSended);//抱歉，验证码发送过于频繁，您可使用获取的验证码认证上网.
    }else if(errorMessage == '接口无返回值！' || errorMessage == '接口返回值不允许为空！'){
        alert(message.alertAuthCodeNoResponse);//抱歉，获取验证码失败（错误信息：接口无响应）
    }else{
        if(errorMessage.length >= 30){
            errorMessage = errorMessage.substring(0,30) + '......';
        }
        alert(message.alertSorryAuthCodeCheckFail + errorMessage +'）');//抱歉，获取验证码失败（错误信息：
    }
}

/**
 * 格式化 认证 错误消息
 * @param message 接口错误消息
 */
function formatAuthError(errorMessage){
    if(errorMessage == null || errorMessage == undefined){
        alert(message.alertAuthFail);//抱歉，认证失败.
    }else if(errorMessage == 'sms_code_error'){
        alert(message.alertAuthCodeError);//抱歉，验证码校验失败，请重新输入.
    }else if(errorMessage == 'sms_code_expired'){
        alert(message.alertAuthCodeExpired);//抱歉，验证码已失效，请重新获取.
    }else if(errorMessage == '接口无返回值！' || errorMessage == '接口返回值不允许为空！'){
        alert(message.alertAuthNoResponse);//抱歉，认证失败（错误信息：接口无响应）
    }else{
        if(errorMessage.length >= 30){
            errorMessage = errorMessage.substring(0,30) + '......';
        }
        alert(message.alertSorryAuthCheckFail+ errorMessage +'）');//抱歉，认证失败（错误信息：
    }
}

/**
 * 按钮变灰，防止重复提交
 * @param buttonId
 */
function disabledButton(buttonId){
    var $button = $('#' + buttonId);
    $button.addClass('disabled');
    $button.attr('disabled', true);
}

/**
 * 按钮恢复
 * @param buttonId
 */
function enableButton(buttonId){
    var $button = $('#' + buttonId);
    $button.removeClass('disabled');
    $button.attr('disabled', false);
}

/**
 * 判断字符串是否为空
 * @auth 许小满
 */
function isBlank(str){
    if(str === null || str === undefined || $.trim(str) === '')
        return true;
    return false;
}
/**
 * 判断字符串是否不为空
 * @auth 许小满
 */
function isNotBlank(str){
    if(str === null || str === undefined || $.trim(str) === '')
        return false;
    return true;
}

/**
 * 显示 手机号、用户名认证
 */
function showMobileUserPassAuth(){
    $('.navs').removeClass('hide');//tab
    $('.nav-panel').removeClass('hide');//tab - div
    $('#mobileAuthDiv').css('display', 'block');//手机号认证div
    $('.agreement').removeClass('hide');//免责申明
}

/**
 * 显示 免费认证
 */
function showFreeAuth(){
    $('#freeAuthDiv').removeClass('hide');
}

/**
 * 认证切换
 */
function switchAuth(){
    showMobileUserPassAuth();
    $('#freeAuthDiv').addClass('hide');
}
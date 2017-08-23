<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta name="format-detection" content="telephone=no"/>
<title>爱WiFi行业Portal管理平台</title>
<link href="<%=request.getContextPath()%>/css/login.min.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
var browser = navigator.appName;
var b_version = navigator.appVersion;
var version = b_version.split(";");
var trim_Version = version[1].replace(/[ ]/g, "");
if (browser == "Microsoft Internet Explorer") {
    if (trim_Version == "MSIE6.0" || trim_Version == "MSIE7.0" || trim_Version == "MSIE8.0") {
        window.location.href = '${pageContext.request.contextPath}/html/error/ie.html';
    }
}
</script>
</head>
<body>
<header class="clfix">
    <div class="wrap">
        <div id="logo">
            <i class="icon-logo"></i>
            <h1>爱WiFi行业Portal管理平台</h1>
        </div>
    </div>
</header>
<section class="main clfix">
    <div class="wrap">
        <div class="login-form">
            <div class="form-title"><i class="icon-user"></i><h2>帐户登录</h2></div>
            <form id="loginForm" name="loginForm" action="<%=request.getContextPath()%>/login_check" method="post">
                <div class="notice" id="noticeBody" style="display: none">
                    <div class="icon">
                        <img src="<%=request.getContextPath()%>/images/notice.png" alt=""/>
                    </div>
                    <div class="content">
                        <div id="scroll_div" class="scroll_div">
                            <div id="scroll_begin" class="scroll_begin">
                                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                            </div>
                            <div id="scroll_end" class="scroll_end"></div>
                        </div>
                    </div>
                </div>
                <ul>
                    <li><input type="text" class="input" id="username" name="username" placeholder="用户名" maxlength="50" value="" tabindex="1" /></li>
                    <li><input type="password" class="input" id="password" name="password" placeholder="密码" maxlength="20" value="" tabindex="2" /></li>
                    <li>
                        <input type="text" class="input" name="code" id="code" placeholder="验证码" tabindex="3" />
                        <img class="iconfont" src="<%=request.getContextPath()%>/user/generateauthcode" id="authCodeImg" onclick="this.src='/user/generateauthcode?d='+Math.random();" alt="点击刷新" width="100%" />
                    </li>
                    <li><input type="button" class="login" value="登&emsp;录" id="login-btn" tabindex="4" /></li>
                    <li><div id="errormsg" class="msgcontent">${error}</div></li>
                </ul>
            </form>
        </div>
    </div>
    <div class="main-bg"></div>
</section>
<div class="wrap clfix">
    <div class="icon-list">
        <ul>
            <li><i class="icon-l"></i>专业的运营管理平台</li>
            <li><i class="icon-m"></i>合理的信息统计分析</li>
            <li><i class="icon-r"></i>完善的上网用户管理</li>
        </ul>
    </div>
</div>
<footer class="clfix">
    <p>版权所有：中国电信爱WiFi运营中心</p>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/login.min.js"></script>
</body>
</html>
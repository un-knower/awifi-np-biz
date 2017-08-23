<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>温馨提醒</title>
	<style>
		html, body {
			width: 100%;
			height: 100%;
			margin: 0px;
			padding: 0px;
		}
		body, select, input, textarea {
			font: 12px/1.2 'Microsoft Yahei', Tahoma, Helvetica, Arial, '\5b8b\4f53', sans-serif;
		}
		a:focus {
			text-decoration:none;
			outline:none;
		}
		a:hover {
			text-decoration:none;
			outline:none;
		}
		a:visited {
			text-decoration:none;
			outline:none;
		}
		.table-layout {
			width: 100%;
			height: 100%;
			min-width: 1000px;
			border-collapse: collapse;
			background-image: url("../../images/bg-repeat_20_720.png");
			background-repeat: repeat-x;
		}
		.container {
			margin: 0 auto;
			width: 1000px;
			height: 300px;
			padding-top: 420px;
			background-image: url("../../images/deniedinfo.png");
			background-repeat: no-repeat;
			background-position: center 90px;
		}
		.container-td {
			height: 480px;
		}
		.awifi-title {
			height: 240px;
			text-align:center;
			color: #d20000;
			font-size: 42px;
			line-height: 60px;
		}
		.awifi-title a{
			font-size: 24px;
			color: #686868;
			text-decoration:none;
			outline:none;
		}
		.footer-td {
			padding-top: 40px;
			vertical-align: top;
			text-align: center;
			font-style: normal;
			color: #686868;
			line-height: 24px;
		}
		.tail-bg {
			height: 120px;
			background-image: url("../../images/footer-bg.png");
			background-repeat: repeat-x;
			background-position: center bottom;
		}
	</style>
</head>
<body>

<table class="table-layout" cellspacing=0>
	<tbody>
	<tr>
		<td class="container-td">
			<div class="container">
				<div class="awifi-title">
				${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}
				你的权限不够！ 只有拥有Admin权限才能访问！
				<br>
				<a href="<%=request.getContextPath() %>/logout">返回登录</a>
				</div>
			</div>
		</td>
	</tr>
	<tr>
		<td class="footer-td">
			支持电话：4008-252525<br/>
			版权所有 © 中国电信爱WIFI运营中心
		</td>
	</tr>
	<tr>
		<td class="tail-bg"></td>
	</tr>
	</tbody>
</table>
</body>
</html>

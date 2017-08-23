<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
<title> 修改密码 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<link href="<%=request.getContextPath()%>/css/auth/updatePwd.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/lib/poshytip/tip-yellow.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/lib/cellphone/jDialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/jquery/jquery.poshytip.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/src/base/util/JTipsUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/src/base/util/StringUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/src/base/util/JDialogUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/md5.js" ></script>
 <script type="text/javascript">
	var updatePwd = {
			init : function(){
				var _this = this;
				_this.submit();
				_this.initTips();
			},
			submit:function(){//提交
				$('#btnsubmit').on('click', function(){
					//获得新密码
					var $newPwd = $("#newPwd");
					var newPwd = $newPwd.val();
					//获得重复的新密码
					var $repeatNewPwd = $("#repeatNewPwd");
					var repeatNewPwd = $repeatNewPwd.val();
					//校验 新密码不能为空
					if(isBlank(newPwd)){
						updateShowTipos($newPwd, '新密码不能为空!');
						return;
					}
					//校验新密码格式
					if(!chkString(newPwd, defrules.password)){
						//alert("格式错误");
						updateShowTipos($newPwd, '新密码必须为6-20位字符，包括字母、数字、下划线、特殊符号!');
						return;
					}
					//校验 重复新密码不能为空
					if(isBlank(repeatNewPwd)){
						updateShowTipos($repeatNewPwd, '重复新密码不能为空!');
						return;
					} 
					//验证两次密码输入是否一致
					if(repeatNewPwd != newPwd){
						updateShowTipos($repeatNewPwd, '重复新密码格式必须和新密码格式一致!');
						return;
					} 
					//得到手机号码
					var $cellphone = $("#cellphone");
					var cellphone = $cellphone.val();
					//得到用户名
					var $userName = $("#userName");
					var userName = $userName.val();
					var url = "/user/setpassword";//重新设置密码
					$.ajax({
						type: 'get',
						url: url,
						dataType: 'json',
						data: {"password":md5(newPwd),
								"qpassword":md5(repeatNewPwd),
								"cellphone":cellphone,
								"userName":userName,
								},  
						success: function(data){
							if(data.result == 'OK'){
							      jDialog.alert('设置新密码：','密码设置成功!');
							      /* window.opener=null;
							      window.open("","_self");
							      window.close();   */
							      
							      /* window.opener = window;
							       var win = window.open("","_self");
							       win.close(); */
							       
							      //window.opener = '';
							        //window.close();
							     /* window.location.href = "/auth/login";  */
							} else {
								jDialog.alert(data.message);
							}
						},
						error: function(XMLHttpRequest, textStatus, errorThrown){
							//alert(XMLHttpRequest.status);
							alert('ajax=========error');
						}
					});
				});
			},
			initTips : function(){
				initTips($('#newPwd'));//新密码
				initTips($('#repeatNewPwd'));//重复新密码
			}
	};
	$(function(){
		updatePwd.init();
	});
	
 </script>
 </head>

 <body>
	<!--整体-->
	
	<div class="all_all">
		<!--头部广告-->
		<div class="header_advertising">
			<img src="${pageContext.request.contextPath}/images/advertising.png" width="100%;" />
		</div>
		<!--头(上)-->
		<div class="header_title">修改密码</div><!--头(上)-->
		<!-- 从忘记密码页面传过来的手机号码 -->
		<input type="hidden"   id="cellphone"  value="${cellphone} "/>
		<!-- 强制修改密码传递过来的用户名 -->
		<input type="hidden"   id="userName"  value="${userName} "/>
		<!--下(中间)-->
		<div class="phone_cellphone">
			<div class="prompt">新密码:</div>
			<div class="input"><input type="text"  id="newPwd"  name="password"/></div>
		</div><!--下(中间)-->
		
		<div class="phone_cellphone">
			<div class="prompt">重复新密码:</div>
			<div class="input"><input type="text"  id="repeatNewPwd"  name="qpassword"/></div>
		</div>
		
		<!--提交按钮-->
		<div class="phone_cellphone">
			<div class="input"  style="width:81%;">
				<button type="button"  class="button"   id="btnsubmit"  style="height:43px;width:92%;font-size: 15px;font-weight: bold;">提交</button>
			</div>
		</div>
	
		<!--尾部广告-->
		<!-- <div class="footer_advertising">
			<img src="images/advertising.png" />
		</div> -->
	</div><!--整体-->
 </body>
</html>

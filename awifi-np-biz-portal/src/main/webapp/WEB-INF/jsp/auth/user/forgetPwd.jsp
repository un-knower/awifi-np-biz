<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
<title> 忘记密码 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<link href="<%=request.getContextPath()%>/css/auth/forgetPwd.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/lib/poshytip/tip-yellow.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/jquery/jquery.poshytip.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/src/base/util/JTipsUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/src/base/util/StringUtil.js"></script>
<script type="text/javascript">
  var forgetPwd = {
		 SMSTIME: null,
		 SMSSECO: 60,
		 init : function(){
		  //alert('init');
		  var _this = this;
		  _this.sendSMS();
		  _this.submit();
		  _this.initTips();
	},
	sendSMS: function(){//发送验证码
		//alert(1);
		var _this = this;
		if(_this.SMSTIME!=null) window.clearTimeout(_this.SMSTIME);
		$('#sendsms').on('click', function(){
			//cleanJTips();//清除上次的消息提醒
			if($(this).hasClass('disabled')){
				//_this.showMessage('请等待');
				return false;
			}
			var $cellphone = $("#cellphone");//获取手机号
		    var cellphone = $cellphone.val();
			//校验 手机号不能为空
			if(isBlank(cellphone)){
				updateShowTipos($cellphone, '手机号不能为空!');
				return;
			}
			//验证手机号格式
			if(!chkString(cellphone, defrules.cellphone)){
				updateShowTipos($cellphone, '手机号格式错误!');
				return;
			}
			$('button#sendsms').addClass('disabled');
			_this.resetSendSMS();
			var url = "/authcode/sendauthcode?cellphone=" + cellphone;
			//alert('cellphone============'+cellphone);
			//alert('url==========='+url);
			//alert('获取验证码');
			$.ajax({
						type: 'get',
						url: url,
						dataType: 'json',
						success: function(data){
							if(data.result == 'OK'){
								//alert('ajax=========ok');
								return;
							} else {
								jDialog.alert(data.message);
							}
						},
						error: function(XMLHttpRequest, textStatus, errorThrown){
							//alert(XMLHttpRequest.status);
							//alert('ajax=========error');
						}
					});
				});
			},
			resetSendSMS: function(){//实现60秒内只能获取一次验证码
				var _this = this;
				if(_this.SMSSECO > 0){
					//alert(1);
					_this.SMSSECO = _this.SMSSECO-1;
					$('button#sendsms').html('请等待'+_this.SMSSECO+'秒');
					_this.SMSTIME = window.setTimeout(function(){_this.resetSendSMS();}, 1000);
				}else{
					//alert(2);
					window.clearTimeout(_this.SMSTIME);
					_this.SMSSECO = 60;
					$('button#sendsms').removeClass('disabled');
					$('button#sendsms').html('获取验证码');
				}
			},
			submit:function(){//提交验证码
			     $('#btnsubmit').on('click',function(){
					  //得到手机号
					  var $cellphone = $("#cellphone");
					  var cellphone = $cellphone.val();
					  //得到验证码
					  var $code = $('#code');
					  var code = $code.val();
					  var url = "/authcode/iscorrect?cellphone=" + cellphone +"&authcode=" + code;//调用后台校验验证码方法
					  $.ajax({
							type: 'get',
							url: url,
							dataType: 'json',
							success: function(data){
								if(data.result == 'OK'){
									var url = "/auth/updatepwdpage?cellphone=" + cellphone; 
									window.location.assign(url); 
									//window.location.href = //重新打开一个页面
								} else {
									jDialog.alert(data.message);
								}
							},
							error: function(XMLHttpRequest, textStatus, errorThrown){
								//alert(XMLHttpRequest.status);
							}
						});
			     });
			},
			initTips : function(){
				initTips($('#cellphone'));//手机号
				initTips($('#code'));//验证码
			}
  };
 
  $(function(){
	  forgetPwd.init();
	});
</script>
 </head>

 <body>
	<!--整体-->
	
	<div class="all">
		<!--头部广告-->
		<div class="header_advertising">
			<img src="${pageContext.request.contextPath}/images/advertising.png" width="100%;" />
		</div>
		<!--头(上)-->
		<div class="header_title">忘记密码</div><!--头(上)-->
			<!--下(中间)-->
			<div class="phone_cellphone">
				<div class="prompt">手机号:</div>
				<div class="input"><input type="text"  id="cellphone"  name="cellphone"/></div>
				
			</div><!--下(中间)-->
			<div id="cellphoneNoEmpty" style="color:red;font-family: '幼圆';padding-left: 22%;padding-top:2%;display:none;">手机号不能为空！</div>
	
			<!--code-->
			<div class="phone_code">
				<div class="prompt">验证码:</div>
				<div class="input">
					<input type="text"   id="code"  class="input_prompt"/>
					<button type="button"  class="code_button"   id="sendsms"  style="height:41px;line-height: 41px;width:60%;">获取验证码</button>
				</div>
			</div>
	
			<!--提交按钮-->
			<div class="phone_cellphone">
				<div class="input"  style="width:81%;">
					<button type="button"  class="button"   id="btnsubmit"  style="height:43px;width:100%;">提交</button>
				</div>
			</div>
		
		<!--尾部广告-->
		<!-- <div class="footer_advertising">
			<img src="images/advertising.png" />
		</div> -->
	</div><!--整体-->
 </body>
</html>

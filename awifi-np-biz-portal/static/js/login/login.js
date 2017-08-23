//切换验证码
function change() {
	var img =document.getElementById("authCodeImg");
	img.src=img.src+"?";
}
//文字滚动方法
function ScrollMessageLeft(){
	var speed = 10;
	var scroll_begin = document.getElementById("scroll_begin");
	var scroll_end = document.getElementById("scroll_end");
	var scroll_div = document.getElementById("scroll_div");
	scroll_end.innerHTML = scroll_begin.innerHTML;
	function Marquee(){
		if(scroll_end.offsetWidth - scroll_div.scrollLeft<=0){
			scroll_div.scrollLeft -= scroll_begin.offsetWidth;
		}else {
			scroll_div.scrollLeft ++;
		}
	}
	var MyMar = setInterval(Marquee,speed);
	scroll_div.onmouseover = function() {clearInterval(MyMar);}
	scroll_div.onmouseout = function() {MyMar=setInterval(Marquee,speed);}
}
var loginpage = {
	init : function(){//初始化函数
		var _this = this;
		_this.initTips();//初始化tips
		_this.showNotice();//公告事件
		_this.bindEvent();//绑定事件
	},
	initTips : function(){//初始化tips
		initTips($('#username'));//用户名
		initTips($('#password'));//密码
		initTips($('#code'));//验证码
	},
	showNotice : function(){
		var noticeUrl = '/auth/announce';
		$.ajax({
			type: 'GET',
			url: noticeUrl,
			dataType: 'json',
			success: function(data){
				if(data.result == 'OK'){
					//状态为true和有具体公告内容时，显示；否则（状态为false或公告内容为空时），隐藏
					var state = data.sign;
					var content = data.data;
					if(content === null || content === undefined || $.trim(content) === ''){
						content = '';
					}
					if (state == true){
						if (content != '') {
							$('#scroll_begin').append(content);
							$('.login-form').css('height', '350px');
							$('.login-form').css('margin-top', '50px');
							$('.login-form ul').css('margin-top', '10px');
							$('#noticeBody').show();
							ScrollMessageLeft();
						} else {
							$('#noticeBody').hide();
						}
					}else {
						$('#noticeBody').hide();
					}
				} else {
					$('#noticeBody').hide();
				}
			}
		})
	},
	bindEvent : function(){//绑定事件
		//var _this = this;
		//alert('bindEvent');
		$(".login").bind('click',function(){//登录函数
			//alert('loginFunc');
			//1. 校验用户名、密码是否正确
			var $userName = $('#username');//用户名
			var userName = $userName.val();
			if(userName === null || userName === undefined || $.trim(userName) === ''){
				updateTips($userName,'用户名不能为空！');
				showTips($userName);
				return;
			}

			var $password = $('#password');//密码
			var password = $password.val();
			if(password === null || password === undefined || $.trim(password) === ''){
				updateTips($password,'密码不能为空！');
				showTips($password);
				return;
			}
			//2. 校验验证码是否正确
			var $code = $('#code');
			var code = $code.val();
			if(code === null || code === undefined || $.trim(code) === '' ){
				updateTips($code,'验证码不能为空！');
				showTips($code);
				return;
			}
			var url = '/auth/validationcode?code=' + code;
			$.ajax({
				type: 'POST',
				url: url,
				dataType: 'json',
				success: function(data){
					if(data.result == 'OK'){
						//3. 提交表单
						$("#loginForm").submit();
					} else {
						updateTips($code,data.message);
						showTips($code);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown){
					//alert(XMLHttpRequest.status);
				}
			});
		});
		$('#username').bind('focus',function(){
			$("#errorDiv").hide();
		});
		$('#password').bind('focus',function(){
			$("#errorDiv").hide();
		});
		$('#code').bind('focus',function(){
			$("#errorDiv").hide();
		});
		// 回车登录
            $('#username').on('keypress', function(e){
                if(e.keyCode == 13){ // Enter
                    $(this).blur();
                    $('.login').click();
                }
            });
            $('#password').on('keypress', function(e){
                  if(e.keyCode == 13){ // Enter
                      $(this).blur();
                      $('.login').click();
                  }
              });
            $('#code').on('keypress', function(e){
                  if(e.keyCode == 13){ // Enter
                      $(this).blur();
                      $('.login').click();
                  }
              });
	}
};
$(window).ready(function(){
	loginpage.init();
});
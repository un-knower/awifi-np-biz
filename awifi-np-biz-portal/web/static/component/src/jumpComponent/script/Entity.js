/**
 * 跳转组件
 * @param state
 * @param divId
 * @returns {{render: render}}
 * @private
 * @Auther: 牛华凤
 * @Date: 2016-1-5
 */
var _Entity_ = function (state, divId) {

	var encryptionInterfaceUrl = '/app/encryption';//请求后端加密接口(本地)

	var _state = state || {
			textAlign: 'center',
			aText: '跳转中',
			aHref: ''
		};

	var EntityReact = React.createClass({
		getInitialState: function () {
			return _state;
		},
		componentDidMount: function(){// 当这个页面加载完之后，跳转到要连接的地址
			if(_DEV_ID == '{@devId@}') {
				return;
			}
			// 得到需要传给后端的数据封装成json格式
			var option = {
				deviceId:_DEV_ID,// 设备id
				userIp:_USER_IP,// 用户ip
				userMac:_USER_MAC,// 用户MAC
				userPhone:_USER_PHONE,// 用户手机号
				terminalType:$.browser.mobileOS,// 终端类型
				customerId:_CUSTOMER_ID// 营业厅id
			};
			var thirdPartyUrl = this.state.aHref;// 获得第三方url
			if(thirdPartyUrl.indexOf('?') == -1){//未包含?号，自动补充
				thirdPartyUrl += '?';
			}else if(thirdPartyUrl.indexOf('=') != -1){//已包含其它参数，自动补充=
				thirdPartyUrl += '&';
			}
			// 通过ajax方式，获得后端的URL
			$.ajax({
				//type: 'GET',// 请求方式
				url: encryptionInterfaceUrl,// 请求后端加密接口
				dataType: 'JSON',// jsonp格式
				//jsonp: 'callback',// 指定回调函数，这里名字可以为其他任意你喜欢的，比如jsonpcallback
				header:{// 清除头部缓存
					'cache-control': 'no-cache'
				},
				data: option,//传给后端的数据
				success: function(data, textStatus, jqXHR){
					if(data.result == 'OK'){
						thirdPartyUrl += 'params=' + data.params;
					} else {
						thirdPartyUrl += 'params=';
					}
					setTimeout(function() {
						window.location.href = thirdPartyUrl;
					}, 50); //延迟跳转
				},
				error: function(data){
					thirdPartyUrl += 'params=';
					setTimeout(function() {
						window.location.href = thirdPartyUrl;
					}, 50); //延迟跳转
				},
				complete: function(XHR, textStatus){

				}
			});
		},
		render: function () {
			return (
				<div className="textlink" style={{textAlign: this.state.textAlign}}>
					<a href={this.state.aHref} title={this.state.aText}>{this.state.aText}</a>
				</div>
			);
		}
	});

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

// 拉portal调用方式
// _Entity_(state, divId).render(); 组件拉了多次则调用多次

/*
 1) 写入页面中相应的Div元素，每个div的id保证唯一性
 2）合并多个组件js文件至一个js文件中，且在合并的js文件末尾加个要调用 ReactJs 的方法
 3）合并多个组件css文件至一个css文件中
 */

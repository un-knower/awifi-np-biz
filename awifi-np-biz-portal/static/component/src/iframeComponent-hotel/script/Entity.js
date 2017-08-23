/**
 * Created by 许小满 on 2016/6/21.
 * 酒店 iFrame 组件
 */
var _Entity_ = function (state, divId) {

    var _state = state || {
            url: 'http://hotel.51awifi.com/site/standardsite/index.html', //要展示的url
            needParse: false //是否需要解析json
        };

    var _iframeState = {
        merchantOpenId: '',//商户id(加密后)
        userOpenId: '',//用户id(加密后)
        url: null//网址
    };

    var getOpenParamsURL = '/pagesrv/mws/param/encry';//参数加密（微站）

    var IFrameComponent = React.createClass({
        getInitialState: function () {
            return _state;
        },
        //根据state变化来渲染组件
        render: function () {
            return (
                <div>
                    { _DEV_ID === '{@devId@}' ? <NullIFrameComponent /> : <JumpIFrameComponent /> }
                </div>
            )
        }
    });

    var JumpIFrameComponent = React.createClass({
        //获取实例的初始化状态
        getInitialState: function() {
            return _iframeState;
        },
        //组件将要被装载
        componentWillMount: function() {
            getOpenParams(this);
        },
        //获取所有参数
        getAllParams: function() {
            //getOpenParams(this);
            var params = '';
            params += 'merchantOpenId=' + this.state.merchantOpenId;
            var userOpenId = this.state.userOpenId;
            params += '&userOpenId=' + (userOpenId ? userOpenId : '');
            params += '&userMac=' + _USER_MAC;
            params += '&dev_id=' + _DEV_ID;
            params += '&ap_mac=' + _DEV_MAC;
            return params;
        },
        //获取iframe src
        getIframeSrc: function() {
            var url = _state.url;
            //拼接 http:// 前缀
            var iframeSrc = url.indexOf('http://') == 0 ? url : ('http://' + url);
            if(_state.needParse) {//是否需要解析json
                var params = this.getAllParams();
                //拼接参数
                iframeSrc += iframeSrc.indexOf('?') != -1 ? '&' : '?';
                iframeSrc += params;
            }
            _iframeState.url = iframeSrc;
            this.setState(_iframeState);
        },
        render: function () {
            var url = this.state.url;
            if(!url){
                return ( <div>跳转中......</div> )
            }
            return (
                <div className="_Entity_">
                    <iframe src={ url } style={{ width:'100%', border: '0px', 'minHeight':'2000px'}} scrolling="no"></iframe>'
                </div>
            )
        }
    });

    //portal制作时，不进行页面跳转，仅显示占用框
    var NullIFrameComponent = React.createClass({
        render: function () {
            return (
                <div className="_Entity_">
                    <div className="nullIframeSrc" style={{ 'height' : '48px'}}>Iframe区域</div>
                </div>
            )
        }
    });

    /**
     * 获取加密后的参数
     * @param _this this
     */
    function getOpenParams(_this){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        var userId = _USER_ID && _USER_ID != '{@userId@}' ? _USER_ID : '';
        var option ={
            merchantId: _CUSTOMER_ID,//商户id
            userId: userId //用户id
        };
        var params = 'params=' + encodeURIComponent(JSON.stringify(option));
        $.ajax({
            url: getOpenParamsURL,
            type: 'GET',
            dataType: 'JSON',
            header: { 'cache-control': 'no-cache' },
            data: params,
            success: function (data) {
                var code = data.code;
                if(code != '0'){
                    alert(data.msg);
                    return;
                }
                var resultData = data.data;
                _iframeState.merchantOpenId =  resultData.merchantOpenId;//商户id(加密后)
                _iframeState.userOpenId =  resultData.userOpenId;//用户id(加密后)
                _this.setState(_iframeState);
                _this.getIframeSrc();//获取iframe src
            },
            error: function (xhr, textStatus) {
                alert(textStatus || '系统异常，请稍后再试...');
            }
        });
    }

    /**
     * 获取解析后的url，因存在偶发的parserror问题，将该方法暂时废弃
     * @param _this this
     function getParseURL(url, params, _this){
        if(_DEV_ID == '{@devId@}'){
            return;
        }
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'JSONP',
            jsonp: 'callback',
            header: { 'cache-control': 'no-cache' },
            data: params,
            success: function (data) {
                var code = data.ResultCode;
                if(code != '0'){
                    alert(data.msg);
                    return;
                }
                var portalUrl = data.Data.portalUrl;
                _iframeState.url = portalUrl + '?' + params;
                _this.setState(_iframeState);
            },
            error: function (xhr, textStatus) {
                alert(xhr.status + ' : ' + xhr.readyState + ' : ' + textStatus);
                window.location.href = url;
            }
        });
    }
     */
    /**
     * 制作页面时返回React对象
     */
    function render() {
        return ReactDOM.render(<IFrameComponent />, document.getElementById(divId));
    }

    /**
     * 暴露render方法
     */
    return {
        render: render
    }
};
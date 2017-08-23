
/** ---------------------------------------  中间内容区域 开始  ----------------------------------------------------------------------------*/

var _Entity_ = function (state, divId) {

    var merchant_url = '/mersrv/ms/merchant/';

    var _state = state || {
            merchant_name:'',
            merchant_msg:'',
            merchant_address:'',
            merchant_telephone:'',
            merchant_opentime:'',
            merchant_closetime:''
        };

    var EntityReact = React.createClass({
        getInitialState: function () {
            // console.log(_state);
            return _state;
        },
        componentDidMount: function(){
            $("[data-toggle='popover']").popover({
                trigger: 'hover',
                container: 'body'
            });


            //上传至正式环境测试时显示如下代码
            if(_DEV_ID == '{@devId@}') {
                return;
            }

            var _this = this;
            $.ajax({
                type:'GET',
                url:merchant_url + _CUSTOMER_ID,
                data:{},
                success: function (data) {
                    // console.log(data);
                    if(data.code == 0) {
                        var temp = data.data.merchant;
                        _state.merchant_name = temp.merchantName;
                        _state.merchant_msg = temp.remark;
                        _state.merchant_address = temp.address;
                        _state.merchant_telephone = temp.telephone;
                        _state.merchant_opentime = temp.openTime;
                        _state.merchant_closetime = temp.closeTime;
                        _this.setAuthState();
                    }else {

                    }
                },
                error: function () {

                }
            });

        },
        //更新组件状态
        setAuthState: function () {
            this.setState(_state);
            // console.log(_state);
        },
        disappearClick: function (eventType, event) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            // alert('testClick');
            var target = $(event.target);
            if (!target.hasClass('popover') //弹窗内部点击不关闭
                && target.parent('.popover-content').length === 0
                && target.parent('.popover-title').length === 0
                && target.parent('.popover').length === 0
                && target.data("toggle") !== "popover"
                && target.attr("id") !== "location"
                && target.attr("id") !== "telephone"
                && target.attr("id") !== "time"){
                //弹窗触发列不关闭，否则显示后隐藏
                // alert('hide');
                // target.attr("id").popover('hide');
            }
        },
        render: function () {
            return (
                <div className="_Entity_">
                    <div className="container" onClick={this.disappearClick.bind(this, 'click')} onTouchStart={this.disappearClick.bind(this, 'touch')}>
                        <div className="merchant_name">{_state.merchant_name ? _state.merchant_name : '暂无'}</div>
                        <div className="merchant_info">{_state.merchant_msg ? _state.merchant_msg : '暂无'}</div>
                        <div className="merchant_iconlist">
                            <div className="location" id="location" data-toggle="popover" data-placement="top" title="" data-content={_state.merchant_address ? _state.merchant_address : '暂无'}></div>
                            <div className="telephone" id="telephone" data-toggle="popover" data-placement="top" title="" data-content={_state.merchant_telephone ? _state.merchant_telephone : '暂无'}></div>
                            <div className="time" id="time" data-toggle="popover" data-placement="top" title="" data-content={_state.merchant_opentime || _state.merchant_closetime ? _state.merchant_opentime + '~' + _state.merchant_closetime : '暂无'}></div>
                        </div>
                    </div>
                </div>
            );
        }
    });

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

/** ---------------------------------------  中间内容区域 结束  ----------------------------------------------------------------------------*/

/** ---------------------------------------  右侧配置区域 开始  ----------------------------------------------------------------------------*/


var _Setting_ = function (entity, divId) {
    var setting = null;

    var _state = entity.state || {

        };

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var SettingReact = React.createClass({
        getInitialState: function() {

            return _state;
        },
        componentDidMount: function () {

        },
        //保存按钮点击事件
        handleSaveClick: function(){

        },
        render: function () {
            return (
                <div className="container">
                <form className="form-horizontal">
                <div className="form-group control-label">&emsp;此组件无需进行设置（商户信息）</div>
            </form>
            </div>
            );
        }
    });

    function render() {
        setting = ReactDOM.render(<SettingReact />, document.getElementById(divId));
        return setting;
    }

    return {
        setting: setting,
        setStates: setStates,
        render: render
    }
};

/** ---------------------------------------  右侧配置区域 结束  ----------------------------------------------------------------------------*/

/**
 * DOM 渲染
 */
var entity = _Entity_('', 'entity').render();
var setting = _Setting_(entity, 'setting').render();





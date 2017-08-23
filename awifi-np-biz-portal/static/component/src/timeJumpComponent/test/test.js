
/**
 * 文本组件
 * @param state对象数据
 * @param divId Div唯一标识
 * @returns {{render: render}}
 * @Auther: zhuxh
 * @Date: 2015-12-29
 */

var _Entity_ = function (state, divId) {

    var _state = state || {
            style: 'st1', //样式
            mode: 'btn', // btn按钮  time倒计时
            btnText: '下一页',
            timer:'5'
        };

    var EntityReact = React.createClass({
        getInitialState: function () {
            return _state;
        },

        componentDidMount: function () {
            var t = null;
            var count = _state.timer;
            if (_DEV_ID != '{@devId@}') {
                if (_state.mode == 'btn') {
                    // 按钮
                    $('.open-next-page a').click(function () {
                        redirect();
                    });
                } else {
                    // 倒计时
                    var open_next_page = $('.open-next-page-text span');
                    open_next_page.text('倒计时... '+count+'秒');
                    t = setInterval(function () {
                        count--;
                        if (count == 0) {
                            clearInterval(t);
                            redirect();
                        } else {
                            open_next_page.text('倒计时... '+count+'秒');
                        }
                    }, 1000);
                }
            }
        },

        render: function () {
            return (
                <div className={'_Entity_ ' + this.state.style}>
                    {this.state.mode == 'btn' ?
                        (<div className="open-next-page">
                            <a href="javascript:;" title={this.state.btnText}>{this.state.btnText}</a>
                        </div>) :
                        (<div className="open-next-page-text">
                            <span>{this.state.btnText}</span>
                        </div>)
                    }
                </div>
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


var _Setting_ = function (entity, divId) {
    var setting = null;

    var _state = entity.state || {
            style: 'st1', //样式
            mode: 'btn', // btn按钮  time倒计时
            btnText: '下一页',
            timer:'5'
        };

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var SettingReact = React.createClass({
        getInitialState: function() {
            return _state;
        },

        handleBtnClick: function(st) {
            setStates({style: st});
        },

        eventSave: function () {
            var mode = $('input[name="mode"]:checked').val(),
                timer = $('#timer').val(),
                btnText = $('#btnText').val(),
                style = $('input[name="btncolor"]:checked').val();

            if (mode == 'btn') {
                // 按钮文字不能为空
                var verify = awifiUtils.verify_field('btnText', /^\S{1,10}$/gm, '按钮文字不能为空', '按钮文字非空且10字以内');
                if (verify) {
                    return ;
                }
            }

            if (mode == 'time') {
                btnText = '倒计时... '+timer+'秒';
            }
            _state.btnText = btnText;
            _state.mode = mode;
            _state.timer = timer;
            _state.style = style;

            setStates(_state);
        },

        render: function () {
            return (
                <div className="_Setting_ container">
                    <form className="form-horizontal">
                        <h2 className="form-title">请选择跳转到下一页的方式</h2>

                        <div className="form-group">
                            <div className="col-sm-9">
                                <label className="radio-inline"><input type="radio" name="mode" value="btn" defaultChecked={this.state.mode == 'btn'} />按钮</label>
                            </div>
                        </div>

                        <div className="form-group">
                            <label className="col-sm-2 control-label">按钮内文字：</label>
                            <div className="col-sm-9">
                                <input type="text" className="form-control" id="btnText" placeholder="请输入按钮内文字" defaultValue={this.state.btnText} />
                            </div>
                        </div>
                        <div id="styles" className="form-group">
                            <label className="col-sm-2 control-label">按钮颜色配置：</label>
                            <div className="col-sm-9">
                                <label className="radio-inline" onClick={ this.handleBtnClick.bind(this, 'st1') }>
                                    <input type="radio" name="btncolor" value="st1" defaultChecked={ this.state.style == 'st1' }/>
                                    <div className="st1">按 钮</div>
                                </label>
                                <label className="radio-inline" onClick={ this.handleBtnClick.bind(this, 'st2') }>
                                    <input type="radio" name="btncolor" value="st2" defaultChecked={ this.state.style == 'st2' }/>
                                    <div className="st2">按 钮</div>
                                </label>
                                <label className="radio-inline" onClick={ this.handleBtnClick.bind(this, 'st3') }>
                                    <input type="radio" name="btncolor" value="st3" defaultChecked={ this.state.style == 'st3' }/>
                                    <div className="st3">按 钮</div>
                                </label>
                            </div>
                            <div className="col-sm-9">
                                <label className="radio-inline" onClick={ this.handleBtnClick.bind(this, 'st4') }>
                                    <input type="radio" name="btncolor" value="st4" defaultChecked={ this.state.style == 'st4' }/>
                                    <div className="st4">按 钮</div>
                                </label>
                                <label className="radio-inline" onClick={ this.handleBtnClick.bind(this, 'st5') }>
                                    <input type="radio" name="btncolor" value="st5" defaultChecked={ this.state.style == 'st5' }/>
                                    <div className="st5">按 钮</div>
                                </label>
                                <label className="radio-inline" onClick={ this.handleBtnClick.bind(this, 'st6') }>
                                    <input type="radio" name="btncolor" value="st6" defaultChecked={ this.state.style == 'st6' }/>
                                    <div className="st6">按 钮</div>
                                </label>
                            </div>
                            <div className="col-sm-9">
                                <label className="radio-inline" onClick={ this.handleBtnClick.bind(this, 'st7') }>
                                    <input type="radio" name="btncolor" value="st7" defaultChecked={ this.state.style == 'st7' }/>
                                    <div className="st7">按 钮</div>
                                </label>
                                <label className="radio-inline" onClick={ this.handleBtnClick.bind(this, 'st8') }>
                                    <input type="radio" name="btncolor" value="st8" defaultChecked={ this.state.style == 'st8' }/>
                                    <div className="st8">按 钮</div>
                                </label>
                                <label className="radio-inline" onClick={ this.handleBtnClick.bind(this, 'st9') }>
                                    <input type="radio" name="btncolor" value="st9" defaultChecked={ this.state.style == 'st9' }/>
                                    <div className="st9">按 钮</div>
                                </label>
                            </div>
                        </div>
                        <div id="hr-line"></div>
                        <div className="form-group">
                            <div className="col-sm-9">
                                <label className="radio-inline"><input type="radio" name="mode" value="time" defaultChecked={this.state.mode == 'time'} />倒计时</label>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-sm-2 control-label">定时跳转下一页时间设定：</label>
                            <div className="col-sm-9">
                                <select id="timer" defaultValue={this.state.timer}>
                                    <option value="3">3秒</option>
                                    <option value="5">5秒</option>
                                    <option value="10">10秒</option>
                                    <option value="15">15秒</option>
                                </select>
                            </div>
                        </div>

                        <div className="form-group form-foot">
                            <button id="save" type="button" className="btn btn-danger btn-block" onClick={this.eventSave}>保&emsp;存</button>
                        </div>
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

var entity = _Entity_('', 'entity').render();

var setting = _Setting_(entity, 'setting').render();
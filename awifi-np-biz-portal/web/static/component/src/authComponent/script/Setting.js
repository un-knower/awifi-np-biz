/**
 * 认证组件--设置
 * @author 许小满
 * @date 2016年01月07日
 */
var _Setting_ = function (entity, divId) {
    var setting = null;

    function setStates(state) {
        entity.setState(state);
        setting.setState(state);
    }

    var _state = entity.state || { };

    var SettingReact = React.createClass({
        getInitialState: function() {
            return _state;
        },
        //组件渲染完成
        componentDidMount: function () {
            $("#titleFontColor").colorpicker({
                transparentColor: true
            });
        },
        //是否含tab点击事件处理 -- 免费上网
        handleFreeTitleChange: function(e){
            _state.message.freeButtonDsp = e.target.value;
            this.setState(_state);
        },
        //是否含tab点击事件处理 -- 手机号认证
        handleMobileTitleChange: function(e){
            _state.message.mobileTitle = e.target.value;
            this.setState(_state);
        },
        //是否含tab点击事件处理 -- 用户名认证
        handleUserPassTitleChange: function(e){
            _state.message.userPassTitle = e.target.value;
            _state.message.inputUserNamePlaceholder = '请输入' + e.target.value;
            this.setState(_state);
        },
        //是否含tab点击时间处理 -- 语音认证
        handleVoiceTitleChange: function(e){
            _state.message.voiceTitle = e.target.value;
            this.setState(_state);
        },
        //checkbox点击事件处理
        handleCheckboxClick: function(type, e){
            switch(type){
                /* 手机号认证相关配置项 */
                case 'hasMobile': _state.hasMobile = e.target.checked; break;//手机-tab
                case 'mobileLoginBtnSty': _state.mobileLoginBtnSty = e.target.checked ? '' : 'hide'; break;//手机号-登录按钮-显示
                case 'hasMsgSend': _state.hasMsgSend = e.target.checked; break;//手机号-短信回发
                /* 静态用户名认证相关配置项 */
                case 'hasUserPass': _state.hasUserPass = e.target.checked; break;//静态用户名tab
                case 'hasModefiyPassword': _state.hasModefiyPassword = e.target.checked; break;//静态用户名-修改密码
                case 'hasForgetPassword': _state.hasForgetPassword = e.target.checked; break;//静态用户名-忘记密码
                /* 语音认证相关配置项 */
                case 'hasVoice': _state.hasVoice = e.target.checked; break;//静态用户名-忘记密码
                /* 免费上网相关配置项 */
                case 'hasFree': _state.hasFree = e.target.checked; break;//免费上网
                case 'hasAutoAuth': _state.hasAutoAuth = e.target.checked; break;//自动登录上网
                /* 按功能相关配置项 */
                case 'openBlacklist': _state.openBlacklist = e.target.checked; break;//开启黑名单
                case 'openMessage': _state.openMessage = e.target.checked; break;//开启中英文
            }
            this.setState(_state);
        },
        //自动登录上网-倒计时秒数-变更事件处理
        handAutoAuthSecondChange: function(e){
            _state.autoAuthSecond = e.target.value;
            this.setState(_state);
        },
        //按钮颜色点击事件处理
        handleBtnClick: function(st){
            _state.style = st;
            this.setState(_state);
        },
        //保存按钮点击事件处理
        handleSaveClick: function(){
            //1.手机号认证、静态用户名认证 不能同时为空
            if(!_state.hasMobile && !_state.hasUserPass && !_state.hasVoice){
                alert('请至少选择一项除[免费上网]外的认证！');
                return;
            }
            //1.如果选中免费上网，则内容不能为空
            if(_state.hasFree && _state.message.freeButtonDsp === ''){
                alert('免费上网按钮内容不允许为空！');
                return;
            }
            //2.如果选中手机号认证，则内容不能为空
            if(_state.hasMobile && _state.message.mobileTitle === ''){
                alert('手机号认证标题不允许为空！');
                return;
            }
            //4.如果选中静态用户名认证，则内容不能为空
            if(_state.hasUserPass && _state.message.userPassTitle === ''){
                alert('用户名认证标题不允许为空！');
                return;
            }
            //6.如果选中语音认证，则内容不能为空
            if(_state.hasVoice && _state.message.voiceTitle === ''){
                alert('语音认证标题不允许为空！');
                return;
            }
            //5.设置tabNum数量
            var $authType = $("input[name='s_authType'][value!='free']:checked");
            var authType = $authType.val();
            _state.active = authType;
            _state.display = authType;
            _state.tabNum = $authType.size();

            //字体颜色
            _state.titleFontColor = $('#titleFontColor').val();
            entity.setState(_state);
        },
        render: function() {
            return (
                <div className="_Setting_">
                    <div className="form-group authModelConfig">
                        <label className="col-sm-12 control-label">认证方式配置：</label>
                        <div className="col-sm-12 mobileArea">
                            <input type="checkbox" name="s_authType" value="mobile" defaultChecked={ _state.hasMobile } onClick={ this.handleCheckboxClick.bind(this, 'hasMobile') }/>&nbsp;&nbsp;&nbsp;
                            <input type="text" className="form-control" value={ _state.message.mobileTitle } onChange={ this.handleMobileTitleChange } placeholder="请输入手机号认证标题"/>
                            {/*屏蔽短信回发
                             <div>
                             <label className="authTitle">短信回发</label>&nbsp;&nbsp;&nbsp;
                             <label className="authTitle3">
                             <input type="checkbox" defaultChecked={ _state.hasMsgSend } onClick={ this.handleCheckboxClick.bind(this, 'hasMsgSend') } />&nbsp;开启（勾选开启）
                             </label>
                             </div>
                             */}
                        </div>
                        <div className="col-sm-12 typeArea">
                            <input type="checkbox" name="s_authType" value="userPass" defaultChecked={ _state.hasUserPass } onClick={ this.handleCheckboxClick.bind(this, 'hasUserPass') }/>&nbsp;&nbsp;&nbsp;
                            <input type="text" className="form-control" value={ _state.message.userPassTitle } onChange={ this.handleUserPassTitleChange } placeholder="请输入用户名认证标题"/>&nbsp;&nbsp;&nbsp;

                            <label className="authTitle">
                                <input type="checkbox" defaultChecked={ _state.hasModefiyPassword } onClick={ this.handleCheckboxClick.bind(this, 'hasModefiyPassword') }/>&nbsp;{ _state.message.modifyPassword }
                            </label>&nbsp;&nbsp;

                            <label className="authTitle">
                                <input type="checkbox" defaultChecked={ _state.hasForgetPassword } onClick={ this.handleCheckboxClick.bind(this, 'hasForgetPassword') }/>&nbsp;{ _state.message.forgetPassword }
                            </label>
                        </div>
                        <div className="col-sm-12 typeArea">
                            <input type="checkbox" name="s_authType" value="voice" defaultChecked={ _state.hasVoice } onClick={ this.handleCheckboxClick.bind(this, 'hasVoice') }/>&nbsp;&nbsp;&nbsp;
                            <input type="text" className="form-control" value={ _state.message.voiceTitle } onChange={ this.handleVoiceTitleChange } placeholder="请输入语音认证标题"/>
                        </div>
                        <div className="col-sm-12 typeArea">
                            <input type="checkbox" name="s_authType" value="free" defaultChecked={ _state.hasFree } onClick={ this.handleCheckboxClick.bind(this, 'hasFree') }/>&nbsp;&nbsp;&nbsp;
                            <input type="text" className="form-control" value={ _state.message.freeButtonDsp } onChange={ this.handleFreeTitleChange } placeholder="请输入免费上网按钮内容"/>
                            <label className="authTitle">*第二次上网认证方式</label>
                            <div className="autoLogin">
                                <label className="authTitle">自动登录上网</label>
                                <label className="authTitle"><input type="checkbox" value="autoAuth" defaultChecked={ _state.hasAutoAuth } onClick={ this.handleCheckboxClick.bind(this, 'hasAutoAuth') }/>&nbsp;开启</label>
                                <span className={ _state.hasAutoAuth ? '' : 'hide' } >
                                    <label className="authTitle0">，倒计时：</label>
                                    <select defaultValue={ _state.autoAuthSecond } onChange={ this.handAutoAuthSecondChange } >
                                        <option value="3">3</option>
                                        <option value="5">5</option>
                                        <option value="10">10</option>
                                    </select>
                                    <label className="authTitle">秒</label>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div className="form-group featuresConfig">
                        <label className="col-sm-12 control-label">功能配置：</label>
                        <div className="col-sm-12 typeArea">
                            <input type="checkbox" id="openBlacklistBox" defaultChecked={ _state.openBlacklist } onClick={ this.handleCheckboxClick.bind(this, 'openBlacklist') }/>
                            <label className="authTitle" htmlFor="openBlacklistBox">开启黑名单（勾选开启）</label>
                        </div>
                        <div className="col-sm-12 typeArea">
                            <input type="checkbox" id="openMessageBox" defaultChecked={ _state.openMessage } onClick={ this.handleCheckboxClick.bind(this, 'openMessage') }/>
                            <label className="authTitle" htmlFor="openMessageBox">开启中英文（勾选开启）</label>
                        </div>
                        <div className="col-sm-12 typeArea title-font-color">
                            <label className="authTitle" >标题字体颜色：</label>
                            <div className="col-sm-10 inline-block">
                                <input type="text" id="titleFontColor" className="form-control" defaultValue={ _state.titleFontColor } />
                            </div>
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="col-sm-2">按钮颜色配置：</label>

                        <div className="col-sm-9">
                            <label className="radio-inline" onClick={ this.handleBtnClick.bind(this,'st1') }>
                                <input type="radio" name="btncolor" value="st1" defaultChecked={ this.state.style == 'st1' }/>
                                <div className="st1">按 钮</div>
                            </label>
                            <label className="radio-inline" onClick={ this.handleBtnClick.bind(this,'st2') }>
                                <input type="radio" name="btncolor" value="st2" defaultChecked={ this.state.style == 'st2' }/>
                                <div className="st2">按 钮</div>
                            </label>
                            <label className="radio-inline" onClick={ this.handleBtnClick.bind(this,'st3') }>
                                <input type="radio" name="btncolor" value="st3" defaultChecked={ this.state.style == 'st3' }/>
                                <div className="st3">按 钮</div>
                            </label>
                        </div>
                        <div className="col-sm-9">
                            <label className="radio-inline" onClick={ this.handleBtnClick.bind(this,'st4') }>
                                <input type="radio" name="btncolor" value="st4" defaultChecked={ this.state.style == 'st4' }/>
                                <div className="st4">按 钮</div>
                            </label>
                            <label className="radio-inline" onClick={ this.handleBtnClick.bind(this,'st5') }>
                                <input type="radio" name="btncolor" value="st5" defaultChecked={ this.state.style == 'st5' }/>
                                <div className="st5">按 钮</div>
                            </label>
                            <label className="radio-inline" onClick={ this.handleBtnClick.bind(this,'st6') }>
                                <input type="radio" name="btncolor" value="st6" defaultChecked={ this.state.style == 'st6' }/>
                                <div className="st6">按 钮</div>
                            </label>
                        </div>
                        <div className="col-sm-9">
                            <label className="radio-inline" onClick={ this.handleBtnClick.bind(this,'st7') }>
                                <input type="radio" name="btncolor" value="st7" defaultChecked={ this.state.style == 'st7' }/>
                                <div className="st7">按 钮</div>
                            </label>
                            <label className="radio-inline" onClick={ this.handleBtnClick.bind(this,'st8') }>
                                <input type="radio" name="btncolor" value="st8" defaultChecked={ this.state.style == 'st8' }/>
                                <div className="st8">按 钮</div>
                            </label>
                            <label className="radio-inline" onClick={ this.handleBtnClick.bind(this,'st9') }>
                                <input type="radio" name="btncolor" value="st9" defaultChecked={ this.state.style == 'st9' }/>
                                <div className="st9">按 钮</div>
                            </label>
                        </div>
                    </div>
                    <div className="form-group form-save">
                        <button type="button" onClick={ this.handleSaveClick } className="btn btn-danger btn-block" >保&emsp;存</button>
                    </div>
                </div>
            );
        }
    });

    function render() {
        setting = ReactDOM.render(<SettingReact/>, document.getElementById(divId));
        return setting;
    }

    return {
        setting: setting,
        setStates: setStates,
        render: render
    }
};
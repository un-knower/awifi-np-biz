var _Setting_ = function (entity, divId) {
    var setting = null;

    function setStates(state) {
        entity.setState(state);
        setting.setState(state);
    }

    var _state = entity.state || {
            style: 'st1',//样式
            active: 'mobile',//选中：mobile 手机号、userPass 静态用户名
            display: 'mobile',//显示：free 免费上网、mobile 手机号、userPass 静态用户名
            tabNum: 2,//tab数量
            //免费上网
            hasFree: true,//是否包含功能点：免费上网
            freeButtonDsp: '点击免费上网',//按钮显示值
            //手机号
            hasMobile: true,//是否包含功能点：手机号认证
            mobileTitle: '手机号认证',//tab标签显示值
            //用户名
            hasUserPass: true,//是否包含功能点：静态用户名认证
            userPassTitle: '静态用户名认证',//tab标签显示值
            /** 修改密码、忘记密码 新增参数 */
            userName: '',//用户名
            mobile: '',//手机号
            hideAuthDivStyle: '',//控制主组件是否隐藏显示
            hasModefiyPassword: true,//是否包含功能点：修改密码
            showModifyPasswordForSetNewPassword: false,//是否显示：修改密码--设置新密码组件
            hasForgetPassword: true,//是否包含功能点：忘记密码
            showForgetPasswordForCheckMobile: false,//是否显示：忘记密码--验证手机号组件
            showForgetPasswordForSetNewPassword: false,//是否显示：忘记密码--设置新密码组件
            showWelcome: false //是否显示欢迎组件
        };

    var SettingReact = React.createClass({
        getInitialState: function() {
            return _state;
        },
        //是否含tab点击事件处理 -- 免费上网
        handleHasFreeBoxClick: function(e){
            _state.hasFree = e.target.checked;
            this.setState(_state);
        },
        //是否含tab点击事件处理 -- 手机号认证
        handleHasMobileBoxClick: function(e){
            _state.hasMobile = e.target.checked;
            this.setState(_state);
        },
        //是否含tab点击事件处理 -- 用户名认证
        handleHasUserPassBoxClick: function(e){
            _state.hasUserPass = e.target.checked;
            this.setState(_state);
        },
        //是否含 用户名认证 -- 修改密码
        handleHasModefiyPasswordBoxClick: function(e){
            _state.hasModefiyPassword = e.target.checked;
            this.setState(_state);
        },
        //是否含 用户名认证 -- 忘记密码
        handleasForgetPasswordBoxClick: function(e){
            _state.hasForgetPassword = e.target.checked;
            this.setState(_state);
        },
        //是否含tab点击事件处理 -- 免费上网
        handleFreeTitleChange: function(e){
            _state.freeButtonDsp = e.target.value;
            this.setState(_state);
        },
        //是否含tab点击事件处理 -- 手机号认证
        handleMobileTitleChange: function(e){
            _state.mobileTitle = e.target.value;
            this.setState(_state);
        },
        //是否含tab点击事件处理 -- 用户名认证
        handleUserPassTitleChange: function(e){
            _state.userPassTitle = e.target.value;
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
            if(!_state.hasMobile && !_state.hasUserPass){
                alert('请至少选择一项除[免费上网]外的认证！');
                return;
            }
            //1.如果选中免费上网，则内容不能为空
            if(_state.hasFree && _state.freeButtonDsp === ''){
                alert('免费上网按钮内容不允许为空！');
                return;
            }
            //2.如果选中手机号认证，则内容不能为空
            if(_state.hasMobile && _state.mobileTitle === ''){
                alert('手机号认证标题不允许为空！');
                return;
            }
            //4.如果选中静态用户名认证，则内容不能为空
            if(_state.hasUserPass && _state.userPassTitle === ''){
                alert('用户名认证标题不允许为空！');
                return;
            }
            //5.设置tabNum数量
            var $authType = $("input[name='s_authType'][value!='free']:checked");
            var authType = $authType.val();
            _state.active = authType;
            _state.display = authType;
            _state.tabNum = $authType.size();

            entity.setState(_state);
        },
        render: function() {
            return (
                <div className="container">
                    <form className="form-horizontal">
                        <div id="authType" className="form-group">
                            <label className="col-sm-12 control-label">认证方式配置：</label>
                            <div className="col-sm-12 typeArea">
                                <input type="checkbox" name="s_authType" value="mobile" defaultChecked={ _state.hasMobile } onClick={ this.handleHasMobileBoxClick }/>&nbsp;&nbsp;&nbsp;
                                <input type="text" value={ _state.mobileTitle } onChange={ this.handleMobileTitleChange } placeholder="请输入手机号认证标题"/>
                            </div>
                            <div className="col-sm-12 typeArea">
                                <input type="checkbox" name="s_authType" value="userPass" defaultChecked={ _state.hasUserPass } onClick={ this.handleHasUserPassBoxClick }/>&nbsp;&nbsp;&nbsp;
                                <input type="text" value={ _state.userPassTitle } onChange={ this.handleUserPassTitleChange } placeholder="请输入用户名认证标题"/>&nbsp;&nbsp;&nbsp;

                                <input type="checkbox" id="hasModefiyPassword" defaultChecked={ _state.hasModefiyPassword } onClick={ this.handleHasModefiyPasswordBoxClick }/>
                                <label className="authTitle" htmlFor="hasModefiyPassword">修改密码</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                <input type="checkbox" id="hasForgetPassword" defaultChecked={ _state.hasForgetPassword } onClick={ this.handleasForgetPasswordBoxClick }/>
                                <label className="authTitle" htmlFor="hasForgetPassword">忘记密码</label>
                            </div>
                            <div className="col-sm-12 typeArea">
                                <input type="checkbox" name="s_authType" value="free" defaultChecked={ _state.hasFree } onClick={ this.handleHasFreeBoxClick }/>&nbsp;&nbsp;&nbsp;
                                <input type="text" value={ _state.freeButtonDsp } onChange={ this.handleFreeTitleChange } placeholder="请输入免费上网按钮内容"/>
                                <label className="authTitle">*第二次上网认证方式</label>
                            </div>
                        </div>
                        <div id="styles" className="form-group">
                            <label className="col-sm-2 control-label">按钮颜色配置：</label>

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
                        </div><br/><br/>
                        <div className="form-group">
                            <div className="col-sm-offset-2 col-sm-9">
                                <button type="button" className="btn btn-danger btn-sm" onClick={ this.handleSaveClick }>保&emsp;存</button>
                            </div>
                        </div>
                    </form>
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
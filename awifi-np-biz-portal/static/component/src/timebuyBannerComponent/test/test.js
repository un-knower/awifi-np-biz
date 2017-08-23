/**
 * 说明：
 *
 *
 *
 */

/** ---------------------------------------  中间内容区域 开始  ----------------------------------------------------------------------------*/

var _Entity_ = function (state, divId) {
    // var userinfo_url = '/timebuysrv/user/base/getUserInfo/'; //查询用户基本信息接口
    var put_userinfo_url = '/timebuysrv/user/base/'; //根据用户id更新用户基本信息接口

    var _state = state || {
            isopen:false,
            setting_user:true,
            setting_user_name:'个人资料',
            faceinfo:'',
            userNick:'',
            sex:'',
            birthday:'',
            address:'',
            merchantname:'',
            userid:'',
            telephone:'',
        };

    var EntityReact = React.createClass({
        getInitialState: function () {

            return _state;
        },
        componentDidMount: function(){

            //上传至正式环境测试时显示如下代码
            if(_DEV_ID == '{@devId@}') {
                return;
            };

            _state.merchantname = _CUSTOMER_NAME;
            this.setAuthState();

        },
        //更新组件状态
        setAuthState: function () {
            this.setState(_state);
            // console.log(_state);
        },
        userCenterChange: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }

            if(!this.state.isopen) {
                this.updateInfo();
            }
            else {
                _state.isopen = !_state.isopen;
                this.setState(_state);
                $("body").css("overflow", _state.isopen ? "hidden" : "auto");
                // console.log(_state);
            }
        },
        updateInfo: function () {
            getUserInfo(this.getUserInfoSuccess);
        },
        getUserInfoSuccess: function () {
            _state.isopen = !_state.isopen;
            this.setState(_state);
            $("body").css("overflow", _state.isopen ? "hidden" : "auto");
            // console.log(_state);
        },
        render: function () {

            return (
                <div className="_Entity_">
                    <div className="banner">
                        <div className="banner_wifi">
                        </div>
                        <div className="banner_merchant">
                            <span className="banner_title">{_state.merchantname ? _state.merchantname : 'aWiFi'}</span>
                        </div>
                        <div className="banner_user" style={{display:this.state.setting_user ? 'block' : 'none'}} id="userCenterDiv">
                            <div className="banner_icon_1" onClick={this.userCenterChange.bind(this, 'click')} onTouchStart={this.userCenterChange.bind(this, 'touch')}></div>
                        </div>
                    </div>
                    {this.state.isopen ? <UserCenterComponent setAuthState={this.setAuthState}/> : ''}
                </div>
            );
        }
    });

    /**
     * 个人资料组件
     * @type {*}
     */
    var UserCenterComponent = React.createClass({
        option:{

        },
        getInitialState: function () {
            this.maskWidth = $(".awifi-container").width();
            return this.option;
        },
        componentDidMount: function () {
            var _this = this;
            var nowImg = null;
            $(".img-upload").click(function() {
                nowImg = this;
                $(this).parent().find("#inputfile").trigger("click");
            });
            $("#inputfile").change(function () {
                var formData = new FormData();

                var temp_file = $('#inputfile')[0].files[0];
                // console.log(temp_file);

                formData.append('file', temp_file);
                $.ajax({
                    type:'POST',
                    url:'/timebuysrv/image/submit',
                    data:formData,
                    cache: false,
                    contentType: false,    //不可缺
                    processData: false,    //不可缺
                    success:function (data) {
                        // console.log(data);
                        if(data.code == '0') {
                            var img_src = "/" + data.data;
                            _state.faceinfo = img_src;
                            _this.props.setAuthState();
                        }else {
                            if(data.msg) {
                                alert(data.msg);
                            }else {
                                alert('个人头像上传失败，请稍后再试');
                            }

                        }
                    },
                    error:function (data) {
                        alert('个人头像上传失败，请稍后再试');
                    }
                });
            });
        },
        handleSaveData: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }

            var temp_name = $('#name').val();
            var temp_sex = $("input[name='sex']:checked").val();
            var temp_birthday = $("#birthday").val();
            var temp_address = $('#address').val();

            if(!temp_name || temp_name == '' || !temp_birthday || temp_birthday == '' || !temp_address || temp_address == '' || !temp_sex || temp_sex == '') {
                alert('信息不能为空');
                return;
            }

            // console.log('name:' + temp_name + '==sex:' + temp_sex + '==birthday:' + temp_birthday + '==address:' + temp_address);
            putUserInfo(temp_name, temp_sex, temp_birthday, temp_address, this.putUserInfoSuccess);
        },
        putUserInfoSuccess: function () {
            $("body").css("overflow", _state.isopen ? "hidden" : "auto");
            this.props.setAuthState();
        },
        closeUserInfo: function(eventType){
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }

            _state.isopen = false;
            this.putUserInfoSuccess();
        },
        render: function () {
            var temp_face = '';
            if(_state.faceinfo == null || !_state.faceinfo || _state.faceinfo == undefined) {
                temp_face = <div className="real_img"></div>;
            }else {
                temp_face = <img src={_state.faceinfo} />;
            }
            return(
                <div className = "m-Mask" style={{width: this.maskWidth}}>
                    <div className="detail">
                        <div className="detail-title">
                            <div className="title-font">{_state.setting_user_name}</div>
                            <a className="closeBtn" onClick={this.closeUserInfo.bind(this, 'click')} onTouchStart={this.closeUserInfo.bind(this, 'touch')}></a>
                        </div>
                        <div className="detail-portrait">
                            <div className="portrait-content">
                                {temp_face}
                                <div className="img-upload"></div>
                                <input type="file" style={{display:'none'}} id="inputfile" />
                            </div>
                        </div>
                        <div className="detail-sex">
                            <div className="content">
                                <div className="sex">
                                    <input type="radio" name="sex" value='0' defaultChecked={_state.sex == '0' ? "checked" : ''}/>男
                                </div>
                                <div className="sex">
                                    <input type="radio" name="sex" value='1' defaultChecked={_state.sex == '1' ? "checked" : ''}/>女
                                </div>
                            </div>
                        </div>
                        <div className="detail-list">
                            <div className="detail-name">
                                <div className="title">昵称</div>
                                <div className="input-panel">
                                    <input className="name-input" type="text" placeholder="最多六个字符" maxLength="8" id="name" defaultValue={_state.userNick}/>
                                </div>
                            </div>
                        </div>
                        <div className="detail-list">
                            <div className="detail-name">
                                <div className="title">生日</div>
                                <div className="input-panel">
                                    <input className="name-input" type="date" id="birthday" defaultValue={_state.birthday}/>
                                </div>
                            </div>
                        </div>
                        <div className="detail-list">
                            <div className="detail-name">
                                <div className="title">地址</div>
                                <div className="input-panel">
                                    <input className="name-input" type="text" placeholder="请输入地址" maxLength="50" id="address" defaultValue={_state.address}/>
                                </div>
                            </div>
                        </div>
                        <div className="detail-list" style={{textAlign:'center'}}>
                            <button className="save-btn" onClick={this.handleSaveData.bind(this, 'click')} onTouchStart={this.handleSaveData.bind(this, 'touch')}>保存</button>
                        </div>
                    </div>
                </div>
            )
        }
    });

    /**
     * 获取用户基本信息接口
     * @param func
     */
    function getUserInfo(func) {

        if(_USER_ID == null || _USER_ID == '') {
            alert('请先登录');
            return;
        }

        $.ajax({
            type:'GET',
            url:put_userinfo_url + _USER_ID,
            data:{},
            success: function (data) {
                // console.log(data);
                if(data.code == '0') {
                    _state.faceinfo = data.data.faceInfo;
                    _state.userNick = data.data.userNick;
                    _state.sex = data.data.sex;
                    _state.birthday = data.data.birthdayStr;
                    _state.address = data.data.address;
                    _state.userid = data.data.id;
                    _state.telphone = data.data.telphone;

                    func();
                }else {
                    alert(data.msg);
                }
            },
            error: function () {

            }
        })
    };

    /**
     * 更新用户基本信息接口
     * @param name
     * @param sex
     * @param birthday
     * @param address
     * @param func
     */
    function putUserInfo(name, sex, birthday, address, func) {
        $.ajax({
            type:'PUT',
            url:put_userinfo_url + _state.userid,
            data:{userId:_state.userid, faceInfo:_state.faceinfo, userNick:name, sex:sex, birthday:birthday, address:address, telphone:_state.telephone},
            success: function (data) {
                // console.log(data);
                if(data.code == '0') {
                    alert('保存个人资料成功');
                    _state.userNick = name;
                    _state.sex = sex;
                    _state.birthday = birthday;
                    _state.address = address;
                    _state.isopen = false;
                    func();
                }else {
                    alert(data.msg);
                }
            },
            error: function () {

            }
        })
    }

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
     * @returns {}
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
            var $userChecked = $('#user').is(':checked');
            // alert($userChecked);
            if($userChecked) {
                _state.setting_user = true;
            }else {
                _state.setting_user = false;
            }
            _state.setting_user_name = $('#user_name').val();
            _state.isopen = false;

            // console.log(_state);
            entity.setState(_state);
        },
        statusChange: function (event) {
            var checked = event.target.checked;
            if(checked) {
                $('#user_name').attr('disabled', false);
                $('#user_name').css('color', '#333');
            }else {
                $('#user_name').attr('disabled', true);
                $('#user_name').css('color', '#DDDDDD');
            }
        },
        render: function () {
            return (
                <div className="container _Setting_">
                    <form className="form-horizontal">
                        <div className="form-group form-group-bottom">
                            <div className="col-sm-9">
                                <input type="checkbox" defaultChecked={_state.setting_user ? "checked" : ''} id="user" onChange={this.statusChange}/>&nbsp;&nbsp;按钮名称：
                                <input type="text" defaultValue="个人资料" style={{width:'85px', color:_state.setting_user ? '#333' : '#DDDDDD'}} disabled={_state.setting_user ? false : true} id="user_name"/>（允许用户填写个人资料）
                            </div>
                        </div>
                        <div className="form-group form-save">
                            <button id="save" type="button" className="btn btn-danger btn-block" onClick={this.handleSaveClick}>保&emsp;存</button>
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

/** ---------------------------------------  右侧配置区域 结束  ----------------------------------------------------------------------------*/

/**
 * DOM 渲染
 */
var entity = _Entity_('', 'entity').render();
var setting = _Setting_(entity, 'setting').render();





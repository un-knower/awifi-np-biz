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
            // console.log(_state);
            return _state;
        },
        componentDidMount: function () {

        },
        //保存按钮点击事件
        handleSaveClick: function(){
            //一键登录
            var $fastLoginChecked = $('#fastLogin').is(':checked');
            if($fastLoginChecked) {
                _state.setting_fastlogin = true;
            }else {
                _state.setting_fastlogin = false;
            }
            _state.setting_fastlogin_name = $('#fastLogin_name').val();

            //时长购买
            var $buyTimeChecked = $('#buyTime').is(':checked');
            if($buyTimeChecked) {
                _state.setting_buytime = true;
            }else {
                _state.setting_buytime = false;
            }
            _state.setting_buytime_name = $('#buyTime_name').val();

            //领取免费礼包
            var $freePkgChecked = $('#freePkg').is(':checked');
            if($freePkgChecked) {
                _state.setting_freepkg = true;
            }else {
                _state.setting_freepkg = false;
            }
            _state.setting_freepkg_name = $('#freePkg_name').val();

            // console.log(_state);

            setStates(_state);
        },
        buyTimeChange:function (event) {
            var checked = event.target.checked;
            // alert(checked);

            if(checked) {
                _state.setting_buytime = true;

                $('#freePkg').attr('disabled', false);
                $('#freePkg_name').attr('disabled', false);
                $('#freePkg_name').attr('style', 'color:#333');
            }else {
                _state.setting_buytime = false;

                _state.setting_freepkg = false;
                $('#freePkg').attr('checked', false);
                $('#freePkg').attr('disabled', true);
                $('#freePkg_name').attr('disabled', true);
                $('#freePkg_name').attr('style', 'color:#DDDDDD');
            }
        },
        render: function () {
            return (
                <div className="container _Setting_">
                    <form className="form-horizontal">
                        <div className="form-group form-group-bottom">
                            <div className="col-sm-9">
                                <input type="checkbox" defaultChecked={_state.setting_fastlogin ? "checked" : ''} id="fastLogin"/>&nbsp;&nbsp;按钮名称：
                                <input type="text" defaultValue="一键登录" style={{width:'85px'}} id="fastLogin_name"/>(老用户可免验证码登录)
                            </div>
                        </div>
                        <div className="form-group form-group-bottom">
                            <div className="col-sm-9">
                                <input type="checkbox" defaultChecked={_state.setting_buytime ? "checked" : ''} id="buyTime" onChange={this.buyTimeChange}/>&nbsp;&nbsp;按钮名称：
                                <input type="text" defaultValue="时长购买" style={{width:'85px'}} id="buyTime_name"/>(企业买断园区，请关闭该按钮)
                            </div>
                        </div>
                        <div className="form-group form-group-bottom">
                            <div className="col-sm-9">
                                <div className="radio-inline">
                                </div>
                                <div className="radio-inline">
                                    <input type="checkbox" defaultChecked={_state.setting_freepkg ? "checked" : ''}  id='freePkg'/>&nbsp;&nbsp;按钮名称：
                                    <input type="text" defaultValue="领取免费礼包" style={{width:'85px'}} id='freePkg_name'/>
                                </div>
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
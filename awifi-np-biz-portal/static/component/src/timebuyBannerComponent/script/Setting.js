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
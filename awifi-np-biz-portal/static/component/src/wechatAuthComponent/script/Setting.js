
var _Setting_ = function (entity, divId) {

    var setting = null;

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var _state = entity.state || {
            btnText: '微信认证上网', //按钮文字
            btnClass: 'st1' // 按钮样式
        };

    var WechatAuthSetting = React.createClass({
        getInitialState: function() {
            return _state;
        },
        render: function () {
            var state = this.state;
            var _btnClass = state.btnClass ? state.btnClass : 'st1';
            var _btnText = state.btnText ? state.btnText : '微信认证上网';
            return (
                <div className="container">
                    <form className="form-horizontal">
                        <div className="form-group">
                            <label for="content" className="col-sm-2 control-label">微信认证按钮文字：</label>
                            <div className="col-sm-9">
                                <input className="form-control" id="btntext" name="btntext" placeholder="请输入文本内容" defaultValue={_btnText} />
                            </div>
                        </div>
                        <div id="styles" className="form-group">
                            <label className="col-sm-2 control-label">按钮配色：</label>
                            <div className="col-sm-9">
                                <label className="radio-inline">
                                    <input type="radio" name="btncolor" value="st1" defaultChecked={_btnClass == 'st1'} />
                                    <div className="st1">按&ensp;钮</div>
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="btncolor" value="st2" defaultChecked={_btnClass == 'st2'} />
                                    <div className="st2">按&ensp;钮</div>
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="btncolor" value="st3" defaultChecked={_btnClass == 'st3'} />
                                    <div className="st3">按&ensp;钮</div>
                                </label>
                            </div>
                            <div className="col-sm-9">
                                <label className="radio-inline">
                                    <input type="radio" name="btncolor" value="st4" defaultChecked={_btnClass == 'st4'} />
                                    <div className="st4">按&ensp;钮</div>
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="btncolor" value="st5" defaultChecked={_btnClass == 'st5'} />
                                    <div className="st5">按&ensp;钮</div>
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="btncolor" value="st6" defaultChecked={_btnClass == 'st6'} />
                                    <div className="st6">按&ensp;钮</div>
                                </label>
                            </div>
                        </div>
                        <div className="form-group">
                            <div className="col-sm-offset-2 col-sm-9 tac">
                                <button id="save" type="button" className="btn btn-danger btn-sm" onClick={this.handlerSaveData}>保&emsp;存</button>
                            </div>
                        </div>
                    </form>
                </div>
            );
        },
        handlerSaveData: function () {
            var btntext = $('#btntext').val(),
                btncolor = $('input[name="btncolor"]:checked').val();
            _state.btnClass = btncolor;
            _state.btnText = btntext;
            setStates(_state);
        }
    });

    /**
     * 渲染DOM
     */
    function render() {
        setting = ReactDOM.render(<WechatAuthSetting />, document.getElementById(divId));
        return setting;
    }

    /**
     * 暴露对象属性及方法
     */
    return {
        setting: setting,
        setStates: setStates,
        render: render
    }
};

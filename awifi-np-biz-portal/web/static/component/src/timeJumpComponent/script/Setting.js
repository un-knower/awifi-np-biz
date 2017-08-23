
/**
 * 超链接组件
 * @param entity
 * @param divId
 * @returns {{setting: *, setStates: setStates, render: render}}
 * @private
 * @Auther: zhuxh
 * @Date: 2015-12-30
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

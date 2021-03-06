/**
 * Created by zhuxh on 15-12-14.
 */

var _Setting_ = function (entity, divId) {
    var setting = null;
    var _state = entity.state || {
            content: '请输入文本内容',
            textAlign: 'left',
            fontSize: '14',
            display: 'none',
            bgColor: '',
            fontColor: '#333',
            height: 'auto',
            opacity: '1'
        };

    function setStates(state) {
        entity.setState(state);
        setting.setState(state);
    }

    var SettingReact = React.createClass({
        getInitialState: function() {
            // 替换文本内容中所有换行符和空隔
            _state.content = _state.content.replace(/<br\/>/g, '\n');
            _state.content = _state.content.replace(/&nbsp;/g, ' ');

            return _state
        },

        componentDidMount: function () {
            $("#bgColor").colorpicker({
                transparentColor: false
            });
            $("#fontColor").colorpicker({
                transparentColor: false
            });
        },

        eventSave: function () {
            var fields = [
                {'id': 'fontColor', 'reg': '', 'empty': '请选择字体色'},
                {'id': 'content', default: '文本内容', default_msg: '请修改文本内容', 'reg': /^[\S\s]{1,500}$/gm, 'empty': '内容不能为空', 'fault': '内容非空且500字以内'}
            ];
            for (var i=0,len=fields.length; i<len; i++) {
                var item = fields[i];
                var verify = awifiUtils.verify_field(item.id, item.reg, item.empty, item.fault, item.default, item.default_msg);
                if (verify) {
                    return ;
                }
            }

            var height_val = $('#height').val();
            if (height_val && !/^\d+$/.test(height_val)) {
                awifiUtils.alert_tips('comp-setting', '内容高度请输入整数');
                return;
            } else {
                if (parseInt(height_val) < 24) {
                    awifiUtils.alert_tips('comp-setting', '内容高度不能小于24像素');
                    return;
                }
            }

            _state.bgColor = $('#bgColor').val();
            _state.fontColor = $('#fontColor').val();
            _state.content = $('#content').val();
            _state.textAlign = $('input[name="textalign"]:checked').val();
            _state.fontSize = $('input[name="fontsize"]:checked').val();
            _state.display = $('input[name="showstyle"]:checked').val();
            _state.opacity = $('input[name="opacity"]:checked').val();
            _state.height = $('#height').val();

            setStates(_state);
        },

        render: function() {
            return (
                <div className="container _Setting_">
                    <form className="form-horizontal">
                        <div className="form-group">
                            <label for="bgColor" className="col-sm-2 control-label">背景色</label>
                            <div className="col-sm-10 inline-block"><input type="text" className="form-control" id="bgColor" name="bgColor" defaultValue={this.state.bgColor} readOnly="readonly" placeholder="请选择颜色" /></div>
                        </div>
                        <div className="form-group">
                            <label for="fontColor" className="col-sm-2 control-label">字体色</label>
                            <div className="col-sm-10 inline-block"><input type="text" className="form-control" id="fontColor" name="fontColor" defaultValue={this.state.fontColor} readOnly="readonly" placeholder="请选择颜色" /></div>
                        </div>
                        <div className="form-group">
                            <label for="content" className="col-sm-2 control-label">文本内容</label>
                            <div className="col-sm-10 inline-block">
                                <textarea className="form-control" rows="3" id="content" placeholder="请输入文本内容" defaultValue={this.state.content}></textarea>
                                <p className="gray mt">内容限500字以内</p>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-sm-2 control-label">对齐样式</label>
                            <div className="col-sm-10 inline-block">
                                <label className="radio-inline">
                                    <input type="radio" name="textalign" value="left" defaultChecked={this.state.textAlign == 'left'} />左对齐
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="textalign" value="center" defaultChecked={this.state.textAlign == 'center'} />居&emsp;中
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="textalign" value="right" defaultChecked={this.state.textAlign == 'right'} />右对齐
                                </label>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-sm-2 control-label">字体大小</label>
                            <div className="col-sm-10 inline-block">
                                <label className="radio-inline fnnormal">
                                    <input type="radio" name="fontsize" value="14" defaultChecked={this.state.fontSize == '14'} />正常
                                </label>
                                <label className="radio-inline fnbigger">
                                    <input type="radio" name="fontsize" value="16" defaultChecked={this.state.fontSize == '16'} />较大
                                </label>
                                <label className="radio-inline fnbig">
                                    <input type="radio" name="fontsize" value="18" defaultChecked={this.state.fontSize == '18'} />大
                                </label>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-sm-2 control-label">展示样式</label>
                            <div className="col-sm-10 inline-block">
                                <label className="radio-inline">
                                    <input type="radio" name="showstyle" value="none" defaultChecked={this.state.display == 'none'} />全部展示
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="showstyle" value="block" defaultChecked={this.state.display == 'block'} />缩进展示
                                </label>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-sm-2 control-label">是否透明</label>
                            <div className="col-sm-10 inline-block">
                                <label className="radio-inline">
                                    <input type="radio" name="opacity" value="0" defaultChecked={this.state.opacity == '0'} />透明
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="opacity" value="1" defaultChecked={this.state.opacity == '1'} />不透明
                                </label>
                            </div>
                        </div>
                        <div className="form-group">
                            <label for="bgColor" className="col-sm-2 control-label">内容高度</label>
                            <div className="col-sm-10 inline-block"><input type="text" className="form-control" id="height" name="height" defaultValue={this.state.height=='auto' ? '' : this.state.height} placeholder="请输入整数(可为空)" /></div>
                        </div>
                        <div className="form-group form-save">
                            <button id="save" type="button" className="btn btn-danger btn-block" onClick={this.eventSave}>保&emsp;存</button>
                        </div>
                    </form>
                </div>
            );
        }
    });

    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
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

//React.render(<NoLink />, document.getElementById('NoLink'));
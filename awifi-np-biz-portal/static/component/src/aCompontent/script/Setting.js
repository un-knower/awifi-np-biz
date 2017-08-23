
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
            textAlign: 'left',
            aText: '链接文字',
            aHref: 'http://'
        };

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var SettingReact = React.createClass({
        getInitialState: function() {
            return _state;
        },

        eventSave: function () {
            var fields = [
                {'id': 'title', default: '', default_msg: '请修改链接内容', 'reg': /^[\w\u4e00-\u9fa5]{1,20}$/gm, 'empty': '链接文字不能为空', 'fault': '链接由1-20位汉字、数字、字母、下划线组成'},
                {'id': 'url', default: 'http://', default_msg: '请修改链接地址', 'reg': /((http|ftp|https|file):\/\/([\w\-]+\.)+[\w\-]+(\/[\w\u4e00-\u9fa5\-\.\/?\@\%\!\&=\+\~\:\#\;\,]*)?)/ig, 'empty': '链接地址不能为空', 'fault': '链接地址格式不正确'}
            ];
            for (var i=0,len=fields.length; i<len; i++) {
                var item = fields[i];
                var verify = awifiUtils.verify_field(item.id, item.reg, item.empty, item.fault, item.default, item.default_msg);
                if (verify) {
                    return ;
                }
            }

            _state.aText = $('#title').val();
            _state.aHref = $('#url').val();
            _state.textAlign = $('input[name="textalign"]:checked').val();

            setStates(_state);
        },

        render: function () {
            return (
                <div className="container">
                    <form className="form-horizontal">
                        <div className="form-group">
                            <label for="title" className="col-sm-2 control-label">链接标题：</label>
                            <div className="col-sm-9">
                                <input type="text" className="form-control" id="title" placeholder="请输入链接标题" defaultValue={this.state.aText} maxLength="20" />
                            </div>
                        </div>
                        <div className="form-group">
                            <label for="url" className="col-sm-2 control-label">链接地址：</label>
                            <div className="col-sm-9">
                                <input type="text" className="form-control" id="url" placeholder="请输入链接地址" defaultValue={this.state.aHref} />
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-sm-2 control-label">对齐样式：</label>
                            <div className="col-sm-9">
                                <label className="radio-inline"><input type="radio" name="textalign" value="left" defaultChecked={this.state.textAlign == 'left'} />左对齐</label>
                                <label className="radio-inline"><input type="radio" name="textalign" value="center" defaultChecked={this.state.textAlign == 'center'} />居&emsp;中</label>
                                <label className="radio-inline"><input type="radio" name="textalign" value="right" defaultChecked={this.state.textAlign == 'right'} />右对齐</label>
                            </div>
                        </div>
                        <div className="form-group form-save">
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

//var _Setting_ = function (entity, divId) {
//    var setting = null;
//
//    var _state = entity.state || {
//            textAlign: 'left',
//            aText: '链接文字',
//            aHref: 'http://'
//        };
//
//    function setStates(item) {
//        entity.setState(item);
//        setting.setState(item);
//    }
//
//    var SettingReact = React.createClass({
//        getInitialState: function() {
//            return _state;
//        },
//
//        eventSave: function () {
//            var fields = [
//                {'id': 'title', default: '链接文字', default_msg: '请修改链接内容', 'reg': /^[\w\u4e00-\u9fa5]{1,20}$/gm, 'empty': '链接文字不能为空', 'fault': '链接由1-20位汉字、数字、字母、下划线组成'},
//                {'id': 'url', default: 'http://', default_msg: '请修改链接地址', 'reg': /((http|ftp|https|file):\/\/([\w\-]+\.)+[\w\-]+(\/[\w\u4e00-\u9fa5\-\.\/?\@\%\!\&=\+\~\:\#\;\,]*)?)/ig, 'empty': '链接地址不能为空', 'fault': '链接地址格式不正确'}
//            ];
//            for (var i=0,len=fields.length; i<len; i++) {
//                var item = fields[i];
//                var verify = awifiUtils.verify_field(item.id, item.reg, item.empty, item.fault, item.default, item.default_msg);
//                if (verify) {
//                    return ;
//                }
//            }
//
//            _state.aText = $('#title').val();
//            _state.aHref = $('#url').val();
//            _state.textAlign = $('input[name="textalign"]:checked').val();
//
//            setStates(_state);
//        },
//
//        render: function () {
//            return (
//                <div className="container">
//                    <form className="form-horizontal">
//                        <div className="form-group">
//                            <label for="title" className="col-sm-2 control-label">链接标题：</label>
//                            <div className="col-sm-9">
//                                <input type="text" className="form-control" id="title" placeholder="请输入链接标题" defaultValue={this.state.aText} maxLength="20" />
//                            </div>
//                        </div>
//                        <div className="form-group">
//                            <label for="url" className="col-sm-2 control-label">链接地址：</label>
//                            <div className="col-sm-9">
//                                <input type="text" className="form-control" id="url" placeholder="请输入链接地址" defaultValue={this.state.aHref} />
//                            </div>
//                        </div>
//                        <div className="form-group">
//                            <label className="col-sm-2 control-label">对齐样式：</label>
//                            <div className="col-sm-9">
//                                <label className="radio-inline"><input type="radio" name="textalign" value="left" defaultChecked={this.state.textAlign == 'left'} />左对齐</label>
//                                <label className="radio-inline"><input type="radio" name="textalign" value="center" defaultChecked={this.state.textAlign == 'center'} />居&emsp;中</label>
//                                <label className="radio-inline"><input type="radio" name="textalign" value="right" defaultChecked={this.state.textAlign == 'right'} />右对齐</label>
//                            </div>
//                        </div>
//                        <div className="form-group form-save">
//                            <button id="save" type="button" className="btn btn-danger btn-block" onClick={this.eventSave}>保&emsp;存</button>
//                        </div>
//                    </form>
//                </div>
//            );
//        }
//    });
//
//    function render() {
//        setting = ReactDOM.render(<SettingReact />, document.getElementById(divId));
//        return setting;
//    }
//
//    return {
//        setting: setting,
//
//        setStates: setStates,
//
//        render: render
//    }
//};

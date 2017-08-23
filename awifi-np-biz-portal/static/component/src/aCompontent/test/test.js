
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
            textAlign: 'left',
            aText: '链接文字',
            aHref: 'http://'
        };

    var EntityReact = React.createClass({
        getInitialState: function () {
            return _state;
        },

        render: function () {
            return (
                <div className="textlink" style={{textAlign: this.state.textAlign}}>
                    <a href={this.state.aHref !== 'http://' ? this.state.aHref : 'javascript:;' } title={this.state.aText}>{this.state.aText}</a>
                </div>
            );
        }
    });

    // OtherReact 在这里定义

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
                {'id': 'title', default: '链接文字', default_msg: '请修改链接内容', 'reg': /^[\w\u4e00-\u9fa5]{1,20}$/gm, 'empty': '链接文字不能为空', 'fault': '链接由1-20位汉字、数字、字母、下划线组成'},
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

var entity = _Entity_('', 'entity').render();

var setting = _Setting_(entity, 'setting').render();
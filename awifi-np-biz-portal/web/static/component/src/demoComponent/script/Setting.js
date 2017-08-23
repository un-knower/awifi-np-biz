
/**
 * 对组件内容的设置
 * @param entity
 * @param divId
 * @returns {{setting: *, setStates: setStates, create: create}}
 * @constructor
 * @Auther: zhuxh
 * @Date: 2015-12-29
 */
var _Setting_ = function (entity, divId) {
    var setting = null;

    /**
     * 更新设置组件状态数据
     * @param state
     */
    function setStates(state) {
        entity.setState(state);
        setting.setState(state);
    }

    /**
     * 声明设置组件
     */
    /*
    var SettingReact = React.createClass({

        getInitialState: function() {
            return {
                content: entity.state.content,
                textAlign: entity.state.textAlign,
                fontSize: entity.state.fontSize,
                display: entity.state.display
            };
        },

        eventContent: function(event) {
            var _state = {
                'content': event.target.value
            };
            setStates(_state);
        },

        eventTextAlign: function (event) {
            var _state = {
                'textAlign': event.target.value
            };
            setStates(_state);
        },

        eventFontSize: function (event) {
            var _state = {
                'fontSize': event.target.value
            };
            setStates(_state);
        },

        eventDisplay: function (event) {
            var _state = {
                'display': event.target.value
            };
            setStates(_state);
        },

        render: function() {
            return (
                <div className="container">
                    <form className="form-horizontal">
                        <div className="form-group">
                            <label for="content" className="col-sm-2 control-label">内容：</label>
                            <div className="col-sm-9">
                                <textarea className="form-control" rows="3" id="content" placeholder="请输入文本内容" value={this.state.content} onChange={this.eventContent}></textarea>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-sm-2 control-label">对齐样式：</label>
                            <div className="col-sm-9">
                                <label className="radio-inline">
                                    <input type="radio" name="textalign" value="left" checked={this.state.textAlign == 'left'} onChange={this.eventTextAlign} />左对齐
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="textalign" value="center" checked={this.state.textAlign == 'center'} onChange={this.eventTextAlign} />居&emsp;中
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="textalign" value="right" checked={this.state.textAlign == 'right'} onChange={this.eventTextAlign} />右对齐
                                </label>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-sm-2 control-label">字体大小：</label>
                            <div className="col-sm-9">
                                <label className="radio-inline fnnormal">
                                    <input type="radio" name="fontsize" value="14" checked={this.state.fontSize == '14'} onChange={this.eventFontSize} />正常
                                </label>
                                <label className="radio-inline fnbigger">
                                    <input type="radio" name="fontsize" value="16" checked={this.state.fontSize == '16'} onChange={this.eventFontSize} />较大
                                </label>
                                <label className="radio-inline fnbig">
                                    <input type="radio" name="fontsize" value="18" checked={this.state.fontSize == '18'} onChange={this.eventFontSize} />大
                                </label>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-sm-2 control-label">展示样式：</label>
                            <div className="col-sm-9">
                                <label className="radio-inline">
                                    <input type="radio" name="showstyle" value="none" checked={this.state.display == 'none'} onChange={this.eventDisplay} />全部展示
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" name="showstyle" value="block" checked={this.state.display == 'block'} onChange={this.eventDisplay} />缩进展示
                                </label>
                            </div>
                        </div>
                    </form>
                </div>
            );
        }
    });
     */

    // ### Reactjs 写法

    return {
        setStates: setStates,

        /**
         * 返回设置组件对象
         * @returns {*}
         */
        render: function () {
            console.log('设置组件entity=>', entity);

            // ### Reactjs 写法
            setting = React.render(<SettingReact />, document.getElementById(divId));
            console.log('设置组件setting = React.render => ', setting);
            return setting;
        }
    }
};

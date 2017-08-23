/**
 * Created by Shin on 2016/08/31.
 * FAQ组件
 */
var _Setting_ = function (entity, divId) {

    var setting = null;

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var _state = entity.state || { };

    var SettingReact = React.createClass({
        //状态初始化函数
        getInitialState: function() {
            return _state;
        },
        //开启中英文-点击事件
        handleOpenLanguageClick: function(e){
            _state.openLanguage = e.target.checked;
            this.setState(_state);
        },
        //中文标题-变更事件
        handleCnTipsTitleChange: function(e){
            _state.cnTipsTitle = e.target.value;
            this.setState(_state);
        },
        //中文内容-变更事件
        handleCnTipsContentChange: function(e){
            _state.cnTipsContent = e.target.value;
            this.setState(_state);
        },
        //英文标题-变更事件
        handleEnTipsTitleChange: function(e){
            _state.enTipsTitle = e.target.value;
            this.setState(_state);
        },
        //英文内容-变更事件
        handleEnTipsContentChange: function(e){
            _state.enTipsContent = e.target.value;
            this.setState(_state);
        },
        //保存按钮
        handleSaveClick: function(){
            if(!_state.cnTipsTitle || _state.cnTipsTitle === ''){
                alert('请输入中文标题!');
                return;
            }
            if(!_state.cnTipsContent || _state.cnTipsContent === ''){
                alert('请输入中文内容!');
                return;
            }
            if(_state.openLanguage){
                if(!_state.enTipsTitle || _state.enTipsTitle === ''){
                    alert('请输入英文标题!');
                    return;
                }
                if(!_state.enTipsContent || _state.enTipsContent === ''){
                    alert('请输入英文内容!');
                    return;
                }
            }
            entity.setState(_state);
        },
        render: function(){
            return (
                <div className="_Setting_ container">
                    <form className="form-horizontal">
                        <div className="form-group">
                            <label className="col-sm-2 control-label">中英文</label>
                            <div className="col-sm-9 inline-block">
                                <label className="radio-inline" style={{paddingLeft:0}}>
                                    <input type="checkbox" id="openLanguage" defaultChecked={ _state.openLanguage } onClick={ this.handleOpenLanguageClick } />开启
                                </label>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-sm-2 control-label">中文标题</label>
                            <div className="col-sm-9 inline-block">
                                <input type="text" className="form-control" value={ _state.cnTipsTitle } onChange={ this.handleCnTipsTitleChange } placeholder="请输入标题（必填）" maxLength="30"/>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-sm-2 control-label">中文内容</label>
                            <div className="col-sm-9 inline-block">
                                <textarea className="form-control" rows="6" value={ _state.cnTipsContent}  onChange={ this.handleCnTipsContentChange } placeholder="请输入具体内容（必填）"/>
                            </div>
                        </div>
                        <div className={ _state.openLanguage ? 'form-group' : 'form-group hide' }>
                            <label className="col-sm-2 control-label">英文标题</label>
                            <div className="col-sm-9 inline-block">
                                <input type="text" className="form-control" value={ _state.enTipsTitle } onChange={ this.handleEnTipsTitleChange } placeholder="请输入标题（必填）" maxLength="30"/>
                            </div>
                        </div>
                        <div className={ _state.openLanguage ? 'form-group' : 'form-group hide' }>
                            <label className="col-sm-2 control-label">英文内容</label>
                            <div className="col-sm-9 inline-block">
                                <textarea className="form-control" rows="6" value={ _state.enTipsContent}  onChange={ this.handleEnTipsContentChange } placeholder="请输入具体内容（必填）"/>
                            </div>
                        </div>
                        <div className="form-group form-save">
                            <button id="save" type="button" className="btn btn-danger btn-block" onClick={ this.handleSaveClick }>保&emsp;存</button>
                        </div>
                    </form>
                </div>
            );
        }
    });
    /**
     * 渲染DOM
     */
    function render() {
        setting = ReactDOM.render(<SettingReact />, document.getElementById(divId));
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
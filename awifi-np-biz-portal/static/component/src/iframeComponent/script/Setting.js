/**
 * Created by 许小满 on 2016/6/21.
 * iFrame 组件
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
        //url输入框 变更事件处理
        handleURLChange: function(e){
            _state.url = e.target.value;
            this.setState(_state);
        },
        //checkbox点击事件处理
        handleCheckboxClick: function(type, e){
            switch(type){
                case 'needParse': _state.needParse = e.target.checked; break;//是否解析接口返回JSON
            }
            this.setState(_state);
        },
        //保存按钮
        handleSaveClick: function(){
            //1.网址 不能同时为空
            if(!_state.url){
                alert('请输入网址！');
                return;
            }
            entity.setState(_state);
        },
        render: function () {
            return (
                <div className="container">
                    <form className="form-horizontal">
                        <div className="form-group">
                            <label htmlFor="title" className="col-sm-2 control-label">网址：</label>
                            <div className="col-sm-9">
                                <input id="url" type="text" className="form-control" value={ this.state.url } placeholder="请输入网址" onChange={ this.handleURLChange }/>
                                <br/>
                                <input type="checkbox" id="needParseBox" defaultChecked={ _state.needParse } onClick={ this.handleCheckboxClick.bind(this, 'needParse') }/>
                                <label className="authTitle" htmlFor="needParseBox">解析接口返回JSON（勾选需要）</label>
                            </div>
                        </div>
                        <div className="form-group">
                            <div className="col-sm-offset-2 col-sm-9">
                                <button id="save" type="button" className="btn btn-danger btn-sm" onClick={ this.handleSaveClick }>保&emsp;存</button>
                            </div>
                        </div>
                    </form>
                </div>
            );
        }

    });

    /**  渲染DOM */
    function render() {
        setting = ReactDOM.render(<SettingReact />, document.getElementById(divId));
        return setting;
    }
    /** 暴露对象属性及方法 */
    return {
        setting: setting,
        setStates: setStates,
        render: render
    }
};

var _Setting_ = function (entity, divId) {

    var setting = null;

    var _state = entity.state || {};

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var WechatAuthedSetting = React.createClass({
        render: function () {
            return (
                <div className="container">
                    <div className="notSetting">当前组件无设置项</div>
                </div>
            );
        }
    });

    /**
     * 渲染DOM
     */
    function render() {
        setting = ReactDOM.render(<WechatAuthedSetting />, document.getElementById(divId));
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

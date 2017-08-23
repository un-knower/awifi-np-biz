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

        },
        render: function () {
            return (
                <div className="container">
                    <form className="form-horizontal">
                        <div className="form-group control-label">&emsp;此组件无需进行设置（幻灯片）</div>
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
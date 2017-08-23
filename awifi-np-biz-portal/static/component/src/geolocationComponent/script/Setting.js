/**
 * Created by Shin on 2016/3/15.
 * HTML5获取经纬度 组件
 */
var _Setting_ = function (entity, divId) {

    var setting = null;

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var _state = entity.state || {
            isShow: false    //是否隐藏此组件文件
        };

    var GeolocationComponent = React.createClass({

        //状态初始化函数
        getInitialState: function() {
            return _state;
        },

        handleIsShowClick: function(e) {
            _state.isShow = e.target.checked;
            this.setState(_state);

        },

        handleSaveClick: function(){
            entity.setState(_state);
        },

        render: function () {
            return (
                <div className="container">
                    <form className="form-horizontal">
                        <div className="form-group control-label">&emsp;经纬度组件无需进行设置</div>
                    </form>
                </div>
            );
        }

    });


    /**
     * 渲染DOM
     */
    function render() {
        setting = ReactDOM.render(<GeolocationComponent />, document.getElementById(divId));
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




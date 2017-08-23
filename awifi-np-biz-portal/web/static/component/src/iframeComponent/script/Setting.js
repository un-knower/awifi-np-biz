/**
 * Created by Shin on 2016/3/15.
 * iFrame 组件
 */
var _Setting_ = function (entity, divId) {

    var setting = null;

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var _state = entity.state || {
            url: ''
        };

    var IFrameComponent = React.createClass({

        //状态初始化函数
        getInitialState: function() {
            return _state;
        },


        handleSaveClick: function(){
            var urlLink = $('#url').val();

            urlLink = (urlLink.indexOf('http://') == 0 ? urlLink : 'http://' + urlLink);

            _state.url = urlLink;

            entity.setState(_state);
        },


        render: function () {
            return (
                <div className="container">
                    <form className="form-horizontal">
                        <div className="form-group">
                            <label for="title" className="col-sm-2 control-label">网址：</label>
                            <div class="col-sm-9">
                                <input id="url" type="text" className="form-control" defaultValue={this.state.url} placeholder="请输入网址"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-9">
                                <button id="save" type="button" className="btn btn-danger btn-sm" onClick={ this.handleSaveClick }>保&emsp;存</button>
                            </div>
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
        setting = ReactDOM.render(<IFrameComponent />, document.getElementById(divId));
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
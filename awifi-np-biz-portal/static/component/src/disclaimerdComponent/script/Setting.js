/**
 * Created by Shin on 2016/03/03.
 * 免责声明组件
 */
var _Setting_ = function (entity, divId) {

    var setting = null;

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var _state = entity.state || {
            smTitle: '',
            content: ''
        };

    var DisclaimerComponent = React.createClass({

        //状态初始化函数
        getInitialState: function() {
            return _state;
        },


        handleSaveClick: function(){

            var titleDetail = $('#smTitle').val();
            var contentDetail = $('#content').val();

            if(titleDetail == '' || titleDetail == 'null' || titleDetail == 'undefined'){
                alert('请输入标题!');
            }

            if(contentDetail == '' || contentDetail == 'null' || contentDetail == 'undefined'){
                alert('请输入具体内容!');
            }

            _state.smTitle = titleDetail;
            _state.content = contentDetail;

            entity.setState(_state);
        },


        render: function () {
            return (
                <div className="container">
                    <form className="form-horizontal">
                        <div className="form-group">
                            <label for="content" className="col-sm-2 control-label">标题：</label>
                            <div className="col-sm-9">
                                <input type="text" className="form-control" id="smTitle" defaultValue={this.state.smTitle} placeholder="请输入标题（必填）" maxLength="16"/>
                            </div>
                        </div>
                        <div className="form-group">
                            <label for="content" className="col-sm-2 control-label">具体内容：</label>
                            <div className="col-sm-9">
                                <textarea className="form-control" rows="6" id="content" defaultValue={this.state.content}  placeholder="请输入具体内容（必填）"/>
                            </div>
                        </div>
                        <div className="form-group">
                            <div className="col-sm-offset-2 col-sm-9">
                                <button type="button" className="btn btn-danger btn-sm" onClick={ this.handleSaveClick }>保&emsp;存</button>
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
        setting = ReactDOM.render(<DisclaimerComponent />, document.getElementById(divId));
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
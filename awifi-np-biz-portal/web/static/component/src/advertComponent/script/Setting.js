/**
 * Created by Shin on 2016/2/17.
 * 广告组件
 */

var _Setting_ = function (entity, divId) {

    var setting = null;

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var _state = entity.state || {
            style: '2:1', //默认广告显示比例
            adCode: '1024-58113', //默认广告显示比例对应的广告码
            advertWidth: 0,//广告div宽度
            advertHeight: 0//广告div高度
        };



    var AdvertComponent = React.createClass({
        //状态初始化函数
        getInitialState: function() {
            return _state;
        },
        //保存按钮点击事件
        handleSaveClick: function(){

            var $checkedRadio = $('input[name="proportionStyle"]:checked');

            var newStyle = $checkedRadio.val();
            if (newStyle == '' || newStyle == 'null' || newStyle == 'undefined') {
                alert('请选择一个广告比例');
            }

            var newAdCode = $checkedRadio.attr('id');

            _state.style = newStyle;
            _state.adCode = newAdCode;
            entity.setState(_state);
        },
        //渲染
        render: function () {
            return (
                <div className="container">
                    <form className="form-horizontal">
                        <div className="areaTop">&nbsp;</div>
                        <div className="form-group">
                            <label className="col-sm-2 control-label-title">广告类型：</label>
                            <label className="col-sm-2 control-label-dec">（请从以下两种方式的广告类型中任选一种的广告比例）</label>
                        </div>
                        <div className="form-group form-group-bottom-0">
                            <label className="col-sm-2 control-label-info">方式一：合约型</label>
                        </div>
                        <div className="form-group form-group-bottom">
                            <div className="col-sm-9">
                                <label className="radio-inline">
                                    <input type="radio" id="1024-58113" name="proportionStyle" value="2:1" defaultChecked={this.state.style == '2:1'}/>2:1
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" id="1024-58049" name="proportionStyle" value="2:3" defaultChecked={this.state.style == '2:3'}/>2:3
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" id="1024-63617" name="proportionStyle" value="4:1" defaultChecked={this.state.style == '4:1'}/>4:1
                                </label>
                            </div>
                        </div>
                        <div className="form-group form-group-bottom-0">
                            <label className="col-sm-2 control-label-info">方式二：竞价型</label>
                        </div>
                        <div className="form-group form-group-bottom">
                            <div className="col-sm-9">
                                <label className="radio-inline">
                                    <input type="radio" id="1024-64065" name="proportionStyle" value="2:1.001" defaultChecked={this.state.style == '2:1.001'}/>2:1
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" id="1024-64033" name="proportionStyle" value="2:3.001" defaultChecked={this.state.style == '2:3.001'}/>2:3
                                </label>
                                <label className="radio-inline">
                                    <input type="radio" id="1024-58241" name="proportionStyle" value="3:1.001" defaultChecked={this.state.style == '3:1.001'}/>3:1
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
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
        setting = ReactDOM.render(<AdvertComponent />, document.getElementById(divId));
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
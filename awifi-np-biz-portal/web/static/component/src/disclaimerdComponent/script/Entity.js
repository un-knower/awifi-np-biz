/**
 * Created by Shin on 2016/03/03.
 * 免责声明组件
 */
var _Entity_ = function (state, divId) {

    var _state = state || {
            smTitle: '免责声明',
            content: '',
            layoutShow: false, // 默认不显示弹出层
            isCheck: true  //默认选中，
        };

    var DisclaimerComponent = React.createClass({
        //返回初始化的信息
        getInitialState: function () {
            return _state;
        },

        //更新状态，供子组件调用
        updateState: function(){
            this.setState(_state);
        },

        //点击改变状态，显示弹出层
        changeIsCheck : function () {
            _state.layoutShow = true;  //显示弹出层
            this.setState(_state);
        },

        //根据state变化来渲染组件
        render: function () {
            _DISCLAIMER = this.state.isCheck ? 'agree' : 'refuse';
            return (
                <div className="_Entity_">
                    <div id="disclaimer" className={this.state.isCheck ? "uncboxed cboxed" : "uncboxed"} onClick={this.changeIsCheck}>{this.state.smTitle}</div>
                    {this.state.layoutShow ? <LayoutComponent updateState={this.updateState} content={this.state.content}/> : ''}
                </div>
            );
        }
    });


    /**
     * 免责声明弹出层子组件， 当点击“同意”时打勾，当点击“拒绝”时不打勾
     */
    var LayoutComponent = React.createClass({

        option: {
            isCheck: ''
        },

        getInitialState: function() {
            return this.option;
        },

        //拒绝按钮的事件
        handleRefuseClick: function(){
            _state.layoutShow = false;  //隐藏弹出层
            _state.isCheck = false;     //不选中状态
            this.props.updateState();   //更新父组件状态

        },

        //同意按钮的事件
        handleAgreeClick: function(){
            _state.layoutShow = false;  //隐藏弹出层
            _state.isCheck = true;      //选中状态
            this.props.updateState();   //更新父组件状态
        },

        render: function(){
            return (
                <div className="content-layout">
                    <div className="content-position">
                        <div className="content-border">
                            <div className="content">{this.props.content}</div>
                            <div className="opt">
                                <input type="button" value="同意" onClick={this.handleAgreeClick}/>
                                <input type="button" className="refuse" value="拒绝" onClick={this.handleRefuseClick}/>
                            </div>
                        </div>
                    </div>
                </div>
            );
        }

    });


    /**
     * 制作页面时返回React对象
     */
    function render() {
        return ReactDOM.render(<DisclaimerComponent />, document.getElementById(divId));
    }

    /**
     * 暴露render方法
     */
    return {
        render: render
    }
};
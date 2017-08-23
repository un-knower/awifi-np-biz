/**
 * Created by 许小满 on 2016/6/29.
 * 酒店 iFrame（弹框） 组件
 */
var _Entity_ = function (state, divId) {

    var _state = state || {
            aText: '链接文字',//链接标题
            textAlign: 'left',//对齐样式
            aHref: 'http://',//链接地址
            isDialogOpen: false//用于控制对话框是否显示
        };

    //主组件
    var EntityReact = React.createClass({
        getInitialState: function () {
            return _state;
        },
        //更新Entity组件状态
        setEntityState: function () {
            this.setState(_state);
        },
        //打开对话框
        openDialog: function(eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            _state.isDialogOpen = true;
            this.setState(_state);//更新Entity组件状态
        },
        render: function () {
            return (
                <div className="_Entity_">
                    <div className="textLink" style={{ textAlign: this.state.textAlign }}>
                        <a href="javascript:void(0)" title={ this.state.aText }
                           onClick={ this.openDialog.bind(this, 'click') } onTouchStart={ this.openDialog.bind(this, 'touch') }
                        >{this.state.aText}</a>
                    </div>
                    { this.state.isDialogOpen ? <IFrameDialogComponent
                        aText={ this.state.aText }
                        aHref={ this.state.aHref }
                        setEntityState={ this.setEntityState }
                    /> : '' }
                </div>
            );
        }
    });

    //对话框组件，里面包含iframe
    var IFrameDialogComponent = React.createClass({
        option:{
            width: $(".awifi-container").width() + 'px' //对话框宽度
        },
        getInitialState: function () {
            return this.option;
        },
        //关闭对话框
        closeDialog: function(){
            _state.isDialogOpen = false;
            this.props.setEntityState();//更新Entity组件状态
        },
        render: function () {
            return (
                <div className = "dialog" style={{ width: this.state.width }}>
                    <div className="detail">
                        <div className="detail-title">
                            <div className="title-font"></div>
                            <a className="closeBtn" onClick={this.closeDialog}></a>
                        </div>
                        <iframe src={ this.props.aHref } style={{ width:'100%', border: '0px', 'height': '100%'}} scrolling="auto"></iframe>'
                    </div>
                </div>
            )
        }
    });

    /**
     * 判断触发的事件是否有效
     * 其中：click适用于PC端、touch适用于移动端
     * @param eventType 事件类型：click 单击时间、touch 触摸事件
     * @returns {boolean} true 有效、false 无效
     * @auth 许小满
     * @date 2016-10-18 18:09:15
     */
    function isEventValid(eventType){
        var isMobileTerminal = /Mobile/.test(navigator.userAgent);//判断浏览器是否为移动端
        if(eventType == null || eventType == undefined){
            alert('eventType.');
            return false;
        }
        //1.eventType'click'时，PC端有效，移动端无效
        if(eventType === 'click'){
            return !isMobileTerminal;
        }
        //2.eventType'touch'时，PC端无效，移动端有效
        else if(eventType === 'touch'){
            return isMobileTerminal;
        }
        //3.其它情况提示错误信息
        else{
            alert('eventType['+eventType+']超出了范围[click/touch].');
            return false;
        }
    }

    // OtherReact 在这里定义

    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
    function render() {
        return ReactDOM.render(<EntityReact />, document.getElementById(divId));
    }

    return {
        render: render
    }
};
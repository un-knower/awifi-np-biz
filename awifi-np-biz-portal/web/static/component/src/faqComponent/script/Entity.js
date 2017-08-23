/**
 * Created by Shin on 2016/08/31.
 * FAQ组件
 */
var _Entity_ = function (state, divId) {

    var container_width = $('.awifi-container').width();

    var _state = state || {
            openLanguage: true,//是否开启中英文
            layoutShow: false,//是否弹出弹出层，默认不弹出
            /* 中文配置 */
            cnTipsTitle: '常见问题',
            cnTipsContent: '',
            /* 中文配置 */
            enTipsTitle: 'FAQ',
            enTipsContent: ''

        };

    if(_state.openLanguage){
        //页面加载完成后，添加中英文触发事件
        $(window).load(function(){
            $('.language .cn').off().click(function () {
                $('.tips .btn').text(_state.cnTipsTitle);
            });

            $('.language .en').off().click(function () {
                $('.tips .btn').text(_state.enTipsTitle);
            });
        });
    }

    var FAQComponent = React.createClass({
        getInitialState: function () {
            return _state;
        },
        //更新状态，供子组件调用
        updateState: function(){
            this.setState(_state);
        },
        //点击改变状态，显示弹出层
        changeIsShowForClick : function () {
            if(/Mobile/i.test(navigator.userAgent)){
                return;
            }
            _state.layoutShow = true;  //显示弹出层
            this.setState(_state);
        },
        //点击改变状态，显示弹出层
        changeIsShowForTouch : function () {
            _state.layoutShow = true;  //显示弹出层
            this.setState(_state);
        },
        //根据state变化来渲染组件
        render: function () {
            //判断中英文
            var language = $('.language .checked').html();
            var isCn = !language || language === 'CN';//是否中文
            var tipsComponent = isCn ?  <FAQTipsCnComponent updateState={ this.updateState } /> :  <FAQTipsEnComponent updateState={ this.updateState } />;
            var html = null;
            if (_DEV_ID == '{@devId@}') {// 无法判断当前是否预览模式
                html =  (
                    <div className="_Entity_">
                        <div className="help">FAQ组件</div>
                    </div>
                )
            } else {
                html = (
                    <div className="_Entity_">
                        <div className="tips" style={{width: container_width+"px"}}>
                            <div className="btn" onTouchStart={ this.changeIsShowForTouch } onClick={ this.changeIsShowForClick } >{ this.state.cnTipsTitle }</div>
                        </div>
                        {this.state.layoutShow ? tipsComponent : ''}
                    </div>
                )
            }
            return html;
        }
    });
    //FAQ内容-弹出层-中文
    var FAQTipsCnComponent = React.createClass({
        option: {
        },
        getInitialState: function() {
            return this.option;
        },
        handleCloseClick: function(){
            _state.layoutShow = false;
            this.props.updateState();
        },
        render: function(){
            var _tipsContent = _state.cnTipsContent;
            _tipsContent = _tipsContent.replace(/\n/g, '<br/>');
            _tipsContent = _tipsContent.replace(/[  ]/g, '&nbsp;');
            return (
                <div className="tipsBody" style={{width: container_width +"px"}}>
                    <div className="tipsBg">
                        <div className="closeBtn" onClick={this.handleCloseClick}>x</div>
                        <div className="tipsInfo">
                            <div><p dangerouslySetInnerHTML={{__html: _tipsContent}} /></div>
                        </div>
                    </div>
                </div>
            );
        }
    });
    //FAQ内容-弹出层-英文
    var FAQTipsEnComponent = React.createClass({
        option: {
        },
        getInitialState: function() {
            return this.option;
        },
        handleCloseClick: function(){
            _state.layoutShow = false;
            this.props.updateState();
        },
        render: function(){
            var _tipsContent = _state.openLanguage ? _state.enTipsContent : _state.cnTipsContent;
            _tipsContent = _tipsContent.replace(/\n/g, '<br/>');
            //_tipsContent = _tipsContent.replace(/[  ]/g, '&nbsp;');
            return (
                <div className="tipsBody" style={{width: container_width +"px"}}>
                    <div className="tipsBg">
                        <div className="closeBtn" onClick={this.handleCloseClick}>x</div>
                        <div className="tipsInfo">
                            <div><p dangerouslySetInnerHTML={{__html: _tipsContent}} /></div>
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
        return ReactDOM.render(<FAQComponent />, document.getElementById(divId));
    }
    /**
     * 暴露render方法
     */
    return {
        render: render
    }
};
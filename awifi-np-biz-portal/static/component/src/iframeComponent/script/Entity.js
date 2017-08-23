/**
 * Created by Shin on 2016/3/15.
 * iFrame 组件
 */
var _Entity_ = function (state, divId) {

    var _state = state || {
            url: '' //要展示的url
        };

    var IFrameComponent = React.createClass({

        getInitialState: function () {
            return _state;
        },

        //根据state变化来渲染组件
        render: function () {
            var height = $(window).height();
            var _this = this;
            var src = _state.url;
            if (src) {
                src = (src.indexOf('http://') == 0 ? src : 'http://' + src);
                _this.state.url = src;
            }

            if (typeof _DEV_ID == 'undefined' || _DEV_ID == '{@devId@}') {
                return (
                    <div className="_Entity_">
                        <div className="nullIframeSrc" style={{height:48}}>Iframe区域</div>
                    </div>
                )
            } else {
                if (this.state.url && this.state.url != 'http://') {
                    return (
                        <div className="_Entity_">
                            <iframe src={this.state.url} style={{height: height}} frameBorder="0"></iframe>
                        </div>
                    )
                } else {
                    return (
                        <div className="_Entity_">
                            <div className="nullIframeSrc" style={{height:48}}>Iframe区域</div>
                        </div>
                    )
                }
            }
        }
    });


    /**
     * 制作页面时返回React对象
     */
    function render() {
        return ReactDOM.render(<IFrameComponent />, document.getElementById(divId));
    }

    /**
     * 暴露render方法
     */
    return {
        render: render
    }
};
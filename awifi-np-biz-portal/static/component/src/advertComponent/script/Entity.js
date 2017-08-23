/**
 * Created by Shin on 2016/2/17.
 * 广告组件
 */
var _Entity_ = function (state, divId) {

    var _state = state || {
            style: '2:1', //默认广告显示比例
            adCode: '1024-58113', //默认广告显示比例对应的广告码
            advertDivId : '',//广告div所对应的id
            advertWidth: 0,//广告div宽度
            advertHeight: 0//广告div高度
        };

    //广告平台地址（生成环境：http://mgg.51awifi.com/advert/push/js/mgg.js）
    var adUrl = 'http://mgg.51awifi.com/advert/push/js/mgg.js';



    var AdvertComponent = React.createClass({
        //返回初始化的信息
        getInitialState: function () {
            return _state;
        },

        //组件渲染之前先向广告平台发起请求
        componentWillMount: function () {
            return;
            var _this = this;
            var $base = $('._Entity_');
            $base.each(function(){
                var $el = $(this);
                var adcode = _state.adCode;
                var _DEVID_ = _DEV_ID;

                if(!adUrl || !adcode){
                    return;
                }

                var styleValue = _this.state.style;

                var newStyleValue = styleValue.split(':');

                var proportion = parseInt(newStyleValue[1], 10)/parseInt(newStyleValue[0], 10);

                _this.state.advertWidth =$el.outerWidth();

                _this.state.advertHeight = _this.state.advertWidth * proportion;

                $el.height(_this.state.advertHeight);


                //上传至正式环境测试时显示如下代码
                if (_DEVID_ == '{@devId@}') {
                    $el.css({
                        'height': _this.state.advertHeight + 'px'
                    }).find('.areaIp').css({
                        'height': _this.state.advertHeight + 'px',
                        'line-height': _this.state.advertHeight + 'px'
                    });
                    return;
                }


                //return;

                var id = 'ad' + (new Date().getTime()) + Math.random().toFixed(10).substr(2);

                this.setAttribute('id', id);

                var s = document.createElement('script');
                s.type = 'text/javascript';
                s.onload = s.onreadystatechange = function () {
                    try{
                        AD.pushframe(id, [_DEVID_, adcode].join('@'), $el.height());
                    }catch(e){}

                };
                s.src = adUrl;
                document.body.appendChild(s);

            });
        },

        componentDidMount: function () {
            var _this = this;
            var $base = $('#'+divId).find('._Entity_');
            $base.each(function(){
                var $el = $(this);
                var adcode = _state.adCode;
                var _DEVID_ = _DEV_ID;

                if(!adUrl || !adcode){
                    return;
                }

                var styleValue = _this.state.style;

                var newStyleValue = styleValue.split(':');

                var proportion = parseInt(newStyleValue[1], 10)/parseInt(newStyleValue[0], 10);

                var _advertWidth =$el.outerWidth();

                var _advertHeight = _advertWidth * proportion;

                $el.height(_advertHeight);


                //上传至正式环境测试时显示如下代码
                if (_DEVID_ == '{@devId@}') {
                    $el.css({
                        'height': _advertHeight + 'px'
                    }).find('.areaIp').css({
                        'height': _advertHeight + 'px',
                        'line-height': _advertHeight + 'px'
                    });
                    return;
                }


                //return;



                var id = 'ad' + (new Date().getTime()) + Math.random().toFixed(10).substr(2);

                $el.attr('id', id);

                var s = document.createElement('script');
                s.type = 'text/javascript';
                s.onload = s.onreadystatechange = function () {
                    try{
                        AD.pushframe(id, [_DEVID_, adcode].join('@'), $el.height());
                    }catch(e){}

                };
                s.src = adUrl;
                document.body.appendChild(s);

            });
        },


        //根据state变化来渲染组件
        render: function () {
            var styleValue = this.state.style;

            var newStyleValue = styleValue.split(':');

            var proportion = parseInt(newStyleValue[1], 10)/parseInt(newStyleValue[0], 10);

            this.state.advertWidth = $('#'+divId).outerWidth();

            this.state.advertHeight = this.state.advertWidth * proportion;

            return (
                <div className="_Entity_" style={{width:this.state.advertWidth + 'px', height:this.state.advertHeight + 'px'}}>
                    <div className="areaIp" style={{width:this.state.advertWidth + 'px', height:this.state.advertHeight + 'px', lineHeight:this.state.advertHeight + 'px'}}>广告区域</div>
                </div>
            );
        }
    });



    /**
     * 制作页面时返回React对象
     */
    function render() {
        return ReactDOM.render(<AdvertComponent />, document.getElementById(divId));
    }

    /**
     * 暴露render方法
     */
    return {
        render: render
    }
};
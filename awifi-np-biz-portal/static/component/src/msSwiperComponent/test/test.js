
/** ---------------------------------------  中间内容区域 开始  ----------------------------------------------------------------------------*/

var _Entity_ = function (state, divId) {
    /**
     * $.yxMobileSlider
     * @charset utf-8
     * @extends jquery.1.9.1
     * @fileOverview 创建一个焦点轮播插件，兼容PC端和移动端，若引用请保留出处，谢谢！
     * @author 李玉玺
     * @version 1.0
     * @date 2013-11-12
     * @example
     * $(".container").yxMobileSlider();
     */
    (function($){
        $.fn.yxMobileSlider = function(settings){
            var defaultSettings = {
                width: 640, //容器宽度
                height: 320, //容器高度
                during: 5000, //间隔时间
                speed:30 //滑动速度
            }
            settings = $.extend(true, {}, defaultSettings, settings);
            return this.each(function(){
                var _this = $(this), s = settings;
                var startX = 0, startY = 0; //触摸开始时手势横纵坐标
                var temPos; //滚动元素当前位置
                var iCurr = 0; //当前滚动屏幕数
                var timer = null; //计时器
                var oMover = $("ul", _this); //滚动元素
                var oLi = $("li", oMover); //滚动单元
                var num = oLi.length; //滚动屏幕数
                var oPosition = {}; //触点位置
                var moveWidth = s.width; //滚动宽度
                //初始化主体样式

                _this.width(s.width).height(s.height).css({
                    position: 'relative',
                    overflow: 'hidden',
                    margin:'0 auto'
                }); //设定容器宽高及样式
                oMover.css({
                    position: 'absolute',
                    left: 0
                });
                oLi.css({
                    float: 'left',
                    display: 'inline'
                });
                $("img", oLi).css({
                    width: '100%',
                    height: '100%'
                });
                //初始化焦点容器及按钮
                _this.append('<div class="focus"><div></div></div>');
                var oFocusContainer = $(".focus");
                for (var i = 0; i < num; i++) {
                    $("div", oFocusContainer).append("<span></span>");
                }
                var oFocus = $("span", oFocusContainer);
                oFocusContainer.css({
                    minHeight: $(this).find('span').height() * 2,
                    position: 'absolute',
                    bottom: 0,
                    background: 'rgba(0,0,0,0.5)'
                })
                $("span", oFocusContainer).css({
                    display: 'block',
                    float: 'left',
                    cursor: 'pointer'
                })
                $("div", oFocusContainer).width(oFocus.outerWidth(true) * num).css({
                    position: 'absolute',
                    right: 10,
                    top: '50%',
                    marginTop: -$(this).find('span').width() / 2
                });
                oFocus.first().addClass("current");
                //页面加载或发生改变
                $(window).bind('resize load', function(){
                    if (isMobile()) {
                        mobileSettings();
                        bindTochuEvent();
                    }
                    oLi.width(_this.width()).height(_this.height());//设定滚动单元宽高
                    oMover.width(num * oLi.width());
                    oFocusContainer.width(_this.width()).height(_this.height() * 0.15).css({
                        zIndex: 2
                    });//设定焦点容器宽高样式
                    _this.fadeIn(300);
                });

                $(window).resize()
                //页面加载完毕BANNER自动滚动
                autoMove();
                //PC机下焦点切换
                if (!isMobile()) {
                    oFocus.hover(function(){
                        iCurr = $(this).index() - 1;
                        stopMove();
                        doMove();
                    }, function(){
                        autoMove();
                    })
                }
                //自动运动
                function autoMove(){
                    timer = setInterval(doMove, s.during);
                }
                //停止自动运动
                function stopMove(){
                    clearInterval(timer);
                }
                //运动效果
                function doMove(){
                    iCurr = iCurr >= num - 1 ? 0 : iCurr + 1;
                    doAnimate(-moveWidth * iCurr);
                    oFocus.eq(iCurr).addClass("current").siblings().removeClass("current");
                }
                //绑定触摸事件
                function bindTochuEvent(){
                    oMover.get(0).addEventListener('touchstart', touchStartFunc, false);
                    oMover.get(0).addEventListener('touchmove', touchMoveFunc, false);
                    oMover.get(0).addEventListener('touchend', touchEndFunc, false);
                }
                //获取触点位置
                function touchPos(e){
                    var touches = e.changedTouches, l = touches.length, touch, tagX, tagY;
                    for (var i = 0; i < l; i++) {
                        touch = touches[i];
                        tagX = touch.clientX;
                        tagY = touch.clientY;
                    }
                    oPosition.x = tagX;
                    oPosition.y = tagY;
                    return oPosition;
                }
                //触摸开始
                function touchStartFunc(e){
                    clearInterval(timer);
                    touchPos(e);
                    startX = oPosition.x;
                    startY = oPosition.y;
                    temPos = oMover.position().left;
                }
                //触摸移动
                function touchMoveFunc(e){
                    touchPos(e);
                    var moveX = oPosition.x - startX;
                    var moveY = oPosition.y - startY;
                    if (Math.abs(moveY) < Math.abs(moveX)) {
                        e.preventDefault();
                        oMover.css({
                            left: temPos + moveX
                        });
                    }
                }
                //触摸结束
                function touchEndFunc(e){
                    touchPos(e);
                    var moveX = oPosition.x - startX;
                    var moveY = oPosition.y - startY;
                    if (Math.abs(moveY) < Math.abs(moveX)) {
                        if (moveX > 0) {
                            iCurr--;
                            if (iCurr >= 0) {
                                var moveX = iCurr * moveWidth;
                                doAnimate(-moveX, autoMove);
                            }
                            else {
                                doAnimate(0, autoMove);
                                iCurr = 0;
                            }
                        }
                        else {
                            iCurr++;
                            if (iCurr < num && iCurr >= 0) {
                                var moveX = iCurr * moveWidth;
                                doAnimate(-moveX, autoMove);
                            }
                            else {
                                iCurr = num - 1;
                                doAnimate(-(num - 1) * moveWidth, autoMove);
                            }
                        }
                        oFocus.eq(iCurr).addClass("current").siblings().removeClass("current");
                    }
                }
                //移动设备基于屏幕宽度设置容器宽高
                function mobileSettings(){
                    moveWidth = $(window).width();
                    var iScale = $(window).width() / s.width;
                    _this.height(s.height * iScale).width($(window).width());
                    oMover.css({
                        left: -iCurr * moveWidth
                    });
                }
                //动画效果
                function doAnimate(iTarget, fn){
                    oMover.stop().animate({
                        left: iTarget
                    }, _this.speed , function(){
                        if (fn)
                            fn();
                    });
                }
                //判断是否是移动设备
                function isMobile(){
                    if (navigator.userAgent.match(/Android/i) || navigator.userAgent.indexOf('iPhone') != -1 || navigator.userAgent.indexOf('iPod') != -1 || navigator.userAgent.indexOf('iPad') != -1) {

                        return true;
                    }
                    else {
                        return false;
                    }
                }
            });
        }
    })(jQuery);


    var carousel_url = '/mersrv/ms/merpic/carousel/';

    var _state = state || {
            swiper_width:0,
            swiper_height:0,
            swiper_list:[]
        };

    var EntityReact = React.createClass({
        getInitialState: function () {
            // console.log(_state);
            return _state;
        },
        componentDidMount: function(){
            var _this = this;
            var $base = $('#'+divId).find('._Entity_');

            $base.each(function(){
                var $el = $(this);
                var _advertWidth =$el.outerWidth();
                var _advertHeight = _advertWidth / 2;
                // console.log(_advertWidth);
                // console.log(_advertHeight);

                $el.height(_advertHeight);

                //上传至正式环境测试时显示如下代码
                if(_DEV_ID == '{@devId@}') {
                    $el.css({
                        'height': _advertHeight + 'px'
                    }).find('.carousel').css({
                        'height': _advertHeight + 'px',
                        'line-height': _advertHeight + 'px'
                    });

                    return;
                }

                _this.state.swiper_width = _advertWidth;
                _this.state.swiper_height = _advertHeight;

            });

            $.ajax({
                type:'GET',
                url:carousel_url + _CUSTOMER_ID,
                data:{},
                success:function (data) {
                    // console.log(data);
                    if(data.code == 0) {
                        _this.state.swiper_list = data.data.picList;
                        _this.setAuthState();
                        // $('.carousel').yxMobileSlider({width:_state.swiper_width, height:_state.swiper_height,during:3000})
                    }
                },
                error:function () {

                }
            });




        },
        componentDidUpdate: function () {
            $('.carousel').yxMobileSlider({width:_state.swiper_width, height:_state.swiper_height,during:3000})
        },
        //更新组件状态
        setAuthState: function () {
            this.setState(_state);
            // console.log(_state);
        },
        handleOnClick: function (item, eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            // window.open(item.picLink);
            // console.log(item);
            if(item.picLink) {
                window.location.href = item.picLink;
            }
        },
        render: function () {
            var _this = this;
            if(!_state.swiper_list){
                _state.swiper_list=[];
            }
            var indicators = _state.swiper_list.map(function (item, index) {
                if(index == 0) {
                    return <li className="carousel-ul-li" id={"pic_" + index} key={index}>
                        <a href="javascript:void(0)"><img className="carousel-img row-10" src={item.picPath} alt={index} id="img" onClick={_this.handleOnClick.bind(_this, item, 'click')} onTouchStart={_this.handleOnClick.bind(_this, item, 'touch')}/></a>
                    </li>
                }else {
                    return <li className="carousel-ul-li" id={"pic_" + index} key={index} >
                        <a href="javascript:void(0)"><img className="carousel-img row-10" src={item.picPath} alt={index} onClick={_this.handleOnClick.bind(_this, item, 'click')} onTouchStart={_this.handleOnClick.bind(_this, item, 'touch')}/></a>
                    </li>
                }
            });

            return (
                <div className="_Entity_">
                    <div className="carousel clr-fix ">
                        <ul className="carousel-ul" id="carousel-ul">
                            {indicators}
                        </ul>
                    </div>
                </div>
            );

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

/** ---------------------------------------  中间内容区域 结束  ----------------------------------------------------------------------------*/

/** ---------------------------------------  右侧配置区域 开始  ----------------------------------------------------------------------------*/


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

/** ---------------------------------------  右侧配置区域 结束  ----------------------------------------------------------------------------*/

/**
 * DOM 渲染
 */
var entity = _Entity_('', 'entity').render();
var setting = _Setting_(entity, 'setting').render();





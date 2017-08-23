
/** ---------------------------------------  中间内容区域 开始  ----------------------------------------------------------------------------*/

var _Entity_ = function (state, divId) {

    var wallalbum_url = '/mersrv/ms/merpic/wallalbum/';

    var _state = state || {
            photo_list:[],
            showBigPic:false,  //是否显示大图
            outerWidth:0,
            outerHeight:0,
            photo_index:0,  //定位到当前放大哪张图片
            more_show:false,  //是否显示更多按钮
            show_allpic:false, //是否显示全部图片
            show_msg:false  //是否显示未上传照片提示
        };

    var EntityReact = React.createClass({
        getInitialState: function () {
            // console.log(_state);
            return _state;
        },
        componentDidMount: function(){
            var _this = this;

            //上传至正式环境测试时显示如下代码
            if(_DEV_ID == '{@devId@}') {
                return;
            }

            getPhotoWall(_this);

        },
        //更新组件状态
        setAuthState: function () {
            this.setState(_state);
            // console.log(_state);
        },
        handleOnClick: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            // alert('更多');
            _state.show_allpic = true;
            _state.more_show = false;
            this.setAuthState(_state);
        },
        showBigPic: function (index, eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            if(!_state.more_show) {
                _state.showBigPic = true;
                _state.photo_index = index;
                this.setAuthState(_state);

                document.body.style.overflowY = 'hidden';

            }else {
                this.handleOnClick.bind(this, eventType);
            }

        },
        render: function () {

            var _this = this;

            if(!_state.photo_list){
                _state.photo_list=[];
            }
            var photocontent = _state.photo_list.map(function (item, index) {
                if(!_state.show_allpic && index > 2) {
                    return;
                }

                return <div className="image" key={index} style={{backgroundImage:'url(' + item.picPath + ')'}} onClick={_this.showBigPic.bind(_this, index, 'click')} onTouchStart={_this.showBigPic.bind(_this, index, 'touch')}>
                </div>
            });

            return (
            <div className="_Entity_">
                <div className="container" style={_state.show_allpic ? {marginBottom:'15px'} : {marginBottom:'0px'}}>
                    <div className="title_container">
                        <div className="title_content">
                            <span>照片墙</span>
                        </div>
                        <div className="title_more" onClick={this.handleOnClick.bind(this, 'click')} onTouchStart={this.handleOnClick.bind(this, 'touch')}>
                            <span>{_state.more_show ? '更多' : ''}</span>
                        </div>
                    </div>
                    {!_state.show_msg ? <div className="photo_container">{photocontent}</div>
                        : <div className="no_photo_container">
                            <div className="no_photo_title">
                                <span>商户暂未上传照片</span>
                            </div>
                        </div>}
                    {this.state.showBigPic ? <ImageShowComponent setAuthState={this.setAuthState}/> : ''}
                    <div className="photo_line" style={_state.show_allpic ? {marginBottom:'-15px'} : {marginBottom:'0px'}}></div>
                </div>

            </div>
            );
        }
    });

    var startX;
    var startY;

    var ImageShowComponent = React.createClass({
        getInitialState: function () {
            // console.log(_state);
            return null;
        },
        handleClose: function (eventType) {
            if(!isEventValid(eventType)){//判断触发的事件是否有效,其中：click适用于PC端、touch适用于移动端
                return;
            }
            // alert(111);
            _state.showBigPic = false;
            this.props.setAuthState();

            document.body.style.overflowY = 'visible';
        },
        componentDidMount: function () {

        },
        render: function(){
            var _this = this;

            var temp_top = document.body.scrollTop;
            // console.log('msphotowall_top======' + temp_top);
            var temp_width = window.screen.width;
            var temp_height = window.screen.height;

            var temp_src = _state.photo_list[_state.photo_index].picPath;
            var temp_alt = _state.photo_list[_state.photo_index].picTitle;

            return (
                <div className="photowall-overlay" style={{width:temp_width, height:temp_height}} onClick={this.handleClose.bind(this, 'click')} onTouchStart={this.handleClose.bind(this, 'touch')}>
                    <div id="myCarousel" className="carousel slide">
                        <div className="carousel-inner" style={{width:'100%', height:'100%'}}>
                            <div className="item active image" style={{width:'100%', height:'100%'}}>
                                <img className="bigimg" src={temp_src} alt={temp_alt}/>
                            </div>
                        </div>
                    </div>
                </div>
            );
        }
    });

    /**
     *
     * @param _this
     * @param status status为click时为点击更多按钮的操作、为空则为默认显示状态
     */
    function getPhotoWall(_this) {
        $.ajax({
            type:'GET',
            url:wallalbum_url + _CUSTOMER_ID,
            data:{},
            success: function (data) {
                // console.log(data);
                if(data.code == 0) {
                    _state.photo_list = data.data.picList;
                    if(!data.data.picList || data.data.picList.length == 0) {
                        _state.show_msg = true;
                    }else {
                        _state.show_msg = false;
                    }

                    if(data.data.picAccount <= 3) {
                        _state.more_show = false;
                    }else {
                        _state.more_show = true;
                    }
                }else {
                    _state.show_msg = true;
                    _state.more_show = false;
                }
                _this.setAuthState(_state);
            },
            error: function () {
                _state.show_msg = true;
                _state.more_show = false;
            }
        });
    }

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
                <div className="form-group control-label">&emsp;此组件无需进行设置（照片墙）</div>
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





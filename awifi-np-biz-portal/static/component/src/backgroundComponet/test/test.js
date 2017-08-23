
/**
 * 文本组件
 * @param state对象数据
 * @param divId Div唯一标识
 * @returns {{render: render}}
 * @Auther: zhuxh
 * @Date: 2015-12-29
 */

var _Entity_ = function(state, divId) {
    var _state = state || {
            imgSrc:'',//背景图片路径
            aHref: 'http://201512awifi.alltosun.net/api/get_background_res'//第三方接口地址
        };

    var EntityReact = React.createClass({

        getInitialState: function () {
            return _state;
        },
        componentWillMount: function(){//在组件渲染之前获得背景图
            if(_DEV_ID == '{@devId@}'){
                var encryptionUrl="http://127.0.0.1/app/encryption";//加密地址
                console.log(encryptionUrl);
                var imgUrl=this.state.aHref;//获取背景图片地址，
                imgUrl+=this.state.aHref.indexOf('?') != -1 ? 'params=' : '?params='
                console.log("++++"+imgUrl);
                var OnGetMemberSuccess = function (data) {//参数加密成功以后执行
                    //alert(JSON.stringify(data));
                    if(data.result == 'OK'){//接口返回参数加密成功后执行
                        imgUrl+=data.params;
                        console.log("imgUrl2:"+imgUrl);

                        $("#backgroundPic").html("<img src='"+imgUrl+"'/>");
                        /*$.ajax({
                            url: imgUrl,
                            dataType: 'JSONP',
                            jsonp: 'callback',
                            header:{
                                'cache-control': 'no-cache'
                            },
                            success: function(data1){
                                alert(JSON.stringify(data1));
                                this.state.imgSrc=data1.bgImg;
                            }
                        });*/
                        /*<img src="xx?pa=xx" />*/
                    }
                };
                var option = {
                    deviceId:_DEV_ID,// 设备id
                    userIp:_USER_IP,// 用户ip
                    userMac:_USER_MAC,// 用户MAC
                    userPhone:_USER_PHONE,// 用户手机号
                    terminalType:$.browser.mobileOS,// 终端类型*/
                    customerId:_CUSTOMER_ID,// 营业厅id
                };
                console.log("option"+option);
                $.ajax({
                    url: encryptionUrl,
                    dataType: 'JSONP',
                    jsonp: 'callback',
                    header:{
                        'cache-control': 'no-cache'
                    },
                    data:option,
                    success: OnGetMemberSuccess
                });
            }
        },
        render: function () {
            if(_DEV_ID == '{@devId@}') {
                return (

                        <div id="backgroundPic" className="backgroundPic">背景组件</div>
                )
            } else {
                return (
                    <div class="_Entity_">
                        <div id="backgroundPic" className="backgroundPic_phone"></div>
                    </div>
                )
            }
        }
    });
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

// 拉portal调用方式
// _Entity_(state, divId).render(); 组件拉了多次则调用多次

/*
 1) 写入页面中相应的Div元素，每个div的id保证唯一性
 2）合并多个组件js文件至一个js文件中，且在合并的js文件末尾加个要调用 ReactJs 的方法
 3）合并多个组件css文件至一个css文件中
 */


var _Setting_ = function (entity, divId) {
    var setting = null;

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var SettingReact = React.createClass({
        getInitialState: function() {
            return {
                aHref: entity.state.aHref
            };
        },
        eventAHref: function(event) {
            var _state = {
                'aHref': event.target.value
            };
            setStates(_state);
        },
        render: function () {
            return (
                <div className="container _Setting_">
                    <form className="form-horizontal">
                        <div className="form-group">
                            <label for="url" className="col-sm-2 control-label">URL：</label>
                            <div className="col-sm-9">
                                <input type="text" className="form-control" id="url" value={this.state.aHref} onChange={this.eventAHref} />
                            </div>
                        </div>
                    </form>
                </div>
            );
        }
    });

    function render() {
        console.log('this.entity=>', entity);
        setting = ReactDOM.render(<SettingReact />, document.getElementById(divId));
        console.log('setting = React.render => ', setting);
        return setting;
    }

    return {
        setting: setting,
        setStates: setStates,
        render: render
    }
};

var entity = _Entity_('', 'entity').render();

var setting = _Setting_(entity, 'setting').render();
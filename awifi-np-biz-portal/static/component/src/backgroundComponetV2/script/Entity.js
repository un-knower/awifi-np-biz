var _Entity_ = function (state, divId) {

    var _state = state || {
            imgSrc: 'images/defaultPicComponent.png', //第三方接口地址(背景图片路径)images/defaultPicComponent.png
            oldImgSrc: '', //旧图片路径，防止垃圾数据
            showImgUpload: false, //用于控制是否显示图片上传图层
            mode: 'local', // remote远程图片  local本地图片
        };

    var BgEntityReact = React.createClass({

        getInitialState: function () {
            return _state;
        },
        componentDidMount: function () {//在组件渲染之前获得背景图
            if (_DEV_ID != '{@devId@}') {
                // 远程图片需加密
                if (this.state.mode == 'remote') {
                    /* 许小满  2016-10-08 注释
                    var encryptionUrl = "/app/getBgSource";//加密地址
                    var imgUrl = this.state.imgSrc;//获取背景图片地址，
                    imgUrl += this.state.imgSrc.indexOf('?') != -1 ? 'params=' : '?params=';
                    var OnGetMemberSuccess = function (data) {//参数加密成功以后执行
                        if (data.result == 'OK') {//接口返回参数加密成功后执行
                            imgUrl += data.params;
                            $("#backgroundPic").html("<img src='" + imgUrl + "'/>");
                        }
                    };
                    var option = {
                        deviceId: _DEV_ID,// 设备id
                        userIp: _USER_IP,// 用户ip
                        userMac: _USER_MAC,// 用户MAC
                        userPhone: _USER_PHONE,// 用户手机号
                        terminalType: $.browser.mobileOS,// 终端类型*!/
                        customerId: _CUSTOMER_ID,// 营业厅id
                    };
                    $.ajax({
                        url: encryptionUrl,
                        dataType: 'JSONP',
                        jsonp: 'callback',
                        header: {
                            'cache-control': 'no-cache'
                        },
                        data: option,
                        success: OnGetMemberSuccess
                    });
                    */
                    var encryptionUrl = "/app/getBgSource?";//加密地址
                    var option = {
                        deviceId: _DEV_ID, // 设备id
                        devMac: _DEV_MAC, // 设备mac
                        userIp: _USER_IP, // 用户ip
                        userMac: _USER_MAC, // 用户MAC
                        userPhone: _USER_PHONE, // 用户手机号
                        terminalType: $.browser.mobileOS, // 终端类型
                        customerId: _CUSTOMER_ID, // 营业厅id
                        url: escape(this.state.aHref) // 请求第3方资源url
                    };
                    var api_url = encryptionUrl + $.param(option);
                    $("#backgroundPic").html('<img src="' + api_url + '"/>');

                    var doc_hgt = $(document).height();
                    var body_hgt = $('html').height();
                    $("#backgroundPic img").css({
                        height: Math.max(doc_hgt, body_hgt) + "px"
                    });

                    // 页面加载完成再计算高度
                    $(function () {
                        var doc_hgt = $(document).height();
                        var body_hgt = $('html').height();
                        $("#backgroundPic img").css({
                            height: Math.max(doc_hgt, body_hgt) + "px"
                        });
                    });

                } else {
                    // 本地图片无需加密
                    $("#backgroundPic").html("<img src='" + this.state.imgSrc + "'/>");
                    var doc_hgt = $(document).height();
                    var body_hgt = $('html').height();
                    $("#backgroundPic img").css({
                        height: Math.max(doc_hgt, body_hgt) + "px"
                    });

                    // 页面加载完成再计算高度
                    $(function () {
                        var doc_hgt = $(document).height();
                        var body_hgt = $('html').height();
                        $("#backgroundPic img").css({
                            height: Math.max(doc_hgt, body_hgt) + "px"
                        });
                    });

                    // css3 背景图
                    // $('body').css({
                    //     'backgroundImage': 'url(' + this.state.imgSrc + ')',
                    //     'backgroundRepeat': 'no-repeat',
                    //     'backgroundSize': '100% 100%',
                    //     'backgroundAttachment': 'fixed'
                    // });
                }
            }
        },
        render: function () {
            var htm = null;
            if (_DEV_ID == '{@devId@}') {
                // 无法判断当前是否预览模式
                htm =  (
                    <div className="_Entity_">
                        <div id="backgroundPic" className="backgroundPic">背景组件</div>
                    </div>
                )
            } else {
                htm = (
                    <div className="_Entity_">
                        <div id="backgroundPic" className="backgroundPic_phone"></div>
                    </div>
                )
            }
            return htm;
        }
    });

    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
    function render() {
        return ReactDOM.render(<BgEntityReact />, document.getElementById(divId));
    }

    return {
        render: render
    }
};
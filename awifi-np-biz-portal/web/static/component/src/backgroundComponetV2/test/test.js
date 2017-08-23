/**
 * 文本组件
 * @param state对象数据
 * @param divId Div唯一标识
 * @returns {{render: render}}
 * @Auther: zhuxh
 * @Date: 2015-12-29
 */

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
        componentWillMount: function () {//在组件渲染之前获得背景图
            if (_DEV_ID != '{@devId@}') {
                // 远程图片需加密
                if (this.state.mode == 'remote') {
                    var encryptionUrl = "/app/encryption";//加密地址
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
                        terminalType: $.browser.mobileOS,// 终端类型*/
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
                } else {
                    // 本地图片无需加密
                    $("#backgroundPic").html("<img src='" + this.state.imgSrc + "'/>");
                }
            }
        },
        render: function () {
            var htm = null;
            if (_DEV_ID == '{@devId@}') {
                // 无法判断当前是否预览模式
                htm =  (
                    <div id="backgroundPic" className="backgroundPic">背景组件</div>
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

// 拉portal调用方式
// _Entity_(state, divId).render(); 组件拉了多次则调用多次

/*
 1) 写入页面中相应的Div元素，每个div的id保证唯一性
 2）合并多个组件js文件至一个js文件中，且在合并的js文件末尾加个要调用 ReactJs 的方法
 3）合并多个组件css文件至一个css文件中
 */


var _Setting_ = function (entity, divId) {
    var setting = null;
    var _state = entity.state;
    // 默认隐藏上传图片层
    _state.showImgUpload = false;

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var BgSettingReact = React.createClass({
        getInitialState: function () {
            return _state;
        },
        eventSave: function (event) {
            var mode = $('input[name="mode"]:checked').val();
            if (mode == 'remote') {
                var imgSrc = $('#url').val();
                if (!imgSrc || !/^http(s)?:\/\/.*/.test(imgSrc)) {
                    awifiUtils.alert_tips('comp-setting', '请输入远程图片http://开头的API地址');
                    return ;
                }
            } else {
                var imgSrc = $('#imgValue').prop('src');
                if (!imgSrc || imgSrc.indexOf('defaultPicComponent.png') != -1) {
                    awifiUtils.alert_tips('comp-setting', '请上传背景图片');
                    return ;
                }
            }
            _state.mode = mode;
            _state.imgSrc = imgSrc;
            setStates(_state);
        },
        //更新状态，供子组件调用
        updateState: function () {
            this.setState(_state);
        },
        //图片上传点击事件
        handleImgUploadClick: function () {
            _state.showImgUpload = true; //显示图片上传图层
            setStates(_state);
        },
        render: function () {
            return (
                <div className="container _Setting_">
                    <form className="form-horizontal">
                        <h2 className="form-title">背景图组件配置</h2>
                        <div className="form-group">
                            <div className="col-sm-9">
                                <label className="radio-inline"><input type="radio" name="mode" value="remote" defaultChecked={this.state.mode == 'remote'}/>远程背景图片URL</label>
                            </div>
                        </div>
                        <div className="form-group">
                            <label htmlFor="url" className="col-sm-2 control-label">URL：</label>
                            <div className="col-sm-9">
                                <textarea className="form-control" id="url" defaultValue={this.state.imgSrc.indexOf('defaultPicComponent.png') != -1 ? '' : this.state.imgSrc}></textarea>
                            </div>
                        </div>
                        <div id="hr-line"></div>
                        <div className="form-group">
                            <div className="col-sm-9">
                                <label className="radio-inline"><input type="radio" name="mode" value="local" defaultChecked={this.state.mode == 'local'}/>上传背景图片</label>
                            </div>
                        </div>
                        { this.state.showImgUpload ? <UpImageComponent updateState={this.updateState}/> : '' }
                        <div className="warp-title">选择图片</div>
                        <div className="photos">
                            <div className="photo" onClick={this.handleImgUploadClick}>
                                <div className="pre background">
                                    <div className="del" title="删除"></div>
                                    <img id="imgValue" src={this.state.mode == 'local' && this.state.imgSrc ? this.state.imgSrc : '/images/defaultPicComponent.png'} alt=""/>
                                </div>
                            </div>
                            <div className="tip">文件大小小于200KB，支持jpg、png格式，建议jpg格式</div>
                        </div>
                        <div className="form-group form-foot">
                            <button id="save" type="button" className="btn btn-danger btn-block" onClick={this.eventSave}>保&emsp;存</button>
                        </div>
                    </form>
                </div>
            );
        }
    });

    var UpImageComponent = React.createClass({
        //文件对象
        _file: null,
        //子组件状态
        option: {
            uploadBtnDisabled: true, //用于控制上传按钮 disabled
            imgSrc: "" //图片路径
        },
        //状态初始化函数
        getInitialState: function () {
            return this.option;
        },
        //关闭按钮单击事件
        handleCloseClick: function () {
            _state.showImgUpload = false;//隐藏图片上传弹出层
            this.props.updateState();//更新父组件状态
        },
        //取消按钮单击事件
        handleCancelClick: function () {
            _state.showImgUpload = false;//隐藏图片上传弹出层
            this.props.updateState();//更新父组件状态
        },
        //文件变更事件
        handleFileChange: function (e) {
            var file = e.target.files[0];
            var reader = new FileReader();
            //判断文件类型
            var _this = this;
            if (file.type.match(/image*/)) {
                reader.onload = function (e) {
                    _this.option.imgSrc = e.target.result;//更改图片路径
                    _this.setState(_this.option);//更新组件状态
                };
                reader.readAsDataURL(file);
                this._file = file;
                this.option.uploadBtnDisabled = false;//按钮取消disabled
                this.setState(this.option);//更新组件状态
            } else {
                this.option.uploadBtnDisabled = true;//按钮设置disabled
                this.option.imgSrc = "";
                this.setState(this.option);//更新组件状态
                alert('请上传图片!');
            }
        },
        //上传点击事件
        handleUploadClick: function (e) {
            var _this = this;
            var url = '/thumb/picupload?oldImgSrc=' + _state.oldImgSrc;
            var options = {
                //exception:function exception(data){alert(data)},
                //另外的一些属性:
                url: url, // 默认是form的action，如果写的话，会覆盖from的action.
                type: 'post',        // 默认是form的method，如果写的话，会覆盖from的method.('get' or 'post').
                dataType: 'json',        // 'xml', 'script', or 'json' (接受服务端返回的类型.)
                clearForm: false,       // 成功提交后，清除所有的表单元素的值.
                resetForm: false,        // 成功提交后，重置所有的表单元素的值.
                //timeout:3000, 	//由于某种原因,提交陷入无限等待之中,timeout参数就是用来限制请求的时间,当请求大于3秒后，跳出请求.
                beforeSubmit: function () {
                    return true;
                },  // 提交前
                success: function (data) {  // 提交后
                    if (data.result == 'FAIL') {
                        alert(data.message);
                        return;
                    }
                    //延迟2秒执行
                    setTimeout(function () {
                        //1. 隐藏弹出层
                        _state.showImgUpload = false;//隐藏图片上传弹出层
                        //2. 更新父组件的图片
                        var url = data.path;//文件地址
                        _state.imgSrc = url;//图片路径
                        _state.oldImgSrc = url;//图片路径
                        //3. 更新父组件状态
                        _this.props.updateState();
                    }, 5000);

                }
            };
            var $form = $(e.target).closest('form');
            $form.ajaxSubmit(options); //'ajaxForm' 方式的表单
        },
        //渲染
        render: function () {
            return (
                <div className="photoh5-overlay">
                    <form name="fileForm" method="post" encType="multipart/form-data">
                        <div className="photoh5">
                            <div className="ph5-warp ph5-title">
                                添加图片
                                <div className="ph5-close" onClick={ this.handleCloseClick }></div>
                            </div>
                            <div className="ph5-body">
                                <div className="ph5-warp">
                                    <div className="browse-file">
                                        选择图片
                                        <input type="file" name="imgFile" onChange={ this.handleFileChange }/>
                                    </div>
                                    <span className="borwse-tip">文件大小需小于200K</span>
                                </div>
                                <div className="ph5-warp ph5-preview">
                                    <div className="ph5-pre-jcrop">
                                        <div className="ph5-pre-jcrop">
                                            <img alt="" src={ this.state.imgSrc }/>
                                        </div>
                                    </div>
                                </div>
                                <div className="ph5-warp ph5-toolbar">
                                    <input type="button" className="ph5-upload" value="上传" disabled={ this.state.uploadBtnDisabled } onClick={ this.handleUploadClick }/>
                                    <input type="button" className="ph5-cancel" value="取消" onClick={ this.handleCancelClick }/>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            );
        }
    });

    function render() {
        setting = ReactDOM.render(<BgSettingReact />, document.getElementById(divId));
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
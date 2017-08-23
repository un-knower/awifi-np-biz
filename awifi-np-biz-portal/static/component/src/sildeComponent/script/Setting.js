/** *
 * 单图组件 Setting
 * @param state
 * @param divId
 * @returns {{render: render}}
 * Created by Shin on 2016/1/18.
 */

var _Setting_ = function (entity, divId) {

    var setting = null;

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var _state = entity.state || {
            showImgUpload: false, //用于控制是否显示图片上传图层
            currentImg: 1, // 当前操作图片标识
            indexImg: 3, // 添加图片序号
            slideshowSpeed: '3000', // 滑动内容展示时间
            showTitle: '0', // 是否显示图片标题
            titleColor: '', // 图片标题颜色
            uploadImgSrc: '', // 上传图片的路径
            imgData: [{
                'imgId': 1,
                'imgSrc': '',
                'oldImgSrc': '',
                'imgText': '',
                'linkSrc': ''
            }, {
                'imgId': 2,
                'imgSrc': '',
                'oldImgSrc': '',
                'imgText': '',
                'linkSrc': ''
            }, {
                'imgId': 3,
                'imgSrc': '',
                'oldImgSrc': '',
                'imgText': '',
                'linkSrc': ''
            }] // 初始化3张图片
        };

    // 设置 _state 对象的值，并同步_state状态树
    function setImgDataItem(imgId, itemData) {
        var imgData = _state.imgData;
        for (var i= 0,len=imgData.length; i<len; i++) {
            var item = imgData[i];
            if (item.imgId == imgId) {
                for (var key in itemData) {
                    item[key] = itemData[key];
                }
                break;
            }
        }
        setStates(_state);
    }

    // 删除组件后，刷新_state状态树
    function delImgDataItem(imgId) {
        var imgData = _state.imgData;
        for (var i=0,len=imgData.length; i<len; i++) {
            var item = imgData[i];
            if (item.imgId == imgId) {
                imgData.splice(i, 1);
                break;
            }
        }
        setStates(_state);
    }

    // 获取指定imgData的数据
    function getImgDataItem(imgId) {
        var imgData = _state.imgData;
        for (var i=0,len=imgData.length; i<len; i++) {
            var item = imgData[i];
            if (item.imgId == imgId) {
                return item;
                break;
            }
        }
        return {};
    }

    // 单个设置图片内容组件
    var ImageItemComponent = React.createClass({
        getInitialState: function() {
            return this.props.img;
        },
        //图片上传点击事件
        handleImgUploadClick: function (event) {
            var imgId = this.props.img.imgId;
            var imgData = getImgDataItem(imgId);
            _state.currentImg = imgId;
            _state.showImgUpload = true; //显示图片上传图层
            _state.uploadImgSrc = imgData.imgSrc;
            setStates(_state);
        },
        handleImgText: function (event) {
            setImgDataItem(this.state.imgId, {
                'imgText': event.target.value
            });
        },
        handleLinkSrc: function (event) {
            setImgDataItem(this.state.imgId, {
                'linkSrc': event.target.value
            });
        },
        handleImgDel: function (event) {
            var imgId = this.props.img.imgId;
            if (_state.imgData.length <= 1) {
                awifiUtils.alert_tips('setting', '不能删除图片');
            } else {
                delImgDataItem(imgId);
            }
        },
        componentWillReceiveProps: function (nextProps) {
            // 更新props值至当前组件state
            this.setState(nextProps.img);
        },
        render: function () {
            return (
                <div className="photos" id={this.props.img.imgId}>
                    <div className="photo clearfix">
                        <div className="del" title="删除" onClick={this.handleImgDel}></div>
                        <div className="pre" onClick={this.handleImgUploadClick} id={this.props.img.imgId}>
                            <div className={this.props.img.imgSrc ? "pre-add pre-saved" : "pre-add"}></div>
                            <img src={this.props.img.imgSrc} alt="" />
                        </div>
                        <div className="cfg cfg-fr">
                            <input title="标题" name="title" data-v={this.props.img.imgText} value={this.state.imgText} onChange={this.handleImgText} placeholder="请输入标题" />
                        </div>
                        <div className="cfg">
                            <input title="链接" name="url" data-v={this.props.img.linkSrc} value={this.state.linkSrc} onChange={this.handleLinkSrc} placeholder="请输入链接" />
                        </div>
                    </div>
                </div>
            )
        }
    });

    var ImageListComponent = React.createClass({
        //状态初始化函数
        getInitialState: function() {
            return _state;
        },

        componentDidMount: function () {
            $("#title_color").colorpicker({
                transparentColor: false
            });
        },

        addPic: function () {
            var imgData = _state.imgData;
            _state.indexImg += 1;
            if (imgData.length == 6) {
                awifiUtils.alert_tips('alert_tips', '最多添加6张图片');
                return ;
            }

            imgData.push({
                'imgId': _state.indexImg,
                'imgSrc': '',
                'oldImgSrc': '',
                'imgText': '',
                'linkSrc': ''
            });

            setStates(_state);
        },

        // 设置一行显示图片1-4张
        handlerSlideshowSpeed: function (event) {
            _state.slideshowSpeed = event.target.value;
            setStates(_state);
        },

        handlerSaveData: function () {
            var imgElements = $('._Setting_').find('.photos');
            var titleColor = $('#title_color').val();
            var showTitle = _state.showTitle;
            _state.titleColor = titleColor;
            var imgData = _state.imgData;
            if (imgData.length < 3) {
                awifiUtils.alert_tips('alert_tips', '至少添加3张图片');
                return false;
            }
            imgElements.each(function (index, ele) {
                var element = $(this);
                var id = element.attr('id');
                var imgSrc = element.find('img').attr('src');
                var title = element.find('input[name="title"]').val();
                var url = element.find('input[name="url"]').val();
                if (!imgSrc) {
                    awifiUtils.alert_tips('alert_tips', '请上传第'+(parseInt(index)+1)+'张图片');
                    return false;
                }
                if (showTitle == '1') {
                    // 显示标题时不能为空
                    if (!title) {
                        awifiUtils.alert_tips('alert_tips', '请输入第'+(parseInt(index)+1)+'张图片标题');
                        return false;
                    }
                }
                /*if (!url) {
                     awifiUtils.alert_tips('alert_tips', '请输入第'+(parseInt(index)+1)+'张图片链接');
                     return false;
                 }*/
                if (url) {
                    // 链接有值时校验是否正确
                    if (!/((http|ftp|https|file):\/\/([\w\-]+\.)+[\w\-]+(\/[\w\u4e00-\u9fa5\-\.\/?\@\%\!\&=\+\~\:\#\;\,]*)?)/ig.test(url)) {
                        awifiUtils.alert_tips('alert_tips', '请输入第'+(parseInt(index)+1)+'张图片链接地址不正确');
                        return false;
                    }
                }
                setImgDataItem(id, {
                    'imgText': title,
                    'linkSrc': url
                });
            });
        },

        handlerShowTitle: function (event) {
            _state.showTitle = event.target.value;
            setStates(_state);
        },

        //渲染
        render: function () {
            var state = this.state;
            var imgComp = state.imgData.map(function (img) {
                return <ImageItemComponent img={img} />
            });

            return (
                <div className="_Setting_ container">
                    <div id="alert_tips"></div>
                    <div className="img_ext">幻灯片信息（1-6张图片，建议图片尺寸480*360像素，每张图片200KB以内，图片尺寸须一致，格式为PNG、JPG，建议JPG格式）</div>
                    { this.state.showImgUpload ? <UpImageComponent imgSrc={state.uploadImgSrc} />: '' }

                    {imgComp}

                    <div className="warp-border txr">
                        <button id="add" className="btn btn-default btn-sm" onClick={this.addPic}>添加图片</button>
                    </div>
                    <div className="warp-border">
                        <span className="item">标题字体颜色：</span>
                        <input type="text" className="ipt_text" id="title_color" defaultValue={state.titleColor} readOnly="readonly" placeholder="请选择颜色" />
                    </div>
                    <div className="warp-border">
                        <span className="item">轮播设置：</span>
                        <select id="row_size" value={this.state.slideshowSpeed} onChange={this.handlerSlideshowSpeed}>
                            <option value="0">不轮播</option>
                            <option value="1000">1秒</option>
                            <option value="3000">3秒</option>
                            <option value="5000">5秒</option>
                            <option value="10000">10秒</option>
                        </select> 自动切换至下一张图片
                    </div>
                    <div className="warp-border">
                        <span className="item">标题是否显示：</span>
                        <label className="radio-inline2"><input type="radio" name="showTitle" value="1" defaultChecked={this.state.showTitle == '1'} onClick={this.handlerShowTitle} />是</label>
                        <label className="radio-inline2"><input type="radio" name="showTitle" value="0" defaultChecked={this.state.showTitle == '0'} onClick={this.handlerShowTitle} />否</label>
                    </div>
                    <div className="warp-border txc">
                        <button id="save" className="btn btn-danger btn-sm" onClick={this.handlerSaveData}>保&emsp;存</button>
                    </div>
                </div>
            );
        }
    });

    /**
     * 上传图片的子组件，当点击添加时将该子组件添加到container的后面，warp-title的前面
     */
    var UpImageComponent = React.createClass({
        //文件对象
        _file: null,
        //状态初始化函数
        getInitialState: function() {
            return {
                uploadBtnDisabled: true, //用于控制上传按钮 disabled
                imgSrc: this.props.imgSrc //图片路径
            };
        },
        //关闭按钮单击事件
        handleCloseClick: function(){
            _state.showImgUpload = false; //隐藏图片上传弹出层
            setStates(_state);
            //this.props.updateState();//更新父组件状态
        },
        //取消按钮单击事件
        handleCancelClick: function(){
            _state.showImgUpload = false; //隐藏图片上传弹出层
            setStates(_state);
            //this.props.updateState();//更新父组件状态
        },
        //文件变更事件
        handleFileChange: function(e){
            var file = e.target.files[0];
            var reader = new FileReader();
            //判断文件类型
            var _this = this;
            if (file.type.match(/image*/)) {
                reader.onload = function (e) {
                    //更新组件状态
                    _this.setState({
                        'imgSrc': e.target.result //更改图片路径
                    });
                };
                reader.readAsDataURL(file);
                this._file = file;
                //更新组件状态
                this.setState({
                    uploadBtnDisabled: false //按钮取消disabled
                });
            } else {
                //更新组件状态
                this.setState({
                    'uploadBtnDisabled': true, //按钮设置disabled
                    'imgSrc': ''
                });
                alert('请上传图片!');
            }
        },
        //上传点击事件
        handleUploadClick: function(e){
            var currentImg = _state.currentImg;
            var currentImgData = getImgDataItem(currentImg);
            var _this = this;
            var url = '/thumb/picupload?oldImgSrc=' + currentImgData.imgSrc;
            var options={
                //exception:function exception(data){alert(data)},
                //另外的一些属性:
                url:url, // 默认是form的action，如果写的话，会覆盖from的action.
                type:'post',        // 默认是form的method，如果写的话，会覆盖from的method.('get' or 'post').
                dataType:'json',        // 'xml', 'script', or 'json' (接受服务端返回的类型.)
                clearForm: false,       // 成功提交后，清除所有的表单元素的值.
                resetForm: false,        // 成功提交后，重置所有的表单元素的值.
                //timeout:3000, 	//由于某种原因,提交陷入无限等待之中,timeout参数就是用来限制请求的时间,当请求大于3秒后，跳出请求.
                beforeSubmit:function() {
                    // 提交前
                    return true;
                },
                success: function(data) {
                    // 提交后
                    if(data.result == 'FAIL'){
                        alert(data.message);
                        return;
                    }
                    //延迟2秒执行
                    setTimeout(function(){
                        //1. 隐藏弹出层
                        _state.showImgUpload = false;//隐藏图片上传弹出层
                        //2. 更新父组件的图片
                        setImgDataItem(currentImg, {
                            'imgSrc': data.path,
                            'oldImgSrc': data.path
                        });
                        //3. 更新父组件状态
                        //_this.props.updateState();
                    }, 5000);

                }
            };
            var $form = $(e.target).closest('form');
            $form.ajaxSubmit(options); //'ajaxForm' 方式的表单
        },
        //渲染
        render: function(){
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
                                            <img src={this.state.imgSrc} alt="" />
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

    /**
     * 渲染DOM
     */
    function render() {
        setting = ReactDOM.render(<ImageListComponent />, document.getElementById(divId));
        return setting;
    }

    /**
     * 暴露对象属性及方法
     */
    return {
        setting: setting,
        setStates: setStates,
        render: render
    }
};
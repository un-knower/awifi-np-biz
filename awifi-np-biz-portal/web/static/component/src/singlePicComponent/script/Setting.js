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
            imgSrc: "", //图片路径
            oldImgSrc: "", //旧图片路径，防止垃圾数据
            showImgUpload: false //用于控制是否显示图片上传图层
        };

    var ImageComponent = React.createClass({
        //状态初始化函数
        getInitialState: function() {
            return _state;
        },
        //更新状态，供子组件调用
        updateState: function(){
            this.setState(_state);
        },
        //图片上传点击事件
        handleImgUploadClick: function(){
            _state.showImgUpload = true;//显示图片上传图层
            this.setState(_state);
        },
        //保存按钮点击事件
        handleSaveClick: function(){
            entity.setState(_state);
        },

        //渲染
        render: function () {
            return (
                <div className="container">
                    { this.state.showImgUpload ? <UpImageComponent updateState={ this.updateState }/>: '' }
                    <div className="warp-title">选择图片</div>
                    <div className="photos">
                        <div className="photo" onClick= { this.handleImgUploadClick }>
                            <div className="pre background">
                                <div className="del" title="删除"></div>
                                <img id="imgValue" src={ this.state.imgSrc } alt="" />
                            </div>
                        </div>
                        <div className="tip">建议图片尺寸：480*360像素，文件大小小于200KB，支持jpg、png格式，建议jpg格式</div>
                    </div>
                    <div className="warp-border txc savebtn">
                        <button id="save" className="btn btn-danger btn-sm" onClick={ this.handleSaveClick }>保&emsp;存</button>
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
        //子组件状态
        option: {
            uploadBtnDisabled: true, //用于控制上传按钮 disabled
            imgSrc: "" //图片路径
        },
        //状态初始化函数
        getInitialState: function() {
            return this.option;
        },
        //关闭按钮单击事件
        handleCloseClick: function(){
            _state.showImgUpload = false;//隐藏图片上传弹出层
            this.props.updateState();//更新父组件状态
        },
        //取消按钮单击事件
        handleCancelClick: function(){
            _state.showImgUpload = false;//隐藏图片上传弹出层
            this.props.updateState();//更新父组件状态
        },
        //文件变更事件
        handleFileChange: function(e){
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
            }else{
                this.option.uploadBtnDisabled = true;//按钮设置disabled
                this.option.imgSrc = "";
                this.setState(this.option);//更新组件状态
                alert('请上传图片!');
            }
        },
        //上传点击事件
        handleUploadClick: function(e){
            //var _this = this;
            //if(!this._file){
            //    alert('请选择图片！');
            //    return;
            //}
            //var formData = new FormData();
            //var params = {//参数
            //    oldImgSrc: _state.oldImgSrc //旧图片路径
            //};
            //for(var key in params){
            //    formData.append(key, params[key]); // 添加参数
            //}
            ////formData.append(opts.name, $ph5.find('.ph5-preview .ph5-pre-jcrop img').attr('src')); // 上传base64
            //formData.append('imageFile', this._file); // 上传二进制
            //var xhr = new XMLHttpRequest();
            //xhr.open('POST', '/thumb/picupload', true);
            //xhr.setRequestHeader('Content-Type', 'multipart/form-data;boundary=abc');
            //xhr.onload = function (e) {
            //    console.log(this.status);
            //    console.log(this.responseText);
            //    if (this.status == 200) {
            //        var rlt;
            //        try{
            //            //{"ResultCode": "1", "Message": "Accountid or Password error!", Data:{ Path:"/media/png/thumb/201503/31/_W3ImWm0wNp7Ws3z2cT6Kd7Io/_W3ImWm0wNp7Ws3z2cT6Kd7Io.png" }}
            //            rlt = eval( '(' + this.responseText + ')');
            //        }catch(err){
            //            rlt = { ResultCode: 1, Message:'Eval object string error!' };
            //        }
            //        if(rlt.result == 'OK'){ // 接口成功
            //            alert('图片上传成功！');
            //        }else{ // 接口失败
            //            alert(['错误: 接口失败' + rlt.message]);
            //        }
            //    } else {
            //        alert('错误: Error:' + this.statusText);
            //    }
            //};
            //xhr.send(formData);


            var _this = this;
            var url = '/thumb/picupload?oldImgSrc=' + _state.oldImgSrc;
            var options={
                //exception:function exception(data){alert(data)},
                //另外的一些属性:
                url:url, // 默认是form的action，如果写的话，会覆盖from的action.
                type:'post',        // 默认是form的method，如果写的话，会覆盖from的method.('get' or 'post').
                dataType:'json',        // 'xml', 'script', or 'json' (接受服务端返回的类型.)
                clearForm: false,       // 成功提交后，清除所有的表单元素的值.
                resetForm: false,        // 成功提交后，重置所有的表单元素的值.
                //timeout:3000, 	//由于某种原因,提交陷入无限等待之中,timeout参数就是用来限制请求的时间,当请求大于3秒后，跳出请求.
                beforeSubmit:function(){return true;},  // 提交前
                success: function(data){  // 提交后
                    if(data.result == 'FAIL'){
                        alert(data.message);
                        return;
                    }
                    //延迟2秒执行
                    setTimeout(function(){
                        //1. 隐藏弹出层
                        _state.showImgUpload = false;//隐藏图片上传弹出层
                        //2. 更新父组件的图片
                        var url = data.path;//文件地址
                        _state.imgSrc = url;//图片路径
                        _state.oldImgSrc = url;//图片路径
                        //3. 更新父组件状态
                        _this.props.updateState();
                    },5000);

                }
            }
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

    /**
     * 渲染DOM
     */
    function render() {
        setting = ReactDOM.render(<ImageComponent />, document.getElementById(divId));
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
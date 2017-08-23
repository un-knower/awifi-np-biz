
/** ---------------------------------------  中间内容区域 开始  ----------------------------------------------------------------------------*/

var _Entity_ = function (state, divId) {

	var uploadImg_url = '/mersrv/media/img/stream/';

	var _state = state || {
			comment_list:[], //留言列表
			total: 0,
			pageNo: 1,       //当前页码
			pageEnd:true,   //是否是最后一页
			showAddComment: false, //是否显示新增留言
			reply: false, //是否在回复
			replyTo: "", //回复谁
			replyIndex: [],
			bigPicture: ""
		};
	var maskWidth = $(".awifi-container").width();
	var EntityReact = React.createClass({
		getInitialState: function () {
			return _state;
		},
		componentDidMount: function(){
			loadCommentData(this, _state.pageNo);
		},
		componentDidUpdate: function(){
			if(_state.reply) {
				this.refs.replyInput.focus();
			}
		},
		//更新组件状态
		setAuthState: function () {
			this.setState(_state);
		},
		addComment: function () {
			if(_DEV_ID == '{@devId@}'){
	            return;
	        }
			_state.showAddComment = true;
			$("body").css("overflow", "hidden");
			this.setAuthState();
		},
		reply: function(i, j){
			if(_DEV_ID == '{@devId@}'){
	            return;
	        }
			var comment = _state.comment_list[i];
			_state.reply = true;
			$("body").css("overflow", "hidden");
			_state.replyTo = j != undefined ? comment.commentReplys[j].replyUserName : comment.commentUserName;
			_state.replyIndex = [i];
			if(j != undefined) _state.replyIndex.push(j);
			this.setAuthState();
		},
		cancelReply: function(){
			_state.reply = false;
			$("body").css("overflow", "");
			_state.replyTo = "";
			_state.replyIndex = [];
			this.setAuthState();
		},
		postReplyData: function(){
			var _this = this;
			var value = this.refs.replyInput.value;
			var commentData = _state.comment_list[_state.replyIndex[0]];
			var data = {
			    "commentId": commentData.commentId,
			    "commentUserId": commentData.commentUserId,
			    "commentUserName": commentData.commentUserName,
			    "userPhone": _USER_PHONE
			};
			var reply = _state.replyIndex[1] != undefined ? commentData.commentReplys[_state.replyIndex[1]] : "";
			data.replyToUserId = reply ? reply.replyUserId : commentData.commentUserId;
			data.replyToUserName = reply ? reply.replyUserName : commentData.commentUserName;
			if(!value) return alert("回复内容不能为空"); 
			else if(value.length > 50) return alert("回复内容不能超过50字符");
			data.content = value;
			$.ajax({
				type:'POST',
				dataType: 'JSON',
				contentType: 'application/json; charset=utf-8',
				url: "/mersrv/merchant/" + _CUSTOMER_ID + "/comment/reply",
				data: JSON.stringify(data),
				success: function (data) {
					if(data.code == "0") {
						_this.cancelReply();
						loadCommentData(_this, 1);
					}else {
						alert(data.msg);
					}
					_this.setAuthState(_state);
				},
				error: function () {
					alert("网络异常");
				}
			});
		},
		setBigPicture:function(e){
			_state.bigPicture = e.target.baseURI;
			this.setAuthState();
		},
		clearBigPicture: function(){
			_state.bigPicture = "";
			this.setAuthState();
		},
		render_comment: function (){
			var _this = this;
			return _state.comment_list ? _state.comment_list.map(function (item, index) {
				return  <div className="m-CommentItem" key={index}>
							<div className = "leftPic">
								<span></span> 
							</div>
							<div className = "rightComment">
								<div className="firstRow">
									<h4>{item.commentUserName}</h4>
										<button onClick={() => {_this.reply(index)}}>回复</button>
								</div>
								<div className="ipTime">{item.createDate}</div>
								<div className="context">{item.content}</div>
								{
									item.commentPicUrls ? <ul className="pics">
										{
											item.commentPicUrls.map(function(imgurl, i){
												return <li key={i}><div className="picItem"><img src={imgurl} onClick={_this.setBigPicture}/></div></li>
											})
										}
									</ul> : ""
								}
								{
									item.commentReplys ?  <ul className="replyPanel">
										{
											item.commentReplys.map(function(reply, j){
												return <li key={reply.commentReplyId} onClick={() => {_this.reply(index, j)}}>
													<span className="user">{reply.replyUserName}</span>
													{reply.replyToUserId ? <span> 回复 <span className="user">{reply.replyToUserName}</span></span> : ""}
													：{reply.content}
												</li>
											})
										}
										</ul> : ""
								}
							</div>
							
						</div>
			}) : ""
		},
		render: function () {
			var _this = this;
			return (
			<div className="_Entity_">
				<div className="container">
					{ this.state.showAddComment ? "" : <div><div className="title_container">
						<h2 className="title">
							留言板({_state.total})
						</h2>
						<button className="titleAdd" onClick={this.addComment}>
							写评价
						</button>
					</div>
					<div className="commentContainer">
						{this.render_comment()}
						{this.state.pageEnd ? <span className="getMore">没有更多了</span> : <button className="getMore" onClick={() => {loadCommentData(_this, _state.pageNo+1)}}>查看更多</button>}
					</div>
					</div>
					}
					{this.state.showAddComment ? <ShowAddComment setAuthState={this.setAuthState} handleClose={this.handleClose}/> : ""}
					{this.state.reply ? <div className="m-CoverContain" style={{width: maskWidth}}>
											<div className="m-Mask" onClick={this.cancelReply}></div>
											<div className="m-Reply">
											<input placeholder={"回复 " + this.state.replyTo} ref="replyInput" />
											<button className="titleAdd" onClick = {this.postReplyData}>提交</button>
										</div></div> : ""}
					{_state.bigPicture ? <div className="m-CoverContain" style={{width: maskWidth}}>
											<div className="m-Mask"><img src={_state.bigPicture} onClick={_this.clearBigPicture}/></div></div> : ""}
				</div>
			</div>
			);
		},
		handleClose: function(reload){
			if(reload === true) loadCommentData(this, 1);
		}
	});
	var commentState = {
		files: [],
		loading: false,
		imgList: [],
		progress: []
	}
	// 添加评论
	var ShowAddComment = React.createClass({
		getInitialState: function () {
			return commentState
		},
		//更新组件状态
		setAuthState: function () {
			this.setState(commentState);
		},
		onChange: function (files) {
			commentState.files = commentState.files.concat(files)
			this.setAuthState();
		},
		handleClose: function (reload) {
			_state.showAddComment = false;
			$("body").css("overflow", "");
			this.props.setAuthState();
			this.props.handleClose(reload);
		},
		handleSubmit: function(){
			if(this.state.loading) return;
			var val = this.refs.comment.value;
			if(!val) return alert("请输入评论内容");
			else if(val.length > 50) return alert("评论内容不能超过50字");
			var postData = { "content": val, "userPhone": _USER_PHONE };
			// 如果上传了图片
			if(commentState.imgList.length){
				postData.commentPicUrl = [];
				commentState.imgList.forEach(function(img, i){
					postData.commentPicUrl.push(img.relativeUrl);
				})
			}
			var _this = this;
			$.ajax({
				type:'POST',
				dataType: 'JSON',
				contentType: 'application/json; charset=utf-8',
				url: "/mersrv/merchant/" + _CUSTOMER_ID + "/comment",
				data: JSON.stringify(postData),
				success: function (data) {
					if(data.code == "0") {
						_this.handleClose(true);
					}else {
						alert(data.msg);
					}
				},
				error: function () {
					alert("网络异常");
				}
			});
		},
		render: function(){
			return (
				<div className="m-AddComment" style={{width: maskWidth}}>
					<div ><textarea className="m-TextArea" rows="5" placeholder="这一刻的想法" ref="comment"></textarea></div>
					<FileInput onChange={this.onChange} maxLength = {6} setAuthState={this.setAuthState} />
					<div className="btnPanel">
						<button className={this.state.loading ? "u-btn-Sure disabled" : "u-btn-Sure"} onClick={this.handleSubmit}>{this.state.loading ? "上传中..." : "评价"}</button>
						<button className="u-btn-Cancel" onClick = {this.handleClose}>取消</button>
					</div>
				</div>
			);
		},
		componentWillUnmount: function () {
			commentState = {
				files: [],
				loading: false,
				imgList: [],
				progress: []
			}
		}
	});
	// 上传图片
	var FileInput = React.createClass({
		getDefaultProps: function () {
			return {
				btnValue: '+',
				className: "m-UploadButton",
				multiple: true,
				maxLength: 6
			}
		},
		propTypes: {
			maxLength: React.PropTypes.number,
			onChange: React.PropTypes.func,
			btnValue: React.PropTypes.string
		},
		handleChange: function (event) {
			event.preventDefault();
			var files = event.target.files;
			if(commentState.imgList.length + files.length > this.props.maxLength) 
				return alert("最多可上传6张图片");
			var count = this.props.multiple ? files.length : 1;
			for (var i = 0; i < count; i++) {
				files[i].thumb = URL.createObjectURL(files[i])
			}
			// convert to array.
			files = Array.prototype.slice.call(files, 0)
			// image filter.
			// if want to support more file type, you can modify or remove this snippet.
			commentState.loading = true;
			this.props.setAuthState();
			var _this = this;
			files.forEach(function(file, i) {
				if (!/\/(?:jpeg|png|gif)/i.test(file.type))
					return;
				var reader = new FileReader();
				// 获取图片大小
				var size = file.size / 1024 > 1024 ? (~~(10 * file.size / 1024 / 1024)) / 10 + "MB" : ~~(file.size / 1024) + "KB";
				var index = commentState.imgList.length;
				commentState.imgList.push({});
				_this.props.setAuthState();
				reader.onload = function() {
					var result = this.result;
					commentState.imgList[index].url = result;
					_this.props.setAuthState();
					// 如果图片大小小于100kb，则直接上传
					if (result.length <= 102400) {
						return _this.img_uploader(result, file.type, index);
					}
					// 需要压缩
					var img = new Image();
					img.src = result;
					if (img.complete) _this.img_complete(img, file, index);
					else {  
						img.onload = function(){
							_this.img_complete(img, file, index);
						};
					}
				};
				reader.readAsDataURL(file);
			})
		},
		img_complete: function(img, file, index){
			var data = this.img_compress(img);
			this.img_uploader(data, file.type, index);
			img = null;
		},
		// 图片压缩
		img_compress: function(img){
			// 用于压缩图片的canvas
			var canvas = document.createElement("canvas");
			var ctx = canvas.getContext('2d');
			// 瓦片canvas
			var tCanvas = document.createElement("canvas");
			var tctx = tCanvas.getContext("2d");
			var initSize = img.src.length;
			var width = img.width;
			var height = img.height;
			// 如果图片大于四百万像素，计算压缩比并将大小压至40万以下
			var ratio;
			if ((ratio = width * height / 400000) > 1) {
				ratio = Math.sqrt(ratio);
				width /= ratio;
				height /= ratio;
			} else {
				ratio = 1;
			}
			canvas.width = width;
			canvas.height = height;
			// 铺底色
			ctx.fillStyle = "#fff";
			ctx.fillRect(0, 0, canvas.width, canvas.height);
			// 如果图片像素大于100万则使用瓦片绘制
			var count;
			if ((count = width * height / 1000000) > 1) {
				count = ~~(Math.sqrt(count) + 1); // 计算要分成多少块瓦片
				// 计算每块瓦片的宽和高
				var nw = ~~(width / count);
				var nh = ~~(height / count);
				tCanvas.width = nw;
				tCanvas.height = nh;
				for ( var i = 0; i < count; i++) {
					for ( var j = 0; j < count; j++) {
						tctx.drawImage(img, i * nw * ratio, j * nh * ratio, nw * ratio,
								nh * ratio, 0, 0, nw, nh);
						ctx.drawImage(tCanvas, i * nw, j * nh, nw, nh);
					}
				}
			} else {
				ctx.drawImage(img, 0, 0, width, height);
			}
			// 进行最小压缩
			var ndata = canvas.toDataURL('image/jpeg', 0.8);
			console.log('压缩前：' + initSize);
			console.log('压缩后：' + ndata.length);
			console.log('压缩率：' + ~~(100 * (initSize - ndata.length) / initSize) + "%");
			tCanvas.width = tCanvas.height = canvas.width = canvas.height = 0;
			return ndata;
		},
		// 图片上传，将base64的图片转成二进制对象，塞进formdata上传
		img_uploader: function(basestr, type, index){
			if(_DEV_ID == '{@devId@}') return;
			var _this = this;
			$.ajax({
				type:'POST',
				url: uploadImg_url,
				dataType : "JSON",
				contentType: 'application/json',
				data: JSON.stringify({
					"data": basestr,
					"type": "comment",
					"belongid": _CUSTOMER_ID
				}),
				success: function (data) {
					if(data.code == "0") {
						commentState.progress[index] = "上传成功";
						commentState.imgList[index].relativeUrl = data.data;
						_this.isUpLoading();
					}else {
						alert(data.msg);
					}
				},
				error: function () {
					alert("网络异常");
				}
			});
		},
		isUpLoading: function (){
			var allLoad = true;
			commentState.imgList.forEach(function(img, i){
				if(!img.relativeUrl) return allLoad = false;
			});
			if(allLoad) commentState.loading = false;
			this.props.setAuthState();
		},
		removePic: function(i){
			commentState.imgList.splice(i, 1);
			commentState.progress.splice(i, 1);
			this.isUpLoading();
		},
		render: function () {
			var className = this.props.className;
			var _this = this;
			var imgContent = [];
			commentState.imgList.forEach(function (item, index) {
				imgContent.push(
					<li key={index}>
						<div className="background" style={{backgroundImage: "url(" + item.url + ")"}}></div>
						<div className="delete">
							<button onClick={() => {_this.removePic(index)}}>&nbsp;×&nbsp;</button>
						</div>
						<div className="progress"><span>{commentState.progress[index] || "上传中"}</span></div>
					</li>
				);
			});
			return (
				<ul className={className} id="img_list">
					{imgContent}
					<li><div className="background" >
						<span className="add">{this.props.btnValue}</span>
						<input type="file" name="file" multiple={this.props.multiple} accept="image/*" onChange={this.handleChange} />
					</div></li>
				</ul>
			)
		}
	});
	/**
	 *
	 * @param _this
	 * @param status status为click时为点击更多按钮的操作、为空则为默认显示状态
	 */
	function loadCommentData(_this, pageNo) {
		if(_DEV_ID == '{@devId@}'){
            return;
        }
		_state.pageNo = pageNo;
		_this.setAuthState(_state);
		$.ajax({
			type:'GET',
			dataType: 'JSON',
			url: "/mersrv/merchant/" + _CUSTOMER_ID + "/comments",
			data:{"pageNo": pageNo, "pageSize": 3},
			success: function (data) {
				if(data.code == "0") {
					_state.comment_list = pageNo > 1 ? _state.comment_list.concat(data.data.records) : data.data.records;
					_state.pageEnd = (!data.data.totalPage || data.data.totalPage == pageNo) ? true : false;
					_state.total = data.data.totalRecord;
				}else {
					alert(data.msg);
				}
				_this.setAuthState(_state);
			},
			error: function () {
				alert("网络异常");
			}
		});
	};


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
				<div className="form-group control-label">&emsp;此组件无需进行设置</div>
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





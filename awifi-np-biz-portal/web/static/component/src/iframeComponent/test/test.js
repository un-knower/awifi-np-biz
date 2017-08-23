
/**
 * 跳转组件
 * @param state
 * @param divId
 * @returns {{render: render}}
 * @private
 * @Auther: 牛华凤
 * @Date: 2016-1-5
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


// 拉portal调用方式
// _Entity_(state, divId).render(); 组件拉了多次则调用多次

/*
 1) 写入页面中相应的Div元素，每个div的id保证唯一性
 2）合并多个组件js文件至一个js文件中，且在合并的js文件末尾加个要调用 ReactJs 的方法
 3）合并多个组件css文件至一个css文件中
 */

/**
 * 跳转组件
 * @param entity
 * @param divId
 * @returns {{setting: *, setStates: setStates, render: render}}
 * @private
 * @Auther: 牛华凤
 * @Date: 2016-1-5
 */
var _Setting_ = function (entity, divId) {

	var setting = null;

	function setStates(item) {
		entity.setState(item);
		setting.setState(item);
	}

	var _state = entity.state || {
			url: ''
		};

	var IFrameComponent = React.createClass({

		//状态初始化函数
		getInitialState: function() {
			return _state;
		},


		handleSaveClick: function(){
			var urlLink = $('#url').val();

			urlLink = (urlLink.indexOf('http://') == 0 ? urlLink : 'http://' + urlLink);

			_state.url = urlLink;

			entity.setState(_state);
		},


		render: function () {
			return (
				<div className="container">
					<form className="form-horizontal">
						<div className="form-group">
							<label for="title" className="col-sm-2 control-label">网址：</label>
							<div class="col-sm-9">
								<input id="url" type="text" className="form-control" defaultValue={this.state.url} placeholder="请输入网址"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-9">
								<button id="save" type="button" className="btn btn-danger btn-sm" onClick={ this.handleSaveClick }>保&emsp;存</button>
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
		setting = ReactDOM.render(<IFrameComponent />, document.getElementById(divId));
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


var entity = _Entity_(null, 'entity').render();
entity.setState({'url':'http://www.baidu.com'});

var setting = _Setting_(entity, 'setting').render();

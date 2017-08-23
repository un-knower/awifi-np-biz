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

	var _state = entity.state || {
			textAlign: 'center',
			aText: '跳转中',
			aHref: ''
		};

	function setStates(item) {
		entity.setState(item);
		setting.setState(item);
	}

	var SettingReact = React.createClass({
		getInitialState: function() {
			return _state;
		},
		eventAHref: function(event) {
			var _state = {
				'aHref': event.target.value
			};
			setStates(_state);
		},
		eventSave: function() {
			var verify = awifiUtils.verify_field('url',/((http):\/\/([\w\-]+\.)+[\w\-]+(\/[\w\u4e00-\u9fa5\-\.\/?\@\%\!\&=\+\~\:\#\;\,]*)?)/ig,'链接地址不能为空','链接地址格式不正确');
			if (verify) {
				return ;
			}
			var _state = {
				'aHref': $("#url").val()
			};
			setStates(_state);
		},
		render: function () {
			return (
				<div className="container">
					<form className="form-horizontal">
						<div className="form-group">
							<label for="url" className="col-sm-2 control-label">链接地址：</label>
							<div className="col-sm-9">
								<input type="text" className="form-control" id="url" placeholder="请输入链接地址" defaultValue={this.state.aHref} onChange={this.eventAHref} />
							</div>
						</div>
						<div className="form-group form-save">
							<button id="save" type="button" className="btn btn-danger btn-block" onClick={this.eventSave}>保&emsp;存</button>
						</div>
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

//React.render(<Setting_2K2ZQKTAE6MAJEH6K1EFIY6L />, document.getElementById(divId));

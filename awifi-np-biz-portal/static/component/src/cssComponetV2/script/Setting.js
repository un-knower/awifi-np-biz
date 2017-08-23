var _Setting_ = function (entity, divId) {
    var setting = null;

    function setStates(item) {
        entity.setState(item);
        setting.setState(item);
    }

    var CssSettingComponent = React.createClass({
        getInitialState: function () {
            return {
                cssHref: entity.state.cssHref
            };
        },
        eventCssHref: function (event) {
            var _state = {
                'cssHref': event.target.value
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
                                <input type="text" className="form-control" id="url" value={this.state.cssHref} onChange={this.eventCssHref}/>
                            </div>
                        </div>
                    </form>
                </div>
            );
        }
    });

    function render() {
        setting = ReactDOM.render(<CssSettingComponent />, document.getElementById(divId));
        return setting;
    }

    return {
        setting: setting,
        setStates: setStates,
        render: render
    }
};

//React.render(<Setting_2K2ZQKTAE6MAJEH6K1EFIY6L />, document.getElementById(divId));

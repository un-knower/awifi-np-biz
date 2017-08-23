
/**
 * 超链接组件
 * @param state
 * @param divId
 * @returns {{render: render}}
 * @private
 * @Auther: zhuxh
 * @Date: 2015-12-30
 */

var _Entity_ = function (state, divId) {


    //请求后端加密接口
    var encryptionInterfaceUrl = '/app/encryption';

    var _state = state || {
            textAlign: 'left',
            aText: '链接文字',
            aHref: 'http://'
        };

    var EntityReact = React.createClass({
        getInitialState: function () {
            return _state;
        },

        componentWillMount: function () {
            if (_DEV_ID == '{@devId@}') {
                return;
            }
            var option = {
                deviceId: _DEV_ID,
                devMac: _DEV_MAC,
                userIp: _USER_IP,
                userMac: _USER_MAC,
                userPhone: _USER_PHONE,
                terminalType: $.browser.mobileOS,
                customerId: _CUSTOMER_ID
            };
            var paramLink = _state.aHref;
            if (paramLink.indexOf('?') == -1) {
                paramLink += '?';
            } else if (paramLink.indexOf('=') != -1) {
                paramLink += '&';
            }
            $.ajax({
                url: encryptionInterfaceUrl,
                dataType: 'JSON',
                header: {
                    'cache-control': 'no-cache'
                },
                data: option,
                success: function (data, textStatus, jqXHR) {
                    if (data.result == 'OK') {
                        paramLink += 'params=' + data.params;
                    } else {
                        paramLink += 'params=';
                    }

                },
                error: function (data) {
                    paramLink += 'params=';
                }
            });
            _state.aHref = paramLink;
        },

        render: function () {
            return (
                <div className="_Entity_">
                    <div className="textLink" style={{textAlign: this.state.textAlign}}>
                        <a href={this.state.aHref !== 'http://' ? this.state.aHref : 'javascript:;' } title={this.state.aText} target="_blank">{this.state.aText}</a>
                    </div>
                </div>
            );
        }
    });

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

//var _Entity_ = function (state, divId) {
//
//    var _state = state || {
//            textAlign: 'left',
//            aText: '链接文字',
//            aHref: 'http://'
//        };
//
//    var EntityReact = React.createClass({
//        getInitialState: function () {
//            return _state;
//        },
//
//        render: function () {
//            return (
//                <div className="textlink" style={{textAlign: this.state.textAlign}}>
//                    <a href={this.state.aHref !== 'http://' ? this.state.aHref : 'javascript:;' } title={this.state.aText}>{this.state.aText}</a>
//                </div>
//            );
//        }
//    });
//
//    // OtherReact 在这里定义
//
//    /**
//     * 制作页面时返回React对象
//     * @returns {*}
//     */
//    function render() {
//        return ReactDOM.render(<EntityReact />, document.getElementById(divId));
//    }
//
//    return {
//        render: render
//    }
//};

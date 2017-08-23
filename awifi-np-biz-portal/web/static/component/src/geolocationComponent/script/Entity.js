/**
 * Created by Shin on 2016/3/15.
 * HTML5获取经纬度 组件
 */
var _Entity_ = function (state, divId) {

    //经纬度请求后端接口地址

    var sendGeolocationToInterfaceUrl = '/geolocation/save';  //beta环境

    var _state = state || {
            isShow: true    //是否隐藏此组件文件
        };


    var GeolocationComponent = React.createClass({

        getInitialState: function () {
            return _state;
        },

        componentDidMount: function () {
            var $base = $('#'+divId).find('._Entity_');

            if (_DEV_ID == '{@devId@}') {
                $base.css('height', 48).html('<div class="areatip">经纬度区域</div>')
                return;
            }

            if(_DEV_ID != '{@devId@}'){
                //try {
                    if (navigator.geolocation) {
                        navigator.geolocation.getCurrentPosition(this.showPosition);
                    }
                //} catch(e) {}
            }
        },

        showPosition: function(position){
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;
            var accuracy = position.coords.accuracy;
            this.pushData(latitude, longitude, accuracy);
        },

        pushData: function(latitude, longitude, accuracy){
            if(_DEV_ID != '{@devId@}'){
                var params = {
                    devId : _DEV_ID,
                    devMAC : _DEV_MAC,
                    longitude : longitude,
                    latitude : latitude,
                    accuracy : accuracy
                };

                $.ajax({
                    url: sendGeolocationToInterfaceUrl,
                    type: 'POST',
                    dataType: 'JSON',
                    header: {
                        'cache-control': 'no-cache'
                    },
                    data: params,
                    success: function(data, textStatus, jqXHR){
                        if(data.result == 'OK'){
                            //TODO
                        } else {
                            alert('失败，' + data.message);
                        }
                    },
                    error: function(data){
                        alert('失败:' + data.message);
                    },
                    complete: function(XHR, textStatus){

                    }
                });
            }
        },

        //根据state变化来渲染组件
        render: function () {
            return (
                <div className="_Entity_">
                    <div id="titleGeolocationInfo" className={this.state.isShow ? "titleUnShow" : "titleIsShow"}>页面正在获取经纬度</div>
                </div>
            );
        }

    });


    /**
     * 制作页面时返回React对象
     */
    function render() {
        return ReactDOM.render(<GeolocationComponent />, document.getElementById(divId));
    }

    /**
     * 暴露render方法
     */
    return {
        render: render
    }
};
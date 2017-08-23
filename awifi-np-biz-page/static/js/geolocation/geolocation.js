/**
 * Created by haoxu on 2017/8/14.
 */
var geolocation = {
    url: '/pagesrv/device/geolocation',
    getLocation:function(){
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(this.showPosition);
        }
    },
    showPosition: function(position){
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;
        try {
            geolocation.pushPosition(latitude, longitude);
        } catch (e) {
            return;
        }
    },

    pushPosition: function(latitude, longitude){
           var _this = this;
            var params = {
                deviceId : _DEV_ID, /**设备id*/
                longitude : longitude,
                latitude : latitude
            };
            $.ajax({
                url: _this.url,
                type: 'put',
                contentType: "application/json",
                dataType: 'JSON',
                processData:false,
                header: {
                    'cache-control': 'no-cache'
                },
                data:JSON.stringify(params),
                success: function(data, textStatus, jqXHR){
                    if(data.result == 'OK'){
                    } else {
                    }
                },
                error: function(data){
                },
                complete: function(XHR, textStatus){
                }
            });
    }
};

try{
    if(_PAGE_TYPE == 2 && !_LONGITUDE && !_LATITUDE){
        geolocation.getLocation();
    }
}catch(e){}

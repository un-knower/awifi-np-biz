/**
 * Created by zhuxh on 15-12-16.
 */


indexApp.controller('PolicyController', function($scope) {

    function resetWidth(){
        var h = $(".datacontent");
        var width = h.outerWidth( true );
        $(".listtitle").css('width', width);
    }
    resetWidth();

});
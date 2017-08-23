indexApp.factory('topMenuService',function(topMenuDao){
    var service = {};
    //获取当前用户名
    service.getCurUserName = function($scope){
    	topMenuDao.getCurUserName($scope);
    };
    return service;
});
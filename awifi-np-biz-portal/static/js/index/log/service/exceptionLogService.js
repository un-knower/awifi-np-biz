indexApp.factory('exceptionlogService',function(exceptionlogDao){
    var service = {};
    //异常日志列表
    service.list = function($scope){
    	exceptionlogDao.list($scope);
    };
    
    //详情
    service.show=function($scope,id){
    	exceptionlogDao.show($scope,id);
    };
    
    return service;
});
indexApp.factory('leftMenuDao',function($http,$cacheFactory){
    var dao = {};

  //左侧菜单权限控制 - 系统设置
    /*dao.hasPermForConfig = function($scope){
        var url = '/permission/haspermission?code=leftmenu_config';
        $http.get(url)
            .success(function(data, status, headers, config){
                if(data.result == 'FAIL'){
                    jDialog.alert('系统异常: ' , data.message);
                    return;
                }
                var hasPermission = data.hasPermission;
                var showConfig = $.trim(hasPermission) == 'yes' ? true : false;
                $scope.showConfig = showConfig;
            });
    };
    //左侧菜单权限控制 - 系统参数配置
    dao.hasPermForSysConfig = function($scope){
        var url = '/permission/haspermission?code=leftmenu_sys_config';
        $http.get(url)
            .success(function(data, status, headers, config){
                if(data.result == 'FAIL'){
                    jDialog.alert('系统异常: ' , data.message);
                    return;
                }
                var hasPermission = data.hasPermission;
                var showSysConfig = $.trim(hasPermission) == 'yes' ? true : false;
                $scope.showSysConfig = showSysConfig;
            });
    };
    //左侧菜单权限控制 - 异常日志
    dao.hasPermForExceptionLog = function($scope){
        var url = '/permission/haspermission?code=leftmenu_exception_log';
        $http.get(url)
            .success(function(data, status, headers, config){
                if(data.result == 'FAIL'){
                    jDialog.alert('系统异常: ' , data.message);
                    return;
                }
                var hasPermission = data.hasPermission;
                var showExceptionLog = $.trim(hasPermission) == 'yes' ? true : false;
                $scope.showExceptionLog = showExceptionLog;
            });
    };
    //左侧菜单权限控制 - 请求日志
    dao.hasPermForRequestLog = function($scope){
        var url = '/permission/haspermission?code=leftmenu_request_log';
        $http.get(url)
            .success(function(data, status, headers, config){
                if(data.result == 'FAIL'){
                    jDialog.alert('系统异常: ' , data.message);
                    return;
                }
                var hasPermission = data.hasPermission;
                var showRequestLog = $.trim(hasPermission) == 'yes' ? true : false;
                $scope.showRequestLog = showRequestLog;
            });
    };*/
    return dao;
});
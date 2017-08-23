indexApp.factory('topMenuDao',function($http){
    var dao = {};
    //获取当前用户名
    dao.getCurUserName = function($scope){
        var url = '/user/getcurusername';
        $http.get(url)
            .success(function(data, status, headers, config){
                if(data.result == 'FAIL'){
                    jDialog.alert('提示: ' , data.message);
                    return;
                }
                var userName = data.userName;
                if(isBlank(userName)){
                    
                }
                $scope.userName = userName;
            });
    };
    return dao;
});
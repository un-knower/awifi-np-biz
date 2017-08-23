indexApp.factory('industryDao', function($http, $location, pageDao){
    var dao = {};

    //获取一级行业列表
    dao.industry1List = function($scope, parentId){
        parentId = defaultString(parentId);
        var url = '/industry/list?parentId=' + parentId;
        $http.get(url).success(function(data, status, headers, config){
            if(data.result == 'FAIL'){
                jDialog.alert('提示', data.message);
                return;
            }
            $scope.industry1s = data.data;//一级行业集合
        });
    };

    //获取二级行业列表
    dao.industry2List = function($scope, industryId1){
        var url = '/industry/list?parentId=' + industryId1;
        $http.get(url).success(function(data, status, headers, config){
            if(data.result == 'FAIL'){
                jDialog.alert('提示', data.message);
                return;
            }
            $scope.industry2s = data.data;//二级行业集合
        });
    };

    return dao;
});

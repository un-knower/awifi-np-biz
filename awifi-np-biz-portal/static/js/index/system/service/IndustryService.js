indexApp.factory('industryService', function($rootScope, industryDao){
    var service = {};

    //获取一级行业列表
    service.industry1List = function($scope){
        industryDao.industry1List($scope);
    };

    //获取二级行业列表
    service.industry2List = function($scope, industryId1){
        $scope.industry2s="";
        if(isBlank(industryId1)){
            return;
        }
        //$scope.customer.childIndustryId = "";
        industryDao.industry2List($scope, industryId1);
    };

    return service;
});

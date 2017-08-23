indexApp.factory('locationService', function($rootScope, locationDao){
    var service = {};

    //获取省数据集合
    service.provinceList = function($scope){
        locationDao.provinceList($scope);
    };

    //获取市数据集合
    service.cityList = function($scope, provinceId){
        //var provinceId = $scope.customer.provinceId;//获取省id
        if(provinceId === null){
            $scope.citys = "";
            $scope.countys = "";
            return;
        }
        locationDao.cityList($scope, provinceId);
    };

    //获取区/县数据集合
    service.countyList = function($scope,cityId ){
        //var cityId = $scope.customer.cityId;//获取市id
        if(cityId === null){
            $scope.countys = "";
            return;
        }
        locationDao.countyList($scope, cityId);
    };

    return service;
});

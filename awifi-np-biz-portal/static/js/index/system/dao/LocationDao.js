indexApp.factory('locationDao', function($http, $location, pageDao){
    var dao = {};

    //获取地区数据集合
    //获取省数据集合
    dao.provinceList = function($scope){
        var url = '/location/province';
        $http.get(url).success(function(data, status, headers, config){
            if(data.result == 'FAIL'){
                jDialog.alert('提示', data.message);
                return;
            }
            $scope.provinces = data.data;//省数据集合
        });
    };

    //获取市数据集合
    dao.cityList = function($scope, provinceId){
        var url = '/location/city?parentId=' + provinceId;
        $http.get(url).success(function(data, status, headers, config){
            if(data.result == 'FAIL'){
                jDialog.alert('提示', data.message);
                return;
            }
            $scope.citys = data.data;//市数据集合
        });
    };

    //获取区/县数据集合
    dao.countyList = function($scope, cityId){
        //var cityId = $scope.customer.cityId;//市id
        var url = '/location/county?parentId=' + cityId;
        $http.get(url).success(function(data, status, headers, config){
            if(data.result == 'FAIL'){
                jDialog.alert('提示', data.message);
                return;
            }
            $scope.countys = data.data;//地区数据集合
        });
    };

    return dao;
});

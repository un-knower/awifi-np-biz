indexApp.controller('sysConfigEditController', function($scope, $location, sysConfigService) {
	//alert('sysConfigEditController');
	//编辑项目配置
    $scope.edit = function edit(){
    	 //参数校验
        if(sysConfigService.paramValidateFail($scope))
            return;
        sysConfigService.edit($scope);
    }
    /**
     * 初始化函数
     */
    function init(){
        //根据id查找系统参数详情
        var sysconfigId = $location.search()['sysconfigId'];
        sysConfigService.show($scope, sysconfigId);
        sysConfigService.initTips();//初始化校验信息栏
    }
    init();
});

indexApp.controller('sysConfigAddController', function($scope, sysConfigService) {
	//alert('sysConfigAddController');
	//保存新增的系统参数
	$scope.add = function() {
		//参数校验
        if(sysConfigService.paramValidateFail($scope))
            return;
		sysConfigService.add($scope);
	};
	/**
	 * 初始化函数
	 */
	function init(){
		$scope.sysconfig = {};
		sysConfigService.initTips();//初始化校验信息栏
	}
	init();
});
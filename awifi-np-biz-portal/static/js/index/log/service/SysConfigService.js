indexApp.factory('sysConfigService',function($rootScope, sysConfigDao){
    var service = {};
    //系统参数列表(分页)
    service.list = function($scope){
    	sysConfigDao.list($scope);
    };
    //新增系统参数配置
    service.add=function($scope){
    	sysConfigDao.add($scope);
    };
    //根据id查找系统参数详情
    service.show = function($scope, id){
    	sysConfigDao.show($scope, id);
    };
    //编辑系统参数
    service.edit = function($scope){
    	sysConfigDao.edit($scope);
    };
    //删除系统参数
    service.remove = function($scope, id){
    	sysConfigDao.remove($scope, id);
    }
    /**
	 * 参数校验
	 * true 代表 校验失败
	 * @auth xuxiaoman
	 */
	service.paramValidateFail = function($scope){
		//名称
		var $aliasName = $("input[data-ng-model='sysconfig.aliasName']");
		var aliasName = $scope.sysconfig.aliasName;
		if(isBlank(aliasName)){
			updateShowTipos($aliasName, '名称不能为空！')
			return true;
		}
		//key
		var $paramKey = $("input[data-ng-model='sysconfig.paramKey']");
		var paramKey = $scope.sysconfig.paramKey;
		if(isBlank(paramKey)){
			updateShowTipos($paramKey, 'Key不能为空！')
			return true;
		}
		//Value
		var $paramValue = $("input[data-ng-model='sysconfig.paramValue']");
		var paramValue = $scope.sysconfig.paramValue;
		if(isBlank(paramValue)){
			updateShowTipos($paramValue, 'Value不能为空！')
			return true;
		}
		//排序号
		var $orderNo = $("input[data-ng-model='sysconfig.orderNo']");
		var orderNo = $scope.sysconfig.orderNo;
		if(isBlank(orderNo)){
			updateShowTipos($orderNo, '排序号不能为空！')
			return true;
		}
		//校验排序号规则
		//alert("格式错误1111111");
		if(!chkString(orderNo, defrules.orderNo)){
			//alert("格式错误2222222");
			updateShowTipos($orderNo, '排序号只能是数字形式!');
			return true;
		}
		//备注
		var $remark = $("textarea[data-ng-model='sysconfig.remark']");
		var remark = $scope.sysconfig.remark;
		if(isNotBlank(remark) && checkLengthFail(remark, 100)){
			updateShowTipos($remark, '备注长度不能超过100！')
			return true;
		}
		return false;
 	};
 	/**
	 * 初始化校验信息栏
	 * @auth xuxiaoman
	 */
	service.initTips = function(){
		initTips($("input[data-ng-model='sysconfig.aliasName']"));
		initTips($("input[data-ng-model='sysconfig.paramKey']"));
		initTips($("input[data-ng-model='sysconfig.paramValue']"));
		initTips($("input[data-ng-model='sysconfig.orderNo']"));
		initTips($("textarea[data-ng-model='project.remark']"));

		//页面切换前示销毁tips
		$rootScope.$on('$locationChangeStart', function () {
			destroyTips($("input[data-ng-model='sysconfig.aliasName']"));
			destroyTips($("input[data-ng-model='sysconfig.paramKey']"));
			destroyTips($("input[data-ng-model='sysconfig.paramValue']"));
			destroyTips($("input[data-ng-model='sysconfig.orderNo']"));
			destroyTips($("textarea[data-ng-model='project.remark']"));
		});
	};
    return service; 
});
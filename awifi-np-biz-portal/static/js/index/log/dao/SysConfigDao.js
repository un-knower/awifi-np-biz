indexApp.factory('sysConfigDao',function($http, $location, pageDao){
	    var dao = {};
	// 系统参数列表(分页)
	dao.list = function($scope) {
		var pageNo = pageDao.getPageNo();
	    var keywords = defaultString($scope.keywords);
		var url = '/sysconfig/list?pageNo=' + pageNo+'&keywords='+keywords;
		$http.get(url)
		 .success(function(data, status, headers, config) {
			if (data.result == 'FAIL') {
				jDialog.alert('提示: ' , data.message);
				return;
			}
			sysconfigs = data.records;
			$scope.sysconfigs = data.records;// 数据集
			$scope.begin = data.begin;// 起始行
			$scope.data = data;
			pageDao.init($scope, data);// 初始化分页栏
		})
		.error(function(data, status, headers, config) {
			// alert('error：' + data);
		});
	};
    // 新增系统参数配置
	dao.add = function($scope) {
		var aliasName = $scope.sysconfig.aliasName;
		var paramKey = $scope.sysconfig.paramKey;
		var paramValue = $scope.sysconfig.paramValue;
		var remark = $scope.sysconfig.remark;
		if(isNull(remark)){
            remark = '';
        }
		remark = encodeURIComponent(remark);
		var orderNo = $scope.sysconfig.orderNo;
		var url = "/sysconfig/add?aliasName=" + aliasName + "&paramKey="+ paramKey + "&paramValue=" + paramValue + "&remark=" + remark+ "&orderNo=" + orderNo;
		$http.get(url)
		   .success(function(data, status, headers, config) {
			if (data.result == 'FAIL') {
				jDialog.alert('提示: ', data.message);
				return;
			}
			jDialog.alert("系统参数配置", "添加成功");
			$location.path('sysconfig/list');
			})
		   .error(function(data, status, headers, config) {
			jDialog.alert('提示: ', datastatus);
		});
	};
	//根据id查找系统参数详情
	dao.show = function($scope, id){
		 var url = "/sysconfig/getinfo?id=" + id;
	        $http.get(url)
	            .success(function(data, status, headers, config){
	                if(data.result == 'FAIL'){
	                	jDialog.alert('提示: ', data.message);
	                    return;
	                }
	                $scope.sysconfig = data.data;//数据集
	            })
	            .error(function(data, status, headers, config){
	            	jDialog.alert('提示: ', datastatus);
	            });
	    };
	//编辑系统参数
	dao.edit = function($scope){
		var id = $scope.sysconfig.id;//得到id
		var aliasName= $scope.sysconfig.aliasName;
		var paramKey= $scope.sysconfig.paramKey;
		var paramValue= $scope.sysconfig.paramValue;
		var remark= $scope.sysconfig.remark;
		if(isNull(remark)){
            remark = '';
        }
		remark = encodeURIComponent(remark);
		var orderNo= $scope.sysconfig.orderNo;
		var url = "/sysconfig/edit?id="+id+"&aliasName=" + aliasName + "&paramKey="+ paramKey + "&paramValue=" + paramValue + "&remark=" + remark+ "&orderNo=" + orderNo;
		$http.get(url)
		   .success(function(data, status, headers, config) {
			if (data.result == 'FAIL') {
				jDialog.alert('提示: ', data.message);
				return;
			}
			jDialog.alert("系统参数配置", "修改成功");
			$location.path('sysconfig/list');
			})
			.error(function(data, status, headers, config) {
			jDialog.alert('提示: ', datastatus);
		});
	}
	//删除系统参数配置
	dao.remove = function($scope, id){
		var url="/sysconfig/delete?id="+id;
		$http.get(url)
		.success(function(data,status,headers,config){
			if(data.result == 'FAIL'){
				jDialog.alert('提示: ', data.message);
				return;	
			}
			jDialog.alert("系统参数删除", "删除成功");
        	$scope.list();
		})
		 .error(function(data,status,headers,config){
         	jDialog.alert('提示: ',data.status);
         });
	}
    return dao;
});
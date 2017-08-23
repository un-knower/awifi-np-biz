indexApp.factory('exceptionlogDao',function($http, pageDao){
    var dao = {};
  //异常日志列表
    dao.list = function($scope){
    	//alert(1);
    	var pageNo = pageDao.getPageNo();
    	var startDate = $scope.startDate;
    	var endDate = $scope.endDate;
    	var keywords = defaultString($scope.keywords);
    	var url = '/exceptionlog/list?pageNo=' + pageNo + '&startDate=' + startDate + '&endDate=' +  endDate + '&keywords=' + encodeURI(keywords);
    	 $http.get(url)
         .success(function(data, status, headers, config){
             if(data.result == 'FAIL'){
                 jDialog.alert('提示: ' , data.message);
                 return;
             }
            exceptionlogLists = data.records;
            $scope.exceptionlogs = data.records;//数据集
            $scope.begin = data.begin;//起始行
            $scope.data = data;
            pageDao.init($scope, data);//初始化分页栏
        })
        .error(function(data, status, headers, config){
        });
    };
    
    //根据id查找请求日志详情
	dao.show = function($scope, id){
		 var url = "/exceptionlog/show?id=" + id;
	        $http.get(url)
	            .success(function(data, status, headers, config){
	                if(data.result == 'FAIL'){
	                	jDialog.alert('提示: ', data.message);
	                    return;
	                }
	                $scope.exceptionlog = data.data;//数据集
	            })
	            .error(function(data, status, headers, config){
	            	jDialog.alert('提示: ', datastatus);
	            });
	    };
    
    
    return dao;
});
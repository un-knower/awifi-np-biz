indexApp.controller('sysConfigController', function(sysConfigService, $scope, $location, $modal) {
    function list(){
    	sysConfigService.list($scope);
    };
    $scope.list = list;
    list();
    //查看
    $scope.show = function(id){
        $scope.id = id;
        var modalInstance = $modal.open({
            templateUrl: 'html/template/system/sysConfigShow.html',
            controller: 'sysConfigShowController',
           scope : $scope
        });
        modalInstance.opened.then(function(){//模态窗口打开之后执行的函数
        });
    };
    //删除
    $scope.delete=function(id){
    	jDialog.confirm('删除参数配置', '<div class="rows"><label class="w100">您确定要删除该参数吗？</label></div>', function(){
    		sysConfigService.remove($scope,id);
            jDialog.hide();
        });
    };

    function resetWidth(){
        var h = $(".datacontent");
        var width = h.outerWidth( true );
        $(".listtitle").css('width', width);
    }

    resetWidth();
});
indexApp.controller('exceptionlogListController', function($scope, $modal, exceptionlogService) {
	$scope.startDate = '';//开始日期
    $scope.endDate = '';//结束日期
    
    //异常日志列表
    function list(){
    	exceptionlogService.list($scope);
    	initConditionForDate();
    }
    $scope.list = list;

   //日期查询 条件
    function initConditionForDate(){
        var $startDate = $("input[data-ng-model='startDate']");
        var $endDate = $("input[data-ng-model='endDate']");

        $startDate.datepicker({
            onSelect: function(dateText, inst) {
                $endDate.datepicker('option', 'minDate',new Date(dateText.replace('-',',')));
                $scope.startDate = dateText;
            }
        });
        $endDate.datepicker({
            onSelect: function(dateText, inst) {
                $startDate.datepicker('option', 'maxDate',new Date(dateText.replace('-',',')));
                $scope.endDate = dateText;
            }
        });
    }
    
    list();
    
    //详情
    $scope.show = function(id){
       $scope.id = id;
       var modalInstance = $modal.open({
            templateUrl: 'html/template/log/exceptionlogShow.html',
            controller: 'exceptionlogShowController',
            size: 'lg',
            scope : $scope
       });
       modalInstance.opened.then(function(){
       });
    };

    function resetWidth(){
        var h = $(".datacontent");
        var width = h.outerWidth( true );
        //console.log(width);
        $(".listtitle").css('width', width);
    }

    resetWidth();
  
});
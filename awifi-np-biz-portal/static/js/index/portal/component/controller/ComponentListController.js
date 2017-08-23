/**
 * Created by XUXIAOMAN on 2015/12/16.
 */
indexApp.controller('componentListController', function($scope, componentService){

    /**
     * 组件列表
     * @author 许小满
     * @date 2015/12/16
     */
    function list (){
        componentService.list($scope);
    }
    $scope.list = list;
    list();

    /**
     * 组件删除
     * @author 许小满
     * @date 2015/12/22
     */
    $scope.delete = function(id){
        jDialog.confirm('组件管理', '<div class="rows"><label class="w100">您确定要删除该组件吗？</label></div>', function(){
            componentService.delete($scope, id);
            jDialog.hide();
        });
    };

    resetWidth();
});
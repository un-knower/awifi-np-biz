/**
 * Created by Shin on 2015/12/18.
 * 默认站点列表 defaultSiteListController
 */
indexApp.controller('defaultSiteListController', function($scope, $modal, siteService) {
    /**
     * list 默认站点列表
     * @param $scope
     * @author 沈亚芳
     * @date 2015/12/18
     */
    function list (){
        siteService.defaultList($scope);
    }
    $scope.list = list;
    list();


    /**
     * delete 删除默认站点列表中的一个站点
     * @param id
     * @author 沈亚芳
     * @date 2015/12/18
     */
    $scope.delete = function(id) {
        jDialog.confirm('默认站点删除', '<div class="rows"><label class="w100">您确定要删除该默认站点吗？</label></div>', function(){
            siteService.delete($scope, id);
            jDialog.hide();
        });
    };
});
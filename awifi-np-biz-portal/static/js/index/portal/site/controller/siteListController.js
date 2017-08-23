/**
 * Created by Shin on 2015/12/18.
 * 站点列表 siteListController
 */
indexApp.controller('siteListController', function($scope, $modal, customerService, siteService) {

    /**
     * list 站点列表
     * @param $scope
     * @author 沈亚芳
     * @date 2015/12/18
     */
    function list (){
        siteService.list($scope);
    }
    $scope.list = list;
    list();


    /**
     * delete 删除站点列表中的一个站点
     * @param id
     * @author 沈亚芳
     * @date 2015/12/18
     */
    $scope.delete = function(id) {
        jDialog.confirm('站点删除', '<div class="rows"><label class="w100">您确定要删除该站点吗？</label></div>', function(){
            siteService.delete($scope, id);
            jDialog.hide();
        });
    };


    /**
     * copySite 复制一个站点
     * @param id
     * @author 沈亚芳
     * @date 2016/07/04
     */
    $scope.copySite = function(id){
        siteService.copySite($scope, id);
    };


    /**
     * init 初始化函数
     * @author 沈亚芳
     * @date 2016/07/22
     */
    function init (){
        customerService.customerSelect2('customerIdAndLevel');
    }
    init();

});
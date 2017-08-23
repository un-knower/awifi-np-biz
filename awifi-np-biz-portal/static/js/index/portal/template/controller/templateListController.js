/**
 * Created by Shin on 2015/12/18.
 * 模板列表 templateListController
 */
indexApp.controller('templateListController', function($scope, $modal, templateService, industryService, projectService) {

    /**
     * list 站点列表
     * @param $scope
     * @author 沈亚芳
     * @date 2015/12/18
     */
    function list (){
        templateService.list($scope);
    }
    $scope.list = list;
    list();


    /**根据一级行业id获取二级行业集合
     * change 初始化函数
     * @author 沈亚芳
     * @date 2015/12/18
     */
    $scope.change = function($event){
        //获取父行业id(一级行业id)
        var industryId1 = $scope.parentIndustryId;
        //获取二级行业列表
        industryService.industry2List($scope, industryId1);
    };


    /**
     * delete 删除站点列表中的一个站点
     * @param id
     * @author 沈亚芳
     * @date 2015/12/18
     */
    $scope.delete = function(id) {
        jDialog.confirm('模板删除', '<div class="rows"><label class="w100">您确定要删除该模板吗？</label></div>', function(){
            templateService.delete($scope, id);
            jDialog.hide();
        });
    };

    /**
     * 模板修改后，同步由该模板创建的所有站点
     * @param id
     */
    $scope.templateSync = function(id) {
        jDialog.confirm('模板同步', '<div class="rows"><label class="w100">您确定要同步应用此模板的站点吗？</label></div>', function(){
            templateService.templateSync($scope, id);
            jDialog.hide();
        });
    };


    /**
     * copyTemplate 复制一个模板
     * @param $scope ，id
     * @author 沈亚芳
     * @date 2016/07/04
     */
    $scope.copyTemplate = function(id){
        templateService.copyTemplate($scope, id);
    };


    /**
     * init 初始化函数
     * @author 沈亚芳
     * @date 2015/12/18
     */
    function init(){
        //获取一级行业列表
        industryService.industry1List($scope);

        // 项目下拉列表
        projectService.projectSelect2('projectName');
    }
    init();
});
/**
 * Created by Shin on 2015/12/18.
 * 模板管理 templateService
 */
indexApp.factory('templateService',function($rootScope, templateDao) {

    /**
     * service 定义个全局service，并将其置空
     * @param 空
     * @author 沈亚芳
     * @date 2015/12/18
     */
    var service = {};


    /**
     * list 模板列表
     * @param $scope
     * @author 沈亚芳
     * @date 2015/12/18
     */
    service.list = function($scope){
        templateDao.list($scope);
    };


    /**
     * delete 删除模板列表中的一个模板
     * @param $scope
     * @author 沈亚芳
     * @date 2015/12/18
     */
    service.delete = function($scope, id){
        templateDao.delete($scope, id);
    };

    /**
     * 同步应用此模板的站点
     * @Auther: zhuxuehuang
     * @Date: 2016-4-29
     */
    service.templateSync = templateDao.templateSync;


    /**
     * copyTemplate 复制一个模板
     * @param $scope ，id
     * @author 沈亚芳
     * @date 2016/07/04
     */
    service.copyTemplate = function($scope, id){
        templateDao.copyTemplate($scope, id);
    };

    return service;
});
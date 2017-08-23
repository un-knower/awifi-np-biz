/**
 * Created by Shin on 2015/12/18.
 * 站点管理 siteService
 */
indexApp.factory('siteService',function($rootScope, siteDao) {

    /**
     * service 定义个全局service，并将其置空
     * @param 空
     * @author 沈亚芳
     * @date 2015/12/18
     */
    var service = {};


    /**
     * list 站点列表
     * @param $scope
     * @author 沈亚芳
     * @date 2015/12/18
     */
    service.list = function($scope){
        siteDao.list($scope);
    };


    /**
     * delete 删除站点列表中的一个站点 和 删除默认站点列表中的一个站点
     * @param $scope
     * @author 沈亚芳
     * @date 2015/12/18
     */
    service.delete = function($scope, id){
        siteDao.delete($scope, id);
    };


    /**
     * list 站点列表
     * @param $scope
     * @author 沈亚芳
     * @date 2015/12/18
     */
    service.defaultList = function($scope){
        siteDao.defaultList($scope);
    };


    /**
     * copySite 复制一个站点
     * @param $scope ，id
     * @author 沈亚芳
     * @date 2016/07/04
     */
    service.copySite = function($scope, id){
        siteDao.copySite($scope, id);
    };


    return service;
});
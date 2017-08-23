/**
 * Created by Shin on 2015/12/18.
 * 站点管理 siteDao
 */
indexApp.factory('siteDao', function($http, $location, pageDao) {

    /**
     * dao 定义个全局dao，并将其置空
     * @param 空
     * @author 沈亚芳
     * @date 2015/12/18
     */
    var dao = {};


    /**
     * list 站点列表
     * @param $scope 获取列表时要提交的参数为：keywords
     * @author 沈亚芳
     * @date 2015/12/18
     */
    dao.list = function($scope){
        loadingAppend();
        var pageNo = pageDao.getPageNo();

        var customerIdAndLevel = defaultString($scope.customerIdAndLevel);
        //以#拆分customerIdAndLevel
        var customerIdAndLevelArray = customerIdAndLevel.split('#');
        var customerId = customerIdAndLevelArray[0];
        var cascadeLabel = customerIdAndLevelArray[1];

        if(isBlank(customerId)){
            customerId = '';
        }
        if(isBlank(cascadeLabel)){
            cascadeLabel = '';
        }

        var keywords = defaultString($scope.keywords);
        var templateName = defaultString($scope.templateName);

        var url = 'site/list?pageNo=' + pageNo + '&customerId=' + customerId + '&keywords=' + keywords + '&templateName=' + templateName;

        $http.get(url)
            .success(function(data){
                loadingRemove();
                if(data.result == 'FAIL'){
                    jDialog.alert('提示:', data.message);
                    return;
                }
                sites = data.records;
                // 缩略图添加时间参数，强制浏览器端不缓存
                _records = data.records;
                for (var i= 0,len=_records.length; i<len; i++) {
                    var item = _records[i];
                    item.thumb = (item.thumb ? item.thumb + '?_t' + new Date().getTime() : item.thumb);
                }
                //数据记录集
                $scope.sites = _records;
                //开始记录数
                $scope.begin = data.begin;
                $scope.data = data;
                //初始化分页栏
                pageDao.init($scope, data);
            })
    };


    /**
     * delete 删除站点列表中的一个站点 和 删除默认站点列表中的一个站点
     * @param $scope 删除时要提交的参数为：id
     * @author 沈亚芳
     * @date 2015/12/18
     */
    dao.delete = function($scope, id){
        var url = 'site/delete?id=' + id;
        $http.get(url)
            .success(function(data){
                if(data.result == 'FAIL'){
                    jDialog.alert('提示:', data.message);
                    return;
                }
                jDialog.alert('站点删除', '删除成功!');
                $scope.list();
            })
    };

    /**
     * defaultList 站点列表
     * @param $scope 获取列表时要提交的参数为：keywords
     * @author 沈亚芳
     * @date 2015/12/18
     */
    dao.defaultList = function($scope){
        loadingAppend();
        var pageNo = pageDao.getPageNo();
        var keywords = defaultString($scope.keywords);

        var url = 'site/defaultSite?pageNo=' + pageNo + '&keywords=' + keywords;

        $http.get(url)
            .success(function(data){
                loadingRemove();
                if(data.result == 'FAIL'){
                    jDialog.alert('提示:', data.message);
                    return;
                }
                defaultSites = data.records;

                ////当默认站点已存在时，则自动去除a标签的href；当点击这个a标签时，则给予提示
                //var records = defaultSites.length;
                //if(records > 0){
                //    var addSite = $('#addSite');
                //    addSite.removeAttr('href', '');
                //    addSite.on('click', function(){
                //        jDialog.alert('温馨提示:', '默认站点已存在');
                //    });
                //}
                // 缩略图添加时间参数，强制浏览器端不缓存
                _records = data.records;
                for (var i= 0,len=_records.length; i<len; i++) {
                    var item = _records[i];
                    item.thumb = (item.thumb ? item.thumb + '?_t' + new Date().getTime() : item.thumb);
                }
                //数据记录集
                $scope.defaultSites = _records;
                //开始记录数
                $scope.begin = data.begin;
                $scope.data = data;
                //初始化分页栏
                pageDao.init($scope, data);
            })
    };


    /**
     * copySite 复制一个站点
     * @param $scope ，id
     * @author 沈亚芳
     * @date 2016/07/04
     */
    dao.copySite = function ($scope, id) {
        var url = 'site/copy?siteId=' + id;
        $http.get(url)
            .success(function(data){
                if(data.result == 'FAIL') {
                    jDialog.alert('提示：', data.message);
                    return;
                }
                jDialog.alert('复制站点', '站点复制成功');
                $scope.list();
            })
    };


    return dao;
});

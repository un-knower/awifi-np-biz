/**
 * Created by Shin on 2015/12/18.
 * 模板管理 templateDao
 */
indexApp.factory('templateDao', function($http, $location, pageDao) {

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
        var keywords = defaultString($scope.keywords);
        var projectId = $scope.projectId;
        var parentIndustryId = $scope.parentIndustryId;
        var childIndustryId = $scope.childIndustryId;

        if(isBlank(projectId)){
            projectId = '';
        }
        if (isBlank(parentIndustryId)){
            parentIndustryId = '';
        }
        if (isBlank(childIndustryId)){
            childIndustryId = '';
        }

        var url = 'template/list?pageNo=' + pageNo + '&keywords=' + keywords + '&projectId=' + projectId + '&primaryIndustry=' + parentIndustryId + '&secondaryIndustry=' + childIndustryId;

        $http.get(url)
            .success(function(data){
                loadingRemove();
                if(data.result == 'FAIL'){
                    jDialog.alert('提示:', data.message);
                    return;
                }
                templates = data.records;
                // 缩略图添加时间参数，强制浏览器端不缓存
                _records = data.records;
                for (var i= 0,len=_records.length; i<len; i++) {
                    var item = _records[i];
                    item.thumb = (item.thumb ? item.thumb + '?_t' + new Date().getTime() : item.thumb);
                }
                //数据记录集
                $scope.templates = _records;
                //开始记录数
                $scope.begin = data.begin;
                $scope.data = data;
                //初始化分页栏
                pageDao.init($scope, data);
            })
    };


    /**
     * delete 删除模板列表中的一个模板
     * @param $scope 删除时要提交的参数为：id
     * @author 沈亚芳
     * @date 2015/12/19
     */
    dao.delete = function($scope, id){
        var url = 'template/delete?id=' + id;
        $http.get(url)
            .success(function(data){
                if(data.result == 'FAIL'){
                    jDialog.alert('提示:', data.message);
                    return;
                }
                jDialog.alert('模板删除', '删除成功!');
                $scope.list();
            })
    };

    /**
     * 同步应用此模板的站点接口
     * @param $scope
     * @param id 模板id
     * @Auther: zhuxuehuang
     * @Date: 2016-4-29
     */
    dao.templateSync = function ($scope, id) {
        var url = '/template/synsite?templateId=' + id;
        $http.get(url).success(function (data) {
            if (data.result == 'FAIL') {
                jDialog.alert('提示:', data.message);
                return;
            }
            jDialog.alert('模板同步', '同步应用此模板的站点接口成功!');
            $scope.list();
        });
    };


    /**
     * copyTemplate 复制一个模板
     * @param $scope ，id
     * @author 沈亚芳
     * @date 2016/07/04
     */
    dao.copyTemplate = function ($scope, id) {
        var url = 'template/copy?templateId=' + id;
        $http.get(url)
            .success(function(data){
                if(data.result == 'FAIL') {
                    jDialog.alert('提示：', data.message);
                    return;
                }
                jDialog.alert('复制模板', '模板复制成功');
                $scope.list();
            })
    };

    return dao;
});

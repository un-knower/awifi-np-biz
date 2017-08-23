/**
 * Created by XUXIAOMAN on 2015/12/16.
 */
indexApp.factory('componentDao', function($http, $location, pageDao) {

    var dao = {};

    /**
     * 组件列表
     * @param $scope 获取列表时要提交的参数为：pageNo、keywords
     * @author 许小满
     * @date 2015/12/16
     */
    dao.list = function($scope){
        loadingAppend();
        var pageNo = pageDao.getPageNo();
        var keywords = defaultString($scope.keywords);
        var url = '/component/list?pageNo=' + pageNo + '&keywords=' + keywords;
        $http.get(url)
            .success(function(data, status, headers, config){
                loadingRemove();
                if(data.result == 'FAIL'){
                    jDialog.alert('提示:', data.message);
                    return;
                }
                //数据记录集
                $scope.components = data.records;
                //开始记录数
                $scope.begin = data.begin;
                $scope.data = data;
                //初始化分页栏
                pageDao.init($scope, data);
            });
    };

    /**
     * 组件注册，添加成功后返回至组件列表页面
     * @param $scope
     * @author 许小满
     * @date 2015/12/16
     */
    dao.add = function ($scope) {
        $('#btnAdd').attr('disabled',true);
        var canUnique = $("input[name='canUnique1']:checked").val();
        // 项目求值
        var projectIds = [];
        $('#projectIdSelect a').each(function () {
            projectIds.push($(this).attr('data-id'));
        });
        var filterProjectIdSelect = [];
        $("#filterProjectIdSelect a").each(function () {
            filterProjectIdSelect.push($(this).attr('data-id'));
        });

        var params = {
            "classify": $scope.classify_id,
            "canUnique": canUnique,
            "projectIds": projectIds.join(','),
            "filterProjectIds": filterProjectIdSelect.join(",")
        };
        var url = '/component/add?' + $.param(params);
        var options={
            //exception:function exception(data){alert(data)},
            //另外的一些属性:
            url:url, // 默认是form的action，如果写的话，会覆盖from的action.
            type:'post',        // 默认是form的method，如果写的话，会覆盖from的method.('get' or 'post').
            dataType:'json',        // 'xml', 'script', or 'json' (接受服务端返回的类型.)
            clearForm: false,       // 成功提交后，清除所有的表单元素的值.
            resetForm: false,        // 成功提交后，重置所有的表单元素的值.
            //timeout:3000, 	//由于某种原因,提交陷入无限等待之中,timeout参数就是用来限制请求的时间,当请求大于3秒后，跳出请求.
            beforeSubmit:function(){return true;},  // 提交前
            success: function(data){  // 提交后
                if(data.result == 'FAIL'){
                    $('#btnAdd').attr('disabled',false);
                    jDialog.alert('提示:', data.message);
                    return;
                }
                jDialog.alert('组件管理:', '组件注册成功');
                $location.path('/component/list');
                $scope.$apply();
            }
        };
        $('#componentForm').ajaxSubmit(options); //'ajaxForm' 方式的表单
    };

    /**
     * 组件编辑，编辑成功后返回至组件列表页面
     * @param $scope
     * @author 许小满
     * @date 2015/12/21
     */
    dao.edit = function ($scope) {
        $('#btnEdit').attr('disabled',true);
        // 项目求值
        var projectIds = [];
        $('#projectIdSelect a').each(function () {
            projectIds.push($(this).attr('data-id'));
        });
        var filterProjectIdSelect = [];
        $("#filterProjectIdSelect a").each(function () {
            filterProjectIdSelect.push($(this).attr('data-id'));
        });

        var params = {
            "classify": $scope.component.classify,
            "canUnique": $scope.component.canUnique,
            "projectIds": projectIds.join(','),
            "filterProjectIds": filterProjectIdSelect.join(",")
        };
        var url = '/component/edit?' + $.param(params);
        var options={
            //exception:function exception(data){alert(data)},
            //另外的一些属性:
            url:url, // 默认是form的action，如果写的话，会覆盖from的action.
            type:'post',        // 默认是form的method，如果写的话，会覆盖from的method.('get' or 'post').
            dataType:'json',        // 'xml', 'script', or 'json' (接受服务端返回的类型.)
            clearForm: false,       // 成功提交后，清除所有的表单元素的值.
            resetForm: false,        // 成功提交后，重置所有的表单元素的值.
            //timeout:3000, 	//由于某种原因,提交陷入无限等待之中,timeout参数就是用来限制请求的时间,当请求大于3秒后，跳出请求.
            beforeSubmit:function(){return true;},  // 提交前
            success: function(data){  // 提交后
                if(data.result == 'FAIL'){
                    $('#btnEdit').attr('disabled',false);
                    jDialog.alert('提示:', data.message);
                    return;
                }

                jDialog.alert('组件管理:', '组件编辑成功');
                $location.path('/component/list');
                $scope.$apply();
            }
        };
        $('#componentForm').ajaxSubmit(options); //'ajaxForm' 方式的表单
    };

    /**
     * 查看
     * @param id 组件表主键id
     * @author 许小满
     * @date 2015/12/21
     */
    dao.show = function($scope, id){
        var url = '/component/show?id=' + id;
        $http.get(url)
            .success(function(resp, status, headers, config){
                if(resp.result == 'FAIL'){
                    jDialog.alert('提示:', resp.message);
                    return;
                }
                //数据记录集
                var _data = resp.data;
                $scope.component = _data;

                // 所选项目值
                var projectIds = _data.projectIdsDsp.split(","),
                    filterProjectIds = _data.filterProjectIdsDsp.split(","),
                    projectNames = _data.projectNames.split(","),
                    filterProjectNames = _data.filterProjectNames.split(",");

                // 已选项目下拉列表回执
                global_projectIdSelected = projectIds;
                global_filterProjectIdSelected = filterProjectIds;

                var projectHtml = [],
                    filterProjectHtml = [];
                for (var item in projectIds) {
                    if (projectIds[item]) {
                        projectHtml.push('<a href="javascript:;" data-index="1" data-id="' + projectIds[item] + '" data-text="' + projectNames[item] + '"><span>×</span>' + projectNames[item] + '</a>');
                    }
                }
                $("#projectIdSelect").html(projectHtml.join(''));

                for (var item in filterProjectIds) {
                    if (filterProjectIds[item]) {
                        filterProjectHtml.push('<a href="javascript:;" data-index="2" data-id="' + filterProjectIds[item] + '" data-text="' + filterProjectNames[item] + '"><span>×</span>' + filterProjectNames[item] + '</a>');
                    }
                }
                $("#filterProjectIdSelect").html(filterProjectHtml.join(''));
            });
    };

    /**
     * 删除
     * @param id 组件表主键id
     * @author 许小满
     * @date 2015/12/22
     */
    dao.delete = function($scope, id){
        var url = '/component/delete?id=' + id;
        $http.get(url)
            .success(function(data, status, headers, config){
                if(data.result == 'FAIL'){
                    jDialog.alert('提示:', data.message);
                    return;
                }
                jDialog.alert('组件管理:', '组件删除成功');
                $scope.list();
            });
    };


    return dao;
});
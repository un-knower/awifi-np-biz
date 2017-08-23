/**
 * Created by 牛华凤 on 2015/11/26.
 * 项目管理 projectService
 */
indexApp.factory('projectService',function($rootScope, $location){
    /**
     * service 定义个全局service，并将其置空
     * @param 空
     * @author 牛华凤
     * @date 2015/11/26
     */
    var service = {};


    // 请选择项目select2实现
    service.projectSelect2 = function (selectId) {
        selectId = selectId || 'fkProjectId';
        var ajaxUrl = '/project/projectList';
        return projectSelect(selectId, ajaxUrl);
    };

    /**
     * 项目名称下拉列表 select2 异步请求数据
     */
    function projectSelect(selectId, ajaxUrl) {
        var userId = $location.search()['userId'];
        req_data = {'userId': userId};

        var $select = $('#' + selectId);

        var project_select = $select.select2({
            language: "zh-CN",
            placeholder: '请选择项目',
            allowClear: true,
            ajax: {
                url: ajaxUrl,
                dataType: 'json',
                delay: 0, // 延时可能清空时未恢复
                data: function (params) {
                    return {
                        keywords: params.term,
                        userId: req_data.userId ? req_data.userId : ''
                    };
                },
                processResults: function (data, params) {
                    var _data = data.data;
                    for (var i = 0,len = _data.length; i<len; i++) {
                        var _item = _data[i];
                        _item['id'] = _item.projectId;
                        _item['text'] = _item.projectName;
                    }
                    return {
                        results: _data
                    };
                },
                cache: true
            },
            escapeMarkup: function (markup) {
                return markup;
            },
            templateResult: function (data) {
                if (data.loading) return '正在搜索...';
                var markup = "<div>" + data.projectName + "</div>";
                return markup;
            },
            templateSelection: function (data) {
                return data.projectName || data.text;
            }
        });

        return project_select;
    }

    return service;
});
/**
 * Created by zhuxuehuang on 16-5-24.
 */

// 已选项目选项Id数组
var global_projectIdSelected = [],
    global_filterProjectIdSelected = [];

indexApp.factory('projectDao', function ($location) {
    var dao = {};

    /**
     * 项目名称下拉列表 select2 异常请求数据
     * @param $scope
     * @param idName
     * @returns {*|jQuery}
     */
    dao.projectSelect2 = function ($scope, idName) {
        idName = idName || 'fkProjectId';
        var userId = $location.search()['userId'];
        var url = '/project/projectList',
            req_data = {'userId': userId};
        var $select = $('#' + idName);

        var project_select = $select.select2({
            language: "zh-CN",
            placeholder: '请选择项目',
            // allowClear: true,
            // minimumResultsForSearch: 1,
            // showSearch: true,
            ajax: {
                url: url,
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

        project_select.on("select2:close", function (e) {
            var target = e.target;
            var textContent = "";
            var selectDivId = $(target).attr("id");
            var selectValue = target.value;

            // 未选中select2选项值
            if (selectValue.toString().indexOf("undefined") != -1 || selectValue == "") {
                return ;
            }

            $(target).find('option').each(function () {
                var option_this = $(this);
                if (option_this.val() == selectValue) {
                    textContent = option_this.text();
                    return;
                }
            });

            var select2 = $select.next('.select2-container');
            var $projectIdSelect = select2.next(".projectIdSelect");
            var $projectIdSelectA = $projectIdSelect.find("a");
            var hasItem = false;

            $projectIdSelectA.each(function () {
                var that = $(this);
                if (that.attr("data-id") == selectValue) {
                    that.remove();
                    hasItem = true;

                    if (selectDivId == "projectId") {
                        global_projectIdSelected.splice($.inArray(selectValue, global_projectIdSelected), 1);
                    }

                    if (selectDivId == "filterProjectId") {
                        global_filterProjectIdSelected.splice($.inArray(selectValue, global_filterProjectIdSelected), 1);
                    }
                }
            });

            // 过滤项目 默认选项不能选择
            if (!hasItem) {
                var addItem = false;

                // 包含项目和排除项目选择不能共存（值同时存在）
                if (selectDivId == "projectId" && $.inArray(selectValue, global_filterProjectIdSelected) == -1) {
                    addItem = true;
                }
                // 过滤项目 默认选项不能选择
                if (selectDivId == "filterProjectId" && selectValue != "0" && $.inArray(selectValue, global_projectIdSelected) == -1) {
                    addItem = true;
                }

                if (addItem) {
                    var item = '<a href="javascript:;" data-index="'+(selectDivId == "projectId" ? "1" : "2")+'" data-id="' + selectValue + '" data-text="' + textContent + '"><span>×</span>' + textContent + '</a>';
                    $projectIdSelect.append(item);

                    if (selectDivId == "projectId") {
                        if ($.inArray(selectValue, global_projectIdSelected) == -1) {
                            global_projectIdSelected.push(selectValue);
                        }
                    }

                    if (selectDivId == "filterProjectId") {
                        if ($.inArray(selectValue, global_filterProjectIdSelected) == -1) {
                            global_filterProjectIdSelected.push(selectValue);
                        }
                    }
                }
            }

            // 清空option列表
            $(target).html("");
            // 还原至请选择项目
            select2.find('.select2-selection__rendered').html('<span class="select2-selection__placeholder">请选择项目</span>');
        });

        // 已选项目名称点击删除
        $(document).on("click", ".projectIdSelect a", function () {
            var that = $(this),
                projectIndex = that.attr("data-index"),
                value = that.attr("data-id");
            if (projectIndex == "1") {
                global_projectIdSelected.splice($.inArray(value, global_projectIdSelected), 1);
            } else if (projectIndex == "2") {
                global_filterProjectIdSelected.splice($.inArray(value, global_filterProjectIdSelected), 1);
            }
            $(this).remove();
        });

        return project_select;
    };

    return dao;
});
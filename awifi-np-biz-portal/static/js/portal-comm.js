/**
 * Created by zhuxh on 15-12-31.
 */

/**
 * 获取URL参数
 * @returns {{}}
 */
function getArgs() {
    var args = {};
    var match = null;
    var search = location.search.substring(1);
    var reg = /(?:([^&]+)=([^&]+))/g;
    while((match = reg.exec(search))!==null){
        args[match[1]] = match[2];
    }
    return args;
}

/**
 * 获取行业列表
 * @Auther: zhuxh
 * @Date: 2015-12-28
 */
function get_industry(parentId) {
    // 客户侧帐号添加模板不能选择行业
    var get = getArgs(),
        userId = get["userId"],
        url = '/industry/list';

    var selectHtml = function(parentId, resp) {
        var htm = ['<option value="">' + (parentId ? "选择二级行业" : "选择一级行业") + '</option>'],
            data = resp.data;
        for (var i = 0, len = data.length; i < len; i++) {
            var v = data[i];
            htm.push('<option value="' + v.industryId + '">' + v.industryName + '</option>');
        }
        if (htm.length > 0) {
            if (parentId) {
                $('#secondary_industry').html(htm.join(''));
            } else {
                $('#primary_industry').html(htm.join(''));
                $('#secondary_industry').html('<option value="">选择二级行业</option>');
            }
        }
    };

    if (userId && userId.length > 0 && !parentId) {
        $.ajax({
            url: url,
            type: 'GET',
            data: {userId: userId},
            dataType: "json",
            success: function (resp) {
                if (resp.result == 'OK') {
                    if (resp.data) {
                        // superadmin帐号
                        selectHtml(parentId, resp);
                    } else {
                        // 客户侧帐号
                        $('#primary_industry').html('<option selected="selected" value="' + resp.parentIndustryId + '">' + resp.parentIndustry + '</option>').prop('disabled', 'disabled');
                        $('#secondary_industry').html('<option selected="selected" value="' + resp.childIndustryId + '">' + resp.childIndustry + '</option>').prop('disabled', 'disabled');
                    }
                } else {
                    jDialog.alert('提示', resp.message);
                }
            }
        });
    } else {
        // 选择行业下拉列表事件
        parentId = parentId || '';
        var req_data = parentId ? {parentId: parentId} : {};
        if (userId && userId.length > 0) {
            req_data['userId'] = userId;
        }
        $.ajax({
            url: url,
            type: 'GET',
            data: req_data,
            dataType: 'json',
            beforeSend: function () {
            },
            complete: function () {
            },
            success: function (resp) {
                if (resp.result == 'OK') {
                    selectHtml(parentId, resp);
                } else {
                    jDialog.alert('提示', resp.message);
                }
            }
        });
    }
}

/**
 * 获取项目列表
 * @Auther: zhuxh
 * @Date: 2015-12-28
 */
function get_project() {
    var url = '/project/projectList',
        gets = getArgs(),
        req_data = {'userId': gets['userId']};
    /*
    $.ajax({
        url: url,
        type: 'get',
        data: req_data,
        dataType: 'json',
        beforeSend: function () {
        },
        complete: function () {
        },
        success: function (resp) {
            if (resp.result == 'OK') {
                var htm = [],
                    data = resp.data;
                for (var i = 0, len = data.length; i < len; i++) {
                    var v = data[i];
                    htm.push('<option value="' + v.projectId + '">' + v.projectName + '</option>');
                }
                if (htm.length > 0) {
                    $('#fk_project_id').append(htm.join(''));
                }
            } else {
                jDialog.alert('提示', resp.message);
            }
        }
    });
    */

    var project_select = $('#fk_project_id').select2({
        placeholder: '请选择项目',
        allowClear: true,
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
    return project_select;
}

/**
 * 
 * @Auther: zhuxh
 * @Date: 2015-12-28
 */
function get_project_default() {
    var url = '/project/isProjectRojht',
        gets = getArgs(),
        req_data = {'userId': gets['userId']};
    $.ajax({
        url: url,
        type: 'GET',
        data: req_data,
        dataType: 'json',
        success: function (resp) {
            if (resp.result == 'OK') {
                var data = resp.data;
                if (data == 0) {
                    jDialog.alert('提示', '您没有添加模板功能权限');
                    setTimeout(function () {
                        location.href = '/';
                    });
                }
            } else {
                jDialog.alert('提示', resp.message);
            }
        }
    });
}

/**
 * 获取客户列表
 * @Auther: zhuxh
 * @Date: 2015-12-28
 */
function get_customer(parentId) {
    parentId = parentId || '';
    var url = '/customer/optionsList',
        req_data = parentId ? {
            'userId': !!getArgs()['userId'] ? getArgs()['userId'] : '',
            parentId: parentId
        } : {
            'userId': !!getArgs()['userId'] ? getArgs()['userId'] : ''
        };
    /*
    $.ajax({
        url: url,
        type: 'get',
        data: req_data,
        dataType: 'json',
        beforeSend: function () {
        },
        complete: function () {
        },
        success: function (resp) {
            if (resp.result == 'OK') {
                var htm = ['<option value="">选择客户</option>'],
                    data = resp.data;
                for (var i = 0, len = data.length; i < len; i++) {
                    var v = data[i];
                    htm.push('<option data-label="' + v.cascadeLabel + '" data-level="' + v.cascadeLevel + '" data-projectid="' + v.projectId + '" value="' + v.customerId + '">' + v.customerName + '</option>');
                }
                if (htm.length > 0) {
                    $('#customer_name').html(htm.join(''));
                }
            } else {
                jDialog.alert('提示', resp.message);
            }
        }
    });
    */

    $('#customer_name').select2({
        placeholder: '请选择客户',
        allowClear: true,
        ajax: {
            url: url,
            dataType: 'json',
            delay: 0, // 延时可能清空时未恢复
            data: function (params) {
                return {
                    keywords: params.term,
                    userId: req_data.userId ? req_data.userId : '',
                    parentId: req_data.parentId ? req_data.parentId : ''
                };
            },
            processResults: function (data, params) {
                var _data = data.data;
                for (var i = 0,len = _data.length; i<len; i++) {
                    var _item = _data[i];
                    _item['id'] = _item.customerId + '#' + _item.cascadeLabel + '#' + _item.projectId + '#' + _item.cascadeLevel;
                    _item['text'] = _item.customerName
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

            var markup = "<div>" + data.customerName + "</div>";
            return markup;
        },
        templateSelection: function (data) {
            return data.customerName || data.text;
        }
    });
}

/**
 * 查询模板列表
 * @Auther: zhuxh
 * @Date: 2015-12-28
 */
function get_template_list(data) {
    var url = '/template/listsite',
        req_data = {
            'customerId': data.customerId,
            'projectId': data.projectId,
            'primaryIndustry': data.primaryIndustry,
            'secondaryIndustry': data.secondaryIndustry,
            'keywords': data.keywords,
            'pageNo': data.pageNo
        };
    $.ajax({
        url: url,
        type: 'get',
        data: req_data,
        dataType: 'json',
        beforeSend: function () {
        },
        complete: function () {
        },
        success: function (resp) {
            if (resp.result == 'OK') {
                var htm = [],
                    data = resp.totalRecord > 0 ? resp.records : [];
                for (var i = 0, len = data.length; i < len; i++) {
                    var v = data[i];
                    htm.push('<div class="template-item" data-tid="'+v.id+'" data-pid="-1">' +
                        '<img src="'+(v.thumb ? v.thumb : '/images/site-default.png')+'" alt="'+v.templateName+'" />' +
                        '<p>' + v.templateName + '</p>' +
                        '<i class="icon-checkbox"></i>' +
                        '</div>');
                }
                $('#template-list').html(htm.length > 0 ? htm.join('') : '<div class="template-item-no">没有模板数据</div>');
                if (resp.totalRecord > 0) {
                    $('#pager').show();
                    $('#list_page').attr('data-max', resp.totalPage);
                } else {
                    $('#pager').hide();
                }
            } else {
                jDialog.alert('提示', resp.message);
            }
        }
    });
}

/**
 * 获取当前帐号名称
 */
function getAccountName() {
    $.get('/user/getcurusername', function (resp) {
        if (resp.result === 'OK') {
            $('.header-user').text(resp.userName);
        }
    }, 'json');
}

/**
 * 按钮右侧提示
 * @param content 提示内容
 * @param className css类名 tips_success | tips_fault 默认错误
 */
function result_tips (content, className, divId) {
    divId = divId || 'result_tips';
    className = className || 'tips_fault';
    $('#' + divId).html(content).removeClass('tips_success tips_fault').addClass(className);
}

$(function () {
    getAccountName();

    /**
     * 用户菜单显示和收起
     */
    $('.header .header-user').on('click', function (e) {
        var $profile = $('.header-profile');
        if($profile.height() === 0){
            $profile.css('height', $profile.find('ul').outerHeight());
        } else {
            $profile.css('height', 0);
        }
    });

    /**
     * 收起用户菜单
     */
    $('.header-profile').on('mouseleave', function(e){
        $(this).css('height', 0);
    });
});
/**
 * Created by zhuxh on 15-12-28.
 */

var this_form = function() {
    var isSite = 0; // 1是站点，0是模板
    var _parentId = -1; // 默认一级
    var check_input = function (id, msg, regExp, regMsg) {
        var $id_name = $('#' + id);
        var id_name = $.trim($id_name.val());
        regExp = regExp || '';
        regMsg = regMsg || '';
        if(id_name === null || id_name === undefined || id_name.length === 0){
            updateTips($id_name, msg);
            showTips($id_name);
            return false;
        }
        if (regExp && !regExp.test(id_name)) {
            updateTips($id_name, regMsg);
            showTips($id_name);
            return false;
        }
        return true;
    };

    var initTip = function(){//初始化tips
        initTips($('#customer_name')); //客户名称
        initTips($('#template_name')); //站点名称
    };

    /**
     * 提交表单
     * @Auther: zhuxh
     * @Date: 2015-12-28
     */
    var submit = function () {
        var url = '/site/addSite';
        var btn = $('.f-btn-sure');
        var customer_name = $('#customer_name option:selected'); // _item.customerId + '#' + _item.cascadeLabel + '#' + _item.projectId + '#' + _item.cascadeLevel;
        var customer_name_values = customer_name.val().split('#');
        var select_tid = $('#select-tid').val();
        if (!select_tid) {
            jDialog.alert('提示', '请先选择模板或站点');
            return ;
        }
        $.ajax({
            url: url,
            type: 'post',
            data: {
                siteName: $('#template_name').val(),
                tempOrSiteId: select_tid,
                customerId: customer_name_values[0], // customer_name.val(),
                cascadeLabel: customer_name_values[1], // customer_name.attr('data-label'),
                parentId: _parentId,
                isSite: isSite
            },
            dataType: 'json',
            beforeSend: function () {
                btn.attr('disabled', 'disabled');
            },
            complete: function () {
                btn.removeAttr('disabled');
            },
            success: function (resp) {

                if (resp.result == 'OK') {
                    result_tips(resp.message, 'tips_success');

                    setTimeout(function () {
                        location.href = '/tool?siteId=' + resp.id;
                    }, 1500);
                } else {
                    result_tips(resp.message);
                }
            },
            error: function() {
                jDialog.alert('提示', '请求错误，请重试');
            }
        });
    };

    /**
     * 二级客户先判断是否有添加模板的权限，有查模板列表，没有查站点列表
     * @param customerId 客户id
     * @param permissionId 权限id（固定值51）
     * @returns {number} 1有权限，0没有权限
     */
    var get_haspermission = function (customerId, permissionId) {
        permissionId = permissionId || 51;
        var url = '/permission/haspermission',
            permission_data = {
                'customerId': customerId,
                'permissionId': permissionId
            };
        var ret_data = 0; // 默认请求站点
        $.ajax({
            url: url,
            type: 'get',
            cache : false,
            async : false, // 同步请求
            data: permission_data,
            dataType: 'json',
            success: function (resp) {
                if (resp.result.toUpperCase() == 'OK') {
                    if (resp.hasPermission.toLowerCase() == 'yes') {
                        // 请求模板数据
                        ret_data = 1;
                    }
                } else {
                    jDialog.alert('提示', resp.message);
                }
            },
            error: function() {
                jDialog.alert('提示', '请求错误，请重试');
            }
        });
        return ret_data;
    };


    // 判断查询模板数据
    var get_list_data = function () {
        var selected = $('#customer_name').find("option:selected"), // _item.customerId + '#' + _item.cascadeLabel + '#' + _item.projectId + '#' + _item.cascadeLevel;
            selected_values = selected.val().split('#');
            level = selected_values[3], // selected.attr('data-level'),
            projectId = selected_values[2], // selected.attr('data-projectid'),
            customer_id = selected_values[0], // selected.val(),
            page = $('#list_page').val(),
            keywords = $('#keywords').val();
        if (customer_id && level > 0) {
            isSite = 1;
            // 判断是否有添加模板权限
            var has_permission = get_haspermission(customer_id, 51);

            if (has_permission === 0) {
                // 站点列表 2级
                var req_data = {
                    'projectId': projectId,
                    'customerId': customer_id,
                    'keywords': keywords,
                    'pageNo': page
                };
                get_site_list(req_data);
            } else {
                isSite = 0;
                // 模板列表 1级
                var req_data = {
                    'customerId': customer_id,
                    'projectId': projectId,
                    'keywords': keywords,
                    'pageNo': page
                };
                get_template_list(req_data);
            }
        } else if (customer_id) {
            isSite = 0;
            // 模板列表 1级
            var req_data = {
                'customerId': customer_id,
                'projectId': projectId,
                'keywords': keywords,
                'pageNo': page
            };
            get_template_list(req_data);
        } else {
            $('#template-list').html('<div class="template-item-no">请选择客户</div>');
            $('#pager').hide();
        }
    };

    /**
     * 查询模板列表
     * @Auther: zhuxh
     * @Date: 2015-12-28
     */
    var get_site_list = function(data) {
        var url = '/site/parentList',
            req_data = {
                'projectId': data.projectId,
                'userId': !!getArgs()['userId'] ? getArgs()['userId'] : '',
                'customerId': data.customerId,
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
                        htm.push('<div class="template-item" data-tid="'+v.id+'" data-pid="'+v.id+'">' +
                            '<img src="'+(v.thumb ? v.thumb : '/images/site-default.png')+'" alt="'+v.siteName+'" />' +
                            '<p>' + v.siteName + '</p>' +
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
            },
            error: function() {
                jDialog.alert('提示', '请求错误，请重试');
            }
        });
    };


    var bindEvent = function() {//绑定事件
        $(".f-btn-sure").click(function () {
            if (check_input('customer_name', '请选择客户！')) {
                if (check_input('template_name', '请填写站点名称！', new RegExp('^[a-zA-Z0-9\u4e00-\u9fa5\_]{1,50}$', 'g'), '站点名称由1-50位汉字、字母、数字、下划线组成')) {
                    submit();
                }
            }
        });

        // 选中模板
        $(document).on('click', '#template-list .template-item', function () {
            var that = $(this),
                tid = that.attr('data-tid');
            that.addClass('template-checked').siblings('.template-checked').removeClass('template-checked');
            $('#select-tid').val(tid);
            _parentId = that.attr('data-pid');
        });

        // 分页
        $(document).on('click', '#pager-prev', function () {
            var page = $('#list_page').val();
            if (page && page > 1) {
                $('#list_page').val(parseInt(page) - 1);
                get_list_data();
            } else {
                jDialog.alert('提示', '没有上一页');
            }
        });

        $(document).on('click', '#pager-next', function () {
            var list_page = $('#list_page'),
                page = list_page.val(),
                page_max = list_page.attr('data-max');
            if (page_max && page < page_max) {
                $('#list_page').val(parseInt(page) + 1);
                get_list_data();
            } else {
                jDialog.alert('提示', '没有下一页');
            }
        });

        // 查询列表
        $('.f-btn-search').click(function () {
            $('#list_page').val(1);
            if (check_input('customer_name', '请选择客户！')) {
                get_list_data();
            }
        });

        // 回车登录
        $('#template_name').on('keypress', function(e){
            if(e.keyCode == 13){ // Enter
                $(this).blur();
                $('.f-btn-sure').click();
            }
        });

        // 一级行业选择
        $('#customer_name').change(function () {
            // 清空关键词
            $('#keywords').val('');
            $('#list_page').val(1);

            get_list_data();
        });

        // 一级行业选择
        $('#primary_industry').change(function () {
            var that = $(this);
            get_industry(that.val());
        });

        // 取消事件
        $('.f-btn-cancel').on('click', function() {
            window.close();
        });
    };

    // 仅当portal平台右上角显示用户信息
    function header_right() {
        if (location.pathname.indexOf('/site/add') === -1) {
            $('.header-right').hide();
        }
    }

    var init = function() {  //初始化函数
        initTip();  //初始化tips
        bindEvent();  //绑定事件
        get_industry(); //获取一级行业
        get_customer();  //获取客户列表
        header_right(); //右上角显示用户信息
    };

    return {
        init: init
    }

};


$(function (){
    this_form().init();
});
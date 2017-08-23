/**
 * Created by zhuxh on 15-12-28.
 */

var this_form = function() {
    var get = getArgs(),
        siteId = get['siteDefaultId'];

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
        initTips($('#template_name')); //站点名称
    };

    /**
     * 提交表单
     * @Auther: zhuxh
     * @Date: 2015-12-28
     */
    var submit = function () {
        var url = '/siteDefault/addDefaultSite',
            req_data = {
                siteName: $('#template_name').val(),
                templateId: $('#select-tid').val(),
                parentId: -1
            };
        if (siteId > 0) {
            url = '/siteDefault/editDefaultSite';
            req_data = {
                siteId: siteId,
                siteName: $('#template_name').val(),
                templateId: $('#select-tid').val(),
                parentId: -1
            };
        }
        var btn = $('.f-btn-sure');
        var select_tid = $('#select-tid').val();
        if (!select_tid) {
            jDialog.alert('提示', '请先选择模板');
            return ;
        }
        $.ajax({
            url: url,
            type: 'post',
            data: req_data,
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
                        location.href = '/tool?siteDefaultId=' + (siteId > 0 ? siteId : resp.id);
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

    function getDefaultSite(siteId) {
        var url = '/siteDefault/queryDefaultSite',
            req_data = {
                siteId: siteId
            };
        $.ajax({
            url: url,
            type: 'post',
            data: req_data,
            dataType: 'json',
            beforeSend: function () {
                $('#template-list-wrap').hide();
            },
            complete: function () {
            },
            success: function (resp) {
                if (resp.result == 'OK') {
                    var data = resp.data;
                    $('#template_name').val(data.siteName);
                    $('#select-tid').val(data.templateId);
                    setTimeout(function () {
                        checkTemplate(data.templateId);
                    }, 1000);
                } else {
                    jDialog.alert('提示', resp.message);
                }
            }
        });
    }

    /**
     * 选中模板样式
     * @param templateId
     */
    function checkTemplate(templateId) {
        $('#template-list .template-item').each(function () {
            var that = $(this),
                tid = that.attr('data-tid');
            if (tid == templateId) {
                that.addClass('template-checked');
            }
        });
    }

    // 判断查询模板数据
    var get_list_data = function () {
        var selected = $('#customer_name').find("option:selected"),
            level = selected.attr('data-level'),
            customer_id = selected.val(),
            page = $('#list_page').val(),
            keywords = $('#keywords').val();

        // 模板列表 1级
        var primaryIndustry = $('#primary_industry').find('option:selected').val(),
            secondaryIndustry = $('#secondary_industry').find('option:selected').val();
        var req_data = {
            'primaryIndustry': primaryIndustry,
            'secondaryIndustry': secondaryIndustry,
            'keywords': keywords,
            'pageNo': page
        };
        $('#search-industry').show();
        get_template_list(req_data);
    };




    var bindEvent = function() {//绑定事件
        $(".f-btn-sure").click(function () {
            if (check_input('template_name', '请填写站点名称！', new RegExp('^[a-zA-Z0-9\u4e00-\u9fa5\_]{1,50}$', 'g'), '站点名称由1-50位汉字、字母、数字、下划线组成')) {
                submit();
            }
        });

        // 选中模板
        $(document).on('click', '#template-list .template-item', function () {
            var that = $(this),
                tid = that.attr('data-tid');
            that.addClass('template-checked').siblings('.template-checked').removeClass('template-checked');
            $('#select-tid').val(tid);
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
            get_list_data();
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
        if (location.pathname.indexOf('/site/default_add') === -1) {
            $('.header-right').hide();
        }
    }

    var init = function() {  //初始化函数
        initTip();  //初始化tips
        bindEvent();  //绑定事件
        get_industry(); //获取一级行业
        get_list_data(); //获取模板列表
        if (siteId > 0) {
            getDefaultSite(siteId);
        }
        header_right(); //右上角显示用户信息
    };

    return {
        init: init
    }

};


$(function (){
    this_form().init();
});
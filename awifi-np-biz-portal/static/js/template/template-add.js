/**
 * Created by zhuxh on 15-12-25.
 */
var project_select = null;

var this_form = function() {
    var get = getArgs(),
        templateId = get['templateId'];

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
        initTips($('#template_name'));//模板名称
        initTips($('#primary_industry'));//一级行业
        initTips($('#secondary_industry'));//二级行业
        initTips($('#fk_project_id'));//项目名称
    };

    /**
     * 提交表单
     * @Auther: zhuxh
     * @Date: 2015-12-28
     */
    var submit = function () {
        var url = '/template/add',
            req_data = {
                templateName: $('#template_name').val(),
                fkProjectId: $('#fk_project_id option:selected').val(),
                primaryIndustry: $('#primary_industry option:selected').val(),
                secondaryIndustry: $('#secondary_industry option:selected').val(),
                remark: $('#remark').val()
            };
        if (templateId > 0) {
            url = '/template/edit';
            req_data = {
                templateId: templateId,
                templateName: $('#template_name').val(),
                fkProjectId: $('#fk_project_id option:selected').val(),
                primaryIndustry: $('#primary_industry option:selected').val(),
                secondaryIndustry: $('#secondary_industry option:selected').val(),
                remark: $('#remark').val()
            };
        }
        var btn = $('.f-btn-sure');
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
                        location.href = '/tool?templateId=' + (templateId > 0 ? templateId : resp.id);
                    }, 1500);
                } else {
                    result_tips(resp.message);
                }
            }
        });

    };

    function getTemplate(templateId) {
        var url = '/template/queryTemplate',
            req_data = {
                templateId: templateId
            };
        $.ajax({
            url: url,
            type: 'post',
            data: req_data,
            dataType: 'json',
            beforeSend: function () {
            },
            complete: function () {
            },
            success: function (resp) {
                if (resp.result == 'OK') {
                    var data = resp.data;
                    $('#template_name').val(data.templateName);
                    $('#remark').val(data.remark);
                    // 回执选中值
                    $('#fk_project_id').append('<option value="'+data.projectId+'">'+data.projectName+'</option>');
                    project_select.val(data.projectId).trigger('change');
                    $('#primary_industry option[value="'+data.primaryIndustry+'"]').attr('selected', true).change();
                    setTimeout(function () {
                        $('#secondary_industry option[value="'+data.secondaryIndustry+'"]').attr('selected', true);

                        // 解除保存按钮禁用
                        $('.f-btn-sure').removeProp('disabled');
                    }, 1000);
                } else {
                    jDialog.alert('提示', resp.message);
                }
            }
        });
    }

    /**
     * 绑定事件
     * @Auther: zhuxh
     * @Date: 2015-12-28
     */
    var bindEvent =function() {
        $(".f-btn-sure").bind('click', function () {
            if (check_input('template_name', '模板名称不能为空！', new RegExp('^[a-zA-Z0-9\u4e00-\u9fa5\_]{1,50}$', 'g'), '模板名称由1-50位汉字、字母、数字、下划线组成')) {
                if (check_input('primary_industry', '请选择一级行业！')) {
                    if (check_input('secondary_industry', '请选择二级行业！')) {
                        if (check_input('fk_project_id', '请选择项目名称！')) {
                            submit();
                        }
                    }
                }
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
        if (location.pathname.indexOf('/template/addpage') === -1) {
            $('.header-right').hide();
        }
    }

    var init = function() {  //初始化函数
        // 保存按钮禁用
        if (!templateId) {
            $('.f-btn-sure').removeProp('disabled');
        }

        initTip();  //初始化tips
        bindEvent();  //绑定事件
        get_industry();  //获取一级行业
        // get_project_default();  //获取默认项目选项
        project_select = get_project();  //获取项目列表 默认由后端返回
        // 获取模板信息
        if (templateId > 0) {
            setTimeout(function () {
                getTemplate(templateId);
            }, 1000);
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
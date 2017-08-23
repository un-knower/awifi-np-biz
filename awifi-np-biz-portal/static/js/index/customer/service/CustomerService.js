indexApp.factory('customerService', function($rootScope){
    var service = {};

    // 请选择客户select2实现
    service.customerSelect2 = function (selectId) {
        var ajaxUrl = '/customer/optionsList';
        customerSelect(selectId, ajaxUrl);
    };

    function customerSelect(selectId, ajaxUrl){
        selectId = selectId || 'customerSelect';
        return $('#' + selectId).select2({
            placeholder: '请选择客户',
            allowClear: true,
            ajax: {
                url: ajaxUrl,
                dataType: 'json',
                delay: 0, // 延时可能清空时未恢复
                data: function (params) {
                    return {
                        keywords: params.term
                    };
                },
                processResults: function (data, params) {
                    var _data = data.data;
                    for (var i = 0,len = _data.length; i<len; i++) {
                        var _item = _data[i];
                        _item['id'] = _item.customerId + '#' + _item.cascadeLabel + '#' + _item.customerName;
                        // 用户列表 {{customer.customerId}}#{{customer.cascadeLabel}}
                        // 新增用户 {{customer.customerId}}#{{customer.cascadeLabel}}#{{ customer.customerName }}
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

    return service;
});
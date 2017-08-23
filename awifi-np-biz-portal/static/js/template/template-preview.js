

$(function () {
    var height = $(window).height();
    if(height > 671){
        $('.main').css('height', height);
    } else {
        $('.main').css('height', '691px');
    }

    var $previewContent = $('.previewContent');
    if (height > 780){
        var previewContentHeight = $previewContent.height();
        $previewContent.css('margin-top', (height-previewContentHeight)/2 );
    } else {
        $previewContent.css('margin-top', '10px' );
    }

    /**
     * 第一回进来先请求接口，接收data
     * data:
     *      id->pageId
     *      pageType
     *      num
     *      pageSign->1:第一页；2：中间页；3：最后一页
     *      url-> iframe src中要用的地址
     */
    var urlAjax = '/template/showpage';
    var templateId = getQueryString('templateId');
    var pageType = '';
    var num = '';
    var sign = '';
    var paramFirst = {
        templateId: templateId,
        pageType:pageType,
        num:num,
        sign:sign
    };
    $.ajax({
        url: urlAjax,
        type: 'post',
        data: paramFirst,
        dataType: 'json',
        success: function (resp) {
            if (resp.result == 'FAIL') {
                jDialog.alert('提示：', resp.message);
                return;
            }
            var info = resp.data;

            var pageId = info.id;

            var pageType = info.pageType;

            var num = info.num;

            var pageSign = info.pageSign;

            var iframeURL = info.url;

            if(pageSign == '1'){
                $('#upBtn').hide();
                $('#downBtn').show();
            } else if (pageSign == '2') {
                $('#upBtn').show();
                $('#downBtn').show();
            } else if (pageSign == '3') {
                $('#upBtn').show();
                $('#downBtn').hide();
            }

            //将iframeUrl值放入src
            $('#iframeURL').attr('src',iframeURL);

            //点击 下一页 操作
            $('#downBtn').on('click', function(){
                var paramDownBtn = {
                    templateId: templateId,
                    pageType:pageType,
                    num:num,
                    sign: '1'
                };
                $.ajax({
                    url: urlAjax,
                    type: 'post',
                    data: paramDownBtn,
                    dataType: 'json',
                    success: function (resp) {
                        if (resp.result == 'FAIL') {
                            jDialog.alert('提示：', resp.message);
                            return;
                        }
                        var downInfo = resp.data;

                        pageType = downInfo.pageType;

                        num = downInfo.num;

                        pageSign = downInfo.pageSign;

                        iframeURL = downInfo.url;

                        if(pageSign == '1'){
                            $('#upBtn').hide();
                            $('#downBtn').show();
                        } else if (pageSign == '2'){
                            $('#upBtn').show();
                            $('#downBtn').show();
                        } else if (pageSign == '3'){
                            $('#upBtn').show();
                            $('#downBtn').hide();
                        }

                        //将iframeUrl值放入src
                        $('#iframeURL').attr('src',iframeURL);
                    }
                });
            });

            //点击 上一页 操作
            $('#upBtn').on('click', function(){
                var paramUpBtn = {
                    templateId: templateId,
                    pageType:pageType,
                    num:num,
                    sign: '0'
                };

                $.ajax({
                    url: urlAjax,
                    type: 'post',
                    data: paramUpBtn,
                    dataType: 'json',
                    success: function (resp) {
                        if (resp.result == 'FAIL') {
                            jDialog.alert('提示：', resp.message);
                            return;
                        }
                        var upInfo = resp.data;

                        pageType = upInfo.pageType;

                        num = upInfo.num;

                        pageSign = upInfo.pageSign;

                        iframeURL = upInfo.url;

                        //若firstPageUp为true，则隐藏上一页
                        if(pageSign == '1'){
                            $('#upBtn').hide();
                            $('#downBtn').show();
                        } else if (pageSign == '2'){
                            $('#upBtn').show();
                            $('#downBtn').show();
                        } else if (pageSign == '3'){
                            $('#upBtn').show();
                            $('#downBtn').hide();
                        }

                        //将iframeUrl值放入src
                        $('#iframeURL').attr('src',iframeURL);
                    }
                });
            });
        }
    });
});
/**
 * 获取window.location地址中参数公用方法
 * @param name
 * @returns {null}
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}



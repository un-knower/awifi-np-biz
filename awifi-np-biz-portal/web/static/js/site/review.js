/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-05-10 19:21
 * 创建作者: haoxu
 * 文件名称: review
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */
$(function () {
    var get_args = getArgs(),
        siteId = get_args['siteId'], // 站点id
        accessToken = get_args['access_token'],//获取access_token
        merchantId = get_args['merchantId'];  //商户ID

    $(".btn-save").click(function(){
        var url = '/portalsrv/site/'+siteId+'/verify?access_token='+accessToken;
        $.ajax({
            url: url,
            type: 'put',
            data: {},
            dataType: 'json',
            success: function (resp) {
                if (resp.code != '0') {
                    jDialog.alert('提示：', resp.message);
                    return;
                }else{
                    alert("审核通过");
                    window.location.href="/static/html/publish.html?siteId="+siteId+"&merchantId="+ merchantId +"&access_token=" +accessToken;
                }

            }
        });
    });
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
});
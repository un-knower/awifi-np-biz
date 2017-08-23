/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-04-17 13:16
 * 创建作者: haoxu
 * 文件名称: portal-publish
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */

$('.form_date').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2,
    forceParse: 0
});
$('.form_date2').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2,
    forceParse: 0
});
var strategyId = 0;
var get_args = getArgs(),
    siteId = get_args['siteId'], // 站点id
    accessToken = get_args['access_token'], //获取access_token
    merchantId = get_args['merchantId'];

$(function(){
     getMerchantList(accessToken);
    //编辑站点
    if(siteId && siteId >0){
        initStrategy(siteId,accessToken);
    }else{   //新增站点

    }
    $("#deploy").click(function(){
        getDeviceLists(accessToken,merchantId);
    });
    $(".btn-save").click(function(){
        publishSite(siteId,accessToken);
    });
});

function publishSite(siteId,accessToken ){
    var url = "/portalsrv/site/"+siteId+"/publish?access_token=" +accessToken;
    var strategyName =$("#strategyName").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var strategyType = $("input:radio[name='scope']").val();
    var content ="";
    if(strategyName == ""){
        jDialog.alert('提示：', "请输入策略名称");
        return false;
    }
    if(startDate =="" ){
        jDialog.alert('提示：', "请输入开始时间");
        return false;
    }
    if(endDate =="" ){
        jDialog.alert('提示：', "请输入结束时间");
        return false;
    }
    if(strategyType == ""){
        jDialog.alert('提示：', "请选择推送范围");
        return false;
    }
    var resp_data = {
        "strategyName":strategyName,
        "startDate":startDate,
        "endDate":endDate,
        "strategyType":strategyType,
        "content":content
    };
    if(strategyId !=0){
        resp_data.strategyId = strategyId;
    }

    $.ajax({
        url: url,
        type: 'put',
        contentType: "application/json",
        data: JSON.stringify(resp_data),
        dataType: 'json',
        success: function (resp) {
            if (resp.code != '0') {
                jDialog.alert('提示：', resp.msg);
                return;
            }else{
                jDialog.alert('提示：', "发布成功");
            }
        }
    });

}
function initStrategy(siteId,accessToken){
    //just for test haoxou
    var data =  { "id": 215,//策略表主键id
        "merchantId": 176199,//商户id
        "strategyName": "xxx",//策略名称
        "strategyType": 2,//策略类型：1代表全部 、2代表SSID、3代表设备id
        "startDate": "2017-04-26",//策略开始日期
        "endDate": "2017-04-30",//策略截止日期
        "ssid": "awifi-ssid",//SSID
        "devices": [//设备集合
            {
                "deviceId": "RADIUS-RADIUS-20141022-22ff325b",//设备id
                "deviceName": "8C7967045B42"//设备名称
            }]
    };
    setStrategy(data);
    //just for test haoxou
    var url = '/portalsrv/site/'+siteId+'/strategy?access_token='+accessToken;
    $.ajax({
        url: url,
        type: 'get',
        data: {},
        dataType: 'json',
        success: function (resp) {
            if (resp.code != '0') {
                jDialog.alert('提示：', resp.msg);
                return;
            }else{
                var data = resp.data;
                setStrategy(data);
            }
        }
    });
}
function setStrategy(data){
    strategyId = data.id;
    $("#strategyName").val(data.strategyName);
    if(data.strategyType ==1){
        $("input:radio[name='scope']").eq(0).attr("checked",'checked');
    }else if(data.strategyType ==2){
        $("input:radio[name='scope']").eq(1).attr("checked",'checked');
    }else{
        $("input:radio[name='scope']").eq(2).attr("checked",'checked');
    }
    $('#startDate').val(data.startDate);
    $('#endDate').val(data.endDate);
}

//获取商户的数据
function getMerchantList(accessToken,merchantName){
    var url = '/mersrv/merchants?access_token='+accessToken;
    var params = {
        "merchantName": merchantName || '',
        "pageSize": 20
    };
    var resp_data = {
        params: JSON.stringify(params),
    };
    $.ajax({
        url: url,
        type: 'get',
        data: resp_data,
        dataType: 'json',
        success: function (resp) {
            if (resp.code == '0') {
                setMerchantLists(resp.data.records);
            } else {
                jDialog.alert('提示', resp.message);
            }
        }
    });
}
function setMerchantLists(data){
    var htm = '';
    for(var i=0; i<data.length;i++){
        var item = data[i];
        htm = htm + '<option value='+item.id+' attr='+item.cascadeLabel+'>'+item.merchantName+'</option>';
    }
    $('#merchant').append(htm);
}
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
//获取设备名称，ssid集合
function getDeviceLists(accessToken,merchantId){
    var url = '/merdevsrv/merchant/'+merchantId+'/devinfo?access_token='+accessToken;
    $.ajax({
        url: url,
        type: 'get',
        data: {},
        dataType: 'json',
        success: function (resp) {
            if (resp.code == '0') {
                setDeviceLists(resp.data.deviceNames);
            } else {
                jDialog.alert('提示', resp.message);
            }
        }
    });
}
function setDeviceLists(){

}
function initTips($obj, message){
   $obj.poshytip({
        content : message,
        className: 'tip-yellow',
        showOn : 'none',
        //showTimeout: 3,
        //hideTimeout: 3,
        //timeOnScreen: 3,
        bgImageFrameSize: 9,
        alignTo: 'target',
        alignX: 'inner-left',
        offsetX: 0,
        offsetY: 20

    });
    $obj.bind('focus',function(){
        hideTips($obj);
    });
}
function initTipsArray(objArray){
    var maxLength = objArray.length;
    for(var i=0; i<maxLength; i++){
        initTips(objArray[i]);
    }
}
function showTips($obj){
    $obj.poshytip('show');
}
function updateShowTipos($obj, message){
    updateTips($obj, message);
    showTips($obj);
    setTimeout(function(){
             hideTips($obj);//3秒后自动隐藏
        },3000);
}
function hideTips($obj){
    $obj.poshytip('hide');
}
function updateTips($obj, message){
    $obj.poshytip('update',message);
}
function destroyTips($obj){
    $obj.poshytip('destroy');
}
function destroyTipsArray(objArray){
    var maxLength = objArray.length;
    for(var i=0; i<maxLength; i++){
        destroyTips(objArray[i]);
    }
}

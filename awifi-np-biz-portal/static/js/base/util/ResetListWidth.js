/**
 * resetWidth 重置列表因fixed引起的宽度问题
 * @author 沈亚芳
 * @date 2015/11/26
 */
function resetWidth(){
    var h = $(".datacontent");
    var width = h.outerWidth( true );
    $(".listtitle").css('width', width);
}
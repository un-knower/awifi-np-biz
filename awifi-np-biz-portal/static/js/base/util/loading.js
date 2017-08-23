/**
 * Created by Shin on 2016/04/20.
 *
 */
function loadingAppend (){

    $('body').append('<div class="loadingBody">' +
        '<div class="loadingBg"></div>' +
        '<div class="closeBtn">x</div>' +
        '<div class="loadingInfo">' +
        '<div class="imgInfo">' +
        '<img src="images/loading-wifi.gif" alt="">' +
        '</div>' +
        '</div>' +
        '</div>');

    var height = $(window).height();
    var imgHeight = $('.imgInfo').height();
    $('.imgInfo').css('margin-top', (height-imgHeight)/2 );

    $('.closeBtn').on('click',  function (e) {
        loadingRemove();
    })
}

function loadingRemove (){
    $('.loadingBody').remove();
}
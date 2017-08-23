indexApp.controller('leftMenuController', function($scope, leftMenuService) {

    $scope.selectMenu = function($event){
        leftMenuService.selectMenu($event);
        //alert('target= ' + target);
        //var data = target.target.getAttribute('data');
        //alert('data= ' + data);

        //alert('aaa=  ' + $(target.target).text());
    };

    $scope.sysSet=function($event){
    	leftMenuService.sysSet($event);
    };

    /**
     * 初始化左侧页面--菜单
     * Created by Shin on 2015/10/14.
     */
    function initMenu(){
        //var d = new Date();
        //console.log('本次加载时间：' + d.toLocaleString() + ' ' + d.getMilliseconds());

        //var base = new Base();
        // 导航收缩切换
        //$('.on-off').on('click', function(e){
        //    var $nav = $(this).parent();
        //    $nav.find('.branch-on').click();
        //    $nav.toggleClass('on');
        //});
        // 导航菜单高亮
        /*
        $('.nav [tree=leaf]').on('click', function(e){
            var $item = $(this);

            if($item.hasClass('active')){
                //TODO
            }else{
                $item.parents('.nav').find('.active').removeClass('active');
                $item.addClass('active');
            }
        });
        // 导航-子菜单收缩切换
        $('.nav .branch').on('click', function(e){
            var $item = $(this);
            var $leafs = null;
            if(!$item.parent().hasClass('on')){ // 简显模式下触发第一个子节点
                $('.on-off').click();
                $item.click();
                $leafs = $item.next('.nav-leaf');
                $leafs.find('li:eq(0)').eq(0).click();
                return;
            }
            $leafs = $item.next('.nav-leaf');
            var $leafheight = $leafs.find('li a');
            //alert($leafheight.height());
            var liheight = $leafheight.height();

            //alert(liheight);
            var $leaf = $leafs.find('li a.show');
            //alert($leaf.length);

            if($item.hasClass('branch-on')){
                $leafs.css('height', 0);
                if($leafs.find('.active').length !== 0){
                    $item.addClass('active');
                }
            }else{
                $item.parent().find('.branch-on').click();

                if($leafs.find('.active').length !== 0){
                    $item.removeClass('active');
                }

                if (liheight == 38) {
                    $leafs.css('height', $leaf.length*($leafheight.height()+1) -1 );
                } else {
                    $leafs.css('height', $leaf.length*($leafheight.height()*2+1) -1 );
                }

            }

            $item.toggleClass('branch-on');
        });
        */

        $('.nav-title').click(function () {
            var that = $(this);
            var sub_ul = that.next('ul');
            var nav_item = that.parent('.nav-item');
            nav_item.siblings().find('ul').slideUp();
            sub_ul.slideToggle();
        });

        $('.nav-sub-title').click(function () {
            var that = $(this);
            var sub_ul = that.next('ul');
            var nav_item = that.parent('li');
            nav_item.siblings().find('ul').slideUp();
            sub_ul.slideToggle();
        });

        // 用户菜单显示和收起
        $('.header .header-user').on('click', function(e){
            e.stopPropagation();
            var $profile = $('.header-profile');
            if($profile.height() === 0){
                $profile.css('height', $profile.find('ul').outerHeight());
                $(document).on('click', hideProfile);
            }else{
                $profile.css('height', 0);
            }
        });
        // Layout布局事件绑定
        $(window).on('resize', function(e){
            $('.main-layout').height($('body').height() - $('.header').height() - $('.nav-map').height());
        }).trigger('resize');

        // 内容切换
        $('[lhref]').on('click', function(e){
            //$('#icontainer').attr('src', $(this).attr('lhref'));
        });

        // 收起用户菜单
        $('.header-profile').on('click', 'a', function(e){
            $('.header-profile').css('height', 0);
            $('.active').removeClass('active');
        });
        // 收起用户菜单
        $('.header-profile').on('mouseleave', function(e){
            $(this).css('height', 0);
        });
    }

    // 隐藏用户菜单
    var hideProfile = function(e){
        if($(e.target).parents('.header-profile').length === 0){
            $(document).off('click', hideProfile);
            $('.header-profile').css('height', 0);
        }
    };

    /**
     * 初始化函数
     */
    function init(){
        initMenu();
        //leftMenuService.initLeftMenu();;//初始化左侧菜单
        //leftMenuService.hasPermForConfig($scope);//左侧菜单权限控制 - 系统设置
        //leftMenuService.hasPermForSysConfig($scope);//左侧菜单权限控制 - 系统参数配置
        //leftMenuService.hasPermForExceptionLog($scope);//左侧菜单权限控制 - 异常日志
        //leftMenuService.hasPermForRequestLog($scope);//左侧菜单权限控制 - 请求日志
    }
    init();
});
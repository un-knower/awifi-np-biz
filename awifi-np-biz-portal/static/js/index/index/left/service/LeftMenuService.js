indexApp.factory('leftMenuService',function(leftMenuDao){
    var service = {};
    //初始化左侧菜单
    service.initLeftMenu = function(){
    	$("#project_menu").click(function(){
             $("#project_ul").toggle();
             if($("#project_ul").css("display")=="none"){
                 $("#img01").attr("src","../../../../images/arrow_d.png");
             }else{
                 $("#img01").attr("src","../../../../images/arrow_u.png");
             }
         });

    	
        
        //点击后li动态设置背景颜色
        //var a = $('.user_menu .menu_selected').text();
        //$('.user_menu li').each(function(){
        //    alert($(this).text());
        //});
        //$('.user_menu li').bind('click',function(){
        //    $(this).addClass("menu_selected").siblings().removeClass("menu_selected");
        //})

    };
    //点击后li动态设置背景颜色
    service.selectMenu = function($event){
        var $choosed = $($event.target);

        //删除旧的选中样式
        $('.project_menu .menu_selected').removeClass('menu_selected');
        $choosed.addClass('menu_selected');

        //alert('adfd==== ' + $choosed.text());
       //alert('obj121= ' + obj.text());
       //alert('obj1111 1 = ' + $(obj).text());
       //alert('obj1122= ' + obj.text());
        //alert('aa22= ' + $(obj).text());
    };
    
    //左侧菜单 系统设置 收起展开
    service.sysSet=function($even){
            $("#sys_ul").toggle();
            if($("#sys_ul").css("display")=="none"){
                $("#img02").attr("src","../../../../images/arrow_d.png");
            }else{
                $("#img02").attr("src","../../../../images/arrow_u.png");
            }
    };
    //左侧菜单权限控制 -商户管理
    /*service.hasPermForMerchantManager = function($scope){
    	//leftMenuDao.hasPermForMerchantManager($scope);
    	alert('hasPermForMerchantManager');
    }; 
    //左侧菜单权限控制 - 商户列表
    service.hasPermForMerchantList = function($scope){
    	
    };
    //左侧菜单权限控制 - AP地图
    service.hasPermForAPMap = function($scope){
    	
    };
    //左侧菜单权限控制 - 设备管理
    service.hasPermForDeviceManager = function($scope){
    	
    };
    //左侧菜单权限控制 -设备列表 
    service.hasPermForDeviceList = function($scope){
    	
    };
    //左侧菜单权限控制 - 账号管理
    service.hasPermForAccountManager = function($scope){
    	
    };
    //左侧菜单权限控制 - 账号列表
    service.hasPermForAccountList = function($scope){
    	
    };
    //左侧菜单权限控制 - 站点管理
    service.hasPermForSiteManager = function($scope){
    	
    };
    //左侧菜单权限控制 - 模板 
    service.hasPermForTemplet = function($scope){
    	
    };
    //左侧菜单权限控制 - 站点定制 
    service.hasPermForSite = function($scope){
    	
    };
    //左侧菜单权限控制 -推送策略 
    service.hasPermForPushStrategy = function($scope){
    	
    };
  //左侧菜单权限控制 -用户管理 
    service.hasPermForUserManager = function($scope){
    	
    };
  //左侧菜单权限控制 - 用户列表
    service.hasPermForUserList = function($scope){
    	
    };
  //左侧菜单权限控制 - 上网日志
    service.hasPermForLogList = function($scope){
    	
    };
  //左侧菜单权限控制 - 白名单管理
    service.hasPermForWhiteListManager = function($scope){
    	
    };
  //左侧菜单权限控制 - 黑名单管理
    service.hasPermForBlackListManager = function($scope){
    	
    };
  //左侧菜单权限控制 - 在线用户管理
    service.hasPermForOnlineManager = function($scope){
    	
    };
  //左侧菜单权限控制 - 应用设置
    service.hasPermForApplicationSetting = function($scope){
    	
    };
  //左侧菜单权限控制 -AP管理
    service.hasPermForAPManager = function($scope){
    	
    };
  //左侧菜单权限控制 - 上网配置
    service.hasPermForWebSetting = function($scope){
    	
    };
  //左侧菜单权限控制 - 网站白名单
    service.hasPermForWebWhiteList = function($scope){
    	
    };
  //左侧菜单权限控制 -网站黑名单
    service.hasPermForWebBlackList = function($scope){
    	
    };
  //左侧菜单权限控制 - 查询统计
    service.hasPermForStatistics = function($scope){
    	
    };
  //左侧菜单权限控制 - 认证记录
    service.hasPermForAuthenticationRecords = function($scope){
    	
    };
  //左侧菜单权限控制 -网站访问排行 
    service.hasPermForSiteAccessRank = function($scope){
    	
    };
  //左侧菜单权限控制 -AP统计 
    service.hasPermForAPStatistics = function($scope){
    	
    };
  //左侧菜单权限控制 -日志管理
    service.hasPermForLogManager = function($scope){
    	
    };
    //左侧菜单权限控制 - 系统参数配置
    service.hasPermForSysConfig = function($scope){
    	leftMenuDao.hasPermForSysConfig($scope);
    };
    
    //左侧菜单权限控制 - 异常日志
    service.hasPermForExceptionLog = function($scope){
    	leftMenuDao.hasPermForExceptionLog($scope);
    };
    
    //左侧菜单权限控制 - 请求日志
    service.hasPermForRequestLog = function($scope){
    	leftMenuDao.hasPermForRequestLog($scope);
    };*/
    return service;
});
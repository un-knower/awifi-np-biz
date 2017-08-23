indexApp.config(function($routeProvider, $httpProvider) {
    $routeProvider
        .when('/', {//第一次加载时，默认模板(显示客户列表)
        	templateUrl : 'html/template/default.html'
        })
        .when('/component/list', {//组件管理--组件列表
            templateUrl : 'html/template/portal/component/componentList.html',
            controller  : 'componentListController'
        })
        .when('/component/add', {//组件管理--组件注册
            templateUrl : 'html/template/portal/component/componentAdd.html',
            controller  : 'componentAddController'
        })
        .when('/component/edit', {//组件管理--组件编辑
            templateUrl : 'html/template/portal/component/componentEdit.html',
            controller  : 'componentEditController'
        })
        .when('/site/list', {//站点管理-站点列表
            templateUrl : 'html/template/portal/site/siteList.html',
            controller  : 'siteListController'
        })
        .when('/site/defaultSiteList', {//站点管理-默认站点列表
            templateUrl : 'html/template/portal/site/defaultSiteList.html',
            controller  : 'defaultSiteListController'
        })
        .when('/template/list', {//模板管理-模板列表
            templateUrl : 'html/template/portal/template/templateList.html',
            controller  : 'templateListController'
        })
        .when('/exceptionlog/list', {//异常日志列表
            templateUrl : 'html/template/log/exceptionlogList.html',
            controller  : 'exceptionlogListController'
        })
        .when('/sysconfig/list', {//系统参数配置
            templateUrl : 'html/template/system/sysConfigList.html',
            controller  : 'sysConfigController'
        })
        .when('/sysconfig/add',{//新增系统参数
            templateUrl : 'html/template/system/sysConfigAdd.html',
            controller  : 'sysConfigAddController'
        })
        .when('/sysconfig/edit',{//编辑系统参数
            templateUrl : 'html/template/system/sysConfigEdit.html',
            controller  : 'sysConfigEditController'
        })
        .otherwise({
            redirectTo: '/'
        });
});
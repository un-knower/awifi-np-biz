var indexApp = angular.module('indexApp', ['ngRoute', 'ui.bootstrap', 'ngFileUpload']);

/**
 * 和配置块不同，运行块在注入器创建之后被执行，它是所有AngularJS应用中第一个被执行的方法。
 * 运行块是AngularJS中与main方法最接近的概念。运行块中的代码块通常很难进行单元测试，它是和应用本身高度耦合的。
 * 运行块通常用来注册全局的事件监听器。例如，我们会在.run()块中设置路由事件的监听器以及过滤未经授权的请求。
 * @auth 许小满
 * @date 2015-10-23 14:33:20
 */
indexApp.run(function($rootScope, $cacheFactory) {
    //加载权限编码集合，并缓存
    //permissionService.getCurUserPermissionCode();
    //路由切换事件
   /* $rootScope.$on('$routeChangeStart', function(evt, next, current) {

    });*/
});

/**
 * http 请求拦截器，如果session失效，自动返回登录页面
 * @auth 许小满
 * @date 2015-10-27 11:46:20
 */
indexApp.factory('sessionInjector', ['$q',function($q) {
    var responseInterceptor = {
        response: function(response) {
            var deferred = $q.defer();
            if (response.status == '200') {
                if (response.data && (typeof response.data) === "string" && response.data.match(/login\.min\.js/m)) {
                    window.top.location.href = '/logout';
                }
            }
            deferred.resolve(response);
            return deferred.promise;
        }
    };
    return responseInterceptor;
}]);

/**
 * http 添加自定义拦截器
 * @auth 许小满
 * @date 2015-10-27 11:46:20
 */
indexApp.config(['$httpProvider', function($httpProvider) {
    /*初始化时，header头如果获取不到，则给它赋值为{}*/
    if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    /*禁用IE对ajax的缓存--特别重要*/
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';

    $httpProvider.interceptors.push('sessionInjector');
}]);

/**
 *
 */
indexApp.factory('init', function () {
    var resetWidth = function () {
        var h = $(".datacontent");
        var width = h.outerWidth( true );
        $(".listtitle").css('width', width);
    };
    return resetWidth;
});
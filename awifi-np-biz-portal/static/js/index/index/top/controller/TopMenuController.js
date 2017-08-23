indexApp.controller('topMenuController', function($scope, topMenuService) {
    topMenuService.getCurUserName($scope);//获取当前用户名
});
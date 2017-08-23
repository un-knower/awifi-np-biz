indexApp.controller('sysConfigShowController', function($scope, sysConfigService) {
    //alert('sysConfigShowController');
    var id = $scope.id;
    sysConfigService.show($scope, id);
});
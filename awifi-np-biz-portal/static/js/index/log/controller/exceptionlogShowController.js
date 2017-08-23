indexApp.controller('exceptionlogShowController', function($scope, exceptionlogService) {
	 
	 var id = $scope.id;
	 
	 exceptionlogService.show($scope, id);
	 
});
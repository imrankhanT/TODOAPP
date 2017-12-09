var app = angular.module('ToDo');

app.controller('homeController',function($scope){
	 $scope.openLeftMenu = function() {
		    $mdSidenav('left').toggle();
		  };
})



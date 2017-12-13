var todo = angular.module('ToDo');

todo.controller('dummyController', function($scope, dummyService, $location) {
	var user = dummyService.acessToken($scope.user, $scope.error);
	user.then(function(response) {
		localStorage.setItem('token', response.data.message);
		console.log(response.data);
		$location.path('home');
	});
})
var ToDo = angular.module('ToDo');

ToDo.controller('registerController', function($scope, registerService,
		$location) {
	$scope.registerUser = function() {
		var register = registerService.registerUser($scope.user, $scope.error);
		register.then(function(response) {
			console.log("Registration successfully........" + response.data);
			localStorage.setItem('token', response.data.responseMessage)
			$location.path('/login')
		}, function(response) {
			console.log("Registration Fails........");
			$scope.error = response.data.responseMessage;
		});
	}
});
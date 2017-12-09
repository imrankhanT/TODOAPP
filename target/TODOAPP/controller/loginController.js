var todo = angular.module('ToDo');

todo.controller('loginController', function($scope, loginService, $location) {
	$scope.loginUser = function() {
		var user = loginService.loginUser($scope.user, $scope.error);
		user.then(function(response) {
			console.log(response.data.responseMessage);
			localStrorage.setItem('token', response.data.responseMessage);
			console.log('Login Sucessfully...........');
		}, function(response) {
			if (response.status == 409) {
				$scope.error = response.data.responseMessage;
			} else {
				console.log("Login Fials....");
				$scope.error = "Please! Enter The Valid Credential";
			}
		})
	}
})
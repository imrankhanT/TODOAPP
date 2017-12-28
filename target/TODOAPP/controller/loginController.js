var todo = angular.module('ToDo');

todo.controller('loginController', function($scope, loginService, $location) {
	$scope.loginUser = function() {
		console.log($scope.user);
		var user = loginService.loginUser($scope.user, $scope.error);
		user.then(function(response) {
			console.log(response.data.message);
			localStorage.setItem('token', response.data.message);
			console.log(response.data);
			// $scope.user = response.data;
			$location.path('home');
			console.log('Login Sucessfully...........');
			toastr.success("Login Sucessfully...........");
		}, function(response) {
			if (response.status == 409) {
				$scope.error = response.data.message;
			} else {
				console.log("Login Fials....");
				$scope.error = "Please! Enter The Valid Credential";
			}
		})
	}
})
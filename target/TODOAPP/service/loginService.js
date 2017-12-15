var todo = angular.module('ToDo');

todo.service('loginService', function($http, $location) {

	this.loginUser = function(user) {
		console.log(user);
		return $http({
			method : 'POST',
			url : 'login',
			data : user
		})
	}
})
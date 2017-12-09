var todo = angular.module('ToDo');

todo.factory('registerService', function($http, $location) {
	var register = {};

	register.registerUser = function(user) {
		return $http({
			method : "POST",
			url : 'register',
			data : user
		})
	}
	return register;
})
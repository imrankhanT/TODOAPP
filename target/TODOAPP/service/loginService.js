var todo = angular.module('ToDo');

todo.factory('loginService', function($http, $location) {
	var login = {};

	login.loginUser = function(user) {
		return $http({
			method : "post",
			url : 'login',
			data : user
		})
	}
	return login;
})
var todo = angular.module('ToDo');

todo.factory('dummyService', function($http, $location) {
	var dummy = {};
	dummy.acessToken = function() {
		return $http({
			method : 'get',
			url : 'dummy',
		})

	}
return dummy;
})
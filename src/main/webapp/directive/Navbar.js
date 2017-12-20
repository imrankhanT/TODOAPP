var Todo = angular.module('ToDo');

Todo.directive('topNavBar', function() {
	return {
		restrict : 'E',
		templateUrl : './template/TopNavBar.html'
	}

});

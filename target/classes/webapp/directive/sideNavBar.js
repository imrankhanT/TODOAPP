var Todo = angular.module('ToDo');

Todo.directive('sideNavBar', function() {
	return {
		restrict : 'E',
		templateUrl : './template/SideNavBar.html'
	}

});
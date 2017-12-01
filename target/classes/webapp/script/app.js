var ToDo = angular.module('ToDO',['ui.router', 'ngSanitize','ngAnimate', 'ngMaterial']);

ToDo.config(['$stateProvider','$urlRouterProvider',
	function($stateProvider,$urlRouterProvider){
		
		$urlRouterProvider.otherwise('login');
}]);
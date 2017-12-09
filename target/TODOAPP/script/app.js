var ToDo = angular.module('ToDo', [ 'ui.router', 'ngSanitize', 'ngAnimate',
		'ngMaterial' ]);

ToDo.config([ '$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {
			$stateProvider.state('login', {
				url : '/login',
				templateUrl : 'template/login.html',
				controller : 'loginController'
			})

			$stateProvider.state('register', {
				url : '/register',
				templateUrl : 'template/register.html',
				controller : 'registerController'
			})
			 $urlRouterProvider.otherwise('login'); 
		} ]);
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

			$stateProvider.state('home', {
				url : '/home',
				templateUrl : 'template/home.html',
				controller : 'noteController'
			})
			
			$stateProvider.state('trash',{
				url : '/trash',
				templateUrl : 'template/trash.html',
			    controller : 'noteController'
			})
			
			$stateProvider.state('archive',{
				url : '/archive',
				templateUrl : 'template/archive.html',
			    controller : 'noteController'
			})
			$urlRouterProvider.otherwise('login');
		} ]);
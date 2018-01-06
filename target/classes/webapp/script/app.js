var ToDo = angular.module('ToDo', [ 'ui.router', 'ngAnimate', 'ngMaterial',
		'ngMaterialDatePicker', 'tb-color-picker', 'toastr', 'ngFileUpload' ]);

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

			$stateProvider.state('trash', {
				url : '/trash',
				templateUrl : 'template/trash.html',
				controller : 'noteController'
			})

			$stateProvider.state('archive', {
				url : '/archive',
				templateUrl : 'template/archive.html',
				controller : 'noteController'
			})

			$stateProvider.state('dailog', {
				url : '/dailog',
				templateUrl : 'template/dailog.html',
				controller : 'noteController'
			})

			$stateProvider.state('collaborator', {
				url : '/collaborator',
				templateUrl : 'template/collaborator.html',
				controller : 'noteController'
			})

			$stateProvider.state('dummy', {
				url : '/dummy',
				templateUrl : 'template/dummy.html',
				controller : 'dummyController'
			})

			$stateProvider.state('reminder', {
				url : '/reminder',
				templateUrl : 'template/reminder.html',
				controller : 'noteController'
			})

			$stateProvider.state('search', {
				url : '/search',
				templateUrl : 'template/search.html',
				controller : 'noteController'
			})

			$stateProvider.state('label', {
				url : '/label.html',
				templateUrl : 'template/label.html',
				controller : 'noteController'
			})
			$urlRouterProvider.otherwise('login');
		} ]);
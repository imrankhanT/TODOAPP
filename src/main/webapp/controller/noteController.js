var app = angular.module('ToDo');

app.controller('noteController', function($scope, notesService, $location,
		$mdDialog,$mdSidenav) {

	$scope.options = [ 'transparent', '#FF8A80', '#FFD180', '#FFFF8D',
			'#CFD8DC', '#80D8FF', '#A7FFEB', '#CCFF90' ];
	$scope.color = '#FF8A80';

	// function to change the color of notes
	$scope.colorChanged = function(newColor, data) {
		data.color = newColor;
		var update = notesService.updateNotes(data);
		update.then(function(response) {
			getNotes();
			console.log(response.data);
		});

	}

	$scope.openLeftMenu = function() {
		console.log("hi.................");
		$mdSidenav('left').toggle();
	}

	var getNotes = function() {
		var getAllNotes = notesService.getAllNotes();
		getAllNotes.then(function(response) {
			$scope.data = response.data;
			$scope.user = {};
			console.log(response.data);
		});
	}

	// function to make a exact copy of notes
	$scope.makeCopy = function(data) {
		console.log("sdjhdhghfhfdjgfdkgfdjghkdg" + data);
		var makeCopies = notesService.addNotes(data);
		makeCopies.then(function(response) {
			console.log(response.data);
			getNotes();
		});
	}

	// functio to show the user information like pic,name and Email
	var getUser = function() {
		console.log("inside get user controller");
		var getUsers = notesService.getUser();
		getUsers.then(function(response) {
			$scope.user = {};
			$scope.user.name = response.data.name;
			$scope.user.email = response.data.email;
			$scope.user.profilePic = response.data.profilePic;
			console.log(response.data);
		});
	}

	// function to add notes
	$scope.addNotes = function() {
		console.log($scope.notes);
		var notes = notesService.addNotes($scope.notes, $scope.error);
		notes.then(function(response) {
			console.log(response.data);
			console.log('Notes Created Sucessfully...........');
			getNotes();
		}, function(response) {
			if (response.status == 409) {
				$scope.error = response.data;
			} else {
				console.log("Fail To created Notes....");
			}
		})
	}

	// fucnction to read all notes from data base
	$scope.readNotes = function() {
		var getAllNotes = notesService.getAllNotes();

		getAllNotes.then(function(response) {
			$scope.data = response.data;
			console.log(response.data)
		})
	}

	// function to perform trash operation
	$scope.deleteNotes = function(data) {

		if (data.trash == false)
			data.trash = true;
		else
			data.trash = false;

		var update = notesService.updateNotes(data);
		update.then(function(response) {
			getNotes();
		})
		getNotes();
	}

	// function to perform archive
	$scope.archive = function(data) {
		if (data.archive)
			data.archive = false;
		else
			data.archive = true;

		var update = notesService.updateNotes(data);
		update.then(function(response) {
			getNotes();
		})
		getNotes();
	}

	// Function To Perform pin operation
	$scope.pin = function(data) {
		if (data.pin == false)
			data.pin = true;
		else
			data.pin = false;

		var update = notesService.updateNotes(data);
		update.then(function(response) {
			getNotes();
		})
		getNotes();
	}

	// function to perform delete operation
	$scope.deleteForver = function(data) {
		var deleteNotes = notesService.deleteNotes(data);
		deleteNotes.then(function(response) {
			getNotes();
		}, function(response) {
			getNotes();
		})
		getNotes();
	}

	// function to perform update operation
	$scope.updateNotes = function(data) {
		var update = notesService.updateNotes(data);
		console.log(data);
		update.then(function(response) {
			getNotes();
		}, function(response) {
			getNotes();
		})
		getNotes();
	}

	// function to logout

	$scope.logout = function() {

		var logout = notesService.logout();

		logout.then(function(response) {
			cosnole.log("Inside Logout.......");
			localStorage.removeItem('token');
			$loactio.path('home');
		})
	}

	// function to show the dailog box
	$scope.showDailog = function(events, data) {
		$mdDialog.show({
			templateUrl : 'template/dailog.html',
			controller : noteController,
			parent : angular.element(document.body),
			targetEvent : events,
			locals : {
				data : data
			},
		});
	}

	// controller to pass the md-dailog box data into notesController using
	// locals
	function noteController($scope, data) {
		$scope.data = data;

		$scope.updateNotes = function(data) {
			var update = notesService.updateNotes(data);
			console.log(data);
			update.then(function(response) {
				$mdDialog.hide();
				getNotes();
			}, function(response) {
				getNotes();
			})
			getNotes();
		}

	}

	getNotes();
	getUser();
});

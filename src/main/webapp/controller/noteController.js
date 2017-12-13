var app = angular.module('ToDo');

app.controller('noteController', function($scope, notesService, $location,
		$mdDialog) {
	
	var getNotes = function() {
		var getAllNotes = notesService.getAllNotes();
		getAllNotes.then(function(response) {
			$scope.data = response.data;
			$scope.user = {};
			console.log(response.data);
		});
	}

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

	$scope.readNotes = function() {
		var getAllNotes = notesService.getAllNotes();

		getAllNotes.then(function(response) {
			$scope.data = response.data;
			console.log(response.data)
		})
	}

	// function to perform trash operaton
	$scope.deleteNotes = function(data) {

		if (data.trash == false) {
			data.trash = true;
			console.log(data);
			var update = notesService.updateNotes(data);
			update.then(function(response) {
				getNotes();
			}, function(response) {
				getNotes();
			})
		} else {
			console.log("false................................", data);
			data.trash = false;
			console.log(data.trash);
			var update = notesService.updateNotes(data);
			update.then(function(response) {
				getNotes();
			}, function(response) {
				getNotes();
			})
		}
		getNotes();
	}

	// function to perform archive
	$scope.archive = function(data) {

		if (data.archive == false) {
			data.archive = true;
			console.log(data);
			var update = notesService.updateNotes(data);
			update.then(function(response) {
				getNotes();
			}, function(response) {
				getNotes();
			})
		} else {
			data.archive = false;
			console.log(data);
			var update = notesService.updateNotes(data);
			update.then(function(response) {
				getNotes();
			}, function(response) {
				getNotes();
			})
		}
		getNotes();
	}

	// Function To Perform pin operation
	$scope.pin = function(data) {
		if (data.pin == false) {
			data.pin = true;
			console.log(data);

			var pin = notesService.updateNotes(data);
			pin.then(function(response) {
				getNotes();
			})
		} else {
			data.pin = false;
			console.log(data);
			var pin = notesService.update(data);
			pin.then(function(response) {
				getNotes();
			})
		}
	}

	$scope.deleteForver = function(data) {
		var deleteNotes = notesService.deleteNotes(data);
		deleteNotes.then(function(response) {
			getNotes();
		}, function(response) {
			getNotes();
		})
		getNotes();
	}

	$scope.openLeftMenu = function() {
		$mdMenu
	}

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

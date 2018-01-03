var app = angular.module('ToDo');

app.controller('noteController', function($scope, $state, notesService,
		$location, $mdDialog, $mdSidenav, mdcDateTimeDialog, loginService,
		$interval, $filter, $mdToast, toastr, Upload) {

	$scope.displayDialog = function() {
		mdcDateTimeDialog.show({
			maxDate : $scope.maxDate,
			time : false
		}).then(function(date) {
			$scope.selectedDateTime = date;
			console.log('New Date / Time selected:', date);
		}, function() {
			console.log('Selection canceled');
		});
	};

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
		$mdSidenav('right').toggle();
	}

	var getNotes = function() {
		var getAllNotes = notesService.getAllNotes();

		getAllNotes.then(function(response) {
			$scope.data = response.data;
			var notes = response.data;
			console.log(response.data);
			$interval(function() {
				for (var i = 0; i < $scope.data.length; i++) {

					if (notes[i].reminderDate != "") {
						var reminderNotesDate = notes[i].reminderDate;
						var dates = $filter('date')(new Date(),
								'MMM dd yyyy HH:mm:ss');
						if (dates == reminderNotesDate) {
							toastr.success("Title : " + notes[i].title + " "
									+ "Description : " + notes[i].description);
						}
					}
				}
			});

		});

	}

	// function to make a exact copy of notes
	$scope.makeCopy = function(data) {
		data.trash = false;
		data.archive = false;
		var makeCopies = notesService.addNotes(data);
		makeCopies.then(function(response) {
			console.log(response.data);
			getNotes();
		});
	}

	// function to show the user information like pic,name and Email
	var getUser = function() {
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
		var notes = notesService.addNotes($scope.notes, $scope.error);
		notes.then(function(response) {
			console.log(response.data);
			console.log('Notes Created Sucessfully...........');
			getNotes();
		}, function(response) {
			if (response.status == 409) {
				$scope.error = response.data;
				toastr('')
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

	// code Snippest for changing the color of current page of top nav bar
	if ($state.current.name == "reminder") {
		$scope.navBarColor = "#7d8b8e";
	} else if ($state.current.name == "home") {
		$scope.navBarColor = "#fb0";
	} else if ($state.current.name == "archive") {
		$scope.navBarColor = '#008B8B';
	} else if ($state.current.name == "trash") {
		$scope.navBarColor = "#555";
	}

	// function to perform trash operation
	$scope.deleteNotes = function(data) {
		console.log(data);
		if (data.trash == false)
			data.trash = true;
		else
			data.trash = false;

		data.archive = false;
		data.pin = false;
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

		data.pin = false;
		data.trash = false;
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

		data.archive = false;
		data.trash = false;
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

	/* image uploading */

	$scope.type = {};
	$scope.openHiddenButton = function(note) {
		console.log("inside the openHidden");
		$('#imageFile').trigger('click');
		$scope.type = note;
	}

	$scope.stepsModel = [];
	$scope.imageUpload = function(note) {
		var reader = new FileReader();
		console.log("note : " + note);
		reader.onload = $scope.imageLoader;
		reader.readAsDataURL(note.notePicture);
	}

	$scope.imageLoader = function(image) {
		$scope.$apply(function() {
			$scope.stepsModel.push(image.target.result);
			var imageSrc = image.target.result;
			$scope.type.notePicture = imageSrc;
			console.log("-------------------------->" + imageSrc);
			var updateData = notesService.updateNotes($scope.type);
			updateData.then(function(response) {
				console.log(response);
				getNotes();
			}, function(response) {
				console.log(response);
			});
		});
	}

	// function to perform update operation
	$scope.updateNotes = function(data) {
		console.log("Note Picture--->" + data.notePicture);
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
		localStorage.removeItem('token');
		toastr.success("Logout Successfully...........");
		$location.path('login');
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

	$scope.collaboratorDailog = function(events, data) {
		console.log("collaborators------->" + events);
		$mdDialog.show({
			templateUrl : 'template/collaborators.html',
			parent : angular.element(document.body),
			controller : homeController,
			clickOutsideToClose : true,
			targetEvent : events,
			locals : {
				data : data
			},
		});
	}

	// Label dailog
	$scope.labelDailog = function(events) {
		$mdDialog.show({
			templateUrl : 'template/labels.html',
			parent : angular.element(document.body),
			controller : labelController,
			targetEvent : events,
			locals : {
				labels : $scope.labels
			},
		});
	}

	
	$scope.openLabelDailog = function(events) {
		$mdDialog.show({
			templateUrl : 'template/noteLabel.html',
			parent : angular.element(document.body),
		    controller : noteLabelController,
			clickOutsideToClose : true,
			targetEvent : events,
			locals : {
				labels : $scope.labels
			},
		});
	}
	
	function noteLabelController($scope,labels){
		$scope.labels = labels;
		getAllLabels();
	}
	
	// labelController
	function labelController($scope, labels) {
		$scope.labels = labels;
		
		$scope.saveLabel = function(label) {
			console.log("Inside SaveLabel--->" + label);
			var label = notesService.saveLabel(label);

			label.then(function(response) {
				console.log(response.data);
				getNotes();
				getAllLabels();

			})
		}

		$scope.done = function() {
			$mdDialog.hide();
		}
		$scope.deleteLabel = function(label) {
			var label = notesService.deleteLabels(label);

			label.then(function(response) {
				console.log("Deleted Sucessfully......." + label);
				getNotes();
				getAllLabels();
			})
		}
	}

	var getAllLabels = function() {
		var getAllLabel = notesService.getAllLabel();

		getAllLabel.then(function(response) {
			$scope.labels = response.data;
			var label = response.data;
			console.log($scope.labels);
		})
	}
	getAllLabels();
	// homeController for Collaborator Dailog
	function homeController($scope, data) {
		$scope.data = data;
		console.log(data);
		var ownerInfo = notesService.getOwner(data);
		ownerInfo.then(function(response) {

			$scope.owner = response.data;
			console.log(response.data);
		})
		$scope.saveCollab = function(email, note) {
			var resp = notesService.storeInfo(email, note);

			resp.then(function(response) {
				$mdDialog.hide();
				console.log(response.data);
			})
		}

		$scope.cancel = function() {
			$mdDialog.hide();
		}

		$scope.deleteCollabUser = function(email, data) {
			console.log("Response Date--->" + email);
			notesService.deleteCollaborator(email, data);
		}
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

	// function to update reminder date
	$scope.reminder = function(data, reminderDateTime) {
		// console.log("Inside Remainder..........");
		console.log(data);
		var date = new Date(reminderDateTime);
		var dates = $filter('date')(new Date(date), 'MMM dd yyyy HH:mm:ss');
		// var parseDate = Date.parse();
		data.reminderDate = dates;
		var updateReminderTime = notesService.updateNotes(data);

		updateReminderTime.then(function(response) {
			getNotes();
		})
		console.log("date------>" + data.reminderDate);
	}

	// fucntion to delete the reminder date
	$scope.deleteReminder = function(data) {
		console.log("jkdgkogodgnfodjgnfjdgnfdgjfd");
		data.reminderDate = "";
		var updateReminderTime = notesService.updateNotes(data);

		updateReminderTime.then(function(response) {
			getNotes();
		})
	}

	getNotes();
	getUser();
});

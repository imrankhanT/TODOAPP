var todo = angular.module('ToDo');

todo.factory('notesService', function($http, $location) {
	var notes = {};

	notes.addNotes = function(notes) {
		return $http({
			method : 'post',
			url : 'addNotes',
			data : notes,
			headers : {
				'accToken' : localStorage.getItem('token')
			}
		});
	}

	notes.getAllNotes = function(data) {
		return $http({
			method : 'get',
			url : 'getAllNotes',
			headers : {
				'accToken' : localStorage.getItem('token')
			}
		});
	}

	notes.updateNotes = function(data) {
		console.log("Inside sERVICE" + data);
		return $http({

			method : 'POST',
			url : 'updateNotes',
			data : data,
			headers : {
				'accToken' : localStorage.getItem('token')
			}
		});
	}

	notes.deleteNotes = function(data) {
		return $http({
			method : 'delete',
			url : 'deleteNotes/' + data.id,
			data : data,
			headers : {
				'accToken' : localStorage.getItem('token')
			}
		})

	}

	notes.getUser = function() {
		console.log("inside get user service");
		return $http({
			method : 'get',
			url : 'getUser',
			headers : {
				'accToken' : localStorage.getItem('token')
			}
		})
	}

	notes.logout = function() {
		return $http({
			method : 'get',
			url : 'logout'
		})
	}

	notes.getOwner = function(data) {
		console.log("Inside owner Service......");
		return $http({
			method : 'post',
			url : 'getOwner',
			data : data
		})
	}

	return notes;
})
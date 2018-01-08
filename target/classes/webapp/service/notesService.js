var todo = angular.module('ToDo');

todo.factory('notesService', function($http, $location) {
	var notes = {};

	notes.addNotes = function(note) {
		return $http({
			method : 'post',
			url : 'addNotes',
			data : note,
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

	notes.storeInfo = function(email, note) {
		return $http({
			method : 'put',
			url : "shareNotes/" + note.id,
			headers : {
				'accToken' : localStorage.getItem('token'),
				'email' : email
			}
		})

	}

	notes.deleteCollaborator = function(email, note) {
		return $http({
			method : 'post',
			url : "deleteCollaborator",
			data : note,
			headers : {
				'accToken' : email
			}
		})
	}

	notes.saveLabel = function(labels) {
		console.log("inside Service of Save Label" + labels);
		return $http({
			method : "POST",
			url : "insertLabel/" + labels,
			headers : {
				'accToken' : localStorage.getItem('token')
			}
		})
	}

	notes.getAllLabel = function() {
		return $http({
			method : "get",
			url : 'getAllLabels',
			headers : {
				'accToken' : localStorage.getItem('token')
			}
		})
	}

	notes.deleteLabels = function(label) {
		console.log("Label---->    " + label.id);
		return $http({
			method : "delete",
			url : "deleteLabels/" + label.id,
			data : label
		})
	}

	notes.updateLabel = function(label) {
		return $http({
			method : "PUT",
			url : "updateLabel/" + label.id + "/" + label.labelName,
			headers : {
				'accToken' : localStorage.getItem('token')
			}
		})
	}

	notes.updateNotesLabels = function(note, label) {
		return $http({
			method : "POST",
			url : "updateNotesLabels/" + note.id + "/" + label.id,
			headers : {
				'accToken' : localStorage.getItem('token')
			}
		})
	}
	return notes;
})
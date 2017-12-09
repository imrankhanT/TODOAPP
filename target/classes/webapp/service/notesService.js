var todo = angular.module('ToDo');

todo.factory('notesService', function($http, $location) {
	var notes = {};

	notes.addNotes = function(notes) {
		return $http({
			method : 'post',
			url : 'addNotes',
			data : notes,
			headers:{
				'accToken':localStorage.getItem('token')
			}
		});
	}
	
	
	notes.getAllNotes = function(){
		return $http({
			method : 'get',
			url : 'getAllNotes',
			headers:{
				'accToken':localStorage.getItem('token')
			}
		});
	}
	
	notes.updateNotes = function(data){
		console.log(data.isTrash);
		return $http({
			
			method : 'POST',
			url : 'updateNotes',
			data : data,
			headers:{
				'accToken':localStorage.getItem('token')
			}
		});
	}
	
	
	/*notes.archive=function(data){
		 $http({
				method : 'post',
				url : 'archive',
				data : data,
				headers:{
					'accToken':localStorage.getItem('token')
				}
			});
	}*/
	return notes;
})
var app = angular.module('ToDo');

app.controller('noteController', function($scope, notesService, $location) {

	var getNotes=function(){
	var getAllNotes = notesService.getAllNotes();
	getAllNotes.then(function(response) {
		$scope.data = response.data;
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
	
	$scope.deleteNotes = function(data){ 
		
		data.trash=true;
		console.log(data);
		 var update = notesService.updateNotes(data);
		 update.then(function(response){
			 getNotes();
		 },function(response){
			 getNotes();
		 })
		/*if(data.isTrash){
	     data.isTrash = false;
	     var update = notesService.updateNotes(data);
	     update.then(function(response){
	    	console.log("trash Value changed.....");
	     })
		}else{
			data.isTrash = true;
			var update = notesService.updateNotes(data);
			update.then(function(response){
				console.log("Notes Deleted Sucessfully....");
			})
		}
		getNotes();*/
	}
	
	
	$scope.archive=function(data){
		
		data.archive=true;
		console.log(data);
		 var update = notesService.updateNotes(data);
		 update.then(function(response){
			 getNotes();
		 },function(response){
			 getNotes();
		 })
		 /*if(data.archive){
			 data.archive = false;
			 var archives = notesService.updateNotes(data);
			 archives.then(function(response){
				 console.log("Archived Sucessfully......");
			 })
		 }else{
			data.isArchive = false;
			var archives = notesService.updateNotes(data);
			archives.then(function(response){
			console.log("Archive value changed sucessfully.....");
			})
		 }
		*/
		getNotes();
	}
	

	getNotes();
});

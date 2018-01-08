<html>
<head>

<link rel="stylesheet"
	href="bower_components/angular-material/angular-material.min.css">

<link rel="stylesheet"
	href="bower_components/material-icons/css/material-icons.min.css">

<link rel="stylesheet"
	href="bower_components/colorPicker/css/colorPickerStyle.css">

<link rel="stylesheet" type="text/css"
	href="bower_components/angular-toastr/dist/angular-toastr.css">

<link rel="stylesheet" type="text/css"
	href="bower_components/angular-material-datetimepicker/css/material-datetimepicker.css">
<link rel="stylesheet" type="text/css"
	href="bower_components/angular-material-datetimepicker/dist/material-datetimepicker.min.css">
<!-- STYLE CSS -->
<link rel="stylesheet" type="text/css" href="css/login.css">
<link rel="stylesheet" type="text/css" href="css/register.css">
<link rel="stylesheet" type="text/css" href="css/home.css">
<link rel="stylesheet" type="text/css" href="css/dailog.css">
<link rel="stylesheet" type="text/css" href="css/reminder.css">


<script src="bower_components/angular/angular.min.js"></script>
<script
	src="bower_components/angular-ui-router/release/angular-ui-router.js"></script>
<script src="bower_components/angular-material/angular-material.min.js"></script>
<script src="bower_components/angular-animate/angular-animate.min.js"></script>
<script src="bower_components/angular-aria/angular-aria.min.js"></script>
<script src="bower_components/angular-messages/angular-messages.min.js"></script>

<script src="bower_components/moment/moment.js"></script>
<!-- JAQUERY -->
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script
	src="bower_components/angular-toastr/dist/angular-toastr.tpls.js"></script>
<script src="bower_components/ng-file-upload/ng-file-upload.min.js"></script>


<script
	src="bower_components/angular-material-datetimepicker/dist/angular-material-datetimepicker.min.js"></script>
<script	src="bower_components/angular-material-datetimepicker/dist/angular-material-datetimepicker.min.js.map"></script>
<script src="bower_components/angular-material-datetimepicker/js/angular-material-datetimepicker.js"></script>


<!-- SCRIPT -->
<script src="script/app.js"></script>

<!-- CONTROLLER -->
<script src="controller/loginController.js"></script>
<script src="controller/registerController.js"></script>
<script src="controller/noteController.js"></script>
<script src="controller/dummyController.js"></script>

<!-- Services -->
<script src="service/loginService.js"></script>
<script src="service/registerService.js"></script>
<script src="service/notesService.js"></script>
<script src="service/dummyService.js"></script>

<!-- <Directive> -->
<script src="bower_components/colorPicker/js/colorPicker.js"></script>
<script src="directive/Navbar.js"></script>
<script src="directive/sideNavBar.js"></script>
<script src="directive/divDirective.js"></script>
</head>
<body ng-app="ToDo">
	<div ui-view></div>
</body>
</html>

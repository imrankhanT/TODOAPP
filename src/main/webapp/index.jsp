<html>
<head>

<script src="bower_components/angular/angular.js"></script>
<script
	src="bower_components/angular-ui-router/release/angular-ui-router.js"></script>
<script src="bower_components/angular-sanitize/angular-sanitize.js"></script>

<link rel="stylesheet"
	href="bower_components/angular-material/angular-material.min.css">
<script src="bower_components/angular-animate/angular-animate.min.js"></script>
<script src="bower_components/angular-aria/angular-aria.min.js"></script>
<script src="bower_components/angular-messages/angular-messages.min.js"></script>
<script src="bower_components/angular-material/angular-material.min.js"></script>
<link rel="stylesheet"
	href="bower_components/material-icons/css/material-icons.min.css">


<!-- STYLE CSS -->
<link rel="stylesheet" type="text/css" href="css/login.css">
<link rel="stylesheet" type="text/css" href="css/register.css">

<!-- SCRIPT -->
<script type="text/javascript" src="script/app.js"></script>

<!-- CONTROLLER -->
<script src="controller/loginController.js"></script>
<script src="controller/registerController.js"></script>
<!-- <script scr="controller/noteController.js"></script> -->
<script type="text/javascript " src="controller/noteController.js"></script>

<!-- Services -->
<script src="service/loginService.js"></script>
<script src="service/registerService.js"></script>
<script src="service/notesService.js"></script>

</head>
<body ng-app="ToDo">
	<div ui-view></div>
</body>
</html>
